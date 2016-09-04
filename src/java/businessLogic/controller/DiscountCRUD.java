/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.controller;

import dataSourceManagement.DAO.DiscountDAO;
import dataSourceManagement.entities.Discount;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JuanCamilo
 */
public class DiscountCRUD {

    public void createDiscount(Discount discount) {
        if (AuthentificationManager.checkPermissions(Discount.class, Actions.CREATE)) {
            DiscountDAO dDAO = new DiscountDAO();
            try {
                dDAO.create(discount);
            } catch (Exception ex) {
                Logger.getLogger(DiscountCRUD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void editDiscount(Discount discount) {
        if (AuthentificationManager.checkPermissions(Discount.class, Actions.UPDATE)) {
            DiscountDAO dDAO = new DiscountDAO();
            try {
                dDAO.edit(discount);
            } catch (Exception ex) {
                Logger.getLogger(DiscountCRUD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void deleteDiscount(int discountID) {
        if (AuthentificationManager.checkPermissions(Discount.class, Actions.DELETE)) {
            DiscountDAO dDAO = new DiscountDAO();
            try {
                dDAO.destroy(discountID);
            } catch (Exception ex) {
                Logger.getLogger(DiscountCRUD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
