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
@Table(name = "personal_requerido_convocatoria", catalog = "bdsae", schema = "licitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonalRequeridoConvocatoria.findAll", query = "SELECT p FROM PersonalRequeridoConvocatoria p"),
    @NamedQuery(name = "PersonalRequeridoConvocatoria.findById", query = "SELECT p FROM PersonalRequeridoConvocatoria p WHERE p.id = :id"),
    @NamedQuery(name = "PersonalRequeridoConvocatoria.findByNombreCargo", query = "SELECT p FROM PersonalRequeridoConvocatoria p WHERE p.nombreCargo = :nombreCargo"),
    @NamedQuery(name = "PersonalRequeridoConvocatoria.findByCantidad", query = "SELECT p FROM PersonalRequeridoConvocatoria p WHERE p.cantidad = :cantidad"),
    @NamedQuery(name = "PersonalRequeridoConvocatoria.findByEstudio", query = "SELECT p FROM PersonalRequeridoConvocatoria p WHERE p.estudio = :estudio"),
    @NamedQuery(name = "PersonalRequeridoConvocatoria.findByExperienciaGeneral", query = "SELECT p FROM PersonalRequeridoConvocatoria p WHERE p.experienciaGeneral = :experienciaGeneral"),
    @NamedQuery(name = "PersonalRequeridoConvocatoria.findByExperienciaEspecifica", query = "SELECT p FROM PersonalRequeridoConvocatoria p WHERE p.experienciaEspecifica = :experienciaEspecifica"),
    @NamedQuery(name = "PersonalRequeridoConvocatoria.findByProfesion", query = "SELECT p FROM PersonalRequeridoConvocatoria p WHERE p.profesion = :profesion"),
    @NamedQuery(name = "PersonalRequeridoConvocatoria.findByCertificacion", query = "SELECT p FROM PersonalRequeridoConvocatoria p WHERE p.certificacion = :certificacion"),
    @NamedQuery(name = "PersonalRequeridoConvocatoria.findByObservacion", query = "SELECT p FROM PersonalRequeridoConvocatoria p WHERE p.observacion = :observacion"),
    @NamedQuery(name = "PersonalRequeridoConvocatoria.findByEstado", query = "SELECT p FROM PersonalRequeridoConvocatoria p WHERE p.estado = :estado"),
    @NamedQuery(name = "PersonalRequeridoConvocatoria.findByFechaRegistro", query = "SELECT p FROM PersonalRequeridoConvocatoria p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "PersonalRequeridoConvocatoria.findByIdUsuario", query = "SELECT p FROM PersonalRequeridoConvocatoria p WHERE p.idUsuario = :idUsuario")})
public class PersonalRequeridoConvocatoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre_cargo")
    private String nombreCargo;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "estudio")
    private String estudio;
    @Column(name = "experiencia_general")
    private String experienciaGeneral;
    @Column(name = "experiencia_especifica")
    private String experienciaEspecifica;
    @Column(name = "profesion")
    private String profesion;
    @Column(name = "certificacion")
    private String certificacion;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_convocatoria", referencedColumnName = "id")
    @ManyToOne
    private Convocatoria idConvocatoria;

    public PersonalRequeridoConvocatoria() {
    }

    public PersonalRequeridoConvocatoria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public String getExperienciaGeneral() {
        return experienciaGeneral;
    }

    public void setExperienciaGeneral(String experienciaGeneral) {
        this.experienciaGeneral = experienciaGeneral;
    }

    public String getExperienciaEspecifica() {
        return experienciaEspecifica;
    }

    public void setExperienciaEspecifica(String experienciaEspecifica) {
        this.experienciaEspecifica = experienciaEspecifica;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getCertificacion() {
        return certificacion;
    }

    public void setCertificacion(String certificacion) {
        this.certificacion = certificacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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
        if (!(object instanceof PersonalRequeridoConvocatoria)) {
            return false;
        }
        PersonalRequeridoConvocatoria other = (PersonalRequeridoConvocatoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.PersonalRequeridoConvocatoria[ id=" + id + " ]";
    }
    
}
