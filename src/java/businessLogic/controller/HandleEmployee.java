/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.controller;

import dataSourceManagement.DAO.EmployeeDAO;
import dataSourceManagement.entities.Employee;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author afacunaa
 */
public class HandleEmployee {
    
    public void createEmployee(Integer employeeId, String name, String lastName, BigInteger documentId, Date birthDate){
        Employee employee = new Employee();
        
        employee.setEmployeeId(employeeId);
        employee.setName(name);
        employee.setLastName(lastName);
        employee.setDocumentId(documentId);
        employee.setBirthDate(birthDate);
        
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee employeeE = employeeDAO.persist(employee);
    }

    
}
