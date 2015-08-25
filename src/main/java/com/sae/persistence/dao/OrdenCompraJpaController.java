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
import com.sae.persistence.domain.CuentaCobro;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.FacturaCompra;
import com.sae.persistence.domain.OrdenCompraInsumo;
import com.sae.persistence.domain.EstadoOrdenCompra;
import com.sae.persistence.domain.OrdenCompra;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class OrdenCompraJpaController implements Serializable {

    public OrdenCompraJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrdenCompra ordenCompra) throws PreexistingEntityException, Exception {
        if (ordenCompra.getCuentaCobroList() == null) {
            ordenCompra.setCuentaCobroList(new ArrayList<CuentaCobro>());
        }
        if (ordenCompra.getFacturaCompraList() == null) {
            ordenCompra.setFacturaCompraList(new ArrayList<FacturaCompra>());
        }
        if (ordenCompra.getOrdenCompraInsumoList() == null) {
            ordenCompra.setOrdenCompraInsumoList(new ArrayList<OrdenCompraInsumo>());
        }
        if (ordenCompra.getEstadoOrdenCompraList() == null) {
            ordenCompra.setEstadoOrdenCompraList(new ArrayList<EstadoOrdenCompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<CuentaCobro> attachedCuentaCobroList = new ArrayList<CuentaCobro>();
            for (CuentaCobro cuentaCobroListCuentaCobroToAttach : ordenCompra.getCuentaCobroList()) {
                cuentaCobroListCuentaCobroToAttach = em.getReference(cuentaCobroListCuentaCobroToAttach.getClass(), cuentaCobroListCuentaCobroToAttach.getId());
                attachedCuentaCobroList.add(cuentaCobroListCuentaCobroToAttach);
            }
            ordenCompra.setCuentaCobroList(attachedCuentaCobroList);
            List<FacturaCompra> attachedFacturaCompraList = new ArrayList<FacturaCompra>();
            for (FacturaCompra facturaCompraListFacturaCompraToAttach : ordenCompra.getFacturaCompraList()) {
                facturaCompraListFacturaCompraToAttach = em.getReference(facturaCompraListFacturaCompraToAttach.getClass(), facturaCompraListFacturaCompraToAttach.getId());
                attachedFacturaCompraList.add(facturaCompraListFacturaCompraToAttach);
            }
            ordenCompra.setFacturaCompraList(attachedFacturaCompraList);
            List<OrdenCompraInsumo> attachedOrdenCompraInsumoList = new ArrayList<OrdenCompraInsumo>();
            for (OrdenCompraInsumo ordenCompraInsumoListOrdenCompraInsumoToAttach : ordenCompra.getOrdenCompraInsumoList()) {
                ordenCompraInsumoListOrdenCompraInsumoToAttach = em.getReference(ordenCompraInsumoListOrdenCompraInsumoToAttach.getClass(), ordenCompraInsumoListOrdenCompraInsumoToAttach.getId());
                attachedOrdenCompraInsumoList.add(ordenCompraInsumoListOrdenCompraInsumoToAttach);
            }
            ordenCompra.setOrdenCompraInsumoList(attachedOrdenCompraInsumoList);
            List<EstadoOrdenCompra> attachedEstadoOrdenCompraList = new ArrayList<EstadoOrdenCompra>();
            for (EstadoOrdenCompra estadoOrdenCompraListEstadoOrdenCompraToAttach : ordenCompra.getEstadoOrdenCompraList()) {
                estadoOrdenCompraListEstadoOrdenCompraToAttach = em.getReference(estadoOrdenCompraListEstadoOrdenCompraToAttach.getClass(), estadoOrdenCompraListEstadoOrdenCompraToAttach.getId());
                attachedEstadoOrdenCompraList.add(estadoOrdenCompraListEstadoOrdenCompraToAttach);
            }
            ordenCompra.setEstadoOrdenCompraList(attachedEstadoOrdenCompraList);
            em.persist(ordenCompra);
            for (CuentaCobro cuentaCobroListCuentaCobro : ordenCompra.getCuentaCobroList()) {
                OrdenCompra oldIdOrdenCompraOfCuentaCobroListCuentaCobro = cuentaCobroListCuentaCobro.getIdOrdenCompra();
                cuentaCobroListCuentaCobro.setIdOrdenCompra(ordenCompra);
                cuentaCobroListCuentaCobro = em.merge(cuentaCobroListCuentaCobro);
                if (oldIdOrdenCompraOfCuentaCobroListCuentaCobro != null) {
                    oldIdOrdenCompraOfCuentaCobroListCuentaCobro.getCuentaCobroList().remove(cuentaCobroListCuentaCobro);
                    oldIdOrdenCompraOfCuentaCobroListCuentaCobro = em.merge(oldIdOrdenCompraOfCuentaCobroListCuentaCobro);
                }
            }
            for (FacturaCompra facturaCompraListFacturaCompra : ordenCompra.getFacturaCompraList()) {
                OrdenCompra oldIdOrdenCompraOfFacturaCompraListFacturaCompra = facturaCompraListFacturaCompra.getIdOrdenCompra();
                facturaCompraListFacturaCompra.setIdOrdenCompra(ordenCompra);
                facturaCompraListFacturaCompra = em.merge(facturaCompraListFacturaCompra);
                if (oldIdOrdenCompraOfFacturaCompraListFacturaCompra != null) {
                    oldIdOrdenCompraOfFacturaCompraListFacturaCompra.getFacturaCompraList().remove(facturaCompraListFacturaCompra);
                    oldIdOrdenCompraOfFacturaCompraListFacturaCompra = em.merge(oldIdOrdenCompraOfFacturaCompraListFacturaCompra);
                }
            }
            for (OrdenCompraInsumo ordenCompraInsumoListOrdenCompraInsumo : ordenCompra.getOrdenCompraInsumoList()) {
                OrdenCompra oldIdOrdenCompraOfOrdenCompraInsumoListOrdenCompraInsumo = ordenCompraInsumoListOrdenCompraInsumo.getIdOrdenCompra();
                ordenCompraInsumoListOrdenCompraInsumo.setIdOrdenCompra(ordenCompra);
                ordenCompraInsumoListOrdenCompraInsumo = em.merge(ordenCompraInsumoListOrdenCompraInsumo);
                if (oldIdOrdenCompraOfOrdenCompraInsumoListOrdenCompraInsumo != null) {
                    oldIdOrdenCompraOfOrdenCompraInsumoListOrdenCompraInsumo.getOrdenCompraInsumoList().remove(ordenCompraInsumoListOrdenCompraInsumo);
                    oldIdOrdenCompraOfOrdenCompraInsumoListOrdenCompraInsumo = em.merge(oldIdOrdenCompraOfOrdenCompraInsumoListOrdenCompraInsumo);
                }
            }
            for (EstadoOrdenCompra estadoOrdenCompraListEstadoOrdenCompra : ordenCompra.getEstadoOrdenCompraList()) {
                OrdenCompra oldIdOrdenCompraOfEstadoOrdenCompraListEstadoOrdenCompra = estadoOrdenCompraListEstadoOrdenCompra.getIdOrdenCompra();
                estadoOrdenCompraListEstadoOrdenCompra.setIdOrdenCompra(ordenCompra);
                estadoOrdenCompraListEstadoOrdenCompra = em.merge(estadoOrdenCompraListEstadoOrdenCompra);
                if (oldIdOrdenCompraOfEstadoOrdenCompraListEstadoOrdenCompra != null) {
                    oldIdOrdenCompraOfEstadoOrdenCompraListEstadoOrdenCompra.getEstadoOrdenCompraList().remove(estadoOrdenCompraListEstadoOrdenCompra);
                    oldIdOrdenCompraOfEstadoOrdenCompraListEstadoOrdenCompra = em.merge(oldIdOrdenCompraOfEstadoOrdenCompraListEstadoOrdenCompra);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrdenCompra(ordenCompra.getId()) != null) {
                throw new PreexistingEntityException("OrdenCompra " + ordenCompra + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrdenCompra ordenCompra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrdenCompra persistentOrdenCompra = em.find(OrdenCompra.class, ordenCompra.getId());
            List<CuentaCobro> cuentaCobroListOld = persistentOrdenCompra.getCuentaCobroList();
            List<CuentaCobro> cuentaCobroListNew = ordenCompra.getCuentaCobroList();
            List<FacturaCompra> facturaCompraListOld = persistentOrdenCompra.getFacturaCompraList();
            List<FacturaCompra> facturaCompraListNew = ordenCompra.getFacturaCompraList();
            List<OrdenCompraInsumo> ordenCompraInsumoListOld = persistentOrdenCompra.getOrdenCompraInsumoList();
            List<OrdenCompraInsumo> ordenCompraInsumoListNew = ordenCompra.getOrdenCompraInsumoList();
            List<EstadoOrdenCompra> estadoOrdenCompraListOld = persistentOrdenCompra.getEstadoOrdenCompraList();
            List<EstadoOrdenCompra> estadoOrdenCompraListNew = ordenCompra.getEstadoOrdenCompraList();
            List<CuentaCobro> attachedCuentaCobroListNew = new ArrayList<CuentaCobro>();
            for (CuentaCobro cuentaCobroListNewCuentaCobroToAttach : cuentaCobroListNew) {
                cuentaCobroListNewCuentaCobroToAttach = em.getReference(cuentaCobroListNewCuentaCobroToAttach.getClass(), cuentaCobroListNewCuentaCobroToAttach.getId());
                attachedCuentaCobroListNew.add(cuentaCobroListNewCuentaCobroToAttach);
            }
            cuentaCobroListNew = attachedCuentaCobroListNew;
            ordenCompra.setCuentaCobroList(cuentaCobroListNew);
            List<FacturaCompra> attachedFacturaCompraListNew = new ArrayList<FacturaCompra>();
            for (FacturaCompra facturaCompraListNewFacturaCompraToAttach : facturaCompraListNew) {
                facturaCompraListNewFacturaCompraToAttach = em.getReference(facturaCompraListNewFacturaCompraToAttach.getClass(), facturaCompraListNewFacturaCompraToAttach.getId());
                attachedFacturaCompraListNew.add(facturaCompraListNewFacturaCompraToAttach);
            }
            facturaCompraListNew = attachedFacturaCompraListNew;
            ordenCompra.setFacturaCompraList(facturaCompraListNew);
            List<OrdenCompraInsumo> attachedOrdenCompraInsumoListNew = new ArrayList<OrdenCompraInsumo>();
            for (OrdenCompraInsumo ordenCompraInsumoListNewOrdenCompraInsumoToAttach : ordenCompraInsumoListNew) {
                ordenCompraInsumoListNewOrdenCompraInsumoToAttach = em.getReference(ordenCompraInsumoListNewOrdenCompraInsumoToAttach.getClass(), ordenCompraInsumoListNewOrdenCompraInsumoToAttach.getId());
                attachedOrdenCompraInsumoListNew.add(ordenCompraInsumoListNewOrdenCompraInsumoToAttach);
            }
            ordenCompraInsumoListNew = attachedOrdenCompraInsumoListNew;
            ordenCompra.setOrdenCompraInsumoList(ordenCompraInsumoListNew);
            List<EstadoOrdenCompra> attachedEstadoOrdenCompraListNew = new ArrayList<EstadoOrdenCompra>();
            for (EstadoOrdenCompra estadoOrdenCompraListNewEstadoOrdenCompraToAttach : estadoOrdenCompraListNew) {
                estadoOrdenCompraListNewEstadoOrdenCompraToAttach = em.getReference(estadoOrdenCompraListNewEstadoOrdenCompraToAttach.getClass(), estadoOrdenCompraListNewEstadoOrdenCompraToAttach.getId());
                attachedEstadoOrdenCompraListNew.add(estadoOrdenCompraListNewEstadoOrdenCompraToAttach);
            }
            estadoOrdenCompraListNew = attachedEstadoOrdenCompraListNew;
            ordenCompra.setEstadoOrdenCompraList(estadoOrdenCompraListNew);
            ordenCompra = em.merge(ordenCompra);
            for (CuentaCobro cuentaCobroListOldCuentaCobro : cuentaCobroListOld) {
                if (!cuentaCobroListNew.contains(cuentaCobroListOldCuentaCobro)) {
                    cuentaCobroListOldCuentaCobro.setIdOrdenCompra(null);
                    cuentaCobroListOldCuentaCobro = em.merge(cuentaCobroListOldCuentaCobro);
                }
            }
            for (CuentaCobro cuentaCobroListNewCuentaCobro : cuentaCobroListNew) {
                if (!cuentaCobroListOld.contains(cuentaCobroListNewCuentaCobro)) {
                    OrdenCompra oldIdOrdenCompraOfCuentaCobroListNewCuentaCobro = cuentaCobroListNewCuentaCobro.getIdOrdenCompra();
                    cuentaCobroListNewCuentaCobro.setIdOrdenCompra(ordenCompra);
                    cuentaCobroListNewCuentaCobro = em.merge(cuentaCobroListNewCuentaCobro);
                    if (oldIdOrdenCompraOfCuentaCobroListNewCuentaCobro != null && !oldIdOrdenCompraOfCuentaCobroListNewCuentaCobro.equals(ordenCompra)) {
                        oldIdOrdenCompraOfCuentaCobroListNewCuentaCobro.getCuentaCobroList().remove(cuentaCobroListNewCuentaCobro);
                        oldIdOrdenCompraOfCuentaCobroListNewCuentaCobro = em.merge(oldIdOrdenCompraOfCuentaCobroListNewCuentaCobro);
                    }
                }
            }
            for (FacturaCompra facturaCompraListOldFacturaCompra : facturaCompraListOld) {
                if (!facturaCompraListNew.contains(facturaCompraListOldFacturaCompra)) {
                    facturaCompraListOldFacturaCompra.setIdOrdenCompra(null);
                    facturaCompraListOldFacturaCompra = em.merge(facturaCompraListOldFacturaCompra);
                }
            }
            for (FacturaCompra facturaCompraListNewFacturaCompra : facturaCompraListNew) {
                if (!facturaCompraListOld.contains(facturaCompraListNewFacturaCompra)) {
                    OrdenCompra oldIdOrdenCompraOfFacturaCompraListNewFacturaCompra = facturaCompraListNewFacturaCompra.getIdOrdenCompra();
                    facturaCompraListNewFacturaCompra.setIdOrdenCompra(ordenCompra);
                    facturaCompraListNewFacturaCompra = em.merge(facturaCompraListNewFacturaCompra);
                    if (oldIdOrdenCompraOfFacturaCompraListNewFacturaCompra != null && !oldIdOrdenCompraOfFacturaCompraListNewFacturaCompra.equals(ordenCompra)) {
                        oldIdOrdenCompraOfFacturaCompraListNewFacturaCompra.getFacturaCompraList().remove(facturaCompraListNewFacturaCompra);
                        oldIdOrdenCompraOfFacturaCompraListNewFacturaCompra = em.merge(oldIdOrdenCompraOfFacturaCompraListNewFacturaCompra);
                    }
                }
            }
            for (OrdenCompraInsumo ordenCompraInsumoListOldOrdenCompraInsumo : ordenCompraInsumoListOld) {
                if (!ordenCompraInsumoListNew.contains(ordenCompraInsumoListOldOrdenCompraInsumo)) {
                    ordenCompraInsumoListOldOrdenCompraInsumo.setIdOrdenCompra(null);
                    ordenCompraInsumoListOldOrdenCompraInsumo = em.merge(ordenCompraInsumoListOldOrdenCompraInsumo);
                }
            }
            for (OrdenCompraInsumo ordenCompraInsumoListNewOrdenCompraInsumo : ordenCompraInsumoListNew) {
                if (!ordenCompraInsumoListOld.contains(ordenCompraInsumoListNewOrdenCompraInsumo)) {
                    OrdenCompra oldIdOrdenCompraOfOrdenCompraInsumoListNewOrdenCompraInsumo = ordenCompraInsumoListNewOrdenCompraInsumo.getIdOrdenCompra();
                    ordenCompraInsumoListNewOrdenCompraInsumo.setIdOrdenCompra(ordenCompra);
                    ordenCompraInsumoListNewOrdenCompraInsumo = em.merge(ordenCompraInsumoListNewOrdenCompraInsumo);
                    if (oldIdOrdenCompraOfOrdenCompraInsumoListNewOrdenCompraInsumo != null && !oldIdOrdenCompraOfOrdenCompraInsumoListNewOrdenCompraInsumo.equals(ordenCompra)) {
                        oldIdOrdenCompraOfOrdenCompraInsumoListNewOrdenCompraInsumo.getOrdenCompraInsumoList().remove(ordenCompraInsumoListNewOrdenCompraInsumo);
                        oldIdOrdenCompraOfOrdenCompraInsumoListNewOrdenCompraInsumo = em.merge(oldIdOrdenCompraOfOrdenCompraInsumoListNewOrdenCompraInsumo);
                    }
                }
            }
            for (EstadoOrdenCompra estadoOrdenCompraListOldEstadoOrdenCompra : estadoOrdenCompraListOld) {
                if (!estadoOrdenCompraListNew.contains(estadoOrdenCompraListOldEstadoOrdenCompra)) {
                    estadoOrdenCompraListOldEstadoOrdenCompra.setIdOrdenCompra(null);
                    estadoOrdenCompraListOldEstadoOrdenCompra = em.merge(estadoOrdenCompraListOldEstadoOrdenCompra);
                }
            }
            for (EstadoOrdenCompra estadoOrdenCompraListNewEstadoOrdenCompra : estadoOrdenCompraListNew) {
                if (!estadoOrdenCompraListOld.contains(estadoOrdenCompraListNewEstadoOrdenCompra)) {
                    OrdenCompra oldIdOrdenCompraOfEstadoOrdenCompraListNewEstadoOrdenCompra = estadoOrdenCompraListNewEstadoOrdenCompra.getIdOrdenCompra();
                    estadoOrdenCompraListNewEstadoOrdenCompra.setIdOrdenCompra(ordenCompra);
                    estadoOrdenCompraListNewEstadoOrdenCompra = em.merge(estadoOrdenCompraListNewEstadoOrdenCompra);
                    if (oldIdOrdenCompraOfEstadoOrdenCompraListNewEstadoOrdenCompra != null && !oldIdOrdenCompraOfEstadoOrdenCompraListNewEstadoOrdenCompra.equals(ordenCompra)) {
                        oldIdOrdenCompraOfEstadoOrdenCompraListNewEstadoOrdenCompra.getEstadoOrdenCompraList().remove(estadoOrdenCompraListNewEstadoOrdenCompra);
                        oldIdOrdenCompraOfEstadoOrdenCompraListNewEstadoOrdenCompra = em.merge(oldIdOrdenCompraOfEstadoOrdenCompraListNewEstadoOrdenCompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ordenCompra.getId();
                if (findOrdenCompra(id) == null) {
                    throw new NonexistentEntityException("The ordenCompra with id " + id + " no longer exists.");
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
            OrdenCompra ordenCompra;
            try {
                ordenCompra = em.getReference(OrdenCompra.class, id);
                ordenCompra.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ordenCompra with id " + id + " no longer exists.", enfe);
            }
            List<CuentaCobro> cuentaCobroList = ordenCompra.getCuentaCobroList();
            for (CuentaCobro cuentaCobroListCuentaCobro : cuentaCobroList) {
                cuentaCobroListCuentaCobro.setIdOrdenCompra(null);
                cuentaCobroListCuentaCobro = em.merge(cuentaCobroListCuentaCobro);
            }
            List<FacturaCompra> facturaCompraList = ordenCompra.getFacturaCompraList();
            for (FacturaCompra facturaCompraListFacturaCompra : facturaCompraList) {
                facturaCompraListFacturaCompra.setIdOrdenCompra(null);
                facturaCompraListFacturaCompra = em.merge(facturaCompraListFacturaCompra);
            }
            List<OrdenCompraInsumo> ordenCompraInsumoList = ordenCompra.getOrdenCompraInsumoList();
            for (OrdenCompraInsumo ordenCompraInsumoListOrdenCompraInsumo : ordenCompraInsumoList) {
                ordenCompraInsumoListOrdenCompraInsumo.setIdOrdenCompra(null);
                ordenCompraInsumoListOrdenCompraInsumo = em.merge(ordenCompraInsumoListOrdenCompraInsumo);
            }
            List<EstadoOrdenCompra> estadoOrdenCompraList = ordenCompra.getEstadoOrdenCompraList();
            for (EstadoOrdenCompra estadoOrdenCompraListEstadoOrdenCompra : estadoOrdenCompraList) {
                estadoOrdenCompraListEstadoOrdenCompra.setIdOrdenCompra(null);
                estadoOrdenCompraListEstadoOrdenCompra = em.merge(estadoOrdenCompraListEstadoOrdenCompra);
            }
            em.remove(ordenCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrdenCompra> findOrdenCompraEntities() {
        return findOrdenCompraEntities(true, -1, -1);
    }

    public List<OrdenCompra> findOrdenCompraEntities(int maxResults, int firstResult) {
        return findOrdenCompraEntities(false, maxResults, firstResult);
    }

    private List<OrdenCompra> findOrdenCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrdenCompra.class));
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

    public OrdenCompra findOrdenCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrdenCompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdenCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrdenCompra> rt = cq.from(OrdenCompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
