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
import com.sae.persistence.domain.ItemEvaluacionConvocatoria;
import com.sae.persistence.domain.Convocatoria;
import com.sae.persistence.domain.AdjuntoConvocatoria;
import com.sae.persistence.domain.EvaluacionConvocatoria;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class EvaluacionConvocatoriaJpaController implements Serializable {

    public EvaluacionConvocatoriaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EvaluacionConvocatoria evaluacionConvocatoria) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItemEvaluacionConvocatoria idItem = evaluacionConvocatoria.getIdItem();
            if (idItem != null) {
                idItem = em.getReference(idItem.getClass(), idItem.getId());
                evaluacionConvocatoria.setIdItem(idItem);
            }
            Convocatoria idConvocatoria = evaluacionConvocatoria.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria = em.getReference(idConvocatoria.getClass(), idConvocatoria.getId());
                evaluacionConvocatoria.setIdConvocatoria(idConvocatoria);
            }
            AdjuntoConvocatoria idAdjuntoItem = evaluacionConvocatoria.getIdAdjuntoItem();
            if (idAdjuntoItem != null) {
                idAdjuntoItem = em.getReference(idAdjuntoItem.getClass(), idAdjuntoItem.getId());
                evaluacionConvocatoria.setIdAdjuntoItem(idAdjuntoItem);
            }
            em.persist(evaluacionConvocatoria);
            if (idItem != null) {
                idItem.getEvaluacionConvocatoriaList().add(evaluacionConvocatoria);
                idItem = em.merge(idItem);
            }
            if (idConvocatoria != null) {
                idConvocatoria.getEvaluacionConvocatoriaList().add(evaluacionConvocatoria);
                idConvocatoria = em.merge(idConvocatoria);
            }
            if (idAdjuntoItem != null) {
                idAdjuntoItem.getEvaluacionConvocatoriaList().add(evaluacionConvocatoria);
                idAdjuntoItem = em.merge(idAdjuntoItem);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEvaluacionConvocatoria(evaluacionConvocatoria.getId()) != null) {
                throw new PreexistingEntityException("EvaluacionConvocatoria " + evaluacionConvocatoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EvaluacionConvocatoria evaluacionConvocatoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EvaluacionConvocatoria persistentEvaluacionConvocatoria = em.find(EvaluacionConvocatoria.class, evaluacionConvocatoria.getId());
            ItemEvaluacionConvocatoria idItemOld = persistentEvaluacionConvocatoria.getIdItem();
            ItemEvaluacionConvocatoria idItemNew = evaluacionConvocatoria.getIdItem();
            Convocatoria idConvocatoriaOld = persistentEvaluacionConvocatoria.getIdConvocatoria();
            Convocatoria idConvocatoriaNew = evaluacionConvocatoria.getIdConvocatoria();
            AdjuntoConvocatoria idAdjuntoItemOld = persistentEvaluacionConvocatoria.getIdAdjuntoItem();
            AdjuntoConvocatoria idAdjuntoItemNew = evaluacionConvocatoria.getIdAdjuntoItem();
            if (idItemNew != null) {
                idItemNew = em.getReference(idItemNew.getClass(), idItemNew.getId());
                evaluacionConvocatoria.setIdItem(idItemNew);
            }
            if (idConvocatoriaNew != null) {
                idConvocatoriaNew = em.getReference(idConvocatoriaNew.getClass(), idConvocatoriaNew.getId());
                evaluacionConvocatoria.setIdConvocatoria(idConvocatoriaNew);
            }
            if (idAdjuntoItemNew != null) {
                idAdjuntoItemNew = em.getReference(idAdjuntoItemNew.getClass(), idAdjuntoItemNew.getId());
                evaluacionConvocatoria.setIdAdjuntoItem(idAdjuntoItemNew);
            }
            evaluacionConvocatoria = em.merge(evaluacionConvocatoria);
            if (idItemOld != null && !idItemOld.equals(idItemNew)) {
                idItemOld.getEvaluacionConvocatoriaList().remove(evaluacionConvocatoria);
                idItemOld = em.merge(idItemOld);
            }
            if (idItemNew != null && !idItemNew.equals(idItemOld)) {
                idItemNew.getEvaluacionConvocatoriaList().add(evaluacionConvocatoria);
                idItemNew = em.merge(idItemNew);
            }
            if (idConvocatoriaOld != null && !idConvocatoriaOld.equals(idConvocatoriaNew)) {
                idConvocatoriaOld.getEvaluacionConvocatoriaList().remove(evaluacionConvocatoria);
                idConvocatoriaOld = em.merge(idConvocatoriaOld);
            }
            if (idConvocatoriaNew != null && !idConvocatoriaNew.equals(idConvocatoriaOld)) {
                idConvocatoriaNew.getEvaluacionConvocatoriaList().add(evaluacionConvocatoria);
                idConvocatoriaNew = em.merge(idConvocatoriaNew);
            }
            if (idAdjuntoItemOld != null && !idAdjuntoItemOld.equals(idAdjuntoItemNew)) {
                idAdjuntoItemOld.getEvaluacionConvocatoriaList().remove(evaluacionConvocatoria);
                idAdjuntoItemOld = em.merge(idAdjuntoItemOld);
            }
            if (idAdjuntoItemNew != null && !idAdjuntoItemNew.equals(idAdjuntoItemOld)) {
                idAdjuntoItemNew.getEvaluacionConvocatoriaList().add(evaluacionConvocatoria);
                idAdjuntoItemNew = em.merge(idAdjuntoItemNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = evaluacionConvocatoria.getId();
                if (findEvaluacionConvocatoria(id) == null) {
                    throw new NonexistentEntityException("The evaluacionConvocatoria with id " + id + " no longer exists.");
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
            EvaluacionConvocatoria evaluacionConvocatoria;
            try {
                evaluacionConvocatoria = em.getReference(EvaluacionConvocatoria.class, id);
                evaluacionConvocatoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evaluacionConvocatoria with id " + id + " no longer exists.", enfe);
            }
            ItemEvaluacionConvocatoria idItem = evaluacionConvocatoria.getIdItem();
            if (idItem != null) {
                idItem.getEvaluacionConvocatoriaList().remove(evaluacionConvocatoria);
                idItem = em.merge(idItem);
            }
            Convocatoria idConvocatoria = evaluacionConvocatoria.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria.getEvaluacionConvocatoriaList().remove(evaluacionConvocatoria);
                idConvocatoria = em.merge(idConvocatoria);
            }
            AdjuntoConvocatoria idAdjuntoItem = evaluacionConvocatoria.getIdAdjuntoItem();
            if (idAdjuntoItem != null) {
                idAdjuntoItem.getEvaluacionConvocatoriaList().remove(evaluacionConvocatoria);
                idAdjuntoItem = em.merge(idAdjuntoItem);
            }
            em.remove(evaluacionConvocatoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EvaluacionConvocatoria> findEvaluacionConvocatoriaEntities() {
        return findEvaluacionConvocatoriaEntities(true, -1, -1);
    }

    public List<EvaluacionConvocatoria> findEvaluacionConvocatoriaEntities(int maxResults, int firstResult) {
        return findEvaluacionConvocatoriaEntities(false, maxResults, firstResult);
    }

    private List<EvaluacionConvocatoria> findEvaluacionConvocatoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EvaluacionConvocatoria.class));
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

    public EvaluacionConvocatoria findEvaluacionConvocatoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EvaluacionConvocatoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getEvaluacionConvocatoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EvaluacionConvocatoria> rt = cq.from(EvaluacionConvocatoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
