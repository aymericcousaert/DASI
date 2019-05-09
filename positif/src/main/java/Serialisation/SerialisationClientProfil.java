/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mguilhin
 */
public class SerialisationClientProfil extends Serialisation{
    
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
       
        String civilite = (String)session.getAttribute("civilite");
        String nom = (String)session.getAttribute("nom");
        String prenom = (String)session.getAttribute("prenom");
        Date dateNaissance = (Date)session.getAttribute("dateNaissance");
        Integer adresse = (Integer)session.getAttribute("adresse");
        String tel = (String)session.getAttribute("tel");
        String mail = (String)session.getAttribute("mail");
        String sgnAstro = (String)session.getAttribute("sgnAstro");
        String sgnChinois = (String)session.getAttribute("sgnChinois"); 
        String aniTot = (String)session.getAttribute("aniTot");
        String couleurBonheur = (String)session.getAttribute("couleurBonheur");
        
       
        JsonObject jsonContainer = new JsonObject();
        jsonContainer.addProperty("civilite", civilite);
        jsonContainer.addProperty("nom", nom);
        jsonContainer.addProperty("prenom", prenom);
        //jsonContainer.addProperty("dateNaissance", dateNaissance);
        jsonContainer.addProperty("adresse", adresse.toString());
        jsonContainer.addProperty("tel", tel);
        jsonContainer.addProperty("mail", mail);
        jsonContainer.addProperty("sgnAstro", sgnAstro);
        jsonContainer.addProperty("sgnChinois", sgnChinois);
        jsonContainer.addProperty("aniTot", aniTot);
        jsonContainer.addProperty("couleurBonheur", couleurBonheur);
        
        PrintWriter out = this.getWriterWithJsonHeader(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(jsonContainer, out);
        
    }
    
}
