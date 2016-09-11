/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JuanCamilo
 */
@Entity
@Table(name = "discount")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Discount.findAll", query = "SELECT d FROM Discount d"),
    @NamedQuery(name = "Discount.findByDiscountId", query = "SELECT d FROM Discount d WHERE d.discountId = :discountId"),
    @NamedQuery(name = "Discount.findByExpirationDate", query = "SELECT d FROM Discount d WHERE d.expirationDate = :expirationDate"),
    @NamedQuery(name = "Discount.findByDiscountAmount", query = "SELECT d FROM Discount d WHERE d.discountAmount = :discountAmount"),
    @NamedQuery(name = "Discount.findByPercentage", query = "SELECT d FROM Discount d WHERE d.percentage = :percentage"),
    @NamedQuery(name = "Discount.findByVehicleId", query = "SELECT d FROM Discount d WHERE d.vehicleId = :vehicleId")})
public class Discount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "discount_id")
    private Integer discountId;
    @Column(name = "expiration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
    @Lob
    @Size(max = 16777215)
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "discount_amount")
    private Float discountAmount;
    @Column(name = "percentage")
    private Float percentage;
    @JoinColumn(name = "shop_order_order_id", referencedColumnName = "order_id")
    @ManyToOne(optional = true)
    private ShopOrder shopOrderOrderId;
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_id")
    @ManyToOne(optional = false)
    private Vehicle vehicleId;

    public Discount() {
    }

    public Discount(Integer discountId) {
        this.discountId = discountId;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (discountId != null ? discountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Discount)) {
            return false;
        }
        Discount other = (Discount) object;
        if ((this.discountId == null && other.discountId != null) || (this.discountId != null && !this.discountId.equals(other.discountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dataSourceManagement.entities.Discount[ discountId=" + discountId + " ]";
    }

    public String getLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(expirationDate) + " %: " + percentage + " D amount "
                + discountAmount + " description: " + description
                + " vehicle: " + vehicleId.getLabel();
    }

}
