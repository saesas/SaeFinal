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
import com.sae.persistence.domain.TipoContratoProyecto;
import com.sae.persistence.domain.TipoContratante;
import com.sae.persistence.domain.Proyecto;
import com.sae.persistence.domain.OrigenOtrosi;
import com.sae.persistence.domain.ContratoProyectoProveedor;
import com.sae.persistence.domain.AdjuntoTecnica;
import com.sae.persistence.domain.EstadoContrato;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.ContratistaContrato;
import com.sae.persistence.domain.ActaInicio;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ContratoProyectoProveedorJpaController implements Serializable {

    public ContratoProyectoProveedorJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContratoProyectoProveedor contratoProyectoProveedor) throws PreexistingEntityException, Exception {
        if (contratoProyectoProveedor.getEstadoContratoList() == null) {
            contratoProyectoProveedor.setEstadoContratoList(new ArrayList<EstadoContrato>());
        }
        if (contratoProyectoProveedor.getContratistaContratoList() == null) {
            contratoProyectoProveedor.setContratistaContratoList(new ArrayList<ContratistaContrato>());
        }
        if (contratoProyectoProveedor.getActaInicioList() == null) {
            contratoProyectoProveedor.setActaInicioList(new ArrayList<ActaInicio>());
        }
        if (contratoProyectoProveedor.getContratoProyectoProveedorList() == null) {
            contratoProyectoProveedor.setContratoProyectoProveedorList(new ArrayList<ContratoProyectoProveedor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoContratoProyecto idTipo = contratoProyectoProveedor.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getId());
                contratoProyectoProveedor.setIdTipo(idTipo);
            }
            TipoContratante idTipoContratante = contratoProyectoProveedor.getIdTipoContratante();
            if (idTipoContratante != null) {
                idTipoContratante = em.getReference(idTipoContratante.getClass(), idTipoContratante.getId());
                contratoProyectoProveedor.setIdTipoContratante(idTipoContratante);
            }
            Proyecto idProyecto = contratoProyectoProveedor.getIdProyecto();
            if (idProyecto != null) {
                idProyecto = em.getReference(idProyecto.getClass(), idProyecto.getId());
                contratoProyectoProveedor.setIdProyecto(idProyecto);
            }
            OrigenOtrosi idOrigenOtrosi = contratoProyectoProveedor.getIdOrigenOtrosi();
            if (idOrigenOtrosi != null) {
                idOrigenOtrosi = em.getReference(idOrigenOtrosi.getClass(), idOrigenOtrosi.getId());
                contratoProyectoProveedor.setIdOrigenOtrosi(idOrigenOtrosi);
            }
            ContratoProyectoProveedor idContratoOtrosi = contratoProyectoProveedor.getIdContratoOtrosi();
            if (idContratoOtrosi != null) {
                idContratoOtrosi = em.getReference(idContratoOtrosi.getClass(), idContratoOtrosi.getId());
                contratoProyectoProveedor.setIdContratoOtrosi(idContratoOtrosi);
            }
            AdjuntoTecnica idAdjuntoLegalizacion = contratoProyectoProveedor.getIdAdjuntoLegalizacion();
            if (idAdjuntoLegalizacion != null) {
                idAdjuntoLegalizacion = em.getReference(idAdjuntoLegalizacion.getClass(), idAdjuntoLegalizacion.getId());
                contratoProyectoProveedor.setIdAdjuntoLegalizacion(idAdjuntoLegalizacion);
            }
            AdjuntoTecnica idAdjunto = contratoProyectoProveedor.getIdAdjunto();
            if (idAdjunto != null) {
                idAdjunto = em.getReference(idAdjunto.getClass(), idAdjunto.getId());
                contratoProyectoProveedor.setIdAdjunto(idAdjunto);
            }
            List<EstadoContrato> attachedEstadoContratoList = new ArrayList<EstadoContrato>();
            for (EstadoContrato estadoContratoListEstadoContratoToAttach : contratoProyectoProveedor.getEstadoContratoList()) {
                estadoContratoListEstadoContratoToAttach = em.getReference(estadoContratoListEstadoContratoToAttach.getClass(), estadoContratoListEstadoContratoToAttach.getId());
                attachedEstadoContratoList.add(estadoContratoListEstadoContratoToAttach);
            }
            contratoProyectoProveedor.setEstadoContratoList(attachedEstadoContratoList);
            List<ContratistaContrato> attachedContratistaContratoList = new ArrayList<ContratistaContrato>();
            for (ContratistaContrato contratistaContratoListContratistaContratoToAttach : contratoProyectoProveedor.getContratistaContratoList()) {
                contratistaContratoListContratistaContratoToAttach = em.getReference(contratistaContratoListContratistaContratoToAttach.getClass(), contratistaContratoListContratistaContratoToAttach.getId());
                attachedContratistaContratoList.add(contratistaContratoListContratistaContratoToAttach);
            }
            contratoProyectoProveedor.setContratistaContratoList(attachedContratistaContratoList);
            List<ActaInicio> attachedActaInicioList = new ArrayList<ActaInicio>();
            for (ActaInicio actaInicioListActaInicioToAttach : contratoProyectoProveedor.getActaInicioList()) {
                actaInicioListActaInicioToAttach = em.getReference(actaInicioListActaInicioToAttach.getClass(), actaInicioListActaInicioToAttach.getId());
                attachedActaInicioList.add(actaInicioListActaInicioToAttach);
            }
            contratoProyectoProveedor.setActaInicioList(attachedActaInicioList);
            List<ContratoProyectoProveedor> attachedContratoProyectoProveedorList = new ArrayList<ContratoProyectoProveedor>();
            for (ContratoProyectoProveedor contratoProyectoProveedorListContratoProyectoProveedorToAttach : contratoProyectoProveedor.getContratoProyectoProveedorList()) {
                contratoProyectoProveedorListContratoProyectoProveedorToAttach = em.getReference(contratoProyectoProveedorListContratoProyectoProveedorToAttach.getClass(), contratoProyectoProveedorListContratoProyectoProveedorToAttach.getId());
                attachedContratoProyectoProveedorList.add(contratoProyectoProveedorListContratoProyectoProveedorToAttach);
            }
            contratoProyectoProveedor.setContratoProyectoProveedorList(attachedContratoProyectoProveedorList);
            em.persist(contratoProyectoProveedor);
            if (idTipo != null) {
                idTipo.getContratoProyectoProveedorList().add(contratoProyectoProveedor);
                idTipo = em.merge(idTipo);
            }
            if (idTipoContratante != null) {
                idTipoContratante.getContratoProyectoProveedorList().add(contratoProyectoProveedor);
                idTipoContratante = em.merge(idTipoContratante);
            }
            if (idProyecto != null) {
                idProyecto.getContratoProyectoProveedorList().add(contratoProyectoProveedor);
                idProyecto = em.merge(idProyecto);
            }
            if (idOrigenOtrosi != null) {
                idOrigenOtrosi.getContratoProyectoProveedorList().add(contratoProyectoProveedor);
                idOrigenOtrosi = em.merge(idOrigenOtrosi);
            }
            if (idContratoOtrosi != null) {
                idContratoOtrosi.getContratoProyectoProveedorList().add(contratoProyectoProveedor);
                idContratoOtrosi = em.merge(idContratoOtrosi);
            }
            if (idAdjuntoLegalizacion != null) {
                idAdjuntoLegalizacion.getContratoProyectoProveedorList().add(contratoProyectoProveedor);
                idAdjuntoLegalizacion = em.merge(idAdjuntoLegalizacion);
            }
            if (idAdjunto != null) {
                idAdjunto.getContratoProyectoProveedorList().add(contratoProyectoProveedor);
                idAdjunto = em.merge(idAdjunto);
            }
            for (EstadoContrato estadoContratoListEstadoContrato : contratoProyectoProveedor.getEstadoContratoList()) {
                ContratoProyectoProveedor oldIdContratoOfEstadoContratoListEstadoContrato = estadoContratoListEstadoContrato.getIdContrato();
                estadoContratoListEstadoContrato.setIdContrato(contratoProyectoProveedor);
                estadoContratoListEstadoContrato = em.merge(estadoContratoListEstadoContrato);
                if (oldIdContratoOfEstadoContratoListEstadoContrato != null) {
                    oldIdContratoOfEstadoContratoListEstadoContrato.getEstadoContratoList().remove(estadoContratoListEstadoContrato);
                    oldIdContratoOfEstadoContratoListEstadoContrato = em.merge(oldIdContratoOfEstadoContratoListEstadoContrato);
                }
            }
            for (ContratistaContrato contratistaContratoListContratistaContrato : contratoProyectoProveedor.getContratistaContratoList()) {
                ContratoProyectoProveedor oldIdContratoOfContratistaContratoListContratistaContrato = contratistaContratoListContratistaContrato.getIdContrato();
                contratistaContratoListContratistaContrato.setIdContrato(contratoProyectoProveedor);
                contratistaContratoListContratistaContrato = em.merge(contratistaContratoListContratistaContrato);
                if (oldIdContratoOfContratistaContratoListContratistaContrato != null) {
                    oldIdContratoOfContratistaContratoListContratistaContrato.getContratistaContratoList().remove(contratistaContratoListContratistaContrato);
                    oldIdContratoOfContratistaContratoListContratistaContrato = em.merge(oldIdContratoOfContratistaContratoListContratistaContrato);
                }
            }
            for (ActaInicio actaInicioListActaInicio : contratoProyectoProveedor.getActaInicioList()) {
                ContratoProyectoProveedor oldIdContratoOfActaInicioListActaInicio = actaInicioListActaInicio.getIdContrato();
                actaInicioListActaInicio.setIdContrato(contratoProyectoProveedor);
                actaInicioListActaInicio = em.merge(actaInicioListActaInicio);
                if (oldIdContratoOfActaInicioListActaInicio != null) {
                    oldIdContratoOfActaInicioListActaInicio.getActaInicioList().remove(actaInicioListActaInicio);
                    oldIdContratoOfActaInicioListActaInicio = em.merge(oldIdContratoOfActaInicioListActaInicio);
                }
            }
            for (ContratoProyectoProveedor contratoProyectoProveedorListContratoProyectoProveedor : contratoProyectoProveedor.getContratoProyectoProveedorList()) {
                ContratoProyectoProveedor oldIdContratoOtrosiOfContratoProyectoProveedorListContratoProyectoProveedor = contratoProyectoProveedorListContratoProyectoProveedor.getIdContratoOtrosi();
                contratoProyectoProveedorListContratoProyectoProveedor.setIdContratoOtrosi(contratoProyectoProveedor);
                contratoProyectoProveedorListContratoProyectoProveedor = em.merge(contratoProyectoProveedorListContratoProyectoProveedor);
                if (oldIdContratoOtrosiOfContratoProyectoProveedorListContratoProyectoProveedor != null) {
                    oldIdContratoOtrosiOfContratoProyectoProveedorListContratoProyectoProveedor.getContratoProyectoProveedorList().remove(contratoProyectoProveedorListContratoProyectoProveedor);
                    oldIdContratoOtrosiOfContratoProyectoProveedorListContratoProyectoProveedor = em.merge(oldIdContratoOtrosiOfContratoProyectoProveedorListContratoProyectoProveedor);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findContratoProyectoProveedor(contratoProyectoProveedor.getId()) != null) {
                throw new PreexistingEntityException("ContratoProyectoProveedor " + contratoProyectoProveedor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContratoProyectoProveedor contratoProyectoProveedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContratoProyectoProveedor persistentContratoProyectoProveedor = em.find(ContratoProyectoProveedor.class, contratoProyectoProveedor.getId());
            TipoContratoProyecto idTipoOld = persistentContratoProyectoProveedor.getIdTipo();
            TipoContratoProyecto idTipoNew = contratoProyectoProveedor.getIdTipo();
            TipoContratante idTipoContratanteOld = persistentContratoProyectoProveedor.getIdTipoContratante();
            TipoContratante idTipoContratanteNew = contratoProyectoProveedor.getIdTipoContratante();
            Proyecto idProyectoOld = persistentContratoProyectoProveedor.getIdProyecto();
            Proyecto idProyectoNew = contratoProyectoProveedor.getIdProyecto();
            OrigenOtrosi idOrigenOtrosiOld = persistentContratoProyectoProveedor.getIdOrigenOtrosi();
            OrigenOtrosi idOrigenOtrosiNew = contratoProyectoProveedor.getIdOrigenOtrosi();
            ContratoProyectoProveedor idContratoOtrosiOld = persistentContratoProyectoProveedor.getIdContratoOtrosi();
            ContratoProyectoProveedor idContratoOtrosiNew = contratoProyectoProveedor.getIdContratoOtrosi();
            AdjuntoTecnica idAdjuntoLegalizacionOld = persistentContratoProyectoProveedor.getIdAdjuntoLegalizacion();
            AdjuntoTecnica idAdjuntoLegalizacionNew = contratoProyectoProveedor.getIdAdjuntoLegalizacion();
            AdjuntoTecnica idAdjuntoOld = persistentContratoProyectoProveedor.getIdAdjunto();
            AdjuntoTecnica idAdjuntoNew = contratoProyectoProveedor.getIdAdjunto();
            List<EstadoContrato> estadoContratoListOld = persistentContratoProyectoProveedor.getEstadoContratoList();
            List<EstadoContrato> estadoContratoListNew = contratoProyectoProveedor.getEstadoContratoList();
            List<ContratistaContrato> contratistaContratoListOld = persistentContratoProyectoProveedor.getContratistaContratoList();
            List<ContratistaContrato> contratistaContratoListNew = contratoProyectoProveedor.getContratistaContratoList();
            List<ActaInicio> actaInicioListOld = persistentContratoProyectoProveedor.getActaInicioList();
            List<ActaInicio> actaInicioListNew = contratoProyectoProveedor.getActaInicioList();
            List<ContratoProyectoProveedor> contratoProyectoProveedorListOld = persistentContratoProyectoProveedor.getContratoProyectoProveedorList();
            List<ContratoProyectoProveedor> contratoProyectoProveedorListNew = contratoProyectoProveedor.getContratoProyectoProveedorList();
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getId());
                contratoProyectoProveedor.setIdTipo(idTipoNew);
            }
            if (idTipoContratanteNew != null) {
                idTipoContratanteNew = em.getReference(idTipoContratanteNew.getClass(), idTipoContratanteNew.getId());
                contratoProyectoProveedor.setIdTipoContratante(idTipoContratanteNew);
            }
            if (idProyectoNew != null) {
                idProyectoNew = em.getReference(idProyectoNew.getClass(), idProyectoNew.getId());
                contratoProyectoProveedor.setIdProyecto(idProyectoNew);
            }
            if (idOrigenOtrosiNew != null) {
                idOrigenOtrosiNew = em.getReference(idOrigenOtrosiNew.getClass(), idOrigenOtrosiNew.getId());
                contratoProyectoProveedor.setIdOrigenOtrosi(idOrigenOtrosiNew);
            }
            if (idContratoOtrosiNew != null) {
                idContratoOtrosiNew = em.getReference(idContratoOtrosiNew.getClass(), idContratoOtrosiNew.getId());
                contratoProyectoProveedor.setIdContratoOtrosi(idContratoOtrosiNew);
            }
            if (idAdjuntoLegalizacionNew != null) {
                idAdjuntoLegalizacionNew = em.getReference(idAdjuntoLegalizacionNew.getClass(), idAdjuntoLegalizacionNew.getId());
                contratoProyectoProveedor.setIdAdjuntoLegalizacion(idAdjuntoLegalizacionNew);
            }
            if (idAdjuntoNew != null) {
                idAdjuntoNew = em.getReference(idAdjuntoNew.getClass(), idAdjuntoNew.getId());
                contratoProyectoProveedor.setIdAdjunto(idAdjuntoNew);
            }
            List<EstadoContrato> attachedEstadoContratoListNew = new ArrayList<EstadoContrato>();
            for (EstadoContrato estadoContratoListNewEstadoContratoToAttach : estadoContratoListNew) {
                estadoContratoListNewEstadoContratoToAttach = em.getReference(estadoContratoListNewEstadoContratoToAttach.getClass(), estadoContratoListNewEstadoContratoToAttach.getId());
                attachedEstadoContratoListNew.add(estadoContratoListNewEstadoContratoToAttach);
            }
            estadoContratoListNew = attachedEstadoContratoListNew;
            contratoProyectoProveedor.setEstadoContratoList(estadoContratoListNew);
            List<ContratistaContrato> attachedContratistaContratoListNew = new ArrayList<ContratistaContrato>();
            for (ContratistaContrato contratistaContratoListNewContratistaContratoToAttach : contratistaContratoListNew) {
                contratistaContratoListNewContratistaContratoToAttach = em.getReference(contratistaContratoListNewContratistaContratoToAttach.getClass(), contratistaContratoListNewContratistaContratoToAttach.getId());
                attachedContratistaContratoListNew.add(contratistaContratoListNewContratistaContratoToAttach);
            }
            contratistaContratoListNew = attachedContratistaContratoListNew;
            contratoProyectoProveedor.setContratistaContratoList(contratistaContratoListNew);
            List<ActaInicio> attachedActaInicioListNew = new ArrayList<ActaInicio>();
            for (ActaInicio actaInicioListNewActaInicioToAttach : actaInicioListNew) {
                actaInicioListNewActaInicioToAttach = em.getReference(actaInicioListNewActaInicioToAttach.getClass(), actaInicioListNewActaInicioToAttach.getId());
                attachedActaInicioListNew.add(actaInicioListNewActaInicioToAttach);
            }
            actaInicioListNew = attachedActaInicioListNew;
            contratoProyectoProveedor.setActaInicioList(actaInicioListNew);
            List<ContratoProyectoProveedor> attachedContratoProyectoProveedorListNew = new ArrayList<ContratoProyectoProveedor>();
            for (ContratoProyectoProveedor contratoProyectoProveedorListNewContratoProyectoProveedorToAttach : contratoProyectoProveedorListNew) {
                contratoProyectoProveedorListNewContratoProyectoProveedorToAttach = em.getReference(contratoProyectoProveedorListNewContratoProyectoProveedorToAttach.getClass(), contratoProyectoProveedorListNewContratoProyectoProveedorToAttach.getId());
                attachedContratoProyectoProveedorListNew.add(contratoProyectoProveedorListNewContratoProyectoProveedorToAttach);
            }
            contratoProyectoProveedorListNew = attachedContratoProyectoProveedorListNew;
            contratoProyectoProveedor.setContratoProyectoProveedorList(contratoProyectoProveedorListNew);
            contratoProyectoProveedor = em.merge(contratoProyectoProveedor);
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getContratoProyectoProveedorList().remove(contratoProyectoProveedor);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getContratoProyectoProveedorList().add(contratoProyectoProveedor);
                idTipoNew = em.merge(idTipoNew);
            }
            if (idTipoContratanteOld != null && !idTipoContratanteOld.equals(idTipoContratanteNew)) {
                idTipoContratanteOld.getContratoProyectoProveedorList().remove(contratoProyectoProveedor);
                idTipoContratanteOld = em.merge(idTipoContratanteOld);
            }
            if (idTipoContratanteNew != null && !idTipoContratanteNew.equals(idTipoContratanteOld)) {
                idTipoContratanteNew.getContratoProyectoProveedorList().add(contratoProyectoProveedor);
                idTipoContratanteNew = em.merge(idTipoContratanteNew);
            }
            if (idProyectoOld != null && !idProyectoOld.equals(idProyectoNew)) {
                idProyectoOld.getContratoProyectoProveedorList().remove(contratoProyectoProveedor);
                idProyectoOld = em.merge(idProyectoOld);
            }
            if (idProyectoNew != null && !idProyectoNew.equals(idProyectoOld)) {
                idProyectoNew.getContratoProyectoProveedorList().add(contratoProyectoProveedor);
                idProyectoNew = em.merge(idProyectoNew);
            }
            if (idOrigenOtrosiOld != null && !idOrigenOtrosiOld.equals(idOrigenOtrosiNew)) {
                idOrigenOtrosiOld.getContratoProyectoProveedorList().remove(contratoProyectoProveedor);
                idOrigenOtrosiOld = em.merge(idOrigenOtrosiOld);
            }
            if (idOrigenOtrosiNew != null && !idOrigenOtrosiNew.equals(idOrigenOtrosiOld)) {
                idOrigenOtrosiNew.getContratoProyectoProveedorList().add(contratoProyectoProveedor);
                idOrigenOtrosiNew = em.merge(idOrigenOtrosiNew);
            }
            if (idContratoOtrosiOld != null && !idContratoOtrosiOld.equals(idContratoOtrosiNew)) {
                idContratoOtrosiOld.getContratoProyectoProveedorList().remove(contratoProyectoProveedor);
                idContratoOtrosiOld = em.merge(idContratoOtrosiOld);
            }
            if (idContratoOtrosiNew != null && !idContratoOtrosiNew.equals(idContratoOtrosiOld)) {
                idContratoOtrosiNew.getContratoProyectoProveedorList().add(contratoProyectoProveedor);
                idContratoOtrosiNew = em.merge(idContratoOtrosiNew);
            }
            if (idAdjuntoLegalizacionOld != null && !idAdjuntoLegalizacionOld.equals(idAdjuntoLegalizacionNew)) {
                idAdjuntoLegalizacionOld.getContratoProyectoProveedorList().remove(contratoProyectoProveedor);
                idAdjuntoLegalizacionOld = em.merge(idAdjuntoLegalizacionOld);
            }
            if (idAdjuntoLegalizacionNew != null && !idAdjuntoLegalizacionNew.equals(idAdjuntoLegalizacionOld)) {
                idAdjuntoLegalizacionNew.getContratoProyectoProveedorList().add(contratoProyectoProveedor);
                idAdjuntoLegalizacionNew = em.merge(idAdjuntoLegalizacionNew);
            }
            if (idAdjuntoOld != null && !idAdjuntoOld.equals(idAdjuntoNew)) {
                idAdjuntoOld.getContratoProyectoProveedorList().remove(contratoProyectoProveedor);
                idAdjuntoOld = em.merge(idAdjuntoOld);
            }
            if (idAdjuntoNew != null && !idAdjuntoNew.equals(idAdjuntoOld)) {
                idAdjuntoNew.getContratoProyectoProveedorList().add(contratoProyectoProveedor);
                idAdjuntoNew = em.merge(idAdjuntoNew);
            }
            for (EstadoContrato estadoContratoListOldEstadoContrato : estadoContratoListOld) {
                if (!estadoContratoListNew.contains(estadoContratoListOldEstadoContrato)) {
                    estadoContratoListOldEstadoContrato.setIdContrato(null);
                    estadoContratoListOldEstadoContrato = em.merge(estadoContratoListOldEstadoContrato);
                }
            }
            for (EstadoContrato estadoContratoListNewEstadoContrato : estadoContratoListNew) {
                if (!estadoContratoListOld.contains(estadoContratoListNewEstadoContrato)) {
                    ContratoProyectoProveedor oldIdContratoOfEstadoContratoListNewEstadoContrato = estadoContratoListNewEstadoContrato.getIdContrato();
                    estadoContratoListNewEstadoContrato.setIdContrato(contratoProyectoProveedor);
                    estadoContratoListNewEstadoContrato = em.merge(estadoContratoListNewEstadoContrato);
                    if (oldIdContratoOfEstadoContratoListNewEstadoContrato != null && !oldIdContratoOfEstadoContratoListNewEstadoContrato.equals(contratoProyectoProveedor)) {
                        oldIdContratoOfEstadoContratoListNewEstadoContrato.getEstadoContratoList().remove(estadoContratoListNewEstadoContrato);
                        oldIdContratoOfEstadoContratoListNewEstadoContrato = em.merge(oldIdContratoOfEstadoContratoListNewEstadoContrato);
                    }
                }
            }
            for (ContratistaContrato contratistaContratoListOldContratistaContrato : contratistaContratoListOld) {
                if (!contratistaContratoListNew.contains(contratistaContratoListOldContratistaContrato)) {
                    contratistaContratoListOldContratistaContrato.setIdContrato(null);
                    contratistaContratoListOldContratistaContrato = em.merge(contratistaContratoListOldContratistaContrato);
                }
            }
            for (ContratistaContrato contratistaContratoListNewContratistaContrato : contratistaContratoListNew) {
                if (!contratistaContratoListOld.contains(contratistaContratoListNewContratistaContrato)) {
                    ContratoProyectoProveedor oldIdContratoOfContratistaContratoListNewContratistaContrato = contratistaContratoListNewContratistaContrato.getIdContrato();
                    contratistaContratoListNewContratistaContrato.setIdContrato(contratoProyectoProveedor);
                    contratistaContratoListNewContratistaContrato = em.merge(contratistaContratoListNewContratistaContrato);
                    if (oldIdContratoOfContratistaContratoListNewContratistaContrato != null && !oldIdContratoOfContratistaContratoListNewContratistaContrato.equals(contratoProyectoProveedor)) {
                        oldIdContratoOfContratistaContratoListNewContratistaContrato.getContratistaContratoList().remove(contratistaContratoListNewContratistaContrato);
                        oldIdContratoOfContratistaContratoListNewContratistaContrato = em.merge(oldIdContratoOfContratistaContratoListNewContratistaContrato);
                    }
                }
            }
            for (ActaInicio actaInicioListOldActaInicio : actaInicioListOld) {
                if (!actaInicioListNew.contains(actaInicioListOldActaInicio)) {
                    actaInicioListOldActaInicio.setIdContrato(null);
                    actaInicioListOldActaInicio = em.merge(actaInicioListOldActaInicio);
                }
            }
            for (ActaInicio actaInicioListNewActaInicio : actaInicioListNew) {
                if (!actaInicioListOld.contains(actaInicioListNewActaInicio)) {
                    ContratoProyectoProveedor oldIdContratoOfActaInicioListNewActaInicio = actaInicioListNewActaInicio.getIdContrato();
                    actaInicioListNewActaInicio.setIdContrato(contratoProyectoProveedor);
                    actaInicioListNewActaInicio = em.merge(actaInicioListNewActaInicio);
                    if (oldIdContratoOfActaInicioListNewActaInicio != null && !oldIdContratoOfActaInicioListNewActaInicio.equals(contratoProyectoProveedor)) {
                        oldIdContratoOfActaInicioListNewActaInicio.getActaInicioList().remove(actaInicioListNewActaInicio);
                        oldIdContratoOfActaInicioListNewActaInicio = em.merge(oldIdContratoOfActaInicioListNewActaInicio);
                    }
                }
            }
            for (ContratoProyectoProveedor contratoProyectoProveedorListOldContratoProyectoProveedor : contratoProyectoProveedorListOld) {
                if (!contratoProyectoProveedorListNew.contains(contratoProyectoProveedorListOldContratoProyectoProveedor)) {
                    contratoProyectoProveedorListOldContratoProyectoProveedor.setIdContratoOtrosi(null);
                    contratoProyectoProveedorListOldContratoProyectoProveedor = em.merge(contratoProyectoProveedorListOldContratoProyectoProveedor);
                }
            }
            for (ContratoProyectoProveedor contratoProyectoProveedorListNewContratoProyectoProveedor : contratoProyectoProveedorListNew) {
                if (!contratoProyectoProveedorListOld.contains(contratoProyectoProveedorListNewContratoProyectoProveedor)) {
                    ContratoProyectoProveedor oldIdContratoOtrosiOfContratoProyectoProveedorListNewContratoProyectoProveedor = contratoProyectoProveedorListNewContratoProyectoProveedor.getIdContratoOtrosi();
                    contratoProyectoProveedorListNewContratoProyectoProveedor.setIdContratoOtrosi(contratoProyectoProveedor);
                    contratoProyectoProveedorListNewContratoProyectoProveedor = em.merge(contratoProyectoProveedorListNewContratoProyectoProveedor);
                    if (oldIdContratoOtrosiOfContratoProyectoProveedorListNewContratoProyectoProveedor != null && !oldIdContratoOtrosiOfContratoProyectoProveedorListNewContratoProyectoProveedor.equals(contratoProyectoProveedor)) {
                        oldIdContratoOtrosiOfContratoProyectoProveedorListNewContratoProyectoProveedor.getContratoProyectoProveedorList().remove(contratoProyectoProveedorListNewContratoProyectoProveedor);
                        oldIdContratoOtrosiOfContratoProyectoProveedorListNewContratoProyectoProveedor = em.merge(oldIdContratoOtrosiOfContratoProyectoProveedorListNewContratoProyectoProveedor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contratoProyectoProveedor.getId();
                if (findContratoProyectoProveedor(id) == null) {
                    throw new NonexistentEntityException("The contratoProyectoProveedor with id " + id + " no longer exists.");
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
            ContratoProyectoProveedor contratoProyectoProveedor;
            try {
                contratoProyectoProveedor = em.getReference(ContratoProyectoProveedor.class, id);
                contratoProyectoProveedor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contratoProyectoProveedor with id " + id + " no longer exists.", enfe);
            }
            TipoContratoProyecto idTipo = contratoProyectoProveedor.getIdTipo();
            if (idTipo != null) {
                idTipo.getContratoProyectoProveedorList().remove(contratoProyectoProveedor);
                idTipo = em.merge(idTipo);
            }
            TipoContratante idTipoContratante = contratoProyectoProveedor.getIdTipoContratante();
            if (idTipoContratante != null) {
                idTipoContratante.getContratoProyectoProveedorList().remove(contratoProyectoProveedor);
                idTipoContratante = em.merge(idTipoContratante);
            }
            Proyecto idProyecto = contratoProyectoProveedor.getIdProyecto();
            if (idProyecto != null) {
                idProyecto.getContratoProyectoProveedorList().remove(contratoProyectoProveedor);
                idProyecto = em.merge(idProyecto);
            }
            OrigenOtrosi idOrigenOtrosi = contratoProyectoProveedor.getIdOrigenOtrosi();
            if (idOrigenOtrosi != null) {
                idOrigenOtrosi.getContratoProyectoProveedorList().remove(contratoProyectoProveedor);
                idOrigenOtrosi = em.merge(idOrigenOtrosi);
            }
            ContratoProyectoProveedor idContratoOtrosi = contratoProyectoProveedor.getIdContratoOtrosi();
            if (idContratoOtrosi != null) {
                idContratoOtrosi.getContratoProyectoProveedorList().remove(contratoProyectoProveedor);
                idContratoOtrosi = em.merge(idContratoOtrosi);
            }
            AdjuntoTecnica idAdjuntoLegalizacion = contratoProyectoProveedor.getIdAdjuntoLegalizacion();
            if (idAdjuntoLegalizacion != null) {
                idAdjuntoLegalizacion.getContratoProyectoProveedorList().remove(contratoProyectoProveedor);
                idAdjuntoLegalizacion = em.merge(idAdjuntoLegalizacion);
            }
            AdjuntoTecnica idAdjunto = contratoProyectoProveedor.getIdAdjunto();
            if (idAdjunto != null) {
                idAdjunto.getContratoProyectoProveedorList().remove(contratoProyectoProveedor);
                idAdjunto = em.merge(idAdjunto);
            }
            List<EstadoContrato> estadoContratoList = contratoProyectoProveedor.getEstadoContratoList();
            for (EstadoContrato estadoContratoListEstadoContrato : estadoContratoList) {
                estadoContratoListEstadoContrato.setIdContrato(null);
                estadoContratoListEstadoContrato = em.merge(estadoContratoListEstadoContrato);
            }
            List<ContratistaContrato> contratistaContratoList = contratoProyectoProveedor.getContratistaContratoList();
            for (ContratistaContrato contratistaContratoListContratistaContrato : contratistaContratoList) {
                contratistaContratoListContratistaContrato.setIdContrato(null);
                contratistaContratoListContratistaContrato = em.merge(contratistaContratoListContratistaContrato);
            }
            List<ActaInicio> actaInicioList = contratoProyectoProveedor.getActaInicioList();
            for (ActaInicio actaInicioListActaInicio : actaInicioList) {
                actaInicioListActaInicio.setIdContrato(null);
                actaInicioListActaInicio = em.merge(actaInicioListActaInicio);
            }
            List<ContratoProyectoProveedor> contratoProyectoProveedorList = contratoProyectoProveedor.getContratoProyectoProveedorList();
            for (ContratoProyectoProveedor contratoProyectoProveedorListContratoProyectoProveedor : contratoProyectoProveedorList) {
                contratoProyectoProveedorListContratoProyectoProveedor.setIdContratoOtrosi(null);
                contratoProyectoProveedorListContratoProyectoProveedor = em.merge(contratoProyectoProveedorListContratoProyectoProveedor);
            }
            em.remove(contratoProyectoProveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContratoProyectoProveedor> findContratoProyectoProveedorEntities() {
        return findContratoProyectoProveedorEntities(true, -1, -1);
    }

    public List<ContratoProyectoProveedor> findContratoProyectoProveedorEntities(int maxResults, int firstResult) {
        return findContratoProyectoProveedorEntities(false, maxResults, firstResult);
    }

    private List<ContratoProyectoProveedor> findContratoProyectoProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContratoProyectoProveedor.class));
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

    public ContratoProyectoProveedor findContratoProyectoProveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContratoProyectoProveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getContratoProyectoProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContratoProyectoProveedor> rt = cq.from(ContratoProyectoProveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
