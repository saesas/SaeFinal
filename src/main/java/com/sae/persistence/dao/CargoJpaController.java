/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.Cargo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.TerceroCargo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class CargoJpaController implements Serializable {

    public CargoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cargo cargo) throws PreexistingEntityException, Exception {
        if (cargo.getTerceroCargoList() == null) {
            cargo.setTerceroCargoList(new ArrayList<TerceroCargo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<TerceroCargo> attachedTerceroCargoList = new ArrayList<TerceroCargo>();
            for (TerceroCargo terceroCargoListTerceroCargoToAttach : cargo.getTerceroCargoList()) {
                terceroCargoListTerceroCargoToAttach = em.getReference(terceroCargoListTerceroCargoToAttach.getClass(), terceroCargoListTerceroCargoToAttach.getId());
                attachedTerceroCargoList.add(terceroCargoListTerceroCargoToAttach);
            }
            cargo.setTerceroCargoList(attachedTerceroCargoList);
            em.persist(cargo);
            for (TerceroCargo terceroCargoListTerceroCargo : cargo.getTerceroCargoList()) {
                Cargo oldIdCargoOfTerceroCargoListTerceroCargo = terceroCargoListTerceroCargo.getIdCargo();
                terceroCargoListTerceroCargo.setIdCargo(cargo);
                terceroCargoListTerceroCargo = em.merge(terceroCargoListTerceroCargo);
                if (oldIdCargoOfTerceroCargoListTerceroCargo != null) {
                    oldIdCargoOfTerceroCargoListTerceroCargo.getTerceroCargoList().remove(terceroCargoListTerceroCargo);
                    oldIdCargoOfTerceroCargoListTerceroCargo = em.merge(oldIdCargoOfTerceroCargoListTerceroCargo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCargo(cargo.getId()) != null) {
                throw new PreexistingEntityException("Cargo " + cargo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cargo cargo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargo persistentCargo = em.find(Cargo.class, cargo.getId());
            List<TerceroCargo> terceroCargoListOld = persistentCargo.getTerceroCargoList();
            List<TerceroCargo> terceroCargoListNew = cargo.getTerceroCargoList();
            List<TerceroCargo> attachedTerceroCargoListNew = new ArrayList<TerceroCargo>();
            for (TerceroCargo terceroCargoListNewTerceroCargoToAttach : terceroCargoListNew) {
                terceroCargoListNewTerceroCargoToAttach = em.getReference(terceroCargoListNewTerceroCargoToAttach.getClass(), terceroCargoListNewTerceroCargoToAttach.getId());
                attachedTerceroCargoListNew.add(terceroCargoListNewTerceroCargoToAttach);
            }
            terceroCargoListNew = attachedTerceroCargoListNew;
            cargo.setTerceroCargoList(terceroCargoListNew);
            cargo = em.merge(cargo);
            for (TerceroCargo terceroCargoListOldTerceroCargo : terceroCargoListOld) {
                if (!terceroCargoListNew.contains(terceroCargoListOldTerceroCargo)) {
                    terceroCargoListOldTerceroCargo.setIdCargo(null);
                    terceroCargoListOldTerceroCargo = em.merge(terceroCargoListOldTerceroCargo);
                }
            }
            for (TerceroCargo terceroCargoListNewTerceroCargo : terceroCargoListNew) {
                if (!terceroCargoListOld.contains(terceroCargoListNewTerceroCargo)) {
                    Cargo oldIdCargoOfTerceroCargoListNewTerceroCargo = terceroCargoListNewTerceroCargo.getIdCargo();
                    terceroCargoListNewTerceroCargo.setIdCargo(cargo);
                    terceroCargoListNewTerceroCargo = em.merge(terceroCargoListNewTerceroCargo);
                    if (oldIdCargoOfTerceroCargoListNewTerceroCargo != null && !oldIdCargoOfTerceroCargoListNewTerceroCargo.equals(cargo)) {
                        oldIdCargoOfTerceroCargoListNewTerceroCargo.getTerceroCargoList().remove(terceroCargoListNewTerceroCargo);
                        oldIdCargoOfTerceroCargoListNewTerceroCargo = em.merge(oldIdCargoOfTerceroCargoListNewTerceroCargo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cargo.getId();
                if (findCargo(id) == null) {
                    throw new NonexistentEntityException("The cargo with id " + id + " no longer exists.");
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
            Cargo cargo;
            try {
                cargo = em.getReference(Cargo.class, id);
                cargo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cargo with id " + id + " no longer exists.", enfe);
            }
            List<TerceroCargo> terceroCargoList = cargo.getTerceroCargoList();
            for (TerceroCargo terceroCargoListTerceroCargo : terceroCargoList) {
                terceroCargoListTerceroCargo.setIdCargo(null);
                terceroCargoListTerceroCargo = em.merge(terceroCargoListTerceroCargo);
            }
            em.remove(cargo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cargo> findCargoEntities() {
        return findCargoEntities(true, -1, -1);
    }

    public List<Cargo> findCargoEntities(int maxResults, int firstResult) {
        return findCargoEntities(false, maxResults, firstResult);
    }

    private List<Cargo> findCargoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cargo.class));
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

    public Cargo findCargo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cargo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCargoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cargo> rt = cq.from(Cargo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
