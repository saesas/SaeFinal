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
import com.sae.persistence.domain.Puc;
import com.sae.persistence.domain.RetencionEmpleado;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.TipoTarifaPuc;
import com.sae.persistence.domain.CuentaRetencion;
import com.sae.persistence.domain.ParametroContableFacturacion;
import com.sae.persistence.domain.ComprobanteCierreDetalle;
import com.sae.persistence.domain.CategoriaCuentaInsumo;
import com.sae.persistence.domain.PucNomina;
import com.sae.persistence.domain.FacturaVenta;
import com.sae.persistence.domain.PucServicioPublico;
import com.sae.persistence.domain.MovimientoPuc;
import com.sae.persistence.domain.ClasePuc;
import com.sae.persistence.domain.GrupoServicios;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class PucJpaController implements Serializable {

    public PucJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Puc puc) throws PreexistingEntityException, Exception {
        if (puc.getRetencionEmpleadoList() == null) {
            puc.setRetencionEmpleadoList(new ArrayList<RetencionEmpleado>());
        }
        if (puc.getTipoTarifaPucList() == null) {
            puc.setTipoTarifaPucList(new ArrayList<TipoTarifaPuc>());
        }
        if (puc.getCuentaRetencionList() == null) {
            puc.setCuentaRetencionList(new ArrayList<CuentaRetencion>());
        }
        if (puc.getParametroContableFacturacionList() == null) {
            puc.setParametroContableFacturacionList(new ArrayList<ParametroContableFacturacion>());
        }
        if (puc.getPucList() == null) {
            puc.setPucList(new ArrayList<Puc>());
        }
        if (puc.getComprobanteCierreDetalleList() == null) {
            puc.setComprobanteCierreDetalleList(new ArrayList<ComprobanteCierreDetalle>());
        }
        if (puc.getComprobanteCierreDetalleList1() == null) {
            puc.setComprobanteCierreDetalleList1(new ArrayList<ComprobanteCierreDetalle>());
        }
        if (puc.getComprobanteCierreDetalleList2() == null) {
            puc.setComprobanteCierreDetalleList2(new ArrayList<ComprobanteCierreDetalle>());
        }
        if (puc.getCategoriaCuentaInsumoList() == null) {
            puc.setCategoriaCuentaInsumoList(new ArrayList<CategoriaCuentaInsumo>());
        }
        if (puc.getPucNominaList() == null) {
            puc.setPucNominaList(new ArrayList<PucNomina>());
        }
        if (puc.getPucNominaList1() == null) {
            puc.setPucNominaList1(new ArrayList<PucNomina>());
        }
        if (puc.getFacturaVentaList() == null) {
            puc.setFacturaVentaList(new ArrayList<FacturaVenta>());
        }
        if (puc.getPucServicioPublicoList() == null) {
            puc.setPucServicioPublicoList(new ArrayList<PucServicioPublico>());
        }
        if (puc.getMovimientoPucList() == null) {
            puc.setMovimientoPucList(new ArrayList<MovimientoPuc>());
        }
        if (puc.getClasePucList() == null) {
            puc.setClasePucList(new ArrayList<ClasePuc>());
        }
        if (puc.getGrupoServiciosList() == null) {
            puc.setGrupoServiciosList(new ArrayList<GrupoServicios>());
        }
        if (puc.getGrupoServiciosList1() == null) {
            puc.setGrupoServiciosList1(new ArrayList<GrupoServicios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puc idCuentaMadre = puc.getIdCuentaMadre();
            if (idCuentaMadre != null) {
                idCuentaMadre = em.getReference(idCuentaMadre.getClass(), idCuentaMadre.getId());
                puc.setIdCuentaMadre(idCuentaMadre);
            }
            List<RetencionEmpleado> attachedRetencionEmpleadoList = new ArrayList<RetencionEmpleado>();
            for (RetencionEmpleado retencionEmpleadoListRetencionEmpleadoToAttach : puc.getRetencionEmpleadoList()) {
                retencionEmpleadoListRetencionEmpleadoToAttach = em.getReference(retencionEmpleadoListRetencionEmpleadoToAttach.getClass(), retencionEmpleadoListRetencionEmpleadoToAttach.getId());
                attachedRetencionEmpleadoList.add(retencionEmpleadoListRetencionEmpleadoToAttach);
            }
            puc.setRetencionEmpleadoList(attachedRetencionEmpleadoList);
            List<TipoTarifaPuc> attachedTipoTarifaPucList = new ArrayList<TipoTarifaPuc>();
            for (TipoTarifaPuc tipoTarifaPucListTipoTarifaPucToAttach : puc.getTipoTarifaPucList()) {
                tipoTarifaPucListTipoTarifaPucToAttach = em.getReference(tipoTarifaPucListTipoTarifaPucToAttach.getClass(), tipoTarifaPucListTipoTarifaPucToAttach.getId());
                attachedTipoTarifaPucList.add(tipoTarifaPucListTipoTarifaPucToAttach);
            }
            puc.setTipoTarifaPucList(attachedTipoTarifaPucList);
            List<CuentaRetencion> attachedCuentaRetencionList = new ArrayList<CuentaRetencion>();
            for (CuentaRetencion cuentaRetencionListCuentaRetencionToAttach : puc.getCuentaRetencionList()) {
                cuentaRetencionListCuentaRetencionToAttach = em.getReference(cuentaRetencionListCuentaRetencionToAttach.getClass(), cuentaRetencionListCuentaRetencionToAttach.getId());
                attachedCuentaRetencionList.add(cuentaRetencionListCuentaRetencionToAttach);
            }
            puc.setCuentaRetencionList(attachedCuentaRetencionList);
            List<ParametroContableFacturacion> attachedParametroContableFacturacionList = new ArrayList<ParametroContableFacturacion>();
            for (ParametroContableFacturacion parametroContableFacturacionListParametroContableFacturacionToAttach : puc.getParametroContableFacturacionList()) {
                parametroContableFacturacionListParametroContableFacturacionToAttach = em.getReference(parametroContableFacturacionListParametroContableFacturacionToAttach.getClass(), parametroContableFacturacionListParametroContableFacturacionToAttach.getId());
                attachedParametroContableFacturacionList.add(parametroContableFacturacionListParametroContableFacturacionToAttach);
            }
            puc.setParametroContableFacturacionList(attachedParametroContableFacturacionList);
            List<Puc> attachedPucList = new ArrayList<Puc>();
            for (Puc pucListPucToAttach : puc.getPucList()) {
                pucListPucToAttach = em.getReference(pucListPucToAttach.getClass(), pucListPucToAttach.getId());
                attachedPucList.add(pucListPucToAttach);
            }
            puc.setPucList(attachedPucList);
            List<ComprobanteCierreDetalle> attachedComprobanteCierreDetalleList = new ArrayList<ComprobanteCierreDetalle>();
            for (ComprobanteCierreDetalle comprobanteCierreDetalleListComprobanteCierreDetalleToAttach : puc.getComprobanteCierreDetalleList()) {
                comprobanteCierreDetalleListComprobanteCierreDetalleToAttach = em.getReference(comprobanteCierreDetalleListComprobanteCierreDetalleToAttach.getClass(), comprobanteCierreDetalleListComprobanteCierreDetalleToAttach.getId());
                attachedComprobanteCierreDetalleList.add(comprobanteCierreDetalleListComprobanteCierreDetalleToAttach);
            }
            puc.setComprobanteCierreDetalleList(attachedComprobanteCierreDetalleList);
            List<ComprobanteCierreDetalle> attachedComprobanteCierreDetalleList1 = new ArrayList<ComprobanteCierreDetalle>();
            for (ComprobanteCierreDetalle comprobanteCierreDetalleList1ComprobanteCierreDetalleToAttach : puc.getComprobanteCierreDetalleList1()) {
                comprobanteCierreDetalleList1ComprobanteCierreDetalleToAttach = em.getReference(comprobanteCierreDetalleList1ComprobanteCierreDetalleToAttach.getClass(), comprobanteCierreDetalleList1ComprobanteCierreDetalleToAttach.getId());
                attachedComprobanteCierreDetalleList1.add(comprobanteCierreDetalleList1ComprobanteCierreDetalleToAttach);
            }
            puc.setComprobanteCierreDetalleList1(attachedComprobanteCierreDetalleList1);
            List<ComprobanteCierreDetalle> attachedComprobanteCierreDetalleList2 = new ArrayList<ComprobanteCierreDetalle>();
            for (ComprobanteCierreDetalle comprobanteCierreDetalleList2ComprobanteCierreDetalleToAttach : puc.getComprobanteCierreDetalleList2()) {
                comprobanteCierreDetalleList2ComprobanteCierreDetalleToAttach = em.getReference(comprobanteCierreDetalleList2ComprobanteCierreDetalleToAttach.getClass(), comprobanteCierreDetalleList2ComprobanteCierreDetalleToAttach.getId());
                attachedComprobanteCierreDetalleList2.add(comprobanteCierreDetalleList2ComprobanteCierreDetalleToAttach);
            }
            puc.setComprobanteCierreDetalleList2(attachedComprobanteCierreDetalleList2);
            List<CategoriaCuentaInsumo> attachedCategoriaCuentaInsumoList = new ArrayList<CategoriaCuentaInsumo>();
            for (CategoriaCuentaInsumo categoriaCuentaInsumoListCategoriaCuentaInsumoToAttach : puc.getCategoriaCuentaInsumoList()) {
                categoriaCuentaInsumoListCategoriaCuentaInsumoToAttach = em.getReference(categoriaCuentaInsumoListCategoriaCuentaInsumoToAttach.getClass(), categoriaCuentaInsumoListCategoriaCuentaInsumoToAttach.getId());
                attachedCategoriaCuentaInsumoList.add(categoriaCuentaInsumoListCategoriaCuentaInsumoToAttach);
            }
            puc.setCategoriaCuentaInsumoList(attachedCategoriaCuentaInsumoList);
            List<PucNomina> attachedPucNominaList = new ArrayList<PucNomina>();
            for (PucNomina pucNominaListPucNominaToAttach : puc.getPucNominaList()) {
                pucNominaListPucNominaToAttach = em.getReference(pucNominaListPucNominaToAttach.getClass(), pucNominaListPucNominaToAttach.getId());
                attachedPucNominaList.add(pucNominaListPucNominaToAttach);
            }
            puc.setPucNominaList(attachedPucNominaList);
            List<PucNomina> attachedPucNominaList1 = new ArrayList<PucNomina>();
            for (PucNomina pucNominaList1PucNominaToAttach : puc.getPucNominaList1()) {
                pucNominaList1PucNominaToAttach = em.getReference(pucNominaList1PucNominaToAttach.getClass(), pucNominaList1PucNominaToAttach.getId());
                attachedPucNominaList1.add(pucNominaList1PucNominaToAttach);
            }
            puc.setPucNominaList1(attachedPucNominaList1);
            List<FacturaVenta> attachedFacturaVentaList = new ArrayList<FacturaVenta>();
            for (FacturaVenta facturaVentaListFacturaVentaToAttach : puc.getFacturaVentaList()) {
                facturaVentaListFacturaVentaToAttach = em.getReference(facturaVentaListFacturaVentaToAttach.getClass(), facturaVentaListFacturaVentaToAttach.getId());
                attachedFacturaVentaList.add(facturaVentaListFacturaVentaToAttach);
            }
            puc.setFacturaVentaList(attachedFacturaVentaList);
            List<PucServicioPublico> attachedPucServicioPublicoList = new ArrayList<PucServicioPublico>();
            for (PucServicioPublico pucServicioPublicoListPucServicioPublicoToAttach : puc.getPucServicioPublicoList()) {
                pucServicioPublicoListPucServicioPublicoToAttach = em.getReference(pucServicioPublicoListPucServicioPublicoToAttach.getClass(), pucServicioPublicoListPucServicioPublicoToAttach.getId());
                attachedPucServicioPublicoList.add(pucServicioPublicoListPucServicioPublicoToAttach);
            }
            puc.setPucServicioPublicoList(attachedPucServicioPublicoList);
            List<MovimientoPuc> attachedMovimientoPucList = new ArrayList<MovimientoPuc>();
            for (MovimientoPuc movimientoPucListMovimientoPucToAttach : puc.getMovimientoPucList()) {
                movimientoPucListMovimientoPucToAttach = em.getReference(movimientoPucListMovimientoPucToAttach.getClass(), movimientoPucListMovimientoPucToAttach.getId());
                attachedMovimientoPucList.add(movimientoPucListMovimientoPucToAttach);
            }
            puc.setMovimientoPucList(attachedMovimientoPucList);
            List<ClasePuc> attachedClasePucList = new ArrayList<ClasePuc>();
            for (ClasePuc clasePucListClasePucToAttach : puc.getClasePucList()) {
                clasePucListClasePucToAttach = em.getReference(clasePucListClasePucToAttach.getClass(), clasePucListClasePucToAttach.getId());
                attachedClasePucList.add(clasePucListClasePucToAttach);
            }
            puc.setClasePucList(attachedClasePucList);
            List<GrupoServicios> attachedGrupoServiciosList = new ArrayList<GrupoServicios>();
            for (GrupoServicios grupoServiciosListGrupoServiciosToAttach : puc.getGrupoServiciosList()) {
                grupoServiciosListGrupoServiciosToAttach = em.getReference(grupoServiciosListGrupoServiciosToAttach.getClass(), grupoServiciosListGrupoServiciosToAttach.getId());
                attachedGrupoServiciosList.add(grupoServiciosListGrupoServiciosToAttach);
            }
            puc.setGrupoServiciosList(attachedGrupoServiciosList);
            List<GrupoServicios> attachedGrupoServiciosList1 = new ArrayList<GrupoServicios>();
            for (GrupoServicios grupoServiciosList1GrupoServiciosToAttach : puc.getGrupoServiciosList1()) {
                grupoServiciosList1GrupoServiciosToAttach = em.getReference(grupoServiciosList1GrupoServiciosToAttach.getClass(), grupoServiciosList1GrupoServiciosToAttach.getId());
                attachedGrupoServiciosList1.add(grupoServiciosList1GrupoServiciosToAttach);
            }
            puc.setGrupoServiciosList1(attachedGrupoServiciosList1);
            em.persist(puc);
            if (idCuentaMadre != null) {
                idCuentaMadre.getPucList().add(puc);
                idCuentaMadre = em.merge(idCuentaMadre);
            }
            for (RetencionEmpleado retencionEmpleadoListRetencionEmpleado : puc.getRetencionEmpleadoList()) {
                Puc oldIdPucOfRetencionEmpleadoListRetencionEmpleado = retencionEmpleadoListRetencionEmpleado.getIdPuc();
                retencionEmpleadoListRetencionEmpleado.setIdPuc(puc);
                retencionEmpleadoListRetencionEmpleado = em.merge(retencionEmpleadoListRetencionEmpleado);
                if (oldIdPucOfRetencionEmpleadoListRetencionEmpleado != null) {
                    oldIdPucOfRetencionEmpleadoListRetencionEmpleado.getRetencionEmpleadoList().remove(retencionEmpleadoListRetencionEmpleado);
                    oldIdPucOfRetencionEmpleadoListRetencionEmpleado = em.merge(oldIdPucOfRetencionEmpleadoListRetencionEmpleado);
                }
            }
            for (TipoTarifaPuc tipoTarifaPucListTipoTarifaPuc : puc.getTipoTarifaPucList()) {
                Puc oldIdPucOfTipoTarifaPucListTipoTarifaPuc = tipoTarifaPucListTipoTarifaPuc.getIdPuc();
                tipoTarifaPucListTipoTarifaPuc.setIdPuc(puc);
                tipoTarifaPucListTipoTarifaPuc = em.merge(tipoTarifaPucListTipoTarifaPuc);
                if (oldIdPucOfTipoTarifaPucListTipoTarifaPuc != null) {
                    oldIdPucOfTipoTarifaPucListTipoTarifaPuc.getTipoTarifaPucList().remove(tipoTarifaPucListTipoTarifaPuc);
                    oldIdPucOfTipoTarifaPucListTipoTarifaPuc = em.merge(oldIdPucOfTipoTarifaPucListTipoTarifaPuc);
                }
            }
            for (CuentaRetencion cuentaRetencionListCuentaRetencion : puc.getCuentaRetencionList()) {
                Puc oldIdPucOfCuentaRetencionListCuentaRetencion = cuentaRetencionListCuentaRetencion.getIdPuc();
                cuentaRetencionListCuentaRetencion.setIdPuc(puc);
                cuentaRetencionListCuentaRetencion = em.merge(cuentaRetencionListCuentaRetencion);
                if (oldIdPucOfCuentaRetencionListCuentaRetencion != null) {
                    oldIdPucOfCuentaRetencionListCuentaRetencion.getCuentaRetencionList().remove(cuentaRetencionListCuentaRetencion);
                    oldIdPucOfCuentaRetencionListCuentaRetencion = em.merge(oldIdPucOfCuentaRetencionListCuentaRetencion);
                }
            }
            for (ParametroContableFacturacion parametroContableFacturacionListParametroContableFacturacion : puc.getParametroContableFacturacionList()) {
                Puc oldIdPucOfParametroContableFacturacionListParametroContableFacturacion = parametroContableFacturacionListParametroContableFacturacion.getIdPuc();
                parametroContableFacturacionListParametroContableFacturacion.setIdPuc(puc);
                parametroContableFacturacionListParametroContableFacturacion = em.merge(parametroContableFacturacionListParametroContableFacturacion);
                if (oldIdPucOfParametroContableFacturacionListParametroContableFacturacion != null) {
                    oldIdPucOfParametroContableFacturacionListParametroContableFacturacion.getParametroContableFacturacionList().remove(parametroContableFacturacionListParametroContableFacturacion);
                    oldIdPucOfParametroContableFacturacionListParametroContableFacturacion = em.merge(oldIdPucOfParametroContableFacturacionListParametroContableFacturacion);
                }
            }
            for (Puc pucListPuc : puc.getPucList()) {
                Puc oldIdCuentaMadreOfPucListPuc = pucListPuc.getIdCuentaMadre();
                pucListPuc.setIdCuentaMadre(puc);
                pucListPuc = em.merge(pucListPuc);
                if (oldIdCuentaMadreOfPucListPuc != null) {
                    oldIdCuentaMadreOfPucListPuc.getPucList().remove(pucListPuc);
                    oldIdCuentaMadreOfPucListPuc = em.merge(oldIdCuentaMadreOfPucListPuc);
                }
            }
            for (ComprobanteCierreDetalle comprobanteCierreDetalleListComprobanteCierreDetalle : puc.getComprobanteCierreDetalleList()) {
                Puc oldIdCuentaHastaOfComprobanteCierreDetalleListComprobanteCierreDetalle = comprobanteCierreDetalleListComprobanteCierreDetalle.getIdCuentaHasta();
                comprobanteCierreDetalleListComprobanteCierreDetalle.setIdCuentaHasta(puc);
                comprobanteCierreDetalleListComprobanteCierreDetalle = em.merge(comprobanteCierreDetalleListComprobanteCierreDetalle);
                if (oldIdCuentaHastaOfComprobanteCierreDetalleListComprobanteCierreDetalle != null) {
                    oldIdCuentaHastaOfComprobanteCierreDetalleListComprobanteCierreDetalle.getComprobanteCierreDetalleList().remove(comprobanteCierreDetalleListComprobanteCierreDetalle);
                    oldIdCuentaHastaOfComprobanteCierreDetalleListComprobanteCierreDetalle = em.merge(oldIdCuentaHastaOfComprobanteCierreDetalleListComprobanteCierreDetalle);
                }
            }
            for (ComprobanteCierreDetalle comprobanteCierreDetalleList1ComprobanteCierreDetalle : puc.getComprobanteCierreDetalleList1()) {
                Puc oldIdCuentaDestinoOfComprobanteCierreDetalleList1ComprobanteCierreDetalle = comprobanteCierreDetalleList1ComprobanteCierreDetalle.getIdCuentaDestino();
                comprobanteCierreDetalleList1ComprobanteCierreDetalle.setIdCuentaDestino(puc);
                comprobanteCierreDetalleList1ComprobanteCierreDetalle = em.merge(comprobanteCierreDetalleList1ComprobanteCierreDetalle);
                if (oldIdCuentaDestinoOfComprobanteCierreDetalleList1ComprobanteCierreDetalle != null) {
                    oldIdCuentaDestinoOfComprobanteCierreDetalleList1ComprobanteCierreDetalle.getComprobanteCierreDetalleList1().remove(comprobanteCierreDetalleList1ComprobanteCierreDetalle);
                    oldIdCuentaDestinoOfComprobanteCierreDetalleList1ComprobanteCierreDetalle = em.merge(oldIdCuentaDestinoOfComprobanteCierreDetalleList1ComprobanteCierreDetalle);
                }
            }
            for (ComprobanteCierreDetalle comprobanteCierreDetalleList2ComprobanteCierreDetalle : puc.getComprobanteCierreDetalleList2()) {
                Puc oldIdCuentaDesdeOfComprobanteCierreDetalleList2ComprobanteCierreDetalle = comprobanteCierreDetalleList2ComprobanteCierreDetalle.getIdCuentaDesde();
                comprobanteCierreDetalleList2ComprobanteCierreDetalle.setIdCuentaDesde(puc);
                comprobanteCierreDetalleList2ComprobanteCierreDetalle = em.merge(comprobanteCierreDetalleList2ComprobanteCierreDetalle);
                if (oldIdCuentaDesdeOfComprobanteCierreDetalleList2ComprobanteCierreDetalle != null) {
                    oldIdCuentaDesdeOfComprobanteCierreDetalleList2ComprobanteCierreDetalle.getComprobanteCierreDetalleList2().remove(comprobanteCierreDetalleList2ComprobanteCierreDetalle);
                    oldIdCuentaDesdeOfComprobanteCierreDetalleList2ComprobanteCierreDetalle = em.merge(oldIdCuentaDesdeOfComprobanteCierreDetalleList2ComprobanteCierreDetalle);
                }
            }
            for (CategoriaCuentaInsumo categoriaCuentaInsumoListCategoriaCuentaInsumo : puc.getCategoriaCuentaInsumoList()) {
                Puc oldIdPucOfCategoriaCuentaInsumoListCategoriaCuentaInsumo = categoriaCuentaInsumoListCategoriaCuentaInsumo.getIdPuc();
                categoriaCuentaInsumoListCategoriaCuentaInsumo.setIdPuc(puc);
                categoriaCuentaInsumoListCategoriaCuentaInsumo = em.merge(categoriaCuentaInsumoListCategoriaCuentaInsumo);
                if (oldIdPucOfCategoriaCuentaInsumoListCategoriaCuentaInsumo != null) {
                    oldIdPucOfCategoriaCuentaInsumoListCategoriaCuentaInsumo.getCategoriaCuentaInsumoList().remove(categoriaCuentaInsumoListCategoriaCuentaInsumo);
                    oldIdPucOfCategoriaCuentaInsumoListCategoriaCuentaInsumo = em.merge(oldIdPucOfCategoriaCuentaInsumoListCategoriaCuentaInsumo);
                }
            }
            for (PucNomina pucNominaListPucNomina : puc.getPucNominaList()) {
                Puc oldIdPucDebitoOfPucNominaListPucNomina = pucNominaListPucNomina.getIdPucDebito();
                pucNominaListPucNomina.setIdPucDebito(puc);
                pucNominaListPucNomina = em.merge(pucNominaListPucNomina);
                if (oldIdPucDebitoOfPucNominaListPucNomina != null) {
                    oldIdPucDebitoOfPucNominaListPucNomina.getPucNominaList().remove(pucNominaListPucNomina);
                    oldIdPucDebitoOfPucNominaListPucNomina = em.merge(oldIdPucDebitoOfPucNominaListPucNomina);
                }
            }
            for (PucNomina pucNominaList1PucNomina : puc.getPucNominaList1()) {
                Puc oldIdPucCreditoOfPucNominaList1PucNomina = pucNominaList1PucNomina.getIdPucCredito();
                pucNominaList1PucNomina.setIdPucCredito(puc);
                pucNominaList1PucNomina = em.merge(pucNominaList1PucNomina);
                if (oldIdPucCreditoOfPucNominaList1PucNomina != null) {
                    oldIdPucCreditoOfPucNominaList1PucNomina.getPucNominaList1().remove(pucNominaList1PucNomina);
                    oldIdPucCreditoOfPucNominaList1PucNomina = em.merge(oldIdPucCreditoOfPucNominaList1PucNomina);
                }
            }
            for (FacturaVenta facturaVentaListFacturaVenta : puc.getFacturaVentaList()) {
                Puc oldIdPucOfFacturaVentaListFacturaVenta = facturaVentaListFacturaVenta.getIdPuc();
                facturaVentaListFacturaVenta.setIdPuc(puc);
                facturaVentaListFacturaVenta = em.merge(facturaVentaListFacturaVenta);
                if (oldIdPucOfFacturaVentaListFacturaVenta != null) {
                    oldIdPucOfFacturaVentaListFacturaVenta.getFacturaVentaList().remove(facturaVentaListFacturaVenta);
                    oldIdPucOfFacturaVentaListFacturaVenta = em.merge(oldIdPucOfFacturaVentaListFacturaVenta);
                }
            }
            for (PucServicioPublico pucServicioPublicoListPucServicioPublico : puc.getPucServicioPublicoList()) {
                Puc oldIdPucOfPucServicioPublicoListPucServicioPublico = pucServicioPublicoListPucServicioPublico.getIdPuc();
                pucServicioPublicoListPucServicioPublico.setIdPuc(puc);
                pucServicioPublicoListPucServicioPublico = em.merge(pucServicioPublicoListPucServicioPublico);
                if (oldIdPucOfPucServicioPublicoListPucServicioPublico != null) {
                    oldIdPucOfPucServicioPublicoListPucServicioPublico.getPucServicioPublicoList().remove(pucServicioPublicoListPucServicioPublico);
                    oldIdPucOfPucServicioPublicoListPucServicioPublico = em.merge(oldIdPucOfPucServicioPublicoListPucServicioPublico);
                }
            }
            for (MovimientoPuc movimientoPucListMovimientoPuc : puc.getMovimientoPucList()) {
                Puc oldIdPucOfMovimientoPucListMovimientoPuc = movimientoPucListMovimientoPuc.getIdPuc();
                movimientoPucListMovimientoPuc.setIdPuc(puc);
                movimientoPucListMovimientoPuc = em.merge(movimientoPucListMovimientoPuc);
                if (oldIdPucOfMovimientoPucListMovimientoPuc != null) {
                    oldIdPucOfMovimientoPucListMovimientoPuc.getMovimientoPucList().remove(movimientoPucListMovimientoPuc);
                    oldIdPucOfMovimientoPucListMovimientoPuc = em.merge(oldIdPucOfMovimientoPucListMovimientoPuc);
                }
            }
            for (ClasePuc clasePucListClasePuc : puc.getClasePucList()) {
                Puc oldIdPucOfClasePucListClasePuc = clasePucListClasePuc.getIdPuc();
                clasePucListClasePuc.setIdPuc(puc);
                clasePucListClasePuc = em.merge(clasePucListClasePuc);
                if (oldIdPucOfClasePucListClasePuc != null) {
                    oldIdPucOfClasePucListClasePuc.getClasePucList().remove(clasePucListClasePuc);
                    oldIdPucOfClasePucListClasePuc = em.merge(oldIdPucOfClasePucListClasePuc);
                }
            }
            for (GrupoServicios grupoServiciosListGrupoServicios : puc.getGrupoServiciosList()) {
                Puc oldIdPucGastosAdministrativosOfGrupoServiciosListGrupoServicios = grupoServiciosListGrupoServicios.getIdPucGastosAdministrativos();
                grupoServiciosListGrupoServicios.setIdPucGastosAdministrativos(puc);
                grupoServiciosListGrupoServicios = em.merge(grupoServiciosListGrupoServicios);
                if (oldIdPucGastosAdministrativosOfGrupoServiciosListGrupoServicios != null) {
                    oldIdPucGastosAdministrativosOfGrupoServiciosListGrupoServicios.getGrupoServiciosList().remove(grupoServiciosListGrupoServicios);
                    oldIdPucGastosAdministrativosOfGrupoServiciosListGrupoServicios = em.merge(oldIdPucGastosAdministrativosOfGrupoServiciosListGrupoServicios);
                }
            }
            for (GrupoServicios grupoServiciosList1GrupoServicios : puc.getGrupoServiciosList1()) {
                Puc oldIdPucCostosProduccionOfGrupoServiciosList1GrupoServicios = grupoServiciosList1GrupoServicios.getIdPucCostosProduccion();
                grupoServiciosList1GrupoServicios.setIdPucCostosProduccion(puc);
                grupoServiciosList1GrupoServicios = em.merge(grupoServiciosList1GrupoServicios);
                if (oldIdPucCostosProduccionOfGrupoServiciosList1GrupoServicios != null) {
                    oldIdPucCostosProduccionOfGrupoServiciosList1GrupoServicios.getGrupoServiciosList1().remove(grupoServiciosList1GrupoServicios);
                    oldIdPucCostosProduccionOfGrupoServiciosList1GrupoServicios = em.merge(oldIdPucCostosProduccionOfGrupoServiciosList1GrupoServicios);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPuc(puc.getId()) != null) {
                throw new PreexistingEntityException("Puc " + puc + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Puc puc) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puc persistentPuc = em.find(Puc.class, puc.getId());
            Puc idCuentaMadreOld = persistentPuc.getIdCuentaMadre();
            Puc idCuentaMadreNew = puc.getIdCuentaMadre();
            List<RetencionEmpleado> retencionEmpleadoListOld = persistentPuc.getRetencionEmpleadoList();
            List<RetencionEmpleado> retencionEmpleadoListNew = puc.getRetencionEmpleadoList();
            List<TipoTarifaPuc> tipoTarifaPucListOld = persistentPuc.getTipoTarifaPucList();
            List<TipoTarifaPuc> tipoTarifaPucListNew = puc.getTipoTarifaPucList();
            List<CuentaRetencion> cuentaRetencionListOld = persistentPuc.getCuentaRetencionList();
            List<CuentaRetencion> cuentaRetencionListNew = puc.getCuentaRetencionList();
            List<ParametroContableFacturacion> parametroContableFacturacionListOld = persistentPuc.getParametroContableFacturacionList();
            List<ParametroContableFacturacion> parametroContableFacturacionListNew = puc.getParametroContableFacturacionList();
            List<Puc> pucListOld = persistentPuc.getPucList();
            List<Puc> pucListNew = puc.getPucList();
            List<ComprobanteCierreDetalle> comprobanteCierreDetalleListOld = persistentPuc.getComprobanteCierreDetalleList();
            List<ComprobanteCierreDetalle> comprobanteCierreDetalleListNew = puc.getComprobanteCierreDetalleList();
            List<ComprobanteCierreDetalle> comprobanteCierreDetalleList1Old = persistentPuc.getComprobanteCierreDetalleList1();
            List<ComprobanteCierreDetalle> comprobanteCierreDetalleList1New = puc.getComprobanteCierreDetalleList1();
            List<ComprobanteCierreDetalle> comprobanteCierreDetalleList2Old = persistentPuc.getComprobanteCierreDetalleList2();
            List<ComprobanteCierreDetalle> comprobanteCierreDetalleList2New = puc.getComprobanteCierreDetalleList2();
            List<CategoriaCuentaInsumo> categoriaCuentaInsumoListOld = persistentPuc.getCategoriaCuentaInsumoList();
            List<CategoriaCuentaInsumo> categoriaCuentaInsumoListNew = puc.getCategoriaCuentaInsumoList();
            List<PucNomina> pucNominaListOld = persistentPuc.getPucNominaList();
            List<PucNomina> pucNominaListNew = puc.getPucNominaList();
            List<PucNomina> pucNominaList1Old = persistentPuc.getPucNominaList1();
            List<PucNomina> pucNominaList1New = puc.getPucNominaList1();
            List<FacturaVenta> facturaVentaListOld = persistentPuc.getFacturaVentaList();
            List<FacturaVenta> facturaVentaListNew = puc.getFacturaVentaList();
            List<PucServicioPublico> pucServicioPublicoListOld = persistentPuc.getPucServicioPublicoList();
            List<PucServicioPublico> pucServicioPublicoListNew = puc.getPucServicioPublicoList();
            List<MovimientoPuc> movimientoPucListOld = persistentPuc.getMovimientoPucList();
            List<MovimientoPuc> movimientoPucListNew = puc.getMovimientoPucList();
            List<ClasePuc> clasePucListOld = persistentPuc.getClasePucList();
            List<ClasePuc> clasePucListNew = puc.getClasePucList();
            List<GrupoServicios> grupoServiciosListOld = persistentPuc.getGrupoServiciosList();
            List<GrupoServicios> grupoServiciosListNew = puc.getGrupoServiciosList();
            List<GrupoServicios> grupoServiciosList1Old = persistentPuc.getGrupoServiciosList1();
            List<GrupoServicios> grupoServiciosList1New = puc.getGrupoServiciosList1();
            if (idCuentaMadreNew != null) {
                idCuentaMadreNew = em.getReference(idCuentaMadreNew.getClass(), idCuentaMadreNew.getId());
                puc.setIdCuentaMadre(idCuentaMadreNew);
            }
            List<RetencionEmpleado> attachedRetencionEmpleadoListNew = new ArrayList<RetencionEmpleado>();
            for (RetencionEmpleado retencionEmpleadoListNewRetencionEmpleadoToAttach : retencionEmpleadoListNew) {
                retencionEmpleadoListNewRetencionEmpleadoToAttach = em.getReference(retencionEmpleadoListNewRetencionEmpleadoToAttach.getClass(), retencionEmpleadoListNewRetencionEmpleadoToAttach.getId());
                attachedRetencionEmpleadoListNew.add(retencionEmpleadoListNewRetencionEmpleadoToAttach);
            }
            retencionEmpleadoListNew = attachedRetencionEmpleadoListNew;
            puc.setRetencionEmpleadoList(retencionEmpleadoListNew);
            List<TipoTarifaPuc> attachedTipoTarifaPucListNew = new ArrayList<TipoTarifaPuc>();
            for (TipoTarifaPuc tipoTarifaPucListNewTipoTarifaPucToAttach : tipoTarifaPucListNew) {
                tipoTarifaPucListNewTipoTarifaPucToAttach = em.getReference(tipoTarifaPucListNewTipoTarifaPucToAttach.getClass(), tipoTarifaPucListNewTipoTarifaPucToAttach.getId());
                attachedTipoTarifaPucListNew.add(tipoTarifaPucListNewTipoTarifaPucToAttach);
            }
            tipoTarifaPucListNew = attachedTipoTarifaPucListNew;
            puc.setTipoTarifaPucList(tipoTarifaPucListNew);
            List<CuentaRetencion> attachedCuentaRetencionListNew = new ArrayList<CuentaRetencion>();
            for (CuentaRetencion cuentaRetencionListNewCuentaRetencionToAttach : cuentaRetencionListNew) {
                cuentaRetencionListNewCuentaRetencionToAttach = em.getReference(cuentaRetencionListNewCuentaRetencionToAttach.getClass(), cuentaRetencionListNewCuentaRetencionToAttach.getId());
                attachedCuentaRetencionListNew.add(cuentaRetencionListNewCuentaRetencionToAttach);
            }
            cuentaRetencionListNew = attachedCuentaRetencionListNew;
            puc.setCuentaRetencionList(cuentaRetencionListNew);
            List<ParametroContableFacturacion> attachedParametroContableFacturacionListNew = new ArrayList<ParametroContableFacturacion>();
            for (ParametroContableFacturacion parametroContableFacturacionListNewParametroContableFacturacionToAttach : parametroContableFacturacionListNew) {
                parametroContableFacturacionListNewParametroContableFacturacionToAttach = em.getReference(parametroContableFacturacionListNewParametroContableFacturacionToAttach.getClass(), parametroContableFacturacionListNewParametroContableFacturacionToAttach.getId());
                attachedParametroContableFacturacionListNew.add(parametroContableFacturacionListNewParametroContableFacturacionToAttach);
            }
            parametroContableFacturacionListNew = attachedParametroContableFacturacionListNew;
            puc.setParametroContableFacturacionList(parametroContableFacturacionListNew);
            List<Puc> attachedPucListNew = new ArrayList<Puc>();
            for (Puc pucListNewPucToAttach : pucListNew) {
                pucListNewPucToAttach = em.getReference(pucListNewPucToAttach.getClass(), pucListNewPucToAttach.getId());
                attachedPucListNew.add(pucListNewPucToAttach);
            }
            pucListNew = attachedPucListNew;
            puc.setPucList(pucListNew);
            List<ComprobanteCierreDetalle> attachedComprobanteCierreDetalleListNew = new ArrayList<ComprobanteCierreDetalle>();
            for (ComprobanteCierreDetalle comprobanteCierreDetalleListNewComprobanteCierreDetalleToAttach : comprobanteCierreDetalleListNew) {
                comprobanteCierreDetalleListNewComprobanteCierreDetalleToAttach = em.getReference(comprobanteCierreDetalleListNewComprobanteCierreDetalleToAttach.getClass(), comprobanteCierreDetalleListNewComprobanteCierreDetalleToAttach.getId());
                attachedComprobanteCierreDetalleListNew.add(comprobanteCierreDetalleListNewComprobanteCierreDetalleToAttach);
            }
            comprobanteCierreDetalleListNew = attachedComprobanteCierreDetalleListNew;
            puc.setComprobanteCierreDetalleList(comprobanteCierreDetalleListNew);
            List<ComprobanteCierreDetalle> attachedComprobanteCierreDetalleList1New = new ArrayList<ComprobanteCierreDetalle>();
            for (ComprobanteCierreDetalle comprobanteCierreDetalleList1NewComprobanteCierreDetalleToAttach : comprobanteCierreDetalleList1New) {
                comprobanteCierreDetalleList1NewComprobanteCierreDetalleToAttach = em.getReference(comprobanteCierreDetalleList1NewComprobanteCierreDetalleToAttach.getClass(), comprobanteCierreDetalleList1NewComprobanteCierreDetalleToAttach.getId());
                attachedComprobanteCierreDetalleList1New.add(comprobanteCierreDetalleList1NewComprobanteCierreDetalleToAttach);
            }
            comprobanteCierreDetalleList1New = attachedComprobanteCierreDetalleList1New;
            puc.setComprobanteCierreDetalleList1(comprobanteCierreDetalleList1New);
            List<ComprobanteCierreDetalle> attachedComprobanteCierreDetalleList2New = new ArrayList<ComprobanteCierreDetalle>();
            for (ComprobanteCierreDetalle comprobanteCierreDetalleList2NewComprobanteCierreDetalleToAttach : comprobanteCierreDetalleList2New) {
                comprobanteCierreDetalleList2NewComprobanteCierreDetalleToAttach = em.getReference(comprobanteCierreDetalleList2NewComprobanteCierreDetalleToAttach.getClass(), comprobanteCierreDetalleList2NewComprobanteCierreDetalleToAttach.getId());
                attachedComprobanteCierreDetalleList2New.add(comprobanteCierreDetalleList2NewComprobanteCierreDetalleToAttach);
            }
            comprobanteCierreDetalleList2New = attachedComprobanteCierreDetalleList2New;
            puc.setComprobanteCierreDetalleList2(comprobanteCierreDetalleList2New);
            List<CategoriaCuentaInsumo> attachedCategoriaCuentaInsumoListNew = new ArrayList<CategoriaCuentaInsumo>();
            for (CategoriaCuentaInsumo categoriaCuentaInsumoListNewCategoriaCuentaInsumoToAttach : categoriaCuentaInsumoListNew) {
                categoriaCuentaInsumoListNewCategoriaCuentaInsumoToAttach = em.getReference(categoriaCuentaInsumoListNewCategoriaCuentaInsumoToAttach.getClass(), categoriaCuentaInsumoListNewCategoriaCuentaInsumoToAttach.getId());
                attachedCategoriaCuentaInsumoListNew.add(categoriaCuentaInsumoListNewCategoriaCuentaInsumoToAttach);
            }
            categoriaCuentaInsumoListNew = attachedCategoriaCuentaInsumoListNew;
            puc.setCategoriaCuentaInsumoList(categoriaCuentaInsumoListNew);
            List<PucNomina> attachedPucNominaListNew = new ArrayList<PucNomina>();
            for (PucNomina pucNominaListNewPucNominaToAttach : pucNominaListNew) {
                pucNominaListNewPucNominaToAttach = em.getReference(pucNominaListNewPucNominaToAttach.getClass(), pucNominaListNewPucNominaToAttach.getId());
                attachedPucNominaListNew.add(pucNominaListNewPucNominaToAttach);
            }
            pucNominaListNew = attachedPucNominaListNew;
            puc.setPucNominaList(pucNominaListNew);
            List<PucNomina> attachedPucNominaList1New = new ArrayList<PucNomina>();
            for (PucNomina pucNominaList1NewPucNominaToAttach : pucNominaList1New) {
                pucNominaList1NewPucNominaToAttach = em.getReference(pucNominaList1NewPucNominaToAttach.getClass(), pucNominaList1NewPucNominaToAttach.getId());
                attachedPucNominaList1New.add(pucNominaList1NewPucNominaToAttach);
            }
            pucNominaList1New = attachedPucNominaList1New;
            puc.setPucNominaList1(pucNominaList1New);
            List<FacturaVenta> attachedFacturaVentaListNew = new ArrayList<FacturaVenta>();
            for (FacturaVenta facturaVentaListNewFacturaVentaToAttach : facturaVentaListNew) {
                facturaVentaListNewFacturaVentaToAttach = em.getReference(facturaVentaListNewFacturaVentaToAttach.getClass(), facturaVentaListNewFacturaVentaToAttach.getId());
                attachedFacturaVentaListNew.add(facturaVentaListNewFacturaVentaToAttach);
            }
            facturaVentaListNew = attachedFacturaVentaListNew;
            puc.setFacturaVentaList(facturaVentaListNew);
            List<PucServicioPublico> attachedPucServicioPublicoListNew = new ArrayList<PucServicioPublico>();
            for (PucServicioPublico pucServicioPublicoListNewPucServicioPublicoToAttach : pucServicioPublicoListNew) {
                pucServicioPublicoListNewPucServicioPublicoToAttach = em.getReference(pucServicioPublicoListNewPucServicioPublicoToAttach.getClass(), pucServicioPublicoListNewPucServicioPublicoToAttach.getId());
                attachedPucServicioPublicoListNew.add(pucServicioPublicoListNewPucServicioPublicoToAttach);
            }
            pucServicioPublicoListNew = attachedPucServicioPublicoListNew;
            puc.setPucServicioPublicoList(pucServicioPublicoListNew);
            List<MovimientoPuc> attachedMovimientoPucListNew = new ArrayList<MovimientoPuc>();
            for (MovimientoPuc movimientoPucListNewMovimientoPucToAttach : movimientoPucListNew) {
                movimientoPucListNewMovimientoPucToAttach = em.getReference(movimientoPucListNewMovimientoPucToAttach.getClass(), movimientoPucListNewMovimientoPucToAttach.getId());
                attachedMovimientoPucListNew.add(movimientoPucListNewMovimientoPucToAttach);
            }
            movimientoPucListNew = attachedMovimientoPucListNew;
            puc.setMovimientoPucList(movimientoPucListNew);
            List<ClasePuc> attachedClasePucListNew = new ArrayList<ClasePuc>();
            for (ClasePuc clasePucListNewClasePucToAttach : clasePucListNew) {
                clasePucListNewClasePucToAttach = em.getReference(clasePucListNewClasePucToAttach.getClass(), clasePucListNewClasePucToAttach.getId());
                attachedClasePucListNew.add(clasePucListNewClasePucToAttach);
            }
            clasePucListNew = attachedClasePucListNew;
            puc.setClasePucList(clasePucListNew);
            List<GrupoServicios> attachedGrupoServiciosListNew = new ArrayList<GrupoServicios>();
            for (GrupoServicios grupoServiciosListNewGrupoServiciosToAttach : grupoServiciosListNew) {
                grupoServiciosListNewGrupoServiciosToAttach = em.getReference(grupoServiciosListNewGrupoServiciosToAttach.getClass(), grupoServiciosListNewGrupoServiciosToAttach.getId());
                attachedGrupoServiciosListNew.add(grupoServiciosListNewGrupoServiciosToAttach);
            }
            grupoServiciosListNew = attachedGrupoServiciosListNew;
            puc.setGrupoServiciosList(grupoServiciosListNew);
            List<GrupoServicios> attachedGrupoServiciosList1New = new ArrayList<GrupoServicios>();
            for (GrupoServicios grupoServiciosList1NewGrupoServiciosToAttach : grupoServiciosList1New) {
                grupoServiciosList1NewGrupoServiciosToAttach = em.getReference(grupoServiciosList1NewGrupoServiciosToAttach.getClass(), grupoServiciosList1NewGrupoServiciosToAttach.getId());
                attachedGrupoServiciosList1New.add(grupoServiciosList1NewGrupoServiciosToAttach);
            }
            grupoServiciosList1New = attachedGrupoServiciosList1New;
            puc.setGrupoServiciosList1(grupoServiciosList1New);
            puc = em.merge(puc);
            if (idCuentaMadreOld != null && !idCuentaMadreOld.equals(idCuentaMadreNew)) {
                idCuentaMadreOld.getPucList().remove(puc);
                idCuentaMadreOld = em.merge(idCuentaMadreOld);
            }
            if (idCuentaMadreNew != null && !idCuentaMadreNew.equals(idCuentaMadreOld)) {
                idCuentaMadreNew.getPucList().add(puc);
                idCuentaMadreNew = em.merge(idCuentaMadreNew);
            }
            for (RetencionEmpleado retencionEmpleadoListOldRetencionEmpleado : retencionEmpleadoListOld) {
                if (!retencionEmpleadoListNew.contains(retencionEmpleadoListOldRetencionEmpleado)) {
                    retencionEmpleadoListOldRetencionEmpleado.setIdPuc(null);
                    retencionEmpleadoListOldRetencionEmpleado = em.merge(retencionEmpleadoListOldRetencionEmpleado);
                }
            }
            for (RetencionEmpleado retencionEmpleadoListNewRetencionEmpleado : retencionEmpleadoListNew) {
                if (!retencionEmpleadoListOld.contains(retencionEmpleadoListNewRetencionEmpleado)) {
                    Puc oldIdPucOfRetencionEmpleadoListNewRetencionEmpleado = retencionEmpleadoListNewRetencionEmpleado.getIdPuc();
                    retencionEmpleadoListNewRetencionEmpleado.setIdPuc(puc);
                    retencionEmpleadoListNewRetencionEmpleado = em.merge(retencionEmpleadoListNewRetencionEmpleado);
                    if (oldIdPucOfRetencionEmpleadoListNewRetencionEmpleado != null && !oldIdPucOfRetencionEmpleadoListNewRetencionEmpleado.equals(puc)) {
                        oldIdPucOfRetencionEmpleadoListNewRetencionEmpleado.getRetencionEmpleadoList().remove(retencionEmpleadoListNewRetencionEmpleado);
                        oldIdPucOfRetencionEmpleadoListNewRetencionEmpleado = em.merge(oldIdPucOfRetencionEmpleadoListNewRetencionEmpleado);
                    }
                }
            }
            for (TipoTarifaPuc tipoTarifaPucListOldTipoTarifaPuc : tipoTarifaPucListOld) {
                if (!tipoTarifaPucListNew.contains(tipoTarifaPucListOldTipoTarifaPuc)) {
                    tipoTarifaPucListOldTipoTarifaPuc.setIdPuc(null);
                    tipoTarifaPucListOldTipoTarifaPuc = em.merge(tipoTarifaPucListOldTipoTarifaPuc);
                }
            }
            for (TipoTarifaPuc tipoTarifaPucListNewTipoTarifaPuc : tipoTarifaPucListNew) {
                if (!tipoTarifaPucListOld.contains(tipoTarifaPucListNewTipoTarifaPuc)) {
                    Puc oldIdPucOfTipoTarifaPucListNewTipoTarifaPuc = tipoTarifaPucListNewTipoTarifaPuc.getIdPuc();
                    tipoTarifaPucListNewTipoTarifaPuc.setIdPuc(puc);
                    tipoTarifaPucListNewTipoTarifaPuc = em.merge(tipoTarifaPucListNewTipoTarifaPuc);
                    if (oldIdPucOfTipoTarifaPucListNewTipoTarifaPuc != null && !oldIdPucOfTipoTarifaPucListNewTipoTarifaPuc.equals(puc)) {
                        oldIdPucOfTipoTarifaPucListNewTipoTarifaPuc.getTipoTarifaPucList().remove(tipoTarifaPucListNewTipoTarifaPuc);
                        oldIdPucOfTipoTarifaPucListNewTipoTarifaPuc = em.merge(oldIdPucOfTipoTarifaPucListNewTipoTarifaPuc);
                    }
                }
            }
            for (CuentaRetencion cuentaRetencionListOldCuentaRetencion : cuentaRetencionListOld) {
                if (!cuentaRetencionListNew.contains(cuentaRetencionListOldCuentaRetencion)) {
                    cuentaRetencionListOldCuentaRetencion.setIdPuc(null);
                    cuentaRetencionListOldCuentaRetencion = em.merge(cuentaRetencionListOldCuentaRetencion);
                }
            }
            for (CuentaRetencion cuentaRetencionListNewCuentaRetencion : cuentaRetencionListNew) {
                if (!cuentaRetencionListOld.contains(cuentaRetencionListNewCuentaRetencion)) {
                    Puc oldIdPucOfCuentaRetencionListNewCuentaRetencion = cuentaRetencionListNewCuentaRetencion.getIdPuc();
                    cuentaRetencionListNewCuentaRetencion.setIdPuc(puc);
                    cuentaRetencionListNewCuentaRetencion = em.merge(cuentaRetencionListNewCuentaRetencion);
                    if (oldIdPucOfCuentaRetencionListNewCuentaRetencion != null && !oldIdPucOfCuentaRetencionListNewCuentaRetencion.equals(puc)) {
                        oldIdPucOfCuentaRetencionListNewCuentaRetencion.getCuentaRetencionList().remove(cuentaRetencionListNewCuentaRetencion);
                        oldIdPucOfCuentaRetencionListNewCuentaRetencion = em.merge(oldIdPucOfCuentaRetencionListNewCuentaRetencion);
                    }
                }
            }
            for (ParametroContableFacturacion parametroContableFacturacionListOldParametroContableFacturacion : parametroContableFacturacionListOld) {
                if (!parametroContableFacturacionListNew.contains(parametroContableFacturacionListOldParametroContableFacturacion)) {
                    parametroContableFacturacionListOldParametroContableFacturacion.setIdPuc(null);
                    parametroContableFacturacionListOldParametroContableFacturacion = em.merge(parametroContableFacturacionListOldParametroContableFacturacion);
                }
            }
            for (ParametroContableFacturacion parametroContableFacturacionListNewParametroContableFacturacion : parametroContableFacturacionListNew) {
                if (!parametroContableFacturacionListOld.contains(parametroContableFacturacionListNewParametroContableFacturacion)) {
                    Puc oldIdPucOfParametroContableFacturacionListNewParametroContableFacturacion = parametroContableFacturacionListNewParametroContableFacturacion.getIdPuc();
                    parametroContableFacturacionListNewParametroContableFacturacion.setIdPuc(puc);
                    parametroContableFacturacionListNewParametroContableFacturacion = em.merge(parametroContableFacturacionListNewParametroContableFacturacion);
                    if (oldIdPucOfParametroContableFacturacionListNewParametroContableFacturacion != null && !oldIdPucOfParametroContableFacturacionListNewParametroContableFacturacion.equals(puc)) {
                        oldIdPucOfParametroContableFacturacionListNewParametroContableFacturacion.getParametroContableFacturacionList().remove(parametroContableFacturacionListNewParametroContableFacturacion);
                        oldIdPucOfParametroContableFacturacionListNewParametroContableFacturacion = em.merge(oldIdPucOfParametroContableFacturacionListNewParametroContableFacturacion);
                    }
                }
            }
            for (Puc pucListOldPuc : pucListOld) {
                if (!pucListNew.contains(pucListOldPuc)) {
                    pucListOldPuc.setIdCuentaMadre(null);
                    pucListOldPuc = em.merge(pucListOldPuc);
                }
            }
            for (Puc pucListNewPuc : pucListNew) {
                if (!pucListOld.contains(pucListNewPuc)) {
                    Puc oldIdCuentaMadreOfPucListNewPuc = pucListNewPuc.getIdCuentaMadre();
                    pucListNewPuc.setIdCuentaMadre(puc);
                    pucListNewPuc = em.merge(pucListNewPuc);
                    if (oldIdCuentaMadreOfPucListNewPuc != null && !oldIdCuentaMadreOfPucListNewPuc.equals(puc)) {
                        oldIdCuentaMadreOfPucListNewPuc.getPucList().remove(pucListNewPuc);
                        oldIdCuentaMadreOfPucListNewPuc = em.merge(oldIdCuentaMadreOfPucListNewPuc);
                    }
                }
            }
            for (ComprobanteCierreDetalle comprobanteCierreDetalleListOldComprobanteCierreDetalle : comprobanteCierreDetalleListOld) {
                if (!comprobanteCierreDetalleListNew.contains(comprobanteCierreDetalleListOldComprobanteCierreDetalle)) {
                    comprobanteCierreDetalleListOldComprobanteCierreDetalle.setIdCuentaHasta(null);
                    comprobanteCierreDetalleListOldComprobanteCierreDetalle = em.merge(comprobanteCierreDetalleListOldComprobanteCierreDetalle);
                }
            }
            for (ComprobanteCierreDetalle comprobanteCierreDetalleListNewComprobanteCierreDetalle : comprobanteCierreDetalleListNew) {
                if (!comprobanteCierreDetalleListOld.contains(comprobanteCierreDetalleListNewComprobanteCierreDetalle)) {
                    Puc oldIdCuentaHastaOfComprobanteCierreDetalleListNewComprobanteCierreDetalle = comprobanteCierreDetalleListNewComprobanteCierreDetalle.getIdCuentaHasta();
                    comprobanteCierreDetalleListNewComprobanteCierreDetalle.setIdCuentaHasta(puc);
                    comprobanteCierreDetalleListNewComprobanteCierreDetalle = em.merge(comprobanteCierreDetalleListNewComprobanteCierreDetalle);
                    if (oldIdCuentaHastaOfComprobanteCierreDetalleListNewComprobanteCierreDetalle != null && !oldIdCuentaHastaOfComprobanteCierreDetalleListNewComprobanteCierreDetalle.equals(puc)) {
                        oldIdCuentaHastaOfComprobanteCierreDetalleListNewComprobanteCierreDetalle.getComprobanteCierreDetalleList().remove(comprobanteCierreDetalleListNewComprobanteCierreDetalle);
                        oldIdCuentaHastaOfComprobanteCierreDetalleListNewComprobanteCierreDetalle = em.merge(oldIdCuentaHastaOfComprobanteCierreDetalleListNewComprobanteCierreDetalle);
                    }
                }
            }
            for (ComprobanteCierreDetalle comprobanteCierreDetalleList1OldComprobanteCierreDetalle : comprobanteCierreDetalleList1Old) {
                if (!comprobanteCierreDetalleList1New.contains(comprobanteCierreDetalleList1OldComprobanteCierreDetalle)) {
                    comprobanteCierreDetalleList1OldComprobanteCierreDetalle.setIdCuentaDestino(null);
                    comprobanteCierreDetalleList1OldComprobanteCierreDetalle = em.merge(comprobanteCierreDetalleList1OldComprobanteCierreDetalle);
                }
            }
            for (ComprobanteCierreDetalle comprobanteCierreDetalleList1NewComprobanteCierreDetalle : comprobanteCierreDetalleList1New) {
                if (!comprobanteCierreDetalleList1Old.contains(comprobanteCierreDetalleList1NewComprobanteCierreDetalle)) {
                    Puc oldIdCuentaDestinoOfComprobanteCierreDetalleList1NewComprobanteCierreDetalle = comprobanteCierreDetalleList1NewComprobanteCierreDetalle.getIdCuentaDestino();
                    comprobanteCierreDetalleList1NewComprobanteCierreDetalle.setIdCuentaDestino(puc);
                    comprobanteCierreDetalleList1NewComprobanteCierreDetalle = em.merge(comprobanteCierreDetalleList1NewComprobanteCierreDetalle);
                    if (oldIdCuentaDestinoOfComprobanteCierreDetalleList1NewComprobanteCierreDetalle != null && !oldIdCuentaDestinoOfComprobanteCierreDetalleList1NewComprobanteCierreDetalle.equals(puc)) {
                        oldIdCuentaDestinoOfComprobanteCierreDetalleList1NewComprobanteCierreDetalle.getComprobanteCierreDetalleList1().remove(comprobanteCierreDetalleList1NewComprobanteCierreDetalle);
                        oldIdCuentaDestinoOfComprobanteCierreDetalleList1NewComprobanteCierreDetalle = em.merge(oldIdCuentaDestinoOfComprobanteCierreDetalleList1NewComprobanteCierreDetalle);
                    }
                }
            }
            for (ComprobanteCierreDetalle comprobanteCierreDetalleList2OldComprobanteCierreDetalle : comprobanteCierreDetalleList2Old) {
                if (!comprobanteCierreDetalleList2New.contains(comprobanteCierreDetalleList2OldComprobanteCierreDetalle)) {
                    comprobanteCierreDetalleList2OldComprobanteCierreDetalle.setIdCuentaDesde(null);
                    comprobanteCierreDetalleList2OldComprobanteCierreDetalle = em.merge(comprobanteCierreDetalleList2OldComprobanteCierreDetalle);
                }
            }
            for (ComprobanteCierreDetalle comprobanteCierreDetalleList2NewComprobanteCierreDetalle : comprobanteCierreDetalleList2New) {
                if (!comprobanteCierreDetalleList2Old.contains(comprobanteCierreDetalleList2NewComprobanteCierreDetalle)) {
                    Puc oldIdCuentaDesdeOfComprobanteCierreDetalleList2NewComprobanteCierreDetalle = comprobanteCierreDetalleList2NewComprobanteCierreDetalle.getIdCuentaDesde();
                    comprobanteCierreDetalleList2NewComprobanteCierreDetalle.setIdCuentaDesde(puc);
                    comprobanteCierreDetalleList2NewComprobanteCierreDetalle = em.merge(comprobanteCierreDetalleList2NewComprobanteCierreDetalle);
                    if (oldIdCuentaDesdeOfComprobanteCierreDetalleList2NewComprobanteCierreDetalle != null && !oldIdCuentaDesdeOfComprobanteCierreDetalleList2NewComprobanteCierreDetalle.equals(puc)) {
                        oldIdCuentaDesdeOfComprobanteCierreDetalleList2NewComprobanteCierreDetalle.getComprobanteCierreDetalleList2().remove(comprobanteCierreDetalleList2NewComprobanteCierreDetalle);
                        oldIdCuentaDesdeOfComprobanteCierreDetalleList2NewComprobanteCierreDetalle = em.merge(oldIdCuentaDesdeOfComprobanteCierreDetalleList2NewComprobanteCierreDetalle);
                    }
                }
            }
            for (CategoriaCuentaInsumo categoriaCuentaInsumoListOldCategoriaCuentaInsumo : categoriaCuentaInsumoListOld) {
                if (!categoriaCuentaInsumoListNew.contains(categoriaCuentaInsumoListOldCategoriaCuentaInsumo)) {
                    categoriaCuentaInsumoListOldCategoriaCuentaInsumo.setIdPuc(null);
                    categoriaCuentaInsumoListOldCategoriaCuentaInsumo = em.merge(categoriaCuentaInsumoListOldCategoriaCuentaInsumo);
                }
            }
            for (CategoriaCuentaInsumo categoriaCuentaInsumoListNewCategoriaCuentaInsumo : categoriaCuentaInsumoListNew) {
                if (!categoriaCuentaInsumoListOld.contains(categoriaCuentaInsumoListNewCategoriaCuentaInsumo)) {
                    Puc oldIdPucOfCategoriaCuentaInsumoListNewCategoriaCuentaInsumo = categoriaCuentaInsumoListNewCategoriaCuentaInsumo.getIdPuc();
                    categoriaCuentaInsumoListNewCategoriaCuentaInsumo.setIdPuc(puc);
                    categoriaCuentaInsumoListNewCategoriaCuentaInsumo = em.merge(categoriaCuentaInsumoListNewCategoriaCuentaInsumo);
                    if (oldIdPucOfCategoriaCuentaInsumoListNewCategoriaCuentaInsumo != null && !oldIdPucOfCategoriaCuentaInsumoListNewCategoriaCuentaInsumo.equals(puc)) {
                        oldIdPucOfCategoriaCuentaInsumoListNewCategoriaCuentaInsumo.getCategoriaCuentaInsumoList().remove(categoriaCuentaInsumoListNewCategoriaCuentaInsumo);
                        oldIdPucOfCategoriaCuentaInsumoListNewCategoriaCuentaInsumo = em.merge(oldIdPucOfCategoriaCuentaInsumoListNewCategoriaCuentaInsumo);
                    }
                }
            }
            for (PucNomina pucNominaListOldPucNomina : pucNominaListOld) {
                if (!pucNominaListNew.contains(pucNominaListOldPucNomina)) {
                    pucNominaListOldPucNomina.setIdPucDebito(null);
                    pucNominaListOldPucNomina = em.merge(pucNominaListOldPucNomina);
                }
            }
            for (PucNomina pucNominaListNewPucNomina : pucNominaListNew) {
                if (!pucNominaListOld.contains(pucNominaListNewPucNomina)) {
                    Puc oldIdPucDebitoOfPucNominaListNewPucNomina = pucNominaListNewPucNomina.getIdPucDebito();
                    pucNominaListNewPucNomina.setIdPucDebito(puc);
                    pucNominaListNewPucNomina = em.merge(pucNominaListNewPucNomina);
                    if (oldIdPucDebitoOfPucNominaListNewPucNomina != null && !oldIdPucDebitoOfPucNominaListNewPucNomina.equals(puc)) {
                        oldIdPucDebitoOfPucNominaListNewPucNomina.getPucNominaList().remove(pucNominaListNewPucNomina);
                        oldIdPucDebitoOfPucNominaListNewPucNomina = em.merge(oldIdPucDebitoOfPucNominaListNewPucNomina);
                    }
                }
            }
            for (PucNomina pucNominaList1OldPucNomina : pucNominaList1Old) {
                if (!pucNominaList1New.contains(pucNominaList1OldPucNomina)) {
                    pucNominaList1OldPucNomina.setIdPucCredito(null);
                    pucNominaList1OldPucNomina = em.merge(pucNominaList1OldPucNomina);
                }
            }
            for (PucNomina pucNominaList1NewPucNomina : pucNominaList1New) {
                if (!pucNominaList1Old.contains(pucNominaList1NewPucNomina)) {
                    Puc oldIdPucCreditoOfPucNominaList1NewPucNomina = pucNominaList1NewPucNomina.getIdPucCredito();
                    pucNominaList1NewPucNomina.setIdPucCredito(puc);
                    pucNominaList1NewPucNomina = em.merge(pucNominaList1NewPucNomina);
                    if (oldIdPucCreditoOfPucNominaList1NewPucNomina != null && !oldIdPucCreditoOfPucNominaList1NewPucNomina.equals(puc)) {
                        oldIdPucCreditoOfPucNominaList1NewPucNomina.getPucNominaList1().remove(pucNominaList1NewPucNomina);
                        oldIdPucCreditoOfPucNominaList1NewPucNomina = em.merge(oldIdPucCreditoOfPucNominaList1NewPucNomina);
                    }
                }
            }
            for (FacturaVenta facturaVentaListOldFacturaVenta : facturaVentaListOld) {
                if (!facturaVentaListNew.contains(facturaVentaListOldFacturaVenta)) {
                    facturaVentaListOldFacturaVenta.setIdPuc(null);
                    facturaVentaListOldFacturaVenta = em.merge(facturaVentaListOldFacturaVenta);
                }
            }
            for (FacturaVenta facturaVentaListNewFacturaVenta : facturaVentaListNew) {
                if (!facturaVentaListOld.contains(facturaVentaListNewFacturaVenta)) {
                    Puc oldIdPucOfFacturaVentaListNewFacturaVenta = facturaVentaListNewFacturaVenta.getIdPuc();
                    facturaVentaListNewFacturaVenta.setIdPuc(puc);
                    facturaVentaListNewFacturaVenta = em.merge(facturaVentaListNewFacturaVenta);
                    if (oldIdPucOfFacturaVentaListNewFacturaVenta != null && !oldIdPucOfFacturaVentaListNewFacturaVenta.equals(puc)) {
                        oldIdPucOfFacturaVentaListNewFacturaVenta.getFacturaVentaList().remove(facturaVentaListNewFacturaVenta);
                        oldIdPucOfFacturaVentaListNewFacturaVenta = em.merge(oldIdPucOfFacturaVentaListNewFacturaVenta);
                    }
                }
            }
            for (PucServicioPublico pucServicioPublicoListOldPucServicioPublico : pucServicioPublicoListOld) {
                if (!pucServicioPublicoListNew.contains(pucServicioPublicoListOldPucServicioPublico)) {
                    pucServicioPublicoListOldPucServicioPublico.setIdPuc(null);
                    pucServicioPublicoListOldPucServicioPublico = em.merge(pucServicioPublicoListOldPucServicioPublico);
                }
            }
            for (PucServicioPublico pucServicioPublicoListNewPucServicioPublico : pucServicioPublicoListNew) {
                if (!pucServicioPublicoListOld.contains(pucServicioPublicoListNewPucServicioPublico)) {
                    Puc oldIdPucOfPucServicioPublicoListNewPucServicioPublico = pucServicioPublicoListNewPucServicioPublico.getIdPuc();
                    pucServicioPublicoListNewPucServicioPublico.setIdPuc(puc);
                    pucServicioPublicoListNewPucServicioPublico = em.merge(pucServicioPublicoListNewPucServicioPublico);
                    if (oldIdPucOfPucServicioPublicoListNewPucServicioPublico != null && !oldIdPucOfPucServicioPublicoListNewPucServicioPublico.equals(puc)) {
                        oldIdPucOfPucServicioPublicoListNewPucServicioPublico.getPucServicioPublicoList().remove(pucServicioPublicoListNewPucServicioPublico);
                        oldIdPucOfPucServicioPublicoListNewPucServicioPublico = em.merge(oldIdPucOfPucServicioPublicoListNewPucServicioPublico);
                    }
                }
            }
            for (MovimientoPuc movimientoPucListOldMovimientoPuc : movimientoPucListOld) {
                if (!movimientoPucListNew.contains(movimientoPucListOldMovimientoPuc)) {
                    movimientoPucListOldMovimientoPuc.setIdPuc(null);
                    movimientoPucListOldMovimientoPuc = em.merge(movimientoPucListOldMovimientoPuc);
                }
            }
            for (MovimientoPuc movimientoPucListNewMovimientoPuc : movimientoPucListNew) {
                if (!movimientoPucListOld.contains(movimientoPucListNewMovimientoPuc)) {
                    Puc oldIdPucOfMovimientoPucListNewMovimientoPuc = movimientoPucListNewMovimientoPuc.getIdPuc();
                    movimientoPucListNewMovimientoPuc.setIdPuc(puc);
                    movimientoPucListNewMovimientoPuc = em.merge(movimientoPucListNewMovimientoPuc);
                    if (oldIdPucOfMovimientoPucListNewMovimientoPuc != null && !oldIdPucOfMovimientoPucListNewMovimientoPuc.equals(puc)) {
                        oldIdPucOfMovimientoPucListNewMovimientoPuc.getMovimientoPucList().remove(movimientoPucListNewMovimientoPuc);
                        oldIdPucOfMovimientoPucListNewMovimientoPuc = em.merge(oldIdPucOfMovimientoPucListNewMovimientoPuc);
                    }
                }
            }
            for (ClasePuc clasePucListOldClasePuc : clasePucListOld) {
                if (!clasePucListNew.contains(clasePucListOldClasePuc)) {
                    clasePucListOldClasePuc.setIdPuc(null);
                    clasePucListOldClasePuc = em.merge(clasePucListOldClasePuc);
                }
            }
            for (ClasePuc clasePucListNewClasePuc : clasePucListNew) {
                if (!clasePucListOld.contains(clasePucListNewClasePuc)) {
                    Puc oldIdPucOfClasePucListNewClasePuc = clasePucListNewClasePuc.getIdPuc();
                    clasePucListNewClasePuc.setIdPuc(puc);
                    clasePucListNewClasePuc = em.merge(clasePucListNewClasePuc);
                    if (oldIdPucOfClasePucListNewClasePuc != null && !oldIdPucOfClasePucListNewClasePuc.equals(puc)) {
                        oldIdPucOfClasePucListNewClasePuc.getClasePucList().remove(clasePucListNewClasePuc);
                        oldIdPucOfClasePucListNewClasePuc = em.merge(oldIdPucOfClasePucListNewClasePuc);
                    }
                }
            }
            for (GrupoServicios grupoServiciosListOldGrupoServicios : grupoServiciosListOld) {
                if (!grupoServiciosListNew.contains(grupoServiciosListOldGrupoServicios)) {
                    grupoServiciosListOldGrupoServicios.setIdPucGastosAdministrativos(null);
                    grupoServiciosListOldGrupoServicios = em.merge(grupoServiciosListOldGrupoServicios);
                }
            }
            for (GrupoServicios grupoServiciosListNewGrupoServicios : grupoServiciosListNew) {
                if (!grupoServiciosListOld.contains(grupoServiciosListNewGrupoServicios)) {
                    Puc oldIdPucGastosAdministrativosOfGrupoServiciosListNewGrupoServicios = grupoServiciosListNewGrupoServicios.getIdPucGastosAdministrativos();
                    grupoServiciosListNewGrupoServicios.setIdPucGastosAdministrativos(puc);
                    grupoServiciosListNewGrupoServicios = em.merge(grupoServiciosListNewGrupoServicios);
                    if (oldIdPucGastosAdministrativosOfGrupoServiciosListNewGrupoServicios != null && !oldIdPucGastosAdministrativosOfGrupoServiciosListNewGrupoServicios.equals(puc)) {
                        oldIdPucGastosAdministrativosOfGrupoServiciosListNewGrupoServicios.getGrupoServiciosList().remove(grupoServiciosListNewGrupoServicios);
                        oldIdPucGastosAdministrativosOfGrupoServiciosListNewGrupoServicios = em.merge(oldIdPucGastosAdministrativosOfGrupoServiciosListNewGrupoServicios);
                    }
                }
            }
            for (GrupoServicios grupoServiciosList1OldGrupoServicios : grupoServiciosList1Old) {
                if (!grupoServiciosList1New.contains(grupoServiciosList1OldGrupoServicios)) {
                    grupoServiciosList1OldGrupoServicios.setIdPucCostosProduccion(null);
                    grupoServiciosList1OldGrupoServicios = em.merge(grupoServiciosList1OldGrupoServicios);
                }
            }
            for (GrupoServicios grupoServiciosList1NewGrupoServicios : grupoServiciosList1New) {
                if (!grupoServiciosList1Old.contains(grupoServiciosList1NewGrupoServicios)) {
                    Puc oldIdPucCostosProduccionOfGrupoServiciosList1NewGrupoServicios = grupoServiciosList1NewGrupoServicios.getIdPucCostosProduccion();
                    grupoServiciosList1NewGrupoServicios.setIdPucCostosProduccion(puc);
                    grupoServiciosList1NewGrupoServicios = em.merge(grupoServiciosList1NewGrupoServicios);
                    if (oldIdPucCostosProduccionOfGrupoServiciosList1NewGrupoServicios != null && !oldIdPucCostosProduccionOfGrupoServiciosList1NewGrupoServicios.equals(puc)) {
                        oldIdPucCostosProduccionOfGrupoServiciosList1NewGrupoServicios.getGrupoServiciosList1().remove(grupoServiciosList1NewGrupoServicios);
                        oldIdPucCostosProduccionOfGrupoServiciosList1NewGrupoServicios = em.merge(oldIdPucCostosProduccionOfGrupoServiciosList1NewGrupoServicios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = puc.getId();
                if (findPuc(id) == null) {
                    throw new NonexistentEntityException("The puc with id " + id + " no longer exists.");
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
            Puc puc;
            try {
                puc = em.getReference(Puc.class, id);
                puc.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The puc with id " + id + " no longer exists.", enfe);
            }
            Puc idCuentaMadre = puc.getIdCuentaMadre();
            if (idCuentaMadre != null) {
                idCuentaMadre.getPucList().remove(puc);
                idCuentaMadre = em.merge(idCuentaMadre);
            }
            List<RetencionEmpleado> retencionEmpleadoList = puc.getRetencionEmpleadoList();
            for (RetencionEmpleado retencionEmpleadoListRetencionEmpleado : retencionEmpleadoList) {
                retencionEmpleadoListRetencionEmpleado.setIdPuc(null);
                retencionEmpleadoListRetencionEmpleado = em.merge(retencionEmpleadoListRetencionEmpleado);
            }
            List<TipoTarifaPuc> tipoTarifaPucList = puc.getTipoTarifaPucList();
            for (TipoTarifaPuc tipoTarifaPucListTipoTarifaPuc : tipoTarifaPucList) {
                tipoTarifaPucListTipoTarifaPuc.setIdPuc(null);
                tipoTarifaPucListTipoTarifaPuc = em.merge(tipoTarifaPucListTipoTarifaPuc);
            }
            List<CuentaRetencion> cuentaRetencionList = puc.getCuentaRetencionList();
            for (CuentaRetencion cuentaRetencionListCuentaRetencion : cuentaRetencionList) {
                cuentaRetencionListCuentaRetencion.setIdPuc(null);
                cuentaRetencionListCuentaRetencion = em.merge(cuentaRetencionListCuentaRetencion);
            }
            List<ParametroContableFacturacion> parametroContableFacturacionList = puc.getParametroContableFacturacionList();
            for (ParametroContableFacturacion parametroContableFacturacionListParametroContableFacturacion : parametroContableFacturacionList) {
                parametroContableFacturacionListParametroContableFacturacion.setIdPuc(null);
                parametroContableFacturacionListParametroContableFacturacion = em.merge(parametroContableFacturacionListParametroContableFacturacion);
            }
            List<Puc> pucList = puc.getPucList();
            for (Puc pucListPuc : pucList) {
                pucListPuc.setIdCuentaMadre(null);
                pucListPuc = em.merge(pucListPuc);
            }
            List<ComprobanteCierreDetalle> comprobanteCierreDetalleList = puc.getComprobanteCierreDetalleList();
            for (ComprobanteCierreDetalle comprobanteCierreDetalleListComprobanteCierreDetalle : comprobanteCierreDetalleList) {
                comprobanteCierreDetalleListComprobanteCierreDetalle.setIdCuentaHasta(null);
                comprobanteCierreDetalleListComprobanteCierreDetalle = em.merge(comprobanteCierreDetalleListComprobanteCierreDetalle);
            }
            List<ComprobanteCierreDetalle> comprobanteCierreDetalleList1 = puc.getComprobanteCierreDetalleList1();
            for (ComprobanteCierreDetalle comprobanteCierreDetalleList1ComprobanteCierreDetalle : comprobanteCierreDetalleList1) {
                comprobanteCierreDetalleList1ComprobanteCierreDetalle.setIdCuentaDestino(null);
                comprobanteCierreDetalleList1ComprobanteCierreDetalle = em.merge(comprobanteCierreDetalleList1ComprobanteCierreDetalle);
            }
            List<ComprobanteCierreDetalle> comprobanteCierreDetalleList2 = puc.getComprobanteCierreDetalleList2();
            for (ComprobanteCierreDetalle comprobanteCierreDetalleList2ComprobanteCierreDetalle : comprobanteCierreDetalleList2) {
                comprobanteCierreDetalleList2ComprobanteCierreDetalle.setIdCuentaDesde(null);
                comprobanteCierreDetalleList2ComprobanteCierreDetalle = em.merge(comprobanteCierreDetalleList2ComprobanteCierreDetalle);
            }
            List<CategoriaCuentaInsumo> categoriaCuentaInsumoList = puc.getCategoriaCuentaInsumoList();
            for (CategoriaCuentaInsumo categoriaCuentaInsumoListCategoriaCuentaInsumo : categoriaCuentaInsumoList) {
                categoriaCuentaInsumoListCategoriaCuentaInsumo.setIdPuc(null);
                categoriaCuentaInsumoListCategoriaCuentaInsumo = em.merge(categoriaCuentaInsumoListCategoriaCuentaInsumo);
            }
            List<PucNomina> pucNominaList = puc.getPucNominaList();
            for (PucNomina pucNominaListPucNomina : pucNominaList) {
                pucNominaListPucNomina.setIdPucDebito(null);
                pucNominaListPucNomina = em.merge(pucNominaListPucNomina);
            }
            List<PucNomina> pucNominaList1 = puc.getPucNominaList1();
            for (PucNomina pucNominaList1PucNomina : pucNominaList1) {
                pucNominaList1PucNomina.setIdPucCredito(null);
                pucNominaList1PucNomina = em.merge(pucNominaList1PucNomina);
            }
            List<FacturaVenta> facturaVentaList = puc.getFacturaVentaList();
            for (FacturaVenta facturaVentaListFacturaVenta : facturaVentaList) {
                facturaVentaListFacturaVenta.setIdPuc(null);
                facturaVentaListFacturaVenta = em.merge(facturaVentaListFacturaVenta);
            }
            List<PucServicioPublico> pucServicioPublicoList = puc.getPucServicioPublicoList();
            for (PucServicioPublico pucServicioPublicoListPucServicioPublico : pucServicioPublicoList) {
                pucServicioPublicoListPucServicioPublico.setIdPuc(null);
                pucServicioPublicoListPucServicioPublico = em.merge(pucServicioPublicoListPucServicioPublico);
            }
            List<MovimientoPuc> movimientoPucList = puc.getMovimientoPucList();
            for (MovimientoPuc movimientoPucListMovimientoPuc : movimientoPucList) {
                movimientoPucListMovimientoPuc.setIdPuc(null);
                movimientoPucListMovimientoPuc = em.merge(movimientoPucListMovimientoPuc);
            }
            List<ClasePuc> clasePucList = puc.getClasePucList();
            for (ClasePuc clasePucListClasePuc : clasePucList) {
                clasePucListClasePuc.setIdPuc(null);
                clasePucListClasePuc = em.merge(clasePucListClasePuc);
            }
            List<GrupoServicios> grupoServiciosList = puc.getGrupoServiciosList();
            for (GrupoServicios grupoServiciosListGrupoServicios : grupoServiciosList) {
                grupoServiciosListGrupoServicios.setIdPucGastosAdministrativos(null);
                grupoServiciosListGrupoServicios = em.merge(grupoServiciosListGrupoServicios);
            }
            List<GrupoServicios> grupoServiciosList1 = puc.getGrupoServiciosList1();
            for (GrupoServicios grupoServiciosList1GrupoServicios : grupoServiciosList1) {
                grupoServiciosList1GrupoServicios.setIdPucCostosProduccion(null);
                grupoServiciosList1GrupoServicios = em.merge(grupoServiciosList1GrupoServicios);
            }
            em.remove(puc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Puc> findPucEntities() {
        return findPucEntities(true, -1, -1);
    }

    public List<Puc> findPucEntities(int maxResults, int firstResult) {
        return findPucEntities(false, maxResults, firstResult);
    }

    private List<Puc> findPucEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Puc.class));
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

    public Puc findPuc(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Puc.class, id);
        } finally {
            em.close();
        }
    }

    public int getPucCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Puc> rt = cq.from(Puc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
