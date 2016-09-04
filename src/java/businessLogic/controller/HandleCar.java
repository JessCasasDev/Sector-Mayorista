/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.controller;

import dataSourceManagement.DAO.CarDAO;
import dataSourceManagement.DAO.StockElementDAO;
import dataSourceManagement.entities.StockElement;
import dataSourceManagement.entities.Vehicle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author mssg_
 */
public class HandleCar {

    public List<Vehicle> getCars() {
        CarDAO carDao = new CarDAO();
        List<Vehicle> vehicle = carDao.getCars();
        return vehicle;
    }

    public Long searchByVIdAndAvaliablity(int vehicleId) {
        StockElementDAO stockDao = new StockElementDAO();
        List<Long> stockList = stockDao.searchByVIdAndAvaliablity(vehicleId);
        return stockList.get(0);
    }

}
