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
@Table(name = "poder", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Poder.findAll", query = "SELECT p FROM Poder p"),
    @NamedQuery(name = "Poder.findById", query = "SELECT p FROM Poder p WHERE p.id = :id"),
    @NamedQuery(name = "Poder.findByObjeto", query = "SELECT p FROM Poder p WHERE p.objeto = :objeto"),
    @NamedQuery(name = "Poder.findByIdNotaria", query = "SELECT p FROM Poder p WHERE p.idNotaria = :idNotaria"),
    @NamedQuery(name = "Poder.findByNumeroRegistro", query = "SELECT p FROM Poder p WHERE p.numeroRegistro = :numeroRegistro"),
    @NamedQuery(name = "Poder.findByEstado", query = "SELECT p FROM Poder p WHERE p.estado = :estado"),
    @NamedQuery(name = "Poder.findByFechaRegistro", query = "SELECT p FROM Poder p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Poder.findByIdUsuario", query = "SELECT p FROM Poder p WHERE p.idUsuario = :idUsuario"),
    @NamedQuery(name = "Poder.findByIdRazonSocial", query = "SELECT p FROM Poder p WHERE p.idRazonSocial = :idRazonSocial")})
public class Poder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "objeto")
    private String objeto;
    @Column(name = "id_notaria")
    private Integer idNotaria;
    @Column(name = "numero_registro")
    private Integer numeroRegistro;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;

    public Poder() {
    }

    public Poder(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public Integer getIdNotaria() {
        return idNotaria;
    }

    public void setIdNotaria(Integer idNotaria) {
        this.idNotaria = idNotaria;
    }

    public Integer getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(Integer numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
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

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
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
        if (!(object instanceof Poder)) {
            return false;
        }
        Poder other = (Poder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Poder[ id=" + id + " ]";
    }
    
}
