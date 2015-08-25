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
import com.sae.persistence.domain.FacturaVenta;
import com.sae.persistence.domain.TipoFacturaVenta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoFacturaVentaJpaController implements Serializable {

    public TipoFacturaVentaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoFacturaVenta tipoFacturaVenta) throws PreexistingEntityException, Exception {
        if (tipoFacturaVenta.getFacturaVentaList() == null) {
            tipoFacturaVenta.setFacturaVentaList(new ArrayList<FacturaVenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<FacturaVenta> attachedFacturaVentaList = new ArrayList<FacturaVenta>();
            for (FacturaVenta facturaVentaListFacturaVentaToAttach : tipoFacturaVenta.getFacturaVentaList()) {
                facturaVentaListFacturaVentaToAttach = em.getReference(facturaVentaListFacturaVentaToAttach.getClass(), facturaVentaListFacturaVentaToAttach.getId());
                attachedFacturaVentaList.add(facturaVentaListFacturaVentaToAttach);
            }
            tipoFacturaVenta.setFacturaVentaList(attachedFacturaVentaList);
            em.persist(tipoFacturaVenta);
            for (FacturaVenta facturaVentaListFacturaVenta : tipoFacturaVenta.getFacturaVentaList()) {
                TipoFacturaVenta oldIdTipoOfFacturaVentaListFacturaVenta = facturaVentaListFacturaVenta.getIdTipo();
                facturaVentaListFacturaVenta.setIdTipo(tipoFacturaVenta);
                facturaVentaListFacturaVenta = em.merge(facturaVentaListFacturaVenta);
                if (oldIdTipoOfFacturaVentaListFacturaVenta != null) {
                    oldIdTipoOfFacturaVentaListFacturaVenta.getFacturaVentaList().remove(facturaVentaListFacturaVenta);
                    oldIdTipoOfFacturaVentaListFacturaVenta = em.merge(oldIdTipoOfFacturaVentaListFacturaVenta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoFacturaVenta(tipoFacturaVenta.getId()) != null) {
                throw new PreexistingEntityException("TipoFacturaVenta " + tipoFacturaVenta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoFacturaVenta tipoFacturaVenta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoFacturaVenta persistentTipoFacturaVenta = em.find(TipoFacturaVenta.class, tipoFacturaVenta.getId());
            List<FacturaVenta> facturaVentaListOld = persistentTipoFacturaVenta.getFacturaVentaList();
            List<FacturaVenta> facturaVentaListNew = tipoFacturaVenta.getFacturaVentaList();
            List<FacturaVenta> attachedFacturaVentaListNew = new ArrayList<FacturaVenta>();
            for (FacturaVenta facturaVentaListNewFacturaVentaToAttach : facturaVentaListNew) {
                facturaVentaListNewFacturaVentaToAttach = em.getReference(facturaVentaListNewFacturaVentaToAttach.getClass(), facturaVentaListNewFacturaVentaToAttach.getId());
                attachedFacturaVentaListNew.add(facturaVentaListNewFacturaVentaToAttach);
            }
            facturaVentaListNew = attachedFacturaVentaListNew;
            tipoFacturaVenta.setFacturaVentaList(facturaVentaListNew);
            tipoFacturaVenta = em.merge(tipoFacturaVenta);
            for (FacturaVenta facturaVentaListOldFacturaVenta : facturaVentaListOld) {
                if (!facturaVentaListNew.contains(facturaVentaListOldFacturaVenta)) {
                    facturaVentaListOldFacturaVenta.setIdTipo(null);
                    facturaVentaListOldFacturaVenta = em.merge(facturaVentaListOldFacturaVenta);
                }
            }
            for (FacturaVenta facturaVentaListNewFacturaVenta : facturaVentaListNew) {
                if (!facturaVentaListOld.contains(facturaVentaListNewFacturaVenta)) {
                    TipoFacturaVenta oldIdTipoOfFacturaVentaListNewFacturaVenta = facturaVentaListNewFacturaVenta.getIdTipo();
                    facturaVentaListNewFacturaVenta.setIdTipo(tipoFacturaVenta);
                    facturaVentaListNewFacturaVenta = em.merge(facturaVentaListNewFacturaVenta);
                    if (oldIdTipoOfFacturaVentaListNewFacturaVenta != null && !oldIdTipoOfFacturaVentaListNewFacturaVenta.equals(tipoFacturaVenta)) {
                        oldIdTipoOfFacturaVentaListNewFacturaVenta.getFacturaVentaList().remove(facturaVentaListNewFacturaVenta);
                        oldIdTipoOfFacturaVentaListNewFacturaVenta = em.merge(oldIdTipoOfFacturaVentaListNewFacturaVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoFacturaVenta.getId();
                if (findTipoFacturaVenta(id) == null) {
                    throw new NonexistentEntityException("The tipoFacturaVenta with id " + id + " no longer exists.");
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
            TipoFacturaVenta tipoFacturaVenta;
            try {
                tipoFacturaVenta = em.getReference(TipoFacturaVenta.class, id);
                tipoFacturaVenta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoFacturaVenta with id " + id + " no longer exists.", enfe);
            }
            List<FacturaVenta> facturaVentaList = tipoFacturaVenta.getFacturaVentaList();
            for (FacturaVenta facturaVentaListFacturaVenta : facturaVentaList) {
                facturaVentaListFacturaVenta.setIdTipo(null);
                facturaVentaListFacturaVenta = em.merge(facturaVentaListFacturaVenta);
            }
            em.remove(tipoFacturaVenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoFacturaVenta> findTipoFacturaVentaEntities() {
        return findTipoFacturaVentaEntities(true, -1, -1);
    }

    public List<TipoFacturaVenta> findTipoFacturaVentaEntities(int maxResults, int firstResult) {
        return findTipoFacturaVentaEntities(false, maxResults, firstResult);
    }

    private List<TipoFacturaVenta> findTipoFacturaVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoFacturaVenta.class));
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

    public TipoFacturaVenta findTipoFacturaVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoFacturaVenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoFacturaVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoFacturaVenta> rt = cq.from(TipoFacturaVenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
