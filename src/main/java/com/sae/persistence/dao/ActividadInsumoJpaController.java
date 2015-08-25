/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.ActividadInsumo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.ActividadPresupuesto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ActividadInsumoJpaController implements Serializable {

    public ActividadInsumoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ActividadInsumo actividadInsumo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActividadPresupuesto idActividadPresupuesto = actividadInsumo.getIdActividadPresupuesto();
            if (idActividadPresupuesto != null) {
                idActividadPresupuesto = em.getReference(idActividadPresupuesto.getClass(), idActividadPresupuesto.getId());
                actividadInsumo.setIdActividadPresupuesto(idActividadPresupuesto);
            }
            em.persist(actividadInsumo);
            if (idActividadPresupuesto != null) {
                idActividadPresupuesto.getActividadInsumoList().add(actividadInsumo);
                idActividadPresupuesto = em.merge(idActividadPresupuesto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findActividadInsumo(actividadInsumo.getId()) != null) {
                throw new PreexistingEntityException("ActividadInsumo " + actividadInsumo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ActividadInsumo actividadInsumo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActividadInsumo persistentActividadInsumo = em.find(ActividadInsumo.class, actividadInsumo.getId());
            ActividadPresupuesto idActividadPresupuestoOld = persistentActividadInsumo.getIdActividadPresupuesto();
            ActividadPresupuesto idActividadPresupuestoNew = actividadInsumo.getIdActividadPresupuesto();
            if (idActividadPresupuestoNew != null) {
                idActividadPresupuestoNew = em.getReference(idActividadPresupuestoNew.getClass(), idActividadPresupuestoNew.getId());
                actividadInsumo.setIdActividadPresupuesto(idActividadPresupuestoNew);
            }
            actividadInsumo = em.merge(actividadInsumo);
            if (idActividadPresupuestoOld != null && !idActividadPresupuestoOld.equals(idActividadPresupuestoNew)) {
                idActividadPresupuestoOld.getActividadInsumoList().remove(actividadInsumo);
                idActividadPresupuestoOld = em.merge(idActividadPresupuestoOld);
            }
            if (idActividadPresupuestoNew != null && !idActividadPresupuestoNew.equals(idActividadPresupuestoOld)) {
                idActividadPresupuestoNew.getActividadInsumoList().add(actividadInsumo);
                idActividadPresupuestoNew = em.merge(idActividadPresupuestoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = actividadInsumo.getId();
                if (findActividadInsumo(id) == null) {
                    throw new NonexistentEntityException("The actividadInsumo with id " + id + " no longer exists.");
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
            ActividadInsumo actividadInsumo;
            try {
                actividadInsumo = em.getReference(ActividadInsumo.class, id);
                actividadInsumo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actividadInsumo with id " + id + " no longer exists.", enfe);
            }
            ActividadPresupuesto idActividadPresupuesto = actividadInsumo.getIdActividadPresupuesto();
            if (idActividadPresupuesto != null) {
                idActividadPresupuesto.getActividadInsumoList().remove(actividadInsumo);
                idActividadPresupuesto = em.merge(idActividadPresupuesto);
            }
            em.remove(actividadInsumo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ActividadInsumo> findActividadInsumoEntities() {
        return findActividadInsumoEntities(true, -1, -1);
    }

    public List<ActividadInsumo> findActividadInsumoEntities(int maxResults, int firstResult) {
        return findActividadInsumoEntities(false, maxResults, firstResult);
    }

    private List<ActividadInsumo> findActividadInsumoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ActividadInsumo.class));
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

    public ActividadInsumo findActividadInsumo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ActividadInsumo.class, id);
        } finally {
            em.close();
        }
    }

    public int getActividadInsumoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ActividadInsumo> rt = cq.from(ActividadInsumo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
