/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.EstadoCivil;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Tercero;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class EstadoCivilJpaController implements Serializable {

    public EstadoCivilJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoCivil estadoCivil) throws PreexistingEntityException, Exception {
        if (estadoCivil.getTerceroList() == null) {
            estadoCivil.setTerceroList(new ArrayList<Tercero>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Tercero> attachedTerceroList = new ArrayList<Tercero>();
            for (Tercero terceroListTerceroToAttach : estadoCivil.getTerceroList()) {
                terceroListTerceroToAttach = em.getReference(terceroListTerceroToAttach.getClass(), terceroListTerceroToAttach.getId());
                attachedTerceroList.add(terceroListTerceroToAttach);
            }
            estadoCivil.setTerceroList(attachedTerceroList);
            em.persist(estadoCivil);
            for (Tercero terceroListTercero : estadoCivil.getTerceroList()) {
                EstadoCivil oldEstadoCivilOfTerceroListTercero = terceroListTercero.getEstadoCivil();
                terceroListTercero.setEstadoCivil(estadoCivil);
                terceroListTercero = em.merge(terceroListTercero);
                if (oldEstadoCivilOfTerceroListTercero != null) {
                    oldEstadoCivilOfTerceroListTercero.getTerceroList().remove(terceroListTercero);
                    oldEstadoCivilOfTerceroListTercero = em.merge(oldEstadoCivilOfTerceroListTercero);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadoCivil(estadoCivil.getId()) != null) {
                throw new PreexistingEntityException("EstadoCivil " + estadoCivil + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoCivil estadoCivil) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoCivil persistentEstadoCivil = em.find(EstadoCivil.class, estadoCivil.getId());
            List<Tercero> terceroListOld = persistentEstadoCivil.getTerceroList();
            List<Tercero> terceroListNew = estadoCivil.getTerceroList();
            List<Tercero> attachedTerceroListNew = new ArrayList<Tercero>();
            for (Tercero terceroListNewTerceroToAttach : terceroListNew) {
                terceroListNewTerceroToAttach = em.getReference(terceroListNewTerceroToAttach.getClass(), terceroListNewTerceroToAttach.getId());
                attachedTerceroListNew.add(terceroListNewTerceroToAttach);
            }
            terceroListNew = attachedTerceroListNew;
            estadoCivil.setTerceroList(terceroListNew);
            estadoCivil = em.merge(estadoCivil);
            for (Tercero terceroListOldTercero : terceroListOld) {
                if (!terceroListNew.contains(terceroListOldTercero)) {
                    terceroListOldTercero.setEstadoCivil(null);
                    terceroListOldTercero = em.merge(terceroListOldTercero);
                }
            }
            for (Tercero terceroListNewTercero : terceroListNew) {
                if (!terceroListOld.contains(terceroListNewTercero)) {
                    EstadoCivil oldEstadoCivilOfTerceroListNewTercero = terceroListNewTercero.getEstadoCivil();
                    terceroListNewTercero.setEstadoCivil(estadoCivil);
                    terceroListNewTercero = em.merge(terceroListNewTercero);
                    if (oldEstadoCivilOfTerceroListNewTercero != null && !oldEstadoCivilOfTerceroListNewTercero.equals(estadoCivil)) {
                        oldEstadoCivilOfTerceroListNewTercero.getTerceroList().remove(terceroListNewTercero);
                        oldEstadoCivilOfTerceroListNewTercero = em.merge(oldEstadoCivilOfTerceroListNewTercero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estadoCivil.getId();
                if (findEstadoCivil(id) == null) {
                    throw new NonexistentEntityException("The estadoCivil with id " + id + " no longer exists.");
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
            EstadoCivil estadoCivil;
            try {
                estadoCivil = em.getReference(EstadoCivil.class, id);
                estadoCivil.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoCivil with id " + id + " no longer exists.", enfe);
            }
            List<Tercero> terceroList = estadoCivil.getTerceroList();
            for (Tercero terceroListTercero : terceroList) {
                terceroListTercero.setEstadoCivil(null);
                terceroListTercero = em.merge(terceroListTercero);
            }
            em.remove(estadoCivil);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoCivil> findEstadoCivilEntities() {
        return findEstadoCivilEntities(true, -1, -1);
    }

    public List<EstadoCivil> findEstadoCivilEntities(int maxResults, int firstResult) {
        return findEstadoCivilEntities(false, maxResults, firstResult);
    }

    private List<EstadoCivil> findEstadoCivilEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoCivil.class));
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

    public EstadoCivil findEstadoCivil(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoCivil.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoCivilCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoCivil> rt = cq.from(EstadoCivil.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
