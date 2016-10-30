/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bean;

import businessLogic.controller.HandleCar;
import dataSourceManagement.entities.Vehicle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author mssg_
 */
@ManagedBean
@ViewScoped

public class ViewCarsBean2 {

    private Vehicle vehicle;
    private String call;
    private List<Vehicle> listProduct;

    /**
     * Creates a new instance of ViewCarsBean2
     */
    public ViewCarsBean2() {
        vehicle = new Vehicle();
        listProduct = new ArrayList<>();

    }

    public List<Vehicle> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Vehicle> listProduct) {
        this.listProduct = listProduct;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String index() {
        return "index";
    }

    public void sortById() {
        Collections.reverse(listProduct);
//         Collections.sort(listProduct, new Comparator<Vehicle>(){
//             @Override
//
//            public int compare(Vehicle item1, Vehicle item2){
//                int compare = item1.getVehicleId().compareTo(item2.getVehicleId());
//                return compare;
//            }
//        });

    }

    public List<Vehicle> findAll() {

        HandleCar handleCar = new HandleCar();
        listProduct = handleCar.getCars();
        //Collections.reverse(listProduct);
        return listProduct;
    }

    public Long totalAvaliableCars(int carId) {
        Long totalCarsById;
        HandleCar handleCar = new HandleCar();
        totalCarsById = handleCar.searchByVIdAndAvaliablity(carId);
        return totalCarsById;
    }

    public List<Integer> findByClientOrder() {
        List<Integer> list;
        HandleCar handleCar = new HandleCar();
        list = handleCar.getCarsByClientOrder();
        return list;
    }

    public Vehicle findSingleCar(int vehicleId) {
        Vehicle item;
        HandleCar handleCar = new HandleCar();
        item = handleCar.getSingleCar(vehicleId);
        return item;
    }

    public Long carsSelectedByOrder(int vehicleId) {
        System.out.println(vehicleId);
        Long quantity;
        HandleCar handleCar = new HandleCar();
        quantity = handleCar.carsSelectedByOrder(vehicleId);
        return quantity;
    }

    public Float getDiscountedPrice(Vehicle v) {
        HandleCar handleCar = new HandleCar();
        return handleCar.getDiscoutPriceByVehicle(v);
    }

}
