/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.AiuEspecificoPresupuesto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Presupuesto;
import com.sae.persistence.domain.ItemAiu;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class AiuEspecificoPresupuestoJpaController implements Serializable {

    public AiuEspecificoPresupuestoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AiuEspecificoPresupuesto aiuEspecificoPresupuesto) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Presupuesto idPresupuesto = aiuEspecificoPresupuesto.getIdPresupuesto();
            if (idPresupuesto != null) {
                idPresupuesto = em.getReference(idPresupuesto.getClass(), idPresupuesto.getId());
                aiuEspecificoPresupuesto.setIdPresupuesto(idPresupuesto);
            }
            ItemAiu idItemAiu = aiuEspecificoPresupuesto.getIdItemAiu();
            if (idItemAiu != null) {
                idItemAiu = em.getReference(idItemAiu.getClass(), idItemAiu.getId());
                aiuEspecificoPresupuesto.setIdItemAiu(idItemAiu);
            }
            em.persist(aiuEspecificoPresupuesto);
            if (idPresupuesto != null) {
                idPresupuesto.getAiuEspecificoPresupuestoList().add(aiuEspecificoPresupuesto);
                idPresupuesto = em.merge(idPresupuesto);
            }
            if (idItemAiu != null) {
                idItemAiu.getAiuEspecificoPresupuestoList().add(aiuEspecificoPresupuesto);
                idItemAiu = em.merge(idItemAiu);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAiuEspecificoPresupuesto(aiuEspecificoPresupuesto.getId()) != null) {
                throw new PreexistingEntityException("AiuEspecificoPresupuesto " + aiuEspecificoPresupuesto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AiuEspecificoPresupuesto aiuEspecificoPresupuesto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AiuEspecificoPresupuesto persistentAiuEspecificoPresupuesto = em.find(AiuEspecificoPresupuesto.class, aiuEspecificoPresupuesto.getId());
            Presupuesto idPresupuestoOld = persistentAiuEspecificoPresupuesto.getIdPresupuesto();
            Presupuesto idPresupuestoNew = aiuEspecificoPresupuesto.getIdPresupuesto();
            ItemAiu idItemAiuOld = persistentAiuEspecificoPresupuesto.getIdItemAiu();
            ItemAiu idItemAiuNew = aiuEspecificoPresupuesto.getIdItemAiu();
            if (idPresupuestoNew != null) {
                idPresupuestoNew = em.getReference(idPresupuestoNew.getClass(), idPresupuestoNew.getId());
                aiuEspecificoPresupuesto.setIdPresupuesto(idPresupuestoNew);
            }
            if (idItemAiuNew != null) {
                idItemAiuNew = em.getReference(idItemAiuNew.getClass(), idItemAiuNew.getId());
                aiuEspecificoPresupuesto.setIdItemAiu(idItemAiuNew);
            }
            aiuEspecificoPresupuesto = em.merge(aiuEspecificoPresupuesto);
            if (idPresupuestoOld != null && !idPresupuestoOld.equals(idPresupuestoNew)) {
                idPresupuestoOld.getAiuEspecificoPresupuestoList().remove(aiuEspecificoPresupuesto);
                idPresupuestoOld = em.merge(idPresupuestoOld);
            }
            if (idPresupuestoNew != null && !idPresupuestoNew.equals(idPresupuestoOld)) {
                idPresupuestoNew.getAiuEspecificoPresupuestoList().add(aiuEspecificoPresupuesto);
                idPresupuestoNew = em.merge(idPresupuestoNew);
            }
            if (idItemAiuOld != null && !idItemAiuOld.equals(idItemAiuNew)) {
                idItemAiuOld.getAiuEspecificoPresupuestoList().remove(aiuEspecificoPresupuesto);
                idItemAiuOld = em.merge(idItemAiuOld);
            }
            if (idItemAiuNew != null && !idItemAiuNew.equals(idItemAiuOld)) {
                idItemAiuNew.getAiuEspecificoPresupuestoList().add(aiuEspecificoPresupuesto);
                idItemAiuNew = em.merge(idItemAiuNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = aiuEspecificoPresupuesto.getId();
                if (findAiuEspecificoPresupuesto(id) == null) {
                    throw new NonexistentEntityException("The aiuEspecificoPresupuesto with id " + id + " no longer exists.");
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
            AiuEspecificoPresupuesto aiuEspecificoPresupuesto;
            try {
                aiuEspecificoPresupuesto = em.getReference(AiuEspecificoPresupuesto.class, id);
                aiuEspecificoPresupuesto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aiuEspecificoPresupuesto with id " + id + " no longer exists.", enfe);
            }
            Presupuesto idPresupuesto = aiuEspecificoPresupuesto.getIdPresupuesto();
            if (idPresupuesto != null) {
                idPresupuesto.getAiuEspecificoPresupuestoList().remove(aiuEspecificoPresupuesto);
                idPresupuesto = em.merge(idPresupuesto);
            }
            ItemAiu idItemAiu = aiuEspecificoPresupuesto.getIdItemAiu();
            if (idItemAiu != null) {
                idItemAiu.getAiuEspecificoPresupuestoList().remove(aiuEspecificoPresupuesto);
                idItemAiu = em.merge(idItemAiu);
            }
            em.remove(aiuEspecificoPresupuesto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AiuEspecificoPresupuesto> findAiuEspecificoPresupuestoEntities() {
        return findAiuEspecificoPresupuestoEntities(true, -1, -1);
    }

    public List<AiuEspecificoPresupuesto> findAiuEspecificoPresupuestoEntities(int maxResults, int firstResult) {
        return findAiuEspecificoPresupuestoEntities(false, maxResults, firstResult);
    }

    private List<AiuEspecificoPresupuesto> findAiuEspecificoPresupuestoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AiuEspecificoPresupuesto.class));
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

    public AiuEspecificoPresupuesto findAiuEspecificoPresupuesto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AiuEspecificoPresupuesto.class, id);
        } finally {
            em.close();
        }
    }

    public int getAiuEspecificoPresupuestoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AiuEspecificoPresupuesto> rt = cq.from(AiuEspecificoPresupuesto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
