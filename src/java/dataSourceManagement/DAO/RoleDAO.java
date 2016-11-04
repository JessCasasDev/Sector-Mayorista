package dataSourceManagement.DAO;

import config.GlobalConfig;
import dataSourceManagement.entities.Role;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class RoleDAO implements Serializable{

    public EntityManagerFactory emf = Persistence.createEntityManagerFactory(GlobalConfig.PERSISTENCE_UNIT);

    public Role searchById(int id) {
        EntityManager em = emf.createEntityManager();
        Role rol = null;
        try {
            rol = em.find(Role.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
        return rol;
    }

    public Role searchByRoleName(String roleName) {
        EntityManager em = emf.createEntityManager();
        Role rol = null;
        try {
            Query q = em.createNamedQuery("Role.findByName");
            q.setParameter("name", roleName);
            rol = (Role) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
        return rol;
    }
}
