/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.GrupoAsociadoServicio;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.GrupoServicios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class GrupoAsociadoServicioJpaController implements Serializable {

    public GrupoAsociadoServicioJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GrupoAsociadoServicio grupoAsociadoServicio) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GrupoServicios idGrupo = grupoAsociadoServicio.getIdGrupo();
            if (idGrupo != null) {
                idGrupo = em.getReference(idGrupo.getClass(), idGrupo.getId());
                grupoAsociadoServicio.setIdGrupo(idGrupo);
            }
            em.persist(grupoAsociadoServicio);
            if (idGrupo != null) {
                idGrupo.getGrupoAsociadoServicioList().add(grupoAsociadoServicio);
                idGrupo = em.merge(idGrupo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGrupoAsociadoServicio(grupoAsociadoServicio.getId()) != null) {
                throw new PreexistingEntityException("GrupoAsociadoServicio " + grupoAsociadoServicio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GrupoAsociadoServicio grupoAsociadoServicio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GrupoAsociadoServicio persistentGrupoAsociadoServicio = em.find(GrupoAsociadoServicio.class, grupoAsociadoServicio.getId());
            GrupoServicios idGrupoOld = persistentGrupoAsociadoServicio.getIdGrupo();
            GrupoServicios idGrupoNew = grupoAsociadoServicio.getIdGrupo();
            if (idGrupoNew != null) {
                idGrupoNew = em.getReference(idGrupoNew.getClass(), idGrupoNew.getId());
                grupoAsociadoServicio.setIdGrupo(idGrupoNew);
            }
            grupoAsociadoServicio = em.merge(grupoAsociadoServicio);
            if (idGrupoOld != null && !idGrupoOld.equals(idGrupoNew)) {
                idGrupoOld.getGrupoAsociadoServicioList().remove(grupoAsociadoServicio);
                idGrupoOld = em.merge(idGrupoOld);
            }
            if (idGrupoNew != null && !idGrupoNew.equals(idGrupoOld)) {
                idGrupoNew.getGrupoAsociadoServicioList().add(grupoAsociadoServicio);
                idGrupoNew = em.merge(idGrupoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = grupoAsociadoServicio.getId();
                if (findGrupoAsociadoServicio(id) == null) {
                    throw new NonexistentEntityException("The grupoAsociadoServicio with id " + id + " no longer exists.");
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
            GrupoAsociadoServicio grupoAsociadoServicio;
            try {
                grupoAsociadoServicio = em.getReference(GrupoAsociadoServicio.class, id);
                grupoAsociadoServicio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupoAsociadoServicio with id " + id + " no longer exists.", enfe);
            }
            GrupoServicios idGrupo = grupoAsociadoServicio.getIdGrupo();
            if (idGrupo != null) {
                idGrupo.getGrupoAsociadoServicioList().remove(grupoAsociadoServicio);
                idGrupo = em.merge(idGrupo);
            }
            em.remove(grupoAsociadoServicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GrupoAsociadoServicio> findGrupoAsociadoServicioEntities() {
        return findGrupoAsociadoServicioEntities(true, -1, -1);
    }

    public List<GrupoAsociadoServicio> findGrupoAsociadoServicioEntities(int maxResults, int firstResult) {
        return findGrupoAsociadoServicioEntities(false, maxResults, firstResult);
    }

    private List<GrupoAsociadoServicio> findGrupoAsociadoServicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GrupoAsociadoServicio.class));
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

    public GrupoAsociadoServicio findGrupoAsociadoServicio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GrupoAsociadoServicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoAsociadoServicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GrupoAsociadoServicio> rt = cq.from(GrupoAsociadoServicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
