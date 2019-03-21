/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;


/**
 *
 * @author acousaert
 */

@Entity
public class Client extends Personne implements Serializable {
    @OneToMany(mappedBy="client")
    private List<Intervention> mesInterventions;
    private double longitude;
    private double latitude;
    
    public Client(){
        super();
    }

    public Client(String civilite, String nom, String prenom, Date dateNaissance, String adresse, String numTel, String mail, String mdp) {
        super(civilite, nom, prenom, dateNaissance, adresse, numTel, mail, mdp);
    }

    public List<Intervention> getMesInterventions() {
        return mesInterventions;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void addIntervention(Intervention i){
        mesInterventions.add(i);
    }
    
}
