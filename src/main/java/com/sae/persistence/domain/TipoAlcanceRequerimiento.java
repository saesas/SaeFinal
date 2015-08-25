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
@Table(name = "tipo_alcance_requerimiento", catalog = "bdsae", schema = "compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoAlcanceRequerimiento.findAll", query = "SELECT t FROM TipoAlcanceRequerimiento t"),
    @NamedQuery(name = "TipoAlcanceRequerimiento.findById", query = "SELECT t FROM TipoAlcanceRequerimiento t WHERE t.id = :id"),
    @NamedQuery(name = "TipoAlcanceRequerimiento.findByEstado", query = "SELECT t FROM TipoAlcanceRequerimiento t WHERE t.estado = :estado"),
    @NamedQuery(name = "TipoAlcanceRequerimiento.findByNombre", query = "SELECT t FROM TipoAlcanceRequerimiento t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoAlcanceRequerimiento.findByFechaRegistro", query = "SELECT t FROM TipoAlcanceRequerimiento t WHERE t.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "TipoAlcanceRequerimiento.findByIdUsuario", query = "SELECT t FROM TipoAlcanceRequerimiento t WHERE t.idUsuario = :idUsuario")})
public class TipoAlcanceRequerimiento implements Serializable {
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
    @OneToMany(mappedBy = "idAlcance")
    private List<Requerimiento> requerimientoList;

    public TipoAlcanceRequerimiento() {
    }

    public TipoAlcanceRequerimiento(Integer id) {
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
    public List<Requerimiento> getRequerimientoList() {
        return requerimientoList;
    }

    public void setRequerimientoList(List<Requerimiento> requerimientoList) {
        this.requerimientoList = requerimientoList;
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
        if (!(object instanceof TipoAlcanceRequerimiento)) {
            return false;
        }
        TipoAlcanceRequerimiento other = (TipoAlcanceRequerimiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.TipoAlcanceRequerimiento[ id=" + id + " ]";
    }
    
}
