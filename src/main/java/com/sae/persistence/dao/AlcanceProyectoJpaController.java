/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.AlcanceProyecto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Proyecto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class AlcanceProyectoJpaController implements Serializable {

    public AlcanceProyectoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AlcanceProyecto alcanceProyecto) throws PreexistingEntityException, Exception {
        if (alcanceProyecto.getProyectoList() == null) {
            alcanceProyecto.setProyectoList(new ArrayList<Proyecto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Proyecto> attachedProyectoList = new ArrayList<Proyecto>();
            for (Proyecto proyectoListProyectoToAttach : alcanceProyecto.getProyectoList()) {
                proyectoListProyectoToAttach = em.getReference(proyectoListProyectoToAttach.getClass(), proyectoListProyectoToAttach.getId());
                attachedProyectoList.add(proyectoListProyectoToAttach);
            }
            alcanceProyecto.setProyectoList(attachedProyectoList);
            em.persist(alcanceProyecto);
            for (Proyecto proyectoListProyecto : alcanceProyecto.getProyectoList()) {
                AlcanceProyecto oldIdAlcanceOfProyectoListProyecto = proyectoListProyecto.getIdAlcance();
                proyectoListProyecto.setIdAlcance(alcanceProyecto);
                proyectoListProyecto = em.merge(proyectoListProyecto);
                if (oldIdAlcanceOfProyectoListProyecto != null) {
                    oldIdAlcanceOfProyectoListProyecto.getProyectoList().remove(proyectoListProyecto);
                    oldIdAlcanceOfProyectoListProyecto = em.merge(oldIdAlcanceOfProyectoListProyecto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAlcanceProyecto(alcanceProyecto.getId()) != null) {
                throw new PreexistingEntityException("AlcanceProyecto " + alcanceProyecto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AlcanceProyecto alcanceProyecto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AlcanceProyecto persistentAlcanceProyecto = em.find(AlcanceProyecto.class, alcanceProyecto.getId());
            List<Proyecto> proyectoListOld = persistentAlcanceProyecto.getProyectoList();
            List<Proyecto> proyectoListNew = alcanceProyecto.getProyectoList();
            List<Proyecto> attachedProyectoListNew = new ArrayList<Proyecto>();
            for (Proyecto proyectoListNewProyectoToAttach : proyectoListNew) {
                proyectoListNewProyectoToAttach = em.getReference(proyectoListNewProyectoToAttach.getClass(), proyectoListNewProyectoToAttach.getId());
                attachedProyectoListNew.add(proyectoListNewProyectoToAttach);
            }
            proyectoListNew = attachedProyectoListNew;
            alcanceProyecto.setProyectoList(proyectoListNew);
            alcanceProyecto = em.merge(alcanceProyecto);
            for (Proyecto proyectoListOldProyecto : proyectoListOld) {
                if (!proyectoListNew.contains(proyectoListOldProyecto)) {
                    proyectoListOldProyecto.setIdAlcance(null);
                    proyectoListOldProyecto = em.merge(proyectoListOldProyecto);
                }
            }
            for (Proyecto proyectoListNewProyecto : proyectoListNew) {
                if (!proyectoListOld.contains(proyectoListNewProyecto)) {
                    AlcanceProyecto oldIdAlcanceOfProyectoListNewProyecto = proyectoListNewProyecto.getIdAlcance();
                    proyectoListNewProyecto.setIdAlcance(alcanceProyecto);
                    proyectoListNewProyecto = em.merge(proyectoListNewProyecto);
                    if (oldIdAlcanceOfProyectoListNewProyecto != null && !oldIdAlcanceOfProyectoListNewProyecto.equals(alcanceProyecto)) {
                        oldIdAlcanceOfProyectoListNewProyecto.getProyectoList().remove(proyectoListNewProyecto);
                        oldIdAlcanceOfProyectoListNewProyecto = em.merge(oldIdAlcanceOfProyectoListNewProyecto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = alcanceProyecto.getId();
                if (findAlcanceProyecto(id) == null) {
                    throw new NonexistentEntityException("The alcanceProyecto with id " + id + " no longer exists.");
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
            AlcanceProyecto alcanceProyecto;
            try {
                alcanceProyecto = em.getReference(AlcanceProyecto.class, id);
                alcanceProyecto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alcanceProyecto with id " + id + " no longer exists.", enfe);
            }
            List<Proyecto> proyectoList = alcanceProyecto.getProyectoList();
            for (Proyecto proyectoListProyecto : proyectoList) {
                proyectoListProyecto.setIdAlcance(null);
                proyectoListProyecto = em.merge(proyectoListProyecto);
            }
            em.remove(alcanceProyecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AlcanceProyecto> findAlcanceProyectoEntities() {
        return findAlcanceProyectoEntities(true, -1, -1);
    }

    public List<AlcanceProyecto> findAlcanceProyectoEntities(int maxResults, int firstResult) {
        return findAlcanceProyectoEntities(false, maxResults, firstResult);
    }

    private List<AlcanceProyecto> findAlcanceProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AlcanceProyecto.class));
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

    public AlcanceProyecto findAlcanceProyecto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AlcanceProyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlcanceProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AlcanceProyecto> rt = cq.from(AlcanceProyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
