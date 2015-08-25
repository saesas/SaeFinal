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
@Table(name = "estado_nomina", catalog = "bdsae", schema = "talento_humano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoNomina.findAll", query = "SELECT e FROM EstadoNomina e"),
    @NamedQuery(name = "EstadoNomina.findById", query = "SELECT e FROM EstadoNomina e WHERE e.id = :id"),
    @NamedQuery(name = "EstadoNomina.findByEstado", query = "SELECT e FROM EstadoNomina e WHERE e.estado = :estado"),
    @NamedQuery(name = "EstadoNomina.findByIdUsuario", query = "SELECT e FROM EstadoNomina e WHERE e.idUsuario = :idUsuario"),
    @NamedQuery(name = "EstadoNomina.findByFechaRegistro", query = "SELECT e FROM EstadoNomina e WHERE e.fechaRegistro = :fechaRegistro")})
public class EstadoNomina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "id_estado_nomina", referencedColumnName = "id")
    @ManyToOne
    private TipoEstadoNomina idEstadoNomina;
    @JoinColumn(name = "id_nomina", referencedColumnName = "id")
    @ManyToOne
    private Nomina idNomina;

    public EstadoNomina() {
    }

    public EstadoNomina(Integer id) {
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

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public TipoEstadoNomina getIdEstadoNomina() {
        return idEstadoNomina;
    }

    public void setIdEstadoNomina(TipoEstadoNomina idEstadoNomina) {
        this.idEstadoNomina = idEstadoNomina;
    }

    public Nomina getIdNomina() {
        return idNomina;
    }

    public void setIdNomina(Nomina idNomina) {
        this.idNomina = idNomina;
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
        if (!(object instanceof EstadoNomina)) {
            return false;
        }
        EstadoNomina other = (EstadoNomina) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.EstadoNomina[ id=" + id + " ]";
    }
    
}
