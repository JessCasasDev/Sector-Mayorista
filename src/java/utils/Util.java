/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dataSourceManagement.DAO.SessionDAO;
import dataSourceManagement.DAO.exceptions.RollbackFailureException;
import dataSourceManagement.entities.Session;
import gui.bean.VehicleBean;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author JuanCamilo
 */
public class Util implements Serializable {

    private static final String CAR_VIEW = "/car/carView.xhtml";

    public static void showVehicle(Integer vehicleId) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getSessionMap().put(VehicleBean.VEHICLE_ID, vehicleId);
        String url = ec.encodeActionURL(FacesContext.getCurrentInstance()
                .getApplication().getViewHandler()
                .getActionURL(FacesContext.getCurrentInstance(),
                        CAR_VIEW));
        System.out.println("url to redirect: " + url);
        ec.redirect(url);
    }

    /**
     * *
     *
     * @return null if not session found
     */
    public static Session getCurrentSession() {
        SessionDAO sDAO = new SessionDAO();
        Session findSession = sDAO.findSession(getSessionID());
        return findSession;
    }

    public static String getSessionID() {
        String sessionID
                = ((HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest())
                .getHeaders("user-agent").nextElement();
        return sessionID;
    }

    public static void redirect(Session session) throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect(session.getCurrentView());
    }

    public static String getCurrentView() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String uri = ((HttpServletRequest) externalContext.getRequest()).getRequestURI();
        return uri;
    }

    /**
     * *
     *
     * @param cSession session to save
     * @return if a session has been saved
     */
    public static boolean saveCurrentSession(Session cSession) {
        SessionDAO sDAO = new SessionDAO();
        try {
            sDAO.create(cSession);
            return true;

        } catch (RollbackFailureException ex) {
            Logger.getLogger(Util.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Util.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
