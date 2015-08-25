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
import com.sae.persistence.domain.ParametroContableFacturacion;
import com.sae.persistence.domain.ParametroFacturacionTipoProyecto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ParametroFacturacionTipoProyectoJpaController implements Serializable {

    public ParametroFacturacionTipoProyectoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ParametroFacturacionTipoProyecto parametroFacturacionTipoProyecto) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ParametroContableFacturacion idParametroFacturacion = parametroFacturacionTipoProyecto.getIdParametroFacturacion();
            if (idParametroFacturacion != null) {
                idParametroFacturacion = em.getReference(idParametroFacturacion.getClass(), idParametroFacturacion.getId());
                parametroFacturacionTipoProyecto.setIdParametroFacturacion(idParametroFacturacion);
            }
            em.persist(parametroFacturacionTipoProyecto);
            if (idParametroFacturacion != null) {
                idParametroFacturacion.getParametroFacturacionTipoProyectoList().add(parametroFacturacionTipoProyecto);
                idParametroFacturacion = em.merge(idParametroFacturacion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findParametroFacturacionTipoProyecto(parametroFacturacionTipoProyecto.getId()) != null) {
                throw new PreexistingEntityException("ParametroFacturacionTipoProyecto " + parametroFacturacionTipoProyecto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ParametroFacturacionTipoProyecto parametroFacturacionTipoProyecto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ParametroFacturacionTipoProyecto persistentParametroFacturacionTipoProyecto = em.find(ParametroFacturacionTipoProyecto.class, parametroFacturacionTipoProyecto.getId());
            ParametroContableFacturacion idParametroFacturacionOld = persistentParametroFacturacionTipoProyecto.getIdParametroFacturacion();
            ParametroContableFacturacion idParametroFacturacionNew = parametroFacturacionTipoProyecto.getIdParametroFacturacion();
            if (idParametroFacturacionNew != null) {
                idParametroFacturacionNew = em.getReference(idParametroFacturacionNew.getClass(), idParametroFacturacionNew.getId());
                parametroFacturacionTipoProyecto.setIdParametroFacturacion(idParametroFacturacionNew);
            }
            parametroFacturacionTipoProyecto = em.merge(parametroFacturacionTipoProyecto);
            if (idParametroFacturacionOld != null && !idParametroFacturacionOld.equals(idParametroFacturacionNew)) {
                idParametroFacturacionOld.getParametroFacturacionTipoProyectoList().remove(parametroFacturacionTipoProyecto);
                idParametroFacturacionOld = em.merge(idParametroFacturacionOld);
            }
            if (idParametroFacturacionNew != null && !idParametroFacturacionNew.equals(idParametroFacturacionOld)) {
                idParametroFacturacionNew.getParametroFacturacionTipoProyectoList().add(parametroFacturacionTipoProyecto);
                idParametroFacturacionNew = em.merge(idParametroFacturacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = parametroFacturacionTipoProyecto.getId();
                if (findParametroFacturacionTipoProyecto(id) == null) {
                    throw new NonexistentEntityException("The parametroFacturacionTipoProyecto with id " + id + " no longer exists.");
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
            ParametroFacturacionTipoProyecto parametroFacturacionTipoProyecto;
            try {
                parametroFacturacionTipoProyecto = em.getReference(ParametroFacturacionTipoProyecto.class, id);
                parametroFacturacionTipoProyecto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parametroFacturacionTipoProyecto with id " + id + " no longer exists.", enfe);
            }
            ParametroContableFacturacion idParametroFacturacion = parametroFacturacionTipoProyecto.getIdParametroFacturacion();
            if (idParametroFacturacion != null) {
                idParametroFacturacion.getParametroFacturacionTipoProyectoList().remove(parametroFacturacionTipoProyecto);
                idParametroFacturacion = em.merge(idParametroFacturacion);
            }
            em.remove(parametroFacturacionTipoProyecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ParametroFacturacionTipoProyecto> findParametroFacturacionTipoProyectoEntities() {
        return findParametroFacturacionTipoProyectoEntities(true, -1, -1);
    }

    public List<ParametroFacturacionTipoProyecto> findParametroFacturacionTipoProyectoEntities(int maxResults, int firstResult) {
        return findParametroFacturacionTipoProyectoEntities(false, maxResults, firstResult);
    }

    private List<ParametroFacturacionTipoProyecto> findParametroFacturacionTipoProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ParametroFacturacionTipoProyecto.class));
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

    public ParametroFacturacionTipoProyecto findParametroFacturacionTipoProyecto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ParametroFacturacionTipoProyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getParametroFacturacionTipoProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ParametroFacturacionTipoProyecto> rt = cq.from(ParametroFacturacionTipoProyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
