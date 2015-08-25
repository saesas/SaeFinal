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
import com.sae.persistence.domain.DerechoPeticion;
import com.sae.persistence.domain.OrigenDerechoPeticion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class OrigenDerechoPeticionJpaController implements Serializable {

    public OrigenDerechoPeticionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrigenDerechoPeticion origenDerechoPeticion) throws PreexistingEntityException, Exception {
        if (origenDerechoPeticion.getDerechoPeticionList() == null) {
            origenDerechoPeticion.setDerechoPeticionList(new ArrayList<DerechoPeticion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<DerechoPeticion> attachedDerechoPeticionList = new ArrayList<DerechoPeticion>();
            for (DerechoPeticion derechoPeticionListDerechoPeticionToAttach : origenDerechoPeticion.getDerechoPeticionList()) {
                derechoPeticionListDerechoPeticionToAttach = em.getReference(derechoPeticionListDerechoPeticionToAttach.getClass(), derechoPeticionListDerechoPeticionToAttach.getId());
                attachedDerechoPeticionList.add(derechoPeticionListDerechoPeticionToAttach);
            }
            origenDerechoPeticion.setDerechoPeticionList(attachedDerechoPeticionList);
            em.persist(origenDerechoPeticion);
            for (DerechoPeticion derechoPeticionListDerechoPeticion : origenDerechoPeticion.getDerechoPeticionList()) {
                OrigenDerechoPeticion oldIdOrigenOfDerechoPeticionListDerechoPeticion = derechoPeticionListDerechoPeticion.getIdOrigen();
                derechoPeticionListDerechoPeticion.setIdOrigen(origenDerechoPeticion);
                derechoPeticionListDerechoPeticion = em.merge(derechoPeticionListDerechoPeticion);
                if (oldIdOrigenOfDerechoPeticionListDerechoPeticion != null) {
                    oldIdOrigenOfDerechoPeticionListDerechoPeticion.getDerechoPeticionList().remove(derechoPeticionListDerechoPeticion);
                    oldIdOrigenOfDerechoPeticionListDerechoPeticion = em.merge(oldIdOrigenOfDerechoPeticionListDerechoPeticion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrigenDerechoPeticion(origenDerechoPeticion.getId()) != null) {
                throw new PreexistingEntityException("OrigenDerechoPeticion " + origenDerechoPeticion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrigenDerechoPeticion origenDerechoPeticion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrigenDerechoPeticion persistentOrigenDerechoPeticion = em.find(OrigenDerechoPeticion.class, origenDerechoPeticion.getId());
            List<DerechoPeticion> derechoPeticionListOld = persistentOrigenDerechoPeticion.getDerechoPeticionList();
            List<DerechoPeticion> derechoPeticionListNew = origenDerechoPeticion.getDerechoPeticionList();
            List<DerechoPeticion> attachedDerechoPeticionListNew = new ArrayList<DerechoPeticion>();
            for (DerechoPeticion derechoPeticionListNewDerechoPeticionToAttach : derechoPeticionListNew) {
                derechoPeticionListNewDerechoPeticionToAttach = em.getReference(derechoPeticionListNewDerechoPeticionToAttach.getClass(), derechoPeticionListNewDerechoPeticionToAttach.getId());
                attachedDerechoPeticionListNew.add(derechoPeticionListNewDerechoPeticionToAttach);
            }
            derechoPeticionListNew = attachedDerechoPeticionListNew;
            origenDerechoPeticion.setDerechoPeticionList(derechoPeticionListNew);
            origenDerechoPeticion = em.merge(origenDerechoPeticion);
            for (DerechoPeticion derechoPeticionListOldDerechoPeticion : derechoPeticionListOld) {
                if (!derechoPeticionListNew.contains(derechoPeticionListOldDerechoPeticion)) {
                    derechoPeticionListOldDerechoPeticion.setIdOrigen(null);
                    derechoPeticionListOldDerechoPeticion = em.merge(derechoPeticionListOldDerechoPeticion);
                }
            }
            for (DerechoPeticion derechoPeticionListNewDerechoPeticion : derechoPeticionListNew) {
                if (!derechoPeticionListOld.contains(derechoPeticionListNewDerechoPeticion)) {
                    OrigenDerechoPeticion oldIdOrigenOfDerechoPeticionListNewDerechoPeticion = derechoPeticionListNewDerechoPeticion.getIdOrigen();
                    derechoPeticionListNewDerechoPeticion.setIdOrigen(origenDerechoPeticion);
                    derechoPeticionListNewDerechoPeticion = em.merge(derechoPeticionListNewDerechoPeticion);
                    if (oldIdOrigenOfDerechoPeticionListNewDerechoPeticion != null && !oldIdOrigenOfDerechoPeticionListNewDerechoPeticion.equals(origenDerechoPeticion)) {
                        oldIdOrigenOfDerechoPeticionListNewDerechoPeticion.getDerechoPeticionList().remove(derechoPeticionListNewDerechoPeticion);
                        oldIdOrigenOfDerechoPeticionListNewDerechoPeticion = em.merge(oldIdOrigenOfDerechoPeticionListNewDerechoPeticion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = origenDerechoPeticion.getId();
                if (findOrigenDerechoPeticion(id) == null) {
                    throw new NonexistentEntityException("The origenDerechoPeticion with id " + id + " no longer exists.");
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
            OrigenDerechoPeticion origenDerechoPeticion;
            try {
                origenDerechoPeticion = em.getReference(OrigenDerechoPeticion.class, id);
                origenDerechoPeticion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The origenDerechoPeticion with id " + id + " no longer exists.", enfe);
            }
            List<DerechoPeticion> derechoPeticionList = origenDerechoPeticion.getDerechoPeticionList();
            for (DerechoPeticion derechoPeticionListDerechoPeticion : derechoPeticionList) {
                derechoPeticionListDerechoPeticion.setIdOrigen(null);
                derechoPeticionListDerechoPeticion = em.merge(derechoPeticionListDerechoPeticion);
            }
            em.remove(origenDerechoPeticion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrigenDerechoPeticion> findOrigenDerechoPeticionEntities() {
        return findOrigenDerechoPeticionEntities(true, -1, -1);
    }

    public List<OrigenDerechoPeticion> findOrigenDerechoPeticionEntities(int maxResults, int firstResult) {
        return findOrigenDerechoPeticionEntities(false, maxResults, firstResult);
    }

    private List<OrigenDerechoPeticion> findOrigenDerechoPeticionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrigenDerechoPeticion.class));
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

    public OrigenDerechoPeticion findOrigenDerechoPeticion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrigenDerechoPeticion.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrigenDerechoPeticionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrigenDerechoPeticion> rt = cq.from(OrigenDerechoPeticion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
