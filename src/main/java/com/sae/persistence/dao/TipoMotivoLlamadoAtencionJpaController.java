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
import com.sae.persistence.domain.MotivoLlamadoAtencion;
import com.sae.persistence.domain.TipoMotivoLlamadoAtencion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoMotivoLlamadoAtencionJpaController implements Serializable {

    public TipoMotivoLlamadoAtencionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoMotivoLlamadoAtencion tipoMotivoLlamadoAtencion) throws PreexistingEntityException, Exception {
        if (tipoMotivoLlamadoAtencion.getMotivoLlamadoAtencionList() == null) {
            tipoMotivoLlamadoAtencion.setMotivoLlamadoAtencionList(new ArrayList<MotivoLlamadoAtencion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<MotivoLlamadoAtencion> attachedMotivoLlamadoAtencionList = new ArrayList<MotivoLlamadoAtencion>();
            for (MotivoLlamadoAtencion motivoLlamadoAtencionListMotivoLlamadoAtencionToAttach : tipoMotivoLlamadoAtencion.getMotivoLlamadoAtencionList()) {
                motivoLlamadoAtencionListMotivoLlamadoAtencionToAttach = em.getReference(motivoLlamadoAtencionListMotivoLlamadoAtencionToAttach.getClass(), motivoLlamadoAtencionListMotivoLlamadoAtencionToAttach.getId());
                attachedMotivoLlamadoAtencionList.add(motivoLlamadoAtencionListMotivoLlamadoAtencionToAttach);
            }
            tipoMotivoLlamadoAtencion.setMotivoLlamadoAtencionList(attachedMotivoLlamadoAtencionList);
            em.persist(tipoMotivoLlamadoAtencion);
            for (MotivoLlamadoAtencion motivoLlamadoAtencionListMotivoLlamadoAtencion : tipoMotivoLlamadoAtencion.getMotivoLlamadoAtencionList()) {
                TipoMotivoLlamadoAtencion oldIdMotivoOfMotivoLlamadoAtencionListMotivoLlamadoAtencion = motivoLlamadoAtencionListMotivoLlamadoAtencion.getIdMotivo();
                motivoLlamadoAtencionListMotivoLlamadoAtencion.setIdMotivo(tipoMotivoLlamadoAtencion);
                motivoLlamadoAtencionListMotivoLlamadoAtencion = em.merge(motivoLlamadoAtencionListMotivoLlamadoAtencion);
                if (oldIdMotivoOfMotivoLlamadoAtencionListMotivoLlamadoAtencion != null) {
                    oldIdMotivoOfMotivoLlamadoAtencionListMotivoLlamadoAtencion.getMotivoLlamadoAtencionList().remove(motivoLlamadoAtencionListMotivoLlamadoAtencion);
                    oldIdMotivoOfMotivoLlamadoAtencionListMotivoLlamadoAtencion = em.merge(oldIdMotivoOfMotivoLlamadoAtencionListMotivoLlamadoAtencion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoMotivoLlamadoAtencion(tipoMotivoLlamadoAtencion.getId()) != null) {
                throw new PreexistingEntityException("TipoMotivoLlamadoAtencion " + tipoMotivoLlamadoAtencion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoMotivoLlamadoAtencion tipoMotivoLlamadoAtencion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoMotivoLlamadoAtencion persistentTipoMotivoLlamadoAtencion = em.find(TipoMotivoLlamadoAtencion.class, tipoMotivoLlamadoAtencion.getId());
            List<MotivoLlamadoAtencion> motivoLlamadoAtencionListOld = persistentTipoMotivoLlamadoAtencion.getMotivoLlamadoAtencionList();
            List<MotivoLlamadoAtencion> motivoLlamadoAtencionListNew = tipoMotivoLlamadoAtencion.getMotivoLlamadoAtencionList();
            List<MotivoLlamadoAtencion> attachedMotivoLlamadoAtencionListNew = new ArrayList<MotivoLlamadoAtencion>();
            for (MotivoLlamadoAtencion motivoLlamadoAtencionListNewMotivoLlamadoAtencionToAttach : motivoLlamadoAtencionListNew) {
                motivoLlamadoAtencionListNewMotivoLlamadoAtencionToAttach = em.getReference(motivoLlamadoAtencionListNewMotivoLlamadoAtencionToAttach.getClass(), motivoLlamadoAtencionListNewMotivoLlamadoAtencionToAttach.getId());
                attachedMotivoLlamadoAtencionListNew.add(motivoLlamadoAtencionListNewMotivoLlamadoAtencionToAttach);
            }
            motivoLlamadoAtencionListNew = attachedMotivoLlamadoAtencionListNew;
            tipoMotivoLlamadoAtencion.setMotivoLlamadoAtencionList(motivoLlamadoAtencionListNew);
            tipoMotivoLlamadoAtencion = em.merge(tipoMotivoLlamadoAtencion);
            for (MotivoLlamadoAtencion motivoLlamadoAtencionListOldMotivoLlamadoAtencion : motivoLlamadoAtencionListOld) {
                if (!motivoLlamadoAtencionListNew.contains(motivoLlamadoAtencionListOldMotivoLlamadoAtencion)) {
                    motivoLlamadoAtencionListOldMotivoLlamadoAtencion.setIdMotivo(null);
                    motivoLlamadoAtencionListOldMotivoLlamadoAtencion = em.merge(motivoLlamadoAtencionListOldMotivoLlamadoAtencion);
                }
            }
            for (MotivoLlamadoAtencion motivoLlamadoAtencionListNewMotivoLlamadoAtencion : motivoLlamadoAtencionListNew) {
                if (!motivoLlamadoAtencionListOld.contains(motivoLlamadoAtencionListNewMotivoLlamadoAtencion)) {
                    TipoMotivoLlamadoAtencion oldIdMotivoOfMotivoLlamadoAtencionListNewMotivoLlamadoAtencion = motivoLlamadoAtencionListNewMotivoLlamadoAtencion.getIdMotivo();
                    motivoLlamadoAtencionListNewMotivoLlamadoAtencion.setIdMotivo(tipoMotivoLlamadoAtencion);
                    motivoLlamadoAtencionListNewMotivoLlamadoAtencion = em.merge(motivoLlamadoAtencionListNewMotivoLlamadoAtencion);
                    if (oldIdMotivoOfMotivoLlamadoAtencionListNewMotivoLlamadoAtencion != null && !oldIdMotivoOfMotivoLlamadoAtencionListNewMotivoLlamadoAtencion.equals(tipoMotivoLlamadoAtencion)) {
                        oldIdMotivoOfMotivoLlamadoAtencionListNewMotivoLlamadoAtencion.getMotivoLlamadoAtencionList().remove(motivoLlamadoAtencionListNewMotivoLlamadoAtencion);
                        oldIdMotivoOfMotivoLlamadoAtencionListNewMotivoLlamadoAtencion = em.merge(oldIdMotivoOfMotivoLlamadoAtencionListNewMotivoLlamadoAtencion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoMotivoLlamadoAtencion.getId();
                if (findTipoMotivoLlamadoAtencion(id) == null) {
                    throw new NonexistentEntityException("The tipoMotivoLlamadoAtencion with id " + id + " no longer exists.");
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
            TipoMotivoLlamadoAtencion tipoMotivoLlamadoAtencion;
            try {
                tipoMotivoLlamadoAtencion = em.getReference(TipoMotivoLlamadoAtencion.class, id);
                tipoMotivoLlamadoAtencion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoMotivoLlamadoAtencion with id " + id + " no longer exists.", enfe);
            }
            List<MotivoLlamadoAtencion> motivoLlamadoAtencionList = tipoMotivoLlamadoAtencion.getMotivoLlamadoAtencionList();
            for (MotivoLlamadoAtencion motivoLlamadoAtencionListMotivoLlamadoAtencion : motivoLlamadoAtencionList) {
                motivoLlamadoAtencionListMotivoLlamadoAtencion.setIdMotivo(null);
                motivoLlamadoAtencionListMotivoLlamadoAtencion = em.merge(motivoLlamadoAtencionListMotivoLlamadoAtencion);
            }
            em.remove(tipoMotivoLlamadoAtencion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoMotivoLlamadoAtencion> findTipoMotivoLlamadoAtencionEntities() {
        return findTipoMotivoLlamadoAtencionEntities(true, -1, -1);
    }

    public List<TipoMotivoLlamadoAtencion> findTipoMotivoLlamadoAtencionEntities(int maxResults, int firstResult) {
        return findTipoMotivoLlamadoAtencionEntities(false, maxResults, firstResult);
    }

    private List<TipoMotivoLlamadoAtencion> findTipoMotivoLlamadoAtencionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoMotivoLlamadoAtencion.class));
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

    public TipoMotivoLlamadoAtencion findTipoMotivoLlamadoAtencion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoMotivoLlamadoAtencion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoMotivoLlamadoAtencionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoMotivoLlamadoAtencion> rt = cq.from(TipoMotivoLlamadoAtencion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
