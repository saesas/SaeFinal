/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author SAE2
 */
@Entity
@Table(name = "tipo_sucursal", catalog = "bdsae", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoSucursal.findAll", query = "SELECT t FROM TipoSucursal t"),
    @NamedQuery(name = "TipoSucursal.findById", query = "SELECT t FROM TipoSucursal t WHERE t.id = :id"),
    @NamedQuery(name = "TipoSucursal.findByNombre", query = "SELECT t FROM TipoSucursal t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoSucursal.findByEstado", query = "SELECT t FROM TipoSucursal t WHERE t.estado = :estado")})
public class TipoSucursal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "estado")
    private Boolean estado;
    @OneToMany(mappedBy = "idTipoSucursal")
    private List<SucursalRazonSocialTercero> sucursalRazonSocialTerceroList;

    public TipoSucursal() {
    }

    public TipoSucursal(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<SucursalRazonSocialTercero> getSucursalRazonSocialTerceroList() {
        return sucursalRazonSocialTerceroList;
    }

    public void setSucursalRazonSocialTerceroList(List<SucursalRazonSocialTercero> sucursalRazonSocialTerceroList) {
        this.sucursalRazonSocialTerceroList = sucursalRazonSocialTerceroList;
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
        if (!(object instanceof TipoSucursal)) {
            return false;
        }
        TipoSucursal other = (TipoSucursal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.TipoSucursal[ id=" + id + " ]";
    }
    
}
