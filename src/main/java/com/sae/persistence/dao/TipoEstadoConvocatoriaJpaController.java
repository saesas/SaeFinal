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
import com.sae.persistence.domain.EstadoConvocatoria;
import com.sae.persistence.domain.TipoEstadoConvocatoria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoEstadoConvocatoriaJpaController implements Serializable {

    public TipoEstadoConvocatoriaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoEstadoConvocatoria tipoEstadoConvocatoria) throws PreexistingEntityException, Exception {
        if (tipoEstadoConvocatoria.getEstadoConvocatoriaList() == null) {
            tipoEstadoConvocatoria.setEstadoConvocatoriaList(new ArrayList<EstadoConvocatoria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<EstadoConvocatoria> attachedEstadoConvocatoriaList = new ArrayList<EstadoConvocatoria>();
            for (EstadoConvocatoria estadoConvocatoriaListEstadoConvocatoriaToAttach : tipoEstadoConvocatoria.getEstadoConvocatoriaList()) {
                estadoConvocatoriaListEstadoConvocatoriaToAttach = em.getReference(estadoConvocatoriaListEstadoConvocatoriaToAttach.getClass(), estadoConvocatoriaListEstadoConvocatoriaToAttach.getId());
                attachedEstadoConvocatoriaList.add(estadoConvocatoriaListEstadoConvocatoriaToAttach);
            }
            tipoEstadoConvocatoria.setEstadoConvocatoriaList(attachedEstadoConvocatoriaList);
            em.persist(tipoEstadoConvocatoria);
            for (EstadoConvocatoria estadoConvocatoriaListEstadoConvocatoria : tipoEstadoConvocatoria.getEstadoConvocatoriaList()) {
                TipoEstadoConvocatoria oldIdEstadoConvocatoriaOfEstadoConvocatoriaListEstadoConvocatoria = estadoConvocatoriaListEstadoConvocatoria.getIdEstadoConvocatoria();
                estadoConvocatoriaListEstadoConvocatoria.setIdEstadoConvocatoria(tipoEstadoConvocatoria);
                estadoConvocatoriaListEstadoConvocatoria = em.merge(estadoConvocatoriaListEstadoConvocatoria);
                if (oldIdEstadoConvocatoriaOfEstadoConvocatoriaListEstadoConvocatoria != null) {
                    oldIdEstadoConvocatoriaOfEstadoConvocatoriaListEstadoConvocatoria.getEstadoConvocatoriaList().remove(estadoConvocatoriaListEstadoConvocatoria);
                    oldIdEstadoConvocatoriaOfEstadoConvocatoriaListEstadoConvocatoria = em.merge(oldIdEstadoConvocatoriaOfEstadoConvocatoriaListEstadoConvocatoria);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoEstadoConvocatoria(tipoEstadoConvocatoria.getId()) != null) {
                throw new PreexistingEntityException("TipoEstadoConvocatoria " + tipoEstadoConvocatoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoEstadoConvocatoria tipoEstadoConvocatoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEstadoConvocatoria persistentTipoEstadoConvocatoria = em.find(TipoEstadoConvocatoria.class, tipoEstadoConvocatoria.getId());
            List<EstadoConvocatoria> estadoConvocatoriaListOld = persistentTipoEstadoConvocatoria.getEstadoConvocatoriaList();
            List<EstadoConvocatoria> estadoConvocatoriaListNew = tipoEstadoConvocatoria.getEstadoConvocatoriaList();
            List<EstadoConvocatoria> attachedEstadoConvocatoriaListNew = new ArrayList<EstadoConvocatoria>();
            for (EstadoConvocatoria estadoConvocatoriaListNewEstadoConvocatoriaToAttach : estadoConvocatoriaListNew) {
                estadoConvocatoriaListNewEstadoConvocatoriaToAttach = em.getReference(estadoConvocatoriaListNewEstadoConvocatoriaToAttach.getClass(), estadoConvocatoriaListNewEstadoConvocatoriaToAttach.getId());
                attachedEstadoConvocatoriaListNew.add(estadoConvocatoriaListNewEstadoConvocatoriaToAttach);
            }
            estadoConvocatoriaListNew = attachedEstadoConvocatoriaListNew;
            tipoEstadoConvocatoria.setEstadoConvocatoriaList(estadoConvocatoriaListNew);
            tipoEstadoConvocatoria = em.merge(tipoEstadoConvocatoria);
            for (EstadoConvocatoria estadoConvocatoriaListOldEstadoConvocatoria : estadoConvocatoriaListOld) {
                if (!estadoConvocatoriaListNew.contains(estadoConvocatoriaListOldEstadoConvocatoria)) {
                    estadoConvocatoriaListOldEstadoConvocatoria.setIdEstadoConvocatoria(null);
                    estadoConvocatoriaListOldEstadoConvocatoria = em.merge(estadoConvocatoriaListOldEstadoConvocatoria);
                }
            }
            for (EstadoConvocatoria estadoConvocatoriaListNewEstadoConvocatoria : estadoConvocatoriaListNew) {
                if (!estadoConvocatoriaListOld.contains(estadoConvocatoriaListNewEstadoConvocatoria)) {
                    TipoEstadoConvocatoria oldIdEstadoConvocatoriaOfEstadoConvocatoriaListNewEstadoConvocatoria = estadoConvocatoriaListNewEstadoConvocatoria.getIdEstadoConvocatoria();
                    estadoConvocatoriaListNewEstadoConvocatoria.setIdEstadoConvocatoria(tipoEstadoConvocatoria);
                    estadoConvocatoriaListNewEstadoConvocatoria = em.merge(estadoConvocatoriaListNewEstadoConvocatoria);
                    if (oldIdEstadoConvocatoriaOfEstadoConvocatoriaListNewEstadoConvocatoria != null && !oldIdEstadoConvocatoriaOfEstadoConvocatoriaListNewEstadoConvocatoria.equals(tipoEstadoConvocatoria)) {
                        oldIdEstadoConvocatoriaOfEstadoConvocatoriaListNewEstadoConvocatoria.getEstadoConvocatoriaList().remove(estadoConvocatoriaListNewEstadoConvocatoria);
                        oldIdEstadoConvocatoriaOfEstadoConvocatoriaListNewEstadoConvocatoria = em.merge(oldIdEstadoConvocatoriaOfEstadoConvocatoriaListNewEstadoConvocatoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoEstadoConvocatoria.getId();
                if (findTipoEstadoConvocatoria(id) == null) {
                    throw new NonexistentEntityException("The tipoEstadoConvocatoria with id " + id + " no longer exists.");
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
            TipoEstadoConvocatoria tipoEstadoConvocatoria;
            try {
                tipoEstadoConvocatoria = em.getReference(TipoEstadoConvocatoria.class, id);
                tipoEstadoConvocatoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoEstadoConvocatoria with id " + id + " no longer exists.", enfe);
            }
            List<EstadoConvocatoria> estadoConvocatoriaList = tipoEstadoConvocatoria.getEstadoConvocatoriaList();
            for (EstadoConvocatoria estadoConvocatoriaListEstadoConvocatoria : estadoConvocatoriaList) {
                estadoConvocatoriaListEstadoConvocatoria.setIdEstadoConvocatoria(null);
                estadoConvocatoriaListEstadoConvocatoria = em.merge(estadoConvocatoriaListEstadoConvocatoria);
            }
            em.remove(tipoEstadoConvocatoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoEstadoConvocatoria> findTipoEstadoConvocatoriaEntities() {
        return findTipoEstadoConvocatoriaEntities(true, -1, -1);
    }

    public List<TipoEstadoConvocatoria> findTipoEstadoConvocatoriaEntities(int maxResults, int firstResult) {
        return findTipoEstadoConvocatoriaEntities(false, maxResults, firstResult);
    }

    private List<TipoEstadoConvocatoria> findTipoEstadoConvocatoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoEstadoConvocatoria.class));
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

    public TipoEstadoConvocatoria findTipoEstadoConvocatoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoEstadoConvocatoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoEstadoConvocatoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoEstadoConvocatoria> rt = cq.from(TipoEstadoConvocatoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
