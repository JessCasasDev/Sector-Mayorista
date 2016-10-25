package dataSourceManagement.DAO;

import config.GlobalConfig;
import dataSourceManagement.entities.Purchase;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class PurchaseDAO {

    public EntityManagerFactory emf3 = Persistence.createEntityManagerFactory(GlobalConfig.PERSISTENCE_UNIT);

    public Purchase persist(Purchase purchase) {
        EntityManager em = emf3.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(purchase);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return purchase;
    }

    public Purchase searchById(Integer id) {
        EntityManager em = emf3.createEntityManager();
        Query q = em.createNamedQuery("Purchase.findByPurchaseId");
        q.setParameter("purchaseId", id);
        Purchase purchase = null;
        try {
            purchase = (Purchase) q.getSingleResult();
        } catch (Exception e) {
        } finally {
            em.close();
        }
        return purchase;
    }

}
