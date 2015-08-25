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
import com.sae.persistence.domain.SolucionProceso;
import com.sae.persistence.domain.Figura;
import com.sae.persistence.domain.FiguraSolucion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class FiguraSolucionJpaController implements Serializable {

    public FiguraSolucionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FiguraSolucion figuraSolucion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SolucionProceso idSolucion = figuraSolucion.getIdSolucion();
            if (idSolucion != null) {
                idSolucion = em.getReference(idSolucion.getClass(), idSolucion.getId());
                figuraSolucion.setIdSolucion(idSolucion);
            }
            Figura idFigura = figuraSolucion.getIdFigura();
            if (idFigura != null) {
                idFigura = em.getReference(idFigura.getClass(), idFigura.getId());
                figuraSolucion.setIdFigura(idFigura);
            }
            em.persist(figuraSolucion);
            if (idSolucion != null) {
                idSolucion.getFiguraSolucionList().add(figuraSolucion);
                idSolucion = em.merge(idSolucion);
            }
            if (idFigura != null) {
                idFigura.getFiguraSolucionList().add(figuraSolucion);
                idFigura = em.merge(idFigura);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFiguraSolucion(figuraSolucion.getId()) != null) {
                throw new PreexistingEntityException("FiguraSolucion " + figuraSolucion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FiguraSolucion figuraSolucion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FiguraSolucion persistentFiguraSolucion = em.find(FiguraSolucion.class, figuraSolucion.getId());
            SolucionProceso idSolucionOld = persistentFiguraSolucion.getIdSolucion();
            SolucionProceso idSolucionNew = figuraSolucion.getIdSolucion();
            Figura idFiguraOld = persistentFiguraSolucion.getIdFigura();
            Figura idFiguraNew = figuraSolucion.getIdFigura();
            if (idSolucionNew != null) {
                idSolucionNew = em.getReference(idSolucionNew.getClass(), idSolucionNew.getId());
                figuraSolucion.setIdSolucion(idSolucionNew);
            }
            if (idFiguraNew != null) {
                idFiguraNew = em.getReference(idFiguraNew.getClass(), idFiguraNew.getId());
                figuraSolucion.setIdFigura(idFiguraNew);
            }
            figuraSolucion = em.merge(figuraSolucion);
            if (idSolucionOld != null && !idSolucionOld.equals(idSolucionNew)) {
                idSolucionOld.getFiguraSolucionList().remove(figuraSolucion);
                idSolucionOld = em.merge(idSolucionOld);
            }
            if (idSolucionNew != null && !idSolucionNew.equals(idSolucionOld)) {
                idSolucionNew.getFiguraSolucionList().add(figuraSolucion);
                idSolucionNew = em.merge(idSolucionNew);
            }
            if (idFiguraOld != null && !idFiguraOld.equals(idFiguraNew)) {
                idFiguraOld.getFiguraSolucionList().remove(figuraSolucion);
                idFiguraOld = em.merge(idFiguraOld);
            }
            if (idFiguraNew != null && !idFiguraNew.equals(idFiguraOld)) {
                idFiguraNew.getFiguraSolucionList().add(figuraSolucion);
                idFiguraNew = em.merge(idFiguraNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = figuraSolucion.getId();
                if (findFiguraSolucion(id) == null) {
                    throw new NonexistentEntityException("The figuraSolucion with id " + id + " no longer exists.");
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
            FiguraSolucion figuraSolucion;
            try {
                figuraSolucion = em.getReference(FiguraSolucion.class, id);
                figuraSolucion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The figuraSolucion with id " + id + " no longer exists.", enfe);
            }
            SolucionProceso idSolucion = figuraSolucion.getIdSolucion();
            if (idSolucion != null) {
                idSolucion.getFiguraSolucionList().remove(figuraSolucion);
                idSolucion = em.merge(idSolucion);
            }
            Figura idFigura = figuraSolucion.getIdFigura();
            if (idFigura != null) {
                idFigura.getFiguraSolucionList().remove(figuraSolucion);
                idFigura = em.merge(idFigura);
            }
            em.remove(figuraSolucion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FiguraSolucion> findFiguraSolucionEntities() {
        return findFiguraSolucionEntities(true, -1, -1);
    }

    public List<FiguraSolucion> findFiguraSolucionEntities(int maxResults, int firstResult) {
        return findFiguraSolucionEntities(false, maxResults, firstResult);
    }

    private List<FiguraSolucion> findFiguraSolucionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FiguraSolucion.class));
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

    public FiguraSolucion findFiguraSolucion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FiguraSolucion.class, id);
        } finally {
            em.close();
        }
    }

    public int getFiguraSolucionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FiguraSolucion> rt = cq.from(FiguraSolucion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
