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
@Table(name = "parametro_facturacion_tipo_proyecto", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametroFacturacionTipoProyecto.findAll", query = "SELECT p FROM ParametroFacturacionTipoProyecto p"),
    @NamedQuery(name = "ParametroFacturacionTipoProyecto.findById", query = "SELECT p FROM ParametroFacturacionTipoProyecto p WHERE p.id = :id"),
    @NamedQuery(name = "ParametroFacturacionTipoProyecto.findByIdTipoProyecto", query = "SELECT p FROM ParametroFacturacionTipoProyecto p WHERE p.idTipoProyecto = :idTipoProyecto"),
    @NamedQuery(name = "ParametroFacturacionTipoProyecto.findByEstado", query = "SELECT p FROM ParametroFacturacionTipoProyecto p WHERE p.estado = :estado"),
    @NamedQuery(name = "ParametroFacturacionTipoProyecto.findByFechaRegistro", query = "SELECT p FROM ParametroFacturacionTipoProyecto p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ParametroFacturacionTipoProyecto.findByIdUsuario", query = "SELECT p FROM ParametroFacturacionTipoProyecto p WHERE p.idUsuario = :idUsuario")})
public class ParametroFacturacionTipoProyecto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_tipo_proyecto")
    private Integer idTipoProyecto;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_parametro_facturacion", referencedColumnName = "id")
    @ManyToOne
    private ParametroContableFacturacion idParametroFacturacion;

    public ParametroFacturacionTipoProyecto() {
    }

    public ParametroFacturacionTipoProyecto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdTipoProyecto() {
        return idTipoProyecto;
    }

    public void setIdTipoProyecto(Integer idTipoProyecto) {
        this.idTipoProyecto = idTipoProyecto;
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

    public ParametroContableFacturacion getIdParametroFacturacion() {
        return idParametroFacturacion;
    }

    public void setIdParametroFacturacion(ParametroContableFacturacion idParametroFacturacion) {
        this.idParametroFacturacion = idParametroFacturacion;
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
        if (!(object instanceof ParametroFacturacionTipoProyecto)) {
            return false;
        }
        ParametroFacturacionTipoProyecto other = (ParametroFacturacionTipoProyecto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ParametroFacturacionTipoProyecto[ id=" + id + " ]";
    }
    
}
