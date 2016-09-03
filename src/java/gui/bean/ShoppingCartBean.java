/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bean;

import businessLogic.controller.HandleAutoSell;
import dataSourceManagement.entities.Client;
import dataSourceManagement.entities.Discount;
import dataSourceManagement.entities.ShopOrder;
import dataSourceManagement.entities.Payment;
import dataSourceManagement.entities.StockElement;
import java.util.Collection;
import java.util.Date;
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
    private Collection<ShopOrder> orders;
    private String currency;
    private float amount;

    public float getTotal(ShopOrder order) {
        HandleAutoSell has = new HandleAutoSell();
        return has.getTotal(order);
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public ShoppingCartBean(){
        orderId=0;
    }
    
    public Collection<ShopOrder> displayCart(){
        HandleAutoSell has = new HandleAutoSell();
        orders = has.getShoppingCart();
        return orders;
    }

    public Collection<ShopOrder> getOrders() {
        return orders;
    }

    public void setOrders(Collection<ShopOrder> orders) {
        this.orders = orders;
    }
    
    public void setOrderId(Integer orderId){
        this.orderId = orderId;
    }
    
    public Integer getOrderId(){
        return orderId;
    }
    
    public void payShopOrder(){
        HandleAutoSell has = new HandleAutoSell();
        has.payOrder(orderId, currency, amount);
    }
    
}
