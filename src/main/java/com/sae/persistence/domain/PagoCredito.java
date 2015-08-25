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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "pago_credito", catalog = "bdsae", schema = "tesoreria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoCredito.findAll", query = "SELECT p FROM PagoCredito p"),
    @NamedQuery(name = "PagoCredito.findById", query = "SELECT p FROM PagoCredito p WHERE p.id = :id"),
    @NamedQuery(name = "PagoCredito.findByAbonoCapital", query = "SELECT p FROM PagoCredito p WHERE p.abonoCapital = :abonoCapital"),
    @NamedQuery(name = "PagoCredito.findByInteresesCorrientes", query = "SELECT p FROM PagoCredito p WHERE p.interesesCorrientes = :interesesCorrientes"),
    @NamedQuery(name = "PagoCredito.findByInteresesMora", query = "SELECT p FROM PagoCredito p WHERE p.interesesMora = :interesesMora"),
    @NamedQuery(name = "PagoCredito.findByFechaPago", query = "SELECT p FROM PagoCredito p WHERE p.fechaPago = :fechaPago"),
    @NamedQuery(name = "PagoCredito.findByIdMedioPago", query = "SELECT p FROM PagoCredito p WHERE p.idMedioPago = :idMedioPago"),
    @NamedQuery(name = "PagoCredito.findBySaldoActual", query = "SELECT p FROM PagoCredito p WHERE p.saldoActual = :saldoActual"),
    @NamedQuery(name = "PagoCredito.findByIdComprobante", query = "SELECT p FROM PagoCredito p WHERE p.idComprobante = :idComprobante"),
    @NamedQuery(name = "PagoCredito.findByIdUsuario", query = "SELECT p FROM PagoCredito p WHERE p.idUsuario = :idUsuario"),
    @NamedQuery(name = "PagoCredito.findByFechaRegistro", query = "SELECT p FROM PagoCredito p WHERE p.fechaRegistro = :fechaRegistro")})
public class PagoCredito implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "abono_capital")
    private Double abonoCapital;
    @Column(name = "intereses_corrientes")
    private Double interesesCorrientes;
    @Column(name = "intereses_mora")
    private Double interesesMora;
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @Column(name = "id_medio_pago")
    private Integer idMedioPago;
    @Column(name = "saldo_actual")
    private Double saldoActual;
    @Column(name = "id_comprobante")
    private Integer idComprobante;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_credito", referencedColumnName = "id")
    @ManyToOne
    private Credito idCredito;

    public PagoCredito() {
    }

    public PagoCredito(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAbonoCapital() {
        return abonoCapital;
    }

    public void setAbonoCapital(Double abonoCapital) {
        this.abonoCapital = abonoCapital;
    }

    public Double getInteresesCorrientes() {
        return interesesCorrientes;
    }

    public void setInteresesCorrientes(Double interesesCorrientes) {
        this.interesesCorrientes = interesesCorrientes;
    }

    public Double getInteresesMora() {
        return interesesMora;
    }

    public void setInteresesMora(Double interesesMora) {
        this.interesesMora = interesesMora;
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

    public Double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(Double saldoActual) {
        this.saldoActual = saldoActual;
    }

    public Integer getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(Integer idComprobante) {
        this.idComprobante = idComprobante;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Credito getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(Credito idCredito) {
        this.idCredito = idCredito;
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
        if (!(object instanceof PagoCredito)) {
            return false;
        }
        PagoCredito other = (PagoCredito) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.PagoCredito[ id=" + id + " ]";
    }
    
}
