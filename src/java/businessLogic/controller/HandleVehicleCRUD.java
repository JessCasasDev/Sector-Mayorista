/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.controller;

import dataSourceManagement.DAO.VehicleDAO;
import dataSourceManagement.DAO.exceptions.RollbackFailureException;
import dataSourceManagement.entities.Vehicle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JuanCamilo
 */
public class HandleVehicleCRUD {

    public boolean createVehicle() {
        VehicleDAO vehicleDAO = new VehicleDAO();
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("mazda");
        vehicle.setModel(2015);
        vehicle.setColor("#FF0066");
        vehicle.setCost("560.000");
        vehicle.setSellPrice("560.000");
        vehicle.setDescription("un muy nuevo auto");
        vehicle.setType("4x4");
        vehicle.setVehicleId(1);
        vehicleDAO.persist(vehicle);
        return true;
    }
}
