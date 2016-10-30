/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.controller;

import dataSourceManagement.DAO.AuthenticationDAO;
import dataSourceManagement.DAO.ClientDAO;
import dataSourceManagement.DAO.DiscountDAO;
import dataSourceManagement.DAO.ShopOrderDAO;
import dataSourceManagement.DAO.StockElementDAO;
import dataSourceManagement.DAO.VehicleDAO;
import dataSourceManagement.entities.Client;
import dataSourceManagement.entities.Discount;
import dataSourceManagement.entities.ShopOrder;
import dataSourceManagement.entities.Vehicle;
import java.util.Collection;
import java.util.Date;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author mssg_
 */
public class HandleAddVehicle {

    public static final String ID = "id";

    public void addToCart(int vehicleId, int quantity) {
        ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
        ClientDAO clientDAO = new ClientDAO();
        Client client = clientDAO.searchByNit((String) ex.getSessionMap().get(ID));
        System.out.println(client.toString());
        StockElementDAO stockElementDAO = new StockElementDAO();
        int shopOrderId = stockElementDAO.addToCart(vehicleId, client.getClientId(), quantity);
        for (int i = 0; i < quantity - 1; i++) {
            stockElementDAO.addToCart(vehicleId, client.getClientId(), quantity);
        }
        ShopOrderDAO soDao = new ShopOrderDAO();
        soDao.editTotalSale(shopOrderId, getTotalSale(vehicleId, quantity));
    }

    public String addToCartAsService(int vehicleId, int quantity, String username) {
        AuthenticationDAO authDao = new AuthenticationDAO();
        ClientDAO clientDAO = new ClientDAO();
        Client client = clientDAO.searchByUsername(authDao.searchByUsername(username));
        StockElementDAO stockElementDAO = new StockElementDAO();
        int shopOrderId = stockElementDAO.addToCart(vehicleId, client.getClientId(), quantity);
        for (int i = 0; i < quantity - 1; i++) {
            stockElementDAO.addToCart(vehicleId, client.getClientId(), quantity);
        }
        ShopOrderDAO soDao = new ShopOrderDAO();
        soDao.editTotalSale(shopOrderId, getTotalSale(vehicleId, quantity));
        HandleAutoSell has = new HandleAutoSell();
        return has.payOrder(shopOrderId, "Efectivo", soDao.searchByOrderId(shopOrderId).getTotalSale());
    }

    public float getTotalSale(Integer vehicleId, int quantity) {
        VehicleDAO vehicleDao = new VehicleDAO();
        Vehicle vehicle = vehicleDao.findVehicle(vehicleId);
        float total = vehicle.getSellPrice() * quantity;
        DiscountDAO dDAO = new DiscountDAO();
        Collection<Discount> discountList;
        discountList = dDAO.searchGroupByVehicleId(vehicle);
        for (Discount discount : discountList) {
            if (new Date().before(discount.getExpirationDate())) {
                total -= quantity * discount.getDiscountAmount();
            }
        }
        return total;
    }

}
