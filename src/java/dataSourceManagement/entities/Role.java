/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Familia Casas
 */
@Entity
@Table(name = "role")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r"),
    @NamedQuery(name = "Role.findByRoleId", query = "SELECT r FROM Role r WHERE r.roleId = :roleId"),
    @NamedQuery(name = "Role.findByName", query = "SELECT r FROM Role r WHERE r.name = :name"),
    @NamedQuery(name = "Role.findByPermissions", query = "SELECT r FROM Role r WHERE r.permissions = :permissions")})
public class Role implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roleId")
    private Collection<Authentication> authenticationCollection;

    public static final String ADMINISTRATOR = "Administrator";
    public static final String EMPLOYEE = "Employee";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "role_id")
    private Integer roleId;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Size(max = 150)
    @Column(name = "permissions")
    private String permissions;
    @Transient
    private Map<Class, List<String>> permissionsMap;

    public Role() {
        permissionsMap = new HashMap<>();
    }

    public Role(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
        //   createPermissionsMap();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleId != null ? roleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        if ((this.roleId == null && other.roleId != null) || (this.roleId != null && !this.roleId.equals(other.roleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dataSourceManagement.entities.Role[ roleId=" + roleId + " ]";
    }

    public boolean checkPermissions(Class c, String action) {
        if (permissionsMap.isEmpty()) {
            createPermissionsMap();
        }
        List<String> actions = permissionsMap.get(c);
        if (actions != null) {
            return actions.contains(action);
        }
        return false;
    }

    private void createPermissionsMap() {
        if (permissions == null || permissions.isEmpty()) {
            return;
        }
        String[] tableList = permissions.split(";");
        String[] perXid;
        String id;
        List<String> per;
        for (String table : tableList) {
            perXid = table.split("_");
            id = perXid[1];
            per = parsePermission(perXid[0]);
            permissionsMap.put(getClass(id), per);
        }
    }

    private List<String> parsePermission(String perXid) {
        String cad = perXid.replaceAll("\\(", "");
        cad = cad.replaceAll("\\)", "");
        return new ArrayList<>(Arrays.asList(cad.split(",")));
    }

    private Class getClass(String id) {
        if ("EM".equals(id)) {
            return Employee.class;
        }
        if ("DI".equals(id)) {
            return Discount.class;
        }
        if ("CL".equals(id)) {
            return Client.class;
        }
        if ("VE".equals(id)) {
            return Vehicle.class;
        }
        if ("PU".equals(id)) {
            return Purchase.class;
        }
        if ("ST".equals(id)) {
            return StockElement.class;
        }
        return null;
    }

    @XmlTransient
    public Collection<Authentication> getAuthenticationCollection() {
        return authenticationCollection;
    }

    public void setAuthenticationCollection(Collection<Authentication> authenticationCollection) {
        this.authenticationCollection = authenticationCollection;
    }

}
