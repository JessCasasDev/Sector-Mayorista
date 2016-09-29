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
import dataSourceManagement.entities.Role;
import java.io.IOException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceContext;

/**
 *
 * @author afacunaa
 */
public class HandleLogin {
    
    public static final String STARTPAGE = "/Sector-Mayorista";
    public static final String INDEXXHTML = "/index.xhtml";
    public static final String EMPLOYEEEMPLOYEE_PROFILEXHTML = "/employee/employee_profile.xhtml";
    public static final String ADMININDEXXHTML = "/admin/admin_index.xhtml";
    public static final String CLIENTCLIENT_PROFILEXHTML = "/client/client_profile.xhtml";
    public static final String STATE = "state";
    public static final String ROLE = "role";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String USERNAME = "username";

    @PersistenceContext
    public String login(String username, String password) {
        System.out.println("request user: " + username);
        AuthenticationDAO authDAO = new AuthenticationDAO();
        Authentication auth = authDAO.searchByUsername(username);
        ClientDAO clientDAO = new ClientDAO();
        Client user1 = clientDAO.searchByUsername(auth);
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee user2 = employeeDAO.searchByUsername(auth);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

        if (username == null && password == null) {
            return "Bienvenido";
        }
        if (user1 != null) { //es un cliente

            if (!user1.getAuthId().getPassword().equals(password)) {
                return "Clave incorrecta.";
            }
            ec.getSessionMap().put(USERNAME, username);
            ec.getSessionMap().put(NAME, user1.getName());
            ec.getSessionMap().put(ID, user1.getNit());
            ec.getSessionMap().put(ROLE, user1.getAuthId().getRoleId().getName());
            ec.getSessionMap().put(STATE, true);
            try {
                String url = ec.encodeActionURL(FacesContext.getCurrentInstance()
                        .getApplication().getViewHandler()
                        .getActionURL(FacesContext.getCurrentInstance(),
                                CLIENTCLIENT_PROFILEXHTML));
                System.out.println("you are logged as client");
                ec.redirect(STARTPAGE + CLIENTCLIENT_PROFILEXHTML);
                return "Ha entrado correctamente a su cuenta";
            } catch (IOException ex) {
                return "Error en redireccionamiento";
            }
        } else {
            if (user2 != null) { //es un empleado

                if (!user2.getAuthId().getPassword().equals(password)) {
                    return "Clave incorrecta.";
                }
                ec.getSessionMap().put(USERNAME, username);
                ec.getSessionMap().put(NAME, user2.getName() + " " + user2.getLastName());
                ec.getSessionMap().put(ID, user2.getEmployeeId());
                ec.getSessionMap().put(ROLE, user2.getAuthId().getRoleId().getName());
                ec.getSessionMap().put(STATE, true);
                
                try {
                    String actionURL = null;
                    if (Role.ADMINISTRATOR.equals(user2.getAuthId().getRoleId().getName())) {
                        System.out.println("you are logged as admin");
                        actionURL = FacesContext.getCurrentInstance()
                                .getApplication().getViewHandler()
                                .getActionURL(FacesContext.getCurrentInstance(),
                                        ADMININDEXXHTML);
                    } else if (Role.EMPLOYEE.equals(user2.getAuthId().getRoleId().getName())) {
                        System.out.println("you are logged as employee");
                        actionURL = FacesContext.getCurrentInstance()
                                .getApplication().getViewHandler()
                                .getActionURL(FacesContext.getCurrentInstance(),
                                        EMPLOYEEEMPLOYEE_PROFILEXHTML);
                    } else {
                        System.out.println("you are user doesnt exist");
                        actionURL = actionURL = FacesContext.getCurrentInstance()
                                .getApplication().getViewHandler()
                                .getActionURL(FacesContext.getCurrentInstance(),
                                        INDEXXHTML);
                    }
                    String url = ec.encodeActionURL(actionURL);
                    ec.redirect(url);
                    return "Ha entrado correctamente a su cuenta";
                } catch (IOException ex) {
                    return "Error en redireccionamiento";
                }

            } else {
                return "Usuario incorrecto";
            }
        }
    }

    public void logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        extContext.getSessionMap().remove(USERNAME);
        extContext.getSessionMap().remove(ROLE);
        extContext.getSessionMap().remove(NAME);
        extContext.getSessionMap().remove(ID);
        extContext.getSessionMap().remove(STATE);
        //extContext.redirect(extContext.getRequestContextPath());
        try {
            String url = extContext.encodeActionURL(
                    FacesContext.getCurrentInstance().getApplication()
                    .getViewHandler().getActionURL(FacesContext
                            .getCurrentInstance(), INDEXXHTML));
            extContext.redirect(url);
            //return "Ha entrado correctamente a su cuenta";
        } catch (IOException ex) {
            //return "Error en redireccionamiento";
        }

    }

}
