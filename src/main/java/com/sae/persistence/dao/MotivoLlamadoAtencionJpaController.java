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
import com.sae.persistence.domain.TipoMotivoLlamadoAtencion;
import com.sae.persistence.domain.LlamadoAtencion;
import com.sae.persistence.domain.MotivoLlamadoAtencion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class MotivoLlamadoAtencionJpaController implements Serializable {

    public MotivoLlamadoAtencionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MotivoLlamadoAtencion motivoLlamadoAtencion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoMotivoLlamadoAtencion idMotivo = motivoLlamadoAtencion.getIdMotivo();
            if (idMotivo != null) {
                idMotivo = em.getReference(idMotivo.getClass(), idMotivo.getId());
                motivoLlamadoAtencion.setIdMotivo(idMotivo);
            }
            LlamadoAtencion idLlamadoAtencion = motivoLlamadoAtencion.getIdLlamadoAtencion();
            if (idLlamadoAtencion != null) {
                idLlamadoAtencion = em.getReference(idLlamadoAtencion.getClass(), idLlamadoAtencion.getId());
                motivoLlamadoAtencion.setIdLlamadoAtencion(idLlamadoAtencion);
            }
            em.persist(motivoLlamadoAtencion);
            if (idMotivo != null) {
                idMotivo.getMotivoLlamadoAtencionList().add(motivoLlamadoAtencion);
                idMotivo = em.merge(idMotivo);
            }
            if (idLlamadoAtencion != null) {
                idLlamadoAtencion.getMotivoLlamadoAtencionList().add(motivoLlamadoAtencion);
                idLlamadoAtencion = em.merge(idLlamadoAtencion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMotivoLlamadoAtencion(motivoLlamadoAtencion.getId()) != null) {
                throw new PreexistingEntityException("MotivoLlamadoAtencion " + motivoLlamadoAtencion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MotivoLlamadoAtencion motivoLlamadoAtencion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MotivoLlamadoAtencion persistentMotivoLlamadoAtencion = em.find(MotivoLlamadoAtencion.class, motivoLlamadoAtencion.getId());
            TipoMotivoLlamadoAtencion idMotivoOld = persistentMotivoLlamadoAtencion.getIdMotivo();
            TipoMotivoLlamadoAtencion idMotivoNew = motivoLlamadoAtencion.getIdMotivo();
            LlamadoAtencion idLlamadoAtencionOld = persistentMotivoLlamadoAtencion.getIdLlamadoAtencion();
            LlamadoAtencion idLlamadoAtencionNew = motivoLlamadoAtencion.getIdLlamadoAtencion();
            if (idMotivoNew != null) {
                idMotivoNew = em.getReference(idMotivoNew.getClass(), idMotivoNew.getId());
                motivoLlamadoAtencion.setIdMotivo(idMotivoNew);
            }
            if (idLlamadoAtencionNew != null) {
                idLlamadoAtencionNew = em.getReference(idLlamadoAtencionNew.getClass(), idLlamadoAtencionNew.getId());
                motivoLlamadoAtencion.setIdLlamadoAtencion(idLlamadoAtencionNew);
            }
            motivoLlamadoAtencion = em.merge(motivoLlamadoAtencion);
            if (idMotivoOld != null && !idMotivoOld.equals(idMotivoNew)) {
                idMotivoOld.getMotivoLlamadoAtencionList().remove(motivoLlamadoAtencion);
                idMotivoOld = em.merge(idMotivoOld);
            }
            if (idMotivoNew != null && !idMotivoNew.equals(idMotivoOld)) {
                idMotivoNew.getMotivoLlamadoAtencionList().add(motivoLlamadoAtencion);
                idMotivoNew = em.merge(idMotivoNew);
            }
            if (idLlamadoAtencionOld != null && !idLlamadoAtencionOld.equals(idLlamadoAtencionNew)) {
                idLlamadoAtencionOld.getMotivoLlamadoAtencionList().remove(motivoLlamadoAtencion);
                idLlamadoAtencionOld = em.merge(idLlamadoAtencionOld);
            }
            if (idLlamadoAtencionNew != null && !idLlamadoAtencionNew.equals(idLlamadoAtencionOld)) {
                idLlamadoAtencionNew.getMotivoLlamadoAtencionList().add(motivoLlamadoAtencion);
                idLlamadoAtencionNew = em.merge(idLlamadoAtencionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = motivoLlamadoAtencion.getId();
                if (findMotivoLlamadoAtencion(id) == null) {
                    throw new NonexistentEntityException("The motivoLlamadoAtencion with id " + id + " no longer exists.");
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
            MotivoLlamadoAtencion motivoLlamadoAtencion;
            try {
                motivoLlamadoAtencion = em.getReference(MotivoLlamadoAtencion.class, id);
                motivoLlamadoAtencion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The motivoLlamadoAtencion with id " + id + " no longer exists.", enfe);
            }
            TipoMotivoLlamadoAtencion idMotivo = motivoLlamadoAtencion.getIdMotivo();
            if (idMotivo != null) {
                idMotivo.getMotivoLlamadoAtencionList().remove(motivoLlamadoAtencion);
                idMotivo = em.merge(idMotivo);
            }
            LlamadoAtencion idLlamadoAtencion = motivoLlamadoAtencion.getIdLlamadoAtencion();
            if (idLlamadoAtencion != null) {
                idLlamadoAtencion.getMotivoLlamadoAtencionList().remove(motivoLlamadoAtencion);
                idLlamadoAtencion = em.merge(idLlamadoAtencion);
            }
            em.remove(motivoLlamadoAtencion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MotivoLlamadoAtencion> findMotivoLlamadoAtencionEntities() {
        return findMotivoLlamadoAtencionEntities(true, -1, -1);
    }

    public List<MotivoLlamadoAtencion> findMotivoLlamadoAtencionEntities(int maxResults, int firstResult) {
        return findMotivoLlamadoAtencionEntities(false, maxResults, firstResult);
    }

    private List<MotivoLlamadoAtencion> findMotivoLlamadoAtencionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MotivoLlamadoAtencion.class));
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

    public MotivoLlamadoAtencion findMotivoLlamadoAtencion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MotivoLlamadoAtencion.class, id);
        } finally {
            em.close();
        }
    }

    public int getMotivoLlamadoAtencionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MotivoLlamadoAtencion> rt = cq.from(MotivoLlamadoAtencion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
