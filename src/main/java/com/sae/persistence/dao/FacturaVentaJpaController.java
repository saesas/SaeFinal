/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.FacturaVenta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.TipoFacturaVenta;
import com.sae.persistence.domain.Puc;
import com.sae.persistence.domain.FacturaVentaMovimientoContable;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.FacturaVentaRetencion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class FacturaVentaJpaController implements Serializable {

    public FacturaVentaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FacturaVenta facturaVenta) throws PreexistingEntityException, Exception {
        if (facturaVenta.getFacturaVentaMovimientoContableList() == null) {
            facturaVenta.setFacturaVentaMovimientoContableList(new ArrayList<FacturaVentaMovimientoContable>());
        }
        if (facturaVenta.getFacturaVentaRetencionList() == null) {
            facturaVenta.setFacturaVentaRetencionList(new ArrayList<FacturaVentaRetencion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoFacturaVenta idTipo = facturaVenta.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getId());
                facturaVenta.setIdTipo(idTipo);
            }
            Puc idPuc = facturaVenta.getIdPuc();
            if (idPuc != null) {
                idPuc = em.getReference(idPuc.getClass(), idPuc.getId());
                facturaVenta.setIdPuc(idPuc);
            }
            List<FacturaVentaMovimientoContable> attachedFacturaVentaMovimientoContableList = new ArrayList<FacturaVentaMovimientoContable>();
            for (FacturaVentaMovimientoContable facturaVentaMovimientoContableListFacturaVentaMovimientoContableToAttach : facturaVenta.getFacturaVentaMovimientoContableList()) {
                facturaVentaMovimientoContableListFacturaVentaMovimientoContableToAttach = em.getReference(facturaVentaMovimientoContableListFacturaVentaMovimientoContableToAttach.getClass(), facturaVentaMovimientoContableListFacturaVentaMovimientoContableToAttach.getId());
                attachedFacturaVentaMovimientoContableList.add(facturaVentaMovimientoContableListFacturaVentaMovimientoContableToAttach);
            }
            facturaVenta.setFacturaVentaMovimientoContableList(attachedFacturaVentaMovimientoContableList);
            List<FacturaVentaRetencion> attachedFacturaVentaRetencionList = new ArrayList<FacturaVentaRetencion>();
            for (FacturaVentaRetencion facturaVentaRetencionListFacturaVentaRetencionToAttach : facturaVenta.getFacturaVentaRetencionList()) {
                facturaVentaRetencionListFacturaVentaRetencionToAttach = em.getReference(facturaVentaRetencionListFacturaVentaRetencionToAttach.getClass(), facturaVentaRetencionListFacturaVentaRetencionToAttach.getId());
                attachedFacturaVentaRetencionList.add(facturaVentaRetencionListFacturaVentaRetencionToAttach);
            }
            facturaVenta.setFacturaVentaRetencionList(attachedFacturaVentaRetencionList);
            em.persist(facturaVenta);
            if (idTipo != null) {
                idTipo.getFacturaVentaList().add(facturaVenta);
                idTipo = em.merge(idTipo);
            }
            if (idPuc != null) {
                idPuc.getFacturaVentaList().add(facturaVenta);
                idPuc = em.merge(idPuc);
            }
            for (FacturaVentaMovimientoContable facturaVentaMovimientoContableListFacturaVentaMovimientoContable : facturaVenta.getFacturaVentaMovimientoContableList()) {
                FacturaVenta oldIdFacturaVentaOfFacturaVentaMovimientoContableListFacturaVentaMovimientoContable = facturaVentaMovimientoContableListFacturaVentaMovimientoContable.getIdFacturaVenta();
                facturaVentaMovimientoContableListFacturaVentaMovimientoContable.setIdFacturaVenta(facturaVenta);
                facturaVentaMovimientoContableListFacturaVentaMovimientoContable = em.merge(facturaVentaMovimientoContableListFacturaVentaMovimientoContable);
                if (oldIdFacturaVentaOfFacturaVentaMovimientoContableListFacturaVentaMovimientoContable != null) {
                    oldIdFacturaVentaOfFacturaVentaMovimientoContableListFacturaVentaMovimientoContable.getFacturaVentaMovimientoContableList().remove(facturaVentaMovimientoContableListFacturaVentaMovimientoContable);
                    oldIdFacturaVentaOfFacturaVentaMovimientoContableListFacturaVentaMovimientoContable = em.merge(oldIdFacturaVentaOfFacturaVentaMovimientoContableListFacturaVentaMovimientoContable);
                }
            }
            for (FacturaVentaRetencion facturaVentaRetencionListFacturaVentaRetencion : facturaVenta.getFacturaVentaRetencionList()) {
                FacturaVenta oldIdFacturaOfFacturaVentaRetencionListFacturaVentaRetencion = facturaVentaRetencionListFacturaVentaRetencion.getIdFactura();
                facturaVentaRetencionListFacturaVentaRetencion.setIdFactura(facturaVenta);
                facturaVentaRetencionListFacturaVentaRetencion = em.merge(facturaVentaRetencionListFacturaVentaRetencion);
                if (oldIdFacturaOfFacturaVentaRetencionListFacturaVentaRetencion != null) {
                    oldIdFacturaOfFacturaVentaRetencionListFacturaVentaRetencion.getFacturaVentaRetencionList().remove(facturaVentaRetencionListFacturaVentaRetencion);
                    oldIdFacturaOfFacturaVentaRetencionListFacturaVentaRetencion = em.merge(oldIdFacturaOfFacturaVentaRetencionListFacturaVentaRetencion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFacturaVenta(facturaVenta.getId()) != null) {
                throw new PreexistingEntityException("FacturaVenta " + facturaVenta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FacturaVenta facturaVenta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacturaVenta persistentFacturaVenta = em.find(FacturaVenta.class, facturaVenta.getId());
            TipoFacturaVenta idTipoOld = persistentFacturaVenta.getIdTipo();
            TipoFacturaVenta idTipoNew = facturaVenta.getIdTipo();
            Puc idPucOld = persistentFacturaVenta.getIdPuc();
            Puc idPucNew = facturaVenta.getIdPuc();
            List<FacturaVentaMovimientoContable> facturaVentaMovimientoContableListOld = persistentFacturaVenta.getFacturaVentaMovimientoContableList();
            List<FacturaVentaMovimientoContable> facturaVentaMovimientoContableListNew = facturaVenta.getFacturaVentaMovimientoContableList();
            List<FacturaVentaRetencion> facturaVentaRetencionListOld = persistentFacturaVenta.getFacturaVentaRetencionList();
            List<FacturaVentaRetencion> facturaVentaRetencionListNew = facturaVenta.getFacturaVentaRetencionList();
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getId());
                facturaVenta.setIdTipo(idTipoNew);
            }
            if (idPucNew != null) {
                idPucNew = em.getReference(idPucNew.getClass(), idPucNew.getId());
                facturaVenta.setIdPuc(idPucNew);
            }
            List<FacturaVentaMovimientoContable> attachedFacturaVentaMovimientoContableListNew = new ArrayList<FacturaVentaMovimientoContable>();
            for (FacturaVentaMovimientoContable facturaVentaMovimientoContableListNewFacturaVentaMovimientoContableToAttach : facturaVentaMovimientoContableListNew) {
                facturaVentaMovimientoContableListNewFacturaVentaMovimientoContableToAttach = em.getReference(facturaVentaMovimientoContableListNewFacturaVentaMovimientoContableToAttach.getClass(), facturaVentaMovimientoContableListNewFacturaVentaMovimientoContableToAttach.getId());
                attachedFacturaVentaMovimientoContableListNew.add(facturaVentaMovimientoContableListNewFacturaVentaMovimientoContableToAttach);
            }
            facturaVentaMovimientoContableListNew = attachedFacturaVentaMovimientoContableListNew;
            facturaVenta.setFacturaVentaMovimientoContableList(facturaVentaMovimientoContableListNew);
            List<FacturaVentaRetencion> attachedFacturaVentaRetencionListNew = new ArrayList<FacturaVentaRetencion>();
            for (FacturaVentaRetencion facturaVentaRetencionListNewFacturaVentaRetencionToAttach : facturaVentaRetencionListNew) {
                facturaVentaRetencionListNewFacturaVentaRetencionToAttach = em.getReference(facturaVentaRetencionListNewFacturaVentaRetencionToAttach.getClass(), facturaVentaRetencionListNewFacturaVentaRetencionToAttach.getId());
                attachedFacturaVentaRetencionListNew.add(facturaVentaRetencionListNewFacturaVentaRetencionToAttach);
            }
            facturaVentaRetencionListNew = attachedFacturaVentaRetencionListNew;
            facturaVenta.setFacturaVentaRetencionList(facturaVentaRetencionListNew);
            facturaVenta = em.merge(facturaVenta);
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getFacturaVentaList().remove(facturaVenta);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getFacturaVentaList().add(facturaVenta);
                idTipoNew = em.merge(idTipoNew);
            }
            if (idPucOld != null && !idPucOld.equals(idPucNew)) {
                idPucOld.getFacturaVentaList().remove(facturaVenta);
                idPucOld = em.merge(idPucOld);
            }
            if (idPucNew != null && !idPucNew.equals(idPucOld)) {
                idPucNew.getFacturaVentaList().add(facturaVenta);
                idPucNew = em.merge(idPucNew);
            }
            for (FacturaVentaMovimientoContable facturaVentaMovimientoContableListOldFacturaVentaMovimientoContable : facturaVentaMovimientoContableListOld) {
                if (!facturaVentaMovimientoContableListNew.contains(facturaVentaMovimientoContableListOldFacturaVentaMovimientoContable)) {
                    facturaVentaMovimientoContableListOldFacturaVentaMovimientoContable.setIdFacturaVenta(null);
                    facturaVentaMovimientoContableListOldFacturaVentaMovimientoContable = em.merge(facturaVentaMovimientoContableListOldFacturaVentaMovimientoContable);
                }
            }
            for (FacturaVentaMovimientoContable facturaVentaMovimientoContableListNewFacturaVentaMovimientoContable : facturaVentaMovimientoContableListNew) {
                if (!facturaVentaMovimientoContableListOld.contains(facturaVentaMovimientoContableListNewFacturaVentaMovimientoContable)) {
                    FacturaVenta oldIdFacturaVentaOfFacturaVentaMovimientoContableListNewFacturaVentaMovimientoContable = facturaVentaMovimientoContableListNewFacturaVentaMovimientoContable.getIdFacturaVenta();
                    facturaVentaMovimientoContableListNewFacturaVentaMovimientoContable.setIdFacturaVenta(facturaVenta);
                    facturaVentaMovimientoContableListNewFacturaVentaMovimientoContable = em.merge(facturaVentaMovimientoContableListNewFacturaVentaMovimientoContable);
                    if (oldIdFacturaVentaOfFacturaVentaMovimientoContableListNewFacturaVentaMovimientoContable != null && !oldIdFacturaVentaOfFacturaVentaMovimientoContableListNewFacturaVentaMovimientoContable.equals(facturaVenta)) {
                        oldIdFacturaVentaOfFacturaVentaMovimientoContableListNewFacturaVentaMovimientoContable.getFacturaVentaMovimientoContableList().remove(facturaVentaMovimientoContableListNewFacturaVentaMovimientoContable);
                        oldIdFacturaVentaOfFacturaVentaMovimientoContableListNewFacturaVentaMovimientoContable = em.merge(oldIdFacturaVentaOfFacturaVentaMovimientoContableListNewFacturaVentaMovimientoContable);
                    }
                }
            }
            for (FacturaVentaRetencion facturaVentaRetencionListOldFacturaVentaRetencion : facturaVentaRetencionListOld) {
                if (!facturaVentaRetencionListNew.contains(facturaVentaRetencionListOldFacturaVentaRetencion)) {
                    facturaVentaRetencionListOldFacturaVentaRetencion.setIdFactura(null);
                    facturaVentaRetencionListOldFacturaVentaRetencion = em.merge(facturaVentaRetencionListOldFacturaVentaRetencion);
                }
            }
            for (FacturaVentaRetencion facturaVentaRetencionListNewFacturaVentaRetencion : facturaVentaRetencionListNew) {
                if (!facturaVentaRetencionListOld.contains(facturaVentaRetencionListNewFacturaVentaRetencion)) {
                    FacturaVenta oldIdFacturaOfFacturaVentaRetencionListNewFacturaVentaRetencion = facturaVentaRetencionListNewFacturaVentaRetencion.getIdFactura();
                    facturaVentaRetencionListNewFacturaVentaRetencion.setIdFactura(facturaVenta);
                    facturaVentaRetencionListNewFacturaVentaRetencion = em.merge(facturaVentaRetencionListNewFacturaVentaRetencion);
                    if (oldIdFacturaOfFacturaVentaRetencionListNewFacturaVentaRetencion != null && !oldIdFacturaOfFacturaVentaRetencionListNewFacturaVentaRetencion.equals(facturaVenta)) {
                        oldIdFacturaOfFacturaVentaRetencionListNewFacturaVentaRetencion.getFacturaVentaRetencionList().remove(facturaVentaRetencionListNewFacturaVentaRetencion);
                        oldIdFacturaOfFacturaVentaRetencionListNewFacturaVentaRetencion = em.merge(oldIdFacturaOfFacturaVentaRetencionListNewFacturaVentaRetencion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facturaVenta.getId();
                if (findFacturaVenta(id) == null) {
                    throw new NonexistentEntityException("The facturaVenta with id " + id + " no longer exists.");
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
            FacturaVenta facturaVenta;
            try {
                facturaVenta = em.getReference(FacturaVenta.class, id);
                facturaVenta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturaVenta with id " + id + " no longer exists.", enfe);
            }
            TipoFacturaVenta idTipo = facturaVenta.getIdTipo();
            if (idTipo != null) {
                idTipo.getFacturaVentaList().remove(facturaVenta);
                idTipo = em.merge(idTipo);
            }
            Puc idPuc = facturaVenta.getIdPuc();
            if (idPuc != null) {
                idPuc.getFacturaVentaList().remove(facturaVenta);
                idPuc = em.merge(idPuc);
            }
            List<FacturaVentaMovimientoContable> facturaVentaMovimientoContableList = facturaVenta.getFacturaVentaMovimientoContableList();
            for (FacturaVentaMovimientoContable facturaVentaMovimientoContableListFacturaVentaMovimientoContable : facturaVentaMovimientoContableList) {
                facturaVentaMovimientoContableListFacturaVentaMovimientoContable.setIdFacturaVenta(null);
                facturaVentaMovimientoContableListFacturaVentaMovimientoContable = em.merge(facturaVentaMovimientoContableListFacturaVentaMovimientoContable);
            }
            List<FacturaVentaRetencion> facturaVentaRetencionList = facturaVenta.getFacturaVentaRetencionList();
            for (FacturaVentaRetencion facturaVentaRetencionListFacturaVentaRetencion : facturaVentaRetencionList) {
                facturaVentaRetencionListFacturaVentaRetencion.setIdFactura(null);
                facturaVentaRetencionListFacturaVentaRetencion = em.merge(facturaVentaRetencionListFacturaVentaRetencion);
            }
            em.remove(facturaVenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FacturaVenta> findFacturaVentaEntities() {
        return findFacturaVentaEntities(true, -1, -1);
    }

    public List<FacturaVenta> findFacturaVentaEntities(int maxResults, int firstResult) {
        return findFacturaVentaEntities(false, maxResults, firstResult);
    }

    private List<FacturaVenta> findFacturaVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FacturaVenta.class));
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

    public FacturaVenta findFacturaVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FacturaVenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FacturaVenta> rt = cq.from(FacturaVenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
