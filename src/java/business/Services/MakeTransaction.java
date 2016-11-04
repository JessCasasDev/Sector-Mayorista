/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.Services;

import businessLogic.controller.HandleAddVehicle;
import businessLogic.controller.HandleCar;
import dataSourceManagement.DAO.AuthenticationDAO;
import dataSourceManagement.DAO.ClientDAO;
import dataSourceManagement.DAO.StockElementDAO;
import dataSourceManagement.DAO.VehicleDAO;
import dataSourceManagement.entities.Authentication;
import dataSourceManagement.entities.Client;
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
        AuthenticationDAO authDao = new AuthenticationDAO();
        Authentication auth = authDao.searchByUsername(userName);
        
        if (auth != null && !auth.getPassword().equals(password)) {
            return new AutoMResponseMessage("No se encontro el usuario", null, false);
        } else {
            ClientDAO clientDao = new ClientDAO();
            Client client = clientDao.searchByUsername(auth);
            if (client == null) {
                return new AutoMResponseMessage("No se encontro el Cliente", null, false);
            } else {
                
                VehicleDAO vDAO = new VehicleDAO();
                Vehicle vehicle = vDAO.findVehicle(Integer.valueOf(vehicleId));
                if (vehicle == null) {
                    return new AutoMResponseMessage("No se encontro el Vehiculo", null, false);
                } else {
                    HandleAddVehicle hav = new HandleAddVehicle();
                    StockElementDAO sea = new StockElementDAO();
                    List<Long> o = sea.searchByVIdAndAvaliablity(vehicle.getVehicleId());
                    for (Long long1 : o) {
                        System.out.println(o);
                    }
                    int totalQuantity = (int)(long)o.get(0);
                    int quantity2 = Integer.valueOf(quantity);
                    if(totalQuantity < quantity2) quantity2 = totalQuantity; 
                    String pago = hav.addToCartAsService(Integer.parseInt(vehicleId), quantity2, userName);
                    vMap.put("Tipo vehiculo", vehicleId);
                    vMap.put(VEHICLE_MODEL, String.valueOf(vehicle.getModel()));
                    vMap.put(VEHICLE_BRAND, String.valueOf(vehicle.getBrand()));
                    vMap.put(VEHICLE_COLOR, String.valueOf(vehicle.getColor()));
                    vMap.put(VEHICLE_SELL_PRICE, String.valueOf(vehicle.getSellPrice()));
                    vMap.put(VEHICLE_TYPE, String.valueOf(vehicle.getType()));
                    vMap.put(VEHICLE_DESCRIPTION, String.valueOf(vehicle.getDescription()));
                    vMap.put("Cantidad", String.valueOf(quantity2));
                    vMap.put("Resumen transaccion", pago);
                    responseList.add(vMap);
                    if(pago.equals("Pago total")) return new AutoMResponseMessage(userName + ": " + pago, responseList, true);
                    else return new AutoMResponseMessage(userName + ": " + pago + " - Transaccion incompleta", responseList, false);
                }

            }
        }
    }
}
