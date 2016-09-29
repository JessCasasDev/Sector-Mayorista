package businessLogic.controller;

import dataSourceManagement.DAO.PurchaseDAO;
import dataSourceManagement.DAO.StockElementDAO;
import dataSourceManagement.DAO.VehicleDAO;
import dataSourceManagement.entities.Purchase;
import dataSourceManagement.entities.StockElement;
import dataSourceManagement.entities.Vehicle;
import java.util.ArrayList;

public class HandleStockElement {

    public boolean createStock(String location, int vehicleId, Integer purchaseId, int quantity) {
        StockElement stock = new StockElement();
        StockElementDAO stockElement = new StockElementDAO();
        PurchaseDAO pd = new PurchaseDAO();
        VehicleDAO vehicleDao = new VehicleDAO();
        Vehicle vehicle = vehicleDao.findVehicle(vehicleId);
        Purchase purchase = pd.searchById(purchaseId);
        stock.setLocation(location);
        if (purchase != null)
            stock.setPurchasePurchaseId(purchase);
        stock.setAvaliable(true);
        if (vehicle != null)
            stock.setVehicleVehicleId(vehicle);
     
        boolean result = stockElement.persist(stock, quantity);   
                
        return result;
    }
    
}
