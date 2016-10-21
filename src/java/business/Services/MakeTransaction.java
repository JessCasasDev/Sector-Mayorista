/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.Services;

import businessLogic.controller.HandleCar;
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
}


