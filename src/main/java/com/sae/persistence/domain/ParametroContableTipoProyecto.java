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
@Table(name = "parametro_contable_tipo_proyecto", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametroContableTipoProyecto.findAll", query = "SELECT p FROM ParametroContableTipoProyecto p"),
    @NamedQuery(name = "ParametroContableTipoProyecto.findById", query = "SELECT p FROM ParametroContableTipoProyecto p WHERE p.id = :id"),
    @NamedQuery(name = "ParametroContableTipoProyecto.findByIdTipoProyecto", query = "SELECT p FROM ParametroContableTipoProyecto p WHERE p.idTipoProyecto = :idTipoProyecto"),
    @NamedQuery(name = "ParametroContableTipoProyecto.findByEstado", query = "SELECT p FROM ParametroContableTipoProyecto p WHERE p.estado = :estado"),
    @NamedQuery(name = "ParametroContableTipoProyecto.findByFechaRegistro", query = "SELECT p FROM ParametroContableTipoProyecto p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ParametroContableTipoProyecto.findByIdUsuario", query = "SELECT p FROM ParametroContableTipoProyecto p WHERE p.idUsuario = :idUsuario")})
public class ParametroContableTipoProyecto implements Serializable {
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
    @JoinColumn(name = "id_parametro", referencedColumnName = "id")
    @ManyToOne
    private ParametroContableProyecto idParametro;

    public ParametroContableTipoProyecto() {
    }

    public ParametroContableTipoProyecto(Integer id) {
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

    public ParametroContableProyecto getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(ParametroContableProyecto idParametro) {
        this.idParametro = idParametro;
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
        if (!(object instanceof ParametroContableTipoProyecto)) {
            return false;
        }
        ParametroContableTipoProyecto other = (ParametroContableTipoProyecto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ParametroContableTipoProyecto[ id=" + id + " ]";
    }
    
}
