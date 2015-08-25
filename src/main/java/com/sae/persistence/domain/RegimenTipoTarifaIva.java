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
@Table(name = "regimen_tipo_tarifa_iva", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegimenTipoTarifaIva.findAll", query = "SELECT r FROM RegimenTipoTarifaIva r"),
    @NamedQuery(name = "RegimenTipoTarifaIva.findById", query = "SELECT r FROM RegimenTipoTarifaIva r WHERE r.id = :id"),
    @NamedQuery(name = "RegimenTipoTarifaIva.findByEstado", query = "SELECT r FROM RegimenTipoTarifaIva r WHERE r.estado = :estado"),
    @NamedQuery(name = "RegimenTipoTarifaIva.findByFechaRegistro", query = "SELECT r FROM RegimenTipoTarifaIva r WHERE r.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "RegimenTipoTarifaIva.findByIdUsuario", query = "SELECT r FROM RegimenTipoTarifaIva r WHERE r.idUsuario = :idUsuario")})
public class RegimenTipoTarifaIva implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_tipo_tarifa_iva", referencedColumnName = "id")
    @ManyToOne
    private TipoTarifaPuc idTipoTarifaIva;
    @JoinColumn(name = "id_regimen", referencedColumnName = "id")
    @ManyToOne
    private Regimen idRegimen;

    public RegimenTipoTarifaIva() {
    }

    public RegimenTipoTarifaIva(Integer id) {
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

    public TipoTarifaPuc getIdTipoTarifaIva() {
        return idTipoTarifaIva;
    }

    public void setIdTipoTarifaIva(TipoTarifaPuc idTipoTarifaIva) {
        this.idTipoTarifaIva = idTipoTarifaIva;
    }

    public Regimen getIdRegimen() {
        return idRegimen;
    }

    public void setIdRegimen(Regimen idRegimen) {
        this.idRegimen = idRegimen;
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
        if (!(object instanceof RegimenTipoTarifaIva)) {
            return false;
        }
        RegimenTipoTarifaIva other = (RegimenTipoTarifaIva) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.RegimenTipoTarifaIva[ id=" + id + " ]";
    }
    
}
