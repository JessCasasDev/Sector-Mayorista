package gui.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ClientProfileBean {
    
    private String name;
    private String nit;
    private String address;
    
    public void ClientProfileBean(){}
    
    public String getName(){
        return this.name;
    }
    
    public String getNit(){
        return this.nit;
    }
    
    public String getAddress(){
        return this.address;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setNit(String nit){
        this.nit = nit;
    }
    
    public void setAddress(String address){
        this.address = address;
    }
}
