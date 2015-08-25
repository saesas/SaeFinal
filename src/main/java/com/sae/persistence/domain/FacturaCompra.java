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
@Table(name = "factura_compra", catalog = "bdsae", schema = "compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturaCompra.findAll", query = "SELECT f FROM FacturaCompra f"),
    @NamedQuery(name = "FacturaCompra.findById", query = "SELECT f FROM FacturaCompra f WHERE f.id = :id"),
    @NamedQuery(name = "FacturaCompra.findByEstado", query = "SELECT f FROM FacturaCompra f WHERE f.estado = :estado"),
    @NamedQuery(name = "FacturaCompra.findByCodigo", query = "SELECT f FROM FacturaCompra f WHERE f.codigo = :codigo"),
    @NamedQuery(name = "FacturaCompra.findByFechaEmision", query = "SELECT f FROM FacturaCompra f WHERE f.fechaEmision = :fechaEmision"),
    @NamedQuery(name = "FacturaCompra.findByFechaLimitePago", query = "SELECT f FROM FacturaCompra f WHERE f.fechaLimitePago = :fechaLimitePago"),
    @NamedQuery(name = "FacturaCompra.findByIdUsuario", query = "SELECT f FROM FacturaCompra f WHERE f.idUsuario = :idUsuario"),
    @NamedQuery(name = "FacturaCompra.findBySubtotal", query = "SELECT f FROM FacturaCompra f WHERE f.subtotal = :subtotal"),
    @NamedQuery(name = "FacturaCompra.findByDescuento", query = "SELECT f FROM FacturaCompra f WHERE f.descuento = :descuento"),
    @NamedQuery(name = "FacturaCompra.findByFletes", query = "SELECT f FROM FacturaCompra f WHERE f.fletes = :fletes"),
    @NamedQuery(name = "FacturaCompra.findByIva", query = "SELECT f FROM FacturaCompra f WHERE f.iva = :iva"),
    @NamedQuery(name = "FacturaCompra.findByReteiva", query = "SELECT f FROM FacturaCompra f WHERE f.reteiva = :reteiva"),
    @NamedQuery(name = "FacturaCompra.findByRetefuente", query = "SELECT f FROM FacturaCompra f WHERE f.retefuente = :retefuente"),
    @NamedQuery(name = "FacturaCompra.findByRetecree", query = "SELECT f FROM FacturaCompra f WHERE f.retecree = :retecree"),
    @NamedQuery(name = "FacturaCompra.findByReteica", query = "SELECT f FROM FacturaCompra f WHERE f.reteica = :reteica"),
    @NamedQuery(name = "FacturaCompra.findByTotalFactura", query = "SELECT f FROM FacturaCompra f WHERE f.totalFactura = :totalFactura"),
    @NamedQuery(name = "FacturaCompra.findByTotalPagar", query = "SELECT f FROM FacturaCompra f WHERE f.totalPagar = :totalPagar"),
    @NamedQuery(name = "FacturaCompra.findByAdministracion", query = "SELECT f FROM FacturaCompra f WHERE f.administracion = :administracion"),
    @NamedQuery(name = "FacturaCompra.findByUtilidad", query = "SELECT f FROM FacturaCompra f WHERE f.utilidad = :utilidad"),
    @NamedQuery(name = "FacturaCompra.findByImpuestos", query = "SELECT f FROM FacturaCompra f WHERE f.impuestos = :impuestos")})
public class FacturaCompra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "fecha_emision")
    @Temporal(TemporalType.DATE)
    private Date fechaEmision;
    @Column(name = "fecha_limite_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaLimitePago;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "subtotal")
    private Double subtotal;
    @Column(name = "descuento")
    private Double descuento;
    @Column(name = "fletes")
    private Double fletes;
    @Column(name = "iva")
    private Double iva;
    @Column(name = "reteiva")
    private Double reteiva;
    @Column(name = "retefuente")
    private Double retefuente;
    @Column(name = "retecree")
    private Double retecree;
    @Column(name = "reteica")
    private Double reteica;
    @Column(name = "total_factura")
    private Double totalFactura;
    @Column(name = "total_pagar")
    private Double totalPagar;
    @Column(name = "administracion")
    private Double administracion;
    @Column(name = "utilidad")
    private Double utilidad;
    @Column(name = "impuestos")
    private Double impuestos;
    @JoinColumn(name = "id_orden_compra", referencedColumnName = "id")
    @ManyToOne
    private OrdenCompra idOrdenCompra;
    @OneToMany(mappedBy = "idFactura")
    private List<InsumoFacturaCompra> insumoFacturaCompraList;
    @OneToMany(mappedBy = "idFactura")
    private List<AdjuntoFactura> adjuntoFacturaList;

    public FacturaCompra() {
    }

    public FacturaCompra(Integer id) {
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaLimitePago() {
        return fechaLimitePago;
    }

    public void setFechaLimitePago(Date fechaLimitePago) {
        this.fechaLimitePago = fechaLimitePago;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getFletes() {
        return fletes;
    }

    public void setFletes(Double fletes) {
        this.fletes = fletes;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Double getReteiva() {
        return reteiva;
    }

    public void setReteiva(Double reteiva) {
        this.reteiva = reteiva;
    }

    public Double getRetefuente() {
        return retefuente;
    }

    public void setRetefuente(Double retefuente) {
        this.retefuente = retefuente;
    }

    public Double getRetecree() {
        return retecree;
    }

    public void setRetecree(Double retecree) {
        this.retecree = retecree;
    }

    public Double getReteica() {
        return reteica;
    }

    public void setReteica(Double reteica) {
        this.reteica = reteica;
    }

    public Double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(Double totalFactura) {
        this.totalFactura = totalFactura;
    }

    public Double getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(Double totalPagar) {
        this.totalPagar = totalPagar;
    }

    public Double getAdministracion() {
        return administracion;
    }

    public void setAdministracion(Double administracion) {
        this.administracion = administracion;
    }

    public Double getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(Double utilidad) {
        this.utilidad = utilidad;
    }

    public Double getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(Double impuestos) {
        this.impuestos = impuestos;
    }

    public OrdenCompra getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(OrdenCompra idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    @XmlTransient
    public List<InsumoFacturaCompra> getInsumoFacturaCompraList() {
        return insumoFacturaCompraList;
    }

    public void setInsumoFacturaCompraList(List<InsumoFacturaCompra> insumoFacturaCompraList) {
        this.insumoFacturaCompraList = insumoFacturaCompraList;
    }

    @XmlTransient
    public List<AdjuntoFactura> getAdjuntoFacturaList() {
        return adjuntoFacturaList;
    }

    public void setAdjuntoFacturaList(List<AdjuntoFactura> adjuntoFacturaList) {
        this.adjuntoFacturaList = adjuntoFacturaList;
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
        if (!(object instanceof FacturaCompra)) {
            return false;
        }
        FacturaCompra other = (FacturaCompra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.FacturaCompra[ id=" + id + " ]";
    }
    
}
