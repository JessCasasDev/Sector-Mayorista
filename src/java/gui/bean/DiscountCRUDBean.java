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
import businessLogic.controller.DiscountCRUD;
import dataSourceManagement.DAO.DiscountDAO;
import dataSourceManagement.DAO.VehicleDAO;
import dataSourceManagement.entities.Discount;
import dataSourceManagement.entities.ShopOrder;
import dataSourceManagement.entities.Vehicle;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class DiscountCRUDBean {

    //Generated by Map
    private Map<String, Integer> availableVehicles;

    //Generated by Map
    private Map<String, Integer> availableDiscounts;

    private Integer selectedVehicleId;
    private Integer selectedDiscountId;

    private Date expirationDate;
    private String description;
    private Float discountAmount;
    private Float percentage;
    private ShopOrder shopOrderOrderId;
    private Vehicle vehicleId;

    public DiscountCRUDBean() {
        selectedVehicleId = -1;
        selectedDiscountId = -1;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Float discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public ShopOrder getShopOrderOrderId() {
        return shopOrderOrderId;
    }

    public void setShopOrderOrderId(ShopOrder shopOrderOrderId) {
        this.shopOrderOrderId = shopOrderOrderId;
    }

    public Vehicle getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Vehicle vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getSelectedDiscountId() {
        return selectedDiscountId;
    }

    public void setSelectedDiscountId(Integer selectedDiscountId) {
        this.selectedDiscountId = selectedDiscountId;
    }

    public Map<String, Integer> getAvailableVehicles() {
        availableVehicles = new LinkedHashMap<>();
        VehicleDAO vdao = new VehicleDAO();
        List<Vehicle> vehicles = vdao.findVehicleEntities();
        for (Vehicle v : vehicles) {
            availableVehicles.put(v.getLabel(), v.getVehicleId());
        }
        return availableVehicles;
    }

    public Map<String, Integer> getAvailableDiscounts() {
        availableDiscounts = new LinkedHashMap<>();
        DiscountDAO discountDAO = new DiscountDAO();
        List<Discount> discounts = discountDAO.findDiscountEntities();
        for (Discount d : discounts) {
            availableDiscounts.put(d.getLabel(), d.getDiscountId());
        }
        return availableDiscounts;
    }

    public Integer getSelectedVehicleId() {
        return selectedVehicleId;
    }

    public void setSelectedVehicleId(Integer selectedVehicleId) {
        this.selectedVehicleId = selectedVehicleId;
    }

    public void createDiscount() {
        DiscountCRUD discountCRUD = new DiscountCRUD();
        try {
            Discount discount = new Discount();
            discount.setDescription(getDescription());
            discount.setDiscountAmount(getDiscountAmount());
            discount.setPercentage(getPercentage());
            discount.setVehicleId(getSelectedVehicle());
            discountCRUD.createDiscount(discount);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void editDiscount() {
        DiscountCRUD discountCRUD = new DiscountCRUD();
        if (selectedVehicleId == -1) {
            System.err.println("any selected vehicle");
            return;
        }
        try {
            Discount edited = getSelectedDiscount();
            String key = edited.getLabel();
            edited.setDescription(getDescription());
            edited.setDiscountAmount(getDiscountAmount());
            edited.setPercentage(getPercentage());
            edited.setVehicleId(getSelectedVehicle());
            discountCRUD.editDiscount(edited);
            availableDiscounts.remove(key);
            availableDiscounts.put(edited.getLabel(), edited.getDiscountId());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void deleteDiscount() {
        DiscountCRUD deleteCRUD = new DiscountCRUD();
        if (selectedVehicleId == -1) {
            System.err.println("any selected vehicle");
            return;
        }
        Discount deletedDiscount = getSelectedDiscount();
        String key = deletedDiscount.getLabel();
        deleteCRUD.deleteDiscount(getSelectedDiscountId());
        availableVehicles.remove(key);
    }

    private Vehicle getSelectedVehicle() {
        VehicleDAO vdao = new VehicleDAO();
        return vdao.findVehicle(getSelectedVehicleId());
    }

    @Override
    public String toString() {
        return "Selected v " + selectedVehicleId
                + " description " + description;
    }

    private Discount getSelectedDiscount() {
        DiscountDAO vdao = new DiscountDAO();
        return vdao.findDiscount(getSelectedDiscountId());
    }

}
