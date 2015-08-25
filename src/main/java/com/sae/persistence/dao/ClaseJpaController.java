/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.Clase;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.TipoClase;
import com.sae.persistence.domain.Recurso;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.Tema;
import com.sae.persistence.domain.EtapaProceso;
import com.sae.persistence.domain.ClaseIncidente;
import com.sae.persistence.domain.SeguimientoProceso;
import com.sae.persistence.domain.Normatividad;
import com.sae.persistence.domain.TipoCuantia;
import com.sae.persistence.domain.Proceso;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ClaseJpaController implements Serializable {

    public ClaseJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clase clase) throws PreexistingEntityException, Exception {
        if (clase.getRecursoList() == null) {
            clase.setRecursoList(new ArrayList<Recurso>());
        }
        if (clase.getTemaList() == null) {
            clase.setTemaList(new ArrayList<Tema>());
        }
        if (clase.getEtapaProcesoList() == null) {
            clase.setEtapaProcesoList(new ArrayList<EtapaProceso>());
        }
        if (clase.getClaseIncidenteList() == null) {
            clase.setClaseIncidenteList(new ArrayList<ClaseIncidente>());
        }
        if (clase.getSeguimientoProcesoList() == null) {
            clase.setSeguimientoProcesoList(new ArrayList<SeguimientoProceso>());
        }
        if (clase.getNormatividadList() == null) {
            clase.setNormatividadList(new ArrayList<Normatividad>());
        }
        if (clase.getNormatividadList1() == null) {
            clase.setNormatividadList1(new ArrayList<Normatividad>());
        }
        if (clase.getTipoCuantiaList() == null) {
            clase.setTipoCuantiaList(new ArrayList<TipoCuantia>());
        }
        if (clase.getProcesoList() == null) {
            clase.setProcesoList(new ArrayList<Proceso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoClase idTipo = clase.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getId());
                clase.setIdTipo(idTipo);
            }
            List<Recurso> attachedRecursoList = new ArrayList<Recurso>();
            for (Recurso recursoListRecursoToAttach : clase.getRecursoList()) {
                recursoListRecursoToAttach = em.getReference(recursoListRecursoToAttach.getClass(), recursoListRecursoToAttach.getId());
                attachedRecursoList.add(recursoListRecursoToAttach);
            }
            clase.setRecursoList(attachedRecursoList);
            List<Tema> attachedTemaList = new ArrayList<Tema>();
            for (Tema temaListTemaToAttach : clase.getTemaList()) {
                temaListTemaToAttach = em.getReference(temaListTemaToAttach.getClass(), temaListTemaToAttach.getId());
                attachedTemaList.add(temaListTemaToAttach);
            }
            clase.setTemaList(attachedTemaList);
            List<EtapaProceso> attachedEtapaProcesoList = new ArrayList<EtapaProceso>();
            for (EtapaProceso etapaProcesoListEtapaProcesoToAttach : clase.getEtapaProcesoList()) {
                etapaProcesoListEtapaProcesoToAttach = em.getReference(etapaProcesoListEtapaProcesoToAttach.getClass(), etapaProcesoListEtapaProcesoToAttach.getId());
                attachedEtapaProcesoList.add(etapaProcesoListEtapaProcesoToAttach);
            }
            clase.setEtapaProcesoList(attachedEtapaProcesoList);
            List<ClaseIncidente> attachedClaseIncidenteList = new ArrayList<ClaseIncidente>();
            for (ClaseIncidente claseIncidenteListClaseIncidenteToAttach : clase.getClaseIncidenteList()) {
                claseIncidenteListClaseIncidenteToAttach = em.getReference(claseIncidenteListClaseIncidenteToAttach.getClass(), claseIncidenteListClaseIncidenteToAttach.getId());
                attachedClaseIncidenteList.add(claseIncidenteListClaseIncidenteToAttach);
            }
            clase.setClaseIncidenteList(attachedClaseIncidenteList);
            List<SeguimientoProceso> attachedSeguimientoProcesoList = new ArrayList<SeguimientoProceso>();
            for (SeguimientoProceso seguimientoProcesoListSeguimientoProcesoToAttach : clase.getSeguimientoProcesoList()) {
                seguimientoProcesoListSeguimientoProcesoToAttach = em.getReference(seguimientoProcesoListSeguimientoProcesoToAttach.getClass(), seguimientoProcesoListSeguimientoProcesoToAttach.getId());
                attachedSeguimientoProcesoList.add(seguimientoProcesoListSeguimientoProcesoToAttach);
            }
            clase.setSeguimientoProcesoList(attachedSeguimientoProcesoList);
            List<Normatividad> attachedNormatividadList = new ArrayList<Normatividad>();
            for (Normatividad normatividadListNormatividadToAttach : clase.getNormatividadList()) {
                normatividadListNormatividadToAttach = em.getReference(normatividadListNormatividadToAttach.getClass(), normatividadListNormatividadToAttach.getId());
                attachedNormatividadList.add(normatividadListNormatividadToAttach);
            }
            clase.setNormatividadList(attachedNormatividadList);
            List<Normatividad> attachedNormatividadList1 = new ArrayList<Normatividad>();
            for (Normatividad normatividadList1NormatividadToAttach : clase.getNormatividadList1()) {
                normatividadList1NormatividadToAttach = em.getReference(normatividadList1NormatividadToAttach.getClass(), normatividadList1NormatividadToAttach.getId());
                attachedNormatividadList1.add(normatividadList1NormatividadToAttach);
            }
            clase.setNormatividadList1(attachedNormatividadList1);
            List<TipoCuantia> attachedTipoCuantiaList = new ArrayList<TipoCuantia>();
            for (TipoCuantia tipoCuantiaListTipoCuantiaToAttach : clase.getTipoCuantiaList()) {
                tipoCuantiaListTipoCuantiaToAttach = em.getReference(tipoCuantiaListTipoCuantiaToAttach.getClass(), tipoCuantiaListTipoCuantiaToAttach.getId());
                attachedTipoCuantiaList.add(tipoCuantiaListTipoCuantiaToAttach);
            }
            clase.setTipoCuantiaList(attachedTipoCuantiaList);
            List<Proceso> attachedProcesoList = new ArrayList<Proceso>();
            for (Proceso procesoListProcesoToAttach : clase.getProcesoList()) {
                procesoListProcesoToAttach = em.getReference(procesoListProcesoToAttach.getClass(), procesoListProcesoToAttach.getId());
                attachedProcesoList.add(procesoListProcesoToAttach);
            }
            clase.setProcesoList(attachedProcesoList);
            em.persist(clase);
            if (idTipo != null) {
                idTipo.getClaseList().add(clase);
                idTipo = em.merge(idTipo);
            }
            for (Recurso recursoListRecurso : clase.getRecursoList()) {
                Clase oldIdInstanciaOfRecursoListRecurso = recursoListRecurso.getIdInstancia();
                recursoListRecurso.setIdInstancia(clase);
                recursoListRecurso = em.merge(recursoListRecurso);
                if (oldIdInstanciaOfRecursoListRecurso != null) {
                    oldIdInstanciaOfRecursoListRecurso.getRecursoList().remove(recursoListRecurso);
                    oldIdInstanciaOfRecursoListRecurso = em.merge(oldIdInstanciaOfRecursoListRecurso);
                }
            }
            for (Tema temaListTema : clase.getTemaList()) {
                Clase oldIdAreaDerechoOfTemaListTema = temaListTema.getIdAreaDerecho();
                temaListTema.setIdAreaDerecho(clase);
                temaListTema = em.merge(temaListTema);
                if (oldIdAreaDerechoOfTemaListTema != null) {
                    oldIdAreaDerechoOfTemaListTema.getTemaList().remove(temaListTema);
                    oldIdAreaDerechoOfTemaListTema = em.merge(oldIdAreaDerechoOfTemaListTema);
                }
            }
            for (EtapaProceso etapaProcesoListEtapaProceso : clase.getEtapaProcesoList()) {
                Clase oldIdInstanciaOfEtapaProcesoListEtapaProceso = etapaProcesoListEtapaProceso.getIdInstancia();
                etapaProcesoListEtapaProceso.setIdInstancia(clase);
                etapaProcesoListEtapaProceso = em.merge(etapaProcesoListEtapaProceso);
                if (oldIdInstanciaOfEtapaProcesoListEtapaProceso != null) {
                    oldIdInstanciaOfEtapaProcesoListEtapaProceso.getEtapaProcesoList().remove(etapaProcesoListEtapaProceso);
                    oldIdInstanciaOfEtapaProcesoListEtapaProceso = em.merge(oldIdInstanciaOfEtapaProcesoListEtapaProceso);
                }
            }
            for (ClaseIncidente claseIncidenteListClaseIncidente : clase.getClaseIncidenteList()) {
                Clase oldIdProcedimientoJuridicoOfClaseIncidenteListClaseIncidente = claseIncidenteListClaseIncidente.getIdProcedimientoJuridico();
                claseIncidenteListClaseIncidente.setIdProcedimientoJuridico(clase);
                claseIncidenteListClaseIncidente = em.merge(claseIncidenteListClaseIncidente);
                if (oldIdProcedimientoJuridicoOfClaseIncidenteListClaseIncidente != null) {
                    oldIdProcedimientoJuridicoOfClaseIncidenteListClaseIncidente.getClaseIncidenteList().remove(claseIncidenteListClaseIncidente);
                    oldIdProcedimientoJuridicoOfClaseIncidenteListClaseIncidente = em.merge(oldIdProcedimientoJuridicoOfClaseIncidenteListClaseIncidente);
                }
            }
            for (SeguimientoProceso seguimientoProcesoListSeguimientoProceso : clase.getSeguimientoProcesoList()) {
                Clase oldIdInstanciaOfSeguimientoProcesoListSeguimientoProceso = seguimientoProcesoListSeguimientoProceso.getIdInstancia();
                seguimientoProcesoListSeguimientoProceso.setIdInstancia(clase);
                seguimientoProcesoListSeguimientoProceso = em.merge(seguimientoProcesoListSeguimientoProceso);
                if (oldIdInstanciaOfSeguimientoProcesoListSeguimientoProceso != null) {
                    oldIdInstanciaOfSeguimientoProcesoListSeguimientoProceso.getSeguimientoProcesoList().remove(seguimientoProcesoListSeguimientoProceso);
                    oldIdInstanciaOfSeguimientoProcesoListSeguimientoProceso = em.merge(oldIdInstanciaOfSeguimientoProcesoListSeguimientoProceso);
                }
            }
            for (Normatividad normatividadListNormatividad : clase.getNormatividadList()) {
                Clase oldIdClaseNormatividadOfNormatividadListNormatividad = normatividadListNormatividad.getIdClaseNormatividad();
                normatividadListNormatividad.setIdClaseNormatividad(clase);
                normatividadListNormatividad = em.merge(normatividadListNormatividad);
                if (oldIdClaseNormatividadOfNormatividadListNormatividad != null) {
                    oldIdClaseNormatividadOfNormatividadListNormatividad.getNormatividadList().remove(normatividadListNormatividad);
                    oldIdClaseNormatividadOfNormatividadListNormatividad = em.merge(oldIdClaseNormatividadOfNormatividadListNormatividad);
                }
            }
            for (Normatividad normatividadList1Normatividad : clase.getNormatividadList1()) {
                Clase oldIdAreaDerechoOfNormatividadList1Normatividad = normatividadList1Normatividad.getIdAreaDerecho();
                normatividadList1Normatividad.setIdAreaDerecho(clase);
                normatividadList1Normatividad = em.merge(normatividadList1Normatividad);
                if (oldIdAreaDerechoOfNormatividadList1Normatividad != null) {
                    oldIdAreaDerechoOfNormatividadList1Normatividad.getNormatividadList1().remove(normatividadList1Normatividad);
                    oldIdAreaDerechoOfNormatividadList1Normatividad = em.merge(oldIdAreaDerechoOfNormatividadList1Normatividad);
                }
            }
            for (TipoCuantia tipoCuantiaListTipoCuantia : clase.getTipoCuantiaList()) {
                Clase oldIdProcedimientoJuridicoOfTipoCuantiaListTipoCuantia = tipoCuantiaListTipoCuantia.getIdProcedimientoJuridico();
                tipoCuantiaListTipoCuantia.setIdProcedimientoJuridico(clase);
                tipoCuantiaListTipoCuantia = em.merge(tipoCuantiaListTipoCuantia);
                if (oldIdProcedimientoJuridicoOfTipoCuantiaListTipoCuantia != null) {
                    oldIdProcedimientoJuridicoOfTipoCuantiaListTipoCuantia.getTipoCuantiaList().remove(tipoCuantiaListTipoCuantia);
                    oldIdProcedimientoJuridicoOfTipoCuantiaListTipoCuantia = em.merge(oldIdProcedimientoJuridicoOfTipoCuantiaListTipoCuantia);
                }
            }
            for (Proceso procesoListProceso : clase.getProcesoList()) {
                Clase oldIdClaseOfProcesoListProceso = procesoListProceso.getIdClase();
                procesoListProceso.setIdClase(clase);
                procesoListProceso = em.merge(procesoListProceso);
                if (oldIdClaseOfProcesoListProceso != null) {
                    oldIdClaseOfProcesoListProceso.getProcesoList().remove(procesoListProceso);
                    oldIdClaseOfProcesoListProceso = em.merge(oldIdClaseOfProcesoListProceso);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClase(clase.getId()) != null) {
                throw new PreexistingEntityException("Clase " + clase + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clase clase) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clase persistentClase = em.find(Clase.class, clase.getId());
            TipoClase idTipoOld = persistentClase.getIdTipo();
            TipoClase idTipoNew = clase.getIdTipo();
            List<Recurso> recursoListOld = persistentClase.getRecursoList();
            List<Recurso> recursoListNew = clase.getRecursoList();
            List<Tema> temaListOld = persistentClase.getTemaList();
            List<Tema> temaListNew = clase.getTemaList();
            List<EtapaProceso> etapaProcesoListOld = persistentClase.getEtapaProcesoList();
            List<EtapaProceso> etapaProcesoListNew = clase.getEtapaProcesoList();
            List<ClaseIncidente> claseIncidenteListOld = persistentClase.getClaseIncidenteList();
            List<ClaseIncidente> claseIncidenteListNew = clase.getClaseIncidenteList();
            List<SeguimientoProceso> seguimientoProcesoListOld = persistentClase.getSeguimientoProcesoList();
            List<SeguimientoProceso> seguimientoProcesoListNew = clase.getSeguimientoProcesoList();
            List<Normatividad> normatividadListOld = persistentClase.getNormatividadList();
            List<Normatividad> normatividadListNew = clase.getNormatividadList();
            List<Normatividad> normatividadList1Old = persistentClase.getNormatividadList1();
            List<Normatividad> normatividadList1New = clase.getNormatividadList1();
            List<TipoCuantia> tipoCuantiaListOld = persistentClase.getTipoCuantiaList();
            List<TipoCuantia> tipoCuantiaListNew = clase.getTipoCuantiaList();
            List<Proceso> procesoListOld = persistentClase.getProcesoList();
            List<Proceso> procesoListNew = clase.getProcesoList();
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getId());
                clase.setIdTipo(idTipoNew);
            }
            List<Recurso> attachedRecursoListNew = new ArrayList<Recurso>();
            for (Recurso recursoListNewRecursoToAttach : recursoListNew) {
                recursoListNewRecursoToAttach = em.getReference(recursoListNewRecursoToAttach.getClass(), recursoListNewRecursoToAttach.getId());
                attachedRecursoListNew.add(recursoListNewRecursoToAttach);
            }
            recursoListNew = attachedRecursoListNew;
            clase.setRecursoList(recursoListNew);
            List<Tema> attachedTemaListNew = new ArrayList<Tema>();
            for (Tema temaListNewTemaToAttach : temaListNew) {
                temaListNewTemaToAttach = em.getReference(temaListNewTemaToAttach.getClass(), temaListNewTemaToAttach.getId());
                attachedTemaListNew.add(temaListNewTemaToAttach);
            }
            temaListNew = attachedTemaListNew;
            clase.setTemaList(temaListNew);
            List<EtapaProceso> attachedEtapaProcesoListNew = new ArrayList<EtapaProceso>();
            for (EtapaProceso etapaProcesoListNewEtapaProcesoToAttach : etapaProcesoListNew) {
                etapaProcesoListNewEtapaProcesoToAttach = em.getReference(etapaProcesoListNewEtapaProcesoToAttach.getClass(), etapaProcesoListNewEtapaProcesoToAttach.getId());
                attachedEtapaProcesoListNew.add(etapaProcesoListNewEtapaProcesoToAttach);
            }
            etapaProcesoListNew = attachedEtapaProcesoListNew;
            clase.setEtapaProcesoList(etapaProcesoListNew);
            List<ClaseIncidente> attachedClaseIncidenteListNew = new ArrayList<ClaseIncidente>();
            for (ClaseIncidente claseIncidenteListNewClaseIncidenteToAttach : claseIncidenteListNew) {
                claseIncidenteListNewClaseIncidenteToAttach = em.getReference(claseIncidenteListNewClaseIncidenteToAttach.getClass(), claseIncidenteListNewClaseIncidenteToAttach.getId());
                attachedClaseIncidenteListNew.add(claseIncidenteListNewClaseIncidenteToAttach);
            }
            claseIncidenteListNew = attachedClaseIncidenteListNew;
            clase.setClaseIncidenteList(claseIncidenteListNew);
            List<SeguimientoProceso> attachedSeguimientoProcesoListNew = new ArrayList<SeguimientoProceso>();
            for (SeguimientoProceso seguimientoProcesoListNewSeguimientoProcesoToAttach : seguimientoProcesoListNew) {
                seguimientoProcesoListNewSeguimientoProcesoToAttach = em.getReference(seguimientoProcesoListNewSeguimientoProcesoToAttach.getClass(), seguimientoProcesoListNewSeguimientoProcesoToAttach.getId());
                attachedSeguimientoProcesoListNew.add(seguimientoProcesoListNewSeguimientoProcesoToAttach);
            }
            seguimientoProcesoListNew = attachedSeguimientoProcesoListNew;
            clase.setSeguimientoProcesoList(seguimientoProcesoListNew);
            List<Normatividad> attachedNormatividadListNew = new ArrayList<Normatividad>();
            for (Normatividad normatividadListNewNormatividadToAttach : normatividadListNew) {
                normatividadListNewNormatividadToAttach = em.getReference(normatividadListNewNormatividadToAttach.getClass(), normatividadListNewNormatividadToAttach.getId());
                attachedNormatividadListNew.add(normatividadListNewNormatividadToAttach);
            }
            normatividadListNew = attachedNormatividadListNew;
            clase.setNormatividadList(normatividadListNew);
            List<Normatividad> attachedNormatividadList1New = new ArrayList<Normatividad>();
            for (Normatividad normatividadList1NewNormatividadToAttach : normatividadList1New) {
                normatividadList1NewNormatividadToAttach = em.getReference(normatividadList1NewNormatividadToAttach.getClass(), normatividadList1NewNormatividadToAttach.getId());
                attachedNormatividadList1New.add(normatividadList1NewNormatividadToAttach);
            }
            normatividadList1New = attachedNormatividadList1New;
            clase.setNormatividadList1(normatividadList1New);
            List<TipoCuantia> attachedTipoCuantiaListNew = new ArrayList<TipoCuantia>();
            for (TipoCuantia tipoCuantiaListNewTipoCuantiaToAttach : tipoCuantiaListNew) {
                tipoCuantiaListNewTipoCuantiaToAttach = em.getReference(tipoCuantiaListNewTipoCuantiaToAttach.getClass(), tipoCuantiaListNewTipoCuantiaToAttach.getId());
                attachedTipoCuantiaListNew.add(tipoCuantiaListNewTipoCuantiaToAttach);
            }
            tipoCuantiaListNew = attachedTipoCuantiaListNew;
            clase.setTipoCuantiaList(tipoCuantiaListNew);
            List<Proceso> attachedProcesoListNew = new ArrayList<Proceso>();
            for (Proceso procesoListNewProcesoToAttach : procesoListNew) {
                procesoListNewProcesoToAttach = em.getReference(procesoListNewProcesoToAttach.getClass(), procesoListNewProcesoToAttach.getId());
                attachedProcesoListNew.add(procesoListNewProcesoToAttach);
            }
            procesoListNew = attachedProcesoListNew;
            clase.setProcesoList(procesoListNew);
            clase = em.merge(clase);
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getClaseList().remove(clase);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getClaseList().add(clase);
                idTipoNew = em.merge(idTipoNew);
            }
            for (Recurso recursoListOldRecurso : recursoListOld) {
                if (!recursoListNew.contains(recursoListOldRecurso)) {
                    recursoListOldRecurso.setIdInstancia(null);
                    recursoListOldRecurso = em.merge(recursoListOldRecurso);
                }
            }
            for (Recurso recursoListNewRecurso : recursoListNew) {
                if (!recursoListOld.contains(recursoListNewRecurso)) {
                    Clase oldIdInstanciaOfRecursoListNewRecurso = recursoListNewRecurso.getIdInstancia();
                    recursoListNewRecurso.setIdInstancia(clase);
                    recursoListNewRecurso = em.merge(recursoListNewRecurso);
                    if (oldIdInstanciaOfRecursoListNewRecurso != null && !oldIdInstanciaOfRecursoListNewRecurso.equals(clase)) {
                        oldIdInstanciaOfRecursoListNewRecurso.getRecursoList().remove(recursoListNewRecurso);
                        oldIdInstanciaOfRecursoListNewRecurso = em.merge(oldIdInstanciaOfRecursoListNewRecurso);
                    }
                }
            }
            for (Tema temaListOldTema : temaListOld) {
                if (!temaListNew.contains(temaListOldTema)) {
                    temaListOldTema.setIdAreaDerecho(null);
                    temaListOldTema = em.merge(temaListOldTema);
                }
            }
            for (Tema temaListNewTema : temaListNew) {
                if (!temaListOld.contains(temaListNewTema)) {
                    Clase oldIdAreaDerechoOfTemaListNewTema = temaListNewTema.getIdAreaDerecho();
                    temaListNewTema.setIdAreaDerecho(clase);
                    temaListNewTema = em.merge(temaListNewTema);
                    if (oldIdAreaDerechoOfTemaListNewTema != null && !oldIdAreaDerechoOfTemaListNewTema.equals(clase)) {
                        oldIdAreaDerechoOfTemaListNewTema.getTemaList().remove(temaListNewTema);
                        oldIdAreaDerechoOfTemaListNewTema = em.merge(oldIdAreaDerechoOfTemaListNewTema);
                    }
                }
            }
            for (EtapaProceso etapaProcesoListOldEtapaProceso : etapaProcesoListOld) {
                if (!etapaProcesoListNew.contains(etapaProcesoListOldEtapaProceso)) {
                    etapaProcesoListOldEtapaProceso.setIdInstancia(null);
                    etapaProcesoListOldEtapaProceso = em.merge(etapaProcesoListOldEtapaProceso);
                }
            }
            for (EtapaProceso etapaProcesoListNewEtapaProceso : etapaProcesoListNew) {
                if (!etapaProcesoListOld.contains(etapaProcesoListNewEtapaProceso)) {
                    Clase oldIdInstanciaOfEtapaProcesoListNewEtapaProceso = etapaProcesoListNewEtapaProceso.getIdInstancia();
                    etapaProcesoListNewEtapaProceso.setIdInstancia(clase);
                    etapaProcesoListNewEtapaProceso = em.merge(etapaProcesoListNewEtapaProceso);
                    if (oldIdInstanciaOfEtapaProcesoListNewEtapaProceso != null && !oldIdInstanciaOfEtapaProcesoListNewEtapaProceso.equals(clase)) {
                        oldIdInstanciaOfEtapaProcesoListNewEtapaProceso.getEtapaProcesoList().remove(etapaProcesoListNewEtapaProceso);
                        oldIdInstanciaOfEtapaProcesoListNewEtapaProceso = em.merge(oldIdInstanciaOfEtapaProcesoListNewEtapaProceso);
                    }
                }
            }
            for (ClaseIncidente claseIncidenteListOldClaseIncidente : claseIncidenteListOld) {
                if (!claseIncidenteListNew.contains(claseIncidenteListOldClaseIncidente)) {
                    claseIncidenteListOldClaseIncidente.setIdProcedimientoJuridico(null);
                    claseIncidenteListOldClaseIncidente = em.merge(claseIncidenteListOldClaseIncidente);
                }
            }
            for (ClaseIncidente claseIncidenteListNewClaseIncidente : claseIncidenteListNew) {
                if (!claseIncidenteListOld.contains(claseIncidenteListNewClaseIncidente)) {
                    Clase oldIdProcedimientoJuridicoOfClaseIncidenteListNewClaseIncidente = claseIncidenteListNewClaseIncidente.getIdProcedimientoJuridico();
                    claseIncidenteListNewClaseIncidente.setIdProcedimientoJuridico(clase);
                    claseIncidenteListNewClaseIncidente = em.merge(claseIncidenteListNewClaseIncidente);
                    if (oldIdProcedimientoJuridicoOfClaseIncidenteListNewClaseIncidente != null && !oldIdProcedimientoJuridicoOfClaseIncidenteListNewClaseIncidente.equals(clase)) {
                        oldIdProcedimientoJuridicoOfClaseIncidenteListNewClaseIncidente.getClaseIncidenteList().remove(claseIncidenteListNewClaseIncidente);
                        oldIdProcedimientoJuridicoOfClaseIncidenteListNewClaseIncidente = em.merge(oldIdProcedimientoJuridicoOfClaseIncidenteListNewClaseIncidente);
                    }
                }
            }
            for (SeguimientoProceso seguimientoProcesoListOldSeguimientoProceso : seguimientoProcesoListOld) {
                if (!seguimientoProcesoListNew.contains(seguimientoProcesoListOldSeguimientoProceso)) {
                    seguimientoProcesoListOldSeguimientoProceso.setIdInstancia(null);
                    seguimientoProcesoListOldSeguimientoProceso = em.merge(seguimientoProcesoListOldSeguimientoProceso);
                }
            }
            for (SeguimientoProceso seguimientoProcesoListNewSeguimientoProceso : seguimientoProcesoListNew) {
                if (!seguimientoProcesoListOld.contains(seguimientoProcesoListNewSeguimientoProceso)) {
                    Clase oldIdInstanciaOfSeguimientoProcesoListNewSeguimientoProceso = seguimientoProcesoListNewSeguimientoProceso.getIdInstancia();
                    seguimientoProcesoListNewSeguimientoProceso.setIdInstancia(clase);
                    seguimientoProcesoListNewSeguimientoProceso = em.merge(seguimientoProcesoListNewSeguimientoProceso);
                    if (oldIdInstanciaOfSeguimientoProcesoListNewSeguimientoProceso != null && !oldIdInstanciaOfSeguimientoProcesoListNewSeguimientoProceso.equals(clase)) {
                        oldIdInstanciaOfSeguimientoProcesoListNewSeguimientoProceso.getSeguimientoProcesoList().remove(seguimientoProcesoListNewSeguimientoProceso);
                        oldIdInstanciaOfSeguimientoProcesoListNewSeguimientoProceso = em.merge(oldIdInstanciaOfSeguimientoProcesoListNewSeguimientoProceso);
                    }
                }
            }
            for (Normatividad normatividadListOldNormatividad : normatividadListOld) {
                if (!normatividadListNew.contains(normatividadListOldNormatividad)) {
                    normatividadListOldNormatividad.setIdClaseNormatividad(null);
                    normatividadListOldNormatividad = em.merge(normatividadListOldNormatividad);
                }
            }
            for (Normatividad normatividadListNewNormatividad : normatividadListNew) {
                if (!normatividadListOld.contains(normatividadListNewNormatividad)) {
                    Clase oldIdClaseNormatividadOfNormatividadListNewNormatividad = normatividadListNewNormatividad.getIdClaseNormatividad();
                    normatividadListNewNormatividad.setIdClaseNormatividad(clase);
                    normatividadListNewNormatividad = em.merge(normatividadListNewNormatividad);
                    if (oldIdClaseNormatividadOfNormatividadListNewNormatividad != null && !oldIdClaseNormatividadOfNormatividadListNewNormatividad.equals(clase)) {
                        oldIdClaseNormatividadOfNormatividadListNewNormatividad.getNormatividadList().remove(normatividadListNewNormatividad);
                        oldIdClaseNormatividadOfNormatividadListNewNormatividad = em.merge(oldIdClaseNormatividadOfNormatividadListNewNormatividad);
                    }
                }
            }
            for (Normatividad normatividadList1OldNormatividad : normatividadList1Old) {
                if (!normatividadList1New.contains(normatividadList1OldNormatividad)) {
                    normatividadList1OldNormatividad.setIdAreaDerecho(null);
                    normatividadList1OldNormatividad = em.merge(normatividadList1OldNormatividad);
                }
            }
            for (Normatividad normatividadList1NewNormatividad : normatividadList1New) {
                if (!normatividadList1Old.contains(normatividadList1NewNormatividad)) {
                    Clase oldIdAreaDerechoOfNormatividadList1NewNormatividad = normatividadList1NewNormatividad.getIdAreaDerecho();
                    normatividadList1NewNormatividad.setIdAreaDerecho(clase);
                    normatividadList1NewNormatividad = em.merge(normatividadList1NewNormatividad);
                    if (oldIdAreaDerechoOfNormatividadList1NewNormatividad != null && !oldIdAreaDerechoOfNormatividadList1NewNormatividad.equals(clase)) {
                        oldIdAreaDerechoOfNormatividadList1NewNormatividad.getNormatividadList1().remove(normatividadList1NewNormatividad);
                        oldIdAreaDerechoOfNormatividadList1NewNormatividad = em.merge(oldIdAreaDerechoOfNormatividadList1NewNormatividad);
                    }
                }
            }
            for (TipoCuantia tipoCuantiaListOldTipoCuantia : tipoCuantiaListOld) {
                if (!tipoCuantiaListNew.contains(tipoCuantiaListOldTipoCuantia)) {
                    tipoCuantiaListOldTipoCuantia.setIdProcedimientoJuridico(null);
                    tipoCuantiaListOldTipoCuantia = em.merge(tipoCuantiaListOldTipoCuantia);
                }
            }
            for (TipoCuantia tipoCuantiaListNewTipoCuantia : tipoCuantiaListNew) {
                if (!tipoCuantiaListOld.contains(tipoCuantiaListNewTipoCuantia)) {
                    Clase oldIdProcedimientoJuridicoOfTipoCuantiaListNewTipoCuantia = tipoCuantiaListNewTipoCuantia.getIdProcedimientoJuridico();
                    tipoCuantiaListNewTipoCuantia.setIdProcedimientoJuridico(clase);
                    tipoCuantiaListNewTipoCuantia = em.merge(tipoCuantiaListNewTipoCuantia);
                    if (oldIdProcedimientoJuridicoOfTipoCuantiaListNewTipoCuantia != null && !oldIdProcedimientoJuridicoOfTipoCuantiaListNewTipoCuantia.equals(clase)) {
                        oldIdProcedimientoJuridicoOfTipoCuantiaListNewTipoCuantia.getTipoCuantiaList().remove(tipoCuantiaListNewTipoCuantia);
                        oldIdProcedimientoJuridicoOfTipoCuantiaListNewTipoCuantia = em.merge(oldIdProcedimientoJuridicoOfTipoCuantiaListNewTipoCuantia);
                    }
                }
            }
            for (Proceso procesoListOldProceso : procesoListOld) {
                if (!procesoListNew.contains(procesoListOldProceso)) {
                    procesoListOldProceso.setIdClase(null);
                    procesoListOldProceso = em.merge(procesoListOldProceso);
                }
            }
            for (Proceso procesoListNewProceso : procesoListNew) {
                if (!procesoListOld.contains(procesoListNewProceso)) {
                    Clase oldIdClaseOfProcesoListNewProceso = procesoListNewProceso.getIdClase();
                    procesoListNewProceso.setIdClase(clase);
                    procesoListNewProceso = em.merge(procesoListNewProceso);
                    if (oldIdClaseOfProcesoListNewProceso != null && !oldIdClaseOfProcesoListNewProceso.equals(clase)) {
                        oldIdClaseOfProcesoListNewProceso.getProcesoList().remove(procesoListNewProceso);
                        oldIdClaseOfProcesoListNewProceso = em.merge(oldIdClaseOfProcesoListNewProceso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clase.getId();
                if (findClase(id) == null) {
                    throw new NonexistentEntityException("The clase with id " + id + " no longer exists.");
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
            Clase clase;
            try {
                clase = em.getReference(Clase.class, id);
                clase.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clase with id " + id + " no longer exists.", enfe);
            }
            TipoClase idTipo = clase.getIdTipo();
            if (idTipo != null) {
                idTipo.getClaseList().remove(clase);
                idTipo = em.merge(idTipo);
            }
            List<Recurso> recursoList = clase.getRecursoList();
            for (Recurso recursoListRecurso : recursoList) {
                recursoListRecurso.setIdInstancia(null);
                recursoListRecurso = em.merge(recursoListRecurso);
            }
            List<Tema> temaList = clase.getTemaList();
            for (Tema temaListTema : temaList) {
                temaListTema.setIdAreaDerecho(null);
                temaListTema = em.merge(temaListTema);
            }
            List<EtapaProceso> etapaProcesoList = clase.getEtapaProcesoList();
            for (EtapaProceso etapaProcesoListEtapaProceso : etapaProcesoList) {
                etapaProcesoListEtapaProceso.setIdInstancia(null);
                etapaProcesoListEtapaProceso = em.merge(etapaProcesoListEtapaProceso);
            }
            List<ClaseIncidente> claseIncidenteList = clase.getClaseIncidenteList();
            for (ClaseIncidente claseIncidenteListClaseIncidente : claseIncidenteList) {
                claseIncidenteListClaseIncidente.setIdProcedimientoJuridico(null);
                claseIncidenteListClaseIncidente = em.merge(claseIncidenteListClaseIncidente);
            }
            List<SeguimientoProceso> seguimientoProcesoList = clase.getSeguimientoProcesoList();
            for (SeguimientoProceso seguimientoProcesoListSeguimientoProceso : seguimientoProcesoList) {
                seguimientoProcesoListSeguimientoProceso.setIdInstancia(null);
                seguimientoProcesoListSeguimientoProceso = em.merge(seguimientoProcesoListSeguimientoProceso);
            }
            List<Normatividad> normatividadList = clase.getNormatividadList();
            for (Normatividad normatividadListNormatividad : normatividadList) {
                normatividadListNormatividad.setIdClaseNormatividad(null);
                normatividadListNormatividad = em.merge(normatividadListNormatividad);
            }
            List<Normatividad> normatividadList1 = clase.getNormatividadList1();
            for (Normatividad normatividadList1Normatividad : normatividadList1) {
                normatividadList1Normatividad.setIdAreaDerecho(null);
                normatividadList1Normatividad = em.merge(normatividadList1Normatividad);
            }
            List<TipoCuantia> tipoCuantiaList = clase.getTipoCuantiaList();
            for (TipoCuantia tipoCuantiaListTipoCuantia : tipoCuantiaList) {
                tipoCuantiaListTipoCuantia.setIdProcedimientoJuridico(null);
                tipoCuantiaListTipoCuantia = em.merge(tipoCuantiaListTipoCuantia);
            }
            List<Proceso> procesoList = clase.getProcesoList();
            for (Proceso procesoListProceso : procesoList) {
                procesoListProceso.setIdClase(null);
                procesoListProceso = em.merge(procesoListProceso);
            }
            em.remove(clase);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clase> findClaseEntities() {
        return findClaseEntities(true, -1, -1);
    }

    public List<Clase> findClaseEntities(int maxResults, int firstResult) {
        return findClaseEntities(false, maxResults, firstResult);
    }

    private List<Clase> findClaseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clase.class));
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

    public Clase findClase(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clase.class, id);
        } finally {
            em.close();
        }
    }

    public int getClaseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clase> rt = cq.from(Clase.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
