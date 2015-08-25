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
import com.sae.persistence.domain.Almacen;
import com.sae.persistence.domain.Bodega;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class BodegaJpaController implements Serializable {

    public BodegaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bodega bodega) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Almacen idAlmacen = bodega.getIdAlmacen();
            if (idAlmacen != null) {
                idAlmacen = em.getReference(idAlmacen.getClass(), idAlmacen.getId());
                bodega.setIdAlmacen(idAlmacen);
            }
            em.persist(bodega);
            if (idAlmacen != null) {
                idAlmacen.getBodegaList().add(bodega);
                idAlmacen = em.merge(idAlmacen);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBodega(bodega.getId()) != null) {
                throw new PreexistingEntityException("Bodega " + bodega + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bodega bodega) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bodega persistentBodega = em.find(Bodega.class, bodega.getId());
            Almacen idAlmacenOld = persistentBodega.getIdAlmacen();
            Almacen idAlmacenNew = bodega.getIdAlmacen();
            if (idAlmacenNew != null) {
                idAlmacenNew = em.getReference(idAlmacenNew.getClass(), idAlmacenNew.getId());
                bodega.setIdAlmacen(idAlmacenNew);
            }
            bodega = em.merge(bodega);
            if (idAlmacenOld != null && !idAlmacenOld.equals(idAlmacenNew)) {
                idAlmacenOld.getBodegaList().remove(bodega);
                idAlmacenOld = em.merge(idAlmacenOld);
            }
            if (idAlmacenNew != null && !idAlmacenNew.equals(idAlmacenOld)) {
                idAlmacenNew.getBodegaList().add(bodega);
                idAlmacenNew = em.merge(idAlmacenNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bodega.getId();
                if (findBodega(id) == null) {
                    throw new NonexistentEntityException("The bodega with id " + id + " no longer exists.");
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
            Bodega bodega;
            try {
                bodega = em.getReference(Bodega.class, id);
                bodega.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bodega with id " + id + " no longer exists.", enfe);
            }
            Almacen idAlmacen = bodega.getIdAlmacen();
            if (idAlmacen != null) {
                idAlmacen.getBodegaList().remove(bodega);
                idAlmacen = em.merge(idAlmacen);
            }
            em.remove(bodega);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bodega> findBodegaEntities() {
        return findBodegaEntities(true, -1, -1);
    }

    public List<Bodega> findBodegaEntities(int maxResults, int firstResult) {
        return findBodegaEntities(false, maxResults, firstResult);
    }

    private List<Bodega> findBodegaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bodega.class));
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

    public Bodega findBodega(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bodega.class, id);
        } finally {
            em.close();
        }
    }

    public int getBodegaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bodega> rt = cq.from(Bodega.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
