package businessLogic.controller;

import dataSourceManagement.DAO.AuthenticationDAO;
import dataSourceManagement.DAO.RoleDAO;
import dataSourceManagement.entities.Authentication;
import dataSourceManagement.entities.Role;

public class HandleAuthentication {
    
    public String createAccount(String username, String password, int role){
        RoleDAO roleDao = new RoleDAO();
        Role rol = roleDao.searchById(role);
        System.out.print(rol.getName());
        Authentication auth = new Authentication();
        auth.setUserName(username);
        auth.setPassword(password);
        if (rol != null){
            auth.setRoleId(rol);
        }
        
        AuthenticationDAO auth_dao = new AuthenticationDAO();
        Authentication new_auth = auth_dao.persist(auth);
        System.out.println("Auth: " + new_auth.getUserName());
        if(new_auth != null)
            return "Cuenta creada éxitosamente";
        else
            return "La cuenta no pudo ser creada";
    }
}
