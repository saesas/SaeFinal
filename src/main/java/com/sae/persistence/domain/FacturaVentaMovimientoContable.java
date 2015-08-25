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
@Table(name = "factura_venta_movimiento_contable", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturaVentaMovimientoContable.findAll", query = "SELECT f FROM FacturaVentaMovimientoContable f"),
    @NamedQuery(name = "FacturaVentaMovimientoContable.findById", query = "SELECT f FROM FacturaVentaMovimientoContable f WHERE f.id = :id"),
    @NamedQuery(name = "FacturaVentaMovimientoContable.findByEstado", query = "SELECT f FROM FacturaVentaMovimientoContable f WHERE f.estado = :estado"),
    @NamedQuery(name = "FacturaVentaMovimientoContable.findByIdUsuario", query = "SELECT f FROM FacturaVentaMovimientoContable f WHERE f.idUsuario = :idUsuario"),
    @NamedQuery(name = "FacturaVentaMovimientoContable.findByFechaRegistro", query = "SELECT f FROM FacturaVentaMovimientoContable f WHERE f.fechaRegistro = :fechaRegistro")})
public class FacturaVentaMovimientoContable implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
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
    @JoinColumn(name = "id_factura_venta", referencedColumnName = "id")
    @ManyToOne
    private FacturaVenta idFacturaVenta;

    public FacturaVentaMovimientoContable() {
    }

    public FacturaVentaMovimientoContable(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public FacturaVenta getIdFacturaVenta() {
        return idFacturaVenta;
    }

    public void setIdFacturaVenta(FacturaVenta idFacturaVenta) {
        this.idFacturaVenta = idFacturaVenta;
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
        if (!(object instanceof FacturaVentaMovimientoContable)) {
            return false;
        }
        FacturaVentaMovimientoContable other = (FacturaVentaMovimientoContable) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.FacturaVentaMovimientoContable[ id=" + id + " ]";
    }
    
}
