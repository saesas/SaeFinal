/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.IdentificacionTercero;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.TipoDocumento;
import com.sae.persistence.domain.Tercero;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class IdentificacionTerceroJpaController implements Serializable {

    public IdentificacionTerceroJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IdentificacionTercero identificacionTercero) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoDocumento idTipoIdentificacion = identificacionTercero.getIdTipoIdentificacion();
            if (idTipoIdentificacion != null) {
                idTipoIdentificacion = em.getReference(idTipoIdentificacion.getClass(), idTipoIdentificacion.getId());
                identificacionTercero.setIdTipoIdentificacion(idTipoIdentificacion);
            }
            Tercero idTercero = identificacionTercero.getIdTercero();
            if (idTercero != null) {
                idTercero = em.getReference(idTercero.getClass(), idTercero.getId());
                identificacionTercero.setIdTercero(idTercero);
            }
            em.persist(identificacionTercero);
            if (idTipoIdentificacion != null) {
                idTipoIdentificacion.getIdentificacionTerceroList().add(identificacionTercero);
                idTipoIdentificacion = em.merge(idTipoIdentificacion);
            }
            if (idTercero != null) {
                idTercero.getIdentificacionTerceroList().add(identificacionTercero);
                idTercero = em.merge(idTercero);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIdentificacionTercero(identificacionTercero.getId()) != null) {
                throw new PreexistingEntityException("IdentificacionTercero " + identificacionTercero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IdentificacionTercero identificacionTercero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IdentificacionTercero persistentIdentificacionTercero = em.find(IdentificacionTercero.class, identificacionTercero.getId());
            TipoDocumento idTipoIdentificacionOld = persistentIdentificacionTercero.getIdTipoIdentificacion();
            TipoDocumento idTipoIdentificacionNew = identificacionTercero.getIdTipoIdentificacion();
            Tercero idTerceroOld = persistentIdentificacionTercero.getIdTercero();
            Tercero idTerceroNew = identificacionTercero.getIdTercero();
            if (idTipoIdentificacionNew != null) {
                idTipoIdentificacionNew = em.getReference(idTipoIdentificacionNew.getClass(), idTipoIdentificacionNew.getId());
                identificacionTercero.setIdTipoIdentificacion(idTipoIdentificacionNew);
            }
            if (idTerceroNew != null) {
                idTerceroNew = em.getReference(idTerceroNew.getClass(), idTerceroNew.getId());
                identificacionTercero.setIdTercero(idTerceroNew);
            }
            identificacionTercero = em.merge(identificacionTercero);
            if (idTipoIdentificacionOld != null && !idTipoIdentificacionOld.equals(idTipoIdentificacionNew)) {
                idTipoIdentificacionOld.getIdentificacionTerceroList().remove(identificacionTercero);
                idTipoIdentificacionOld = em.merge(idTipoIdentificacionOld);
            }
            if (idTipoIdentificacionNew != null && !idTipoIdentificacionNew.equals(idTipoIdentificacionOld)) {
                idTipoIdentificacionNew.getIdentificacionTerceroList().add(identificacionTercero);
                idTipoIdentificacionNew = em.merge(idTipoIdentificacionNew);
            }
            if (idTerceroOld != null && !idTerceroOld.equals(idTerceroNew)) {
                idTerceroOld.getIdentificacionTerceroList().remove(identificacionTercero);
                idTerceroOld = em.merge(idTerceroOld);
            }
            if (idTerceroNew != null && !idTerceroNew.equals(idTerceroOld)) {
                idTerceroNew.getIdentificacionTerceroList().add(identificacionTercero);
                idTerceroNew = em.merge(idTerceroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = identificacionTercero.getId();
                if (findIdentificacionTercero(id) == null) {
                    throw new NonexistentEntityException("The identificacionTercero with id " + id + " no longer exists.");
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
            IdentificacionTercero identificacionTercero;
            try {
                identificacionTercero = em.getReference(IdentificacionTercero.class, id);
                identificacionTercero.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The identificacionTercero with id " + id + " no longer exists.", enfe);
            }
            TipoDocumento idTipoIdentificacion = identificacionTercero.getIdTipoIdentificacion();
            if (idTipoIdentificacion != null) {
                idTipoIdentificacion.getIdentificacionTerceroList().remove(identificacionTercero);
                idTipoIdentificacion = em.merge(idTipoIdentificacion);
            }
            Tercero idTercero = identificacionTercero.getIdTercero();
            if (idTercero != null) {
                idTercero.getIdentificacionTerceroList().remove(identificacionTercero);
                idTercero = em.merge(idTercero);
            }
            em.remove(identificacionTercero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IdentificacionTercero> findIdentificacionTerceroEntities() {
        return findIdentificacionTerceroEntities(true, -1, -1);
    }

    public List<IdentificacionTercero> findIdentificacionTerceroEntities(int maxResults, int firstResult) {
        return findIdentificacionTerceroEntities(false, maxResults, firstResult);
    }

    private List<IdentificacionTercero> findIdentificacionTerceroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IdentificacionTercero.class));
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

    public IdentificacionTercero findIdentificacionTercero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IdentificacionTercero.class, id);
        } finally {
            em.close();
        }
    }

    public int getIdentificacionTerceroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IdentificacionTercero> rt = cq.from(IdentificacionTercero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
