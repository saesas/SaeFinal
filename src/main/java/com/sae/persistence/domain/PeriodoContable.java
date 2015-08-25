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
@Table(name = "periodo_contable", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PeriodoContable.findAll", query = "SELECT p FROM PeriodoContable p"),
    @NamedQuery(name = "PeriodoContable.findById", query = "SELECT p FROM PeriodoContable p WHERE p.id = :id"),
    @NamedQuery(name = "PeriodoContable.findByFechaInicio", query = "SELECT p FROM PeriodoContable p WHERE p.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "PeriodoContable.findByFechaFin", query = "SELECT p FROM PeriodoContable p WHERE p.fechaFin = :fechaFin"),
    @NamedQuery(name = "PeriodoContable.findByEstado", query = "SELECT p FROM PeriodoContable p WHERE p.estado = :estado"),
    @NamedQuery(name = "PeriodoContable.findByFechaRegistro", query = "SELECT p FROM PeriodoContable p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "PeriodoContable.findByIdUsuario", query = "SELECT p FROM PeriodoContable p WHERE p.idUsuario = :idUsuario")})
public class PeriodoContable implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;

    public PeriodoContable() {
    }

    public PeriodoContable(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PeriodoContable)) {
            return false;
        }
        PeriodoContable other = (PeriodoContable) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.PeriodoContable[ id=" + id + " ]";
    }
    
}
