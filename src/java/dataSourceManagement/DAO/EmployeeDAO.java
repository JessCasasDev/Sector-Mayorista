/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.DAO;

import config.GlobalConfig;
import dataSourceManagement.entities.Authentication;
import dataSourceManagement.entities.Employee;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author afacunaa
 */
public class EmployeeDAO implements Serializable{

    public EntityManagerFactory emf3 = Persistence.createEntityManagerFactory(GlobalConfig.PERSISTENCE_UNIT);

    public Employee persist(Employee employee) {
        EntityManager em = emf3.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(employee);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return employee;
    }
    
    public boolean edit(Employee employee){
        Employee newEmployee;
        EntityManager em = emf3.createEntityManager();  
        em.getTransaction().begin();
        try {
            newEmployee = em.merge(em.find(Employee.class, employee.getEmployeeId())); 
            newEmployee.setName(employee.getName());
            newEmployee.setLastName(employee.getLastName());
            newEmployee.setDocumentId(employee.getDocumentId());
            newEmployee.setBirthDate(employee.getBirthDate());
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
        return true;
    }
    public Employee searchByUsername(Authentication username){
        EntityManager em = emf3.createEntityManager();
        Query q = em.createNamedQuery("Employee.findByAuthenticationId");
        q.setParameter("authId", username);
        Employee employee = null;
        try {
            employee = (Employee) q.getSingleResult();
            //employee = em.find(Employee.class, username.getAuthId());

        } catch (Exception e) {
        } finally {
            em.close();
        }
        return employee;
    }

    public Employee searchById(Integer id) {
        EntityManager em = emf3.createEntityManager();
        Query q = em.createNamedQuery("Employee.findByEmployeeId");
        q.setParameter("employeeId", id);
        Employee employee = null;
        try {
            employee = (Employee) q.getSingleResult();
            //employee = em.find(Employee.class, username.getAuthId());

        } catch (Exception e) {
        } finally {
            em.close();
        }
        return employee;   
    }

    public List<Employee> getEmployees() {
        EntityManager em = emf3.createEntityManager();
        List<Employee> employeeCollection = null;
        Query q = em.createNamedQuery("Employee.findAll");
        try {
            employeeCollection = q.getResultList();
        } catch (Exception e) {
        } finally {
            em.close();
        }
        return employeeCollection;
    }    

    public boolean deleteEmployee(Integer id) {
        EntityManager em = emf3.createEntityManager();
        Employee employee = em.find(Employee.class, id);
        try {
            em.getTransaction().begin();
            em.remove(employee);
            em.getTransaction().commit();
        } catch (Exception e) {
            return false;
        } finally {
            em.close();

        }
        return true;
    }
}
