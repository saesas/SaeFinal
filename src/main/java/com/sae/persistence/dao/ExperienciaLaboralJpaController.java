/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.ExperienciaLaboral;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.HojaVida;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ExperienciaLaboralJpaController implements Serializable {

    public ExperienciaLaboralJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ExperienciaLaboral experienciaLaboral) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HojaVida idHojaVida = experienciaLaboral.getIdHojaVida();
            if (idHojaVida != null) {
                idHojaVida = em.getReference(idHojaVida.getClass(), idHojaVida.getId());
                experienciaLaboral.setIdHojaVida(idHojaVida);
            }
            em.persist(experienciaLaboral);
            if (idHojaVida != null) {
                idHojaVida.getExperienciaLaboralList().add(experienciaLaboral);
                idHojaVida = em.merge(idHojaVida);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findExperienciaLaboral(experienciaLaboral.getId()) != null) {
                throw new PreexistingEntityException("ExperienciaLaboral " + experienciaLaboral + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ExperienciaLaboral experienciaLaboral) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ExperienciaLaboral persistentExperienciaLaboral = em.find(ExperienciaLaboral.class, experienciaLaboral.getId());
            HojaVida idHojaVidaOld = persistentExperienciaLaboral.getIdHojaVida();
            HojaVida idHojaVidaNew = experienciaLaboral.getIdHojaVida();
            if (idHojaVidaNew != null) {
                idHojaVidaNew = em.getReference(idHojaVidaNew.getClass(), idHojaVidaNew.getId());
                experienciaLaboral.setIdHojaVida(idHojaVidaNew);
            }
            experienciaLaboral = em.merge(experienciaLaboral);
            if (idHojaVidaOld != null && !idHojaVidaOld.equals(idHojaVidaNew)) {
                idHojaVidaOld.getExperienciaLaboralList().remove(experienciaLaboral);
                idHojaVidaOld = em.merge(idHojaVidaOld);
            }
            if (idHojaVidaNew != null && !idHojaVidaNew.equals(idHojaVidaOld)) {
                idHojaVidaNew.getExperienciaLaboralList().add(experienciaLaboral);
                idHojaVidaNew = em.merge(idHojaVidaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = experienciaLaboral.getId();
                if (findExperienciaLaboral(id) == null) {
                    throw new NonexistentEntityException("The experienciaLaboral with id " + id + " no longer exists.");
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
            ExperienciaLaboral experienciaLaboral;
            try {
                experienciaLaboral = em.getReference(ExperienciaLaboral.class, id);
                experienciaLaboral.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The experienciaLaboral with id " + id + " no longer exists.", enfe);
            }
            HojaVida idHojaVida = experienciaLaboral.getIdHojaVida();
            if (idHojaVida != null) {
                idHojaVida.getExperienciaLaboralList().remove(experienciaLaboral);
                idHojaVida = em.merge(idHojaVida);
            }
            em.remove(experienciaLaboral);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ExperienciaLaboral> findExperienciaLaboralEntities() {
        return findExperienciaLaboralEntities(true, -1, -1);
    }

    public List<ExperienciaLaboral> findExperienciaLaboralEntities(int maxResults, int firstResult) {
        return findExperienciaLaboralEntities(false, maxResults, firstResult);
    }

    private List<ExperienciaLaboral> findExperienciaLaboralEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ExperienciaLaboral.class));
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

    public ExperienciaLaboral findExperienciaLaboral(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ExperienciaLaboral.class, id);
        } finally {
            em.close();
        }
    }

    public int getExperienciaLaboralCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ExperienciaLaboral> rt = cq.from(ExperienciaLaboral.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
