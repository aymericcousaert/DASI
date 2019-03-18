/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 *
 * @author acousaert
 */
@Entity

public class Employe extends Personne implements Serializable {

    
    private Time debutTravail;
    private Time finTravail;
    private Boolean estDispo;
    @OneToOne
    private Intervention intervention;

    public Employe(Time debutTravail, Time finTravail,String civilite, String nom, String prenom, Date dateNaissance, String adresse, String numTel, String mail, String mdp) {
        super(civilite, nom, prenom, dateNaissance, adresse, numTel, mail, mdp);
        this.debutTravail = debutTravail;
        this.finTravail = finTravail;
        this.estDispo = false;
    }

   

  

    public Employe() {
        super();
    }

    public Boolean getEstDispo() {
        return estDispo;
    }

    public void setEstDispo(Boolean estDispo) {
        this.estDispo = estDispo;
    }

 

    public Time getDebutTravail() {
        return debutTravail;
    }

    public void setDebutTravail(Time debutTravail) {
        this.debutTravail = debutTravail;
    }

    public Time getFinTravail() {
        return finTravail;
    }

    public void setFinTravail(Time finTravail) {
        this.finTravail = finTravail;
    }
    
    
}