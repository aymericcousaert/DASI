/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import javax.persistence.Entity;

/**
 *
 * @author acousaert
 */
@Entity
public class Livraison extends Intervention {
    private String objet;
    private String entreprise;

    public Livraison() {
    }

    public Livraison(String colis, String entreprise, String description, Client client, Employe employe) {
        super(description, client, employe);
        this.objet = colis;
        this.entreprise = entreprise;
    }

    public String getColis() {
        return objet;
    }

    public void setColis(String colis) {
        this.objet = colis;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    
    
}
