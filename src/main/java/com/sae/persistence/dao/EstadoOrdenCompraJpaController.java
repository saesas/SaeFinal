/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.EstadoOrdenCompra;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.TipoEstadoOrdenCompra;
import com.sae.persistence.domain.OrdenCompra;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class EstadoOrdenCompraJpaController implements Serializable {

    public EstadoOrdenCompraJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoOrdenCompra estadoOrdenCompra) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEstadoOrdenCompra idEstadoOrdenCompra = estadoOrdenCompra.getIdEstadoOrdenCompra();
            if (idEstadoOrdenCompra != null) {
                idEstadoOrdenCompra = em.getReference(idEstadoOrdenCompra.getClass(), idEstadoOrdenCompra.getId());
                estadoOrdenCompra.setIdEstadoOrdenCompra(idEstadoOrdenCompra);
            }
            OrdenCompra idOrdenCompra = estadoOrdenCompra.getIdOrdenCompra();
            if (idOrdenCompra != null) {
                idOrdenCompra = em.getReference(idOrdenCompra.getClass(), idOrdenCompra.getId());
                estadoOrdenCompra.setIdOrdenCompra(idOrdenCompra);
            }
            em.persist(estadoOrdenCompra);
            if (idEstadoOrdenCompra != null) {
                idEstadoOrdenCompra.getEstadoOrdenCompraList().add(estadoOrdenCompra);
                idEstadoOrdenCompra = em.merge(idEstadoOrdenCompra);
            }
            if (idOrdenCompra != null) {
                idOrdenCompra.getEstadoOrdenCompraList().add(estadoOrdenCompra);
                idOrdenCompra = em.merge(idOrdenCompra);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadoOrdenCompra(estadoOrdenCompra.getId()) != null) {
                throw new PreexistingEntityException("EstadoOrdenCompra " + estadoOrdenCompra + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoOrdenCompra estadoOrdenCompra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoOrdenCompra persistentEstadoOrdenCompra = em.find(EstadoOrdenCompra.class, estadoOrdenCompra.getId());
            TipoEstadoOrdenCompra idEstadoOrdenCompraOld = persistentEstadoOrdenCompra.getIdEstadoOrdenCompra();
            TipoEstadoOrdenCompra idEstadoOrdenCompraNew = estadoOrdenCompra.getIdEstadoOrdenCompra();
            OrdenCompra idOrdenCompraOld = persistentEstadoOrdenCompra.getIdOrdenCompra();
            OrdenCompra idOrdenCompraNew = estadoOrdenCompra.getIdOrdenCompra();
            if (idEstadoOrdenCompraNew != null) {
                idEstadoOrdenCompraNew = em.getReference(idEstadoOrdenCompraNew.getClass(), idEstadoOrdenCompraNew.getId());
                estadoOrdenCompra.setIdEstadoOrdenCompra(idEstadoOrdenCompraNew);
            }
            if (idOrdenCompraNew != null) {
                idOrdenCompraNew = em.getReference(idOrdenCompraNew.getClass(), idOrdenCompraNew.getId());
                estadoOrdenCompra.setIdOrdenCompra(idOrdenCompraNew);
            }
            estadoOrdenCompra = em.merge(estadoOrdenCompra);
            if (idEstadoOrdenCompraOld != null && !idEstadoOrdenCompraOld.equals(idEstadoOrdenCompraNew)) {
                idEstadoOrdenCompraOld.getEstadoOrdenCompraList().remove(estadoOrdenCompra);
                idEstadoOrdenCompraOld = em.merge(idEstadoOrdenCompraOld);
            }
            if (idEstadoOrdenCompraNew != null && !idEstadoOrdenCompraNew.equals(idEstadoOrdenCompraOld)) {
                idEstadoOrdenCompraNew.getEstadoOrdenCompraList().add(estadoOrdenCompra);
                idEstadoOrdenCompraNew = em.merge(idEstadoOrdenCompraNew);
            }
            if (idOrdenCompraOld != null && !idOrdenCompraOld.equals(idOrdenCompraNew)) {
                idOrdenCompraOld.getEstadoOrdenCompraList().remove(estadoOrdenCompra);
                idOrdenCompraOld = em.merge(idOrdenCompraOld);
            }
            if (idOrdenCompraNew != null && !idOrdenCompraNew.equals(idOrdenCompraOld)) {
                idOrdenCompraNew.getEstadoOrdenCompraList().add(estadoOrdenCompra);
                idOrdenCompraNew = em.merge(idOrdenCompraNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estadoOrdenCompra.getId();
                if (findEstadoOrdenCompra(id) == null) {
                    throw new NonexistentEntityException("The estadoOrdenCompra with id " + id + " no longer exists.");
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
            EstadoOrdenCompra estadoOrdenCompra;
            try {
                estadoOrdenCompra = em.getReference(EstadoOrdenCompra.class, id);
                estadoOrdenCompra.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoOrdenCompra with id " + id + " no longer exists.", enfe);
            }
            TipoEstadoOrdenCompra idEstadoOrdenCompra = estadoOrdenCompra.getIdEstadoOrdenCompra();
            if (idEstadoOrdenCompra != null) {
                idEstadoOrdenCompra.getEstadoOrdenCompraList().remove(estadoOrdenCompra);
                idEstadoOrdenCompra = em.merge(idEstadoOrdenCompra);
            }
            OrdenCompra idOrdenCompra = estadoOrdenCompra.getIdOrdenCompra();
            if (idOrdenCompra != null) {
                idOrdenCompra.getEstadoOrdenCompraList().remove(estadoOrdenCompra);
                idOrdenCompra = em.merge(idOrdenCompra);
            }
            em.remove(estadoOrdenCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoOrdenCompra> findEstadoOrdenCompraEntities() {
        return findEstadoOrdenCompraEntities(true, -1, -1);
    }

    public List<EstadoOrdenCompra> findEstadoOrdenCompraEntities(int maxResults, int firstResult) {
        return findEstadoOrdenCompraEntities(false, maxResults, firstResult);
    }

    private List<EstadoOrdenCompra> findEstadoOrdenCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoOrdenCompra.class));
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

    public EstadoOrdenCompra findEstadoOrdenCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoOrdenCompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoOrdenCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoOrdenCompra> rt = cq.from(EstadoOrdenCompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
