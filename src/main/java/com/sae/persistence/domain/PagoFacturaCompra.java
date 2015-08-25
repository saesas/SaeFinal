/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SAE2
 */
@Entity
@Table(name = "pago_factura_compra", catalog = "bdsae", schema = "tesoreria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoFacturaCompra.findAll", query = "SELECT p FROM PagoFacturaCompra p"),
    @NamedQuery(name = "PagoFacturaCompra.findById", query = "SELECT p FROM PagoFacturaCompra p WHERE p.id = :id"),
    @NamedQuery(name = "PagoFacturaCompra.findByIdFactura", query = "SELECT p FROM PagoFacturaCompra p WHERE p.idFactura = :idFactura"),
    @NamedQuery(name = "PagoFacturaCompra.findByValorDescuento", query = "SELECT p FROM PagoFacturaCompra p WHERE p.valorDescuento = :valorDescuento"),
    @NamedQuery(name = "PagoFacturaCompra.findByRetencionAsumida", query = "SELECT p FROM PagoFacturaCompra p WHERE p.retencionAsumida = :retencionAsumida"),
    @NamedQuery(name = "PagoFacturaCompra.findByFechaPago", query = "SELECT p FROM PagoFacturaCompra p WHERE p.fechaPago = :fechaPago"),
    @NamedQuery(name = "PagoFacturaCompra.findByIdMedioPago", query = "SELECT p FROM PagoFacturaCompra p WHERE p.idMedioPago = :idMedioPago"),
    @NamedQuery(name = "PagoFacturaCompra.findByValorPagado", query = "SELECT p FROM PagoFacturaCompra p WHERE p.valorPagado = :valorPagado"),
    @NamedQuery(name = "PagoFacturaCompra.findBySaldoActual", query = "SELECT p FROM PagoFacturaCompra p WHERE p.saldoActual = :saldoActual"),
    @NamedQuery(name = "PagoFacturaCompra.findByFechaRegistro", query = "SELECT p FROM PagoFacturaCompra p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "PagoFacturaCompra.findByIdUsuario", query = "SELECT p FROM PagoFacturaCompra p WHERE p.idUsuario = :idUsuario"),
    @NamedQuery(name = "PagoFacturaCompra.findByIdComprobante", query = "SELECT p FROM PagoFacturaCompra p WHERE p.idComprobante = :idComprobante")})
public class PagoFacturaCompra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_factura")
    private Integer idFactura;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_descuento")
    private Double valorDescuento;
    @Column(name = "retencion_asumida")
    private Double retencionAsumida;
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;
    @Column(name = "id_medio_pago")
    private Integer idMedioPago;
    @Column(name = "valor_pagado")
    private Double valorPagado;
    @Column(name = "saldo_actual")
    private Double saldoActual;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_comprobante")
    private Integer idComprobante;

    public PagoFacturaCompra() {
    }

    public PagoFacturaCompra(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public Double getValorDescuento() {
        return valorDescuento;
    }

    public void setValorDescuento(Double valorDescuento) {
        this.valorDescuento = valorDescuento;
    }

    public Double getRetencionAsumida() {
        return retencionAsumida;
    }

    public void setRetencionAsumida(Double retencionAsumida) {
        this.retencionAsumida = retencionAsumida;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Integer getIdMedioPago() {
        return idMedioPago;
    }

    public void setIdMedioPago(Integer idMedioPago) {
        this.idMedioPago = idMedioPago;
    }

    public Double getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(Double valorPagado) {
        this.valorPagado = valorPagado;
    }

    public Double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(Double saldoActual) {
        this.saldoActual = saldoActual;
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

    public Integer getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(Integer idComprobante) {
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
        if (!(object instanceof PagoFacturaCompra)) {
            return false;
        }
        PagoFacturaCompra other = (PagoFacturaCompra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.PagoFacturaCompra[ id=" + id + " ]";
    }
    
}
