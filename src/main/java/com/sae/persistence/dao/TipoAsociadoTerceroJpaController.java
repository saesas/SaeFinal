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
import com.sae.persistence.domain.TipoTercero;
import com.sae.persistence.domain.Tercero;
import com.sae.persistence.domain.TipoAsociadoTercero;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoAsociadoTerceroJpaController implements Serializable {

    public TipoAsociadoTerceroJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoAsociadoTercero tipoAsociadoTercero) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTercero idTipo = tipoAsociadoTercero.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getId());
                tipoAsociadoTercero.setIdTipo(idTipo);
            }
            Tercero idTercero = tipoAsociadoTercero.getIdTercero();
            if (idTercero != null) {
                idTercero = em.getReference(idTercero.getClass(), idTercero.getId());
                tipoAsociadoTercero.setIdTercero(idTercero);
            }
            em.persist(tipoAsociadoTercero);
            if (idTipo != null) {
                idTipo.getTipoAsociadoTerceroList().add(tipoAsociadoTercero);
                idTipo = em.merge(idTipo);
            }
            if (idTercero != null) {
                idTercero.getTipoAsociadoTerceroList().add(tipoAsociadoTercero);
                idTercero = em.merge(idTercero);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoAsociadoTercero(tipoAsociadoTercero.getId()) != null) {
                throw new PreexistingEntityException("TipoAsociadoTercero " + tipoAsociadoTercero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoAsociadoTercero tipoAsociadoTercero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoAsociadoTercero persistentTipoAsociadoTercero = em.find(TipoAsociadoTercero.class, tipoAsociadoTercero.getId());
            TipoTercero idTipoOld = persistentTipoAsociadoTercero.getIdTipo();
            TipoTercero idTipoNew = tipoAsociadoTercero.getIdTipo();
            Tercero idTerceroOld = persistentTipoAsociadoTercero.getIdTercero();
            Tercero idTerceroNew = tipoAsociadoTercero.getIdTercero();
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getId());
                tipoAsociadoTercero.setIdTipo(idTipoNew);
            }
            if (idTerceroNew != null) {
                idTerceroNew = em.getReference(idTerceroNew.getClass(), idTerceroNew.getId());
                tipoAsociadoTercero.setIdTercero(idTerceroNew);
            }
            tipoAsociadoTercero = em.merge(tipoAsociadoTercero);
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getTipoAsociadoTerceroList().remove(tipoAsociadoTercero);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getTipoAsociadoTerceroList().add(tipoAsociadoTercero);
                idTipoNew = em.merge(idTipoNew);
            }
            if (idTerceroOld != null && !idTerceroOld.equals(idTerceroNew)) {
                idTerceroOld.getTipoAsociadoTerceroList().remove(tipoAsociadoTercero);
                idTerceroOld = em.merge(idTerceroOld);
            }
            if (idTerceroNew != null && !idTerceroNew.equals(idTerceroOld)) {
                idTerceroNew.getTipoAsociadoTerceroList().add(tipoAsociadoTercero);
                idTerceroNew = em.merge(idTerceroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoAsociadoTercero.getId();
                if (findTipoAsociadoTercero(id) == null) {
                    throw new NonexistentEntityException("The tipoAsociadoTercero with id " + id + " no longer exists.");
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
            TipoAsociadoTercero tipoAsociadoTercero;
            try {
                tipoAsociadoTercero = em.getReference(TipoAsociadoTercero.class, id);
                tipoAsociadoTercero.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoAsociadoTercero with id " + id + " no longer exists.", enfe);
            }
            TipoTercero idTipo = tipoAsociadoTercero.getIdTipo();
            if (idTipo != null) {
                idTipo.getTipoAsociadoTerceroList().remove(tipoAsociadoTercero);
                idTipo = em.merge(idTipo);
            }
            Tercero idTercero = tipoAsociadoTercero.getIdTercero();
            if (idTercero != null) {
                idTercero.getTipoAsociadoTerceroList().remove(tipoAsociadoTercero);
                idTercero = em.merge(idTercero);
            }
            em.remove(tipoAsociadoTercero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoAsociadoTercero> findTipoAsociadoTerceroEntities() {
        return findTipoAsociadoTerceroEntities(true, -1, -1);
    }

    public List<TipoAsociadoTercero> findTipoAsociadoTerceroEntities(int maxResults, int firstResult) {
        return findTipoAsociadoTerceroEntities(false, maxResults, firstResult);
    }

    private List<TipoAsociadoTercero> findTipoAsociadoTerceroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoAsociadoTercero.class));
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

    public TipoAsociadoTercero findTipoAsociadoTercero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoAsociadoTercero.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoAsociadoTerceroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoAsociadoTercero> rt = cq.from(TipoAsociadoTercero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
