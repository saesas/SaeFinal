/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.CategoriaInsumo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Insumo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class CategoriaInsumoJpaController implements Serializable {

    public CategoriaInsumoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CategoriaInsumo categoriaInsumo) throws PreexistingEntityException, Exception {
        if (categoriaInsumo.getInsumoList() == null) {
            categoriaInsumo.setInsumoList(new ArrayList<Insumo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Insumo> attachedInsumoList = new ArrayList<Insumo>();
            for (Insumo insumoListInsumoToAttach : categoriaInsumo.getInsumoList()) {
                insumoListInsumoToAttach = em.getReference(insumoListInsumoToAttach.getClass(), insumoListInsumoToAttach.getId());
                attachedInsumoList.add(insumoListInsumoToAttach);
            }
            categoriaInsumo.setInsumoList(attachedInsumoList);
            em.persist(categoriaInsumo);
            for (Insumo insumoListInsumo : categoriaInsumo.getInsumoList()) {
                CategoriaInsumo oldIdCategoriaOfInsumoListInsumo = insumoListInsumo.getIdCategoria();
                insumoListInsumo.setIdCategoria(categoriaInsumo);
                insumoListInsumo = em.merge(insumoListInsumo);
                if (oldIdCategoriaOfInsumoListInsumo != null) {
                    oldIdCategoriaOfInsumoListInsumo.getInsumoList().remove(insumoListInsumo);
                    oldIdCategoriaOfInsumoListInsumo = em.merge(oldIdCategoriaOfInsumoListInsumo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCategoriaInsumo(categoriaInsumo.getId()) != null) {
                throw new PreexistingEntityException("CategoriaInsumo " + categoriaInsumo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CategoriaInsumo categoriaInsumo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategoriaInsumo persistentCategoriaInsumo = em.find(CategoriaInsumo.class, categoriaInsumo.getId());
            List<Insumo> insumoListOld = persistentCategoriaInsumo.getInsumoList();
            List<Insumo> insumoListNew = categoriaInsumo.getInsumoList();
            List<Insumo> attachedInsumoListNew = new ArrayList<Insumo>();
            for (Insumo insumoListNewInsumoToAttach : insumoListNew) {
                insumoListNewInsumoToAttach = em.getReference(insumoListNewInsumoToAttach.getClass(), insumoListNewInsumoToAttach.getId());
                attachedInsumoListNew.add(insumoListNewInsumoToAttach);
            }
            insumoListNew = attachedInsumoListNew;
            categoriaInsumo.setInsumoList(insumoListNew);
            categoriaInsumo = em.merge(categoriaInsumo);
            for (Insumo insumoListOldInsumo : insumoListOld) {
                if (!insumoListNew.contains(insumoListOldInsumo)) {
                    insumoListOldInsumo.setIdCategoria(null);
                    insumoListOldInsumo = em.merge(insumoListOldInsumo);
                }
            }
            for (Insumo insumoListNewInsumo : insumoListNew) {
                if (!insumoListOld.contains(insumoListNewInsumo)) {
                    CategoriaInsumo oldIdCategoriaOfInsumoListNewInsumo = insumoListNewInsumo.getIdCategoria();
                    insumoListNewInsumo.setIdCategoria(categoriaInsumo);
                    insumoListNewInsumo = em.merge(insumoListNewInsumo);
                    if (oldIdCategoriaOfInsumoListNewInsumo != null && !oldIdCategoriaOfInsumoListNewInsumo.equals(categoriaInsumo)) {
                        oldIdCategoriaOfInsumoListNewInsumo.getInsumoList().remove(insumoListNewInsumo);
                        oldIdCategoriaOfInsumoListNewInsumo = em.merge(oldIdCategoriaOfInsumoListNewInsumo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categoriaInsumo.getId();
                if (findCategoriaInsumo(id) == null) {
                    throw new NonexistentEntityException("The categoriaInsumo with id " + id + " no longer exists.");
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
            CategoriaInsumo categoriaInsumo;
            try {
                categoriaInsumo = em.getReference(CategoriaInsumo.class, id);
                categoriaInsumo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaInsumo with id " + id + " no longer exists.", enfe);
            }
            List<Insumo> insumoList = categoriaInsumo.getInsumoList();
            for (Insumo insumoListInsumo : insumoList) {
                insumoListInsumo.setIdCategoria(null);
                insumoListInsumo = em.merge(insumoListInsumo);
            }
            em.remove(categoriaInsumo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CategoriaInsumo> findCategoriaInsumoEntities() {
        return findCategoriaInsumoEntities(true, -1, -1);
    }

    public List<CategoriaInsumo> findCategoriaInsumoEntities(int maxResults, int firstResult) {
        return findCategoriaInsumoEntities(false, maxResults, firstResult);
    }

    private List<CategoriaInsumo> findCategoriaInsumoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CategoriaInsumo.class));
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

    public CategoriaInsumo findCategoriaInsumo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CategoriaInsumo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaInsumoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CategoriaInsumo> rt = cq.from(CategoriaInsumo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
