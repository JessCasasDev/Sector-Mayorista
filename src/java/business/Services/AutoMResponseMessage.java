/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.Services;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mssg_
 */
public class AutoMResponseMessage implements Serializable {
    private String response;
    private String color;
    private String brand;
    private String model;
    private Map<String, String> data;
    
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getChasis() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    private String chasis;
    private String engine;
    private String price;
    private boolean succesfull;

    public AutoMResponseMessage(String response, Map<String, String> data, boolean succesfull) {
        this.response = response;
        this.data = data;
        this.chasis = data.get("vehicle_chasis");
        this.engine = data.get("vehicle_engine");
        this.model = data.get("model");
        this.brand = data.get("brand");
        this.color = data.get("color");
        this.price = data.get("sell_price");
        this.succesfull = succesfull;
    }

    @Override
    public String toString() {
        return "hola"; //To change body of generated methods, choose Tools | Templates.
    }
    

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
    
    public Map<String,String> getData() {
        //    return data;
        return data;
        //return Arrays.toString(data.toArray(new Map[data.size()]));
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public boolean isSuccesfull() {
        return succesfull;
    }

    public void setSuccesfull(boolean succesfull) {
        this.succesfull = succesfull;
    }
    
    
}
