/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.DAO;

import config.GlobalConfig;
import dataSourceManagement.entities.Employee;
import dataSourceManagement.entities.MonthlyRegister;
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
public class MonthlyRegisterDAO implements Serializable{

    public EntityManagerFactory emf3 = Persistence.createEntityManagerFactory(GlobalConfig.PERSISTENCE_UNIT);

    public MonthlyRegister persist(MonthlyRegister mr) {
        EntityManager em = emf3.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(mr);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return mr;
    }

    public List<MonthlyRegister> searchByEmployeeId(Employee employee) {
        EntityManager em = emf3.createEntityManager();
        Query q = em.createNamedQuery("MonthlyRegister.findByEmployeeId");
        q.setParameter("employeeEmployeeId", employee);
        List<MonthlyRegister> mr = null;
        try {
            mr = q.getResultList();
        } catch (Exception e) {
        } finally {
            em.close();
        }
        return mr;
    }

}
