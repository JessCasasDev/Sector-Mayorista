package gui.bean;

import businessLogic.controller.HandlePurchase;
import businessLogic.controller.HandleStockElement;
import businessLogic.controller.HandleVehicleCRUD;
import dataSourceManagement.entities.Purchase;
import dataSourceManagement.entities.StockElement;
import dataSourceManagement.entities.Vehicle;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

@ManagedBean
@ViewScoped
public class AcquireVehiclesBean implements Serializable{

    private String deliverydate;
    private Integer quantity;
    private List<Vehicle> vehicles;
    private String location;
    private String selectedItem;
    private HashMap<String, String> availableItems; // +getter
    private String message;
    private Integer day;
    private Integer month;
    private Integer year;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public HashMap<String, String> getAvailableItems() {
        return availableItems;
    }

    public AcquireVehiclesBean() {
        availableItems = new HashMap<>();
        HandleVehicleCRUD handler = new HandleVehicleCRUD();
        vehicles = handler.findVehicleEntities();
        for (Vehicle v : vehicles) {
            availableItems.put(v.getLabel(), v.getVehicleId().toString());
        }
    }

    public void createPurchase(){
        System.out.println("gui.bean.AcquireVehiclesBean.createPurchase()   -   " + quantity);
        HandlePurchase hp = new HandlePurchase();
        HandleStockElement stock = new HandleStockElement();
        Date date;
        try {
            date = setDateTime(day, month, year);
            Purchase purchase = hp.createPurchase(date, quantity);

            if (purchase != null) {
                boolean st = stock.createStock(location, Integer.parseInt(selectedItem), purchase.getPurchaseId(), getQuantity());
                if (st) {
                    message = "Compra realizada éxitosamente";
                } else {
                    message = "Compra fallida por falta de inventario en la fabrica";
                }
            } else {
                message = "Compra fallida";
            }
        } catch (ParseException ex) {
            Logger.getLogger(AcquireVehiclesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void vehicleChanged(ValueChangeEvent e) {
        selectedItem = (String) e.getNewValue();
        System.out.println("SelectedItem: " + selectedItem);
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public String getDeliverydate() {
        return deliverydate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setDeliverydate(String deliverydate) {
        this.deliverydate = deliverydate;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public Date setDateTime(int day, int month, int year) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String s_day = String.valueOf(day);
        String s_month = String.valueOf(month);
        String s_year = String.valueOf(year);
        String dateInString = s_day + "-" + s_month + "-" + s_year + " 23:59:59";
        Date date = sdf.parse(dateInString);
        return date;
    }
}
