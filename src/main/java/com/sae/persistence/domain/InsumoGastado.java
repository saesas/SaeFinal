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
@Table(name = "insumo_gastado", catalog = "bdsae", schema = "tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InsumoGastado.findAll", query = "SELECT i FROM InsumoGastado i"),
    @NamedQuery(name = "InsumoGastado.findById", query = "SELECT i FROM InsumoGastado i WHERE i.id = :id"),
    @NamedQuery(name = "InsumoGastado.findByEstado", query = "SELECT i FROM InsumoGastado i WHERE i.estado = :estado"),
    @NamedQuery(name = "InsumoGastado.findByIdInsumoFactura", query = "SELECT i FROM InsumoGastado i WHERE i.idInsumoFactura = :idInsumoFactura"),
    @NamedQuery(name = "InsumoGastado.findByCantidad", query = "SELECT i FROM InsumoGastado i WHERE i.cantidad = :cantidad"),
    @NamedQuery(name = "InsumoGastado.findByIdUsuario", query = "SELECT i FROM InsumoGastado i WHERE i.idUsuario = :idUsuario"),
    @NamedQuery(name = "InsumoGastado.findByIdActividad", query = "SELECT i FROM InsumoGastado i WHERE i.idActividad = :idActividad"),
    @NamedQuery(name = "InsumoGastado.findByFechaRegistro", query = "SELECT i FROM InsumoGastado i WHERE i.fechaRegistro = :fechaRegistro")})
public class InsumoGastado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_insumo_factura")
    private Integer idInsumoFactura;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_actividad")
    private Integer idActividad;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_bitacora", referencedColumnName = "id")
    @ManyToOne
    private Bitacora idBitacora;

    public InsumoGastado() {
    }

    public InsumoGastado(Integer id) {
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

    public Integer getIdInsumoFactura() {
        return idInsumoFactura;
    }

    public void setIdInsumoFactura(Integer idInsumoFactura) {
        this.idInsumoFactura = idInsumoFactura;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Integer idActividad) {
        this.idActividad = idActividad;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Bitacora getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Bitacora idBitacora) {
        this.idBitacora = idBitacora;
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
        if (!(object instanceof InsumoGastado)) {
            return false;
        }
        InsumoGastado other = (InsumoGastado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.InsumoGastado[ id=" + id + " ]";
    }
    
}
