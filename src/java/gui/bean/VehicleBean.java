/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bean;

import dataSourceManagement.DAO.VehicleDAO;
import dataSourceManagement.entities.Vehicle;
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
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
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
    }

}
