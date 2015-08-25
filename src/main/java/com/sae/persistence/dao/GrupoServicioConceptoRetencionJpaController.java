/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.GrupoServicioConceptoRetencion;
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
public class GrupoServicioConceptoRetencionJpaController implements Serializable {

    public GrupoServicioConceptoRetencionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GrupoServicioConceptoRetencion grupoServicioConceptoRetencion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GrupoServicios grupo = grupoServicioConceptoRetencion.getGrupo();
            if (grupo != null) {
                grupo = em.getReference(grupo.getClass(), grupo.getId());
                grupoServicioConceptoRetencion.setGrupo(grupo);
            }
            em.persist(grupoServicioConceptoRetencion);
            if (grupo != null) {
                grupo.getGrupoServicioConceptoRetencionList().add(grupoServicioConceptoRetencion);
                grupo = em.merge(grupo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGrupoServicioConceptoRetencion(grupoServicioConceptoRetencion.getId()) != null) {
                throw new PreexistingEntityException("GrupoServicioConceptoRetencion " + grupoServicioConceptoRetencion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GrupoServicioConceptoRetencion grupoServicioConceptoRetencion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GrupoServicioConceptoRetencion persistentGrupoServicioConceptoRetencion = em.find(GrupoServicioConceptoRetencion.class, grupoServicioConceptoRetencion.getId());
            GrupoServicios grupoOld = persistentGrupoServicioConceptoRetencion.getGrupo();
            GrupoServicios grupoNew = grupoServicioConceptoRetencion.getGrupo();
            if (grupoNew != null) {
                grupoNew = em.getReference(grupoNew.getClass(), grupoNew.getId());
                grupoServicioConceptoRetencion.setGrupo(grupoNew);
            }
            grupoServicioConceptoRetencion = em.merge(grupoServicioConceptoRetencion);
            if (grupoOld != null && !grupoOld.equals(grupoNew)) {
                grupoOld.getGrupoServicioConceptoRetencionList().remove(grupoServicioConceptoRetencion);
                grupoOld = em.merge(grupoOld);
            }
            if (grupoNew != null && !grupoNew.equals(grupoOld)) {
                grupoNew.getGrupoServicioConceptoRetencionList().add(grupoServicioConceptoRetencion);
                grupoNew = em.merge(grupoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = grupoServicioConceptoRetencion.getId();
                if (findGrupoServicioConceptoRetencion(id) == null) {
                    throw new NonexistentEntityException("The grupoServicioConceptoRetencion with id " + id + " no longer exists.");
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
            GrupoServicioConceptoRetencion grupoServicioConceptoRetencion;
            try {
                grupoServicioConceptoRetencion = em.getReference(GrupoServicioConceptoRetencion.class, id);
                grupoServicioConceptoRetencion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupoServicioConceptoRetencion with id " + id + " no longer exists.", enfe);
            }
            GrupoServicios grupo = grupoServicioConceptoRetencion.getGrupo();
            if (grupo != null) {
                grupo.getGrupoServicioConceptoRetencionList().remove(grupoServicioConceptoRetencion);
                grupo = em.merge(grupo);
            }
            em.remove(grupoServicioConceptoRetencion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GrupoServicioConceptoRetencion> findGrupoServicioConceptoRetencionEntities() {
        return findGrupoServicioConceptoRetencionEntities(true, -1, -1);
    }

    public List<GrupoServicioConceptoRetencion> findGrupoServicioConceptoRetencionEntities(int maxResults, int firstResult) {
        return findGrupoServicioConceptoRetencionEntities(false, maxResults, firstResult);
    }

    private List<GrupoServicioConceptoRetencion> findGrupoServicioConceptoRetencionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GrupoServicioConceptoRetencion.class));
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

    public GrupoServicioConceptoRetencion findGrupoServicioConceptoRetencion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GrupoServicioConceptoRetencion.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoServicioConceptoRetencionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GrupoServicioConceptoRetencion> rt = cq.from(GrupoServicioConceptoRetencion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
