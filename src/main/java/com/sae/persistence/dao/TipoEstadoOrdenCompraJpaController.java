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
import com.sae.persistence.domain.EstadoOrdenCompra;
import com.sae.persistence.domain.TipoEstadoOrdenCompra;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoEstadoOrdenCompraJpaController implements Serializable {

    public TipoEstadoOrdenCompraJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoEstadoOrdenCompra tipoEstadoOrdenCompra) throws PreexistingEntityException, Exception {
        if (tipoEstadoOrdenCompra.getEstadoOrdenCompraList() == null) {
            tipoEstadoOrdenCompra.setEstadoOrdenCompraList(new ArrayList<EstadoOrdenCompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<EstadoOrdenCompra> attachedEstadoOrdenCompraList = new ArrayList<EstadoOrdenCompra>();
            for (EstadoOrdenCompra estadoOrdenCompraListEstadoOrdenCompraToAttach : tipoEstadoOrdenCompra.getEstadoOrdenCompraList()) {
                estadoOrdenCompraListEstadoOrdenCompraToAttach = em.getReference(estadoOrdenCompraListEstadoOrdenCompraToAttach.getClass(), estadoOrdenCompraListEstadoOrdenCompraToAttach.getId());
                attachedEstadoOrdenCompraList.add(estadoOrdenCompraListEstadoOrdenCompraToAttach);
            }
            tipoEstadoOrdenCompra.setEstadoOrdenCompraList(attachedEstadoOrdenCompraList);
            em.persist(tipoEstadoOrdenCompra);
            for (EstadoOrdenCompra estadoOrdenCompraListEstadoOrdenCompra : tipoEstadoOrdenCompra.getEstadoOrdenCompraList()) {
                TipoEstadoOrdenCompra oldIdEstadoOrdenCompraOfEstadoOrdenCompraListEstadoOrdenCompra = estadoOrdenCompraListEstadoOrdenCompra.getIdEstadoOrdenCompra();
                estadoOrdenCompraListEstadoOrdenCompra.setIdEstadoOrdenCompra(tipoEstadoOrdenCompra);
                estadoOrdenCompraListEstadoOrdenCompra = em.merge(estadoOrdenCompraListEstadoOrdenCompra);
                if (oldIdEstadoOrdenCompraOfEstadoOrdenCompraListEstadoOrdenCompra != null) {
                    oldIdEstadoOrdenCompraOfEstadoOrdenCompraListEstadoOrdenCompra.getEstadoOrdenCompraList().remove(estadoOrdenCompraListEstadoOrdenCompra);
                    oldIdEstadoOrdenCompraOfEstadoOrdenCompraListEstadoOrdenCompra = em.merge(oldIdEstadoOrdenCompraOfEstadoOrdenCompraListEstadoOrdenCompra);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoEstadoOrdenCompra(tipoEstadoOrdenCompra.getId()) != null) {
                throw new PreexistingEntityException("TipoEstadoOrdenCompra " + tipoEstadoOrdenCompra + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoEstadoOrdenCompra tipoEstadoOrdenCompra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEstadoOrdenCompra persistentTipoEstadoOrdenCompra = em.find(TipoEstadoOrdenCompra.class, tipoEstadoOrdenCompra.getId());
            List<EstadoOrdenCompra> estadoOrdenCompraListOld = persistentTipoEstadoOrdenCompra.getEstadoOrdenCompraList();
            List<EstadoOrdenCompra> estadoOrdenCompraListNew = tipoEstadoOrdenCompra.getEstadoOrdenCompraList();
            List<EstadoOrdenCompra> attachedEstadoOrdenCompraListNew = new ArrayList<EstadoOrdenCompra>();
            for (EstadoOrdenCompra estadoOrdenCompraListNewEstadoOrdenCompraToAttach : estadoOrdenCompraListNew) {
                estadoOrdenCompraListNewEstadoOrdenCompraToAttach = em.getReference(estadoOrdenCompraListNewEstadoOrdenCompraToAttach.getClass(), estadoOrdenCompraListNewEstadoOrdenCompraToAttach.getId());
                attachedEstadoOrdenCompraListNew.add(estadoOrdenCompraListNewEstadoOrdenCompraToAttach);
            }
            estadoOrdenCompraListNew = attachedEstadoOrdenCompraListNew;
            tipoEstadoOrdenCompra.setEstadoOrdenCompraList(estadoOrdenCompraListNew);
            tipoEstadoOrdenCompra = em.merge(tipoEstadoOrdenCompra);
            for (EstadoOrdenCompra estadoOrdenCompraListOldEstadoOrdenCompra : estadoOrdenCompraListOld) {
                if (!estadoOrdenCompraListNew.contains(estadoOrdenCompraListOldEstadoOrdenCompra)) {
                    estadoOrdenCompraListOldEstadoOrdenCompra.setIdEstadoOrdenCompra(null);
                    estadoOrdenCompraListOldEstadoOrdenCompra = em.merge(estadoOrdenCompraListOldEstadoOrdenCompra);
                }
            }
            for (EstadoOrdenCompra estadoOrdenCompraListNewEstadoOrdenCompra : estadoOrdenCompraListNew) {
                if (!estadoOrdenCompraListOld.contains(estadoOrdenCompraListNewEstadoOrdenCompra)) {
                    TipoEstadoOrdenCompra oldIdEstadoOrdenCompraOfEstadoOrdenCompraListNewEstadoOrdenCompra = estadoOrdenCompraListNewEstadoOrdenCompra.getIdEstadoOrdenCompra();
                    estadoOrdenCompraListNewEstadoOrdenCompra.setIdEstadoOrdenCompra(tipoEstadoOrdenCompra);
                    estadoOrdenCompraListNewEstadoOrdenCompra = em.merge(estadoOrdenCompraListNewEstadoOrdenCompra);
                    if (oldIdEstadoOrdenCompraOfEstadoOrdenCompraListNewEstadoOrdenCompra != null && !oldIdEstadoOrdenCompraOfEstadoOrdenCompraListNewEstadoOrdenCompra.equals(tipoEstadoOrdenCompra)) {
                        oldIdEstadoOrdenCompraOfEstadoOrdenCompraListNewEstadoOrdenCompra.getEstadoOrdenCompraList().remove(estadoOrdenCompraListNewEstadoOrdenCompra);
                        oldIdEstadoOrdenCompraOfEstadoOrdenCompraListNewEstadoOrdenCompra = em.merge(oldIdEstadoOrdenCompraOfEstadoOrdenCompraListNewEstadoOrdenCompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoEstadoOrdenCompra.getId();
                if (findTipoEstadoOrdenCompra(id) == null) {
                    throw new NonexistentEntityException("The tipoEstadoOrdenCompra with id " + id + " no longer exists.");
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
            TipoEstadoOrdenCompra tipoEstadoOrdenCompra;
            try {
                tipoEstadoOrdenCompra = em.getReference(TipoEstadoOrdenCompra.class, id);
                tipoEstadoOrdenCompra.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoEstadoOrdenCompra with id " + id + " no longer exists.", enfe);
            }
            List<EstadoOrdenCompra> estadoOrdenCompraList = tipoEstadoOrdenCompra.getEstadoOrdenCompraList();
            for (EstadoOrdenCompra estadoOrdenCompraListEstadoOrdenCompra : estadoOrdenCompraList) {
                estadoOrdenCompraListEstadoOrdenCompra.setIdEstadoOrdenCompra(null);
                estadoOrdenCompraListEstadoOrdenCompra = em.merge(estadoOrdenCompraListEstadoOrdenCompra);
            }
            em.remove(tipoEstadoOrdenCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoEstadoOrdenCompra> findTipoEstadoOrdenCompraEntities() {
        return findTipoEstadoOrdenCompraEntities(true, -1, -1);
    }

    public List<TipoEstadoOrdenCompra> findTipoEstadoOrdenCompraEntities(int maxResults, int firstResult) {
        return findTipoEstadoOrdenCompraEntities(false, maxResults, firstResult);
    }

    private List<TipoEstadoOrdenCompra> findTipoEstadoOrdenCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoEstadoOrdenCompra.class));
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

    public TipoEstadoOrdenCompra findTipoEstadoOrdenCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoEstadoOrdenCompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoEstadoOrdenCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoEstadoOrdenCompra> rt = cq.from(TipoEstadoOrdenCompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
