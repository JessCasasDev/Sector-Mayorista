package gui.bean;

import businessLogic.controller.HandleClient;
import businessLogic.controller.LDAPAutomarket;
import config.GlobalConfig;
import dataSourceManagement.DAO.AuthenticationDAO;
import dataSourceManagement.entities.Authentication;
import dataSourceManagement.entities.Client;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class ClientBean implements Serializable {

    private String username;
    private String password;
    private String name;
    private String address;
    private String nit;
    private String message;
    private String session_id;
    private int session_counter;

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

    public String getSession_id() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.session_id = ec.getSessionId(true);
        System.out.println("Session id Client: " + session_id);
        return this.session_id;
    }

    public int getSession_counter() {
        this.session_counter = GlobalConfig.session_counter;
        return this.session_counter;
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

    public void setSession_counter(int counter) {
        this.session_counter = GlobalConfig.session_counter;
        System.out.println("Session counter Client: " + GlobalConfig.session_counter);
    }

    public void setSession_id(String id) {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.session_id = ec.getSessionId(true);
        System.out.println("Session id Client: " + session_id);
    }

    public void createClient() {
        userBean = new AuthenticationBean();
        LDAPAutomarket ldap = new LDAPAutomarket();
        boolean addUser = ldap.addUser(username, password, name);
        if (addUser) {
            Authentication userCreated = userBean.createAccount(username, "********", "2");
            HandleClient hc = new HandleClient();
            message = hc.createClient(name, nit, address, userCreated);
        } else {
            message = "No se pudo crear el usuario. Intente con otro nombre de usuario";
        }

    }

    public void setProfile() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

        HandleClient hc = new HandleClient();
        AuthenticationDAO authDAO = new AuthenticationDAO();
        Authentication auth = authDAO.searchByUsername(this.getUsername());
        Client client = hc.getClient(auth);
        this.setAddress(client.getAddress());
        this.setNit(client.getNit());
        this.setName(client.getName());
    }
}
