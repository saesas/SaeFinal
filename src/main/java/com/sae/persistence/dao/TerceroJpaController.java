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
import com.sae.persistence.domain.Genero;
import com.sae.persistence.domain.EstadoCivil;
import com.sae.persistence.domain.TipoAsociadoTercero;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.SucursalRazonSocialTercero;
import com.sae.persistence.domain.TerceroRazonSocial;
import com.sae.persistence.domain.RepresentateLegalRazonSocial;
import com.sae.persistence.domain.CuentaBancariaTercero;
import com.sae.persistence.domain.IdentificacionTercero;
import com.sae.persistence.domain.Tercero;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TerceroJpaController implements Serializable {

    public TerceroJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tercero tercero) throws PreexistingEntityException, Exception {
        if (tercero.getTipoAsociadoTerceroList() == null) {
            tercero.setTipoAsociadoTerceroList(new ArrayList<TipoAsociadoTercero>());
        }
        if (tercero.getSucursalRazonSocialTerceroList() == null) {
            tercero.setSucursalRazonSocialTerceroList(new ArrayList<SucursalRazonSocialTercero>());
        }
        if (tercero.getTerceroRazonSocialList() == null) {
            tercero.setTerceroRazonSocialList(new ArrayList<TerceroRazonSocial>());
        }
        if (tercero.getRepresentateLegalRazonSocialList() == null) {
            tercero.setRepresentateLegalRazonSocialList(new ArrayList<RepresentateLegalRazonSocial>());
        }
        if (tercero.getCuentaBancariaTerceroList() == null) {
            tercero.setCuentaBancariaTerceroList(new ArrayList<CuentaBancariaTercero>());
        }
        if (tercero.getCuentaBancariaTerceroList1() == null) {
            tercero.setCuentaBancariaTerceroList1(new ArrayList<CuentaBancariaTercero>());
        }
        if (tercero.getIdentificacionTerceroList() == null) {
            tercero.setIdentificacionTerceroList(new ArrayList<IdentificacionTercero>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Genero genero = tercero.getGenero();
            if (genero != null) {
                genero = em.getReference(genero.getClass(), genero.getId());
                tercero.setGenero(genero);
            }
            EstadoCivil estadoCivil = tercero.getEstadoCivil();
            if (estadoCivil != null) {
                estadoCivil = em.getReference(estadoCivil.getClass(), estadoCivil.getId());
                tercero.setEstadoCivil(estadoCivil);
            }
            List<TipoAsociadoTercero> attachedTipoAsociadoTerceroList = new ArrayList<TipoAsociadoTercero>();
            for (TipoAsociadoTercero tipoAsociadoTerceroListTipoAsociadoTerceroToAttach : tercero.getTipoAsociadoTerceroList()) {
                tipoAsociadoTerceroListTipoAsociadoTerceroToAttach = em.getReference(tipoAsociadoTerceroListTipoAsociadoTerceroToAttach.getClass(), tipoAsociadoTerceroListTipoAsociadoTerceroToAttach.getId());
                attachedTipoAsociadoTerceroList.add(tipoAsociadoTerceroListTipoAsociadoTerceroToAttach);
            }
            tercero.setTipoAsociadoTerceroList(attachedTipoAsociadoTerceroList);
            List<SucursalRazonSocialTercero> attachedSucursalRazonSocialTerceroList = new ArrayList<SucursalRazonSocialTercero>();
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListSucursalRazonSocialTerceroToAttach : tercero.getSucursalRazonSocialTerceroList()) {
                sucursalRazonSocialTerceroListSucursalRazonSocialTerceroToAttach = em.getReference(sucursalRazonSocialTerceroListSucursalRazonSocialTerceroToAttach.getClass(), sucursalRazonSocialTerceroListSucursalRazonSocialTerceroToAttach.getId());
                attachedSucursalRazonSocialTerceroList.add(sucursalRazonSocialTerceroListSucursalRazonSocialTerceroToAttach);
            }
            tercero.setSucursalRazonSocialTerceroList(attachedSucursalRazonSocialTerceroList);
            List<TerceroRazonSocial> attachedTerceroRazonSocialList = new ArrayList<TerceroRazonSocial>();
            for (TerceroRazonSocial terceroRazonSocialListTerceroRazonSocialToAttach : tercero.getTerceroRazonSocialList()) {
                terceroRazonSocialListTerceroRazonSocialToAttach = em.getReference(terceroRazonSocialListTerceroRazonSocialToAttach.getClass(), terceroRazonSocialListTerceroRazonSocialToAttach.getId());
                attachedTerceroRazonSocialList.add(terceroRazonSocialListTerceroRazonSocialToAttach);
            }
            tercero.setTerceroRazonSocialList(attachedTerceroRazonSocialList);
            List<RepresentateLegalRazonSocial> attachedRepresentateLegalRazonSocialList = new ArrayList<RepresentateLegalRazonSocial>();
            for (RepresentateLegalRazonSocial representateLegalRazonSocialListRepresentateLegalRazonSocialToAttach : tercero.getRepresentateLegalRazonSocialList()) {
                representateLegalRazonSocialListRepresentateLegalRazonSocialToAttach = em.getReference(representateLegalRazonSocialListRepresentateLegalRazonSocialToAttach.getClass(), representateLegalRazonSocialListRepresentateLegalRazonSocialToAttach.getId());
                attachedRepresentateLegalRazonSocialList.add(representateLegalRazonSocialListRepresentateLegalRazonSocialToAttach);
            }
            tercero.setRepresentateLegalRazonSocialList(attachedRepresentateLegalRazonSocialList);
            List<CuentaBancariaTercero> attachedCuentaBancariaTerceroList = new ArrayList<CuentaBancariaTercero>();
            for (CuentaBancariaTercero cuentaBancariaTerceroListCuentaBancariaTerceroToAttach : tercero.getCuentaBancariaTerceroList()) {
                cuentaBancariaTerceroListCuentaBancariaTerceroToAttach = em.getReference(cuentaBancariaTerceroListCuentaBancariaTerceroToAttach.getClass(), cuentaBancariaTerceroListCuentaBancariaTerceroToAttach.getId());
                attachedCuentaBancariaTerceroList.add(cuentaBancariaTerceroListCuentaBancariaTerceroToAttach);
            }
            tercero.setCuentaBancariaTerceroList(attachedCuentaBancariaTerceroList);
            List<CuentaBancariaTercero> attachedCuentaBancariaTerceroList1 = new ArrayList<CuentaBancariaTercero>();
            for (CuentaBancariaTercero cuentaBancariaTerceroList1CuentaBancariaTerceroToAttach : tercero.getCuentaBancariaTerceroList1()) {
                cuentaBancariaTerceroList1CuentaBancariaTerceroToAttach = em.getReference(cuentaBancariaTerceroList1CuentaBancariaTerceroToAttach.getClass(), cuentaBancariaTerceroList1CuentaBancariaTerceroToAttach.getId());
                attachedCuentaBancariaTerceroList1.add(cuentaBancariaTerceroList1CuentaBancariaTerceroToAttach);
            }
            tercero.setCuentaBancariaTerceroList1(attachedCuentaBancariaTerceroList1);
            List<IdentificacionTercero> attachedIdentificacionTerceroList = new ArrayList<IdentificacionTercero>();
            for (IdentificacionTercero identificacionTerceroListIdentificacionTerceroToAttach : tercero.getIdentificacionTerceroList()) {
                identificacionTerceroListIdentificacionTerceroToAttach = em.getReference(identificacionTerceroListIdentificacionTerceroToAttach.getClass(), identificacionTerceroListIdentificacionTerceroToAttach.getId());
                attachedIdentificacionTerceroList.add(identificacionTerceroListIdentificacionTerceroToAttach);
            }
            tercero.setIdentificacionTerceroList(attachedIdentificacionTerceroList);
            em.persist(tercero);
            if (genero != null) {
                genero.getTerceroList().add(tercero);
                genero = em.merge(genero);
            }
            if (estadoCivil != null) {
                estadoCivil.getTerceroList().add(tercero);
                estadoCivil = em.merge(estadoCivil);
            }
            for (TipoAsociadoTercero tipoAsociadoTerceroListTipoAsociadoTercero : tercero.getTipoAsociadoTerceroList()) {
                Tercero oldIdTerceroOfTipoAsociadoTerceroListTipoAsociadoTercero = tipoAsociadoTerceroListTipoAsociadoTercero.getIdTercero();
                tipoAsociadoTerceroListTipoAsociadoTercero.setIdTercero(tercero);
                tipoAsociadoTerceroListTipoAsociadoTercero = em.merge(tipoAsociadoTerceroListTipoAsociadoTercero);
                if (oldIdTerceroOfTipoAsociadoTerceroListTipoAsociadoTercero != null) {
                    oldIdTerceroOfTipoAsociadoTerceroListTipoAsociadoTercero.getTipoAsociadoTerceroList().remove(tipoAsociadoTerceroListTipoAsociadoTercero);
                    oldIdTerceroOfTipoAsociadoTerceroListTipoAsociadoTercero = em.merge(oldIdTerceroOfTipoAsociadoTerceroListTipoAsociadoTercero);
                }
            }
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListSucursalRazonSocialTercero : tercero.getSucursalRazonSocialTerceroList()) {
                Tercero oldIdTerceroOfSucursalRazonSocialTerceroListSucursalRazonSocialTercero = sucursalRazonSocialTerceroListSucursalRazonSocialTercero.getIdTercero();
                sucursalRazonSocialTerceroListSucursalRazonSocialTercero.setIdTercero(tercero);
                sucursalRazonSocialTerceroListSucursalRazonSocialTercero = em.merge(sucursalRazonSocialTerceroListSucursalRazonSocialTercero);
                if (oldIdTerceroOfSucursalRazonSocialTerceroListSucursalRazonSocialTercero != null) {
                    oldIdTerceroOfSucursalRazonSocialTerceroListSucursalRazonSocialTercero.getSucursalRazonSocialTerceroList().remove(sucursalRazonSocialTerceroListSucursalRazonSocialTercero);
                    oldIdTerceroOfSucursalRazonSocialTerceroListSucursalRazonSocialTercero = em.merge(oldIdTerceroOfSucursalRazonSocialTerceroListSucursalRazonSocialTercero);
                }
            }
            for (TerceroRazonSocial terceroRazonSocialListTerceroRazonSocial : tercero.getTerceroRazonSocialList()) {
                Tercero oldIdTerceroOfTerceroRazonSocialListTerceroRazonSocial = terceroRazonSocialListTerceroRazonSocial.getIdTercero();
                terceroRazonSocialListTerceroRazonSocial.setIdTercero(tercero);
                terceroRazonSocialListTerceroRazonSocial = em.merge(terceroRazonSocialListTerceroRazonSocial);
                if (oldIdTerceroOfTerceroRazonSocialListTerceroRazonSocial != null) {
                    oldIdTerceroOfTerceroRazonSocialListTerceroRazonSocial.getTerceroRazonSocialList().remove(terceroRazonSocialListTerceroRazonSocial);
                    oldIdTerceroOfTerceroRazonSocialListTerceroRazonSocial = em.merge(oldIdTerceroOfTerceroRazonSocialListTerceroRazonSocial);
                }
            }
            for (RepresentateLegalRazonSocial representateLegalRazonSocialListRepresentateLegalRazonSocial : tercero.getRepresentateLegalRazonSocialList()) {
                Tercero oldIdTerceroOfRepresentateLegalRazonSocialListRepresentateLegalRazonSocial = representateLegalRazonSocialListRepresentateLegalRazonSocial.getIdTercero();
                representateLegalRazonSocialListRepresentateLegalRazonSocial.setIdTercero(tercero);
                representateLegalRazonSocialListRepresentateLegalRazonSocial = em.merge(representateLegalRazonSocialListRepresentateLegalRazonSocial);
                if (oldIdTerceroOfRepresentateLegalRazonSocialListRepresentateLegalRazonSocial != null) {
                    oldIdTerceroOfRepresentateLegalRazonSocialListRepresentateLegalRazonSocial.getRepresentateLegalRazonSocialList().remove(representateLegalRazonSocialListRepresentateLegalRazonSocial);
                    oldIdTerceroOfRepresentateLegalRazonSocialListRepresentateLegalRazonSocial = em.merge(oldIdTerceroOfRepresentateLegalRazonSocialListRepresentateLegalRazonSocial);
                }
            }
            for (CuentaBancariaTercero cuentaBancariaTerceroListCuentaBancariaTercero : tercero.getCuentaBancariaTerceroList()) {
                Tercero oldIdTerceroOfCuentaBancariaTerceroListCuentaBancariaTercero = cuentaBancariaTerceroListCuentaBancariaTercero.getIdTercero();
                cuentaBancariaTerceroListCuentaBancariaTercero.setIdTercero(tercero);
                cuentaBancariaTerceroListCuentaBancariaTercero = em.merge(cuentaBancariaTerceroListCuentaBancariaTercero);
                if (oldIdTerceroOfCuentaBancariaTerceroListCuentaBancariaTercero != null) {
                    oldIdTerceroOfCuentaBancariaTerceroListCuentaBancariaTercero.getCuentaBancariaTerceroList().remove(cuentaBancariaTerceroListCuentaBancariaTercero);
                    oldIdTerceroOfCuentaBancariaTerceroListCuentaBancariaTercero = em.merge(oldIdTerceroOfCuentaBancariaTerceroListCuentaBancariaTercero);
                }
            }
            for (CuentaBancariaTercero cuentaBancariaTerceroList1CuentaBancariaTercero : tercero.getCuentaBancariaTerceroList1()) {
                Tercero oldIdNombreBancoOfCuentaBancariaTerceroList1CuentaBancariaTercero = cuentaBancariaTerceroList1CuentaBancariaTercero.getIdNombreBanco();
                cuentaBancariaTerceroList1CuentaBancariaTercero.setIdNombreBanco(tercero);
                cuentaBancariaTerceroList1CuentaBancariaTercero = em.merge(cuentaBancariaTerceroList1CuentaBancariaTercero);
                if (oldIdNombreBancoOfCuentaBancariaTerceroList1CuentaBancariaTercero != null) {
                    oldIdNombreBancoOfCuentaBancariaTerceroList1CuentaBancariaTercero.getCuentaBancariaTerceroList1().remove(cuentaBancariaTerceroList1CuentaBancariaTercero);
                    oldIdNombreBancoOfCuentaBancariaTerceroList1CuentaBancariaTercero = em.merge(oldIdNombreBancoOfCuentaBancariaTerceroList1CuentaBancariaTercero);
                }
            }
            for (IdentificacionTercero identificacionTerceroListIdentificacionTercero : tercero.getIdentificacionTerceroList()) {
                Tercero oldIdTerceroOfIdentificacionTerceroListIdentificacionTercero = identificacionTerceroListIdentificacionTercero.getIdTercero();
                identificacionTerceroListIdentificacionTercero.setIdTercero(tercero);
                identificacionTerceroListIdentificacionTercero = em.merge(identificacionTerceroListIdentificacionTercero);
                if (oldIdTerceroOfIdentificacionTerceroListIdentificacionTercero != null) {
                    oldIdTerceroOfIdentificacionTerceroListIdentificacionTercero.getIdentificacionTerceroList().remove(identificacionTerceroListIdentificacionTercero);
                    oldIdTerceroOfIdentificacionTerceroListIdentificacionTercero = em.merge(oldIdTerceroOfIdentificacionTerceroListIdentificacionTercero);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTercero(tercero.getId()) != null) {
                throw new PreexistingEntityException("Tercero " + tercero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tercero tercero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tercero persistentTercero = em.find(Tercero.class, tercero.getId());
            Genero generoOld = persistentTercero.getGenero();
            Genero generoNew = tercero.getGenero();
            EstadoCivil estadoCivilOld = persistentTercero.getEstadoCivil();
            EstadoCivil estadoCivilNew = tercero.getEstadoCivil();
            List<TipoAsociadoTercero> tipoAsociadoTerceroListOld = persistentTercero.getTipoAsociadoTerceroList();
            List<TipoAsociadoTercero> tipoAsociadoTerceroListNew = tercero.getTipoAsociadoTerceroList();
            List<SucursalRazonSocialTercero> sucursalRazonSocialTerceroListOld = persistentTercero.getSucursalRazonSocialTerceroList();
            List<SucursalRazonSocialTercero> sucursalRazonSocialTerceroListNew = tercero.getSucursalRazonSocialTerceroList();
            List<TerceroRazonSocial> terceroRazonSocialListOld = persistentTercero.getTerceroRazonSocialList();
            List<TerceroRazonSocial> terceroRazonSocialListNew = tercero.getTerceroRazonSocialList();
            List<RepresentateLegalRazonSocial> representateLegalRazonSocialListOld = persistentTercero.getRepresentateLegalRazonSocialList();
            List<RepresentateLegalRazonSocial> representateLegalRazonSocialListNew = tercero.getRepresentateLegalRazonSocialList();
            List<CuentaBancariaTercero> cuentaBancariaTerceroListOld = persistentTercero.getCuentaBancariaTerceroList();
            List<CuentaBancariaTercero> cuentaBancariaTerceroListNew = tercero.getCuentaBancariaTerceroList();
            List<CuentaBancariaTercero> cuentaBancariaTerceroList1Old = persistentTercero.getCuentaBancariaTerceroList1();
            List<CuentaBancariaTercero> cuentaBancariaTerceroList1New = tercero.getCuentaBancariaTerceroList1();
            List<IdentificacionTercero> identificacionTerceroListOld = persistentTercero.getIdentificacionTerceroList();
            List<IdentificacionTercero> identificacionTerceroListNew = tercero.getIdentificacionTerceroList();
            if (generoNew != null) {
                generoNew = em.getReference(generoNew.getClass(), generoNew.getId());
                tercero.setGenero(generoNew);
            }
            if (estadoCivilNew != null) {
                estadoCivilNew = em.getReference(estadoCivilNew.getClass(), estadoCivilNew.getId());
                tercero.setEstadoCivil(estadoCivilNew);
            }
            List<TipoAsociadoTercero> attachedTipoAsociadoTerceroListNew = new ArrayList<TipoAsociadoTercero>();
            for (TipoAsociadoTercero tipoAsociadoTerceroListNewTipoAsociadoTerceroToAttach : tipoAsociadoTerceroListNew) {
                tipoAsociadoTerceroListNewTipoAsociadoTerceroToAttach = em.getReference(tipoAsociadoTerceroListNewTipoAsociadoTerceroToAttach.getClass(), tipoAsociadoTerceroListNewTipoAsociadoTerceroToAttach.getId());
                attachedTipoAsociadoTerceroListNew.add(tipoAsociadoTerceroListNewTipoAsociadoTerceroToAttach);
            }
            tipoAsociadoTerceroListNew = attachedTipoAsociadoTerceroListNew;
            tercero.setTipoAsociadoTerceroList(tipoAsociadoTerceroListNew);
            List<SucursalRazonSocialTercero> attachedSucursalRazonSocialTerceroListNew = new ArrayList<SucursalRazonSocialTercero>();
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListNewSucursalRazonSocialTerceroToAttach : sucursalRazonSocialTerceroListNew) {
                sucursalRazonSocialTerceroListNewSucursalRazonSocialTerceroToAttach = em.getReference(sucursalRazonSocialTerceroListNewSucursalRazonSocialTerceroToAttach.getClass(), sucursalRazonSocialTerceroListNewSucursalRazonSocialTerceroToAttach.getId());
                attachedSucursalRazonSocialTerceroListNew.add(sucursalRazonSocialTerceroListNewSucursalRazonSocialTerceroToAttach);
            }
            sucursalRazonSocialTerceroListNew = attachedSucursalRazonSocialTerceroListNew;
            tercero.setSucursalRazonSocialTerceroList(sucursalRazonSocialTerceroListNew);
            List<TerceroRazonSocial> attachedTerceroRazonSocialListNew = new ArrayList<TerceroRazonSocial>();
            for (TerceroRazonSocial terceroRazonSocialListNewTerceroRazonSocialToAttach : terceroRazonSocialListNew) {
                terceroRazonSocialListNewTerceroRazonSocialToAttach = em.getReference(terceroRazonSocialListNewTerceroRazonSocialToAttach.getClass(), terceroRazonSocialListNewTerceroRazonSocialToAttach.getId());
                attachedTerceroRazonSocialListNew.add(terceroRazonSocialListNewTerceroRazonSocialToAttach);
            }
            terceroRazonSocialListNew = attachedTerceroRazonSocialListNew;
            tercero.setTerceroRazonSocialList(terceroRazonSocialListNew);
            List<RepresentateLegalRazonSocial> attachedRepresentateLegalRazonSocialListNew = new ArrayList<RepresentateLegalRazonSocial>();
            for (RepresentateLegalRazonSocial representateLegalRazonSocialListNewRepresentateLegalRazonSocialToAttach : representateLegalRazonSocialListNew) {
                representateLegalRazonSocialListNewRepresentateLegalRazonSocialToAttach = em.getReference(representateLegalRazonSocialListNewRepresentateLegalRazonSocialToAttach.getClass(), representateLegalRazonSocialListNewRepresentateLegalRazonSocialToAttach.getId());
                attachedRepresentateLegalRazonSocialListNew.add(representateLegalRazonSocialListNewRepresentateLegalRazonSocialToAttach);
            }
            representateLegalRazonSocialListNew = attachedRepresentateLegalRazonSocialListNew;
            tercero.setRepresentateLegalRazonSocialList(representateLegalRazonSocialListNew);
            List<CuentaBancariaTercero> attachedCuentaBancariaTerceroListNew = new ArrayList<CuentaBancariaTercero>();
            for (CuentaBancariaTercero cuentaBancariaTerceroListNewCuentaBancariaTerceroToAttach : cuentaBancariaTerceroListNew) {
                cuentaBancariaTerceroListNewCuentaBancariaTerceroToAttach = em.getReference(cuentaBancariaTerceroListNewCuentaBancariaTerceroToAttach.getClass(), cuentaBancariaTerceroListNewCuentaBancariaTerceroToAttach.getId());
                attachedCuentaBancariaTerceroListNew.add(cuentaBancariaTerceroListNewCuentaBancariaTerceroToAttach);
            }
            cuentaBancariaTerceroListNew = attachedCuentaBancariaTerceroListNew;
            tercero.setCuentaBancariaTerceroList(cuentaBancariaTerceroListNew);
            List<CuentaBancariaTercero> attachedCuentaBancariaTerceroList1New = new ArrayList<CuentaBancariaTercero>();
            for (CuentaBancariaTercero cuentaBancariaTerceroList1NewCuentaBancariaTerceroToAttach : cuentaBancariaTerceroList1New) {
                cuentaBancariaTerceroList1NewCuentaBancariaTerceroToAttach = em.getReference(cuentaBancariaTerceroList1NewCuentaBancariaTerceroToAttach.getClass(), cuentaBancariaTerceroList1NewCuentaBancariaTerceroToAttach.getId());
                attachedCuentaBancariaTerceroList1New.add(cuentaBancariaTerceroList1NewCuentaBancariaTerceroToAttach);
            }
            cuentaBancariaTerceroList1New = attachedCuentaBancariaTerceroList1New;
            tercero.setCuentaBancariaTerceroList1(cuentaBancariaTerceroList1New);
            List<IdentificacionTercero> attachedIdentificacionTerceroListNew = new ArrayList<IdentificacionTercero>();
            for (IdentificacionTercero identificacionTerceroListNewIdentificacionTerceroToAttach : identificacionTerceroListNew) {
                identificacionTerceroListNewIdentificacionTerceroToAttach = em.getReference(identificacionTerceroListNewIdentificacionTerceroToAttach.getClass(), identificacionTerceroListNewIdentificacionTerceroToAttach.getId());
                attachedIdentificacionTerceroListNew.add(identificacionTerceroListNewIdentificacionTerceroToAttach);
            }
            identificacionTerceroListNew = attachedIdentificacionTerceroListNew;
            tercero.setIdentificacionTerceroList(identificacionTerceroListNew);
            tercero = em.merge(tercero);
            if (generoOld != null && !generoOld.equals(generoNew)) {
                generoOld.getTerceroList().remove(tercero);
                generoOld = em.merge(generoOld);
            }
            if (generoNew != null && !generoNew.equals(generoOld)) {
                generoNew.getTerceroList().add(tercero);
                generoNew = em.merge(generoNew);
            }
            if (estadoCivilOld != null && !estadoCivilOld.equals(estadoCivilNew)) {
                estadoCivilOld.getTerceroList().remove(tercero);
                estadoCivilOld = em.merge(estadoCivilOld);
            }
            if (estadoCivilNew != null && !estadoCivilNew.equals(estadoCivilOld)) {
                estadoCivilNew.getTerceroList().add(tercero);
                estadoCivilNew = em.merge(estadoCivilNew);
            }
            for (TipoAsociadoTercero tipoAsociadoTerceroListOldTipoAsociadoTercero : tipoAsociadoTerceroListOld) {
                if (!tipoAsociadoTerceroListNew.contains(tipoAsociadoTerceroListOldTipoAsociadoTercero)) {
                    tipoAsociadoTerceroListOldTipoAsociadoTercero.setIdTercero(null);
                    tipoAsociadoTerceroListOldTipoAsociadoTercero = em.merge(tipoAsociadoTerceroListOldTipoAsociadoTercero);
                }
            }
            for (TipoAsociadoTercero tipoAsociadoTerceroListNewTipoAsociadoTercero : tipoAsociadoTerceroListNew) {
                if (!tipoAsociadoTerceroListOld.contains(tipoAsociadoTerceroListNewTipoAsociadoTercero)) {
                    Tercero oldIdTerceroOfTipoAsociadoTerceroListNewTipoAsociadoTercero = tipoAsociadoTerceroListNewTipoAsociadoTercero.getIdTercero();
                    tipoAsociadoTerceroListNewTipoAsociadoTercero.setIdTercero(tercero);
                    tipoAsociadoTerceroListNewTipoAsociadoTercero = em.merge(tipoAsociadoTerceroListNewTipoAsociadoTercero);
                    if (oldIdTerceroOfTipoAsociadoTerceroListNewTipoAsociadoTercero != null && !oldIdTerceroOfTipoAsociadoTerceroListNewTipoAsociadoTercero.equals(tercero)) {
                        oldIdTerceroOfTipoAsociadoTerceroListNewTipoAsociadoTercero.getTipoAsociadoTerceroList().remove(tipoAsociadoTerceroListNewTipoAsociadoTercero);
                        oldIdTerceroOfTipoAsociadoTerceroListNewTipoAsociadoTercero = em.merge(oldIdTerceroOfTipoAsociadoTerceroListNewTipoAsociadoTercero);
                    }
                }
            }
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListOldSucursalRazonSocialTercero : sucursalRazonSocialTerceroListOld) {
                if (!sucursalRazonSocialTerceroListNew.contains(sucursalRazonSocialTerceroListOldSucursalRazonSocialTercero)) {
                    sucursalRazonSocialTerceroListOldSucursalRazonSocialTercero.setIdTercero(null);
                    sucursalRazonSocialTerceroListOldSucursalRazonSocialTercero = em.merge(sucursalRazonSocialTerceroListOldSucursalRazonSocialTercero);
                }
            }
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero : sucursalRazonSocialTerceroListNew) {
                if (!sucursalRazonSocialTerceroListOld.contains(sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero)) {
                    Tercero oldIdTerceroOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero = sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero.getIdTercero();
                    sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero.setIdTercero(tercero);
                    sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero = em.merge(sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero);
                    if (oldIdTerceroOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero != null && !oldIdTerceroOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero.equals(tercero)) {
                        oldIdTerceroOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero.getSucursalRazonSocialTerceroList().remove(sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero);
                        oldIdTerceroOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero = em.merge(oldIdTerceroOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero);
                    }
                }
            }
            for (TerceroRazonSocial terceroRazonSocialListOldTerceroRazonSocial : terceroRazonSocialListOld) {
                if (!terceroRazonSocialListNew.contains(terceroRazonSocialListOldTerceroRazonSocial)) {
                    terceroRazonSocialListOldTerceroRazonSocial.setIdTercero(null);
                    terceroRazonSocialListOldTerceroRazonSocial = em.merge(terceroRazonSocialListOldTerceroRazonSocial);
                }
            }
            for (TerceroRazonSocial terceroRazonSocialListNewTerceroRazonSocial : terceroRazonSocialListNew) {
                if (!terceroRazonSocialListOld.contains(terceroRazonSocialListNewTerceroRazonSocial)) {
                    Tercero oldIdTerceroOfTerceroRazonSocialListNewTerceroRazonSocial = terceroRazonSocialListNewTerceroRazonSocial.getIdTercero();
                    terceroRazonSocialListNewTerceroRazonSocial.setIdTercero(tercero);
                    terceroRazonSocialListNewTerceroRazonSocial = em.merge(terceroRazonSocialListNewTerceroRazonSocial);
                    if (oldIdTerceroOfTerceroRazonSocialListNewTerceroRazonSocial != null && !oldIdTerceroOfTerceroRazonSocialListNewTerceroRazonSocial.equals(tercero)) {
                        oldIdTerceroOfTerceroRazonSocialListNewTerceroRazonSocial.getTerceroRazonSocialList().remove(terceroRazonSocialListNewTerceroRazonSocial);
                        oldIdTerceroOfTerceroRazonSocialListNewTerceroRazonSocial = em.merge(oldIdTerceroOfTerceroRazonSocialListNewTerceroRazonSocial);
                    }
                }
            }
            for (RepresentateLegalRazonSocial representateLegalRazonSocialListOldRepresentateLegalRazonSocial : representateLegalRazonSocialListOld) {
                if (!representateLegalRazonSocialListNew.contains(representateLegalRazonSocialListOldRepresentateLegalRazonSocial)) {
                    representateLegalRazonSocialListOldRepresentateLegalRazonSocial.setIdTercero(null);
                    representateLegalRazonSocialListOldRepresentateLegalRazonSocial = em.merge(representateLegalRazonSocialListOldRepresentateLegalRazonSocial);
                }
            }
            for (RepresentateLegalRazonSocial representateLegalRazonSocialListNewRepresentateLegalRazonSocial : representateLegalRazonSocialListNew) {
                if (!representateLegalRazonSocialListOld.contains(representateLegalRazonSocialListNewRepresentateLegalRazonSocial)) {
                    Tercero oldIdTerceroOfRepresentateLegalRazonSocialListNewRepresentateLegalRazonSocial = representateLegalRazonSocialListNewRepresentateLegalRazonSocial.getIdTercero();
                    representateLegalRazonSocialListNewRepresentateLegalRazonSocial.setIdTercero(tercero);
                    representateLegalRazonSocialListNewRepresentateLegalRazonSocial = em.merge(representateLegalRazonSocialListNewRepresentateLegalRazonSocial);
                    if (oldIdTerceroOfRepresentateLegalRazonSocialListNewRepresentateLegalRazonSocial != null && !oldIdTerceroOfRepresentateLegalRazonSocialListNewRepresentateLegalRazonSocial.equals(tercero)) {
                        oldIdTerceroOfRepresentateLegalRazonSocialListNewRepresentateLegalRazonSocial.getRepresentateLegalRazonSocialList().remove(representateLegalRazonSocialListNewRepresentateLegalRazonSocial);
                        oldIdTerceroOfRepresentateLegalRazonSocialListNewRepresentateLegalRazonSocial = em.merge(oldIdTerceroOfRepresentateLegalRazonSocialListNewRepresentateLegalRazonSocial);
                    }
                }
            }
            for (CuentaBancariaTercero cuentaBancariaTerceroListOldCuentaBancariaTercero : cuentaBancariaTerceroListOld) {
                if (!cuentaBancariaTerceroListNew.contains(cuentaBancariaTerceroListOldCuentaBancariaTercero)) {
                    cuentaBancariaTerceroListOldCuentaBancariaTercero.setIdTercero(null);
                    cuentaBancariaTerceroListOldCuentaBancariaTercero = em.merge(cuentaBancariaTerceroListOldCuentaBancariaTercero);
                }
            }
            for (CuentaBancariaTercero cuentaBancariaTerceroListNewCuentaBancariaTercero : cuentaBancariaTerceroListNew) {
                if (!cuentaBancariaTerceroListOld.contains(cuentaBancariaTerceroListNewCuentaBancariaTercero)) {
                    Tercero oldIdTerceroOfCuentaBancariaTerceroListNewCuentaBancariaTercero = cuentaBancariaTerceroListNewCuentaBancariaTercero.getIdTercero();
                    cuentaBancariaTerceroListNewCuentaBancariaTercero.setIdTercero(tercero);
                    cuentaBancariaTerceroListNewCuentaBancariaTercero = em.merge(cuentaBancariaTerceroListNewCuentaBancariaTercero);
                    if (oldIdTerceroOfCuentaBancariaTerceroListNewCuentaBancariaTercero != null && !oldIdTerceroOfCuentaBancariaTerceroListNewCuentaBancariaTercero.equals(tercero)) {
                        oldIdTerceroOfCuentaBancariaTerceroListNewCuentaBancariaTercero.getCuentaBancariaTerceroList().remove(cuentaBancariaTerceroListNewCuentaBancariaTercero);
                        oldIdTerceroOfCuentaBancariaTerceroListNewCuentaBancariaTercero = em.merge(oldIdTerceroOfCuentaBancariaTerceroListNewCuentaBancariaTercero);
                    }
                }
            }
            for (CuentaBancariaTercero cuentaBancariaTerceroList1OldCuentaBancariaTercero : cuentaBancariaTerceroList1Old) {
                if (!cuentaBancariaTerceroList1New.contains(cuentaBancariaTerceroList1OldCuentaBancariaTercero)) {
                    cuentaBancariaTerceroList1OldCuentaBancariaTercero.setIdNombreBanco(null);
                    cuentaBancariaTerceroList1OldCuentaBancariaTercero = em.merge(cuentaBancariaTerceroList1OldCuentaBancariaTercero);
                }
            }
            for (CuentaBancariaTercero cuentaBancariaTerceroList1NewCuentaBancariaTercero : cuentaBancariaTerceroList1New) {
                if (!cuentaBancariaTerceroList1Old.contains(cuentaBancariaTerceroList1NewCuentaBancariaTercero)) {
                    Tercero oldIdNombreBancoOfCuentaBancariaTerceroList1NewCuentaBancariaTercero = cuentaBancariaTerceroList1NewCuentaBancariaTercero.getIdNombreBanco();
                    cuentaBancariaTerceroList1NewCuentaBancariaTercero.setIdNombreBanco(tercero);
                    cuentaBancariaTerceroList1NewCuentaBancariaTercero = em.merge(cuentaBancariaTerceroList1NewCuentaBancariaTercero);
                    if (oldIdNombreBancoOfCuentaBancariaTerceroList1NewCuentaBancariaTercero != null && !oldIdNombreBancoOfCuentaBancariaTerceroList1NewCuentaBancariaTercero.equals(tercero)) {
                        oldIdNombreBancoOfCuentaBancariaTerceroList1NewCuentaBancariaTercero.getCuentaBancariaTerceroList1().remove(cuentaBancariaTerceroList1NewCuentaBancariaTercero);
                        oldIdNombreBancoOfCuentaBancariaTerceroList1NewCuentaBancariaTercero = em.merge(oldIdNombreBancoOfCuentaBancariaTerceroList1NewCuentaBancariaTercero);
                    }
                }
            }
            for (IdentificacionTercero identificacionTerceroListOldIdentificacionTercero : identificacionTerceroListOld) {
                if (!identificacionTerceroListNew.contains(identificacionTerceroListOldIdentificacionTercero)) {
                    identificacionTerceroListOldIdentificacionTercero.setIdTercero(null);
                    identificacionTerceroListOldIdentificacionTercero = em.merge(identificacionTerceroListOldIdentificacionTercero);
                }
            }
            for (IdentificacionTercero identificacionTerceroListNewIdentificacionTercero : identificacionTerceroListNew) {
                if (!identificacionTerceroListOld.contains(identificacionTerceroListNewIdentificacionTercero)) {
                    Tercero oldIdTerceroOfIdentificacionTerceroListNewIdentificacionTercero = identificacionTerceroListNewIdentificacionTercero.getIdTercero();
                    identificacionTerceroListNewIdentificacionTercero.setIdTercero(tercero);
                    identificacionTerceroListNewIdentificacionTercero = em.merge(identificacionTerceroListNewIdentificacionTercero);
                    if (oldIdTerceroOfIdentificacionTerceroListNewIdentificacionTercero != null && !oldIdTerceroOfIdentificacionTerceroListNewIdentificacionTercero.equals(tercero)) {
                        oldIdTerceroOfIdentificacionTerceroListNewIdentificacionTercero.getIdentificacionTerceroList().remove(identificacionTerceroListNewIdentificacionTercero);
                        oldIdTerceroOfIdentificacionTerceroListNewIdentificacionTercero = em.merge(oldIdTerceroOfIdentificacionTerceroListNewIdentificacionTercero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tercero.getId();
                if (findTercero(id) == null) {
                    throw new NonexistentEntityException("The tercero with id " + id + " no longer exists.");
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
            Tercero tercero;
            try {
                tercero = em.getReference(Tercero.class, id);
                tercero.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tercero with id " + id + " no longer exists.", enfe);
            }
            Genero genero = tercero.getGenero();
            if (genero != null) {
                genero.getTerceroList().remove(tercero);
                genero = em.merge(genero);
            }
            EstadoCivil estadoCivil = tercero.getEstadoCivil();
            if (estadoCivil != null) {
                estadoCivil.getTerceroList().remove(tercero);
                estadoCivil = em.merge(estadoCivil);
            }
            List<TipoAsociadoTercero> tipoAsociadoTerceroList = tercero.getTipoAsociadoTerceroList();
            for (TipoAsociadoTercero tipoAsociadoTerceroListTipoAsociadoTercero : tipoAsociadoTerceroList) {
                tipoAsociadoTerceroListTipoAsociadoTercero.setIdTercero(null);
                tipoAsociadoTerceroListTipoAsociadoTercero = em.merge(tipoAsociadoTerceroListTipoAsociadoTercero);
            }
            List<SucursalRazonSocialTercero> sucursalRazonSocialTerceroList = tercero.getSucursalRazonSocialTerceroList();
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListSucursalRazonSocialTercero : sucursalRazonSocialTerceroList) {
                sucursalRazonSocialTerceroListSucursalRazonSocialTercero.setIdTercero(null);
                sucursalRazonSocialTerceroListSucursalRazonSocialTercero = em.merge(sucursalRazonSocialTerceroListSucursalRazonSocialTercero);
            }
            List<TerceroRazonSocial> terceroRazonSocialList = tercero.getTerceroRazonSocialList();
            for (TerceroRazonSocial terceroRazonSocialListTerceroRazonSocial : terceroRazonSocialList) {
                terceroRazonSocialListTerceroRazonSocial.setIdTercero(null);
                terceroRazonSocialListTerceroRazonSocial = em.merge(terceroRazonSocialListTerceroRazonSocial);
            }
            List<RepresentateLegalRazonSocial> representateLegalRazonSocialList = tercero.getRepresentateLegalRazonSocialList();
            for (RepresentateLegalRazonSocial representateLegalRazonSocialListRepresentateLegalRazonSocial : representateLegalRazonSocialList) {
                representateLegalRazonSocialListRepresentateLegalRazonSocial.setIdTercero(null);
                representateLegalRazonSocialListRepresentateLegalRazonSocial = em.merge(representateLegalRazonSocialListRepresentateLegalRazonSocial);
            }
            List<CuentaBancariaTercero> cuentaBancariaTerceroList = tercero.getCuentaBancariaTerceroList();
            for (CuentaBancariaTercero cuentaBancariaTerceroListCuentaBancariaTercero : cuentaBancariaTerceroList) {
                cuentaBancariaTerceroListCuentaBancariaTercero.setIdTercero(null);
                cuentaBancariaTerceroListCuentaBancariaTercero = em.merge(cuentaBancariaTerceroListCuentaBancariaTercero);
            }
            List<CuentaBancariaTercero> cuentaBancariaTerceroList1 = tercero.getCuentaBancariaTerceroList1();
            for (CuentaBancariaTercero cuentaBancariaTerceroList1CuentaBancariaTercero : cuentaBancariaTerceroList1) {
                cuentaBancariaTerceroList1CuentaBancariaTercero.setIdNombreBanco(null);
                cuentaBancariaTerceroList1CuentaBancariaTercero = em.merge(cuentaBancariaTerceroList1CuentaBancariaTercero);
            }
            List<IdentificacionTercero> identificacionTerceroList = tercero.getIdentificacionTerceroList();
            for (IdentificacionTercero identificacionTerceroListIdentificacionTercero : identificacionTerceroList) {
                identificacionTerceroListIdentificacionTercero.setIdTercero(null);
                identificacionTerceroListIdentificacionTercero = em.merge(identificacionTerceroListIdentificacionTercero);
            }
            em.remove(tercero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tercero> findTerceroEntities() {
        return findTerceroEntities(true, -1, -1);
    }

    public List<Tercero> findTerceroEntities(int maxResults, int firstResult) {
        return findTerceroEntities(false, maxResults, firstResult);
    }

    private List<Tercero> findTerceroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tercero.class));
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

    public Tercero findTercero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tercero.class, id);
        } finally {
            em.close();
        }
    }

    public int getTerceroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tercero> rt = cq.from(Tercero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
