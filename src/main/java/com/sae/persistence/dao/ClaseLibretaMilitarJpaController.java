/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.ClaseLibretaMilitar;
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
public class ClaseLibretaMilitarJpaController implements Serializable {

    public ClaseLibretaMilitarJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClaseLibretaMilitar claseLibretaMilitar) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(claseLibretaMilitar);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClaseLibretaMilitar(claseLibretaMilitar.getId()) != null) {
                throw new PreexistingEntityException("ClaseLibretaMilitar " + claseLibretaMilitar + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClaseLibretaMilitar claseLibretaMilitar) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            claseLibretaMilitar = em.merge(claseLibretaMilitar);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = claseLibretaMilitar.getId();
                if (findClaseLibretaMilitar(id) == null) {
                    throw new NonexistentEntityException("The claseLibretaMilitar with id " + id + " no longer exists.");
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
            ClaseLibretaMilitar claseLibretaMilitar;
            try {
                claseLibretaMilitar = em.getReference(ClaseLibretaMilitar.class, id);
                claseLibretaMilitar.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The claseLibretaMilitar with id " + id + " no longer exists.", enfe);
            }
            em.remove(claseLibretaMilitar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClaseLibretaMilitar> findClaseLibretaMilitarEntities() {
        return findClaseLibretaMilitarEntities(true, -1, -1);
    }

    public List<ClaseLibretaMilitar> findClaseLibretaMilitarEntities(int maxResults, int firstResult) {
        return findClaseLibretaMilitarEntities(false, maxResults, firstResult);
    }

    private List<ClaseLibretaMilitar> findClaseLibretaMilitarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClaseLibretaMilitar.class));
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

    public ClaseLibretaMilitar findClaseLibretaMilitar(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClaseLibretaMilitar.class, id);
        } finally {
            em.close();
        }
    }

    public int getClaseLibretaMilitarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClaseLibretaMilitar> rt = cq.from(ClaseLibretaMilitar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
