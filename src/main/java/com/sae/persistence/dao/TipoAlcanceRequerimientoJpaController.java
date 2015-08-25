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
import com.sae.persistence.domain.TipoAlcanceRequerimiento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoAlcanceRequerimientoJpaController implements Serializable {

    public TipoAlcanceRequerimientoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoAlcanceRequerimiento tipoAlcanceRequerimiento) throws PreexistingEntityException, Exception {
        if (tipoAlcanceRequerimiento.getRequerimientoList() == null) {
            tipoAlcanceRequerimiento.setRequerimientoList(new ArrayList<Requerimiento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Requerimiento> attachedRequerimientoList = new ArrayList<Requerimiento>();
            for (Requerimiento requerimientoListRequerimientoToAttach : tipoAlcanceRequerimiento.getRequerimientoList()) {
                requerimientoListRequerimientoToAttach = em.getReference(requerimientoListRequerimientoToAttach.getClass(), requerimientoListRequerimientoToAttach.getId());
                attachedRequerimientoList.add(requerimientoListRequerimientoToAttach);
            }
            tipoAlcanceRequerimiento.setRequerimientoList(attachedRequerimientoList);
            em.persist(tipoAlcanceRequerimiento);
            for (Requerimiento requerimientoListRequerimiento : tipoAlcanceRequerimiento.getRequerimientoList()) {
                TipoAlcanceRequerimiento oldIdAlcanceOfRequerimientoListRequerimiento = requerimientoListRequerimiento.getIdAlcance();
                requerimientoListRequerimiento.setIdAlcance(tipoAlcanceRequerimiento);
                requerimientoListRequerimiento = em.merge(requerimientoListRequerimiento);
                if (oldIdAlcanceOfRequerimientoListRequerimiento != null) {
                    oldIdAlcanceOfRequerimientoListRequerimiento.getRequerimientoList().remove(requerimientoListRequerimiento);
                    oldIdAlcanceOfRequerimientoListRequerimiento = em.merge(oldIdAlcanceOfRequerimientoListRequerimiento);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoAlcanceRequerimiento(tipoAlcanceRequerimiento.getId()) != null) {
                throw new PreexistingEntityException("TipoAlcanceRequerimiento " + tipoAlcanceRequerimiento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoAlcanceRequerimiento tipoAlcanceRequerimiento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoAlcanceRequerimiento persistentTipoAlcanceRequerimiento = em.find(TipoAlcanceRequerimiento.class, tipoAlcanceRequerimiento.getId());
            List<Requerimiento> requerimientoListOld = persistentTipoAlcanceRequerimiento.getRequerimientoList();
            List<Requerimiento> requerimientoListNew = tipoAlcanceRequerimiento.getRequerimientoList();
            List<Requerimiento> attachedRequerimientoListNew = new ArrayList<Requerimiento>();
            for (Requerimiento requerimientoListNewRequerimientoToAttach : requerimientoListNew) {
                requerimientoListNewRequerimientoToAttach = em.getReference(requerimientoListNewRequerimientoToAttach.getClass(), requerimientoListNewRequerimientoToAttach.getId());
                attachedRequerimientoListNew.add(requerimientoListNewRequerimientoToAttach);
            }
            requerimientoListNew = attachedRequerimientoListNew;
            tipoAlcanceRequerimiento.setRequerimientoList(requerimientoListNew);
            tipoAlcanceRequerimiento = em.merge(tipoAlcanceRequerimiento);
            for (Requerimiento requerimientoListOldRequerimiento : requerimientoListOld) {
                if (!requerimientoListNew.contains(requerimientoListOldRequerimiento)) {
                    requerimientoListOldRequerimiento.setIdAlcance(null);
                    requerimientoListOldRequerimiento = em.merge(requerimientoListOldRequerimiento);
                }
            }
            for (Requerimiento requerimientoListNewRequerimiento : requerimientoListNew) {
                if (!requerimientoListOld.contains(requerimientoListNewRequerimiento)) {
                    TipoAlcanceRequerimiento oldIdAlcanceOfRequerimientoListNewRequerimiento = requerimientoListNewRequerimiento.getIdAlcance();
                    requerimientoListNewRequerimiento.setIdAlcance(tipoAlcanceRequerimiento);
                    requerimientoListNewRequerimiento = em.merge(requerimientoListNewRequerimiento);
                    if (oldIdAlcanceOfRequerimientoListNewRequerimiento != null && !oldIdAlcanceOfRequerimientoListNewRequerimiento.equals(tipoAlcanceRequerimiento)) {
                        oldIdAlcanceOfRequerimientoListNewRequerimiento.getRequerimientoList().remove(requerimientoListNewRequerimiento);
                        oldIdAlcanceOfRequerimientoListNewRequerimiento = em.merge(oldIdAlcanceOfRequerimientoListNewRequerimiento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoAlcanceRequerimiento.getId();
                if (findTipoAlcanceRequerimiento(id) == null) {
                    throw new NonexistentEntityException("The tipoAlcanceRequerimiento with id " + id + " no longer exists.");
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
            TipoAlcanceRequerimiento tipoAlcanceRequerimiento;
            try {
                tipoAlcanceRequerimiento = em.getReference(TipoAlcanceRequerimiento.class, id);
                tipoAlcanceRequerimiento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoAlcanceRequerimiento with id " + id + " no longer exists.", enfe);
            }
            List<Requerimiento> requerimientoList = tipoAlcanceRequerimiento.getRequerimientoList();
            for (Requerimiento requerimientoListRequerimiento : requerimientoList) {
                requerimientoListRequerimiento.setIdAlcance(null);
                requerimientoListRequerimiento = em.merge(requerimientoListRequerimiento);
            }
            em.remove(tipoAlcanceRequerimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoAlcanceRequerimiento> findTipoAlcanceRequerimientoEntities() {
        return findTipoAlcanceRequerimientoEntities(true, -1, -1);
    }

    public List<TipoAlcanceRequerimiento> findTipoAlcanceRequerimientoEntities(int maxResults, int firstResult) {
        return findTipoAlcanceRequerimientoEntities(false, maxResults, firstResult);
    }

    private List<TipoAlcanceRequerimiento> findTipoAlcanceRequerimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoAlcanceRequerimiento.class));
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

    public TipoAlcanceRequerimiento findTipoAlcanceRequerimiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoAlcanceRequerimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoAlcanceRequerimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoAlcanceRequerimiento> rt = cq.from(TipoAlcanceRequerimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
