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
@Table(name = "estado_proyecto", catalog = "bdsae", schema = "tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoProyecto.findAll", query = "SELECT e FROM EstadoProyecto e"),
    @NamedQuery(name = "EstadoProyecto.findById", query = "SELECT e FROM EstadoProyecto e WHERE e.id = :id"),
    @NamedQuery(name = "EstadoProyecto.findByFechaRegistro", query = "SELECT e FROM EstadoProyecto e WHERE e.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "EstadoProyecto.findByIdUsuario", query = "SELECT e FROM EstadoProyecto e WHERE e.idUsuario = :idUsuario"),
    @NamedQuery(name = "EstadoProyecto.findByEstado", query = "SELECT e FROM EstadoProyecto e WHERE e.estado = :estado")})
public class EstadoProyecto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "estado")
    private Boolean estado;
    @JoinColumn(name = "id_estado", referencedColumnName = "id")
    @ManyToOne
    private TipoEstadoProyecto idEstado;
    @JoinColumn(name = "id_proyecto", referencedColumnName = "id")
    @ManyToOne
    private Proyecto idProyecto;

    public EstadoProyecto() {
    }

    public EstadoProyecto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public TipoEstadoProyecto getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(TipoEstadoProyecto idEstado) {
        this.idEstado = idEstado;
    }

    public Proyecto getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Proyecto idProyecto) {
        this.idProyecto = idProyecto;
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
        if (!(object instanceof EstadoProyecto)) {
            return false;
        }
        EstadoProyecto other = (EstadoProyecto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.EstadoProyecto[ id=" + id + " ]";
    }
    
}
