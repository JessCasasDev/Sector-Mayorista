package gui.bean;

import businessLogic.controller.HandleClient;
import dataSourceManagement.entities.Authentication;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ClientBean implements Serializable {

    private String username;
    private String password;
    private String name;
    private String address;
    private String nit;
    private String message;
    @ManagedProperty(value = "#{userBean}")
    private AuthenticationBean userBean;

    public ClientBean() {
    }

    public AuthenticationBean getUserBean() {
        return userBean;
    }

    public void setUserBean(AuthenticationBean ub) {
        this.userBean = ub;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void ClientProfileBean() {
    }

    public String getName() {
        return this.name;
    }

    public String getNit() {
        return this.nit;
    }

    public String getAddress() {
        return this.address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void createClient() {
        userBean = new AuthenticationBean();
        Authentication userCreated = userBean.createAccount(username, password, "2");
        if (userCreated != null) {
            HandleClient hc = new HandleClient();
            message = hc.createClient(name, nit, address, userCreated);
        } else {
            message = "No se pudo crear el usuario. Intente con otro nombre de usuario";
        }

    }
}
