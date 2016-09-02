/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.controller;

import dataSourceManagement.DAO.ClientDAO;
import dataSourceManagement.DAO.ShopOrderDAO;
import dataSourceManagement.entities.Client;
import dataSourceManagement.entities.Discount;
import dataSourceManagement.entities.ShopOrder;
import dataSourceManagement.entities.Payment;
import dataSourceManagement.entities.StockElement;
import java.util.Collection;
import java.util.Date;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author afacunaa
 */
public class HandleAutoSell {
      
    public void addToCart(Collection<StockElement> stockElementCollection){ //crear una orden
        
        ShopOrderDAO orderDAO = new ShopOrderDAO();
        
        Integer orderId;
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
        order.setPaymentCollection(paymentCollection);
        order.setState(state);
        order.setStockElementCollection(stockElementCollection);
        
        orderDAO.persist(order);
    }
    
    public Collection<ShopOrder> getShoppingCart(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ShopOrderDAO orderDAO = new ShopOrderDAO();
        ClientDAO clientDAO = new ClientDAO();
        Client client = clientDAO.searchByNit((String) ec.getSessionMap().get("id"));        
        Collection<ShopOrder> orderCollection = orderDAO.searchGroupByStateAndClient("Seleccionada", client);
        return orderCollection;
    }
    
    public void payOrder(Integer orderId){
        
        ShopOrderDAO orderDAO = new ShopOrderDAO();
        ShopOrder order = orderDAO.searchByOrderId(orderId);
        orderDAO.buyAutos(order);
        
    }
    
    
}
