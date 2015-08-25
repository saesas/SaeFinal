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
import com.sae.persistence.domain.Proceso;
import com.sae.persistence.domain.TipoEstadoProceso;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoEstadoProcesoJpaController implements Serializable {

    public TipoEstadoProcesoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoEstadoProceso tipoEstadoProceso) throws PreexistingEntityException, Exception {
        if (tipoEstadoProceso.getProcesoList() == null) {
            tipoEstadoProceso.setProcesoList(new ArrayList<Proceso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Proceso> attachedProcesoList = new ArrayList<Proceso>();
            for (Proceso procesoListProcesoToAttach : tipoEstadoProceso.getProcesoList()) {
                procesoListProcesoToAttach = em.getReference(procesoListProcesoToAttach.getClass(), procesoListProcesoToAttach.getId());
                attachedProcesoList.add(procesoListProcesoToAttach);
            }
            tipoEstadoProceso.setProcesoList(attachedProcesoList);
            em.persist(tipoEstadoProceso);
            for (Proceso procesoListProceso : tipoEstadoProceso.getProcesoList()) {
                TipoEstadoProceso oldIdEstadoOfProcesoListProceso = procesoListProceso.getIdEstado();
                procesoListProceso.setIdEstado(tipoEstadoProceso);
                procesoListProceso = em.merge(procesoListProceso);
                if (oldIdEstadoOfProcesoListProceso != null) {
                    oldIdEstadoOfProcesoListProceso.getProcesoList().remove(procesoListProceso);
                    oldIdEstadoOfProcesoListProceso = em.merge(oldIdEstadoOfProcesoListProceso);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoEstadoProceso(tipoEstadoProceso.getId()) != null) {
                throw new PreexistingEntityException("TipoEstadoProceso " + tipoEstadoProceso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoEstadoProceso tipoEstadoProceso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEstadoProceso persistentTipoEstadoProceso = em.find(TipoEstadoProceso.class, tipoEstadoProceso.getId());
            List<Proceso> procesoListOld = persistentTipoEstadoProceso.getProcesoList();
            List<Proceso> procesoListNew = tipoEstadoProceso.getProcesoList();
            List<Proceso> attachedProcesoListNew = new ArrayList<Proceso>();
            for (Proceso procesoListNewProcesoToAttach : procesoListNew) {
                procesoListNewProcesoToAttach = em.getReference(procesoListNewProcesoToAttach.getClass(), procesoListNewProcesoToAttach.getId());
                attachedProcesoListNew.add(procesoListNewProcesoToAttach);
            }
            procesoListNew = attachedProcesoListNew;
            tipoEstadoProceso.setProcesoList(procesoListNew);
            tipoEstadoProceso = em.merge(tipoEstadoProceso);
            for (Proceso procesoListOldProceso : procesoListOld) {
                if (!procesoListNew.contains(procesoListOldProceso)) {
                    procesoListOldProceso.setIdEstado(null);
                    procesoListOldProceso = em.merge(procesoListOldProceso);
                }
            }
            for (Proceso procesoListNewProceso : procesoListNew) {
                if (!procesoListOld.contains(procesoListNewProceso)) {
                    TipoEstadoProceso oldIdEstadoOfProcesoListNewProceso = procesoListNewProceso.getIdEstado();
                    procesoListNewProceso.setIdEstado(tipoEstadoProceso);
                    procesoListNewProceso = em.merge(procesoListNewProceso);
                    if (oldIdEstadoOfProcesoListNewProceso != null && !oldIdEstadoOfProcesoListNewProceso.equals(tipoEstadoProceso)) {
                        oldIdEstadoOfProcesoListNewProceso.getProcesoList().remove(procesoListNewProceso);
                        oldIdEstadoOfProcesoListNewProceso = em.merge(oldIdEstadoOfProcesoListNewProceso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoEstadoProceso.getId();
                if (findTipoEstadoProceso(id) == null) {
                    throw new NonexistentEntityException("The tipoEstadoProceso with id " + id + " no longer exists.");
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
            TipoEstadoProceso tipoEstadoProceso;
            try {
                tipoEstadoProceso = em.getReference(TipoEstadoProceso.class, id);
                tipoEstadoProceso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoEstadoProceso with id " + id + " no longer exists.", enfe);
            }
            List<Proceso> procesoList = tipoEstadoProceso.getProcesoList();
            for (Proceso procesoListProceso : procesoList) {
                procesoListProceso.setIdEstado(null);
                procesoListProceso = em.merge(procesoListProceso);
            }
            em.remove(tipoEstadoProceso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoEstadoProceso> findTipoEstadoProcesoEntities() {
        return findTipoEstadoProcesoEntities(true, -1, -1);
    }

    public List<TipoEstadoProceso> findTipoEstadoProcesoEntities(int maxResults, int firstResult) {
        return findTipoEstadoProcesoEntities(false, maxResults, firstResult);
    }

    private List<TipoEstadoProceso> findTipoEstadoProcesoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoEstadoProceso.class));
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

    public TipoEstadoProceso findTipoEstadoProceso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoEstadoProceso.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoEstadoProcesoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoEstadoProceso> rt = cq.from(TipoEstadoProceso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
