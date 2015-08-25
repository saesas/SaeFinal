/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.ClasePuc;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.TipoClasePuc;
import com.sae.persistence.domain.Puc;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ClasePucJpaController implements Serializable {

    public ClasePucJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClasePuc clasePuc) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoClasePuc idClase = clasePuc.getIdClase();
            if (idClase != null) {
                idClase = em.getReference(idClase.getClass(), idClase.getId());
                clasePuc.setIdClase(idClase);
            }
            Puc idPuc = clasePuc.getIdPuc();
            if (idPuc != null) {
                idPuc = em.getReference(idPuc.getClass(), idPuc.getId());
                clasePuc.setIdPuc(idPuc);
            }
            em.persist(clasePuc);
            if (idClase != null) {
                idClase.getClasePucList().add(clasePuc);
                idClase = em.merge(idClase);
            }
            if (idPuc != null) {
                idPuc.getClasePucList().add(clasePuc);
                idPuc = em.merge(idPuc);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClasePuc(clasePuc.getId()) != null) {
                throw new PreexistingEntityException("ClasePuc " + clasePuc + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClasePuc clasePuc) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClasePuc persistentClasePuc = em.find(ClasePuc.class, clasePuc.getId());
            TipoClasePuc idClaseOld = persistentClasePuc.getIdClase();
            TipoClasePuc idClaseNew = clasePuc.getIdClase();
            Puc idPucOld = persistentClasePuc.getIdPuc();
            Puc idPucNew = clasePuc.getIdPuc();
            if (idClaseNew != null) {
                idClaseNew = em.getReference(idClaseNew.getClass(), idClaseNew.getId());
                clasePuc.setIdClase(idClaseNew);
            }
            if (idPucNew != null) {
                idPucNew = em.getReference(idPucNew.getClass(), idPucNew.getId());
                clasePuc.setIdPuc(idPucNew);
            }
            clasePuc = em.merge(clasePuc);
            if (idClaseOld != null && !idClaseOld.equals(idClaseNew)) {
                idClaseOld.getClasePucList().remove(clasePuc);
                idClaseOld = em.merge(idClaseOld);
            }
            if (idClaseNew != null && !idClaseNew.equals(idClaseOld)) {
                idClaseNew.getClasePucList().add(clasePuc);
                idClaseNew = em.merge(idClaseNew);
            }
            if (idPucOld != null && !idPucOld.equals(idPucNew)) {
                idPucOld.getClasePucList().remove(clasePuc);
                idPucOld = em.merge(idPucOld);
            }
            if (idPucNew != null && !idPucNew.equals(idPucOld)) {
                idPucNew.getClasePucList().add(clasePuc);
                idPucNew = em.merge(idPucNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clasePuc.getId();
                if (findClasePuc(id) == null) {
                    throw new NonexistentEntityException("The clasePuc with id " + id + " no longer exists.");
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
            ClasePuc clasePuc;
            try {
                clasePuc = em.getReference(ClasePuc.class, id);
                clasePuc.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clasePuc with id " + id + " no longer exists.", enfe);
            }
            TipoClasePuc idClase = clasePuc.getIdClase();
            if (idClase != null) {
                idClase.getClasePucList().remove(clasePuc);
                idClase = em.merge(idClase);
            }
            Puc idPuc = clasePuc.getIdPuc();
            if (idPuc != null) {
                idPuc.getClasePucList().remove(clasePuc);
                idPuc = em.merge(idPuc);
            }
            em.remove(clasePuc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClasePuc> findClasePucEntities() {
        return findClasePucEntities(true, -1, -1);
    }

    public List<ClasePuc> findClasePucEntities(int maxResults, int firstResult) {
        return findClasePucEntities(false, maxResults, firstResult);
    }

    private List<ClasePuc> findClasePucEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClasePuc.class));
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

    public ClasePuc findClasePuc(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClasePuc.class, id);
        } finally {
            em.close();
        }
    }

    public int getClasePucCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClasePuc> rt = cq.from(ClasePuc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
