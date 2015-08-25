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
import com.sae.persistence.domain.TipoEstadoAudiencia;
import com.sae.persistence.domain.Audiencia;
import com.sae.persistence.domain.EstadoAudiencia;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class EstadoAudienciaJpaController implements Serializable {

    public EstadoAudienciaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoAudiencia estadoAudiencia) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEstadoAudiencia idEstado = estadoAudiencia.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getId());
                estadoAudiencia.setIdEstado(idEstado);
            }
            Audiencia idAudiencia = estadoAudiencia.getIdAudiencia();
            if (idAudiencia != null) {
                idAudiencia = em.getReference(idAudiencia.getClass(), idAudiencia.getId());
                estadoAudiencia.setIdAudiencia(idAudiencia);
            }
            em.persist(estadoAudiencia);
            if (idEstado != null) {
                idEstado.getEstadoAudienciaList().add(estadoAudiencia);
                idEstado = em.merge(idEstado);
            }
            if (idAudiencia != null) {
                idAudiencia.getEstadoAudienciaList().add(estadoAudiencia);
                idAudiencia = em.merge(idAudiencia);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadoAudiencia(estadoAudiencia.getId()) != null) {
                throw new PreexistingEntityException("EstadoAudiencia " + estadoAudiencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoAudiencia estadoAudiencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoAudiencia persistentEstadoAudiencia = em.find(EstadoAudiencia.class, estadoAudiencia.getId());
            TipoEstadoAudiencia idEstadoOld = persistentEstadoAudiencia.getIdEstado();
            TipoEstadoAudiencia idEstadoNew = estadoAudiencia.getIdEstado();
            Audiencia idAudienciaOld = persistentEstadoAudiencia.getIdAudiencia();
            Audiencia idAudienciaNew = estadoAudiencia.getIdAudiencia();
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getId());
                estadoAudiencia.setIdEstado(idEstadoNew);
            }
            if (idAudienciaNew != null) {
                idAudienciaNew = em.getReference(idAudienciaNew.getClass(), idAudienciaNew.getId());
                estadoAudiencia.setIdAudiencia(idAudienciaNew);
            }
            estadoAudiencia = em.merge(estadoAudiencia);
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getEstadoAudienciaList().remove(estadoAudiencia);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getEstadoAudienciaList().add(estadoAudiencia);
                idEstadoNew = em.merge(idEstadoNew);
            }
            if (idAudienciaOld != null && !idAudienciaOld.equals(idAudienciaNew)) {
                idAudienciaOld.getEstadoAudienciaList().remove(estadoAudiencia);
                idAudienciaOld = em.merge(idAudienciaOld);
            }
            if (idAudienciaNew != null && !idAudienciaNew.equals(idAudienciaOld)) {
                idAudienciaNew.getEstadoAudienciaList().add(estadoAudiencia);
                idAudienciaNew = em.merge(idAudienciaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estadoAudiencia.getId();
                if (findEstadoAudiencia(id) == null) {
                    throw new NonexistentEntityException("The estadoAudiencia with id " + id + " no longer exists.");
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
            EstadoAudiencia estadoAudiencia;
            try {
                estadoAudiencia = em.getReference(EstadoAudiencia.class, id);
                estadoAudiencia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoAudiencia with id " + id + " no longer exists.", enfe);
            }
            TipoEstadoAudiencia idEstado = estadoAudiencia.getIdEstado();
            if (idEstado != null) {
                idEstado.getEstadoAudienciaList().remove(estadoAudiencia);
                idEstado = em.merge(idEstado);
            }
            Audiencia idAudiencia = estadoAudiencia.getIdAudiencia();
            if (idAudiencia != null) {
                idAudiencia.getEstadoAudienciaList().remove(estadoAudiencia);
                idAudiencia = em.merge(idAudiencia);
            }
            em.remove(estadoAudiencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoAudiencia> findEstadoAudienciaEntities() {
        return findEstadoAudienciaEntities(true, -1, -1);
    }

    public List<EstadoAudiencia> findEstadoAudienciaEntities(int maxResults, int firstResult) {
        return findEstadoAudienciaEntities(false, maxResults, firstResult);
    }

    private List<EstadoAudiencia> findEstadoAudienciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoAudiencia.class));
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

    public EstadoAudiencia findEstadoAudiencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoAudiencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoAudienciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoAudiencia> rt = cq.from(EstadoAudiencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
