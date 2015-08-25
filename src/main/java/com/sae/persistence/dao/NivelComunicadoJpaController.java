/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.NivelComunicado;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author SAE2
 */
public class NivelComunicadoJpaController implements Serializable {

    public NivelComunicadoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NivelComunicado nivelComunicado) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nivelComunicado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNivelComunicado(nivelComunicado.getId()) != null) {
                throw new PreexistingEntityException("NivelComunicado " + nivelComunicado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NivelComunicado nivelComunicado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            nivelComunicado = em.merge(nivelComunicado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = nivelComunicado.getId();
                if (findNivelComunicado(id) == null) {
                    throw new NonexistentEntityException("The nivelComunicado with id " + id + " no longer exists.");
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
            NivelComunicado nivelComunicado;
            try {
                nivelComunicado = em.getReference(NivelComunicado.class, id);
                nivelComunicado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nivelComunicado with id " + id + " no longer exists.", enfe);
            }
            em.remove(nivelComunicado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NivelComunicado> findNivelComunicadoEntities() {
        return findNivelComunicadoEntities(true, -1, -1);
    }

    public List<NivelComunicado> findNivelComunicadoEntities(int maxResults, int firstResult) {
        return findNivelComunicadoEntities(false, maxResults, firstResult);
    }

    private List<NivelComunicado> findNivelComunicadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NivelComunicado.class));
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

    public NivelComunicado findNivelComunicado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NivelComunicado.class, id);
        } finally {
            em.close();
        }
    }

    public int getNivelComunicadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NivelComunicado> rt = cq.from(NivelComunicado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
