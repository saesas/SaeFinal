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
import com.sae.persistence.domain.GrupoNominal;
import com.sae.persistence.domain.PeriodoPago;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class PeriodoPagoJpaController implements Serializable {

    public PeriodoPagoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PeriodoPago periodoPago) throws PreexistingEntityException, Exception {
        if (periodoPago.getGrupoNominalList() == null) {
            periodoPago.setGrupoNominalList(new ArrayList<GrupoNominal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<GrupoNominal> attachedGrupoNominalList = new ArrayList<GrupoNominal>();
            for (GrupoNominal grupoNominalListGrupoNominalToAttach : periodoPago.getGrupoNominalList()) {
                grupoNominalListGrupoNominalToAttach = em.getReference(grupoNominalListGrupoNominalToAttach.getClass(), grupoNominalListGrupoNominalToAttach.getId());
                attachedGrupoNominalList.add(grupoNominalListGrupoNominalToAttach);
            }
            periodoPago.setGrupoNominalList(attachedGrupoNominalList);
            em.persist(periodoPago);
            for (GrupoNominal grupoNominalListGrupoNominal : periodoPago.getGrupoNominalList()) {
                PeriodoPago oldIdPeriodoOfGrupoNominalListGrupoNominal = grupoNominalListGrupoNominal.getIdPeriodo();
                grupoNominalListGrupoNominal.setIdPeriodo(periodoPago);
                grupoNominalListGrupoNominal = em.merge(grupoNominalListGrupoNominal);
                if (oldIdPeriodoOfGrupoNominalListGrupoNominal != null) {
                    oldIdPeriodoOfGrupoNominalListGrupoNominal.getGrupoNominalList().remove(grupoNominalListGrupoNominal);
                    oldIdPeriodoOfGrupoNominalListGrupoNominal = em.merge(oldIdPeriodoOfGrupoNominalListGrupoNominal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPeriodoPago(periodoPago.getId()) != null) {
                throw new PreexistingEntityException("PeriodoPago " + periodoPago + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PeriodoPago periodoPago) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PeriodoPago persistentPeriodoPago = em.find(PeriodoPago.class, periodoPago.getId());
            List<GrupoNominal> grupoNominalListOld = persistentPeriodoPago.getGrupoNominalList();
            List<GrupoNominal> grupoNominalListNew = periodoPago.getGrupoNominalList();
            List<GrupoNominal> attachedGrupoNominalListNew = new ArrayList<GrupoNominal>();
            for (GrupoNominal grupoNominalListNewGrupoNominalToAttach : grupoNominalListNew) {
                grupoNominalListNewGrupoNominalToAttach = em.getReference(grupoNominalListNewGrupoNominalToAttach.getClass(), grupoNominalListNewGrupoNominalToAttach.getId());
                attachedGrupoNominalListNew.add(grupoNominalListNewGrupoNominalToAttach);
            }
            grupoNominalListNew = attachedGrupoNominalListNew;
            periodoPago.setGrupoNominalList(grupoNominalListNew);
            periodoPago = em.merge(periodoPago);
            for (GrupoNominal grupoNominalListOldGrupoNominal : grupoNominalListOld) {
                if (!grupoNominalListNew.contains(grupoNominalListOldGrupoNominal)) {
                    grupoNominalListOldGrupoNominal.setIdPeriodo(null);
                    grupoNominalListOldGrupoNominal = em.merge(grupoNominalListOldGrupoNominal);
                }
            }
            for (GrupoNominal grupoNominalListNewGrupoNominal : grupoNominalListNew) {
                if (!grupoNominalListOld.contains(grupoNominalListNewGrupoNominal)) {
                    PeriodoPago oldIdPeriodoOfGrupoNominalListNewGrupoNominal = grupoNominalListNewGrupoNominal.getIdPeriodo();
                    grupoNominalListNewGrupoNominal.setIdPeriodo(periodoPago);
                    grupoNominalListNewGrupoNominal = em.merge(grupoNominalListNewGrupoNominal);
                    if (oldIdPeriodoOfGrupoNominalListNewGrupoNominal != null && !oldIdPeriodoOfGrupoNominalListNewGrupoNominal.equals(periodoPago)) {
                        oldIdPeriodoOfGrupoNominalListNewGrupoNominal.getGrupoNominalList().remove(grupoNominalListNewGrupoNominal);
                        oldIdPeriodoOfGrupoNominalListNewGrupoNominal = em.merge(oldIdPeriodoOfGrupoNominalListNewGrupoNominal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = periodoPago.getId();
                if (findPeriodoPago(id) == null) {
                    throw new NonexistentEntityException("The periodoPago with id " + id + " no longer exists.");
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
            PeriodoPago periodoPago;
            try {
                periodoPago = em.getReference(PeriodoPago.class, id);
                periodoPago.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The periodoPago with id " + id + " no longer exists.", enfe);
            }
            List<GrupoNominal> grupoNominalList = periodoPago.getGrupoNominalList();
            for (GrupoNominal grupoNominalListGrupoNominal : grupoNominalList) {
                grupoNominalListGrupoNominal.setIdPeriodo(null);
                grupoNominalListGrupoNominal = em.merge(grupoNominalListGrupoNominal);
            }
            em.remove(periodoPago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PeriodoPago> findPeriodoPagoEntities() {
        return findPeriodoPagoEntities(true, -1, -1);
    }

    public List<PeriodoPago> findPeriodoPagoEntities(int maxResults, int firstResult) {
        return findPeriodoPagoEntities(false, maxResults, firstResult);
    }

    private List<PeriodoPago> findPeriodoPagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PeriodoPago.class));
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

    public PeriodoPago findPeriodoPago(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PeriodoPago.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeriodoPagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PeriodoPago> rt = cq.from(PeriodoPago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
