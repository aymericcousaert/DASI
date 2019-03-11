/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import dao.ClientDAO;
import dao.JpaUtil;
import javax.persistence.RollbackException;
import metier.modele.Client;
import static util.DebugLogger.log;
import static util.GeoTest.getLatLng;
import static util.Message.envoyerMail;

/**
 *
 * @author acousaert
 */
public class Service {
    
    public void calculCoord(Client c) {
        LatLng coords = getLatLng("61 Avenue Roger Salengro, Villeurbanne");
        c.setLatitude(coords.lat);
        c.setLongitude(coords.lng);
    }
    
    public void inscrireClient(Client p) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        ClientDAO cdao = new ClientDAO();
        calculCoord(p);
        if(cdao.verifieClient(p)){
            try{
                System.out.println("try service");
                cdao.ajouteClient(p);
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
    
    
}
