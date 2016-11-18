package gui.bean;

import businessLogic.controller.HandleEmployee;
import businessLogic.controller.LDAPAutomarket;
import config.GlobalConfig;
import dataSourceManagement.entities.Authentication;
import dataSourceManagement.entities.Employee;
import dataSourceManagement.entities.MonthlyRegister;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class EmployeeBean implements Serializable {

    private String username;
    private String password;
    private String name;
    private String lastName;
    private Integer documentId;
    private Date birthDate;
    private Object date1;
    private Integer administrator;
    private String message;
    private Integer day;
    private Integer month;
    private Integer year;
    private String session_id;
    private int session_counter;

    @ManagedProperty(value = "#{userBean}")
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

    public void setUserBean(AuthenticationBean ub) {
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

    public void setSession_counter(int counter) {
        this.session_counter = GlobalConfig.session_counter;
        System.out.println("Session counter Administrator: " + GlobalConfig.session_counter);
    }

    public void setSession_id(String id) {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.session_id = ec.getSessionId(true);
        System.out.println("Session id Administrator: " + session_id);
    }

    public String getSession_id() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.session_id = ec.getSessionId(true);
        System.out.println("Session id Employee: " + session_id);
        return this.session_id;
    }

    public int getSession_counter() {
        this.session_counter = GlobalConfig.session_counter;
        return this.session_counter;
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

    public void setDay(Integer day) {
        this.day = day;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }

    public void setDate1(Object date1) {
        this.date1 = date1;
    }

    public Object getDate1() {
        return date1;
    }

    public void createEmployee() throws ParseException {
        userBean = new AuthenticationBean();
        LDAPAutomarket ldap = new LDAPAutomarket();
        boolean addUser = ldap.addUser(username, password, name);
        if (addUser) {
            Authentication userCreated = userBean.createAccount(username, "********", "3");
            System.out.println("gui.bean.EmployeeBean.createEmployee() " + date1);
            HandleEmployee hc = new HandleEmployee();
            BigInteger newid = BigInteger.valueOf(documentId);
            Date date = this.setDateTime(day, month, year);
            boolean bl;
            bl = hc.createEmployee(name, lastName, newid, date, userCreated);
            if (bl) {
                message = "Empleado creado con éxito";
            } else {
                message = "El Empleado no se pudo crear";
            }
        } else {
            message = "No se pudo crear el usuario. Porfavor intente con otro nombre de usuario";
        }
    }

    public List<MonthlyRegister> showSalary() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HandleEmployee hc = new HandleEmployee();
        String employeeName = (String) ec.getSessionMap().get("username");
        return hc.getSalary(employeeName);
    }

    public float weightedGrade() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HandleEmployee hc = new HandleEmployee();
        String employeeName = (String) ec.getSessionMap().get("username");
        List<MonthlyRegister> mrList = hc.getSalary(employeeName);
        float wgrade = 0;
        for (MonthlyRegister monthlyRegister : mrList) {
            wgrade += monthlyRegister.getGrade();
        }
        return (wgrade / mrList.size());
    }

    public void displayProfile() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Integer employeeId = (Integer) ec.getSessionMap().get("id");
        Employee employee = getEmployee(employeeId);
        setBirthDate(employee.getBirthDate());
        setDocumentId(employee.getDocumentId().intValue());
    }

    public void setValues(int id) {
        HandleEmployee emp = new HandleEmployee();
        Employee employee = emp.getEmploye(id);
        if (employee != null) {
            this.setName(employee.getName());
            this.setLastName(employee.getLastName());
            this.setDocumentId(employee.getDocumentId().intValue());
        }
        FacesContext.getCurrentInstance().renderResponse();
    }

    public boolean setEmployee(int selectedItem, String employeeName,
            String employeeLastName, Integer employeeDocumentId,
            Integer employeeDay, Integer employeeMonth, Integer employeeYear) {
        HandleEmployee emp = new HandleEmployee();
        Date date = new Date();
        try {
            date = this.setDateTime(employeeDay, employeeMonth, employeeYear);

        } catch (ParseException ex) {
            Logger.getLogger(EmployeeBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return emp.editEmployee(selectedItem, employeeName, employeeLastName, employeeDocumentId, date);
    }

    public Date setDateTime(int day, int month, int year) throws ParseException {
        Date birth = new Date();
        setBirthDate(birth);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String s_day = String.valueOf(day);
        String s_month = String.valueOf(month);
        String s_year = String.valueOf(year);
        String dateInString = s_day + "-" + s_month + "-" + s_year + " 10:20:56";
        Date date = sdf.parse(dateInString);
        return date;
    }

    public boolean deleteEmployee(Integer id) {
        HandleEmployee empl = new HandleEmployee();
        return empl.deleteEmployee(id);
    }

    public Employee getEmployee(Integer id) {
        HandleEmployee handle = new HandleEmployee();
        return handle.getEmploye(id);

    }

}
