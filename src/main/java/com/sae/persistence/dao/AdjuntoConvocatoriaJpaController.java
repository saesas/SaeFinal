/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.AdjuntoConvocatoria;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Convocatoria;
import com.sae.persistence.domain.EvaluacionConvocatoria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class AdjuntoConvocatoriaJpaController implements Serializable {

    public AdjuntoConvocatoriaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AdjuntoConvocatoria adjuntoConvocatoria) throws PreexistingEntityException, Exception {
        if (adjuntoConvocatoria.getEvaluacionConvocatoriaList() == null) {
            adjuntoConvocatoria.setEvaluacionConvocatoriaList(new ArrayList<EvaluacionConvocatoria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Convocatoria idConvocatoria = adjuntoConvocatoria.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria = em.getReference(idConvocatoria.getClass(), idConvocatoria.getId());
                adjuntoConvocatoria.setIdConvocatoria(idConvocatoria);
            }
            List<EvaluacionConvocatoria> attachedEvaluacionConvocatoriaList = new ArrayList<EvaluacionConvocatoria>();
            for (EvaluacionConvocatoria evaluacionConvocatoriaListEvaluacionConvocatoriaToAttach : adjuntoConvocatoria.getEvaluacionConvocatoriaList()) {
                evaluacionConvocatoriaListEvaluacionConvocatoriaToAttach = em.getReference(evaluacionConvocatoriaListEvaluacionConvocatoriaToAttach.getClass(), evaluacionConvocatoriaListEvaluacionConvocatoriaToAttach.getId());
                attachedEvaluacionConvocatoriaList.add(evaluacionConvocatoriaListEvaluacionConvocatoriaToAttach);
            }
            adjuntoConvocatoria.setEvaluacionConvocatoriaList(attachedEvaluacionConvocatoriaList);
            em.persist(adjuntoConvocatoria);
            if (idConvocatoria != null) {
                idConvocatoria.getAdjuntoConvocatoriaList().add(adjuntoConvocatoria);
                idConvocatoria = em.merge(idConvocatoria);
            }
            for (EvaluacionConvocatoria evaluacionConvocatoriaListEvaluacionConvocatoria : adjuntoConvocatoria.getEvaluacionConvocatoriaList()) {
                AdjuntoConvocatoria oldIdAdjuntoItemOfEvaluacionConvocatoriaListEvaluacionConvocatoria = evaluacionConvocatoriaListEvaluacionConvocatoria.getIdAdjuntoItem();
                evaluacionConvocatoriaListEvaluacionConvocatoria.setIdAdjuntoItem(adjuntoConvocatoria);
                evaluacionConvocatoriaListEvaluacionConvocatoria = em.merge(evaluacionConvocatoriaListEvaluacionConvocatoria);
                if (oldIdAdjuntoItemOfEvaluacionConvocatoriaListEvaluacionConvocatoria != null) {
                    oldIdAdjuntoItemOfEvaluacionConvocatoriaListEvaluacionConvocatoria.getEvaluacionConvocatoriaList().remove(evaluacionConvocatoriaListEvaluacionConvocatoria);
                    oldIdAdjuntoItemOfEvaluacionConvocatoriaListEvaluacionConvocatoria = em.merge(oldIdAdjuntoItemOfEvaluacionConvocatoriaListEvaluacionConvocatoria);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAdjuntoConvocatoria(adjuntoConvocatoria.getId()) != null) {
                throw new PreexistingEntityException("AdjuntoConvocatoria " + adjuntoConvocatoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AdjuntoConvocatoria adjuntoConvocatoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AdjuntoConvocatoria persistentAdjuntoConvocatoria = em.find(AdjuntoConvocatoria.class, adjuntoConvocatoria.getId());
            Convocatoria idConvocatoriaOld = persistentAdjuntoConvocatoria.getIdConvocatoria();
            Convocatoria idConvocatoriaNew = adjuntoConvocatoria.getIdConvocatoria();
            List<EvaluacionConvocatoria> evaluacionConvocatoriaListOld = persistentAdjuntoConvocatoria.getEvaluacionConvocatoriaList();
            List<EvaluacionConvocatoria> evaluacionConvocatoriaListNew = adjuntoConvocatoria.getEvaluacionConvocatoriaList();
            if (idConvocatoriaNew != null) {
                idConvocatoriaNew = em.getReference(idConvocatoriaNew.getClass(), idConvocatoriaNew.getId());
                adjuntoConvocatoria.setIdConvocatoria(idConvocatoriaNew);
            }
            List<EvaluacionConvocatoria> attachedEvaluacionConvocatoriaListNew = new ArrayList<EvaluacionConvocatoria>();
            for (EvaluacionConvocatoria evaluacionConvocatoriaListNewEvaluacionConvocatoriaToAttach : evaluacionConvocatoriaListNew) {
                evaluacionConvocatoriaListNewEvaluacionConvocatoriaToAttach = em.getReference(evaluacionConvocatoriaListNewEvaluacionConvocatoriaToAttach.getClass(), evaluacionConvocatoriaListNewEvaluacionConvocatoriaToAttach.getId());
                attachedEvaluacionConvocatoriaListNew.add(evaluacionConvocatoriaListNewEvaluacionConvocatoriaToAttach);
            }
            evaluacionConvocatoriaListNew = attachedEvaluacionConvocatoriaListNew;
            adjuntoConvocatoria.setEvaluacionConvocatoriaList(evaluacionConvocatoriaListNew);
            adjuntoConvocatoria = em.merge(adjuntoConvocatoria);
            if (idConvocatoriaOld != null && !idConvocatoriaOld.equals(idConvocatoriaNew)) {
                idConvocatoriaOld.getAdjuntoConvocatoriaList().remove(adjuntoConvocatoria);
                idConvocatoriaOld = em.merge(idConvocatoriaOld);
            }
            if (idConvocatoriaNew != null && !idConvocatoriaNew.equals(idConvocatoriaOld)) {
                idConvocatoriaNew.getAdjuntoConvocatoriaList().add(adjuntoConvocatoria);
                idConvocatoriaNew = em.merge(idConvocatoriaNew);
            }
            for (EvaluacionConvocatoria evaluacionConvocatoriaListOldEvaluacionConvocatoria : evaluacionConvocatoriaListOld) {
                if (!evaluacionConvocatoriaListNew.contains(evaluacionConvocatoriaListOldEvaluacionConvocatoria)) {
                    evaluacionConvocatoriaListOldEvaluacionConvocatoria.setIdAdjuntoItem(null);
                    evaluacionConvocatoriaListOldEvaluacionConvocatoria = em.merge(evaluacionConvocatoriaListOldEvaluacionConvocatoria);
                }
            }
            for (EvaluacionConvocatoria evaluacionConvocatoriaListNewEvaluacionConvocatoria : evaluacionConvocatoriaListNew) {
                if (!evaluacionConvocatoriaListOld.contains(evaluacionConvocatoriaListNewEvaluacionConvocatoria)) {
                    AdjuntoConvocatoria oldIdAdjuntoItemOfEvaluacionConvocatoriaListNewEvaluacionConvocatoria = evaluacionConvocatoriaListNewEvaluacionConvocatoria.getIdAdjuntoItem();
                    evaluacionConvocatoriaListNewEvaluacionConvocatoria.setIdAdjuntoItem(adjuntoConvocatoria);
                    evaluacionConvocatoriaListNewEvaluacionConvocatoria = em.merge(evaluacionConvocatoriaListNewEvaluacionConvocatoria);
                    if (oldIdAdjuntoItemOfEvaluacionConvocatoriaListNewEvaluacionConvocatoria != null && !oldIdAdjuntoItemOfEvaluacionConvocatoriaListNewEvaluacionConvocatoria.equals(adjuntoConvocatoria)) {
                        oldIdAdjuntoItemOfEvaluacionConvocatoriaListNewEvaluacionConvocatoria.getEvaluacionConvocatoriaList().remove(evaluacionConvocatoriaListNewEvaluacionConvocatoria);
                        oldIdAdjuntoItemOfEvaluacionConvocatoriaListNewEvaluacionConvocatoria = em.merge(oldIdAdjuntoItemOfEvaluacionConvocatoriaListNewEvaluacionConvocatoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = adjuntoConvocatoria.getId();
                if (findAdjuntoConvocatoria(id) == null) {
                    throw new NonexistentEntityException("The adjuntoConvocatoria with id " + id + " no longer exists.");
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
            AdjuntoConvocatoria adjuntoConvocatoria;
            try {
                adjuntoConvocatoria = em.getReference(AdjuntoConvocatoria.class, id);
                adjuntoConvocatoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The adjuntoConvocatoria with id " + id + " no longer exists.", enfe);
            }
            Convocatoria idConvocatoria = adjuntoConvocatoria.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria.getAdjuntoConvocatoriaList().remove(adjuntoConvocatoria);
                idConvocatoria = em.merge(idConvocatoria);
            }
            List<EvaluacionConvocatoria> evaluacionConvocatoriaList = adjuntoConvocatoria.getEvaluacionConvocatoriaList();
            for (EvaluacionConvocatoria evaluacionConvocatoriaListEvaluacionConvocatoria : evaluacionConvocatoriaList) {
                evaluacionConvocatoriaListEvaluacionConvocatoria.setIdAdjuntoItem(null);
                evaluacionConvocatoriaListEvaluacionConvocatoria = em.merge(evaluacionConvocatoriaListEvaluacionConvocatoria);
            }
            em.remove(adjuntoConvocatoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AdjuntoConvocatoria> findAdjuntoConvocatoriaEntities() {
        return findAdjuntoConvocatoriaEntities(true, -1, -1);
    }

    public List<AdjuntoConvocatoria> findAdjuntoConvocatoriaEntities(int maxResults, int firstResult) {
        return findAdjuntoConvocatoriaEntities(false, maxResults, firstResult);
    }

    private List<AdjuntoConvocatoria> findAdjuntoConvocatoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AdjuntoConvocatoria.class));
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

    public AdjuntoConvocatoria findAdjuntoConvocatoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AdjuntoConvocatoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdjuntoConvocatoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AdjuntoConvocatoria> rt = cq.from(AdjuntoConvocatoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
