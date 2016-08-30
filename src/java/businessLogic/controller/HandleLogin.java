/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.controller;

import dataSourceManagement.DAO.AuthenticationDAO;
import dataSourceManagement.DAO.ClientDAO;
import dataSourceManagement.DAO.EmployeeDAO;
import dataSourceManagement.entities.Authentication;
import dataSourceManagement.entities.Client;
import dataSourceManagement.entities.Employee;
import java.io.IOException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceContext;

/**
 *
 * @author afacunaa
 */
public class HandleLogin {
    
    @PersistenceContext
    public String login(String username, String password){
        AuthenticationDAO authDAO = new AuthenticationDAO();
        Authentication auth = authDAO.searchByUsername(username);
        ClientDAO clientDAO = new ClientDAO();
        Client user1 = clientDAO.searchByUsername(auth);
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee user2 = employeeDAO.searchByUsername(auth);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        
        if (username == null && password == null){
            return "Bienvenido";
        }
        if(user1!=null){ //es un cliente
            if (!user1.getAuthId().getPassword().equals(password)) {
                return "Clave incorrecta.";
            }
            ec.getSessionMap().put("username", username);
            ec.getSessionMap().put("name", user1.getName());
            ec.getSessionMap().put("id", user1.getNit());
            ec.getSessionMap().put("role", user1.getAuthId().getRoleId().getName());
            try{
                String url = ec.encodeActionURL(
                        FacesContext.getCurrentInstance().getApplication().getViewHandler().getActionURL(FacesContext.getCurrentInstance(), "/client/client_profile.xhtml"));
                ec.redirect(url);
                return "Ha entrado correctamente a su cuenta";
            } catch (IOException ex) {
                return "Error en redireccionamiento";
            }
        }else{
            if(user2!=null){ //es un empleado
                if (!user2.getAuthId().getPassword().equals(password)) {
                    return  "Clave incorrecta.";
                }
                ec.getSessionMap().put("username", username);
                ec.getSessionMap().put("name", user2.getName()+" "+user2.getLastName());
                ec.getSessionMap().put("id", user2.getDocumentId());
                ec.getSessionMap().put("role", user2.getAuthId().getRoleId().getName());
                try{
                    String url = ec.encodeActionURL(
                            FacesContext.getCurrentInstance().getApplication().getViewHandler().getActionURL(FacesContext.getCurrentInstance(), "/employee/employee_profile.xhtml"));
                    ec.redirect(url);
                    return "Ha entrado correctamente a su cuenta";
                } catch (IOException ex) {
                    return "Error en redireccionamiento";
                }
                
            }else{
                return "Usuario incorrecto";
            }
        }
    }
    
    public void logout(){
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        extContext.getSessionMap().remove("username");
        extContext.getSessionMap().remove("role");
        extContext.getSessionMap().remove("name");
        extContext.getSessionMap().remove("id");
        //extContext.redirect(extContext.getRequestContextPath());
        try{
            String url = extContext.encodeActionURL(
                    FacesContext.getCurrentInstance().getApplication().getViewHandler().getActionURL(FacesContext.getCurrentInstance(), "/index.xhtml"));
            extContext.redirect(url);
            //return "Ha entrado correctamente a su cuenta";
        } catch (IOException ex) {
            //return "Error en redireccionamiento";
        }
        
}
    
}
