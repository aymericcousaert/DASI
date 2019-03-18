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
public class Animal extends Intervention {
    private String animal;

    public Animal() {
    }

    public Animal(String animal, String description, Client client) {
        super(description, client);
        this.animal = animal;
    }

    


    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    
}
