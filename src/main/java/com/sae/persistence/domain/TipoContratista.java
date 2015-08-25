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
@Table(name = "tipo_contratista", catalog = "bdsae", schema = "tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoContratista.findAll", query = "SELECT t FROM TipoContratista t"),
    @NamedQuery(name = "TipoContratista.findById", query = "SELECT t FROM TipoContratista t WHERE t.id = :id"),
    @NamedQuery(name = "TipoContratista.findByNombre", query = "SELECT t FROM TipoContratista t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoContratista.findByEstado", query = "SELECT t FROM TipoContratista t WHERE t.estado = :estado"),
    @NamedQuery(name = "TipoContratista.findByFechaRegistro", query = "SELECT t FROM TipoContratista t WHERE t.fechaRegistro = :fechaRegistro")})
public class TipoContratista implements Serializable {
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
    @OneToMany(mappedBy = "idTipoContratista")
    private List<TipoContratoProyecto> tipoContratoProyectoList;

    public TipoContratista() {
    }

    public TipoContratista(Integer id) {
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
    public List<TipoContratoProyecto> getTipoContratoProyectoList() {
        return tipoContratoProyectoList;
    }

    public void setTipoContratoProyectoList(List<TipoContratoProyecto> tipoContratoProyectoList) {
        this.tipoContratoProyectoList = tipoContratoProyectoList;
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
        if (!(object instanceof TipoContratista)) {
            return false;
        }
        TipoContratista other = (TipoContratista) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.TipoContratista[ id=" + id + " ]";
    }
    
}
