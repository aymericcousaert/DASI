/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
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
import static util.GeoTest.getLatLng;
import static util.GeoTest.getTripDurationOrDistance;
import static util.Message.envoyerMail;

/**
 *
 * @author acousaert
 */
public class Service {
    
    public void calculCoord(Client c) {
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
            envoyerMail("contact@proact.if", p.getMail(), "Bienvenue chez PROACT'IF", "Bonjour " + p.getPrenom() + ", Votre inscription a échouée. L'adresse e-mail est déja enregistrée! ");
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
        JpaUtil.creerEntityManager();
        PersonneDAO pdao = new PersonneDAO();
        List<Employe> employesDispo;
        Time heureDebut = java.sql.Time.valueOf(java.time.LocalTime.now());
        try {
            employesDispo = pdao.trouveDispoEmploye(heureDebut);
        } catch (RollbackException ex){
            log(ex.getMessage());
            employesDispo = null;
        }
        JpaUtil.fermerEntityManager();
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
        List<Employe> employesDispo = trouverDispoEmploye();
        if(employesDispo.isEmpty()){
            System.out.println("Intervention rejetée : Aucun employé disponible");
        } else {
            
            switch(type){
            case "Incident" : 
                Incident i = new Incident(nom,description,c);
                break;
            case "Animal" :
                Animal a = new Animal(nom,description,c);
                break;
            case "Livraison" : 
                Livraison l = new Livraison(nom,description,c);
                break;
            
        }
            
        }
    }
    
    
    
}