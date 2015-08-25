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
@Table(name = "razon_social", catalog = "bdsae", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RazonSocial.findAll", query = "SELECT r FROM RazonSocial r"),
    @NamedQuery(name = "RazonSocial.findById", query = "SELECT r FROM RazonSocial r WHERE r.id = :id"),
    @NamedQuery(name = "RazonSocial.findByNombre", query = "SELECT r FROM RazonSocial r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "RazonSocial.findByAbreviatura", query = "SELECT r FROM RazonSocial r WHERE r.abreviatura = :abreviatura"),
    @NamedQuery(name = "RazonSocial.findByEstado", query = "SELECT r FROM RazonSocial r WHERE r.estado = :estado"),
    @NamedQuery(name = "RazonSocial.findByFechaRegistro", query = "SELECT r FROM RazonSocial r WHERE r.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "RazonSocial.findByUsuarioRegistro", query = "SELECT r FROM RazonSocial r WHERE r.usuarioRegistro = :usuarioRegistro"),
    @NamedQuery(name = "RazonSocial.findByNit", query = "SELECT r FROM RazonSocial r WHERE r.nit = :nit")})
public class RazonSocial implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "abreviatura")
    private String abreviatura;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "usuario_registro")
    private Integer usuarioRegistro;
    @Column(name = "nit")
    private String nit;
    @JoinColumn(name = "tipo_organizacion", referencedColumnName = "id")
    @ManyToOne
    private TipoOrganizacion tipoOrganizacion;
    @OneToMany(mappedBy = "idRazonSocial")
    private List<SucursalRazonSocialTercero> sucursalRazonSocialTerceroList;
    @OneToMany(mappedBy = "idRazonSocial")
    private List<TerceroRazonSocial> terceroRazonSocialList;
    @OneToMany(mappedBy = "idRazonSocial")
    private List<RepresentateLegalRazonSocial> representateLegalRazonSocialList;

    public RazonSocial() {
    }

    public RazonSocial(Integer id) {
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

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
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

    public Integer getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(Integer usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public TipoOrganizacion getTipoOrganizacion() {
        return tipoOrganizacion;
    }

    public void setTipoOrganizacion(TipoOrganizacion tipoOrganizacion) {
        this.tipoOrganizacion = tipoOrganizacion;
    }

    @XmlTransient
    public List<SucursalRazonSocialTercero> getSucursalRazonSocialTerceroList() {
        return sucursalRazonSocialTerceroList;
    }

    public void setSucursalRazonSocialTerceroList(List<SucursalRazonSocialTercero> sucursalRazonSocialTerceroList) {
        this.sucursalRazonSocialTerceroList = sucursalRazonSocialTerceroList;
    }

    @XmlTransient
    public List<TerceroRazonSocial> getTerceroRazonSocialList() {
        return terceroRazonSocialList;
    }

    public void setTerceroRazonSocialList(List<TerceroRazonSocial> terceroRazonSocialList) {
        this.terceroRazonSocialList = terceroRazonSocialList;
    }

    @XmlTransient
    public List<RepresentateLegalRazonSocial> getRepresentateLegalRazonSocialList() {
        return representateLegalRazonSocialList;
    }

    public void setRepresentateLegalRazonSocialList(List<RepresentateLegalRazonSocial> representateLegalRazonSocialList) {
        this.representateLegalRazonSocialList = representateLegalRazonSocialList;
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
        if (!(object instanceof RazonSocial)) {
            return false;
        }
        RazonSocial other = (RazonSocial) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.RazonSocial[ id=" + id + " ]";
    }
    
}
