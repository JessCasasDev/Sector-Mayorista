package dataSourceManagement.DAO;

import config.GlobalConfig;
import dataSourceManagement.entities.Authentication;
import dataSourceManagement.entities.Client;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ClientDAO implements Serializable{

  public EntityManagerFactory emf3 = Persistence.createEntityManagerFactory(GlobalConfig.PERSISTENCE_UNIT);

    public Client persist(Client client) {
        EntityManager em = emf3.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(client);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return client;
    }

    public Client searchByUsername(Authentication username) {
        EntityManager em = emf3.createEntityManager();
        Query q = em.createNamedQuery("Client.findByAuthenticationId");
        q.setParameter("authId", username);
        Client client = null;
        try {
            client = (Client) q.getSingleResult();
        } catch (Exception e) {
        } finally {
            em.close();
        }
        return client;
    }

    public Client searchByNit(String nit) {
        EntityManager em = emf3.createEntityManager();
        Query q = em.createNamedQuery("Client.findByNit");
        q.setParameter("nit", nit);
        Client client = null;
        try {
            client = (Client) q.getSingleResult();
        } catch (Exception e) {
        } finally {
            em.close();
        }
        return client;
    }
}
