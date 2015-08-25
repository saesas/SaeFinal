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
import com.sae.persistence.domain.ItemContrato;
import com.sae.persistence.domain.RevisionContratoLaboral;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class RevisionContratoLaboralJpaController implements Serializable {

    public RevisionContratoLaboralJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RevisionContratoLaboral revisionContratoLaboral) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItemContrato idItem = revisionContratoLaboral.getIdItem();
            if (idItem != null) {
                idItem = em.getReference(idItem.getClass(), idItem.getId());
                revisionContratoLaboral.setIdItem(idItem);
            }
            em.persist(revisionContratoLaboral);
            if (idItem != null) {
                idItem.getRevisionContratoLaboralList().add(revisionContratoLaboral);
                idItem = em.merge(idItem);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRevisionContratoLaboral(revisionContratoLaboral.getId()) != null) {
                throw new PreexistingEntityException("RevisionContratoLaboral " + revisionContratoLaboral + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RevisionContratoLaboral revisionContratoLaboral) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RevisionContratoLaboral persistentRevisionContratoLaboral = em.find(RevisionContratoLaboral.class, revisionContratoLaboral.getId());
            ItemContrato idItemOld = persistentRevisionContratoLaboral.getIdItem();
            ItemContrato idItemNew = revisionContratoLaboral.getIdItem();
            if (idItemNew != null) {
                idItemNew = em.getReference(idItemNew.getClass(), idItemNew.getId());
                revisionContratoLaboral.setIdItem(idItemNew);
            }
            revisionContratoLaboral = em.merge(revisionContratoLaboral);
            if (idItemOld != null && !idItemOld.equals(idItemNew)) {
                idItemOld.getRevisionContratoLaboralList().remove(revisionContratoLaboral);
                idItemOld = em.merge(idItemOld);
            }
            if (idItemNew != null && !idItemNew.equals(idItemOld)) {
                idItemNew.getRevisionContratoLaboralList().add(revisionContratoLaboral);
                idItemNew = em.merge(idItemNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = revisionContratoLaboral.getId();
                if (findRevisionContratoLaboral(id) == null) {
                    throw new NonexistentEntityException("The revisionContratoLaboral with id " + id + " no longer exists.");
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
            RevisionContratoLaboral revisionContratoLaboral;
            try {
                revisionContratoLaboral = em.getReference(RevisionContratoLaboral.class, id);
                revisionContratoLaboral.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The revisionContratoLaboral with id " + id + " no longer exists.", enfe);
            }
            ItemContrato idItem = revisionContratoLaboral.getIdItem();
            if (idItem != null) {
                idItem.getRevisionContratoLaboralList().remove(revisionContratoLaboral);
                idItem = em.merge(idItem);
            }
            em.remove(revisionContratoLaboral);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RevisionContratoLaboral> findRevisionContratoLaboralEntities() {
        return findRevisionContratoLaboralEntities(true, -1, -1);
    }

    public List<RevisionContratoLaboral> findRevisionContratoLaboralEntities(int maxResults, int firstResult) {
        return findRevisionContratoLaboralEntities(false, maxResults, firstResult);
    }

    private List<RevisionContratoLaboral> findRevisionContratoLaboralEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RevisionContratoLaboral.class));
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

    public RevisionContratoLaboral findRevisionContratoLaboral(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RevisionContratoLaboral.class, id);
        } finally {
            em.close();
        }
    }

    public int getRevisionContratoLaboralCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RevisionContratoLaboral> rt = cq.from(RevisionContratoLaboral.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
