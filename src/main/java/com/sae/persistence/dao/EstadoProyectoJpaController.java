/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.EstadoProyecto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.TipoEstadoProyecto;
import com.sae.persistence.domain.Proyecto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class EstadoProyectoJpaController implements Serializable {

    public EstadoProyectoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoProyecto estadoProyecto) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEstadoProyecto idEstado = estadoProyecto.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getId());
                estadoProyecto.setIdEstado(idEstado);
            }
            Proyecto idProyecto = estadoProyecto.getIdProyecto();
            if (idProyecto != null) {
                idProyecto = em.getReference(idProyecto.getClass(), idProyecto.getId());
                estadoProyecto.setIdProyecto(idProyecto);
            }
            em.persist(estadoProyecto);
            if (idEstado != null) {
                idEstado.getEstadoProyectoList().add(estadoProyecto);
                idEstado = em.merge(idEstado);
            }
            if (idProyecto != null) {
                idProyecto.getEstadoProyectoList().add(estadoProyecto);
                idProyecto = em.merge(idProyecto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadoProyecto(estadoProyecto.getId()) != null) {
                throw new PreexistingEntityException("EstadoProyecto " + estadoProyecto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoProyecto estadoProyecto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoProyecto persistentEstadoProyecto = em.find(EstadoProyecto.class, estadoProyecto.getId());
            TipoEstadoProyecto idEstadoOld = persistentEstadoProyecto.getIdEstado();
            TipoEstadoProyecto idEstadoNew = estadoProyecto.getIdEstado();
            Proyecto idProyectoOld = persistentEstadoProyecto.getIdProyecto();
            Proyecto idProyectoNew = estadoProyecto.getIdProyecto();
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getId());
                estadoProyecto.setIdEstado(idEstadoNew);
            }
            if (idProyectoNew != null) {
                idProyectoNew = em.getReference(idProyectoNew.getClass(), idProyectoNew.getId());
                estadoProyecto.setIdProyecto(idProyectoNew);
            }
            estadoProyecto = em.merge(estadoProyecto);
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getEstadoProyectoList().remove(estadoProyecto);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getEstadoProyectoList().add(estadoProyecto);
                idEstadoNew = em.merge(idEstadoNew);
            }
            if (idProyectoOld != null && !idProyectoOld.equals(idProyectoNew)) {
                idProyectoOld.getEstadoProyectoList().remove(estadoProyecto);
                idProyectoOld = em.merge(idProyectoOld);
            }
            if (idProyectoNew != null && !idProyectoNew.equals(idProyectoOld)) {
                idProyectoNew.getEstadoProyectoList().add(estadoProyecto);
                idProyectoNew = em.merge(idProyectoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estadoProyecto.getId();
                if (findEstadoProyecto(id) == null) {
                    throw new NonexistentEntityException("The estadoProyecto with id " + id + " no longer exists.");
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
            EstadoProyecto estadoProyecto;
            try {
                estadoProyecto = em.getReference(EstadoProyecto.class, id);
                estadoProyecto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoProyecto with id " + id + " no longer exists.", enfe);
            }
            TipoEstadoProyecto idEstado = estadoProyecto.getIdEstado();
            if (idEstado != null) {
                idEstado.getEstadoProyectoList().remove(estadoProyecto);
                idEstado = em.merge(idEstado);
            }
            Proyecto idProyecto = estadoProyecto.getIdProyecto();
            if (idProyecto != null) {
                idProyecto.getEstadoProyectoList().remove(estadoProyecto);
                idProyecto = em.merge(idProyecto);
            }
            em.remove(estadoProyecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoProyecto> findEstadoProyectoEntities() {
        return findEstadoProyectoEntities(true, -1, -1);
    }

    public List<EstadoProyecto> findEstadoProyectoEntities(int maxResults, int firstResult) {
        return findEstadoProyectoEntities(false, maxResults, firstResult);
    }

    private List<EstadoProyecto> findEstadoProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoProyecto.class));
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

    public EstadoProyecto findEstadoProyecto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoProyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoProyecto> rt = cq.from(EstadoProyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
