/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.ItemContrato;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.RevisionContratoLaboral;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ItemContratoJpaController implements Serializable {

    public ItemContratoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ItemContrato itemContrato) throws PreexistingEntityException, Exception {
        if (itemContrato.getRevisionContratoLaboralList() == null) {
            itemContrato.setRevisionContratoLaboralList(new ArrayList<RevisionContratoLaboral>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RevisionContratoLaboral> attachedRevisionContratoLaboralList = new ArrayList<RevisionContratoLaboral>();
            for (RevisionContratoLaboral revisionContratoLaboralListRevisionContratoLaboralToAttach : itemContrato.getRevisionContratoLaboralList()) {
                revisionContratoLaboralListRevisionContratoLaboralToAttach = em.getReference(revisionContratoLaboralListRevisionContratoLaboralToAttach.getClass(), revisionContratoLaboralListRevisionContratoLaboralToAttach.getId());
                attachedRevisionContratoLaboralList.add(revisionContratoLaboralListRevisionContratoLaboralToAttach);
            }
            itemContrato.setRevisionContratoLaboralList(attachedRevisionContratoLaboralList);
            em.persist(itemContrato);
            for (RevisionContratoLaboral revisionContratoLaboralListRevisionContratoLaboral : itemContrato.getRevisionContratoLaboralList()) {
                ItemContrato oldIdItemOfRevisionContratoLaboralListRevisionContratoLaboral = revisionContratoLaboralListRevisionContratoLaboral.getIdItem();
                revisionContratoLaboralListRevisionContratoLaboral.setIdItem(itemContrato);
                revisionContratoLaboralListRevisionContratoLaboral = em.merge(revisionContratoLaboralListRevisionContratoLaboral);
                if (oldIdItemOfRevisionContratoLaboralListRevisionContratoLaboral != null) {
                    oldIdItemOfRevisionContratoLaboralListRevisionContratoLaboral.getRevisionContratoLaboralList().remove(revisionContratoLaboralListRevisionContratoLaboral);
                    oldIdItemOfRevisionContratoLaboralListRevisionContratoLaboral = em.merge(oldIdItemOfRevisionContratoLaboralListRevisionContratoLaboral);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findItemContrato(itemContrato.getId()) != null) {
                throw new PreexistingEntityException("ItemContrato " + itemContrato + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ItemContrato itemContrato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItemContrato persistentItemContrato = em.find(ItemContrato.class, itemContrato.getId());
            List<RevisionContratoLaboral> revisionContratoLaboralListOld = persistentItemContrato.getRevisionContratoLaboralList();
            List<RevisionContratoLaboral> revisionContratoLaboralListNew = itemContrato.getRevisionContratoLaboralList();
            List<RevisionContratoLaboral> attachedRevisionContratoLaboralListNew = new ArrayList<RevisionContratoLaboral>();
            for (RevisionContratoLaboral revisionContratoLaboralListNewRevisionContratoLaboralToAttach : revisionContratoLaboralListNew) {
                revisionContratoLaboralListNewRevisionContratoLaboralToAttach = em.getReference(revisionContratoLaboralListNewRevisionContratoLaboralToAttach.getClass(), revisionContratoLaboralListNewRevisionContratoLaboralToAttach.getId());
                attachedRevisionContratoLaboralListNew.add(revisionContratoLaboralListNewRevisionContratoLaboralToAttach);
            }
            revisionContratoLaboralListNew = attachedRevisionContratoLaboralListNew;
            itemContrato.setRevisionContratoLaboralList(revisionContratoLaboralListNew);
            itemContrato = em.merge(itemContrato);
            for (RevisionContratoLaboral revisionContratoLaboralListOldRevisionContratoLaboral : revisionContratoLaboralListOld) {
                if (!revisionContratoLaboralListNew.contains(revisionContratoLaboralListOldRevisionContratoLaboral)) {
                    revisionContratoLaboralListOldRevisionContratoLaboral.setIdItem(null);
                    revisionContratoLaboralListOldRevisionContratoLaboral = em.merge(revisionContratoLaboralListOldRevisionContratoLaboral);
                }
            }
            for (RevisionContratoLaboral revisionContratoLaboralListNewRevisionContratoLaboral : revisionContratoLaboralListNew) {
                if (!revisionContratoLaboralListOld.contains(revisionContratoLaboralListNewRevisionContratoLaboral)) {
                    ItemContrato oldIdItemOfRevisionContratoLaboralListNewRevisionContratoLaboral = revisionContratoLaboralListNewRevisionContratoLaboral.getIdItem();
                    revisionContratoLaboralListNewRevisionContratoLaboral.setIdItem(itemContrato);
                    revisionContratoLaboralListNewRevisionContratoLaboral = em.merge(revisionContratoLaboralListNewRevisionContratoLaboral);
                    if (oldIdItemOfRevisionContratoLaboralListNewRevisionContratoLaboral != null && !oldIdItemOfRevisionContratoLaboralListNewRevisionContratoLaboral.equals(itemContrato)) {
                        oldIdItemOfRevisionContratoLaboralListNewRevisionContratoLaboral.getRevisionContratoLaboralList().remove(revisionContratoLaboralListNewRevisionContratoLaboral);
                        oldIdItemOfRevisionContratoLaboralListNewRevisionContratoLaboral = em.merge(oldIdItemOfRevisionContratoLaboralListNewRevisionContratoLaboral);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = itemContrato.getId();
                if (findItemContrato(id) == null) {
                    throw new NonexistentEntityException("The itemContrato with id " + id + " no longer exists.");
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
            ItemContrato itemContrato;
            try {
                itemContrato = em.getReference(ItemContrato.class, id);
                itemContrato.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemContrato with id " + id + " no longer exists.", enfe);
            }
            List<RevisionContratoLaboral> revisionContratoLaboralList = itemContrato.getRevisionContratoLaboralList();
            for (RevisionContratoLaboral revisionContratoLaboralListRevisionContratoLaboral : revisionContratoLaboralList) {
                revisionContratoLaboralListRevisionContratoLaboral.setIdItem(null);
                revisionContratoLaboralListRevisionContratoLaboral = em.merge(revisionContratoLaboralListRevisionContratoLaboral);
            }
            em.remove(itemContrato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ItemContrato> findItemContratoEntities() {
        return findItemContratoEntities(true, -1, -1);
    }

    public List<ItemContrato> findItemContratoEntities(int maxResults, int firstResult) {
        return findItemContratoEntities(false, maxResults, firstResult);
    }

    private List<ItemContrato> findItemContratoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ItemContrato.class));
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

    public ItemContrato findItemContrato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ItemContrato.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemContratoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ItemContrato> rt = cq.from(ItemContrato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
