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
@Table(name = "reintegro", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reintegro.findAll", query = "SELECT r FROM Reintegro r"),
    @NamedQuery(name = "Reintegro.findById", query = "SELECT r FROM Reintegro r WHERE r.id = :id"),
    @NamedQuery(name = "Reintegro.findByIdAnticipo", query = "SELECT r FROM Reintegro r WHERE r.idAnticipo = :idAnticipo"),
    @NamedQuery(name = "Reintegro.findByDetalle", query = "SELECT r FROM Reintegro r WHERE r.detalle = :detalle"),
    @NamedQuery(name = "Reintegro.findByFechaRegistro", query = "SELECT r FROM Reintegro r WHERE r.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Reintegro.findByIdUsuario", query = "SELECT r FROM Reintegro r WHERE r.idUsuario = :idUsuario"),
    @NamedQuery(name = "Reintegro.findByEstado", query = "SELECT r FROM Reintegro r WHERE r.estado = :estado"),
    @NamedQuery(name = "Reintegro.findByIdRazonSocial", query = "SELECT r FROM Reintegro r WHERE r.idRazonSocial = :idRazonSocial")})
public class Reintegro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_anticipo")
    private Integer idAnticipo;
    @Column(name = "detalle")
    private String detalle;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @JoinColumn(name = "id_comprobante", referencedColumnName = "id")
    @ManyToOne
    private Comprobante idComprobante;

    public Reintegro() {
    }

    public Reintegro(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdAnticipo() {
        return idAnticipo;
    }

    public void setIdAnticipo(Integer idAnticipo) {
        this.idAnticipo = idAnticipo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    public Comprobante getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(Comprobante idComprobante) {
        this.idComprobante = idComprobante;
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
        if (!(object instanceof Reintegro)) {
            return false;
        }
        Reintegro other = (Reintegro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Reintegro[ id=" + id + " ]";
    }
    
}
