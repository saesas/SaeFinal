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
@Table(name = "motivo_terminacion", catalog = "bdsae", schema = "talento_humano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MotivoTerminacion.findAll", query = "SELECT m FROM MotivoTerminacion m"),
    @NamedQuery(name = "MotivoTerminacion.findById", query = "SELECT m FROM MotivoTerminacion m WHERE m.id = :id"),
    @NamedQuery(name = "MotivoTerminacion.findByEstado", query = "SELECT m FROM MotivoTerminacion m WHERE m.estado = :estado"),
    @NamedQuery(name = "MotivoTerminacion.findByNombre", query = "SELECT m FROM MotivoTerminacion m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "MotivoTerminacion.findByFechaRegistro", query = "SELECT m FROM MotivoTerminacion m WHERE m.fechaRegistro = :fechaRegistro")})
public class MotivoTerminacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "idMotivoTerminacion")
    private List<ContratoTercero> contratoTerceroList;

    public MotivoTerminacion() {
    }

    public MotivoTerminacion(Integer id) {
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

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @XmlTransient
    public List<ContratoTercero> getContratoTerceroList() {
        return contratoTerceroList;
    }

    public void setContratoTerceroList(List<ContratoTercero> contratoTerceroList) {
        this.contratoTerceroList = contratoTerceroList;
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
        if (!(object instanceof MotivoTerminacion)) {
            return false;
        }
        MotivoTerminacion other = (MotivoTerminacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.MotivoTerminacion[ id=" + id + " ]";
    }
    
}
