/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.controller;

import dataSourceManagement.DAO.EmployeeDAO;
import dataSourceManagement.entities.Authentication;
import dataSourceManagement.entities.Employee;
import java.math.BigInteger;
import java.util.Date;

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

    
}
