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
import com.sae.persistence.domain.TipoEstadoProceso;
import com.sae.persistence.domain.Tema;
import com.sae.persistence.domain.Clase;
import com.sae.persistence.domain.SeguimientoProceso;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.FiguraProceso;
import com.sae.persistence.domain.Proceso;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ProcesoJpaController implements Serializable {

    public ProcesoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proceso proceso) throws PreexistingEntityException, Exception {
        if (proceso.getSeguimientoProcesoList() == null) {
            proceso.setSeguimientoProcesoList(new ArrayList<SeguimientoProceso>());
        }
        if (proceso.getFiguraProcesoList() == null) {
            proceso.setFiguraProcesoList(new ArrayList<FiguraProceso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEstadoProceso idEstado = proceso.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getId());
                proceso.setIdEstado(idEstado);
            }
            Tema idTema = proceso.getIdTema();
            if (idTema != null) {
                idTema = em.getReference(idTema.getClass(), idTema.getId());
                proceso.setIdTema(idTema);
            }
            Clase idClase = proceso.getIdClase();
            if (idClase != null) {
                idClase = em.getReference(idClase.getClass(), idClase.getId());
                proceso.setIdClase(idClase);
            }
            List<SeguimientoProceso> attachedSeguimientoProcesoList = new ArrayList<SeguimientoProceso>();
            for (SeguimientoProceso seguimientoProcesoListSeguimientoProcesoToAttach : proceso.getSeguimientoProcesoList()) {
                seguimientoProcesoListSeguimientoProcesoToAttach = em.getReference(seguimientoProcesoListSeguimientoProcesoToAttach.getClass(), seguimientoProcesoListSeguimientoProcesoToAttach.getId());
                attachedSeguimientoProcesoList.add(seguimientoProcesoListSeguimientoProcesoToAttach);
            }
            proceso.setSeguimientoProcesoList(attachedSeguimientoProcesoList);
            List<FiguraProceso> attachedFiguraProcesoList = new ArrayList<FiguraProceso>();
            for (FiguraProceso figuraProcesoListFiguraProcesoToAttach : proceso.getFiguraProcesoList()) {
                figuraProcesoListFiguraProcesoToAttach = em.getReference(figuraProcesoListFiguraProcesoToAttach.getClass(), figuraProcesoListFiguraProcesoToAttach.getId());
                attachedFiguraProcesoList.add(figuraProcesoListFiguraProcesoToAttach);
            }
            proceso.setFiguraProcesoList(attachedFiguraProcesoList);
            em.persist(proceso);
            if (idEstado != null) {
                idEstado.getProcesoList().add(proceso);
                idEstado = em.merge(idEstado);
            }
            if (idTema != null) {
                idTema.getProcesoList().add(proceso);
                idTema = em.merge(idTema);
            }
            if (idClase != null) {
                idClase.getProcesoList().add(proceso);
                idClase = em.merge(idClase);
            }
            for (SeguimientoProceso seguimientoProcesoListSeguimientoProceso : proceso.getSeguimientoProcesoList()) {
                Proceso oldIdProcesoOfSeguimientoProcesoListSeguimientoProceso = seguimientoProcesoListSeguimientoProceso.getIdProceso();
                seguimientoProcesoListSeguimientoProceso.setIdProceso(proceso);
                seguimientoProcesoListSeguimientoProceso = em.merge(seguimientoProcesoListSeguimientoProceso);
                if (oldIdProcesoOfSeguimientoProcesoListSeguimientoProceso != null) {
                    oldIdProcesoOfSeguimientoProcesoListSeguimientoProceso.getSeguimientoProcesoList().remove(seguimientoProcesoListSeguimientoProceso);
                    oldIdProcesoOfSeguimientoProcesoListSeguimientoProceso = em.merge(oldIdProcesoOfSeguimientoProcesoListSeguimientoProceso);
                }
            }
            for (FiguraProceso figuraProcesoListFiguraProceso : proceso.getFiguraProcesoList()) {
                Proceso oldIdProcesoOfFiguraProcesoListFiguraProceso = figuraProcesoListFiguraProceso.getIdProceso();
                figuraProcesoListFiguraProceso.setIdProceso(proceso);
                figuraProcesoListFiguraProceso = em.merge(figuraProcesoListFiguraProceso);
                if (oldIdProcesoOfFiguraProcesoListFiguraProceso != null) {
                    oldIdProcesoOfFiguraProcesoListFiguraProceso.getFiguraProcesoList().remove(figuraProcesoListFiguraProceso);
                    oldIdProcesoOfFiguraProcesoListFiguraProceso = em.merge(oldIdProcesoOfFiguraProcesoListFiguraProceso);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProceso(proceso.getId()) != null) {
                throw new PreexistingEntityException("Proceso " + proceso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proceso proceso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proceso persistentProceso = em.find(Proceso.class, proceso.getId());
            TipoEstadoProceso idEstadoOld = persistentProceso.getIdEstado();
            TipoEstadoProceso idEstadoNew = proceso.getIdEstado();
            Tema idTemaOld = persistentProceso.getIdTema();
            Tema idTemaNew = proceso.getIdTema();
            Clase idClaseOld = persistentProceso.getIdClase();
            Clase idClaseNew = proceso.getIdClase();
            List<SeguimientoProceso> seguimientoProcesoListOld = persistentProceso.getSeguimientoProcesoList();
            List<SeguimientoProceso> seguimientoProcesoListNew = proceso.getSeguimientoProcesoList();
            List<FiguraProceso> figuraProcesoListOld = persistentProceso.getFiguraProcesoList();
            List<FiguraProceso> figuraProcesoListNew = proceso.getFiguraProcesoList();
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getId());
                proceso.setIdEstado(idEstadoNew);
            }
            if (idTemaNew != null) {
                idTemaNew = em.getReference(idTemaNew.getClass(), idTemaNew.getId());
                proceso.setIdTema(idTemaNew);
            }
            if (idClaseNew != null) {
                idClaseNew = em.getReference(idClaseNew.getClass(), idClaseNew.getId());
                proceso.setIdClase(idClaseNew);
            }
            List<SeguimientoProceso> attachedSeguimientoProcesoListNew = new ArrayList<SeguimientoProceso>();
            for (SeguimientoProceso seguimientoProcesoListNewSeguimientoProcesoToAttach : seguimientoProcesoListNew) {
                seguimientoProcesoListNewSeguimientoProcesoToAttach = em.getReference(seguimientoProcesoListNewSeguimientoProcesoToAttach.getClass(), seguimientoProcesoListNewSeguimientoProcesoToAttach.getId());
                attachedSeguimientoProcesoListNew.add(seguimientoProcesoListNewSeguimientoProcesoToAttach);
            }
            seguimientoProcesoListNew = attachedSeguimientoProcesoListNew;
            proceso.setSeguimientoProcesoList(seguimientoProcesoListNew);
            List<FiguraProceso> attachedFiguraProcesoListNew = new ArrayList<FiguraProceso>();
            for (FiguraProceso figuraProcesoListNewFiguraProcesoToAttach : figuraProcesoListNew) {
                figuraProcesoListNewFiguraProcesoToAttach = em.getReference(figuraProcesoListNewFiguraProcesoToAttach.getClass(), figuraProcesoListNewFiguraProcesoToAttach.getId());
                attachedFiguraProcesoListNew.add(figuraProcesoListNewFiguraProcesoToAttach);
            }
            figuraProcesoListNew = attachedFiguraProcesoListNew;
            proceso.setFiguraProcesoList(figuraProcesoListNew);
            proceso = em.merge(proceso);
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getProcesoList().remove(proceso);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getProcesoList().add(proceso);
                idEstadoNew = em.merge(idEstadoNew);
            }
            if (idTemaOld != null && !idTemaOld.equals(idTemaNew)) {
                idTemaOld.getProcesoList().remove(proceso);
                idTemaOld = em.merge(idTemaOld);
            }
            if (idTemaNew != null && !idTemaNew.equals(idTemaOld)) {
                idTemaNew.getProcesoList().add(proceso);
                idTemaNew = em.merge(idTemaNew);
            }
            if (idClaseOld != null && !idClaseOld.equals(idClaseNew)) {
                idClaseOld.getProcesoList().remove(proceso);
                idClaseOld = em.merge(idClaseOld);
            }
            if (idClaseNew != null && !idClaseNew.equals(idClaseOld)) {
                idClaseNew.getProcesoList().add(proceso);
                idClaseNew = em.merge(idClaseNew);
            }
            for (SeguimientoProceso seguimientoProcesoListOldSeguimientoProceso : seguimientoProcesoListOld) {
                if (!seguimientoProcesoListNew.contains(seguimientoProcesoListOldSeguimientoProceso)) {
                    seguimientoProcesoListOldSeguimientoProceso.setIdProceso(null);
                    seguimientoProcesoListOldSeguimientoProceso = em.merge(seguimientoProcesoListOldSeguimientoProceso);
                }
            }
            for (SeguimientoProceso seguimientoProcesoListNewSeguimientoProceso : seguimientoProcesoListNew) {
                if (!seguimientoProcesoListOld.contains(seguimientoProcesoListNewSeguimientoProceso)) {
                    Proceso oldIdProcesoOfSeguimientoProcesoListNewSeguimientoProceso = seguimientoProcesoListNewSeguimientoProceso.getIdProceso();
                    seguimientoProcesoListNewSeguimientoProceso.setIdProceso(proceso);
                    seguimientoProcesoListNewSeguimientoProceso = em.merge(seguimientoProcesoListNewSeguimientoProceso);
                    if (oldIdProcesoOfSeguimientoProcesoListNewSeguimientoProceso != null && !oldIdProcesoOfSeguimientoProcesoListNewSeguimientoProceso.equals(proceso)) {
                        oldIdProcesoOfSeguimientoProcesoListNewSeguimientoProceso.getSeguimientoProcesoList().remove(seguimientoProcesoListNewSeguimientoProceso);
                        oldIdProcesoOfSeguimientoProcesoListNewSeguimientoProceso = em.merge(oldIdProcesoOfSeguimientoProcesoListNewSeguimientoProceso);
                    }
                }
            }
            for (FiguraProceso figuraProcesoListOldFiguraProceso : figuraProcesoListOld) {
                if (!figuraProcesoListNew.contains(figuraProcesoListOldFiguraProceso)) {
                    figuraProcesoListOldFiguraProceso.setIdProceso(null);
                    figuraProcesoListOldFiguraProceso = em.merge(figuraProcesoListOldFiguraProceso);
                }
            }
            for (FiguraProceso figuraProcesoListNewFiguraProceso : figuraProcesoListNew) {
                if (!figuraProcesoListOld.contains(figuraProcesoListNewFiguraProceso)) {
                    Proceso oldIdProcesoOfFiguraProcesoListNewFiguraProceso = figuraProcesoListNewFiguraProceso.getIdProceso();
                    figuraProcesoListNewFiguraProceso.setIdProceso(proceso);
                    figuraProcesoListNewFiguraProceso = em.merge(figuraProcesoListNewFiguraProceso);
                    if (oldIdProcesoOfFiguraProcesoListNewFiguraProceso != null && !oldIdProcesoOfFiguraProcesoListNewFiguraProceso.equals(proceso)) {
                        oldIdProcesoOfFiguraProcesoListNewFiguraProceso.getFiguraProcesoList().remove(figuraProcesoListNewFiguraProceso);
                        oldIdProcesoOfFiguraProcesoListNewFiguraProceso = em.merge(oldIdProcesoOfFiguraProcesoListNewFiguraProceso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proceso.getId();
                if (findProceso(id) == null) {
                    throw new NonexistentEntityException("The proceso with id " + id + " no longer exists.");
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
            Proceso proceso;
            try {
                proceso = em.getReference(Proceso.class, id);
                proceso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proceso with id " + id + " no longer exists.", enfe);
            }
            TipoEstadoProceso idEstado = proceso.getIdEstado();
            if (idEstado != null) {
                idEstado.getProcesoList().remove(proceso);
                idEstado = em.merge(idEstado);
            }
            Tema idTema = proceso.getIdTema();
            if (idTema != null) {
                idTema.getProcesoList().remove(proceso);
                idTema = em.merge(idTema);
            }
            Clase idClase = proceso.getIdClase();
            if (idClase != null) {
                idClase.getProcesoList().remove(proceso);
                idClase = em.merge(idClase);
            }
            List<SeguimientoProceso> seguimientoProcesoList = proceso.getSeguimientoProcesoList();
            for (SeguimientoProceso seguimientoProcesoListSeguimientoProceso : seguimientoProcesoList) {
                seguimientoProcesoListSeguimientoProceso.setIdProceso(null);
                seguimientoProcesoListSeguimientoProceso = em.merge(seguimientoProcesoListSeguimientoProceso);
            }
            List<FiguraProceso> figuraProcesoList = proceso.getFiguraProcesoList();
            for (FiguraProceso figuraProcesoListFiguraProceso : figuraProcesoList) {
                figuraProcesoListFiguraProceso.setIdProceso(null);
                figuraProcesoListFiguraProceso = em.merge(figuraProcesoListFiguraProceso);
            }
            em.remove(proceso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proceso> findProcesoEntities() {
        return findProcesoEntities(true, -1, -1);
    }

    public List<Proceso> findProcesoEntities(int maxResults, int firstResult) {
        return findProcesoEntities(false, maxResults, firstResult);
    }

    private List<Proceso> findProcesoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proceso.class));
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

    public Proceso findProceso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proceso.class, id);
        } finally {
            em.close();
        }
    }

    public int getProcesoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proceso> rt = cq.from(Proceso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
