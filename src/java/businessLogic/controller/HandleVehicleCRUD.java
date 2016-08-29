/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.controller;

import dataSourceManagement.DAO.VehicleDAO;
import dataSourceManagement.DAO.exceptions.NonexistentEntityException;
import dataSourceManagement.DAO.exceptions.RollbackFailureException;
import dataSourceManagement.entities.Vehicle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JuanCamilo
 */
public class HandleVehicleCRUD {

    public boolean createVehicle(String brand, Integer model, String color,
            Float cost, Float sellP, String description, String type) {
        VehicleDAO vehicleDAO = new VehicleDAO();
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(brand);
        vehicle.setModel(model);
        vehicle.setColor(color);
        vehicle.setCost(cost);
        vehicle.setSellPrice(sellP);
        vehicle.setDescription(description);
        vehicle.setType(type);
        vehicleDAO.persist(vehicle);
        return true;
    }

    public void editVehicle(Vehicle v) {
        try {
            VehicleDAO vehicleDAO = new VehicleDAO();
            vehicleDAO.edit(v);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(HandleVehicleCRUD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(HandleVehicleCRUD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(HandleVehicleCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteVehicle(Vehicle v) {
        try {
            VehicleDAO vehicleDAO = new VehicleDAO();
            vehicleDAO.destroy(v.getVehicleId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(HandleVehicleCRUD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(HandleVehicleCRUD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(HandleVehicleCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
