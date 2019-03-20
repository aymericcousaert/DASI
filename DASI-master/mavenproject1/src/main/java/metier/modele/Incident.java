/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.sql.Time;
import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author acousaert
 */
@Entity
public class Incident extends Intervention {
    private String nature;

    public Incident() {
    }

    public Incident(String nature, String description, Client client, Employe employe) {
        super(description, client,employe);
        this.nature = nature;
    }

   


    
    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }
    
}
