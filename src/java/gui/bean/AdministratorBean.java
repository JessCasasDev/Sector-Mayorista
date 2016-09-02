package gui.bean;

import businessLogic.controller.HandleEmployee;
import dataSourceManagement.entities.Employee;
import java.util.Collection;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class AdministratorBean {
    private Collection<Employee> employee_list;
    public Collection<Employee> management(){
        HandleEmployee employee = new HandleEmployee();
        return employee.getEmployeeInformation();
    }
}
