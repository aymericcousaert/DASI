/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.data.Client;
import metier.data.Employe;
import metier.service.Service;

/**
 *
 * @author mguilhin
 */
public class ActionConnexion extends Action{
    
    @Override
    public boolean executer(HttpServletRequest request){
        Service service = new Service();
        
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        HttpSession session = request.getSession(true);
        
        Client clientConnecte = null;
        Employe employeConnecte = null;
        Boolean connected = false;

        Object o = service.connexion(login,password);
        if(o != null){
            session.setAttribute("login",login);
            if(o instanceof Client){
                clientConnecte = (Client)o;
                connected = true;
                request.setAttribute("nature","client");
                session.setAttribute("civilite",clientConnecte.getCivilte());
                session.setAttribute("nom",clientConnecte.getNom());
                session.setAttribute("prenom",clientConnecte.getPrenom());
                //session.setAttribute("dateNaissance",clientConnecte.getDateNaissance());
                //session.setAttribute("adresse",clientConnecte.getCodePostale());
                session.setAttribute("tel",clientConnecte.getTelephone());
                session.setAttribute("mail",clientConnecte.getMail());
                session.setAttribute("sgnAstro",clientConnecte.getSigneAstro());
                session.setAttribute("sgnChinois",clientConnecte.getSigneChinois());
                session.setAttribute("aniTot",clientConnecte.getAnimal());
                session.setAttribute("couleurBonheur",clientConnecte.getCouleur());
                

            }else if(o instanceof Employe){
                employeConnecte=(Employe)o;
                connected = true;
                request.setAttribute("nature","employe");

            }
        }else{
            connected = false;
            request.setAttribute("nature"," ");
        }
        request.setAttribute("connected",connected);
        
        return false;
    }
    
}
