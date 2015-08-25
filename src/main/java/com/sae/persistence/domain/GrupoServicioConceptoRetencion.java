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
@Table(name = "grupo_servicio_concepto_retencion", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoServicioConceptoRetencion.findAll", query = "SELECT g FROM GrupoServicioConceptoRetencion g"),
    @NamedQuery(name = "GrupoServicioConceptoRetencion.findById", query = "SELECT g FROM GrupoServicioConceptoRetencion g WHERE g.id = :id"),
    @NamedQuery(name = "GrupoServicioConceptoRetencion.findByReteica", query = "SELECT g FROM GrupoServicioConceptoRetencion g WHERE g.reteica = :reteica"),
    @NamedQuery(name = "GrupoServicioConceptoRetencion.findByEstado", query = "SELECT g FROM GrupoServicioConceptoRetencion g WHERE g.estado = :estado"),
    @NamedQuery(name = "GrupoServicioConceptoRetencion.findByFechaRegistro", query = "SELECT g FROM GrupoServicioConceptoRetencion g WHERE g.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "GrupoServicioConceptoRetencion.findByUsuarioRegistro", query = "SELECT g FROM GrupoServicioConceptoRetencion g WHERE g.usuarioRegistro = :usuarioRegistro")})
public class GrupoServicioConceptoRetencion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "reteica")
    private Double reteica;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "usuario_registro")
    private Integer usuarioRegistro;
    @JoinColumn(name = "grupo", referencedColumnName = "id")
    @ManyToOne
    private GrupoServicios grupo;

    public GrupoServicioConceptoRetencion() {
    }

    public GrupoServicioConceptoRetencion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getReteica() {
        return reteica;
    }

    public void setReteica(Double reteica) {
        this.reteica = reteica;
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

    public Integer getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(Integer usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public GrupoServicios getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoServicios grupo) {
        this.grupo = grupo;
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
        if (!(object instanceof GrupoServicioConceptoRetencion)) {
            return false;
        }
        GrupoServicioConceptoRetencion other = (GrupoServicioConceptoRetencion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.GrupoServicioConceptoRetencion[ id=" + id + " ]";
    }
    
}
