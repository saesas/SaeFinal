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
@Table(name = "normatividad", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Normatividad.findAll", query = "SELECT n FROM Normatividad n"),
    @NamedQuery(name = "Normatividad.findById", query = "SELECT n FROM Normatividad n WHERE n.id = :id"),
    @NamedQuery(name = "Normatividad.findByReferencia", query = "SELECT n FROM Normatividad n WHERE n.referencia = :referencia"),
    @NamedQuery(name = "Normatividad.findByDescripcion", query = "SELECT n FROM Normatividad n WHERE n.descripcion = :descripcion"),
    @NamedQuery(name = "Normatividad.findByExigencia", query = "SELECT n FROM Normatividad n WHERE n.exigencia = :exigencia"),
    @NamedQuery(name = "Normatividad.findByIdEmisor", query = "SELECT n FROM Normatividad n WHERE n.idEmisor = :idEmisor"),
    @NamedQuery(name = "Normatividad.findByObservaciones", query = "SELECT n FROM Normatividad n WHERE n.observaciones = :observaciones"),
    @NamedQuery(name = "Normatividad.findByEvidencias", query = "SELECT n FROM Normatividad n WHERE n.evidencias = :evidencias"),
    @NamedQuery(name = "Normatividad.findByFechaExpedicion", query = "SELECT n FROM Normatividad n WHERE n.fechaExpedicion = :fechaExpedicion"),
    @NamedQuery(name = "Normatividad.findByFechaInicio", query = "SELECT n FROM Normatividad n WHERE n.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Normatividad.findByFechaDerogacion", query = "SELECT n FROM Normatividad n WHERE n.fechaDerogacion = :fechaDerogacion"),
    @NamedQuery(name = "Normatividad.findByFechaRegistro", query = "SELECT n FROM Normatividad n WHERE n.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Normatividad.findByIdUsuario", query = "SELECT n FROM Normatividad n WHERE n.idUsuario = :idUsuario")})
public class Normatividad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "referencia")
    private String referencia;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "exigencia")
    private String exigencia;
    @Column(name = "id_emisor")
    private Integer idEmisor;
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "evidencias")
    private String evidencias;
    @Column(name = "fecha_expedicion")
    @Temporal(TemporalType.DATE)
    private Date fechaExpedicion;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_derogacion")
    @Temporal(TemporalType.DATE)
    private Date fechaDerogacion;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_estado", referencedColumnName = "id")
    @ManyToOne
    private TipoEstadoNormatividad idEstado;
    @JoinColumn(name = "id_tema", referencedColumnName = "id")
    @ManyToOne
    private Tema idTema;
    @JoinColumn(name = "id_clase_normatividad", referencedColumnName = "id")
    @ManyToOne
    private Clase idClaseNormatividad;
    @JoinColumn(name = "id_area_derecho", referencedColumnName = "id")
    @ManyToOne
    private Clase idAreaDerecho;
    @OneToMany(mappedBy = "idNormatividad")
    private List<SeguimientoNormatividad> seguimientoNormatividadList;

    public Normatividad() {
    }

    public Normatividad(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getExigencia() {
        return exigencia;
    }

    public void setExigencia(String exigencia) {
        this.exigencia = exigencia;
    }

    public Integer getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(Integer idEmisor) {
        this.idEmisor = idEmisor;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEvidencias() {
        return evidencias;
    }

    public void setEvidencias(String evidencias) {
        this.evidencias = evidencias;
    }

    public Date getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(Date fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaDerogacion() {
        return fechaDerogacion;
    }

    public void setFechaDerogacion(Date fechaDerogacion) {
        this.fechaDerogacion = fechaDerogacion;
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

    public TipoEstadoNormatividad getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(TipoEstadoNormatividad idEstado) {
        this.idEstado = idEstado;
    }

    public Tema getIdTema() {
        return idTema;
    }

    public void setIdTema(Tema idTema) {
        this.idTema = idTema;
    }

    public Clase getIdClaseNormatividad() {
        return idClaseNormatividad;
    }

    public void setIdClaseNormatividad(Clase idClaseNormatividad) {
        this.idClaseNormatividad = idClaseNormatividad;
    }

    public Clase getIdAreaDerecho() {
        return idAreaDerecho;
    }

    public void setIdAreaDerecho(Clase idAreaDerecho) {
        this.idAreaDerecho = idAreaDerecho;
    }

    @XmlTransient
    public List<SeguimientoNormatividad> getSeguimientoNormatividadList() {
        return seguimientoNormatividadList;
    }

    public void setSeguimientoNormatividadList(List<SeguimientoNormatividad> seguimientoNormatividadList) {
        this.seguimientoNormatividadList = seguimientoNormatividadList;
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
        if (!(object instanceof Normatividad)) {
            return false;
        }
        Normatividad other = (Normatividad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Normatividad[ id=" + id + " ]";
    }
    
}
