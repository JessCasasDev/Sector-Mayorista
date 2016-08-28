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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JuanCamilo
 */
@Entity
@Table(name = "authentication")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Authentication.findAll", query = "SELECT a FROM Authentication a"),
    @NamedQuery(name = "Authentication.findByAuthId", query = "SELECT a FROM Authentication a WHERE a.authId = :authId"),
    @NamedQuery(name = "Authentication.findByUserName", query = "SELECT a FROM Authentication a WHERE a.userName = :userName"),
    @NamedQuery(name = "Authentication.findByPassword", query = "SELECT a FROM Authentication a WHERE a.password = :password")})
public class Authentication implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "auth_id")
    private Integer authId;
    @Size(max = 45)
    @Column(name = "user_name")
    private String userName;
    @Size(max = 45)
    @Column(name = "password")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "authId")
    private Collection<Client> clientCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "authId")
    private Collection<Employee> employeeCollection;
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @ManyToOne(optional = false)
    private Role roleId;

    public Authentication() {
    }

    public Authentication(Integer authId) {
        this.authId = authId;
    }

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public Collection<Client> getClientCollection() {
        return clientCollection;
    }

    public void setClientCollection(Collection<Client> clientCollection) {
        this.clientCollection = clientCollection;
    }

    @XmlTransient
    public Collection<Employee> getEmployeeCollection() {
        return employeeCollection;
    }

    public void setEmployeeCollection(Collection<Employee> employeeCollection) {
        this.employeeCollection = employeeCollection;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (authId != null ? authId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Authentication)) {
            return false;
        }
        Authentication other = (Authentication) object;
        if ((this.authId == null && other.authId != null) || (this.authId != null && !this.authId.equals(other.authId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dataSourceManagement.entities.Authentication[ authId=" + authId + " ]";
    }
    
}
