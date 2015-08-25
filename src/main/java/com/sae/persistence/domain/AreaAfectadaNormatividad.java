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
@Table(name = "area_afectada_normatividad", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AreaAfectadaNormatividad.findAll", query = "SELECT a FROM AreaAfectadaNormatividad a"),
    @NamedQuery(name = "AreaAfectadaNormatividad.findById", query = "SELECT a FROM AreaAfectadaNormatividad a WHERE a.id = :id"),
    @NamedQuery(name = "AreaAfectadaNormatividad.findByImpacto", query = "SELECT a FROM AreaAfectadaNormatividad a WHERE a.impacto = :impacto"),
    @NamedQuery(name = "AreaAfectadaNormatividad.findByPorcentajeCumplimiento", query = "SELECT a FROM AreaAfectadaNormatividad a WHERE a.porcentajeCumplimiento = :porcentajeCumplimiento"),
    @NamedQuery(name = "AreaAfectadaNormatividad.findByActividad", query = "SELECT a FROM AreaAfectadaNormatividad a WHERE a.actividad = :actividad"),
    @NamedQuery(name = "AreaAfectadaNormatividad.findByFechaRegistro", query = "SELECT a FROM AreaAfectadaNormatividad a WHERE a.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "AreaAfectadaNormatividad.findByIdUsuario", query = "SELECT a FROM AreaAfectadaNormatividad a WHERE a.idUsuario = :idUsuario"),
    @NamedQuery(name = "AreaAfectadaNormatividad.findByIdRazonSocial", query = "SELECT a FROM AreaAfectadaNormatividad a WHERE a.idRazonSocial = :idRazonSocial")})
public class AreaAfectadaNormatividad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "impacto")
    private String impacto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "porcentaje_cumplimiento")
    private Double porcentajeCumplimiento;
    @Column(name = "actividad")
    private String actividad;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @JoinColumn(name = "id_seguimiento", referencedColumnName = "id")
    @ManyToOne
    private SeguimientoNormatividad idSeguimiento;
    @JoinColumn(name = "id_area", referencedColumnName = "id")
    @ManyToOne
    private AreaAfectada idArea;

    public AreaAfectadaNormatividad() {
    }

    public AreaAfectadaNormatividad(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImpacto() {
        return impacto;
    }

    public void setImpacto(String impacto) {
        this.impacto = impacto;
    }

    public Double getPorcentajeCumplimiento() {
        return porcentajeCumplimiento;
    }

    public void setPorcentajeCumplimiento(Double porcentajeCumplimiento) {
        this.porcentajeCumplimiento = porcentajeCumplimiento;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
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

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    public SeguimientoNormatividad getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(SeguimientoNormatividad idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    public AreaAfectada getIdArea() {
        return idArea;
    }

    public void setIdArea(AreaAfectada idArea) {
        this.idArea = idArea;
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
        if (!(object instanceof AreaAfectadaNormatividad)) {
            return false;
        }
        AreaAfectadaNormatividad other = (AreaAfectadaNormatividad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.AreaAfectadaNormatividad[ id=" + id + " ]";
    }
    
}
