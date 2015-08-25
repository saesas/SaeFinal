/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.TipoReintegro;
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
public class TipoReintegroJpaController implements Serializable {

    public TipoReintegroJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoReintegro tipoReintegro) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoReintegro);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoReintegro(tipoReintegro.getId()) != null) {
                throw new PreexistingEntityException("TipoReintegro " + tipoReintegro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoReintegro tipoReintegro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tipoReintegro = em.merge(tipoReintegro);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoReintegro.getId();
                if (findTipoReintegro(id) == null) {
                    throw new NonexistentEntityException("The tipoReintegro with id " + id + " no longer exists.");
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
            TipoReintegro tipoReintegro;
            try {
                tipoReintegro = em.getReference(TipoReintegro.class, id);
                tipoReintegro.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoReintegro with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoReintegro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoReintegro> findTipoReintegroEntities() {
        return findTipoReintegroEntities(true, -1, -1);
    }

    public List<TipoReintegro> findTipoReintegroEntities(int maxResults, int firstResult) {
        return findTipoReintegroEntities(false, maxResults, firstResult);
    }

    private List<TipoReintegro> findTipoReintegroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoReintegro.class));
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

    public TipoReintegro findTipoReintegro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoReintegro.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoReintegroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoReintegro> rt = cq.from(TipoReintegro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
