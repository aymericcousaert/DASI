/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import dao.JpaUtil;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import metier.modele.Client;
import metier.modele.Employe;
import metier.service.Service;
import static util.DebugLogger.log;

/**
 *
 * @author acousaert
 */
public class Main {
    
    public static void main (String args[]) throws ParseException{
        
        JpaUtil.init();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = sdf.parse("21/12/2012");
        //Client Paul = new Client("M", "Toutcourt", "Paul",d , "7 Avenue Jean Capelle Ouest, Villeurbanne", "0652573263", "paul.toutcourt@gmail.com","azerty");
        //Client Pierre = new Client("M", "Toutcourt", "Pierre",d , "7 Avenue Jean Capelle Ouest, Villeurbanne", "0652573263", "paul.toutcourt@gmail.com","azerty");
        
        Employe Jacques = new Employe(new Time(0,0,0),new Time(8,0,0),"M","Bress","StephaneA",d, "50 rue professeur Florence, Lyon","0606060606","stephane.bress@msn.fr","azerty");
        Employe Jilles = new Employe(new Time(8,0,0),new Time(16,0,0),"M","Bress","StephaneB",d, "50 rue professeur Florence, Lyon","0606060606","stephaazerne.bress@msn.fr","azerty");
        
        //afficherClient(Paul);
        Service s = new Service();
        s.inscrireClient("M", "Toutcourt", "Paul",d , "7 Avenue Jean Capelle Ouest, Villeurbanne", "0652573263", "paul.toutcourt@gmail.com","azerty");
        s.inscrireClient("M", "Toutcourt", "Pierre",d , "7 Avenue Jean Capelle Ouest, Villeurbanne", "0652573263", "paul.toutcourt@gmail.com","azerty");
        s.ajouterEmploye(Jacques);
        s.ajouterEmploye(Jilles);
        s.connecterPersonne("paul.toutcourt@gmail.com", "azerty");
        
        
        
    }

}   
