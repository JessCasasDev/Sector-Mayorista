/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import businessLogic.controller.Actions;
import businessLogic.controller.AuthentificationManager;
import dataSourceManagement.entities.Vehicle;
import gui.bean.VehicleBean;
import java.io.IOException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author JuanCamilo
 */
public class Util {

    private static final String CAR_VIEW = "/car/carView.xhtml";

    public static void showVehicle(Integer vehicleId) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getSessionMap().put(VehicleBean.VEHICLE_ID, vehicleId);
        String url = ec.encodeActionURL(FacesContext.getCurrentInstance()
                .getApplication().getViewHandler()
                .getActionURL(FacesContext.getCurrentInstance(),
                        CAR_VIEW));
        System.out.println("url to redirect: " + url);
        ec.redirect(url);
    }
}
