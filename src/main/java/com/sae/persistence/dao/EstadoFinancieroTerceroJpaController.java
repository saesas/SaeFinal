/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.EstadoFinanciero;
import com.sae.persistence.domain.EstadoFinancieroTercero;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class EstadoFinancieroTerceroJpaController implements Serializable {

    public EstadoFinancieroTerceroJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoFinancieroTercero estadoFinancieroTercero) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoFinanciero idTipo = estadoFinancieroTercero.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getId());
                estadoFinancieroTercero.setIdTipo(idTipo);
            }
            em.persist(estadoFinancieroTercero);
            if (idTipo != null) {
                idTipo.getEstadoFinancieroTerceroList().add(estadoFinancieroTercero);
                idTipo = em.merge(idTipo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadoFinancieroTercero(estadoFinancieroTercero.getId()) != null) {
                throw new PreexistingEntityException("EstadoFinancieroTercero " + estadoFinancieroTercero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoFinancieroTercero estadoFinancieroTercero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoFinancieroTercero persistentEstadoFinancieroTercero = em.find(EstadoFinancieroTercero.class, estadoFinancieroTercero.getId());
            EstadoFinanciero idTipoOld = persistentEstadoFinancieroTercero.getIdTipo();
            EstadoFinanciero idTipoNew = estadoFinancieroTercero.getIdTipo();
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getId());
                estadoFinancieroTercero.setIdTipo(idTipoNew);
            }
            estadoFinancieroTercero = em.merge(estadoFinancieroTercero);
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getEstadoFinancieroTerceroList().remove(estadoFinancieroTercero);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getEstadoFinancieroTerceroList().add(estadoFinancieroTercero);
                idTipoNew = em.merge(idTipoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estadoFinancieroTercero.getId();
                if (findEstadoFinancieroTercero(id) == null) {
                    throw new NonexistentEntityException("The estadoFinancieroTercero with id " + id + " no longer exists.");
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
            EstadoFinancieroTercero estadoFinancieroTercero;
            try {
                estadoFinancieroTercero = em.getReference(EstadoFinancieroTercero.class, id);
                estadoFinancieroTercero.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoFinancieroTercero with id " + id + " no longer exists.", enfe);
            }
            EstadoFinanciero idTipo = estadoFinancieroTercero.getIdTipo();
            if (idTipo != null) {
                idTipo.getEstadoFinancieroTerceroList().remove(estadoFinancieroTercero);
                idTipo = em.merge(idTipo);
            }
            em.remove(estadoFinancieroTercero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoFinancieroTercero> findEstadoFinancieroTerceroEntities() {
        return findEstadoFinancieroTerceroEntities(true, -1, -1);
    }

    public List<EstadoFinancieroTercero> findEstadoFinancieroTerceroEntities(int maxResults, int firstResult) {
        return findEstadoFinancieroTerceroEntities(false, maxResults, firstResult);
    }

    private List<EstadoFinancieroTercero> findEstadoFinancieroTerceroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoFinancieroTercero.class));
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

    public EstadoFinancieroTercero findEstadoFinancieroTercero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoFinancieroTercero.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoFinancieroTerceroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoFinancieroTercero> rt = cq.from(EstadoFinancieroTercero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
