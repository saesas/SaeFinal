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
@Table(name = "aplicacion_tributaria", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AplicacionTributaria.findAll", query = "SELECT a FROM AplicacionTributaria a"),
    @NamedQuery(name = "AplicacionTributaria.findById", query = "SELECT a FROM AplicacionTributaria a WHERE a.id = :id"),
    @NamedQuery(name = "AplicacionTributaria.findByIdPais", query = "SELECT a FROM AplicacionTributaria a WHERE a.idPais = :idPais"),
    @NamedQuery(name = "AplicacionTributaria.findByEstado", query = "SELECT a FROM AplicacionTributaria a WHERE a.estado = :estado"),
    @NamedQuery(name = "AplicacionTributaria.findByFechaRegistro", query = "SELECT a FROM AplicacionTributaria a WHERE a.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "AplicacionTributaria.findByIdUsuario", query = "SELECT a FROM AplicacionTributaria a WHERE a.idUsuario = :idUsuario")})
public class AplicacionTributaria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_pais")
    private Integer idPais;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_retencion", referencedColumnName = "id")
    @ManyToOne
    private Retencion idRetencion;

    public AplicacionTributaria() {
    }

    public AplicacionTributaria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPais() {
        return idPais;
    }

    public void setIdPais(Integer idPais) {
        this.idPais = idPais;
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

    public Retencion getIdRetencion() {
        return idRetencion;
    }

    public void setIdRetencion(Retencion idRetencion) {
        this.idRetencion = idRetencion;
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
        if (!(object instanceof AplicacionTributaria)) {
            return false;
        }
        AplicacionTributaria other = (AplicacionTributaria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.AplicacionTributaria[ id=" + id + " ]";
    }
    
}
