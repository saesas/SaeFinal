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
import com.sae.persistence.domain.TipoEstadoConvocatoria;
import com.sae.persistence.domain.Convocatoria;
import com.sae.persistence.domain.EstadoConvocatoria;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class EstadoConvocatoriaJpaController implements Serializable {

    public EstadoConvocatoriaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoConvocatoria estadoConvocatoria) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEstadoConvocatoria idEstadoConvocatoria = estadoConvocatoria.getIdEstadoConvocatoria();
            if (idEstadoConvocatoria != null) {
                idEstadoConvocatoria = em.getReference(idEstadoConvocatoria.getClass(), idEstadoConvocatoria.getId());
                estadoConvocatoria.setIdEstadoConvocatoria(idEstadoConvocatoria);
            }
            Convocatoria idConvocatoria = estadoConvocatoria.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria = em.getReference(idConvocatoria.getClass(), idConvocatoria.getId());
                estadoConvocatoria.setIdConvocatoria(idConvocatoria);
            }
            em.persist(estadoConvocatoria);
            if (idEstadoConvocatoria != null) {
                idEstadoConvocatoria.getEstadoConvocatoriaList().add(estadoConvocatoria);
                idEstadoConvocatoria = em.merge(idEstadoConvocatoria);
            }
            if (idConvocatoria != null) {
                idConvocatoria.getEstadoConvocatoriaList().add(estadoConvocatoria);
                idConvocatoria = em.merge(idConvocatoria);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadoConvocatoria(estadoConvocatoria.getId()) != null) {
                throw new PreexistingEntityException("EstadoConvocatoria " + estadoConvocatoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoConvocatoria estadoConvocatoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoConvocatoria persistentEstadoConvocatoria = em.find(EstadoConvocatoria.class, estadoConvocatoria.getId());
            TipoEstadoConvocatoria idEstadoConvocatoriaOld = persistentEstadoConvocatoria.getIdEstadoConvocatoria();
            TipoEstadoConvocatoria idEstadoConvocatoriaNew = estadoConvocatoria.getIdEstadoConvocatoria();
            Convocatoria idConvocatoriaOld = persistentEstadoConvocatoria.getIdConvocatoria();
            Convocatoria idConvocatoriaNew = estadoConvocatoria.getIdConvocatoria();
            if (idEstadoConvocatoriaNew != null) {
                idEstadoConvocatoriaNew = em.getReference(idEstadoConvocatoriaNew.getClass(), idEstadoConvocatoriaNew.getId());
                estadoConvocatoria.setIdEstadoConvocatoria(idEstadoConvocatoriaNew);
            }
            if (idConvocatoriaNew != null) {
                idConvocatoriaNew = em.getReference(idConvocatoriaNew.getClass(), idConvocatoriaNew.getId());
                estadoConvocatoria.setIdConvocatoria(idConvocatoriaNew);
            }
            estadoConvocatoria = em.merge(estadoConvocatoria);
            if (idEstadoConvocatoriaOld != null && !idEstadoConvocatoriaOld.equals(idEstadoConvocatoriaNew)) {
                idEstadoConvocatoriaOld.getEstadoConvocatoriaList().remove(estadoConvocatoria);
                idEstadoConvocatoriaOld = em.merge(idEstadoConvocatoriaOld);
            }
            if (idEstadoConvocatoriaNew != null && !idEstadoConvocatoriaNew.equals(idEstadoConvocatoriaOld)) {
                idEstadoConvocatoriaNew.getEstadoConvocatoriaList().add(estadoConvocatoria);
                idEstadoConvocatoriaNew = em.merge(idEstadoConvocatoriaNew);
            }
            if (idConvocatoriaOld != null && !idConvocatoriaOld.equals(idConvocatoriaNew)) {
                idConvocatoriaOld.getEstadoConvocatoriaList().remove(estadoConvocatoria);
                idConvocatoriaOld = em.merge(idConvocatoriaOld);
            }
            if (idConvocatoriaNew != null && !idConvocatoriaNew.equals(idConvocatoriaOld)) {
                idConvocatoriaNew.getEstadoConvocatoriaList().add(estadoConvocatoria);
                idConvocatoriaNew = em.merge(idConvocatoriaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estadoConvocatoria.getId();
                if (findEstadoConvocatoria(id) == null) {
                    throw new NonexistentEntityException("The estadoConvocatoria with id " + id + " no longer exists.");
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
            EstadoConvocatoria estadoConvocatoria;
            try {
                estadoConvocatoria = em.getReference(EstadoConvocatoria.class, id);
                estadoConvocatoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoConvocatoria with id " + id + " no longer exists.", enfe);
            }
            TipoEstadoConvocatoria idEstadoConvocatoria = estadoConvocatoria.getIdEstadoConvocatoria();
            if (idEstadoConvocatoria != null) {
                idEstadoConvocatoria.getEstadoConvocatoriaList().remove(estadoConvocatoria);
                idEstadoConvocatoria = em.merge(idEstadoConvocatoria);
            }
            Convocatoria idConvocatoria = estadoConvocatoria.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria.getEstadoConvocatoriaList().remove(estadoConvocatoria);
                idConvocatoria = em.merge(idConvocatoria);
            }
            em.remove(estadoConvocatoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoConvocatoria> findEstadoConvocatoriaEntities() {
        return findEstadoConvocatoriaEntities(true, -1, -1);
    }

    public List<EstadoConvocatoria> findEstadoConvocatoriaEntities(int maxResults, int firstResult) {
        return findEstadoConvocatoriaEntities(false, maxResults, firstResult);
    }

    private List<EstadoConvocatoria> findEstadoConvocatoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoConvocatoria.class));
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

    public EstadoConvocatoria findEstadoConvocatoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoConvocatoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoConvocatoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoConvocatoria> rt = cq.from(EstadoConvocatoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
