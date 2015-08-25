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
import com.sae.persistence.domain.Comprobante;
import com.sae.persistence.domain.Reintegro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ReintegroJpaController implements Serializable {

    public ReintegroJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reintegro reintegro) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comprobante idComprobante = reintegro.getIdComprobante();
            if (idComprobante != null) {
                idComprobante = em.getReference(idComprobante.getClass(), idComprobante.getId());
                reintegro.setIdComprobante(idComprobante);
            }
            em.persist(reintegro);
            if (idComprobante != null) {
                idComprobante.getReintegroList().add(reintegro);
                idComprobante = em.merge(idComprobante);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findReintegro(reintegro.getId()) != null) {
                throw new PreexistingEntityException("Reintegro " + reintegro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reintegro reintegro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reintegro persistentReintegro = em.find(Reintegro.class, reintegro.getId());
            Comprobante idComprobanteOld = persistentReintegro.getIdComprobante();
            Comprobante idComprobanteNew = reintegro.getIdComprobante();
            if (idComprobanteNew != null) {
                idComprobanteNew = em.getReference(idComprobanteNew.getClass(), idComprobanteNew.getId());
                reintegro.setIdComprobante(idComprobanteNew);
            }
            reintegro = em.merge(reintegro);
            if (idComprobanteOld != null && !idComprobanteOld.equals(idComprobanteNew)) {
                idComprobanteOld.getReintegroList().remove(reintegro);
                idComprobanteOld = em.merge(idComprobanteOld);
            }
            if (idComprobanteNew != null && !idComprobanteNew.equals(idComprobanteOld)) {
                idComprobanteNew.getReintegroList().add(reintegro);
                idComprobanteNew = em.merge(idComprobanteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reintegro.getId();
                if (findReintegro(id) == null) {
                    throw new NonexistentEntityException("The reintegro with id " + id + " no longer exists.");
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
            Reintegro reintegro;
            try {
                reintegro = em.getReference(Reintegro.class, id);
                reintegro.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reintegro with id " + id + " no longer exists.", enfe);
            }
            Comprobante idComprobante = reintegro.getIdComprobante();
            if (idComprobante != null) {
                idComprobante.getReintegroList().remove(reintegro);
                idComprobante = em.merge(idComprobante);
            }
            em.remove(reintegro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reintegro> findReintegroEntities() {
        return findReintegroEntities(true, -1, -1);
    }

    public List<Reintegro> findReintegroEntities(int maxResults, int firstResult) {
        return findReintegroEntities(false, maxResults, firstResult);
    }

    private List<Reintegro> findReintegroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reintegro.class));
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

    public Reintegro findReintegro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reintegro.class, id);
        } finally {
            em.close();
        }
    }

    public int getReintegroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reintegro> rt = cq.from(Reintegro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
