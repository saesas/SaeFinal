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
@Table(name = "referencia_personal", catalog = "bdsae", schema = "talento_humano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReferenciaPersonal.findAll", query = "SELECT r FROM ReferenciaPersonal r"),
    @NamedQuery(name = "ReferenciaPersonal.findById", query = "SELECT r FROM ReferenciaPersonal r WHERE r.id = :id"),
    @NamedQuery(name = "ReferenciaPersonal.findByEstado", query = "SELECT r FROM ReferenciaPersonal r WHERE r.estado = :estado"),
    @NamedQuery(name = "ReferenciaPersonal.findByNombre", query = "SELECT r FROM ReferenciaPersonal r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "ReferenciaPersonal.findByTelefono", query = "SELECT r FROM ReferenciaPersonal r WHERE r.telefono = :telefono"),
    @NamedQuery(name = "ReferenciaPersonal.findByEmail", query = "SELECT r FROM ReferenciaPersonal r WHERE r.email = :email"),
    @NamedQuery(name = "ReferenciaPersonal.findByDireccion", query = "SELECT r FROM ReferenciaPersonal r WHERE r.direccion = :direccion"),
    @NamedQuery(name = "ReferenciaPersonal.findByIdMunicipio", query = "SELECT r FROM ReferenciaPersonal r WHERE r.idMunicipio = :idMunicipio"),
    @NamedQuery(name = "ReferenciaPersonal.findByIdProfesion", query = "SELECT r FROM ReferenciaPersonal r WHERE r.idProfesion = :idProfesion"),
    @NamedQuery(name = "ReferenciaPersonal.findByFechaRegistro", query = "SELECT r FROM ReferenciaPersonal r WHERE r.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ReferenciaPersonal.findByIdUsuario", query = "SELECT r FROM ReferenciaPersonal r WHERE r.idUsuario = :idUsuario")})
public class ReferenciaPersonal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "telefono")
    private Integer telefono;
    @Column(name = "email")
    private String email;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "id_municipio")
    private Integer idMunicipio;
    @Column(name = "id_profesion")
    private Integer idProfesion;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_hoja_vida", referencedColumnName = "id")
    @ManyToOne
    private HojaVida idHojaVida;

    public ReferenciaPersonal() {
    }

    public ReferenciaPersonal(Integer id) {
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

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public Integer getIdProfesion() {
        return idProfesion;
    }

    public void setIdProfesion(Integer idProfesion) {
        this.idProfesion = idProfesion;
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

    public HojaVida getIdHojaVida() {
        return idHojaVida;
    }

    public void setIdHojaVida(HojaVida idHojaVida) {
        this.idHojaVida = idHojaVida;
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
        if (!(object instanceof ReferenciaPersonal)) {
            return false;
        }
        ReferenciaPersonal other = (ReferenciaPersonal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ReferenciaPersonal[ id=" + id + " ]";
    }
    
}
