/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author SAE2
 */
@Entity
@Table(name = "puc", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Puc.findAll", query = "SELECT p FROM Puc p"),
    @NamedQuery(name = "Puc.findById", query = "SELECT p FROM Puc p WHERE p.id = :id"),
    @NamedQuery(name = "Puc.findByCuenta", query = "SELECT p FROM Puc p WHERE p.cuenta = :cuenta"),
    @NamedQuery(name = "Puc.findByNombre", query = "SELECT p FROM Puc p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Puc.findByRama", query = "SELECT p FROM Puc p WHERE p.rama = :rama"),
    @NamedQuery(name = "Puc.findByAceptaMovimientos", query = "SELECT p FROM Puc p WHERE p.aceptaMovimientos = :aceptaMovimientos"),
    @NamedQuery(name = "Puc.findByEstado", query = "SELECT p FROM Puc p WHERE p.estado = :estado"),
    @NamedQuery(name = "Puc.findByFechaRegistro", query = "SELECT p FROM Puc p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Puc.findByIdUsuario", query = "SELECT p FROM Puc p WHERE p.idUsuario = :idUsuario")})
public class Puc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "cuenta")
    private Integer cuenta;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "rama")
    private Integer rama;
    @Column(name = "acepta_movimientos")
    private Boolean aceptaMovimientos;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @OneToMany(mappedBy = "idPuc")
    private List<RetencionEmpleado> retencionEmpleadoList;
    @OneToMany(mappedBy = "idPuc")
    private List<TipoTarifaPuc> tipoTarifaPucList;
    @OneToMany(mappedBy = "idPuc")
    private List<CuentaRetencion> cuentaRetencionList;
    @OneToMany(mappedBy = "idPuc")
    private List<ParametroContableFacturacion> parametroContableFacturacionList;
    @OneToMany(mappedBy = "idCuentaMadre")
    private List<Puc> pucList;
    @JoinColumn(name = "id_cuenta_madre", referencedColumnName = "id")
    @ManyToOne
    private Puc idCuentaMadre;
    @OneToMany(mappedBy = "idCuentaHasta")
    private List<ComprobanteCierreDetalle> comprobanteCierreDetalleList;
    @OneToMany(mappedBy = "idCuentaDestino")
    private List<ComprobanteCierreDetalle> comprobanteCierreDetalleList1;
    @OneToMany(mappedBy = "idCuentaDesde")
    private List<ComprobanteCierreDetalle> comprobanteCierreDetalleList2;
    @OneToMany(mappedBy = "idPuc")
    private List<CategoriaCuentaInsumo> categoriaCuentaInsumoList;
    @OneToMany(mappedBy = "idPucDebito")
    private List<PucNomina> pucNominaList;
    @OneToMany(mappedBy = "idPucCredito")
    private List<PucNomina> pucNominaList1;
    @OneToMany(mappedBy = "idPuc")
    private List<FacturaVenta> facturaVentaList;
    @OneToMany(mappedBy = "idPuc")
    private List<PucServicioPublico> pucServicioPublicoList;
    @OneToMany(mappedBy = "idPuc")
    private List<MovimientoPuc> movimientoPucList;
    @OneToMany(mappedBy = "idPuc")
    private List<ClasePuc> clasePucList;
    @OneToMany(mappedBy = "idPucGastosAdministrativos")
    private List<GrupoServicios> grupoServiciosList;
    @OneToMany(mappedBy = "idPucCostosProduccion")
    private List<GrupoServicios> grupoServiciosList1;

    public Puc() {
    }

    public Puc(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getRama() {
        return rama;
    }

    public void setRama(Integer rama) {
        this.rama = rama;
    }

    public Boolean getAceptaMovimientos() {
        return aceptaMovimientos;
    }

    public void setAceptaMovimientos(Boolean aceptaMovimientos) {
        this.aceptaMovimientos = aceptaMovimientos;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlTransient
    public List<RetencionEmpleado> getRetencionEmpleadoList() {
        return retencionEmpleadoList;
    }

    public void setRetencionEmpleadoList(List<RetencionEmpleado> retencionEmpleadoList) {
        this.retencionEmpleadoList = retencionEmpleadoList;
    }

    @XmlTransient
    public List<TipoTarifaPuc> getTipoTarifaPucList() {
        return tipoTarifaPucList;
    }

    public void setTipoTarifaPucList(List<TipoTarifaPuc> tipoTarifaPucList) {
        this.tipoTarifaPucList = tipoTarifaPucList;
    }

    @XmlTransient
    public List<CuentaRetencion> getCuentaRetencionList() {
        return cuentaRetencionList;
    }

    public void setCuentaRetencionList(List<CuentaRetencion> cuentaRetencionList) {
        this.cuentaRetencionList = cuentaRetencionList;
    }

    @XmlTransient
    public List<ParametroContableFacturacion> getParametroContableFacturacionList() {
        return parametroContableFacturacionList;
    }

    public void setParametroContableFacturacionList(List<ParametroContableFacturacion> parametroContableFacturacionList) {
        this.parametroContableFacturacionList = parametroContableFacturacionList;
    }

    @XmlTransient
    public List<Puc> getPucList() {
        return pucList;
    }

    public void setPucList(List<Puc> pucList) {
        this.pucList = pucList;
    }

    public Puc getIdCuentaMadre() {
        return idCuentaMadre;
    }

    public void setIdCuentaMadre(Puc idCuentaMadre) {
        this.idCuentaMadre = idCuentaMadre;
    }

    @XmlTransient
    public List<ComprobanteCierreDetalle> getComprobanteCierreDetalleList() {
        return comprobanteCierreDetalleList;
    }

    public void setComprobanteCierreDetalleList(List<ComprobanteCierreDetalle> comprobanteCierreDetalleList) {
        this.comprobanteCierreDetalleList = comprobanteCierreDetalleList;
    }

    @XmlTransient
    public List<ComprobanteCierreDetalle> getComprobanteCierreDetalleList1() {
        return comprobanteCierreDetalleList1;
    }

    public void setComprobanteCierreDetalleList1(List<ComprobanteCierreDetalle> comprobanteCierreDetalleList1) {
        this.comprobanteCierreDetalleList1 = comprobanteCierreDetalleList1;
    }

    @XmlTransient
    public List<ComprobanteCierreDetalle> getComprobanteCierreDetalleList2() {
        return comprobanteCierreDetalleList2;
    }

    public void setComprobanteCierreDetalleList2(List<ComprobanteCierreDetalle> comprobanteCierreDetalleList2) {
        this.comprobanteCierreDetalleList2 = comprobanteCierreDetalleList2;
    }

    @XmlTransient
    public List<CategoriaCuentaInsumo> getCategoriaCuentaInsumoList() {
        return categoriaCuentaInsumoList;
    }

    public void setCategoriaCuentaInsumoList(List<CategoriaCuentaInsumo> categoriaCuentaInsumoList) {
        this.categoriaCuentaInsumoList = categoriaCuentaInsumoList;
    }

    @XmlTransient
    public List<PucNomina> getPucNominaList() {
        return pucNominaList;
    }

    public void setPucNominaList(List<PucNomina> pucNominaList) {
        this.pucNominaList = pucNominaList;
    }

    @XmlTransient
    public List<PucNomina> getPucNominaList1() {
        return pucNominaList1;
    }

    public void setPucNominaList1(List<PucNomina> pucNominaList1) {
        this.pucNominaList1 = pucNominaList1;
    }

    @XmlTransient
    public List<FacturaVenta> getFacturaVentaList() {
        return facturaVentaList;
    }

    public void setFacturaVentaList(List<FacturaVenta> facturaVentaList) {
        this.facturaVentaList = facturaVentaList;
    }

    @XmlTransient
    public List<PucServicioPublico> getPucServicioPublicoList() {
        return pucServicioPublicoList;
    }

    public void setPucServicioPublicoList(List<PucServicioPublico> pucServicioPublicoList) {
        this.pucServicioPublicoList = pucServicioPublicoList;
    }

    @XmlTransient
    public List<MovimientoPuc> getMovimientoPucList() {
        return movimientoPucList;
    }

    public void setMovimientoPucList(List<MovimientoPuc> movimientoPucList) {
        this.movimientoPucList = movimientoPucList;
    }

    @XmlTransient
    public List<ClasePuc> getClasePucList() {
        return clasePucList;
    }

    public void setClasePucList(List<ClasePuc> clasePucList) {
        this.clasePucList = clasePucList;
    }

    @XmlTransient
    public List<GrupoServicios> getGrupoServiciosList() {
        return grupoServiciosList;
    }

    public void setGrupoServiciosList(List<GrupoServicios> grupoServiciosList) {
        this.grupoServiciosList = grupoServiciosList;
    }

    @XmlTransient
    public List<GrupoServicios> getGrupoServiciosList1() {
        return grupoServiciosList1;
    }

    public void setGrupoServiciosList1(List<GrupoServicios> grupoServiciosList1) {
        this.grupoServiciosList1 = grupoServiciosList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Puc)) {
            return false;
        }
        Puc other = (Puc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Puc[ id=" + id + " ]";
    }
    
}
