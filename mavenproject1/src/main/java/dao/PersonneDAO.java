/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Time;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import metier.modele.Employe;
import metier.modele.Personne;
import static util.DebugLogger.log;

/**
 *
 * @author acousaert
 */
public class PersonneDAO {
    
    public void ajoutePersonne(Personne p){
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(p);
        } catch (Exception e) {
            log(e.getMessage());
        }   
        
    }
    
    
    public Personne mergePersonne(Personne p){
        EntityManager em = JpaUtil.obtenirEntityManager();
        Personne newP;
        try {
            newP = em.merge(p);
        } catch (Exception e) {
            log(e.getMessage());
            newP = p;
        }   
        return newP;
    }
    
    public boolean verifiePersonne(Personne p) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Boolean existePas;
        try {
            String jpql = "select c from Client c where c.mail =:mail";
            Query query = em.createQuery(jpql);
            query.setParameter("mail", p.getMail());
            Personne pers = (Personne) query.getSingleResult();
            existePas = false;
        } catch (Exception e) {
            log(e.getMessage());
            existePas = true;
        }
        return existePas;
        
    }
    
    
    public Personne finder(Personne p){
        EntityManager em = JpaUtil.obtenirEntityManager();
        Personne newP = p;
        try {
            newP = em.find(Personne.class, p.getId());
        } catch (Exception e) {
            log(e.getMessage());
        }
        return newP;
    }
    
    public Personne connectePersonne(String mail, String mdp) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Personne pers;
        try {
            String jpql = "select p from Personne p where p.mail =:mail and p.mdp =:mdp";
            Query query = em.createQuery(jpql);
            query.setParameter("mail", mail);
            query.setParameter("mdp", mdp);
            pers = (Personne) query.getSingleResult();
            log("\n Connexion pour "+ pers.getPrenom() + " " + pers.getNom() + " réussie.\n");
        } catch (Exception e) {
            log("\n Connexion échouée \n");
            log(e.getMessage());
            pers = null;
        }
        return pers;
    }

    public List<Employe> trouveDispoEmploye(Time heureDebut) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Employe> employesDispo;
        try {
        String jpql = "select e from Employe e where e.debutTravail < :heureDebut and e.finTravail > :heureDebut and e.estDispo = 1";
        Query query = em.createQuery(jpql);
        query.setParameter("heureDebut", heureDebut);
        employesDispo = (List<Employe>) query.getResultList();
        } catch (Exception ex){
            employesDispo = null;
            log(ex.getMessage());
        }
        return employesDispo;
    }
    
    public void lock(Employe e){
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.lock(e, LockModeType.OPTIMISTIC);
        } catch (Exception ex){
            log("catch");
            log(ex.getMessage());
        }
        
    }

    
    
}
