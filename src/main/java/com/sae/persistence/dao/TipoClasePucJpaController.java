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
import com.sae.persistence.domain.ClasePuc;
import com.sae.persistence.domain.TipoClasePuc;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoClasePucJpaController implements Serializable {

    public TipoClasePucJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoClasePuc tipoClasePuc) throws PreexistingEntityException, Exception {
        if (tipoClasePuc.getClasePucList() == null) {
            tipoClasePuc.setClasePucList(new ArrayList<ClasePuc>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ClasePuc> attachedClasePucList = new ArrayList<ClasePuc>();
            for (ClasePuc clasePucListClasePucToAttach : tipoClasePuc.getClasePucList()) {
                clasePucListClasePucToAttach = em.getReference(clasePucListClasePucToAttach.getClass(), clasePucListClasePucToAttach.getId());
                attachedClasePucList.add(clasePucListClasePucToAttach);
            }
            tipoClasePuc.setClasePucList(attachedClasePucList);
            em.persist(tipoClasePuc);
            for (ClasePuc clasePucListClasePuc : tipoClasePuc.getClasePucList()) {
                TipoClasePuc oldIdClaseOfClasePucListClasePuc = clasePucListClasePuc.getIdClase();
                clasePucListClasePuc.setIdClase(tipoClasePuc);
                clasePucListClasePuc = em.merge(clasePucListClasePuc);
                if (oldIdClaseOfClasePucListClasePuc != null) {
                    oldIdClaseOfClasePucListClasePuc.getClasePucList().remove(clasePucListClasePuc);
                    oldIdClaseOfClasePucListClasePuc = em.merge(oldIdClaseOfClasePucListClasePuc);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoClasePuc(tipoClasePuc.getId()) != null) {
                throw new PreexistingEntityException("TipoClasePuc " + tipoClasePuc + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoClasePuc tipoClasePuc) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoClasePuc persistentTipoClasePuc = em.find(TipoClasePuc.class, tipoClasePuc.getId());
            List<ClasePuc> clasePucListOld = persistentTipoClasePuc.getClasePucList();
            List<ClasePuc> clasePucListNew = tipoClasePuc.getClasePucList();
            List<ClasePuc> attachedClasePucListNew = new ArrayList<ClasePuc>();
            for (ClasePuc clasePucListNewClasePucToAttach : clasePucListNew) {
                clasePucListNewClasePucToAttach = em.getReference(clasePucListNewClasePucToAttach.getClass(), clasePucListNewClasePucToAttach.getId());
                attachedClasePucListNew.add(clasePucListNewClasePucToAttach);
            }
            clasePucListNew = attachedClasePucListNew;
            tipoClasePuc.setClasePucList(clasePucListNew);
            tipoClasePuc = em.merge(tipoClasePuc);
            for (ClasePuc clasePucListOldClasePuc : clasePucListOld) {
                if (!clasePucListNew.contains(clasePucListOldClasePuc)) {
                    clasePucListOldClasePuc.setIdClase(null);
                    clasePucListOldClasePuc = em.merge(clasePucListOldClasePuc);
                }
            }
            for (ClasePuc clasePucListNewClasePuc : clasePucListNew) {
                if (!clasePucListOld.contains(clasePucListNewClasePuc)) {
                    TipoClasePuc oldIdClaseOfClasePucListNewClasePuc = clasePucListNewClasePuc.getIdClase();
                    clasePucListNewClasePuc.setIdClase(tipoClasePuc);
                    clasePucListNewClasePuc = em.merge(clasePucListNewClasePuc);
                    if (oldIdClaseOfClasePucListNewClasePuc != null && !oldIdClaseOfClasePucListNewClasePuc.equals(tipoClasePuc)) {
                        oldIdClaseOfClasePucListNewClasePuc.getClasePucList().remove(clasePucListNewClasePuc);
                        oldIdClaseOfClasePucListNewClasePuc = em.merge(oldIdClaseOfClasePucListNewClasePuc);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoClasePuc.getId();
                if (findTipoClasePuc(id) == null) {
                    throw new NonexistentEntityException("The tipoClasePuc with id " + id + " no longer exists.");
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
            TipoClasePuc tipoClasePuc;
            try {
                tipoClasePuc = em.getReference(TipoClasePuc.class, id);
                tipoClasePuc.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoClasePuc with id " + id + " no longer exists.", enfe);
            }
            List<ClasePuc> clasePucList = tipoClasePuc.getClasePucList();
            for (ClasePuc clasePucListClasePuc : clasePucList) {
                clasePucListClasePuc.setIdClase(null);
                clasePucListClasePuc = em.merge(clasePucListClasePuc);
            }
            em.remove(tipoClasePuc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoClasePuc> findTipoClasePucEntities() {
        return findTipoClasePucEntities(true, -1, -1);
    }

    public List<TipoClasePuc> findTipoClasePucEntities(int maxResults, int firstResult) {
        return findTipoClasePucEntities(false, maxResults, firstResult);
    }

    private List<TipoClasePuc> findTipoClasePucEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoClasePuc.class));
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

    public TipoClasePuc findTipoClasePuc(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoClasePuc.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoClasePucCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoClasePuc> rt = cq.from(TipoClasePuc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
