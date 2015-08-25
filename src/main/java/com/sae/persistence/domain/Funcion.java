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
@Table(name = "funcion", catalog = "bdsae", schema = "talento_humano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Funcion.findAll", query = "SELECT f FROM Funcion f"),
    @NamedQuery(name = "Funcion.findById", query = "SELECT f FROM Funcion f WHERE f.id = :id"),
    @NamedQuery(name = "Funcion.findByNombre", query = "SELECT f FROM Funcion f WHERE f.nombre = :nombre"),
    @NamedQuery(name = "Funcion.findByDescripcion", query = "SELECT f FROM Funcion f WHERE f.descripcion = :descripcion"),
    @NamedQuery(name = "Funcion.findByEstado", query = "SELECT f FROM Funcion f WHERE f.estado = :estado"),
    @NamedQuery(name = "Funcion.findByFechaRegistro", query = "SELECT f FROM Funcion f WHERE f.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Funcion.findByIdUsuario", query = "SELECT f FROM Funcion f WHERE f.idUsuario = :idUsuario")})
public class Funcion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @OneToMany(mappedBy = "idFuncion")
    private List<ContratoFuncion> contratoFuncionList;
    @OneToMany(mappedBy = "idFuncion")
    private List<FuncionCargo> funcionCargoList;

    public Funcion() {
    }

    public Funcion(Integer id) {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    public List<ContratoFuncion> getContratoFuncionList() {
        return contratoFuncionList;
    }

    public void setContratoFuncionList(List<ContratoFuncion> contratoFuncionList) {
        this.contratoFuncionList = contratoFuncionList;
    }

    @XmlTransient
    public List<FuncionCargo> getFuncionCargoList() {
        return funcionCargoList;
    }

    public void setFuncionCargoList(List<FuncionCargo> funcionCargoList) {
        this.funcionCargoList = funcionCargoList;
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
        if (!(object instanceof Funcion)) {
            return false;
        }
        Funcion other = (Funcion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Funcion[ id=" + id + " ]";
    }
    
}
