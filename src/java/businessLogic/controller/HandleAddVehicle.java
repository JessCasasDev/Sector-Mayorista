/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.controller;

import dataSourceManagement.DAO.ClientDAO;
import dataSourceManagement.DAO.ShopOrderDAO;
import dataSourceManagement.DAO.StockElementDAO;
import dataSourceManagement.entities.Client;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
/**
 *
 * @author mssg_
 */
public class HandleAddVehicle {
    
    public static final String ID = "id";
    
    public void addToCart(int vehicleId , int quantity){
        ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
        ClientDAO clientDAO = new ClientDAO();
        Client client = clientDAO.searchByNit((String) ex.getSessionMap().get(ID));
        System.out.println(client.toString());
        for (int i = 0; i < quantity; i++) {
            StockElementDAO stockElementDAO = new StockElementDAO();
            stockElementDAO.addToCart(vehicleId, client.getClientId());
        }
        
    }
    
    
    
}
