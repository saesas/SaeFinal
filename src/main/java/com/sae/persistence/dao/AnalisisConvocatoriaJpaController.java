/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.AnalisisConvocatoria;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Convocatoria;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class AnalisisConvocatoriaJpaController implements Serializable {

    public AnalisisConvocatoriaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AnalisisConvocatoria analisisConvocatoria) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Convocatoria idConvocatoria = analisisConvocatoria.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria = em.getReference(idConvocatoria.getClass(), idConvocatoria.getId());
                analisisConvocatoria.setIdConvocatoria(idConvocatoria);
            }
            em.persist(analisisConvocatoria);
            if (idConvocatoria != null) {
                idConvocatoria.getAnalisisConvocatoriaList().add(analisisConvocatoria);
                idConvocatoria = em.merge(idConvocatoria);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAnalisisConvocatoria(analisisConvocatoria.getId()) != null) {
                throw new PreexistingEntityException("AnalisisConvocatoria " + analisisConvocatoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AnalisisConvocatoria analisisConvocatoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AnalisisConvocatoria persistentAnalisisConvocatoria = em.find(AnalisisConvocatoria.class, analisisConvocatoria.getId());
            Convocatoria idConvocatoriaOld = persistentAnalisisConvocatoria.getIdConvocatoria();
            Convocatoria idConvocatoriaNew = analisisConvocatoria.getIdConvocatoria();
            if (idConvocatoriaNew != null) {
                idConvocatoriaNew = em.getReference(idConvocatoriaNew.getClass(), idConvocatoriaNew.getId());
                analisisConvocatoria.setIdConvocatoria(idConvocatoriaNew);
            }
            analisisConvocatoria = em.merge(analisisConvocatoria);
            if (idConvocatoriaOld != null && !idConvocatoriaOld.equals(idConvocatoriaNew)) {
                idConvocatoriaOld.getAnalisisConvocatoriaList().remove(analisisConvocatoria);
                idConvocatoriaOld = em.merge(idConvocatoriaOld);
            }
            if (idConvocatoriaNew != null && !idConvocatoriaNew.equals(idConvocatoriaOld)) {
                idConvocatoriaNew.getAnalisisConvocatoriaList().add(analisisConvocatoria);
                idConvocatoriaNew = em.merge(idConvocatoriaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = analisisConvocatoria.getId();
                if (findAnalisisConvocatoria(id) == null) {
                    throw new NonexistentEntityException("The analisisConvocatoria with id " + id + " no longer exists.");
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
            AnalisisConvocatoria analisisConvocatoria;
            try {
                analisisConvocatoria = em.getReference(AnalisisConvocatoria.class, id);
                analisisConvocatoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The analisisConvocatoria with id " + id + " no longer exists.", enfe);
            }
            Convocatoria idConvocatoria = analisisConvocatoria.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria.getAnalisisConvocatoriaList().remove(analisisConvocatoria);
                idConvocatoria = em.merge(idConvocatoria);
            }
            em.remove(analisisConvocatoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AnalisisConvocatoria> findAnalisisConvocatoriaEntities() {
        return findAnalisisConvocatoriaEntities(true, -1, -1);
    }

    public List<AnalisisConvocatoria> findAnalisisConvocatoriaEntities(int maxResults, int firstResult) {
        return findAnalisisConvocatoriaEntities(false, maxResults, firstResult);
    }

    private List<AnalisisConvocatoria> findAnalisisConvocatoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AnalisisConvocatoria.class));
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

    public AnalisisConvocatoria findAnalisisConvocatoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AnalisisConvocatoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnalisisConvocatoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AnalisisConvocatoria> rt = cq.from(AnalisisConvocatoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
