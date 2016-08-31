/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bean;

import businessLogic.controller.HandleAutoSell;
import dataSourceManagement.DAO.ShopOrderDAO;
import dataSourceManagement.entities.Client;
import dataSourceManagement.entities.Discount;
import dataSourceManagement.entities.ShopOrder;
import dataSourceManagement.entities.Payment;
import dataSourceManagement.entities.StockElement;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author afacunaa
 */

@ManagedBean
@ViewScoped
public class ShoppingCartBean {
    
    private Integer orderId;
    private Date orderDate;
    private Date deliveryDate;
    private String state; //Finalizada, en espera (pagos parciales), seleccionada (carrito)
    private Collection<Discount> discountCollection;
    private Collection<StockElement> stockElementCollection;
    private Collection<Payment> paymentCollection;
    private Client clientId;
    private Map <String, Integer> availableOrders;
    
    /*public Map<String, Integer> getAvailableOrders(){
        return availableOrders;
    }*/
    
    public void setAvailableOrders(Map<String, Integer> availableOrders){
        this.availableOrders=availableOrders;
    }
    
    public Collection<ShopOrder> displayCart(){
        HandleAutoSell has = new HandleAutoSell();
        return has.getShoppingCart();
    }
    
    public Map<String, Integer> getAvailableOrders(){
        availableOrders = new LinkedHashMap<>();
        ShopOrderDAO vdao = new ShopOrderDAO();
        List<ShopOrder> orders = (List<ShopOrder>) vdao.searchGroupByState("Seleccionada");
        for (ShopOrder v : orders) {
            availableOrders.put(v.getState(), v.getOrderId());
        }
        return availableOrders;
    }
    
    public Integer getOrderId(){
        return orderId;
    }
    
    public void payShopOrder(){
        
    }
    
}
