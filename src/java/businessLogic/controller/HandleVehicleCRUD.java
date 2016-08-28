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
        try {
            VehicleDAO vehicleDAO = new VehicleDAO();
            Vehicle vehicle = new Vehicle();
            vehicle.setModel(2015);
            vehicleDAO.create(vehicle);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(HandleVehicleCRUD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (Exception ex) {
            Logger.getLogger(HandleVehicleCRUD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}
