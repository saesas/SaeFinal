/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.Genero;
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
public class GeneroJpaController implements Serializable {

    public GeneroJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Genero genero) throws PreexistingEntityException, Exception {
        if (genero.getTerceroList() == null) {
            genero.setTerceroList(new ArrayList<Tercero>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Tercero> attachedTerceroList = new ArrayList<Tercero>();
            for (Tercero terceroListTerceroToAttach : genero.getTerceroList()) {
                terceroListTerceroToAttach = em.getReference(terceroListTerceroToAttach.getClass(), terceroListTerceroToAttach.getId());
                attachedTerceroList.add(terceroListTerceroToAttach);
            }
            genero.setTerceroList(attachedTerceroList);
            em.persist(genero);
            for (Tercero terceroListTercero : genero.getTerceroList()) {
                Genero oldGeneroOfTerceroListTercero = terceroListTercero.getGenero();
                terceroListTercero.setGenero(genero);
                terceroListTercero = em.merge(terceroListTercero);
                if (oldGeneroOfTerceroListTercero != null) {
                    oldGeneroOfTerceroListTercero.getTerceroList().remove(terceroListTercero);
                    oldGeneroOfTerceroListTercero = em.merge(oldGeneroOfTerceroListTercero);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGenero(genero.getId()) != null) {
                throw new PreexistingEntityException("Genero " + genero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Genero genero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Genero persistentGenero = em.find(Genero.class, genero.getId());
            List<Tercero> terceroListOld = persistentGenero.getTerceroList();
            List<Tercero> terceroListNew = genero.getTerceroList();
            List<Tercero> attachedTerceroListNew = new ArrayList<Tercero>();
            for (Tercero terceroListNewTerceroToAttach : terceroListNew) {
                terceroListNewTerceroToAttach = em.getReference(terceroListNewTerceroToAttach.getClass(), terceroListNewTerceroToAttach.getId());
                attachedTerceroListNew.add(terceroListNewTerceroToAttach);
            }
            terceroListNew = attachedTerceroListNew;
            genero.setTerceroList(terceroListNew);
            genero = em.merge(genero);
            for (Tercero terceroListOldTercero : terceroListOld) {
                if (!terceroListNew.contains(terceroListOldTercero)) {
                    terceroListOldTercero.setGenero(null);
                    terceroListOldTercero = em.merge(terceroListOldTercero);
                }
            }
            for (Tercero terceroListNewTercero : terceroListNew) {
                if (!terceroListOld.contains(terceroListNewTercero)) {
                    Genero oldGeneroOfTerceroListNewTercero = terceroListNewTercero.getGenero();
                    terceroListNewTercero.setGenero(genero);
                    terceroListNewTercero = em.merge(terceroListNewTercero);
                    if (oldGeneroOfTerceroListNewTercero != null && !oldGeneroOfTerceroListNewTercero.equals(genero)) {
                        oldGeneroOfTerceroListNewTercero.getTerceroList().remove(terceroListNewTercero);
                        oldGeneroOfTerceroListNewTercero = em.merge(oldGeneroOfTerceroListNewTercero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = genero.getId();
                if (findGenero(id) == null) {
                    throw new NonexistentEntityException("The genero with id " + id + " no longer exists.");
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
            Genero genero;
            try {
                genero = em.getReference(Genero.class, id);
                genero.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The genero with id " + id + " no longer exists.", enfe);
            }
            List<Tercero> terceroList = genero.getTerceroList();
            for (Tercero terceroListTercero : terceroList) {
                terceroListTercero.setGenero(null);
                terceroListTercero = em.merge(terceroListTercero);
            }
            em.remove(genero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Genero> findGeneroEntities() {
        return findGeneroEntities(true, -1, -1);
    }

    public List<Genero> findGeneroEntities(int maxResults, int firstResult) {
        return findGeneroEntities(false, maxResults, firstResult);
    }

    private List<Genero> findGeneroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Genero.class));
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

    public Genero findGenero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Genero.class, id);
        } finally {
            em.close();
        }
    }

    public int getGeneroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Genero> rt = cq.from(Genero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
