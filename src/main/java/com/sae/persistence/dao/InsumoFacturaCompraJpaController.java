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
import com.sae.persistence.domain.OrdenCompraInsumo;
import com.sae.persistence.domain.FacturaCompra;
import com.sae.persistence.domain.InsumoFacturaCompra;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class InsumoFacturaCompraJpaController implements Serializable {

    public InsumoFacturaCompraJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InsumoFacturaCompra insumoFacturaCompra) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrdenCompraInsumo idOrdenInsumo = insumoFacturaCompra.getIdOrdenInsumo();
            if (idOrdenInsumo != null) {
                idOrdenInsumo = em.getReference(idOrdenInsumo.getClass(), idOrdenInsumo.getId());
                insumoFacturaCompra.setIdOrdenInsumo(idOrdenInsumo);
            }
            FacturaCompra idFactura = insumoFacturaCompra.getIdFactura();
            if (idFactura != null) {
                idFactura = em.getReference(idFactura.getClass(), idFactura.getId());
                insumoFacturaCompra.setIdFactura(idFactura);
            }
            em.persist(insumoFacturaCompra);
            if (idOrdenInsumo != null) {
                idOrdenInsumo.getInsumoFacturaCompraList().add(insumoFacturaCompra);
                idOrdenInsumo = em.merge(idOrdenInsumo);
            }
            if (idFactura != null) {
                idFactura.getInsumoFacturaCompraList().add(insumoFacturaCompra);
                idFactura = em.merge(idFactura);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInsumoFacturaCompra(insumoFacturaCompra.getId()) != null) {
                throw new PreexistingEntityException("InsumoFacturaCompra " + insumoFacturaCompra + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InsumoFacturaCompra insumoFacturaCompra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InsumoFacturaCompra persistentInsumoFacturaCompra = em.find(InsumoFacturaCompra.class, insumoFacturaCompra.getId());
            OrdenCompraInsumo idOrdenInsumoOld = persistentInsumoFacturaCompra.getIdOrdenInsumo();
            OrdenCompraInsumo idOrdenInsumoNew = insumoFacturaCompra.getIdOrdenInsumo();
            FacturaCompra idFacturaOld = persistentInsumoFacturaCompra.getIdFactura();
            FacturaCompra idFacturaNew = insumoFacturaCompra.getIdFactura();
            if (idOrdenInsumoNew != null) {
                idOrdenInsumoNew = em.getReference(idOrdenInsumoNew.getClass(), idOrdenInsumoNew.getId());
                insumoFacturaCompra.setIdOrdenInsumo(idOrdenInsumoNew);
            }
            if (idFacturaNew != null) {
                idFacturaNew = em.getReference(idFacturaNew.getClass(), idFacturaNew.getId());
                insumoFacturaCompra.setIdFactura(idFacturaNew);
            }
            insumoFacturaCompra = em.merge(insumoFacturaCompra);
            if (idOrdenInsumoOld != null && !idOrdenInsumoOld.equals(idOrdenInsumoNew)) {
                idOrdenInsumoOld.getInsumoFacturaCompraList().remove(insumoFacturaCompra);
                idOrdenInsumoOld = em.merge(idOrdenInsumoOld);
            }
            if (idOrdenInsumoNew != null && !idOrdenInsumoNew.equals(idOrdenInsumoOld)) {
                idOrdenInsumoNew.getInsumoFacturaCompraList().add(insumoFacturaCompra);
                idOrdenInsumoNew = em.merge(idOrdenInsumoNew);
            }
            if (idFacturaOld != null && !idFacturaOld.equals(idFacturaNew)) {
                idFacturaOld.getInsumoFacturaCompraList().remove(insumoFacturaCompra);
                idFacturaOld = em.merge(idFacturaOld);
            }
            if (idFacturaNew != null && !idFacturaNew.equals(idFacturaOld)) {
                idFacturaNew.getInsumoFacturaCompraList().add(insumoFacturaCompra);
                idFacturaNew = em.merge(idFacturaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = insumoFacturaCompra.getId();
                if (findInsumoFacturaCompra(id) == null) {
                    throw new NonexistentEntityException("The insumoFacturaCompra with id " + id + " no longer exists.");
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
            InsumoFacturaCompra insumoFacturaCompra;
            try {
                insumoFacturaCompra = em.getReference(InsumoFacturaCompra.class, id);
                insumoFacturaCompra.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The insumoFacturaCompra with id " + id + " no longer exists.", enfe);
            }
            OrdenCompraInsumo idOrdenInsumo = insumoFacturaCompra.getIdOrdenInsumo();
            if (idOrdenInsumo != null) {
                idOrdenInsumo.getInsumoFacturaCompraList().remove(insumoFacturaCompra);
                idOrdenInsumo = em.merge(idOrdenInsumo);
            }
            FacturaCompra idFactura = insumoFacturaCompra.getIdFactura();
            if (idFactura != null) {
                idFactura.getInsumoFacturaCompraList().remove(insumoFacturaCompra);
                idFactura = em.merge(idFactura);
            }
            em.remove(insumoFacturaCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InsumoFacturaCompra> findInsumoFacturaCompraEntities() {
        return findInsumoFacturaCompraEntities(true, -1, -1);
    }

    public List<InsumoFacturaCompra> findInsumoFacturaCompraEntities(int maxResults, int firstResult) {
        return findInsumoFacturaCompraEntities(false, maxResults, firstResult);
    }

    private List<InsumoFacturaCompra> findInsumoFacturaCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InsumoFacturaCompra.class));
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

    public InsumoFacturaCompra findInsumoFacturaCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InsumoFacturaCompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getInsumoFacturaCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InsumoFacturaCompra> rt = cq.from(InsumoFacturaCompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
