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
import com.sae.persistence.domain.Clase;
import com.sae.persistence.domain.TipoClase;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoClaseJpaController implements Serializable {

    public TipoClaseJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoClase tipoClase) throws PreexistingEntityException, Exception {
        if (tipoClase.getClaseList() == null) {
            tipoClase.setClaseList(new ArrayList<Clase>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Clase> attachedClaseList = new ArrayList<Clase>();
            for (Clase claseListClaseToAttach : tipoClase.getClaseList()) {
                claseListClaseToAttach = em.getReference(claseListClaseToAttach.getClass(), claseListClaseToAttach.getId());
                attachedClaseList.add(claseListClaseToAttach);
            }
            tipoClase.setClaseList(attachedClaseList);
            em.persist(tipoClase);
            for (Clase claseListClase : tipoClase.getClaseList()) {
                TipoClase oldIdTipoOfClaseListClase = claseListClase.getIdTipo();
                claseListClase.setIdTipo(tipoClase);
                claseListClase = em.merge(claseListClase);
                if (oldIdTipoOfClaseListClase != null) {
                    oldIdTipoOfClaseListClase.getClaseList().remove(claseListClase);
                    oldIdTipoOfClaseListClase = em.merge(oldIdTipoOfClaseListClase);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoClase(tipoClase.getId()) != null) {
                throw new PreexistingEntityException("TipoClase " + tipoClase + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoClase tipoClase) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoClase persistentTipoClase = em.find(TipoClase.class, tipoClase.getId());
            List<Clase> claseListOld = persistentTipoClase.getClaseList();
            List<Clase> claseListNew = tipoClase.getClaseList();
            List<Clase> attachedClaseListNew = new ArrayList<Clase>();
            for (Clase claseListNewClaseToAttach : claseListNew) {
                claseListNewClaseToAttach = em.getReference(claseListNewClaseToAttach.getClass(), claseListNewClaseToAttach.getId());
                attachedClaseListNew.add(claseListNewClaseToAttach);
            }
            claseListNew = attachedClaseListNew;
            tipoClase.setClaseList(claseListNew);
            tipoClase = em.merge(tipoClase);
            for (Clase claseListOldClase : claseListOld) {
                if (!claseListNew.contains(claseListOldClase)) {
                    claseListOldClase.setIdTipo(null);
                    claseListOldClase = em.merge(claseListOldClase);
                }
            }
            for (Clase claseListNewClase : claseListNew) {
                if (!claseListOld.contains(claseListNewClase)) {
                    TipoClase oldIdTipoOfClaseListNewClase = claseListNewClase.getIdTipo();
                    claseListNewClase.setIdTipo(tipoClase);
                    claseListNewClase = em.merge(claseListNewClase);
                    if (oldIdTipoOfClaseListNewClase != null && !oldIdTipoOfClaseListNewClase.equals(tipoClase)) {
                        oldIdTipoOfClaseListNewClase.getClaseList().remove(claseListNewClase);
                        oldIdTipoOfClaseListNewClase = em.merge(oldIdTipoOfClaseListNewClase);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoClase.getId();
                if (findTipoClase(id) == null) {
                    throw new NonexistentEntityException("The tipoClase with id " + id + " no longer exists.");
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
            TipoClase tipoClase;
            try {
                tipoClase = em.getReference(TipoClase.class, id);
                tipoClase.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoClase with id " + id + " no longer exists.", enfe);
            }
            List<Clase> claseList = tipoClase.getClaseList();
            for (Clase claseListClase : claseList) {
                claseListClase.setIdTipo(null);
                claseListClase = em.merge(claseListClase);
            }
            em.remove(tipoClase);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoClase> findTipoClaseEntities() {
        return findTipoClaseEntities(true, -1, -1);
    }

    public List<TipoClase> findTipoClaseEntities(int maxResults, int firstResult) {
        return findTipoClaseEntities(false, maxResults, firstResult);
    }

    private List<TipoClase> findTipoClaseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoClase.class));
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

    public TipoClase findTipoClase(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoClase.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoClaseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoClase> rt = cq.from(TipoClase.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
