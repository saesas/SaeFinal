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
import com.sae.persistence.domain.Clase;
import com.sae.persistence.domain.Recurso;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class RecursoJpaController implements Serializable {

    public RecursoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Recurso recurso) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clase idInstancia = recurso.getIdInstancia();
            if (idInstancia != null) {
                idInstancia = em.getReference(idInstancia.getClass(), idInstancia.getId());
                recurso.setIdInstancia(idInstancia);
            }
            em.persist(recurso);
            if (idInstancia != null) {
                idInstancia.getRecursoList().add(recurso);
                idInstancia = em.merge(idInstancia);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRecurso(recurso.getId()) != null) {
                throw new PreexistingEntityException("Recurso " + recurso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Recurso recurso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Recurso persistentRecurso = em.find(Recurso.class, recurso.getId());
            Clase idInstanciaOld = persistentRecurso.getIdInstancia();
            Clase idInstanciaNew = recurso.getIdInstancia();
            if (idInstanciaNew != null) {
                idInstanciaNew = em.getReference(idInstanciaNew.getClass(), idInstanciaNew.getId());
                recurso.setIdInstancia(idInstanciaNew);
            }
            recurso = em.merge(recurso);
            if (idInstanciaOld != null && !idInstanciaOld.equals(idInstanciaNew)) {
                idInstanciaOld.getRecursoList().remove(recurso);
                idInstanciaOld = em.merge(idInstanciaOld);
            }
            if (idInstanciaNew != null && !idInstanciaNew.equals(idInstanciaOld)) {
                idInstanciaNew.getRecursoList().add(recurso);
                idInstanciaNew = em.merge(idInstanciaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = recurso.getId();
                if (findRecurso(id) == null) {
                    throw new NonexistentEntityException("The recurso with id " + id + " no longer exists.");
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
            Recurso recurso;
            try {
                recurso = em.getReference(Recurso.class, id);
                recurso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recurso with id " + id + " no longer exists.", enfe);
            }
            Clase idInstancia = recurso.getIdInstancia();
            if (idInstancia != null) {
                idInstancia.getRecursoList().remove(recurso);
                idInstancia = em.merge(idInstancia);
            }
            em.remove(recurso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Recurso> findRecursoEntities() {
        return findRecursoEntities(true, -1, -1);
    }

    public List<Recurso> findRecursoEntities(int maxResults, int firstResult) {
        return findRecursoEntities(false, maxResults, firstResult);
    }

    private List<Recurso> findRecursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Recurso.class));
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

    public Recurso findRecurso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Recurso.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Recurso> rt = cq.from(Recurso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
