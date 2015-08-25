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
@Table(name = "cuenta_cobro", catalog = "bdsae", schema = "compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuentaCobro.findAll", query = "SELECT c FROM CuentaCobro c"),
    @NamedQuery(name = "CuentaCobro.findById", query = "SELECT c FROM CuentaCobro c WHERE c.id = :id"),
    @NamedQuery(name = "CuentaCobro.findByEstado", query = "SELECT c FROM CuentaCobro c WHERE c.estado = :estado"),
    @NamedQuery(name = "CuentaCobro.findByCodigo", query = "SELECT c FROM CuentaCobro c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "CuentaCobro.findByFechaEmision", query = "SELECT c FROM CuentaCobro c WHERE c.fechaEmision = :fechaEmision"),
    @NamedQuery(name = "CuentaCobro.findByNumero", query = "SELECT c FROM CuentaCobro c WHERE c.numero = :numero"),
    @NamedQuery(name = "CuentaCobro.findByFechaEntrega", query = "SELECT c FROM CuentaCobro c WHERE c.fechaEntrega = :fechaEntrega"),
    @NamedQuery(name = "CuentaCobro.findByRetencion", query = "SELECT c FROM CuentaCobro c WHERE c.retencion = :retencion"),
    @NamedQuery(name = "CuentaCobro.findByDescuentos", query = "SELECT c FROM CuentaCobro c WHERE c.descuentos = :descuentos"),
    @NamedQuery(name = "CuentaCobro.findByFechaInicio", query = "SELECT c FROM CuentaCobro c WHERE c.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "CuentaCobro.findByFechaFin", query = "SELECT c FROM CuentaCobro c WHERE c.fechaFin = :fechaFin"),
    @NamedQuery(name = "CuentaCobro.findByIdUsuario", query = "SELECT c FROM CuentaCobro c WHERE c.idUsuario = :idUsuario"),
    @NamedQuery(name = "CuentaCobro.findByIdRazonSocial", query = "SELECT c FROM CuentaCobro c WHERE c.idRazonSocial = :idRazonSocial")})
public class CuentaCobro implements Serializable {
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
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "fecha_entrega")
    @Temporal(TemporalType.DATE)
    private Date fechaEntrega;
    @Column(name = "retencion")
    private Boolean retencion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "descuentos")
    private Double descuentos;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @JoinColumn(name = "id_orden_compra", referencedColumnName = "id")
    @ManyToOne
    private OrdenCompra idOrdenCompra;
    @OneToMany(mappedBy = "idCuentaCobro")
    private List<AdjuntoCuentaCobro> adjuntoCuentaCobroList;

    public CuentaCobro() {
    }

    public CuentaCobro(Integer id) {
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

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Boolean getRetencion() {
        return retencion;
    }

    public void setRetencion(Boolean retencion) {
        this.retencion = retencion;
    }

    public Double getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(Double descuentos) {
        this.descuentos = descuentos;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    public OrdenCompra getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(OrdenCompra idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    @XmlTransient
    public List<AdjuntoCuentaCobro> getAdjuntoCuentaCobroList() {
        return adjuntoCuentaCobroList;
    }

    public void setAdjuntoCuentaCobroList(List<AdjuntoCuentaCobro> adjuntoCuentaCobroList) {
        this.adjuntoCuentaCobroList = adjuntoCuentaCobroList;
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
        if (!(object instanceof CuentaCobro)) {
            return false;
        }
        CuentaCobro other = (CuentaCobro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.CuentaCobro[ id=" + id + " ]";
    }
    
}
