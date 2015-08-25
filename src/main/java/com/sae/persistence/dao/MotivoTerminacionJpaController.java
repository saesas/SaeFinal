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
import com.sae.persistence.domain.ContratoTercero;
import com.sae.persistence.domain.MotivoTerminacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class MotivoTerminacionJpaController implements Serializable {

    public MotivoTerminacionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MotivoTerminacion motivoTerminacion) throws PreexistingEntityException, Exception {
        if (motivoTerminacion.getContratoTerceroList() == null) {
            motivoTerminacion.setContratoTerceroList(new ArrayList<ContratoTercero>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ContratoTercero> attachedContratoTerceroList = new ArrayList<ContratoTercero>();
            for (ContratoTercero contratoTerceroListContratoTerceroToAttach : motivoTerminacion.getContratoTerceroList()) {
                contratoTerceroListContratoTerceroToAttach = em.getReference(contratoTerceroListContratoTerceroToAttach.getClass(), contratoTerceroListContratoTerceroToAttach.getId());
                attachedContratoTerceroList.add(contratoTerceroListContratoTerceroToAttach);
            }
            motivoTerminacion.setContratoTerceroList(attachedContratoTerceroList);
            em.persist(motivoTerminacion);
            for (ContratoTercero contratoTerceroListContratoTercero : motivoTerminacion.getContratoTerceroList()) {
                MotivoTerminacion oldIdMotivoTerminacionOfContratoTerceroListContratoTercero = contratoTerceroListContratoTercero.getIdMotivoTerminacion();
                contratoTerceroListContratoTercero.setIdMotivoTerminacion(motivoTerminacion);
                contratoTerceroListContratoTercero = em.merge(contratoTerceroListContratoTercero);
                if (oldIdMotivoTerminacionOfContratoTerceroListContratoTercero != null) {
                    oldIdMotivoTerminacionOfContratoTerceroListContratoTercero.getContratoTerceroList().remove(contratoTerceroListContratoTercero);
                    oldIdMotivoTerminacionOfContratoTerceroListContratoTercero = em.merge(oldIdMotivoTerminacionOfContratoTerceroListContratoTercero);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMotivoTerminacion(motivoTerminacion.getId()) != null) {
                throw new PreexistingEntityException("MotivoTerminacion " + motivoTerminacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MotivoTerminacion motivoTerminacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MotivoTerminacion persistentMotivoTerminacion = em.find(MotivoTerminacion.class, motivoTerminacion.getId());
            List<ContratoTercero> contratoTerceroListOld = persistentMotivoTerminacion.getContratoTerceroList();
            List<ContratoTercero> contratoTerceroListNew = motivoTerminacion.getContratoTerceroList();
            List<ContratoTercero> attachedContratoTerceroListNew = new ArrayList<ContratoTercero>();
            for (ContratoTercero contratoTerceroListNewContratoTerceroToAttach : contratoTerceroListNew) {
                contratoTerceroListNewContratoTerceroToAttach = em.getReference(contratoTerceroListNewContratoTerceroToAttach.getClass(), contratoTerceroListNewContratoTerceroToAttach.getId());
                attachedContratoTerceroListNew.add(contratoTerceroListNewContratoTerceroToAttach);
            }
            contratoTerceroListNew = attachedContratoTerceroListNew;
            motivoTerminacion.setContratoTerceroList(contratoTerceroListNew);
            motivoTerminacion = em.merge(motivoTerminacion);
            for (ContratoTercero contratoTerceroListOldContratoTercero : contratoTerceroListOld) {
                if (!contratoTerceroListNew.contains(contratoTerceroListOldContratoTercero)) {
                    contratoTerceroListOldContratoTercero.setIdMotivoTerminacion(null);
                    contratoTerceroListOldContratoTercero = em.merge(contratoTerceroListOldContratoTercero);
                }
            }
            for (ContratoTercero contratoTerceroListNewContratoTercero : contratoTerceroListNew) {
                if (!contratoTerceroListOld.contains(contratoTerceroListNewContratoTercero)) {
                    MotivoTerminacion oldIdMotivoTerminacionOfContratoTerceroListNewContratoTercero = contratoTerceroListNewContratoTercero.getIdMotivoTerminacion();
                    contratoTerceroListNewContratoTercero.setIdMotivoTerminacion(motivoTerminacion);
                    contratoTerceroListNewContratoTercero = em.merge(contratoTerceroListNewContratoTercero);
                    if (oldIdMotivoTerminacionOfContratoTerceroListNewContratoTercero != null && !oldIdMotivoTerminacionOfContratoTerceroListNewContratoTercero.equals(motivoTerminacion)) {
                        oldIdMotivoTerminacionOfContratoTerceroListNewContratoTercero.getContratoTerceroList().remove(contratoTerceroListNewContratoTercero);
                        oldIdMotivoTerminacionOfContratoTerceroListNewContratoTercero = em.merge(oldIdMotivoTerminacionOfContratoTerceroListNewContratoTercero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = motivoTerminacion.getId();
                if (findMotivoTerminacion(id) == null) {
                    throw new NonexistentEntityException("The motivoTerminacion with id " + id + " no longer exists.");
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
            MotivoTerminacion motivoTerminacion;
            try {
                motivoTerminacion = em.getReference(MotivoTerminacion.class, id);
                motivoTerminacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The motivoTerminacion with id " + id + " no longer exists.", enfe);
            }
            List<ContratoTercero> contratoTerceroList = motivoTerminacion.getContratoTerceroList();
            for (ContratoTercero contratoTerceroListContratoTercero : contratoTerceroList) {
                contratoTerceroListContratoTercero.setIdMotivoTerminacion(null);
                contratoTerceroListContratoTercero = em.merge(contratoTerceroListContratoTercero);
            }
            em.remove(motivoTerminacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MotivoTerminacion> findMotivoTerminacionEntities() {
        return findMotivoTerminacionEntities(true, -1, -1);
    }

    public List<MotivoTerminacion> findMotivoTerminacionEntities(int maxResults, int firstResult) {
        return findMotivoTerminacionEntities(false, maxResults, firstResult);
    }

    private List<MotivoTerminacion> findMotivoTerminacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MotivoTerminacion.class));
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

    public MotivoTerminacion findMotivoTerminacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MotivoTerminacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getMotivoTerminacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MotivoTerminacion> rt = cq.from(MotivoTerminacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
