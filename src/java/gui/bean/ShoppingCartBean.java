/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.bean;

import dataSourceManagement.DAO.ShopOrderDAO;
import dataSourceManagement.entities.Client;
import dataSourceManagement.entities.Discount;
import dataSourceManagement.entities.ShopOrder;
import dataSourceManagement.entities.Payment;
import dataSourceManagement.entities.StockElement;
import java.util.Collection;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author afacunaa
 */

@ManagedBean
@ViewScoped
public class ShoppingCartBean {
    
    private Integer orderId;
    private Date orderDate;
    private Date deliveryDate;
    private String state; //Finalizada, en espera (pagos parciales), seleccionada (carrito)
    private Collection<Discount> discountCollection;
    private Collection<StockElement> stockElementCollection;
    private Collection<Payment> paymentCollection;
    private Client clientId;
    
    public Collection<ShopOrder> displayCart(){
        ShopOrderDAO orderDAO = new ShopOrderDAO();
        Collection<ShopOrder> orderCollection = orderDAO.searchGroupByState("Seleccionada");
        return orderCollection;
    }
    
    
    
}
