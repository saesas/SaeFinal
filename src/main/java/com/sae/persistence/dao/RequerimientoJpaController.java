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
import com.sae.persistence.domain.TipoRequerimiento;
import com.sae.persistence.domain.TipoAlcanceRequerimiento;
import com.sae.persistence.domain.ProveedorRequerimiento;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.RequerimentoInsumo;
import com.sae.persistence.domain.Requerimiento;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class RequerimientoJpaController implements Serializable {

    public RequerimientoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Requerimiento requerimiento) throws PreexistingEntityException, Exception {
        if (requerimiento.getProveedorRequerimientoList() == null) {
            requerimiento.setProveedorRequerimientoList(new ArrayList<ProveedorRequerimiento>());
        }
        if (requerimiento.getRequerimentoInsumoList() == null) {
            requerimiento.setRequerimentoInsumoList(new ArrayList<RequerimentoInsumo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoRequerimiento idTipo = requerimiento.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getId());
                requerimiento.setIdTipo(idTipo);
            }
            TipoAlcanceRequerimiento idAlcance = requerimiento.getIdAlcance();
            if (idAlcance != null) {
                idAlcance = em.getReference(idAlcance.getClass(), idAlcance.getId());
                requerimiento.setIdAlcance(idAlcance);
            }
            List<ProveedorRequerimiento> attachedProveedorRequerimientoList = new ArrayList<ProveedorRequerimiento>();
            for (ProveedorRequerimiento proveedorRequerimientoListProveedorRequerimientoToAttach : requerimiento.getProveedorRequerimientoList()) {
                proveedorRequerimientoListProveedorRequerimientoToAttach = em.getReference(proveedorRequerimientoListProveedorRequerimientoToAttach.getClass(), proveedorRequerimientoListProveedorRequerimientoToAttach.getId());
                attachedProveedorRequerimientoList.add(proveedorRequerimientoListProveedorRequerimientoToAttach);
            }
            requerimiento.setProveedorRequerimientoList(attachedProveedorRequerimientoList);
            List<RequerimentoInsumo> attachedRequerimentoInsumoList = new ArrayList<RequerimentoInsumo>();
            for (RequerimentoInsumo requerimentoInsumoListRequerimentoInsumoToAttach : requerimiento.getRequerimentoInsumoList()) {
                requerimentoInsumoListRequerimentoInsumoToAttach = em.getReference(requerimentoInsumoListRequerimentoInsumoToAttach.getClass(), requerimentoInsumoListRequerimentoInsumoToAttach.getId());
                attachedRequerimentoInsumoList.add(requerimentoInsumoListRequerimentoInsumoToAttach);
            }
            requerimiento.setRequerimentoInsumoList(attachedRequerimentoInsumoList);
            em.persist(requerimiento);
            if (idTipo != null) {
                idTipo.getRequerimientoList().add(requerimiento);
                idTipo = em.merge(idTipo);
            }
            if (idAlcance != null) {
                idAlcance.getRequerimientoList().add(requerimiento);
                idAlcance = em.merge(idAlcance);
            }
            for (ProveedorRequerimiento proveedorRequerimientoListProveedorRequerimiento : requerimiento.getProveedorRequerimientoList()) {
                Requerimiento oldIdRequerimientoOfProveedorRequerimientoListProveedorRequerimiento = proveedorRequerimientoListProveedorRequerimiento.getIdRequerimiento();
                proveedorRequerimientoListProveedorRequerimiento.setIdRequerimiento(requerimiento);
                proveedorRequerimientoListProveedorRequerimiento = em.merge(proveedorRequerimientoListProveedorRequerimiento);
                if (oldIdRequerimientoOfProveedorRequerimientoListProveedorRequerimiento != null) {
                    oldIdRequerimientoOfProveedorRequerimientoListProveedorRequerimiento.getProveedorRequerimientoList().remove(proveedorRequerimientoListProveedorRequerimiento);
                    oldIdRequerimientoOfProveedorRequerimientoListProveedorRequerimiento = em.merge(oldIdRequerimientoOfProveedorRequerimientoListProveedorRequerimiento);
                }
            }
            for (RequerimentoInsumo requerimentoInsumoListRequerimentoInsumo : requerimiento.getRequerimentoInsumoList()) {
                Requerimiento oldIdRequerimientoOfRequerimentoInsumoListRequerimentoInsumo = requerimentoInsumoListRequerimentoInsumo.getIdRequerimiento();
                requerimentoInsumoListRequerimentoInsumo.setIdRequerimiento(requerimiento);
                requerimentoInsumoListRequerimentoInsumo = em.merge(requerimentoInsumoListRequerimentoInsumo);
                if (oldIdRequerimientoOfRequerimentoInsumoListRequerimentoInsumo != null) {
                    oldIdRequerimientoOfRequerimentoInsumoListRequerimentoInsumo.getRequerimentoInsumoList().remove(requerimentoInsumoListRequerimentoInsumo);
                    oldIdRequerimientoOfRequerimentoInsumoListRequerimentoInsumo = em.merge(oldIdRequerimientoOfRequerimentoInsumoListRequerimentoInsumo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRequerimiento(requerimiento.getId()) != null) {
                throw new PreexistingEntityException("Requerimiento " + requerimiento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Requerimiento requerimiento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Requerimiento persistentRequerimiento = em.find(Requerimiento.class, requerimiento.getId());
            TipoRequerimiento idTipoOld = persistentRequerimiento.getIdTipo();
            TipoRequerimiento idTipoNew = requerimiento.getIdTipo();
            TipoAlcanceRequerimiento idAlcanceOld = persistentRequerimiento.getIdAlcance();
            TipoAlcanceRequerimiento idAlcanceNew = requerimiento.getIdAlcance();
            List<ProveedorRequerimiento> proveedorRequerimientoListOld = persistentRequerimiento.getProveedorRequerimientoList();
            List<ProveedorRequerimiento> proveedorRequerimientoListNew = requerimiento.getProveedorRequerimientoList();
            List<RequerimentoInsumo> requerimentoInsumoListOld = persistentRequerimiento.getRequerimentoInsumoList();
            List<RequerimentoInsumo> requerimentoInsumoListNew = requerimiento.getRequerimentoInsumoList();
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getId());
                requerimiento.setIdTipo(idTipoNew);
            }
            if (idAlcanceNew != null) {
                idAlcanceNew = em.getReference(idAlcanceNew.getClass(), idAlcanceNew.getId());
                requerimiento.setIdAlcance(idAlcanceNew);
            }
            List<ProveedorRequerimiento> attachedProveedorRequerimientoListNew = new ArrayList<ProveedorRequerimiento>();
            for (ProveedorRequerimiento proveedorRequerimientoListNewProveedorRequerimientoToAttach : proveedorRequerimientoListNew) {
                proveedorRequerimientoListNewProveedorRequerimientoToAttach = em.getReference(proveedorRequerimientoListNewProveedorRequerimientoToAttach.getClass(), proveedorRequerimientoListNewProveedorRequerimientoToAttach.getId());
                attachedProveedorRequerimientoListNew.add(proveedorRequerimientoListNewProveedorRequerimientoToAttach);
            }
            proveedorRequerimientoListNew = attachedProveedorRequerimientoListNew;
            requerimiento.setProveedorRequerimientoList(proveedorRequerimientoListNew);
            List<RequerimentoInsumo> attachedRequerimentoInsumoListNew = new ArrayList<RequerimentoInsumo>();
            for (RequerimentoInsumo requerimentoInsumoListNewRequerimentoInsumoToAttach : requerimentoInsumoListNew) {
                requerimentoInsumoListNewRequerimentoInsumoToAttach = em.getReference(requerimentoInsumoListNewRequerimentoInsumoToAttach.getClass(), requerimentoInsumoListNewRequerimentoInsumoToAttach.getId());
                attachedRequerimentoInsumoListNew.add(requerimentoInsumoListNewRequerimentoInsumoToAttach);
            }
            requerimentoInsumoListNew = attachedRequerimentoInsumoListNew;
            requerimiento.setRequerimentoInsumoList(requerimentoInsumoListNew);
            requerimiento = em.merge(requerimiento);
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getRequerimientoList().remove(requerimiento);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getRequerimientoList().add(requerimiento);
                idTipoNew = em.merge(idTipoNew);
            }
            if (idAlcanceOld != null && !idAlcanceOld.equals(idAlcanceNew)) {
                idAlcanceOld.getRequerimientoList().remove(requerimiento);
                idAlcanceOld = em.merge(idAlcanceOld);
            }
            if (idAlcanceNew != null && !idAlcanceNew.equals(idAlcanceOld)) {
                idAlcanceNew.getRequerimientoList().add(requerimiento);
                idAlcanceNew = em.merge(idAlcanceNew);
            }
            for (ProveedorRequerimiento proveedorRequerimientoListOldProveedorRequerimiento : proveedorRequerimientoListOld) {
                if (!proveedorRequerimientoListNew.contains(proveedorRequerimientoListOldProveedorRequerimiento)) {
                    proveedorRequerimientoListOldProveedorRequerimiento.setIdRequerimiento(null);
                    proveedorRequerimientoListOldProveedorRequerimiento = em.merge(proveedorRequerimientoListOldProveedorRequerimiento);
                }
            }
            for (ProveedorRequerimiento proveedorRequerimientoListNewProveedorRequerimiento : proveedorRequerimientoListNew) {
                if (!proveedorRequerimientoListOld.contains(proveedorRequerimientoListNewProveedorRequerimiento)) {
                    Requerimiento oldIdRequerimientoOfProveedorRequerimientoListNewProveedorRequerimiento = proveedorRequerimientoListNewProveedorRequerimiento.getIdRequerimiento();
                    proveedorRequerimientoListNewProveedorRequerimiento.setIdRequerimiento(requerimiento);
                    proveedorRequerimientoListNewProveedorRequerimiento = em.merge(proveedorRequerimientoListNewProveedorRequerimiento);
                    if (oldIdRequerimientoOfProveedorRequerimientoListNewProveedorRequerimiento != null && !oldIdRequerimientoOfProveedorRequerimientoListNewProveedorRequerimiento.equals(requerimiento)) {
                        oldIdRequerimientoOfProveedorRequerimientoListNewProveedorRequerimiento.getProveedorRequerimientoList().remove(proveedorRequerimientoListNewProveedorRequerimiento);
                        oldIdRequerimientoOfProveedorRequerimientoListNewProveedorRequerimiento = em.merge(oldIdRequerimientoOfProveedorRequerimientoListNewProveedorRequerimiento);
                    }
                }
            }
            for (RequerimentoInsumo requerimentoInsumoListOldRequerimentoInsumo : requerimentoInsumoListOld) {
                if (!requerimentoInsumoListNew.contains(requerimentoInsumoListOldRequerimentoInsumo)) {
                    requerimentoInsumoListOldRequerimentoInsumo.setIdRequerimiento(null);
                    requerimentoInsumoListOldRequerimentoInsumo = em.merge(requerimentoInsumoListOldRequerimentoInsumo);
                }
            }
            for (RequerimentoInsumo requerimentoInsumoListNewRequerimentoInsumo : requerimentoInsumoListNew) {
                if (!requerimentoInsumoListOld.contains(requerimentoInsumoListNewRequerimentoInsumo)) {
                    Requerimiento oldIdRequerimientoOfRequerimentoInsumoListNewRequerimentoInsumo = requerimentoInsumoListNewRequerimentoInsumo.getIdRequerimiento();
                    requerimentoInsumoListNewRequerimentoInsumo.setIdRequerimiento(requerimiento);
                    requerimentoInsumoListNewRequerimentoInsumo = em.merge(requerimentoInsumoListNewRequerimentoInsumo);
                    if (oldIdRequerimientoOfRequerimentoInsumoListNewRequerimentoInsumo != null && !oldIdRequerimientoOfRequerimentoInsumoListNewRequerimentoInsumo.equals(requerimiento)) {
                        oldIdRequerimientoOfRequerimentoInsumoListNewRequerimentoInsumo.getRequerimentoInsumoList().remove(requerimentoInsumoListNewRequerimentoInsumo);
                        oldIdRequerimientoOfRequerimentoInsumoListNewRequerimentoInsumo = em.merge(oldIdRequerimientoOfRequerimentoInsumoListNewRequerimentoInsumo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = requerimiento.getId();
                if (findRequerimiento(id) == null) {
                    throw new NonexistentEntityException("The requerimiento with id " + id + " no longer exists.");
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
            Requerimiento requerimiento;
            try {
                requerimiento = em.getReference(Requerimiento.class, id);
                requerimiento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The requerimiento with id " + id + " no longer exists.", enfe);
            }
            TipoRequerimiento idTipo = requerimiento.getIdTipo();
            if (idTipo != null) {
                idTipo.getRequerimientoList().remove(requerimiento);
                idTipo = em.merge(idTipo);
            }
            TipoAlcanceRequerimiento idAlcance = requerimiento.getIdAlcance();
            if (idAlcance != null) {
                idAlcance.getRequerimientoList().remove(requerimiento);
                idAlcance = em.merge(idAlcance);
            }
            List<ProveedorRequerimiento> proveedorRequerimientoList = requerimiento.getProveedorRequerimientoList();
            for (ProveedorRequerimiento proveedorRequerimientoListProveedorRequerimiento : proveedorRequerimientoList) {
                proveedorRequerimientoListProveedorRequerimiento.setIdRequerimiento(null);
                proveedorRequerimientoListProveedorRequerimiento = em.merge(proveedorRequerimientoListProveedorRequerimiento);
            }
            List<RequerimentoInsumo> requerimentoInsumoList = requerimiento.getRequerimentoInsumoList();
            for (RequerimentoInsumo requerimentoInsumoListRequerimentoInsumo : requerimentoInsumoList) {
                requerimentoInsumoListRequerimentoInsumo.setIdRequerimiento(null);
                requerimentoInsumoListRequerimentoInsumo = em.merge(requerimentoInsumoListRequerimentoInsumo);
            }
            em.remove(requerimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Requerimiento> findRequerimientoEntities() {
        return findRequerimientoEntities(true, -1, -1);
    }

    public List<Requerimiento> findRequerimientoEntities(int maxResults, int firstResult) {
        return findRequerimientoEntities(false, maxResults, firstResult);
    }

    private List<Requerimiento> findRequerimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Requerimiento.class));
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

    public Requerimiento findRequerimiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Requerimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getRequerimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Requerimiento> rt = cq.from(Requerimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
