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
import com.sae.persistence.domain.SucursalRazonSocialTercero;
import com.sae.persistence.domain.TipoSucursal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoSucursalJpaController implements Serializable {

    public TipoSucursalJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoSucursal tipoSucursal) throws PreexistingEntityException, Exception {
        if (tipoSucursal.getSucursalRazonSocialTerceroList() == null) {
            tipoSucursal.setSucursalRazonSocialTerceroList(new ArrayList<SucursalRazonSocialTercero>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SucursalRazonSocialTercero> attachedSucursalRazonSocialTerceroList = new ArrayList<SucursalRazonSocialTercero>();
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListSucursalRazonSocialTerceroToAttach : tipoSucursal.getSucursalRazonSocialTerceroList()) {
                sucursalRazonSocialTerceroListSucursalRazonSocialTerceroToAttach = em.getReference(sucursalRazonSocialTerceroListSucursalRazonSocialTerceroToAttach.getClass(), sucursalRazonSocialTerceroListSucursalRazonSocialTerceroToAttach.getId());
                attachedSucursalRazonSocialTerceroList.add(sucursalRazonSocialTerceroListSucursalRazonSocialTerceroToAttach);
            }
            tipoSucursal.setSucursalRazonSocialTerceroList(attachedSucursalRazonSocialTerceroList);
            em.persist(tipoSucursal);
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListSucursalRazonSocialTercero : tipoSucursal.getSucursalRazonSocialTerceroList()) {
                TipoSucursal oldIdTipoSucursalOfSucursalRazonSocialTerceroListSucursalRazonSocialTercero = sucursalRazonSocialTerceroListSucursalRazonSocialTercero.getIdTipoSucursal();
                sucursalRazonSocialTerceroListSucursalRazonSocialTercero.setIdTipoSucursal(tipoSucursal);
                sucursalRazonSocialTerceroListSucursalRazonSocialTercero = em.merge(sucursalRazonSocialTerceroListSucursalRazonSocialTercero);
                if (oldIdTipoSucursalOfSucursalRazonSocialTerceroListSucursalRazonSocialTercero != null) {
                    oldIdTipoSucursalOfSucursalRazonSocialTerceroListSucursalRazonSocialTercero.getSucursalRazonSocialTerceroList().remove(sucursalRazonSocialTerceroListSucursalRazonSocialTercero);
                    oldIdTipoSucursalOfSucursalRazonSocialTerceroListSucursalRazonSocialTercero = em.merge(oldIdTipoSucursalOfSucursalRazonSocialTerceroListSucursalRazonSocialTercero);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoSucursal(tipoSucursal.getId()) != null) {
                throw new PreexistingEntityException("TipoSucursal " + tipoSucursal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoSucursal tipoSucursal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoSucursal persistentTipoSucursal = em.find(TipoSucursal.class, tipoSucursal.getId());
            List<SucursalRazonSocialTercero> sucursalRazonSocialTerceroListOld = persistentTipoSucursal.getSucursalRazonSocialTerceroList();
            List<SucursalRazonSocialTercero> sucursalRazonSocialTerceroListNew = tipoSucursal.getSucursalRazonSocialTerceroList();
            List<SucursalRazonSocialTercero> attachedSucursalRazonSocialTerceroListNew = new ArrayList<SucursalRazonSocialTercero>();
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListNewSucursalRazonSocialTerceroToAttach : sucursalRazonSocialTerceroListNew) {
                sucursalRazonSocialTerceroListNewSucursalRazonSocialTerceroToAttach = em.getReference(sucursalRazonSocialTerceroListNewSucursalRazonSocialTerceroToAttach.getClass(), sucursalRazonSocialTerceroListNewSucursalRazonSocialTerceroToAttach.getId());
                attachedSucursalRazonSocialTerceroListNew.add(sucursalRazonSocialTerceroListNewSucursalRazonSocialTerceroToAttach);
            }
            sucursalRazonSocialTerceroListNew = attachedSucursalRazonSocialTerceroListNew;
            tipoSucursal.setSucursalRazonSocialTerceroList(sucursalRazonSocialTerceroListNew);
            tipoSucursal = em.merge(tipoSucursal);
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListOldSucursalRazonSocialTercero : sucursalRazonSocialTerceroListOld) {
                if (!sucursalRazonSocialTerceroListNew.contains(sucursalRazonSocialTerceroListOldSucursalRazonSocialTercero)) {
                    sucursalRazonSocialTerceroListOldSucursalRazonSocialTercero.setIdTipoSucursal(null);
                    sucursalRazonSocialTerceroListOldSucursalRazonSocialTercero = em.merge(sucursalRazonSocialTerceroListOldSucursalRazonSocialTercero);
                }
            }
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero : sucursalRazonSocialTerceroListNew) {
                if (!sucursalRazonSocialTerceroListOld.contains(sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero)) {
                    TipoSucursal oldIdTipoSucursalOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero = sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero.getIdTipoSucursal();
                    sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero.setIdTipoSucursal(tipoSucursal);
                    sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero = em.merge(sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero);
                    if (oldIdTipoSucursalOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero != null && !oldIdTipoSucursalOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero.equals(tipoSucursal)) {
                        oldIdTipoSucursalOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero.getSucursalRazonSocialTerceroList().remove(sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero);
                        oldIdTipoSucursalOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero = em.merge(oldIdTipoSucursalOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoSucursal.getId();
                if (findTipoSucursal(id) == null) {
                    throw new NonexistentEntityException("The tipoSucursal with id " + id + " no longer exists.");
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
            TipoSucursal tipoSucursal;
            try {
                tipoSucursal = em.getReference(TipoSucursal.class, id);
                tipoSucursal.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoSucursal with id " + id + " no longer exists.", enfe);
            }
            List<SucursalRazonSocialTercero> sucursalRazonSocialTerceroList = tipoSucursal.getSucursalRazonSocialTerceroList();
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListSucursalRazonSocialTercero : sucursalRazonSocialTerceroList) {
                sucursalRazonSocialTerceroListSucursalRazonSocialTercero.setIdTipoSucursal(null);
                sucursalRazonSocialTerceroListSucursalRazonSocialTercero = em.merge(sucursalRazonSocialTerceroListSucursalRazonSocialTercero);
            }
            em.remove(tipoSucursal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoSucursal> findTipoSucursalEntities() {
        return findTipoSucursalEntities(true, -1, -1);
    }

    public List<TipoSucursal> findTipoSucursalEntities(int maxResults, int firstResult) {
        return findTipoSucursalEntities(false, maxResults, firstResult);
    }

    private List<TipoSucursal> findTipoSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoSucursal.class));
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

    public TipoSucursal findTipoSucursal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoSucursal.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoSucursalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoSucursal> rt = cq.from(TipoSucursal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
