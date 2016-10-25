package businessLogic.controller;

import dataSourceManagement.DAO.AuthenticationDAO;
import dataSourceManagement.DAO.RoleDAO;
import dataSourceManagement.entities.Authentication;
import dataSourceManagement.entities.Role;

public class HandleAuthentication {

    public Authentication createAccount(String username, String password, Integer role) {
        RoleDAO roleDao = new RoleDAO();
        AuthenticationDAO auth_dao = new AuthenticationDAO();
        Authentication auth = new Authentication();

        Authentication is_username = auth_dao.searchByUsername(username);
        if (is_username == null) {
            Role rol = roleDao.searchById(role);

            auth.setUserName(username);
            auth.setPassword(password);
            if (rol != null) {
                auth.setRoleId(rol);
            }

            Authentication new_auth = auth_dao.persist(auth);
            System.out.println("Auth: " + new_auth.getUserName());
            return auth;
        } else {
            is_username = null;
        }
        return is_username;
    }
}
