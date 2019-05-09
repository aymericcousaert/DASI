package Servlet;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Actions.ActionClientProfil;
import Actions.ActionConnexion;
import Actions.ActionInscription;
import Serialisation.SerialisationClientProfil;
import Serialisation.SerialisationConnexion;
import Serialisation.SerialisationInscription;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dao.JpaUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metier.data.Client;
import metier.data.Employe;
import metier.service.Service;
/**
 *
 * @author mguilhin
 */
@WebServlet(urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    Service service = new Service();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        String action = request.getParameter("action");
        response.setCharacterEncoding("UTF-8");

        switch(action){
            
            case "connecter":
                ActionConnexion actionConnexion = new ActionConnexion();
                SerialisationConnexion serialisationConnexion = new SerialisationConnexion();
                actionConnexion.executer(request);
                serialisationConnexion.serialiser(request, response);
                
            break;
            case "inscription":
                ActionInscription actionInscription = new ActionInscription();
                SerialisationInscription serialisationInscription = new SerialisationInscription();
                actionInscription.executer(request);
                serialisationInscription.serialiser(request, response);
            break;
            case "afficherProfil":
                
                SerialisationClientProfil serialisationClientProfil = new SerialisationClientProfil();
                
                serialisationClientProfil.serialiser(request, response);
            break;
                
                
        }
    }

        
        

    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    @Override
    public void init() throws ServletException {
        super.init();
        JpaUtil.init();
    }

    @Override
    public void destroy() {
        JpaUtil.destroy();
        super.destroy();
  }
        
}
