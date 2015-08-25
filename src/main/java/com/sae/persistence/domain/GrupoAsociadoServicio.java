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
@Table(name = "grupo_asociado_servicio", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoAsociadoServicio.findAll", query = "SELECT g FROM GrupoAsociadoServicio g"),
    @NamedQuery(name = "GrupoAsociadoServicio.findById", query = "SELECT g FROM GrupoAsociadoServicio g WHERE g.id = :id"),
    @NamedQuery(name = "GrupoAsociadoServicio.findByIdServicio", query = "SELECT g FROM GrupoAsociadoServicio g WHERE g.idServicio = :idServicio"),
    @NamedQuery(name = "GrupoAsociadoServicio.findByEstado", query = "SELECT g FROM GrupoAsociadoServicio g WHERE g.estado = :estado"),
    @NamedQuery(name = "GrupoAsociadoServicio.findByFechaRegistro", query = "SELECT g FROM GrupoAsociadoServicio g WHERE g.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "GrupoAsociadoServicio.findByIdUsuario", query = "SELECT g FROM GrupoAsociadoServicio g WHERE g.idUsuario = :idUsuario")})
public class GrupoAsociadoServicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_servicio")
    private Integer idServicio;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_grupo", referencedColumnName = "id")
    @ManyToOne
    private GrupoServicios idGrupo;

    public GrupoAsociadoServicio() {
    }

    public GrupoAsociadoServicio(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
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

    public GrupoServicios getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(GrupoServicios idGrupo) {
        this.idGrupo = idGrupo;
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
        if (!(object instanceof GrupoAsociadoServicio)) {
            return false;
        }
        GrupoAsociadoServicio other = (GrupoAsociadoServicio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.GrupoAsociadoServicio[ id=" + id + " ]";
    }
    
}
