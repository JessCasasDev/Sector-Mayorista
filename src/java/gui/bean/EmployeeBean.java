package gui.bean;

import businessLogic.controller.HandleEmployee;
import dataSourceManagement.entities.Authentication;
import dataSourceManagement.entities.MonthlyRegister;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class EmployeeBean implements Serializable{
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

    public EmployeeBean() {
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
    
    public void createEmployee() throws ParseException{
        userBean = new AuthenticationBean();
        Authentication userCreated = userBean.createAccount(username, password, "3");
       
        if (userCreated != null){
            HandleEmployee hc = new HandleEmployee();
            BigInteger newid = BigInteger.valueOf(documentId);
            Date birth = new Date();
            setBirthDate(birth);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            String s_day = String.valueOf(day);
            String s_month = String.valueOf(month);
            String s_year = String.valueOf(year);
            String dateInString = s_day + "-" + s_month + "-" + s_year + " 10:20:56";
            Date date = sdf.parse(dateInString);
            boolean bl;
            bl = hc.createEmployee(name, lastName, newid, date, userCreated);     
            if (bl)   
               message = "Usuario creado con éxito";
            else
                message = "El Empleado no se pudo crear";
        }
        else
            message = "No se pudo crear el usuario";
        }
    
    public List<MonthlyRegister> showSalary(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HandleEmployee hc = new HandleEmployee();
        String employeeId = (String) ec.getSessionMap().get("username");
        return hc.getSalary(employeeId);
    }
}

