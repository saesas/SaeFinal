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
import com.sae.persistence.domain.Credito;
import com.sae.persistence.domain.PagoCredito;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class PagoCreditoJpaController implements Serializable {

    public PagoCreditoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PagoCredito pagoCredito) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Credito idCredito = pagoCredito.getIdCredito();
            if (idCredito != null) {
                idCredito = em.getReference(idCredito.getClass(), idCredito.getId());
                pagoCredito.setIdCredito(idCredito);
            }
            em.persist(pagoCredito);
            if (idCredito != null) {
                idCredito.getPagoCreditoList().add(pagoCredito);
                idCredito = em.merge(idCredito);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPagoCredito(pagoCredito.getId()) != null) {
                throw new PreexistingEntityException("PagoCredito " + pagoCredito + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PagoCredito pagoCredito) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PagoCredito persistentPagoCredito = em.find(PagoCredito.class, pagoCredito.getId());
            Credito idCreditoOld = persistentPagoCredito.getIdCredito();
            Credito idCreditoNew = pagoCredito.getIdCredito();
            if (idCreditoNew != null) {
                idCreditoNew = em.getReference(idCreditoNew.getClass(), idCreditoNew.getId());
                pagoCredito.setIdCredito(idCreditoNew);
            }
            pagoCredito = em.merge(pagoCredito);
            if (idCreditoOld != null && !idCreditoOld.equals(idCreditoNew)) {
                idCreditoOld.getPagoCreditoList().remove(pagoCredito);
                idCreditoOld = em.merge(idCreditoOld);
            }
            if (idCreditoNew != null && !idCreditoNew.equals(idCreditoOld)) {
                idCreditoNew.getPagoCreditoList().add(pagoCredito);
                idCreditoNew = em.merge(idCreditoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagoCredito.getId();
                if (findPagoCredito(id) == null) {
                    throw new NonexistentEntityException("The pagoCredito with id " + id + " no longer exists.");
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
            PagoCredito pagoCredito;
            try {
                pagoCredito = em.getReference(PagoCredito.class, id);
                pagoCredito.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagoCredito with id " + id + " no longer exists.", enfe);
            }
            Credito idCredito = pagoCredito.getIdCredito();
            if (idCredito != null) {
                idCredito.getPagoCreditoList().remove(pagoCredito);
                idCredito = em.merge(idCredito);
            }
            em.remove(pagoCredito);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PagoCredito> findPagoCreditoEntities() {
        return findPagoCreditoEntities(true, -1, -1);
    }

    public List<PagoCredito> findPagoCreditoEntities(int maxResults, int firstResult) {
        return findPagoCreditoEntities(false, maxResults, firstResult);
    }

    private List<PagoCredito> findPagoCreditoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PagoCredito.class));
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

    public PagoCredito findPagoCredito(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PagoCredito.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoCreditoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PagoCredito> rt = cq.from(PagoCredito.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
