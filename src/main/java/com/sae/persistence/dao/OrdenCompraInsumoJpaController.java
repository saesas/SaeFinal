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
import com.sae.persistence.domain.ProveedorRequerimiento;
import com.sae.persistence.domain.OrdenCompra;
import com.sae.persistence.domain.InsumoFacturaCompra;
import com.sae.persistence.domain.OrdenCompraInsumo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class OrdenCompraInsumoJpaController implements Serializable {

    public OrdenCompraInsumoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrdenCompraInsumo ordenCompraInsumo) throws PreexistingEntityException, Exception {
        if (ordenCompraInsumo.getInsumoFacturaCompraList() == null) {
            ordenCompraInsumo.setInsumoFacturaCompraList(new ArrayList<InsumoFacturaCompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProveedorRequerimiento idProveedorRequerimiento = ordenCompraInsumo.getIdProveedorRequerimiento();
            if (idProveedorRequerimiento != null) {
                idProveedorRequerimiento = em.getReference(idProveedorRequerimiento.getClass(), idProveedorRequerimiento.getId());
                ordenCompraInsumo.setIdProveedorRequerimiento(idProveedorRequerimiento);
            }
            OrdenCompra idOrdenCompra = ordenCompraInsumo.getIdOrdenCompra();
            if (idOrdenCompra != null) {
                idOrdenCompra = em.getReference(idOrdenCompra.getClass(), idOrdenCompra.getId());
                ordenCompraInsumo.setIdOrdenCompra(idOrdenCompra);
            }
            List<InsumoFacturaCompra> attachedInsumoFacturaCompraList = new ArrayList<InsumoFacturaCompra>();
            for (InsumoFacturaCompra insumoFacturaCompraListInsumoFacturaCompraToAttach : ordenCompraInsumo.getInsumoFacturaCompraList()) {
                insumoFacturaCompraListInsumoFacturaCompraToAttach = em.getReference(insumoFacturaCompraListInsumoFacturaCompraToAttach.getClass(), insumoFacturaCompraListInsumoFacturaCompraToAttach.getId());
                attachedInsumoFacturaCompraList.add(insumoFacturaCompraListInsumoFacturaCompraToAttach);
            }
            ordenCompraInsumo.setInsumoFacturaCompraList(attachedInsumoFacturaCompraList);
            em.persist(ordenCompraInsumo);
            if (idProveedorRequerimiento != null) {
                idProveedorRequerimiento.getOrdenCompraInsumoList().add(ordenCompraInsumo);
                idProveedorRequerimiento = em.merge(idProveedorRequerimiento);
            }
            if (idOrdenCompra != null) {
                idOrdenCompra.getOrdenCompraInsumoList().add(ordenCompraInsumo);
                idOrdenCompra = em.merge(idOrdenCompra);
            }
            for (InsumoFacturaCompra insumoFacturaCompraListInsumoFacturaCompra : ordenCompraInsumo.getInsumoFacturaCompraList()) {
                OrdenCompraInsumo oldIdOrdenInsumoOfInsumoFacturaCompraListInsumoFacturaCompra = insumoFacturaCompraListInsumoFacturaCompra.getIdOrdenInsumo();
                insumoFacturaCompraListInsumoFacturaCompra.setIdOrdenInsumo(ordenCompraInsumo);
                insumoFacturaCompraListInsumoFacturaCompra = em.merge(insumoFacturaCompraListInsumoFacturaCompra);
                if (oldIdOrdenInsumoOfInsumoFacturaCompraListInsumoFacturaCompra != null) {
                    oldIdOrdenInsumoOfInsumoFacturaCompraListInsumoFacturaCompra.getInsumoFacturaCompraList().remove(insumoFacturaCompraListInsumoFacturaCompra);
                    oldIdOrdenInsumoOfInsumoFacturaCompraListInsumoFacturaCompra = em.merge(oldIdOrdenInsumoOfInsumoFacturaCompraListInsumoFacturaCompra);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrdenCompraInsumo(ordenCompraInsumo.getId()) != null) {
                throw new PreexistingEntityException("OrdenCompraInsumo " + ordenCompraInsumo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrdenCompraInsumo ordenCompraInsumo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrdenCompraInsumo persistentOrdenCompraInsumo = em.find(OrdenCompraInsumo.class, ordenCompraInsumo.getId());
            ProveedorRequerimiento idProveedorRequerimientoOld = persistentOrdenCompraInsumo.getIdProveedorRequerimiento();
            ProveedorRequerimiento idProveedorRequerimientoNew = ordenCompraInsumo.getIdProveedorRequerimiento();
            OrdenCompra idOrdenCompraOld = persistentOrdenCompraInsumo.getIdOrdenCompra();
            OrdenCompra idOrdenCompraNew = ordenCompraInsumo.getIdOrdenCompra();
            List<InsumoFacturaCompra> insumoFacturaCompraListOld = persistentOrdenCompraInsumo.getInsumoFacturaCompraList();
            List<InsumoFacturaCompra> insumoFacturaCompraListNew = ordenCompraInsumo.getInsumoFacturaCompraList();
            if (idProveedorRequerimientoNew != null) {
                idProveedorRequerimientoNew = em.getReference(idProveedorRequerimientoNew.getClass(), idProveedorRequerimientoNew.getId());
                ordenCompraInsumo.setIdProveedorRequerimiento(idProveedorRequerimientoNew);
            }
            if (idOrdenCompraNew != null) {
                idOrdenCompraNew = em.getReference(idOrdenCompraNew.getClass(), idOrdenCompraNew.getId());
                ordenCompraInsumo.setIdOrdenCompra(idOrdenCompraNew);
            }
            List<InsumoFacturaCompra> attachedInsumoFacturaCompraListNew = new ArrayList<InsumoFacturaCompra>();
            for (InsumoFacturaCompra insumoFacturaCompraListNewInsumoFacturaCompraToAttach : insumoFacturaCompraListNew) {
                insumoFacturaCompraListNewInsumoFacturaCompraToAttach = em.getReference(insumoFacturaCompraListNewInsumoFacturaCompraToAttach.getClass(), insumoFacturaCompraListNewInsumoFacturaCompraToAttach.getId());
                attachedInsumoFacturaCompraListNew.add(insumoFacturaCompraListNewInsumoFacturaCompraToAttach);
            }
            insumoFacturaCompraListNew = attachedInsumoFacturaCompraListNew;
            ordenCompraInsumo.setInsumoFacturaCompraList(insumoFacturaCompraListNew);
            ordenCompraInsumo = em.merge(ordenCompraInsumo);
            if (idProveedorRequerimientoOld != null && !idProveedorRequerimientoOld.equals(idProveedorRequerimientoNew)) {
                idProveedorRequerimientoOld.getOrdenCompraInsumoList().remove(ordenCompraInsumo);
                idProveedorRequerimientoOld = em.merge(idProveedorRequerimientoOld);
            }
            if (idProveedorRequerimientoNew != null && !idProveedorRequerimientoNew.equals(idProveedorRequerimientoOld)) {
                idProveedorRequerimientoNew.getOrdenCompraInsumoList().add(ordenCompraInsumo);
                idProveedorRequerimientoNew = em.merge(idProveedorRequerimientoNew);
            }
            if (idOrdenCompraOld != null && !idOrdenCompraOld.equals(idOrdenCompraNew)) {
                idOrdenCompraOld.getOrdenCompraInsumoList().remove(ordenCompraInsumo);
                idOrdenCompraOld = em.merge(idOrdenCompraOld);
            }
            if (idOrdenCompraNew != null && !idOrdenCompraNew.equals(idOrdenCompraOld)) {
                idOrdenCompraNew.getOrdenCompraInsumoList().add(ordenCompraInsumo);
                idOrdenCompraNew = em.merge(idOrdenCompraNew);
            }
            for (InsumoFacturaCompra insumoFacturaCompraListOldInsumoFacturaCompra : insumoFacturaCompraListOld) {
                if (!insumoFacturaCompraListNew.contains(insumoFacturaCompraListOldInsumoFacturaCompra)) {
                    insumoFacturaCompraListOldInsumoFacturaCompra.setIdOrdenInsumo(null);
                    insumoFacturaCompraListOldInsumoFacturaCompra = em.merge(insumoFacturaCompraListOldInsumoFacturaCompra);
                }
            }
            for (InsumoFacturaCompra insumoFacturaCompraListNewInsumoFacturaCompra : insumoFacturaCompraListNew) {
                if (!insumoFacturaCompraListOld.contains(insumoFacturaCompraListNewInsumoFacturaCompra)) {
                    OrdenCompraInsumo oldIdOrdenInsumoOfInsumoFacturaCompraListNewInsumoFacturaCompra = insumoFacturaCompraListNewInsumoFacturaCompra.getIdOrdenInsumo();
                    insumoFacturaCompraListNewInsumoFacturaCompra.setIdOrdenInsumo(ordenCompraInsumo);
                    insumoFacturaCompraListNewInsumoFacturaCompra = em.merge(insumoFacturaCompraListNewInsumoFacturaCompra);
                    if (oldIdOrdenInsumoOfInsumoFacturaCompraListNewInsumoFacturaCompra != null && !oldIdOrdenInsumoOfInsumoFacturaCompraListNewInsumoFacturaCompra.equals(ordenCompraInsumo)) {
                        oldIdOrdenInsumoOfInsumoFacturaCompraListNewInsumoFacturaCompra.getInsumoFacturaCompraList().remove(insumoFacturaCompraListNewInsumoFacturaCompra);
                        oldIdOrdenInsumoOfInsumoFacturaCompraListNewInsumoFacturaCompra = em.merge(oldIdOrdenInsumoOfInsumoFacturaCompraListNewInsumoFacturaCompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ordenCompraInsumo.getId();
                if (findOrdenCompraInsumo(id) == null) {
                    throw new NonexistentEntityException("The ordenCompraInsumo with id " + id + " no longer exists.");
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
            OrdenCompraInsumo ordenCompraInsumo;
            try {
                ordenCompraInsumo = em.getReference(OrdenCompraInsumo.class, id);
                ordenCompraInsumo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ordenCompraInsumo with id " + id + " no longer exists.", enfe);
            }
            ProveedorRequerimiento idProveedorRequerimiento = ordenCompraInsumo.getIdProveedorRequerimiento();
            if (idProveedorRequerimiento != null) {
                idProveedorRequerimiento.getOrdenCompraInsumoList().remove(ordenCompraInsumo);
                idProveedorRequerimiento = em.merge(idProveedorRequerimiento);
            }
            OrdenCompra idOrdenCompra = ordenCompraInsumo.getIdOrdenCompra();
            if (idOrdenCompra != null) {
                idOrdenCompra.getOrdenCompraInsumoList().remove(ordenCompraInsumo);
                idOrdenCompra = em.merge(idOrdenCompra);
            }
            List<InsumoFacturaCompra> insumoFacturaCompraList = ordenCompraInsumo.getInsumoFacturaCompraList();
            for (InsumoFacturaCompra insumoFacturaCompraListInsumoFacturaCompra : insumoFacturaCompraList) {
                insumoFacturaCompraListInsumoFacturaCompra.setIdOrdenInsumo(null);
                insumoFacturaCompraListInsumoFacturaCompra = em.merge(insumoFacturaCompraListInsumoFacturaCompra);
            }
            em.remove(ordenCompraInsumo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrdenCompraInsumo> findOrdenCompraInsumoEntities() {
        return findOrdenCompraInsumoEntities(true, -1, -1);
    }

    public List<OrdenCompraInsumo> findOrdenCompraInsumoEntities(int maxResults, int firstResult) {
        return findOrdenCompraInsumoEntities(false, maxResults, firstResult);
    }

    private List<OrdenCompraInsumo> findOrdenCompraInsumoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrdenCompraInsumo.class));
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

    public OrdenCompraInsumo findOrdenCompraInsumo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrdenCompraInsumo.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdenCompraInsumoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrdenCompraInsumo> rt = cq.from(OrdenCompraInsumo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
