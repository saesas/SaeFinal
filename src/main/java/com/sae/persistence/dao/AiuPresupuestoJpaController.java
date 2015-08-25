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
import com.sae.persistence.domain.Presupuesto;
import com.sae.persistence.domain.Aiu;
import com.sae.persistence.domain.AiuPresupuesto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class AiuPresupuestoJpaController implements Serializable {

    public AiuPresupuestoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AiuPresupuesto aiuPresupuesto) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Presupuesto idPresupuesto = aiuPresupuesto.getIdPresupuesto();
            if (idPresupuesto != null) {
                idPresupuesto = em.getReference(idPresupuesto.getClass(), idPresupuesto.getId());
                aiuPresupuesto.setIdPresupuesto(idPresupuesto);
            }
            Aiu idTipoAiu = aiuPresupuesto.getIdTipoAiu();
            if (idTipoAiu != null) {
                idTipoAiu = em.getReference(idTipoAiu.getClass(), idTipoAiu.getId());
                aiuPresupuesto.setIdTipoAiu(idTipoAiu);
            }
            em.persist(aiuPresupuesto);
            if (idPresupuesto != null) {
                idPresupuesto.getAiuPresupuestoList().add(aiuPresupuesto);
                idPresupuesto = em.merge(idPresupuesto);
            }
            if (idTipoAiu != null) {
                idTipoAiu.getAiuPresupuestoList().add(aiuPresupuesto);
                idTipoAiu = em.merge(idTipoAiu);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAiuPresupuesto(aiuPresupuesto.getId()) != null) {
                throw new PreexistingEntityException("AiuPresupuesto " + aiuPresupuesto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AiuPresupuesto aiuPresupuesto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AiuPresupuesto persistentAiuPresupuesto = em.find(AiuPresupuesto.class, aiuPresupuesto.getId());
            Presupuesto idPresupuestoOld = persistentAiuPresupuesto.getIdPresupuesto();
            Presupuesto idPresupuestoNew = aiuPresupuesto.getIdPresupuesto();
            Aiu idTipoAiuOld = persistentAiuPresupuesto.getIdTipoAiu();
            Aiu idTipoAiuNew = aiuPresupuesto.getIdTipoAiu();
            if (idPresupuestoNew != null) {
                idPresupuestoNew = em.getReference(idPresupuestoNew.getClass(), idPresupuestoNew.getId());
                aiuPresupuesto.setIdPresupuesto(idPresupuestoNew);
            }
            if (idTipoAiuNew != null) {
                idTipoAiuNew = em.getReference(idTipoAiuNew.getClass(), idTipoAiuNew.getId());
                aiuPresupuesto.setIdTipoAiu(idTipoAiuNew);
            }
            aiuPresupuesto = em.merge(aiuPresupuesto);
            if (idPresupuestoOld != null && !idPresupuestoOld.equals(idPresupuestoNew)) {
                idPresupuestoOld.getAiuPresupuestoList().remove(aiuPresupuesto);
                idPresupuestoOld = em.merge(idPresupuestoOld);
            }
            if (idPresupuestoNew != null && !idPresupuestoNew.equals(idPresupuestoOld)) {
                idPresupuestoNew.getAiuPresupuestoList().add(aiuPresupuesto);
                idPresupuestoNew = em.merge(idPresupuestoNew);
            }
            if (idTipoAiuOld != null && !idTipoAiuOld.equals(idTipoAiuNew)) {
                idTipoAiuOld.getAiuPresupuestoList().remove(aiuPresupuesto);
                idTipoAiuOld = em.merge(idTipoAiuOld);
            }
            if (idTipoAiuNew != null && !idTipoAiuNew.equals(idTipoAiuOld)) {
                idTipoAiuNew.getAiuPresupuestoList().add(aiuPresupuesto);
                idTipoAiuNew = em.merge(idTipoAiuNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = aiuPresupuesto.getId();
                if (findAiuPresupuesto(id) == null) {
                    throw new NonexistentEntityException("The aiuPresupuesto with id " + id + " no longer exists.");
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
            AiuPresupuesto aiuPresupuesto;
            try {
                aiuPresupuesto = em.getReference(AiuPresupuesto.class, id);
                aiuPresupuesto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aiuPresupuesto with id " + id + " no longer exists.", enfe);
            }
            Presupuesto idPresupuesto = aiuPresupuesto.getIdPresupuesto();
            if (idPresupuesto != null) {
                idPresupuesto.getAiuPresupuestoList().remove(aiuPresupuesto);
                idPresupuesto = em.merge(idPresupuesto);
            }
            Aiu idTipoAiu = aiuPresupuesto.getIdTipoAiu();
            if (idTipoAiu != null) {
                idTipoAiu.getAiuPresupuestoList().remove(aiuPresupuesto);
                idTipoAiu = em.merge(idTipoAiu);
            }
            em.remove(aiuPresupuesto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AiuPresupuesto> findAiuPresupuestoEntities() {
        return findAiuPresupuestoEntities(true, -1, -1);
    }

    public List<AiuPresupuesto> findAiuPresupuestoEntities(int maxResults, int firstResult) {
        return findAiuPresupuestoEntities(false, maxResults, firstResult);
    }

    private List<AiuPresupuesto> findAiuPresupuestoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AiuPresupuesto.class));
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

    public AiuPresupuesto findAiuPresupuesto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AiuPresupuesto.class, id);
        } finally {
            em.close();
        }
    }

    public int getAiuPresupuestoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AiuPresupuesto> rt = cq.from(AiuPresupuesto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
