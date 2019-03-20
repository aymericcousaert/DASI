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
        
        Employe Jacques = new Employe(new Time(8,0,0),new Time(16,0,0),"M","Bress","A",d, "50 rue professeur Florence, Lyon","0606060606","stephane.bress@msn.fr","azerty");
        //Employe Jilles = new Employe(new Time(8,0,0),new Time(16,0,0),"M","Bress","B",d, "50 rue de Rivoli, Paris","0606060606","stephaazerne.bress@msn.fr","azerty");
        //Employe Thomas = new Employe(new Time(8,0,0),new Time(16,0,0),"M","Bress","C",d, "50 rue des tuiliers, Lyon","0606060606","sazerne.bress@msn.fr","azerty");
        //Employe Théo = new Employe(new Time(8,0,0),new Time(16,0,0),"M","Bress","D",d, "9 rue du Puits vieux, Saint-Priest","0606060606","azerty.bress@msn.fr","azerty");

        //afficherClient(Paul);
        Service s = new Service();
        s.inscrireClient("M", "Toutcourt", "Paul",d , "7 Avenue Jean Capelle Ouest, Villeurbanne", "0652573263", "paul.toutcourt@gmail.com","azerty");
        s.inscrireClient("M", "Toutcourt", "Pierre",d , "7 Avenue Jean Capelle Ouest, Villeurbanne", "0652573263", "paul.toutcourt@gmail.com","azerty");
        s.ajouterEmploye(Jacques);
        //s.ajouterEmploye(Jilles);
        //s.ajouterEmploye(Thomas);
        //s.ajouterEmploye(Théo);
        Client Paul = (Client) s.connecterPersonne("paul.toutcourt@gmail.com", "azerty");
        //log(s.trouverPlusProcheEmploye(s.trouverDispoEmploye(),Paul).getPrenom());
        s.ajouterIntervention(Paul, "j'ai glissé chef", "Incident", "ouille");
        //log(Jacques.getIntervention().getCommentaire());
        //s.cloturerIntervention(Jacques,"succes","c t dur",d);
        
    }

}   
