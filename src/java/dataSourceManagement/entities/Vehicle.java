/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mssg_
 */
@Entity
@Table(name = "vehicle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vehicle.findAll", query = "SELECT v FROM Vehicle v"),
    @NamedQuery(name = "Vehicle.findByVehicleId", query = "SELECT v FROM Vehicle v WHERE v.vehicleId = :vehicleId"),
    @NamedQuery(name = "Vehicle.findByType", query = "SELECT v FROM Vehicle v WHERE v.type = :type"),
    @NamedQuery(name = "Vehicle.findByModel", query = "SELECT v FROM Vehicle v WHERE v.model = :model"),
    @NamedQuery(name = "Vehicle.findByColor", query = "SELECT v FROM Vehicle v WHERE v.color = :color"),
    @NamedQuery(name = "Vehicle.findByBrand", query = "SELECT v FROM Vehicle v WHERE v.brand = :brand"),
    @NamedQuery(name = "Vehicle.findBySellPrice", query = "SELECT v FROM Vehicle v WHERE v.sellPrice = :sellPrice"),
    @NamedQuery(name = "Vehicle.findByCost", query = "SELECT v FROM Vehicle v WHERE v.cost = :cost")})
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "vehicle_id", nullable = false)
    private Integer vehicleId;
    @Size(max = 45)
    @Column(name = "type", length = 45)
    private String type;
    @Column(name = "model")
    private Integer model;
    @Lob
    @Size(max = 16777215)
    @Column(name = "description", length = 16777215)
    private String description;
    @Size(max = 45)
    @Column(name = "color", length = 45)
    private String color;
    @Size(max = 45)
    @Column(name = "brand", length = 45)
    private String brand;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "sell_price", precision = 12)
    private Float sellPrice;
    @Column(name = "cost", precision = 12)
    private Float cost;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicleId")
    private Collection<Discount> discountCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicleVehicleId")
    private Collection<StockElement> stockElementCollection;

    public Vehicle() {
    }

    public Vehicle(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
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

    public Float getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Float sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    @XmlTransient
    public Collection<Discount> getDiscountCollection() {
        return discountCollection;
    }

    public void setDiscountCollection(Collection<Discount> discountCollection) {
        this.discountCollection = discountCollection;
    }

    @XmlTransient
    public Collection<StockElement> getStockElementCollection() {
        return stockElementCollection;
    }

    public void setStockElementCollection(Collection<StockElement> stockElementCollection) {
        this.stockElementCollection = stockElementCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vehicleId != null ? vehicleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vehicle)) {
            return false;
        }
        Vehicle other = (Vehicle) object;
        if ((this.vehicleId == null && other.vehicleId != null) || (this.vehicleId != null && !this.vehicleId.equals(other.vehicleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dataSourceManagement.entities.Vehicle[ vehicleId=" + vehicleId + " ]";
    }
    
}
