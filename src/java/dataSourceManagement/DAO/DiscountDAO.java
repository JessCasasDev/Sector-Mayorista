/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.DAO;

import dataSourceManagement.DAO.exceptions.NonexistentEntityException;
import dataSourceManagement.DAO.exceptions.RollbackFailureException;
import dataSourceManagement.entities.Discount;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dataSourceManagement.entities.ShopOrder;
import dataSourceManagement.entities.Vehicle;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author JuanCamilo
 */
public class DiscountDAO implements Serializable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("autoMarketPU");

    public void create(Discount discount) throws RollbackFailureException, Exception {
        EntityManager em = emf.createEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            ShopOrder shopOrderOrderId = discount.getShopOrderOrderId();
            if (shopOrderOrderId != null) {
                shopOrderOrderId = em.getReference(shopOrderOrderId.getClass(), shopOrderOrderId.getOrderId());
                discount.setShopOrderOrderId(shopOrderOrderId);
            }
            Vehicle vehicleId = discount.getVehicleId();
            if (vehicleId != null) {
                vehicleId = em.getReference(vehicleId.getClass(), vehicleId.getVehicleId());
                discount.setVehicleId(vehicleId);
            }
            em.persist(discount);
            if (shopOrderOrderId != null) {
                shopOrderOrderId.getDiscountCollection().add(discount);
                shopOrderOrderId = em.merge(shopOrderOrderId);
            }
            if (vehicleId != null) {
                vehicleId.getDiscountCollection().add(discount);
                vehicleId = em.merge(vehicleId);
            }
            utx.commit();
            System.out.println("discount added: " + discount.getLabel());
        } catch (Exception ex) {
            System.out.println(ex.toString());
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Discount discount) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = emf.createEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            Discount persistentDiscount = em.find(Discount.class, discount.getDiscountId());
            ShopOrder shopOrderOrderIdOld = persistentDiscount.getShopOrderOrderId();
            ShopOrder shopOrderOrderIdNew = discount.getShopOrderOrderId();
            Vehicle vehicleIdOld = persistentDiscount.getVehicleId();
            Vehicle vehicleIdNew = discount.getVehicleId();
            if (shopOrderOrderIdNew != null) {
                shopOrderOrderIdNew = em.getReference(shopOrderOrderIdNew.getClass(), shopOrderOrderIdNew.getOrderId());
                discount.setShopOrderOrderId(shopOrderOrderIdNew);
            }
            if (vehicleIdNew != null) {
                vehicleIdNew = em.getReference(vehicleIdNew.getClass(), vehicleIdNew.getVehicleId());
                discount.setVehicleId(vehicleIdNew);
            }
            discount = em.merge(discount);
            if (shopOrderOrderIdOld != null && !shopOrderOrderIdOld.equals(shopOrderOrderIdNew)) {
                shopOrderOrderIdOld.getDiscountCollection().remove(discount);
                shopOrderOrderIdOld = em.merge(shopOrderOrderIdOld);
            }
            if (shopOrderOrderIdNew != null && !shopOrderOrderIdNew.equals(shopOrderOrderIdOld)) {
                shopOrderOrderIdNew.getDiscountCollection().add(discount);
                shopOrderOrderIdNew = em.merge(shopOrderOrderIdNew);
            }
            if (vehicleIdOld != null && !vehicleIdOld.equals(vehicleIdNew)) {
                vehicleIdOld.getDiscountCollection().remove(discount);
                vehicleIdOld = em.merge(vehicleIdOld);
            }
            if (vehicleIdNew != null && !vehicleIdNew.equals(vehicleIdOld)) {
                vehicleIdNew.getDiscountCollection().add(discount);
                vehicleIdNew = em.merge(vehicleIdNew);
            }
            utx.commit();
            System.out.println("discount edited: " + discount.getLabel());
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = discount.getDiscountId();
                if (findDiscount(id) == null) {
                    throw new NonexistentEntityException("The discount with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = emf.createEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            Discount discount;
            try {
                discount = em.getReference(Discount.class, id);
                discount.getDiscountId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The discount with id " + id + " no longer exists.", enfe);
            }
            ShopOrder shopOrderOrderId = discount.getShopOrderOrderId();
            if (shopOrderOrderId != null) {
                shopOrderOrderId.getDiscountCollection().remove(discount);
                shopOrderOrderId = em.merge(shopOrderOrderId);
            }
            Vehicle vehicleId = discount.getVehicleId();
            if (vehicleId != null) {
                vehicleId.getDiscountCollection().remove(discount);
                vehicleId = em.merge(vehicleId);
            }
            em.remove(discount);
            utx.commit();
            System.out.println("discount destroyed: " + discount.getLabel());
        } catch (Exception ex) {
            System.out.println(ex);
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Discount> findDiscountEntities() {
        return findDiscountEntities(true, -1, -1);
    }

    public List<Discount> findDiscountEntities(int maxResults, int firstResult) {
        return findDiscountEntities(false, maxResults, firstResult);
    }

    private List<Discount> findDiscountEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Discount.class));
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

    public Discount findDiscount(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Discount.class, id);
        } finally {
            em.close();
        }
    }

    public int getDiscountCount() {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Discount> rt = cq.from(Discount.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Collection<Discount> searchGroupByVehicleId(Vehicle vehicleId){
        EntityManager em = emf.createEntityManager();
        Collection<Discount> orderCollection = null;
        Query q = em.createNamedQuery("Discount.findByVehicleId");
        q.setParameter("vehicleId", vehicleId);
        try {
            orderCollection = q.getResultList();
        } catch (Exception e){
        } finally {
            em.close();
        }
        return orderCollection;
    }

}
