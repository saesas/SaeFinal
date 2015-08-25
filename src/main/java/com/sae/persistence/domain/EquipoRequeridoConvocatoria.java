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
@Table(name = "equipo_requerido_convocatoria", catalog = "bdsae", schema = "licitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EquipoRequeridoConvocatoria.findAll", query = "SELECT e FROM EquipoRequeridoConvocatoria e"),
    @NamedQuery(name = "EquipoRequeridoConvocatoria.findById", query = "SELECT e FROM EquipoRequeridoConvocatoria e WHERE e.id = :id"),
    @NamedQuery(name = "EquipoRequeridoConvocatoria.findByTipo", query = "SELECT e FROM EquipoRequeridoConvocatoria e WHERE e.tipo = :tipo"),
    @NamedQuery(name = "EquipoRequeridoConvocatoria.findByModelo", query = "SELECT e FROM EquipoRequeridoConvocatoria e WHERE e.modelo = :modelo"),
    @NamedQuery(name = "EquipoRequeridoConvocatoria.findByCapacidad", query = "SELECT e FROM EquipoRequeridoConvocatoria e WHERE e.capacidad = :capacidad"),
    @NamedQuery(name = "EquipoRequeridoConvocatoria.findByEspecificacion", query = "SELECT e FROM EquipoRequeridoConvocatoria e WHERE e.especificacion = :especificacion"),
    @NamedQuery(name = "EquipoRequeridoConvocatoria.findByObservacion", query = "SELECT e FROM EquipoRequeridoConvocatoria e WHERE e.observacion = :observacion"),
    @NamedQuery(name = "EquipoRequeridoConvocatoria.findByEstado", query = "SELECT e FROM EquipoRequeridoConvocatoria e WHERE e.estado = :estado"),
    @NamedQuery(name = "EquipoRequeridoConvocatoria.findByIdUsuario", query = "SELECT e FROM EquipoRequeridoConvocatoria e WHERE e.idUsuario = :idUsuario"),
    @NamedQuery(name = "EquipoRequeridoConvocatoria.findByFechaRegistro", query = "SELECT e FROM EquipoRequeridoConvocatoria e WHERE e.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "EquipoRequeridoConvocatoria.findByAsunto", query = "SELECT e FROM EquipoRequeridoConvocatoria e WHERE e.asunto = :asunto"),
    @NamedQuery(name = "EquipoRequeridoConvocatoria.findByCantidad", query = "SELECT e FROM EquipoRequeridoConvocatoria e WHERE e.cantidad = :cantidad")})
public class EquipoRequeridoConvocatoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "modelo")
    private String modelo;
    @Column(name = "capacidad")
    private String capacidad;
    @Column(name = "especificacion")
    private String especificacion;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "asunto")
    private String asunto;
    @Column(name = "cantidad")
    private Integer cantidad;
    @JoinColumn(name = "id_convocatoria", referencedColumnName = "id")
    @ManyToOne
    private Convocatoria idConvocatoria;

    public EquipoRequeridoConvocatoria() {
    }

    public EquipoRequeridoConvocatoria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public String getEspecificacion() {
        return especificacion;
    }

    public void setEspecificacion(String especificacion) {
        this.especificacion = especificacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Convocatoria getIdConvocatoria() {
        return idConvocatoria;
    }

    public void setIdConvocatoria(Convocatoria idConvocatoria) {
        this.idConvocatoria = idConvocatoria;
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
        if (!(object instanceof EquipoRequeridoConvocatoria)) {
            return false;
        }
        EquipoRequeridoConvocatoria other = (EquipoRequeridoConvocatoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.EquipoRequeridoConvocatoria[ id=" + id + " ]";
    }
    
}
