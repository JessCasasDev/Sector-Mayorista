/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.DAO;

import dataSourceManagement.entities.Client;
import dataSourceManagement.entities.ShopOrder;
import dataSourceManagement.entities.StockElement;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author afacunaa
 */
public class StockElementDAO {

    public EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("autoMarketPU");

    public StockElement persist(StockElement se) {
        EntityManager em = emf1.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(se);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return se;
    }
    
    public boolean editAvailability(Integer elementId, Boolean availability) {
        StockElement se;
        EntityManager em = emf1.createEntityManager();  
        em.getTransaction().begin();
        boolean success = true;
        try {
            se = em.merge(em.find(StockElement.class, elementId)); 
            se.setAvaliable(availability);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            success = false;
        } finally {
            em.close();
        }
        return success;
    }
    
    public Collection<StockElement> searchGroupByOrderIdAndAvailable(ShopOrder order, Boolean available){
        EntityManager em = emf1.createEntityManager();
        Collection<StockElement> orderCollection = null;
        Query q = em.createNamedQuery("StockElement.findByShopOrderIdAndAvailable");
        q.setParameter("available", available);
        q.setParameter("shopOrderOrderId", order);
        try {
            orderCollection = q.getResultList();
        } catch (Exception e){
        } finally {
            em.close();
        }
        return orderCollection;
    }
    public List<Long> searchByVIdAndAvaliablity(int vehicleId){
        EntityManager em = emf1.createEntityManager();
        //List<StockElement> stockList = new ArrayList<>();
        //Query q = em.createNamedQuery("StockElement.findByVehicleId");
        List<Long> avaliable = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(*) FROM vehicle , stock_element WHERE vehicle_id = vehicle_vehicle_id AND vehicle_id = ");
        sb.append(vehicleId);
        sb.append(" AND avaliable = 1;");
        Query q = em.createNativeQuery(sb.toString());
        //q.setParameter("vehicleId", vehicleId);
        try {
            avaliable =  q.getResultList();
            //System.out.println(q.getSingleResult());
        } catch (Exception e){
        } finally {
            em.close();
        }
        //System.out.println(stockList.size());
        return avaliable;
        
    }
    public void addToCart(int vehicleId){
        int orderId  = 0;
        StringBuilder sb = new StringBuilder();
        EntityManager em = emf1.createEntityManager();
        EntityManager em2 = emf1.createEntityManager();
        Query q = em.createNativeQuery("SELECT order_id FROM shop_order, client WHERE shop_order.client_id = client.client_id and `state` = \"Seleccionada\" ; ");
        em2.getTransaction().begin();
        sb.append("SELECT element_id FROM vehicle, stock_element WHERE  avaliable = 1 AND vehicle_id = vehicle_vehicle_id and vehicle_vehicle_id = ");
        sb.append(vehicleId);
        sb.append(" ;");
        Query q2 = em.createNativeQuery(sb.toString());
        StockElement se;
        try {
            try {
                orderId = (Integer) q.getResultList().get(0);
            } catch (Exception e) {
                 
            }
            if (orderId!=0) {
                se = em2.merge(em2.find(StockElement.class, q2.getResultList().get(0))); 
                se.setShopOrderOrderId(em2.find(ShopOrder.class, orderId));
                se.setAvaliable(false);
                em2.getTransaction().commit(); 
            }else{
                ShopOrderDAO shopOrderDAO = new ShopOrderDAO();
                ShopOrder shopOrder = new ShopOrder();
                shopOrder.setClientId(em2.find(Client.class, 1));
                shopOrder.setOrderDate(Date.from(Instant.now()));
                shopOrder.setDeliveryDate(null);
                shopOrder.setState("Seleccionada");
                shopOrderDAO.persist(shopOrder); 
                System.out.println(q.getResultList());
                orderId = (Integer) q.getResultList().get(0);
                se = em2.merge(em2.find(StockElement.class, q2.getResultList().get(0))); 
                se.setShopOrderOrderId(em2.find(ShopOrder.class, orderId));
                se.setAvaliable(false);
                em2.getTransaction().commit(); 
            }           
            //System.out.println(q.getSingleResult());
        } catch (Exception e){
            em2.getTransaction().rollback();
        } finally {
            em.close();
            em2.close();
        }
        System.out.println(orderId);
    }

}
