/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.controller;

import dataSourceManagement.DAO.ClientDAO;
import dataSourceManagement.entities.Authentication;
import dataSourceManagement.entities.Client;

public class HandleClient {
    
    public String createClient(String name, String nit, String address, Authentication auth){
        Client client = new Client();
        
        client.setName(name);
        client.setNit(nit);
        client.setAddress(address);
        client.setAuthId(auth);
        
        ClientDAO clientDao = new ClientDAO();
        Client cli_1 = clientDao.persist(client);
        
        if(cli_1 != null)
            return "Cuenta creada éxitosamente";
        else
            return "La cuenta no pudo ser creada";
    }
}
