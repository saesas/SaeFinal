/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.AportePagoSoi;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.PagoSoi;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class AportePagoSoiJpaController implements Serializable {

    public AportePagoSoiJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AportePagoSoi aportePagoSoi) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PagoSoi idPagoSoi = aportePagoSoi.getIdPagoSoi();
            if (idPagoSoi != null) {
                idPagoSoi = em.getReference(idPagoSoi.getClass(), idPagoSoi.getId());
                aportePagoSoi.setIdPagoSoi(idPagoSoi);
            }
            em.persist(aportePagoSoi);
            if (idPagoSoi != null) {
                idPagoSoi.getAportePagoSoiList().add(aportePagoSoi);
                idPagoSoi = em.merge(idPagoSoi);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAportePagoSoi(aportePagoSoi.getId()) != null) {
                throw new PreexistingEntityException("AportePagoSoi " + aportePagoSoi + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AportePagoSoi aportePagoSoi) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AportePagoSoi persistentAportePagoSoi = em.find(AportePagoSoi.class, aportePagoSoi.getId());
            PagoSoi idPagoSoiOld = persistentAportePagoSoi.getIdPagoSoi();
            PagoSoi idPagoSoiNew = aportePagoSoi.getIdPagoSoi();
            if (idPagoSoiNew != null) {
                idPagoSoiNew = em.getReference(idPagoSoiNew.getClass(), idPagoSoiNew.getId());
                aportePagoSoi.setIdPagoSoi(idPagoSoiNew);
            }
            aportePagoSoi = em.merge(aportePagoSoi);
            if (idPagoSoiOld != null && !idPagoSoiOld.equals(idPagoSoiNew)) {
                idPagoSoiOld.getAportePagoSoiList().remove(aportePagoSoi);
                idPagoSoiOld = em.merge(idPagoSoiOld);
            }
            if (idPagoSoiNew != null && !idPagoSoiNew.equals(idPagoSoiOld)) {
                idPagoSoiNew.getAportePagoSoiList().add(aportePagoSoi);
                idPagoSoiNew = em.merge(idPagoSoiNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = aportePagoSoi.getId();
                if (findAportePagoSoi(id) == null) {
                    throw new NonexistentEntityException("The aportePagoSoi with id " + id + " no longer exists.");
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
            AportePagoSoi aportePagoSoi;
            try {
                aportePagoSoi = em.getReference(AportePagoSoi.class, id);
                aportePagoSoi.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aportePagoSoi with id " + id + " no longer exists.", enfe);
            }
            PagoSoi idPagoSoi = aportePagoSoi.getIdPagoSoi();
            if (idPagoSoi != null) {
                idPagoSoi.getAportePagoSoiList().remove(aportePagoSoi);
                idPagoSoi = em.merge(idPagoSoi);
            }
            em.remove(aportePagoSoi);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AportePagoSoi> findAportePagoSoiEntities() {
        return findAportePagoSoiEntities(true, -1, -1);
    }

    public List<AportePagoSoi> findAportePagoSoiEntities(int maxResults, int firstResult) {
        return findAportePagoSoiEntities(false, maxResults, firstResult);
    }

    private List<AportePagoSoi> findAportePagoSoiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AportePagoSoi.class));
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

    public AportePagoSoi findAportePagoSoi(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AportePagoSoi.class, id);
        } finally {
            em.close();
        }
    }

    public int getAportePagoSoiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AportePagoSoi> rt = cq.from(AportePagoSoi.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
