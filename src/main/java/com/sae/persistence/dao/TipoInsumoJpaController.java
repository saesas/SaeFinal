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
import com.sae.persistence.domain.Insumo;
import com.sae.persistence.domain.TipoInsumo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoInsumoJpaController implements Serializable {

    public TipoInsumoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoInsumo tipoInsumo) throws PreexistingEntityException, Exception {
        if (tipoInsumo.getInsumoList() == null) {
            tipoInsumo.setInsumoList(new ArrayList<Insumo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Insumo> attachedInsumoList = new ArrayList<Insumo>();
            for (Insumo insumoListInsumoToAttach : tipoInsumo.getInsumoList()) {
                insumoListInsumoToAttach = em.getReference(insumoListInsumoToAttach.getClass(), insumoListInsumoToAttach.getId());
                attachedInsumoList.add(insumoListInsumoToAttach);
            }
            tipoInsumo.setInsumoList(attachedInsumoList);
            em.persist(tipoInsumo);
            for (Insumo insumoListInsumo : tipoInsumo.getInsumoList()) {
                TipoInsumo oldIdTipoInsumoOfInsumoListInsumo = insumoListInsumo.getIdTipoInsumo();
                insumoListInsumo.setIdTipoInsumo(tipoInsumo);
                insumoListInsumo = em.merge(insumoListInsumo);
                if (oldIdTipoInsumoOfInsumoListInsumo != null) {
                    oldIdTipoInsumoOfInsumoListInsumo.getInsumoList().remove(insumoListInsumo);
                    oldIdTipoInsumoOfInsumoListInsumo = em.merge(oldIdTipoInsumoOfInsumoListInsumo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoInsumo(tipoInsumo.getId()) != null) {
                throw new PreexistingEntityException("TipoInsumo " + tipoInsumo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoInsumo tipoInsumo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoInsumo persistentTipoInsumo = em.find(TipoInsumo.class, tipoInsumo.getId());
            List<Insumo> insumoListOld = persistentTipoInsumo.getInsumoList();
            List<Insumo> insumoListNew = tipoInsumo.getInsumoList();
            List<Insumo> attachedInsumoListNew = new ArrayList<Insumo>();
            for (Insumo insumoListNewInsumoToAttach : insumoListNew) {
                insumoListNewInsumoToAttach = em.getReference(insumoListNewInsumoToAttach.getClass(), insumoListNewInsumoToAttach.getId());
                attachedInsumoListNew.add(insumoListNewInsumoToAttach);
            }
            insumoListNew = attachedInsumoListNew;
            tipoInsumo.setInsumoList(insumoListNew);
            tipoInsumo = em.merge(tipoInsumo);
            for (Insumo insumoListOldInsumo : insumoListOld) {
                if (!insumoListNew.contains(insumoListOldInsumo)) {
                    insumoListOldInsumo.setIdTipoInsumo(null);
                    insumoListOldInsumo = em.merge(insumoListOldInsumo);
                }
            }
            for (Insumo insumoListNewInsumo : insumoListNew) {
                if (!insumoListOld.contains(insumoListNewInsumo)) {
                    TipoInsumo oldIdTipoInsumoOfInsumoListNewInsumo = insumoListNewInsumo.getIdTipoInsumo();
                    insumoListNewInsumo.setIdTipoInsumo(tipoInsumo);
                    insumoListNewInsumo = em.merge(insumoListNewInsumo);
                    if (oldIdTipoInsumoOfInsumoListNewInsumo != null && !oldIdTipoInsumoOfInsumoListNewInsumo.equals(tipoInsumo)) {
                        oldIdTipoInsumoOfInsumoListNewInsumo.getInsumoList().remove(insumoListNewInsumo);
                        oldIdTipoInsumoOfInsumoListNewInsumo = em.merge(oldIdTipoInsumoOfInsumoListNewInsumo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoInsumo.getId();
                if (findTipoInsumo(id) == null) {
                    throw new NonexistentEntityException("The tipoInsumo with id " + id + " no longer exists.");
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
            TipoInsumo tipoInsumo;
            try {
                tipoInsumo = em.getReference(TipoInsumo.class, id);
                tipoInsumo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoInsumo with id " + id + " no longer exists.", enfe);
            }
            List<Insumo> insumoList = tipoInsumo.getInsumoList();
            for (Insumo insumoListInsumo : insumoList) {
                insumoListInsumo.setIdTipoInsumo(null);
                insumoListInsumo = em.merge(insumoListInsumo);
            }
            em.remove(tipoInsumo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoInsumo> findTipoInsumoEntities() {
        return findTipoInsumoEntities(true, -1, -1);
    }

    public List<TipoInsumo> findTipoInsumoEntities(int maxResults, int firstResult) {
        return findTipoInsumoEntities(false, maxResults, firstResult);
    }

    private List<TipoInsumo> findTipoInsumoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoInsumo.class));
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

    public TipoInsumo findTipoInsumo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoInsumo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoInsumoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoInsumo> rt = cq.from(TipoInsumo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
