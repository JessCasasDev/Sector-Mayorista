/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.Services;

import businessLogic.controller.HandleAddVehicle;
import businessLogic.controller.HandleCar;
import dataSourceManagement.DAO.VehicleDAO;
import dataSourceManagement.entities.Vehicle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mssg_
 */
public class MakeTransaction {

    private static final String VEHICLE_DESCRIPTION = "description";
    private static final String VEHICLE_MODEL = "model";
    private static final String VEHICLE_BRAND = "brand";
    private static final String VEHICLE_COLOR = "color";
    private static final String VEHICLE_SELL_PRICE = "sell_price";
    private static final String VEHICLE_TYPE = "type";
    private static final String VEHICLE_ID = "id";

    public AutoMResponseMessage make(String userName, String password) {
        List<Vehicle> listProduct;
        List<Map<String, String>> responseList = new ArrayList<>();
        Map<String, String> vMap;

        HandleCar handleCar = new HandleCar();
        listProduct = handleCar.getCars();
        for (Vehicle vehicle : listProduct) {
            vMap = new HashMap<>();
            vMap.put(VEHICLE_DESCRIPTION, vehicle.getLabel());
            vMap.put(VEHICLE_ID, String.valueOf(vehicle.getVehicleId()));
            responseList.add(vMap);
        }
        return new AutoMResponseMessage(userName, responseList, true);
    }

    public AutoMResponseMessage request(String userName, String password, String vehicleId) {
        List<Map<String, String>> responseList = new ArrayList<>(1);
        Map<String, String> vMap = new HashMap<>();

        VehicleDAO vDAO = new VehicleDAO();
        Vehicle vehicle = vDAO.findVehicle(Integer.valueOf(vehicleId));
        vMap.put(VEHICLE_MODEL, String.valueOf(vehicle.getModel()));
        vMap.put(VEHICLE_BRAND, String.valueOf(vehicle.getBrand()));
        vMap.put(VEHICLE_COLOR, String.valueOf(vehicle.getColor()));
        vMap.put(VEHICLE_SELL_PRICE, String.valueOf(vehicle.getSellPrice()));
        vMap.put(VEHICLE_TYPE, String.valueOf(vehicle.getType()));
        vMap.put(VEHICLE_DESCRIPTION, String.valueOf(vehicle.getDescription()));
        responseList.add(vMap);
        return new AutoMResponseMessage(userName, responseList, true);
    }
    
    public AutoMResponseMessage sell(String userName, String password, String vehicleId, String quantity) {
        List<Map<String, String>> responseList = new ArrayList<>(1);
        Map<String, String> vMap = new HashMap<>();
        VehicleDAO vDAO = new VehicleDAO();
        Vehicle vehicle = vDAO.findVehicle(Integer.valueOf(vehicleId));
        HandleAddVehicle hav = new HandleAddVehicle();
        String pago = hav.addToCartAsService(Integer.parseInt(vehicleId), Integer.parseInt(quantity), userName);
        vMap.put("Tipo vehiculo", vehicleId);
        vMap.put("Cantidad", quantity);
        vMap.put("Resumen transaccion", pago);
        
        return new AutoMResponseMessage(userName+": "+pago, responseList, true);
    }
}
