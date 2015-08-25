/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.AdjuntoPropuesta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Propuesta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class AdjuntoPropuestaJpaController implements Serializable {

    public AdjuntoPropuestaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AdjuntoPropuesta adjuntoPropuesta) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Propuesta idPropuesta = adjuntoPropuesta.getIdPropuesta();
            if (idPropuesta != null) {
                idPropuesta = em.getReference(idPropuesta.getClass(), idPropuesta.getId());
                adjuntoPropuesta.setIdPropuesta(idPropuesta);
            }
            em.persist(adjuntoPropuesta);
            if (idPropuesta != null) {
                idPropuesta.getAdjuntoPropuestaList().add(adjuntoPropuesta);
                idPropuesta = em.merge(idPropuesta);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAdjuntoPropuesta(adjuntoPropuesta.getId()) != null) {
                throw new PreexistingEntityException("AdjuntoPropuesta " + adjuntoPropuesta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AdjuntoPropuesta adjuntoPropuesta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AdjuntoPropuesta persistentAdjuntoPropuesta = em.find(AdjuntoPropuesta.class, adjuntoPropuesta.getId());
            Propuesta idPropuestaOld = persistentAdjuntoPropuesta.getIdPropuesta();
            Propuesta idPropuestaNew = adjuntoPropuesta.getIdPropuesta();
            if (idPropuestaNew != null) {
                idPropuestaNew = em.getReference(idPropuestaNew.getClass(), idPropuestaNew.getId());
                adjuntoPropuesta.setIdPropuesta(idPropuestaNew);
            }
            adjuntoPropuesta = em.merge(adjuntoPropuesta);
            if (idPropuestaOld != null && !idPropuestaOld.equals(idPropuestaNew)) {
                idPropuestaOld.getAdjuntoPropuestaList().remove(adjuntoPropuesta);
                idPropuestaOld = em.merge(idPropuestaOld);
            }
            if (idPropuestaNew != null && !idPropuestaNew.equals(idPropuestaOld)) {
                idPropuestaNew.getAdjuntoPropuestaList().add(adjuntoPropuesta);
                idPropuestaNew = em.merge(idPropuestaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = adjuntoPropuesta.getId();
                if (findAdjuntoPropuesta(id) == null) {
                    throw new NonexistentEntityException("The adjuntoPropuesta with id " + id + " no longer exists.");
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
            AdjuntoPropuesta adjuntoPropuesta;
            try {
                adjuntoPropuesta = em.getReference(AdjuntoPropuesta.class, id);
                adjuntoPropuesta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The adjuntoPropuesta with id " + id + " no longer exists.", enfe);
            }
            Propuesta idPropuesta = adjuntoPropuesta.getIdPropuesta();
            if (idPropuesta != null) {
                idPropuesta.getAdjuntoPropuestaList().remove(adjuntoPropuesta);
                idPropuesta = em.merge(idPropuesta);
            }
            em.remove(adjuntoPropuesta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AdjuntoPropuesta> findAdjuntoPropuestaEntities() {
        return findAdjuntoPropuestaEntities(true, -1, -1);
    }

    public List<AdjuntoPropuesta> findAdjuntoPropuestaEntities(int maxResults, int firstResult) {
        return findAdjuntoPropuestaEntities(false, maxResults, firstResult);
    }

    private List<AdjuntoPropuesta> findAdjuntoPropuestaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AdjuntoPropuesta.class));
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

    public AdjuntoPropuesta findAdjuntoPropuesta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AdjuntoPropuesta.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdjuntoPropuestaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AdjuntoPropuesta> rt = cq.from(AdjuntoPropuesta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
