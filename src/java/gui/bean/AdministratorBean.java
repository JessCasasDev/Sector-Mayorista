package gui.bean;

import businessLogic.controller.HandleEmployee;
import dataSourceManagement.entities.Employee;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@ManagedBean
@ViewScoped
public class AdministratorBean implements Serializable{
    private Float payment;
    private Float grade;
    private int selectedItem; // +getter +setter
    private String employeeName;
    private String employeeLastName;
    private Integer employeeDocumentId;
    private Date birthDate;
    private Integer employeeDay;
    private Integer employeeMonth;
    private Integer employeeYear;
    private HashMap<String, String> availableItems; // +getter
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public void setEmployeeDocumentId(Integer employeeDocumentId) {
        this.employeeDocumentId = employeeDocumentId;
    }

    public void setEmployeeDay(Integer employeeDay) {
        this.employeeDay = employeeDay;
    }

    public void setEmployeeMonth(Integer employeeMonth) {
        this.employeeMonth = employeeMonth;
    }

    public void setEmployeeYear(Integer employeeYear) {
        this.employeeYear = employeeYear;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public Integer getEmployeeDocumentId() {
        return employeeDocumentId;
    }

    public Integer getEmployeeDay() {
        return employeeDay;
    }

    public Integer getEmployeeMonth() {
        return employeeMonth;
    }

    public Integer getEmployeeYear() {
        return employeeYear;
    }
    @ManagedProperty(value="#{employeeBean}")
    private EmployeeBean employeeBean;

    public AdministratorBean() {
        this.setAvailableItem();
    }
    public void setAvailableItem(){
        HandleEmployee he = new HandleEmployee();
        List<Employee> collect = he.getEmployeeInformation();
        availableItems = new LinkedHashMap<>();
        for(Employee emp : collect){
            availableItems.put(emp.getInformation(), emp.getEmployeeId().toString());
        }
    }
    public void setEmployeeBean(EmployeeBean employeeBean) {
        this.employeeBean = employeeBean;
    }

    public EmployeeBean getEmployeeBean() {
        return employeeBean;
    }
    
    public void setPayment(Float payment) {
        this.payment = payment;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public Float getPayment() {
        return payment;
    }

    public Float getGrade() {
        return grade;
    }
    
    public Collection<Employee> management(){
        HandleEmployee employee = new HandleEmployee();
        return employee.getEmployeeInformation();
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public HashMap<String, String> getAvailableItems() {
        return availableItems;
    }

    public void updateEmployee(Integer employee_id){
        HandleEmployee employee = new HandleEmployee();
        employee.setMonth(employee_id, payment, grade);
        grade = null;
        payment = null;
        message = "Pago y calificación exitosa";
    }
    
    public HashMap<String, String> getEmployeeList(){
        return availableItems;
    }
    
    public void employeeChanged(ValueChangeEvent e) {
    selectedItem = (int) e.getNewValue();
    System.out.println("gui.bean.AdministratorBean.employeeChanged()  " + selectedItem);
    employeeBean = new EmployeeBean();
    Employee emp = employeeBean.getEmployee(selectedItem);
    this.setEmployeeName(emp.getName());
    this.setEmployeeLastName(emp.getLastName());
    this.setEmployeeDocumentId(emp.getDocumentId().intValue());
    this.setEmployeeDay(emp.getBirthDate().getDay());
    this.setEmployeeMonth(emp.getBirthDate().getMonth());
    this.setEmployeeYear(emp.getBirthDate().getYear());
    
    FacesContext.getCurrentInstance().renderResponse();
    }
    
    public void editEmployee(){
        EmployeeBean employeeBean = new EmployeeBean();
        if (!employeeBean.setEmployee(selectedItem, employeeName, employeeLastName, employeeDocumentId,
                employeeDay, employeeMonth, employeeYear))
            message="El empleado no se ha podido actualizar";
        else
            message="Empleado Actualizado";
    }
    
    public void deleteEmployee(){
        EmployeeBean employee = new EmployeeBean();
        if (employee.deleteEmployee(selectedItem)){
            message = "Empleado eliminado con éxito";
            this.setAvailableItem();
        }
        else
            message = "El Empleado no pudo ser eliminado";
    }
}
