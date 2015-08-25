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
@Table(name = "nivel_comunicado", catalog = "bdsae", schema = "comunicaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NivelComunicado.findAll", query = "SELECT n FROM NivelComunicado n"),
    @NamedQuery(name = "NivelComunicado.findById", query = "SELECT n FROM NivelComunicado n WHERE n.id = :id"),
    @NamedQuery(name = "NivelComunicado.findByNombre", query = "SELECT n FROM NivelComunicado n WHERE n.nombre = :nombre"),
    @NamedQuery(name = "NivelComunicado.findByEstado", query = "SELECT n FROM NivelComunicado n WHERE n.estado = :estado"),
    @NamedQuery(name = "NivelComunicado.findByFechaRegistro", query = "SELECT n FROM NivelComunicado n WHERE n.fechaRegistro = :fechaRegistro")})
public class NivelComunicado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;

    public NivelComunicado() {
    }

    public NivelComunicado(Integer id) {
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

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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
        if (!(object instanceof NivelComunicado)) {
            return false;
        }
        NivelComunicado other = (NivelComunicado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.NivelComunicado[ id=" + id + " ]";
    }
    
}
