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
@Table(name = "acta_cobro_actividad", catalog = "bdsae", schema = "tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActaCobroActividad.findAll", query = "SELECT a FROM ActaCobroActividad a"),
    @NamedQuery(name = "ActaCobroActividad.findById", query = "SELECT a FROM ActaCobroActividad a WHERE a.id = :id"),
    @NamedQuery(name = "ActaCobroActividad.findByCantidad", query = "SELECT a FROM ActaCobroActividad a WHERE a.cantidad = :cantidad"),
    @NamedQuery(name = "ActaCobroActividad.findByIdUnidad", query = "SELECT a FROM ActaCobroActividad a WHERE a.idUnidad = :idUnidad"),
    @NamedQuery(name = "ActaCobroActividad.findByValorUnitario", query = "SELECT a FROM ActaCobroActividad a WHERE a.valorUnitario = :valorUnitario"),
    @NamedQuery(name = "ActaCobroActividad.findBySubtotal", query = "SELECT a FROM ActaCobroActividad a WHERE a.subtotal = :subtotal"),
    @NamedQuery(name = "ActaCobroActividad.findByIdMoneda", query = "SELECT a FROM ActaCobroActividad a WHERE a.idMoneda = :idMoneda"),
    @NamedQuery(name = "ActaCobroActividad.findByEstado", query = "SELECT a FROM ActaCobroActividad a WHERE a.estado = :estado"),
    @NamedQuery(name = "ActaCobroActividad.findByFechaRegistro", query = "SELECT a FROM ActaCobroActividad a WHERE a.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ActaCobroActividad.findByIdUsuario", query = "SELECT a FROM ActaCobroActividad a WHERE a.idUsuario = :idUsuario")})
public class ActaCobroActividad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad")
    private Double cantidad;
    @Column(name = "id_unidad")
    private Integer idUnidad;
    @Column(name = "valor_unitario")
    private Double valorUnitario;
    @Column(name = "subtotal")
    private Double subtotal;
    @Column(name = "id_moneda")
    private Integer idMoneda;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_actividad", referencedColumnName = "id")
    @ManyToOne
    private Actividad idActividad;
    @JoinColumn(name = "id_acta_cobro", referencedColumnName = "id")
    @ManyToOne
    private ActaCobro idActaCobro;

    public ActaCobroActividad() {
    }

    public ActaCobroActividad(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(Integer idMoneda) {
        this.idMoneda = idMoneda;
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

    public Actividad getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Actividad idActividad) {
        this.idActividad = idActividad;
    }

    public ActaCobro getIdActaCobro() {
        return idActaCobro;
    }

    public void setIdActaCobro(ActaCobro idActaCobro) {
        this.idActaCobro = idActaCobro;
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
        if (!(object instanceof ActaCobroActividad)) {
            return false;
        }
        ActaCobroActividad other = (ActaCobroActividad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ActaCobroActividad[ id=" + id + " ]";
    }
    
}
