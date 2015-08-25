/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.CuentaCobroMovimientoContable;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.MovimientoPuc;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class CuentaCobroMovimientoContableJpaController implements Serializable {

    public CuentaCobroMovimientoContableJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CuentaCobroMovimientoContable cuentaCobroMovimientoContable) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MovimientoPuc idMovimiento = cuentaCobroMovimientoContable.getIdMovimiento();
            if (idMovimiento != null) {
                idMovimiento = em.getReference(idMovimiento.getClass(), idMovimiento.getId());
                cuentaCobroMovimientoContable.setIdMovimiento(idMovimiento);
            }
            em.persist(cuentaCobroMovimientoContable);
            if (idMovimiento != null) {
                idMovimiento.getCuentaCobroMovimientoContableList().add(cuentaCobroMovimientoContable);
                idMovimiento = em.merge(idMovimiento);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCuentaCobroMovimientoContable(cuentaCobroMovimientoContable.getId()) != null) {
                throw new PreexistingEntityException("CuentaCobroMovimientoContable " + cuentaCobroMovimientoContable + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CuentaCobroMovimientoContable cuentaCobroMovimientoContable) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuentaCobroMovimientoContable persistentCuentaCobroMovimientoContable = em.find(CuentaCobroMovimientoContable.class, cuentaCobroMovimientoContable.getId());
            MovimientoPuc idMovimientoOld = persistentCuentaCobroMovimientoContable.getIdMovimiento();
            MovimientoPuc idMovimientoNew = cuentaCobroMovimientoContable.getIdMovimiento();
            if (idMovimientoNew != null) {
                idMovimientoNew = em.getReference(idMovimientoNew.getClass(), idMovimientoNew.getId());
                cuentaCobroMovimientoContable.setIdMovimiento(idMovimientoNew);
            }
            cuentaCobroMovimientoContable = em.merge(cuentaCobroMovimientoContable);
            if (idMovimientoOld != null && !idMovimientoOld.equals(idMovimientoNew)) {
                idMovimientoOld.getCuentaCobroMovimientoContableList().remove(cuentaCobroMovimientoContable);
                idMovimientoOld = em.merge(idMovimientoOld);
            }
            if (idMovimientoNew != null && !idMovimientoNew.equals(idMovimientoOld)) {
                idMovimientoNew.getCuentaCobroMovimientoContableList().add(cuentaCobroMovimientoContable);
                idMovimientoNew = em.merge(idMovimientoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuentaCobroMovimientoContable.getId();
                if (findCuentaCobroMovimientoContable(id) == null) {
                    throw new NonexistentEntityException("The cuentaCobroMovimientoContable with id " + id + " no longer exists.");
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
            CuentaCobroMovimientoContable cuentaCobroMovimientoContable;
            try {
                cuentaCobroMovimientoContable = em.getReference(CuentaCobroMovimientoContable.class, id);
                cuentaCobroMovimientoContable.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuentaCobroMovimientoContable with id " + id + " no longer exists.", enfe);
            }
            MovimientoPuc idMovimiento = cuentaCobroMovimientoContable.getIdMovimiento();
            if (idMovimiento != null) {
                idMovimiento.getCuentaCobroMovimientoContableList().remove(cuentaCobroMovimientoContable);
                idMovimiento = em.merge(idMovimiento);
            }
            em.remove(cuentaCobroMovimientoContable);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CuentaCobroMovimientoContable> findCuentaCobroMovimientoContableEntities() {
        return findCuentaCobroMovimientoContableEntities(true, -1, -1);
    }

    public List<CuentaCobroMovimientoContable> findCuentaCobroMovimientoContableEntities(int maxResults, int firstResult) {
        return findCuentaCobroMovimientoContableEntities(false, maxResults, firstResult);
    }

    private List<CuentaCobroMovimientoContable> findCuentaCobroMovimientoContableEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CuentaCobroMovimientoContable.class));
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

    public CuentaCobroMovimientoContable findCuentaCobroMovimientoContable(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CuentaCobroMovimientoContable.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentaCobroMovimientoContableCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CuentaCobroMovimientoContable> rt = cq.from(CuentaCobroMovimientoContable.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
