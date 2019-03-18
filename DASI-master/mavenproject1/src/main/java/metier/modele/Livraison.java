/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author acousaert
 */
@Entity
public class Livraison extends Intervention {
    private String livraison;

    public Livraison() {
    }

    public Livraison(String livraison, String description, Client client) {
        super(description, client);
        this.livraison = livraison;
    }

    

    public String getLivraison() {
        return livraison;
    }

    public void setLivraison(String livraison) {
        this.livraison = livraison;
    }
    
    
}
