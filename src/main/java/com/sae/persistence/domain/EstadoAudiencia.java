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
@Table(name = "estado_audiencia", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoAudiencia.findAll", query = "SELECT e FROM EstadoAudiencia e"),
    @NamedQuery(name = "EstadoAudiencia.findById", query = "SELECT e FROM EstadoAudiencia e WHERE e.id = :id"),
    @NamedQuery(name = "EstadoAudiencia.findByFechaEstado", query = "SELECT e FROM EstadoAudiencia e WHERE e.fechaEstado = :fechaEstado"),
    @NamedQuery(name = "EstadoAudiencia.findByFechaRegistro", query = "SELECT e FROM EstadoAudiencia e WHERE e.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "EstadoAudiencia.findByIdUsuario", query = "SELECT e FROM EstadoAudiencia e WHERE e.idUsuario = :idUsuario")})
public class EstadoAudiencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha_estado")
    @Temporal(TemporalType.DATE)
    private Date fechaEstado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_estado", referencedColumnName = "id")
    @ManyToOne
    private TipoEstadoAudiencia idEstado;
    @JoinColumn(name = "id_audiencia", referencedColumnName = "id")
    @ManyToOne
    private Audiencia idAudiencia;

    public EstadoAudiencia() {
    }

    public EstadoAudiencia(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(Date fechaEstado) {
        this.fechaEstado = fechaEstado;
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

    public TipoEstadoAudiencia getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(TipoEstadoAudiencia idEstado) {
        this.idEstado = idEstado;
    }

    public Audiencia getIdAudiencia() {
        return idAudiencia;
    }

    public void setIdAudiencia(Audiencia idAudiencia) {
        this.idAudiencia = idAudiencia;
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
        if (!(object instanceof EstadoAudiencia)) {
            return false;
        }
        EstadoAudiencia other = (EstadoAudiencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.EstadoAudiencia[ id=" + id + " ]";
    }
    
}
