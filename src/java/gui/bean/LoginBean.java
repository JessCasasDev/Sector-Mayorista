/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bean;

import businessLogic.controller.HandleLogin;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author afacunaa
 */
@ManagedBean
@RequestScoped
public class LoginBean {

    private String username;
    private String password;
    private String rol;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage2() {
        return message2;
    }

    public void setMessage2(String message2) {
        this.message2 = message2;
    }
    private String message2;

    public LoginBean() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        if (ec.getSessionMap().get("user") == null) {
            message2 = "Invitado";
        } else {
            message2 = (String) ec.getSessionMap().get("user");
        }

    }

    public void login() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HandleLogin doLogin = new HandleLogin();
        message = doLogin.login(username, password);
        message2 = (String) ec.getSessionMap().get("user");
    }

    public void logout() throws IOException {
        message = null;
        message2 = "Invitado";
        HandleLogin doLogout = new HandleLogin();
        doLogout.logout();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void verify_if_logged() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Boolean test = (Boolean) ec.getSessionMap().get(HandleLogin.STATE);

        if (test == null || !test) {
            ec.redirect(HandleLogin.INDEXXHTML);
        }
    }

    public void verify_role(String role) {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        if (!role.equals(ec.getSessionMap().get(HandleLogin.ROLE))) {
            try {
                ec.redirect(HandleLogin.INDEXXHTML);
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
