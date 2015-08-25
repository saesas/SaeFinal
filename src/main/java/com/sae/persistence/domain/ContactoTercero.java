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
@Table(name = "contacto_tercero", catalog = "bdsae", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContactoTercero.findAll", query = "SELECT c FROM ContactoTercero c"),
    @NamedQuery(name = "ContactoTercero.findById", query = "SELECT c FROM ContactoTercero c WHERE c.id = :id"),
    @NamedQuery(name = "ContactoTercero.findByNombre", query = "SELECT c FROM ContactoTercero c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ContactoTercero.findByTelefono", query = "SELECT c FROM ContactoTercero c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "ContactoTercero.findByCorreo", query = "SELECT c FROM ContactoTercero c WHERE c.correo = :correo"),
    @NamedQuery(name = "ContactoTercero.findByEstado", query = "SELECT c FROM ContactoTercero c WHERE c.estado = :estado"),
    @NamedQuery(name = "ContactoTercero.findByFechaRegistro", query = "SELECT c FROM ContactoTercero c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ContactoTercero.findByIdUsuario", query = "SELECT c FROM ContactoTercero c WHERE c.idUsuario = :idUsuario")})
public class ContactoTercero implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "telefono")
    private Integer telefono;
    @Column(name = "correo")
    private String correo;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_sucursal", referencedColumnName = "id")
    @ManyToOne
    private SucursalRazonSocialTercero idSucursal;
    @JoinColumn(name = "id_dependencia", referencedColumnName = "id")
    @ManyToOne
    private DependenciaSucursal idDependencia;

    public ContactoTercero() {
    }

    public ContactoTercero(Integer id) {
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

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    public SucursalRazonSocialTercero getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(SucursalRazonSocialTercero idSucursal) {
        this.idSucursal = idSucursal;
    }

    public DependenciaSucursal getIdDependencia() {
        return idDependencia;
    }

    public void setIdDependencia(DependenciaSucursal idDependencia) {
        this.idDependencia = idDependencia;
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
        if (!(object instanceof ContactoTercero)) {
            return false;
        }
        ContactoTercero other = (ContactoTercero) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ContactoTercero[ id=" + id + " ]";
    }
    
}
