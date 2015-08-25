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
@Table(name = "unidad_moneda", catalog = "bdsae", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UnidadMoneda.findAll", query = "SELECT u FROM UnidadMoneda u"),
    @NamedQuery(name = "UnidadMoneda.findById", query = "SELECT u FROM UnidadMoneda u WHERE u.id = :id"),
    @NamedQuery(name = "UnidadMoneda.findByNombre", query = "SELECT u FROM UnidadMoneda u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "UnidadMoneda.findBySimbolo", query = "SELECT u FROM UnidadMoneda u WHERE u.simbolo = :simbolo"),
    @NamedQuery(name = "UnidadMoneda.findByEstado", query = "SELECT u FROM UnidadMoneda u WHERE u.estado = :estado"),
    @NamedQuery(name = "UnidadMoneda.findByFechaRegistro", query = "SELECT u FROM UnidadMoneda u WHERE u.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "UnidadMoneda.findByIdUsuario", query = "SELECT u FROM UnidadMoneda u WHERE u.idUsuario = :idUsuario")})
public class UnidadMoneda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "simbolo")
    private String simbolo;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;

    public UnidadMoneda() {
    }

    public UnidadMoneda(Integer id) {
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

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UnidadMoneda)) {
            return false;
        }
        UnidadMoneda other = (UnidadMoneda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.UnidadMoneda[ id=" + id + " ]";
    }
    
}
