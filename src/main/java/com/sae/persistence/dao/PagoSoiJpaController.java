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
import com.sae.persistence.domain.AportePagoSoi;
import com.sae.persistence.domain.PagoSoi;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class PagoSoiJpaController implements Serializable {

    public PagoSoiJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PagoSoi pagoSoi) throws PreexistingEntityException, Exception {
        if (pagoSoi.getAportePagoSoiList() == null) {
            pagoSoi.setAportePagoSoiList(new ArrayList<AportePagoSoi>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<AportePagoSoi> attachedAportePagoSoiList = new ArrayList<AportePagoSoi>();
            for (AportePagoSoi aportePagoSoiListAportePagoSoiToAttach : pagoSoi.getAportePagoSoiList()) {
                aportePagoSoiListAportePagoSoiToAttach = em.getReference(aportePagoSoiListAportePagoSoiToAttach.getClass(), aportePagoSoiListAportePagoSoiToAttach.getId());
                attachedAportePagoSoiList.add(aportePagoSoiListAportePagoSoiToAttach);
            }
            pagoSoi.setAportePagoSoiList(attachedAportePagoSoiList);
            em.persist(pagoSoi);
            for (AportePagoSoi aportePagoSoiListAportePagoSoi : pagoSoi.getAportePagoSoiList()) {
                PagoSoi oldIdPagoSoiOfAportePagoSoiListAportePagoSoi = aportePagoSoiListAportePagoSoi.getIdPagoSoi();
                aportePagoSoiListAportePagoSoi.setIdPagoSoi(pagoSoi);
                aportePagoSoiListAportePagoSoi = em.merge(aportePagoSoiListAportePagoSoi);
                if (oldIdPagoSoiOfAportePagoSoiListAportePagoSoi != null) {
                    oldIdPagoSoiOfAportePagoSoiListAportePagoSoi.getAportePagoSoiList().remove(aportePagoSoiListAportePagoSoi);
                    oldIdPagoSoiOfAportePagoSoiListAportePagoSoi = em.merge(oldIdPagoSoiOfAportePagoSoiListAportePagoSoi);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPagoSoi(pagoSoi.getId()) != null) {
                throw new PreexistingEntityException("PagoSoi " + pagoSoi + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PagoSoi pagoSoi) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PagoSoi persistentPagoSoi = em.find(PagoSoi.class, pagoSoi.getId());
            List<AportePagoSoi> aportePagoSoiListOld = persistentPagoSoi.getAportePagoSoiList();
            List<AportePagoSoi> aportePagoSoiListNew = pagoSoi.getAportePagoSoiList();
            List<AportePagoSoi> attachedAportePagoSoiListNew = new ArrayList<AportePagoSoi>();
            for (AportePagoSoi aportePagoSoiListNewAportePagoSoiToAttach : aportePagoSoiListNew) {
                aportePagoSoiListNewAportePagoSoiToAttach = em.getReference(aportePagoSoiListNewAportePagoSoiToAttach.getClass(), aportePagoSoiListNewAportePagoSoiToAttach.getId());
                attachedAportePagoSoiListNew.add(aportePagoSoiListNewAportePagoSoiToAttach);
            }
            aportePagoSoiListNew = attachedAportePagoSoiListNew;
            pagoSoi.setAportePagoSoiList(aportePagoSoiListNew);
            pagoSoi = em.merge(pagoSoi);
            for (AportePagoSoi aportePagoSoiListOldAportePagoSoi : aportePagoSoiListOld) {
                if (!aportePagoSoiListNew.contains(aportePagoSoiListOldAportePagoSoi)) {
                    aportePagoSoiListOldAportePagoSoi.setIdPagoSoi(null);
                    aportePagoSoiListOldAportePagoSoi = em.merge(aportePagoSoiListOldAportePagoSoi);
                }
            }
            for (AportePagoSoi aportePagoSoiListNewAportePagoSoi : aportePagoSoiListNew) {
                if (!aportePagoSoiListOld.contains(aportePagoSoiListNewAportePagoSoi)) {
                    PagoSoi oldIdPagoSoiOfAportePagoSoiListNewAportePagoSoi = aportePagoSoiListNewAportePagoSoi.getIdPagoSoi();
                    aportePagoSoiListNewAportePagoSoi.setIdPagoSoi(pagoSoi);
                    aportePagoSoiListNewAportePagoSoi = em.merge(aportePagoSoiListNewAportePagoSoi);
                    if (oldIdPagoSoiOfAportePagoSoiListNewAportePagoSoi != null && !oldIdPagoSoiOfAportePagoSoiListNewAportePagoSoi.equals(pagoSoi)) {
                        oldIdPagoSoiOfAportePagoSoiListNewAportePagoSoi.getAportePagoSoiList().remove(aportePagoSoiListNewAportePagoSoi);
                        oldIdPagoSoiOfAportePagoSoiListNewAportePagoSoi = em.merge(oldIdPagoSoiOfAportePagoSoiListNewAportePagoSoi);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagoSoi.getId();
                if (findPagoSoi(id) == null) {
                    throw new NonexistentEntityException("The pagoSoi with id " + id + " no longer exists.");
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
            PagoSoi pagoSoi;
            try {
                pagoSoi = em.getReference(PagoSoi.class, id);
                pagoSoi.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagoSoi with id " + id + " no longer exists.", enfe);
            }
            List<AportePagoSoi> aportePagoSoiList = pagoSoi.getAportePagoSoiList();
            for (AportePagoSoi aportePagoSoiListAportePagoSoi : aportePagoSoiList) {
                aportePagoSoiListAportePagoSoi.setIdPagoSoi(null);
                aportePagoSoiListAportePagoSoi = em.merge(aportePagoSoiListAportePagoSoi);
            }
            em.remove(pagoSoi);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PagoSoi> findPagoSoiEntities() {
        return findPagoSoiEntities(true, -1, -1);
    }

    public List<PagoSoi> findPagoSoiEntities(int maxResults, int firstResult) {
        return findPagoSoiEntities(false, maxResults, firstResult);
    }

    private List<PagoSoi> findPagoSoiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PagoSoi.class));
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

    public PagoSoi findPagoSoi(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PagoSoi.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoSoiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PagoSoi> rt = cq.from(PagoSoi.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
