/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.DAO;

import dataSourceManagement.entities.Vehicle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author mssg_
 */
public class CarDAO {

    public EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("autoMarketPU");

    public Vehicle persist(Vehicle vehicle) {
        EntityManager em = emf1.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(vehicle);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return vehicle;
    }

    public List<Vehicle> getCars() {
        EntityManager em = emf1.createEntityManager();
        List<Vehicle> vehicle = new ArrayList<Vehicle>();
        Query q = em.createNamedQuery("Vehicle.findAll");

        try {
            vehicle = q.getResultList();
        } catch (Exception e) {
        } finally {
            em.close();
        }

        return vehicle;
    }

}
