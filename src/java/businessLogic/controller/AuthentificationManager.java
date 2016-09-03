/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.controller;

import dataSourceManagement.DAO.RoleDAO;
import dataSourceManagement.entities.Role;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author JuanCamilo
 */
public class AuthentificationManager {

    public static boolean checkPermissions(Class c, String action) {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String roleName = (String) ec.getSessionMap().get(HandleLogin.ROLE);
        RoleDAO rDAO = new RoleDAO();
        Role r = rDAO.searchByRoleName(roleName);
      /*  boolean checkPermission = r.checkPermissions(c, action);
        return checkPermission;*/return true;
    }
}
