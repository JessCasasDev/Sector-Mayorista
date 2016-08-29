package dataSourceManagement.DAO;

import dataSourceManagement.entities.Client;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ClientDAO {
    public EntityManagerFactory emf3 = Persistence.createEntityManagerFactory("autoMarketPU");
    
    public Client persist(Client client) {
        EntityManager em = emf3.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(client);
            em.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return client;
    }
}
