/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.AdjuntoCuentaCobro;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.CuentaCobro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class AdjuntoCuentaCobroJpaController implements Serializable {

    public AdjuntoCuentaCobroJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AdjuntoCuentaCobro adjuntoCuentaCobro) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuentaCobro idCuentaCobro = adjuntoCuentaCobro.getIdCuentaCobro();
            if (idCuentaCobro != null) {
                idCuentaCobro = em.getReference(idCuentaCobro.getClass(), idCuentaCobro.getId());
                adjuntoCuentaCobro.setIdCuentaCobro(idCuentaCobro);
            }
            em.persist(adjuntoCuentaCobro);
            if (idCuentaCobro != null) {
                idCuentaCobro.getAdjuntoCuentaCobroList().add(adjuntoCuentaCobro);
                idCuentaCobro = em.merge(idCuentaCobro);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAdjuntoCuentaCobro(adjuntoCuentaCobro.getId()) != null) {
                throw new PreexistingEntityException("AdjuntoCuentaCobro " + adjuntoCuentaCobro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AdjuntoCuentaCobro adjuntoCuentaCobro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AdjuntoCuentaCobro persistentAdjuntoCuentaCobro = em.find(AdjuntoCuentaCobro.class, adjuntoCuentaCobro.getId());
            CuentaCobro idCuentaCobroOld = persistentAdjuntoCuentaCobro.getIdCuentaCobro();
            CuentaCobro idCuentaCobroNew = adjuntoCuentaCobro.getIdCuentaCobro();
            if (idCuentaCobroNew != null) {
                idCuentaCobroNew = em.getReference(idCuentaCobroNew.getClass(), idCuentaCobroNew.getId());
                adjuntoCuentaCobro.setIdCuentaCobro(idCuentaCobroNew);
            }
            adjuntoCuentaCobro = em.merge(adjuntoCuentaCobro);
            if (idCuentaCobroOld != null && !idCuentaCobroOld.equals(idCuentaCobroNew)) {
                idCuentaCobroOld.getAdjuntoCuentaCobroList().remove(adjuntoCuentaCobro);
                idCuentaCobroOld = em.merge(idCuentaCobroOld);
            }
            if (idCuentaCobroNew != null && !idCuentaCobroNew.equals(idCuentaCobroOld)) {
                idCuentaCobroNew.getAdjuntoCuentaCobroList().add(adjuntoCuentaCobro);
                idCuentaCobroNew = em.merge(idCuentaCobroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = adjuntoCuentaCobro.getId();
                if (findAdjuntoCuentaCobro(id) == null) {
                    throw new NonexistentEntityException("The adjuntoCuentaCobro with id " + id + " no longer exists.");
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
            AdjuntoCuentaCobro adjuntoCuentaCobro;
            try {
                adjuntoCuentaCobro = em.getReference(AdjuntoCuentaCobro.class, id);
                adjuntoCuentaCobro.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The adjuntoCuentaCobro with id " + id + " no longer exists.", enfe);
            }
            CuentaCobro idCuentaCobro = adjuntoCuentaCobro.getIdCuentaCobro();
            if (idCuentaCobro != null) {
                idCuentaCobro.getAdjuntoCuentaCobroList().remove(adjuntoCuentaCobro);
                idCuentaCobro = em.merge(idCuentaCobro);
            }
            em.remove(adjuntoCuentaCobro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AdjuntoCuentaCobro> findAdjuntoCuentaCobroEntities() {
        return findAdjuntoCuentaCobroEntities(true, -1, -1);
    }

    public List<AdjuntoCuentaCobro> findAdjuntoCuentaCobroEntities(int maxResults, int firstResult) {
        return findAdjuntoCuentaCobroEntities(false, maxResults, firstResult);
    }

    private List<AdjuntoCuentaCobro> findAdjuntoCuentaCobroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AdjuntoCuentaCobro.class));
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

    public AdjuntoCuentaCobro findAdjuntoCuentaCobro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AdjuntoCuentaCobro.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdjuntoCuentaCobroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AdjuntoCuentaCobro> rt = cq.from(AdjuntoCuentaCobro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
