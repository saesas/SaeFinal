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
@Table(name = "motivo_derecho_peticion", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MotivoDerechoPeticion.findAll", query = "SELECT m FROM MotivoDerechoPeticion m"),
    @NamedQuery(name = "MotivoDerechoPeticion.findById", query = "SELECT m FROM MotivoDerechoPeticion m WHERE m.id = :id"),
    @NamedQuery(name = "MotivoDerechoPeticion.findByNombre", query = "SELECT m FROM MotivoDerechoPeticion m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "MotivoDerechoPeticion.findByEstado", query = "SELECT m FROM MotivoDerechoPeticion m WHERE m.estado = :estado"),
    @NamedQuery(name = "MotivoDerechoPeticion.findByFechaRegistro", query = "SELECT m FROM MotivoDerechoPeticion m WHERE m.fechaRegistro = :fechaRegistro")})
public class MotivoDerechoPeticion implements Serializable {
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
    @OneToMany(mappedBy = "idMotivo")
    private List<DerechoPeticion> derechoPeticionList;

    public MotivoDerechoPeticion() {
    }

    public MotivoDerechoPeticion(Integer id) {
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

    @XmlTransient
    public List<DerechoPeticion> getDerechoPeticionList() {
        return derechoPeticionList;
    }

    public void setDerechoPeticionList(List<DerechoPeticion> derechoPeticionList) {
        this.derechoPeticionList = derechoPeticionList;
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
        if (!(object instanceof MotivoDerechoPeticion)) {
            return false;
        }
        MotivoDerechoPeticion other = (MotivoDerechoPeticion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.MotivoDerechoPeticion[ id=" + id + " ]";
    }
    
}
