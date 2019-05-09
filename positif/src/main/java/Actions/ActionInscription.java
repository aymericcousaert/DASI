/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import metier.data.Client;
import metier.service.Service;

/**
 *
 * @author mguilhin
 */
public class ActionInscription extends Action{
    
    @Override
    public boolean executer(HttpServletRequest request){
        
        Service service = new Service();
        final SimpleDateFormat JSON_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
        Boolean dateNaissanceFormat = true;
        Boolean codePostalFormat = true;
        
        String civilite = request.getParameter("civilite");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        Date dateNaissance = null;
        try {
            dateNaissance = JSON_DATE_FORMAT.parse(request.getParameter("dateNaissance"));
        } catch (ParseException ex) {
            dateNaissanceFormat = false;
        }
        Integer codePostal = 0;
        System.out.println("*******************");
        System.out.println(request.getParameter("codePostal"));
        try {
            codePostal = Integer.parseInt(request.getParameter("codePostal"));
        } catch (NumberFormatException e) {
            codePostalFormat = false;
        }
        
        
        String tel = request.getParameter("tel");
        String mail = request.getParameter("mail");
        String mdp = request.getParameter("mdp");
        
        if (codePostalFormat && dateNaissanceFormat) {
            Client c = new Client(civilite, nom, prenom, dateNaissance, codePostal, tel, mail, mdp);
            service.insrcrireClient(c);
        } 
        request.setAttribute("dateNaissanceFormat", dateNaissanceFormat);
        request.setAttribute("codePostalFormat", codePostalFormat);
        
        
        return false;
    }
    
}
