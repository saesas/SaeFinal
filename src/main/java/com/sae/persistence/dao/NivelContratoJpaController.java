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
import com.sae.persistence.domain.NivelContrato;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class NivelContratoJpaController implements Serializable {

    public NivelContratoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NivelContrato nivelContrato) throws PreexistingEntityException, Exception {
        if (nivelContrato.getContratoTerceroList() == null) {
            nivelContrato.setContratoTerceroList(new ArrayList<ContratoTercero>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ContratoTercero> attachedContratoTerceroList = new ArrayList<ContratoTercero>();
            for (ContratoTercero contratoTerceroListContratoTerceroToAttach : nivelContrato.getContratoTerceroList()) {
                contratoTerceroListContratoTerceroToAttach = em.getReference(contratoTerceroListContratoTerceroToAttach.getClass(), contratoTerceroListContratoTerceroToAttach.getId());
                attachedContratoTerceroList.add(contratoTerceroListContratoTerceroToAttach);
            }
            nivelContrato.setContratoTerceroList(attachedContratoTerceroList);
            em.persist(nivelContrato);
            for (ContratoTercero contratoTerceroListContratoTercero : nivelContrato.getContratoTerceroList()) {
                NivelContrato oldIdNivelContratoOfContratoTerceroListContratoTercero = contratoTerceroListContratoTercero.getIdNivelContrato();
                contratoTerceroListContratoTercero.setIdNivelContrato(nivelContrato);
                contratoTerceroListContratoTercero = em.merge(contratoTerceroListContratoTercero);
                if (oldIdNivelContratoOfContratoTerceroListContratoTercero != null) {
                    oldIdNivelContratoOfContratoTerceroListContratoTercero.getContratoTerceroList().remove(contratoTerceroListContratoTercero);
                    oldIdNivelContratoOfContratoTerceroListContratoTercero = em.merge(oldIdNivelContratoOfContratoTerceroListContratoTercero);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNivelContrato(nivelContrato.getId()) != null) {
                throw new PreexistingEntityException("NivelContrato " + nivelContrato + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NivelContrato nivelContrato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NivelContrato persistentNivelContrato = em.find(NivelContrato.class, nivelContrato.getId());
            List<ContratoTercero> contratoTerceroListOld = persistentNivelContrato.getContratoTerceroList();
            List<ContratoTercero> contratoTerceroListNew = nivelContrato.getContratoTerceroList();
            List<ContratoTercero> attachedContratoTerceroListNew = new ArrayList<ContratoTercero>();
            for (ContratoTercero contratoTerceroListNewContratoTerceroToAttach : contratoTerceroListNew) {
                contratoTerceroListNewContratoTerceroToAttach = em.getReference(contratoTerceroListNewContratoTerceroToAttach.getClass(), contratoTerceroListNewContratoTerceroToAttach.getId());
                attachedContratoTerceroListNew.add(contratoTerceroListNewContratoTerceroToAttach);
            }
            contratoTerceroListNew = attachedContratoTerceroListNew;
            nivelContrato.setContratoTerceroList(contratoTerceroListNew);
            nivelContrato = em.merge(nivelContrato);
            for (ContratoTercero contratoTerceroListOldContratoTercero : contratoTerceroListOld) {
                if (!contratoTerceroListNew.contains(contratoTerceroListOldContratoTercero)) {
                    contratoTerceroListOldContratoTercero.setIdNivelContrato(null);
                    contratoTerceroListOldContratoTercero = em.merge(contratoTerceroListOldContratoTercero);
                }
            }
            for (ContratoTercero contratoTerceroListNewContratoTercero : contratoTerceroListNew) {
                if (!contratoTerceroListOld.contains(contratoTerceroListNewContratoTercero)) {
                    NivelContrato oldIdNivelContratoOfContratoTerceroListNewContratoTercero = contratoTerceroListNewContratoTercero.getIdNivelContrato();
                    contratoTerceroListNewContratoTercero.setIdNivelContrato(nivelContrato);
                    contratoTerceroListNewContratoTercero = em.merge(contratoTerceroListNewContratoTercero);
                    if (oldIdNivelContratoOfContratoTerceroListNewContratoTercero != null && !oldIdNivelContratoOfContratoTerceroListNewContratoTercero.equals(nivelContrato)) {
                        oldIdNivelContratoOfContratoTerceroListNewContratoTercero.getContratoTerceroList().remove(contratoTerceroListNewContratoTercero);
                        oldIdNivelContratoOfContratoTerceroListNewContratoTercero = em.merge(oldIdNivelContratoOfContratoTerceroListNewContratoTercero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = nivelContrato.getId();
                if (findNivelContrato(id) == null) {
                    throw new NonexistentEntityException("The nivelContrato with id " + id + " no longer exists.");
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
            NivelContrato nivelContrato;
            try {
                nivelContrato = em.getReference(NivelContrato.class, id);
                nivelContrato.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nivelContrato with id " + id + " no longer exists.", enfe);
            }
            List<ContratoTercero> contratoTerceroList = nivelContrato.getContratoTerceroList();
            for (ContratoTercero contratoTerceroListContratoTercero : contratoTerceroList) {
                contratoTerceroListContratoTercero.setIdNivelContrato(null);
                contratoTerceroListContratoTercero = em.merge(contratoTerceroListContratoTercero);
            }
            em.remove(nivelContrato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NivelContrato> findNivelContratoEntities() {
        return findNivelContratoEntities(true, -1, -1);
    }

    public List<NivelContrato> findNivelContratoEntities(int maxResults, int firstResult) {
        return findNivelContratoEntities(false, maxResults, firstResult);
    }

    private List<NivelContrato> findNivelContratoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NivelContrato.class));
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

    public NivelContrato findNivelContrato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NivelContrato.class, id);
        } finally {
            em.close();
        }
    }

    public int getNivelContratoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NivelContrato> rt = cq.from(NivelContrato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
