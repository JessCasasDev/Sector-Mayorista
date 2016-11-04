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
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 *
 * @author afacunaa
 */
public class HandleEmployee implements Serializable{

    public HandleEmployee() {
    }
    
    
    public boolean createEmployee(String name, String lastName, BigInteger documentId, Date birthDate, Authentication user) {
        Employee employee = new Employee();

        employee.setName(name);
        employee.setLastName(lastName);
        employee.setDocumentId(documentId);
        employee.setBirthDate(birthDate);
        employee.setAuthId(user);
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee employeeE = employeeDAO.persist(employee);
        if (employeeE != null) {
            return true;
        } else {
            return false;
        }
    }
    
    public List<Employee> getEmployeeInformation(){
        EmployeeDAO emp_dao = new EmployeeDAO();
        return emp_dao.getEmployees();
    }

    public List<MonthlyRegister> getSalary(String username) {
        AuthenticationDAO authDAO = new AuthenticationDAO();
        Authentication auth = authDAO.searchByUsername(username);
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee employee = employeeDAO.searchByUsername(auth);
        MonthlyRegisterDAO mrDAO = new MonthlyRegisterDAO();
        List<MonthlyRegister> mrlist = mrDAO.searchByEmployeeId(employee);
        return mrlist;
    }

    public boolean setMonth(Integer employee_id, Float payment, Float grade) {
        MonthlyRegisterDAO month = new MonthlyRegisterDAO();
        MonthlyRegister m_register = new MonthlyRegister();
        EmployeeDAO empDao = new EmployeeDAO();
        Employee emp = empDao.searchById(employee_id);
        if (emp != null) {
            m_register.setEmployeeEmployeeId(emp);
            m_register.setPayment(payment);
            m_register.setGrade(grade);
            m_register.setDate(new Date());
            MonthlyRegister mr = month.persist(m_register);
            if (mr == null) {
                return false;
            }
        }
        return true;
    }
    
    public Employee getEmploye(Integer id){
        EmployeeDAO empDao = new EmployeeDAO();        
        return empDao.searchById(id);
        
    }

    public boolean editEmployee(Integer id, String employeeName, String employeeLastName, Integer employeeDocumentId, Date date) {
        EmployeeDAO employee = new EmployeeDAO();
        Employee emp = employee.searchById(id);
        
        if(employeeName!= null){
            emp.setName(employeeName);
        }
        
        if(employeeLastName!= null){
            emp.setLastName(employeeLastName);
        }
        
        if(employeeDocumentId!= null){
            BigInteger bi = BigInteger.valueOf(employeeDocumentId);
            emp.setDocumentId(bi);
        }
        
        if(date!= null){
            emp.setBirthDate(date);
        }
        return employee.edit(emp);        
    }

    public boolean deleteEmployee(Integer id) {
        EmployeeDAO empl = new EmployeeDAO();
        return empl.deleteEmployee(id);
    }
    
}

