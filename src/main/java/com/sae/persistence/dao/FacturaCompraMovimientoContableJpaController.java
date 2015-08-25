/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.FacturaCompraMovimientoContable;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author SAE2
 */
public class FacturaCompraMovimientoContableJpaController implements Serializable {

    public FacturaCompraMovimientoContableJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FacturaCompraMovimientoContable facturaCompraMovimientoContable) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(facturaCompraMovimientoContable);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFacturaCompraMovimientoContable(facturaCompraMovimientoContable.getId()) != null) {
                throw new PreexistingEntityException("FacturaCompraMovimientoContable " + facturaCompraMovimientoContable + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FacturaCompraMovimientoContable facturaCompraMovimientoContable) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            facturaCompraMovimientoContable = em.merge(facturaCompraMovimientoContable);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facturaCompraMovimientoContable.getId();
                if (findFacturaCompraMovimientoContable(id) == null) {
                    throw new NonexistentEntityException("The facturaCompraMovimientoContable with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacturaCompraMovimientoContable facturaCompraMovimientoContable;
            try {
                facturaCompraMovimientoContable = em.getReference(FacturaCompraMovimientoContable.class, id);
                facturaCompraMovimientoContable.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturaCompraMovimientoContable with id " + id + " no longer exists.", enfe);
            }
            em.remove(facturaCompraMovimientoContable);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FacturaCompraMovimientoContable> findFacturaCompraMovimientoContableEntities() {
        return findFacturaCompraMovimientoContableEntities(true, -1, -1);
    }

    public List<FacturaCompraMovimientoContable> findFacturaCompraMovimientoContableEntities(int maxResults, int firstResult) {
        return findFacturaCompraMovimientoContableEntities(false, maxResults, firstResult);
    }

    private List<FacturaCompraMovimientoContable> findFacturaCompraMovimientoContableEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FacturaCompraMovimientoContable.class));
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

    public FacturaCompraMovimientoContable findFacturaCompraMovimientoContable(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FacturaCompraMovimientoContable.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaCompraMovimientoContableCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FacturaCompraMovimientoContable> rt = cq.from(FacturaCompraMovimientoContable.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
