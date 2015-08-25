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
import com.sae.persistence.domain.EstadoAudiencia;
import com.sae.persistence.domain.TipoEstadoAudiencia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoEstadoAudienciaJpaController implements Serializable {

    public TipoEstadoAudienciaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoEstadoAudiencia tipoEstadoAudiencia) throws PreexistingEntityException, Exception {
        if (tipoEstadoAudiencia.getEstadoAudienciaList() == null) {
            tipoEstadoAudiencia.setEstadoAudienciaList(new ArrayList<EstadoAudiencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<EstadoAudiencia> attachedEstadoAudienciaList = new ArrayList<EstadoAudiencia>();
            for (EstadoAudiencia estadoAudienciaListEstadoAudienciaToAttach : tipoEstadoAudiencia.getEstadoAudienciaList()) {
                estadoAudienciaListEstadoAudienciaToAttach = em.getReference(estadoAudienciaListEstadoAudienciaToAttach.getClass(), estadoAudienciaListEstadoAudienciaToAttach.getId());
                attachedEstadoAudienciaList.add(estadoAudienciaListEstadoAudienciaToAttach);
            }
            tipoEstadoAudiencia.setEstadoAudienciaList(attachedEstadoAudienciaList);
            em.persist(tipoEstadoAudiencia);
            for (EstadoAudiencia estadoAudienciaListEstadoAudiencia : tipoEstadoAudiencia.getEstadoAudienciaList()) {
                TipoEstadoAudiencia oldIdEstadoOfEstadoAudienciaListEstadoAudiencia = estadoAudienciaListEstadoAudiencia.getIdEstado();
                estadoAudienciaListEstadoAudiencia.setIdEstado(tipoEstadoAudiencia);
                estadoAudienciaListEstadoAudiencia = em.merge(estadoAudienciaListEstadoAudiencia);
                if (oldIdEstadoOfEstadoAudienciaListEstadoAudiencia != null) {
                    oldIdEstadoOfEstadoAudienciaListEstadoAudiencia.getEstadoAudienciaList().remove(estadoAudienciaListEstadoAudiencia);
                    oldIdEstadoOfEstadoAudienciaListEstadoAudiencia = em.merge(oldIdEstadoOfEstadoAudienciaListEstadoAudiencia);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoEstadoAudiencia(tipoEstadoAudiencia.getId()) != null) {
                throw new PreexistingEntityException("TipoEstadoAudiencia " + tipoEstadoAudiencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoEstadoAudiencia tipoEstadoAudiencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEstadoAudiencia persistentTipoEstadoAudiencia = em.find(TipoEstadoAudiencia.class, tipoEstadoAudiencia.getId());
            List<EstadoAudiencia> estadoAudienciaListOld = persistentTipoEstadoAudiencia.getEstadoAudienciaList();
            List<EstadoAudiencia> estadoAudienciaListNew = tipoEstadoAudiencia.getEstadoAudienciaList();
            List<EstadoAudiencia> attachedEstadoAudienciaListNew = new ArrayList<EstadoAudiencia>();
            for (EstadoAudiencia estadoAudienciaListNewEstadoAudienciaToAttach : estadoAudienciaListNew) {
                estadoAudienciaListNewEstadoAudienciaToAttach = em.getReference(estadoAudienciaListNewEstadoAudienciaToAttach.getClass(), estadoAudienciaListNewEstadoAudienciaToAttach.getId());
                attachedEstadoAudienciaListNew.add(estadoAudienciaListNewEstadoAudienciaToAttach);
            }
            estadoAudienciaListNew = attachedEstadoAudienciaListNew;
            tipoEstadoAudiencia.setEstadoAudienciaList(estadoAudienciaListNew);
            tipoEstadoAudiencia = em.merge(tipoEstadoAudiencia);
            for (EstadoAudiencia estadoAudienciaListOldEstadoAudiencia : estadoAudienciaListOld) {
                if (!estadoAudienciaListNew.contains(estadoAudienciaListOldEstadoAudiencia)) {
                    estadoAudienciaListOldEstadoAudiencia.setIdEstado(null);
                    estadoAudienciaListOldEstadoAudiencia = em.merge(estadoAudienciaListOldEstadoAudiencia);
                }
            }
            for (EstadoAudiencia estadoAudienciaListNewEstadoAudiencia : estadoAudienciaListNew) {
                if (!estadoAudienciaListOld.contains(estadoAudienciaListNewEstadoAudiencia)) {
                    TipoEstadoAudiencia oldIdEstadoOfEstadoAudienciaListNewEstadoAudiencia = estadoAudienciaListNewEstadoAudiencia.getIdEstado();
                    estadoAudienciaListNewEstadoAudiencia.setIdEstado(tipoEstadoAudiencia);
                    estadoAudienciaListNewEstadoAudiencia = em.merge(estadoAudienciaListNewEstadoAudiencia);
                    if (oldIdEstadoOfEstadoAudienciaListNewEstadoAudiencia != null && !oldIdEstadoOfEstadoAudienciaListNewEstadoAudiencia.equals(tipoEstadoAudiencia)) {
                        oldIdEstadoOfEstadoAudienciaListNewEstadoAudiencia.getEstadoAudienciaList().remove(estadoAudienciaListNewEstadoAudiencia);
                        oldIdEstadoOfEstadoAudienciaListNewEstadoAudiencia = em.merge(oldIdEstadoOfEstadoAudienciaListNewEstadoAudiencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoEstadoAudiencia.getId();
                if (findTipoEstadoAudiencia(id) == null) {
                    throw new NonexistentEntityException("The tipoEstadoAudiencia with id " + id + " no longer exists.");
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
            TipoEstadoAudiencia tipoEstadoAudiencia;
            try {
                tipoEstadoAudiencia = em.getReference(TipoEstadoAudiencia.class, id);
                tipoEstadoAudiencia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoEstadoAudiencia with id " + id + " no longer exists.", enfe);
            }
            List<EstadoAudiencia> estadoAudienciaList = tipoEstadoAudiencia.getEstadoAudienciaList();
            for (EstadoAudiencia estadoAudienciaListEstadoAudiencia : estadoAudienciaList) {
                estadoAudienciaListEstadoAudiencia.setIdEstado(null);
                estadoAudienciaListEstadoAudiencia = em.merge(estadoAudienciaListEstadoAudiencia);
            }
            em.remove(tipoEstadoAudiencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoEstadoAudiencia> findTipoEstadoAudienciaEntities() {
        return findTipoEstadoAudienciaEntities(true, -1, -1);
    }

    public List<TipoEstadoAudiencia> findTipoEstadoAudienciaEntities(int maxResults, int firstResult) {
        return findTipoEstadoAudienciaEntities(false, maxResults, firstResult);
    }

    private List<TipoEstadoAudiencia> findTipoEstadoAudienciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoEstadoAudiencia.class));
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

    public TipoEstadoAudiencia findTipoEstadoAudiencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoEstadoAudiencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoEstadoAudienciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoEstadoAudiencia> rt = cq.from(TipoEstadoAudiencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
