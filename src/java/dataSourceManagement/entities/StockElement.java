/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JuanCamilo
 */
@Entity
@Table(name = "stock_element")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StockElement.findAll", query = "SELECT s FROM StockElement s"),
    @NamedQuery(name = "StockElement.findByElementId", query = "SELECT s FROM StockElement s WHERE s.elementId = :elementId"),
    @NamedQuery(name = "StockElement.findByLocation", query = "SELECT s FROM StockElement s WHERE s.location = :location"),
    @NamedQuery(name = "StockElement.findByAvaliable", query = "SELECT s FROM StockElement s WHERE s.avaliable = :avaliable")})
public class StockElement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "element_id")
    private Integer elementId;
    @Size(max = 45)
    @Column(name = "location")
    private String location;
    @Column(name = "avaliable")
    private Boolean avaliable;
    @JoinColumn(name = "shop_order_order_id", referencedColumnName = "order_id")
    @ManyToOne(optional = false)
    private ShopOrder shopOrderOrderId;
    @JoinColumn(name = "purchase_purchase_id", referencedColumnName = "purchase_id")
    @ManyToOne(optional = false)
    private Purchase purchasePurchaseId;
    @JoinColumn(name = "vehicle_vehicle_id", referencedColumnName = "vehicle_id")
    @ManyToOne(optional = false)
    private Vehicle vehicleVehicleId;

    public StockElement() {
    }

    public StockElement(Integer elementId) {
        this.elementId = elementId;
    }

    public Integer getElementId() {
        return elementId;
    }

    public void setElementId(Integer elementId) {
        this.elementId = elementId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getAvaliable() {
        return avaliable;
    }

    public void setAvaliable(Boolean avaliable) {
        this.avaliable = avaliable;
    }

    public ShopOrder getShopOrderOrderId() {
        return shopOrderOrderId;
    }

    public void setShopOrderOrderId(ShopOrder shopOrderOrderId) {
        this.shopOrderOrderId = shopOrderOrderId;
    }

    public Purchase getPurchasePurchaseId() {
        return purchasePurchaseId;
    }

    public void setPurchasePurchaseId(Purchase purchasePurchaseId) {
        this.purchasePurchaseId = purchasePurchaseId;
    }

    public Vehicle getVehicleVehicleId() {
        return vehicleVehicleId;
    }

    public void setVehicleVehicleId(Vehicle vehicleVehicleId) {
        this.vehicleVehicleId = vehicleVehicleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (elementId != null ? elementId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StockElement)) {
            return false;
        }
        StockElement other = (StockElement) object;
        if ((this.elementId == null && other.elementId != null) || (this.elementId != null && !this.elementId.equals(other.elementId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dataSourceManagement.entities.StockElement[ elementId=" + elementId + " ]";
    }
    
}
