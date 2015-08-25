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
@Table(name = "movimiento_puc", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MovimientoPuc.findAll", query = "SELECT m FROM MovimientoPuc m"),
    @NamedQuery(name = "MovimientoPuc.findById", query = "SELECT m FROM MovimientoPuc m WHERE m.id = :id"),
    @NamedQuery(name = "MovimientoPuc.findByValor", query = "SELECT m FROM MovimientoPuc m WHERE m.valor = :valor"),
    @NamedQuery(name = "MovimientoPuc.findByFecha", query = "SELECT m FROM MovimientoPuc m WHERE m.fecha = :fecha"),
    @NamedQuery(name = "MovimientoPuc.findByNaturaleza", query = "SELECT m FROM MovimientoPuc m WHERE m.naturaleza = :naturaleza"),
    @NamedQuery(name = "MovimientoPuc.findByDetalle", query = "SELECT m FROM MovimientoPuc m WHERE m.detalle = :detalle"),
    @NamedQuery(name = "MovimientoPuc.findByExcluido", query = "SELECT m FROM MovimientoPuc m WHERE m.excluido = :excluido"),
    @NamedQuery(name = "MovimientoPuc.findByIdRazonSocial", query = "SELECT m FROM MovimientoPuc m WHERE m.idRazonSocial = :idRazonSocial"),
    @NamedQuery(name = "MovimientoPuc.findByIdTercero", query = "SELECT m FROM MovimientoPuc m WHERE m.idTercero = :idTercero"),
    @NamedQuery(name = "MovimientoPuc.findBySaldoFinal", query = "SELECT m FROM MovimientoPuc m WHERE m.saldoFinal = :saldoFinal"),
    @NamedQuery(name = "MovimientoPuc.findByFechaRegistro", query = "SELECT m FROM MovimientoPuc m WHERE m.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "MovimientoPuc.findByIdUsuario", query = "SELECT m FROM MovimientoPuc m WHERE m.idUsuario = :idUsuario")})
public class MovimientoPuc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "naturaleza")
    private String naturaleza;
    @Column(name = "detalle")
    private String detalle;
    @Column(name = "excluido")
    private Boolean excluido;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @Column(name = "id_tercero")
    private Integer idTercero;
    @Column(name = "saldo_final")
    private Double saldoFinal;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @OneToMany(mappedBy = "idMovimiento")
    private List<CuentaCobroMovimientoContable> cuentaCobroMovimientoContableList;
    @OneToMany(mappedBy = "idMovimiento")
    private List<FacturaVentaMovimientoContable> facturaVentaMovimientoContableList;
    @JoinColumn(name = "id_puc", referencedColumnName = "id")
    @ManyToOne
    private Puc idPuc;
    @JoinColumn(name = "id_comprobante", referencedColumnName = "id")
    @ManyToOne
    private Comprobante idComprobante;

    public MovimientoPuc() {
    }

    public MovimientoPuc(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(String naturaleza) {
        this.naturaleza = naturaleza;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Boolean getExcluido() {
        return excluido;
    }

    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    public Integer getIdTercero() {
        return idTercero;
    }

    public void setIdTercero(Integer idTercero) {
        this.idTercero = idTercero;
    }

    public Double getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(Double saldoFinal) {
        this.saldoFinal = saldoFinal;
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
    public List<CuentaCobroMovimientoContable> getCuentaCobroMovimientoContableList() {
        return cuentaCobroMovimientoContableList;
    }

    public void setCuentaCobroMovimientoContableList(List<CuentaCobroMovimientoContable> cuentaCobroMovimientoContableList) {
        this.cuentaCobroMovimientoContableList = cuentaCobroMovimientoContableList;
    }

    @XmlTransient
    public List<FacturaVentaMovimientoContable> getFacturaVentaMovimientoContableList() {
        return facturaVentaMovimientoContableList;
    }

    public void setFacturaVentaMovimientoContableList(List<FacturaVentaMovimientoContable> facturaVentaMovimientoContableList) {
        this.facturaVentaMovimientoContableList = facturaVentaMovimientoContableList;
    }

    public Puc getIdPuc() {
        return idPuc;
    }

    public void setIdPuc(Puc idPuc) {
        this.idPuc = idPuc;
    }

    public Comprobante getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(Comprobante idComprobante) {
        this.idComprobante = idComprobante;
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
        if (!(object instanceof MovimientoPuc)) {
            return false;
        }
        MovimientoPuc other = (MovimientoPuc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.MovimientoPuc[ id=" + id + " ]";
    }
    
}
