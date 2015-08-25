/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.HistorialSaldoTercero;
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
public class HistorialSaldoTerceroJpaController implements Serializable {

    public HistorialSaldoTerceroJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HistorialSaldoTercero historialSaldoTercero) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(historialSaldoTercero);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHistorialSaldoTercero(historialSaldoTercero.getId()) != null) {
                throw new PreexistingEntityException("HistorialSaldoTercero " + historialSaldoTercero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HistorialSaldoTercero historialSaldoTercero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            historialSaldoTercero = em.merge(historialSaldoTercero);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = historialSaldoTercero.getId();
                if (findHistorialSaldoTercero(id) == null) {
                    throw new NonexistentEntityException("The historialSaldoTercero with id " + id + " no longer exists.");
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
            HistorialSaldoTercero historialSaldoTercero;
            try {
                historialSaldoTercero = em.getReference(HistorialSaldoTercero.class, id);
                historialSaldoTercero.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historialSaldoTercero with id " + id + " no longer exists.", enfe);
            }
            em.remove(historialSaldoTercero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HistorialSaldoTercero> findHistorialSaldoTerceroEntities() {
        return findHistorialSaldoTerceroEntities(true, -1, -1);
    }

    public List<HistorialSaldoTercero> findHistorialSaldoTerceroEntities(int maxResults, int firstResult) {
        return findHistorialSaldoTerceroEntities(false, maxResults, firstResult);
    }

    private List<HistorialSaldoTercero> findHistorialSaldoTerceroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistorialSaldoTercero.class));
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

    public HistorialSaldoTercero findHistorialSaldoTercero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistorialSaldoTercero.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistorialSaldoTerceroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistorialSaldoTercero> rt = cq.from(HistorialSaldoTercero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
