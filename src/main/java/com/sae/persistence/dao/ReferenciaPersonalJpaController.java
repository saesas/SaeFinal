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
import com.sae.persistence.domain.HojaVida;
import com.sae.persistence.domain.ReferenciaPersonal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ReferenciaPersonalJpaController implements Serializable {

    public ReferenciaPersonalJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ReferenciaPersonal referenciaPersonal) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HojaVida idHojaVida = referenciaPersonal.getIdHojaVida();
            if (idHojaVida != null) {
                idHojaVida = em.getReference(idHojaVida.getClass(), idHojaVida.getId());
                referenciaPersonal.setIdHojaVida(idHojaVida);
            }
            em.persist(referenciaPersonal);
            if (idHojaVida != null) {
                idHojaVida.getReferenciaPersonalList().add(referenciaPersonal);
                idHojaVida = em.merge(idHojaVida);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findReferenciaPersonal(referenciaPersonal.getId()) != null) {
                throw new PreexistingEntityException("ReferenciaPersonal " + referenciaPersonal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ReferenciaPersonal referenciaPersonal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ReferenciaPersonal persistentReferenciaPersonal = em.find(ReferenciaPersonal.class, referenciaPersonal.getId());
            HojaVida idHojaVidaOld = persistentReferenciaPersonal.getIdHojaVida();
            HojaVida idHojaVidaNew = referenciaPersonal.getIdHojaVida();
            if (idHojaVidaNew != null) {
                idHojaVidaNew = em.getReference(idHojaVidaNew.getClass(), idHojaVidaNew.getId());
                referenciaPersonal.setIdHojaVida(idHojaVidaNew);
            }
            referenciaPersonal = em.merge(referenciaPersonal);
            if (idHojaVidaOld != null && !idHojaVidaOld.equals(idHojaVidaNew)) {
                idHojaVidaOld.getReferenciaPersonalList().remove(referenciaPersonal);
                idHojaVidaOld = em.merge(idHojaVidaOld);
            }
            if (idHojaVidaNew != null && !idHojaVidaNew.equals(idHojaVidaOld)) {
                idHojaVidaNew.getReferenciaPersonalList().add(referenciaPersonal);
                idHojaVidaNew = em.merge(idHojaVidaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = referenciaPersonal.getId();
                if (findReferenciaPersonal(id) == null) {
                    throw new NonexistentEntityException("The referenciaPersonal with id " + id + " no longer exists.");
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
            ReferenciaPersonal referenciaPersonal;
            try {
                referenciaPersonal = em.getReference(ReferenciaPersonal.class, id);
                referenciaPersonal.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The referenciaPersonal with id " + id + " no longer exists.", enfe);
            }
            HojaVida idHojaVida = referenciaPersonal.getIdHojaVida();
            if (idHojaVida != null) {
                idHojaVida.getReferenciaPersonalList().remove(referenciaPersonal);
                idHojaVida = em.merge(idHojaVida);
            }
            em.remove(referenciaPersonal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ReferenciaPersonal> findReferenciaPersonalEntities() {
        return findReferenciaPersonalEntities(true, -1, -1);
    }

    public List<ReferenciaPersonal> findReferenciaPersonalEntities(int maxResults, int firstResult) {
        return findReferenciaPersonalEntities(false, maxResults, firstResult);
    }

    private List<ReferenciaPersonal> findReferenciaPersonalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ReferenciaPersonal.class));
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

    public ReferenciaPersonal findReferenciaPersonal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ReferenciaPersonal.class, id);
        } finally {
            em.close();
        }
    }

    public int getReferenciaPersonalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ReferenciaPersonal> rt = cq.from(ReferenciaPersonal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
