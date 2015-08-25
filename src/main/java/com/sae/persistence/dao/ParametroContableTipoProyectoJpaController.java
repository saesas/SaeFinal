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
import com.sae.persistence.domain.ParametroContableProyecto;
import com.sae.persistence.domain.ParametroContableTipoProyecto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ParametroContableTipoProyectoJpaController implements Serializable {

    public ParametroContableTipoProyectoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ParametroContableTipoProyecto parametroContableTipoProyecto) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ParametroContableProyecto idParametro = parametroContableTipoProyecto.getIdParametro();
            if (idParametro != null) {
                idParametro = em.getReference(idParametro.getClass(), idParametro.getId());
                parametroContableTipoProyecto.setIdParametro(idParametro);
            }
            em.persist(parametroContableTipoProyecto);
            if (idParametro != null) {
                idParametro.getParametroContableTipoProyectoList().add(parametroContableTipoProyecto);
                idParametro = em.merge(idParametro);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findParametroContableTipoProyecto(parametroContableTipoProyecto.getId()) != null) {
                throw new PreexistingEntityException("ParametroContableTipoProyecto " + parametroContableTipoProyecto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ParametroContableTipoProyecto parametroContableTipoProyecto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ParametroContableTipoProyecto persistentParametroContableTipoProyecto = em.find(ParametroContableTipoProyecto.class, parametroContableTipoProyecto.getId());
            ParametroContableProyecto idParametroOld = persistentParametroContableTipoProyecto.getIdParametro();
            ParametroContableProyecto idParametroNew = parametroContableTipoProyecto.getIdParametro();
            if (idParametroNew != null) {
                idParametroNew = em.getReference(idParametroNew.getClass(), idParametroNew.getId());
                parametroContableTipoProyecto.setIdParametro(idParametroNew);
            }
            parametroContableTipoProyecto = em.merge(parametroContableTipoProyecto);
            if (idParametroOld != null && !idParametroOld.equals(idParametroNew)) {
                idParametroOld.getParametroContableTipoProyectoList().remove(parametroContableTipoProyecto);
                idParametroOld = em.merge(idParametroOld);
            }
            if (idParametroNew != null && !idParametroNew.equals(idParametroOld)) {
                idParametroNew.getParametroContableTipoProyectoList().add(parametroContableTipoProyecto);
                idParametroNew = em.merge(idParametroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = parametroContableTipoProyecto.getId();
                if (findParametroContableTipoProyecto(id) == null) {
                    throw new NonexistentEntityException("The parametroContableTipoProyecto with id " + id + " no longer exists.");
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
            ParametroContableTipoProyecto parametroContableTipoProyecto;
            try {
                parametroContableTipoProyecto = em.getReference(ParametroContableTipoProyecto.class, id);
                parametroContableTipoProyecto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parametroContableTipoProyecto with id " + id + " no longer exists.", enfe);
            }
            ParametroContableProyecto idParametro = parametroContableTipoProyecto.getIdParametro();
            if (idParametro != null) {
                idParametro.getParametroContableTipoProyectoList().remove(parametroContableTipoProyecto);
                idParametro = em.merge(idParametro);
            }
            em.remove(parametroContableTipoProyecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ParametroContableTipoProyecto> findParametroContableTipoProyectoEntities() {
        return findParametroContableTipoProyectoEntities(true, -1, -1);
    }

    public List<ParametroContableTipoProyecto> findParametroContableTipoProyectoEntities(int maxResults, int firstResult) {
        return findParametroContableTipoProyectoEntities(false, maxResults, firstResult);
    }

    private List<ParametroContableTipoProyecto> findParametroContableTipoProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ParametroContableTipoProyecto.class));
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

    public ParametroContableTipoProyecto findParametroContableTipoProyecto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ParametroContableTipoProyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getParametroContableTipoProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ParametroContableTipoProyecto> rt = cq.from(ParametroContableTipoProyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
