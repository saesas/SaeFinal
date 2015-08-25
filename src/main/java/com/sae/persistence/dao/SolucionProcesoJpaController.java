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
import com.sae.persistence.domain.TipoMecanismoSolucion;
import com.sae.persistence.domain.SeguimientoProceso;
import com.sae.persistence.domain.FiguraSolucion;
import com.sae.persistence.domain.SolucionProceso;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class SolucionProcesoJpaController implements Serializable {

    public SolucionProcesoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SolucionProceso solucionProceso) throws PreexistingEntityException, Exception {
        if (solucionProceso.getFiguraSolucionList() == null) {
            solucionProceso.setFiguraSolucionList(new ArrayList<FiguraSolucion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoMecanismoSolucion idMecanismo = solucionProceso.getIdMecanismo();
            if (idMecanismo != null) {
                idMecanismo = em.getReference(idMecanismo.getClass(), idMecanismo.getId());
                solucionProceso.setIdMecanismo(idMecanismo);
            }
            SeguimientoProceso idSeguimiento = solucionProceso.getIdSeguimiento();
            if (idSeguimiento != null) {
                idSeguimiento = em.getReference(idSeguimiento.getClass(), idSeguimiento.getId());
                solucionProceso.setIdSeguimiento(idSeguimiento);
            }
            List<FiguraSolucion> attachedFiguraSolucionList = new ArrayList<FiguraSolucion>();
            for (FiguraSolucion figuraSolucionListFiguraSolucionToAttach : solucionProceso.getFiguraSolucionList()) {
                figuraSolucionListFiguraSolucionToAttach = em.getReference(figuraSolucionListFiguraSolucionToAttach.getClass(), figuraSolucionListFiguraSolucionToAttach.getId());
                attachedFiguraSolucionList.add(figuraSolucionListFiguraSolucionToAttach);
            }
            solucionProceso.setFiguraSolucionList(attachedFiguraSolucionList);
            em.persist(solucionProceso);
            if (idMecanismo != null) {
                idMecanismo.getSolucionProcesoList().add(solucionProceso);
                idMecanismo = em.merge(idMecanismo);
            }
            if (idSeguimiento != null) {
                idSeguimiento.getSolucionProcesoList().add(solucionProceso);
                idSeguimiento = em.merge(idSeguimiento);
            }
            for (FiguraSolucion figuraSolucionListFiguraSolucion : solucionProceso.getFiguraSolucionList()) {
                SolucionProceso oldIdSolucionOfFiguraSolucionListFiguraSolucion = figuraSolucionListFiguraSolucion.getIdSolucion();
                figuraSolucionListFiguraSolucion.setIdSolucion(solucionProceso);
                figuraSolucionListFiguraSolucion = em.merge(figuraSolucionListFiguraSolucion);
                if (oldIdSolucionOfFiguraSolucionListFiguraSolucion != null) {
                    oldIdSolucionOfFiguraSolucionListFiguraSolucion.getFiguraSolucionList().remove(figuraSolucionListFiguraSolucion);
                    oldIdSolucionOfFiguraSolucionListFiguraSolucion = em.merge(oldIdSolucionOfFiguraSolucionListFiguraSolucion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSolucionProceso(solucionProceso.getId()) != null) {
                throw new PreexistingEntityException("SolucionProceso " + solucionProceso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SolucionProceso solucionProceso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SolucionProceso persistentSolucionProceso = em.find(SolucionProceso.class, solucionProceso.getId());
            TipoMecanismoSolucion idMecanismoOld = persistentSolucionProceso.getIdMecanismo();
            TipoMecanismoSolucion idMecanismoNew = solucionProceso.getIdMecanismo();
            SeguimientoProceso idSeguimientoOld = persistentSolucionProceso.getIdSeguimiento();
            SeguimientoProceso idSeguimientoNew = solucionProceso.getIdSeguimiento();
            List<FiguraSolucion> figuraSolucionListOld = persistentSolucionProceso.getFiguraSolucionList();
            List<FiguraSolucion> figuraSolucionListNew = solucionProceso.getFiguraSolucionList();
            if (idMecanismoNew != null) {
                idMecanismoNew = em.getReference(idMecanismoNew.getClass(), idMecanismoNew.getId());
                solucionProceso.setIdMecanismo(idMecanismoNew);
            }
            if (idSeguimientoNew != null) {
                idSeguimientoNew = em.getReference(idSeguimientoNew.getClass(), idSeguimientoNew.getId());
                solucionProceso.setIdSeguimiento(idSeguimientoNew);
            }
            List<FiguraSolucion> attachedFiguraSolucionListNew = new ArrayList<FiguraSolucion>();
            for (FiguraSolucion figuraSolucionListNewFiguraSolucionToAttach : figuraSolucionListNew) {
                figuraSolucionListNewFiguraSolucionToAttach = em.getReference(figuraSolucionListNewFiguraSolucionToAttach.getClass(), figuraSolucionListNewFiguraSolucionToAttach.getId());
                attachedFiguraSolucionListNew.add(figuraSolucionListNewFiguraSolucionToAttach);
            }
            figuraSolucionListNew = attachedFiguraSolucionListNew;
            solucionProceso.setFiguraSolucionList(figuraSolucionListNew);
            solucionProceso = em.merge(solucionProceso);
            if (idMecanismoOld != null && !idMecanismoOld.equals(idMecanismoNew)) {
                idMecanismoOld.getSolucionProcesoList().remove(solucionProceso);
                idMecanismoOld = em.merge(idMecanismoOld);
            }
            if (idMecanismoNew != null && !idMecanismoNew.equals(idMecanismoOld)) {
                idMecanismoNew.getSolucionProcesoList().add(solucionProceso);
                idMecanismoNew = em.merge(idMecanismoNew);
            }
            if (idSeguimientoOld != null && !idSeguimientoOld.equals(idSeguimientoNew)) {
                idSeguimientoOld.getSolucionProcesoList().remove(solucionProceso);
                idSeguimientoOld = em.merge(idSeguimientoOld);
            }
            if (idSeguimientoNew != null && !idSeguimientoNew.equals(idSeguimientoOld)) {
                idSeguimientoNew.getSolucionProcesoList().add(solucionProceso);
                idSeguimientoNew = em.merge(idSeguimientoNew);
            }
            for (FiguraSolucion figuraSolucionListOldFiguraSolucion : figuraSolucionListOld) {
                if (!figuraSolucionListNew.contains(figuraSolucionListOldFiguraSolucion)) {
                    figuraSolucionListOldFiguraSolucion.setIdSolucion(null);
                    figuraSolucionListOldFiguraSolucion = em.merge(figuraSolucionListOldFiguraSolucion);
                }
            }
            for (FiguraSolucion figuraSolucionListNewFiguraSolucion : figuraSolucionListNew) {
                if (!figuraSolucionListOld.contains(figuraSolucionListNewFiguraSolucion)) {
                    SolucionProceso oldIdSolucionOfFiguraSolucionListNewFiguraSolucion = figuraSolucionListNewFiguraSolucion.getIdSolucion();
                    figuraSolucionListNewFiguraSolucion.setIdSolucion(solucionProceso);
                    figuraSolucionListNewFiguraSolucion = em.merge(figuraSolucionListNewFiguraSolucion);
                    if (oldIdSolucionOfFiguraSolucionListNewFiguraSolucion != null && !oldIdSolucionOfFiguraSolucionListNewFiguraSolucion.equals(solucionProceso)) {
                        oldIdSolucionOfFiguraSolucionListNewFiguraSolucion.getFiguraSolucionList().remove(figuraSolucionListNewFiguraSolucion);
                        oldIdSolucionOfFiguraSolucionListNewFiguraSolucion = em.merge(oldIdSolucionOfFiguraSolucionListNewFiguraSolucion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = solucionProceso.getId();
                if (findSolucionProceso(id) == null) {
                    throw new NonexistentEntityException("The solucionProceso with id " + id + " no longer exists.");
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
            SolucionProceso solucionProceso;
            try {
                solucionProceso = em.getReference(SolucionProceso.class, id);
                solucionProceso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The solucionProceso with id " + id + " no longer exists.", enfe);
            }
            TipoMecanismoSolucion idMecanismo = solucionProceso.getIdMecanismo();
            if (idMecanismo != null) {
                idMecanismo.getSolucionProcesoList().remove(solucionProceso);
                idMecanismo = em.merge(idMecanismo);
            }
            SeguimientoProceso idSeguimiento = solucionProceso.getIdSeguimiento();
            if (idSeguimiento != null) {
                idSeguimiento.getSolucionProcesoList().remove(solucionProceso);
                idSeguimiento = em.merge(idSeguimiento);
            }
            List<FiguraSolucion> figuraSolucionList = solucionProceso.getFiguraSolucionList();
            for (FiguraSolucion figuraSolucionListFiguraSolucion : figuraSolucionList) {
                figuraSolucionListFiguraSolucion.setIdSolucion(null);
                figuraSolucionListFiguraSolucion = em.merge(figuraSolucionListFiguraSolucion);
            }
            em.remove(solucionProceso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SolucionProceso> findSolucionProcesoEntities() {
        return findSolucionProcesoEntities(true, -1, -1);
    }

    public List<SolucionProceso> findSolucionProcesoEntities(int maxResults, int firstResult) {
        return findSolucionProcesoEntities(false, maxResults, firstResult);
    }

    private List<SolucionProceso> findSolucionProcesoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SolucionProceso.class));
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

    public SolucionProceso findSolucionProceso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SolucionProceso.class, id);
        } finally {
            em.close();
        }
    }

    public int getSolucionProcesoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SolucionProceso> rt = cq.from(SolucionProceso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
