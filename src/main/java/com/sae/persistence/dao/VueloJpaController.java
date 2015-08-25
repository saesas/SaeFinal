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
import com.sae.persistence.domain.Tiquete;
import com.sae.persistence.domain.Vuelo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class VueloJpaController implements Serializable {

    public VueloJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vuelo vuelo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tiquete idTiquete = vuelo.getIdTiquete();
            if (idTiquete != null) {
                idTiquete = em.getReference(idTiquete.getClass(), idTiquete.getId());
                vuelo.setIdTiquete(idTiquete);
            }
            em.persist(vuelo);
            if (idTiquete != null) {
                idTiquete.getVueloList().add(vuelo);
                idTiquete = em.merge(idTiquete);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVuelo(vuelo.getId()) != null) {
                throw new PreexistingEntityException("Vuelo " + vuelo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vuelo vuelo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vuelo persistentVuelo = em.find(Vuelo.class, vuelo.getId());
            Tiquete idTiqueteOld = persistentVuelo.getIdTiquete();
            Tiquete idTiqueteNew = vuelo.getIdTiquete();
            if (idTiqueteNew != null) {
                idTiqueteNew = em.getReference(idTiqueteNew.getClass(), idTiqueteNew.getId());
                vuelo.setIdTiquete(idTiqueteNew);
            }
            vuelo = em.merge(vuelo);
            if (idTiqueteOld != null && !idTiqueteOld.equals(idTiqueteNew)) {
                idTiqueteOld.getVueloList().remove(vuelo);
                idTiqueteOld = em.merge(idTiqueteOld);
            }
            if (idTiqueteNew != null && !idTiqueteNew.equals(idTiqueteOld)) {
                idTiqueteNew.getVueloList().add(vuelo);
                idTiqueteNew = em.merge(idTiqueteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vuelo.getId();
                if (findVuelo(id) == null) {
                    throw new NonexistentEntityException("The vuelo with id " + id + " no longer exists.");
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
            Vuelo vuelo;
            try {
                vuelo = em.getReference(Vuelo.class, id);
                vuelo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vuelo with id " + id + " no longer exists.", enfe);
            }
            Tiquete idTiquete = vuelo.getIdTiquete();
            if (idTiquete != null) {
                idTiquete.getVueloList().remove(vuelo);
                idTiquete = em.merge(idTiquete);
            }
            em.remove(vuelo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vuelo> findVueloEntities() {
        return findVueloEntities(true, -1, -1);
    }

    public List<Vuelo> findVueloEntities(int maxResults, int firstResult) {
        return findVueloEntities(false, maxResults, firstResult);
    }

    private List<Vuelo> findVueloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vuelo.class));
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

    public Vuelo findVuelo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vuelo.class, id);
        } finally {
            em.close();
        }
    }

    public int getVueloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vuelo> rt = cq.from(Vuelo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
