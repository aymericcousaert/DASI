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
    
    private void calculCoord(Client c) {
        LatLng coords;
        coords = getLatLng(c.getAdresse());
        c.setLatitude(coords.lat);
        c.setLongitude(coords.lng);
    }
    
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
    
    public void ajouterIntervention(Client c, String description, String type, String nom) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            List<Employe> employesDispo = trouverDispoEmploye();
            if(employesDispo.isEmpty()){
                System.out.println("Intervention rejetée : Aucun employé disponible");
            } else {
                Employe plusProche = trouverPlusProcheEmploye(employesDispo,c);
                InterventionDAO idao = new InterventionDAO();
                Intervention inter = null;
                switch(type){
                case "Incident" : 
                    inter = new Incident(nom,description,c,plusProche);
                    break;
                case "Animal" :
                    inter = new Animal(nom,description,c,plusProche);
                    break;
                case "Livraison" : 
                    inter = new Livraison(nom,description,c,plusProche);
                    break;
                }
                plusProche.setIntervention(inter);
                plusProche.setPrenom("test");
                idao.ajouteIntervention(inter);
                envoyerNotification(plusProche.getNumTel(),"Intervention " + type + " demandée le " + inter.getHeureDebut() + " pour " + c.getPrenom() + " "
                    + c.getNom() + ", " + c.getAdresse() + ". \"" + description + "\". Trajet : " + getTripDurationByBicycleInMinute(getLatLng(plusProche.getAdresse()),getLatLng(c.getAdresse()))+ " min en vélo");
            }
            JpaUtil.validerTransaction();
        } catch (RollbackException ex){
                log(ex.getMessage());
                JpaUtil.annulerTransaction();
        }
        JpaUtil.fermerEntityManager();
    }
    
    public void cloturerIntervention(Employe e,String statut, String commentaire, Date date){
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        System.out.println(e.getPrenom());
        /*PersonneDAO pdao = new PersonneDAO();
        pdao.mergePersonne(e);*/
        JpaUtil.validerTransaction();
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
    
    
    
    
    
}
