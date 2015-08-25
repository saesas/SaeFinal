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
@Table(name = "prestamo_anticipo", catalog = "bdsae", schema = "tesoreria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrestamoAnticipo.findAll", query = "SELECT p FROM PrestamoAnticipo p"),
    @NamedQuery(name = "PrestamoAnticipo.findById", query = "SELECT p FROM PrestamoAnticipo p WHERE p.id = :id"),
    @NamedQuery(name = "PrestamoAnticipo.findByIdTercero", query = "SELECT p FROM PrestamoAnticipo p WHERE p.idTercero = :idTercero"),
    @NamedQuery(name = "PrestamoAnticipo.findByValorPrestamo", query = "SELECT p FROM PrestamoAnticipo p WHERE p.valorPrestamo = :valorPrestamo"),
    @NamedQuery(name = "PrestamoAnticipo.findByNumeroCuotas", query = "SELECT p FROM PrestamoAnticipo p WHERE p.numeroCuotas = :numeroCuotas"),
    @NamedQuery(name = "PrestamoAnticipo.findByFechaEntrega", query = "SELECT p FROM PrestamoAnticipo p WHERE p.fechaEntrega = :fechaEntrega"),
    @NamedQuery(name = "PrestamoAnticipo.findByFechaRegistro", query = "SELECT p FROM PrestamoAnticipo p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "PrestamoAnticipo.findByDescripcion", query = "SELECT p FROM PrestamoAnticipo p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "PrestamoAnticipo.findByIdMedioEntrega", query = "SELECT p FROM PrestamoAnticipo p WHERE p.idMedioEntrega = :idMedioEntrega"),
    @NamedQuery(name = "PrestamoAnticipo.findByIdMedioPago", query = "SELECT p FROM PrestamoAnticipo p WHERE p.idMedioPago = :idMedioPago"),
    @NamedQuery(name = "PrestamoAnticipo.findByIdComprobante", query = "SELECT p FROM PrestamoAnticipo p WHERE p.idComprobante = :idComprobante"),
    @NamedQuery(name = "PrestamoAnticipo.findByEstado", query = "SELECT p FROM PrestamoAnticipo p WHERE p.estado = :estado"),
    @NamedQuery(name = "PrestamoAnticipo.findByIdUsuario", query = "SELECT p FROM PrestamoAnticipo p WHERE p.idUsuario = :idUsuario"),
    @NamedQuery(name = "PrestamoAnticipo.findByIdProyecto", query = "SELECT p FROM PrestamoAnticipo p WHERE p.idProyecto = :idProyecto"),
    @NamedQuery(name = "PrestamoAnticipo.findByValorPagado", query = "SELECT p FROM PrestamoAnticipo p WHERE p.valorPagado = :valorPagado"),
    @NamedQuery(name = "PrestamoAnticipo.findByIdRazonSocial", query = "SELECT p FROM PrestamoAnticipo p WHERE p.idRazonSocial = :idRazonSocial")})
public class PrestamoAnticipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_tercero")
    private Integer idTercero;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_prestamo")
    private Double valorPrestamo;
    @Column(name = "numero_cuotas")
    private Integer numeroCuotas;
    @Column(name = "fecha_entrega")
    @Temporal(TemporalType.DATE)
    private Date fechaEntrega;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "id_medio_entrega")
    private Integer idMedioEntrega;
    @Column(name = "id_medio_pago")
    private Integer idMedioPago;
    @Column(name = "id_comprobante")
    private Integer idComprobante;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_proyecto")
    private Integer idProyecto;
    @Column(name = "valor_pagado")
    private Double valorPagado;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @OneToMany(mappedBy = "idAnticipo")
    private List<PagoAnticipoReintegro> pagoAnticipoReintegroList;

    public PrestamoAnticipo() {
    }

    public PrestamoAnticipo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdTercero() {
        return idTercero;
    }

    public void setIdTercero(Integer idTercero) {
        this.idTercero = idTercero;
    }

    public Double getValorPrestamo() {
        return valorPrestamo;
    }

    public void setValorPrestamo(Double valorPrestamo) {
        this.valorPrestamo = valorPrestamo;
    }

    public Integer getNumeroCuotas() {
        return numeroCuotas;
    }

    public void setNumeroCuotas(Integer numeroCuotas) {
        this.numeroCuotas = numeroCuotas;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdMedioEntrega() {
        return idMedioEntrega;
    }

    public void setIdMedioEntrega(Integer idMedioEntrega) {
        this.idMedioEntrega = idMedioEntrega;
    }

    public Integer getIdMedioPago() {
        return idMedioPago;
    }

    public void setIdMedioPago(Integer idMedioPago) {
        this.idMedioPago = idMedioPago;
    }

    public Integer getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(Integer idComprobante) {
        this.idComprobante = idComprobante;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Double getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(Double valorPagado) {
        this.valorPagado = valorPagado;
    }

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    @XmlTransient
    public List<PagoAnticipoReintegro> getPagoAnticipoReintegroList() {
        return pagoAnticipoReintegroList;
    }

    public void setPagoAnticipoReintegroList(List<PagoAnticipoReintegro> pagoAnticipoReintegroList) {
        this.pagoAnticipoReintegroList = pagoAnticipoReintegroList;
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
        if (!(object instanceof PrestamoAnticipo)) {
            return false;
        }
        PrestamoAnticipo other = (PrestamoAnticipo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.PrestamoAnticipo[ id=" + id + " ]";
    }
    
}
