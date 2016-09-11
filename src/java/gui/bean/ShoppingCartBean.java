/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bean;

import businessLogic.controller.HandleAddVehicle;
import businessLogic.controller.HandleAutoSell;
import businessLogic.controller.HandleCar;
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
    private String vFalse;

    public String getvFalse() {
        return vFalse;
    }

    public void setvFalse(String vFalse) {
        this.vFalse = vFalse;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Collection<Discount> getDiscountCollection() {
        return discountCollection;
    }

    public void setDiscountCollection(Collection<Discount> discountCollection) {
        this.discountCollection = discountCollection;
    }

    public Collection<StockElement> getStockElementCollection() {
        return stockElementCollection;
    }

    public void setStockElementCollection(Collection<StockElement> stockElementCollection) {
        this.stockElementCollection = stockElementCollection;
    }

    public Collection<Payment> getPaymentCollection() {
        return paymentCollection;
    }

    public void setPaymentCollection(Collection<Payment> paymentCollection) {
        this.paymentCollection = paymentCollection;
    }

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    private int quantity;
    private int vehicleId;
    private String message;
    private int quantityToRemove;

    public int getQuantityToRemove() {
        return quantityToRemove;
    }

    public void setQuantityToRemove(int quantityToRemove) {
        this.quantityToRemove = quantityToRemove;
    }

    public float getTotal(ShopOrder order, String withPayments) {
        HandleAutoSell has = new HandleAutoSell();
        if (withPayments.equals("False")){
            return has.getTotal(order, Boolean.FALSE);
        }else{
            return has.getTotal(order, Boolean.TRUE);
        }
    }
    
    public float getAlreadyPaid(ShopOrder order) {
        HandleAutoSell has = new HandleAutoSell();
        return has.getTotal(order, Boolean.FALSE) - has.getTotal(order, Boolean.TRUE);
    }
    
    public String getSumm(ShopOrder order){
        HandleAutoSell has = new HandleAutoSell();
        return has.getSummary(order);
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

    public ShoppingCartBean() {
        orderId = 0;
    }

    public Collection<ShopOrder> displayCart() {
        HandleAutoSell has = new HandleAutoSell();
        orders = has.getShoppingCart();
        return orders;
    }
    
    public Collection<ShopOrder> displayHistory() {
        HandleAutoSell has = new HandleAutoSell();
        return has.getBuyingHistory();
        
    }

    public Collection<ShopOrder> getOrders() {
        return orders;
    }

    public void setOrders(Collection<ShopOrder> orders) {
        this.orders = orders;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void payShopOrder() {
        HandleAutoSell has = new HandleAutoSell();
        message = has.payOrder(orderId, currency, amount);
    }

    public void addVehiclesToCart(int vehicleId, int maxQuantity) {
        if (quantity > maxQuantity) {
            message = "Numero de vehicuos no disponible";
            quantity = 0;
        } else {
            HandleAddVehicle handleAddVehicle = new HandleAddVehicle();
            handleAddVehicle.addToCart(vehicleId, quantity);
            quantity = 0;
        }

    }
    public void removeFromCart(int vehicleId,int maxQuantity){
        if (quantityToRemove > maxQuantity) {
            message = "Numero de vehicuos no disponible";
            quantityToRemove = 0;
        } else {
            HandleCar handleCar = new HandleCar();
            handleCar.removeFromCart(vehicleId, quantityToRemove);
            quantityToRemove = 0;
        }
    }
}
