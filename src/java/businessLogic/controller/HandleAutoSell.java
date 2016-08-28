/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.controller;

import dataSourceManagement.DAO.OrderDAO;
import dataSourceManagement.entities.Client;
import dataSourceManagement.entities.Discount;
import dataSourceManagement.entities.ShopOrder;
import dataSourceManagement.entities.Payment;
import dataSourceManagement.entities.StockElement;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author afacunaa
 */
public class HandleAutoSell {
      
    public void addToCart(Collection<StockElement> stockElementCollection){ //crear una orden
        
        OrderDAO orderDAO = new OrderDAO();
        
        Integer orderId = orderDAO.newOrderId();
        Date orderDate = new Date();
        Date deliveryDate = new Date();
        String state = "Seleccionada"; //"Finalizada" (pagado todo), "En espera" (pagos parciales), "Seleccionada" (carrito)
        Collection<Discount> discountCollection = null;
        Collection<Payment> paymentCollection = null;
        Client clientId = new Client();
        for (StockElement se:stockElementCollection){
            se.setAvaliable(Boolean.FALSE);
        }
        
        ShopOrder order = new ShopOrder();
        order.setClientId(clientId);
        order.setDeliveryDate(deliveryDate);
        order.setDiscountCollection(discountCollection);
        order.setOrderDate(orderDate);
        order.setOrderId(orderId);
        order.setPaymentCollection(paymentCollection);
        order.setState(state);
        order.setStockElementCollection(stockElementCollection);
        
        orderDAO.persist(order);
    }
    
    public void payOrder(Integer orderId){
        
        OrderDAO orderDAO = new OrderDAO();
        ShopOrder order = orderDAO.searchByOrderId(orderId);
        orderDAO.buyAutos(order);
        
    }
    
    
}
