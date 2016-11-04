/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.DAO;

import config.GlobalConfig;
import dataSourceManagement.entities.ShopOrder;
import dataSourceManagement.entities.Payment;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author afacunaa
 */
public class PaymentDAO implements Serializable{

   public EntityManagerFactory emf1 = Persistence.createEntityManagerFactory(GlobalConfig.PERSISTENCE_UNIT);

    public Payment persist(Payment payment) {
        EntityManager em = emf1.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(payment);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return payment;
    }

    public Collection<Payment> searchGroupByOrderId(ShopOrder order) {
        EntityManager em = emf1.createEntityManager();
        Collection<Payment> paymentCollection = null;
        Query q = em.createNamedQuery("Payment.findByShopOrderId");
        q.setParameter("shopOrderOrderId", order);
        try {
            paymentCollection = q.getResultList();
        } catch (Exception e) {
        } finally {
            em.close();
        }
        return paymentCollection;
    }

}
