/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.AdjuntoBitacora;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Bitacora;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class AdjuntoBitacoraJpaController implements Serializable {

    public AdjuntoBitacoraJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AdjuntoBitacora adjuntoBitacora) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bitacora idBitacora = adjuntoBitacora.getIdBitacora();
            if (idBitacora != null) {
                idBitacora = em.getReference(idBitacora.getClass(), idBitacora.getId());
                adjuntoBitacora.setIdBitacora(idBitacora);
            }
            em.persist(adjuntoBitacora);
            if (idBitacora != null) {
                idBitacora.getAdjuntoBitacoraList().add(adjuntoBitacora);
                idBitacora = em.merge(idBitacora);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAdjuntoBitacora(adjuntoBitacora.getId()) != null) {
                throw new PreexistingEntityException("AdjuntoBitacora " + adjuntoBitacora + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AdjuntoBitacora adjuntoBitacora) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AdjuntoBitacora persistentAdjuntoBitacora = em.find(AdjuntoBitacora.class, adjuntoBitacora.getId());
            Bitacora idBitacoraOld = persistentAdjuntoBitacora.getIdBitacora();
            Bitacora idBitacoraNew = adjuntoBitacora.getIdBitacora();
            if (idBitacoraNew != null) {
                idBitacoraNew = em.getReference(idBitacoraNew.getClass(), idBitacoraNew.getId());
                adjuntoBitacora.setIdBitacora(idBitacoraNew);
            }
            adjuntoBitacora = em.merge(adjuntoBitacora);
            if (idBitacoraOld != null && !idBitacoraOld.equals(idBitacoraNew)) {
                idBitacoraOld.getAdjuntoBitacoraList().remove(adjuntoBitacora);
                idBitacoraOld = em.merge(idBitacoraOld);
            }
            if (idBitacoraNew != null && !idBitacoraNew.equals(idBitacoraOld)) {
                idBitacoraNew.getAdjuntoBitacoraList().add(adjuntoBitacora);
                idBitacoraNew = em.merge(idBitacoraNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = adjuntoBitacora.getId();
                if (findAdjuntoBitacora(id) == null) {
                    throw new NonexistentEntityException("The adjuntoBitacora with id " + id + " no longer exists.");
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
            AdjuntoBitacora adjuntoBitacora;
            try {
                adjuntoBitacora = em.getReference(AdjuntoBitacora.class, id);
                adjuntoBitacora.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The adjuntoBitacora with id " + id + " no longer exists.", enfe);
            }
            Bitacora idBitacora = adjuntoBitacora.getIdBitacora();
            if (idBitacora != null) {
                idBitacora.getAdjuntoBitacoraList().remove(adjuntoBitacora);
                idBitacora = em.merge(idBitacora);
            }
            em.remove(adjuntoBitacora);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AdjuntoBitacora> findAdjuntoBitacoraEntities() {
        return findAdjuntoBitacoraEntities(true, -1, -1);
    }

    public List<AdjuntoBitacora> findAdjuntoBitacoraEntities(int maxResults, int firstResult) {
        return findAdjuntoBitacoraEntities(false, maxResults, firstResult);
    }

    private List<AdjuntoBitacora> findAdjuntoBitacoraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AdjuntoBitacora.class));
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

    public AdjuntoBitacora findAdjuntoBitacora(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AdjuntoBitacora.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdjuntoBitacoraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AdjuntoBitacora> rt = cq.from(AdjuntoBitacora.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
