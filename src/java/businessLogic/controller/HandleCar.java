/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.controller;

import static businessLogic.controller.HandleAddVehicle.ID;
import dataSourceManagement.DAO.CarDAO;
import dataSourceManagement.DAO.ClientDAO;
import dataSourceManagement.DAO.StockElementDAO;
import dataSourceManagement.entities.Client;
import dataSourceManagement.entities.StockElement;
import dataSourceManagement.entities.Vehicle;
import java.util.ArrayList;
import java.util.Collection;
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
        Vehicle vehicle = new Vehicle();
        vehicle = carDao.getSingleCar(vehicleId);
        return vehicle;
    }
    public Long carsSelectedByOrder(int vehicleId){
        ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
        ClientDAO clientDAO = new ClientDAO();
        Client client = clientDAO.searchByNit((String) ex.getSessionMap().get(ID));
        Long quantity = null ;
        CarDAO carDAO = new CarDAO();
        System.out.println(client.getClientId());
        quantity = carDAO.carsSelectedByOrder(vehicleId,client.getClientId());
        return quantity;
    }
}
