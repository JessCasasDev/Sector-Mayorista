/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JuanCamilo
 */
@Entity
@Table(name = "purchase")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Purchase.findAll", query = "SELECT p FROM Purchase p"),
    @NamedQuery(name = "Purchase.findByPurchaseId", query = "SELECT p FROM Purchase p WHERE p.purchaseId = :purchaseId"),
    @NamedQuery(name = "Purchase.findByPurchaseDate", query = "SELECT p FROM Purchase p WHERE p.purchaseDate = :purchaseDate"),
    @NamedQuery(name = "Purchase.findByDeliveryDate", query = "SELECT p FROM Purchase p WHERE p.deliveryDate = :deliveryDate"),
    @NamedQuery(name = "Purchase.findByQuantity", query = "SELECT p FROM Purchase p WHERE p.quantity = :quantity")})
public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "purchase_id")
    private Integer purchaseId;
    @Column(name = "purchase_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseDate;
    @Column(name = "delivery_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private int quantity;
    @JoinColumn(name = "employee_employee_id", referencedColumnName = "employee_id")
    @ManyToOne(optional = false)
    private Employee employeeEmployeeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchasePurchaseId")
    private Collection<StockElement> stockElementCollection;

    public Purchase() {
    }

    public Purchase(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Purchase(Integer purchaseId, int quantity) {
        this.purchaseId = purchaseId;
        this.quantity = quantity;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Employee getEmployeeEmployeeId() {
        return employeeEmployeeId;
    }

    public void setEmployeeEmployeeId(Employee employeeEmployeeId) {
        this.employeeEmployeeId = employeeEmployeeId;
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
        hash += (purchaseId != null ? purchaseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Purchase)) {
            return false;
        }
        Purchase other = (Purchase) object;
        if ((this.purchaseId == null && other.purchaseId != null) || (this.purchaseId != null && !this.purchaseId.equals(other.purchaseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dataSourceManagement.entities.Purchase[ purchaseId=" + purchaseId + " ]";
    }

}