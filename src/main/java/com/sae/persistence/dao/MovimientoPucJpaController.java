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
import com.sae.persistence.domain.Puc;
import com.sae.persistence.domain.Comprobante;
import com.sae.persistence.domain.CuentaCobroMovimientoContable;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.FacturaVentaMovimientoContable;
import com.sae.persistence.domain.MovimientoPuc;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class MovimientoPucJpaController implements Serializable {

    public MovimientoPucJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MovimientoPuc movimientoPuc) throws PreexistingEntityException, Exception {
        if (movimientoPuc.getCuentaCobroMovimientoContableList() == null) {
            movimientoPuc.setCuentaCobroMovimientoContableList(new ArrayList<CuentaCobroMovimientoContable>());
        }
        if (movimientoPuc.getFacturaVentaMovimientoContableList() == null) {
            movimientoPuc.setFacturaVentaMovimientoContableList(new ArrayList<FacturaVentaMovimientoContable>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puc idPuc = movimientoPuc.getIdPuc();
            if (idPuc != null) {
                idPuc = em.getReference(idPuc.getClass(), idPuc.getId());
                movimientoPuc.setIdPuc(idPuc);
            }
            Comprobante idComprobante = movimientoPuc.getIdComprobante();
            if (idComprobante != null) {
                idComprobante = em.getReference(idComprobante.getClass(), idComprobante.getId());
                movimientoPuc.setIdComprobante(idComprobante);
            }
            List<CuentaCobroMovimientoContable> attachedCuentaCobroMovimientoContableList = new ArrayList<CuentaCobroMovimientoContable>();
            for (CuentaCobroMovimientoContable cuentaCobroMovimientoContableListCuentaCobroMovimientoContableToAttach : movimientoPuc.getCuentaCobroMovimientoContableList()) {
                cuentaCobroMovimientoContableListCuentaCobroMovimientoContableToAttach = em.getReference(cuentaCobroMovimientoContableListCuentaCobroMovimientoContableToAttach.getClass(), cuentaCobroMovimientoContableListCuentaCobroMovimientoContableToAttach.getId());
                attachedCuentaCobroMovimientoContableList.add(cuentaCobroMovimientoContableListCuentaCobroMovimientoContableToAttach);
            }
            movimientoPuc.setCuentaCobroMovimientoContableList(attachedCuentaCobroMovimientoContableList);
            List<FacturaVentaMovimientoContable> attachedFacturaVentaMovimientoContableList = new ArrayList<FacturaVentaMovimientoContable>();
            for (FacturaVentaMovimientoContable facturaVentaMovimientoContableListFacturaVentaMovimientoContableToAttach : movimientoPuc.getFacturaVentaMovimientoContableList()) {
                facturaVentaMovimientoContableListFacturaVentaMovimientoContableToAttach = em.getReference(facturaVentaMovimientoContableListFacturaVentaMovimientoContableToAttach.getClass(), facturaVentaMovimientoContableListFacturaVentaMovimientoContableToAttach.getId());
                attachedFacturaVentaMovimientoContableList.add(facturaVentaMovimientoContableListFacturaVentaMovimientoContableToAttach);
            }
            movimientoPuc.setFacturaVentaMovimientoContableList(attachedFacturaVentaMovimientoContableList);
            em.persist(movimientoPuc);
            if (idPuc != null) {
                idPuc.getMovimientoPucList().add(movimientoPuc);
                idPuc = em.merge(idPuc);
            }
            if (idComprobante != null) {
                idComprobante.getMovimientoPucList().add(movimientoPuc);
                idComprobante = em.merge(idComprobante);
            }
            for (CuentaCobroMovimientoContable cuentaCobroMovimientoContableListCuentaCobroMovimientoContable : movimientoPuc.getCuentaCobroMovimientoContableList()) {
                MovimientoPuc oldIdMovimientoOfCuentaCobroMovimientoContableListCuentaCobroMovimientoContable = cuentaCobroMovimientoContableListCuentaCobroMovimientoContable.getIdMovimiento();
                cuentaCobroMovimientoContableListCuentaCobroMovimientoContable.setIdMovimiento(movimientoPuc);
                cuentaCobroMovimientoContableListCuentaCobroMovimientoContable = em.merge(cuentaCobroMovimientoContableListCuentaCobroMovimientoContable);
                if (oldIdMovimientoOfCuentaCobroMovimientoContableListCuentaCobroMovimientoContable != null) {
                    oldIdMovimientoOfCuentaCobroMovimientoContableListCuentaCobroMovimientoContable.getCuentaCobroMovimientoContableList().remove(cuentaCobroMovimientoContableListCuentaCobroMovimientoContable);
                    oldIdMovimientoOfCuentaCobroMovimientoContableListCuentaCobroMovimientoContable = em.merge(oldIdMovimientoOfCuentaCobroMovimientoContableListCuentaCobroMovimientoContable);
                }
            }
            for (FacturaVentaMovimientoContable facturaVentaMovimientoContableListFacturaVentaMovimientoContable : movimientoPuc.getFacturaVentaMovimientoContableList()) {
                MovimientoPuc oldIdMovimientoOfFacturaVentaMovimientoContableListFacturaVentaMovimientoContable = facturaVentaMovimientoContableListFacturaVentaMovimientoContable.getIdMovimiento();
                facturaVentaMovimientoContableListFacturaVentaMovimientoContable.setIdMovimiento(movimientoPuc);
                facturaVentaMovimientoContableListFacturaVentaMovimientoContable = em.merge(facturaVentaMovimientoContableListFacturaVentaMovimientoContable);
                if (oldIdMovimientoOfFacturaVentaMovimientoContableListFacturaVentaMovimientoContable != null) {
                    oldIdMovimientoOfFacturaVentaMovimientoContableListFacturaVentaMovimientoContable.getFacturaVentaMovimientoContableList().remove(facturaVentaMovimientoContableListFacturaVentaMovimientoContable);
                    oldIdMovimientoOfFacturaVentaMovimientoContableListFacturaVentaMovimientoContable = em.merge(oldIdMovimientoOfFacturaVentaMovimientoContableListFacturaVentaMovimientoContable);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMovimientoPuc(movimientoPuc.getId()) != null) {
                throw new PreexistingEntityException("MovimientoPuc " + movimientoPuc + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MovimientoPuc movimientoPuc) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MovimientoPuc persistentMovimientoPuc = em.find(MovimientoPuc.class, movimientoPuc.getId());
            Puc idPucOld = persistentMovimientoPuc.getIdPuc();
            Puc idPucNew = movimientoPuc.getIdPuc();
            Comprobante idComprobanteOld = persistentMovimientoPuc.getIdComprobante();
            Comprobante idComprobanteNew = movimientoPuc.getIdComprobante();
            List<CuentaCobroMovimientoContable> cuentaCobroMovimientoContableListOld = persistentMovimientoPuc.getCuentaCobroMovimientoContableList();
            List<CuentaCobroMovimientoContable> cuentaCobroMovimientoContableListNew = movimientoPuc.getCuentaCobroMovimientoContableList();
            List<FacturaVentaMovimientoContable> facturaVentaMovimientoContableListOld = persistentMovimientoPuc.getFacturaVentaMovimientoContableList();
            List<FacturaVentaMovimientoContable> facturaVentaMovimientoContableListNew = movimientoPuc.getFacturaVentaMovimientoContableList();
            if (idPucNew != null) {
                idPucNew = em.getReference(idPucNew.getClass(), idPucNew.getId());
                movimientoPuc.setIdPuc(idPucNew);
            }
            if (idComprobanteNew != null) {
                idComprobanteNew = em.getReference(idComprobanteNew.getClass(), idComprobanteNew.getId());
                movimientoPuc.setIdComprobante(idComprobanteNew);
            }
            List<CuentaCobroMovimientoContable> attachedCuentaCobroMovimientoContableListNew = new ArrayList<CuentaCobroMovimientoContable>();
            for (CuentaCobroMovimientoContable cuentaCobroMovimientoContableListNewCuentaCobroMovimientoContableToAttach : cuentaCobroMovimientoContableListNew) {
                cuentaCobroMovimientoContableListNewCuentaCobroMovimientoContableToAttach = em.getReference(cuentaCobroMovimientoContableListNewCuentaCobroMovimientoContableToAttach.getClass(), cuentaCobroMovimientoContableListNewCuentaCobroMovimientoContableToAttach.getId());
                attachedCuentaCobroMovimientoContableListNew.add(cuentaCobroMovimientoContableListNewCuentaCobroMovimientoContableToAttach);
            }
            cuentaCobroMovimientoContableListNew = attachedCuentaCobroMovimientoContableListNew;
            movimientoPuc.setCuentaCobroMovimientoContableList(cuentaCobroMovimientoContableListNew);
            List<FacturaVentaMovimientoContable> attachedFacturaVentaMovimientoContableListNew = new ArrayList<FacturaVentaMovimientoContable>();
            for (FacturaVentaMovimientoContable facturaVentaMovimientoContableListNewFacturaVentaMovimientoContableToAttach : facturaVentaMovimientoContableListNew) {
                facturaVentaMovimientoContableListNewFacturaVentaMovimientoContableToAttach = em.getReference(facturaVentaMovimientoContableListNewFacturaVentaMovimientoContableToAttach.getClass(), facturaVentaMovimientoContableListNewFacturaVentaMovimientoContableToAttach.getId());
                attachedFacturaVentaMovimientoContableListNew.add(facturaVentaMovimientoContableListNewFacturaVentaMovimientoContableToAttach);
            }
            facturaVentaMovimientoContableListNew = attachedFacturaVentaMovimientoContableListNew;
            movimientoPuc.setFacturaVentaMovimientoContableList(facturaVentaMovimientoContableListNew);
            movimientoPuc = em.merge(movimientoPuc);
            if (idPucOld != null && !idPucOld.equals(idPucNew)) {
                idPucOld.getMovimientoPucList().remove(movimientoPuc);
                idPucOld = em.merge(idPucOld);
            }
            if (idPucNew != null && !idPucNew.equals(idPucOld)) {
                idPucNew.getMovimientoPucList().add(movimientoPuc);
                idPucNew = em.merge(idPucNew);
            }
            if (idComprobanteOld != null && !idComprobanteOld.equals(idComprobanteNew)) {
                idComprobanteOld.getMovimientoPucList().remove(movimientoPuc);
                idComprobanteOld = em.merge(idComprobanteOld);
            }
            if (idComprobanteNew != null && !idComprobanteNew.equals(idComprobanteOld)) {
                idComprobanteNew.getMovimientoPucList().add(movimientoPuc);
                idComprobanteNew = em.merge(idComprobanteNew);
            }
            for (CuentaCobroMovimientoContable cuentaCobroMovimientoContableListOldCuentaCobroMovimientoContable : cuentaCobroMovimientoContableListOld) {
                if (!cuentaCobroMovimientoContableListNew.contains(cuentaCobroMovimientoContableListOldCuentaCobroMovimientoContable)) {
                    cuentaCobroMovimientoContableListOldCuentaCobroMovimientoContable.setIdMovimiento(null);
                    cuentaCobroMovimientoContableListOldCuentaCobroMovimientoContable = em.merge(cuentaCobroMovimientoContableListOldCuentaCobroMovimientoContable);
                }
            }
            for (CuentaCobroMovimientoContable cuentaCobroMovimientoContableListNewCuentaCobroMovimientoContable : cuentaCobroMovimientoContableListNew) {
                if (!cuentaCobroMovimientoContableListOld.contains(cuentaCobroMovimientoContableListNewCuentaCobroMovimientoContable)) {
                    MovimientoPuc oldIdMovimientoOfCuentaCobroMovimientoContableListNewCuentaCobroMovimientoContable = cuentaCobroMovimientoContableListNewCuentaCobroMovimientoContable.getIdMovimiento();
                    cuentaCobroMovimientoContableListNewCuentaCobroMovimientoContable.setIdMovimiento(movimientoPuc);
                    cuentaCobroMovimientoContableListNewCuentaCobroMovimientoContable = em.merge(cuentaCobroMovimientoContableListNewCuentaCobroMovimientoContable);
                    if (oldIdMovimientoOfCuentaCobroMovimientoContableListNewCuentaCobroMovimientoContable != null && !oldIdMovimientoOfCuentaCobroMovimientoContableListNewCuentaCobroMovimientoContable.equals(movimientoPuc)) {
                        oldIdMovimientoOfCuentaCobroMovimientoContableListNewCuentaCobroMovimientoContable.getCuentaCobroMovimientoContableList().remove(cuentaCobroMovimientoContableListNewCuentaCobroMovimientoContable);
                        oldIdMovimientoOfCuentaCobroMovimientoContableListNewCuentaCobroMovimientoContable = em.merge(oldIdMovimientoOfCuentaCobroMovimientoContableListNewCuentaCobroMovimientoContable);
                    }
                }
            }
            for (FacturaVentaMovimientoContable facturaVentaMovimientoContableListOldFacturaVentaMovimientoContable : facturaVentaMovimientoContableListOld) {
                if (!facturaVentaMovimientoContableListNew.contains(facturaVentaMovimientoContableListOldFacturaVentaMovimientoContable)) {
                    facturaVentaMovimientoContableListOldFacturaVentaMovimientoContable.setIdMovimiento(null);
                    facturaVentaMovimientoContableListOldFacturaVentaMovimientoContable = em.merge(facturaVentaMovimientoContableListOldFacturaVentaMovimientoContable);
                }
            }
            for (FacturaVentaMovimientoContable facturaVentaMovimientoContableListNewFacturaVentaMovimientoContable : facturaVentaMovimientoContableListNew) {
                if (!facturaVentaMovimientoContableListOld.contains(facturaVentaMovimientoContableListNewFacturaVentaMovimientoContable)) {
                    MovimientoPuc oldIdMovimientoOfFacturaVentaMovimientoContableListNewFacturaVentaMovimientoContable = facturaVentaMovimientoContableListNewFacturaVentaMovimientoContable.getIdMovimiento();
                    facturaVentaMovimientoContableListNewFacturaVentaMovimientoContable.setIdMovimiento(movimientoPuc);
                    facturaVentaMovimientoContableListNewFacturaVentaMovimientoContable = em.merge(facturaVentaMovimientoContableListNewFacturaVentaMovimientoContable);
                    if (oldIdMovimientoOfFacturaVentaMovimientoContableListNewFacturaVentaMovimientoContable != null && !oldIdMovimientoOfFacturaVentaMovimientoContableListNewFacturaVentaMovimientoContable.equals(movimientoPuc)) {
                        oldIdMovimientoOfFacturaVentaMovimientoContableListNewFacturaVentaMovimientoContable.getFacturaVentaMovimientoContableList().remove(facturaVentaMovimientoContableListNewFacturaVentaMovimientoContable);
                        oldIdMovimientoOfFacturaVentaMovimientoContableListNewFacturaVentaMovimientoContable = em.merge(oldIdMovimientoOfFacturaVentaMovimientoContableListNewFacturaVentaMovimientoContable);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = movimientoPuc.getId();
                if (findMovimientoPuc(id) == null) {
                    throw new NonexistentEntityException("The movimientoPuc with id " + id + " no longer exists.");
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
            MovimientoPuc movimientoPuc;
            try {
                movimientoPuc = em.getReference(MovimientoPuc.class, id);
                movimientoPuc.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The movimientoPuc with id " + id + " no longer exists.", enfe);
            }
            Puc idPuc = movimientoPuc.getIdPuc();
            if (idPuc != null) {
                idPuc.getMovimientoPucList().remove(movimientoPuc);
                idPuc = em.merge(idPuc);
            }
            Comprobante idComprobante = movimientoPuc.getIdComprobante();
            if (idComprobante != null) {
                idComprobante.getMovimientoPucList().remove(movimientoPuc);
                idComprobante = em.merge(idComprobante);
            }
            List<CuentaCobroMovimientoContable> cuentaCobroMovimientoContableList = movimientoPuc.getCuentaCobroMovimientoContableList();
            for (CuentaCobroMovimientoContable cuentaCobroMovimientoContableListCuentaCobroMovimientoContable : cuentaCobroMovimientoContableList) {
                cuentaCobroMovimientoContableListCuentaCobroMovimientoContable.setIdMovimiento(null);
                cuentaCobroMovimientoContableListCuentaCobroMovimientoContable = em.merge(cuentaCobroMovimientoContableListCuentaCobroMovimientoContable);
            }
            List<FacturaVentaMovimientoContable> facturaVentaMovimientoContableList = movimientoPuc.getFacturaVentaMovimientoContableList();
            for (FacturaVentaMovimientoContable facturaVentaMovimientoContableListFacturaVentaMovimientoContable : facturaVentaMovimientoContableList) {
                facturaVentaMovimientoContableListFacturaVentaMovimientoContable.setIdMovimiento(null);
                facturaVentaMovimientoContableListFacturaVentaMovimientoContable = em.merge(facturaVentaMovimientoContableListFacturaVentaMovimientoContable);
            }
            em.remove(movimientoPuc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MovimientoPuc> findMovimientoPucEntities() {
        return findMovimientoPucEntities(true, -1, -1);
    }

    public List<MovimientoPuc> findMovimientoPucEntities(int maxResults, int firstResult) {
        return findMovimientoPucEntities(false, maxResults, firstResult);
    }

    private List<MovimientoPuc> findMovimientoPucEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MovimientoPuc.class));
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

    public MovimientoPuc findMovimientoPuc(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MovimientoPuc.class, id);
        } finally {
            em.close();
        }
    }

    public int getMovimientoPucCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MovimientoPuc> rt = cq.from(MovimientoPuc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
