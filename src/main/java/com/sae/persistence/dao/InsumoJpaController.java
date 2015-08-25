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
import com.sae.persistence.domain.TipoInsumo;
import com.sae.persistence.domain.CategoriaInsumo;
import com.sae.persistence.domain.Insumo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class InsumoJpaController implements Serializable {

    public InsumoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Insumo insumo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoInsumo idTipoInsumo = insumo.getIdTipoInsumo();
            if (idTipoInsumo != null) {
                idTipoInsumo = em.getReference(idTipoInsumo.getClass(), idTipoInsumo.getId());
                insumo.setIdTipoInsumo(idTipoInsumo);
            }
            CategoriaInsumo idCategoria = insumo.getIdCategoria();
            if (idCategoria != null) {
                idCategoria = em.getReference(idCategoria.getClass(), idCategoria.getId());
                insumo.setIdCategoria(idCategoria);
            }
            em.persist(insumo);
            if (idTipoInsumo != null) {
                idTipoInsumo.getInsumoList().add(insumo);
                idTipoInsumo = em.merge(idTipoInsumo);
            }
            if (idCategoria != null) {
                idCategoria.getInsumoList().add(insumo);
                idCategoria = em.merge(idCategoria);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInsumo(insumo.getId()) != null) {
                throw new PreexistingEntityException("Insumo " + insumo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Insumo insumo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Insumo persistentInsumo = em.find(Insumo.class, insumo.getId());
            TipoInsumo idTipoInsumoOld = persistentInsumo.getIdTipoInsumo();
            TipoInsumo idTipoInsumoNew = insumo.getIdTipoInsumo();
            CategoriaInsumo idCategoriaOld = persistentInsumo.getIdCategoria();
            CategoriaInsumo idCategoriaNew = insumo.getIdCategoria();
            if (idTipoInsumoNew != null) {
                idTipoInsumoNew = em.getReference(idTipoInsumoNew.getClass(), idTipoInsumoNew.getId());
                insumo.setIdTipoInsumo(idTipoInsumoNew);
            }
            if (idCategoriaNew != null) {
                idCategoriaNew = em.getReference(idCategoriaNew.getClass(), idCategoriaNew.getId());
                insumo.setIdCategoria(idCategoriaNew);
            }
            insumo = em.merge(insumo);
            if (idTipoInsumoOld != null && !idTipoInsumoOld.equals(idTipoInsumoNew)) {
                idTipoInsumoOld.getInsumoList().remove(insumo);
                idTipoInsumoOld = em.merge(idTipoInsumoOld);
            }
            if (idTipoInsumoNew != null && !idTipoInsumoNew.equals(idTipoInsumoOld)) {
                idTipoInsumoNew.getInsumoList().add(insumo);
                idTipoInsumoNew = em.merge(idTipoInsumoNew);
            }
            if (idCategoriaOld != null && !idCategoriaOld.equals(idCategoriaNew)) {
                idCategoriaOld.getInsumoList().remove(insumo);
                idCategoriaOld = em.merge(idCategoriaOld);
            }
            if (idCategoriaNew != null && !idCategoriaNew.equals(idCategoriaOld)) {
                idCategoriaNew.getInsumoList().add(insumo);
                idCategoriaNew = em.merge(idCategoriaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = insumo.getId();
                if (findInsumo(id) == null) {
                    throw new NonexistentEntityException("The insumo with id " + id + " no longer exists.");
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
            Insumo insumo;
            try {
                insumo = em.getReference(Insumo.class, id);
                insumo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The insumo with id " + id + " no longer exists.", enfe);
            }
            TipoInsumo idTipoInsumo = insumo.getIdTipoInsumo();
            if (idTipoInsumo != null) {
                idTipoInsumo.getInsumoList().remove(insumo);
                idTipoInsumo = em.merge(idTipoInsumo);
            }
            CategoriaInsumo idCategoria = insumo.getIdCategoria();
            if (idCategoria != null) {
                idCategoria.getInsumoList().remove(insumo);
                idCategoria = em.merge(idCategoria);
            }
            em.remove(insumo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Insumo> findInsumoEntities() {
        return findInsumoEntities(true, -1, -1);
    }

    public List<Insumo> findInsumoEntities(int maxResults, int firstResult) {
        return findInsumoEntities(false, maxResults, firstResult);
    }

    private List<Insumo> findInsumoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Insumo.class));
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

    public Insumo findInsumo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Insumo.class, id);
        } finally {
            em.close();
        }
    }

    public int getInsumoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Insumo> rt = cq.from(Insumo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
