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
@Table(name = "inasistencia_contrato", catalog = "bdsae", schema = "talento_humano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InasistenciaContrato.findAll", query = "SELECT i FROM InasistenciaContrato i"),
    @NamedQuery(name = "InasistenciaContrato.findById", query = "SELECT i FROM InasistenciaContrato i WHERE i.id = :id"),
    @NamedQuery(name = "InasistenciaContrato.findByEstado", query = "SELECT i FROM InasistenciaContrato i WHERE i.estado = :estado"),
    @NamedQuery(name = "InasistenciaContrato.findByFechaInasistencia", query = "SELECT i FROM InasistenciaContrato i WHERE i.fechaInasistencia = :fechaInasistencia"),
    @NamedQuery(name = "InasistenciaContrato.findByJustificacion", query = "SELECT i FROM InasistenciaContrato i WHERE i.justificacion = :justificacion"),
    @NamedQuery(name = "InasistenciaContrato.findBySoporteInasistencia", query = "SELECT i FROM InasistenciaContrato i WHERE i.soporteInasistencia = :soporteInasistencia"),
    @NamedQuery(name = "InasistenciaContrato.findByFechaRegistro", query = "SELECT i FROM InasistenciaContrato i WHERE i.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "InasistenciaContrato.findByIdUsuario", query = "SELECT i FROM InasistenciaContrato i WHERE i.idUsuario = :idUsuario")})
public class InasistenciaContrato implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_inasistencia")
    @Temporal(TemporalType.DATE)
    private Date fechaInasistencia;
    @Column(name = "justificacion")
    private String justificacion;
    @Column(name = "soporte_inasistencia")
    private String soporteInasistencia;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_motivo_inasistencia", referencedColumnName = "id")
    @ManyToOne
    private MotivoInasistencia idMotivoInasistencia;
    @JoinColumn(name = "id_contrato", referencedColumnName = "id")
    @ManyToOne
    private ContratoTercero idContrato;

    public InasistenciaContrato() {
    }

    public InasistenciaContrato(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Date getFechaInasistencia() {
        return fechaInasistencia;
    }

    public void setFechaInasistencia(Date fechaInasistencia) {
        this.fechaInasistencia = fechaInasistencia;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public String getSoporteInasistencia() {
        return soporteInasistencia;
    }

    public void setSoporteInasistencia(String soporteInasistencia) {
        this.soporteInasistencia = soporteInasistencia;
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

    public MotivoInasistencia getIdMotivoInasistencia() {
        return idMotivoInasistencia;
    }

    public void setIdMotivoInasistencia(MotivoInasistencia idMotivoInasistencia) {
        this.idMotivoInasistencia = idMotivoInasistencia;
    }

    public ContratoTercero getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(ContratoTercero idContrato) {
        this.idContrato = idContrato;
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
        if (!(object instanceof InasistenciaContrato)) {
            return false;
        }
        InasistenciaContrato other = (InasistenciaContrato) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.InasistenciaContrato[ id=" + id + " ]";
    }
    
}
