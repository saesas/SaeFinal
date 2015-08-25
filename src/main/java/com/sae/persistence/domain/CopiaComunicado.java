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
@Table(name = "copia_comunicado", catalog = "bdsae", schema = "comunicaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CopiaComunicado.findAll", query = "SELECT c FROM CopiaComunicado c"),
    @NamedQuery(name = "CopiaComunicado.findById", query = "SELECT c FROM CopiaComunicado c WHERE c.id = :id"),
    @NamedQuery(name = "CopiaComunicado.findByEstado", query = "SELECT c FROM CopiaComunicado c WHERE c.estado = :estado"),
    @NamedQuery(name = "CopiaComunicado.findByIdTercero", query = "SELECT c FROM CopiaComunicado c WHERE c.idTercero = :idTercero"),
    @NamedQuery(name = "CopiaComunicado.findByFechaRegistro", query = "SELECT c FROM CopiaComunicado c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "CopiaComunicado.findByIdUsuario", query = "SELECT c FROM CopiaComunicado c WHERE c.idUsuario = :idUsuario")})
public class CopiaComunicado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_tercero")
    private Integer idTercero;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_comunicado", referencedColumnName = "id")
    @ManyToOne
    private Comunicado idComunicado;

    public CopiaComunicado() {
    }

    public CopiaComunicado(Integer id) {
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

    public Integer getIdTercero() {
        return idTercero;
    }

    public void setIdTercero(Integer idTercero) {
        this.idTercero = idTercero;
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

    public Comunicado getIdComunicado() {
        return idComunicado;
    }

    public void setIdComunicado(Comunicado idComunicado) {
        this.idComunicado = idComunicado;
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
        if (!(object instanceof CopiaComunicado)) {
            return false;
        }
        CopiaComunicado other = (CopiaComunicado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.CopiaComunicado[ id=" + id + " ]";
    }
    
}
