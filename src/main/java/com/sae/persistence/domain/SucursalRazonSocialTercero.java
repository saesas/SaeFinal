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
@Table(name = "sucursal_razon_social_tercero", catalog = "bdsae", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SucursalRazonSocialTercero.findAll", query = "SELECT s FROM SucursalRazonSocialTercero s"),
    @NamedQuery(name = "SucursalRazonSocialTercero.findById", query = "SELECT s FROM SucursalRazonSocialTercero s WHERE s.id = :id"),
    @NamedQuery(name = "SucursalRazonSocialTercero.findByNombre", query = "SELECT s FROM SucursalRazonSocialTercero s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "SucursalRazonSocialTercero.findByDireccion", query = "SELECT s FROM SucursalRazonSocialTercero s WHERE s.direccion = :direccion"),
    @NamedQuery(name = "SucursalRazonSocialTercero.findByTelefono", query = "SELECT s FROM SucursalRazonSocialTercero s WHERE s.telefono = :telefono"),
    @NamedQuery(name = "SucursalRazonSocialTercero.findByEstado", query = "SELECT s FROM SucursalRazonSocialTercero s WHERE s.estado = :estado"),
    @NamedQuery(name = "SucursalRazonSocialTercero.findByFechaRegistro", query = "SELECT s FROM SucursalRazonSocialTercero s WHERE s.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "SucursalRazonSocialTercero.findByIdUsuario", query = "SELECT s FROM SucursalRazonSocialTercero s WHERE s.idUsuario = :idUsuario"),
    @NamedQuery(name = "SucursalRazonSocialTercero.findByEstrato", query = "SELECT s FROM SucursalRazonSocialTercero s WHERE s.estrato = :estrato")})
public class SucursalRazonSocialTercero implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private Integer telefono;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "estrato")
    private Integer estrato;
    @JoinColumn(name = "id_tipo_sucursal", referencedColumnName = "id")
    @ManyToOne
    private TipoSucursal idTipoSucursal;
    @JoinColumn(name = "id_tercero", referencedColumnName = "id")
    @ManyToOne
    private Tercero idTercero;
    @JoinColumn(name = "id_razon_social", referencedColumnName = "id")
    @ManyToOne
    private RazonSocial idRazonSocial;
    @JoinColumn(name = "id_municipio", referencedColumnName = "id")
    @ManyToOne
    private Municipio idMunicipio;
    @OneToMany(mappedBy = "idSucursal")
    private List<ContactoTercero> contactoTerceroList;

    public SucursalRazonSocialTercero() {
    }

    public SucursalRazonSocialTercero(Integer id) {
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
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

    public Integer getEstrato() {
        return estrato;
    }

    public void setEstrato(Integer estrato) {
        this.estrato = estrato;
    }

    public TipoSucursal getIdTipoSucursal() {
        return idTipoSucursal;
    }

    public void setIdTipoSucursal(TipoSucursal idTipoSucursal) {
        this.idTipoSucursal = idTipoSucursal;
    }

    public Tercero getIdTercero() {
        return idTercero;
    }

    public void setIdTercero(Tercero idTercero) {
        this.idTercero = idTercero;
    }

    public RazonSocial getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(RazonSocial idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    public Municipio getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Municipio idMunicipio) {
        this.idMunicipio = idMunicipio;
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
        if (!(object instanceof SucursalRazonSocialTercero)) {
            return false;
        }
        SucursalRazonSocialTercero other = (SucursalRazonSocialTercero) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.SucursalRazonSocialTercero[ id=" + id + " ]";
    }
    
}
