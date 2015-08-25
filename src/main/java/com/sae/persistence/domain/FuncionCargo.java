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
@Table(name = "funcion_cargo", catalog = "bdsae", schema = "talento_humano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FuncionCargo.findAll", query = "SELECT f FROM FuncionCargo f"),
    @NamedQuery(name = "FuncionCargo.findById", query = "SELECT f FROM FuncionCargo f WHERE f.id = :id"),
    @NamedQuery(name = "FuncionCargo.findByIdCargo", query = "SELECT f FROM FuncionCargo f WHERE f.idCargo = :idCargo"),
    @NamedQuery(name = "FuncionCargo.findByEstado", query = "SELECT f FROM FuncionCargo f WHERE f.estado = :estado"),
    @NamedQuery(name = "FuncionCargo.findByFechaRegistro", query = "SELECT f FROM FuncionCargo f WHERE f.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "FuncionCargo.findByIdUsuario", query = "SELECT f FROM FuncionCargo f WHERE f.idUsuario = :idUsuario")})
public class FuncionCargo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_cargo")
    private Integer idCargo;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_funcion", referencedColumnName = "id")
    @ManyToOne
    private Funcion idFuncion;

    public FuncionCargo() {
    }

    public FuncionCargo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
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

    public Funcion getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(Funcion idFuncion) {
        this.idFuncion = idFuncion;
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
        if (!(object instanceof FuncionCargo)) {
            return false;
        }
        FuncionCargo other = (FuncionCargo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.FuncionCargo[ id=" + id + " ]";
    }
    
}
