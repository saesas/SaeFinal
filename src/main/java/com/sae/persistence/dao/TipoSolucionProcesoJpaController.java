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
import com.sae.persistence.domain.SeguimientoProceso;
import com.sae.persistence.domain.TipoSolucionProceso;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoSolucionProcesoJpaController implements Serializable {

    public TipoSolucionProcesoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoSolucionProceso tipoSolucionProceso) throws PreexistingEntityException, Exception {
        if (tipoSolucionProceso.getSeguimientoProcesoList() == null) {
            tipoSolucionProceso.setSeguimientoProcesoList(new ArrayList<SeguimientoProceso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SeguimientoProceso> attachedSeguimientoProcesoList = new ArrayList<SeguimientoProceso>();
            for (SeguimientoProceso seguimientoProcesoListSeguimientoProcesoToAttach : tipoSolucionProceso.getSeguimientoProcesoList()) {
                seguimientoProcesoListSeguimientoProcesoToAttach = em.getReference(seguimientoProcesoListSeguimientoProcesoToAttach.getClass(), seguimientoProcesoListSeguimientoProcesoToAttach.getId());
                attachedSeguimientoProcesoList.add(seguimientoProcesoListSeguimientoProcesoToAttach);
            }
            tipoSolucionProceso.setSeguimientoProcesoList(attachedSeguimientoProcesoList);
            em.persist(tipoSolucionProceso);
            for (SeguimientoProceso seguimientoProcesoListSeguimientoProceso : tipoSolucionProceso.getSeguimientoProcesoList()) {
                TipoSolucionProceso oldIdSolucionOfSeguimientoProcesoListSeguimientoProceso = seguimientoProcesoListSeguimientoProceso.getIdSolucion();
                seguimientoProcesoListSeguimientoProceso.setIdSolucion(tipoSolucionProceso);
                seguimientoProcesoListSeguimientoProceso = em.merge(seguimientoProcesoListSeguimientoProceso);
                if (oldIdSolucionOfSeguimientoProcesoListSeguimientoProceso != null) {
                    oldIdSolucionOfSeguimientoProcesoListSeguimientoProceso.getSeguimientoProcesoList().remove(seguimientoProcesoListSeguimientoProceso);
                    oldIdSolucionOfSeguimientoProcesoListSeguimientoProceso = em.merge(oldIdSolucionOfSeguimientoProcesoListSeguimientoProceso);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoSolucionProceso(tipoSolucionProceso.getId()) != null) {
                throw new PreexistingEntityException("TipoSolucionProceso " + tipoSolucionProceso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoSolucionProceso tipoSolucionProceso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoSolucionProceso persistentTipoSolucionProceso = em.find(TipoSolucionProceso.class, tipoSolucionProceso.getId());
            List<SeguimientoProceso> seguimientoProcesoListOld = persistentTipoSolucionProceso.getSeguimientoProcesoList();
            List<SeguimientoProceso> seguimientoProcesoListNew = tipoSolucionProceso.getSeguimientoProcesoList();
            List<SeguimientoProceso> attachedSeguimientoProcesoListNew = new ArrayList<SeguimientoProceso>();
            for (SeguimientoProceso seguimientoProcesoListNewSeguimientoProcesoToAttach : seguimientoProcesoListNew) {
                seguimientoProcesoListNewSeguimientoProcesoToAttach = em.getReference(seguimientoProcesoListNewSeguimientoProcesoToAttach.getClass(), seguimientoProcesoListNewSeguimientoProcesoToAttach.getId());
                attachedSeguimientoProcesoListNew.add(seguimientoProcesoListNewSeguimientoProcesoToAttach);
            }
            seguimientoProcesoListNew = attachedSeguimientoProcesoListNew;
            tipoSolucionProceso.setSeguimientoProcesoList(seguimientoProcesoListNew);
            tipoSolucionProceso = em.merge(tipoSolucionProceso);
            for (SeguimientoProceso seguimientoProcesoListOldSeguimientoProceso : seguimientoProcesoListOld) {
                if (!seguimientoProcesoListNew.contains(seguimientoProcesoListOldSeguimientoProceso)) {
                    seguimientoProcesoListOldSeguimientoProceso.setIdSolucion(null);
                    seguimientoProcesoListOldSeguimientoProceso = em.merge(seguimientoProcesoListOldSeguimientoProceso);
                }
            }
            for (SeguimientoProceso seguimientoProcesoListNewSeguimientoProceso : seguimientoProcesoListNew) {
                if (!seguimientoProcesoListOld.contains(seguimientoProcesoListNewSeguimientoProceso)) {
                    TipoSolucionProceso oldIdSolucionOfSeguimientoProcesoListNewSeguimientoProceso = seguimientoProcesoListNewSeguimientoProceso.getIdSolucion();
                    seguimientoProcesoListNewSeguimientoProceso.setIdSolucion(tipoSolucionProceso);
                    seguimientoProcesoListNewSeguimientoProceso = em.merge(seguimientoProcesoListNewSeguimientoProceso);
                    if (oldIdSolucionOfSeguimientoProcesoListNewSeguimientoProceso != null && !oldIdSolucionOfSeguimientoProcesoListNewSeguimientoProceso.equals(tipoSolucionProceso)) {
                        oldIdSolucionOfSeguimientoProcesoListNewSeguimientoProceso.getSeguimientoProcesoList().remove(seguimientoProcesoListNewSeguimientoProceso);
                        oldIdSolucionOfSeguimientoProcesoListNewSeguimientoProceso = em.merge(oldIdSolucionOfSeguimientoProcesoListNewSeguimientoProceso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoSolucionProceso.getId();
                if (findTipoSolucionProceso(id) == null) {
                    throw new NonexistentEntityException("The tipoSolucionProceso with id " + id + " no longer exists.");
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
            TipoSolucionProceso tipoSolucionProceso;
            try {
                tipoSolucionProceso = em.getReference(TipoSolucionProceso.class, id);
                tipoSolucionProceso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoSolucionProceso with id " + id + " no longer exists.", enfe);
            }
            List<SeguimientoProceso> seguimientoProcesoList = tipoSolucionProceso.getSeguimientoProcesoList();
            for (SeguimientoProceso seguimientoProcesoListSeguimientoProceso : seguimientoProcesoList) {
                seguimientoProcesoListSeguimientoProceso.setIdSolucion(null);
                seguimientoProcesoListSeguimientoProceso = em.merge(seguimientoProcesoListSeguimientoProceso);
            }
            em.remove(tipoSolucionProceso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoSolucionProceso> findTipoSolucionProcesoEntities() {
        return findTipoSolucionProcesoEntities(true, -1, -1);
    }

    public List<TipoSolucionProceso> findTipoSolucionProcesoEntities(int maxResults, int firstResult) {
        return findTipoSolucionProcesoEntities(false, maxResults, firstResult);
    }

    private List<TipoSolucionProceso> findTipoSolucionProcesoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoSolucionProceso.class));
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

    public TipoSolucionProceso findTipoSolucionProceso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoSolucionProceso.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoSolucionProcesoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoSolucionProceso> rt = cq.from(TipoSolucionProceso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
