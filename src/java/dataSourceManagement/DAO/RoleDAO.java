package dataSourceManagement.DAO;

import dataSourceManagement.entities.Role;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RoleDAO {
    
    public EntityManagerFactory emf = Persistence.createEntityManagerFactory("autoMarketPU");
    
    public Role searchById(int id){
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
}
