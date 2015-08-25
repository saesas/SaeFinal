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
import com.sae.persistence.domain.SubtipoDatoMatrizRiesgo;
import com.sae.persistence.domain.TipoDatoMatrizRiesgo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoDatoMatrizRiesgoJpaController implements Serializable {

    public TipoDatoMatrizRiesgoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoDatoMatrizRiesgo tipoDatoMatrizRiesgo) throws PreexistingEntityException, Exception {
        if (tipoDatoMatrizRiesgo.getSubtipoDatoMatrizRiesgoList() == null) {
            tipoDatoMatrizRiesgo.setSubtipoDatoMatrizRiesgoList(new ArrayList<SubtipoDatoMatrizRiesgo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SubtipoDatoMatrizRiesgo> attachedSubtipoDatoMatrizRiesgoList = new ArrayList<SubtipoDatoMatrizRiesgo>();
            for (SubtipoDatoMatrizRiesgo subtipoDatoMatrizRiesgoListSubtipoDatoMatrizRiesgoToAttach : tipoDatoMatrizRiesgo.getSubtipoDatoMatrizRiesgoList()) {
                subtipoDatoMatrizRiesgoListSubtipoDatoMatrizRiesgoToAttach = em.getReference(subtipoDatoMatrizRiesgoListSubtipoDatoMatrizRiesgoToAttach.getClass(), subtipoDatoMatrizRiesgoListSubtipoDatoMatrizRiesgoToAttach.getId());
                attachedSubtipoDatoMatrizRiesgoList.add(subtipoDatoMatrizRiesgoListSubtipoDatoMatrizRiesgoToAttach);
            }
            tipoDatoMatrizRiesgo.setSubtipoDatoMatrizRiesgoList(attachedSubtipoDatoMatrizRiesgoList);
            em.persist(tipoDatoMatrizRiesgo);
            for (SubtipoDatoMatrizRiesgo subtipoDatoMatrizRiesgoListSubtipoDatoMatrizRiesgo : tipoDatoMatrizRiesgo.getSubtipoDatoMatrizRiesgoList()) {
                TipoDatoMatrizRiesgo oldIdTipoOfSubtipoDatoMatrizRiesgoListSubtipoDatoMatrizRiesgo = subtipoDatoMatrizRiesgoListSubtipoDatoMatrizRiesgo.getIdTipo();
                subtipoDatoMatrizRiesgoListSubtipoDatoMatrizRiesgo.setIdTipo(tipoDatoMatrizRiesgo);
                subtipoDatoMatrizRiesgoListSubtipoDatoMatrizRiesgo = em.merge(subtipoDatoMatrizRiesgoListSubtipoDatoMatrizRiesgo);
                if (oldIdTipoOfSubtipoDatoMatrizRiesgoListSubtipoDatoMatrizRiesgo != null) {
                    oldIdTipoOfSubtipoDatoMatrizRiesgoListSubtipoDatoMatrizRiesgo.getSubtipoDatoMatrizRiesgoList().remove(subtipoDatoMatrizRiesgoListSubtipoDatoMatrizRiesgo);
                    oldIdTipoOfSubtipoDatoMatrizRiesgoListSubtipoDatoMatrizRiesgo = em.merge(oldIdTipoOfSubtipoDatoMatrizRiesgoListSubtipoDatoMatrizRiesgo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoDatoMatrizRiesgo(tipoDatoMatrizRiesgo.getId()) != null) {
                throw new PreexistingEntityException("TipoDatoMatrizRiesgo " + tipoDatoMatrizRiesgo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoDatoMatrizRiesgo tipoDatoMatrizRiesgo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoDatoMatrizRiesgo persistentTipoDatoMatrizRiesgo = em.find(TipoDatoMatrizRiesgo.class, tipoDatoMatrizRiesgo.getId());
            List<SubtipoDatoMatrizRiesgo> subtipoDatoMatrizRiesgoListOld = persistentTipoDatoMatrizRiesgo.getSubtipoDatoMatrizRiesgoList();
            List<SubtipoDatoMatrizRiesgo> subtipoDatoMatrizRiesgoListNew = tipoDatoMatrizRiesgo.getSubtipoDatoMatrizRiesgoList();
            List<SubtipoDatoMatrizRiesgo> attachedSubtipoDatoMatrizRiesgoListNew = new ArrayList<SubtipoDatoMatrizRiesgo>();
            for (SubtipoDatoMatrizRiesgo subtipoDatoMatrizRiesgoListNewSubtipoDatoMatrizRiesgoToAttach : subtipoDatoMatrizRiesgoListNew) {
                subtipoDatoMatrizRiesgoListNewSubtipoDatoMatrizRiesgoToAttach = em.getReference(subtipoDatoMatrizRiesgoListNewSubtipoDatoMatrizRiesgoToAttach.getClass(), subtipoDatoMatrizRiesgoListNewSubtipoDatoMatrizRiesgoToAttach.getId());
                attachedSubtipoDatoMatrizRiesgoListNew.add(subtipoDatoMatrizRiesgoListNewSubtipoDatoMatrizRiesgoToAttach);
            }
            subtipoDatoMatrizRiesgoListNew = attachedSubtipoDatoMatrizRiesgoListNew;
            tipoDatoMatrizRiesgo.setSubtipoDatoMatrizRiesgoList(subtipoDatoMatrizRiesgoListNew);
            tipoDatoMatrizRiesgo = em.merge(tipoDatoMatrizRiesgo);
            for (SubtipoDatoMatrizRiesgo subtipoDatoMatrizRiesgoListOldSubtipoDatoMatrizRiesgo : subtipoDatoMatrizRiesgoListOld) {
                if (!subtipoDatoMatrizRiesgoListNew.contains(subtipoDatoMatrizRiesgoListOldSubtipoDatoMatrizRiesgo)) {
                    subtipoDatoMatrizRiesgoListOldSubtipoDatoMatrizRiesgo.setIdTipo(null);
                    subtipoDatoMatrizRiesgoListOldSubtipoDatoMatrizRiesgo = em.merge(subtipoDatoMatrizRiesgoListOldSubtipoDatoMatrizRiesgo);
                }
            }
            for (SubtipoDatoMatrizRiesgo subtipoDatoMatrizRiesgoListNewSubtipoDatoMatrizRiesgo : subtipoDatoMatrizRiesgoListNew) {
                if (!subtipoDatoMatrizRiesgoListOld.contains(subtipoDatoMatrizRiesgoListNewSubtipoDatoMatrizRiesgo)) {
                    TipoDatoMatrizRiesgo oldIdTipoOfSubtipoDatoMatrizRiesgoListNewSubtipoDatoMatrizRiesgo = subtipoDatoMatrizRiesgoListNewSubtipoDatoMatrizRiesgo.getIdTipo();
                    subtipoDatoMatrizRiesgoListNewSubtipoDatoMatrizRiesgo.setIdTipo(tipoDatoMatrizRiesgo);
                    subtipoDatoMatrizRiesgoListNewSubtipoDatoMatrizRiesgo = em.merge(subtipoDatoMatrizRiesgoListNewSubtipoDatoMatrizRiesgo);
                    if (oldIdTipoOfSubtipoDatoMatrizRiesgoListNewSubtipoDatoMatrizRiesgo != null && !oldIdTipoOfSubtipoDatoMatrizRiesgoListNewSubtipoDatoMatrizRiesgo.equals(tipoDatoMatrizRiesgo)) {
                        oldIdTipoOfSubtipoDatoMatrizRiesgoListNewSubtipoDatoMatrizRiesgo.getSubtipoDatoMatrizRiesgoList().remove(subtipoDatoMatrizRiesgoListNewSubtipoDatoMatrizRiesgo);
                        oldIdTipoOfSubtipoDatoMatrizRiesgoListNewSubtipoDatoMatrizRiesgo = em.merge(oldIdTipoOfSubtipoDatoMatrizRiesgoListNewSubtipoDatoMatrizRiesgo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoDatoMatrizRiesgo.getId();
                if (findTipoDatoMatrizRiesgo(id) == null) {
                    throw new NonexistentEntityException("The tipoDatoMatrizRiesgo with id " + id + " no longer exists.");
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
            TipoDatoMatrizRiesgo tipoDatoMatrizRiesgo;
            try {
                tipoDatoMatrizRiesgo = em.getReference(TipoDatoMatrizRiesgo.class, id);
                tipoDatoMatrizRiesgo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoDatoMatrizRiesgo with id " + id + " no longer exists.", enfe);
            }
            List<SubtipoDatoMatrizRiesgo> subtipoDatoMatrizRiesgoList = tipoDatoMatrizRiesgo.getSubtipoDatoMatrizRiesgoList();
            for (SubtipoDatoMatrizRiesgo subtipoDatoMatrizRiesgoListSubtipoDatoMatrizRiesgo : subtipoDatoMatrizRiesgoList) {
                subtipoDatoMatrizRiesgoListSubtipoDatoMatrizRiesgo.setIdTipo(null);
                subtipoDatoMatrizRiesgoListSubtipoDatoMatrizRiesgo = em.merge(subtipoDatoMatrizRiesgoListSubtipoDatoMatrizRiesgo);
            }
            em.remove(tipoDatoMatrizRiesgo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoDatoMatrizRiesgo> findTipoDatoMatrizRiesgoEntities() {
        return findTipoDatoMatrizRiesgoEntities(true, -1, -1);
    }

    public List<TipoDatoMatrizRiesgo> findTipoDatoMatrizRiesgoEntities(int maxResults, int firstResult) {
        return findTipoDatoMatrizRiesgoEntities(false, maxResults, firstResult);
    }

    private List<TipoDatoMatrizRiesgo> findTipoDatoMatrizRiesgoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoDatoMatrizRiesgo.class));
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

    public TipoDatoMatrizRiesgo findTipoDatoMatrizRiesgo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoDatoMatrizRiesgo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoDatoMatrizRiesgoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoDatoMatrizRiesgo> rt = cq.from(TipoDatoMatrizRiesgo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
