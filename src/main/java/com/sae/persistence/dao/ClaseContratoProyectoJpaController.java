/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.ClaseContratoProyecto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.TipoContratoProyecto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ClaseContratoProyectoJpaController implements Serializable {

    public ClaseContratoProyectoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClaseContratoProyecto claseContratoProyecto) throws PreexistingEntityException, Exception {
        if (claseContratoProyecto.getTipoContratoProyectoList() == null) {
            claseContratoProyecto.setTipoContratoProyectoList(new ArrayList<TipoContratoProyecto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<TipoContratoProyecto> attachedTipoContratoProyectoList = new ArrayList<TipoContratoProyecto>();
            for (TipoContratoProyecto tipoContratoProyectoListTipoContratoProyectoToAttach : claseContratoProyecto.getTipoContratoProyectoList()) {
                tipoContratoProyectoListTipoContratoProyectoToAttach = em.getReference(tipoContratoProyectoListTipoContratoProyectoToAttach.getClass(), tipoContratoProyectoListTipoContratoProyectoToAttach.getId());
                attachedTipoContratoProyectoList.add(tipoContratoProyectoListTipoContratoProyectoToAttach);
            }
            claseContratoProyecto.setTipoContratoProyectoList(attachedTipoContratoProyectoList);
            em.persist(claseContratoProyecto);
            for (TipoContratoProyecto tipoContratoProyectoListTipoContratoProyecto : claseContratoProyecto.getTipoContratoProyectoList()) {
                ClaseContratoProyecto oldIdClaseOfTipoContratoProyectoListTipoContratoProyecto = tipoContratoProyectoListTipoContratoProyecto.getIdClase();
                tipoContratoProyectoListTipoContratoProyecto.setIdClase(claseContratoProyecto);
                tipoContratoProyectoListTipoContratoProyecto = em.merge(tipoContratoProyectoListTipoContratoProyecto);
                if (oldIdClaseOfTipoContratoProyectoListTipoContratoProyecto != null) {
                    oldIdClaseOfTipoContratoProyectoListTipoContratoProyecto.getTipoContratoProyectoList().remove(tipoContratoProyectoListTipoContratoProyecto);
                    oldIdClaseOfTipoContratoProyectoListTipoContratoProyecto = em.merge(oldIdClaseOfTipoContratoProyectoListTipoContratoProyecto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClaseContratoProyecto(claseContratoProyecto.getId()) != null) {
                throw new PreexistingEntityException("ClaseContratoProyecto " + claseContratoProyecto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClaseContratoProyecto claseContratoProyecto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClaseContratoProyecto persistentClaseContratoProyecto = em.find(ClaseContratoProyecto.class, claseContratoProyecto.getId());
            List<TipoContratoProyecto> tipoContratoProyectoListOld = persistentClaseContratoProyecto.getTipoContratoProyectoList();
            List<TipoContratoProyecto> tipoContratoProyectoListNew = claseContratoProyecto.getTipoContratoProyectoList();
            List<TipoContratoProyecto> attachedTipoContratoProyectoListNew = new ArrayList<TipoContratoProyecto>();
            for (TipoContratoProyecto tipoContratoProyectoListNewTipoContratoProyectoToAttach : tipoContratoProyectoListNew) {
                tipoContratoProyectoListNewTipoContratoProyectoToAttach = em.getReference(tipoContratoProyectoListNewTipoContratoProyectoToAttach.getClass(), tipoContratoProyectoListNewTipoContratoProyectoToAttach.getId());
                attachedTipoContratoProyectoListNew.add(tipoContratoProyectoListNewTipoContratoProyectoToAttach);
            }
            tipoContratoProyectoListNew = attachedTipoContratoProyectoListNew;
            claseContratoProyecto.setTipoContratoProyectoList(tipoContratoProyectoListNew);
            claseContratoProyecto = em.merge(claseContratoProyecto);
            for (TipoContratoProyecto tipoContratoProyectoListOldTipoContratoProyecto : tipoContratoProyectoListOld) {
                if (!tipoContratoProyectoListNew.contains(tipoContratoProyectoListOldTipoContratoProyecto)) {
                    tipoContratoProyectoListOldTipoContratoProyecto.setIdClase(null);
                    tipoContratoProyectoListOldTipoContratoProyecto = em.merge(tipoContratoProyectoListOldTipoContratoProyecto);
                }
            }
            for (TipoContratoProyecto tipoContratoProyectoListNewTipoContratoProyecto : tipoContratoProyectoListNew) {
                if (!tipoContratoProyectoListOld.contains(tipoContratoProyectoListNewTipoContratoProyecto)) {
                    ClaseContratoProyecto oldIdClaseOfTipoContratoProyectoListNewTipoContratoProyecto = tipoContratoProyectoListNewTipoContratoProyecto.getIdClase();
                    tipoContratoProyectoListNewTipoContratoProyecto.setIdClase(claseContratoProyecto);
                    tipoContratoProyectoListNewTipoContratoProyecto = em.merge(tipoContratoProyectoListNewTipoContratoProyecto);
                    if (oldIdClaseOfTipoContratoProyectoListNewTipoContratoProyecto != null && !oldIdClaseOfTipoContratoProyectoListNewTipoContratoProyecto.equals(claseContratoProyecto)) {
                        oldIdClaseOfTipoContratoProyectoListNewTipoContratoProyecto.getTipoContratoProyectoList().remove(tipoContratoProyectoListNewTipoContratoProyecto);
                        oldIdClaseOfTipoContratoProyectoListNewTipoContratoProyecto = em.merge(oldIdClaseOfTipoContratoProyectoListNewTipoContratoProyecto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = claseContratoProyecto.getId();
                if (findClaseContratoProyecto(id) == null) {
                    throw new NonexistentEntityException("The claseContratoProyecto with id " + id + " no longer exists.");
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
            ClaseContratoProyecto claseContratoProyecto;
            try {
                claseContratoProyecto = em.getReference(ClaseContratoProyecto.class, id);
                claseContratoProyecto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The claseContratoProyecto with id " + id + " no longer exists.", enfe);
            }
            List<TipoContratoProyecto> tipoContratoProyectoList = claseContratoProyecto.getTipoContratoProyectoList();
            for (TipoContratoProyecto tipoContratoProyectoListTipoContratoProyecto : tipoContratoProyectoList) {
                tipoContratoProyectoListTipoContratoProyecto.setIdClase(null);
                tipoContratoProyectoListTipoContratoProyecto = em.merge(tipoContratoProyectoListTipoContratoProyecto);
            }
            em.remove(claseContratoProyecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClaseContratoProyecto> findClaseContratoProyectoEntities() {
        return findClaseContratoProyectoEntities(true, -1, -1);
    }

    public List<ClaseContratoProyecto> findClaseContratoProyectoEntities(int maxResults, int firstResult) {
        return findClaseContratoProyectoEntities(false, maxResults, firstResult);
    }

    private List<ClaseContratoProyecto> findClaseContratoProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClaseContratoProyecto.class));
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

    public ClaseContratoProyecto findClaseContratoProyecto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClaseContratoProyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getClaseContratoProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClaseContratoProyecto> rt = cq.from(ClaseContratoProyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
