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
@Table(name = "tercero_cargo", catalog = "bdsae", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TerceroCargo.findAll", query = "SELECT t FROM TerceroCargo t"),
    @NamedQuery(name = "TerceroCargo.findById", query = "SELECT t FROM TerceroCargo t WHERE t.id = :id"),
    @NamedQuery(name = "TerceroCargo.findByEstado", query = "SELECT t FROM TerceroCargo t WHERE t.estado = :estado"),
    @NamedQuery(name = "TerceroCargo.findByFechaRegistro", query = "SELECT t FROM TerceroCargo t WHERE t.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "TerceroCargo.findByIdUsuario", query = "SELECT t FROM TerceroCargo t WHERE t.idUsuario = :idUsuario")})
public class TerceroCargo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_tercero", referencedColumnName = "id")
    @ManyToOne
    private TerceroRazonSocial idTercero;
    @JoinColumn(name = "id_cargo", referencedColumnName = "id")
    @ManyToOne
    private Cargo idCargo;

    public TerceroCargo() {
    }

    public TerceroCargo(Integer id) {
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

    public TerceroRazonSocial getIdTercero() {
        return idTercero;
    }

    public void setIdTercero(TerceroRazonSocial idTercero) {
        this.idTercero = idTercero;
    }

    public Cargo getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Cargo idCargo) {
        this.idCargo = idCargo;
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
        if (!(object instanceof TerceroCargo)) {
            return false;
        }
        TerceroCargo other = (TerceroCargo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.TerceroCargo[ id=" + id + " ]";
    }
    
}
