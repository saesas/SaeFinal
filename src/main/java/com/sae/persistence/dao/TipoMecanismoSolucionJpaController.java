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
import com.sae.persistence.domain.SolucionProceso;
import com.sae.persistence.domain.TipoMecanismoSolucion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TipoMecanismoSolucionJpaController implements Serializable {

    public TipoMecanismoSolucionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoMecanismoSolucion tipoMecanismoSolucion) throws PreexistingEntityException, Exception {
        if (tipoMecanismoSolucion.getSolucionProcesoList() == null) {
            tipoMecanismoSolucion.setSolucionProcesoList(new ArrayList<SolucionProceso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SolucionProceso> attachedSolucionProcesoList = new ArrayList<SolucionProceso>();
            for (SolucionProceso solucionProcesoListSolucionProcesoToAttach : tipoMecanismoSolucion.getSolucionProcesoList()) {
                solucionProcesoListSolucionProcesoToAttach = em.getReference(solucionProcesoListSolucionProcesoToAttach.getClass(), solucionProcesoListSolucionProcesoToAttach.getId());
                attachedSolucionProcesoList.add(solucionProcesoListSolucionProcesoToAttach);
            }
            tipoMecanismoSolucion.setSolucionProcesoList(attachedSolucionProcesoList);
            em.persist(tipoMecanismoSolucion);
            for (SolucionProceso solucionProcesoListSolucionProceso : tipoMecanismoSolucion.getSolucionProcesoList()) {
                TipoMecanismoSolucion oldIdMecanismoOfSolucionProcesoListSolucionProceso = solucionProcesoListSolucionProceso.getIdMecanismo();
                solucionProcesoListSolucionProceso.setIdMecanismo(tipoMecanismoSolucion);
                solucionProcesoListSolucionProceso = em.merge(solucionProcesoListSolucionProceso);
                if (oldIdMecanismoOfSolucionProcesoListSolucionProceso != null) {
                    oldIdMecanismoOfSolucionProcesoListSolucionProceso.getSolucionProcesoList().remove(solucionProcesoListSolucionProceso);
                    oldIdMecanismoOfSolucionProcesoListSolucionProceso = em.merge(oldIdMecanismoOfSolucionProcesoListSolucionProceso);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoMecanismoSolucion(tipoMecanismoSolucion.getId()) != null) {
                throw new PreexistingEntityException("TipoMecanismoSolucion " + tipoMecanismoSolucion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoMecanismoSolucion tipoMecanismoSolucion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoMecanismoSolucion persistentTipoMecanismoSolucion = em.find(TipoMecanismoSolucion.class, tipoMecanismoSolucion.getId());
            List<SolucionProceso> solucionProcesoListOld = persistentTipoMecanismoSolucion.getSolucionProcesoList();
            List<SolucionProceso> solucionProcesoListNew = tipoMecanismoSolucion.getSolucionProcesoList();
            List<SolucionProceso> attachedSolucionProcesoListNew = new ArrayList<SolucionProceso>();
            for (SolucionProceso solucionProcesoListNewSolucionProcesoToAttach : solucionProcesoListNew) {
                solucionProcesoListNewSolucionProcesoToAttach = em.getReference(solucionProcesoListNewSolucionProcesoToAttach.getClass(), solucionProcesoListNewSolucionProcesoToAttach.getId());
                attachedSolucionProcesoListNew.add(solucionProcesoListNewSolucionProcesoToAttach);
            }
            solucionProcesoListNew = attachedSolucionProcesoListNew;
            tipoMecanismoSolucion.setSolucionProcesoList(solucionProcesoListNew);
            tipoMecanismoSolucion = em.merge(tipoMecanismoSolucion);
            for (SolucionProceso solucionProcesoListOldSolucionProceso : solucionProcesoListOld) {
                if (!solucionProcesoListNew.contains(solucionProcesoListOldSolucionProceso)) {
                    solucionProcesoListOldSolucionProceso.setIdMecanismo(null);
                    solucionProcesoListOldSolucionProceso = em.merge(solucionProcesoListOldSolucionProceso);
                }
            }
            for (SolucionProceso solucionProcesoListNewSolucionProceso : solucionProcesoListNew) {
                if (!solucionProcesoListOld.contains(solucionProcesoListNewSolucionProceso)) {
                    TipoMecanismoSolucion oldIdMecanismoOfSolucionProcesoListNewSolucionProceso = solucionProcesoListNewSolucionProceso.getIdMecanismo();
                    solucionProcesoListNewSolucionProceso.setIdMecanismo(tipoMecanismoSolucion);
                    solucionProcesoListNewSolucionProceso = em.merge(solucionProcesoListNewSolucionProceso);
                    if (oldIdMecanismoOfSolucionProcesoListNewSolucionProceso != null && !oldIdMecanismoOfSolucionProcesoListNewSolucionProceso.equals(tipoMecanismoSolucion)) {
                        oldIdMecanismoOfSolucionProcesoListNewSolucionProceso.getSolucionProcesoList().remove(solucionProcesoListNewSolucionProceso);
                        oldIdMecanismoOfSolucionProcesoListNewSolucionProceso = em.merge(oldIdMecanismoOfSolucionProcesoListNewSolucionProceso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoMecanismoSolucion.getId();
                if (findTipoMecanismoSolucion(id) == null) {
                    throw new NonexistentEntityException("The tipoMecanismoSolucion with id " + id + " no longer exists.");
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
            TipoMecanismoSolucion tipoMecanismoSolucion;
            try {
                tipoMecanismoSolucion = em.getReference(TipoMecanismoSolucion.class, id);
                tipoMecanismoSolucion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoMecanismoSolucion with id " + id + " no longer exists.", enfe);
            }
            List<SolucionProceso> solucionProcesoList = tipoMecanismoSolucion.getSolucionProcesoList();
            for (SolucionProceso solucionProcesoListSolucionProceso : solucionProcesoList) {
                solucionProcesoListSolucionProceso.setIdMecanismo(null);
                solucionProcesoListSolucionProceso = em.merge(solucionProcesoListSolucionProceso);
            }
            em.remove(tipoMecanismoSolucion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoMecanismoSolucion> findTipoMecanismoSolucionEntities() {
        return findTipoMecanismoSolucionEntities(true, -1, -1);
    }

    public List<TipoMecanismoSolucion> findTipoMecanismoSolucionEntities(int maxResults, int firstResult) {
        return findTipoMecanismoSolucionEntities(false, maxResults, firstResult);
    }

    private List<TipoMecanismoSolucion> findTipoMecanismoSolucionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoMecanismoSolucion.class));
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

    public TipoMecanismoSolucion findTipoMecanismoSolucion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoMecanismoSolucion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoMecanismoSolucionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoMecanismoSolucion> rt = cq.from(TipoMecanismoSolucion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
