/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.DAO;

import dataSourceManagement.DAO.exceptions.IllegalOrphanException;
import dataSourceManagement.DAO.exceptions.NonexistentEntityException;
import dataSourceManagement.DAO.exceptions.PreexistingEntityException;
import dataSourceManagement.DAO.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dataSourceManagement.entities.Discount;
import java.util.ArrayList;
import java.util.Collection;
import dataSourceManagement.entities.StockElement;
import dataSourceManagement.entities.Vehicle;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author JuanCamilo
 */
public class VehicleDAO implements Serializable {

    private UserTransaction utx = null;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("autoMarketPU");

    public Vehicle persist(Vehicle vehicle) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(vehicle);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        System.out.println("vehicle added \n" + vehicle.getLabel());
        return vehicle;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vehicle vehicle) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (vehicle.getDiscountCollection() == null) {
            vehicle.setDiscountCollection(new ArrayList<Discount>());
        }
        if (vehicle.getStockElementCollection() == null) {
            vehicle.setStockElementCollection(new ArrayList<StockElement>());
        }
        EntityManager em = null;
        try {
            //utx.begin();
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Discount> attachedDiscountCollection = new ArrayList<Discount>();
            for (Discount discountCollectionDiscountToAttach : vehicle.getDiscountCollection()) {
                discountCollectionDiscountToAttach = em.getReference(discountCollectionDiscountToAttach.getClass(), discountCollectionDiscountToAttach.getDiscountId());
                attachedDiscountCollection.add(discountCollectionDiscountToAttach);
            }
            vehicle.setDiscountCollection(attachedDiscountCollection);
            Collection<StockElement> attachedStockElementCollection = new ArrayList<StockElement>();
            for (StockElement stockElementCollectionStockElementToAttach : vehicle.getStockElementCollection()) {
                stockElementCollectionStockElementToAttach = em.getReference(stockElementCollectionStockElementToAttach.getClass(), stockElementCollectionStockElementToAttach.getElementId());
                attachedStockElementCollection.add(stockElementCollectionStockElementToAttach);
            }
            vehicle.setStockElementCollection(attachedStockElementCollection);
            em.persist(vehicle);
            for (Discount discountCollectionDiscount : vehicle.getDiscountCollection()) {
                Vehicle oldVehicleIdOfDiscountCollectionDiscount = discountCollectionDiscount.getVehicleId();
                discountCollectionDiscount.setVehicleId(vehicle);
                discountCollectionDiscount = em.merge(discountCollectionDiscount);
                if (oldVehicleIdOfDiscountCollectionDiscount != null) {
                    oldVehicleIdOfDiscountCollectionDiscount.getDiscountCollection().remove(discountCollectionDiscount);
                    oldVehicleIdOfDiscountCollectionDiscount = em.merge(oldVehicleIdOfDiscountCollectionDiscount);
                }
            }
            for (StockElement stockElementCollectionStockElement : vehicle.getStockElementCollection()) {
                Vehicle oldVehicleVehicleIdOfStockElementCollectionStockElement = stockElementCollectionStockElement.getVehicleVehicleId();
                stockElementCollectionStockElement.setVehicleVehicleId(vehicle);
                stockElementCollectionStockElement = em.merge(stockElementCollectionStockElement);
                if (oldVehicleVehicleIdOfStockElementCollectionStockElement != null) {
                    oldVehicleVehicleIdOfStockElementCollectionStockElement.getStockElementCollection().remove(stockElementCollectionStockElement);
                    oldVehicleVehicleIdOfStockElementCollectionStockElement = em.merge(oldVehicleVehicleIdOfStockElementCollectionStockElement);
                }
            }
            // utx.commit();
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                //  utx.rollback();
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findVehicle(vehicle.getVehicleId()) != null) {
                throw new PreexistingEntityException("Vehicle " + vehicle + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vehicle vehicle) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            //utx.begin();
            em = getEntityManager();
            em.getTransaction().begin();
            Vehicle persistentVehicle = em.find(Vehicle.class, vehicle.getVehicleId());
            Collection<Discount> discountCollectionOld = persistentVehicle.getDiscountCollection();
            Collection<Discount> discountCollectionNew = vehicle.getDiscountCollection();
            Collection<StockElement> stockElementCollectionOld = persistentVehicle.getStockElementCollection();
            Collection<StockElement> stockElementCollectionNew = vehicle.getStockElementCollection();
            List<String> illegalOrphanMessages = null;
            for (Discount discountCollectionOldDiscount : discountCollectionOld) {
                if (!discountCollectionNew.contains(discountCollectionOldDiscount)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Discount " + discountCollectionOldDiscount + " since its vehicleId field is not nullable.");
                }
            }
            for (StockElement stockElementCollectionOldStockElement : stockElementCollectionOld) {
                if (!stockElementCollectionNew.contains(stockElementCollectionOldStockElement)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain StockElement " + stockElementCollectionOldStockElement + " since its vehicleVehicleId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Discount> attachedDiscountCollectionNew = new ArrayList<Discount>();
            for (Discount discountCollectionNewDiscountToAttach : discountCollectionNew) {
                discountCollectionNewDiscountToAttach = em.getReference(discountCollectionNewDiscountToAttach.getClass(), discountCollectionNewDiscountToAttach.getDiscountId());
                attachedDiscountCollectionNew.add(discountCollectionNewDiscountToAttach);
            }
            discountCollectionNew = attachedDiscountCollectionNew;
            vehicle.setDiscountCollection(discountCollectionNew);
            Collection<StockElement> attachedStockElementCollectionNew = new ArrayList<StockElement>();
            for (StockElement stockElementCollectionNewStockElementToAttach : stockElementCollectionNew) {
                stockElementCollectionNewStockElementToAttach = em.getReference(stockElementCollectionNewStockElementToAttach.getClass(), stockElementCollectionNewStockElementToAttach.getElementId());
                attachedStockElementCollectionNew.add(stockElementCollectionNewStockElementToAttach);
            }
            stockElementCollectionNew = attachedStockElementCollectionNew;
            vehicle.setStockElementCollection(stockElementCollectionNew);
            vehicle = em.merge(vehicle);
            for (Discount discountCollectionNewDiscount : discountCollectionNew) {
                if (!discountCollectionOld.contains(discountCollectionNewDiscount)) {
                    Vehicle oldVehicleIdOfDiscountCollectionNewDiscount = discountCollectionNewDiscount.getVehicleId();
                    discountCollectionNewDiscount.setVehicleId(vehicle);
                    discountCollectionNewDiscount = em.merge(discountCollectionNewDiscount);
                    if (oldVehicleIdOfDiscountCollectionNewDiscount != null && !oldVehicleIdOfDiscountCollectionNewDiscount.equals(vehicle)) {
                        oldVehicleIdOfDiscountCollectionNewDiscount.getDiscountCollection().remove(discountCollectionNewDiscount);
                        oldVehicleIdOfDiscountCollectionNewDiscount = em.merge(oldVehicleIdOfDiscountCollectionNewDiscount);
                    }
                }
            }
            for (StockElement stockElementCollectionNewStockElement : stockElementCollectionNew) {
                if (!stockElementCollectionOld.contains(stockElementCollectionNewStockElement)) {
                    Vehicle oldVehicleVehicleIdOfStockElementCollectionNewStockElement = stockElementCollectionNewStockElement.getVehicleVehicleId();
                    stockElementCollectionNewStockElement.setVehicleVehicleId(vehicle);
                    stockElementCollectionNewStockElement = em.merge(stockElementCollectionNewStockElement);
                    if (oldVehicleVehicleIdOfStockElementCollectionNewStockElement != null && !oldVehicleVehicleIdOfStockElementCollectionNewStockElement.equals(vehicle)) {
                        oldVehicleVehicleIdOfStockElementCollectionNewStockElement.getStockElementCollection().remove(stockElementCollectionNewStockElement);
                        oldVehicleVehicleIdOfStockElementCollectionNewStockElement = em.merge(oldVehicleVehicleIdOfStockElementCollectionNewStockElement);
                    }
                }
            }
            // utx.commit();
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                System.out.println("error editing \n" + ex.toString());
                em.getTransaction().rollback();
                // utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vehicle.getVehicleId();
                if (findVehicle(id) == null) {
                    throw new NonexistentEntityException("The vehicle with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        System.out.println("vehicle edited \n" + vehicle.getLabel());
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            //utx.begin();
            em = getEntityManager();
            em.getTransaction().begin();
            Vehicle vehicle;
            try {
                vehicle = em.getReference(Vehicle.class, id);
                vehicle.getVehicleId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vehicle with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Discount> discountCollectionOrphanCheck = vehicle.getDiscountCollection();
            for (Discount discountCollectionOrphanCheckDiscount : discountCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vehicle (" + vehicle + ") cannot be destroyed since the Discount " + discountCollectionOrphanCheckDiscount + " in its discountCollection field has a non-nullable vehicleId field.");
            }
            Collection<StockElement> stockElementCollectionOrphanCheck = vehicle.getStockElementCollection();
            for (StockElement stockElementCollectionOrphanCheckStockElement : stockElementCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vehicle (" + vehicle + ") cannot be destroyed since the StockElement " + stockElementCollectionOrphanCheckStockElement + " in its stockElementCollection field has a non-nullable vehicleVehicleId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(vehicle);
            em.getTransaction().commit();
            //utx.commit();
        } catch (Exception ex) {
            try {
                System.out.println("error destroying \n" + ex.toString());
                em.getTransaction().rollback();
                // utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        System.out.println("vehicle destroyed with id: \t" + id);
    }

    public List<Vehicle> findVehicleEntities() {
        return findVehicleEntities(true, -1, -1);
    }

    public List<Vehicle> findVehicleEntities(int maxResults, int firstResult) {
        return findVehicleEntities(false, maxResults, firstResult);
    }

    private List<Vehicle> findVehicleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vehicle.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Vehicle findVehicle(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vehicle.class, id);
        } finally {
            em.close();
        }
    }

    public int getVehicleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vehicle> rt = cq.from(Vehicle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
