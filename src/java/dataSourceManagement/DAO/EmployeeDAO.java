/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.DAO;

import dataSourceManagement.entities.Authentication;
import dataSourceManagement.entities.Employee;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author afacunaa
 */
public class EmployeeDAO {
    
    public EntityManagerFactory emf3 = Persistence.createEntityManagerFactory("autoMarketPU");
    
    public Employee persist(Employee employee) {
        EntityManager em = emf3.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(employee);
            em.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return employee;
    }
    
    public Employee searchByUsername(Authentication username){
        EntityManager em = emf3.createEntityManager();
        Query q = em.createNamedQuery("Employee.findByAuthenticationId");
        q.setParameter("authId", username);
        Employee employee = null;
        try {
            employee = (Employee) q.getSingleResult();
            //employee = em.find(Employee.class, username.getAuthId());
            
        } catch (Exception e){
        } finally {
            em.close();
        }
        return employee;
    }
    
}
