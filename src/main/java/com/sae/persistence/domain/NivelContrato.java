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
@Table(name = "nivel_contrato", catalog = "bdsae", schema = "talento_humano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NivelContrato.findAll", query = "SELECT n FROM NivelContrato n"),
    @NamedQuery(name = "NivelContrato.findById", query = "SELECT n FROM NivelContrato n WHERE n.id = :id"),
    @NamedQuery(name = "NivelContrato.findByEstado", query = "SELECT n FROM NivelContrato n WHERE n.estado = :estado"),
    @NamedQuery(name = "NivelContrato.findByNombre", query = "SELECT n FROM NivelContrato n WHERE n.nombre = :nombre"),
    @NamedQuery(name = "NivelContrato.findByFechaRegistro", query = "SELECT n FROM NivelContrato n WHERE n.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "NivelContrato.findByIdUsuario", query = "SELECT n FROM NivelContrato n WHERE n.idUsuario = :idUsuario")})
public class NivelContrato implements Serializable {
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
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @OneToMany(mappedBy = "idNivelContrato")
    private List<ContratoTercero> contratoTerceroList;

    public NivelContrato() {
    }

    public NivelContrato(Integer id) {
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

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
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
        if (!(object instanceof NivelContrato)) {
            return false;
        }
        NivelContrato other = (NivelContrato) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.NivelContrato[ id=" + id + " ]";
    }
    
}
