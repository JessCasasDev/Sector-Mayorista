/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


import businessLogic.controller.HandleCar;
import dataSourceManagement.entities.StockElement;
import dataSourceManagement.entities.Vehicle;
import java.util.ArrayList;

/**
 *
 * @author mssg_
 */
@ManagedBean
@ViewScoped
public class ViewCarsBean {
    
    private String message;
    private ArrayList<Vehicle> cars = new ArrayList<>();
    public ViewCarsBean() {
       message = "qwqd";
        
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Vehicle> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Vehicle> cars) {
        this.cars = cars;
    }
    
    public void showCars() {
        System.out.println("dataSourceManagement.DAO.CarDAO.getCars()");
        HandleCar handleCar = new HandleCar();
        cars = handleCar.getCars();
    }
}
