/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.ListaPrecio;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Presupuesto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ListaPrecioJpaController implements Serializable {

    public ListaPrecioJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ListaPrecio listaPrecio) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Presupuesto idPresupuesto = listaPrecio.getIdPresupuesto();
            if (idPresupuesto != null) {
                idPresupuesto = em.getReference(idPresupuesto.getClass(), idPresupuesto.getId());
                listaPrecio.setIdPresupuesto(idPresupuesto);
            }
            em.persist(listaPrecio);
            if (idPresupuesto != null) {
                idPresupuesto.getListaPrecioList().add(listaPrecio);
                idPresupuesto = em.merge(idPresupuesto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findListaPrecio(listaPrecio.getId()) != null) {
                throw new PreexistingEntityException("ListaPrecio " + listaPrecio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ListaPrecio listaPrecio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ListaPrecio persistentListaPrecio = em.find(ListaPrecio.class, listaPrecio.getId());
            Presupuesto idPresupuestoOld = persistentListaPrecio.getIdPresupuesto();
            Presupuesto idPresupuestoNew = listaPrecio.getIdPresupuesto();
            if (idPresupuestoNew != null) {
                idPresupuestoNew = em.getReference(idPresupuestoNew.getClass(), idPresupuestoNew.getId());
                listaPrecio.setIdPresupuesto(idPresupuestoNew);
            }
            listaPrecio = em.merge(listaPrecio);
            if (idPresupuestoOld != null && !idPresupuestoOld.equals(idPresupuestoNew)) {
                idPresupuestoOld.getListaPrecioList().remove(listaPrecio);
                idPresupuestoOld = em.merge(idPresupuestoOld);
            }
            if (idPresupuestoNew != null && !idPresupuestoNew.equals(idPresupuestoOld)) {
                idPresupuestoNew.getListaPrecioList().add(listaPrecio);
                idPresupuestoNew = em.merge(idPresupuestoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = listaPrecio.getId();
                if (findListaPrecio(id) == null) {
                    throw new NonexistentEntityException("The listaPrecio with id " + id + " no longer exists.");
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
            ListaPrecio listaPrecio;
            try {
                listaPrecio = em.getReference(ListaPrecio.class, id);
                listaPrecio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The listaPrecio with id " + id + " no longer exists.", enfe);
            }
            Presupuesto idPresupuesto = listaPrecio.getIdPresupuesto();
            if (idPresupuesto != null) {
                idPresupuesto.getListaPrecioList().remove(listaPrecio);
                idPresupuesto = em.merge(idPresupuesto);
            }
            em.remove(listaPrecio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ListaPrecio> findListaPrecioEntities() {
        return findListaPrecioEntities(true, -1, -1);
    }

    public List<ListaPrecio> findListaPrecioEntities(int maxResults, int firstResult) {
        return findListaPrecioEntities(false, maxResults, firstResult);
    }

    private List<ListaPrecio> findListaPrecioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ListaPrecio.class));
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

    public ListaPrecio findListaPrecio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ListaPrecio.class, id);
        } finally {
            em.close();
        }
    }

    public int getListaPrecioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ListaPrecio> rt = cq.from(ListaPrecio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
