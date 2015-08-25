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
import com.sae.persistence.domain.Proceso;
import com.sae.persistence.domain.Figura;
import com.sae.persistence.domain.FiguraProceso;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class FiguraProcesoJpaController implements Serializable {

    public FiguraProcesoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FiguraProceso figuraProceso) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proceso idProceso = figuraProceso.getIdProceso();
            if (idProceso != null) {
                idProceso = em.getReference(idProceso.getClass(), idProceso.getId());
                figuraProceso.setIdProceso(idProceso);
            }
            Figura idFigura = figuraProceso.getIdFigura();
            if (idFigura != null) {
                idFigura = em.getReference(idFigura.getClass(), idFigura.getId());
                figuraProceso.setIdFigura(idFigura);
            }
            em.persist(figuraProceso);
            if (idProceso != null) {
                idProceso.getFiguraProcesoList().add(figuraProceso);
                idProceso = em.merge(idProceso);
            }
            if (idFigura != null) {
                idFigura.getFiguraProcesoList().add(figuraProceso);
                idFigura = em.merge(idFigura);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFiguraProceso(figuraProceso.getId()) != null) {
                throw new PreexistingEntityException("FiguraProceso " + figuraProceso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FiguraProceso figuraProceso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FiguraProceso persistentFiguraProceso = em.find(FiguraProceso.class, figuraProceso.getId());
            Proceso idProcesoOld = persistentFiguraProceso.getIdProceso();
            Proceso idProcesoNew = figuraProceso.getIdProceso();
            Figura idFiguraOld = persistentFiguraProceso.getIdFigura();
            Figura idFiguraNew = figuraProceso.getIdFigura();
            if (idProcesoNew != null) {
                idProcesoNew = em.getReference(idProcesoNew.getClass(), idProcesoNew.getId());
                figuraProceso.setIdProceso(idProcesoNew);
            }
            if (idFiguraNew != null) {
                idFiguraNew = em.getReference(idFiguraNew.getClass(), idFiguraNew.getId());
                figuraProceso.setIdFigura(idFiguraNew);
            }
            figuraProceso = em.merge(figuraProceso);
            if (idProcesoOld != null && !idProcesoOld.equals(idProcesoNew)) {
                idProcesoOld.getFiguraProcesoList().remove(figuraProceso);
                idProcesoOld = em.merge(idProcesoOld);
            }
            if (idProcesoNew != null && !idProcesoNew.equals(idProcesoOld)) {
                idProcesoNew.getFiguraProcesoList().add(figuraProceso);
                idProcesoNew = em.merge(idProcesoNew);
            }
            if (idFiguraOld != null && !idFiguraOld.equals(idFiguraNew)) {
                idFiguraOld.getFiguraProcesoList().remove(figuraProceso);
                idFiguraOld = em.merge(idFiguraOld);
            }
            if (idFiguraNew != null && !idFiguraNew.equals(idFiguraOld)) {
                idFiguraNew.getFiguraProcesoList().add(figuraProceso);
                idFiguraNew = em.merge(idFiguraNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = figuraProceso.getId();
                if (findFiguraProceso(id) == null) {
                    throw new NonexistentEntityException("The figuraProceso with id " + id + " no longer exists.");
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
            FiguraProceso figuraProceso;
            try {
                figuraProceso = em.getReference(FiguraProceso.class, id);
                figuraProceso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The figuraProceso with id " + id + " no longer exists.", enfe);
            }
            Proceso idProceso = figuraProceso.getIdProceso();
            if (idProceso != null) {
                idProceso.getFiguraProcesoList().remove(figuraProceso);
                idProceso = em.merge(idProceso);
            }
            Figura idFigura = figuraProceso.getIdFigura();
            if (idFigura != null) {
                idFigura.getFiguraProcesoList().remove(figuraProceso);
                idFigura = em.merge(idFigura);
            }
            em.remove(figuraProceso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FiguraProceso> findFiguraProcesoEntities() {
        return findFiguraProcesoEntities(true, -1, -1);
    }

    public List<FiguraProceso> findFiguraProcesoEntities(int maxResults, int firstResult) {
        return findFiguraProcesoEntities(false, maxResults, firstResult);
    }

    private List<FiguraProceso> findFiguraProcesoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FiguraProceso.class));
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

    public FiguraProceso findFiguraProceso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FiguraProceso.class, id);
        } finally {
            em.close();
        }
    }

    public int getFiguraProcesoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FiguraProceso> rt = cq.from(FiguraProceso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
