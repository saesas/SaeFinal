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
@Table(name = "factura_compra_movimiento_contable", catalog = "bdsae", schema = "tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturaCompraMovimientoContable.findAll", query = "SELECT f FROM FacturaCompraMovimientoContable f"),
    @NamedQuery(name = "FacturaCompraMovimientoContable.findById", query = "SELECT f FROM FacturaCompraMovimientoContable f WHERE f.id = :id"),
    @NamedQuery(name = "FacturaCompraMovimientoContable.findByIdFacturaCompra", query = "SELECT f FROM FacturaCompraMovimientoContable f WHERE f.idFacturaCompra = :idFacturaCompra"),
    @NamedQuery(name = "FacturaCompraMovimientoContable.findByIdMovimiento", query = "SELECT f FROM FacturaCompraMovimientoContable f WHERE f.idMovimiento = :idMovimiento"),
    @NamedQuery(name = "FacturaCompraMovimientoContable.findByEstado", query = "SELECT f FROM FacturaCompraMovimientoContable f WHERE f.estado = :estado"),
    @NamedQuery(name = "FacturaCompraMovimientoContable.findByIdUsuario", query = "SELECT f FROM FacturaCompraMovimientoContable f WHERE f.idUsuario = :idUsuario"),
    @NamedQuery(name = "FacturaCompraMovimientoContable.findByFechaRegistro", query = "SELECT f FROM FacturaCompraMovimientoContable f WHERE f.fechaRegistro = :fechaRegistro")})
public class FacturaCompraMovimientoContable implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_factura_compra")
    private Integer idFacturaCompra;
    @Column(name = "id_movimiento")
    private Integer idMovimiento;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    public FacturaCompraMovimientoContable() {
    }

    public FacturaCompraMovimientoContable(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdFacturaCompra() {
        return idFacturaCompra;
    }

    public void setIdFacturaCompra(Integer idFacturaCompra) {
        this.idFacturaCompra = idFacturaCompra;
    }

    public Integer getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Integer idMovimiento) {
        this.idMovimiento = idMovimiento;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaCompraMovimientoContable)) {
            return false;
        }
        FacturaCompraMovimientoContable other = (FacturaCompraMovimientoContable) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.FacturaCompraMovimientoContable[ id=" + id + " ]";
    }
    
}
