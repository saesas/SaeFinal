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
import com.sae.persistence.domain.MotivoDerechoPeticion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class MotivoDerechoPeticionJpaController implements Serializable {

    public MotivoDerechoPeticionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MotivoDerechoPeticion motivoDerechoPeticion) throws PreexistingEntityException, Exception {
        if (motivoDerechoPeticion.getDerechoPeticionList() == null) {
            motivoDerechoPeticion.setDerechoPeticionList(new ArrayList<DerechoPeticion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<DerechoPeticion> attachedDerechoPeticionList = new ArrayList<DerechoPeticion>();
            for (DerechoPeticion derechoPeticionListDerechoPeticionToAttach : motivoDerechoPeticion.getDerechoPeticionList()) {
                derechoPeticionListDerechoPeticionToAttach = em.getReference(derechoPeticionListDerechoPeticionToAttach.getClass(), derechoPeticionListDerechoPeticionToAttach.getId());
                attachedDerechoPeticionList.add(derechoPeticionListDerechoPeticionToAttach);
            }
            motivoDerechoPeticion.setDerechoPeticionList(attachedDerechoPeticionList);
            em.persist(motivoDerechoPeticion);
            for (DerechoPeticion derechoPeticionListDerechoPeticion : motivoDerechoPeticion.getDerechoPeticionList()) {
                MotivoDerechoPeticion oldIdMotivoOfDerechoPeticionListDerechoPeticion = derechoPeticionListDerechoPeticion.getIdMotivo();
                derechoPeticionListDerechoPeticion.setIdMotivo(motivoDerechoPeticion);
                derechoPeticionListDerechoPeticion = em.merge(derechoPeticionListDerechoPeticion);
                if (oldIdMotivoOfDerechoPeticionListDerechoPeticion != null) {
                    oldIdMotivoOfDerechoPeticionListDerechoPeticion.getDerechoPeticionList().remove(derechoPeticionListDerechoPeticion);
                    oldIdMotivoOfDerechoPeticionListDerechoPeticion = em.merge(oldIdMotivoOfDerechoPeticionListDerechoPeticion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMotivoDerechoPeticion(motivoDerechoPeticion.getId()) != null) {
                throw new PreexistingEntityException("MotivoDerechoPeticion " + motivoDerechoPeticion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MotivoDerechoPeticion motivoDerechoPeticion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MotivoDerechoPeticion persistentMotivoDerechoPeticion = em.find(MotivoDerechoPeticion.class, motivoDerechoPeticion.getId());
            List<DerechoPeticion> derechoPeticionListOld = persistentMotivoDerechoPeticion.getDerechoPeticionList();
            List<DerechoPeticion> derechoPeticionListNew = motivoDerechoPeticion.getDerechoPeticionList();
            List<DerechoPeticion> attachedDerechoPeticionListNew = new ArrayList<DerechoPeticion>();
            for (DerechoPeticion derechoPeticionListNewDerechoPeticionToAttach : derechoPeticionListNew) {
                derechoPeticionListNewDerechoPeticionToAttach = em.getReference(derechoPeticionListNewDerechoPeticionToAttach.getClass(), derechoPeticionListNewDerechoPeticionToAttach.getId());
                attachedDerechoPeticionListNew.add(derechoPeticionListNewDerechoPeticionToAttach);
            }
            derechoPeticionListNew = attachedDerechoPeticionListNew;
            motivoDerechoPeticion.setDerechoPeticionList(derechoPeticionListNew);
            motivoDerechoPeticion = em.merge(motivoDerechoPeticion);
            for (DerechoPeticion derechoPeticionListOldDerechoPeticion : derechoPeticionListOld) {
                if (!derechoPeticionListNew.contains(derechoPeticionListOldDerechoPeticion)) {
                    derechoPeticionListOldDerechoPeticion.setIdMotivo(null);
                    derechoPeticionListOldDerechoPeticion = em.merge(derechoPeticionListOldDerechoPeticion);
                }
            }
            for (DerechoPeticion derechoPeticionListNewDerechoPeticion : derechoPeticionListNew) {
                if (!derechoPeticionListOld.contains(derechoPeticionListNewDerechoPeticion)) {
                    MotivoDerechoPeticion oldIdMotivoOfDerechoPeticionListNewDerechoPeticion = derechoPeticionListNewDerechoPeticion.getIdMotivo();
                    derechoPeticionListNewDerechoPeticion.setIdMotivo(motivoDerechoPeticion);
                    derechoPeticionListNewDerechoPeticion = em.merge(derechoPeticionListNewDerechoPeticion);
                    if (oldIdMotivoOfDerechoPeticionListNewDerechoPeticion != null && !oldIdMotivoOfDerechoPeticionListNewDerechoPeticion.equals(motivoDerechoPeticion)) {
                        oldIdMotivoOfDerechoPeticionListNewDerechoPeticion.getDerechoPeticionList().remove(derechoPeticionListNewDerechoPeticion);
                        oldIdMotivoOfDerechoPeticionListNewDerechoPeticion = em.merge(oldIdMotivoOfDerechoPeticionListNewDerechoPeticion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = motivoDerechoPeticion.getId();
                if (findMotivoDerechoPeticion(id) == null) {
                    throw new NonexistentEntityException("The motivoDerechoPeticion with id " + id + " no longer exists.");
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
            MotivoDerechoPeticion motivoDerechoPeticion;
            try {
                motivoDerechoPeticion = em.getReference(MotivoDerechoPeticion.class, id);
                motivoDerechoPeticion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The motivoDerechoPeticion with id " + id + " no longer exists.", enfe);
            }
            List<DerechoPeticion> derechoPeticionList = motivoDerechoPeticion.getDerechoPeticionList();
            for (DerechoPeticion derechoPeticionListDerechoPeticion : derechoPeticionList) {
                derechoPeticionListDerechoPeticion.setIdMotivo(null);
                derechoPeticionListDerechoPeticion = em.merge(derechoPeticionListDerechoPeticion);
            }
            em.remove(motivoDerechoPeticion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MotivoDerechoPeticion> findMotivoDerechoPeticionEntities() {
        return findMotivoDerechoPeticionEntities(true, -1, -1);
    }

    public List<MotivoDerechoPeticion> findMotivoDerechoPeticionEntities(int maxResults, int firstResult) {
        return findMotivoDerechoPeticionEntities(false, maxResults, firstResult);
    }

    private List<MotivoDerechoPeticion> findMotivoDerechoPeticionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MotivoDerechoPeticion.class));
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

    public MotivoDerechoPeticion findMotivoDerechoPeticion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MotivoDerechoPeticion.class, id);
        } finally {
            em.close();
        }
    }

    public int getMotivoDerechoPeticionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MotivoDerechoPeticion> rt = cq.from(MotivoDerechoPeticion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
