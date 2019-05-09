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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mguilhin
 */
public class SerialisationInscription extends Serialisation{
    
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        Boolean dateNaissanceFormat = (Boolean)request.getAttribute("dateNaissanceFormat");
        Boolean codePostalFormat = (Boolean)request.getAttribute("codePostalFormat");
        JsonObject jsonContainer = new JsonObject();
        jsonContainer.addProperty("dateNaissanceFormat", dateNaissanceFormat);
        jsonContainer.addProperty("codePostalFormat", codePostalFormat);
        
        
        PrintWriter out = this.getWriterWithJsonHeader(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(jsonContainer, out);
        
    }
    
}
