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
@Table(name = "estado_convocatoria", catalog = "bdsae", schema = "licitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoConvocatoria.findAll", query = "SELECT e FROM EstadoConvocatoria e"),
    @NamedQuery(name = "EstadoConvocatoria.findById", query = "SELECT e FROM EstadoConvocatoria e WHERE e.id = :id"),
    @NamedQuery(name = "EstadoConvocatoria.findByFecha", query = "SELECT e FROM EstadoConvocatoria e WHERE e.fecha = :fecha"),
    @NamedQuery(name = "EstadoConvocatoria.findByEstado", query = "SELECT e FROM EstadoConvocatoria e WHERE e.estado = :estado"),
    @NamedQuery(name = "EstadoConvocatoria.findByFechaRegistro", query = "SELECT e FROM EstadoConvocatoria e WHERE e.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "EstadoConvocatoria.findByIdUsuario", query = "SELECT e FROM EstadoConvocatoria e WHERE e.idUsuario = :idUsuario")})
public class EstadoConvocatoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_estado_convocatoria", referencedColumnName = "id")
    @ManyToOne
    private TipoEstadoConvocatoria idEstadoConvocatoria;
    @JoinColumn(name = "id_convocatoria", referencedColumnName = "id")
    @ManyToOne
    private Convocatoria idConvocatoria;

    public EstadoConvocatoria() {
    }

    public EstadoConvocatoria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public TipoEstadoConvocatoria getIdEstadoConvocatoria() {
        return idEstadoConvocatoria;
    }

    public void setIdEstadoConvocatoria(TipoEstadoConvocatoria idEstadoConvocatoria) {
        this.idEstadoConvocatoria = idEstadoConvocatoria;
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
        if (!(object instanceof EstadoConvocatoria)) {
            return false;
        }
        EstadoConvocatoria other = (EstadoConvocatoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.EstadoConvocatoria[ id=" + id + " ]";
    }
    
}
