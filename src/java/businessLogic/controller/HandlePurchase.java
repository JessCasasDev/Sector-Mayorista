package businessLogic.controller;

import business.ServicesConsume.ResponseMessage;
import dataSourceManagement.DAO.EmployeeDAO;
import dataSourceManagement.DAO.PurchaseDAO;
import dataSourceManagement.entities.Employee;
import dataSourceManagement.entities.Purchase;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class HandlePurchase implements Serializable{
    public static final String ID = "id";
    
    public Purchase createPurchase(Date date, Integer quantity) throws ParseException {
        PurchaseDAO purchase = new PurchaseDAO();
        EmployeeDAO emp = new EmployeeDAO();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Integer current_id = (Integer) ec.getSessionMap().get(ID);
        Employee employee = emp.searchById(current_id);
        
        Purchase p = new Purchase();
        p.setDeliveryDate(date);
        p.setQuantity(quantity);
        p.setPurchaseDate(new Date());
        p.setEmployeeEmployeeId(employee);
        
        return purchase.persist(p);
        
    }
    
}
