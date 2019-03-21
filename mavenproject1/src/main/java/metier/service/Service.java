/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import dao.InterventionDAO;
import dao.JpaUtil;
import dao.PersonneDAO;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.persistence.RollbackException;
import metier.modele.Animal;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.Incident;
import metier.modele.Intervention;
import metier.modele.Livraison;
import metier.modele.Personne;
import static util.DebugLogger.log;
import static util.GeoTest.getFlightDistanceInKm;
import static util.GeoTest.getLatLng;
import static util.GeoTest.getTripDurationOrDistance;
import static util.GeoTest.getTripDurationByBicycleInMinute;
import static util.Message.envoyerMail;
import static util.Message.envoyerNotification;

/**
 *
 * @author acousaert
 */
public class Service {
    
    
    
    public void inscrireClient(String civilite, String nom, String prenom, Date dateNaissance, String adresse, String numTel, String mail, String mdp) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        PersonneDAO pdao = new PersonneDAO();
        
        Client p = new Client(civilite,nom,prenom,dateNaissance,adresse,numTel,mail,mdp);
        calculCoord(p);
        if(pdao.verifiePersonne(p)){
            try{
                pdao.ajoutePersonne(p);
                JpaUtil.validerTransaction();
            }
            catch(RollbackException e){
                log(e.getMessage());
                JpaUtil.annulerTransaction();
            }
            envoyerMail("contact@proact.if", p.getMail(), "Bienvenue chez PROACT'IF", "Bonjour " + p.getPrenom() + ", nous vous confirmons votre inscription au service PROACT'IF. Votre numéro est : " + p.getId() + "."); 
        } else {
            envoyerMail("contact@proact.if", p.getMail(), "Bienvenue chez PROACT'IF", "Bonjour " + p.getPrenom() + ", Votre inscription a échoué. L'adresse e-mail renseignée est déjà enregistrée. ");
        }
        
        
        
        JpaUtil.fermerEntityManager();
    }
    
    private void calculCoord(Client c) {
        LatLng coords;
        coords = getLatLng(c.getAdresse());
        c.setLatitude(coords.lat);
        c.setLongitude(coords.lng);
    }
    
    public void ajouterEmploye(Employe e){
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        PersonneDAO pdao = new PersonneDAO();
        if(pdao.verifiePersonne(e)){
            try{
                pdao.ajoutePersonne(e);
                JpaUtil.validerTransaction();
            }
            catch(RollbackException ex){
                log(ex.getMessage());
                JpaUtil.annulerTransaction();
            }
        } else {
            System.out.println("L'employé existe deja");
        }
        JpaUtil.fermerEntityManager();
    }
    
    public Personne connecterPersonne(String mail, String mdp) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        PersonneDAO pdao = new PersonneDAO();
        Personne pers;
        try{
            pers = pdao.connectePersonne(mail, mdp);
            JpaUtil.validerTransaction();
        } catch(RollbackException ex) {
            log(ex.getMessage());
            pers = null;
            JpaUtil.annulerTransaction();
        }
        JpaUtil.fermerEntityManager();
        return pers;
    }

    public void ajouterIntervention(Client c, String description, String type, String... args) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        PersonneDAO pdao = new PersonneDAO();;
        InterventionDAO idao =  new InterventionDAO();
        Employe plusProche = null;
        try {
            List<Employe> employesDispo = trouverDispoEmploye();
            if(employesDispo.isEmpty()){
                envoyerNotification(c.getNumTel(),"Votre demande d'intervention a été rejetée : Aucun employé n'est disponible actuellement. Veuillez réessayez ultérieurement.");
            } else {
                plusProche = trouverPlusProcheEmploye(employesDispo,c);
                pdao.lock(plusProche);
                Intervention inter = null;
                switch(type){
                case "Incident" : 
                    inter = new Incident(description,c,plusProche);
                    break;
                case "Animal" :
                    inter = new Animal(args[0],description,c,plusProche);
                    break;
                case "Livraison" : 
                    inter = new Livraison(args[0],args[1],description,c,plusProche);
                    break;
                }
                c.addIntervention(inter);
                plusProche.setIntervention(inter);
                plusProche.setEstDispo(Boolean.FALSE);
                idao.ajouteIntervention(inter);
                envoyerNotification(plusProche.getNumTel(),"Bonjour " + plusProche.getPrenom() + ", Intervention " + type + " demandée le " + inter.getHeureDebut() + " pour " + c.getPrenom() + " "
                    + c.getNom() + ", " + c.getAdresse() + ". \"" + description + "\". Trajet : " + getTripDurationByBicycleInMinute(getLatLng(plusProche.getAdresse()),getLatLng(c.getAdresse()))+ " min en vélo");
            }
            JpaUtil.validerTransaction();
        } catch (RollbackException ex){
            log(ex.getMessage());
            JpaUtil.annulerTransaction();
        }
        JpaUtil.fermerEntityManager();
    }
    
    private Employe trouverPlusProcheEmploye(List<Employe> employesDispo, Client c) {
        Employe plusProcheEmploye = employesDispo.get(0);
        double tempsActuel = getTripDurationOrDistance(TravelMode.BICYCLING, true, getLatLng(employesDispo.get(0).getAdresse()),getLatLng(c.getAdresse()));
        
        for(Employe e : employesDispo){
            double nouveauTemps = getTripDurationOrDistance(TravelMode.BICYCLING, true, getLatLng(e.getAdresse()),getLatLng(c.getAdresse()));
            if(tempsActuel > nouveauTemps){
                plusProcheEmploye = e;
                tempsActuel = nouveauTemps;
            }
        }
        
        return plusProcheEmploye;
    }
    
    private List<Employe> trouverDispoEmploye() {
        PersonneDAO pdao = new PersonneDAO();
        List<Employe> employesDispo;
        Time heureDebut = java.sql.Time.valueOf(java.time.LocalTime.now());
        try {
            employesDispo = pdao.trouveDispoEmploye(heureDebut);
        } catch (RollbackException ex){
            log(ex.getMessage());
            employesDispo = null;
        }
        return employesDispo;
    }
    
    public void cloturerIntervention(Employe e,String statut, String commentaire, Date date){
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try{
            InterventionDAO idao = new InterventionDAO();
            PersonneDAO pdao = new PersonneDAO();
            Intervention inter = e.getIntervention();
            inter.setCommentaire(commentaire);
            inter.setStatut(statut);
            inter.setHeureFin(date);
            e.setEstDispo(Boolean.TRUE);
            e.setIntervention(null);
            e = (Employe) pdao.mergePersonne(e);
            inter = (Intervention) idao.mergeIntervention(inter);
            envoyerNotification(inter.getClient().getNumTel(),"Votre demande d'intervention du " + inter.getHeureDebut() + " a été cloturée à  " 
                + inter.getHeureFin() + ". Résultat : " + inter.getStatut() + ". "
                + inter.getCommentaire() + ". Cordialement, " + e.getPrenom() + "." );
            JpaUtil.validerTransaction();
        } catch (RollbackException ex){
            log(ex.getMessage());
            JpaUtil.annulerTransaction();
        }
        JpaUtil.fermerEntityManager();
    }
    
    public List<Intervention> rechercheInterventions(Employe e, Integer km, Boolean Animal, Boolean Incident, Boolean Livraison, Boolean mesInterventions) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        InterventionDAO idao = new InterventionDAO();
        List<Intervention> InterRecherchees;
        try {
            InterRecherchees = idao.rechercheInterventions(e, km, Animal, Incident, Livraison, mesInterventions);
            for( int i = 0; i < InterRecherchees.size(); i++){
            double distance = getFlightDistanceInKm(getLatLng(e.getAdresse()), getLatLng(InterRecherchees.get(i).getClient().getAdresse()));
            if (distance > km) {
                InterRecherchees.remove(i);
            }}
            JpaUtil.validerTransaction();
        } catch (RollbackException ex){
            log(ex.getMessage());
            InterRecherchees = null;
            JpaUtil.annulerTransaction();
        }
        
        JpaUtil.fermerEntityManager();
        return InterRecherchees;
        
    }
    
    public Intervention findIntervention(Employe e){
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        InterventionDAO idao = new InterventionDAO();
        Intervention i = null;
        try{
            i = idao.finder(e);
            JpaUtil.validerTransaction();
        } catch (RollbackException ex){
            log(ex.getMessage());
            JpaUtil.annulerTransaction();
        }
        JpaUtil.fermerEntityManager();
        return i;
    }
    
    public Personne updatePersonne(Personne p){
        PersonneDAO pdao = new PersonneDAO();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        Personne newP = p;
        try{
            newP = pdao.finder(p);
            JpaUtil.validerTransaction();
        } catch (RollbackException e) {
            log(e.getMessage());
            JpaUtil.annulerTransaction();
        }
        JpaUtil.fermerEntityManager();
        return newP;
    }
    
    public Vector<Intervention> historiqueClient(Client c){
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        Vector<Intervention> historique = new Vector<Intervention>();
        InterventionDAO idao = new InterventionDAO();
        try{
            historique = idao.historiqueClient(c);
            JpaUtil.validerTransaction();
        } catch (RollbackException e){
            log(e.getMessage());
            JpaUtil.annulerTransaction();
        }
        JpaUtil.fermerEntityManager();
        return historique;
    } 
    
    
    
    
    
}
