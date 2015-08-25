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
@Table(name = "adjunto_cuenta_cobro", catalog = "bdsae", schema = "compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdjuntoCuentaCobro.findAll", query = "SELECT a FROM AdjuntoCuentaCobro a"),
    @NamedQuery(name = "AdjuntoCuentaCobro.findById", query = "SELECT a FROM AdjuntoCuentaCobro a WHERE a.id = :id"),
    @NamedQuery(name = "AdjuntoCuentaCobro.findByEstado", query = "SELECT a FROM AdjuntoCuentaCobro a WHERE a.estado = :estado"),
    @NamedQuery(name = "AdjuntoCuentaCobro.findByNombre", query = "SELECT a FROM AdjuntoCuentaCobro a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "AdjuntoCuentaCobro.findByIdTipoAdjunto", query = "SELECT a FROM AdjuntoCuentaCobro a WHERE a.idTipoAdjunto = :idTipoAdjunto"),
    @NamedQuery(name = "AdjuntoCuentaCobro.findByIdUsuario", query = "SELECT a FROM AdjuntoCuentaCobro a WHERE a.idUsuario = :idUsuario"),
    @NamedQuery(name = "AdjuntoCuentaCobro.findByFechaRegistro", query = "SELECT a FROM AdjuntoCuentaCobro a WHERE a.fechaRegistro = :fechaRegistro")})
public class AdjuntoCuentaCobro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "id_tipo_adjunto")
    private Integer idTipoAdjunto;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @JoinColumn(name = "id_cuenta_cobro", referencedColumnName = "id")
    @ManyToOne
    private CuentaCobro idCuentaCobro;

    public AdjuntoCuentaCobro() {
    }

    public AdjuntoCuentaCobro(Integer id) {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdTipoAdjunto() {
        return idTipoAdjunto;
    }

    public void setIdTipoAdjunto(Integer idTipoAdjunto) {
        this.idTipoAdjunto = idTipoAdjunto;
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

    public CuentaCobro getIdCuentaCobro() {
        return idCuentaCobro;
    }

    public void setIdCuentaCobro(CuentaCobro idCuentaCobro) {
        this.idCuentaCobro = idCuentaCobro;
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
        if (!(object instanceof AdjuntoCuentaCobro)) {
            return false;
        }
        AdjuntoCuentaCobro other = (AdjuntoCuentaCobro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.AdjuntoCuentaCobro[ id=" + id + " ]";
    }
    
}
