/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.PagoFacturaCompra;
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
public class PagoFacturaCompraJpaController implements Serializable {

    public PagoFacturaCompraJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PagoFacturaCompra pagoFacturaCompra) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(pagoFacturaCompra);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPagoFacturaCompra(pagoFacturaCompra.getId()) != null) {
                throw new PreexistingEntityException("PagoFacturaCompra " + pagoFacturaCompra + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PagoFacturaCompra pagoFacturaCompra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            pagoFacturaCompra = em.merge(pagoFacturaCompra);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagoFacturaCompra.getId();
                if (findPagoFacturaCompra(id) == null) {
                    throw new NonexistentEntityException("The pagoFacturaCompra with id " + id + " no longer exists.");
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
            PagoFacturaCompra pagoFacturaCompra;
            try {
                pagoFacturaCompra = em.getReference(PagoFacturaCompra.class, id);
                pagoFacturaCompra.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagoFacturaCompra with id " + id + " no longer exists.", enfe);
            }
            em.remove(pagoFacturaCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PagoFacturaCompra> findPagoFacturaCompraEntities() {
        return findPagoFacturaCompraEntities(true, -1, -1);
    }

    public List<PagoFacturaCompra> findPagoFacturaCompraEntities(int maxResults, int firstResult) {
        return findPagoFacturaCompraEntities(false, maxResults, firstResult);
    }

    private List<PagoFacturaCompra> findPagoFacturaCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PagoFacturaCompra.class));
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

    public PagoFacturaCompra findPagoFacturaCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PagoFacturaCompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoFacturaCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PagoFacturaCompra> rt = cq.from(PagoFacturaCompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
