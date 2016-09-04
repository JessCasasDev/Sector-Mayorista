/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.controller;

import dataSourceManagement.DAO.AuthenticationDAO;
import dataSourceManagement.DAO.EmployeeDAO;
import dataSourceManagement.DAO.MonthlyRegisterDAO;
import dataSourceManagement.entities.Authentication;
import dataSourceManagement.entities.Employee;
import dataSourceManagement.entities.MonthlyRegister;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author afacunaa
 */
public class HandleEmployee {
    
    public boolean createEmployee(String name, String lastName, BigInteger documentId, Date birthDate, Authentication user){
        Employee employee = new Employee();
        
        employee.setName(name);
        employee.setLastName(lastName);
        employee.setDocumentId(documentId);
        employee.setBirthDate(birthDate);
        employee.setAuthId(user);
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee employeeE = employeeDAO.persist(employee);
        if (employeeE != null){
            return true;
        }else{
            return false;
        }
    }
    
    public Collection<Employee> getEmployeeInformation(){
        EmployeeDAO emp_dao = new EmployeeDAO();
        return emp_dao.getEmployees();
    }

    
    public List<MonthlyRegister> getSalary(String username){
        AuthenticationDAO authDAO = new AuthenticationDAO();
        Authentication auth = authDAO.searchByUsername(username);
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee employee = employeeDAO.searchByUsername(auth);
        MonthlyRegisterDAO mrDAO = new MonthlyRegisterDAO();
        List<MonthlyRegister> mrlist = mrDAO.searchByEmployeeId(employee);
        return mrlist;
    }
    
    
}
