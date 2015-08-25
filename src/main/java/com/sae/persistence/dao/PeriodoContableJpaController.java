/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.PeriodoContable;
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
public class PeriodoContableJpaController implements Serializable {

    public PeriodoContableJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PeriodoContable periodoContable) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(periodoContable);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPeriodoContable(periodoContable.getId()) != null) {
                throw new PreexistingEntityException("PeriodoContable " + periodoContable + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PeriodoContable periodoContable) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            periodoContable = em.merge(periodoContable);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = periodoContable.getId();
                if (findPeriodoContable(id) == null) {
                    throw new NonexistentEntityException("The periodoContable with id " + id + " no longer exists.");
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
            PeriodoContable periodoContable;
            try {
                periodoContable = em.getReference(PeriodoContable.class, id);
                periodoContable.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The periodoContable with id " + id + " no longer exists.", enfe);
            }
            em.remove(periodoContable);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PeriodoContable> findPeriodoContableEntities() {
        return findPeriodoContableEntities(true, -1, -1);
    }

    public List<PeriodoContable> findPeriodoContableEntities(int maxResults, int firstResult) {
        return findPeriodoContableEntities(false, maxResults, firstResult);
    }

    private List<PeriodoContable> findPeriodoContableEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PeriodoContable.class));
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

    public PeriodoContable findPeriodoContable(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PeriodoContable.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeriodoContableCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PeriodoContable> rt = cq.from(PeriodoContable.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
