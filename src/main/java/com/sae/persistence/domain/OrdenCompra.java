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
@Table(name = "orden_compra", catalog = "bdsae", schema = "compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdenCompra.findAll", query = "SELECT o FROM OrdenCompra o"),
    @NamedQuery(name = "OrdenCompra.findById", query = "SELECT o FROM OrdenCompra o WHERE o.id = :id"),
    @NamedQuery(name = "OrdenCompra.findByEstado", query = "SELECT o FROM OrdenCompra o WHERE o.estado = :estado"),
    @NamedQuery(name = "OrdenCompra.findBySubtotal", query = "SELECT o FROM OrdenCompra o WHERE o.subtotal = :subtotal"),
    @NamedQuery(name = "OrdenCompra.findByDescuentos", query = "SELECT o FROM OrdenCompra o WHERE o.descuentos = :descuentos"),
    @NamedQuery(name = "OrdenCompra.findByFletes", query = "SELECT o FROM OrdenCompra o WHERE o.fletes = :fletes"),
    @NamedQuery(name = "OrdenCompra.findByIva", query = "SELECT o FROM OrdenCompra o WHERE o.iva = :iva"),
    @NamedQuery(name = "OrdenCompra.findByReteiva", query = "SELECT o FROM OrdenCompra o WHERE o.reteiva = :reteiva"),
    @NamedQuery(name = "OrdenCompra.findByRetefuente", query = "SELECT o FROM OrdenCompra o WHERE o.retefuente = :retefuente"),
    @NamedQuery(name = "OrdenCompra.findByRetecree", query = "SELECT o FROM OrdenCompra o WHERE o.retecree = :retecree"),
    @NamedQuery(name = "OrdenCompra.findByReteica", query = "SELECT o FROM OrdenCompra o WHERE o.reteica = :reteica"),
    @NamedQuery(name = "OrdenCompra.findByTotalFacturado", query = "SELECT o FROM OrdenCompra o WHERE o.totalFacturado = :totalFacturado"),
    @NamedQuery(name = "OrdenCompra.findByTotalFacturar", query = "SELECT o FROM OrdenCompra o WHERE o.totalFacturar = :totalFacturar"),
    @NamedQuery(name = "OrdenCompra.findByAdministracion", query = "SELECT o FROM OrdenCompra o WHERE o.administracion = :administracion"),
    @NamedQuery(name = "OrdenCompra.findByUtilidad", query = "SELECT o FROM OrdenCompra o WHERE o.utilidad = :utilidad"),
    @NamedQuery(name = "OrdenCompra.findByImpuesto", query = "SELECT o FROM OrdenCompra o WHERE o.impuesto = :impuesto"),
    @NamedQuery(name = "OrdenCompra.findByFechaRegistro", query = "SELECT o FROM OrdenCompra o WHERE o.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "OrdenCompra.findByIdUsuario", query = "SELECT o FROM OrdenCompra o WHERE o.idUsuario = :idUsuario")})
public class OrdenCompra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "subtotal")
    private Double subtotal;
    @Column(name = "descuentos")
    private Double descuentos;
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
    @Column(name = "total_facturado")
    private Double totalFacturado;
    @Column(name = "total_facturar")
    private Double totalFacturar;
    @Column(name = "administracion")
    private Double administracion;
    @Column(name = "utilidad")
    private Double utilidad;
    @Column(name = "impuesto")
    private Double impuesto;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @OneToMany(mappedBy = "idOrdenCompra")
    private List<CuentaCobro> cuentaCobroList;
    @OneToMany(mappedBy = "idOrdenCompra")
    private List<FacturaCompra> facturaCompraList;
    @OneToMany(mappedBy = "idOrdenCompra")
    private List<OrdenCompraInsumo> ordenCompraInsumoList;
    @OneToMany(mappedBy = "idOrdenCompra")
    private List<EstadoOrdenCompra> estadoOrdenCompraList;

    public OrdenCompra() {
    }

    public OrdenCompra(Integer id) {
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

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(Double descuentos) {
        this.descuentos = descuentos;
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

    public Double getTotalFacturado() {
        return totalFacturado;
    }

    public void setTotalFacturado(Double totalFacturado) {
        this.totalFacturado = totalFacturado;
    }

    public Double getTotalFacturar() {
        return totalFacturar;
    }

    public void setTotalFacturar(Double totalFacturar) {
        this.totalFacturar = totalFacturar;
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

    public Double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Double impuesto) {
        this.impuesto = impuesto;
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

    @XmlTransient
    public List<CuentaCobro> getCuentaCobroList() {
        return cuentaCobroList;
    }

    public void setCuentaCobroList(List<CuentaCobro> cuentaCobroList) {
        this.cuentaCobroList = cuentaCobroList;
    }

    @XmlTransient
    public List<FacturaCompra> getFacturaCompraList() {
        return facturaCompraList;
    }

    public void setFacturaCompraList(List<FacturaCompra> facturaCompraList) {
        this.facturaCompraList = facturaCompraList;
    }

    @XmlTransient
    public List<OrdenCompraInsumo> getOrdenCompraInsumoList() {
        return ordenCompraInsumoList;
    }

    public void setOrdenCompraInsumoList(List<OrdenCompraInsumo> ordenCompraInsumoList) {
        this.ordenCompraInsumoList = ordenCompraInsumoList;
    }

    @XmlTransient
    public List<EstadoOrdenCompra> getEstadoOrdenCompraList() {
        return estadoOrdenCompraList;
    }

    public void setEstadoOrdenCompraList(List<EstadoOrdenCompra> estadoOrdenCompraList) {
        this.estadoOrdenCompraList = estadoOrdenCompraList;
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
        if (!(object instanceof OrdenCompra)) {
            return false;
        }
        OrdenCompra other = (OrdenCompra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.OrdenCompra[ id=" + id + " ]";
    }
    
}
