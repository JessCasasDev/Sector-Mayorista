/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.DAO;

import dataSourceManagement.entities.ShopOrder;
import dataSourceManagement.entities.Payment;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author afacunaa
 */
public class PaymentDAO {
    
    public EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("autoMarketPU");
    
    public Payment persist(Payment payment) {
        EntityManager em = emf1.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(payment);
            em.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return payment;
    }
    
    public Integer newPaymentId(){
        Integer id = 0;
        EntityManager em = emf1.createEntityManager();
        Payment payment = null;
        Query q = em.createNamedQuery("Payment.findAll");
        try {
            id = q.getResultList().size();
        } catch (Exception e) {
        } finally {
            em.close();
        }
        return id;
    }
    
}
