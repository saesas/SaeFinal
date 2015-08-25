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
import com.sae.persistence.domain.Requerimiento;
import com.sae.persistence.domain.TipoRequerimiento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoRequerimientoJpaController implements Serializable {

    public TipoRequerimientoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoRequerimiento tipoRequerimiento) throws PreexistingEntityException, Exception {
        if (tipoRequerimiento.getRequerimientoList() == null) {
            tipoRequerimiento.setRequerimientoList(new ArrayList<Requerimiento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Requerimiento> attachedRequerimientoList = new ArrayList<Requerimiento>();
            for (Requerimiento requerimientoListRequerimientoToAttach : tipoRequerimiento.getRequerimientoList()) {
                requerimientoListRequerimientoToAttach = em.getReference(requerimientoListRequerimientoToAttach.getClass(), requerimientoListRequerimientoToAttach.getId());
                attachedRequerimientoList.add(requerimientoListRequerimientoToAttach);
            }
            tipoRequerimiento.setRequerimientoList(attachedRequerimientoList);
            em.persist(tipoRequerimiento);
            for (Requerimiento requerimientoListRequerimiento : tipoRequerimiento.getRequerimientoList()) {
                TipoRequerimiento oldIdTipoOfRequerimientoListRequerimiento = requerimientoListRequerimiento.getIdTipo();
                requerimientoListRequerimiento.setIdTipo(tipoRequerimiento);
                requerimientoListRequerimiento = em.merge(requerimientoListRequerimiento);
                if (oldIdTipoOfRequerimientoListRequerimiento != null) {
                    oldIdTipoOfRequerimientoListRequerimiento.getRequerimientoList().remove(requerimientoListRequerimiento);
                    oldIdTipoOfRequerimientoListRequerimiento = em.merge(oldIdTipoOfRequerimientoListRequerimiento);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoRequerimiento(tipoRequerimiento.getId()) != null) {
                throw new PreexistingEntityException("TipoRequerimiento " + tipoRequerimiento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoRequerimiento tipoRequerimiento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoRequerimiento persistentTipoRequerimiento = em.find(TipoRequerimiento.class, tipoRequerimiento.getId());
            List<Requerimiento> requerimientoListOld = persistentTipoRequerimiento.getRequerimientoList();
            List<Requerimiento> requerimientoListNew = tipoRequerimiento.getRequerimientoList();
            List<Requerimiento> attachedRequerimientoListNew = new ArrayList<Requerimiento>();
            for (Requerimiento requerimientoListNewRequerimientoToAttach : requerimientoListNew) {
                requerimientoListNewRequerimientoToAttach = em.getReference(requerimientoListNewRequerimientoToAttach.getClass(), requerimientoListNewRequerimientoToAttach.getId());
                attachedRequerimientoListNew.add(requerimientoListNewRequerimientoToAttach);
            }
            requerimientoListNew = attachedRequerimientoListNew;
            tipoRequerimiento.setRequerimientoList(requerimientoListNew);
            tipoRequerimiento = em.merge(tipoRequerimiento);
            for (Requerimiento requerimientoListOldRequerimiento : requerimientoListOld) {
                if (!requerimientoListNew.contains(requerimientoListOldRequerimiento)) {
                    requerimientoListOldRequerimiento.setIdTipo(null);
                    requerimientoListOldRequerimiento = em.merge(requerimientoListOldRequerimiento);
                }
            }
            for (Requerimiento requerimientoListNewRequerimiento : requerimientoListNew) {
                if (!requerimientoListOld.contains(requerimientoListNewRequerimiento)) {
                    TipoRequerimiento oldIdTipoOfRequerimientoListNewRequerimiento = requerimientoListNewRequerimiento.getIdTipo();
                    requerimientoListNewRequerimiento.setIdTipo(tipoRequerimiento);
                    requerimientoListNewRequerimiento = em.merge(requerimientoListNewRequerimiento);
                    if (oldIdTipoOfRequerimientoListNewRequerimiento != null && !oldIdTipoOfRequerimientoListNewRequerimiento.equals(tipoRequerimiento)) {
                        oldIdTipoOfRequerimientoListNewRequerimiento.getRequerimientoList().remove(requerimientoListNewRequerimiento);
                        oldIdTipoOfRequerimientoListNewRequerimiento = em.merge(oldIdTipoOfRequerimientoListNewRequerimiento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoRequerimiento.getId();
                if (findTipoRequerimiento(id) == null) {
                    throw new NonexistentEntityException("The tipoRequerimiento with id " + id + " no longer exists.");
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
            TipoRequerimiento tipoRequerimiento;
            try {
                tipoRequerimiento = em.getReference(TipoRequerimiento.class, id);
                tipoRequerimiento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoRequerimiento with id " + id + " no longer exists.", enfe);
            }
            List<Requerimiento> requerimientoList = tipoRequerimiento.getRequerimientoList();
            for (Requerimiento requerimientoListRequerimiento : requerimientoList) {
                requerimientoListRequerimiento.setIdTipo(null);
                requerimientoListRequerimiento = em.merge(requerimientoListRequerimiento);
            }
            em.remove(tipoRequerimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoRequerimiento> findTipoRequerimientoEntities() {
        return findTipoRequerimientoEntities(true, -1, -1);
    }

    public List<TipoRequerimiento> findTipoRequerimientoEntities(int maxResults, int firstResult) {
        return findTipoRequerimientoEntities(false, maxResults, firstResult);
    }

    private List<TipoRequerimiento> findTipoRequerimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoRequerimiento.class));
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

    public TipoRequerimiento findTipoRequerimiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoRequerimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoRequerimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoRequerimiento> rt = cq.from(TipoRequerimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
