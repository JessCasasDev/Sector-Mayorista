/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JuanCamilo
 */
@Entity
@Table(name = "monthly_register")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MonthlyRegister.findAll", query = "SELECT m FROM MonthlyRegister m"),
    @NamedQuery(name = "MonthlyRegister.findByRegisteredId", query = "SELECT m FROM MonthlyRegister m WHERE m.registeredId = :registeredId"),
    @NamedQuery(name = "MonthlyRegister.findByPayment", query = "SELECT m FROM MonthlyRegister m WHERE m.payment = :payment"),
    @NamedQuery(name = "MonthlyRegister.findByGrade", query = "SELECT m FROM MonthlyRegister m WHERE m.grade = :grade"),
    @NamedQuery(name = "MonthlyRegister.findByDate", query = "SELECT m FROM MonthlyRegister m WHERE m.date = :date"),
    @NamedQuery(name = "MonthlyRegister.findByEmployeeId", query = "SELECT m FROM MonthlyRegister m WHERE m.employeeEmployeeId = :employeeEmployeeId")})
public class MonthlyRegister implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "registered_id")
    private Integer registeredId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "payment")
    private Float payment;
    @Column(name = "grade")
    private Float grade;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "employee_employee_id", referencedColumnName = "employee_id")
    @ManyToOne(optional = false)
    private Employee employeeEmployeeId;

    public MonthlyRegister() {
    }

    public MonthlyRegister(Integer registeredId) {
        this.registeredId = registeredId;
    }

    public Integer getRegisteredId() {
        return registeredId;
    }

    public void setRegisteredId(Integer registeredId) {
        this.registeredId = registeredId;
    }

    public Float getPayment() {
        return payment;
    }

    public void setPayment(Float payment) {
        this.payment = payment;
    }

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Employee getEmployeeEmployeeId() {
        return employeeEmployeeId;
    }

    public void setEmployeeEmployeeId(Employee employeeEmployeeId) {
        this.employeeEmployeeId = employeeEmployeeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (registeredId != null ? registeredId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MonthlyRegister)) {
            return false;
        }
        MonthlyRegister other = (MonthlyRegister) object;
        if ((this.registeredId == null && other.registeredId != null) || (this.registeredId != null && !this.registeredId.equals(other.registeredId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dataSourceManagement.entities.MonthlyRegister[ registeredId=" + registeredId + " ]";
    }
    
}
