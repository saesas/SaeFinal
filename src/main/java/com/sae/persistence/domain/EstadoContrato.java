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
@Table(name = "estado_contrato", catalog = "bdsae", schema = "tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoContrato.findAll", query = "SELECT e FROM EstadoContrato e"),
    @NamedQuery(name = "EstadoContrato.findById", query = "SELECT e FROM EstadoContrato e WHERE e.id = :id"),
    @NamedQuery(name = "EstadoContrato.findByFechaRegistro", query = "SELECT e FROM EstadoContrato e WHERE e.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "EstadoContrato.findByIdUsuario", query = "SELECT e FROM EstadoContrato e WHERE e.idUsuario = :idUsuario"),
    @NamedQuery(name = "EstadoContrato.findByEstado", query = "SELECT e FROM EstadoContrato e WHERE e.estado = :estado")})
public class EstadoContrato implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "estado")
    private Boolean estado;
    @JoinColumn(name = "id_estado", referencedColumnName = "id")
    @ManyToOne
    private TipoEstadoContrato idEstado;
    @JoinColumn(name = "id_contrato", referencedColumnName = "id")
    @ManyToOne
    private ContratoProyectoProveedor idContrato;

    public EstadoContrato() {
    }

    public EstadoContrato(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public TipoEstadoContrato getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(TipoEstadoContrato idEstado) {
        this.idEstado = idEstado;
    }

    public ContratoProyectoProveedor getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(ContratoProyectoProveedor idContrato) {
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
        if (!(object instanceof EstadoContrato)) {
            return false;
        }
        EstadoContrato other = (EstadoContrato) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.EstadoContrato[ id=" + id + " ]";
    }
    
}
