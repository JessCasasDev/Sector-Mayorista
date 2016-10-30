/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bean;

import dataSourceManagement.DAO.DiscountDAO;
import dataSourceManagement.DAO.VehicleDAO;
import dataSourceManagement.entities.Colors;
import dataSourceManagement.entities.Discount;
import dataSourceManagement.entities.Vehicle;
import java.util.Collection;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author mssg_
 */
@ManagedBean
@ViewScoped

public class VehicleBean {

    public static final String VEHICLE_ID = "vehicle";
    private Vehicle vehicle;
    private Float subTotal;
    private String message;
    private String title;
    private String imageSRC;
    private String type;
    private Integer model;
    private String description;
    private String color;
    private String brand;
    private String cost;
    private String sellPrice;
    private String id;
    private Collection<Discount> discounts;

    /**
     * Creates a new instance of VehicleBean
     */
    public VehicleBean() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Integer id = (Integer) ec.getSessionMap().get(VEHICLE_ID);
        if (id == null) {
            setMessage("no session found");
            return;
        }
        VehicleDAO vdao = new VehicleDAO();
        vehicle = vdao.findVehicle(id);
        fillData(vehicle);
        DiscountDAO dDAO = new DiscountDAO();
        discounts = dDAO.searchGroupByVehicleId(vehicle);
        subTotal = vehicle.getSellPrice();
        for (Discount d : discounts) {
            subTotal -= d.getDiscountAmount();
        }
    }

    public Float getSubTotal() {
        return subTotal;
    }
    
    public String getId(){
        return this.id;
    }
    
    public void setId(String id){
        this.id = id;
    }

    public void setSubTotal(Float subTotal) {
        this.subTotal = subTotal;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.fillData(this.vehicle);
        ViewCarsBean2 v = new ViewCarsBean2();
        this.setSubTotal(v.getDiscountedPrice(vehicle));
        
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageSRC() {
        return imageSRC;
    }

    public void setImageSRC(String imageSRC) {
        this.imageSRC = imageSRC;
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

    private void fillData(Vehicle v) {
        setTitle(v.getType());
        setType(v.getType());
        setModel(v.getModel());
        setDescription(v.getDescription());
        setColor(v.getColor());
        setBrand(v.getBrand());
        setCost(String.valueOf(v.getCost()));
        setSellPrice(String.valueOf(v.getSellPrice()));
        setId(String.valueOf(v.getVehicleId().intValue()));
        setImageSRC("../images/" + getSource(v.getColor()));
    }

    private String getSource(String color) {
        switch (color) {
            case Colors.YELLOW:
                return "yellow.png";
            case Colors.RED:
                return "red.png";
            case Colors.GRAY:
                return "gray.png";
            case Colors.BLACK:
                return "black.png";
            case Colors.WHITE:
                return "white.png";
            case Colors.PURPLE:
                return "purple.png";
            case Colors.GREEN:
                return "green.png";
            case Colors.BLUE:
                return "blue.png";
            case Colors.GOLDEN:
                return "golden.png";
            case Colors.SILVER:
                return "silver.png";
            default:
                return "not_available.png";
        }
    }

    

}
