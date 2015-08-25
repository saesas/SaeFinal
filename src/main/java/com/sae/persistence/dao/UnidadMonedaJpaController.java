/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.UnidadMoneda;
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
public class UnidadMonedaJpaController implements Serializable {

    public UnidadMonedaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UnidadMoneda unidadMoneda) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(unidadMoneda);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUnidadMoneda(unidadMoneda.getId()) != null) {
                throw new PreexistingEntityException("UnidadMoneda " + unidadMoneda + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UnidadMoneda unidadMoneda) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            unidadMoneda = em.merge(unidadMoneda);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = unidadMoneda.getId();
                if (findUnidadMoneda(id) == null) {
                    throw new NonexistentEntityException("The unidadMoneda with id " + id + " no longer exists.");
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
            UnidadMoneda unidadMoneda;
            try {
                unidadMoneda = em.getReference(UnidadMoneda.class, id);
                unidadMoneda.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The unidadMoneda with id " + id + " no longer exists.", enfe);
            }
            em.remove(unidadMoneda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UnidadMoneda> findUnidadMonedaEntities() {
        return findUnidadMonedaEntities(true, -1, -1);
    }

    public List<UnidadMoneda> findUnidadMonedaEntities(int maxResults, int firstResult) {
        return findUnidadMonedaEntities(false, maxResults, firstResult);
    }

    private List<UnidadMoneda> findUnidadMonedaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UnidadMoneda.class));
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

    public UnidadMoneda findUnidadMoneda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UnidadMoneda.class, id);
        } finally {
            em.close();
        }
    }

    public int getUnidadMonedaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UnidadMoneda> rt = cq.from(UnidadMoneda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
