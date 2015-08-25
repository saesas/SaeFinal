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
import com.sae.persistence.domain.EvaluacionConvocatoria;
import com.sae.persistence.domain.ItemEvaluacionConvocatoria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ItemEvaluacionConvocatoriaJpaController implements Serializable {

    public ItemEvaluacionConvocatoriaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ItemEvaluacionConvocatoria itemEvaluacionConvocatoria) throws PreexistingEntityException, Exception {
        if (itemEvaluacionConvocatoria.getEvaluacionConvocatoriaList() == null) {
            itemEvaluacionConvocatoria.setEvaluacionConvocatoriaList(new ArrayList<EvaluacionConvocatoria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<EvaluacionConvocatoria> attachedEvaluacionConvocatoriaList = new ArrayList<EvaluacionConvocatoria>();
            for (EvaluacionConvocatoria evaluacionConvocatoriaListEvaluacionConvocatoriaToAttach : itemEvaluacionConvocatoria.getEvaluacionConvocatoriaList()) {
                evaluacionConvocatoriaListEvaluacionConvocatoriaToAttach = em.getReference(evaluacionConvocatoriaListEvaluacionConvocatoriaToAttach.getClass(), evaluacionConvocatoriaListEvaluacionConvocatoriaToAttach.getId());
                attachedEvaluacionConvocatoriaList.add(evaluacionConvocatoriaListEvaluacionConvocatoriaToAttach);
            }
            itemEvaluacionConvocatoria.setEvaluacionConvocatoriaList(attachedEvaluacionConvocatoriaList);
            em.persist(itemEvaluacionConvocatoria);
            for (EvaluacionConvocatoria evaluacionConvocatoriaListEvaluacionConvocatoria : itemEvaluacionConvocatoria.getEvaluacionConvocatoriaList()) {
                ItemEvaluacionConvocatoria oldIdItemOfEvaluacionConvocatoriaListEvaluacionConvocatoria = evaluacionConvocatoriaListEvaluacionConvocatoria.getIdItem();
                evaluacionConvocatoriaListEvaluacionConvocatoria.setIdItem(itemEvaluacionConvocatoria);
                evaluacionConvocatoriaListEvaluacionConvocatoria = em.merge(evaluacionConvocatoriaListEvaluacionConvocatoria);
                if (oldIdItemOfEvaluacionConvocatoriaListEvaluacionConvocatoria != null) {
                    oldIdItemOfEvaluacionConvocatoriaListEvaluacionConvocatoria.getEvaluacionConvocatoriaList().remove(evaluacionConvocatoriaListEvaluacionConvocatoria);
                    oldIdItemOfEvaluacionConvocatoriaListEvaluacionConvocatoria = em.merge(oldIdItemOfEvaluacionConvocatoriaListEvaluacionConvocatoria);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findItemEvaluacionConvocatoria(itemEvaluacionConvocatoria.getId()) != null) {
                throw new PreexistingEntityException("ItemEvaluacionConvocatoria " + itemEvaluacionConvocatoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ItemEvaluacionConvocatoria itemEvaluacionConvocatoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItemEvaluacionConvocatoria persistentItemEvaluacionConvocatoria = em.find(ItemEvaluacionConvocatoria.class, itemEvaluacionConvocatoria.getId());
            List<EvaluacionConvocatoria> evaluacionConvocatoriaListOld = persistentItemEvaluacionConvocatoria.getEvaluacionConvocatoriaList();
            List<EvaluacionConvocatoria> evaluacionConvocatoriaListNew = itemEvaluacionConvocatoria.getEvaluacionConvocatoriaList();
            List<EvaluacionConvocatoria> attachedEvaluacionConvocatoriaListNew = new ArrayList<EvaluacionConvocatoria>();
            for (EvaluacionConvocatoria evaluacionConvocatoriaListNewEvaluacionConvocatoriaToAttach : evaluacionConvocatoriaListNew) {
                evaluacionConvocatoriaListNewEvaluacionConvocatoriaToAttach = em.getReference(evaluacionConvocatoriaListNewEvaluacionConvocatoriaToAttach.getClass(), evaluacionConvocatoriaListNewEvaluacionConvocatoriaToAttach.getId());
                attachedEvaluacionConvocatoriaListNew.add(evaluacionConvocatoriaListNewEvaluacionConvocatoriaToAttach);
            }
            evaluacionConvocatoriaListNew = attachedEvaluacionConvocatoriaListNew;
            itemEvaluacionConvocatoria.setEvaluacionConvocatoriaList(evaluacionConvocatoriaListNew);
            itemEvaluacionConvocatoria = em.merge(itemEvaluacionConvocatoria);
            for (EvaluacionConvocatoria evaluacionConvocatoriaListOldEvaluacionConvocatoria : evaluacionConvocatoriaListOld) {
                if (!evaluacionConvocatoriaListNew.contains(evaluacionConvocatoriaListOldEvaluacionConvocatoria)) {
                    evaluacionConvocatoriaListOldEvaluacionConvocatoria.setIdItem(null);
                    evaluacionConvocatoriaListOldEvaluacionConvocatoria = em.merge(evaluacionConvocatoriaListOldEvaluacionConvocatoria);
                }
            }
            for (EvaluacionConvocatoria evaluacionConvocatoriaListNewEvaluacionConvocatoria : evaluacionConvocatoriaListNew) {
                if (!evaluacionConvocatoriaListOld.contains(evaluacionConvocatoriaListNewEvaluacionConvocatoria)) {
                    ItemEvaluacionConvocatoria oldIdItemOfEvaluacionConvocatoriaListNewEvaluacionConvocatoria = evaluacionConvocatoriaListNewEvaluacionConvocatoria.getIdItem();
                    evaluacionConvocatoriaListNewEvaluacionConvocatoria.setIdItem(itemEvaluacionConvocatoria);
                    evaluacionConvocatoriaListNewEvaluacionConvocatoria = em.merge(evaluacionConvocatoriaListNewEvaluacionConvocatoria);
                    if (oldIdItemOfEvaluacionConvocatoriaListNewEvaluacionConvocatoria != null && !oldIdItemOfEvaluacionConvocatoriaListNewEvaluacionConvocatoria.equals(itemEvaluacionConvocatoria)) {
                        oldIdItemOfEvaluacionConvocatoriaListNewEvaluacionConvocatoria.getEvaluacionConvocatoriaList().remove(evaluacionConvocatoriaListNewEvaluacionConvocatoria);
                        oldIdItemOfEvaluacionConvocatoriaListNewEvaluacionConvocatoria = em.merge(oldIdItemOfEvaluacionConvocatoriaListNewEvaluacionConvocatoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = itemEvaluacionConvocatoria.getId();
                if (findItemEvaluacionConvocatoria(id) == null) {
                    throw new NonexistentEntityException("The itemEvaluacionConvocatoria with id " + id + " no longer exists.");
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
            ItemEvaluacionConvocatoria itemEvaluacionConvocatoria;
            try {
                itemEvaluacionConvocatoria = em.getReference(ItemEvaluacionConvocatoria.class, id);
                itemEvaluacionConvocatoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemEvaluacionConvocatoria with id " + id + " no longer exists.", enfe);
            }
            List<EvaluacionConvocatoria> evaluacionConvocatoriaList = itemEvaluacionConvocatoria.getEvaluacionConvocatoriaList();
            for (EvaluacionConvocatoria evaluacionConvocatoriaListEvaluacionConvocatoria : evaluacionConvocatoriaList) {
                evaluacionConvocatoriaListEvaluacionConvocatoria.setIdItem(null);
                evaluacionConvocatoriaListEvaluacionConvocatoria = em.merge(evaluacionConvocatoriaListEvaluacionConvocatoria);
            }
            em.remove(itemEvaluacionConvocatoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ItemEvaluacionConvocatoria> findItemEvaluacionConvocatoriaEntities() {
        return findItemEvaluacionConvocatoriaEntities(true, -1, -1);
    }

    public List<ItemEvaluacionConvocatoria> findItemEvaluacionConvocatoriaEntities(int maxResults, int firstResult) {
        return findItemEvaluacionConvocatoriaEntities(false, maxResults, firstResult);
    }

    private List<ItemEvaluacionConvocatoria> findItemEvaluacionConvocatoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ItemEvaluacionConvocatoria.class));
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

    public ItemEvaluacionConvocatoria findItemEvaluacionConvocatoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ItemEvaluacionConvocatoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemEvaluacionConvocatoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ItemEvaluacionConvocatoria> rt = cq.from(ItemEvaluacionConvocatoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
