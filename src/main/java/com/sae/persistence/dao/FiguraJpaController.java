/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.Figura;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.FiguraPoder;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.FiguraCitacionAudiencia;
import com.sae.persistence.domain.FiguraProceso;
import com.sae.persistence.domain.FiguraDerechoPeticion;
import com.sae.persistence.domain.FiguraSolucion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class FiguraJpaController implements Serializable {

    public FiguraJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Figura figura) throws PreexistingEntityException, Exception {
        if (figura.getFiguraPoderList() == null) {
            figura.setFiguraPoderList(new ArrayList<FiguraPoder>());
        }
        if (figura.getFiguraCitacionAudienciaList() == null) {
            figura.setFiguraCitacionAudienciaList(new ArrayList<FiguraCitacionAudiencia>());
        }
        if (figura.getFiguraProcesoList() == null) {
            figura.setFiguraProcesoList(new ArrayList<FiguraProceso>());
        }
        if (figura.getFiguraDerechoPeticionList() == null) {
            figura.setFiguraDerechoPeticionList(new ArrayList<FiguraDerechoPeticion>());
        }
        if (figura.getFiguraSolucionList() == null) {
            figura.setFiguraSolucionList(new ArrayList<FiguraSolucion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<FiguraPoder> attachedFiguraPoderList = new ArrayList<FiguraPoder>();
            for (FiguraPoder figuraPoderListFiguraPoderToAttach : figura.getFiguraPoderList()) {
                figuraPoderListFiguraPoderToAttach = em.getReference(figuraPoderListFiguraPoderToAttach.getClass(), figuraPoderListFiguraPoderToAttach.getId());
                attachedFiguraPoderList.add(figuraPoderListFiguraPoderToAttach);
            }
            figura.setFiguraPoderList(attachedFiguraPoderList);
            List<FiguraCitacionAudiencia> attachedFiguraCitacionAudienciaList = new ArrayList<FiguraCitacionAudiencia>();
            for (FiguraCitacionAudiencia figuraCitacionAudienciaListFiguraCitacionAudienciaToAttach : figura.getFiguraCitacionAudienciaList()) {
                figuraCitacionAudienciaListFiguraCitacionAudienciaToAttach = em.getReference(figuraCitacionAudienciaListFiguraCitacionAudienciaToAttach.getClass(), figuraCitacionAudienciaListFiguraCitacionAudienciaToAttach.getId());
                attachedFiguraCitacionAudienciaList.add(figuraCitacionAudienciaListFiguraCitacionAudienciaToAttach);
            }
            figura.setFiguraCitacionAudienciaList(attachedFiguraCitacionAudienciaList);
            List<FiguraProceso> attachedFiguraProcesoList = new ArrayList<FiguraProceso>();
            for (FiguraProceso figuraProcesoListFiguraProcesoToAttach : figura.getFiguraProcesoList()) {
                figuraProcesoListFiguraProcesoToAttach = em.getReference(figuraProcesoListFiguraProcesoToAttach.getClass(), figuraProcesoListFiguraProcesoToAttach.getId());
                attachedFiguraProcesoList.add(figuraProcesoListFiguraProcesoToAttach);
            }
            figura.setFiguraProcesoList(attachedFiguraProcesoList);
            List<FiguraDerechoPeticion> attachedFiguraDerechoPeticionList = new ArrayList<FiguraDerechoPeticion>();
            for (FiguraDerechoPeticion figuraDerechoPeticionListFiguraDerechoPeticionToAttach : figura.getFiguraDerechoPeticionList()) {
                figuraDerechoPeticionListFiguraDerechoPeticionToAttach = em.getReference(figuraDerechoPeticionListFiguraDerechoPeticionToAttach.getClass(), figuraDerechoPeticionListFiguraDerechoPeticionToAttach.getId());
                attachedFiguraDerechoPeticionList.add(figuraDerechoPeticionListFiguraDerechoPeticionToAttach);
            }
            figura.setFiguraDerechoPeticionList(attachedFiguraDerechoPeticionList);
            List<FiguraSolucion> attachedFiguraSolucionList = new ArrayList<FiguraSolucion>();
            for (FiguraSolucion figuraSolucionListFiguraSolucionToAttach : figura.getFiguraSolucionList()) {
                figuraSolucionListFiguraSolucionToAttach = em.getReference(figuraSolucionListFiguraSolucionToAttach.getClass(), figuraSolucionListFiguraSolucionToAttach.getId());
                attachedFiguraSolucionList.add(figuraSolucionListFiguraSolucionToAttach);
            }
            figura.setFiguraSolucionList(attachedFiguraSolucionList);
            em.persist(figura);
            for (FiguraPoder figuraPoderListFiguraPoder : figura.getFiguraPoderList()) {
                Figura oldIdFiguraOfFiguraPoderListFiguraPoder = figuraPoderListFiguraPoder.getIdFigura();
                figuraPoderListFiguraPoder.setIdFigura(figura);
                figuraPoderListFiguraPoder = em.merge(figuraPoderListFiguraPoder);
                if (oldIdFiguraOfFiguraPoderListFiguraPoder != null) {
                    oldIdFiguraOfFiguraPoderListFiguraPoder.getFiguraPoderList().remove(figuraPoderListFiguraPoder);
                    oldIdFiguraOfFiguraPoderListFiguraPoder = em.merge(oldIdFiguraOfFiguraPoderListFiguraPoder);
                }
            }
            for (FiguraCitacionAudiencia figuraCitacionAudienciaListFiguraCitacionAudiencia : figura.getFiguraCitacionAudienciaList()) {
                Figura oldIdFiguraOfFiguraCitacionAudienciaListFiguraCitacionAudiencia = figuraCitacionAudienciaListFiguraCitacionAudiencia.getIdFigura();
                figuraCitacionAudienciaListFiguraCitacionAudiencia.setIdFigura(figura);
                figuraCitacionAudienciaListFiguraCitacionAudiencia = em.merge(figuraCitacionAudienciaListFiguraCitacionAudiencia);
                if (oldIdFiguraOfFiguraCitacionAudienciaListFiguraCitacionAudiencia != null) {
                    oldIdFiguraOfFiguraCitacionAudienciaListFiguraCitacionAudiencia.getFiguraCitacionAudienciaList().remove(figuraCitacionAudienciaListFiguraCitacionAudiencia);
                    oldIdFiguraOfFiguraCitacionAudienciaListFiguraCitacionAudiencia = em.merge(oldIdFiguraOfFiguraCitacionAudienciaListFiguraCitacionAudiencia);
                }
            }
            for (FiguraProceso figuraProcesoListFiguraProceso : figura.getFiguraProcesoList()) {
                Figura oldIdFiguraOfFiguraProcesoListFiguraProceso = figuraProcesoListFiguraProceso.getIdFigura();
                figuraProcesoListFiguraProceso.setIdFigura(figura);
                figuraProcesoListFiguraProceso = em.merge(figuraProcesoListFiguraProceso);
                if (oldIdFiguraOfFiguraProcesoListFiguraProceso != null) {
                    oldIdFiguraOfFiguraProcesoListFiguraProceso.getFiguraProcesoList().remove(figuraProcesoListFiguraProceso);
                    oldIdFiguraOfFiguraProcesoListFiguraProceso = em.merge(oldIdFiguraOfFiguraProcesoListFiguraProceso);
                }
            }
            for (FiguraDerechoPeticion figuraDerechoPeticionListFiguraDerechoPeticion : figura.getFiguraDerechoPeticionList()) {
                Figura oldIdFiguraOfFiguraDerechoPeticionListFiguraDerechoPeticion = figuraDerechoPeticionListFiguraDerechoPeticion.getIdFigura();
                figuraDerechoPeticionListFiguraDerechoPeticion.setIdFigura(figura);
                figuraDerechoPeticionListFiguraDerechoPeticion = em.merge(figuraDerechoPeticionListFiguraDerechoPeticion);
                if (oldIdFiguraOfFiguraDerechoPeticionListFiguraDerechoPeticion != null) {
                    oldIdFiguraOfFiguraDerechoPeticionListFiguraDerechoPeticion.getFiguraDerechoPeticionList().remove(figuraDerechoPeticionListFiguraDerechoPeticion);
                    oldIdFiguraOfFiguraDerechoPeticionListFiguraDerechoPeticion = em.merge(oldIdFiguraOfFiguraDerechoPeticionListFiguraDerechoPeticion);
                }
            }
            for (FiguraSolucion figuraSolucionListFiguraSolucion : figura.getFiguraSolucionList()) {
                Figura oldIdFiguraOfFiguraSolucionListFiguraSolucion = figuraSolucionListFiguraSolucion.getIdFigura();
                figuraSolucionListFiguraSolucion.setIdFigura(figura);
                figuraSolucionListFiguraSolucion = em.merge(figuraSolucionListFiguraSolucion);
                if (oldIdFiguraOfFiguraSolucionListFiguraSolucion != null) {
                    oldIdFiguraOfFiguraSolucionListFiguraSolucion.getFiguraSolucionList().remove(figuraSolucionListFiguraSolucion);
                    oldIdFiguraOfFiguraSolucionListFiguraSolucion = em.merge(oldIdFiguraOfFiguraSolucionListFiguraSolucion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFigura(figura.getId()) != null) {
                throw new PreexistingEntityException("Figura " + figura + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Figura figura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Figura persistentFigura = em.find(Figura.class, figura.getId());
            List<FiguraPoder> figuraPoderListOld = persistentFigura.getFiguraPoderList();
            List<FiguraPoder> figuraPoderListNew = figura.getFiguraPoderList();
            List<FiguraCitacionAudiencia> figuraCitacionAudienciaListOld = persistentFigura.getFiguraCitacionAudienciaList();
            List<FiguraCitacionAudiencia> figuraCitacionAudienciaListNew = figura.getFiguraCitacionAudienciaList();
            List<FiguraProceso> figuraProcesoListOld = persistentFigura.getFiguraProcesoList();
            List<FiguraProceso> figuraProcesoListNew = figura.getFiguraProcesoList();
            List<FiguraDerechoPeticion> figuraDerechoPeticionListOld = persistentFigura.getFiguraDerechoPeticionList();
            List<FiguraDerechoPeticion> figuraDerechoPeticionListNew = figura.getFiguraDerechoPeticionList();
            List<FiguraSolucion> figuraSolucionListOld = persistentFigura.getFiguraSolucionList();
            List<FiguraSolucion> figuraSolucionListNew = figura.getFiguraSolucionList();
            List<FiguraPoder> attachedFiguraPoderListNew = new ArrayList<FiguraPoder>();
            for (FiguraPoder figuraPoderListNewFiguraPoderToAttach : figuraPoderListNew) {
                figuraPoderListNewFiguraPoderToAttach = em.getReference(figuraPoderListNewFiguraPoderToAttach.getClass(), figuraPoderListNewFiguraPoderToAttach.getId());
                attachedFiguraPoderListNew.add(figuraPoderListNewFiguraPoderToAttach);
            }
            figuraPoderListNew = attachedFiguraPoderListNew;
            figura.setFiguraPoderList(figuraPoderListNew);
            List<FiguraCitacionAudiencia> attachedFiguraCitacionAudienciaListNew = new ArrayList<FiguraCitacionAudiencia>();
            for (FiguraCitacionAudiencia figuraCitacionAudienciaListNewFiguraCitacionAudienciaToAttach : figuraCitacionAudienciaListNew) {
                figuraCitacionAudienciaListNewFiguraCitacionAudienciaToAttach = em.getReference(figuraCitacionAudienciaListNewFiguraCitacionAudienciaToAttach.getClass(), figuraCitacionAudienciaListNewFiguraCitacionAudienciaToAttach.getId());
                attachedFiguraCitacionAudienciaListNew.add(figuraCitacionAudienciaListNewFiguraCitacionAudienciaToAttach);
            }
            figuraCitacionAudienciaListNew = attachedFiguraCitacionAudienciaListNew;
            figura.setFiguraCitacionAudienciaList(figuraCitacionAudienciaListNew);
            List<FiguraProceso> attachedFiguraProcesoListNew = new ArrayList<FiguraProceso>();
            for (FiguraProceso figuraProcesoListNewFiguraProcesoToAttach : figuraProcesoListNew) {
                figuraProcesoListNewFiguraProcesoToAttach = em.getReference(figuraProcesoListNewFiguraProcesoToAttach.getClass(), figuraProcesoListNewFiguraProcesoToAttach.getId());
                attachedFiguraProcesoListNew.add(figuraProcesoListNewFiguraProcesoToAttach);
            }
            figuraProcesoListNew = attachedFiguraProcesoListNew;
            figura.setFiguraProcesoList(figuraProcesoListNew);
            List<FiguraDerechoPeticion> attachedFiguraDerechoPeticionListNew = new ArrayList<FiguraDerechoPeticion>();
            for (FiguraDerechoPeticion figuraDerechoPeticionListNewFiguraDerechoPeticionToAttach : figuraDerechoPeticionListNew) {
                figuraDerechoPeticionListNewFiguraDerechoPeticionToAttach = em.getReference(figuraDerechoPeticionListNewFiguraDerechoPeticionToAttach.getClass(), figuraDerechoPeticionListNewFiguraDerechoPeticionToAttach.getId());
                attachedFiguraDerechoPeticionListNew.add(figuraDerechoPeticionListNewFiguraDerechoPeticionToAttach);
            }
            figuraDerechoPeticionListNew = attachedFiguraDerechoPeticionListNew;
            figura.setFiguraDerechoPeticionList(figuraDerechoPeticionListNew);
            List<FiguraSolucion> attachedFiguraSolucionListNew = new ArrayList<FiguraSolucion>();
            for (FiguraSolucion figuraSolucionListNewFiguraSolucionToAttach : figuraSolucionListNew) {
                figuraSolucionListNewFiguraSolucionToAttach = em.getReference(figuraSolucionListNewFiguraSolucionToAttach.getClass(), figuraSolucionListNewFiguraSolucionToAttach.getId());
                attachedFiguraSolucionListNew.add(figuraSolucionListNewFiguraSolucionToAttach);
            }
            figuraSolucionListNew = attachedFiguraSolucionListNew;
            figura.setFiguraSolucionList(figuraSolucionListNew);
            figura = em.merge(figura);
            for (FiguraPoder figuraPoderListOldFiguraPoder : figuraPoderListOld) {
                if (!figuraPoderListNew.contains(figuraPoderListOldFiguraPoder)) {
                    figuraPoderListOldFiguraPoder.setIdFigura(null);
                    figuraPoderListOldFiguraPoder = em.merge(figuraPoderListOldFiguraPoder);
                }
            }
            for (FiguraPoder figuraPoderListNewFiguraPoder : figuraPoderListNew) {
                if (!figuraPoderListOld.contains(figuraPoderListNewFiguraPoder)) {
                    Figura oldIdFiguraOfFiguraPoderListNewFiguraPoder = figuraPoderListNewFiguraPoder.getIdFigura();
                    figuraPoderListNewFiguraPoder.setIdFigura(figura);
                    figuraPoderListNewFiguraPoder = em.merge(figuraPoderListNewFiguraPoder);
                    if (oldIdFiguraOfFiguraPoderListNewFiguraPoder != null && !oldIdFiguraOfFiguraPoderListNewFiguraPoder.equals(figura)) {
                        oldIdFiguraOfFiguraPoderListNewFiguraPoder.getFiguraPoderList().remove(figuraPoderListNewFiguraPoder);
                        oldIdFiguraOfFiguraPoderListNewFiguraPoder = em.merge(oldIdFiguraOfFiguraPoderListNewFiguraPoder);
                    }
                }
            }
            for (FiguraCitacionAudiencia figuraCitacionAudienciaListOldFiguraCitacionAudiencia : figuraCitacionAudienciaListOld) {
                if (!figuraCitacionAudienciaListNew.contains(figuraCitacionAudienciaListOldFiguraCitacionAudiencia)) {
                    figuraCitacionAudienciaListOldFiguraCitacionAudiencia.setIdFigura(null);
                    figuraCitacionAudienciaListOldFiguraCitacionAudiencia = em.merge(figuraCitacionAudienciaListOldFiguraCitacionAudiencia);
                }
            }
            for (FiguraCitacionAudiencia figuraCitacionAudienciaListNewFiguraCitacionAudiencia : figuraCitacionAudienciaListNew) {
                if (!figuraCitacionAudienciaListOld.contains(figuraCitacionAudienciaListNewFiguraCitacionAudiencia)) {
                    Figura oldIdFiguraOfFiguraCitacionAudienciaListNewFiguraCitacionAudiencia = figuraCitacionAudienciaListNewFiguraCitacionAudiencia.getIdFigura();
                    figuraCitacionAudienciaListNewFiguraCitacionAudiencia.setIdFigura(figura);
                    figuraCitacionAudienciaListNewFiguraCitacionAudiencia = em.merge(figuraCitacionAudienciaListNewFiguraCitacionAudiencia);
                    if (oldIdFiguraOfFiguraCitacionAudienciaListNewFiguraCitacionAudiencia != null && !oldIdFiguraOfFiguraCitacionAudienciaListNewFiguraCitacionAudiencia.equals(figura)) {
                        oldIdFiguraOfFiguraCitacionAudienciaListNewFiguraCitacionAudiencia.getFiguraCitacionAudienciaList().remove(figuraCitacionAudienciaListNewFiguraCitacionAudiencia);
                        oldIdFiguraOfFiguraCitacionAudienciaListNewFiguraCitacionAudiencia = em.merge(oldIdFiguraOfFiguraCitacionAudienciaListNewFiguraCitacionAudiencia);
                    }
                }
            }
            for (FiguraProceso figuraProcesoListOldFiguraProceso : figuraProcesoListOld) {
                if (!figuraProcesoListNew.contains(figuraProcesoListOldFiguraProceso)) {
                    figuraProcesoListOldFiguraProceso.setIdFigura(null);
                    figuraProcesoListOldFiguraProceso = em.merge(figuraProcesoListOldFiguraProceso);
                }
            }
            for (FiguraProceso figuraProcesoListNewFiguraProceso : figuraProcesoListNew) {
                if (!figuraProcesoListOld.contains(figuraProcesoListNewFiguraProceso)) {
                    Figura oldIdFiguraOfFiguraProcesoListNewFiguraProceso = figuraProcesoListNewFiguraProceso.getIdFigura();
                    figuraProcesoListNewFiguraProceso.setIdFigura(figura);
                    figuraProcesoListNewFiguraProceso = em.merge(figuraProcesoListNewFiguraProceso);
                    if (oldIdFiguraOfFiguraProcesoListNewFiguraProceso != null && !oldIdFiguraOfFiguraProcesoListNewFiguraProceso.equals(figura)) {
                        oldIdFiguraOfFiguraProcesoListNewFiguraProceso.getFiguraProcesoList().remove(figuraProcesoListNewFiguraProceso);
                        oldIdFiguraOfFiguraProcesoListNewFiguraProceso = em.merge(oldIdFiguraOfFiguraProcesoListNewFiguraProceso);
                    }
                }
            }
            for (FiguraDerechoPeticion figuraDerechoPeticionListOldFiguraDerechoPeticion : figuraDerechoPeticionListOld) {
                if (!figuraDerechoPeticionListNew.contains(figuraDerechoPeticionListOldFiguraDerechoPeticion)) {
                    figuraDerechoPeticionListOldFiguraDerechoPeticion.setIdFigura(null);
                    figuraDerechoPeticionListOldFiguraDerechoPeticion = em.merge(figuraDerechoPeticionListOldFiguraDerechoPeticion);
                }
            }
            for (FiguraDerechoPeticion figuraDerechoPeticionListNewFiguraDerechoPeticion : figuraDerechoPeticionListNew) {
                if (!figuraDerechoPeticionListOld.contains(figuraDerechoPeticionListNewFiguraDerechoPeticion)) {
                    Figura oldIdFiguraOfFiguraDerechoPeticionListNewFiguraDerechoPeticion = figuraDerechoPeticionListNewFiguraDerechoPeticion.getIdFigura();
                    figuraDerechoPeticionListNewFiguraDerechoPeticion.setIdFigura(figura);
                    figuraDerechoPeticionListNewFiguraDerechoPeticion = em.merge(figuraDerechoPeticionListNewFiguraDerechoPeticion);
                    if (oldIdFiguraOfFiguraDerechoPeticionListNewFiguraDerechoPeticion != null && !oldIdFiguraOfFiguraDerechoPeticionListNewFiguraDerechoPeticion.equals(figura)) {
                        oldIdFiguraOfFiguraDerechoPeticionListNewFiguraDerechoPeticion.getFiguraDerechoPeticionList().remove(figuraDerechoPeticionListNewFiguraDerechoPeticion);
                        oldIdFiguraOfFiguraDerechoPeticionListNewFiguraDerechoPeticion = em.merge(oldIdFiguraOfFiguraDerechoPeticionListNewFiguraDerechoPeticion);
                    }
                }
            }
            for (FiguraSolucion figuraSolucionListOldFiguraSolucion : figuraSolucionListOld) {
                if (!figuraSolucionListNew.contains(figuraSolucionListOldFiguraSolucion)) {
                    figuraSolucionListOldFiguraSolucion.setIdFigura(null);
                    figuraSolucionListOldFiguraSolucion = em.merge(figuraSolucionListOldFiguraSolucion);
                }
            }
            for (FiguraSolucion figuraSolucionListNewFiguraSolucion : figuraSolucionListNew) {
                if (!figuraSolucionListOld.contains(figuraSolucionListNewFiguraSolucion)) {
                    Figura oldIdFiguraOfFiguraSolucionListNewFiguraSolucion = figuraSolucionListNewFiguraSolucion.getIdFigura();
                    figuraSolucionListNewFiguraSolucion.setIdFigura(figura);
                    figuraSolucionListNewFiguraSolucion = em.merge(figuraSolucionListNewFiguraSolucion);
                    if (oldIdFiguraOfFiguraSolucionListNewFiguraSolucion != null && !oldIdFiguraOfFiguraSolucionListNewFiguraSolucion.equals(figura)) {
                        oldIdFiguraOfFiguraSolucionListNewFiguraSolucion.getFiguraSolucionList().remove(figuraSolucionListNewFiguraSolucion);
                        oldIdFiguraOfFiguraSolucionListNewFiguraSolucion = em.merge(oldIdFiguraOfFiguraSolucionListNewFiguraSolucion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = figura.getId();
                if (findFigura(id) == null) {
                    throw new NonexistentEntityException("The figura with id " + id + " no longer exists.");
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
            Figura figura;
            try {
                figura = em.getReference(Figura.class, id);
                figura.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The figura with id " + id + " no longer exists.", enfe);
            }
            List<FiguraPoder> figuraPoderList = figura.getFiguraPoderList();
            for (FiguraPoder figuraPoderListFiguraPoder : figuraPoderList) {
                figuraPoderListFiguraPoder.setIdFigura(null);
                figuraPoderListFiguraPoder = em.merge(figuraPoderListFiguraPoder);
            }
            List<FiguraCitacionAudiencia> figuraCitacionAudienciaList = figura.getFiguraCitacionAudienciaList();
            for (FiguraCitacionAudiencia figuraCitacionAudienciaListFiguraCitacionAudiencia : figuraCitacionAudienciaList) {
                figuraCitacionAudienciaListFiguraCitacionAudiencia.setIdFigura(null);
                figuraCitacionAudienciaListFiguraCitacionAudiencia = em.merge(figuraCitacionAudienciaListFiguraCitacionAudiencia);
            }
            List<FiguraProceso> figuraProcesoList = figura.getFiguraProcesoList();
            for (FiguraProceso figuraProcesoListFiguraProceso : figuraProcesoList) {
                figuraProcesoListFiguraProceso.setIdFigura(null);
                figuraProcesoListFiguraProceso = em.merge(figuraProcesoListFiguraProceso);
            }
            List<FiguraDerechoPeticion> figuraDerechoPeticionList = figura.getFiguraDerechoPeticionList();
            for (FiguraDerechoPeticion figuraDerechoPeticionListFiguraDerechoPeticion : figuraDerechoPeticionList) {
                figuraDerechoPeticionListFiguraDerechoPeticion.setIdFigura(null);
                figuraDerechoPeticionListFiguraDerechoPeticion = em.merge(figuraDerechoPeticionListFiguraDerechoPeticion);
            }
            List<FiguraSolucion> figuraSolucionList = figura.getFiguraSolucionList();
            for (FiguraSolucion figuraSolucionListFiguraSolucion : figuraSolucionList) {
                figuraSolucionListFiguraSolucion.setIdFigura(null);
                figuraSolucionListFiguraSolucion = em.merge(figuraSolucionListFiguraSolucion);
            }
            em.remove(figura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Figura> findFiguraEntities() {
        return findFiguraEntities(true, -1, -1);
    }

    public List<Figura> findFiguraEntities(int maxResults, int firstResult) {
        return findFiguraEntities(false, maxResults, firstResult);
    }

    private List<Figura> findFiguraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Figura.class));
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

    public Figura findFigura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Figura.class, id);
        } finally {
            em.close();
        }
    }

    public int getFiguraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Figura> rt = cq.from(Figura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
