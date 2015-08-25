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
import com.sae.persistence.domain.Tercero;
import com.sae.persistence.domain.RazonSocial;
import com.sae.persistence.domain.TerceroCargo;
import com.sae.persistence.domain.TerceroRazonSocial;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TerceroRazonSocialJpaController implements Serializable {

    public TerceroRazonSocialJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TerceroRazonSocial terceroRazonSocial) throws PreexistingEntityException, Exception {
        if (terceroRazonSocial.getTerceroCargoList() == null) {
            terceroRazonSocial.setTerceroCargoList(new ArrayList<TerceroCargo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tercero idTercero = terceroRazonSocial.getIdTercero();
            if (idTercero != null) {
                idTercero = em.getReference(idTercero.getClass(), idTercero.getId());
                terceroRazonSocial.setIdTercero(idTercero);
            }
            RazonSocial idRazonSocial = terceroRazonSocial.getIdRazonSocial();
            if (idRazonSocial != null) {
                idRazonSocial = em.getReference(idRazonSocial.getClass(), idRazonSocial.getId());
                terceroRazonSocial.setIdRazonSocial(idRazonSocial);
            }
            List<TerceroCargo> attachedTerceroCargoList = new ArrayList<TerceroCargo>();
            for (TerceroCargo terceroCargoListTerceroCargoToAttach : terceroRazonSocial.getTerceroCargoList()) {
                terceroCargoListTerceroCargoToAttach = em.getReference(terceroCargoListTerceroCargoToAttach.getClass(), terceroCargoListTerceroCargoToAttach.getId());
                attachedTerceroCargoList.add(terceroCargoListTerceroCargoToAttach);
            }
            terceroRazonSocial.setTerceroCargoList(attachedTerceroCargoList);
            em.persist(terceroRazonSocial);
            if (idTercero != null) {
                idTercero.getTerceroRazonSocialList().add(terceroRazonSocial);
                idTercero = em.merge(idTercero);
            }
            if (idRazonSocial != null) {
                idRazonSocial.getTerceroRazonSocialList().add(terceroRazonSocial);
                idRazonSocial = em.merge(idRazonSocial);
            }
            for (TerceroCargo terceroCargoListTerceroCargo : terceroRazonSocial.getTerceroCargoList()) {
                TerceroRazonSocial oldIdTerceroOfTerceroCargoListTerceroCargo = terceroCargoListTerceroCargo.getIdTercero();
                terceroCargoListTerceroCargo.setIdTercero(terceroRazonSocial);
                terceroCargoListTerceroCargo = em.merge(terceroCargoListTerceroCargo);
                if (oldIdTerceroOfTerceroCargoListTerceroCargo != null) {
                    oldIdTerceroOfTerceroCargoListTerceroCargo.getTerceroCargoList().remove(terceroCargoListTerceroCargo);
                    oldIdTerceroOfTerceroCargoListTerceroCargo = em.merge(oldIdTerceroOfTerceroCargoListTerceroCargo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTerceroRazonSocial(terceroRazonSocial.getId()) != null) {
                throw new PreexistingEntityException("TerceroRazonSocial " + terceroRazonSocial + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TerceroRazonSocial terceroRazonSocial) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TerceroRazonSocial persistentTerceroRazonSocial = em.find(TerceroRazonSocial.class, terceroRazonSocial.getId());
            Tercero idTerceroOld = persistentTerceroRazonSocial.getIdTercero();
            Tercero idTerceroNew = terceroRazonSocial.getIdTercero();
            RazonSocial idRazonSocialOld = persistentTerceroRazonSocial.getIdRazonSocial();
            RazonSocial idRazonSocialNew = terceroRazonSocial.getIdRazonSocial();
            List<TerceroCargo> terceroCargoListOld = persistentTerceroRazonSocial.getTerceroCargoList();
            List<TerceroCargo> terceroCargoListNew = terceroRazonSocial.getTerceroCargoList();
            if (idTerceroNew != null) {
                idTerceroNew = em.getReference(idTerceroNew.getClass(), idTerceroNew.getId());
                terceroRazonSocial.setIdTercero(idTerceroNew);
            }
            if (idRazonSocialNew != null) {
                idRazonSocialNew = em.getReference(idRazonSocialNew.getClass(), idRazonSocialNew.getId());
                terceroRazonSocial.setIdRazonSocial(idRazonSocialNew);
            }
            List<TerceroCargo> attachedTerceroCargoListNew = new ArrayList<TerceroCargo>();
            for (TerceroCargo terceroCargoListNewTerceroCargoToAttach : terceroCargoListNew) {
                terceroCargoListNewTerceroCargoToAttach = em.getReference(terceroCargoListNewTerceroCargoToAttach.getClass(), terceroCargoListNewTerceroCargoToAttach.getId());
                attachedTerceroCargoListNew.add(terceroCargoListNewTerceroCargoToAttach);
            }
            terceroCargoListNew = attachedTerceroCargoListNew;
            terceroRazonSocial.setTerceroCargoList(terceroCargoListNew);
            terceroRazonSocial = em.merge(terceroRazonSocial);
            if (idTerceroOld != null && !idTerceroOld.equals(idTerceroNew)) {
                idTerceroOld.getTerceroRazonSocialList().remove(terceroRazonSocial);
                idTerceroOld = em.merge(idTerceroOld);
            }
            if (idTerceroNew != null && !idTerceroNew.equals(idTerceroOld)) {
                idTerceroNew.getTerceroRazonSocialList().add(terceroRazonSocial);
                idTerceroNew = em.merge(idTerceroNew);
            }
            if (idRazonSocialOld != null && !idRazonSocialOld.equals(idRazonSocialNew)) {
                idRazonSocialOld.getTerceroRazonSocialList().remove(terceroRazonSocial);
                idRazonSocialOld = em.merge(idRazonSocialOld);
            }
            if (idRazonSocialNew != null && !idRazonSocialNew.equals(idRazonSocialOld)) {
                idRazonSocialNew.getTerceroRazonSocialList().add(terceroRazonSocial);
                idRazonSocialNew = em.merge(idRazonSocialNew);
            }
            for (TerceroCargo terceroCargoListOldTerceroCargo : terceroCargoListOld) {
                if (!terceroCargoListNew.contains(terceroCargoListOldTerceroCargo)) {
                    terceroCargoListOldTerceroCargo.setIdTercero(null);
                    terceroCargoListOldTerceroCargo = em.merge(terceroCargoListOldTerceroCargo);
                }
            }
            for (TerceroCargo terceroCargoListNewTerceroCargo : terceroCargoListNew) {
                if (!terceroCargoListOld.contains(terceroCargoListNewTerceroCargo)) {
                    TerceroRazonSocial oldIdTerceroOfTerceroCargoListNewTerceroCargo = terceroCargoListNewTerceroCargo.getIdTercero();
                    terceroCargoListNewTerceroCargo.setIdTercero(terceroRazonSocial);
                    terceroCargoListNewTerceroCargo = em.merge(terceroCargoListNewTerceroCargo);
                    if (oldIdTerceroOfTerceroCargoListNewTerceroCargo != null && !oldIdTerceroOfTerceroCargoListNewTerceroCargo.equals(terceroRazonSocial)) {
                        oldIdTerceroOfTerceroCargoListNewTerceroCargo.getTerceroCargoList().remove(terceroCargoListNewTerceroCargo);
                        oldIdTerceroOfTerceroCargoListNewTerceroCargo = em.merge(oldIdTerceroOfTerceroCargoListNewTerceroCargo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = terceroRazonSocial.getId();
                if (findTerceroRazonSocial(id) == null) {
                    throw new NonexistentEntityException("The terceroRazonSocial with id " + id + " no longer exists.");
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
            TerceroRazonSocial terceroRazonSocial;
            try {
                terceroRazonSocial = em.getReference(TerceroRazonSocial.class, id);
                terceroRazonSocial.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The terceroRazonSocial with id " + id + " no longer exists.", enfe);
            }
            Tercero idTercero = terceroRazonSocial.getIdTercero();
            if (idTercero != null) {
                idTercero.getTerceroRazonSocialList().remove(terceroRazonSocial);
                idTercero = em.merge(idTercero);
            }
            RazonSocial idRazonSocial = terceroRazonSocial.getIdRazonSocial();
            if (idRazonSocial != null) {
                idRazonSocial.getTerceroRazonSocialList().remove(terceroRazonSocial);
                idRazonSocial = em.merge(idRazonSocial);
            }
            List<TerceroCargo> terceroCargoList = terceroRazonSocial.getTerceroCargoList();
            for (TerceroCargo terceroCargoListTerceroCargo : terceroCargoList) {
                terceroCargoListTerceroCargo.setIdTercero(null);
                terceroCargoListTerceroCargo = em.merge(terceroCargoListTerceroCargo);
            }
            em.remove(terceroRazonSocial);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TerceroRazonSocial> findTerceroRazonSocialEntities() {
        return findTerceroRazonSocialEntities(true, -1, -1);
    }

    public List<TerceroRazonSocial> findTerceroRazonSocialEntities(int maxResults, int firstResult) {
        return findTerceroRazonSocialEntities(false, maxResults, firstResult);
    }

    private List<TerceroRazonSocial> findTerceroRazonSocialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TerceroRazonSocial.class));
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

    public TerceroRazonSocial findTerceroRazonSocial(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TerceroRazonSocial.class, id);
        } finally {
            em.close();
        }
    }

    public int getTerceroRazonSocialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TerceroRazonSocial> rt = cq.from(TerceroRazonSocial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
