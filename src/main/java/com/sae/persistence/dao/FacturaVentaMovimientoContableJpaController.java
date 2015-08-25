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
import com.sae.persistence.domain.MovimientoPuc;
import com.sae.persistence.domain.FacturaVenta;
import com.sae.persistence.domain.FacturaVentaMovimientoContable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class FacturaVentaMovimientoContableJpaController implements Serializable {

    public FacturaVentaMovimientoContableJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FacturaVentaMovimientoContable facturaVentaMovimientoContable) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MovimientoPuc idMovimiento = facturaVentaMovimientoContable.getIdMovimiento();
            if (idMovimiento != null) {
                idMovimiento = em.getReference(idMovimiento.getClass(), idMovimiento.getId());
                facturaVentaMovimientoContable.setIdMovimiento(idMovimiento);
            }
            FacturaVenta idFacturaVenta = facturaVentaMovimientoContable.getIdFacturaVenta();
            if (idFacturaVenta != null) {
                idFacturaVenta = em.getReference(idFacturaVenta.getClass(), idFacturaVenta.getId());
                facturaVentaMovimientoContable.setIdFacturaVenta(idFacturaVenta);
            }
            em.persist(facturaVentaMovimientoContable);
            if (idMovimiento != null) {
                idMovimiento.getFacturaVentaMovimientoContableList().add(facturaVentaMovimientoContable);
                idMovimiento = em.merge(idMovimiento);
            }
            if (idFacturaVenta != null) {
                idFacturaVenta.getFacturaVentaMovimientoContableList().add(facturaVentaMovimientoContable);
                idFacturaVenta = em.merge(idFacturaVenta);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFacturaVentaMovimientoContable(facturaVentaMovimientoContable.getId()) != null) {
                throw new PreexistingEntityException("FacturaVentaMovimientoContable " + facturaVentaMovimientoContable + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FacturaVentaMovimientoContable facturaVentaMovimientoContable) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacturaVentaMovimientoContable persistentFacturaVentaMovimientoContable = em.find(FacturaVentaMovimientoContable.class, facturaVentaMovimientoContable.getId());
            MovimientoPuc idMovimientoOld = persistentFacturaVentaMovimientoContable.getIdMovimiento();
            MovimientoPuc idMovimientoNew = facturaVentaMovimientoContable.getIdMovimiento();
            FacturaVenta idFacturaVentaOld = persistentFacturaVentaMovimientoContable.getIdFacturaVenta();
            FacturaVenta idFacturaVentaNew = facturaVentaMovimientoContable.getIdFacturaVenta();
            if (idMovimientoNew != null) {
                idMovimientoNew = em.getReference(idMovimientoNew.getClass(), idMovimientoNew.getId());
                facturaVentaMovimientoContable.setIdMovimiento(idMovimientoNew);
            }
            if (idFacturaVentaNew != null) {
                idFacturaVentaNew = em.getReference(idFacturaVentaNew.getClass(), idFacturaVentaNew.getId());
                facturaVentaMovimientoContable.setIdFacturaVenta(idFacturaVentaNew);
            }
            facturaVentaMovimientoContable = em.merge(facturaVentaMovimientoContable);
            if (idMovimientoOld != null && !idMovimientoOld.equals(idMovimientoNew)) {
                idMovimientoOld.getFacturaVentaMovimientoContableList().remove(facturaVentaMovimientoContable);
                idMovimientoOld = em.merge(idMovimientoOld);
            }
            if (idMovimientoNew != null && !idMovimientoNew.equals(idMovimientoOld)) {
                idMovimientoNew.getFacturaVentaMovimientoContableList().add(facturaVentaMovimientoContable);
                idMovimientoNew = em.merge(idMovimientoNew);
            }
            if (idFacturaVentaOld != null && !idFacturaVentaOld.equals(idFacturaVentaNew)) {
                idFacturaVentaOld.getFacturaVentaMovimientoContableList().remove(facturaVentaMovimientoContable);
                idFacturaVentaOld = em.merge(idFacturaVentaOld);
            }
            if (idFacturaVentaNew != null && !idFacturaVentaNew.equals(idFacturaVentaOld)) {
                idFacturaVentaNew.getFacturaVentaMovimientoContableList().add(facturaVentaMovimientoContable);
                idFacturaVentaNew = em.merge(idFacturaVentaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facturaVentaMovimientoContable.getId();
                if (findFacturaVentaMovimientoContable(id) == null) {
                    throw new NonexistentEntityException("The facturaVentaMovimientoContable with id " + id + " no longer exists.");
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
            FacturaVentaMovimientoContable facturaVentaMovimientoContable;
            try {
                facturaVentaMovimientoContable = em.getReference(FacturaVentaMovimientoContable.class, id);
                facturaVentaMovimientoContable.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturaVentaMovimientoContable with id " + id + " no longer exists.", enfe);
            }
            MovimientoPuc idMovimiento = facturaVentaMovimientoContable.getIdMovimiento();
            if (idMovimiento != null) {
                idMovimiento.getFacturaVentaMovimientoContableList().remove(facturaVentaMovimientoContable);
                idMovimiento = em.merge(idMovimiento);
            }
            FacturaVenta idFacturaVenta = facturaVentaMovimientoContable.getIdFacturaVenta();
            if (idFacturaVenta != null) {
                idFacturaVenta.getFacturaVentaMovimientoContableList().remove(facturaVentaMovimientoContable);
                idFacturaVenta = em.merge(idFacturaVenta);
            }
            em.remove(facturaVentaMovimientoContable);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FacturaVentaMovimientoContable> findFacturaVentaMovimientoContableEntities() {
        return findFacturaVentaMovimientoContableEntities(true, -1, -1);
    }

    public List<FacturaVentaMovimientoContable> findFacturaVentaMovimientoContableEntities(int maxResults, int firstResult) {
        return findFacturaVentaMovimientoContableEntities(false, maxResults, firstResult);
    }

    private List<FacturaVentaMovimientoContable> findFacturaVentaMovimientoContableEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FacturaVentaMovimientoContable.class));
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

    public FacturaVentaMovimientoContable findFacturaVentaMovimientoContable(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FacturaVentaMovimientoContable.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaVentaMovimientoContableCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FacturaVentaMovimientoContable> rt = cq.from(FacturaVentaMovimientoContable.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
