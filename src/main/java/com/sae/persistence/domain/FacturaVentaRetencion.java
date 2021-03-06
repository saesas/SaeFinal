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
@Table(name = "factura_venta_retencion", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturaVentaRetencion.findAll", query = "SELECT f FROM FacturaVentaRetencion f"),
    @NamedQuery(name = "FacturaVentaRetencion.findById", query = "SELECT f FROM FacturaVentaRetencion f WHERE f.id = :id"),
    @NamedQuery(name = "FacturaVentaRetencion.findByEstado", query = "SELECT f FROM FacturaVentaRetencion f WHERE f.estado = :estado"),
    @NamedQuery(name = "FacturaVentaRetencion.findByFechaRegistro", query = "SELECT f FROM FacturaVentaRetencion f WHERE f.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "FacturaVentaRetencion.findByIdUsuario", query = "SELECT f FROM FacturaVentaRetencion f WHERE f.idUsuario = :idUsuario")})
public class FacturaVentaRetencion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_retencion", referencedColumnName = "id")
    @ManyToOne
    private Retencion idRetencion;
    @JoinColumn(name = "id_factura", referencedColumnName = "id")
    @ManyToOne
    private FacturaVenta idFactura;

    public FacturaVentaRetencion() {
    }

    public FacturaVentaRetencion(Integer id) {
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

    public Retencion getIdRetencion() {
        return idRetencion;
    }

    public void setIdRetencion(Retencion idRetencion) {
        this.idRetencion = idRetencion;
    }

    public FacturaVenta getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(FacturaVenta idFactura) {
        this.idFactura = idFactura;
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
        if (!(object instanceof FacturaVentaRetencion)) {
            return false;
        }
        FacturaVentaRetencion other = (FacturaVentaRetencion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.FacturaVentaRetencion[ id=" + id + " ]";
    }
    
}
