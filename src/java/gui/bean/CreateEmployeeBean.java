/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bean;

import businessLogic.controller.HandleEmployee;
import java.math.BigInteger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.Date;

/**
 *
 * @author afacunaa
 */
@ManagedBean
@ViewScoped
public class CreateEmployeeBean {
    private Integer employeeId;
    private String name;
    private String lastName;
    private BigInteger documentId;
    private Date birthDate;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
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

    public BigInteger getDocumentId() {
        return documentId;
    }

    public void setDocumentId(BigInteger documentId) {
        this.documentId = documentId;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    
    public void createEmployee(){
        HandleEmployee createEmployee = new HandleEmployee();
        createEmployee.createEmployee(10, name, lastName, documentId, new Date());
    }
}
