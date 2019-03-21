/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Client;
import metier.modele.Intervention;
import static util.DebugLogger.log;

/**
 *
 * @author acousaert
 */
public class InterventionDAO {
    
    public void ajouteIntervention(Intervention i){
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(i);
        } catch (Exception e) {
            log("Invervention non ajoutée\n");
        }   
        
    }
    
    public Intervention mergeIntervention(Intervention i){
        EntityManager em = JpaUtil.obtenirEntityManager();
        Intervention newI;
        try {
            newI = em.merge(i);
        } catch (Exception e) {
            log(e.getMessage());
            newI = i;
        }   
        return newI;
    }
    
    public Vector<Intervention> historiqueClient(Client c){
        EntityManager em = JpaUtil.obtenirEntityManager();
        Vector<Intervention> historique = new Vector<Intervention>();
        try {
            String jpql = "select i from Intervention i where i.client = :client";
            Query query = em.createQuery(jpql);
            query.setParameter("client",c);
            historique = (Vector<Intervention>) query.getResultList();
        } catch (Exception e) {
            log(e.getMessage());
        }
        return historique;
    }
}
