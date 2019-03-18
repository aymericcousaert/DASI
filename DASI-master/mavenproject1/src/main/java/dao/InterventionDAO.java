/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.EntityManager;
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
            log(e.getMessage());
        }   
        
    }
    
}
