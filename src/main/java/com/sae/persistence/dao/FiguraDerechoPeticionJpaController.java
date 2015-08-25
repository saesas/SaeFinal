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
import com.sae.persistence.domain.Figura;
import com.sae.persistence.domain.DerechoPeticion;
import com.sae.persistence.domain.FiguraDerechoPeticion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class FiguraDerechoPeticionJpaController implements Serializable {

    public FiguraDerechoPeticionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FiguraDerechoPeticion figuraDerechoPeticion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Figura idFigura = figuraDerechoPeticion.getIdFigura();
            if (idFigura != null) {
                idFigura = em.getReference(idFigura.getClass(), idFigura.getId());
                figuraDerechoPeticion.setIdFigura(idFigura);
            }
            DerechoPeticion idDerechoPeticion = figuraDerechoPeticion.getIdDerechoPeticion();
            if (idDerechoPeticion != null) {
                idDerechoPeticion = em.getReference(idDerechoPeticion.getClass(), idDerechoPeticion.getId());
                figuraDerechoPeticion.setIdDerechoPeticion(idDerechoPeticion);
            }
            em.persist(figuraDerechoPeticion);
            if (idFigura != null) {
                idFigura.getFiguraDerechoPeticionList().add(figuraDerechoPeticion);
                idFigura = em.merge(idFigura);
            }
            if (idDerechoPeticion != null) {
                idDerechoPeticion.getFiguraDerechoPeticionList().add(figuraDerechoPeticion);
                idDerechoPeticion = em.merge(idDerechoPeticion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFiguraDerechoPeticion(figuraDerechoPeticion.getId()) != null) {
                throw new PreexistingEntityException("FiguraDerechoPeticion " + figuraDerechoPeticion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FiguraDerechoPeticion figuraDerechoPeticion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FiguraDerechoPeticion persistentFiguraDerechoPeticion = em.find(FiguraDerechoPeticion.class, figuraDerechoPeticion.getId());
            Figura idFiguraOld = persistentFiguraDerechoPeticion.getIdFigura();
            Figura idFiguraNew = figuraDerechoPeticion.getIdFigura();
            DerechoPeticion idDerechoPeticionOld = persistentFiguraDerechoPeticion.getIdDerechoPeticion();
            DerechoPeticion idDerechoPeticionNew = figuraDerechoPeticion.getIdDerechoPeticion();
            if (idFiguraNew != null) {
                idFiguraNew = em.getReference(idFiguraNew.getClass(), idFiguraNew.getId());
                figuraDerechoPeticion.setIdFigura(idFiguraNew);
            }
            if (idDerechoPeticionNew != null) {
                idDerechoPeticionNew = em.getReference(idDerechoPeticionNew.getClass(), idDerechoPeticionNew.getId());
                figuraDerechoPeticion.setIdDerechoPeticion(idDerechoPeticionNew);
            }
            figuraDerechoPeticion = em.merge(figuraDerechoPeticion);
            if (idFiguraOld != null && !idFiguraOld.equals(idFiguraNew)) {
                idFiguraOld.getFiguraDerechoPeticionList().remove(figuraDerechoPeticion);
                idFiguraOld = em.merge(idFiguraOld);
            }
            if (idFiguraNew != null && !idFiguraNew.equals(idFiguraOld)) {
                idFiguraNew.getFiguraDerechoPeticionList().add(figuraDerechoPeticion);
                idFiguraNew = em.merge(idFiguraNew);
            }
            if (idDerechoPeticionOld != null && !idDerechoPeticionOld.equals(idDerechoPeticionNew)) {
                idDerechoPeticionOld.getFiguraDerechoPeticionList().remove(figuraDerechoPeticion);
                idDerechoPeticionOld = em.merge(idDerechoPeticionOld);
            }
            if (idDerechoPeticionNew != null && !idDerechoPeticionNew.equals(idDerechoPeticionOld)) {
                idDerechoPeticionNew.getFiguraDerechoPeticionList().add(figuraDerechoPeticion);
                idDerechoPeticionNew = em.merge(idDerechoPeticionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = figuraDerechoPeticion.getId();
                if (findFiguraDerechoPeticion(id) == null) {
                    throw new NonexistentEntityException("The figuraDerechoPeticion with id " + id + " no longer exists.");
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
            FiguraDerechoPeticion figuraDerechoPeticion;
            try {
                figuraDerechoPeticion = em.getReference(FiguraDerechoPeticion.class, id);
                figuraDerechoPeticion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The figuraDerechoPeticion with id " + id + " no longer exists.", enfe);
            }
            Figura idFigura = figuraDerechoPeticion.getIdFigura();
            if (idFigura != null) {
                idFigura.getFiguraDerechoPeticionList().remove(figuraDerechoPeticion);
                idFigura = em.merge(idFigura);
            }
            DerechoPeticion idDerechoPeticion = figuraDerechoPeticion.getIdDerechoPeticion();
            if (idDerechoPeticion != null) {
                idDerechoPeticion.getFiguraDerechoPeticionList().remove(figuraDerechoPeticion);
                idDerechoPeticion = em.merge(idDerechoPeticion);
            }
            em.remove(figuraDerechoPeticion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FiguraDerechoPeticion> findFiguraDerechoPeticionEntities() {
        return findFiguraDerechoPeticionEntities(true, -1, -1);
    }

    public List<FiguraDerechoPeticion> findFiguraDerechoPeticionEntities(int maxResults, int firstResult) {
        return findFiguraDerechoPeticionEntities(false, maxResults, firstResult);
    }

    private List<FiguraDerechoPeticion> findFiguraDerechoPeticionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FiguraDerechoPeticion.class));
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

    public FiguraDerechoPeticion findFiguraDerechoPeticion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FiguraDerechoPeticion.class, id);
        } finally {
            em.close();
        }
    }

    public int getFiguraDerechoPeticionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FiguraDerechoPeticion> rt = cq.from(FiguraDerechoPeticion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
