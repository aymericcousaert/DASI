/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Client;
import static util.DebugLogger.log;

/**
 *
 * @author acousaert
 */
public class ClientDAO {
    
    public void ajouteClient(Client p){
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(p);
        } catch (Exception e) {
            log(e.getMessage());
        }   
        
    }
    
    public boolean verifieClient(Client p) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Client resultat = new Client();
        try {
            String jpql = "select c from Client c where c.mail =:mail";
            Query query = em.createQuery(jpql);
            query.setParameter("mail", p.getMail());
            resultat = (Client) query.getSingleResult();
            return false;
        } catch (Exception e) {
            log(e.getMessage());
            return true;
        }
        
    }
    
    
}
