/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.DAO;

import dataSourceManagement.entities.ShopOrder;
import dataSourceManagement.entities.StockElement;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author afacunaa
 */
public class StockElementDAO {

    public EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("autoMarketPU");

    public StockElement persist(StockElement se) {
        EntityManager em = emf1.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(se);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return se;
    }
    
    public boolean editAvailability(Integer elementId, Boolean availability) {
        StockElement se;
        EntityManager em = emf1.createEntityManager();  
        em.getTransaction().begin();
        boolean success = true;
        try {
            se = em.merge(em.find(StockElement.class, elementId)); 
            se.setAvaliable(availability);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            success = false;
        } finally {
            em.close();
        }
        return success;
    }
    
    public Collection<StockElement> searchGroupByOrderIdAndAvailable(ShopOrder order, Boolean available){
        EntityManager em = emf1.createEntityManager();
        Collection<StockElement> orderCollection = null;
        Query q = em.createNamedQuery("StockElement.findByShopOrderIdAndAvailable");
        q.setParameter("available", available);
        q.setParameter("shopOrderOrderId", order);
        try {
            orderCollection = q.getResultList();
        } catch (Exception e){
        } finally {
            em.close();
        }
        return orderCollection;
    }

}
