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
@Table(name = "usuario_perfil", catalog = "bdsae", schema = "autenticacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioPerfil.findAll", query = "SELECT u FROM UsuarioPerfil u"),
    @NamedQuery(name = "UsuarioPerfil.findById", query = "SELECT u FROM UsuarioPerfil u WHERE u.id = :id"),
    @NamedQuery(name = "UsuarioPerfil.findByEstado", query = "SELECT u FROM UsuarioPerfil u WHERE u.estado = :estado"),
    @NamedQuery(name = "UsuarioPerfil.findByFechaRegistro", query = "SELECT u FROM UsuarioPerfil u WHERE u.fechaRegistro = :fechaRegistro")})
public class UsuarioPerfil implements Serializable {
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
    @JoinColumn(name = "id_usuario_registro", referencedColumnName = "id")
    @ManyToOne
    private Usuario idUsuarioRegistro;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @ManyToOne
    private Usuario idUsuario;
    @JoinColumn(name = "id_perfil", referencedColumnName = "id")
    @ManyToOne
    private Perfil idPerfil;

    public UsuarioPerfil() {
    }

    public UsuarioPerfil(Integer id) {
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

    public Usuario getIdUsuarioRegistro() {
        return idUsuarioRegistro;
    }

    public void setIdUsuarioRegistro(Usuario idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioPerfil)) {
            return false;
        }
        UsuarioPerfil other = (UsuarioPerfil) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.UsuarioPerfil[ id=" + id + " ]";
    }
    
}
