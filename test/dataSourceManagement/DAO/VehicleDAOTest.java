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
        VehicleDAO instance = null;
        EntityManager expResult = null;
        EntityManager result = instance.getEntityManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create method, of class VehicleDAO.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Vehicle vehicle = null;
        VehicleDAO instance = null;
        instance.create(vehicle);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class VehicleDAO.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Vehicle vehicle = null;
        VehicleDAO instance = null;
        instance.edit(vehicle);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class VehicleDAO.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = null;
        VehicleDAO instance = null;
        instance.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findVehicleEntities method, of class VehicleDAO.
     */
    @Test
    public void testFindVehicleEntities_0args() {
        System.out.println("findVehicleEntities");
        VehicleDAO instance = null;
        List<Vehicle> expResult = null;
        List<Vehicle> result = instance.findVehicleEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findVehicleEntities method, of class VehicleDAO.
     */
    @Test
    public void testFindVehicleEntities_int_int() {
        System.out.println("findVehicleEntities");
        int maxResults = 0;
        int firstResult = 0;
        VehicleDAO instance = null;
        List<Vehicle> expResult = null;
        List<Vehicle> result = instance.findVehicleEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findVehicle method, of class VehicleDAO.
     */
    @Test
    public void testFindVehicle() {
        System.out.println("findVehicle");
        Integer id = null;
        VehicleDAO instance = null;
        Vehicle expResult = null;
        Vehicle result = instance.findVehicle(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVehicleCount method, of class VehicleDAO.
     */
    @Test
    public void testGetVehicleCount() {
        System.out.println("getVehicleCount");
        VehicleDAO instance = null;
        int expResult = 0;
        int result = instance.getVehicleCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
