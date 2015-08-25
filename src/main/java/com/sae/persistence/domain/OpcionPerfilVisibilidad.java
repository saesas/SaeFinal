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
@Table(name = "opcion_perfil_visibilidad", catalog = "bdsae", schema = "autenticacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OpcionPerfilVisibilidad.findAll", query = "SELECT o FROM OpcionPerfilVisibilidad o"),
    @NamedQuery(name = "OpcionPerfilVisibilidad.findById", query = "SELECT o FROM OpcionPerfilVisibilidad o WHERE o.id = :id"),
    @NamedQuery(name = "OpcionPerfilVisibilidad.findByEstado", query = "SELECT o FROM OpcionPerfilVisibilidad o WHERE o.estado = :estado"),
    @NamedQuery(name = "OpcionPerfilVisibilidad.findByFechaRegistro", query = "SELECT o FROM OpcionPerfilVisibilidad o WHERE o.fechaRegistro = :fechaRegistro")})
public class OpcionPerfilVisibilidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @JoinColumn(name = "id_visibilidad", referencedColumnName = "id")
    @ManyToOne
    private Visibilidad idVisibilidad;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @ManyToOne
    private Usuario idUsuario;
    @JoinColumn(name = "id_perfil", referencedColumnName = "id")
    @ManyToOne
    private Perfil idPerfil;
    @JoinColumn(name = "id_opcion", referencedColumnName = "id")
    @ManyToOne
    private Opcion idOpcion;

    public OpcionPerfilVisibilidad() {
    }

    public OpcionPerfilVisibilidad(Integer id) {
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

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Visibilidad getIdVisibilidad() {
        return idVisibilidad;
    }

    public void setIdVisibilidad(Visibilidad idVisibilidad) {
        this.idVisibilidad = idVisibilidad;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Perfil getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Perfil idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Opcion getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(Opcion idOpcion) {
        this.idOpcion = idOpcion;
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
        if (!(object instanceof OpcionPerfilVisibilidad)) {
            return false;
        }
        OpcionPerfilVisibilidad other = (OpcionPerfilVisibilidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.OpcionPerfilVisibilidad[ id=" + id + " ]";
    }
    
}
