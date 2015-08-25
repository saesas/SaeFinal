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
@Table(name = "clase_incidente", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClaseIncidente.findAll", query = "SELECT c FROM ClaseIncidente c"),
    @NamedQuery(name = "ClaseIncidente.findById", query = "SELECT c FROM ClaseIncidente c WHERE c.id = :id"),
    @NamedQuery(name = "ClaseIncidente.findByNombre", query = "SELECT c FROM ClaseIncidente c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ClaseIncidente.findByEstado", query = "SELECT c FROM ClaseIncidente c WHERE c.estado = :estado"),
    @NamedQuery(name = "ClaseIncidente.findByFechaRegistro", query = "SELECT c FROM ClaseIncidente c WHERE c.fechaRegistro = :fechaRegistro")})
public class ClaseIncidente implements Serializable {
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
    @JoinColumn(name = "id_procedimiento_juridico", referencedColumnName = "id")
    @ManyToOne
    private Clase idProcedimientoJuridico;

    public ClaseIncidente() {
    }

    public ClaseIncidente(Integer id) {
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

    public Clase getIdProcedimientoJuridico() {
        return idProcedimientoJuridico;
    }

    public void setIdProcedimientoJuridico(Clase idProcedimientoJuridico) {
        this.idProcedimientoJuridico = idProcedimientoJuridico;
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
        if (!(object instanceof ClaseIncidente)) {
            return false;
        }
        ClaseIncidente other = (ClaseIncidente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ClaseIncidente[ id=" + id + " ]";
    }
    
}
