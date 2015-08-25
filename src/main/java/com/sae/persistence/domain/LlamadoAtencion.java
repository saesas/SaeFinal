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
@Table(name = "llamado_atencion", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LlamadoAtencion.findAll", query = "SELECT l FROM LlamadoAtencion l"),
    @NamedQuery(name = "LlamadoAtencion.findById", query = "SELECT l FROM LlamadoAtencion l WHERE l.id = :id"),
    @NamedQuery(name = "LlamadoAtencion.findByEstado", query = "SELECT l FROM LlamadoAtencion l WHERE l.estado = :estado"),
    @NamedQuery(name = "LlamadoAtencion.findByDescripcion", query = "SELECT l FROM LlamadoAtencion l WHERE l.descripcion = :descripcion"),
    @NamedQuery(name = "LlamadoAtencion.findByIdTercero", query = "SELECT l FROM LlamadoAtencion l WHERE l.idTercero = :idTercero"),
    @NamedQuery(name = "LlamadoAtencion.findByFechaRegistro", query = "SELECT l FROM LlamadoAtencion l WHERE l.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "LlamadoAtencion.findByIdUsuario", query = "SELECT l FROM LlamadoAtencion l WHERE l.idUsuario = :idUsuario"),
    @NamedQuery(name = "LlamadoAtencion.findByIdRazonSocial", query = "SELECT l FROM LlamadoAtencion l WHERE l.idRazonSocial = :idRazonSocial")})
public class LlamadoAtencion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "id_tercero")
    private Integer idTercero;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @OneToMany(mappedBy = "idLlamadoAtencion")
    private List<MotivoLlamadoAtencion> motivoLlamadoAtencionList;

    public LlamadoAtencion() {
    }

    public LlamadoAtencion(Integer id) {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdTercero() {
        return idTercero;
    }

    public void setIdTercero(Integer idTercero) {
        this.idTercero = idTercero;
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

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    @XmlTransient
    public List<MotivoLlamadoAtencion> getMotivoLlamadoAtencionList() {
        return motivoLlamadoAtencionList;
    }

    public void setMotivoLlamadoAtencionList(List<MotivoLlamadoAtencion> motivoLlamadoAtencionList) {
        this.motivoLlamadoAtencionList = motivoLlamadoAtencionList;
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
        if (!(object instanceof LlamadoAtencion)) {
            return false;
        }
        LlamadoAtencion other = (LlamadoAtencion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.LlamadoAtencion[ id=" + id + " ]";
    }
    
}
