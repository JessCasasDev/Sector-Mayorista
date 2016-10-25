/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.DAO;

import config.GlobalConfig;
import dataSourceManagement.entities.Client;
import dataSourceManagement.entities.Vehicle;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author mssg_
 */
public class CarDAO {
   public EntityManagerFactory emf1 = Persistence.createEntityManagerFactory(GlobalConfig.PERSISTENCE_UNIT);
 
    public Vehicle persist(Vehicle vehicle) {
        EntityManager em = emf1.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(vehicle);
            em.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return vehicle;
    }
    public List<Vehicle> getCars(){
        EntityManager em = emf1.createEntityManager();
        List<Vehicle> vehicle = new ArrayList<Vehicle>();
        Query q = em.createNamedQuery("Vehicle.findAll");
        
        try {
            vehicle =  q.getResultList();
        } catch (Exception e) {
        } finally {
            em.close();
        }
        
        return vehicle;
    }
     public List<Integer> getCarsByClientOrder(Client client){
        EntityManager em = emf1.createEntityManager();
        StringBuilder sb = new StringBuilder();
        List<Integer> vehicle = null;
        sb.append("SELECT  vehicle_id FROM client, shop_order, stock_element, vehicle WHERE client.client_id =shop_order.client_id AND shop_order_order_id = order_id AND client.client_id = ");
        sb.append(client.getClientId());
        sb.append("  AND shop_order.`state` = \"Seleccionada\" AND vehicle_id = vehicle_vehicle_id GROUP BY vehicle_id;");
        Query q = em.createNativeQuery(sb.toString());
        
        try {
            vehicle =  q.getResultList();
        } catch (Exception e) {
        } finally {
            em.close();
        }
        
        return vehicle;
    }
    public Vehicle getSingleCar(int vehicleId){
        EntityManager em = emf1.createEntityManager();
        Vehicle vehicle = new Vehicle();
        Query q = em.createNamedQuery("Vehicle.findByVehicleId");
        q.setParameter("vehicleId", vehicleId);
        try {
            vehicle =(Vehicle)  q.getResultList().get(0);
        } catch (Exception e) {
        } finally {
            em.close();
        }
        
        return vehicle;
        
    }
    public Long carsSelectedByOrder(int vehicleId, int clientId){
        Long quantity = null;
        StringBuilder sb = new StringBuilder();
        EntityManager em = emf1.createEntityManager();
        sb.append("SELECT count(*) FROM client, shop_order, stock_element, vehicle WHERE vehicle_id = ");
        sb.append(vehicleId);
        sb.append(" AND client.client_id =shop_order.client_id AND shop_order_order_id = order_id AND client.client_id = ");
        sb.append(clientId);
        sb.append(" AND shop_order.`state` = \"Seleccionada\" AND vehicle_id = vehicle_vehicle_id;");
        //System.out.println(sb.toString());
        Query q = em.createNativeQuery(sb.toString());
        try {
            
            quantity =(Long) q.getSingleResult();
        } catch (Exception e) {
        } finally {
            em.close();
        }
        System.out.println(quantity);
        return quantity;
    }
   
}
