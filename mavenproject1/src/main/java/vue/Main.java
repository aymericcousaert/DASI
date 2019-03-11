/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import dao.JpaUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import metier.modele.Client;
import metier.service.Service;

/**
 *
 * @author acousaert
 */
public class Main {
    
    public static void main (String args[]) throws ParseException{
        
        JpaUtil.init();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = sdf.parse("21/12/2012");
        Client Paul = new Client("M", "Toutcourt", "Paul",d , "7 Avenue Jean Capelle Ouest, Villeurbanne", "0652573263", "paul.toutcourt@gmail.com");
       
        //afficherClient(Paul);
        Service s = new Service();
        s.inscrireClient(Paul);
        
        
    }
    
    public static void afficherClient(Client c){
        System.out.println(c.getPrenom() + " " + c.getNom());
    }
}   
