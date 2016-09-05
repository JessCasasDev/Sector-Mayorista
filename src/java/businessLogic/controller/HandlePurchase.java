package businessLogic.controller;

import dataSourceManagement.DAO.EmployeeDAO;
import dataSourceManagement.DAO.PurchaseDAO;
import dataSourceManagement.entities.Employee;
import dataSourceManagement.entities.Purchase;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class HandlePurchase {
    public static final String ID = "id";
    
    public Purchase createPurchase(String deliverydate, Integer quantity) throws ParseException {
        PurchaseDAO purchase = new PurchaseDAO();
        EmployeeDAO emp = new EmployeeDAO();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",
                Locale.US);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Integer current_id = (Integer) ec.getSessionMap().get(ID);
        Employee employee = emp.searchById(current_id);
        Date date = sdf.parse(deliverydate);
        
        Purchase p = new Purchase();
        p.setDeliveryDate(date);
        p.setQuantity(quantity);
        p.setPurchaseDate(new Date());
        p.setEmployeeEmployeeId(employee);
        
        return purchase.persist(p);
        
    }
    
}
