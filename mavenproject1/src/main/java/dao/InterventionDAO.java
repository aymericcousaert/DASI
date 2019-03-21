/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.util.ArrayList;
import java.util.List;
import metier.modele.Employe;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Client;
import metier.modele.Intervention;
import static util.DebugLogger.log;

/**
 *
 * @author acousaert mguilhin
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
    
     public List<Intervention> rechercheInterventions(Employe e, Integer km, Boolean Animal, Boolean Incident, Boolean Livraison, Boolean mesInterventions) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Intervention> InterRecherchees = new ArrayList<Intervention>();
        try { 
            Query query;
            if (Incident) {
                if (mesInterventions) {
                    String jpql = "select i from Incident i where i.employe =:emp";
                    query = em.createQuery(jpql); 
                    query.setParameter("emp",  e);
                }
                else {
                    String jpql = "select i from Incident i";
                    query = em.createQuery(jpql); 
                }
                InterRecherchees.addAll((List<Intervention>) query.getResultList());
            }
            if (Animal) {
                if (mesInterventions) {
                    String jpql = "select i from Animal i where i.employe =:emp";
                    query = em.createQuery(jpql); 
                    query.setParameter("emp",  e);
                }
                else {
                    String jpql = "select i from Animal i";
                    query = em.createQuery(jpql); 
                }
                InterRecherchees.addAll((List<Intervention>) query.getResultList());
            }
            if (Livraison) {
                if (mesInterventions) {
                    String jpql = "select i from Livraison i where i.employe =:emp";
                    query = em.createQuery(jpql); 
                    query.setParameter("emp",  e);
                }
                else {
                    String jpql = "select i from Livraison i";
                    query = em.createQuery(jpql); 
                }
                InterRecherchees.addAll((List<Intervention>) query.getResultList());
            }
        } catch (Exception ex){
            InterRecherchees = null;
            log(ex.getMessage());
        }
        return InterRecherchees;
     }
     
     public Intervention finder(Employe e){
        EntityManager em = JpaUtil.obtenirEntityManager();
        Intervention inter = null;
        try {
            String jpql = "select i from Intervention i where i.employe = :employe";
            Query query = em.createQuery(jpql);
            query.setParameter("employe",e);
            inter = (Intervention) query.getSingleResult();
        } catch (Exception ex) {
            log(ex.getMessage());
        }
        return inter;
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
