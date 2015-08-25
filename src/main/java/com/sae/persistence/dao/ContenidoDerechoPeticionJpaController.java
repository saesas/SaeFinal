/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.ContenidoDerechoPeticion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.TipoContenidoDerechoPeticion;
import com.sae.persistence.domain.DerechoPeticion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ContenidoDerechoPeticionJpaController implements Serializable {

    public ContenidoDerechoPeticionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContenidoDerechoPeticion contenidoDerechoPeticion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoContenidoDerechoPeticion idTipoContenido = contenidoDerechoPeticion.getIdTipoContenido();
            if (idTipoContenido != null) {
                idTipoContenido = em.getReference(idTipoContenido.getClass(), idTipoContenido.getId());
                contenidoDerechoPeticion.setIdTipoContenido(idTipoContenido);
            }
            DerechoPeticion idDerecho = contenidoDerechoPeticion.getIdDerecho();
            if (idDerecho != null) {
                idDerecho = em.getReference(idDerecho.getClass(), idDerecho.getId());
                contenidoDerechoPeticion.setIdDerecho(idDerecho);
            }
            em.persist(contenidoDerechoPeticion);
            if (idTipoContenido != null) {
                idTipoContenido.getContenidoDerechoPeticionList().add(contenidoDerechoPeticion);
                idTipoContenido = em.merge(idTipoContenido);
            }
            if (idDerecho != null) {
                idDerecho.getContenidoDerechoPeticionList().add(contenidoDerechoPeticion);
                idDerecho = em.merge(idDerecho);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findContenidoDerechoPeticion(contenidoDerechoPeticion.getId()) != null) {
                throw new PreexistingEntityException("ContenidoDerechoPeticion " + contenidoDerechoPeticion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContenidoDerechoPeticion contenidoDerechoPeticion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContenidoDerechoPeticion persistentContenidoDerechoPeticion = em.find(ContenidoDerechoPeticion.class, contenidoDerechoPeticion.getId());
            TipoContenidoDerechoPeticion idTipoContenidoOld = persistentContenidoDerechoPeticion.getIdTipoContenido();
            TipoContenidoDerechoPeticion idTipoContenidoNew = contenidoDerechoPeticion.getIdTipoContenido();
            DerechoPeticion idDerechoOld = persistentContenidoDerechoPeticion.getIdDerecho();
            DerechoPeticion idDerechoNew = contenidoDerechoPeticion.getIdDerecho();
            if (idTipoContenidoNew != null) {
                idTipoContenidoNew = em.getReference(idTipoContenidoNew.getClass(), idTipoContenidoNew.getId());
                contenidoDerechoPeticion.setIdTipoContenido(idTipoContenidoNew);
            }
            if (idDerechoNew != null) {
                idDerechoNew = em.getReference(idDerechoNew.getClass(), idDerechoNew.getId());
                contenidoDerechoPeticion.setIdDerecho(idDerechoNew);
            }
            contenidoDerechoPeticion = em.merge(contenidoDerechoPeticion);
            if (idTipoContenidoOld != null && !idTipoContenidoOld.equals(idTipoContenidoNew)) {
                idTipoContenidoOld.getContenidoDerechoPeticionList().remove(contenidoDerechoPeticion);
                idTipoContenidoOld = em.merge(idTipoContenidoOld);
            }
            if (idTipoContenidoNew != null && !idTipoContenidoNew.equals(idTipoContenidoOld)) {
                idTipoContenidoNew.getContenidoDerechoPeticionList().add(contenidoDerechoPeticion);
                idTipoContenidoNew = em.merge(idTipoContenidoNew);
            }
            if (idDerechoOld != null && !idDerechoOld.equals(idDerechoNew)) {
                idDerechoOld.getContenidoDerechoPeticionList().remove(contenidoDerechoPeticion);
                idDerechoOld = em.merge(idDerechoOld);
            }
            if (idDerechoNew != null && !idDerechoNew.equals(idDerechoOld)) {
                idDerechoNew.getContenidoDerechoPeticionList().add(contenidoDerechoPeticion);
                idDerechoNew = em.merge(idDerechoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contenidoDerechoPeticion.getId();
                if (findContenidoDerechoPeticion(id) == null) {
                    throw new NonexistentEntityException("The contenidoDerechoPeticion with id " + id + " no longer exists.");
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
            ContenidoDerechoPeticion contenidoDerechoPeticion;
            try {
                contenidoDerechoPeticion = em.getReference(ContenidoDerechoPeticion.class, id);
                contenidoDerechoPeticion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contenidoDerechoPeticion with id " + id + " no longer exists.", enfe);
            }
            TipoContenidoDerechoPeticion idTipoContenido = contenidoDerechoPeticion.getIdTipoContenido();
            if (idTipoContenido != null) {
                idTipoContenido.getContenidoDerechoPeticionList().remove(contenidoDerechoPeticion);
                idTipoContenido = em.merge(idTipoContenido);
            }
            DerechoPeticion idDerecho = contenidoDerechoPeticion.getIdDerecho();
            if (idDerecho != null) {
                idDerecho.getContenidoDerechoPeticionList().remove(contenidoDerechoPeticion);
                idDerecho = em.merge(idDerecho);
            }
            em.remove(contenidoDerechoPeticion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContenidoDerechoPeticion> findContenidoDerechoPeticionEntities() {
        return findContenidoDerechoPeticionEntities(true, -1, -1);
    }

    public List<ContenidoDerechoPeticion> findContenidoDerechoPeticionEntities(int maxResults, int firstResult) {
        return findContenidoDerechoPeticionEntities(false, maxResults, firstResult);
    }

    private List<ContenidoDerechoPeticion> findContenidoDerechoPeticionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContenidoDerechoPeticion.class));
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

    public ContenidoDerechoPeticion findContenidoDerechoPeticion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContenidoDerechoPeticion.class, id);
        } finally {
            em.close();
        }
    }

    public int getContenidoDerechoPeticionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContenidoDerechoPeticion> rt = cq.from(ContenidoDerechoPeticion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
