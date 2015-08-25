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
@Table(name = "acta_inicio", catalog = "bdsae", schema = "tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActaInicio.findAll", query = "SELECT a FROM ActaInicio a"),
    @NamedQuery(name = "ActaInicio.findById", query = "SELECT a FROM ActaInicio a WHERE a.id = :id"),
    @NamedQuery(name = "ActaInicio.findByCodigo", query = "SELECT a FROM ActaInicio a WHERE a.codigo = :codigo"),
    @NamedQuery(name = "ActaInicio.findByFechaInicioContrato", query = "SELECT a FROM ActaInicio a WHERE a.fechaInicioContrato = :fechaInicioContrato"),
    @NamedQuery(name = "ActaInicio.findByFechaFinContrato", query = "SELECT a FROM ActaInicio a WHERE a.fechaFinContrato = :fechaFinContrato"),
    @NamedQuery(name = "ActaInicio.findByEstado", query = "SELECT a FROM ActaInicio a WHERE a.estado = :estado"),
    @NamedQuery(name = "ActaInicio.findByFechaRegistro", query = "SELECT a FROM ActaInicio a WHERE a.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ActaInicio.findByIdUsuario", query = "SELECT a FROM ActaInicio a WHERE a.idUsuario = :idUsuario")})
public class ActaInicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "fecha_inicio_contrato")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioContrato;
    @Column(name = "fecha_fin_contrato")
    @Temporal(TemporalType.DATE)
    private Date fechaFinContrato;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_contrato", referencedColumnName = "id")
    @ManyToOne
    private ContratoProyectoProveedor idContrato;
    @JoinColumn(name = "adjunto_tecnica", referencedColumnName = "id")
    @ManyToOne
    private AdjuntoTecnica adjuntoTecnica;

    public ActaInicio() {
    }

    public ActaInicio(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFechaInicioContrato() {
        return fechaInicioContrato;
    }

    public void setFechaInicioContrato(Date fechaInicioContrato) {
        this.fechaInicioContrato = fechaInicioContrato;
    }

    public Date getFechaFinContrato() {
        return fechaFinContrato;
    }

    public void setFechaFinContrato(Date fechaFinContrato) {
        this.fechaFinContrato = fechaFinContrato;
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

    public ContratoProyectoProveedor getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(ContratoProyectoProveedor idContrato) {
        this.idContrato = idContrato;
    }

    public AdjuntoTecnica getAdjuntoTecnica() {
        return adjuntoTecnica;
    }

    public void setAdjuntoTecnica(AdjuntoTecnica adjuntoTecnica) {
        this.adjuntoTecnica = adjuntoTecnica;
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
        if (!(object instanceof ActaInicio)) {
            return false;
        }
        ActaInicio other = (ActaInicio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ActaInicio[ id=" + id + " ]";
    }
    
}
