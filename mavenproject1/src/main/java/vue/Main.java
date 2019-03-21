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
import metier.modele.Animal;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.Incident;
import metier.modele.Intervention;
import metier.modele.Livraison;
import metier.service.Service;

/**
 *
 * @author acousaert
 */
public class Main {
    
    public static void main (String args[]) throws ParseException, InterruptedException{
        
        //Format date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        JpaUtil.init();
        Service s = new Service();
        
        //CREATION DES EMPLOYES EN DUR
        Employe e1 = new Employe(new Time(8,0,0),new Time(20,0,0),"M","Brel","Jacques",sdf.parse("25/12/1993"), "10 rue des alouettes, Lyon","0664987456","jacques.brel@gmail.com","azerty");
        Employe e2 = new Employe(new Time(8,0,0),new Time(20,0,0),"M","Durand","Pierre",sdf.parse("15/05/1993"), "20 avenue Roger Salengro, Villeurbanne","0634978848","pierre.durand@orange.fr","azerty");
        Employe e3 = new Employe(new Time(8,0,0),new Time(20,0,0),"M","Dubois","Paul",sdf.parse("14/07/1985"), "9 rue du Puits Vieux, Saint-Priest","0664684952","paul.dubois@insa-lyon.fr","azerty");
        s.ajouterEmploye(e1);
        s.ajouterEmploye(e2);
        s.ajouterEmploye(e3);
        
        //INSCRIPTION DES CLIENTS
        s.inscrireClient("M", "Toutcourt", "Paul",sdf.parse("21/12/1983") , "7 Avenue Jean Capelle Ouest, Villeurbanne", "0652573263", "paul.toutcourt@gmail.com","azerty");
        s.inscrireClient("M", "Dupont", "Lionel",sdf.parse("03/12/1997") , "5 rue des tuiliers, Lyon", "0652563444", "lionel.dupont@gmail.com","azerty");
        s.inscrireClient("M", "Berger", "Antoine",sdf.parse("25/02/1976") , "20 avenue Albert Einstein, Villeurbanne", "0664587915", "antoine.berger","azerty");
        s.inscrireClient("M", "Saoule", "Sam",sdf.parse("23/03/1992") , "50 rue professeur florence, Lyon", "0664978541", "sam.saoule@gmail.com","azerty");
        s.inscrireClient("M", "Crush", "Sarah",sdf.parse("29/09/1987") , "10 rue de Rivoli, Paris", "0631648521", "sarah.crush@gmail.com","azerty");
        s.inscrireClient("M", "Crush", "Sarah",sdf.parse("29/09/1987") , "10 rue de Rivoli, Paris", "0631648521", "sarah.crush@gmail.com","azerty");
       
        //CONNEXION D'UN CLIENT
        
        Client Paul = (Client) s.connecterPersonne("paul.toutcourt@gmail.com", "azerty");
        
        //CONNEXION D'UN EMPLOYE
        
        Employe Pierre = (Employe) s.connecterPersonne("pierre.durand@orange.fr", "azerty");
        
        //AJOUT D'UNE INTERVENTION
        
        s.ajouterIntervention(Paul, "fuite d'eau signalée par le voisin du dessous", "Incident");
        s.ajouterIntervention(Paul, "promener medor dans le jardin", "Animal", "Chien");
        s.ajouterIntervention(Paul, "deposer le colis sur la table de la cuisine", "Livraison","colis","DHL");
        s.ajouterIntervention(Paul, "J'ai laissé ma gazinière allumée", "Incident");
        
        //EMPLOYE CONSULTE SON INTERVENTION EN COURS
        
        Pierre = (Employe)s.updatePersonne(Pierre);
        System.out.println("\r\nVotre intervention en cours : "+ s.findIntervention(Pierre).getDescription() + "\r\n");
        
        //CLIENT CONSULTE L'HISTORIQUE DE SES INTERVENTIONS
        
        System.out.println("\r\nVoici l'historique de vos interventions : \r\n");
        for(Intervention i : s.historiqueClient(Paul)){
            String type = (i instanceof Incident)?"Incident ":"";
            type = (i instanceof Animal)?"Animal ":type;
            type = (i instanceof Livraison)?"Livraison ":type;
            System.out.println("Intervention "+ type + "demandée le " + i.getHeureDebut() + ". " + i.getDescription() + ". Statut : "+ i.getStatut() + ". Commentaire : " + i.getCommentaire() + ".");
        }
        
        //EMPLOYE CONSULTE LES INTERVENTIONS DU JOUR SUR LE TABLEAU DE BORD
        
        System.out.println("\r\nVoici le tableau de bord des interventions du jour selon les critères selectionnés : \r\n");
        for(Intervention i : s.rechercheInterventions(Pierre ,20, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE)){
            String type = (i instanceof Incident)?"Incident ":"";
            type = (i instanceof Animal)?"Animal ":type;
            type = (i instanceof Livraison)?"Livraison ":type;
            System.out.println("Intervention "+ type + "demandée le " + i.getHeureDebut() + ". " + i.getDescription() + ". Statut : "+ i.getStatut() + ". Commentaire : " + i.getCommentaire() + ".");
        }
        
        //CLOTURER INTERVENTION
        
        Pierre = (Employe)s.updatePersonne(Pierre);
        s.cloturerIntervention(Pierre,"succes","Le robinet était mal fermé",new Date());
        
        //NOUVELLE CONSULTATION DE L'HISTORIQUE DES INTERVENTIONS
        
        System.out.println("\r\nVoici l'historique de vos interventions : \r\n");
        for(Intervention i : s.historiqueClient(Paul)){
            String type = (i instanceof Incident)?"Incident ":"";
            type = (i instanceof Animal)?"Animal ":type;
            type = (i instanceof Livraison)?"Livraison ":type;
            System.out.println("Intervention "+ type + "demandée le " + i.getHeureDebut() + ". " + i.getDescription() + ". Statut : "+ i.getStatut() + ". Commentaire : " + i.getCommentaire() + ".");
        }
        
        
        //AJOUT D'UNE INTERVENTION
        
        s.ajouterIntervention(Paul, "J'ai laissé ma gazinière allumée", "Incident");
        
        //NOUVELLE CONSULTATION DU TABLEAU DE BORD DES INTERVENTIONS
        
        Pierre = (Employe)s.updatePersonne(Pierre);
        System.out.println("\r\nVoici le tableau de bord des interventions du jour selon les critères selectionnés : \r\n");
        for(Intervention i : s.rechercheInterventions(Pierre ,20, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE)){
            String type = (i instanceof Incident)?"Incident ":"";
            type = (i instanceof Animal)?"Animal ":type;
            type = (i instanceof Livraison)?"Livraison ":type;
            System.out.println("Intervention "+ type + "demandée le " + i.getHeureDebut() + ". " + i.getDescription() + ". Statut : "+ i.getStatut() + ". Commentaire : " + i.getCommentaire() + ".");
        }
    }

}   
