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
@Table(name = "dependencia_sucursal", catalog = "bdsae", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DependenciaSucursal.findAll", query = "SELECT d FROM DependenciaSucursal d"),
    @NamedQuery(name = "DependenciaSucursal.findById", query = "SELECT d FROM DependenciaSucursal d WHERE d.id = :id"),
    @NamedQuery(name = "DependenciaSucursal.findByNombre", query = "SELECT d FROM DependenciaSucursal d WHERE d.nombre = :nombre"),
    @NamedQuery(name = "DependenciaSucursal.findByEstado", query = "SELECT d FROM DependenciaSucursal d WHERE d.estado = :estado"),
    @NamedQuery(name = "DependenciaSucursal.findByIdUsuario", query = "SELECT d FROM DependenciaSucursal d WHERE d.idUsuario = :idUsuario"),
    @NamedQuery(name = "DependenciaSucursal.findByFechaRegistro", query = "SELECT d FROM DependenciaSucursal d WHERE d.fechaRegistro = :fechaRegistro")})
public class DependenciaSucursal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "idDependencia")
    private List<ContactoTercero> contactoTerceroList;

    public DependenciaSucursal() {
    }

    public DependenciaSucursal(Integer id) {
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

    @XmlTransient
    public List<ContactoTercero> getContactoTerceroList() {
        return contactoTerceroList;
    }

    public void setContactoTerceroList(List<ContactoTercero> contactoTerceroList) {
        this.contactoTerceroList = contactoTerceroList;
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
        if (!(object instanceof DependenciaSucursal)) {
            return false;
        }
        DependenciaSucursal other = (DependenciaSucursal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.DependenciaSucursal[ id=" + id + " ]";
    }
    
}
