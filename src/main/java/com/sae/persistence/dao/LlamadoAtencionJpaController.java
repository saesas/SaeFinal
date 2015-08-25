/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.LlamadoAtencion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.MotivoLlamadoAtencion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class LlamadoAtencionJpaController implements Serializable {

    public LlamadoAtencionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LlamadoAtencion llamadoAtencion) throws PreexistingEntityException, Exception {
        if (llamadoAtencion.getMotivoLlamadoAtencionList() == null) {
            llamadoAtencion.setMotivoLlamadoAtencionList(new ArrayList<MotivoLlamadoAtencion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<MotivoLlamadoAtencion> attachedMotivoLlamadoAtencionList = new ArrayList<MotivoLlamadoAtencion>();
            for (MotivoLlamadoAtencion motivoLlamadoAtencionListMotivoLlamadoAtencionToAttach : llamadoAtencion.getMotivoLlamadoAtencionList()) {
                motivoLlamadoAtencionListMotivoLlamadoAtencionToAttach = em.getReference(motivoLlamadoAtencionListMotivoLlamadoAtencionToAttach.getClass(), motivoLlamadoAtencionListMotivoLlamadoAtencionToAttach.getId());
                attachedMotivoLlamadoAtencionList.add(motivoLlamadoAtencionListMotivoLlamadoAtencionToAttach);
            }
            llamadoAtencion.setMotivoLlamadoAtencionList(attachedMotivoLlamadoAtencionList);
            em.persist(llamadoAtencion);
            for (MotivoLlamadoAtencion motivoLlamadoAtencionListMotivoLlamadoAtencion : llamadoAtencion.getMotivoLlamadoAtencionList()) {
                LlamadoAtencion oldIdLlamadoAtencionOfMotivoLlamadoAtencionListMotivoLlamadoAtencion = motivoLlamadoAtencionListMotivoLlamadoAtencion.getIdLlamadoAtencion();
                motivoLlamadoAtencionListMotivoLlamadoAtencion.setIdLlamadoAtencion(llamadoAtencion);
                motivoLlamadoAtencionListMotivoLlamadoAtencion = em.merge(motivoLlamadoAtencionListMotivoLlamadoAtencion);
                if (oldIdLlamadoAtencionOfMotivoLlamadoAtencionListMotivoLlamadoAtencion != null) {
                    oldIdLlamadoAtencionOfMotivoLlamadoAtencionListMotivoLlamadoAtencion.getMotivoLlamadoAtencionList().remove(motivoLlamadoAtencionListMotivoLlamadoAtencion);
                    oldIdLlamadoAtencionOfMotivoLlamadoAtencionListMotivoLlamadoAtencion = em.merge(oldIdLlamadoAtencionOfMotivoLlamadoAtencionListMotivoLlamadoAtencion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLlamadoAtencion(llamadoAtencion.getId()) != null) {
                throw new PreexistingEntityException("LlamadoAtencion " + llamadoAtencion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LlamadoAtencion llamadoAtencion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LlamadoAtencion persistentLlamadoAtencion = em.find(LlamadoAtencion.class, llamadoAtencion.getId());
            List<MotivoLlamadoAtencion> motivoLlamadoAtencionListOld = persistentLlamadoAtencion.getMotivoLlamadoAtencionList();
            List<MotivoLlamadoAtencion> motivoLlamadoAtencionListNew = llamadoAtencion.getMotivoLlamadoAtencionList();
            List<MotivoLlamadoAtencion> attachedMotivoLlamadoAtencionListNew = new ArrayList<MotivoLlamadoAtencion>();
            for (MotivoLlamadoAtencion motivoLlamadoAtencionListNewMotivoLlamadoAtencionToAttach : motivoLlamadoAtencionListNew) {
                motivoLlamadoAtencionListNewMotivoLlamadoAtencionToAttach = em.getReference(motivoLlamadoAtencionListNewMotivoLlamadoAtencionToAttach.getClass(), motivoLlamadoAtencionListNewMotivoLlamadoAtencionToAttach.getId());
                attachedMotivoLlamadoAtencionListNew.add(motivoLlamadoAtencionListNewMotivoLlamadoAtencionToAttach);
            }
            motivoLlamadoAtencionListNew = attachedMotivoLlamadoAtencionListNew;
            llamadoAtencion.setMotivoLlamadoAtencionList(motivoLlamadoAtencionListNew);
            llamadoAtencion = em.merge(llamadoAtencion);
            for (MotivoLlamadoAtencion motivoLlamadoAtencionListOldMotivoLlamadoAtencion : motivoLlamadoAtencionListOld) {
                if (!motivoLlamadoAtencionListNew.contains(motivoLlamadoAtencionListOldMotivoLlamadoAtencion)) {
                    motivoLlamadoAtencionListOldMotivoLlamadoAtencion.setIdLlamadoAtencion(null);
                    motivoLlamadoAtencionListOldMotivoLlamadoAtencion = em.merge(motivoLlamadoAtencionListOldMotivoLlamadoAtencion);
                }
            }
            for (MotivoLlamadoAtencion motivoLlamadoAtencionListNewMotivoLlamadoAtencion : motivoLlamadoAtencionListNew) {
                if (!motivoLlamadoAtencionListOld.contains(motivoLlamadoAtencionListNewMotivoLlamadoAtencion)) {
                    LlamadoAtencion oldIdLlamadoAtencionOfMotivoLlamadoAtencionListNewMotivoLlamadoAtencion = motivoLlamadoAtencionListNewMotivoLlamadoAtencion.getIdLlamadoAtencion();
                    motivoLlamadoAtencionListNewMotivoLlamadoAtencion.setIdLlamadoAtencion(llamadoAtencion);
                    motivoLlamadoAtencionListNewMotivoLlamadoAtencion = em.merge(motivoLlamadoAtencionListNewMotivoLlamadoAtencion);
                    if (oldIdLlamadoAtencionOfMotivoLlamadoAtencionListNewMotivoLlamadoAtencion != null && !oldIdLlamadoAtencionOfMotivoLlamadoAtencionListNewMotivoLlamadoAtencion.equals(llamadoAtencion)) {
                        oldIdLlamadoAtencionOfMotivoLlamadoAtencionListNewMotivoLlamadoAtencion.getMotivoLlamadoAtencionList().remove(motivoLlamadoAtencionListNewMotivoLlamadoAtencion);
                        oldIdLlamadoAtencionOfMotivoLlamadoAtencionListNewMotivoLlamadoAtencion = em.merge(oldIdLlamadoAtencionOfMotivoLlamadoAtencionListNewMotivoLlamadoAtencion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = llamadoAtencion.getId();
                if (findLlamadoAtencion(id) == null) {
                    throw new NonexistentEntityException("The llamadoAtencion with id " + id + " no longer exists.");
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
            LlamadoAtencion llamadoAtencion;
            try {
                llamadoAtencion = em.getReference(LlamadoAtencion.class, id);
                llamadoAtencion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The llamadoAtencion with id " + id + " no longer exists.", enfe);
            }
            List<MotivoLlamadoAtencion> motivoLlamadoAtencionList = llamadoAtencion.getMotivoLlamadoAtencionList();
            for (MotivoLlamadoAtencion motivoLlamadoAtencionListMotivoLlamadoAtencion : motivoLlamadoAtencionList) {
                motivoLlamadoAtencionListMotivoLlamadoAtencion.setIdLlamadoAtencion(null);
                motivoLlamadoAtencionListMotivoLlamadoAtencion = em.merge(motivoLlamadoAtencionListMotivoLlamadoAtencion);
            }
            em.remove(llamadoAtencion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LlamadoAtencion> findLlamadoAtencionEntities() {
        return findLlamadoAtencionEntities(true, -1, -1);
    }

    public List<LlamadoAtencion> findLlamadoAtencionEntities(int maxResults, int firstResult) {
        return findLlamadoAtencionEntities(false, maxResults, firstResult);
    }

    private List<LlamadoAtencion> findLlamadoAtencionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LlamadoAtencion.class));
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

    public LlamadoAtencion findLlamadoAtencion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LlamadoAtencion.class, id);
        } finally {
            em.close();
        }
    }

    public int getLlamadoAtencionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LlamadoAtencion> rt = cq.from(LlamadoAtencion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
