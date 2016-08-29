/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.DAO;

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
    
}
