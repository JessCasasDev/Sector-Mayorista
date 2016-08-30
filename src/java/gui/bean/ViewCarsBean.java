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
import java.util.Collection;
import java.util.List;

/**
 *
 * @author mssg_
 */
@ManagedBean
@ViewScoped
public class ViewCarsBean {
    
    private String message;
    private Collection<Vehicle> cars = null;
    private Vehicle vehicle;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    public ViewCarsBean() {
       message = "qwqd";
        
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collection<Vehicle> getCars() {
        return cars;
    }

    public void setCars(Collection<Vehicle> cars) {
        this.cars = cars;
    }
    
    public void showCars() {
        System.out.println("OIJDRFPOKEWDOWKEDPEOKEDKPEKEWPOKOEW");
        HandleCar handleCar = new HandleCar();
        cars = handleCar.getCars();
    }
    public List<Vehicle> findAll(){
        List<Vehicle> listProduct = new ArrayList<Vehicle>();
        HandleCar handleCar = new HandleCar();
        listProduct = handleCar.getCars();
        return listProduct;
    }
}
