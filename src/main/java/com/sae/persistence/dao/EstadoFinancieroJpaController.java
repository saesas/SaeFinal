/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.EstadoFinanciero;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.EstadoFinancieroTercero;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class EstadoFinancieroJpaController implements Serializable {

    public EstadoFinancieroJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoFinanciero estadoFinanciero) throws PreexistingEntityException, Exception {
        if (estadoFinanciero.getEstadoFinancieroTerceroList() == null) {
            estadoFinanciero.setEstadoFinancieroTerceroList(new ArrayList<EstadoFinancieroTercero>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<EstadoFinancieroTercero> attachedEstadoFinancieroTerceroList = new ArrayList<EstadoFinancieroTercero>();
            for (EstadoFinancieroTercero estadoFinancieroTerceroListEstadoFinancieroTerceroToAttach : estadoFinanciero.getEstadoFinancieroTerceroList()) {
                estadoFinancieroTerceroListEstadoFinancieroTerceroToAttach = em.getReference(estadoFinancieroTerceroListEstadoFinancieroTerceroToAttach.getClass(), estadoFinancieroTerceroListEstadoFinancieroTerceroToAttach.getId());
                attachedEstadoFinancieroTerceroList.add(estadoFinancieroTerceroListEstadoFinancieroTerceroToAttach);
            }
            estadoFinanciero.setEstadoFinancieroTerceroList(attachedEstadoFinancieroTerceroList);
            em.persist(estadoFinanciero);
            for (EstadoFinancieroTercero estadoFinancieroTerceroListEstadoFinancieroTercero : estadoFinanciero.getEstadoFinancieroTerceroList()) {
                EstadoFinanciero oldIdTipoOfEstadoFinancieroTerceroListEstadoFinancieroTercero = estadoFinancieroTerceroListEstadoFinancieroTercero.getIdTipo();
                estadoFinancieroTerceroListEstadoFinancieroTercero.setIdTipo(estadoFinanciero);
                estadoFinancieroTerceroListEstadoFinancieroTercero = em.merge(estadoFinancieroTerceroListEstadoFinancieroTercero);
                if (oldIdTipoOfEstadoFinancieroTerceroListEstadoFinancieroTercero != null) {
                    oldIdTipoOfEstadoFinancieroTerceroListEstadoFinancieroTercero.getEstadoFinancieroTerceroList().remove(estadoFinancieroTerceroListEstadoFinancieroTercero);
                    oldIdTipoOfEstadoFinancieroTerceroListEstadoFinancieroTercero = em.merge(oldIdTipoOfEstadoFinancieroTerceroListEstadoFinancieroTercero);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadoFinanciero(estadoFinanciero.getId()) != null) {
                throw new PreexistingEntityException("EstadoFinanciero " + estadoFinanciero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoFinanciero estadoFinanciero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoFinanciero persistentEstadoFinanciero = em.find(EstadoFinanciero.class, estadoFinanciero.getId());
            List<EstadoFinancieroTercero> estadoFinancieroTerceroListOld = persistentEstadoFinanciero.getEstadoFinancieroTerceroList();
            List<EstadoFinancieroTercero> estadoFinancieroTerceroListNew = estadoFinanciero.getEstadoFinancieroTerceroList();
            List<EstadoFinancieroTercero> attachedEstadoFinancieroTerceroListNew = new ArrayList<EstadoFinancieroTercero>();
            for (EstadoFinancieroTercero estadoFinancieroTerceroListNewEstadoFinancieroTerceroToAttach : estadoFinancieroTerceroListNew) {
                estadoFinancieroTerceroListNewEstadoFinancieroTerceroToAttach = em.getReference(estadoFinancieroTerceroListNewEstadoFinancieroTerceroToAttach.getClass(), estadoFinancieroTerceroListNewEstadoFinancieroTerceroToAttach.getId());
                attachedEstadoFinancieroTerceroListNew.add(estadoFinancieroTerceroListNewEstadoFinancieroTerceroToAttach);
            }
            estadoFinancieroTerceroListNew = attachedEstadoFinancieroTerceroListNew;
            estadoFinanciero.setEstadoFinancieroTerceroList(estadoFinancieroTerceroListNew);
            estadoFinanciero = em.merge(estadoFinanciero);
            for (EstadoFinancieroTercero estadoFinancieroTerceroListOldEstadoFinancieroTercero : estadoFinancieroTerceroListOld) {
                if (!estadoFinancieroTerceroListNew.contains(estadoFinancieroTerceroListOldEstadoFinancieroTercero)) {
                    estadoFinancieroTerceroListOldEstadoFinancieroTercero.setIdTipo(null);
                    estadoFinancieroTerceroListOldEstadoFinancieroTercero = em.merge(estadoFinancieroTerceroListOldEstadoFinancieroTercero);
                }
            }
            for (EstadoFinancieroTercero estadoFinancieroTerceroListNewEstadoFinancieroTercero : estadoFinancieroTerceroListNew) {
                if (!estadoFinancieroTerceroListOld.contains(estadoFinancieroTerceroListNewEstadoFinancieroTercero)) {
                    EstadoFinanciero oldIdTipoOfEstadoFinancieroTerceroListNewEstadoFinancieroTercero = estadoFinancieroTerceroListNewEstadoFinancieroTercero.getIdTipo();
                    estadoFinancieroTerceroListNewEstadoFinancieroTercero.setIdTipo(estadoFinanciero);
                    estadoFinancieroTerceroListNewEstadoFinancieroTercero = em.merge(estadoFinancieroTerceroListNewEstadoFinancieroTercero);
                    if (oldIdTipoOfEstadoFinancieroTerceroListNewEstadoFinancieroTercero != null && !oldIdTipoOfEstadoFinancieroTerceroListNewEstadoFinancieroTercero.equals(estadoFinanciero)) {
                        oldIdTipoOfEstadoFinancieroTerceroListNewEstadoFinancieroTercero.getEstadoFinancieroTerceroList().remove(estadoFinancieroTerceroListNewEstadoFinancieroTercero);
                        oldIdTipoOfEstadoFinancieroTerceroListNewEstadoFinancieroTercero = em.merge(oldIdTipoOfEstadoFinancieroTerceroListNewEstadoFinancieroTercero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estadoFinanciero.getId();
                if (findEstadoFinanciero(id) == null) {
                    throw new NonexistentEntityException("The estadoFinanciero with id " + id + " no longer exists.");
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
            EstadoFinanciero estadoFinanciero;
            try {
                estadoFinanciero = em.getReference(EstadoFinanciero.class, id);
                estadoFinanciero.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoFinanciero with id " + id + " no longer exists.", enfe);
            }
            List<EstadoFinancieroTercero> estadoFinancieroTerceroList = estadoFinanciero.getEstadoFinancieroTerceroList();
            for (EstadoFinancieroTercero estadoFinancieroTerceroListEstadoFinancieroTercero : estadoFinancieroTerceroList) {
                estadoFinancieroTerceroListEstadoFinancieroTercero.setIdTipo(null);
                estadoFinancieroTerceroListEstadoFinancieroTercero = em.merge(estadoFinancieroTerceroListEstadoFinancieroTercero);
            }
            em.remove(estadoFinanciero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoFinanciero> findEstadoFinancieroEntities() {
        return findEstadoFinancieroEntities(true, -1, -1);
    }

    public List<EstadoFinanciero> findEstadoFinancieroEntities(int maxResults, int firstResult) {
        return findEstadoFinancieroEntities(false, maxResults, firstResult);
    }

    private List<EstadoFinanciero> findEstadoFinancieroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoFinanciero.class));
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

    public EstadoFinanciero findEstadoFinanciero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoFinanciero.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoFinancieroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoFinanciero> rt = cq.from(EstadoFinanciero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
