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
import com.sae.persistence.domain.CitacionAudiencia;
import com.sae.persistence.domain.FiguraCitacionAudiencia;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class FiguraCitacionAudienciaJpaController implements Serializable {

    public FiguraCitacionAudienciaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FiguraCitacionAudiencia figuraCitacionAudiencia) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Figura idFigura = figuraCitacionAudiencia.getIdFigura();
            if (idFigura != null) {
                idFigura = em.getReference(idFigura.getClass(), idFigura.getId());
                figuraCitacionAudiencia.setIdFigura(idFigura);
            }
            CitacionAudiencia idCitacion = figuraCitacionAudiencia.getIdCitacion();
            if (idCitacion != null) {
                idCitacion = em.getReference(idCitacion.getClass(), idCitacion.getId());
                figuraCitacionAudiencia.setIdCitacion(idCitacion);
            }
            em.persist(figuraCitacionAudiencia);
            if (idFigura != null) {
                idFigura.getFiguraCitacionAudienciaList().add(figuraCitacionAudiencia);
                idFigura = em.merge(idFigura);
            }
            if (idCitacion != null) {
                idCitacion.getFiguraCitacionAudienciaList().add(figuraCitacionAudiencia);
                idCitacion = em.merge(idCitacion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFiguraCitacionAudiencia(figuraCitacionAudiencia.getId()) != null) {
                throw new PreexistingEntityException("FiguraCitacionAudiencia " + figuraCitacionAudiencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FiguraCitacionAudiencia figuraCitacionAudiencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FiguraCitacionAudiencia persistentFiguraCitacionAudiencia = em.find(FiguraCitacionAudiencia.class, figuraCitacionAudiencia.getId());
            Figura idFiguraOld = persistentFiguraCitacionAudiencia.getIdFigura();
            Figura idFiguraNew = figuraCitacionAudiencia.getIdFigura();
            CitacionAudiencia idCitacionOld = persistentFiguraCitacionAudiencia.getIdCitacion();
            CitacionAudiencia idCitacionNew = figuraCitacionAudiencia.getIdCitacion();
            if (idFiguraNew != null) {
                idFiguraNew = em.getReference(idFiguraNew.getClass(), idFiguraNew.getId());
                figuraCitacionAudiencia.setIdFigura(idFiguraNew);
            }
            if (idCitacionNew != null) {
                idCitacionNew = em.getReference(idCitacionNew.getClass(), idCitacionNew.getId());
                figuraCitacionAudiencia.setIdCitacion(idCitacionNew);
            }
            figuraCitacionAudiencia = em.merge(figuraCitacionAudiencia);
            if (idFiguraOld != null && !idFiguraOld.equals(idFiguraNew)) {
                idFiguraOld.getFiguraCitacionAudienciaList().remove(figuraCitacionAudiencia);
                idFiguraOld = em.merge(idFiguraOld);
            }
            if (idFiguraNew != null && !idFiguraNew.equals(idFiguraOld)) {
                idFiguraNew.getFiguraCitacionAudienciaList().add(figuraCitacionAudiencia);
                idFiguraNew = em.merge(idFiguraNew);
            }
            if (idCitacionOld != null && !idCitacionOld.equals(idCitacionNew)) {
                idCitacionOld.getFiguraCitacionAudienciaList().remove(figuraCitacionAudiencia);
                idCitacionOld = em.merge(idCitacionOld);
            }
            if (idCitacionNew != null && !idCitacionNew.equals(idCitacionOld)) {
                idCitacionNew.getFiguraCitacionAudienciaList().add(figuraCitacionAudiencia);
                idCitacionNew = em.merge(idCitacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = figuraCitacionAudiencia.getId();
                if (findFiguraCitacionAudiencia(id) == null) {
                    throw new NonexistentEntityException("The figuraCitacionAudiencia with id " + id + " no longer exists.");
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
            FiguraCitacionAudiencia figuraCitacionAudiencia;
            try {
                figuraCitacionAudiencia = em.getReference(FiguraCitacionAudiencia.class, id);
                figuraCitacionAudiencia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The figuraCitacionAudiencia with id " + id + " no longer exists.", enfe);
            }
            Figura idFigura = figuraCitacionAudiencia.getIdFigura();
            if (idFigura != null) {
                idFigura.getFiguraCitacionAudienciaList().remove(figuraCitacionAudiencia);
                idFigura = em.merge(idFigura);
            }
            CitacionAudiencia idCitacion = figuraCitacionAudiencia.getIdCitacion();
            if (idCitacion != null) {
                idCitacion.getFiguraCitacionAudienciaList().remove(figuraCitacionAudiencia);
                idCitacion = em.merge(idCitacion);
            }
            em.remove(figuraCitacionAudiencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FiguraCitacionAudiencia> findFiguraCitacionAudienciaEntities() {
        return findFiguraCitacionAudienciaEntities(true, -1, -1);
    }

    public List<FiguraCitacionAudiencia> findFiguraCitacionAudienciaEntities(int maxResults, int firstResult) {
        return findFiguraCitacionAudienciaEntities(false, maxResults, firstResult);
    }

    private List<FiguraCitacionAudiencia> findFiguraCitacionAudienciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FiguraCitacionAudiencia.class));
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

    public FiguraCitacionAudiencia findFiguraCitacionAudiencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FiguraCitacionAudiencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getFiguraCitacionAudienciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FiguraCitacionAudiencia> rt = cq.from(FiguraCitacionAudiencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
