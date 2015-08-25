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
import com.sae.persistence.domain.TipoCuantia;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoCuantiaJpaController implements Serializable {

    public TipoCuantiaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoCuantia tipoCuantia) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clase idProcedimientoJuridico = tipoCuantia.getIdProcedimientoJuridico();
            if (idProcedimientoJuridico != null) {
                idProcedimientoJuridico = em.getReference(idProcedimientoJuridico.getClass(), idProcedimientoJuridico.getId());
                tipoCuantia.setIdProcedimientoJuridico(idProcedimientoJuridico);
            }
            em.persist(tipoCuantia);
            if (idProcedimientoJuridico != null) {
                idProcedimientoJuridico.getTipoCuantiaList().add(tipoCuantia);
                idProcedimientoJuridico = em.merge(idProcedimientoJuridico);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoCuantia(tipoCuantia.getId()) != null) {
                throw new PreexistingEntityException("TipoCuantia " + tipoCuantia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoCuantia tipoCuantia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoCuantia persistentTipoCuantia = em.find(TipoCuantia.class, tipoCuantia.getId());
            Clase idProcedimientoJuridicoOld = persistentTipoCuantia.getIdProcedimientoJuridico();
            Clase idProcedimientoJuridicoNew = tipoCuantia.getIdProcedimientoJuridico();
            if (idProcedimientoJuridicoNew != null) {
                idProcedimientoJuridicoNew = em.getReference(idProcedimientoJuridicoNew.getClass(), idProcedimientoJuridicoNew.getId());
                tipoCuantia.setIdProcedimientoJuridico(idProcedimientoJuridicoNew);
            }
            tipoCuantia = em.merge(tipoCuantia);
            if (idProcedimientoJuridicoOld != null && !idProcedimientoJuridicoOld.equals(idProcedimientoJuridicoNew)) {
                idProcedimientoJuridicoOld.getTipoCuantiaList().remove(tipoCuantia);
                idProcedimientoJuridicoOld = em.merge(idProcedimientoJuridicoOld);
            }
            if (idProcedimientoJuridicoNew != null && !idProcedimientoJuridicoNew.equals(idProcedimientoJuridicoOld)) {
                idProcedimientoJuridicoNew.getTipoCuantiaList().add(tipoCuantia);
                idProcedimientoJuridicoNew = em.merge(idProcedimientoJuridicoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoCuantia.getId();
                if (findTipoCuantia(id) == null) {
                    throw new NonexistentEntityException("The tipoCuantia with id " + id + " no longer exists.");
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
            TipoCuantia tipoCuantia;
            try {
                tipoCuantia = em.getReference(TipoCuantia.class, id);
                tipoCuantia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoCuantia with id " + id + " no longer exists.", enfe);
            }
            Clase idProcedimientoJuridico = tipoCuantia.getIdProcedimientoJuridico();
            if (idProcedimientoJuridico != null) {
                idProcedimientoJuridico.getTipoCuantiaList().remove(tipoCuantia);
                idProcedimientoJuridico = em.merge(idProcedimientoJuridico);
            }
            em.remove(tipoCuantia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoCuantia> findTipoCuantiaEntities() {
        return findTipoCuantiaEntities(true, -1, -1);
    }

    public List<TipoCuantia> findTipoCuantiaEntities(int maxResults, int firstResult) {
        return findTipoCuantiaEntities(false, maxResults, firstResult);
    }

    private List<TipoCuantia> findTipoCuantiaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoCuantia.class));
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

    public TipoCuantia findTipoCuantia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoCuantia.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoCuantiaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoCuantia> rt = cq.from(TipoCuantia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
