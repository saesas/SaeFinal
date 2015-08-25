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
@Table(name = "adjunto_propuesta", catalog = "bdsae", schema = "licitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdjuntoPropuesta.findAll", query = "SELECT a FROM AdjuntoPropuesta a"),
    @NamedQuery(name = "AdjuntoPropuesta.findById", query = "SELECT a FROM AdjuntoPropuesta a WHERE a.id = :id"),
    @NamedQuery(name = "AdjuntoPropuesta.findByNombreAdjunto", query = "SELECT a FROM AdjuntoPropuesta a WHERE a.nombreAdjunto = :nombreAdjunto"),
    @NamedQuery(name = "AdjuntoPropuesta.findByIdTipo", query = "SELECT a FROM AdjuntoPropuesta a WHERE a.idTipo = :idTipo"),
    @NamedQuery(name = "AdjuntoPropuesta.findByObservacion", query = "SELECT a FROM AdjuntoPropuesta a WHERE a.observacion = :observacion"),
    @NamedQuery(name = "AdjuntoPropuesta.findByPuntaje", query = "SELECT a FROM AdjuntoPropuesta a WHERE a.puntaje = :puntaje"),
    @NamedQuery(name = "AdjuntoPropuesta.findBySubsanable", query = "SELECT a FROM AdjuntoPropuesta a WHERE a.subsanable = :subsanable"),
    @NamedQuery(name = "AdjuntoPropuesta.findByEstado", query = "SELECT a FROM AdjuntoPropuesta a WHERE a.estado = :estado"),
    @NamedQuery(name = "AdjuntoPropuesta.findByFechaRegistro", query = "SELECT a FROM AdjuntoPropuesta a WHERE a.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "AdjuntoPropuesta.findByIdUsuario", query = "SELECT a FROM AdjuntoPropuesta a WHERE a.idUsuario = :idUsuario")})
public class AdjuntoPropuesta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre_adjunto")
    private String nombreAdjunto;
    @Column(name = "id_tipo")
    private Integer idTipo;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "puntaje")
    private String puntaje;
    @Column(name = "subsanable")
    private String subsanable;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_propuesta", referencedColumnName = "id")
    @ManyToOne
    private Propuesta idPropuesta;

    public AdjuntoPropuesta() {
    }

    public AdjuntoPropuesta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreAdjunto() {
        return nombreAdjunto;
    }

    public void setNombreAdjunto(String nombreAdjunto) {
        this.nombreAdjunto = nombreAdjunto;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(String puntaje) {
        this.puntaje = puntaje;
    }

    public String getSubsanable() {
        return subsanable;
    }

    public void setSubsanable(String subsanable) {
        this.subsanable = subsanable;
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

    public Propuesta getIdPropuesta() {
        return idPropuesta;
    }

    public void setIdPropuesta(Propuesta idPropuesta) {
        this.idPropuesta = idPropuesta;
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
        if (!(object instanceof AdjuntoPropuesta)) {
            return false;
        }
        AdjuntoPropuesta other = (AdjuntoPropuesta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.AdjuntoPropuesta[ id=" + id + " ]";
    }
    
}
