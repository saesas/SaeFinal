/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.PagoAnticipoReintegro;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.PrestamoAnticipo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class PagoAnticipoReintegroJpaController implements Serializable {

    public PagoAnticipoReintegroJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PagoAnticipoReintegro pagoAnticipoReintegro) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PrestamoAnticipo idAnticipo = pagoAnticipoReintegro.getIdAnticipo();
            if (idAnticipo != null) {
                idAnticipo = em.getReference(idAnticipo.getClass(), idAnticipo.getId());
                pagoAnticipoReintegro.setIdAnticipo(idAnticipo);
            }
            em.persist(pagoAnticipoReintegro);
            if (idAnticipo != null) {
                idAnticipo.getPagoAnticipoReintegroList().add(pagoAnticipoReintegro);
                idAnticipo = em.merge(idAnticipo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPagoAnticipoReintegro(pagoAnticipoReintegro.getId()) != null) {
                throw new PreexistingEntityException("PagoAnticipoReintegro " + pagoAnticipoReintegro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PagoAnticipoReintegro pagoAnticipoReintegro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PagoAnticipoReintegro persistentPagoAnticipoReintegro = em.find(PagoAnticipoReintegro.class, pagoAnticipoReintegro.getId());
            PrestamoAnticipo idAnticipoOld = persistentPagoAnticipoReintegro.getIdAnticipo();
            PrestamoAnticipo idAnticipoNew = pagoAnticipoReintegro.getIdAnticipo();
            if (idAnticipoNew != null) {
                idAnticipoNew = em.getReference(idAnticipoNew.getClass(), idAnticipoNew.getId());
                pagoAnticipoReintegro.setIdAnticipo(idAnticipoNew);
            }
            pagoAnticipoReintegro = em.merge(pagoAnticipoReintegro);
            if (idAnticipoOld != null && !idAnticipoOld.equals(idAnticipoNew)) {
                idAnticipoOld.getPagoAnticipoReintegroList().remove(pagoAnticipoReintegro);
                idAnticipoOld = em.merge(idAnticipoOld);
            }
            if (idAnticipoNew != null && !idAnticipoNew.equals(idAnticipoOld)) {
                idAnticipoNew.getPagoAnticipoReintegroList().add(pagoAnticipoReintegro);
                idAnticipoNew = em.merge(idAnticipoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagoAnticipoReintegro.getId();
                if (findPagoAnticipoReintegro(id) == null) {
                    throw new NonexistentEntityException("The pagoAnticipoReintegro with id " + id + " no longer exists.");
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
            PagoAnticipoReintegro pagoAnticipoReintegro;
            try {
                pagoAnticipoReintegro = em.getReference(PagoAnticipoReintegro.class, id);
                pagoAnticipoReintegro.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagoAnticipoReintegro with id " + id + " no longer exists.", enfe);
            }
            PrestamoAnticipo idAnticipo = pagoAnticipoReintegro.getIdAnticipo();
            if (idAnticipo != null) {
                idAnticipo.getPagoAnticipoReintegroList().remove(pagoAnticipoReintegro);
                idAnticipo = em.merge(idAnticipo);
            }
            em.remove(pagoAnticipoReintegro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PagoAnticipoReintegro> findPagoAnticipoReintegroEntities() {
        return findPagoAnticipoReintegroEntities(true, -1, -1);
    }

    public List<PagoAnticipoReintegro> findPagoAnticipoReintegroEntities(int maxResults, int firstResult) {
        return findPagoAnticipoReintegroEntities(false, maxResults, firstResult);
    }

    private List<PagoAnticipoReintegro> findPagoAnticipoReintegroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PagoAnticipoReintegro.class));
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

    public PagoAnticipoReintegro findPagoAnticipoReintegro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PagoAnticipoReintegro.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoAnticipoReintegroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PagoAnticipoReintegro> rt = cq.from(PagoAnticipoReintegro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
