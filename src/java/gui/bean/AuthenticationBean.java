package gui.bean;

import businessLogic.controller.HandleAuthentication;
import dataSourceManagement.entities.Authentication;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class AuthenticationBean implements Serializable{
    
    private String username;
    private String password;
    private String message;

    public AuthenticationBean() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMessage() {
        return message;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public Authentication createAccount(String username, String password, String role){
        HandleAuthentication hu = new HandleAuthentication(); 
        Authentication aut = hu.createAccount(username, password, Integer.parseInt(role));
        return aut;
    }
}
