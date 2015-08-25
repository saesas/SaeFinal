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
@Table(name = "insumo_factura_compra", catalog = "bdsae", schema = "compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InsumoFacturaCompra.findAll", query = "SELECT i FROM InsumoFacturaCompra i"),
    @NamedQuery(name = "InsumoFacturaCompra.findById", query = "SELECT i FROM InsumoFacturaCompra i WHERE i.id = :id"),
    @NamedQuery(name = "InsumoFacturaCompra.findByEstado", query = "SELECT i FROM InsumoFacturaCompra i WHERE i.estado = :estado"),
    @NamedQuery(name = "InsumoFacturaCompra.findByCantidad", query = "SELECT i FROM InsumoFacturaCompra i WHERE i.cantidad = :cantidad"),
    @NamedQuery(name = "InsumoFacturaCompra.findByValor", query = "SELECT i FROM InsumoFacturaCompra i WHERE i.valor = :valor"),
    @NamedQuery(name = "InsumoFacturaCompra.findByIdBodega", query = "SELECT i FROM InsumoFacturaCompra i WHERE i.idBodega = :idBodega"),
    @NamedQuery(name = "InsumoFacturaCompra.findByIdUnidad", query = "SELECT i FROM InsumoFacturaCompra i WHERE i.idUnidad = :idUnidad"),
    @NamedQuery(name = "InsumoFacturaCompra.findByFechaFactura", query = "SELECT i FROM InsumoFacturaCompra i WHERE i.fechaFactura = :fechaFactura"),
    @NamedQuery(name = "InsumoFacturaCompra.findByIdUsuarioFactura", query = "SELECT i FROM InsumoFacturaCompra i WHERE i.idUsuarioFactura = :idUsuarioFactura"),
    @NamedQuery(name = "InsumoFacturaCompra.findByFechaRecibido", query = "SELECT i FROM InsumoFacturaCompra i WHERE i.fechaRecibido = :fechaRecibido"),
    @NamedQuery(name = "InsumoFacturaCompra.findByCantidadRecibida", query = "SELECT i FROM InsumoFacturaCompra i WHERE i.cantidadRecibida = :cantidadRecibida"),
    @NamedQuery(name = "InsumoFacturaCompra.findByIdUsuarioRecibido", query = "SELECT i FROM InsumoFacturaCompra i WHERE i.idUsuarioRecibido = :idUsuarioRecibido")})
public class InsumoFacturaCompra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "cantidad")
    private Integer cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "id_bodega")
    private Integer idBodega;
    @Column(name = "id_unidad")
    private Integer idUnidad;
    @Column(name = "fecha_factura")
    @Temporal(TemporalType.DATE)
    private Date fechaFactura;
    @Column(name = "id_usuario_factura")
    private Integer idUsuarioFactura;
    @Column(name = "fecha_recibido")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRecibido;
    @Column(name = "cantidad_recibida")
    private Integer cantidadRecibida;
    @Column(name = "id_usuario_recibido")
    private Integer idUsuarioRecibido;
    @JoinColumn(name = "id_orden_insumo", referencedColumnName = "id")
    @ManyToOne
    private OrdenCompraInsumo idOrdenInsumo;
    @JoinColumn(name = "id_factura", referencedColumnName = "id")
    @ManyToOne
    private FacturaCompra idFactura;

    public InsumoFacturaCompra() {
    }

    public InsumoFacturaCompra(Integer id) {
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(Integer idBodega) {
        this.idBodega = idBodega;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public Integer getIdUsuarioFactura() {
        return idUsuarioFactura;
    }

    public void setIdUsuarioFactura(Integer idUsuarioFactura) {
        this.idUsuarioFactura = idUsuarioFactura;
    }

    public Date getFechaRecibido() {
        return fechaRecibido;
    }

    public void setFechaRecibido(Date fechaRecibido) {
        this.fechaRecibido = fechaRecibido;
    }

    public Integer getCantidadRecibida() {
        return cantidadRecibida;
    }

    public void setCantidadRecibida(Integer cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }

    public Integer getIdUsuarioRecibido() {
        return idUsuarioRecibido;
    }

    public void setIdUsuarioRecibido(Integer idUsuarioRecibido) {
        this.idUsuarioRecibido = idUsuarioRecibido;
    }

    public OrdenCompraInsumo getIdOrdenInsumo() {
        return idOrdenInsumo;
    }

    public void setIdOrdenInsumo(OrdenCompraInsumo idOrdenInsumo) {
        this.idOrdenInsumo = idOrdenInsumo;
    }

    public FacturaCompra getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(FacturaCompra idFactura) {
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
        if (!(object instanceof InsumoFacturaCompra)) {
            return false;
        }
        InsumoFacturaCompra other = (InsumoFacturaCompra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.InsumoFacturaCompra[ id=" + id + " ]";
    }
    
}
