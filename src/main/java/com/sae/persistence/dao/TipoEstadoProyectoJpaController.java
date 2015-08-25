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
import com.sae.persistence.domain.EstadoProyecto;
import com.sae.persistence.domain.TipoEstadoProyecto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoEstadoProyectoJpaController implements Serializable {

    public TipoEstadoProyectoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoEstadoProyecto tipoEstadoProyecto) throws PreexistingEntityException, Exception {
        if (tipoEstadoProyecto.getEstadoProyectoList() == null) {
            tipoEstadoProyecto.setEstadoProyectoList(new ArrayList<EstadoProyecto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<EstadoProyecto> attachedEstadoProyectoList = new ArrayList<EstadoProyecto>();
            for (EstadoProyecto estadoProyectoListEstadoProyectoToAttach : tipoEstadoProyecto.getEstadoProyectoList()) {
                estadoProyectoListEstadoProyectoToAttach = em.getReference(estadoProyectoListEstadoProyectoToAttach.getClass(), estadoProyectoListEstadoProyectoToAttach.getId());
                attachedEstadoProyectoList.add(estadoProyectoListEstadoProyectoToAttach);
            }
            tipoEstadoProyecto.setEstadoProyectoList(attachedEstadoProyectoList);
            em.persist(tipoEstadoProyecto);
            for (EstadoProyecto estadoProyectoListEstadoProyecto : tipoEstadoProyecto.getEstadoProyectoList()) {
                TipoEstadoProyecto oldIdEstadoOfEstadoProyectoListEstadoProyecto = estadoProyectoListEstadoProyecto.getIdEstado();
                estadoProyectoListEstadoProyecto.setIdEstado(tipoEstadoProyecto);
                estadoProyectoListEstadoProyecto = em.merge(estadoProyectoListEstadoProyecto);
                if (oldIdEstadoOfEstadoProyectoListEstadoProyecto != null) {
                    oldIdEstadoOfEstadoProyectoListEstadoProyecto.getEstadoProyectoList().remove(estadoProyectoListEstadoProyecto);
                    oldIdEstadoOfEstadoProyectoListEstadoProyecto = em.merge(oldIdEstadoOfEstadoProyectoListEstadoProyecto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoEstadoProyecto(tipoEstadoProyecto.getId()) != null) {
                throw new PreexistingEntityException("TipoEstadoProyecto " + tipoEstadoProyecto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoEstadoProyecto tipoEstadoProyecto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEstadoProyecto persistentTipoEstadoProyecto = em.find(TipoEstadoProyecto.class, tipoEstadoProyecto.getId());
            List<EstadoProyecto> estadoProyectoListOld = persistentTipoEstadoProyecto.getEstadoProyectoList();
            List<EstadoProyecto> estadoProyectoListNew = tipoEstadoProyecto.getEstadoProyectoList();
            List<EstadoProyecto> attachedEstadoProyectoListNew = new ArrayList<EstadoProyecto>();
            for (EstadoProyecto estadoProyectoListNewEstadoProyectoToAttach : estadoProyectoListNew) {
                estadoProyectoListNewEstadoProyectoToAttach = em.getReference(estadoProyectoListNewEstadoProyectoToAttach.getClass(), estadoProyectoListNewEstadoProyectoToAttach.getId());
                attachedEstadoProyectoListNew.add(estadoProyectoListNewEstadoProyectoToAttach);
            }
            estadoProyectoListNew = attachedEstadoProyectoListNew;
            tipoEstadoProyecto.setEstadoProyectoList(estadoProyectoListNew);
            tipoEstadoProyecto = em.merge(tipoEstadoProyecto);
            for (EstadoProyecto estadoProyectoListOldEstadoProyecto : estadoProyectoListOld) {
                if (!estadoProyectoListNew.contains(estadoProyectoListOldEstadoProyecto)) {
                    estadoProyectoListOldEstadoProyecto.setIdEstado(null);
                    estadoProyectoListOldEstadoProyecto = em.merge(estadoProyectoListOldEstadoProyecto);
                }
            }
            for (EstadoProyecto estadoProyectoListNewEstadoProyecto : estadoProyectoListNew) {
                if (!estadoProyectoListOld.contains(estadoProyectoListNewEstadoProyecto)) {
                    TipoEstadoProyecto oldIdEstadoOfEstadoProyectoListNewEstadoProyecto = estadoProyectoListNewEstadoProyecto.getIdEstado();
                    estadoProyectoListNewEstadoProyecto.setIdEstado(tipoEstadoProyecto);
                    estadoProyectoListNewEstadoProyecto = em.merge(estadoProyectoListNewEstadoProyecto);
                    if (oldIdEstadoOfEstadoProyectoListNewEstadoProyecto != null && !oldIdEstadoOfEstadoProyectoListNewEstadoProyecto.equals(tipoEstadoProyecto)) {
                        oldIdEstadoOfEstadoProyectoListNewEstadoProyecto.getEstadoProyectoList().remove(estadoProyectoListNewEstadoProyecto);
                        oldIdEstadoOfEstadoProyectoListNewEstadoProyecto = em.merge(oldIdEstadoOfEstadoProyectoListNewEstadoProyecto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoEstadoProyecto.getId();
                if (findTipoEstadoProyecto(id) == null) {
                    throw new NonexistentEntityException("The tipoEstadoProyecto with id " + id + " no longer exists.");
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
            TipoEstadoProyecto tipoEstadoProyecto;
            try {
                tipoEstadoProyecto = em.getReference(TipoEstadoProyecto.class, id);
                tipoEstadoProyecto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoEstadoProyecto with id " + id + " no longer exists.", enfe);
            }
            List<EstadoProyecto> estadoProyectoList = tipoEstadoProyecto.getEstadoProyectoList();
            for (EstadoProyecto estadoProyectoListEstadoProyecto : estadoProyectoList) {
                estadoProyectoListEstadoProyecto.setIdEstado(null);
                estadoProyectoListEstadoProyecto = em.merge(estadoProyectoListEstadoProyecto);
            }
            em.remove(tipoEstadoProyecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoEstadoProyecto> findTipoEstadoProyectoEntities() {
        return findTipoEstadoProyectoEntities(true, -1, -1);
    }

    public List<TipoEstadoProyecto> findTipoEstadoProyectoEntities(int maxResults, int firstResult) {
        return findTipoEstadoProyectoEntities(false, maxResults, firstResult);
    }

    private List<TipoEstadoProyecto> findTipoEstadoProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoEstadoProyecto.class));
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

    public TipoEstadoProyecto findTipoEstadoProyecto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoEstadoProyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoEstadoProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoEstadoProyecto> rt = cq.from(TipoEstadoProyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
