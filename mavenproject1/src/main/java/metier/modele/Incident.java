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
public class Incident extends Intervention {

    public Incident() {
    }

    public Incident(String description, Client client, Employe employe) {
        super(description, client,employe);
    }

    
}
