/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.controller;

import dataSourceManagement.DAO.ClientDAO;
import dataSourceManagement.DAO.DiscountDAO;
import dataSourceManagement.DAO.PaymentDAO;
import dataSourceManagement.DAO.ShopOrderDAO;
import dataSourceManagement.DAO.StockElementDAO;
import dataSourceManagement.DAO.VehicleDAO;
import dataSourceManagement.entities.Client;
import dataSourceManagement.entities.Discount;
import dataSourceManagement.entities.ShopOrder;
import dataSourceManagement.entities.Payment;
import dataSourceManagement.entities.StockElement;
import dataSourceManagement.entities.Vehicle;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author afacunaa
 */
public class HandleAutoSell implements Serializable{

    public static final String ID = "id";
    public static final String SELECCIONADA = "Seleccionada";
    public static final String ESPERA = "En espera";
    public static final String FINALIZADA = "Finalizada";

    public void addToCart(Collection<StockElement> stockElementCollection) { //crear una orden

        ShopOrderDAO orderDAO = new ShopOrderDAO();

        Integer orderId;
        Date orderDate = new Date();
        Date deliveryDate = new Date();
        String state = SELECCIONADA; //"Finalizada" (pagado todo), "En espera" (pagos parciales), "Seleccionada" (carrito)
        Collection<Discount> discountCollection = null;
        Collection<Payment> paymentCollection = null;
        Client clientId = new Client();
        for (StockElement se : stockElementCollection) {
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

    public Collection<ShopOrder> getShoppingCart() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ShopOrderDAO orderDAO = new ShopOrderDAO();
        ClientDAO clientDAO = new ClientDAO();
        Client client = clientDAO.searchByNit((String) ec.getSessionMap().get(ID));
        Collection<ShopOrder> orderCollection = orderDAO.searchGroupByStateAndClient(SELECCIONADA, client);
        Collection<ShopOrder> orderCollection2 = orderDAO.searchGroupByStateAndClient(ESPERA, client);
        orderCollection.addAll(orderCollection2);
        return orderCollection;
    }

    public Collection<ShopOrder> getBuyingHistory() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ShopOrderDAO orderDAO = new ShopOrderDAO();
        ClientDAO clientDAO = new ClientDAO();
        Client client = clientDAO.searchByNit((String) ec.getSessionMap().get(ID));
        Collection<ShopOrder> orderCollection = orderDAO.searchGroupByStateAndClient(FINALIZADA, client);
        return orderCollection;
    }

    public float getTotal(ShopOrder order, Boolean withPayments) {
        float total = order.getTotalSale();
        /*StockElementDAO seDAO = new StockElementDAO();
        VehicleDAO vDAO = new VehicleDAO();
        DiscountDAO dDAO = new DiscountDAO();
        Collection<StockElement> cars = seDAO.searchGroupByOrderIdAndAvailable(order, Boolean.FALSE);
        Collection<Discount> discountList;
        Vehicle v;
        for (StockElement car : cars) {
            v = vDAO.findVehicle(car.getVehicleVehicleId().getVehicleId());
            total += v.getSellPrice();
            discountList = dDAO.searchGroupByVehicleId(v);
            for (Discount discount : discountList) {
                if (new Date().before(discount.getExpirationDate())) {
                    total -= discount.getDiscountAmount();
                }
            }
        }*/
        if (!order.getState().equals(FINALIZADA) && withPayments) {
            PaymentDAO payDAO = new PaymentDAO();
            float debt = total;
            for (Payment pay : payDAO.searchGroupByOrderId(order)) {
                if (debt > Float.parseFloat(pay.getDebt())) {
                    total = Float.parseFloat(pay.getDebt());
                    debt = total;
                }
            }
        }
        return total;
    }

    public String getSummary(ShopOrder order) {
        String summary = "";
        StockElementDAO seDAO = new StockElementDAO();
        VehicleDAO vDAO = new VehicleDAO();
        HandleCar hc = new HandleCar();
        Collection<StockElement> cars = seDAO.searchGroupByOrderIdAndAvailable(order, Boolean.FALSE);
        Vehicle v;
        HashMap<Vehicle, Integer> hm = new HashMap<>();
        for (StockElement car : cars) {
            v = vDAO.findVehicle(car.getVehicleVehicleId().getVehicleId());
            if (!hm.containsKey(v)) {
                hm.put(v, 1);
            } else {
                hm.put(v, hm.get(v) + 1);
            }
        }
        for (Vehicle x : hm.keySet()) {
            summary += x.getType() + " " + x.getColor() + " - Cantidad: " + hm.get(x) + " - Precio unidad: $ " + x.getSellPrice() + " - Precio unidad con descuento: $ " + hc.getDiscountPriceByVehicle(order, hm.get(x)) + " || " + "\n ";
        }
        return summary;
    }

    public String payOrder(Integer orderId, String currency, float amount) {
        ShopOrderDAO orderDAO = new ShopOrderDAO();
        ShopOrder order = orderDAO.searchByOrderId(orderId);
        float total = getTotal(order, Boolean.TRUE);
        if (amount < total) { //Pago parcial
            orderDAO.buyAutos(order, currency, ESPERA, total - amount);
            return "Pago parcial";
        } else if (amount == total) { //Pago total
            orderDAO.buyAutos(order, currency, FINALIZADA, total - amount);
            return "Pago total";
        } else {
            //Pago de mas
            return "Cantidad incorrecta: Pago de mas";
        }
    }

}
