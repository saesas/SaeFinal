/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.CategoriaCuentaInsumo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Puc;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class CategoriaCuentaInsumoJpaController implements Serializable {

    public CategoriaCuentaInsumoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CategoriaCuentaInsumo categoriaCuentaInsumo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puc idPuc = categoriaCuentaInsumo.getIdPuc();
            if (idPuc != null) {
                idPuc = em.getReference(idPuc.getClass(), idPuc.getId());
                categoriaCuentaInsumo.setIdPuc(idPuc);
            }
            em.persist(categoriaCuentaInsumo);
            if (idPuc != null) {
                idPuc.getCategoriaCuentaInsumoList().add(categoriaCuentaInsumo);
                idPuc = em.merge(idPuc);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCategoriaCuentaInsumo(categoriaCuentaInsumo.getId()) != null) {
                throw new PreexistingEntityException("CategoriaCuentaInsumo " + categoriaCuentaInsumo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CategoriaCuentaInsumo categoriaCuentaInsumo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategoriaCuentaInsumo persistentCategoriaCuentaInsumo = em.find(CategoriaCuentaInsumo.class, categoriaCuentaInsumo.getId());
            Puc idPucOld = persistentCategoriaCuentaInsumo.getIdPuc();
            Puc idPucNew = categoriaCuentaInsumo.getIdPuc();
            if (idPucNew != null) {
                idPucNew = em.getReference(idPucNew.getClass(), idPucNew.getId());
                categoriaCuentaInsumo.setIdPuc(idPucNew);
            }
            categoriaCuentaInsumo = em.merge(categoriaCuentaInsumo);
            if (idPucOld != null && !idPucOld.equals(idPucNew)) {
                idPucOld.getCategoriaCuentaInsumoList().remove(categoriaCuentaInsumo);
                idPucOld = em.merge(idPucOld);
            }
            if (idPucNew != null && !idPucNew.equals(idPucOld)) {
                idPucNew.getCategoriaCuentaInsumoList().add(categoriaCuentaInsumo);
                idPucNew = em.merge(idPucNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categoriaCuentaInsumo.getId();
                if (findCategoriaCuentaInsumo(id) == null) {
                    throw new NonexistentEntityException("The categoriaCuentaInsumo with id " + id + " no longer exists.");
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
            CategoriaCuentaInsumo categoriaCuentaInsumo;
            try {
                categoriaCuentaInsumo = em.getReference(CategoriaCuentaInsumo.class, id);
                categoriaCuentaInsumo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaCuentaInsumo with id " + id + " no longer exists.", enfe);
            }
            Puc idPuc = categoriaCuentaInsumo.getIdPuc();
            if (idPuc != null) {
                idPuc.getCategoriaCuentaInsumoList().remove(categoriaCuentaInsumo);
                idPuc = em.merge(idPuc);
            }
            em.remove(categoriaCuentaInsumo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CategoriaCuentaInsumo> findCategoriaCuentaInsumoEntities() {
        return findCategoriaCuentaInsumoEntities(true, -1, -1);
    }

    public List<CategoriaCuentaInsumo> findCategoriaCuentaInsumoEntities(int maxResults, int firstResult) {
        return findCategoriaCuentaInsumoEntities(false, maxResults, firstResult);
    }

    private List<CategoriaCuentaInsumo> findCategoriaCuentaInsumoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CategoriaCuentaInsumo.class));
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

    public CategoriaCuentaInsumo findCategoriaCuentaInsumo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CategoriaCuentaInsumo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaCuentaInsumoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CategoriaCuentaInsumo> rt = cq.from(CategoriaCuentaInsumo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
