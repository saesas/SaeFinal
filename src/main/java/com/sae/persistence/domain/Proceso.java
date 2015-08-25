/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author SAE2
 */
@Entity
@Table(name = "proceso", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proceso.findAll", query = "SELECT p FROM Proceso p"),
    @NamedQuery(name = "Proceso.findById", query = "SELECT p FROM Proceso p WHERE p.id = :id"),
    @NamedQuery(name = "Proceso.findByIdMunicipio", query = "SELECT p FROM Proceso p WHERE p.idMunicipio = :idMunicipio"),
    @NamedQuery(name = "Proceso.findByIdInstitucion", query = "SELECT p FROM Proceso p WHERE p.idInstitucion = :idInstitucion"),
    @NamedQuery(name = "Proceso.findByRadicado", query = "SELECT p FROM Proceso p WHERE p.radicado = :radicado"),
    @NamedQuery(name = "Proceso.findByFechaRadicacion", query = "SELECT p FROM Proceso p WHERE p.fechaRadicacion = :fechaRadicacion"),
    @NamedQuery(name = "Proceso.findBySituacion", query = "SELECT p FROM Proceso p WHERE p.situacion = :situacion"),
    @NamedQuery(name = "Proceso.findByIdProyecto", query = "SELECT p FROM Proceso p WHERE p.idProyecto = :idProyecto"),
    @NamedQuery(name = "Proceso.findByPretension", query = "SELECT p FROM Proceso p WHERE p.pretension = :pretension"),
    @NamedQuery(name = "Proceso.findByIdResponsable", query = "SELECT p FROM Proceso p WHERE p.idResponsable = :idResponsable"),
    @NamedQuery(name = "Proceso.findByAnotacion", query = "SELECT p FROM Proceso p WHERE p.anotacion = :anotacion"),
    @NamedQuery(name = "Proceso.findByObservacion", query = "SELECT p FROM Proceso p WHERE p.observacion = :observacion"),
    @NamedQuery(name = "Proceso.findByFechaRegistro", query = "SELECT p FROM Proceso p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Proceso.findByIdUsuario", query = "SELECT p FROM Proceso p WHERE p.idUsuario = :idUsuario"),
    @NamedQuery(name = "Proceso.findByIdRazonSocial", query = "SELECT p FROM Proceso p WHERE p.idRazonSocial = :idRazonSocial")})
public class Proceso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_municipio")
    private Integer idMunicipio;
    @Column(name = "id_institucion")
    private Integer idInstitucion;
    @Column(name = "radicado")
    private String radicado;
    @Column(name = "fecha_radicacion")
    @Temporal(TemporalType.DATE)
    private Date fechaRadicacion;
    @Column(name = "situacion")
    private String situacion;
    @Column(name = "id_proyecto")
    private Integer idProyecto;
    @Column(name = "pretension")
    private String pretension;
    @Column(name = "id_responsable")
    private Integer idResponsable;
    @Column(name = "anotacion")
    private String anotacion;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @OneToMany(mappedBy = "idProceso")
    private List<SeguimientoProceso> seguimientoProcesoList;
    @JoinColumn(name = "id_estado", referencedColumnName = "id")
    @ManyToOne
    private TipoEstadoProceso idEstado;
    @JoinColumn(name = "id_tema", referencedColumnName = "id")
    @ManyToOne
    private Tema idTema;
    @JoinColumn(name = "id_clase", referencedColumnName = "id")
    @ManyToOne
    private Clase idClase;
    @OneToMany(mappedBy = "idProceso")
    private List<FiguraProceso> figuraProcesoList;

    public Proceso() {
    }

    public Proceso(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public Integer getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(Integer idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public String getRadicado() {
        return radicado;
    }

    public void setRadicado(String radicado) {
        this.radicado = radicado;
    }

    public Date getFechaRadicacion() {
        return fechaRadicacion;
    }

    public void setFechaRadicacion(Date fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
    }

    public String getSituacion() {
        return situacion;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getPretension() {
        return pretension;
    }

    public void setPretension(String pretension) {
        this.pretension = pretension;
    }

    public Integer getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(Integer idResponsable) {
        this.idResponsable = idResponsable;
    }

    public String getAnotacion() {
        return anotacion;
    }

    public void setAnotacion(String anotacion) {
        this.anotacion = anotacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    @XmlTransient
    public List<SeguimientoProceso> getSeguimientoProcesoList() {
        return seguimientoProcesoList;
    }

    public void setSeguimientoProcesoList(List<SeguimientoProceso> seguimientoProcesoList) {
        this.seguimientoProcesoList = seguimientoProcesoList;
    }

    public TipoEstadoProceso getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(TipoEstadoProceso idEstado) {
        this.idEstado = idEstado;
    }

    public Tema getIdTema() {
        return idTema;
    }

    public void setIdTema(Tema idTema) {
        this.idTema = idTema;
    }

    public Clase getIdClase() {
        return idClase;
    }

    public void setIdClase(Clase idClase) {
        this.idClase = idClase;
    }

    @XmlTransient
    public List<FiguraProceso> getFiguraProcesoList() {
        return figuraProcesoList;
    }

    public void setFiguraProcesoList(List<FiguraProceso> figuraProcesoList) {
        this.figuraProcesoList = figuraProcesoList;
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
        if (!(object instanceof Proceso)) {
            return false;
        }
        Proceso other = (Proceso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Proceso[ id=" + id + " ]";
    }
    
}
