/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.PagoFacturaVenta;
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
public class PagoFacturaVentaJpaController implements Serializable {

    public PagoFacturaVentaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PagoFacturaVenta pagoFacturaVenta) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(pagoFacturaVenta);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPagoFacturaVenta(pagoFacturaVenta.getId()) != null) {
                throw new PreexistingEntityException("PagoFacturaVenta " + pagoFacturaVenta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PagoFacturaVenta pagoFacturaVenta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            pagoFacturaVenta = em.merge(pagoFacturaVenta);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagoFacturaVenta.getId();
                if (findPagoFacturaVenta(id) == null) {
                    throw new NonexistentEntityException("The pagoFacturaVenta with id " + id + " no longer exists.");
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
            PagoFacturaVenta pagoFacturaVenta;
            try {
                pagoFacturaVenta = em.getReference(PagoFacturaVenta.class, id);
                pagoFacturaVenta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagoFacturaVenta with id " + id + " no longer exists.", enfe);
            }
            em.remove(pagoFacturaVenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PagoFacturaVenta> findPagoFacturaVentaEntities() {
        return findPagoFacturaVentaEntities(true, -1, -1);
    }

    public List<PagoFacturaVenta> findPagoFacturaVentaEntities(int maxResults, int firstResult) {
        return findPagoFacturaVentaEntities(false, maxResults, firstResult);
    }

    private List<PagoFacturaVenta> findPagoFacturaVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PagoFacturaVenta.class));
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

    public PagoFacturaVenta findPagoFacturaVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PagoFacturaVenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoFacturaVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PagoFacturaVenta> rt = cq.from(PagoFacturaVenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
