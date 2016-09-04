package dataSourceManagement.DAO;

import dataSourceManagement.entities.Authentication;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

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
    
    public Authentication searchByUsername(String username){
        EntityManager em = emf1.createEntityManager();
        Query q = em.createNamedQuery("Authentication.findByUserName");
        q.setParameter("userName", username);
        Authentication auth = null;
        try {
            auth = (Authentication) q.getSingleResult();
        } catch (Exception e){
            System.out.println(e);
        } finally {
            em.close();
        }
        return auth;
    }

}
