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
import com.sae.persistence.domain.TipoEstadoContrato;
import com.sae.persistence.domain.ContratoProyectoProveedor;
import com.sae.persistence.domain.EstadoContrato;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class EstadoContratoJpaController implements Serializable {

    public EstadoContratoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoContrato estadoContrato) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEstadoContrato idEstado = estadoContrato.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getId());
                estadoContrato.setIdEstado(idEstado);
            }
            ContratoProyectoProveedor idContrato = estadoContrato.getIdContrato();
            if (idContrato != null) {
                idContrato = em.getReference(idContrato.getClass(), idContrato.getId());
                estadoContrato.setIdContrato(idContrato);
            }
            em.persist(estadoContrato);
            if (idEstado != null) {
                idEstado.getEstadoContratoList().add(estadoContrato);
                idEstado = em.merge(idEstado);
            }
            if (idContrato != null) {
                idContrato.getEstadoContratoList().add(estadoContrato);
                idContrato = em.merge(idContrato);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadoContrato(estadoContrato.getId()) != null) {
                throw new PreexistingEntityException("EstadoContrato " + estadoContrato + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoContrato estadoContrato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoContrato persistentEstadoContrato = em.find(EstadoContrato.class, estadoContrato.getId());
            TipoEstadoContrato idEstadoOld = persistentEstadoContrato.getIdEstado();
            TipoEstadoContrato idEstadoNew = estadoContrato.getIdEstado();
            ContratoProyectoProveedor idContratoOld = persistentEstadoContrato.getIdContrato();
            ContratoProyectoProveedor idContratoNew = estadoContrato.getIdContrato();
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getId());
                estadoContrato.setIdEstado(idEstadoNew);
            }
            if (idContratoNew != null) {
                idContratoNew = em.getReference(idContratoNew.getClass(), idContratoNew.getId());
                estadoContrato.setIdContrato(idContratoNew);
            }
            estadoContrato = em.merge(estadoContrato);
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getEstadoContratoList().remove(estadoContrato);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getEstadoContratoList().add(estadoContrato);
                idEstadoNew = em.merge(idEstadoNew);
            }
            if (idContratoOld != null && !idContratoOld.equals(idContratoNew)) {
                idContratoOld.getEstadoContratoList().remove(estadoContrato);
                idContratoOld = em.merge(idContratoOld);
            }
            if (idContratoNew != null && !idContratoNew.equals(idContratoOld)) {
                idContratoNew.getEstadoContratoList().add(estadoContrato);
                idContratoNew = em.merge(idContratoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estadoContrato.getId();
                if (findEstadoContrato(id) == null) {
                    throw new NonexistentEntityException("The estadoContrato with id " + id + " no longer exists.");
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
            EstadoContrato estadoContrato;
            try {
                estadoContrato = em.getReference(EstadoContrato.class, id);
                estadoContrato.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoContrato with id " + id + " no longer exists.", enfe);
            }
            TipoEstadoContrato idEstado = estadoContrato.getIdEstado();
            if (idEstado != null) {
                idEstado.getEstadoContratoList().remove(estadoContrato);
                idEstado = em.merge(idEstado);
            }
            ContratoProyectoProveedor idContrato = estadoContrato.getIdContrato();
            if (idContrato != null) {
                idContrato.getEstadoContratoList().remove(estadoContrato);
                idContrato = em.merge(idContrato);
            }
            em.remove(estadoContrato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoContrato> findEstadoContratoEntities() {
        return findEstadoContratoEntities(true, -1, -1);
    }

    public List<EstadoContrato> findEstadoContratoEntities(int maxResults, int firstResult) {
        return findEstadoContratoEntities(false, maxResults, firstResult);
    }

    private List<EstadoContrato> findEstadoContratoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoContrato.class));
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

    public EstadoContrato findEstadoContrato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoContrato.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoContratoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoContrato> rt = cq.from(EstadoContrato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
