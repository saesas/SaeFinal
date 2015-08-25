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
import com.sae.persistence.domain.TipoProyectoConvocatoria;
import com.sae.persistence.domain.TipoProcesoConvocatoria;
import com.sae.persistence.domain.FormaPagoConvocatoria;
import com.sae.persistence.domain.MatrizRiesgo;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.Propuesta;
import com.sae.persistence.domain.SocioConvocatoria;
import com.sae.persistence.domain.EquipoRequeridoConvocatoria;
import com.sae.persistence.domain.AdjuntoConvocatoria;
import com.sae.persistence.domain.EvaluacionConvocatoria;
import com.sae.persistence.domain.RupEstadoFinancieroConsolidadoConvocatoria;
import com.sae.persistence.domain.EstadoConvocatoria;
import com.sae.persistence.domain.PersonalRequeridoConvocatoria;
import com.sae.persistence.domain.AnalisisConvocatoria;
import com.sae.persistence.domain.Convocatoria;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ConvocatoriaJpaController implements Serializable {

    public ConvocatoriaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Convocatoria convocatoria) throws PreexistingEntityException, Exception {
        if (convocatoria.getMatrizRiesgoList() == null) {
            convocatoria.setMatrizRiesgoList(new ArrayList<MatrizRiesgo>());
        }
        if (convocatoria.getPropuestaList() == null) {
            convocatoria.setPropuestaList(new ArrayList<Propuesta>());
        }
        if (convocatoria.getSocioConvocatoriaList() == null) {
            convocatoria.setSocioConvocatoriaList(new ArrayList<SocioConvocatoria>());
        }
        if (convocatoria.getEquipoRequeridoConvocatoriaList() == null) {
            convocatoria.setEquipoRequeridoConvocatoriaList(new ArrayList<EquipoRequeridoConvocatoria>());
        }
        if (convocatoria.getAdjuntoConvocatoriaList() == null) {
            convocatoria.setAdjuntoConvocatoriaList(new ArrayList<AdjuntoConvocatoria>());
        }
        if (convocatoria.getEvaluacionConvocatoriaList() == null) {
            convocatoria.setEvaluacionConvocatoriaList(new ArrayList<EvaluacionConvocatoria>());
        }
        if (convocatoria.getRupEstadoFinancieroConsolidadoConvocatoriaList() == null) {
            convocatoria.setRupEstadoFinancieroConsolidadoConvocatoriaList(new ArrayList<RupEstadoFinancieroConsolidadoConvocatoria>());
        }
        if (convocatoria.getEstadoConvocatoriaList() == null) {
            convocatoria.setEstadoConvocatoriaList(new ArrayList<EstadoConvocatoria>());
        }
        if (convocatoria.getPersonalRequeridoConvocatoriaList() == null) {
            convocatoria.setPersonalRequeridoConvocatoriaList(new ArrayList<PersonalRequeridoConvocatoria>());
        }
        if (convocatoria.getAnalisisConvocatoriaList() == null) {
            convocatoria.setAnalisisConvocatoriaList(new ArrayList<AnalisisConvocatoria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoProyectoConvocatoria idTipoProyecto = convocatoria.getIdTipoProyecto();
            if (idTipoProyecto != null) {
                idTipoProyecto = em.getReference(idTipoProyecto.getClass(), idTipoProyecto.getId());
                convocatoria.setIdTipoProyecto(idTipoProyecto);
            }
            TipoProcesoConvocatoria idTipoProceso = convocatoria.getIdTipoProceso();
            if (idTipoProceso != null) {
                idTipoProceso = em.getReference(idTipoProceso.getClass(), idTipoProceso.getId());
                convocatoria.setIdTipoProceso(idTipoProceso);
            }
            FormaPagoConvocatoria idFormaPago = convocatoria.getIdFormaPago();
            if (idFormaPago != null) {
                idFormaPago = em.getReference(idFormaPago.getClass(), idFormaPago.getId());
                convocatoria.setIdFormaPago(idFormaPago);
            }
            List<MatrizRiesgo> attachedMatrizRiesgoList = new ArrayList<MatrizRiesgo>();
            for (MatrizRiesgo matrizRiesgoListMatrizRiesgoToAttach : convocatoria.getMatrizRiesgoList()) {
                matrizRiesgoListMatrizRiesgoToAttach = em.getReference(matrizRiesgoListMatrizRiesgoToAttach.getClass(), matrizRiesgoListMatrizRiesgoToAttach.getId());
                attachedMatrizRiesgoList.add(matrizRiesgoListMatrizRiesgoToAttach);
            }
            convocatoria.setMatrizRiesgoList(attachedMatrizRiesgoList);
            List<Propuesta> attachedPropuestaList = new ArrayList<Propuesta>();
            for (Propuesta propuestaListPropuestaToAttach : convocatoria.getPropuestaList()) {
                propuestaListPropuestaToAttach = em.getReference(propuestaListPropuestaToAttach.getClass(), propuestaListPropuestaToAttach.getId());
                attachedPropuestaList.add(propuestaListPropuestaToAttach);
            }
            convocatoria.setPropuestaList(attachedPropuestaList);
            List<SocioConvocatoria> attachedSocioConvocatoriaList = new ArrayList<SocioConvocatoria>();
            for (SocioConvocatoria socioConvocatoriaListSocioConvocatoriaToAttach : convocatoria.getSocioConvocatoriaList()) {
                socioConvocatoriaListSocioConvocatoriaToAttach = em.getReference(socioConvocatoriaListSocioConvocatoriaToAttach.getClass(), socioConvocatoriaListSocioConvocatoriaToAttach.getId());
                attachedSocioConvocatoriaList.add(socioConvocatoriaListSocioConvocatoriaToAttach);
            }
            convocatoria.setSocioConvocatoriaList(attachedSocioConvocatoriaList);
            List<EquipoRequeridoConvocatoria> attachedEquipoRequeridoConvocatoriaList = new ArrayList<EquipoRequeridoConvocatoria>();
            for (EquipoRequeridoConvocatoria equipoRequeridoConvocatoriaListEquipoRequeridoConvocatoriaToAttach : convocatoria.getEquipoRequeridoConvocatoriaList()) {
                equipoRequeridoConvocatoriaListEquipoRequeridoConvocatoriaToAttach = em.getReference(equipoRequeridoConvocatoriaListEquipoRequeridoConvocatoriaToAttach.getClass(), equipoRequeridoConvocatoriaListEquipoRequeridoConvocatoriaToAttach.getId());
                attachedEquipoRequeridoConvocatoriaList.add(equipoRequeridoConvocatoriaListEquipoRequeridoConvocatoriaToAttach);
            }
            convocatoria.setEquipoRequeridoConvocatoriaList(attachedEquipoRequeridoConvocatoriaList);
            List<AdjuntoConvocatoria> attachedAdjuntoConvocatoriaList = new ArrayList<AdjuntoConvocatoria>();
            for (AdjuntoConvocatoria adjuntoConvocatoriaListAdjuntoConvocatoriaToAttach : convocatoria.getAdjuntoConvocatoriaList()) {
                adjuntoConvocatoriaListAdjuntoConvocatoriaToAttach = em.getReference(adjuntoConvocatoriaListAdjuntoConvocatoriaToAttach.getClass(), adjuntoConvocatoriaListAdjuntoConvocatoriaToAttach.getId());
                attachedAdjuntoConvocatoriaList.add(adjuntoConvocatoriaListAdjuntoConvocatoriaToAttach);
            }
            convocatoria.setAdjuntoConvocatoriaList(attachedAdjuntoConvocatoriaList);
            List<EvaluacionConvocatoria> attachedEvaluacionConvocatoriaList = new ArrayList<EvaluacionConvocatoria>();
            for (EvaluacionConvocatoria evaluacionConvocatoriaListEvaluacionConvocatoriaToAttach : convocatoria.getEvaluacionConvocatoriaList()) {
                evaluacionConvocatoriaListEvaluacionConvocatoriaToAttach = em.getReference(evaluacionConvocatoriaListEvaluacionConvocatoriaToAttach.getClass(), evaluacionConvocatoriaListEvaluacionConvocatoriaToAttach.getId());
                attachedEvaluacionConvocatoriaList.add(evaluacionConvocatoriaListEvaluacionConvocatoriaToAttach);
            }
            convocatoria.setEvaluacionConvocatoriaList(attachedEvaluacionConvocatoriaList);
            List<RupEstadoFinancieroConsolidadoConvocatoria> attachedRupEstadoFinancieroConsolidadoConvocatoriaList = new ArrayList<RupEstadoFinancieroConsolidadoConvocatoria>();
            for (RupEstadoFinancieroConsolidadoConvocatoria rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoriaToAttach : convocatoria.getRupEstadoFinancieroConsolidadoConvocatoriaList()) {
                rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoriaToAttach = em.getReference(rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoriaToAttach.getClass(), rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoriaToAttach.getId());
                attachedRupEstadoFinancieroConsolidadoConvocatoriaList.add(rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoriaToAttach);
            }
            convocatoria.setRupEstadoFinancieroConsolidadoConvocatoriaList(attachedRupEstadoFinancieroConsolidadoConvocatoriaList);
            List<EstadoConvocatoria> attachedEstadoConvocatoriaList = new ArrayList<EstadoConvocatoria>();
            for (EstadoConvocatoria estadoConvocatoriaListEstadoConvocatoriaToAttach : convocatoria.getEstadoConvocatoriaList()) {
                estadoConvocatoriaListEstadoConvocatoriaToAttach = em.getReference(estadoConvocatoriaListEstadoConvocatoriaToAttach.getClass(), estadoConvocatoriaListEstadoConvocatoriaToAttach.getId());
                attachedEstadoConvocatoriaList.add(estadoConvocatoriaListEstadoConvocatoriaToAttach);
            }
            convocatoria.setEstadoConvocatoriaList(attachedEstadoConvocatoriaList);
            List<PersonalRequeridoConvocatoria> attachedPersonalRequeridoConvocatoriaList = new ArrayList<PersonalRequeridoConvocatoria>();
            for (PersonalRequeridoConvocatoria personalRequeridoConvocatoriaListPersonalRequeridoConvocatoriaToAttach : convocatoria.getPersonalRequeridoConvocatoriaList()) {
                personalRequeridoConvocatoriaListPersonalRequeridoConvocatoriaToAttach = em.getReference(personalRequeridoConvocatoriaListPersonalRequeridoConvocatoriaToAttach.getClass(), personalRequeridoConvocatoriaListPersonalRequeridoConvocatoriaToAttach.getId());
                attachedPersonalRequeridoConvocatoriaList.add(personalRequeridoConvocatoriaListPersonalRequeridoConvocatoriaToAttach);
            }
            convocatoria.setPersonalRequeridoConvocatoriaList(attachedPersonalRequeridoConvocatoriaList);
            List<AnalisisConvocatoria> attachedAnalisisConvocatoriaList = new ArrayList<AnalisisConvocatoria>();
            for (AnalisisConvocatoria analisisConvocatoriaListAnalisisConvocatoriaToAttach : convocatoria.getAnalisisConvocatoriaList()) {
                analisisConvocatoriaListAnalisisConvocatoriaToAttach = em.getReference(analisisConvocatoriaListAnalisisConvocatoriaToAttach.getClass(), analisisConvocatoriaListAnalisisConvocatoriaToAttach.getId());
                attachedAnalisisConvocatoriaList.add(analisisConvocatoriaListAnalisisConvocatoriaToAttach);
            }
            convocatoria.setAnalisisConvocatoriaList(attachedAnalisisConvocatoriaList);
            em.persist(convocatoria);
            if (idTipoProyecto != null) {
                idTipoProyecto.getConvocatoriaList().add(convocatoria);
                idTipoProyecto = em.merge(idTipoProyecto);
            }
            if (idTipoProceso != null) {
                idTipoProceso.getConvocatoriaList().add(convocatoria);
                idTipoProceso = em.merge(idTipoProceso);
            }
            if (idFormaPago != null) {
                idFormaPago.getConvocatoriaList().add(convocatoria);
                idFormaPago = em.merge(idFormaPago);
            }
            for (MatrizRiesgo matrizRiesgoListMatrizRiesgo : convocatoria.getMatrizRiesgoList()) {
                Convocatoria oldIdConvocatoriaOfMatrizRiesgoListMatrizRiesgo = matrizRiesgoListMatrizRiesgo.getIdConvocatoria();
                matrizRiesgoListMatrizRiesgo.setIdConvocatoria(convocatoria);
                matrizRiesgoListMatrizRiesgo = em.merge(matrizRiesgoListMatrizRiesgo);
                if (oldIdConvocatoriaOfMatrizRiesgoListMatrizRiesgo != null) {
                    oldIdConvocatoriaOfMatrizRiesgoListMatrizRiesgo.getMatrizRiesgoList().remove(matrizRiesgoListMatrizRiesgo);
                    oldIdConvocatoriaOfMatrizRiesgoListMatrizRiesgo = em.merge(oldIdConvocatoriaOfMatrizRiesgoListMatrizRiesgo);
                }
            }
            for (Propuesta propuestaListPropuesta : convocatoria.getPropuestaList()) {
                Convocatoria oldIdConvocatoriaOfPropuestaListPropuesta = propuestaListPropuesta.getIdConvocatoria();
                propuestaListPropuesta.setIdConvocatoria(convocatoria);
                propuestaListPropuesta = em.merge(propuestaListPropuesta);
                if (oldIdConvocatoriaOfPropuestaListPropuesta != null) {
                    oldIdConvocatoriaOfPropuestaListPropuesta.getPropuestaList().remove(propuestaListPropuesta);
                    oldIdConvocatoriaOfPropuestaListPropuesta = em.merge(oldIdConvocatoriaOfPropuestaListPropuesta);
                }
            }
            for (SocioConvocatoria socioConvocatoriaListSocioConvocatoria : convocatoria.getSocioConvocatoriaList()) {
                Convocatoria oldIdConvocatoriaOfSocioConvocatoriaListSocioConvocatoria = socioConvocatoriaListSocioConvocatoria.getIdConvocatoria();
                socioConvocatoriaListSocioConvocatoria.setIdConvocatoria(convocatoria);
                socioConvocatoriaListSocioConvocatoria = em.merge(socioConvocatoriaListSocioConvocatoria);
                if (oldIdConvocatoriaOfSocioConvocatoriaListSocioConvocatoria != null) {
                    oldIdConvocatoriaOfSocioConvocatoriaListSocioConvocatoria.getSocioConvocatoriaList().remove(socioConvocatoriaListSocioConvocatoria);
                    oldIdConvocatoriaOfSocioConvocatoriaListSocioConvocatoria = em.merge(oldIdConvocatoriaOfSocioConvocatoriaListSocioConvocatoria);
                }
            }
            for (EquipoRequeridoConvocatoria equipoRequeridoConvocatoriaListEquipoRequeridoConvocatoria : convocatoria.getEquipoRequeridoConvocatoriaList()) {
                Convocatoria oldIdConvocatoriaOfEquipoRequeridoConvocatoriaListEquipoRequeridoConvocatoria = equipoRequeridoConvocatoriaListEquipoRequeridoConvocatoria.getIdConvocatoria();
                equipoRequeridoConvocatoriaListEquipoRequeridoConvocatoria.setIdConvocatoria(convocatoria);
                equipoRequeridoConvocatoriaListEquipoRequeridoConvocatoria = em.merge(equipoRequeridoConvocatoriaListEquipoRequeridoConvocatoria);
                if (oldIdConvocatoriaOfEquipoRequeridoConvocatoriaListEquipoRequeridoConvocatoria != null) {
                    oldIdConvocatoriaOfEquipoRequeridoConvocatoriaListEquipoRequeridoConvocatoria.getEquipoRequeridoConvocatoriaList().remove(equipoRequeridoConvocatoriaListEquipoRequeridoConvocatoria);
                    oldIdConvocatoriaOfEquipoRequeridoConvocatoriaListEquipoRequeridoConvocatoria = em.merge(oldIdConvocatoriaOfEquipoRequeridoConvocatoriaListEquipoRequeridoConvocatoria);
                }
            }
            for (AdjuntoConvocatoria adjuntoConvocatoriaListAdjuntoConvocatoria : convocatoria.getAdjuntoConvocatoriaList()) {
                Convocatoria oldIdConvocatoriaOfAdjuntoConvocatoriaListAdjuntoConvocatoria = adjuntoConvocatoriaListAdjuntoConvocatoria.getIdConvocatoria();
                adjuntoConvocatoriaListAdjuntoConvocatoria.setIdConvocatoria(convocatoria);
                adjuntoConvocatoriaListAdjuntoConvocatoria = em.merge(adjuntoConvocatoriaListAdjuntoConvocatoria);
                if (oldIdConvocatoriaOfAdjuntoConvocatoriaListAdjuntoConvocatoria != null) {
                    oldIdConvocatoriaOfAdjuntoConvocatoriaListAdjuntoConvocatoria.getAdjuntoConvocatoriaList().remove(adjuntoConvocatoriaListAdjuntoConvocatoria);
                    oldIdConvocatoriaOfAdjuntoConvocatoriaListAdjuntoConvocatoria = em.merge(oldIdConvocatoriaOfAdjuntoConvocatoriaListAdjuntoConvocatoria);
                }
            }
            for (EvaluacionConvocatoria evaluacionConvocatoriaListEvaluacionConvocatoria : convocatoria.getEvaluacionConvocatoriaList()) {
                Convocatoria oldIdConvocatoriaOfEvaluacionConvocatoriaListEvaluacionConvocatoria = evaluacionConvocatoriaListEvaluacionConvocatoria.getIdConvocatoria();
                evaluacionConvocatoriaListEvaluacionConvocatoria.setIdConvocatoria(convocatoria);
                evaluacionConvocatoriaListEvaluacionConvocatoria = em.merge(evaluacionConvocatoriaListEvaluacionConvocatoria);
                if (oldIdConvocatoriaOfEvaluacionConvocatoriaListEvaluacionConvocatoria != null) {
                    oldIdConvocatoriaOfEvaluacionConvocatoriaListEvaluacionConvocatoria.getEvaluacionConvocatoriaList().remove(evaluacionConvocatoriaListEvaluacionConvocatoria);
                    oldIdConvocatoriaOfEvaluacionConvocatoriaListEvaluacionConvocatoria = em.merge(oldIdConvocatoriaOfEvaluacionConvocatoriaListEvaluacionConvocatoria);
                }
            }
            for (RupEstadoFinancieroConsolidadoConvocatoria rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria : convocatoria.getRupEstadoFinancieroConsolidadoConvocatoriaList()) {
                Convocatoria oldIdConvocatoriaOfRupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria = rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria.getIdConvocatoria();
                rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria.setIdConvocatoria(convocatoria);
                rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria = em.merge(rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria);
                if (oldIdConvocatoriaOfRupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria != null) {
                    oldIdConvocatoriaOfRupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria.getRupEstadoFinancieroConsolidadoConvocatoriaList().remove(rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria);
                    oldIdConvocatoriaOfRupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria = em.merge(oldIdConvocatoriaOfRupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria);
                }
            }
            for (EstadoConvocatoria estadoConvocatoriaListEstadoConvocatoria : convocatoria.getEstadoConvocatoriaList()) {
                Convocatoria oldIdConvocatoriaOfEstadoConvocatoriaListEstadoConvocatoria = estadoConvocatoriaListEstadoConvocatoria.getIdConvocatoria();
                estadoConvocatoriaListEstadoConvocatoria.setIdConvocatoria(convocatoria);
                estadoConvocatoriaListEstadoConvocatoria = em.merge(estadoConvocatoriaListEstadoConvocatoria);
                if (oldIdConvocatoriaOfEstadoConvocatoriaListEstadoConvocatoria != null) {
                    oldIdConvocatoriaOfEstadoConvocatoriaListEstadoConvocatoria.getEstadoConvocatoriaList().remove(estadoConvocatoriaListEstadoConvocatoria);
                    oldIdConvocatoriaOfEstadoConvocatoriaListEstadoConvocatoria = em.merge(oldIdConvocatoriaOfEstadoConvocatoriaListEstadoConvocatoria);
                }
            }
            for (PersonalRequeridoConvocatoria personalRequeridoConvocatoriaListPersonalRequeridoConvocatoria : convocatoria.getPersonalRequeridoConvocatoriaList()) {
                Convocatoria oldIdConvocatoriaOfPersonalRequeridoConvocatoriaListPersonalRequeridoConvocatoria = personalRequeridoConvocatoriaListPersonalRequeridoConvocatoria.getIdConvocatoria();
                personalRequeridoConvocatoriaListPersonalRequeridoConvocatoria.setIdConvocatoria(convocatoria);
                personalRequeridoConvocatoriaListPersonalRequeridoConvocatoria = em.merge(personalRequeridoConvocatoriaListPersonalRequeridoConvocatoria);
                if (oldIdConvocatoriaOfPersonalRequeridoConvocatoriaListPersonalRequeridoConvocatoria != null) {
                    oldIdConvocatoriaOfPersonalRequeridoConvocatoriaListPersonalRequeridoConvocatoria.getPersonalRequeridoConvocatoriaList().remove(personalRequeridoConvocatoriaListPersonalRequeridoConvocatoria);
                    oldIdConvocatoriaOfPersonalRequeridoConvocatoriaListPersonalRequeridoConvocatoria = em.merge(oldIdConvocatoriaOfPersonalRequeridoConvocatoriaListPersonalRequeridoConvocatoria);
                }
            }
            for (AnalisisConvocatoria analisisConvocatoriaListAnalisisConvocatoria : convocatoria.getAnalisisConvocatoriaList()) {
                Convocatoria oldIdConvocatoriaOfAnalisisConvocatoriaListAnalisisConvocatoria = analisisConvocatoriaListAnalisisConvocatoria.getIdConvocatoria();
                analisisConvocatoriaListAnalisisConvocatoria.setIdConvocatoria(convocatoria);
                analisisConvocatoriaListAnalisisConvocatoria = em.merge(analisisConvocatoriaListAnalisisConvocatoria);
                if (oldIdConvocatoriaOfAnalisisConvocatoriaListAnalisisConvocatoria != null) {
                    oldIdConvocatoriaOfAnalisisConvocatoriaListAnalisisConvocatoria.getAnalisisConvocatoriaList().remove(analisisConvocatoriaListAnalisisConvocatoria);
                    oldIdConvocatoriaOfAnalisisConvocatoriaListAnalisisConvocatoria = em.merge(oldIdConvocatoriaOfAnalisisConvocatoriaListAnalisisConvocatoria);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findConvocatoria(convocatoria.getId()) != null) {
                throw new PreexistingEntityException("Convocatoria " + convocatoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Convocatoria convocatoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Convocatoria persistentConvocatoria = em.find(Convocatoria.class, convocatoria.getId());
            TipoProyectoConvocatoria idTipoProyectoOld = persistentConvocatoria.getIdTipoProyecto();
            TipoProyectoConvocatoria idTipoProyectoNew = convocatoria.getIdTipoProyecto();
            TipoProcesoConvocatoria idTipoProcesoOld = persistentConvocatoria.getIdTipoProceso();
            TipoProcesoConvocatoria idTipoProcesoNew = convocatoria.getIdTipoProceso();
            FormaPagoConvocatoria idFormaPagoOld = persistentConvocatoria.getIdFormaPago();
            FormaPagoConvocatoria idFormaPagoNew = convocatoria.getIdFormaPago();
            List<MatrizRiesgo> matrizRiesgoListOld = persistentConvocatoria.getMatrizRiesgoList();
            List<MatrizRiesgo> matrizRiesgoListNew = convocatoria.getMatrizRiesgoList();
            List<Propuesta> propuestaListOld = persistentConvocatoria.getPropuestaList();
            List<Propuesta> propuestaListNew = convocatoria.getPropuestaList();
            List<SocioConvocatoria> socioConvocatoriaListOld = persistentConvocatoria.getSocioConvocatoriaList();
            List<SocioConvocatoria> socioConvocatoriaListNew = convocatoria.getSocioConvocatoriaList();
            List<EquipoRequeridoConvocatoria> equipoRequeridoConvocatoriaListOld = persistentConvocatoria.getEquipoRequeridoConvocatoriaList();
            List<EquipoRequeridoConvocatoria> equipoRequeridoConvocatoriaListNew = convocatoria.getEquipoRequeridoConvocatoriaList();
            List<AdjuntoConvocatoria> adjuntoConvocatoriaListOld = persistentConvocatoria.getAdjuntoConvocatoriaList();
            List<AdjuntoConvocatoria> adjuntoConvocatoriaListNew = convocatoria.getAdjuntoConvocatoriaList();
            List<EvaluacionConvocatoria> evaluacionConvocatoriaListOld = persistentConvocatoria.getEvaluacionConvocatoriaList();
            List<EvaluacionConvocatoria> evaluacionConvocatoriaListNew = convocatoria.getEvaluacionConvocatoriaList();
            List<RupEstadoFinancieroConsolidadoConvocatoria> rupEstadoFinancieroConsolidadoConvocatoriaListOld = persistentConvocatoria.getRupEstadoFinancieroConsolidadoConvocatoriaList();
            List<RupEstadoFinancieroConsolidadoConvocatoria> rupEstadoFinancieroConsolidadoConvocatoriaListNew = convocatoria.getRupEstadoFinancieroConsolidadoConvocatoriaList();
            List<EstadoConvocatoria> estadoConvocatoriaListOld = persistentConvocatoria.getEstadoConvocatoriaList();
            List<EstadoConvocatoria> estadoConvocatoriaListNew = convocatoria.getEstadoConvocatoriaList();
            List<PersonalRequeridoConvocatoria> personalRequeridoConvocatoriaListOld = persistentConvocatoria.getPersonalRequeridoConvocatoriaList();
            List<PersonalRequeridoConvocatoria> personalRequeridoConvocatoriaListNew = convocatoria.getPersonalRequeridoConvocatoriaList();
            List<AnalisisConvocatoria> analisisConvocatoriaListOld = persistentConvocatoria.getAnalisisConvocatoriaList();
            List<AnalisisConvocatoria> analisisConvocatoriaListNew = convocatoria.getAnalisisConvocatoriaList();
            if (idTipoProyectoNew != null) {
                idTipoProyectoNew = em.getReference(idTipoProyectoNew.getClass(), idTipoProyectoNew.getId());
                convocatoria.setIdTipoProyecto(idTipoProyectoNew);
            }
            if (idTipoProcesoNew != null) {
                idTipoProcesoNew = em.getReference(idTipoProcesoNew.getClass(), idTipoProcesoNew.getId());
                convocatoria.setIdTipoProceso(idTipoProcesoNew);
            }
            if (idFormaPagoNew != null) {
                idFormaPagoNew = em.getReference(idFormaPagoNew.getClass(), idFormaPagoNew.getId());
                convocatoria.setIdFormaPago(idFormaPagoNew);
            }
            List<MatrizRiesgo> attachedMatrizRiesgoListNew = new ArrayList<MatrizRiesgo>();
            for (MatrizRiesgo matrizRiesgoListNewMatrizRiesgoToAttach : matrizRiesgoListNew) {
                matrizRiesgoListNewMatrizRiesgoToAttach = em.getReference(matrizRiesgoListNewMatrizRiesgoToAttach.getClass(), matrizRiesgoListNewMatrizRiesgoToAttach.getId());
                attachedMatrizRiesgoListNew.add(matrizRiesgoListNewMatrizRiesgoToAttach);
            }
            matrizRiesgoListNew = attachedMatrizRiesgoListNew;
            convocatoria.setMatrizRiesgoList(matrizRiesgoListNew);
            List<Propuesta> attachedPropuestaListNew = new ArrayList<Propuesta>();
            for (Propuesta propuestaListNewPropuestaToAttach : propuestaListNew) {
                propuestaListNewPropuestaToAttach = em.getReference(propuestaListNewPropuestaToAttach.getClass(), propuestaListNewPropuestaToAttach.getId());
                attachedPropuestaListNew.add(propuestaListNewPropuestaToAttach);
            }
            propuestaListNew = attachedPropuestaListNew;
            convocatoria.setPropuestaList(propuestaListNew);
            List<SocioConvocatoria> attachedSocioConvocatoriaListNew = new ArrayList<SocioConvocatoria>();
            for (SocioConvocatoria socioConvocatoriaListNewSocioConvocatoriaToAttach : socioConvocatoriaListNew) {
                socioConvocatoriaListNewSocioConvocatoriaToAttach = em.getReference(socioConvocatoriaListNewSocioConvocatoriaToAttach.getClass(), socioConvocatoriaListNewSocioConvocatoriaToAttach.getId());
                attachedSocioConvocatoriaListNew.add(socioConvocatoriaListNewSocioConvocatoriaToAttach);
            }
            socioConvocatoriaListNew = attachedSocioConvocatoriaListNew;
            convocatoria.setSocioConvocatoriaList(socioConvocatoriaListNew);
            List<EquipoRequeridoConvocatoria> attachedEquipoRequeridoConvocatoriaListNew = new ArrayList<EquipoRequeridoConvocatoria>();
            for (EquipoRequeridoConvocatoria equipoRequeridoConvocatoriaListNewEquipoRequeridoConvocatoriaToAttach : equipoRequeridoConvocatoriaListNew) {
                equipoRequeridoConvocatoriaListNewEquipoRequeridoConvocatoriaToAttach = em.getReference(equipoRequeridoConvocatoriaListNewEquipoRequeridoConvocatoriaToAttach.getClass(), equipoRequeridoConvocatoriaListNewEquipoRequeridoConvocatoriaToAttach.getId());
                attachedEquipoRequeridoConvocatoriaListNew.add(equipoRequeridoConvocatoriaListNewEquipoRequeridoConvocatoriaToAttach);
            }
            equipoRequeridoConvocatoriaListNew = attachedEquipoRequeridoConvocatoriaListNew;
            convocatoria.setEquipoRequeridoConvocatoriaList(equipoRequeridoConvocatoriaListNew);
            List<AdjuntoConvocatoria> attachedAdjuntoConvocatoriaListNew = new ArrayList<AdjuntoConvocatoria>();
            for (AdjuntoConvocatoria adjuntoConvocatoriaListNewAdjuntoConvocatoriaToAttach : adjuntoConvocatoriaListNew) {
                adjuntoConvocatoriaListNewAdjuntoConvocatoriaToAttach = em.getReference(adjuntoConvocatoriaListNewAdjuntoConvocatoriaToAttach.getClass(), adjuntoConvocatoriaListNewAdjuntoConvocatoriaToAttach.getId());
                attachedAdjuntoConvocatoriaListNew.add(adjuntoConvocatoriaListNewAdjuntoConvocatoriaToAttach);
            }
            adjuntoConvocatoriaListNew = attachedAdjuntoConvocatoriaListNew;
            convocatoria.setAdjuntoConvocatoriaList(adjuntoConvocatoriaListNew);
            List<EvaluacionConvocatoria> attachedEvaluacionConvocatoriaListNew = new ArrayList<EvaluacionConvocatoria>();
            for (EvaluacionConvocatoria evaluacionConvocatoriaListNewEvaluacionConvocatoriaToAttach : evaluacionConvocatoriaListNew) {
                evaluacionConvocatoriaListNewEvaluacionConvocatoriaToAttach = em.getReference(evaluacionConvocatoriaListNewEvaluacionConvocatoriaToAttach.getClass(), evaluacionConvocatoriaListNewEvaluacionConvocatoriaToAttach.getId());
                attachedEvaluacionConvocatoriaListNew.add(evaluacionConvocatoriaListNewEvaluacionConvocatoriaToAttach);
            }
            evaluacionConvocatoriaListNew = attachedEvaluacionConvocatoriaListNew;
            convocatoria.setEvaluacionConvocatoriaList(evaluacionConvocatoriaListNew);
            List<RupEstadoFinancieroConsolidadoConvocatoria> attachedRupEstadoFinancieroConsolidadoConvocatoriaListNew = new ArrayList<RupEstadoFinancieroConsolidadoConvocatoria>();
            for (RupEstadoFinancieroConsolidadoConvocatoria rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoriaToAttach : rupEstadoFinancieroConsolidadoConvocatoriaListNew) {
                rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoriaToAttach = em.getReference(rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoriaToAttach.getClass(), rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoriaToAttach.getId());
                attachedRupEstadoFinancieroConsolidadoConvocatoriaListNew.add(rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoriaToAttach);
            }
            rupEstadoFinancieroConsolidadoConvocatoriaListNew = attachedRupEstadoFinancieroConsolidadoConvocatoriaListNew;
            convocatoria.setRupEstadoFinancieroConsolidadoConvocatoriaList(rupEstadoFinancieroConsolidadoConvocatoriaListNew);
            List<EstadoConvocatoria> attachedEstadoConvocatoriaListNew = new ArrayList<EstadoConvocatoria>();
            for (EstadoConvocatoria estadoConvocatoriaListNewEstadoConvocatoriaToAttach : estadoConvocatoriaListNew) {
                estadoConvocatoriaListNewEstadoConvocatoriaToAttach = em.getReference(estadoConvocatoriaListNewEstadoConvocatoriaToAttach.getClass(), estadoConvocatoriaListNewEstadoConvocatoriaToAttach.getId());
                attachedEstadoConvocatoriaListNew.add(estadoConvocatoriaListNewEstadoConvocatoriaToAttach);
            }
            estadoConvocatoriaListNew = attachedEstadoConvocatoriaListNew;
            convocatoria.setEstadoConvocatoriaList(estadoConvocatoriaListNew);
            List<PersonalRequeridoConvocatoria> attachedPersonalRequeridoConvocatoriaListNew = new ArrayList<PersonalRequeridoConvocatoria>();
            for (PersonalRequeridoConvocatoria personalRequeridoConvocatoriaListNewPersonalRequeridoConvocatoriaToAttach : personalRequeridoConvocatoriaListNew) {
                personalRequeridoConvocatoriaListNewPersonalRequeridoConvocatoriaToAttach = em.getReference(personalRequeridoConvocatoriaListNewPersonalRequeridoConvocatoriaToAttach.getClass(), personalRequeridoConvocatoriaListNewPersonalRequeridoConvocatoriaToAttach.getId());
                attachedPersonalRequeridoConvocatoriaListNew.add(personalRequeridoConvocatoriaListNewPersonalRequeridoConvocatoriaToAttach);
            }
            personalRequeridoConvocatoriaListNew = attachedPersonalRequeridoConvocatoriaListNew;
            convocatoria.setPersonalRequeridoConvocatoriaList(personalRequeridoConvocatoriaListNew);
            List<AnalisisConvocatoria> attachedAnalisisConvocatoriaListNew = new ArrayList<AnalisisConvocatoria>();
            for (AnalisisConvocatoria analisisConvocatoriaListNewAnalisisConvocatoriaToAttach : analisisConvocatoriaListNew) {
                analisisConvocatoriaListNewAnalisisConvocatoriaToAttach = em.getReference(analisisConvocatoriaListNewAnalisisConvocatoriaToAttach.getClass(), analisisConvocatoriaListNewAnalisisConvocatoriaToAttach.getId());
                attachedAnalisisConvocatoriaListNew.add(analisisConvocatoriaListNewAnalisisConvocatoriaToAttach);
            }
            analisisConvocatoriaListNew = attachedAnalisisConvocatoriaListNew;
            convocatoria.setAnalisisConvocatoriaList(analisisConvocatoriaListNew);
            convocatoria = em.merge(convocatoria);
            if (idTipoProyectoOld != null && !idTipoProyectoOld.equals(idTipoProyectoNew)) {
                idTipoProyectoOld.getConvocatoriaList().remove(convocatoria);
                idTipoProyectoOld = em.merge(idTipoProyectoOld);
            }
            if (idTipoProyectoNew != null && !idTipoProyectoNew.equals(idTipoProyectoOld)) {
                idTipoProyectoNew.getConvocatoriaList().add(convocatoria);
                idTipoProyectoNew = em.merge(idTipoProyectoNew);
            }
            if (idTipoProcesoOld != null && !idTipoProcesoOld.equals(idTipoProcesoNew)) {
                idTipoProcesoOld.getConvocatoriaList().remove(convocatoria);
                idTipoProcesoOld = em.merge(idTipoProcesoOld);
            }
            if (idTipoProcesoNew != null && !idTipoProcesoNew.equals(idTipoProcesoOld)) {
                idTipoProcesoNew.getConvocatoriaList().add(convocatoria);
                idTipoProcesoNew = em.merge(idTipoProcesoNew);
            }
            if (idFormaPagoOld != null && !idFormaPagoOld.equals(idFormaPagoNew)) {
                idFormaPagoOld.getConvocatoriaList().remove(convocatoria);
                idFormaPagoOld = em.merge(idFormaPagoOld);
            }
            if (idFormaPagoNew != null && !idFormaPagoNew.equals(idFormaPagoOld)) {
                idFormaPagoNew.getConvocatoriaList().add(convocatoria);
                idFormaPagoNew = em.merge(idFormaPagoNew);
            }
            for (MatrizRiesgo matrizRiesgoListOldMatrizRiesgo : matrizRiesgoListOld) {
                if (!matrizRiesgoListNew.contains(matrizRiesgoListOldMatrizRiesgo)) {
                    matrizRiesgoListOldMatrizRiesgo.setIdConvocatoria(null);
                    matrizRiesgoListOldMatrizRiesgo = em.merge(matrizRiesgoListOldMatrizRiesgo);
                }
            }
            for (MatrizRiesgo matrizRiesgoListNewMatrizRiesgo : matrizRiesgoListNew) {
                if (!matrizRiesgoListOld.contains(matrizRiesgoListNewMatrizRiesgo)) {
                    Convocatoria oldIdConvocatoriaOfMatrizRiesgoListNewMatrizRiesgo = matrizRiesgoListNewMatrizRiesgo.getIdConvocatoria();
                    matrizRiesgoListNewMatrizRiesgo.setIdConvocatoria(convocatoria);
                    matrizRiesgoListNewMatrizRiesgo = em.merge(matrizRiesgoListNewMatrizRiesgo);
                    if (oldIdConvocatoriaOfMatrizRiesgoListNewMatrizRiesgo != null && !oldIdConvocatoriaOfMatrizRiesgoListNewMatrizRiesgo.equals(convocatoria)) {
                        oldIdConvocatoriaOfMatrizRiesgoListNewMatrizRiesgo.getMatrizRiesgoList().remove(matrizRiesgoListNewMatrizRiesgo);
                        oldIdConvocatoriaOfMatrizRiesgoListNewMatrizRiesgo = em.merge(oldIdConvocatoriaOfMatrizRiesgoListNewMatrizRiesgo);
                    }
                }
            }
            for (Propuesta propuestaListOldPropuesta : propuestaListOld) {
                if (!propuestaListNew.contains(propuestaListOldPropuesta)) {
                    propuestaListOldPropuesta.setIdConvocatoria(null);
                    propuestaListOldPropuesta = em.merge(propuestaListOldPropuesta);
                }
            }
            for (Propuesta propuestaListNewPropuesta : propuestaListNew) {
                if (!propuestaListOld.contains(propuestaListNewPropuesta)) {
                    Convocatoria oldIdConvocatoriaOfPropuestaListNewPropuesta = propuestaListNewPropuesta.getIdConvocatoria();
                    propuestaListNewPropuesta.setIdConvocatoria(convocatoria);
                    propuestaListNewPropuesta = em.merge(propuestaListNewPropuesta);
                    if (oldIdConvocatoriaOfPropuestaListNewPropuesta != null && !oldIdConvocatoriaOfPropuestaListNewPropuesta.equals(convocatoria)) {
                        oldIdConvocatoriaOfPropuestaListNewPropuesta.getPropuestaList().remove(propuestaListNewPropuesta);
                        oldIdConvocatoriaOfPropuestaListNewPropuesta = em.merge(oldIdConvocatoriaOfPropuestaListNewPropuesta);
                    }
                }
            }
            for (SocioConvocatoria socioConvocatoriaListOldSocioConvocatoria : socioConvocatoriaListOld) {
                if (!socioConvocatoriaListNew.contains(socioConvocatoriaListOldSocioConvocatoria)) {
                    socioConvocatoriaListOldSocioConvocatoria.setIdConvocatoria(null);
                    socioConvocatoriaListOldSocioConvocatoria = em.merge(socioConvocatoriaListOldSocioConvocatoria);
                }
            }
            for (SocioConvocatoria socioConvocatoriaListNewSocioConvocatoria : socioConvocatoriaListNew) {
                if (!socioConvocatoriaListOld.contains(socioConvocatoriaListNewSocioConvocatoria)) {
                    Convocatoria oldIdConvocatoriaOfSocioConvocatoriaListNewSocioConvocatoria = socioConvocatoriaListNewSocioConvocatoria.getIdConvocatoria();
                    socioConvocatoriaListNewSocioConvocatoria.setIdConvocatoria(convocatoria);
                    socioConvocatoriaListNewSocioConvocatoria = em.merge(socioConvocatoriaListNewSocioConvocatoria);
                    if (oldIdConvocatoriaOfSocioConvocatoriaListNewSocioConvocatoria != null && !oldIdConvocatoriaOfSocioConvocatoriaListNewSocioConvocatoria.equals(convocatoria)) {
                        oldIdConvocatoriaOfSocioConvocatoriaListNewSocioConvocatoria.getSocioConvocatoriaList().remove(socioConvocatoriaListNewSocioConvocatoria);
                        oldIdConvocatoriaOfSocioConvocatoriaListNewSocioConvocatoria = em.merge(oldIdConvocatoriaOfSocioConvocatoriaListNewSocioConvocatoria);
                    }
                }
            }
            for (EquipoRequeridoConvocatoria equipoRequeridoConvocatoriaListOldEquipoRequeridoConvocatoria : equipoRequeridoConvocatoriaListOld) {
                if (!equipoRequeridoConvocatoriaListNew.contains(equipoRequeridoConvocatoriaListOldEquipoRequeridoConvocatoria)) {
                    equipoRequeridoConvocatoriaListOldEquipoRequeridoConvocatoria.setIdConvocatoria(null);
                    equipoRequeridoConvocatoriaListOldEquipoRequeridoConvocatoria = em.merge(equipoRequeridoConvocatoriaListOldEquipoRequeridoConvocatoria);
                }
            }
            for (EquipoRequeridoConvocatoria equipoRequeridoConvocatoriaListNewEquipoRequeridoConvocatoria : equipoRequeridoConvocatoriaListNew) {
                if (!equipoRequeridoConvocatoriaListOld.contains(equipoRequeridoConvocatoriaListNewEquipoRequeridoConvocatoria)) {
                    Convocatoria oldIdConvocatoriaOfEquipoRequeridoConvocatoriaListNewEquipoRequeridoConvocatoria = equipoRequeridoConvocatoriaListNewEquipoRequeridoConvocatoria.getIdConvocatoria();
                    equipoRequeridoConvocatoriaListNewEquipoRequeridoConvocatoria.setIdConvocatoria(convocatoria);
                    equipoRequeridoConvocatoriaListNewEquipoRequeridoConvocatoria = em.merge(equipoRequeridoConvocatoriaListNewEquipoRequeridoConvocatoria);
                    if (oldIdConvocatoriaOfEquipoRequeridoConvocatoriaListNewEquipoRequeridoConvocatoria != null && !oldIdConvocatoriaOfEquipoRequeridoConvocatoriaListNewEquipoRequeridoConvocatoria.equals(convocatoria)) {
                        oldIdConvocatoriaOfEquipoRequeridoConvocatoriaListNewEquipoRequeridoConvocatoria.getEquipoRequeridoConvocatoriaList().remove(equipoRequeridoConvocatoriaListNewEquipoRequeridoConvocatoria);
                        oldIdConvocatoriaOfEquipoRequeridoConvocatoriaListNewEquipoRequeridoConvocatoria = em.merge(oldIdConvocatoriaOfEquipoRequeridoConvocatoriaListNewEquipoRequeridoConvocatoria);
                    }
                }
            }
            for (AdjuntoConvocatoria adjuntoConvocatoriaListOldAdjuntoConvocatoria : adjuntoConvocatoriaListOld) {
                if (!adjuntoConvocatoriaListNew.contains(adjuntoConvocatoriaListOldAdjuntoConvocatoria)) {
                    adjuntoConvocatoriaListOldAdjuntoConvocatoria.setIdConvocatoria(null);
                    adjuntoConvocatoriaListOldAdjuntoConvocatoria = em.merge(adjuntoConvocatoriaListOldAdjuntoConvocatoria);
                }
            }
            for (AdjuntoConvocatoria adjuntoConvocatoriaListNewAdjuntoConvocatoria : adjuntoConvocatoriaListNew) {
                if (!adjuntoConvocatoriaListOld.contains(adjuntoConvocatoriaListNewAdjuntoConvocatoria)) {
                    Convocatoria oldIdConvocatoriaOfAdjuntoConvocatoriaListNewAdjuntoConvocatoria = adjuntoConvocatoriaListNewAdjuntoConvocatoria.getIdConvocatoria();
                    adjuntoConvocatoriaListNewAdjuntoConvocatoria.setIdConvocatoria(convocatoria);
                    adjuntoConvocatoriaListNewAdjuntoConvocatoria = em.merge(adjuntoConvocatoriaListNewAdjuntoConvocatoria);
                    if (oldIdConvocatoriaOfAdjuntoConvocatoriaListNewAdjuntoConvocatoria != null && !oldIdConvocatoriaOfAdjuntoConvocatoriaListNewAdjuntoConvocatoria.equals(convocatoria)) {
                        oldIdConvocatoriaOfAdjuntoConvocatoriaListNewAdjuntoConvocatoria.getAdjuntoConvocatoriaList().remove(adjuntoConvocatoriaListNewAdjuntoConvocatoria);
                        oldIdConvocatoriaOfAdjuntoConvocatoriaListNewAdjuntoConvocatoria = em.merge(oldIdConvocatoriaOfAdjuntoConvocatoriaListNewAdjuntoConvocatoria);
                    }
                }
            }
            for (EvaluacionConvocatoria evaluacionConvocatoriaListOldEvaluacionConvocatoria : evaluacionConvocatoriaListOld) {
                if (!evaluacionConvocatoriaListNew.contains(evaluacionConvocatoriaListOldEvaluacionConvocatoria)) {
                    evaluacionConvocatoriaListOldEvaluacionConvocatoria.setIdConvocatoria(null);
                    evaluacionConvocatoriaListOldEvaluacionConvocatoria = em.merge(evaluacionConvocatoriaListOldEvaluacionConvocatoria);
                }
            }
            for (EvaluacionConvocatoria evaluacionConvocatoriaListNewEvaluacionConvocatoria : evaluacionConvocatoriaListNew) {
                if (!evaluacionConvocatoriaListOld.contains(evaluacionConvocatoriaListNewEvaluacionConvocatoria)) {
                    Convocatoria oldIdConvocatoriaOfEvaluacionConvocatoriaListNewEvaluacionConvocatoria = evaluacionConvocatoriaListNewEvaluacionConvocatoria.getIdConvocatoria();
                    evaluacionConvocatoriaListNewEvaluacionConvocatoria.setIdConvocatoria(convocatoria);
                    evaluacionConvocatoriaListNewEvaluacionConvocatoria = em.merge(evaluacionConvocatoriaListNewEvaluacionConvocatoria);
                    if (oldIdConvocatoriaOfEvaluacionConvocatoriaListNewEvaluacionConvocatoria != null && !oldIdConvocatoriaOfEvaluacionConvocatoriaListNewEvaluacionConvocatoria.equals(convocatoria)) {
                        oldIdConvocatoriaOfEvaluacionConvocatoriaListNewEvaluacionConvocatoria.getEvaluacionConvocatoriaList().remove(evaluacionConvocatoriaListNewEvaluacionConvocatoria);
                        oldIdConvocatoriaOfEvaluacionConvocatoriaListNewEvaluacionConvocatoria = em.merge(oldIdConvocatoriaOfEvaluacionConvocatoriaListNewEvaluacionConvocatoria);
                    }
                }
            }
            for (RupEstadoFinancieroConsolidadoConvocatoria rupEstadoFinancieroConsolidadoConvocatoriaListOldRupEstadoFinancieroConsolidadoConvocatoria : rupEstadoFinancieroConsolidadoConvocatoriaListOld) {
                if (!rupEstadoFinancieroConsolidadoConvocatoriaListNew.contains(rupEstadoFinancieroConsolidadoConvocatoriaListOldRupEstadoFinancieroConsolidadoConvocatoria)) {
                    rupEstadoFinancieroConsolidadoConvocatoriaListOldRupEstadoFinancieroConsolidadoConvocatoria.setIdConvocatoria(null);
                    rupEstadoFinancieroConsolidadoConvocatoriaListOldRupEstadoFinancieroConsolidadoConvocatoria = em.merge(rupEstadoFinancieroConsolidadoConvocatoriaListOldRupEstadoFinancieroConsolidadoConvocatoria);
                }
            }
            for (RupEstadoFinancieroConsolidadoConvocatoria rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria : rupEstadoFinancieroConsolidadoConvocatoriaListNew) {
                if (!rupEstadoFinancieroConsolidadoConvocatoriaListOld.contains(rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria)) {
                    Convocatoria oldIdConvocatoriaOfRupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria = rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria.getIdConvocatoria();
                    rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria.setIdConvocatoria(convocatoria);
                    rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria = em.merge(rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria);
                    if (oldIdConvocatoriaOfRupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria != null && !oldIdConvocatoriaOfRupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria.equals(convocatoria)) {
                        oldIdConvocatoriaOfRupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria.getRupEstadoFinancieroConsolidadoConvocatoriaList().remove(rupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria);
                        oldIdConvocatoriaOfRupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria = em.merge(oldIdConvocatoriaOfRupEstadoFinancieroConsolidadoConvocatoriaListNewRupEstadoFinancieroConsolidadoConvocatoria);
                    }
                }
            }
            for (EstadoConvocatoria estadoConvocatoriaListOldEstadoConvocatoria : estadoConvocatoriaListOld) {
                if (!estadoConvocatoriaListNew.contains(estadoConvocatoriaListOldEstadoConvocatoria)) {
                    estadoConvocatoriaListOldEstadoConvocatoria.setIdConvocatoria(null);
                    estadoConvocatoriaListOldEstadoConvocatoria = em.merge(estadoConvocatoriaListOldEstadoConvocatoria);
                }
            }
            for (EstadoConvocatoria estadoConvocatoriaListNewEstadoConvocatoria : estadoConvocatoriaListNew) {
                if (!estadoConvocatoriaListOld.contains(estadoConvocatoriaListNewEstadoConvocatoria)) {
                    Convocatoria oldIdConvocatoriaOfEstadoConvocatoriaListNewEstadoConvocatoria = estadoConvocatoriaListNewEstadoConvocatoria.getIdConvocatoria();
                    estadoConvocatoriaListNewEstadoConvocatoria.setIdConvocatoria(convocatoria);
                    estadoConvocatoriaListNewEstadoConvocatoria = em.merge(estadoConvocatoriaListNewEstadoConvocatoria);
                    if (oldIdConvocatoriaOfEstadoConvocatoriaListNewEstadoConvocatoria != null && !oldIdConvocatoriaOfEstadoConvocatoriaListNewEstadoConvocatoria.equals(convocatoria)) {
                        oldIdConvocatoriaOfEstadoConvocatoriaListNewEstadoConvocatoria.getEstadoConvocatoriaList().remove(estadoConvocatoriaListNewEstadoConvocatoria);
                        oldIdConvocatoriaOfEstadoConvocatoriaListNewEstadoConvocatoria = em.merge(oldIdConvocatoriaOfEstadoConvocatoriaListNewEstadoConvocatoria);
                    }
                }
            }
            for (PersonalRequeridoConvocatoria personalRequeridoConvocatoriaListOldPersonalRequeridoConvocatoria : personalRequeridoConvocatoriaListOld) {
                if (!personalRequeridoConvocatoriaListNew.contains(personalRequeridoConvocatoriaListOldPersonalRequeridoConvocatoria)) {
                    personalRequeridoConvocatoriaListOldPersonalRequeridoConvocatoria.setIdConvocatoria(null);
                    personalRequeridoConvocatoriaListOldPersonalRequeridoConvocatoria = em.merge(personalRequeridoConvocatoriaListOldPersonalRequeridoConvocatoria);
                }
            }
            for (PersonalRequeridoConvocatoria personalRequeridoConvocatoriaListNewPersonalRequeridoConvocatoria : personalRequeridoConvocatoriaListNew) {
                if (!personalRequeridoConvocatoriaListOld.contains(personalRequeridoConvocatoriaListNewPersonalRequeridoConvocatoria)) {
                    Convocatoria oldIdConvocatoriaOfPersonalRequeridoConvocatoriaListNewPersonalRequeridoConvocatoria = personalRequeridoConvocatoriaListNewPersonalRequeridoConvocatoria.getIdConvocatoria();
                    personalRequeridoConvocatoriaListNewPersonalRequeridoConvocatoria.setIdConvocatoria(convocatoria);
                    personalRequeridoConvocatoriaListNewPersonalRequeridoConvocatoria = em.merge(personalRequeridoConvocatoriaListNewPersonalRequeridoConvocatoria);
                    if (oldIdConvocatoriaOfPersonalRequeridoConvocatoriaListNewPersonalRequeridoConvocatoria != null && !oldIdConvocatoriaOfPersonalRequeridoConvocatoriaListNewPersonalRequeridoConvocatoria.equals(convocatoria)) {
                        oldIdConvocatoriaOfPersonalRequeridoConvocatoriaListNewPersonalRequeridoConvocatoria.getPersonalRequeridoConvocatoriaList().remove(personalRequeridoConvocatoriaListNewPersonalRequeridoConvocatoria);
                        oldIdConvocatoriaOfPersonalRequeridoConvocatoriaListNewPersonalRequeridoConvocatoria = em.merge(oldIdConvocatoriaOfPersonalRequeridoConvocatoriaListNewPersonalRequeridoConvocatoria);
                    }
                }
            }
            for (AnalisisConvocatoria analisisConvocatoriaListOldAnalisisConvocatoria : analisisConvocatoriaListOld) {
                if (!analisisConvocatoriaListNew.contains(analisisConvocatoriaListOldAnalisisConvocatoria)) {
                    analisisConvocatoriaListOldAnalisisConvocatoria.setIdConvocatoria(null);
                    analisisConvocatoriaListOldAnalisisConvocatoria = em.merge(analisisConvocatoriaListOldAnalisisConvocatoria);
                }
            }
            for (AnalisisConvocatoria analisisConvocatoriaListNewAnalisisConvocatoria : analisisConvocatoriaListNew) {
                if (!analisisConvocatoriaListOld.contains(analisisConvocatoriaListNewAnalisisConvocatoria)) {
                    Convocatoria oldIdConvocatoriaOfAnalisisConvocatoriaListNewAnalisisConvocatoria = analisisConvocatoriaListNewAnalisisConvocatoria.getIdConvocatoria();
                    analisisConvocatoriaListNewAnalisisConvocatoria.setIdConvocatoria(convocatoria);
                    analisisConvocatoriaListNewAnalisisConvocatoria = em.merge(analisisConvocatoriaListNewAnalisisConvocatoria);
                    if (oldIdConvocatoriaOfAnalisisConvocatoriaListNewAnalisisConvocatoria != null && !oldIdConvocatoriaOfAnalisisConvocatoriaListNewAnalisisConvocatoria.equals(convocatoria)) {
                        oldIdConvocatoriaOfAnalisisConvocatoriaListNewAnalisisConvocatoria.getAnalisisConvocatoriaList().remove(analisisConvocatoriaListNewAnalisisConvocatoria);
                        oldIdConvocatoriaOfAnalisisConvocatoriaListNewAnalisisConvocatoria = em.merge(oldIdConvocatoriaOfAnalisisConvocatoriaListNewAnalisisConvocatoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = convocatoria.getId();
                if (findConvocatoria(id) == null) {
                    throw new NonexistentEntityException("The convocatoria with id " + id + " no longer exists.");
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
            Convocatoria convocatoria;
            try {
                convocatoria = em.getReference(Convocatoria.class, id);
                convocatoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The convocatoria with id " + id + " no longer exists.", enfe);
            }
            TipoProyectoConvocatoria idTipoProyecto = convocatoria.getIdTipoProyecto();
            if (idTipoProyecto != null) {
                idTipoProyecto.getConvocatoriaList().remove(convocatoria);
                idTipoProyecto = em.merge(idTipoProyecto);
            }
            TipoProcesoConvocatoria idTipoProceso = convocatoria.getIdTipoProceso();
            if (idTipoProceso != null) {
                idTipoProceso.getConvocatoriaList().remove(convocatoria);
                idTipoProceso = em.merge(idTipoProceso);
            }
            FormaPagoConvocatoria idFormaPago = convocatoria.getIdFormaPago();
            if (idFormaPago != null) {
                idFormaPago.getConvocatoriaList().remove(convocatoria);
                idFormaPago = em.merge(idFormaPago);
            }
            List<MatrizRiesgo> matrizRiesgoList = convocatoria.getMatrizRiesgoList();
            for (MatrizRiesgo matrizRiesgoListMatrizRiesgo : matrizRiesgoList) {
                matrizRiesgoListMatrizRiesgo.setIdConvocatoria(null);
                matrizRiesgoListMatrizRiesgo = em.merge(matrizRiesgoListMatrizRiesgo);
            }
            List<Propuesta> propuestaList = convocatoria.getPropuestaList();
            for (Propuesta propuestaListPropuesta : propuestaList) {
                propuestaListPropuesta.setIdConvocatoria(null);
                propuestaListPropuesta = em.merge(propuestaListPropuesta);
            }
            List<SocioConvocatoria> socioConvocatoriaList = convocatoria.getSocioConvocatoriaList();
            for (SocioConvocatoria socioConvocatoriaListSocioConvocatoria : socioConvocatoriaList) {
                socioConvocatoriaListSocioConvocatoria.setIdConvocatoria(null);
                socioConvocatoriaListSocioConvocatoria = em.merge(socioConvocatoriaListSocioConvocatoria);
            }
            List<EquipoRequeridoConvocatoria> equipoRequeridoConvocatoriaList = convocatoria.getEquipoRequeridoConvocatoriaList();
            for (EquipoRequeridoConvocatoria equipoRequeridoConvocatoriaListEquipoRequeridoConvocatoria : equipoRequeridoConvocatoriaList) {
                equipoRequeridoConvocatoriaListEquipoRequeridoConvocatoria.setIdConvocatoria(null);
                equipoRequeridoConvocatoriaListEquipoRequeridoConvocatoria = em.merge(equipoRequeridoConvocatoriaListEquipoRequeridoConvocatoria);
            }
            List<AdjuntoConvocatoria> adjuntoConvocatoriaList = convocatoria.getAdjuntoConvocatoriaList();
            for (AdjuntoConvocatoria adjuntoConvocatoriaListAdjuntoConvocatoria : adjuntoConvocatoriaList) {
                adjuntoConvocatoriaListAdjuntoConvocatoria.setIdConvocatoria(null);
                adjuntoConvocatoriaListAdjuntoConvocatoria = em.merge(adjuntoConvocatoriaListAdjuntoConvocatoria);
            }
            List<EvaluacionConvocatoria> evaluacionConvocatoriaList = convocatoria.getEvaluacionConvocatoriaList();
            for (EvaluacionConvocatoria evaluacionConvocatoriaListEvaluacionConvocatoria : evaluacionConvocatoriaList) {
                evaluacionConvocatoriaListEvaluacionConvocatoria.setIdConvocatoria(null);
                evaluacionConvocatoriaListEvaluacionConvocatoria = em.merge(evaluacionConvocatoriaListEvaluacionConvocatoria);
            }
            List<RupEstadoFinancieroConsolidadoConvocatoria> rupEstadoFinancieroConsolidadoConvocatoriaList = convocatoria.getRupEstadoFinancieroConsolidadoConvocatoriaList();
            for (RupEstadoFinancieroConsolidadoConvocatoria rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria : rupEstadoFinancieroConsolidadoConvocatoriaList) {
                rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria.setIdConvocatoria(null);
                rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria = em.merge(rupEstadoFinancieroConsolidadoConvocatoriaListRupEstadoFinancieroConsolidadoConvocatoria);
            }
            List<EstadoConvocatoria> estadoConvocatoriaList = convocatoria.getEstadoConvocatoriaList();
            for (EstadoConvocatoria estadoConvocatoriaListEstadoConvocatoria : estadoConvocatoriaList) {
                estadoConvocatoriaListEstadoConvocatoria.setIdConvocatoria(null);
                estadoConvocatoriaListEstadoConvocatoria = em.merge(estadoConvocatoriaListEstadoConvocatoria);
            }
            List<PersonalRequeridoConvocatoria> personalRequeridoConvocatoriaList = convocatoria.getPersonalRequeridoConvocatoriaList();
            for (PersonalRequeridoConvocatoria personalRequeridoConvocatoriaListPersonalRequeridoConvocatoria : personalRequeridoConvocatoriaList) {
                personalRequeridoConvocatoriaListPersonalRequeridoConvocatoria.setIdConvocatoria(null);
                personalRequeridoConvocatoriaListPersonalRequeridoConvocatoria = em.merge(personalRequeridoConvocatoriaListPersonalRequeridoConvocatoria);
            }
            List<AnalisisConvocatoria> analisisConvocatoriaList = convocatoria.getAnalisisConvocatoriaList();
            for (AnalisisConvocatoria analisisConvocatoriaListAnalisisConvocatoria : analisisConvocatoriaList) {
                analisisConvocatoriaListAnalisisConvocatoria.setIdConvocatoria(null);
                analisisConvocatoriaListAnalisisConvocatoria = em.merge(analisisConvocatoriaListAnalisisConvocatoria);
            }
            em.remove(convocatoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Convocatoria> findConvocatoriaEntities() {
        return findConvocatoriaEntities(true, -1, -1);
    }

    public List<Convocatoria> findConvocatoriaEntities(int maxResults, int firstResult) {
        return findConvocatoriaEntities(false, maxResults, firstResult);
    }

    private List<Convocatoria> findConvocatoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Convocatoria.class));
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

    public Convocatoria findConvocatoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Convocatoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getConvocatoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Convocatoria> rt = cq.from(Convocatoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
