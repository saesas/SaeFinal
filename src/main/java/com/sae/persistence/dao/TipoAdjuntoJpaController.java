/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.TipoAdjunto;
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
public class TipoAdjuntoJpaController implements Serializable {

    public TipoAdjuntoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoAdjunto tipoAdjunto) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoAdjunto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoAdjunto(tipoAdjunto.getId()) != null) {
                throw new PreexistingEntityException("TipoAdjunto " + tipoAdjunto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoAdjunto tipoAdjunto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tipoAdjunto = em.merge(tipoAdjunto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoAdjunto.getId();
                if (findTipoAdjunto(id) == null) {
                    throw new NonexistentEntityException("The tipoAdjunto with id " + id + " no longer exists.");
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
            TipoAdjunto tipoAdjunto;
            try {
                tipoAdjunto = em.getReference(TipoAdjunto.class, id);
                tipoAdjunto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoAdjunto with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoAdjunto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoAdjunto> findTipoAdjuntoEntities() {
        return findTipoAdjuntoEntities(true, -1, -1);
    }

    public List<TipoAdjunto> findTipoAdjuntoEntities(int maxResults, int firstResult) {
        return findTipoAdjuntoEntities(false, maxResults, firstResult);
    }

    private List<TipoAdjunto> findTipoAdjuntoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoAdjunto.class));
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

    public TipoAdjunto findTipoAdjunto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoAdjunto.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoAdjuntoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoAdjunto> rt = cq.from(TipoAdjunto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
