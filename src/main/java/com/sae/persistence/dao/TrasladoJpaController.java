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
import com.sae.persistence.domain.TipoTraslado;
import com.sae.persistence.domain.Traslado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TrasladoJpaController implements Serializable {

    public TrasladoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Traslado traslado) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTraslado idTipo = traslado.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getId());
                traslado.setIdTipo(idTipo);
            }
            em.persist(traslado);
            if (idTipo != null) {
                idTipo.getTrasladoList().add(traslado);
                idTipo = em.merge(idTipo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTraslado(traslado.getId()) != null) {
                throw new PreexistingEntityException("Traslado " + traslado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Traslado traslado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Traslado persistentTraslado = em.find(Traslado.class, traslado.getId());
            TipoTraslado idTipoOld = persistentTraslado.getIdTipo();
            TipoTraslado idTipoNew = traslado.getIdTipo();
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getId());
                traslado.setIdTipo(idTipoNew);
            }
            traslado = em.merge(traslado);
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getTrasladoList().remove(traslado);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getTrasladoList().add(traslado);
                idTipoNew = em.merge(idTipoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = traslado.getId();
                if (findTraslado(id) == null) {
                    throw new NonexistentEntityException("The traslado with id " + id + " no longer exists.");
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
            Traslado traslado;
            try {
                traslado = em.getReference(Traslado.class, id);
                traslado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The traslado with id " + id + " no longer exists.", enfe);
            }
            TipoTraslado idTipo = traslado.getIdTipo();
            if (idTipo != null) {
                idTipo.getTrasladoList().remove(traslado);
                idTipo = em.merge(idTipo);
            }
            em.remove(traslado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Traslado> findTrasladoEntities() {
        return findTrasladoEntities(true, -1, -1);
    }

    public List<Traslado> findTrasladoEntities(int maxResults, int firstResult) {
        return findTrasladoEntities(false, maxResults, firstResult);
    }

    private List<Traslado> findTrasladoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Traslado.class));
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

    public Traslado findTraslado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Traslado.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrasladoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Traslado> rt = cq.from(Traslado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
