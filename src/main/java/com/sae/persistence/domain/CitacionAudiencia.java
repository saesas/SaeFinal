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
@Table(name = "citacion_audiencia", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CitacionAudiencia.findAll", query = "SELECT c FROM CitacionAudiencia c"),
    @NamedQuery(name = "CitacionAudiencia.findById", query = "SELECT c FROM CitacionAudiencia c WHERE c.id = :id"),
    @NamedQuery(name = "CitacionAudiencia.findByIdInstitucionCompetente", query = "SELECT c FROM CitacionAudiencia c WHERE c.idInstitucionCompetente = :idInstitucionCompetente"),
    @NamedQuery(name = "CitacionAudiencia.findByFechaCitacion", query = "SELECT c FROM CitacionAudiencia c WHERE c.fechaCitacion = :fechaCitacion"),
    @NamedQuery(name = "CitacionAudiencia.findByFechaAudiencia", query = "SELECT c FROM CitacionAudiencia c WHERE c.fechaAudiencia = :fechaAudiencia"),
    @NamedQuery(name = "CitacionAudiencia.findByMotivo", query = "SELECT c FROM CitacionAudiencia c WHERE c.motivo = :motivo"),
    @NamedQuery(name = "CitacionAudiencia.findByEstado", query = "SELECT c FROM CitacionAudiencia c WHERE c.estado = :estado"),
    @NamedQuery(name = "CitacionAudiencia.findByFechaRegistro", query = "SELECT c FROM CitacionAudiencia c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "CitacionAudiencia.findByIdUsuario", query = "SELECT c FROM CitacionAudiencia c WHERE c.idUsuario = :idUsuario"),
    @NamedQuery(name = "CitacionAudiencia.findByIdRazonSocial", query = "SELECT c FROM CitacionAudiencia c WHERE c.idRazonSocial = :idRazonSocial")})
public class CitacionAudiencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_institucion_competente")
    private Integer idInstitucionCompetente;
    @Column(name = "fecha_citacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCitacion;
    @Column(name = "fecha_audiencia")
    @Temporal(TemporalType.DATE)
    private Date fechaAudiencia;
    @Column(name = "motivo")
    private String motivo;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id")
    @ManyToOne
    private TipoCitacion idTipo;
    @OneToMany(mappedBy = "idCitacion")
    private List<Audiencia> audienciaList;
    @OneToMany(mappedBy = "idCitacion")
    private List<FiguraCitacionAudiencia> figuraCitacionAudienciaList;

    public CitacionAudiencia() {
    }

    public CitacionAudiencia(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdInstitucionCompetente() {
        return idInstitucionCompetente;
    }

    public void setIdInstitucionCompetente(Integer idInstitucionCompetente) {
        this.idInstitucionCompetente = idInstitucionCompetente;
    }

    public Date getFechaCitacion() {
        return fechaCitacion;
    }

    public void setFechaCitacion(Date fechaCitacion) {
        this.fechaCitacion = fechaCitacion;
    }

    public Date getFechaAudiencia() {
        return fechaAudiencia;
    }

    public void setFechaAudiencia(Date fechaAudiencia) {
        this.fechaAudiencia = fechaAudiencia;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
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

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    public TipoCitacion getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(TipoCitacion idTipo) {
        this.idTipo = idTipo;
    }

    @XmlTransient
    public List<Audiencia> getAudienciaList() {
        return audienciaList;
    }

    public void setAudienciaList(List<Audiencia> audienciaList) {
        this.audienciaList = audienciaList;
    }

    @XmlTransient
    public List<FiguraCitacionAudiencia> getFiguraCitacionAudienciaList() {
        return figuraCitacionAudienciaList;
    }

    public void setFiguraCitacionAudienciaList(List<FiguraCitacionAudiencia> figuraCitacionAudienciaList) {
        this.figuraCitacionAudienciaList = figuraCitacionAudienciaList;
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
        if (!(object instanceof CitacionAudiencia)) {
            return false;
        }
        CitacionAudiencia other = (CitacionAudiencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.CitacionAudiencia[ id=" + id + " ]";
    }
    
}
