/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.AdjuntoFactura;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.FacturaCompra;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class AdjuntoFacturaJpaController implements Serializable {

    public AdjuntoFacturaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AdjuntoFactura adjuntoFactura) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacturaCompra idFactura = adjuntoFactura.getIdFactura();
            if (idFactura != null) {
                idFactura = em.getReference(idFactura.getClass(), idFactura.getId());
                adjuntoFactura.setIdFactura(idFactura);
            }
            em.persist(adjuntoFactura);
            if (idFactura != null) {
                idFactura.getAdjuntoFacturaList().add(adjuntoFactura);
                idFactura = em.merge(idFactura);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAdjuntoFactura(adjuntoFactura.getId()) != null) {
                throw new PreexistingEntityException("AdjuntoFactura " + adjuntoFactura + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AdjuntoFactura adjuntoFactura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AdjuntoFactura persistentAdjuntoFactura = em.find(AdjuntoFactura.class, adjuntoFactura.getId());
            FacturaCompra idFacturaOld = persistentAdjuntoFactura.getIdFactura();
            FacturaCompra idFacturaNew = adjuntoFactura.getIdFactura();
            if (idFacturaNew != null) {
                idFacturaNew = em.getReference(idFacturaNew.getClass(), idFacturaNew.getId());
                adjuntoFactura.setIdFactura(idFacturaNew);
            }
            adjuntoFactura = em.merge(adjuntoFactura);
            if (idFacturaOld != null && !idFacturaOld.equals(idFacturaNew)) {
                idFacturaOld.getAdjuntoFacturaList().remove(adjuntoFactura);
                idFacturaOld = em.merge(idFacturaOld);
            }
            if (idFacturaNew != null && !idFacturaNew.equals(idFacturaOld)) {
                idFacturaNew.getAdjuntoFacturaList().add(adjuntoFactura);
                idFacturaNew = em.merge(idFacturaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = adjuntoFactura.getId();
                if (findAdjuntoFactura(id) == null) {
                    throw new NonexistentEntityException("The adjuntoFactura with id " + id + " no longer exists.");
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
            AdjuntoFactura adjuntoFactura;
            try {
                adjuntoFactura = em.getReference(AdjuntoFactura.class, id);
                adjuntoFactura.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The adjuntoFactura with id " + id + " no longer exists.", enfe);
            }
            FacturaCompra idFactura = adjuntoFactura.getIdFactura();
            if (idFactura != null) {
                idFactura.getAdjuntoFacturaList().remove(adjuntoFactura);
                idFactura = em.merge(idFactura);
            }
            em.remove(adjuntoFactura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AdjuntoFactura> findAdjuntoFacturaEntities() {
        return findAdjuntoFacturaEntities(true, -1, -1);
    }

    public List<AdjuntoFactura> findAdjuntoFacturaEntities(int maxResults, int firstResult) {
        return findAdjuntoFacturaEntities(false, maxResults, firstResult);
    }

    private List<AdjuntoFactura> findAdjuntoFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AdjuntoFactura.class));
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

    public AdjuntoFactura findAdjuntoFactura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AdjuntoFactura.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdjuntoFacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AdjuntoFactura> rt = cq.from(AdjuntoFactura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
