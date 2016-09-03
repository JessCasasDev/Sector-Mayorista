/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.controller;

import dataSourceManagement.DAO.ClientDAO;
import dataSourceManagement.DAO.PaymentDAO;
import dataSourceManagement.DAO.ShopOrderDAO;
import dataSourceManagement.DAO.StockElementDAO;
import dataSourceManagement.DAO.VehicleDAO;
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
    public static final String ID = "id";
    public static final String SELECCIONADA = "Seleccionada";
    public static final String ESPERA = "En espera";
    public static final String FINALIZADA = "Finalizada";
      
    public void addToCart(Collection<StockElement> stockElementCollection){ //crear una orden
        
        ShopOrderDAO orderDAO = new ShopOrderDAO();
        
        Integer orderId;
        Date orderDate = new Date();
        Date deliveryDate = new Date();
        String state = SELECCIONADA; //"Finalizada" (pagado todo), "En espera" (pagos parciales), "Seleccionada" (carrito)
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
        Client client = clientDAO.searchByNit((String) ec.getSessionMap().get(ID));        
        Collection<ShopOrder> orderCollection = orderDAO.searchGroupByStateAndClient(SELECCIONADA, client);
        Collection<ShopOrder> orderCollection2 = orderDAO.searchGroupByStateAndClient(ESPERA, client);
        orderCollection.addAll(orderCollection2);
        return orderCollection;
    }
    
    public float getTotal(ShopOrder order){
        float total=0;
        StockElementDAO seDAO = new StockElementDAO();
        VehicleDAO vDAO = new VehicleDAO();
        Collection<StockElement> cars = seDAO.searchGroupByOrderIdAndAvailable(order, Boolean.TRUE);
        for (StockElement car : cars) {
            total += vDAO.findVehicle(car.getVehicleVehicleId().getVehicleId()).getSellPrice();
        }
        PaymentDAO payDAO = new PaymentDAO();
        float debt = total;
        for (Payment pay : payDAO.searchGroupByOrderId(order)) {
            if (debt > Float.parseFloat(pay.getDebt())){
                total = Float.parseFloat(pay.getDebt());
                debt = total;
            }
        }
        return total;
    }
    
    public void payOrder(Integer orderId, String currency, float amount){
        ShopOrderDAO orderDAO = new ShopOrderDAO();
        ShopOrder order = orderDAO.searchByOrderId(orderId);
        float total = getTotal(order);
        if (amount < total) { //Pago parcial
            orderDAO.buyAutos(order, currency, ESPERA, total-amount);
        }
        else if (amount == total){ //Pago total
                orderDAO.buyAutos(order, currency, FINALIZADA, total-amount);
            }
        else{
            //Pago de mas
        }
    }
    
    
}
