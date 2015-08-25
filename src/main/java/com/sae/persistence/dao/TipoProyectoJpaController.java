/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.TipoProyecto;
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
public class TipoProyectoJpaController implements Serializable {

    public TipoProyectoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoProyecto tipoProyecto) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoProyecto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoProyecto(tipoProyecto.getId()) != null) {
                throw new PreexistingEntityException("TipoProyecto " + tipoProyecto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoProyecto tipoProyecto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tipoProyecto = em.merge(tipoProyecto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoProyecto.getId();
                if (findTipoProyecto(id) == null) {
                    throw new NonexistentEntityException("The tipoProyecto with id " + id + " no longer exists.");
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
            TipoProyecto tipoProyecto;
            try {
                tipoProyecto = em.getReference(TipoProyecto.class, id);
                tipoProyecto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoProyecto with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoProyecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoProyecto> findTipoProyectoEntities() {
        return findTipoProyectoEntities(true, -1, -1);
    }

    public List<TipoProyecto> findTipoProyectoEntities(int maxResults, int firstResult) {
        return findTipoProyectoEntities(false, maxResults, firstResult);
    }

    private List<TipoProyecto> findTipoProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoProyecto.class));
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

    public TipoProyecto findTipoProyecto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoProyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoProyecto> rt = cq.from(TipoProyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
