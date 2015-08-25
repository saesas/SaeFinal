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
import com.sae.persistence.domain.TerceroRazonSocial;
import com.sae.persistence.domain.Cargo;
import com.sae.persistence.domain.TerceroCargo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TerceroCargoJpaController implements Serializable {

    public TerceroCargoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TerceroCargo terceroCargo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TerceroRazonSocial idTercero = terceroCargo.getIdTercero();
            if (idTercero != null) {
                idTercero = em.getReference(idTercero.getClass(), idTercero.getId());
                terceroCargo.setIdTercero(idTercero);
            }
            Cargo idCargo = terceroCargo.getIdCargo();
            if (idCargo != null) {
                idCargo = em.getReference(idCargo.getClass(), idCargo.getId());
                terceroCargo.setIdCargo(idCargo);
            }
            em.persist(terceroCargo);
            if (idTercero != null) {
                idTercero.getTerceroCargoList().add(terceroCargo);
                idTercero = em.merge(idTercero);
            }
            if (idCargo != null) {
                idCargo.getTerceroCargoList().add(terceroCargo);
                idCargo = em.merge(idCargo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTerceroCargo(terceroCargo.getId()) != null) {
                throw new PreexistingEntityException("TerceroCargo " + terceroCargo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TerceroCargo terceroCargo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TerceroCargo persistentTerceroCargo = em.find(TerceroCargo.class, terceroCargo.getId());
            TerceroRazonSocial idTerceroOld = persistentTerceroCargo.getIdTercero();
            TerceroRazonSocial idTerceroNew = terceroCargo.getIdTercero();
            Cargo idCargoOld = persistentTerceroCargo.getIdCargo();
            Cargo idCargoNew = terceroCargo.getIdCargo();
            if (idTerceroNew != null) {
                idTerceroNew = em.getReference(idTerceroNew.getClass(), idTerceroNew.getId());
                terceroCargo.setIdTercero(idTerceroNew);
            }
            if (idCargoNew != null) {
                idCargoNew = em.getReference(idCargoNew.getClass(), idCargoNew.getId());
                terceroCargo.setIdCargo(idCargoNew);
            }
            terceroCargo = em.merge(terceroCargo);
            if (idTerceroOld != null && !idTerceroOld.equals(idTerceroNew)) {
                idTerceroOld.getTerceroCargoList().remove(terceroCargo);
                idTerceroOld = em.merge(idTerceroOld);
            }
            if (idTerceroNew != null && !idTerceroNew.equals(idTerceroOld)) {
                idTerceroNew.getTerceroCargoList().add(terceroCargo);
                idTerceroNew = em.merge(idTerceroNew);
            }
            if (idCargoOld != null && !idCargoOld.equals(idCargoNew)) {
                idCargoOld.getTerceroCargoList().remove(terceroCargo);
                idCargoOld = em.merge(idCargoOld);
            }
            if (idCargoNew != null && !idCargoNew.equals(idCargoOld)) {
                idCargoNew.getTerceroCargoList().add(terceroCargo);
                idCargoNew = em.merge(idCargoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = terceroCargo.getId();
                if (findTerceroCargo(id) == null) {
                    throw new NonexistentEntityException("The terceroCargo with id " + id + " no longer exists.");
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
            TerceroCargo terceroCargo;
            try {
                terceroCargo = em.getReference(TerceroCargo.class, id);
                terceroCargo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The terceroCargo with id " + id + " no longer exists.", enfe);
            }
            TerceroRazonSocial idTercero = terceroCargo.getIdTercero();
            if (idTercero != null) {
                idTercero.getTerceroCargoList().remove(terceroCargo);
                idTercero = em.merge(idTercero);
            }
            Cargo idCargo = terceroCargo.getIdCargo();
            if (idCargo != null) {
                idCargo.getTerceroCargoList().remove(terceroCargo);
                idCargo = em.merge(idCargo);
            }
            em.remove(terceroCargo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TerceroCargo> findTerceroCargoEntities() {
        return findTerceroCargoEntities(true, -1, -1);
    }

    public List<TerceroCargo> findTerceroCargoEntities(int maxResults, int firstResult) {
        return findTerceroCargoEntities(false, maxResults, firstResult);
    }

    private List<TerceroCargo> findTerceroCargoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TerceroCargo.class));
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

    public TerceroCargo findTerceroCargo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TerceroCargo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTerceroCargoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TerceroCargo> rt = cq.from(TerceroCargo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
