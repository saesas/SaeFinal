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
@Table(name = "revision_contrato_laboral", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RevisionContratoLaboral.findAll", query = "SELECT r FROM RevisionContratoLaboral r"),
    @NamedQuery(name = "RevisionContratoLaboral.findById", query = "SELECT r FROM RevisionContratoLaboral r WHERE r.id = :id"),
    @NamedQuery(name = "RevisionContratoLaboral.findByIdContrato", query = "SELECT r FROM RevisionContratoLaboral r WHERE r.idContrato = :idContrato"),
    @NamedQuery(name = "RevisionContratoLaboral.findByEstadoAprobacion", query = "SELECT r FROM RevisionContratoLaboral r WHERE r.estadoAprobacion = :estadoAprobacion"),
    @NamedQuery(name = "RevisionContratoLaboral.findByObservacion", query = "SELECT r FROM RevisionContratoLaboral r WHERE r.observacion = :observacion"),
    @NamedQuery(name = "RevisionContratoLaboral.findByFechaRegistro", query = "SELECT r FROM RevisionContratoLaboral r WHERE r.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "RevisionContratoLaboral.findByIdUsuario", query = "SELECT r FROM RevisionContratoLaboral r WHERE r.idUsuario = :idUsuario")})
public class RevisionContratoLaboral implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_contrato")
    private Integer idContrato;
    @Column(name = "estado_aprobacion")
    private Boolean estadoAprobacion;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_item", referencedColumnName = "id")
    @ManyToOne
    private ItemContrato idItem;

    public RevisionContratoLaboral() {
    }

    public RevisionContratoLaboral(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Integer idContrato) {
        this.idContrato = idContrato;
    }

    public Boolean getEstadoAprobacion() {
        return estadoAprobacion;
    }

    public void setEstadoAprobacion(Boolean estadoAprobacion) {
        this.estadoAprobacion = estadoAprobacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public ItemContrato getIdItem() {
        return idItem;
    }

    public void setIdItem(ItemContrato idItem) {
        this.idItem = idItem;
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
        if (!(object instanceof RevisionContratoLaboral)) {
            return false;
        }
        RevisionContratoLaboral other = (RevisionContratoLaboral) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.RevisionContratoLaboral[ id=" + id + " ]";
    }
    
}
