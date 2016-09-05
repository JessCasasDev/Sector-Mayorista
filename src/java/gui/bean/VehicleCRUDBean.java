/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bean;

/**
 *
 * @author JuanCamilo
 */
import businessLogic.controller.HandleVehicleCRUD;
import com.sun.javafx.scene.control.SelectedCellsMap;
import dataSourceManagement.DAO.VehicleDAO;
import dataSourceManagement.entities.Vehicle;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@ManagedBean
@ViewScoped
public class VehicleCRUDBean {

    //Generated by Map
    private Map<String, Integer> availableVehicles;

    private static Integer selectedVehicleId;

    private String type;
    private Integer model;
    private String description;
    private String color;
    private String brand;
    private String cost;
    private String sellPrice;

    public VehicleCRUDBean() {
        selectedVehicleId = -1;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Map<String, Integer> getAvailableVehicles() {
        availableVehicles = new LinkedHashMap<>();
        HandleVehicleCRUD vCRUD = new HandleVehicleCRUD();
        List<Vehicle> vehicles = vCRUD.findVehicleEntities();
        for (Vehicle v : vehicles) {
            availableVehicles.put(v.getLabel(), v.getVehicleId());
        }
        return availableVehicles;
    }

    public Integer getSelectedVehicleId() {
        return selectedVehicleId;
    }

    public void setSelectedVehicleId(Integer selectedVehicleId) {
        this.selectedVehicleId = selectedVehicleId;
    }

    public void fillVehicleData(ValueChangeEvent e) {
        int newVal = Integer.parseInt(e.getNewValue().toString());
        setSelectedVehicleId(newVal);
        System.out.println("new Val selected: " + newVal);
        Vehicle selected = getSelectedVehicle();
        this.setType(selected.getType());
        this.setBrand(selected.getBrand());
        this.setColor(selected.getColor());
        this.setDescription(selected.getDescription());
        this.setModel(selected.getModel());
        this.setCost(selected.getCost() + "");//String.format("%.2f", selected.getCost()));
        this.setSellPrice(selected.getSellPrice() + "");//String.format("%.2f", selected.getSellPrice()));
        FacesContext.getCurrentInstance().renderResponse();
    }

    public void createVehicle() {
        HandleVehicleCRUD createVehicle = new HandleVehicleCRUD();
        try {
            Float costF = Float.valueOf(getCost());
            Float sellCF = Float.valueOf(getSellPrice());
            createVehicle.createVehicle(getBrand(), getModel(), getColor(),
                    costF, sellCF, getDescription(), getType());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void editVehicle() {
        HandleVehicleCRUD editVehicle = new HandleVehicleCRUD();
        if (selectedVehicleId == -1) {
            System.err.println("any selected vehicle");
            return;
        }
        try {
            Float costF = Float.valueOf(getCost());
            Float sellCF = Float.valueOf(getSellPrice());
            Vehicle edited = getSelectedVehicle();
            String key = edited.getLabel();
            edited.setBrand(brand);
            edited.setColor(color);
            edited.setModel(model);
            edited.setCost(costF);
            edited.setSellPrice(sellCF);
            edited.setDescription(description);
            edited.setType(type);
            editVehicle.editVehicle(edited);
            availableVehicles.remove(key);
            availableVehicles.put(edited.getLabel(), edited.getVehicleId());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void deleteVehicle() {
        HandleVehicleCRUD editVehicle = new HandleVehicleCRUD();
        if (selectedVehicleId == -1) {
            System.err.println("any selected vehicle");
            return;
        }
        Vehicle deletedVehicle = getSelectedVehicle();
        String key = deletedVehicle.getLabel();
        editVehicle.deleteVehicle(getSelectedVehicle());
        availableVehicles.remove(key);
    }

    private Vehicle getSelectedVehicle() {
        return getSelectedVehicle(getSelectedVehicleId());
    }

    private Vehicle getSelectedVehicle(int id) {
        VehicleDAO vdao = new VehicleDAO();
        return vdao.findVehicle(id);
    }
    
    public String getVehicleName(int id){
        return getSelectedVehicle(id).getLabel();
    }
     public String getVehiclePrice(int id){
        return getSelectedVehicle(id).getSellPrice().toString();
    }
     
     public String getDiscount(int id, float discount){
         Vehicle vh = getSelectedVehicle(id);
         Float f = (vh.getSellPrice()*discount);
         return f.toString();
         
     }
    

    @Override
    public String toString() {
        return "Selected v " + selectedVehicleId
                + " description " + description;
    }
}
