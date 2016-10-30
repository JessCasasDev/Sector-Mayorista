/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.controller;

import dataSourceManagement.DAO.CarDAO;
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
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author mssg_
 */
public class HandleCar {
    public static final String ID = "id";
    
    public List<Vehicle> getCars(){
        CarDAO carDao = new CarDAO();
        List<Vehicle> vehicle = carDao.getCars();
        return vehicle;
    }
    
    public float getDiscoutPriceByVehicle(Vehicle v){
        DiscountDAO dDAO = new DiscountDAO();
        Collection<Discount> discountList;
        discountList = dDAO.searchGroupByVehicleId(v);
        float priceDiscounted = v.getSellPrice();
        for (Discount discount : discountList) {
            priceDiscounted -= discount.getDiscountAmount();
        }
        return priceDiscounted;
    }
    
    public float getDiscountPriceByVehicle(ShopOrder order, Integer quantity){
        return order.getTotalSale()/quantity;
    }
    
    public Long searchByVIdAndAvaliablity(int vehicleId){
        StockElementDAO stockDao = new StockElementDAO();
        List<Long> stockList = stockDao.searchByVIdAndAvaliablity(vehicleId);
        return stockList.get(0);
    }
    public List<Integer> getCarsByClientOrder(){
        ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
        ClientDAO clientDAO = new ClientDAO();
        Client client = clientDAO.searchByNit((String) ex.getSessionMap().get(ID));
        CarDAO carDao = new CarDAO();
        List<Integer> vehicle = carDao.getCarsByClientOrder(client);
        return vehicle;
    }
    public Vehicle getSingleCar(int vehicleId){
        CarDAO carDao = new CarDAO();
        Vehicle vehicle;
        vehicle = carDao.getSingleCar(vehicleId);
        return vehicle;
    }
    public Long carsSelectedByOrder(int vehicleId){
        ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
        ClientDAO clientDAO = new ClientDAO();
        Client client = clientDAO.searchByNit((String) ex.getSessionMap().get(ID));
        Long quantity;
        CarDAO carDAO = new CarDAO();
        //System.out.println(client.getClientId());
        quantity = carDAO.carsSelectedByOrder(vehicleId,client.getClientId());
        return quantity;
    }
    public void removeFromCart(int vehicleId ,int quantityToRemove){
        ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
        ClientDAO clientDAO = new ClientDAO();
        Client client = clientDAO.searchByNit((String) ex.getSessionMap().get(ID));
        ShopOrderDAO orderDao = new ShopOrderDAO();
        StockElementDAO stockElementDAO = new StockElementDAO();
        stockElementDAO.removeFromCart(vehicleId, client.getClientId() ,quantityToRemove);
        int orderId = orderDao.searchByStateAndClient("Seleccionada", client).getOrderId();
        orderDao.editTotalSale(orderId, -1*getTotalSale(vehicleId, quantityToRemove));
    }
    
    public float getTotalSale(Integer vehicleId, int quantity){
        VehicleDAO vehicleDao = new VehicleDAO();
        Vehicle vehicle = vehicleDao.findVehicle(vehicleId);
        float total = vehicle.getSellPrice() * quantity;
        DiscountDAO dDAO = new DiscountDAO();
        Collection<Discount> discountList;
        discountList = dDAO.searchGroupByVehicleId(vehicle);
        for (Discount discount : discountList) {
                if (new Date().before(discount.getExpirationDate())) {
                    total -= discount.getDiscountAmount();
                }
            }
        return total;
    }
}
