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
@Table(name = "origen_derecho_peticion", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrigenDerechoPeticion.findAll", query = "SELECT o FROM OrigenDerechoPeticion o"),
    @NamedQuery(name = "OrigenDerechoPeticion.findById", query = "SELECT o FROM OrigenDerechoPeticion o WHERE o.id = :id"),
    @NamedQuery(name = "OrigenDerechoPeticion.findByNombre", query = "SELECT o FROM OrigenDerechoPeticion o WHERE o.nombre = :nombre"),
    @NamedQuery(name = "OrigenDerechoPeticion.findByEstado", query = "SELECT o FROM OrigenDerechoPeticion o WHERE o.estado = :estado"),
    @NamedQuery(name = "OrigenDerechoPeticion.findByFechaRegistro", query = "SELECT o FROM OrigenDerechoPeticion o WHERE o.fechaRegistro = :fechaRegistro")})
public class OrigenDerechoPeticion implements Serializable {
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
    @OneToMany(mappedBy = "idOrigen")
    private List<DerechoPeticion> derechoPeticionList;

    public OrigenDerechoPeticion() {
    }

    public OrigenDerechoPeticion(Integer id) {
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
        if (!(object instanceof OrigenDerechoPeticion)) {
            return false;
        }
        OrigenDerechoPeticion other = (OrigenDerechoPeticion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.OrigenDerechoPeticion[ id=" + id + " ]";
    }
    
}
