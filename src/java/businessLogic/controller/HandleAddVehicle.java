/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.controller;

import dataSourceManagement.DAO.ShopOrderDAO;
import dataSourceManagement.DAO.StockElementDAO;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
/**
 *
 * @author mssg_
 */
public class HandleAddVehicle {
    
    
    public void addToCart(int vehicleId , int quantity){
        ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
        Map<String,Object> clientId = ex.getSessionMap();
        System.out.println(clientId.toString());
        for (int i = 0; i < quantity; i++) {
            StockElementDAO shopOrderDao = new StockElementDAO();
            shopOrderDao.addToCart(vehicleId);
        }
        
    }
    
    
    
}
