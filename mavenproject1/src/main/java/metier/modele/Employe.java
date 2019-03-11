/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.sql.Time;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author acousaert
 */
@Entity
@Table
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Time debutTravail;
    private Time finTravail;
}
