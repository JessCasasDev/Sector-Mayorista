package dataSourceManagement.DAO;

import dataSourceManagement.entities.Authentication;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AuthenticationDAO {
    public EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("autoMarketPU");
    
    public Authentication persist(Authentication auth) {
        EntityManager em = emf1.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(auth);
            em.getTransaction().commit();       
        } catch(Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            emf1.close();
        }
        return auth;
    }
    

}
