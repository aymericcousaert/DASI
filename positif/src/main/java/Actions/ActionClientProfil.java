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
public class ActionClientProfil extends Action{
    
    @Override
    public boolean executer(HttpServletRequest request){
        return true;
    }
}
