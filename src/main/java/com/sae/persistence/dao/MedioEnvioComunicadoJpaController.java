/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.MedioEnvioComunicado;
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
public class MedioEnvioComunicadoJpaController implements Serializable {

    public MedioEnvioComunicadoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MedioEnvioComunicado medioEnvioComunicado) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(medioEnvioComunicado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMedioEnvioComunicado(medioEnvioComunicado.getId()) != null) {
                throw new PreexistingEntityException("MedioEnvioComunicado " + medioEnvioComunicado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MedioEnvioComunicado medioEnvioComunicado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            medioEnvioComunicado = em.merge(medioEnvioComunicado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = medioEnvioComunicado.getId();
                if (findMedioEnvioComunicado(id) == null) {
                    throw new NonexistentEntityException("The medioEnvioComunicado with id " + id + " no longer exists.");
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
            MedioEnvioComunicado medioEnvioComunicado;
            try {
                medioEnvioComunicado = em.getReference(MedioEnvioComunicado.class, id);
                medioEnvioComunicado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The medioEnvioComunicado with id " + id + " no longer exists.", enfe);
            }
            em.remove(medioEnvioComunicado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MedioEnvioComunicado> findMedioEnvioComunicadoEntities() {
        return findMedioEnvioComunicadoEntities(true, -1, -1);
    }

    public List<MedioEnvioComunicado> findMedioEnvioComunicadoEntities(int maxResults, int firstResult) {
        return findMedioEnvioComunicadoEntities(false, maxResults, firstResult);
    }

    private List<MedioEnvioComunicado> findMedioEnvioComunicadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MedioEnvioComunicado.class));
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

    public MedioEnvioComunicado findMedioEnvioComunicado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MedioEnvioComunicado.class, id);
        } finally {
            em.close();
        }
    }

    public int getMedioEnvioComunicadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MedioEnvioComunicado> rt = cq.from(MedioEnvioComunicado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
