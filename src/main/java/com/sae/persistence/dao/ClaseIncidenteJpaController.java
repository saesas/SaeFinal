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
import com.sae.persistence.domain.Clase;
import com.sae.persistence.domain.ClaseIncidente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ClaseIncidenteJpaController implements Serializable {

    public ClaseIncidenteJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClaseIncidente claseIncidente) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clase idProcedimientoJuridico = claseIncidente.getIdProcedimientoJuridico();
            if (idProcedimientoJuridico != null) {
                idProcedimientoJuridico = em.getReference(idProcedimientoJuridico.getClass(), idProcedimientoJuridico.getId());
                claseIncidente.setIdProcedimientoJuridico(idProcedimientoJuridico);
            }
            em.persist(claseIncidente);
            if (idProcedimientoJuridico != null) {
                idProcedimientoJuridico.getClaseIncidenteList().add(claseIncidente);
                idProcedimientoJuridico = em.merge(idProcedimientoJuridico);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClaseIncidente(claseIncidente.getId()) != null) {
                throw new PreexistingEntityException("ClaseIncidente " + claseIncidente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClaseIncidente claseIncidente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClaseIncidente persistentClaseIncidente = em.find(ClaseIncidente.class, claseIncidente.getId());
            Clase idProcedimientoJuridicoOld = persistentClaseIncidente.getIdProcedimientoJuridico();
            Clase idProcedimientoJuridicoNew = claseIncidente.getIdProcedimientoJuridico();
            if (idProcedimientoJuridicoNew != null) {
                idProcedimientoJuridicoNew = em.getReference(idProcedimientoJuridicoNew.getClass(), idProcedimientoJuridicoNew.getId());
                claseIncidente.setIdProcedimientoJuridico(idProcedimientoJuridicoNew);
            }
            claseIncidente = em.merge(claseIncidente);
            if (idProcedimientoJuridicoOld != null && !idProcedimientoJuridicoOld.equals(idProcedimientoJuridicoNew)) {
                idProcedimientoJuridicoOld.getClaseIncidenteList().remove(claseIncidente);
                idProcedimientoJuridicoOld = em.merge(idProcedimientoJuridicoOld);
            }
            if (idProcedimientoJuridicoNew != null && !idProcedimientoJuridicoNew.equals(idProcedimientoJuridicoOld)) {
                idProcedimientoJuridicoNew.getClaseIncidenteList().add(claseIncidente);
                idProcedimientoJuridicoNew = em.merge(idProcedimientoJuridicoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = claseIncidente.getId();
                if (findClaseIncidente(id) == null) {
                    throw new NonexistentEntityException("The claseIncidente with id " + id + " no longer exists.");
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
            ClaseIncidente claseIncidente;
            try {
                claseIncidente = em.getReference(ClaseIncidente.class, id);
                claseIncidente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The claseIncidente with id " + id + " no longer exists.", enfe);
            }
            Clase idProcedimientoJuridico = claseIncidente.getIdProcedimientoJuridico();
            if (idProcedimientoJuridico != null) {
                idProcedimientoJuridico.getClaseIncidenteList().remove(claseIncidente);
                idProcedimientoJuridico = em.merge(idProcedimientoJuridico);
            }
            em.remove(claseIncidente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClaseIncidente> findClaseIncidenteEntities() {
        return findClaseIncidenteEntities(true, -1, -1);
    }

    public List<ClaseIncidente> findClaseIncidenteEntities(int maxResults, int firstResult) {
        return findClaseIncidenteEntities(false, maxResults, firstResult);
    }

    private List<ClaseIncidente> findClaseIncidenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClaseIncidente.class));
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

    public ClaseIncidente findClaseIncidente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClaseIncidente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClaseIncidenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClaseIncidente> rt = cq.from(ClaseIncidente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
