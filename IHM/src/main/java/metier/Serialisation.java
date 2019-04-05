/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.PrintWriter;
import java.util.List;
import metier.Service.Personne;

/**
 *
 * @author mguilhin
 */
public class Serialisation {
    public static void listePersonnes(PrintWriter out, List<Personne> personnes) {
        JsonArray jsonListe = new JsonArray();
        
        for (Personne p : personnes) {
            JsonObject jsonPersonne = new JsonObject();
            
            jsonPersonne.addProperty("id", p.getId());
            jsonPersonne.addProperty("civilite", p.getCivilite());
            jsonPersonne.addProperty("nom", p.getNom());
            jsonPersonne.addProperty("prenom", p.getPrenom());
            jsonPersonne.addProperty("mail", p.getMail());
            jsonPersonne.addProperty("adresse", p.getAdresse());
            jsonListe.add(jsonPersonne);
        }
        
        JsonObject container = new JsonObject();
        container.add("personnes", jsonListe);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(container);
        out.println(json);
    }
}
