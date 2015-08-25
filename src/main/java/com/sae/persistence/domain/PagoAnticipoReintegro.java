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
@Table(name = "pago_anticipo_reintegro", catalog = "bdsae", schema = "tesoreria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoAnticipoReintegro.findAll", query = "SELECT p FROM PagoAnticipoReintegro p"),
    @NamedQuery(name = "PagoAnticipoReintegro.findById", query = "SELECT p FROM PagoAnticipoReintegro p WHERE p.id = :id"),
    @NamedQuery(name = "PagoAnticipoReintegro.findByValorPago", query = "SELECT p FROM PagoAnticipoReintegro p WHERE p.valorPago = :valorPago"),
    @NamedQuery(name = "PagoAnticipoReintegro.findByIdMedioPago", query = "SELECT p FROM PagoAnticipoReintegro p WHERE p.idMedioPago = :idMedioPago"),
    @NamedQuery(name = "PagoAnticipoReintegro.findByFechaPago", query = "SELECT p FROM PagoAnticipoReintegro p WHERE p.fechaPago = :fechaPago"),
    @NamedQuery(name = "PagoAnticipoReintegro.findByIdReintegro", query = "SELECT p FROM PagoAnticipoReintegro p WHERE p.idReintegro = :idReintegro"),
    @NamedQuery(name = "PagoAnticipoReintegro.findByFechaRegistro", query = "SELECT p FROM PagoAnticipoReintegro p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "PagoAnticipoReintegro.findByIdUsuario", query = "SELECT p FROM PagoAnticipoReintegro p WHERE p.idUsuario = :idUsuario"),
    @NamedQuery(name = "PagoAnticipoReintegro.findByEstado", query = "SELECT p FROM PagoAnticipoReintegro p WHERE p.estado = :estado")})
public class PagoAnticipoReintegro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_pago")
    private Double valorPago;
    @Column(name = "id_medio_pago")
    private Integer idMedioPago;
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @Column(name = "id_reintegro")
    private Integer idReintegro;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "estado")
    private Boolean estado;
    @JoinColumn(name = "id_anticipo", referencedColumnName = "id")
    @ManyToOne
    private PrestamoAnticipo idAnticipo;

    public PagoAnticipoReintegro() {
    }

    public PagoAnticipoReintegro(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public Integer getIdMedioPago() {
        return idMedioPago;
    }

    public void setIdMedioPago(Integer idMedioPago) {
        this.idMedioPago = idMedioPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Integer getIdReintegro() {
        return idReintegro;
    }

    public void setIdReintegro(Integer idReintegro) {
        this.idReintegro = idReintegro;
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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public PrestamoAnticipo getIdAnticipo() {
        return idAnticipo;
    }

    public void setIdAnticipo(PrestamoAnticipo idAnticipo) {
        this.idAnticipo = idAnticipo;
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
        if (!(object instanceof PagoAnticipoReintegro)) {
            return false;
        }
        PagoAnticipoReintegro other = (PagoAnticipoReintegro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.PagoAnticipoReintegro[ id=" + id + " ]";
    }
    
}
