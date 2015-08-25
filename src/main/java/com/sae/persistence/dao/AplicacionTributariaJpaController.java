/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.AplicacionTributaria;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Retencion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class AplicacionTributariaJpaController implements Serializable {

    public AplicacionTributariaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AplicacionTributaria aplicacionTributaria) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Retencion idRetencion = aplicacionTributaria.getIdRetencion();
            if (idRetencion != null) {
                idRetencion = em.getReference(idRetencion.getClass(), idRetencion.getId());
                aplicacionTributaria.setIdRetencion(idRetencion);
            }
            em.persist(aplicacionTributaria);
            if (idRetencion != null) {
                idRetencion.getAplicacionTributariaList().add(aplicacionTributaria);
                idRetencion = em.merge(idRetencion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAplicacionTributaria(aplicacionTributaria.getId()) != null) {
                throw new PreexistingEntityException("AplicacionTributaria " + aplicacionTributaria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AplicacionTributaria aplicacionTributaria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AplicacionTributaria persistentAplicacionTributaria = em.find(AplicacionTributaria.class, aplicacionTributaria.getId());
            Retencion idRetencionOld = persistentAplicacionTributaria.getIdRetencion();
            Retencion idRetencionNew = aplicacionTributaria.getIdRetencion();
            if (idRetencionNew != null) {
                idRetencionNew = em.getReference(idRetencionNew.getClass(), idRetencionNew.getId());
                aplicacionTributaria.setIdRetencion(idRetencionNew);
            }
            aplicacionTributaria = em.merge(aplicacionTributaria);
            if (idRetencionOld != null && !idRetencionOld.equals(idRetencionNew)) {
                idRetencionOld.getAplicacionTributariaList().remove(aplicacionTributaria);
                idRetencionOld = em.merge(idRetencionOld);
            }
            if (idRetencionNew != null && !idRetencionNew.equals(idRetencionOld)) {
                idRetencionNew.getAplicacionTributariaList().add(aplicacionTributaria);
                idRetencionNew = em.merge(idRetencionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = aplicacionTributaria.getId();
                if (findAplicacionTributaria(id) == null) {
                    throw new NonexistentEntityException("The aplicacionTributaria with id " + id + " no longer exists.");
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
            AplicacionTributaria aplicacionTributaria;
            try {
                aplicacionTributaria = em.getReference(AplicacionTributaria.class, id);
                aplicacionTributaria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aplicacionTributaria with id " + id + " no longer exists.", enfe);
            }
            Retencion idRetencion = aplicacionTributaria.getIdRetencion();
            if (idRetencion != null) {
                idRetencion.getAplicacionTributariaList().remove(aplicacionTributaria);
                idRetencion = em.merge(idRetencion);
            }
            em.remove(aplicacionTributaria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AplicacionTributaria> findAplicacionTributariaEntities() {
        return findAplicacionTributariaEntities(true, -1, -1);
    }

    public List<AplicacionTributaria> findAplicacionTributariaEntities(int maxResults, int firstResult) {
        return findAplicacionTributariaEntities(false, maxResults, firstResult);
    }

    private List<AplicacionTributaria> findAplicacionTributariaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AplicacionTributaria.class));
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

    public AplicacionTributaria findAplicacionTributaria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AplicacionTributaria.class, id);
        } finally {
            em.close();
        }
    }

    public int getAplicacionTributariaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AplicacionTributaria> rt = cq.from(AplicacionTributaria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
