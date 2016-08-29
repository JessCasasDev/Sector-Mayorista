package gui.bean;

import businessLogic.controller.HandleEmployee;
import dataSourceManagement.entities.Authentication;
import java.math.BigInteger;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class CreateEmployeeBean {
    private String username;
    private String password;
    private String name;
    private String lastName;
    private Integer documentId;
    private Date birthDate;
    private Integer administrator;
    private String message;
    private Integer day;
    private Integer month;
    private Integer year;
    @ManagedProperty(value="#{userBean}")
    private AuthenticationBean userBean;

    public CreateEmployeeBean() {
        message = "";
    }   

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public AuthenticationBean getUserBean() {
        return userBean;
    }
   
   public void setUserBean(AuthenticationBean ub){
       this.userBean = ub;
   }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdministrator(int administrator) {
        this.administrator = administrator;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getAdministrator() {
        return administrator;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    
    public void setDay(Integer day){
        this.day = day;
    }
    
    public void setMonth(Integer month){
        this.month = month;
    }
    
    public void setYear(Integer year){
        this.year = year;
    }
    
    public Integer getDay(){
        return day;
    }
    
    public Integer getMonth(){
        return month;
    }
    
    public Integer getYear(){
        return year;
    }
    
    public void createEmployee(){
        userBean = new AuthenticationBean();
        Authentication userCreated = userBean.createAccount(username, password, "3");
       
        if (userCreated != null){
            HandleEmployee hc = new HandleEmployee();
            BigInteger newid = BigInteger.valueOf(documentId);
            Date birth = new Date();
            setBirthDate(birth);
            boolean bl;
            bl = hc.createEmployee(name, lastName, newid, birthDate, userCreated);     
            if (bl)   
               message = "Usuario creado con éxito";
            else
                message = "El Empleado no se pudo crear";
        }
        else
            message = "No se pudo crear el usuario";
        }
    }

