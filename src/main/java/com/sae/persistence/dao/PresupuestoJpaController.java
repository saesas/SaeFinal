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
import com.sae.persistence.domain.ListaPrecio;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.AiuPresupuesto;
import com.sae.persistence.domain.ActaCobro;
import com.sae.persistence.domain.AiuEspecificoPresupuesto;
import com.sae.persistence.domain.ActividadPresupuesto;
import com.sae.persistence.domain.Presupuesto;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class PresupuestoJpaController implements Serializable {

    public PresupuestoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Presupuesto presupuesto) throws PreexistingEntityException, Exception {
        if (presupuesto.getListaPrecioList() == null) {
            presupuesto.setListaPrecioList(new ArrayList<ListaPrecio>());
        }
        if (presupuesto.getAiuPresupuestoList() == null) {
            presupuesto.setAiuPresupuestoList(new ArrayList<AiuPresupuesto>());
        }
        if (presupuesto.getActaCobroList() == null) {
            presupuesto.setActaCobroList(new ArrayList<ActaCobro>());
        }
        if (presupuesto.getAiuEspecificoPresupuestoList() == null) {
            presupuesto.setAiuEspecificoPresupuestoList(new ArrayList<AiuEspecificoPresupuesto>());
        }
        if (presupuesto.getActividadPresupuestoList() == null) {
            presupuesto.setActividadPresupuestoList(new ArrayList<ActividadPresupuesto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ListaPrecio> attachedListaPrecioList = new ArrayList<ListaPrecio>();
            for (ListaPrecio listaPrecioListListaPrecioToAttach : presupuesto.getListaPrecioList()) {
                listaPrecioListListaPrecioToAttach = em.getReference(listaPrecioListListaPrecioToAttach.getClass(), listaPrecioListListaPrecioToAttach.getId());
                attachedListaPrecioList.add(listaPrecioListListaPrecioToAttach);
            }
            presupuesto.setListaPrecioList(attachedListaPrecioList);
            List<AiuPresupuesto> attachedAiuPresupuestoList = new ArrayList<AiuPresupuesto>();
            for (AiuPresupuesto aiuPresupuestoListAiuPresupuestoToAttach : presupuesto.getAiuPresupuestoList()) {
                aiuPresupuestoListAiuPresupuestoToAttach = em.getReference(aiuPresupuestoListAiuPresupuestoToAttach.getClass(), aiuPresupuestoListAiuPresupuestoToAttach.getId());
                attachedAiuPresupuestoList.add(aiuPresupuestoListAiuPresupuestoToAttach);
            }
            presupuesto.setAiuPresupuestoList(attachedAiuPresupuestoList);
            List<ActaCobro> attachedActaCobroList = new ArrayList<ActaCobro>();
            for (ActaCobro actaCobroListActaCobroToAttach : presupuesto.getActaCobroList()) {
                actaCobroListActaCobroToAttach = em.getReference(actaCobroListActaCobroToAttach.getClass(), actaCobroListActaCobroToAttach.getId());
                attachedActaCobroList.add(actaCobroListActaCobroToAttach);
            }
            presupuesto.setActaCobroList(attachedActaCobroList);
            List<AiuEspecificoPresupuesto> attachedAiuEspecificoPresupuestoList = new ArrayList<AiuEspecificoPresupuesto>();
            for (AiuEspecificoPresupuesto aiuEspecificoPresupuestoListAiuEspecificoPresupuestoToAttach : presupuesto.getAiuEspecificoPresupuestoList()) {
                aiuEspecificoPresupuestoListAiuEspecificoPresupuestoToAttach = em.getReference(aiuEspecificoPresupuestoListAiuEspecificoPresupuestoToAttach.getClass(), aiuEspecificoPresupuestoListAiuEspecificoPresupuestoToAttach.getId());
                attachedAiuEspecificoPresupuestoList.add(aiuEspecificoPresupuestoListAiuEspecificoPresupuestoToAttach);
            }
            presupuesto.setAiuEspecificoPresupuestoList(attachedAiuEspecificoPresupuestoList);
            List<ActividadPresupuesto> attachedActividadPresupuestoList = new ArrayList<ActividadPresupuesto>();
            for (ActividadPresupuesto actividadPresupuestoListActividadPresupuestoToAttach : presupuesto.getActividadPresupuestoList()) {
                actividadPresupuestoListActividadPresupuestoToAttach = em.getReference(actividadPresupuestoListActividadPresupuestoToAttach.getClass(), actividadPresupuestoListActividadPresupuestoToAttach.getId());
                attachedActividadPresupuestoList.add(actividadPresupuestoListActividadPresupuestoToAttach);
            }
            presupuesto.setActividadPresupuestoList(attachedActividadPresupuestoList);
            em.persist(presupuesto);
            for (ListaPrecio listaPrecioListListaPrecio : presupuesto.getListaPrecioList()) {
                Presupuesto oldIdPresupuestoOfListaPrecioListListaPrecio = listaPrecioListListaPrecio.getIdPresupuesto();
                listaPrecioListListaPrecio.setIdPresupuesto(presupuesto);
                listaPrecioListListaPrecio = em.merge(listaPrecioListListaPrecio);
                if (oldIdPresupuestoOfListaPrecioListListaPrecio != null) {
                    oldIdPresupuestoOfListaPrecioListListaPrecio.getListaPrecioList().remove(listaPrecioListListaPrecio);
                    oldIdPresupuestoOfListaPrecioListListaPrecio = em.merge(oldIdPresupuestoOfListaPrecioListListaPrecio);
                }
            }
            for (AiuPresupuesto aiuPresupuestoListAiuPresupuesto : presupuesto.getAiuPresupuestoList()) {
                Presupuesto oldIdPresupuestoOfAiuPresupuestoListAiuPresupuesto = aiuPresupuestoListAiuPresupuesto.getIdPresupuesto();
                aiuPresupuestoListAiuPresupuesto.setIdPresupuesto(presupuesto);
                aiuPresupuestoListAiuPresupuesto = em.merge(aiuPresupuestoListAiuPresupuesto);
                if (oldIdPresupuestoOfAiuPresupuestoListAiuPresupuesto != null) {
                    oldIdPresupuestoOfAiuPresupuestoListAiuPresupuesto.getAiuPresupuestoList().remove(aiuPresupuestoListAiuPresupuesto);
                    oldIdPresupuestoOfAiuPresupuestoListAiuPresupuesto = em.merge(oldIdPresupuestoOfAiuPresupuestoListAiuPresupuesto);
                }
            }
            for (ActaCobro actaCobroListActaCobro : presupuesto.getActaCobroList()) {
                Presupuesto oldIdPresupuestoOfActaCobroListActaCobro = actaCobroListActaCobro.getIdPresupuesto();
                actaCobroListActaCobro.setIdPresupuesto(presupuesto);
                actaCobroListActaCobro = em.merge(actaCobroListActaCobro);
                if (oldIdPresupuestoOfActaCobroListActaCobro != null) {
                    oldIdPresupuestoOfActaCobroListActaCobro.getActaCobroList().remove(actaCobroListActaCobro);
                    oldIdPresupuestoOfActaCobroListActaCobro = em.merge(oldIdPresupuestoOfActaCobroListActaCobro);
                }
            }
            for (AiuEspecificoPresupuesto aiuEspecificoPresupuestoListAiuEspecificoPresupuesto : presupuesto.getAiuEspecificoPresupuestoList()) {
                Presupuesto oldIdPresupuestoOfAiuEspecificoPresupuestoListAiuEspecificoPresupuesto = aiuEspecificoPresupuestoListAiuEspecificoPresupuesto.getIdPresupuesto();
                aiuEspecificoPresupuestoListAiuEspecificoPresupuesto.setIdPresupuesto(presupuesto);
                aiuEspecificoPresupuestoListAiuEspecificoPresupuesto = em.merge(aiuEspecificoPresupuestoListAiuEspecificoPresupuesto);
                if (oldIdPresupuestoOfAiuEspecificoPresupuestoListAiuEspecificoPresupuesto != null) {
                    oldIdPresupuestoOfAiuEspecificoPresupuestoListAiuEspecificoPresupuesto.getAiuEspecificoPresupuestoList().remove(aiuEspecificoPresupuestoListAiuEspecificoPresupuesto);
                    oldIdPresupuestoOfAiuEspecificoPresupuestoListAiuEspecificoPresupuesto = em.merge(oldIdPresupuestoOfAiuEspecificoPresupuestoListAiuEspecificoPresupuesto);
                }
            }
            for (ActividadPresupuesto actividadPresupuestoListActividadPresupuesto : presupuesto.getActividadPresupuestoList()) {
                Presupuesto oldIdPresupuestoOfActividadPresupuestoListActividadPresupuesto = actividadPresupuestoListActividadPresupuesto.getIdPresupuesto();
                actividadPresupuestoListActividadPresupuesto.setIdPresupuesto(presupuesto);
                actividadPresupuestoListActividadPresupuesto = em.merge(actividadPresupuestoListActividadPresupuesto);
                if (oldIdPresupuestoOfActividadPresupuestoListActividadPresupuesto != null) {
                    oldIdPresupuestoOfActividadPresupuestoListActividadPresupuesto.getActividadPresupuestoList().remove(actividadPresupuestoListActividadPresupuesto);
                    oldIdPresupuestoOfActividadPresupuestoListActividadPresupuesto = em.merge(oldIdPresupuestoOfActividadPresupuestoListActividadPresupuesto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPresupuesto(presupuesto.getId()) != null) {
                throw new PreexistingEntityException("Presupuesto " + presupuesto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Presupuesto presupuesto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Presupuesto persistentPresupuesto = em.find(Presupuesto.class, presupuesto.getId());
            List<ListaPrecio> listaPrecioListOld = persistentPresupuesto.getListaPrecioList();
            List<ListaPrecio> listaPrecioListNew = presupuesto.getListaPrecioList();
            List<AiuPresupuesto> aiuPresupuestoListOld = persistentPresupuesto.getAiuPresupuestoList();
            List<AiuPresupuesto> aiuPresupuestoListNew = presupuesto.getAiuPresupuestoList();
            List<ActaCobro> actaCobroListOld = persistentPresupuesto.getActaCobroList();
            List<ActaCobro> actaCobroListNew = presupuesto.getActaCobroList();
            List<AiuEspecificoPresupuesto> aiuEspecificoPresupuestoListOld = persistentPresupuesto.getAiuEspecificoPresupuestoList();
            List<AiuEspecificoPresupuesto> aiuEspecificoPresupuestoListNew = presupuesto.getAiuEspecificoPresupuestoList();
            List<ActividadPresupuesto> actividadPresupuestoListOld = persistentPresupuesto.getActividadPresupuestoList();
            List<ActividadPresupuesto> actividadPresupuestoListNew = presupuesto.getActividadPresupuestoList();
            List<ListaPrecio> attachedListaPrecioListNew = new ArrayList<ListaPrecio>();
            for (ListaPrecio listaPrecioListNewListaPrecioToAttach : listaPrecioListNew) {
                listaPrecioListNewListaPrecioToAttach = em.getReference(listaPrecioListNewListaPrecioToAttach.getClass(), listaPrecioListNewListaPrecioToAttach.getId());
                attachedListaPrecioListNew.add(listaPrecioListNewListaPrecioToAttach);
            }
            listaPrecioListNew = attachedListaPrecioListNew;
            presupuesto.setListaPrecioList(listaPrecioListNew);
            List<AiuPresupuesto> attachedAiuPresupuestoListNew = new ArrayList<AiuPresupuesto>();
            for (AiuPresupuesto aiuPresupuestoListNewAiuPresupuestoToAttach : aiuPresupuestoListNew) {
                aiuPresupuestoListNewAiuPresupuestoToAttach = em.getReference(aiuPresupuestoListNewAiuPresupuestoToAttach.getClass(), aiuPresupuestoListNewAiuPresupuestoToAttach.getId());
                attachedAiuPresupuestoListNew.add(aiuPresupuestoListNewAiuPresupuestoToAttach);
            }
            aiuPresupuestoListNew = attachedAiuPresupuestoListNew;
            presupuesto.setAiuPresupuestoList(aiuPresupuestoListNew);
            List<ActaCobro> attachedActaCobroListNew = new ArrayList<ActaCobro>();
            for (ActaCobro actaCobroListNewActaCobroToAttach : actaCobroListNew) {
                actaCobroListNewActaCobroToAttach = em.getReference(actaCobroListNewActaCobroToAttach.getClass(), actaCobroListNewActaCobroToAttach.getId());
                attachedActaCobroListNew.add(actaCobroListNewActaCobroToAttach);
            }
            actaCobroListNew = attachedActaCobroListNew;
            presupuesto.setActaCobroList(actaCobroListNew);
            List<AiuEspecificoPresupuesto> attachedAiuEspecificoPresupuestoListNew = new ArrayList<AiuEspecificoPresupuesto>();
            for (AiuEspecificoPresupuesto aiuEspecificoPresupuestoListNewAiuEspecificoPresupuestoToAttach : aiuEspecificoPresupuestoListNew) {
                aiuEspecificoPresupuestoListNewAiuEspecificoPresupuestoToAttach = em.getReference(aiuEspecificoPresupuestoListNewAiuEspecificoPresupuestoToAttach.getClass(), aiuEspecificoPresupuestoListNewAiuEspecificoPresupuestoToAttach.getId());
                attachedAiuEspecificoPresupuestoListNew.add(aiuEspecificoPresupuestoListNewAiuEspecificoPresupuestoToAttach);
            }
            aiuEspecificoPresupuestoListNew = attachedAiuEspecificoPresupuestoListNew;
            presupuesto.setAiuEspecificoPresupuestoList(aiuEspecificoPresupuestoListNew);
            List<ActividadPresupuesto> attachedActividadPresupuestoListNew = new ArrayList<ActividadPresupuesto>();
            for (ActividadPresupuesto actividadPresupuestoListNewActividadPresupuestoToAttach : actividadPresupuestoListNew) {
                actividadPresupuestoListNewActividadPresupuestoToAttach = em.getReference(actividadPresupuestoListNewActividadPresupuestoToAttach.getClass(), actividadPresupuestoListNewActividadPresupuestoToAttach.getId());
                attachedActividadPresupuestoListNew.add(actividadPresupuestoListNewActividadPresupuestoToAttach);
            }
            actividadPresupuestoListNew = attachedActividadPresupuestoListNew;
            presupuesto.setActividadPresupuestoList(actividadPresupuestoListNew);
            presupuesto = em.merge(presupuesto);
            for (ListaPrecio listaPrecioListOldListaPrecio : listaPrecioListOld) {
                if (!listaPrecioListNew.contains(listaPrecioListOldListaPrecio)) {
                    listaPrecioListOldListaPrecio.setIdPresupuesto(null);
                    listaPrecioListOldListaPrecio = em.merge(listaPrecioListOldListaPrecio);
                }
            }
            for (ListaPrecio listaPrecioListNewListaPrecio : listaPrecioListNew) {
                if (!listaPrecioListOld.contains(listaPrecioListNewListaPrecio)) {
                    Presupuesto oldIdPresupuestoOfListaPrecioListNewListaPrecio = listaPrecioListNewListaPrecio.getIdPresupuesto();
                    listaPrecioListNewListaPrecio.setIdPresupuesto(presupuesto);
                    listaPrecioListNewListaPrecio = em.merge(listaPrecioListNewListaPrecio);
                    if (oldIdPresupuestoOfListaPrecioListNewListaPrecio != null && !oldIdPresupuestoOfListaPrecioListNewListaPrecio.equals(presupuesto)) {
                        oldIdPresupuestoOfListaPrecioListNewListaPrecio.getListaPrecioList().remove(listaPrecioListNewListaPrecio);
                        oldIdPresupuestoOfListaPrecioListNewListaPrecio = em.merge(oldIdPresupuestoOfListaPrecioListNewListaPrecio);
                    }
                }
            }
            for (AiuPresupuesto aiuPresupuestoListOldAiuPresupuesto : aiuPresupuestoListOld) {
                if (!aiuPresupuestoListNew.contains(aiuPresupuestoListOldAiuPresupuesto)) {
                    aiuPresupuestoListOldAiuPresupuesto.setIdPresupuesto(null);
                    aiuPresupuestoListOldAiuPresupuesto = em.merge(aiuPresupuestoListOldAiuPresupuesto);
                }
            }
            for (AiuPresupuesto aiuPresupuestoListNewAiuPresupuesto : aiuPresupuestoListNew) {
                if (!aiuPresupuestoListOld.contains(aiuPresupuestoListNewAiuPresupuesto)) {
                    Presupuesto oldIdPresupuestoOfAiuPresupuestoListNewAiuPresupuesto = aiuPresupuestoListNewAiuPresupuesto.getIdPresupuesto();
                    aiuPresupuestoListNewAiuPresupuesto.setIdPresupuesto(presupuesto);
                    aiuPresupuestoListNewAiuPresupuesto = em.merge(aiuPresupuestoListNewAiuPresupuesto);
                    if (oldIdPresupuestoOfAiuPresupuestoListNewAiuPresupuesto != null && !oldIdPresupuestoOfAiuPresupuestoListNewAiuPresupuesto.equals(presupuesto)) {
                        oldIdPresupuestoOfAiuPresupuestoListNewAiuPresupuesto.getAiuPresupuestoList().remove(aiuPresupuestoListNewAiuPresupuesto);
                        oldIdPresupuestoOfAiuPresupuestoListNewAiuPresupuesto = em.merge(oldIdPresupuestoOfAiuPresupuestoListNewAiuPresupuesto);
                    }
                }
            }
            for (ActaCobro actaCobroListOldActaCobro : actaCobroListOld) {
                if (!actaCobroListNew.contains(actaCobroListOldActaCobro)) {
                    actaCobroListOldActaCobro.setIdPresupuesto(null);
                    actaCobroListOldActaCobro = em.merge(actaCobroListOldActaCobro);
                }
            }
            for (ActaCobro actaCobroListNewActaCobro : actaCobroListNew) {
                if (!actaCobroListOld.contains(actaCobroListNewActaCobro)) {
                    Presupuesto oldIdPresupuestoOfActaCobroListNewActaCobro = actaCobroListNewActaCobro.getIdPresupuesto();
                    actaCobroListNewActaCobro.setIdPresupuesto(presupuesto);
                    actaCobroListNewActaCobro = em.merge(actaCobroListNewActaCobro);
                    if (oldIdPresupuestoOfActaCobroListNewActaCobro != null && !oldIdPresupuestoOfActaCobroListNewActaCobro.equals(presupuesto)) {
                        oldIdPresupuestoOfActaCobroListNewActaCobro.getActaCobroList().remove(actaCobroListNewActaCobro);
                        oldIdPresupuestoOfActaCobroListNewActaCobro = em.merge(oldIdPresupuestoOfActaCobroListNewActaCobro);
                    }
                }
            }
            for (AiuEspecificoPresupuesto aiuEspecificoPresupuestoListOldAiuEspecificoPresupuesto : aiuEspecificoPresupuestoListOld) {
                if (!aiuEspecificoPresupuestoListNew.contains(aiuEspecificoPresupuestoListOldAiuEspecificoPresupuesto)) {
                    aiuEspecificoPresupuestoListOldAiuEspecificoPresupuesto.setIdPresupuesto(null);
                    aiuEspecificoPresupuestoListOldAiuEspecificoPresupuesto = em.merge(aiuEspecificoPresupuestoListOldAiuEspecificoPresupuesto);
                }
            }
            for (AiuEspecificoPresupuesto aiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto : aiuEspecificoPresupuestoListNew) {
                if (!aiuEspecificoPresupuestoListOld.contains(aiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto)) {
                    Presupuesto oldIdPresupuestoOfAiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto = aiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto.getIdPresupuesto();
                    aiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto.setIdPresupuesto(presupuesto);
                    aiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto = em.merge(aiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto);
                    if (oldIdPresupuestoOfAiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto != null && !oldIdPresupuestoOfAiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto.equals(presupuesto)) {
                        oldIdPresupuestoOfAiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto.getAiuEspecificoPresupuestoList().remove(aiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto);
                        oldIdPresupuestoOfAiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto = em.merge(oldIdPresupuestoOfAiuEspecificoPresupuestoListNewAiuEspecificoPresupuesto);
                    }
                }
            }
            for (ActividadPresupuesto actividadPresupuestoListOldActividadPresupuesto : actividadPresupuestoListOld) {
                if (!actividadPresupuestoListNew.contains(actividadPresupuestoListOldActividadPresupuesto)) {
                    actividadPresupuestoListOldActividadPresupuesto.setIdPresupuesto(null);
                    actividadPresupuestoListOldActividadPresupuesto = em.merge(actividadPresupuestoListOldActividadPresupuesto);
                }
            }
            for (ActividadPresupuesto actividadPresupuestoListNewActividadPresupuesto : actividadPresupuestoListNew) {
                if (!actividadPresupuestoListOld.contains(actividadPresupuestoListNewActividadPresupuesto)) {
                    Presupuesto oldIdPresupuestoOfActividadPresupuestoListNewActividadPresupuesto = actividadPresupuestoListNewActividadPresupuesto.getIdPresupuesto();
                    actividadPresupuestoListNewActividadPresupuesto.setIdPresupuesto(presupuesto);
                    actividadPresupuestoListNewActividadPresupuesto = em.merge(actividadPresupuestoListNewActividadPresupuesto);
                    if (oldIdPresupuestoOfActividadPresupuestoListNewActividadPresupuesto != null && !oldIdPresupuestoOfActividadPresupuestoListNewActividadPresupuesto.equals(presupuesto)) {
                        oldIdPresupuestoOfActividadPresupuestoListNewActividadPresupuesto.getActividadPresupuestoList().remove(actividadPresupuestoListNewActividadPresupuesto);
                        oldIdPresupuestoOfActividadPresupuestoListNewActividadPresupuesto = em.merge(oldIdPresupuestoOfActividadPresupuestoListNewActividadPresupuesto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = presupuesto.getId();
                if (findPresupuesto(id) == null) {
                    throw new NonexistentEntityException("The presupuesto with id " + id + " no longer exists.");
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
            Presupuesto presupuesto;
            try {
                presupuesto = em.getReference(Presupuesto.class, id);
                presupuesto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The presupuesto with id " + id + " no longer exists.", enfe);
            }
            List<ListaPrecio> listaPrecioList = presupuesto.getListaPrecioList();
            for (ListaPrecio listaPrecioListListaPrecio : listaPrecioList) {
                listaPrecioListListaPrecio.setIdPresupuesto(null);
                listaPrecioListListaPrecio = em.merge(listaPrecioListListaPrecio);
            }
            List<AiuPresupuesto> aiuPresupuestoList = presupuesto.getAiuPresupuestoList();
            for (AiuPresupuesto aiuPresupuestoListAiuPresupuesto : aiuPresupuestoList) {
                aiuPresupuestoListAiuPresupuesto.setIdPresupuesto(null);
                aiuPresupuestoListAiuPresupuesto = em.merge(aiuPresupuestoListAiuPresupuesto);
            }
            List<ActaCobro> actaCobroList = presupuesto.getActaCobroList();
            for (ActaCobro actaCobroListActaCobro : actaCobroList) {
                actaCobroListActaCobro.setIdPresupuesto(null);
                actaCobroListActaCobro = em.merge(actaCobroListActaCobro);
            }
            List<AiuEspecificoPresupuesto> aiuEspecificoPresupuestoList = presupuesto.getAiuEspecificoPresupuestoList();
            for (AiuEspecificoPresupuesto aiuEspecificoPresupuestoListAiuEspecificoPresupuesto : aiuEspecificoPresupuestoList) {
                aiuEspecificoPresupuestoListAiuEspecificoPresupuesto.setIdPresupuesto(null);
                aiuEspecificoPresupuestoListAiuEspecificoPresupuesto = em.merge(aiuEspecificoPresupuestoListAiuEspecificoPresupuesto);
            }
            List<ActividadPresupuesto> actividadPresupuestoList = presupuesto.getActividadPresupuestoList();
            for (ActividadPresupuesto actividadPresupuestoListActividadPresupuesto : actividadPresupuestoList) {
                actividadPresupuestoListActividadPresupuesto.setIdPresupuesto(null);
                actividadPresupuestoListActividadPresupuesto = em.merge(actividadPresupuestoListActividadPresupuesto);
            }
            em.remove(presupuesto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Presupuesto> findPresupuestoEntities() {
        return findPresupuestoEntities(true, -1, -1);
    }

    public List<Presupuesto> findPresupuestoEntities(int maxResults, int firstResult) {
        return findPresupuestoEntities(false, maxResults, firstResult);
    }

    private List<Presupuesto> findPresupuestoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Presupuesto.class));
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

    public Presupuesto findPresupuesto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Presupuesto.class, id);
        } finally {
            em.close();
        }
    }

    public int getPresupuestoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Presupuesto> rt = cq.from(Presupuesto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
