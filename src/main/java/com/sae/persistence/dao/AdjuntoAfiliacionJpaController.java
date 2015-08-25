/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.AdjuntoAfiliacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Afiliacion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class AdjuntoAfiliacionJpaController implements Serializable {

    public AdjuntoAfiliacionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AdjuntoAfiliacion adjuntoAfiliacion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Afiliacion idAfiliacion = adjuntoAfiliacion.getIdAfiliacion();
            if (idAfiliacion != null) {
                idAfiliacion = em.getReference(idAfiliacion.getClass(), idAfiliacion.getId());
                adjuntoAfiliacion.setIdAfiliacion(idAfiliacion);
            }
            em.persist(adjuntoAfiliacion);
            if (idAfiliacion != null) {
                idAfiliacion.getAdjuntoAfiliacionList().add(adjuntoAfiliacion);
                idAfiliacion = em.merge(idAfiliacion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAdjuntoAfiliacion(adjuntoAfiliacion.getId()) != null) {
                throw new PreexistingEntityException("AdjuntoAfiliacion " + adjuntoAfiliacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AdjuntoAfiliacion adjuntoAfiliacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AdjuntoAfiliacion persistentAdjuntoAfiliacion = em.find(AdjuntoAfiliacion.class, adjuntoAfiliacion.getId());
            Afiliacion idAfiliacionOld = persistentAdjuntoAfiliacion.getIdAfiliacion();
            Afiliacion idAfiliacionNew = adjuntoAfiliacion.getIdAfiliacion();
            if (idAfiliacionNew != null) {
                idAfiliacionNew = em.getReference(idAfiliacionNew.getClass(), idAfiliacionNew.getId());
                adjuntoAfiliacion.setIdAfiliacion(idAfiliacionNew);
            }
            adjuntoAfiliacion = em.merge(adjuntoAfiliacion);
            if (idAfiliacionOld != null && !idAfiliacionOld.equals(idAfiliacionNew)) {
                idAfiliacionOld.getAdjuntoAfiliacionList().remove(adjuntoAfiliacion);
                idAfiliacionOld = em.merge(idAfiliacionOld);
            }
            if (idAfiliacionNew != null && !idAfiliacionNew.equals(idAfiliacionOld)) {
                idAfiliacionNew.getAdjuntoAfiliacionList().add(adjuntoAfiliacion);
                idAfiliacionNew = em.merge(idAfiliacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = adjuntoAfiliacion.getId();
                if (findAdjuntoAfiliacion(id) == null) {
                    throw new NonexistentEntityException("The adjuntoAfiliacion with id " + id + " no longer exists.");
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
            AdjuntoAfiliacion adjuntoAfiliacion;
            try {
                adjuntoAfiliacion = em.getReference(AdjuntoAfiliacion.class, id);
                adjuntoAfiliacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The adjuntoAfiliacion with id " + id + " no longer exists.", enfe);
            }
            Afiliacion idAfiliacion = adjuntoAfiliacion.getIdAfiliacion();
            if (idAfiliacion != null) {
                idAfiliacion.getAdjuntoAfiliacionList().remove(adjuntoAfiliacion);
                idAfiliacion = em.merge(idAfiliacion);
            }
            em.remove(adjuntoAfiliacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AdjuntoAfiliacion> findAdjuntoAfiliacionEntities() {
        return findAdjuntoAfiliacionEntities(true, -1, -1);
    }

    public List<AdjuntoAfiliacion> findAdjuntoAfiliacionEntities(int maxResults, int firstResult) {
        return findAdjuntoAfiliacionEntities(false, maxResults, firstResult);
    }

    private List<AdjuntoAfiliacion> findAdjuntoAfiliacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AdjuntoAfiliacion.class));
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

    public AdjuntoAfiliacion findAdjuntoAfiliacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AdjuntoAfiliacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdjuntoAfiliacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AdjuntoAfiliacion> rt = cq.from(AdjuntoAfiliacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
