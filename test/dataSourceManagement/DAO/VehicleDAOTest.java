/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.DAO;

import dataSourceManagement.entities.Vehicle;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author JuanCamilo
 */
public class VehicleDAOTest {

    public VehicleDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getEntityManager method, of class VehicleDAO.
     */
    @Test
    public void testGetEntityManager() {
        System.out.println("getEntityManager");
        VehicleDAO instance = new VehicleDAO();
        EntityManager result = instance.getEntityManager();
        assertNotNull(result);
    }

    /**
     * Test of create method, of class VehicleDAO.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("mazda");
        vehicle.setVehicleId(1250);
        VehicleDAO instance = new VehicleDAO();
        instance.create(vehicle);
        List<Vehicle> vehicles = instance.findVehicleEntities();
        assertEquals(1, vehicles.size());
        assertEquals(vehicle, vehicles.get(0));
        instance.destroy(vehicles.get(0).getVehicleId());
        assertEquals(0, vehicles.size());
    }

}
