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
import com.sae.persistence.domain.TipoDatoMatrizRiesgo;
import com.sae.persistence.domain.RegistroMatrizRiesgo;
import com.sae.persistence.domain.SubtipoDatoMatrizRiesgo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class SubtipoDatoMatrizRiesgoJpaController implements Serializable {

    public SubtipoDatoMatrizRiesgoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SubtipoDatoMatrizRiesgo subtipoDatoMatrizRiesgo) throws PreexistingEntityException, Exception {
        if (subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList() == null) {
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList(new ArrayList<RegistroMatrizRiesgo>());
        }
        if (subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList1() == null) {
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList1(new ArrayList<RegistroMatrizRiesgo>());
        }
        if (subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList2() == null) {
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList2(new ArrayList<RegistroMatrizRiesgo>());
        }
        if (subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList3() == null) {
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList3(new ArrayList<RegistroMatrizRiesgo>());
        }
        if (subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList4() == null) {
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList4(new ArrayList<RegistroMatrizRiesgo>());
        }
        if (subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList5() == null) {
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList5(new ArrayList<RegistroMatrizRiesgo>());
        }
        if (subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList6() == null) {
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList6(new ArrayList<RegistroMatrizRiesgo>());
        }
        if (subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList7() == null) {
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList7(new ArrayList<RegistroMatrizRiesgo>());
        }
        if (subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList8() == null) {
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList8(new ArrayList<RegistroMatrizRiesgo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoDatoMatrizRiesgo idTipo = subtipoDatoMatrizRiesgo.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getId());
                subtipoDatoMatrizRiesgo.setIdTipo(idTipo);
            }
            List<RegistroMatrizRiesgo> attachedRegistroMatrizRiesgoList = new ArrayList<RegistroMatrizRiesgo>();
            for (RegistroMatrizRiesgo registroMatrizRiesgoListRegistroMatrizRiesgoToAttach : subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList()) {
                registroMatrizRiesgoListRegistroMatrizRiesgoToAttach = em.getReference(registroMatrizRiesgoListRegistroMatrizRiesgoToAttach.getClass(), registroMatrizRiesgoListRegistroMatrizRiesgoToAttach.getId());
                attachedRegistroMatrizRiesgoList.add(registroMatrizRiesgoListRegistroMatrizRiesgoToAttach);
            }
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList(attachedRegistroMatrizRiesgoList);
            List<RegistroMatrizRiesgo> attachedRegistroMatrizRiesgoList1 = new ArrayList<RegistroMatrizRiesgo>();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList1RegistroMatrizRiesgoToAttach : subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList1()) {
                registroMatrizRiesgoList1RegistroMatrizRiesgoToAttach = em.getReference(registroMatrizRiesgoList1RegistroMatrizRiesgoToAttach.getClass(), registroMatrizRiesgoList1RegistroMatrizRiesgoToAttach.getId());
                attachedRegistroMatrizRiesgoList1.add(registroMatrizRiesgoList1RegistroMatrizRiesgoToAttach);
            }
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList1(attachedRegistroMatrizRiesgoList1);
            List<RegistroMatrizRiesgo> attachedRegistroMatrizRiesgoList2 = new ArrayList<RegistroMatrizRiesgo>();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList2RegistroMatrizRiesgoToAttach : subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList2()) {
                registroMatrizRiesgoList2RegistroMatrizRiesgoToAttach = em.getReference(registroMatrizRiesgoList2RegistroMatrizRiesgoToAttach.getClass(), registroMatrizRiesgoList2RegistroMatrizRiesgoToAttach.getId());
                attachedRegistroMatrizRiesgoList2.add(registroMatrizRiesgoList2RegistroMatrizRiesgoToAttach);
            }
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList2(attachedRegistroMatrizRiesgoList2);
            List<RegistroMatrizRiesgo> attachedRegistroMatrizRiesgoList3 = new ArrayList<RegistroMatrizRiesgo>();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList3RegistroMatrizRiesgoToAttach : subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList3()) {
                registroMatrizRiesgoList3RegistroMatrizRiesgoToAttach = em.getReference(registroMatrizRiesgoList3RegistroMatrizRiesgoToAttach.getClass(), registroMatrizRiesgoList3RegistroMatrizRiesgoToAttach.getId());
                attachedRegistroMatrizRiesgoList3.add(registroMatrizRiesgoList3RegistroMatrizRiesgoToAttach);
            }
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList3(attachedRegistroMatrizRiesgoList3);
            List<RegistroMatrizRiesgo> attachedRegistroMatrizRiesgoList4 = new ArrayList<RegistroMatrizRiesgo>();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList4RegistroMatrizRiesgoToAttach : subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList4()) {
                registroMatrizRiesgoList4RegistroMatrizRiesgoToAttach = em.getReference(registroMatrizRiesgoList4RegistroMatrizRiesgoToAttach.getClass(), registroMatrizRiesgoList4RegistroMatrizRiesgoToAttach.getId());
                attachedRegistroMatrizRiesgoList4.add(registroMatrizRiesgoList4RegistroMatrizRiesgoToAttach);
            }
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList4(attachedRegistroMatrizRiesgoList4);
            List<RegistroMatrizRiesgo> attachedRegistroMatrizRiesgoList5 = new ArrayList<RegistroMatrizRiesgo>();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList5RegistroMatrizRiesgoToAttach : subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList5()) {
                registroMatrizRiesgoList5RegistroMatrizRiesgoToAttach = em.getReference(registroMatrizRiesgoList5RegistroMatrizRiesgoToAttach.getClass(), registroMatrizRiesgoList5RegistroMatrizRiesgoToAttach.getId());
                attachedRegistroMatrizRiesgoList5.add(registroMatrizRiesgoList5RegistroMatrizRiesgoToAttach);
            }
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList5(attachedRegistroMatrizRiesgoList5);
            List<RegistroMatrizRiesgo> attachedRegistroMatrizRiesgoList6 = new ArrayList<RegistroMatrizRiesgo>();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList6RegistroMatrizRiesgoToAttach : subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList6()) {
                registroMatrizRiesgoList6RegistroMatrizRiesgoToAttach = em.getReference(registroMatrizRiesgoList6RegistroMatrizRiesgoToAttach.getClass(), registroMatrizRiesgoList6RegistroMatrizRiesgoToAttach.getId());
                attachedRegistroMatrizRiesgoList6.add(registroMatrizRiesgoList6RegistroMatrizRiesgoToAttach);
            }
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList6(attachedRegistroMatrizRiesgoList6);
            List<RegistroMatrizRiesgo> attachedRegistroMatrizRiesgoList7 = new ArrayList<RegistroMatrizRiesgo>();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList7RegistroMatrizRiesgoToAttach : subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList7()) {
                registroMatrizRiesgoList7RegistroMatrizRiesgoToAttach = em.getReference(registroMatrizRiesgoList7RegistroMatrizRiesgoToAttach.getClass(), registroMatrizRiesgoList7RegistroMatrizRiesgoToAttach.getId());
                attachedRegistroMatrizRiesgoList7.add(registroMatrizRiesgoList7RegistroMatrizRiesgoToAttach);
            }
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList7(attachedRegistroMatrizRiesgoList7);
            List<RegistroMatrizRiesgo> attachedRegistroMatrizRiesgoList8 = new ArrayList<RegistroMatrizRiesgo>();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList8RegistroMatrizRiesgoToAttach : subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList8()) {
                registroMatrizRiesgoList8RegistroMatrizRiesgoToAttach = em.getReference(registroMatrizRiesgoList8RegistroMatrizRiesgoToAttach.getClass(), registroMatrizRiesgoList8RegistroMatrizRiesgoToAttach.getId());
                attachedRegistroMatrizRiesgoList8.add(registroMatrizRiesgoList8RegistroMatrizRiesgoToAttach);
            }
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList8(attachedRegistroMatrizRiesgoList8);
            em.persist(subtipoDatoMatrizRiesgo);
            if (idTipo != null) {
                idTipo.getSubtipoDatoMatrizRiesgoList().add(subtipoDatoMatrizRiesgo);
                idTipo = em.merge(idTipo);
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoListRegistroMatrizRiesgo : subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList()) {
                SubtipoDatoMatrizRiesgo oldIdTipoOfRegistroMatrizRiesgoListRegistroMatrizRiesgo = registroMatrizRiesgoListRegistroMatrizRiesgo.getIdTipo();
                registroMatrizRiesgoListRegistroMatrizRiesgo.setIdTipo(subtipoDatoMatrizRiesgo);
                registroMatrizRiesgoListRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoListRegistroMatrizRiesgo);
                if (oldIdTipoOfRegistroMatrizRiesgoListRegistroMatrizRiesgo != null) {
                    oldIdTipoOfRegistroMatrizRiesgoListRegistroMatrizRiesgo.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgoListRegistroMatrizRiesgo);
                    oldIdTipoOfRegistroMatrizRiesgoListRegistroMatrizRiesgo = em.merge(oldIdTipoOfRegistroMatrizRiesgoListRegistroMatrizRiesgo);
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList1RegistroMatrizRiesgo : subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList1()) {
                SubtipoDatoMatrizRiesgo oldIdProbabilidadTratamientoOfRegistroMatrizRiesgoList1RegistroMatrizRiesgo = registroMatrizRiesgoList1RegistroMatrizRiesgo.getIdProbabilidadTratamiento();
                registroMatrizRiesgoList1RegistroMatrizRiesgo.setIdProbabilidadTratamiento(subtipoDatoMatrizRiesgo);
                registroMatrizRiesgoList1RegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList1RegistroMatrizRiesgo);
                if (oldIdProbabilidadTratamientoOfRegistroMatrizRiesgoList1RegistroMatrizRiesgo != null) {
                    oldIdProbabilidadTratamientoOfRegistroMatrizRiesgoList1RegistroMatrizRiesgo.getRegistroMatrizRiesgoList1().remove(registroMatrizRiesgoList1RegistroMatrizRiesgo);
                    oldIdProbabilidadTratamientoOfRegistroMatrizRiesgoList1RegistroMatrizRiesgo = em.merge(oldIdProbabilidadTratamientoOfRegistroMatrizRiesgoList1RegistroMatrizRiesgo);
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList2RegistroMatrizRiesgo : subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList2()) {
                SubtipoDatoMatrizRiesgo oldIdProbabilidadOfRegistroMatrizRiesgoList2RegistroMatrizRiesgo = registroMatrizRiesgoList2RegistroMatrizRiesgo.getIdProbabilidad();
                registroMatrizRiesgoList2RegistroMatrizRiesgo.setIdProbabilidad(subtipoDatoMatrizRiesgo);
                registroMatrizRiesgoList2RegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList2RegistroMatrizRiesgo);
                if (oldIdProbabilidadOfRegistroMatrizRiesgoList2RegistroMatrizRiesgo != null) {
                    oldIdProbabilidadOfRegistroMatrizRiesgoList2RegistroMatrizRiesgo.getRegistroMatrizRiesgoList2().remove(registroMatrizRiesgoList2RegistroMatrizRiesgo);
                    oldIdProbabilidadOfRegistroMatrizRiesgoList2RegistroMatrizRiesgo = em.merge(oldIdProbabilidadOfRegistroMatrizRiesgoList2RegistroMatrizRiesgo);
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList3RegistroMatrizRiesgo : subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList3()) {
                SubtipoDatoMatrizRiesgo oldIdMonitoreoOfRegistroMatrizRiesgoList3RegistroMatrizRiesgo = registroMatrizRiesgoList3RegistroMatrizRiesgo.getIdMonitoreo();
                registroMatrizRiesgoList3RegistroMatrizRiesgo.setIdMonitoreo(subtipoDatoMatrizRiesgo);
                registroMatrizRiesgoList3RegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList3RegistroMatrizRiesgo);
                if (oldIdMonitoreoOfRegistroMatrizRiesgoList3RegistroMatrizRiesgo != null) {
                    oldIdMonitoreoOfRegistroMatrizRiesgoList3RegistroMatrizRiesgo.getRegistroMatrizRiesgoList3().remove(registroMatrizRiesgoList3RegistroMatrizRiesgo);
                    oldIdMonitoreoOfRegistroMatrizRiesgoList3RegistroMatrizRiesgo = em.merge(oldIdMonitoreoOfRegistroMatrizRiesgoList3RegistroMatrizRiesgo);
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList4RegistroMatrizRiesgo : subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList4()) {
                SubtipoDatoMatrizRiesgo oldIdFuenteOfRegistroMatrizRiesgoList4RegistroMatrizRiesgo = registroMatrizRiesgoList4RegistroMatrizRiesgo.getIdFuente();
                registroMatrizRiesgoList4RegistroMatrizRiesgo.setIdFuente(subtipoDatoMatrizRiesgo);
                registroMatrizRiesgoList4RegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList4RegistroMatrizRiesgo);
                if (oldIdFuenteOfRegistroMatrizRiesgoList4RegistroMatrizRiesgo != null) {
                    oldIdFuenteOfRegistroMatrizRiesgoList4RegistroMatrizRiesgo.getRegistroMatrizRiesgoList4().remove(registroMatrizRiesgoList4RegistroMatrizRiesgo);
                    oldIdFuenteOfRegistroMatrizRiesgoList4RegistroMatrizRiesgo = em.merge(oldIdFuenteOfRegistroMatrizRiesgoList4RegistroMatrizRiesgo);
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList5RegistroMatrizRiesgo : subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList5()) {
                SubtipoDatoMatrizRiesgo oldIdEtapaOfRegistroMatrizRiesgoList5RegistroMatrizRiesgo = registroMatrizRiesgoList5RegistroMatrizRiesgo.getIdEtapa();
                registroMatrizRiesgoList5RegistroMatrizRiesgo.setIdEtapa(subtipoDatoMatrizRiesgo);
                registroMatrizRiesgoList5RegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList5RegistroMatrizRiesgo);
                if (oldIdEtapaOfRegistroMatrizRiesgoList5RegistroMatrizRiesgo != null) {
                    oldIdEtapaOfRegistroMatrizRiesgoList5RegistroMatrizRiesgo.getRegistroMatrizRiesgoList5().remove(registroMatrizRiesgoList5RegistroMatrizRiesgo);
                    oldIdEtapaOfRegistroMatrizRiesgoList5RegistroMatrizRiesgo = em.merge(oldIdEtapaOfRegistroMatrizRiesgoList5RegistroMatrizRiesgo);
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList6RegistroMatrizRiesgo : subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList6()) {
                SubtipoDatoMatrizRiesgo oldIdClaseOfRegistroMatrizRiesgoList6RegistroMatrizRiesgo = registroMatrizRiesgoList6RegistroMatrizRiesgo.getIdClase();
                registroMatrizRiesgoList6RegistroMatrizRiesgo.setIdClase(subtipoDatoMatrizRiesgo);
                registroMatrizRiesgoList6RegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList6RegistroMatrizRiesgo);
                if (oldIdClaseOfRegistroMatrizRiesgoList6RegistroMatrizRiesgo != null) {
                    oldIdClaseOfRegistroMatrizRiesgoList6RegistroMatrizRiesgo.getRegistroMatrizRiesgoList6().remove(registroMatrizRiesgoList6RegistroMatrizRiesgo);
                    oldIdClaseOfRegistroMatrizRiesgoList6RegistroMatrizRiesgo = em.merge(oldIdClaseOfRegistroMatrizRiesgoList6RegistroMatrizRiesgo);
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList7RegistroMatrizRiesgo : subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList7()) {
                SubtipoDatoMatrizRiesgo oldIdCategoriaTratamientoOfRegistroMatrizRiesgoList7RegistroMatrizRiesgo = registroMatrizRiesgoList7RegistroMatrizRiesgo.getIdCategoriaTratamiento();
                registroMatrizRiesgoList7RegistroMatrizRiesgo.setIdCategoriaTratamiento(subtipoDatoMatrizRiesgo);
                registroMatrizRiesgoList7RegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList7RegistroMatrizRiesgo);
                if (oldIdCategoriaTratamientoOfRegistroMatrizRiesgoList7RegistroMatrizRiesgo != null) {
                    oldIdCategoriaTratamientoOfRegistroMatrizRiesgoList7RegistroMatrizRiesgo.getRegistroMatrizRiesgoList7().remove(registroMatrizRiesgoList7RegistroMatrizRiesgo);
                    oldIdCategoriaTratamientoOfRegistroMatrizRiesgoList7RegistroMatrizRiesgo = em.merge(oldIdCategoriaTratamientoOfRegistroMatrizRiesgoList7RegistroMatrizRiesgo);
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList8RegistroMatrizRiesgo : subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList8()) {
                SubtipoDatoMatrizRiesgo oldIdCategoriaOfRegistroMatrizRiesgoList8RegistroMatrizRiesgo = registroMatrizRiesgoList8RegistroMatrizRiesgo.getIdCategoria();
                registroMatrizRiesgoList8RegistroMatrizRiesgo.setIdCategoria(subtipoDatoMatrizRiesgo);
                registroMatrizRiesgoList8RegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList8RegistroMatrizRiesgo);
                if (oldIdCategoriaOfRegistroMatrizRiesgoList8RegistroMatrizRiesgo != null) {
                    oldIdCategoriaOfRegistroMatrizRiesgoList8RegistroMatrizRiesgo.getRegistroMatrizRiesgoList8().remove(registroMatrizRiesgoList8RegistroMatrizRiesgo);
                    oldIdCategoriaOfRegistroMatrizRiesgoList8RegistroMatrizRiesgo = em.merge(oldIdCategoriaOfRegistroMatrizRiesgoList8RegistroMatrizRiesgo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSubtipoDatoMatrizRiesgo(subtipoDatoMatrizRiesgo.getId()) != null) {
                throw new PreexistingEntityException("SubtipoDatoMatrizRiesgo " + subtipoDatoMatrizRiesgo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SubtipoDatoMatrizRiesgo subtipoDatoMatrizRiesgo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SubtipoDatoMatrizRiesgo persistentSubtipoDatoMatrizRiesgo = em.find(SubtipoDatoMatrizRiesgo.class, subtipoDatoMatrizRiesgo.getId());
            TipoDatoMatrizRiesgo idTipoOld = persistentSubtipoDatoMatrizRiesgo.getIdTipo();
            TipoDatoMatrizRiesgo idTipoNew = subtipoDatoMatrizRiesgo.getIdTipo();
            List<RegistroMatrizRiesgo> registroMatrizRiesgoListOld = persistentSubtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList();
            List<RegistroMatrizRiesgo> registroMatrizRiesgoListNew = subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList();
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList1Old = persistentSubtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList1();
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList1New = subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList1();
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList2Old = persistentSubtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList2();
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList2New = subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList2();
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList3Old = persistentSubtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList3();
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList3New = subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList3();
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList4Old = persistentSubtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList4();
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList4New = subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList4();
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList5Old = persistentSubtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList5();
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList5New = subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList5();
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList6Old = persistentSubtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList6();
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList6New = subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList6();
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList7Old = persistentSubtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList7();
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList7New = subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList7();
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList8Old = persistentSubtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList8();
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList8New = subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList8();
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getId());
                subtipoDatoMatrizRiesgo.setIdTipo(idTipoNew);
            }
            List<RegistroMatrizRiesgo> attachedRegistroMatrizRiesgoListNew = new ArrayList<RegistroMatrizRiesgo>();
            for (RegistroMatrizRiesgo registroMatrizRiesgoListNewRegistroMatrizRiesgoToAttach : registroMatrizRiesgoListNew) {
                registroMatrizRiesgoListNewRegistroMatrizRiesgoToAttach = em.getReference(registroMatrizRiesgoListNewRegistroMatrizRiesgoToAttach.getClass(), registroMatrizRiesgoListNewRegistroMatrizRiesgoToAttach.getId());
                attachedRegistroMatrizRiesgoListNew.add(registroMatrizRiesgoListNewRegistroMatrizRiesgoToAttach);
            }
            registroMatrizRiesgoListNew = attachedRegistroMatrizRiesgoListNew;
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList(registroMatrizRiesgoListNew);
            List<RegistroMatrizRiesgo> attachedRegistroMatrizRiesgoList1New = new ArrayList<RegistroMatrizRiesgo>();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList1NewRegistroMatrizRiesgoToAttach : registroMatrizRiesgoList1New) {
                registroMatrizRiesgoList1NewRegistroMatrizRiesgoToAttach = em.getReference(registroMatrizRiesgoList1NewRegistroMatrizRiesgoToAttach.getClass(), registroMatrizRiesgoList1NewRegistroMatrizRiesgoToAttach.getId());
                attachedRegistroMatrizRiesgoList1New.add(registroMatrizRiesgoList1NewRegistroMatrizRiesgoToAttach);
            }
            registroMatrizRiesgoList1New = attachedRegistroMatrizRiesgoList1New;
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList1(registroMatrizRiesgoList1New);
            List<RegistroMatrizRiesgo> attachedRegistroMatrizRiesgoList2New = new ArrayList<RegistroMatrizRiesgo>();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList2NewRegistroMatrizRiesgoToAttach : registroMatrizRiesgoList2New) {
                registroMatrizRiesgoList2NewRegistroMatrizRiesgoToAttach = em.getReference(registroMatrizRiesgoList2NewRegistroMatrizRiesgoToAttach.getClass(), registroMatrizRiesgoList2NewRegistroMatrizRiesgoToAttach.getId());
                attachedRegistroMatrizRiesgoList2New.add(registroMatrizRiesgoList2NewRegistroMatrizRiesgoToAttach);
            }
            registroMatrizRiesgoList2New = attachedRegistroMatrizRiesgoList2New;
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList2(registroMatrizRiesgoList2New);
            List<RegistroMatrizRiesgo> attachedRegistroMatrizRiesgoList3New = new ArrayList<RegistroMatrizRiesgo>();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList3NewRegistroMatrizRiesgoToAttach : registroMatrizRiesgoList3New) {
                registroMatrizRiesgoList3NewRegistroMatrizRiesgoToAttach = em.getReference(registroMatrizRiesgoList3NewRegistroMatrizRiesgoToAttach.getClass(), registroMatrizRiesgoList3NewRegistroMatrizRiesgoToAttach.getId());
                attachedRegistroMatrizRiesgoList3New.add(registroMatrizRiesgoList3NewRegistroMatrizRiesgoToAttach);
            }
            registroMatrizRiesgoList3New = attachedRegistroMatrizRiesgoList3New;
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList3(registroMatrizRiesgoList3New);
            List<RegistroMatrizRiesgo> attachedRegistroMatrizRiesgoList4New = new ArrayList<RegistroMatrizRiesgo>();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList4NewRegistroMatrizRiesgoToAttach : registroMatrizRiesgoList4New) {
                registroMatrizRiesgoList4NewRegistroMatrizRiesgoToAttach = em.getReference(registroMatrizRiesgoList4NewRegistroMatrizRiesgoToAttach.getClass(), registroMatrizRiesgoList4NewRegistroMatrizRiesgoToAttach.getId());
                attachedRegistroMatrizRiesgoList4New.add(registroMatrizRiesgoList4NewRegistroMatrizRiesgoToAttach);
            }
            registroMatrizRiesgoList4New = attachedRegistroMatrizRiesgoList4New;
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList4(registroMatrizRiesgoList4New);
            List<RegistroMatrizRiesgo> attachedRegistroMatrizRiesgoList5New = new ArrayList<RegistroMatrizRiesgo>();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList5NewRegistroMatrizRiesgoToAttach : registroMatrizRiesgoList5New) {
                registroMatrizRiesgoList5NewRegistroMatrizRiesgoToAttach = em.getReference(registroMatrizRiesgoList5NewRegistroMatrizRiesgoToAttach.getClass(), registroMatrizRiesgoList5NewRegistroMatrizRiesgoToAttach.getId());
                attachedRegistroMatrizRiesgoList5New.add(registroMatrizRiesgoList5NewRegistroMatrizRiesgoToAttach);
            }
            registroMatrizRiesgoList5New = attachedRegistroMatrizRiesgoList5New;
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList5(registroMatrizRiesgoList5New);
            List<RegistroMatrizRiesgo> attachedRegistroMatrizRiesgoList6New = new ArrayList<RegistroMatrizRiesgo>();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList6NewRegistroMatrizRiesgoToAttach : registroMatrizRiesgoList6New) {
                registroMatrizRiesgoList6NewRegistroMatrizRiesgoToAttach = em.getReference(registroMatrizRiesgoList6NewRegistroMatrizRiesgoToAttach.getClass(), registroMatrizRiesgoList6NewRegistroMatrizRiesgoToAttach.getId());
                attachedRegistroMatrizRiesgoList6New.add(registroMatrizRiesgoList6NewRegistroMatrizRiesgoToAttach);
            }
            registroMatrizRiesgoList6New = attachedRegistroMatrizRiesgoList6New;
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList6(registroMatrizRiesgoList6New);
            List<RegistroMatrizRiesgo> attachedRegistroMatrizRiesgoList7New = new ArrayList<RegistroMatrizRiesgo>();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList7NewRegistroMatrizRiesgoToAttach : registroMatrizRiesgoList7New) {
                registroMatrizRiesgoList7NewRegistroMatrizRiesgoToAttach = em.getReference(registroMatrizRiesgoList7NewRegistroMatrizRiesgoToAttach.getClass(), registroMatrizRiesgoList7NewRegistroMatrizRiesgoToAttach.getId());
                attachedRegistroMatrizRiesgoList7New.add(registroMatrizRiesgoList7NewRegistroMatrizRiesgoToAttach);
            }
            registroMatrizRiesgoList7New = attachedRegistroMatrizRiesgoList7New;
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList7(registroMatrizRiesgoList7New);
            List<RegistroMatrizRiesgo> attachedRegistroMatrizRiesgoList8New = new ArrayList<RegistroMatrizRiesgo>();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList8NewRegistroMatrizRiesgoToAttach : registroMatrizRiesgoList8New) {
                registroMatrizRiesgoList8NewRegistroMatrizRiesgoToAttach = em.getReference(registroMatrizRiesgoList8NewRegistroMatrizRiesgoToAttach.getClass(), registroMatrizRiesgoList8NewRegistroMatrizRiesgoToAttach.getId());
                attachedRegistroMatrizRiesgoList8New.add(registroMatrizRiesgoList8NewRegistroMatrizRiesgoToAttach);
            }
            registroMatrizRiesgoList8New = attachedRegistroMatrizRiesgoList8New;
            subtipoDatoMatrizRiesgo.setRegistroMatrizRiesgoList8(registroMatrizRiesgoList8New);
            subtipoDatoMatrizRiesgo = em.merge(subtipoDatoMatrizRiesgo);
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getSubtipoDatoMatrizRiesgoList().remove(subtipoDatoMatrizRiesgo);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getSubtipoDatoMatrizRiesgoList().add(subtipoDatoMatrizRiesgo);
                idTipoNew = em.merge(idTipoNew);
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoListOldRegistroMatrizRiesgo : registroMatrizRiesgoListOld) {
                if (!registroMatrizRiesgoListNew.contains(registroMatrizRiesgoListOldRegistroMatrizRiesgo)) {
                    registroMatrizRiesgoListOldRegistroMatrizRiesgo.setIdTipo(null);
                    registroMatrizRiesgoListOldRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoListOldRegistroMatrizRiesgo);
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoListNewRegistroMatrizRiesgo : registroMatrizRiesgoListNew) {
                if (!registroMatrizRiesgoListOld.contains(registroMatrizRiesgoListNewRegistroMatrizRiesgo)) {
                    SubtipoDatoMatrizRiesgo oldIdTipoOfRegistroMatrizRiesgoListNewRegistroMatrizRiesgo = registroMatrizRiesgoListNewRegistroMatrizRiesgo.getIdTipo();
                    registroMatrizRiesgoListNewRegistroMatrizRiesgo.setIdTipo(subtipoDatoMatrizRiesgo);
                    registroMatrizRiesgoListNewRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoListNewRegistroMatrizRiesgo);
                    if (oldIdTipoOfRegistroMatrizRiesgoListNewRegistroMatrizRiesgo != null && !oldIdTipoOfRegistroMatrizRiesgoListNewRegistroMatrizRiesgo.equals(subtipoDatoMatrizRiesgo)) {
                        oldIdTipoOfRegistroMatrizRiesgoListNewRegistroMatrizRiesgo.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgoListNewRegistroMatrizRiesgo);
                        oldIdTipoOfRegistroMatrizRiesgoListNewRegistroMatrizRiesgo = em.merge(oldIdTipoOfRegistroMatrizRiesgoListNewRegistroMatrizRiesgo);
                    }
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList1OldRegistroMatrizRiesgo : registroMatrizRiesgoList1Old) {
                if (!registroMatrizRiesgoList1New.contains(registroMatrizRiesgoList1OldRegistroMatrizRiesgo)) {
                    registroMatrizRiesgoList1OldRegistroMatrizRiesgo.setIdProbabilidadTratamiento(null);
                    registroMatrizRiesgoList1OldRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList1OldRegistroMatrizRiesgo);
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList1NewRegistroMatrizRiesgo : registroMatrizRiesgoList1New) {
                if (!registroMatrizRiesgoList1Old.contains(registroMatrizRiesgoList1NewRegistroMatrizRiesgo)) {
                    SubtipoDatoMatrizRiesgo oldIdProbabilidadTratamientoOfRegistroMatrizRiesgoList1NewRegistroMatrizRiesgo = registroMatrizRiesgoList1NewRegistroMatrizRiesgo.getIdProbabilidadTratamiento();
                    registroMatrizRiesgoList1NewRegistroMatrizRiesgo.setIdProbabilidadTratamiento(subtipoDatoMatrizRiesgo);
                    registroMatrizRiesgoList1NewRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList1NewRegistroMatrizRiesgo);
                    if (oldIdProbabilidadTratamientoOfRegistroMatrizRiesgoList1NewRegistroMatrizRiesgo != null && !oldIdProbabilidadTratamientoOfRegistroMatrizRiesgoList1NewRegistroMatrizRiesgo.equals(subtipoDatoMatrizRiesgo)) {
                        oldIdProbabilidadTratamientoOfRegistroMatrizRiesgoList1NewRegistroMatrizRiesgo.getRegistroMatrizRiesgoList1().remove(registroMatrizRiesgoList1NewRegistroMatrizRiesgo);
                        oldIdProbabilidadTratamientoOfRegistroMatrizRiesgoList1NewRegistroMatrizRiesgo = em.merge(oldIdProbabilidadTratamientoOfRegistroMatrizRiesgoList1NewRegistroMatrizRiesgo);
                    }
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList2OldRegistroMatrizRiesgo : registroMatrizRiesgoList2Old) {
                if (!registroMatrizRiesgoList2New.contains(registroMatrizRiesgoList2OldRegistroMatrizRiesgo)) {
                    registroMatrizRiesgoList2OldRegistroMatrizRiesgo.setIdProbabilidad(null);
                    registroMatrizRiesgoList2OldRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList2OldRegistroMatrizRiesgo);
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList2NewRegistroMatrizRiesgo : registroMatrizRiesgoList2New) {
                if (!registroMatrizRiesgoList2Old.contains(registroMatrizRiesgoList2NewRegistroMatrizRiesgo)) {
                    SubtipoDatoMatrizRiesgo oldIdProbabilidadOfRegistroMatrizRiesgoList2NewRegistroMatrizRiesgo = registroMatrizRiesgoList2NewRegistroMatrizRiesgo.getIdProbabilidad();
                    registroMatrizRiesgoList2NewRegistroMatrizRiesgo.setIdProbabilidad(subtipoDatoMatrizRiesgo);
                    registroMatrizRiesgoList2NewRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList2NewRegistroMatrizRiesgo);
                    if (oldIdProbabilidadOfRegistroMatrizRiesgoList2NewRegistroMatrizRiesgo != null && !oldIdProbabilidadOfRegistroMatrizRiesgoList2NewRegistroMatrizRiesgo.equals(subtipoDatoMatrizRiesgo)) {
                        oldIdProbabilidadOfRegistroMatrizRiesgoList2NewRegistroMatrizRiesgo.getRegistroMatrizRiesgoList2().remove(registroMatrizRiesgoList2NewRegistroMatrizRiesgo);
                        oldIdProbabilidadOfRegistroMatrizRiesgoList2NewRegistroMatrizRiesgo = em.merge(oldIdProbabilidadOfRegistroMatrizRiesgoList2NewRegistroMatrizRiesgo);
                    }
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList3OldRegistroMatrizRiesgo : registroMatrizRiesgoList3Old) {
                if (!registroMatrizRiesgoList3New.contains(registroMatrizRiesgoList3OldRegistroMatrizRiesgo)) {
                    registroMatrizRiesgoList3OldRegistroMatrizRiesgo.setIdMonitoreo(null);
                    registroMatrizRiesgoList3OldRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList3OldRegistroMatrizRiesgo);
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList3NewRegistroMatrizRiesgo : registroMatrizRiesgoList3New) {
                if (!registroMatrizRiesgoList3Old.contains(registroMatrizRiesgoList3NewRegistroMatrizRiesgo)) {
                    SubtipoDatoMatrizRiesgo oldIdMonitoreoOfRegistroMatrizRiesgoList3NewRegistroMatrizRiesgo = registroMatrizRiesgoList3NewRegistroMatrizRiesgo.getIdMonitoreo();
                    registroMatrizRiesgoList3NewRegistroMatrizRiesgo.setIdMonitoreo(subtipoDatoMatrizRiesgo);
                    registroMatrizRiesgoList3NewRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList3NewRegistroMatrizRiesgo);
                    if (oldIdMonitoreoOfRegistroMatrizRiesgoList3NewRegistroMatrizRiesgo != null && !oldIdMonitoreoOfRegistroMatrizRiesgoList3NewRegistroMatrizRiesgo.equals(subtipoDatoMatrizRiesgo)) {
                        oldIdMonitoreoOfRegistroMatrizRiesgoList3NewRegistroMatrizRiesgo.getRegistroMatrizRiesgoList3().remove(registroMatrizRiesgoList3NewRegistroMatrizRiesgo);
                        oldIdMonitoreoOfRegistroMatrizRiesgoList3NewRegistroMatrizRiesgo = em.merge(oldIdMonitoreoOfRegistroMatrizRiesgoList3NewRegistroMatrizRiesgo);
                    }
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList4OldRegistroMatrizRiesgo : registroMatrizRiesgoList4Old) {
                if (!registroMatrizRiesgoList4New.contains(registroMatrizRiesgoList4OldRegistroMatrizRiesgo)) {
                    registroMatrizRiesgoList4OldRegistroMatrizRiesgo.setIdFuente(null);
                    registroMatrizRiesgoList4OldRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList4OldRegistroMatrizRiesgo);
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList4NewRegistroMatrizRiesgo : registroMatrizRiesgoList4New) {
                if (!registroMatrizRiesgoList4Old.contains(registroMatrizRiesgoList4NewRegistroMatrizRiesgo)) {
                    SubtipoDatoMatrizRiesgo oldIdFuenteOfRegistroMatrizRiesgoList4NewRegistroMatrizRiesgo = registroMatrizRiesgoList4NewRegistroMatrizRiesgo.getIdFuente();
                    registroMatrizRiesgoList4NewRegistroMatrizRiesgo.setIdFuente(subtipoDatoMatrizRiesgo);
                    registroMatrizRiesgoList4NewRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList4NewRegistroMatrizRiesgo);
                    if (oldIdFuenteOfRegistroMatrizRiesgoList4NewRegistroMatrizRiesgo != null && !oldIdFuenteOfRegistroMatrizRiesgoList4NewRegistroMatrizRiesgo.equals(subtipoDatoMatrizRiesgo)) {
                        oldIdFuenteOfRegistroMatrizRiesgoList4NewRegistroMatrizRiesgo.getRegistroMatrizRiesgoList4().remove(registroMatrizRiesgoList4NewRegistroMatrizRiesgo);
                        oldIdFuenteOfRegistroMatrizRiesgoList4NewRegistroMatrizRiesgo = em.merge(oldIdFuenteOfRegistroMatrizRiesgoList4NewRegistroMatrizRiesgo);
                    }
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList5OldRegistroMatrizRiesgo : registroMatrizRiesgoList5Old) {
                if (!registroMatrizRiesgoList5New.contains(registroMatrizRiesgoList5OldRegistroMatrizRiesgo)) {
                    registroMatrizRiesgoList5OldRegistroMatrizRiesgo.setIdEtapa(null);
                    registroMatrizRiesgoList5OldRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList5OldRegistroMatrizRiesgo);
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList5NewRegistroMatrizRiesgo : registroMatrizRiesgoList5New) {
                if (!registroMatrizRiesgoList5Old.contains(registroMatrizRiesgoList5NewRegistroMatrizRiesgo)) {
                    SubtipoDatoMatrizRiesgo oldIdEtapaOfRegistroMatrizRiesgoList5NewRegistroMatrizRiesgo = registroMatrizRiesgoList5NewRegistroMatrizRiesgo.getIdEtapa();
                    registroMatrizRiesgoList5NewRegistroMatrizRiesgo.setIdEtapa(subtipoDatoMatrizRiesgo);
                    registroMatrizRiesgoList5NewRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList5NewRegistroMatrizRiesgo);
                    if (oldIdEtapaOfRegistroMatrizRiesgoList5NewRegistroMatrizRiesgo != null && !oldIdEtapaOfRegistroMatrizRiesgoList5NewRegistroMatrizRiesgo.equals(subtipoDatoMatrizRiesgo)) {
                        oldIdEtapaOfRegistroMatrizRiesgoList5NewRegistroMatrizRiesgo.getRegistroMatrizRiesgoList5().remove(registroMatrizRiesgoList5NewRegistroMatrizRiesgo);
                        oldIdEtapaOfRegistroMatrizRiesgoList5NewRegistroMatrizRiesgo = em.merge(oldIdEtapaOfRegistroMatrizRiesgoList5NewRegistroMatrizRiesgo);
                    }
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList6OldRegistroMatrizRiesgo : registroMatrizRiesgoList6Old) {
                if (!registroMatrizRiesgoList6New.contains(registroMatrizRiesgoList6OldRegistroMatrizRiesgo)) {
                    registroMatrizRiesgoList6OldRegistroMatrizRiesgo.setIdClase(null);
                    registroMatrizRiesgoList6OldRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList6OldRegistroMatrizRiesgo);
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList6NewRegistroMatrizRiesgo : registroMatrizRiesgoList6New) {
                if (!registroMatrizRiesgoList6Old.contains(registroMatrizRiesgoList6NewRegistroMatrizRiesgo)) {
                    SubtipoDatoMatrizRiesgo oldIdClaseOfRegistroMatrizRiesgoList6NewRegistroMatrizRiesgo = registroMatrizRiesgoList6NewRegistroMatrizRiesgo.getIdClase();
                    registroMatrizRiesgoList6NewRegistroMatrizRiesgo.setIdClase(subtipoDatoMatrizRiesgo);
                    registroMatrizRiesgoList6NewRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList6NewRegistroMatrizRiesgo);
                    if (oldIdClaseOfRegistroMatrizRiesgoList6NewRegistroMatrizRiesgo != null && !oldIdClaseOfRegistroMatrizRiesgoList6NewRegistroMatrizRiesgo.equals(subtipoDatoMatrizRiesgo)) {
                        oldIdClaseOfRegistroMatrizRiesgoList6NewRegistroMatrizRiesgo.getRegistroMatrizRiesgoList6().remove(registroMatrizRiesgoList6NewRegistroMatrizRiesgo);
                        oldIdClaseOfRegistroMatrizRiesgoList6NewRegistroMatrizRiesgo = em.merge(oldIdClaseOfRegistroMatrizRiesgoList6NewRegistroMatrizRiesgo);
                    }
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList7OldRegistroMatrizRiesgo : registroMatrizRiesgoList7Old) {
                if (!registroMatrizRiesgoList7New.contains(registroMatrizRiesgoList7OldRegistroMatrizRiesgo)) {
                    registroMatrizRiesgoList7OldRegistroMatrizRiesgo.setIdCategoriaTratamiento(null);
                    registroMatrizRiesgoList7OldRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList7OldRegistroMatrizRiesgo);
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList7NewRegistroMatrizRiesgo : registroMatrizRiesgoList7New) {
                if (!registroMatrizRiesgoList7Old.contains(registroMatrizRiesgoList7NewRegistroMatrizRiesgo)) {
                    SubtipoDatoMatrizRiesgo oldIdCategoriaTratamientoOfRegistroMatrizRiesgoList7NewRegistroMatrizRiesgo = registroMatrizRiesgoList7NewRegistroMatrizRiesgo.getIdCategoriaTratamiento();
                    registroMatrizRiesgoList7NewRegistroMatrizRiesgo.setIdCategoriaTratamiento(subtipoDatoMatrizRiesgo);
                    registroMatrizRiesgoList7NewRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList7NewRegistroMatrizRiesgo);
                    if (oldIdCategoriaTratamientoOfRegistroMatrizRiesgoList7NewRegistroMatrizRiesgo != null && !oldIdCategoriaTratamientoOfRegistroMatrizRiesgoList7NewRegistroMatrizRiesgo.equals(subtipoDatoMatrizRiesgo)) {
                        oldIdCategoriaTratamientoOfRegistroMatrizRiesgoList7NewRegistroMatrizRiesgo.getRegistroMatrizRiesgoList7().remove(registroMatrizRiesgoList7NewRegistroMatrizRiesgo);
                        oldIdCategoriaTratamientoOfRegistroMatrizRiesgoList7NewRegistroMatrizRiesgo = em.merge(oldIdCategoriaTratamientoOfRegistroMatrizRiesgoList7NewRegistroMatrizRiesgo);
                    }
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList8OldRegistroMatrizRiesgo : registroMatrizRiesgoList8Old) {
                if (!registroMatrizRiesgoList8New.contains(registroMatrizRiesgoList8OldRegistroMatrizRiesgo)) {
                    registroMatrizRiesgoList8OldRegistroMatrizRiesgo.setIdCategoria(null);
                    registroMatrizRiesgoList8OldRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList8OldRegistroMatrizRiesgo);
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoList8NewRegistroMatrizRiesgo : registroMatrizRiesgoList8New) {
                if (!registroMatrizRiesgoList8Old.contains(registroMatrizRiesgoList8NewRegistroMatrizRiesgo)) {
                    SubtipoDatoMatrizRiesgo oldIdCategoriaOfRegistroMatrizRiesgoList8NewRegistroMatrizRiesgo = registroMatrizRiesgoList8NewRegistroMatrizRiesgo.getIdCategoria();
                    registroMatrizRiesgoList8NewRegistroMatrizRiesgo.setIdCategoria(subtipoDatoMatrizRiesgo);
                    registroMatrizRiesgoList8NewRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList8NewRegistroMatrizRiesgo);
                    if (oldIdCategoriaOfRegistroMatrizRiesgoList8NewRegistroMatrizRiesgo != null && !oldIdCategoriaOfRegistroMatrizRiesgoList8NewRegistroMatrizRiesgo.equals(subtipoDatoMatrizRiesgo)) {
                        oldIdCategoriaOfRegistroMatrizRiesgoList8NewRegistroMatrizRiesgo.getRegistroMatrizRiesgoList8().remove(registroMatrizRiesgoList8NewRegistroMatrizRiesgo);
                        oldIdCategoriaOfRegistroMatrizRiesgoList8NewRegistroMatrizRiesgo = em.merge(oldIdCategoriaOfRegistroMatrizRiesgoList8NewRegistroMatrizRiesgo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = subtipoDatoMatrizRiesgo.getId();
                if (findSubtipoDatoMatrizRiesgo(id) == null) {
                    throw new NonexistentEntityException("The subtipoDatoMatrizRiesgo with id " + id + " no longer exists.");
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
            SubtipoDatoMatrizRiesgo subtipoDatoMatrizRiesgo;
            try {
                subtipoDatoMatrizRiesgo = em.getReference(SubtipoDatoMatrizRiesgo.class, id);
                subtipoDatoMatrizRiesgo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subtipoDatoMatrizRiesgo with id " + id + " no longer exists.", enfe);
            }
            TipoDatoMatrizRiesgo idTipo = subtipoDatoMatrizRiesgo.getIdTipo();
            if (idTipo != null) {
                idTipo.getSubtipoDatoMatrizRiesgoList().remove(subtipoDatoMatrizRiesgo);
                idTipo = em.merge(idTipo);
            }
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList = subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList();
            for (RegistroMatrizRiesgo registroMatrizRiesgoListRegistroMatrizRiesgo : registroMatrizRiesgoList) {
                registroMatrizRiesgoListRegistroMatrizRiesgo.setIdTipo(null);
                registroMatrizRiesgoListRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoListRegistroMatrizRiesgo);
            }
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList1 = subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList1();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList1RegistroMatrizRiesgo : registroMatrizRiesgoList1) {
                registroMatrizRiesgoList1RegistroMatrizRiesgo.setIdProbabilidadTratamiento(null);
                registroMatrizRiesgoList1RegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList1RegistroMatrizRiesgo);
            }
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList2 = subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList2();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList2RegistroMatrizRiesgo : registroMatrizRiesgoList2) {
                registroMatrizRiesgoList2RegistroMatrizRiesgo.setIdProbabilidad(null);
                registroMatrizRiesgoList2RegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList2RegistroMatrizRiesgo);
            }
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList3 = subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList3();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList3RegistroMatrizRiesgo : registroMatrizRiesgoList3) {
                registroMatrizRiesgoList3RegistroMatrizRiesgo.setIdMonitoreo(null);
                registroMatrizRiesgoList3RegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList3RegistroMatrizRiesgo);
            }
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList4 = subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList4();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList4RegistroMatrizRiesgo : registroMatrizRiesgoList4) {
                registroMatrizRiesgoList4RegistroMatrizRiesgo.setIdFuente(null);
                registroMatrizRiesgoList4RegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList4RegistroMatrizRiesgo);
            }
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList5 = subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList5();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList5RegistroMatrizRiesgo : registroMatrizRiesgoList5) {
                registroMatrizRiesgoList5RegistroMatrizRiesgo.setIdEtapa(null);
                registroMatrizRiesgoList5RegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList5RegistroMatrizRiesgo);
            }
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList6 = subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList6();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList6RegistroMatrizRiesgo : registroMatrizRiesgoList6) {
                registroMatrizRiesgoList6RegistroMatrizRiesgo.setIdClase(null);
                registroMatrizRiesgoList6RegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList6RegistroMatrizRiesgo);
            }
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList7 = subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList7();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList7RegistroMatrizRiesgo : registroMatrizRiesgoList7) {
                registroMatrizRiesgoList7RegistroMatrizRiesgo.setIdCategoriaTratamiento(null);
                registroMatrizRiesgoList7RegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList7RegistroMatrizRiesgo);
            }
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList8 = subtipoDatoMatrizRiesgo.getRegistroMatrizRiesgoList8();
            for (RegistroMatrizRiesgo registroMatrizRiesgoList8RegistroMatrizRiesgo : registroMatrizRiesgoList8) {
                registroMatrizRiesgoList8RegistroMatrizRiesgo.setIdCategoria(null);
                registroMatrizRiesgoList8RegistroMatrizRiesgo = em.merge(registroMatrizRiesgoList8RegistroMatrizRiesgo);
            }
            em.remove(subtipoDatoMatrizRiesgo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SubtipoDatoMatrizRiesgo> findSubtipoDatoMatrizRiesgoEntities() {
        return findSubtipoDatoMatrizRiesgoEntities(true, -1, -1);
    }

    public List<SubtipoDatoMatrizRiesgo> findSubtipoDatoMatrizRiesgoEntities(int maxResults, int firstResult) {
        return findSubtipoDatoMatrizRiesgoEntities(false, maxResults, firstResult);
    }

    private List<SubtipoDatoMatrizRiesgo> findSubtipoDatoMatrizRiesgoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SubtipoDatoMatrizRiesgo.class));
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

    public SubtipoDatoMatrizRiesgo findSubtipoDatoMatrizRiesgo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SubtipoDatoMatrizRiesgo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubtipoDatoMatrizRiesgoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SubtipoDatoMatrizRiesgo> rt = cq.from(SubtipoDatoMatrizRiesgo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
