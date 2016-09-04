package gui.bean;

import businessLogic.controller.HandleEmployee;
import dataSourceManagement.entities.Employee;
import java.io.Serializable;
import java.util.Collection;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class AdministratorBean implements Serializable {

    private Float payment;
    private Float grade;
    private Collection<Employee> employee_list;

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

    public Collection<Employee> management() {
        HandleEmployee employee = new HandleEmployee();
        return employee.getEmployeeInformation();
    }

    public AdministratorBean() {
    }

    public void updateEmployee(Integer employee_id) {
        HandleEmployee employee = new HandleEmployee();
        employee.setMonth(employee_id, payment, grade);
        grade = null;
        payment = null;
    }
}
