package gui.bean;

import businessLogic.controller.HandleEmployee;
import dataSourceManagement.entities.Employee;
import java.util.Collection;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class AdministratorBean {
    private Long payment;
    private Double grade;
    private Collection<Employee> employee_list;
    public Collection<Employee> management(){
        HandleEmployee employee = new HandleEmployee();
        return employee.getEmployeeInformation();
    }
    
    public void employee_management(Integer employee_id){
        System.out.println("Employee ID: " + employee_id + ", payment: " + payment + ", grade: " + grade );
    }
}
