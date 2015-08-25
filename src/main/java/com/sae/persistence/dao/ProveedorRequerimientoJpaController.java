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
import com.sae.persistence.domain.RequerimentoInsumo;
import com.sae.persistence.domain.OrdenCompraInsumo;
import com.sae.persistence.domain.ProveedorRequerimiento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ProveedorRequerimientoJpaController implements Serializable {

    public ProveedorRequerimientoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProveedorRequerimiento proveedorRequerimiento) throws PreexistingEntityException, Exception {
        if (proveedorRequerimiento.getOrdenCompraInsumoList() == null) {
            proveedorRequerimiento.setOrdenCompraInsumoList(new ArrayList<OrdenCompraInsumo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Requerimiento idRequerimiento = proveedorRequerimiento.getIdRequerimiento();
            if (idRequerimiento != null) {
                idRequerimiento = em.getReference(idRequerimiento.getClass(), idRequerimiento.getId());
                proveedorRequerimiento.setIdRequerimiento(idRequerimiento);
            }
            RequerimentoInsumo idInsumoRequerimiento = proveedorRequerimiento.getIdInsumoRequerimiento();
            if (idInsumoRequerimiento != null) {
                idInsumoRequerimiento = em.getReference(idInsumoRequerimiento.getClass(), idInsumoRequerimiento.getId());
                proveedorRequerimiento.setIdInsumoRequerimiento(idInsumoRequerimiento);
            }
            List<OrdenCompraInsumo> attachedOrdenCompraInsumoList = new ArrayList<OrdenCompraInsumo>();
            for (OrdenCompraInsumo ordenCompraInsumoListOrdenCompraInsumoToAttach : proveedorRequerimiento.getOrdenCompraInsumoList()) {
                ordenCompraInsumoListOrdenCompraInsumoToAttach = em.getReference(ordenCompraInsumoListOrdenCompraInsumoToAttach.getClass(), ordenCompraInsumoListOrdenCompraInsumoToAttach.getId());
                attachedOrdenCompraInsumoList.add(ordenCompraInsumoListOrdenCompraInsumoToAttach);
            }
            proveedorRequerimiento.setOrdenCompraInsumoList(attachedOrdenCompraInsumoList);
            em.persist(proveedorRequerimiento);
            if (idRequerimiento != null) {
                idRequerimiento.getProveedorRequerimientoList().add(proveedorRequerimiento);
                idRequerimiento = em.merge(idRequerimiento);
            }
            if (idInsumoRequerimiento != null) {
                idInsumoRequerimiento.getProveedorRequerimientoList().add(proveedorRequerimiento);
                idInsumoRequerimiento = em.merge(idInsumoRequerimiento);
            }
            for (OrdenCompraInsumo ordenCompraInsumoListOrdenCompraInsumo : proveedorRequerimiento.getOrdenCompraInsumoList()) {
                ProveedorRequerimiento oldIdProveedorRequerimientoOfOrdenCompraInsumoListOrdenCompraInsumo = ordenCompraInsumoListOrdenCompraInsumo.getIdProveedorRequerimiento();
                ordenCompraInsumoListOrdenCompraInsumo.setIdProveedorRequerimiento(proveedorRequerimiento);
                ordenCompraInsumoListOrdenCompraInsumo = em.merge(ordenCompraInsumoListOrdenCompraInsumo);
                if (oldIdProveedorRequerimientoOfOrdenCompraInsumoListOrdenCompraInsumo != null) {
                    oldIdProveedorRequerimientoOfOrdenCompraInsumoListOrdenCompraInsumo.getOrdenCompraInsumoList().remove(ordenCompraInsumoListOrdenCompraInsumo);
                    oldIdProveedorRequerimientoOfOrdenCompraInsumoListOrdenCompraInsumo = em.merge(oldIdProveedorRequerimientoOfOrdenCompraInsumoListOrdenCompraInsumo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProveedorRequerimiento(proveedorRequerimiento.getId()) != null) {
                throw new PreexistingEntityException("ProveedorRequerimiento " + proveedorRequerimiento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProveedorRequerimiento proveedorRequerimiento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProveedorRequerimiento persistentProveedorRequerimiento = em.find(ProveedorRequerimiento.class, proveedorRequerimiento.getId());
            Requerimiento idRequerimientoOld = persistentProveedorRequerimiento.getIdRequerimiento();
            Requerimiento idRequerimientoNew = proveedorRequerimiento.getIdRequerimiento();
            RequerimentoInsumo idInsumoRequerimientoOld = persistentProveedorRequerimiento.getIdInsumoRequerimiento();
            RequerimentoInsumo idInsumoRequerimientoNew = proveedorRequerimiento.getIdInsumoRequerimiento();
            List<OrdenCompraInsumo> ordenCompraInsumoListOld = persistentProveedorRequerimiento.getOrdenCompraInsumoList();
            List<OrdenCompraInsumo> ordenCompraInsumoListNew = proveedorRequerimiento.getOrdenCompraInsumoList();
            if (idRequerimientoNew != null) {
                idRequerimientoNew = em.getReference(idRequerimientoNew.getClass(), idRequerimientoNew.getId());
                proveedorRequerimiento.setIdRequerimiento(idRequerimientoNew);
            }
            if (idInsumoRequerimientoNew != null) {
                idInsumoRequerimientoNew = em.getReference(idInsumoRequerimientoNew.getClass(), idInsumoRequerimientoNew.getId());
                proveedorRequerimiento.setIdInsumoRequerimiento(idInsumoRequerimientoNew);
            }
            List<OrdenCompraInsumo> attachedOrdenCompraInsumoListNew = new ArrayList<OrdenCompraInsumo>();
            for (OrdenCompraInsumo ordenCompraInsumoListNewOrdenCompraInsumoToAttach : ordenCompraInsumoListNew) {
                ordenCompraInsumoListNewOrdenCompraInsumoToAttach = em.getReference(ordenCompraInsumoListNewOrdenCompraInsumoToAttach.getClass(), ordenCompraInsumoListNewOrdenCompraInsumoToAttach.getId());
                attachedOrdenCompraInsumoListNew.add(ordenCompraInsumoListNewOrdenCompraInsumoToAttach);
            }
            ordenCompraInsumoListNew = attachedOrdenCompraInsumoListNew;
            proveedorRequerimiento.setOrdenCompraInsumoList(ordenCompraInsumoListNew);
            proveedorRequerimiento = em.merge(proveedorRequerimiento);
            if (idRequerimientoOld != null && !idRequerimientoOld.equals(idRequerimientoNew)) {
                idRequerimientoOld.getProveedorRequerimientoList().remove(proveedorRequerimiento);
                idRequerimientoOld = em.merge(idRequerimientoOld);
            }
            if (idRequerimientoNew != null && !idRequerimientoNew.equals(idRequerimientoOld)) {
                idRequerimientoNew.getProveedorRequerimientoList().add(proveedorRequerimiento);
                idRequerimientoNew = em.merge(idRequerimientoNew);
            }
            if (idInsumoRequerimientoOld != null && !idInsumoRequerimientoOld.equals(idInsumoRequerimientoNew)) {
                idInsumoRequerimientoOld.getProveedorRequerimientoList().remove(proveedorRequerimiento);
                idInsumoRequerimientoOld = em.merge(idInsumoRequerimientoOld);
            }
            if (idInsumoRequerimientoNew != null && !idInsumoRequerimientoNew.equals(idInsumoRequerimientoOld)) {
                idInsumoRequerimientoNew.getProveedorRequerimientoList().add(proveedorRequerimiento);
                idInsumoRequerimientoNew = em.merge(idInsumoRequerimientoNew);
            }
            for (OrdenCompraInsumo ordenCompraInsumoListOldOrdenCompraInsumo : ordenCompraInsumoListOld) {
                if (!ordenCompraInsumoListNew.contains(ordenCompraInsumoListOldOrdenCompraInsumo)) {
                    ordenCompraInsumoListOldOrdenCompraInsumo.setIdProveedorRequerimiento(null);
                    ordenCompraInsumoListOldOrdenCompraInsumo = em.merge(ordenCompraInsumoListOldOrdenCompraInsumo);
                }
            }
            for (OrdenCompraInsumo ordenCompraInsumoListNewOrdenCompraInsumo : ordenCompraInsumoListNew) {
                if (!ordenCompraInsumoListOld.contains(ordenCompraInsumoListNewOrdenCompraInsumo)) {
                    ProveedorRequerimiento oldIdProveedorRequerimientoOfOrdenCompraInsumoListNewOrdenCompraInsumo = ordenCompraInsumoListNewOrdenCompraInsumo.getIdProveedorRequerimiento();
                    ordenCompraInsumoListNewOrdenCompraInsumo.setIdProveedorRequerimiento(proveedorRequerimiento);
                    ordenCompraInsumoListNewOrdenCompraInsumo = em.merge(ordenCompraInsumoListNewOrdenCompraInsumo);
                    if (oldIdProveedorRequerimientoOfOrdenCompraInsumoListNewOrdenCompraInsumo != null && !oldIdProveedorRequerimientoOfOrdenCompraInsumoListNewOrdenCompraInsumo.equals(proveedorRequerimiento)) {
                        oldIdProveedorRequerimientoOfOrdenCompraInsumoListNewOrdenCompraInsumo.getOrdenCompraInsumoList().remove(ordenCompraInsumoListNewOrdenCompraInsumo);
                        oldIdProveedorRequerimientoOfOrdenCompraInsumoListNewOrdenCompraInsumo = em.merge(oldIdProveedorRequerimientoOfOrdenCompraInsumoListNewOrdenCompraInsumo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proveedorRequerimiento.getId();
                if (findProveedorRequerimiento(id) == null) {
                    throw new NonexistentEntityException("The proveedorRequerimiento with id " + id + " no longer exists.");
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
            ProveedorRequerimiento proveedorRequerimiento;
            try {
                proveedorRequerimiento = em.getReference(ProveedorRequerimiento.class, id);
                proveedorRequerimiento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedorRequerimiento with id " + id + " no longer exists.", enfe);
            }
            Requerimiento idRequerimiento = proveedorRequerimiento.getIdRequerimiento();
            if (idRequerimiento != null) {
                idRequerimiento.getProveedorRequerimientoList().remove(proveedorRequerimiento);
                idRequerimiento = em.merge(idRequerimiento);
            }
            RequerimentoInsumo idInsumoRequerimiento = proveedorRequerimiento.getIdInsumoRequerimiento();
            if (idInsumoRequerimiento != null) {
                idInsumoRequerimiento.getProveedorRequerimientoList().remove(proveedorRequerimiento);
                idInsumoRequerimiento = em.merge(idInsumoRequerimiento);
            }
            List<OrdenCompraInsumo> ordenCompraInsumoList = proveedorRequerimiento.getOrdenCompraInsumoList();
            for (OrdenCompraInsumo ordenCompraInsumoListOrdenCompraInsumo : ordenCompraInsumoList) {
                ordenCompraInsumoListOrdenCompraInsumo.setIdProveedorRequerimiento(null);
                ordenCompraInsumoListOrdenCompraInsumo = em.merge(ordenCompraInsumoListOrdenCompraInsumo);
            }
            em.remove(proveedorRequerimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProveedorRequerimiento> findProveedorRequerimientoEntities() {
        return findProveedorRequerimientoEntities(true, -1, -1);
    }

    public List<ProveedorRequerimiento> findProveedorRequerimientoEntities(int maxResults, int firstResult) {
        return findProveedorRequerimientoEntities(false, maxResults, firstResult);
    }

    private List<ProveedorRequerimiento> findProveedorRequerimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProveedorRequerimiento.class));
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

    public ProveedorRequerimiento findProveedorRequerimiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProveedorRequerimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedorRequerimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProveedorRequerimiento> rt = cq.from(ProveedorRequerimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
