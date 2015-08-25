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
@Table(name = "destinatario_bitacora", catalog = "bdsae", schema = "tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DestinatarioBitacora.findAll", query = "SELECT d FROM DestinatarioBitacora d"),
    @NamedQuery(name = "DestinatarioBitacora.findById", query = "SELECT d FROM DestinatarioBitacora d WHERE d.id = :id"),
    @NamedQuery(name = "DestinatarioBitacora.findByIdProyecto", query = "SELECT d FROM DestinatarioBitacora d WHERE d.idProyecto = :idProyecto"),
    @NamedQuery(name = "DestinatarioBitacora.findByIdDestinatario", query = "SELECT d FROM DestinatarioBitacora d WHERE d.idDestinatario = :idDestinatario"),
    @NamedQuery(name = "DestinatarioBitacora.findByEstado", query = "SELECT d FROM DestinatarioBitacora d WHERE d.estado = :estado"),
    @NamedQuery(name = "DestinatarioBitacora.findByFechaRegistro", query = "SELECT d FROM DestinatarioBitacora d WHERE d.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "DestinatarioBitacora.findByIdUsuario", query = "SELECT d FROM DestinatarioBitacora d WHERE d.idUsuario = :idUsuario")})
public class DestinatarioBitacora implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_proyecto")
    private Integer idProyecto;
    @Column(name = "id_destinatario")
    private Integer idDestinatario;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;

    public DestinatarioBitacora() {
    }

    public DestinatarioBitacora(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Integer getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(Integer idDestinatario) {
        this.idDestinatario = idDestinatario;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DestinatarioBitacora)) {
            return false;
        }
        DestinatarioBitacora other = (DestinatarioBitacora) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.DestinatarioBitacora[ id=" + id + " ]";
    }
    
}
