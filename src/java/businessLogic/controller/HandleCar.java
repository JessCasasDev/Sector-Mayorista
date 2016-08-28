/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.controller;

import dataSourceManagement.DAO.CarDAO;
import dataSourceManagement.entities.StockElement;
import dataSourceManagement.entities.Vehicle;
import java.util.ArrayList;

/**
 *
 * @author mssg_
 */
public class HandleCar {
    
    public ArrayList<Vehicle> getCars(){
        CarDAO carDao = new CarDAO();
        ArrayList<Vehicle> vehicle = carDao.getCars();
        return vehicle;
    }
    
}
