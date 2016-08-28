/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.DAO;

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
public class OrderDAO {
    
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
        
        Integer paymentId = paymentDAO.newPaymentId();
        String type="Efectivo";
        Date date = new Date();
        String debt ="";
        
        Payment payment = new Payment();
        payment.setDate(date);
        payment.setDebt(debt);
        payment.setType(type);
        payment.setPaymentId(paymentId);
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
        q.setParameter(1, state);
        try {
            orderCollection = q.getResultList();
        } catch (Exception e){
        } finally {
            em.close();
        }
        return orderCollection;
    }
    
    public Integer newOrderId(){
        Integer id = 0;
        EntityManager em = emf1.createEntityManager();
        ShopOrder order = null;
        Query q = em.createNamedQuery("ShopOrder.findAll");
        try {
            id = q.getResultList().size();
        } catch (Exception e) {
        } finally {
            em.close();
        }
        return id;
    }
    
}
