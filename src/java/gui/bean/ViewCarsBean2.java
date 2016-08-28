/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bean;

import dataSourceManagement.entities.Vehicle;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author mssg_
 */
@ManagedBean(name = "viewCarsBean2")
@ViewScoped

public class ViewCarsBean2 {
    
    private Vehicle vechicle = new Vehicle();
    private String call ;

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public Vehicle getVechicle() {
        return vechicle;
    }

    public void setVechicle(Vehicle vechicle) {
        this.vechicle = vechicle;
    }
    
    public String index(){
        return "index";
    }

    /**
     * Creates a new instance of ViewCarsBean2
     */
    public ViewCarsBean2() {
    }
    
    public List<String> findAll(){
        List<String> listProduct = new ArrayList<String>();
        
        listProduct.add("osiejdpw");
        listProduct.add("weiejdpw");
        listProduct.add("iejdpw");
        listProduct.add("we");
        
        return listProduct;
    }
}
