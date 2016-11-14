package businessLogic.controller;

import business.ServicesConsume.ResponseMessage;
import dataSourceManagement.DAO.PurchaseDAO;
import dataSourceManagement.DAO.StockElementDAO;
import dataSourceManagement.DAO.VehicleDAO;
import dataSourceManagement.entities.Purchase;
import dataSourceManagement.entities.StockElement;
import dataSourceManagement.entities.Vehicle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HandleStockElement implements Serializable{

    public boolean createStock(String location, int vehicleId, Integer purchaseId, int quantity) {
        ResponseMessage rm = buyVehicle(33, vehicleId, quantity);
        if (!rm.isSuccess())
            return false;
        List<StockElement> elements = new ArrayList<>();
        StockElement stock;
        StockElementDAO stockElement = new StockElementDAO();
        PurchaseDAO pd = new PurchaseDAO();
        VehicleDAO vehicleDao = new VehicleDAO();
        Vehicle vehicle = vehicleDao.findVehicle(vehicleId);
        Purchase purchase = pd.searchById(purchaseId);
        
        System.out.println("GetData : " + rm.isSuccess() + " , " + rm.getData());
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

    private static ResponseMessage buyVehicle(java.lang.Integer arg0, java.lang.Integer arg1, 
            java.lang.Integer arg2) {
        business.ServicesConsume.VehicleSellerWS_Service service = 
                new business.ServicesConsume.VehicleSellerWS_Service();
        business.ServicesConsume.VehicleSellerWS port = service.getVehicleSellerWSPort();
        return port.buyVehicle(arg0, arg1, arg2);
    }
    
    
    
}
