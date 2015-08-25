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
@Table(name = "cuenta_cobro_movimiento_contable", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuentaCobroMovimientoContable.findAll", query = "SELECT c FROM CuentaCobroMovimientoContable c"),
    @NamedQuery(name = "CuentaCobroMovimientoContable.findById", query = "SELECT c FROM CuentaCobroMovimientoContable c WHERE c.id = :id"),
    @NamedQuery(name = "CuentaCobroMovimientoContable.findByIdCuentaCobro", query = "SELECT c FROM CuentaCobroMovimientoContable c WHERE c.idCuentaCobro = :idCuentaCobro"),
    @NamedQuery(name = "CuentaCobroMovimientoContable.findByEstado", query = "SELECT c FROM CuentaCobroMovimientoContable c WHERE c.estado = :estado"),
    @NamedQuery(name = "CuentaCobroMovimientoContable.findByIdUsuario", query = "SELECT c FROM CuentaCobroMovimientoContable c WHERE c.idUsuario = :idUsuario"),
    @NamedQuery(name = "CuentaCobroMovimientoContable.findByFechaRegistro", query = "SELECT c FROM CuentaCobroMovimientoContable c WHERE c.fechaRegistro = :fechaRegistro")})
public class CuentaCobroMovimientoContable implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_cuenta_cobro")
    private Integer idCuentaCobro;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_movimiento", referencedColumnName = "id")
    @ManyToOne
    private MovimientoPuc idMovimiento;

    public CuentaCobroMovimientoContable() {
    }

    public CuentaCobroMovimientoContable(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCuentaCobro() {
        return idCuentaCobro;
    }

    public void setIdCuentaCobro(Integer idCuentaCobro) {
        this.idCuentaCobro = idCuentaCobro;
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

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public MovimientoPuc getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(MovimientoPuc idMovimiento) {
        this.idMovimiento = idMovimiento;
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
        if (!(object instanceof CuentaCobroMovimientoContable)) {
            return false;
        }
        CuentaCobroMovimientoContable other = (CuentaCobroMovimientoContable) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.CuentaCobroMovimientoContable[ id=" + id + " ]";
    }
    
}
