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
import com.sae.persistence.domain.Retencion;
import com.sae.persistence.domain.AplicacionConcepto;
import com.sae.persistence.domain.ValorRetencion;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.GrupoServicios;
import com.sae.persistence.domain.ActividadConcepto;
import com.sae.persistence.domain.ConceptoRetencion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ConceptoRetencionJpaController implements Serializable {

    public ConceptoRetencionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConceptoRetencion conceptoRetencion) throws PreexistingEntityException, Exception {
        if (conceptoRetencion.getValorRetencionList() == null) {
            conceptoRetencion.setValorRetencionList(new ArrayList<ValorRetencion>());
        }
        if (conceptoRetencion.getGrupoServiciosList() == null) {
            conceptoRetencion.setGrupoServiciosList(new ArrayList<GrupoServicios>());
        }
        if (conceptoRetencion.getGrupoServiciosList1() == null) {
            conceptoRetencion.setGrupoServiciosList1(new ArrayList<GrupoServicios>());
        }
        if (conceptoRetencion.getActividadConceptoList() == null) {
            conceptoRetencion.setActividadConceptoList(new ArrayList<ActividadConcepto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Retencion idTipoRetencion = conceptoRetencion.getIdTipoRetencion();
            if (idTipoRetencion != null) {
                idTipoRetencion = em.getReference(idTipoRetencion.getClass(), idTipoRetencion.getId());
                conceptoRetencion.setIdTipoRetencion(idTipoRetencion);
            }
            AplicacionConcepto idAplicacionConcepto = conceptoRetencion.getIdAplicacionConcepto();
            if (idAplicacionConcepto != null) {
                idAplicacionConcepto = em.getReference(idAplicacionConcepto.getClass(), idAplicacionConcepto.getId());
                conceptoRetencion.setIdAplicacionConcepto(idAplicacionConcepto);
            }
            List<ValorRetencion> attachedValorRetencionList = new ArrayList<ValorRetencion>();
            for (ValorRetencion valorRetencionListValorRetencionToAttach : conceptoRetencion.getValorRetencionList()) {
                valorRetencionListValorRetencionToAttach = em.getReference(valorRetencionListValorRetencionToAttach.getClass(), valorRetencionListValorRetencionToAttach.getId());
                attachedValorRetencionList.add(valorRetencionListValorRetencionToAttach);
            }
            conceptoRetencion.setValorRetencionList(attachedValorRetencionList);
            List<GrupoServicios> attachedGrupoServiciosList = new ArrayList<GrupoServicios>();
            for (GrupoServicios grupoServiciosListGrupoServiciosToAttach : conceptoRetencion.getGrupoServiciosList()) {
                grupoServiciosListGrupoServiciosToAttach = em.getReference(grupoServiciosListGrupoServiciosToAttach.getClass(), grupoServiciosListGrupoServiciosToAttach.getId());
                attachedGrupoServiciosList.add(grupoServiciosListGrupoServiciosToAttach);
            }
            conceptoRetencion.setGrupoServiciosList(attachedGrupoServiciosList);
            List<GrupoServicios> attachedGrupoServiciosList1 = new ArrayList<GrupoServicios>();
            for (GrupoServicios grupoServiciosList1GrupoServiciosToAttach : conceptoRetencion.getGrupoServiciosList1()) {
                grupoServiciosList1GrupoServiciosToAttach = em.getReference(grupoServiciosList1GrupoServiciosToAttach.getClass(), grupoServiciosList1GrupoServiciosToAttach.getId());
                attachedGrupoServiciosList1.add(grupoServiciosList1GrupoServiciosToAttach);
            }
            conceptoRetencion.setGrupoServiciosList1(attachedGrupoServiciosList1);
            List<ActividadConcepto> attachedActividadConceptoList = new ArrayList<ActividadConcepto>();
            for (ActividadConcepto actividadConceptoListActividadConceptoToAttach : conceptoRetencion.getActividadConceptoList()) {
                actividadConceptoListActividadConceptoToAttach = em.getReference(actividadConceptoListActividadConceptoToAttach.getClass(), actividadConceptoListActividadConceptoToAttach.getId());
                attachedActividadConceptoList.add(actividadConceptoListActividadConceptoToAttach);
            }
            conceptoRetencion.setActividadConceptoList(attachedActividadConceptoList);
            em.persist(conceptoRetencion);
            if (idTipoRetencion != null) {
                idTipoRetencion.getConceptoRetencionList().add(conceptoRetencion);
                idTipoRetencion = em.merge(idTipoRetencion);
            }
            if (idAplicacionConcepto != null) {
                idAplicacionConcepto.getConceptoRetencionList().add(conceptoRetencion);
                idAplicacionConcepto = em.merge(idAplicacionConcepto);
            }
            for (ValorRetencion valorRetencionListValorRetencion : conceptoRetencion.getValorRetencionList()) {
                ConceptoRetencion oldIdConceptoOfValorRetencionListValorRetencion = valorRetencionListValorRetencion.getIdConcepto();
                valorRetencionListValorRetencion.setIdConcepto(conceptoRetencion);
                valorRetencionListValorRetencion = em.merge(valorRetencionListValorRetencion);
                if (oldIdConceptoOfValorRetencionListValorRetencion != null) {
                    oldIdConceptoOfValorRetencionListValorRetencion.getValorRetencionList().remove(valorRetencionListValorRetencion);
                    oldIdConceptoOfValorRetencionListValorRetencion = em.merge(oldIdConceptoOfValorRetencionListValorRetencion);
                }
            }
            for (GrupoServicios grupoServiciosListGrupoServicios : conceptoRetencion.getGrupoServiciosList()) {
                ConceptoRetencion oldIdConceptoRetecreeOfGrupoServiciosListGrupoServicios = grupoServiciosListGrupoServicios.getIdConceptoRetecree();
                grupoServiciosListGrupoServicios.setIdConceptoRetecree(conceptoRetencion);
                grupoServiciosListGrupoServicios = em.merge(grupoServiciosListGrupoServicios);
                if (oldIdConceptoRetecreeOfGrupoServiciosListGrupoServicios != null) {
                    oldIdConceptoRetecreeOfGrupoServiciosListGrupoServicios.getGrupoServiciosList().remove(grupoServiciosListGrupoServicios);
                    oldIdConceptoRetecreeOfGrupoServiciosListGrupoServicios = em.merge(oldIdConceptoRetecreeOfGrupoServiciosListGrupoServicios);
                }
            }
            for (GrupoServicios grupoServiciosList1GrupoServicios : conceptoRetencion.getGrupoServiciosList1()) {
                ConceptoRetencion oldIdConceptoRetefuenteOfGrupoServiciosList1GrupoServicios = grupoServiciosList1GrupoServicios.getIdConceptoRetefuente();
                grupoServiciosList1GrupoServicios.setIdConceptoRetefuente(conceptoRetencion);
                grupoServiciosList1GrupoServicios = em.merge(grupoServiciosList1GrupoServicios);
                if (oldIdConceptoRetefuenteOfGrupoServiciosList1GrupoServicios != null) {
                    oldIdConceptoRetefuenteOfGrupoServiciosList1GrupoServicios.getGrupoServiciosList1().remove(grupoServiciosList1GrupoServicios);
                    oldIdConceptoRetefuenteOfGrupoServiciosList1GrupoServicios = em.merge(oldIdConceptoRetefuenteOfGrupoServiciosList1GrupoServicios);
                }
            }
            for (ActividadConcepto actividadConceptoListActividadConcepto : conceptoRetencion.getActividadConceptoList()) {
                ConceptoRetencion oldIdConceptoOfActividadConceptoListActividadConcepto = actividadConceptoListActividadConcepto.getIdConcepto();
                actividadConceptoListActividadConcepto.setIdConcepto(conceptoRetencion);
                actividadConceptoListActividadConcepto = em.merge(actividadConceptoListActividadConcepto);
                if (oldIdConceptoOfActividadConceptoListActividadConcepto != null) {
                    oldIdConceptoOfActividadConceptoListActividadConcepto.getActividadConceptoList().remove(actividadConceptoListActividadConcepto);
                    oldIdConceptoOfActividadConceptoListActividadConcepto = em.merge(oldIdConceptoOfActividadConceptoListActividadConcepto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findConceptoRetencion(conceptoRetencion.getId()) != null) {
                throw new PreexistingEntityException("ConceptoRetencion " + conceptoRetencion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConceptoRetencion conceptoRetencion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ConceptoRetencion persistentConceptoRetencion = em.find(ConceptoRetencion.class, conceptoRetencion.getId());
            Retencion idTipoRetencionOld = persistentConceptoRetencion.getIdTipoRetencion();
            Retencion idTipoRetencionNew = conceptoRetencion.getIdTipoRetencion();
            AplicacionConcepto idAplicacionConceptoOld = persistentConceptoRetencion.getIdAplicacionConcepto();
            AplicacionConcepto idAplicacionConceptoNew = conceptoRetencion.getIdAplicacionConcepto();
            List<ValorRetencion> valorRetencionListOld = persistentConceptoRetencion.getValorRetencionList();
            List<ValorRetencion> valorRetencionListNew = conceptoRetencion.getValorRetencionList();
            List<GrupoServicios> grupoServiciosListOld = persistentConceptoRetencion.getGrupoServiciosList();
            List<GrupoServicios> grupoServiciosListNew = conceptoRetencion.getGrupoServiciosList();
            List<GrupoServicios> grupoServiciosList1Old = persistentConceptoRetencion.getGrupoServiciosList1();
            List<GrupoServicios> grupoServiciosList1New = conceptoRetencion.getGrupoServiciosList1();
            List<ActividadConcepto> actividadConceptoListOld = persistentConceptoRetencion.getActividadConceptoList();
            List<ActividadConcepto> actividadConceptoListNew = conceptoRetencion.getActividadConceptoList();
            if (idTipoRetencionNew != null) {
                idTipoRetencionNew = em.getReference(idTipoRetencionNew.getClass(), idTipoRetencionNew.getId());
                conceptoRetencion.setIdTipoRetencion(idTipoRetencionNew);
            }
            if (idAplicacionConceptoNew != null) {
                idAplicacionConceptoNew = em.getReference(idAplicacionConceptoNew.getClass(), idAplicacionConceptoNew.getId());
                conceptoRetencion.setIdAplicacionConcepto(idAplicacionConceptoNew);
            }
            List<ValorRetencion> attachedValorRetencionListNew = new ArrayList<ValorRetencion>();
            for (ValorRetencion valorRetencionListNewValorRetencionToAttach : valorRetencionListNew) {
                valorRetencionListNewValorRetencionToAttach = em.getReference(valorRetencionListNewValorRetencionToAttach.getClass(), valorRetencionListNewValorRetencionToAttach.getId());
                attachedValorRetencionListNew.add(valorRetencionListNewValorRetencionToAttach);
            }
            valorRetencionListNew = attachedValorRetencionListNew;
            conceptoRetencion.setValorRetencionList(valorRetencionListNew);
            List<GrupoServicios> attachedGrupoServiciosListNew = new ArrayList<GrupoServicios>();
            for (GrupoServicios grupoServiciosListNewGrupoServiciosToAttach : grupoServiciosListNew) {
                grupoServiciosListNewGrupoServiciosToAttach = em.getReference(grupoServiciosListNewGrupoServiciosToAttach.getClass(), grupoServiciosListNewGrupoServiciosToAttach.getId());
                attachedGrupoServiciosListNew.add(grupoServiciosListNewGrupoServiciosToAttach);
            }
            grupoServiciosListNew = attachedGrupoServiciosListNew;
            conceptoRetencion.setGrupoServiciosList(grupoServiciosListNew);
            List<GrupoServicios> attachedGrupoServiciosList1New = new ArrayList<GrupoServicios>();
            for (GrupoServicios grupoServiciosList1NewGrupoServiciosToAttach : grupoServiciosList1New) {
                grupoServiciosList1NewGrupoServiciosToAttach = em.getReference(grupoServiciosList1NewGrupoServiciosToAttach.getClass(), grupoServiciosList1NewGrupoServiciosToAttach.getId());
                attachedGrupoServiciosList1New.add(grupoServiciosList1NewGrupoServiciosToAttach);
            }
            grupoServiciosList1New = attachedGrupoServiciosList1New;
            conceptoRetencion.setGrupoServiciosList1(grupoServiciosList1New);
            List<ActividadConcepto> attachedActividadConceptoListNew = new ArrayList<ActividadConcepto>();
            for (ActividadConcepto actividadConceptoListNewActividadConceptoToAttach : actividadConceptoListNew) {
                actividadConceptoListNewActividadConceptoToAttach = em.getReference(actividadConceptoListNewActividadConceptoToAttach.getClass(), actividadConceptoListNewActividadConceptoToAttach.getId());
                attachedActividadConceptoListNew.add(actividadConceptoListNewActividadConceptoToAttach);
            }
            actividadConceptoListNew = attachedActividadConceptoListNew;
            conceptoRetencion.setActividadConceptoList(actividadConceptoListNew);
            conceptoRetencion = em.merge(conceptoRetencion);
            if (idTipoRetencionOld != null && !idTipoRetencionOld.equals(idTipoRetencionNew)) {
                idTipoRetencionOld.getConceptoRetencionList().remove(conceptoRetencion);
                idTipoRetencionOld = em.merge(idTipoRetencionOld);
            }
            if (idTipoRetencionNew != null && !idTipoRetencionNew.equals(idTipoRetencionOld)) {
                idTipoRetencionNew.getConceptoRetencionList().add(conceptoRetencion);
                idTipoRetencionNew = em.merge(idTipoRetencionNew);
            }
            if (idAplicacionConceptoOld != null && !idAplicacionConceptoOld.equals(idAplicacionConceptoNew)) {
                idAplicacionConceptoOld.getConceptoRetencionList().remove(conceptoRetencion);
                idAplicacionConceptoOld = em.merge(idAplicacionConceptoOld);
            }
            if (idAplicacionConceptoNew != null && !idAplicacionConceptoNew.equals(idAplicacionConceptoOld)) {
                idAplicacionConceptoNew.getConceptoRetencionList().add(conceptoRetencion);
                idAplicacionConceptoNew = em.merge(idAplicacionConceptoNew);
            }
            for (ValorRetencion valorRetencionListOldValorRetencion : valorRetencionListOld) {
                if (!valorRetencionListNew.contains(valorRetencionListOldValorRetencion)) {
                    valorRetencionListOldValorRetencion.setIdConcepto(null);
                    valorRetencionListOldValorRetencion = em.merge(valorRetencionListOldValorRetencion);
                }
            }
            for (ValorRetencion valorRetencionListNewValorRetencion : valorRetencionListNew) {
                if (!valorRetencionListOld.contains(valorRetencionListNewValorRetencion)) {
                    ConceptoRetencion oldIdConceptoOfValorRetencionListNewValorRetencion = valorRetencionListNewValorRetencion.getIdConcepto();
                    valorRetencionListNewValorRetencion.setIdConcepto(conceptoRetencion);
                    valorRetencionListNewValorRetencion = em.merge(valorRetencionListNewValorRetencion);
                    if (oldIdConceptoOfValorRetencionListNewValorRetencion != null && !oldIdConceptoOfValorRetencionListNewValorRetencion.equals(conceptoRetencion)) {
                        oldIdConceptoOfValorRetencionListNewValorRetencion.getValorRetencionList().remove(valorRetencionListNewValorRetencion);
                        oldIdConceptoOfValorRetencionListNewValorRetencion = em.merge(oldIdConceptoOfValorRetencionListNewValorRetencion);
                    }
                }
            }
            for (GrupoServicios grupoServiciosListOldGrupoServicios : grupoServiciosListOld) {
                if (!grupoServiciosListNew.contains(grupoServiciosListOldGrupoServicios)) {
                    grupoServiciosListOldGrupoServicios.setIdConceptoRetecree(null);
                    grupoServiciosListOldGrupoServicios = em.merge(grupoServiciosListOldGrupoServicios);
                }
            }
            for (GrupoServicios grupoServiciosListNewGrupoServicios : grupoServiciosListNew) {
                if (!grupoServiciosListOld.contains(grupoServiciosListNewGrupoServicios)) {
                    ConceptoRetencion oldIdConceptoRetecreeOfGrupoServiciosListNewGrupoServicios = grupoServiciosListNewGrupoServicios.getIdConceptoRetecree();
                    grupoServiciosListNewGrupoServicios.setIdConceptoRetecree(conceptoRetencion);
                    grupoServiciosListNewGrupoServicios = em.merge(grupoServiciosListNewGrupoServicios);
                    if (oldIdConceptoRetecreeOfGrupoServiciosListNewGrupoServicios != null && !oldIdConceptoRetecreeOfGrupoServiciosListNewGrupoServicios.equals(conceptoRetencion)) {
                        oldIdConceptoRetecreeOfGrupoServiciosListNewGrupoServicios.getGrupoServiciosList().remove(grupoServiciosListNewGrupoServicios);
                        oldIdConceptoRetecreeOfGrupoServiciosListNewGrupoServicios = em.merge(oldIdConceptoRetecreeOfGrupoServiciosListNewGrupoServicios);
                    }
                }
            }
            for (GrupoServicios grupoServiciosList1OldGrupoServicios : grupoServiciosList1Old) {
                if (!grupoServiciosList1New.contains(grupoServiciosList1OldGrupoServicios)) {
                    grupoServiciosList1OldGrupoServicios.setIdConceptoRetefuente(null);
                    grupoServiciosList1OldGrupoServicios = em.merge(grupoServiciosList1OldGrupoServicios);
                }
            }
            for (GrupoServicios grupoServiciosList1NewGrupoServicios : grupoServiciosList1New) {
                if (!grupoServiciosList1Old.contains(grupoServiciosList1NewGrupoServicios)) {
                    ConceptoRetencion oldIdConceptoRetefuenteOfGrupoServiciosList1NewGrupoServicios = grupoServiciosList1NewGrupoServicios.getIdConceptoRetefuente();
                    grupoServiciosList1NewGrupoServicios.setIdConceptoRetefuente(conceptoRetencion);
                    grupoServiciosList1NewGrupoServicios = em.merge(grupoServiciosList1NewGrupoServicios);
                    if (oldIdConceptoRetefuenteOfGrupoServiciosList1NewGrupoServicios != null && !oldIdConceptoRetefuenteOfGrupoServiciosList1NewGrupoServicios.equals(conceptoRetencion)) {
                        oldIdConceptoRetefuenteOfGrupoServiciosList1NewGrupoServicios.getGrupoServiciosList1().remove(grupoServiciosList1NewGrupoServicios);
                        oldIdConceptoRetefuenteOfGrupoServiciosList1NewGrupoServicios = em.merge(oldIdConceptoRetefuenteOfGrupoServiciosList1NewGrupoServicios);
                    }
                }
            }
            for (ActividadConcepto actividadConceptoListOldActividadConcepto : actividadConceptoListOld) {
                if (!actividadConceptoListNew.contains(actividadConceptoListOldActividadConcepto)) {
                    actividadConceptoListOldActividadConcepto.setIdConcepto(null);
                    actividadConceptoListOldActividadConcepto = em.merge(actividadConceptoListOldActividadConcepto);
                }
            }
            for (ActividadConcepto actividadConceptoListNewActividadConcepto : actividadConceptoListNew) {
                if (!actividadConceptoListOld.contains(actividadConceptoListNewActividadConcepto)) {
                    ConceptoRetencion oldIdConceptoOfActividadConceptoListNewActividadConcepto = actividadConceptoListNewActividadConcepto.getIdConcepto();
                    actividadConceptoListNewActividadConcepto.setIdConcepto(conceptoRetencion);
                    actividadConceptoListNewActividadConcepto = em.merge(actividadConceptoListNewActividadConcepto);
                    if (oldIdConceptoOfActividadConceptoListNewActividadConcepto != null && !oldIdConceptoOfActividadConceptoListNewActividadConcepto.equals(conceptoRetencion)) {
                        oldIdConceptoOfActividadConceptoListNewActividadConcepto.getActividadConceptoList().remove(actividadConceptoListNewActividadConcepto);
                        oldIdConceptoOfActividadConceptoListNewActividadConcepto = em.merge(oldIdConceptoOfActividadConceptoListNewActividadConcepto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = conceptoRetencion.getId();
                if (findConceptoRetencion(id) == null) {
                    throw new NonexistentEntityException("The conceptoRetencion with id " + id + " no longer exists.");
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
            ConceptoRetencion conceptoRetencion;
            try {
                conceptoRetencion = em.getReference(ConceptoRetencion.class, id);
                conceptoRetencion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The conceptoRetencion with id " + id + " no longer exists.", enfe);
            }
            Retencion idTipoRetencion = conceptoRetencion.getIdTipoRetencion();
            if (idTipoRetencion != null) {
                idTipoRetencion.getConceptoRetencionList().remove(conceptoRetencion);
                idTipoRetencion = em.merge(idTipoRetencion);
            }
            AplicacionConcepto idAplicacionConcepto = conceptoRetencion.getIdAplicacionConcepto();
            if (idAplicacionConcepto != null) {
                idAplicacionConcepto.getConceptoRetencionList().remove(conceptoRetencion);
                idAplicacionConcepto = em.merge(idAplicacionConcepto);
            }
            List<ValorRetencion> valorRetencionList = conceptoRetencion.getValorRetencionList();
            for (ValorRetencion valorRetencionListValorRetencion : valorRetencionList) {
                valorRetencionListValorRetencion.setIdConcepto(null);
                valorRetencionListValorRetencion = em.merge(valorRetencionListValorRetencion);
            }
            List<GrupoServicios> grupoServiciosList = conceptoRetencion.getGrupoServiciosList();
            for (GrupoServicios grupoServiciosListGrupoServicios : grupoServiciosList) {
                grupoServiciosListGrupoServicios.setIdConceptoRetecree(null);
                grupoServiciosListGrupoServicios = em.merge(grupoServiciosListGrupoServicios);
            }
            List<GrupoServicios> grupoServiciosList1 = conceptoRetencion.getGrupoServiciosList1();
            for (GrupoServicios grupoServiciosList1GrupoServicios : grupoServiciosList1) {
                grupoServiciosList1GrupoServicios.setIdConceptoRetefuente(null);
                grupoServiciosList1GrupoServicios = em.merge(grupoServiciosList1GrupoServicios);
            }
            List<ActividadConcepto> actividadConceptoList = conceptoRetencion.getActividadConceptoList();
            for (ActividadConcepto actividadConceptoListActividadConcepto : actividadConceptoList) {
                actividadConceptoListActividadConcepto.setIdConcepto(null);
                actividadConceptoListActividadConcepto = em.merge(actividadConceptoListActividadConcepto);
            }
            em.remove(conceptoRetencion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ConceptoRetencion> findConceptoRetencionEntities() {
        return findConceptoRetencionEntities(true, -1, -1);
    }

    public List<ConceptoRetencion> findConceptoRetencionEntities(int maxResults, int firstResult) {
        return findConceptoRetencionEntities(false, maxResults, firstResult);
    }

    private List<ConceptoRetencion> findConceptoRetencionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConceptoRetencion.class));
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

    public ConceptoRetencion findConceptoRetencion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConceptoRetencion.class, id);
        } finally {
            em.close();
        }
    }

    public int getConceptoRetencionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConceptoRetencion> rt = cq.from(ConceptoRetencion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
