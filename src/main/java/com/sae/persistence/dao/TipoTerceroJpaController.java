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
import com.sae.persistence.domain.TipoAsociadoTercero;
import com.sae.persistence.domain.TipoTercero;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoTerceroJpaController implements Serializable {

    public TipoTerceroJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoTercero tipoTercero) throws PreexistingEntityException, Exception {
        if (tipoTercero.getTipoAsociadoTerceroList() == null) {
            tipoTercero.setTipoAsociadoTerceroList(new ArrayList<TipoAsociadoTercero>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<TipoAsociadoTercero> attachedTipoAsociadoTerceroList = new ArrayList<TipoAsociadoTercero>();
            for (TipoAsociadoTercero tipoAsociadoTerceroListTipoAsociadoTerceroToAttach : tipoTercero.getTipoAsociadoTerceroList()) {
                tipoAsociadoTerceroListTipoAsociadoTerceroToAttach = em.getReference(tipoAsociadoTerceroListTipoAsociadoTerceroToAttach.getClass(), tipoAsociadoTerceroListTipoAsociadoTerceroToAttach.getId());
                attachedTipoAsociadoTerceroList.add(tipoAsociadoTerceroListTipoAsociadoTerceroToAttach);
            }
            tipoTercero.setTipoAsociadoTerceroList(attachedTipoAsociadoTerceroList);
            em.persist(tipoTercero);
            for (TipoAsociadoTercero tipoAsociadoTerceroListTipoAsociadoTercero : tipoTercero.getTipoAsociadoTerceroList()) {
                TipoTercero oldIdTipoOfTipoAsociadoTerceroListTipoAsociadoTercero = tipoAsociadoTerceroListTipoAsociadoTercero.getIdTipo();
                tipoAsociadoTerceroListTipoAsociadoTercero.setIdTipo(tipoTercero);
                tipoAsociadoTerceroListTipoAsociadoTercero = em.merge(tipoAsociadoTerceroListTipoAsociadoTercero);
                if (oldIdTipoOfTipoAsociadoTerceroListTipoAsociadoTercero != null) {
                    oldIdTipoOfTipoAsociadoTerceroListTipoAsociadoTercero.getTipoAsociadoTerceroList().remove(tipoAsociadoTerceroListTipoAsociadoTercero);
                    oldIdTipoOfTipoAsociadoTerceroListTipoAsociadoTercero = em.merge(oldIdTipoOfTipoAsociadoTerceroListTipoAsociadoTercero);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoTercero(tipoTercero.getId()) != null) {
                throw new PreexistingEntityException("TipoTercero " + tipoTercero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoTercero tipoTercero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTercero persistentTipoTercero = em.find(TipoTercero.class, tipoTercero.getId());
            List<TipoAsociadoTercero> tipoAsociadoTerceroListOld = persistentTipoTercero.getTipoAsociadoTerceroList();
            List<TipoAsociadoTercero> tipoAsociadoTerceroListNew = tipoTercero.getTipoAsociadoTerceroList();
            List<TipoAsociadoTercero> attachedTipoAsociadoTerceroListNew = new ArrayList<TipoAsociadoTercero>();
            for (TipoAsociadoTercero tipoAsociadoTerceroListNewTipoAsociadoTerceroToAttach : tipoAsociadoTerceroListNew) {
                tipoAsociadoTerceroListNewTipoAsociadoTerceroToAttach = em.getReference(tipoAsociadoTerceroListNewTipoAsociadoTerceroToAttach.getClass(), tipoAsociadoTerceroListNewTipoAsociadoTerceroToAttach.getId());
                attachedTipoAsociadoTerceroListNew.add(tipoAsociadoTerceroListNewTipoAsociadoTerceroToAttach);
            }
            tipoAsociadoTerceroListNew = attachedTipoAsociadoTerceroListNew;
            tipoTercero.setTipoAsociadoTerceroList(tipoAsociadoTerceroListNew);
            tipoTercero = em.merge(tipoTercero);
            for (TipoAsociadoTercero tipoAsociadoTerceroListOldTipoAsociadoTercero : tipoAsociadoTerceroListOld) {
                if (!tipoAsociadoTerceroListNew.contains(tipoAsociadoTerceroListOldTipoAsociadoTercero)) {
                    tipoAsociadoTerceroListOldTipoAsociadoTercero.setIdTipo(null);
                    tipoAsociadoTerceroListOldTipoAsociadoTercero = em.merge(tipoAsociadoTerceroListOldTipoAsociadoTercero);
                }
            }
            for (TipoAsociadoTercero tipoAsociadoTerceroListNewTipoAsociadoTercero : tipoAsociadoTerceroListNew) {
                if (!tipoAsociadoTerceroListOld.contains(tipoAsociadoTerceroListNewTipoAsociadoTercero)) {
                    TipoTercero oldIdTipoOfTipoAsociadoTerceroListNewTipoAsociadoTercero = tipoAsociadoTerceroListNewTipoAsociadoTercero.getIdTipo();
                    tipoAsociadoTerceroListNewTipoAsociadoTercero.setIdTipo(tipoTercero);
                    tipoAsociadoTerceroListNewTipoAsociadoTercero = em.merge(tipoAsociadoTerceroListNewTipoAsociadoTercero);
                    if (oldIdTipoOfTipoAsociadoTerceroListNewTipoAsociadoTercero != null && !oldIdTipoOfTipoAsociadoTerceroListNewTipoAsociadoTercero.equals(tipoTercero)) {
                        oldIdTipoOfTipoAsociadoTerceroListNewTipoAsociadoTercero.getTipoAsociadoTerceroList().remove(tipoAsociadoTerceroListNewTipoAsociadoTercero);
                        oldIdTipoOfTipoAsociadoTerceroListNewTipoAsociadoTercero = em.merge(oldIdTipoOfTipoAsociadoTerceroListNewTipoAsociadoTercero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoTercero.getId();
                if (findTipoTercero(id) == null) {
                    throw new NonexistentEntityException("The tipoTercero with id " + id + " no longer exists.");
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
            TipoTercero tipoTercero;
            try {
                tipoTercero = em.getReference(TipoTercero.class, id);
                tipoTercero.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoTercero with id " + id + " no longer exists.", enfe);
            }
            List<TipoAsociadoTercero> tipoAsociadoTerceroList = tipoTercero.getTipoAsociadoTerceroList();
            for (TipoAsociadoTercero tipoAsociadoTerceroListTipoAsociadoTercero : tipoAsociadoTerceroList) {
                tipoAsociadoTerceroListTipoAsociadoTercero.setIdTipo(null);
                tipoAsociadoTerceroListTipoAsociadoTercero = em.merge(tipoAsociadoTerceroListTipoAsociadoTercero);
            }
            em.remove(tipoTercero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoTercero> findTipoTerceroEntities() {
        return findTipoTerceroEntities(true, -1, -1);
    }

    public List<TipoTercero> findTipoTerceroEntities(int maxResults, int firstResult) {
        return findTipoTerceroEntities(false, maxResults, firstResult);
    }

    private List<TipoTercero> findTipoTerceroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoTercero.class));
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

    public TipoTercero findTipoTercero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoTercero.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoTerceroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoTercero> rt = cq.from(TipoTercero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
