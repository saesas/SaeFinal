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
import com.sae.persistence.domain.Retencion;
import com.sae.persistence.domain.FacturaVenta;
import com.sae.persistence.domain.FacturaVentaRetencion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class FacturaVentaRetencionJpaController implements Serializable {

    public FacturaVentaRetencionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FacturaVentaRetencion facturaVentaRetencion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Retencion idRetencion = facturaVentaRetencion.getIdRetencion();
            if (idRetencion != null) {
                idRetencion = em.getReference(idRetencion.getClass(), idRetencion.getId());
                facturaVentaRetencion.setIdRetencion(idRetencion);
            }
            FacturaVenta idFactura = facturaVentaRetencion.getIdFactura();
            if (idFactura != null) {
                idFactura = em.getReference(idFactura.getClass(), idFactura.getId());
                facturaVentaRetencion.setIdFactura(idFactura);
            }
            em.persist(facturaVentaRetencion);
            if (idRetencion != null) {
                idRetencion.getFacturaVentaRetencionList().add(facturaVentaRetencion);
                idRetencion = em.merge(idRetencion);
            }
            if (idFactura != null) {
                idFactura.getFacturaVentaRetencionList().add(facturaVentaRetencion);
                idFactura = em.merge(idFactura);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFacturaVentaRetencion(facturaVentaRetencion.getId()) != null) {
                throw new PreexistingEntityException("FacturaVentaRetencion " + facturaVentaRetencion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FacturaVentaRetencion facturaVentaRetencion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacturaVentaRetencion persistentFacturaVentaRetencion = em.find(FacturaVentaRetencion.class, facturaVentaRetencion.getId());
            Retencion idRetencionOld = persistentFacturaVentaRetencion.getIdRetencion();
            Retencion idRetencionNew = facturaVentaRetencion.getIdRetencion();
            FacturaVenta idFacturaOld = persistentFacturaVentaRetencion.getIdFactura();
            FacturaVenta idFacturaNew = facturaVentaRetencion.getIdFactura();
            if (idRetencionNew != null) {
                idRetencionNew = em.getReference(idRetencionNew.getClass(), idRetencionNew.getId());
                facturaVentaRetencion.setIdRetencion(idRetencionNew);
            }
            if (idFacturaNew != null) {
                idFacturaNew = em.getReference(idFacturaNew.getClass(), idFacturaNew.getId());
                facturaVentaRetencion.setIdFactura(idFacturaNew);
            }
            facturaVentaRetencion = em.merge(facturaVentaRetencion);
            if (idRetencionOld != null && !idRetencionOld.equals(idRetencionNew)) {
                idRetencionOld.getFacturaVentaRetencionList().remove(facturaVentaRetencion);
                idRetencionOld = em.merge(idRetencionOld);
            }
            if (idRetencionNew != null && !idRetencionNew.equals(idRetencionOld)) {
                idRetencionNew.getFacturaVentaRetencionList().add(facturaVentaRetencion);
                idRetencionNew = em.merge(idRetencionNew);
            }
            if (idFacturaOld != null && !idFacturaOld.equals(idFacturaNew)) {
                idFacturaOld.getFacturaVentaRetencionList().remove(facturaVentaRetencion);
                idFacturaOld = em.merge(idFacturaOld);
            }
            if (idFacturaNew != null && !idFacturaNew.equals(idFacturaOld)) {
                idFacturaNew.getFacturaVentaRetencionList().add(facturaVentaRetencion);
                idFacturaNew = em.merge(idFacturaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facturaVentaRetencion.getId();
                if (findFacturaVentaRetencion(id) == null) {
                    throw new NonexistentEntityException("The facturaVentaRetencion with id " + id + " no longer exists.");
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
            FacturaVentaRetencion facturaVentaRetencion;
            try {
                facturaVentaRetencion = em.getReference(FacturaVentaRetencion.class, id);
                facturaVentaRetencion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturaVentaRetencion with id " + id + " no longer exists.", enfe);
            }
            Retencion idRetencion = facturaVentaRetencion.getIdRetencion();
            if (idRetencion != null) {
                idRetencion.getFacturaVentaRetencionList().remove(facturaVentaRetencion);
                idRetencion = em.merge(idRetencion);
            }
            FacturaVenta idFactura = facturaVentaRetencion.getIdFactura();
            if (idFactura != null) {
                idFactura.getFacturaVentaRetencionList().remove(facturaVentaRetencion);
                idFactura = em.merge(idFactura);
            }
            em.remove(facturaVentaRetencion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FacturaVentaRetencion> findFacturaVentaRetencionEntities() {
        return findFacturaVentaRetencionEntities(true, -1, -1);
    }

    public List<FacturaVentaRetencion> findFacturaVentaRetencionEntities(int maxResults, int firstResult) {
        return findFacturaVentaRetencionEntities(false, maxResults, firstResult);
    }

    private List<FacturaVentaRetencion> findFacturaVentaRetencionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FacturaVentaRetencion.class));
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

    public FacturaVentaRetencion findFacturaVentaRetencion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FacturaVentaRetencion.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaVentaRetencionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FacturaVentaRetencion> rt = cq.from(FacturaVentaRetencion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
