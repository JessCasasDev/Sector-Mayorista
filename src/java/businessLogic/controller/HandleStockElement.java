package businessLogic.controller;

import dataSourceManagement.DAO.PurchaseDAO;
import dataSourceManagement.DAO.StockElementDAO;
import dataSourceManagement.DAO.VehicleDAO;
import dataSourceManagement.entities.Purchase;
import dataSourceManagement.entities.StockElement;
import dataSourceManagement.entities.Vehicle;
import java.util.ArrayList;
import java.util.List;

public class HandleStockElement {

    public boolean createStock(String location, int vehicleId, Integer purchaseId, int quantity) {
        List<StockElement> elements = new ArrayList<>();
        StockElement stock;
        StockElementDAO stockElement = new StockElementDAO();
        PurchaseDAO pd = new PurchaseDAO();
        VehicleDAO vehicleDao = new VehicleDAO();
        Vehicle vehicle = vehicleDao.findVehicle(vehicleId);
        Purchase purchase = pd.searchById(purchaseId);
        for (int i = 0; i < quantity; i++) {
            stock = new StockElement();
            stock.setLocation(location);
            if (purchase != null)
                stock.setPurchasePurchaseId(purchase);
            stock.setAvaliable(true);
            if (vehicle != null)
                stock.setVehicleVehicleId(vehicle);
            elements.add(stock);
        }
        boolean result = stockElement.persist(elements);   
                
        return result;
    }
    
}
