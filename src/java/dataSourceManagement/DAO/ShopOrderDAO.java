/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.DAO;

import dataSourceManagement.entities.Client;
import dataSourceManagement.entities.ShopOrder;
import dataSourceManagement.entities.Payment;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author afacunaa
 */
public class ShopOrderDAO {
    
    public EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("autoMarketPU");
    
    public ShopOrder persist(ShopOrder order) {
        EntityManager em = emf1.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(order);
            em.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return order;
    }
    
    public void buyAutos(ShopOrder order){
        PaymentDAO paymentDAO = new PaymentDAO();
        
        Integer paymentId;
        String type="Efectivo";
        Date date = new Date();
        String debt ="";
        
        Payment payment = new Payment();
        payment.setDate(date);
        payment.setDebt(debt);
        payment.setType(type);
        payment.setShopOrderOrderId(order);
        
        paymentDAO.persist(payment);
        
        order.setState("Finalizada");
    }
    
    public ShopOrder searchByOrderId(Integer orderId){
        
        EntityManager em = emf1.createEntityManager();
        ShopOrder order = em.find(ShopOrder.class, orderId);
        em.close();
        return order;
    }
    
    public Collection<ShopOrder> searchGroupByState(String state){
        EntityManager em = emf1.createEntityManager();
        Collection<ShopOrder> orderCollection = null;
        Query q = em.createNamedQuery("ShopOrder.findByState");
        q.setParameter("state", state);
        try {
            orderCollection = q.getResultList();
        } catch (Exception e){
        } finally {
            em.close();
        }
        return orderCollection;
    }
    
    public Collection<ShopOrder> searchGroupByStateAndClient(String state, Client clientId){
        EntityManager em = emf1.createEntityManager();
        Collection<ShopOrder> orderCollection = null;
        Query q = em.createNamedQuery("ShopOrder.findByStateAndClient");
        q.setParameter("state", state);
        q.setParameter("clientId", clientId);
        try {
            orderCollection = q.getResultList();
        } catch (Exception e){
        } finally {
            em.close();
        }
        return orderCollection;
    }
    
}
