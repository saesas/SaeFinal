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
@Table(name = "identificacion_tercero", catalog = "bdsae", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IdentificacionTercero.findAll", query = "SELECT i FROM IdentificacionTercero i"),
    @NamedQuery(name = "IdentificacionTercero.findById", query = "SELECT i FROM IdentificacionTercero i WHERE i.id = :id"),
    @NamedQuery(name = "IdentificacionTercero.findByNumeroIdentificacion", query = "SELECT i FROM IdentificacionTercero i WHERE i.numeroIdentificacion = :numeroIdentificacion"),
    @NamedQuery(name = "IdentificacionTercero.findByEstado", query = "SELECT i FROM IdentificacionTercero i WHERE i.estado = :estado"),
    @NamedQuery(name = "IdentificacionTercero.findByFechaExpedicion", query = "SELECT i FROM IdentificacionTercero i WHERE i.fechaExpedicion = :fechaExpedicion"),
    @NamedQuery(name = "IdentificacionTercero.findByFechaRegistro", query = "SELECT i FROM IdentificacionTercero i WHERE i.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "IdentificacionTercero.findByIdUsuario", query = "SELECT i FROM IdentificacionTercero i WHERE i.idUsuario = :idUsuario")})
public class IdentificacionTercero implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "numero_identificacion")
    private String numeroIdentificacion;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_expedicion")
    @Temporal(TemporalType.DATE)
    private Date fechaExpedicion;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_tipo_identificacion", referencedColumnName = "id")
    @ManyToOne
    private TipoDocumento idTipoIdentificacion;
    @JoinColumn(name = "id_tercero", referencedColumnName = "id")
    @ManyToOne
    private Tercero idTercero;

    public IdentificacionTercero() {
    }

    public IdentificacionTercero(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Date getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(Date fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
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

    public TipoDocumento getIdTipoIdentificacion() {
        return idTipoIdentificacion;
    }

    public void setIdTipoIdentificacion(TipoDocumento idTipoIdentificacion) {
        this.idTipoIdentificacion = idTipoIdentificacion;
    }

    public Tercero getIdTercero() {
        return idTercero;
    }

    public void setIdTercero(Tercero idTercero) {
        this.idTercero = idTercero;
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
        if (!(object instanceof IdentificacionTercero)) {
            return false;
        }
        IdentificacionTercero other = (IdentificacionTercero) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.IdentificacionTercero[ id=" + id + " ]";
    }
    
}
