/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.DAO;

import businessLogic.controller.HandleAddVehicle;
import config.GlobalConfig;
import dataSourceManagement.entities.Client;
import dataSourceManagement.entities.ShopOrder;
import dataSourceManagement.entities.StockElement;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author afacunaa
 */
public class StockElementDAO implements Serializable{

    public EntityManagerFactory emf1 = Persistence.createEntityManagerFactory(GlobalConfig.PERSISTENCE_UNIT);

    public boolean persist(List<StockElement> se) {
        EntityManager em = emf1.createEntityManager();
        boolean transaction = true;

        EntityTransaction transaction1 = em.getTransaction();
        transaction1.begin();
        try {
            for (int i = 0; i < se.size(); i++) {
                em.persist(se.get(i));
                /*em.createNativeQuery("INSERT INTO stock_element(location, avaliable,"
                 + "vehicle_vehicle_id, purchase_purchase_id) VALUES('" +se.getLocation()+"',true,"
                 + se.getVehicleVehicleId().getVehicleId()+","+
                 se.getPurchasePurchaseId().getPurchaseId()+");").executeUpdate();*/

            }
            transaction1.commit();

        } catch (Exception e) {
            e.printStackTrace();
            transaction = false;
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return transaction;
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

    public Collection<StockElement> searchGroupByOrderIdAndAvailable(ShopOrder order, Boolean available) {
        EntityManager em = emf1.createEntityManager();
        Collection<StockElement> orderCollection = null;
        Query q = em.createNamedQuery("StockElement.findByShopOrderIdAndAvailable");
        q.setParameter("available", available);
        q.setParameter("shopOrderOrderId", order);
        try {
            orderCollection = q.getResultList();
        } catch (Exception e) {
        } finally {
            em.close();
        }
        return orderCollection;
    }

    public List<Long> searchByVIdAndAvaliablity(int vehicleId) {
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
            avaliable = q.getResultList();
            //System.out.println(q.getSingleResult());
        } catch (Exception e) {
        } finally {
            em.close();
        }
        //System.out.println(stockList.size());
        return avaliable;

    }

    public Integer addToCart(int vehicleId, int clientId, int quantity) {
        int orderId = 0;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        EntityManager em = emf1.createEntityManager();
        EntityManager em2 = emf1.createEntityManager();
        EntityTransaction transaction = em2.getTransaction();
        sb2.append("SELECT order_id FROM shop_order, client WHERE shop_order.client_id = client.client_id and `state` = \"Seleccionada\" AND client.client_id =  ");
        sb2.append(clientId);
        sb2.append(" ;");
        Query q = em.createNativeQuery(sb2.toString());

        sb.append("SELECT element_id FROM vehicle, stock_element WHERE  avaliable = 1 AND vehicle_id = vehicle_vehicle_id and vehicle_vehicle_id = ");
        sb.append(vehicleId);
        sb.append(" ;");
        Query q2 = em.createNativeQuery(sb.toString());
        StockElement se = null;
        try {
            try {
                orderId = (Integer) q.getResultList().get(0);
            } catch (Exception e) {

            }
            transaction.begin();
            if (orderId != 0) {
                se = em2.merge(em2.find(StockElement.class, q2.getResultList().get(0)));
                se.setShopOrderOrderId(em2.find(ShopOrder.class, orderId));
                se.setAvaliable(false);
                transaction.commit();
            } else {
                ShopOrderDAO shopOrderDAO = new ShopOrderDAO();
                ShopOrder shopOrder = new ShopOrder();
                shopOrder.setClientId(em2.find(Client.class, clientId));
                shopOrder.setOrderDate(Date.from(Instant.now()));
                shopOrder.setDeliveryDate(null);
                shopOrder.setState("Seleccionada");
                shopOrder.setTotalSale(0);
                shopOrderDAO.persist(shopOrder);
                System.out.println(q.getResultList());
                orderId = (Integer) q.getResultList().get(0);
                se = em2.merge(em2.find(StockElement.class, q2.getResultList().get(0)));
                se.setShopOrderOrderId(em2.find(ShopOrder.class, orderId));
                se.setAvaliable(false);
                transaction.commit();
            }
            //System.out.println(q.getSingleResult());
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            em.close();
            em2.close();
        }
        // System.out.println(orderId);
        return se.getShopOrderOrderId().getOrderId();
    }

    public void removeFromCart(int vehicleId, int clientId, int quantityToRemove) {
        StringBuilder sb = new StringBuilder();
        EntityManager em = emf1.createEntityManager();

        List<Integer> stocks = new ArrayList<Integer>();

        sb.append("SELECT element_id FROM client, shop_order ,stock_element, vehicle WHERE client.client_id  = shop_order.client_id AND shop_order_order_id = order_id AND shop_order.`state` = \"Seleccionada\" AND vehicle_vehicle_id = vehicle_id AND client.client_id = ");
        sb.append(clientId);
        sb.append(" AND vehicle_id = ");
        sb.append(vehicleId);
        sb.append(" ;");
        StockElement se;
        Query q = em.createNativeQuery(sb.toString());
        EntityManager em2 = emf1.createEntityManager();
        EntityTransaction transaction = em2.getTransaction();
        try {
            stocks = q.getResultList();
            transaction.begin();
            for (int i = 0; i < quantityToRemove; i++) {
                se = em2.merge(em2.find(StockElement.class, stocks.get(i)));
                se.setShopOrderOrderId(null);
                se.setAvaliable(true);
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.toString());
            transaction.rollback();
        } finally {
            em.close();
        }
    }

}
