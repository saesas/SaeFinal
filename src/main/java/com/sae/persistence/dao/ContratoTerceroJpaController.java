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
import com.sae.persistence.domain.TipoContrato;
import com.sae.persistence.domain.NivelContrato;
import com.sae.persistence.domain.MotivoTerminacion;
import com.sae.persistence.domain.JornadaLaboral;
import com.sae.persistence.domain.GrupoNominal;
import com.sae.persistence.domain.ContratoTercero;
import com.sae.persistence.domain.Afiliacion;
import com.sae.persistence.domain.InasistenciaContrato;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.ValorPagarNomina;
import com.sae.persistence.domain.ClausulaContrato;
import com.sae.persistence.domain.ContratoFuncion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ContratoTerceroJpaController implements Serializable {

    public ContratoTerceroJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContratoTercero contratoTercero) throws PreexistingEntityException, Exception {
        if (contratoTercero.getInasistenciaContratoList() == null) {
            contratoTercero.setInasistenciaContratoList(new ArrayList<InasistenciaContrato>());
        }
        if (contratoTercero.getValorPagarNominaList() == null) {
            contratoTercero.setValorPagarNominaList(new ArrayList<ValorPagarNomina>());
        }
        if (contratoTercero.getContratoTerceroList() == null) {
            contratoTercero.setContratoTerceroList(new ArrayList<ContratoTercero>());
        }
        if (contratoTercero.getClausulaContratoList() == null) {
            contratoTercero.setClausulaContratoList(new ArrayList<ClausulaContrato>());
        }
        if (contratoTercero.getContratoFuncionList() == null) {
            contratoTercero.setContratoFuncionList(new ArrayList<ContratoFuncion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoContrato idTipoContrato = contratoTercero.getIdTipoContrato();
            if (idTipoContrato != null) {
                idTipoContrato = em.getReference(idTipoContrato.getClass(), idTipoContrato.getId());
                contratoTercero.setIdTipoContrato(idTipoContrato);
            }
            NivelContrato idNivelContrato = contratoTercero.getIdNivelContrato();
            if (idNivelContrato != null) {
                idNivelContrato = em.getReference(idNivelContrato.getClass(), idNivelContrato.getId());
                contratoTercero.setIdNivelContrato(idNivelContrato);
            }
            MotivoTerminacion idMotivoTerminacion = contratoTercero.getIdMotivoTerminacion();
            if (idMotivoTerminacion != null) {
                idMotivoTerminacion = em.getReference(idMotivoTerminacion.getClass(), idMotivoTerminacion.getId());
                contratoTercero.setIdMotivoTerminacion(idMotivoTerminacion);
            }
            JornadaLaboral idJornadaLaboral = contratoTercero.getIdJornadaLaboral();
            if (idJornadaLaboral != null) {
                idJornadaLaboral = em.getReference(idJornadaLaboral.getClass(), idJornadaLaboral.getId());
                contratoTercero.setIdJornadaLaboral(idJornadaLaboral);
            }
            GrupoNominal idGrupoNominal = contratoTercero.getIdGrupoNominal();
            if (idGrupoNominal != null) {
                idGrupoNominal = em.getReference(idGrupoNominal.getClass(), idGrupoNominal.getId());
                contratoTercero.setIdGrupoNominal(idGrupoNominal);
            }
            ContratoTercero idContratoOtrosi = contratoTercero.getIdContratoOtrosi();
            if (idContratoOtrosi != null) {
                idContratoOtrosi = em.getReference(idContratoOtrosi.getClass(), idContratoOtrosi.getId());
                contratoTercero.setIdContratoOtrosi(idContratoOtrosi);
            }
            Afiliacion idAfiliacion = contratoTercero.getIdAfiliacion();
            if (idAfiliacion != null) {
                idAfiliacion = em.getReference(idAfiliacion.getClass(), idAfiliacion.getId());
                contratoTercero.setIdAfiliacion(idAfiliacion);
            }
            List<InasistenciaContrato> attachedInasistenciaContratoList = new ArrayList<InasistenciaContrato>();
            for (InasistenciaContrato inasistenciaContratoListInasistenciaContratoToAttach : contratoTercero.getInasistenciaContratoList()) {
                inasistenciaContratoListInasistenciaContratoToAttach = em.getReference(inasistenciaContratoListInasistenciaContratoToAttach.getClass(), inasistenciaContratoListInasistenciaContratoToAttach.getId());
                attachedInasistenciaContratoList.add(inasistenciaContratoListInasistenciaContratoToAttach);
            }
            contratoTercero.setInasistenciaContratoList(attachedInasistenciaContratoList);
            List<ValorPagarNomina> attachedValorPagarNominaList = new ArrayList<ValorPagarNomina>();
            for (ValorPagarNomina valorPagarNominaListValorPagarNominaToAttach : contratoTercero.getValorPagarNominaList()) {
                valorPagarNominaListValorPagarNominaToAttach = em.getReference(valorPagarNominaListValorPagarNominaToAttach.getClass(), valorPagarNominaListValorPagarNominaToAttach.getId());
                attachedValorPagarNominaList.add(valorPagarNominaListValorPagarNominaToAttach);
            }
            contratoTercero.setValorPagarNominaList(attachedValorPagarNominaList);
            List<ContratoTercero> attachedContratoTerceroList = new ArrayList<ContratoTercero>();
            for (ContratoTercero contratoTerceroListContratoTerceroToAttach : contratoTercero.getContratoTerceroList()) {
                contratoTerceroListContratoTerceroToAttach = em.getReference(contratoTerceroListContratoTerceroToAttach.getClass(), contratoTerceroListContratoTerceroToAttach.getId());
                attachedContratoTerceroList.add(contratoTerceroListContratoTerceroToAttach);
            }
            contratoTercero.setContratoTerceroList(attachedContratoTerceroList);
            List<ClausulaContrato> attachedClausulaContratoList = new ArrayList<ClausulaContrato>();
            for (ClausulaContrato clausulaContratoListClausulaContratoToAttach : contratoTercero.getClausulaContratoList()) {
                clausulaContratoListClausulaContratoToAttach = em.getReference(clausulaContratoListClausulaContratoToAttach.getClass(), clausulaContratoListClausulaContratoToAttach.getId());
                attachedClausulaContratoList.add(clausulaContratoListClausulaContratoToAttach);
            }
            contratoTercero.setClausulaContratoList(attachedClausulaContratoList);
            List<ContratoFuncion> attachedContratoFuncionList = new ArrayList<ContratoFuncion>();
            for (ContratoFuncion contratoFuncionListContratoFuncionToAttach : contratoTercero.getContratoFuncionList()) {
                contratoFuncionListContratoFuncionToAttach = em.getReference(contratoFuncionListContratoFuncionToAttach.getClass(), contratoFuncionListContratoFuncionToAttach.getId());
                attachedContratoFuncionList.add(contratoFuncionListContratoFuncionToAttach);
            }
            contratoTercero.setContratoFuncionList(attachedContratoFuncionList);
            em.persist(contratoTercero);
            if (idTipoContrato != null) {
                idTipoContrato.getContratoTerceroList().add(contratoTercero);
                idTipoContrato = em.merge(idTipoContrato);
            }
            if (idNivelContrato != null) {
                idNivelContrato.getContratoTerceroList().add(contratoTercero);
                idNivelContrato = em.merge(idNivelContrato);
            }
            if (idMotivoTerminacion != null) {
                idMotivoTerminacion.getContratoTerceroList().add(contratoTercero);
                idMotivoTerminacion = em.merge(idMotivoTerminacion);
            }
            if (idJornadaLaboral != null) {
                idJornadaLaboral.getContratoTerceroList().add(contratoTercero);
                idJornadaLaboral = em.merge(idJornadaLaboral);
            }
            if (idGrupoNominal != null) {
                idGrupoNominal.getContratoTerceroList().add(contratoTercero);
                idGrupoNominal = em.merge(idGrupoNominal);
            }
            if (idContratoOtrosi != null) {
                idContratoOtrosi.getContratoTerceroList().add(contratoTercero);
                idContratoOtrosi = em.merge(idContratoOtrosi);
            }
            if (idAfiliacion != null) {
                idAfiliacion.getContratoTerceroList().add(contratoTercero);
                idAfiliacion = em.merge(idAfiliacion);
            }
            for (InasistenciaContrato inasistenciaContratoListInasistenciaContrato : contratoTercero.getInasistenciaContratoList()) {
                ContratoTercero oldIdContratoOfInasistenciaContratoListInasistenciaContrato = inasistenciaContratoListInasistenciaContrato.getIdContrato();
                inasistenciaContratoListInasistenciaContrato.setIdContrato(contratoTercero);
                inasistenciaContratoListInasistenciaContrato = em.merge(inasistenciaContratoListInasistenciaContrato);
                if (oldIdContratoOfInasistenciaContratoListInasistenciaContrato != null) {
                    oldIdContratoOfInasistenciaContratoListInasistenciaContrato.getInasistenciaContratoList().remove(inasistenciaContratoListInasistenciaContrato);
                    oldIdContratoOfInasistenciaContratoListInasistenciaContrato = em.merge(oldIdContratoOfInasistenciaContratoListInasistenciaContrato);
                }
            }
            for (ValorPagarNomina valorPagarNominaListValorPagarNomina : contratoTercero.getValorPagarNominaList()) {
                ContratoTercero oldIdContratoOfValorPagarNominaListValorPagarNomina = valorPagarNominaListValorPagarNomina.getIdContrato();
                valorPagarNominaListValorPagarNomina.setIdContrato(contratoTercero);
                valorPagarNominaListValorPagarNomina = em.merge(valorPagarNominaListValorPagarNomina);
                if (oldIdContratoOfValorPagarNominaListValorPagarNomina != null) {
                    oldIdContratoOfValorPagarNominaListValorPagarNomina.getValorPagarNominaList().remove(valorPagarNominaListValorPagarNomina);
                    oldIdContratoOfValorPagarNominaListValorPagarNomina = em.merge(oldIdContratoOfValorPagarNominaListValorPagarNomina);
                }
            }
            for (ContratoTercero contratoTerceroListContratoTercero : contratoTercero.getContratoTerceroList()) {
                ContratoTercero oldIdContratoOtrosiOfContratoTerceroListContratoTercero = contratoTerceroListContratoTercero.getIdContratoOtrosi();
                contratoTerceroListContratoTercero.setIdContratoOtrosi(contratoTercero);
                contratoTerceroListContratoTercero = em.merge(contratoTerceroListContratoTercero);
                if (oldIdContratoOtrosiOfContratoTerceroListContratoTercero != null) {
                    oldIdContratoOtrosiOfContratoTerceroListContratoTercero.getContratoTerceroList().remove(contratoTerceroListContratoTercero);
                    oldIdContratoOtrosiOfContratoTerceroListContratoTercero = em.merge(oldIdContratoOtrosiOfContratoTerceroListContratoTercero);
                }
            }
            for (ClausulaContrato clausulaContratoListClausulaContrato : contratoTercero.getClausulaContratoList()) {
                ContratoTercero oldIdContratoOfClausulaContratoListClausulaContrato = clausulaContratoListClausulaContrato.getIdContrato();
                clausulaContratoListClausulaContrato.setIdContrato(contratoTercero);
                clausulaContratoListClausulaContrato = em.merge(clausulaContratoListClausulaContrato);
                if (oldIdContratoOfClausulaContratoListClausulaContrato != null) {
                    oldIdContratoOfClausulaContratoListClausulaContrato.getClausulaContratoList().remove(clausulaContratoListClausulaContrato);
                    oldIdContratoOfClausulaContratoListClausulaContrato = em.merge(oldIdContratoOfClausulaContratoListClausulaContrato);
                }
            }
            for (ContratoFuncion contratoFuncionListContratoFuncion : contratoTercero.getContratoFuncionList()) {
                ContratoTercero oldIdContratoOfContratoFuncionListContratoFuncion = contratoFuncionListContratoFuncion.getIdContrato();
                contratoFuncionListContratoFuncion.setIdContrato(contratoTercero);
                contratoFuncionListContratoFuncion = em.merge(contratoFuncionListContratoFuncion);
                if (oldIdContratoOfContratoFuncionListContratoFuncion != null) {
                    oldIdContratoOfContratoFuncionListContratoFuncion.getContratoFuncionList().remove(contratoFuncionListContratoFuncion);
                    oldIdContratoOfContratoFuncionListContratoFuncion = em.merge(oldIdContratoOfContratoFuncionListContratoFuncion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findContratoTercero(contratoTercero.getId()) != null) {
                throw new PreexistingEntityException("ContratoTercero " + contratoTercero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContratoTercero contratoTercero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContratoTercero persistentContratoTercero = em.find(ContratoTercero.class, contratoTercero.getId());
            TipoContrato idTipoContratoOld = persistentContratoTercero.getIdTipoContrato();
            TipoContrato idTipoContratoNew = contratoTercero.getIdTipoContrato();
            NivelContrato idNivelContratoOld = persistentContratoTercero.getIdNivelContrato();
            NivelContrato idNivelContratoNew = contratoTercero.getIdNivelContrato();
            MotivoTerminacion idMotivoTerminacionOld = persistentContratoTercero.getIdMotivoTerminacion();
            MotivoTerminacion idMotivoTerminacionNew = contratoTercero.getIdMotivoTerminacion();
            JornadaLaboral idJornadaLaboralOld = persistentContratoTercero.getIdJornadaLaboral();
            JornadaLaboral idJornadaLaboralNew = contratoTercero.getIdJornadaLaboral();
            GrupoNominal idGrupoNominalOld = persistentContratoTercero.getIdGrupoNominal();
            GrupoNominal idGrupoNominalNew = contratoTercero.getIdGrupoNominal();
            ContratoTercero idContratoOtrosiOld = persistentContratoTercero.getIdContratoOtrosi();
            ContratoTercero idContratoOtrosiNew = contratoTercero.getIdContratoOtrosi();
            Afiliacion idAfiliacionOld = persistentContratoTercero.getIdAfiliacion();
            Afiliacion idAfiliacionNew = contratoTercero.getIdAfiliacion();
            List<InasistenciaContrato> inasistenciaContratoListOld = persistentContratoTercero.getInasistenciaContratoList();
            List<InasistenciaContrato> inasistenciaContratoListNew = contratoTercero.getInasistenciaContratoList();
            List<ValorPagarNomina> valorPagarNominaListOld = persistentContratoTercero.getValorPagarNominaList();
            List<ValorPagarNomina> valorPagarNominaListNew = contratoTercero.getValorPagarNominaList();
            List<ContratoTercero> contratoTerceroListOld = persistentContratoTercero.getContratoTerceroList();
            List<ContratoTercero> contratoTerceroListNew = contratoTercero.getContratoTerceroList();
            List<ClausulaContrato> clausulaContratoListOld = persistentContratoTercero.getClausulaContratoList();
            List<ClausulaContrato> clausulaContratoListNew = contratoTercero.getClausulaContratoList();
            List<ContratoFuncion> contratoFuncionListOld = persistentContratoTercero.getContratoFuncionList();
            List<ContratoFuncion> contratoFuncionListNew = contratoTercero.getContratoFuncionList();
            if (idTipoContratoNew != null) {
                idTipoContratoNew = em.getReference(idTipoContratoNew.getClass(), idTipoContratoNew.getId());
                contratoTercero.setIdTipoContrato(idTipoContratoNew);
            }
            if (idNivelContratoNew != null) {
                idNivelContratoNew = em.getReference(idNivelContratoNew.getClass(), idNivelContratoNew.getId());
                contratoTercero.setIdNivelContrato(idNivelContratoNew);
            }
            if (idMotivoTerminacionNew != null) {
                idMotivoTerminacionNew = em.getReference(idMotivoTerminacionNew.getClass(), idMotivoTerminacionNew.getId());
                contratoTercero.setIdMotivoTerminacion(idMotivoTerminacionNew);
            }
            if (idJornadaLaboralNew != null) {
                idJornadaLaboralNew = em.getReference(idJornadaLaboralNew.getClass(), idJornadaLaboralNew.getId());
                contratoTercero.setIdJornadaLaboral(idJornadaLaboralNew);
            }
            if (idGrupoNominalNew != null) {
                idGrupoNominalNew = em.getReference(idGrupoNominalNew.getClass(), idGrupoNominalNew.getId());
                contratoTercero.setIdGrupoNominal(idGrupoNominalNew);
            }
            if (idContratoOtrosiNew != null) {
                idContratoOtrosiNew = em.getReference(idContratoOtrosiNew.getClass(), idContratoOtrosiNew.getId());
                contratoTercero.setIdContratoOtrosi(idContratoOtrosiNew);
            }
            if (idAfiliacionNew != null) {
                idAfiliacionNew = em.getReference(idAfiliacionNew.getClass(), idAfiliacionNew.getId());
                contratoTercero.setIdAfiliacion(idAfiliacionNew);
            }
            List<InasistenciaContrato> attachedInasistenciaContratoListNew = new ArrayList<InasistenciaContrato>();
            for (InasistenciaContrato inasistenciaContratoListNewInasistenciaContratoToAttach : inasistenciaContratoListNew) {
                inasistenciaContratoListNewInasistenciaContratoToAttach = em.getReference(inasistenciaContratoListNewInasistenciaContratoToAttach.getClass(), inasistenciaContratoListNewInasistenciaContratoToAttach.getId());
                attachedInasistenciaContratoListNew.add(inasistenciaContratoListNewInasistenciaContratoToAttach);
            }
            inasistenciaContratoListNew = attachedInasistenciaContratoListNew;
            contratoTercero.setInasistenciaContratoList(inasistenciaContratoListNew);
            List<ValorPagarNomina> attachedValorPagarNominaListNew = new ArrayList<ValorPagarNomina>();
            for (ValorPagarNomina valorPagarNominaListNewValorPagarNominaToAttach : valorPagarNominaListNew) {
                valorPagarNominaListNewValorPagarNominaToAttach = em.getReference(valorPagarNominaListNewValorPagarNominaToAttach.getClass(), valorPagarNominaListNewValorPagarNominaToAttach.getId());
                attachedValorPagarNominaListNew.add(valorPagarNominaListNewValorPagarNominaToAttach);
            }
            valorPagarNominaListNew = attachedValorPagarNominaListNew;
            contratoTercero.setValorPagarNominaList(valorPagarNominaListNew);
            List<ContratoTercero> attachedContratoTerceroListNew = new ArrayList<ContratoTercero>();
            for (ContratoTercero contratoTerceroListNewContratoTerceroToAttach : contratoTerceroListNew) {
                contratoTerceroListNewContratoTerceroToAttach = em.getReference(contratoTerceroListNewContratoTerceroToAttach.getClass(), contratoTerceroListNewContratoTerceroToAttach.getId());
                attachedContratoTerceroListNew.add(contratoTerceroListNewContratoTerceroToAttach);
            }
            contratoTerceroListNew = attachedContratoTerceroListNew;
            contratoTercero.setContratoTerceroList(contratoTerceroListNew);
            List<ClausulaContrato> attachedClausulaContratoListNew = new ArrayList<ClausulaContrato>();
            for (ClausulaContrato clausulaContratoListNewClausulaContratoToAttach : clausulaContratoListNew) {
                clausulaContratoListNewClausulaContratoToAttach = em.getReference(clausulaContratoListNewClausulaContratoToAttach.getClass(), clausulaContratoListNewClausulaContratoToAttach.getId());
                attachedClausulaContratoListNew.add(clausulaContratoListNewClausulaContratoToAttach);
            }
            clausulaContratoListNew = attachedClausulaContratoListNew;
            contratoTercero.setClausulaContratoList(clausulaContratoListNew);
            List<ContratoFuncion> attachedContratoFuncionListNew = new ArrayList<ContratoFuncion>();
            for (ContratoFuncion contratoFuncionListNewContratoFuncionToAttach : contratoFuncionListNew) {
                contratoFuncionListNewContratoFuncionToAttach = em.getReference(contratoFuncionListNewContratoFuncionToAttach.getClass(), contratoFuncionListNewContratoFuncionToAttach.getId());
                attachedContratoFuncionListNew.add(contratoFuncionListNewContratoFuncionToAttach);
            }
            contratoFuncionListNew = attachedContratoFuncionListNew;
            contratoTercero.setContratoFuncionList(contratoFuncionListNew);
            contratoTercero = em.merge(contratoTercero);
            if (idTipoContratoOld != null && !idTipoContratoOld.equals(idTipoContratoNew)) {
                idTipoContratoOld.getContratoTerceroList().remove(contratoTercero);
                idTipoContratoOld = em.merge(idTipoContratoOld);
            }
            if (idTipoContratoNew != null && !idTipoContratoNew.equals(idTipoContratoOld)) {
                idTipoContratoNew.getContratoTerceroList().add(contratoTercero);
                idTipoContratoNew = em.merge(idTipoContratoNew);
            }
            if (idNivelContratoOld != null && !idNivelContratoOld.equals(idNivelContratoNew)) {
                idNivelContratoOld.getContratoTerceroList().remove(contratoTercero);
                idNivelContratoOld = em.merge(idNivelContratoOld);
            }
            if (idNivelContratoNew != null && !idNivelContratoNew.equals(idNivelContratoOld)) {
                idNivelContratoNew.getContratoTerceroList().add(contratoTercero);
                idNivelContratoNew = em.merge(idNivelContratoNew);
            }
            if (idMotivoTerminacionOld != null && !idMotivoTerminacionOld.equals(idMotivoTerminacionNew)) {
                idMotivoTerminacionOld.getContratoTerceroList().remove(contratoTercero);
                idMotivoTerminacionOld = em.merge(idMotivoTerminacionOld);
            }
            if (idMotivoTerminacionNew != null && !idMotivoTerminacionNew.equals(idMotivoTerminacionOld)) {
                idMotivoTerminacionNew.getContratoTerceroList().add(contratoTercero);
                idMotivoTerminacionNew = em.merge(idMotivoTerminacionNew);
            }
            if (idJornadaLaboralOld != null && !idJornadaLaboralOld.equals(idJornadaLaboralNew)) {
                idJornadaLaboralOld.getContratoTerceroList().remove(contratoTercero);
                idJornadaLaboralOld = em.merge(idJornadaLaboralOld);
            }
            if (idJornadaLaboralNew != null && !idJornadaLaboralNew.equals(idJornadaLaboralOld)) {
                idJornadaLaboralNew.getContratoTerceroList().add(contratoTercero);
                idJornadaLaboralNew = em.merge(idJornadaLaboralNew);
            }
            if (idGrupoNominalOld != null && !idGrupoNominalOld.equals(idGrupoNominalNew)) {
                idGrupoNominalOld.getContratoTerceroList().remove(contratoTercero);
                idGrupoNominalOld = em.merge(idGrupoNominalOld);
            }
            if (idGrupoNominalNew != null && !idGrupoNominalNew.equals(idGrupoNominalOld)) {
                idGrupoNominalNew.getContratoTerceroList().add(contratoTercero);
                idGrupoNominalNew = em.merge(idGrupoNominalNew);
            }
            if (idContratoOtrosiOld != null && !idContratoOtrosiOld.equals(idContratoOtrosiNew)) {
                idContratoOtrosiOld.getContratoTerceroList().remove(contratoTercero);
                idContratoOtrosiOld = em.merge(idContratoOtrosiOld);
            }
            if (idContratoOtrosiNew != null && !idContratoOtrosiNew.equals(idContratoOtrosiOld)) {
                idContratoOtrosiNew.getContratoTerceroList().add(contratoTercero);
                idContratoOtrosiNew = em.merge(idContratoOtrosiNew);
            }
            if (idAfiliacionOld != null && !idAfiliacionOld.equals(idAfiliacionNew)) {
                idAfiliacionOld.getContratoTerceroList().remove(contratoTercero);
                idAfiliacionOld = em.merge(idAfiliacionOld);
            }
            if (idAfiliacionNew != null && !idAfiliacionNew.equals(idAfiliacionOld)) {
                idAfiliacionNew.getContratoTerceroList().add(contratoTercero);
                idAfiliacionNew = em.merge(idAfiliacionNew);
            }
            for (InasistenciaContrato inasistenciaContratoListOldInasistenciaContrato : inasistenciaContratoListOld) {
                if (!inasistenciaContratoListNew.contains(inasistenciaContratoListOldInasistenciaContrato)) {
                    inasistenciaContratoListOldInasistenciaContrato.setIdContrato(null);
                    inasistenciaContratoListOldInasistenciaContrato = em.merge(inasistenciaContratoListOldInasistenciaContrato);
                }
            }
            for (InasistenciaContrato inasistenciaContratoListNewInasistenciaContrato : inasistenciaContratoListNew) {
                if (!inasistenciaContratoListOld.contains(inasistenciaContratoListNewInasistenciaContrato)) {
                    ContratoTercero oldIdContratoOfInasistenciaContratoListNewInasistenciaContrato = inasistenciaContratoListNewInasistenciaContrato.getIdContrato();
                    inasistenciaContratoListNewInasistenciaContrato.setIdContrato(contratoTercero);
                    inasistenciaContratoListNewInasistenciaContrato = em.merge(inasistenciaContratoListNewInasistenciaContrato);
                    if (oldIdContratoOfInasistenciaContratoListNewInasistenciaContrato != null && !oldIdContratoOfInasistenciaContratoListNewInasistenciaContrato.equals(contratoTercero)) {
                        oldIdContratoOfInasistenciaContratoListNewInasistenciaContrato.getInasistenciaContratoList().remove(inasistenciaContratoListNewInasistenciaContrato);
                        oldIdContratoOfInasistenciaContratoListNewInasistenciaContrato = em.merge(oldIdContratoOfInasistenciaContratoListNewInasistenciaContrato);
                    }
                }
            }
            for (ValorPagarNomina valorPagarNominaListOldValorPagarNomina : valorPagarNominaListOld) {
                if (!valorPagarNominaListNew.contains(valorPagarNominaListOldValorPagarNomina)) {
                    valorPagarNominaListOldValorPagarNomina.setIdContrato(null);
                    valorPagarNominaListOldValorPagarNomina = em.merge(valorPagarNominaListOldValorPagarNomina);
                }
            }
            for (ValorPagarNomina valorPagarNominaListNewValorPagarNomina : valorPagarNominaListNew) {
                if (!valorPagarNominaListOld.contains(valorPagarNominaListNewValorPagarNomina)) {
                    ContratoTercero oldIdContratoOfValorPagarNominaListNewValorPagarNomina = valorPagarNominaListNewValorPagarNomina.getIdContrato();
                    valorPagarNominaListNewValorPagarNomina.setIdContrato(contratoTercero);
                    valorPagarNominaListNewValorPagarNomina = em.merge(valorPagarNominaListNewValorPagarNomina);
                    if (oldIdContratoOfValorPagarNominaListNewValorPagarNomina != null && !oldIdContratoOfValorPagarNominaListNewValorPagarNomina.equals(contratoTercero)) {
                        oldIdContratoOfValorPagarNominaListNewValorPagarNomina.getValorPagarNominaList().remove(valorPagarNominaListNewValorPagarNomina);
                        oldIdContratoOfValorPagarNominaListNewValorPagarNomina = em.merge(oldIdContratoOfValorPagarNominaListNewValorPagarNomina);
                    }
                }
            }
            for (ContratoTercero contratoTerceroListOldContratoTercero : contratoTerceroListOld) {
                if (!contratoTerceroListNew.contains(contratoTerceroListOldContratoTercero)) {
                    contratoTerceroListOldContratoTercero.setIdContratoOtrosi(null);
                    contratoTerceroListOldContratoTercero = em.merge(contratoTerceroListOldContratoTercero);
                }
            }
            for (ContratoTercero contratoTerceroListNewContratoTercero : contratoTerceroListNew) {
                if (!contratoTerceroListOld.contains(contratoTerceroListNewContratoTercero)) {
                    ContratoTercero oldIdContratoOtrosiOfContratoTerceroListNewContratoTercero = contratoTerceroListNewContratoTercero.getIdContratoOtrosi();
                    contratoTerceroListNewContratoTercero.setIdContratoOtrosi(contratoTercero);
                    contratoTerceroListNewContratoTercero = em.merge(contratoTerceroListNewContratoTercero);
                    if (oldIdContratoOtrosiOfContratoTerceroListNewContratoTercero != null && !oldIdContratoOtrosiOfContratoTerceroListNewContratoTercero.equals(contratoTercero)) {
                        oldIdContratoOtrosiOfContratoTerceroListNewContratoTercero.getContratoTerceroList().remove(contratoTerceroListNewContratoTercero);
                        oldIdContratoOtrosiOfContratoTerceroListNewContratoTercero = em.merge(oldIdContratoOtrosiOfContratoTerceroListNewContratoTercero);
                    }
                }
            }
            for (ClausulaContrato clausulaContratoListOldClausulaContrato : clausulaContratoListOld) {
                if (!clausulaContratoListNew.contains(clausulaContratoListOldClausulaContrato)) {
                    clausulaContratoListOldClausulaContrato.setIdContrato(null);
                    clausulaContratoListOldClausulaContrato = em.merge(clausulaContratoListOldClausulaContrato);
                }
            }
            for (ClausulaContrato clausulaContratoListNewClausulaContrato : clausulaContratoListNew) {
                if (!clausulaContratoListOld.contains(clausulaContratoListNewClausulaContrato)) {
                    ContratoTercero oldIdContratoOfClausulaContratoListNewClausulaContrato = clausulaContratoListNewClausulaContrato.getIdContrato();
                    clausulaContratoListNewClausulaContrato.setIdContrato(contratoTercero);
                    clausulaContratoListNewClausulaContrato = em.merge(clausulaContratoListNewClausulaContrato);
                    if (oldIdContratoOfClausulaContratoListNewClausulaContrato != null && !oldIdContratoOfClausulaContratoListNewClausulaContrato.equals(contratoTercero)) {
                        oldIdContratoOfClausulaContratoListNewClausulaContrato.getClausulaContratoList().remove(clausulaContratoListNewClausulaContrato);
                        oldIdContratoOfClausulaContratoListNewClausulaContrato = em.merge(oldIdContratoOfClausulaContratoListNewClausulaContrato);
                    }
                }
            }
            for (ContratoFuncion contratoFuncionListOldContratoFuncion : contratoFuncionListOld) {
                if (!contratoFuncionListNew.contains(contratoFuncionListOldContratoFuncion)) {
                    contratoFuncionListOldContratoFuncion.setIdContrato(null);
                    contratoFuncionListOldContratoFuncion = em.merge(contratoFuncionListOldContratoFuncion);
                }
            }
            for (ContratoFuncion contratoFuncionListNewContratoFuncion : contratoFuncionListNew) {
                if (!contratoFuncionListOld.contains(contratoFuncionListNewContratoFuncion)) {
                    ContratoTercero oldIdContratoOfContratoFuncionListNewContratoFuncion = contratoFuncionListNewContratoFuncion.getIdContrato();
                    contratoFuncionListNewContratoFuncion.setIdContrato(contratoTercero);
                    contratoFuncionListNewContratoFuncion = em.merge(contratoFuncionListNewContratoFuncion);
                    if (oldIdContratoOfContratoFuncionListNewContratoFuncion != null && !oldIdContratoOfContratoFuncionListNewContratoFuncion.equals(contratoTercero)) {
                        oldIdContratoOfContratoFuncionListNewContratoFuncion.getContratoFuncionList().remove(contratoFuncionListNewContratoFuncion);
                        oldIdContratoOfContratoFuncionListNewContratoFuncion = em.merge(oldIdContratoOfContratoFuncionListNewContratoFuncion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contratoTercero.getId();
                if (findContratoTercero(id) == null) {
                    throw new NonexistentEntityException("The contratoTercero with id " + id + " no longer exists.");
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
            ContratoTercero contratoTercero;
            try {
                contratoTercero = em.getReference(ContratoTercero.class, id);
                contratoTercero.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contratoTercero with id " + id + " no longer exists.", enfe);
            }
            TipoContrato idTipoContrato = contratoTercero.getIdTipoContrato();
            if (idTipoContrato != null) {
                idTipoContrato.getContratoTerceroList().remove(contratoTercero);
                idTipoContrato = em.merge(idTipoContrato);
            }
            NivelContrato idNivelContrato = contratoTercero.getIdNivelContrato();
            if (idNivelContrato != null) {
                idNivelContrato.getContratoTerceroList().remove(contratoTercero);
                idNivelContrato = em.merge(idNivelContrato);
            }
            MotivoTerminacion idMotivoTerminacion = contratoTercero.getIdMotivoTerminacion();
            if (idMotivoTerminacion != null) {
                idMotivoTerminacion.getContratoTerceroList().remove(contratoTercero);
                idMotivoTerminacion = em.merge(idMotivoTerminacion);
            }
            JornadaLaboral idJornadaLaboral = contratoTercero.getIdJornadaLaboral();
            if (idJornadaLaboral != null) {
                idJornadaLaboral.getContratoTerceroList().remove(contratoTercero);
                idJornadaLaboral = em.merge(idJornadaLaboral);
            }
            GrupoNominal idGrupoNominal = contratoTercero.getIdGrupoNominal();
            if (idGrupoNominal != null) {
                idGrupoNominal.getContratoTerceroList().remove(contratoTercero);
                idGrupoNominal = em.merge(idGrupoNominal);
            }
            ContratoTercero idContratoOtrosi = contratoTercero.getIdContratoOtrosi();
            if (idContratoOtrosi != null) {
                idContratoOtrosi.getContratoTerceroList().remove(contratoTercero);
                idContratoOtrosi = em.merge(idContratoOtrosi);
            }
            Afiliacion idAfiliacion = contratoTercero.getIdAfiliacion();
            if (idAfiliacion != null) {
                idAfiliacion.getContratoTerceroList().remove(contratoTercero);
                idAfiliacion = em.merge(idAfiliacion);
            }
            List<InasistenciaContrato> inasistenciaContratoList = contratoTercero.getInasistenciaContratoList();
            for (InasistenciaContrato inasistenciaContratoListInasistenciaContrato : inasistenciaContratoList) {
                inasistenciaContratoListInasistenciaContrato.setIdContrato(null);
                inasistenciaContratoListInasistenciaContrato = em.merge(inasistenciaContratoListInasistenciaContrato);
            }
            List<ValorPagarNomina> valorPagarNominaList = contratoTercero.getValorPagarNominaList();
            for (ValorPagarNomina valorPagarNominaListValorPagarNomina : valorPagarNominaList) {
                valorPagarNominaListValorPagarNomina.setIdContrato(null);
                valorPagarNominaListValorPagarNomina = em.merge(valorPagarNominaListValorPagarNomina);
            }
            List<ContratoTercero> contratoTerceroList = contratoTercero.getContratoTerceroList();
            for (ContratoTercero contratoTerceroListContratoTercero : contratoTerceroList) {
                contratoTerceroListContratoTercero.setIdContratoOtrosi(null);
                contratoTerceroListContratoTercero = em.merge(contratoTerceroListContratoTercero);
            }
            List<ClausulaContrato> clausulaContratoList = contratoTercero.getClausulaContratoList();
            for (ClausulaContrato clausulaContratoListClausulaContrato : clausulaContratoList) {
                clausulaContratoListClausulaContrato.setIdContrato(null);
                clausulaContratoListClausulaContrato = em.merge(clausulaContratoListClausulaContrato);
            }
            List<ContratoFuncion> contratoFuncionList = contratoTercero.getContratoFuncionList();
            for (ContratoFuncion contratoFuncionListContratoFuncion : contratoFuncionList) {
                contratoFuncionListContratoFuncion.setIdContrato(null);
                contratoFuncionListContratoFuncion = em.merge(contratoFuncionListContratoFuncion);
            }
            em.remove(contratoTercero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContratoTercero> findContratoTerceroEntities() {
        return findContratoTerceroEntities(true, -1, -1);
    }

    public List<ContratoTercero> findContratoTerceroEntities(int maxResults, int firstResult) {
        return findContratoTerceroEntities(false, maxResults, firstResult);
    }

    private List<ContratoTercero> findContratoTerceroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContratoTercero.class));
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

    public ContratoTercero findContratoTercero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContratoTercero.class, id);
        } finally {
            em.close();
        }
    }

    public int getContratoTerceroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContratoTercero> rt = cq.from(ContratoTercero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
