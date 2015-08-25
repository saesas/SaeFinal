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
@Table(name = "estado_financiero", catalog = "bdsae", schema = "licitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoFinanciero.findAll", query = "SELECT e FROM EstadoFinanciero e"),
    @NamedQuery(name = "EstadoFinanciero.findById", query = "SELECT e FROM EstadoFinanciero e WHERE e.id = :id"),
    @NamedQuery(name = "EstadoFinanciero.findByNombre", query = "SELECT e FROM EstadoFinanciero e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "EstadoFinanciero.findByEstado", query = "SELECT e FROM EstadoFinanciero e WHERE e.estado = :estado"),
    @NamedQuery(name = "EstadoFinanciero.findByFechaRegistro", query = "SELECT e FROM EstadoFinanciero e WHERE e.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "EstadoFinanciero.findByIdUsuario", query = "SELECT e FROM EstadoFinanciero e WHERE e.idUsuario = :idUsuario")})
public class EstadoFinanciero implements Serializable {
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
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @OneToMany(mappedBy = "idTipo")
    private List<EstadoFinancieroTercero> estadoFinancieroTerceroList;

    public EstadoFinanciero() {
    }

    public EstadoFinanciero(Integer id) {
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

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlTransient
    public List<EstadoFinancieroTercero> getEstadoFinancieroTerceroList() {
        return estadoFinancieroTerceroList;
    }

    public void setEstadoFinancieroTerceroList(List<EstadoFinancieroTercero> estadoFinancieroTerceroList) {
        this.estadoFinancieroTerceroList = estadoFinancieroTerceroList;
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
        if (!(object instanceof EstadoFinanciero)) {
            return false;
        }
        EstadoFinanciero other = (EstadoFinanciero) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.EstadoFinanciero[ id=" + id + " ]";
    }
    
}
