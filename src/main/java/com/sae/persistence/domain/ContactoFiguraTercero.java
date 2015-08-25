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
@Table(name = "contacto_figura_tercero", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContactoFiguraTercero.findAll", query = "SELECT c FROM ContactoFiguraTercero c"),
    @NamedQuery(name = "ContactoFiguraTercero.findById", query = "SELECT c FROM ContactoFiguraTercero c WHERE c.id = :id"),
    @NamedQuery(name = "ContactoFiguraTercero.findByNombre", query = "SELECT c FROM ContactoFiguraTercero c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ContactoFiguraTercero.findByIdentificacion", query = "SELECT c FROM ContactoFiguraTercero c WHERE c.identificacion = :identificacion"),
    @NamedQuery(name = "ContactoFiguraTercero.findByEmail", query = "SELECT c FROM ContactoFiguraTercero c WHERE c.email = :email"),
    @NamedQuery(name = "ContactoFiguraTercero.findByDireccion", query = "SELECT c FROM ContactoFiguraTercero c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "ContactoFiguraTercero.findByTelefono", query = "SELECT c FROM ContactoFiguraTercero c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "ContactoFiguraTercero.findByEstado", query = "SELECT c FROM ContactoFiguraTercero c WHERE c.estado = :estado"),
    @NamedQuery(name = "ContactoFiguraTercero.findByFechaRegistro", query = "SELECT c FROM ContactoFiguraTercero c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ContactoFiguraTercero.findByIdUsuario", query = "SELECT c FROM ContactoFiguraTercero c WHERE c.idUsuario = :idUsuario")})
public class ContactoFiguraTercero implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "identificacion")
    private Integer identificacion;
    @Column(name = "email")
    private String email;
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
    @JoinColumn(name = "id_figura_poder", referencedColumnName = "id")
    @ManyToOne
    private FiguraPoder idFiguraPoder;

    public ContactoFiguraTercero() {
    }

    public ContactoFiguraTercero(Integer id) {
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

    public Integer getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(Integer identificacion) {
        this.identificacion = identificacion;
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

    public FiguraPoder getIdFiguraPoder() {
        return idFiguraPoder;
    }

    public void setIdFiguraPoder(FiguraPoder idFiguraPoder) {
        this.idFiguraPoder = idFiguraPoder;
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
        if (!(object instanceof ContactoFiguraTercero)) {
            return false;
        }
        ContactoFiguraTercero other = (ContactoFiguraTercero) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ContactoFiguraTercero[ id=" + id + " ]";
    }
    
}
