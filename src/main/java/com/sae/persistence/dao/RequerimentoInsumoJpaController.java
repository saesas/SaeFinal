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
import com.sae.persistence.domain.ProveedorRequerimiento;
import com.sae.persistence.domain.RequerimentoInsumo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class RequerimentoInsumoJpaController implements Serializable {

    public RequerimentoInsumoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RequerimentoInsumo requerimentoInsumo) throws PreexistingEntityException, Exception {
        if (requerimentoInsumo.getProveedorRequerimientoList() == null) {
            requerimentoInsumo.setProveedorRequerimientoList(new ArrayList<ProveedorRequerimiento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Requerimiento idRequerimiento = requerimentoInsumo.getIdRequerimiento();
            if (idRequerimiento != null) {
                idRequerimiento = em.getReference(idRequerimiento.getClass(), idRequerimiento.getId());
                requerimentoInsumo.setIdRequerimiento(idRequerimiento);
            }
            List<ProveedorRequerimiento> attachedProveedorRequerimientoList = new ArrayList<ProveedorRequerimiento>();
            for (ProveedorRequerimiento proveedorRequerimientoListProveedorRequerimientoToAttach : requerimentoInsumo.getProveedorRequerimientoList()) {
                proveedorRequerimientoListProveedorRequerimientoToAttach = em.getReference(proveedorRequerimientoListProveedorRequerimientoToAttach.getClass(), proveedorRequerimientoListProveedorRequerimientoToAttach.getId());
                attachedProveedorRequerimientoList.add(proveedorRequerimientoListProveedorRequerimientoToAttach);
            }
            requerimentoInsumo.setProveedorRequerimientoList(attachedProveedorRequerimientoList);
            em.persist(requerimentoInsumo);
            if (idRequerimiento != null) {
                idRequerimiento.getRequerimentoInsumoList().add(requerimentoInsumo);
                idRequerimiento = em.merge(idRequerimiento);
            }
            for (ProveedorRequerimiento proveedorRequerimientoListProveedorRequerimiento : requerimentoInsumo.getProveedorRequerimientoList()) {
                RequerimentoInsumo oldIdInsumoRequerimientoOfProveedorRequerimientoListProveedorRequerimiento = proveedorRequerimientoListProveedorRequerimiento.getIdInsumoRequerimiento();
                proveedorRequerimientoListProveedorRequerimiento.setIdInsumoRequerimiento(requerimentoInsumo);
                proveedorRequerimientoListProveedorRequerimiento = em.merge(proveedorRequerimientoListProveedorRequerimiento);
                if (oldIdInsumoRequerimientoOfProveedorRequerimientoListProveedorRequerimiento != null) {
                    oldIdInsumoRequerimientoOfProveedorRequerimientoListProveedorRequerimiento.getProveedorRequerimientoList().remove(proveedorRequerimientoListProveedorRequerimiento);
                    oldIdInsumoRequerimientoOfProveedorRequerimientoListProveedorRequerimiento = em.merge(oldIdInsumoRequerimientoOfProveedorRequerimientoListProveedorRequerimiento);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRequerimentoInsumo(requerimentoInsumo.getId()) != null) {
                throw new PreexistingEntityException("RequerimentoInsumo " + requerimentoInsumo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RequerimentoInsumo requerimentoInsumo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RequerimentoInsumo persistentRequerimentoInsumo = em.find(RequerimentoInsumo.class, requerimentoInsumo.getId());
            Requerimiento idRequerimientoOld = persistentRequerimentoInsumo.getIdRequerimiento();
            Requerimiento idRequerimientoNew = requerimentoInsumo.getIdRequerimiento();
            List<ProveedorRequerimiento> proveedorRequerimientoListOld = persistentRequerimentoInsumo.getProveedorRequerimientoList();
            List<ProveedorRequerimiento> proveedorRequerimientoListNew = requerimentoInsumo.getProveedorRequerimientoList();
            if (idRequerimientoNew != null) {
                idRequerimientoNew = em.getReference(idRequerimientoNew.getClass(), idRequerimientoNew.getId());
                requerimentoInsumo.setIdRequerimiento(idRequerimientoNew);
            }
            List<ProveedorRequerimiento> attachedProveedorRequerimientoListNew = new ArrayList<ProveedorRequerimiento>();
            for (ProveedorRequerimiento proveedorRequerimientoListNewProveedorRequerimientoToAttach : proveedorRequerimientoListNew) {
                proveedorRequerimientoListNewProveedorRequerimientoToAttach = em.getReference(proveedorRequerimientoListNewProveedorRequerimientoToAttach.getClass(), proveedorRequerimientoListNewProveedorRequerimientoToAttach.getId());
                attachedProveedorRequerimientoListNew.add(proveedorRequerimientoListNewProveedorRequerimientoToAttach);
            }
            proveedorRequerimientoListNew = attachedProveedorRequerimientoListNew;
            requerimentoInsumo.setProveedorRequerimientoList(proveedorRequerimientoListNew);
            requerimentoInsumo = em.merge(requerimentoInsumo);
            if (idRequerimientoOld != null && !idRequerimientoOld.equals(idRequerimientoNew)) {
                idRequerimientoOld.getRequerimentoInsumoList().remove(requerimentoInsumo);
                idRequerimientoOld = em.merge(idRequerimientoOld);
            }
            if (idRequerimientoNew != null && !idRequerimientoNew.equals(idRequerimientoOld)) {
                idRequerimientoNew.getRequerimentoInsumoList().add(requerimentoInsumo);
                idRequerimientoNew = em.merge(idRequerimientoNew);
            }
            for (ProveedorRequerimiento proveedorRequerimientoListOldProveedorRequerimiento : proveedorRequerimientoListOld) {
                if (!proveedorRequerimientoListNew.contains(proveedorRequerimientoListOldProveedorRequerimiento)) {
                    proveedorRequerimientoListOldProveedorRequerimiento.setIdInsumoRequerimiento(null);
                    proveedorRequerimientoListOldProveedorRequerimiento = em.merge(proveedorRequerimientoListOldProveedorRequerimiento);
                }
            }
            for (ProveedorRequerimiento proveedorRequerimientoListNewProveedorRequerimiento : proveedorRequerimientoListNew) {
                if (!proveedorRequerimientoListOld.contains(proveedorRequerimientoListNewProveedorRequerimiento)) {
                    RequerimentoInsumo oldIdInsumoRequerimientoOfProveedorRequerimientoListNewProveedorRequerimiento = proveedorRequerimientoListNewProveedorRequerimiento.getIdInsumoRequerimiento();
                    proveedorRequerimientoListNewProveedorRequerimiento.setIdInsumoRequerimiento(requerimentoInsumo);
                    proveedorRequerimientoListNewProveedorRequerimiento = em.merge(proveedorRequerimientoListNewProveedorRequerimiento);
                    if (oldIdInsumoRequerimientoOfProveedorRequerimientoListNewProveedorRequerimiento != null && !oldIdInsumoRequerimientoOfProveedorRequerimientoListNewProveedorRequerimiento.equals(requerimentoInsumo)) {
                        oldIdInsumoRequerimientoOfProveedorRequerimientoListNewProveedorRequerimiento.getProveedorRequerimientoList().remove(proveedorRequerimientoListNewProveedorRequerimiento);
                        oldIdInsumoRequerimientoOfProveedorRequerimientoListNewProveedorRequerimiento = em.merge(oldIdInsumoRequerimientoOfProveedorRequerimientoListNewProveedorRequerimiento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = requerimentoInsumo.getId();
                if (findRequerimentoInsumo(id) == null) {
                    throw new NonexistentEntityException("The requerimentoInsumo with id " + id + " no longer exists.");
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
            RequerimentoInsumo requerimentoInsumo;
            try {
                requerimentoInsumo = em.getReference(RequerimentoInsumo.class, id);
                requerimentoInsumo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The requerimentoInsumo with id " + id + " no longer exists.", enfe);
            }
            Requerimiento idRequerimiento = requerimentoInsumo.getIdRequerimiento();
            if (idRequerimiento != null) {
                idRequerimiento.getRequerimentoInsumoList().remove(requerimentoInsumo);
                idRequerimiento = em.merge(idRequerimiento);
            }
            List<ProveedorRequerimiento> proveedorRequerimientoList = requerimentoInsumo.getProveedorRequerimientoList();
            for (ProveedorRequerimiento proveedorRequerimientoListProveedorRequerimiento : proveedorRequerimientoList) {
                proveedorRequerimientoListProveedorRequerimiento.setIdInsumoRequerimiento(null);
                proveedorRequerimientoListProveedorRequerimiento = em.merge(proveedorRequerimientoListProveedorRequerimiento);
            }
            em.remove(requerimentoInsumo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RequerimentoInsumo> findRequerimentoInsumoEntities() {
        return findRequerimentoInsumoEntities(true, -1, -1);
    }

    public List<RequerimentoInsumo> findRequerimentoInsumoEntities(int maxResults, int firstResult) {
        return findRequerimentoInsumoEntities(false, maxResults, firstResult);
    }

    private List<RequerimentoInsumo> findRequerimentoInsumoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RequerimentoInsumo.class));
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

    public RequerimentoInsumo findRequerimentoInsumo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RequerimentoInsumo.class, id);
        } finally {
            em.close();
        }
    }

    public int getRequerimentoInsumoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RequerimentoInsumo> rt = cq.from(RequerimentoInsumo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
