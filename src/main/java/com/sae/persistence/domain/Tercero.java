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
@Table(name = "tercero", catalog = "bdsae", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tercero.findAll", query = "SELECT t FROM Tercero t"),
    @NamedQuery(name = "Tercero.findById", query = "SELECT t FROM Tercero t WHERE t.id = :id"),
    @NamedQuery(name = "Tercero.findByNombre", query = "SELECT t FROM Tercero t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Tercero.findByApellido", query = "SELECT t FROM Tercero t WHERE t.apellido = :apellido"),
    @NamedQuery(name = "Tercero.findByCorreoElectronico", query = "SELECT t FROM Tercero t WHERE t.correoElectronico = :correoElectronico"),
    @NamedQuery(name = "Tercero.findByFechaNacimiento", query = "SELECT t FROM Tercero t WHERE t.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Tercero.findByEstado", query = "SELECT t FROM Tercero t WHERE t.estado = :estado"),
    @NamedQuery(name = "Tercero.findByFechaRegistro", query = "SELECT t FROM Tercero t WHERE t.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Tercero.findByUsuarioRegistro", query = "SELECT t FROM Tercero t WHERE t.usuarioRegistro = :usuarioRegistro")})
public class Tercero implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "correo_electronico")
    private String correoElectronico;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "usuario_registro")
    private Integer usuarioRegistro;
    @JoinColumn(name = "genero", referencedColumnName = "id")
    @ManyToOne
    private Genero genero;
    @JoinColumn(name = "estado_civil", referencedColumnName = "id")
    @ManyToOne
    private EstadoCivil estadoCivil;
    @OneToMany(mappedBy = "idTercero")
    private List<TipoAsociadoTercero> tipoAsociadoTerceroList;
    @OneToMany(mappedBy = "idTercero")
    private List<SucursalRazonSocialTercero> sucursalRazonSocialTerceroList;
    @OneToMany(mappedBy = "idTercero")
    private List<TerceroRazonSocial> terceroRazonSocialList;
    @OneToMany(mappedBy = "idTercero")
    private List<RepresentateLegalRazonSocial> representateLegalRazonSocialList;
    @OneToMany(mappedBy = "idTercero")
    private List<CuentaBancariaTercero> cuentaBancariaTerceroList;
    @OneToMany(mappedBy = "idNombreBanco")
    private List<CuentaBancariaTercero> cuentaBancariaTerceroList1;
    @OneToMany(mappedBy = "idTercero")
    private List<IdentificacionTercero> identificacionTerceroList;

    public Tercero() {
    }

    public Tercero(Integer id) {
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    @XmlTransient
    public List<TipoAsociadoTercero> getTipoAsociadoTerceroList() {
        return tipoAsociadoTerceroList;
    }

    public void setTipoAsociadoTerceroList(List<TipoAsociadoTercero> tipoAsociadoTerceroList) {
        this.tipoAsociadoTerceroList = tipoAsociadoTerceroList;
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

    @XmlTransient
    public List<CuentaBancariaTercero> getCuentaBancariaTerceroList() {
        return cuentaBancariaTerceroList;
    }

    public void setCuentaBancariaTerceroList(List<CuentaBancariaTercero> cuentaBancariaTerceroList) {
        this.cuentaBancariaTerceroList = cuentaBancariaTerceroList;
    }

    @XmlTransient
    public List<CuentaBancariaTercero> getCuentaBancariaTerceroList1() {
        return cuentaBancariaTerceroList1;
    }

    public void setCuentaBancariaTerceroList1(List<CuentaBancariaTercero> cuentaBancariaTerceroList1) {
        this.cuentaBancariaTerceroList1 = cuentaBancariaTerceroList1;
    }

    @XmlTransient
    public List<IdentificacionTercero> getIdentificacionTerceroList() {
        return identificacionTerceroList;
    }

    public void setIdentificacionTerceroList(List<IdentificacionTercero> identificacionTerceroList) {
        this.identificacionTerceroList = identificacionTerceroList;
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
        if (!(object instanceof Tercero)) {
            return false;
        }
        Tercero other = (Tercero) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Tercero[ id=" + id + " ]";
    }
    
}
