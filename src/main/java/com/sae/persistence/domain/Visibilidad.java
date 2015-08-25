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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "visibilidad", catalog = "bdsae", schema = "autenticacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visibilidad.findAll", query = "SELECT v FROM Visibilidad v"),
    @NamedQuery(name = "Visibilidad.findById", query = "SELECT v FROM Visibilidad v WHERE v.id = :id"),
    @NamedQuery(name = "Visibilidad.findByNombre", query = "SELECT v FROM Visibilidad v WHERE v.nombre = :nombre"),
    @NamedQuery(name = "Visibilidad.findByEstado", query = "SELECT v FROM Visibilidad v WHERE v.estado = :estado"),
    @NamedQuery(name = "Visibilidad.findByFechaRegistro", query = "SELECT v FROM Visibilidad v WHERE v.fechaRegistro = :fechaRegistro")})
public class Visibilidad implements Serializable {
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
    @OneToMany(mappedBy = "idVisibilidad")
    private List<OpcionPerfilVisibilidad> opcionPerfilVisibilidadList;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @ManyToOne
    private Usuario idUsuario;

    public Visibilidad() {
    }

    public Visibilidad(Integer id) {
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
    public List<OpcionPerfilVisibilidad> getOpcionPerfilVisibilidadList() {
        return opcionPerfilVisibilidadList;
    }

    public void setOpcionPerfilVisibilidadList(List<OpcionPerfilVisibilidad> opcionPerfilVisibilidadList) {
        this.opcionPerfilVisibilidadList = opcionPerfilVisibilidadList;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
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
        if (!(object instanceof Visibilidad)) {
            return false;
        }
        Visibilidad other = (Visibilidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Visibilidad[ id=" + id + " ]";
    }
    
}
