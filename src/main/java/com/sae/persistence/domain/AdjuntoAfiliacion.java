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
@Table(name = "adjunto_afiliacion", catalog = "bdsae", schema = "talento_humano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdjuntoAfiliacion.findAll", query = "SELECT a FROM AdjuntoAfiliacion a"),
    @NamedQuery(name = "AdjuntoAfiliacion.findById", query = "SELECT a FROM AdjuntoAfiliacion a WHERE a.id = :id"),
    @NamedQuery(name = "AdjuntoAfiliacion.findByEstado", query = "SELECT a FROM AdjuntoAfiliacion a WHERE a.estado = :estado"),
    @NamedQuery(name = "AdjuntoAfiliacion.findByAdjunto", query = "SELECT a FROM AdjuntoAfiliacion a WHERE a.adjunto = :adjunto"),
    @NamedQuery(name = "AdjuntoAfiliacion.findByIdTipoAdjunto", query = "SELECT a FROM AdjuntoAfiliacion a WHERE a.idTipoAdjunto = :idTipoAdjunto"),
    @NamedQuery(name = "AdjuntoAfiliacion.findByFechaRegistro", query = "SELECT a FROM AdjuntoAfiliacion a WHERE a.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "AdjuntoAfiliacion.findByIdUsuario", query = "SELECT a FROM AdjuntoAfiliacion a WHERE a.idUsuario = :idUsuario")})
public class AdjuntoAfiliacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "adjunto")
    private String adjunto;
    @Column(name = "id_tipo_adjunto")
    private Integer idTipoAdjunto;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_afiliacion", referencedColumnName = "id")
    @ManyToOne
    private Afiliacion idAfiliacion;

    public AdjuntoAfiliacion() {
    }

    public AdjuntoAfiliacion(Integer id) {
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

    public String getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(String adjunto) {
        this.adjunto = adjunto;
    }

    public Integer getIdTipoAdjunto() {
        return idTipoAdjunto;
    }

    public void setIdTipoAdjunto(Integer idTipoAdjunto) {
        this.idTipoAdjunto = idTipoAdjunto;
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

    public Afiliacion getIdAfiliacion() {
        return idAfiliacion;
    }

    public void setIdAfiliacion(Afiliacion idAfiliacion) {
        this.idAfiliacion = idAfiliacion;
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
        if (!(object instanceof AdjuntoAfiliacion)) {
            return false;
        }
        AdjuntoAfiliacion other = (AdjuntoAfiliacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.AdjuntoAfiliacion[ id=" + id + " ]";
    }
    
}
