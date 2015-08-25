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
@Table(name = "contratista_contrato", catalog = "bdsae", schema = "tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContratistaContrato.findAll", query = "SELECT c FROM ContratistaContrato c"),
    @NamedQuery(name = "ContratistaContrato.findById", query = "SELECT c FROM ContratistaContrato c WHERE c.id = :id"),
    @NamedQuery(name = "ContratistaContrato.findByIdContratista", query = "SELECT c FROM ContratistaContrato c WHERE c.idContratista = :idContratista"),
    @NamedQuery(name = "ContratistaContrato.findByEstado", query = "SELECT c FROM ContratistaContrato c WHERE c.estado = :estado"),
    @NamedQuery(name = "ContratistaContrato.findByFechaRegistro", query = "SELECT c FROM ContratistaContrato c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ContratistaContrato.findByIdUsuario", query = "SELECT c FROM ContratistaContrato c WHERE c.idUsuario = :idUsuario")})
public class ContratistaContrato implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_contratista")
    private Integer idContratista;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_contrato", referencedColumnName = "id")
    @ManyToOne
    private ContratoProyectoProveedor idContrato;

    public ContratistaContrato() {
    }

    public ContratistaContrato(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdContratista() {
        return idContratista;
    }

    public void setIdContratista(Integer idContratista) {
        this.idContratista = idContratista;
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

    public ContratoProyectoProveedor getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(ContratoProyectoProveedor idContrato) {
        this.idContrato = idContrato;
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
        if (!(object instanceof ContratistaContrato)) {
            return false;
        }
        ContratistaContrato other = (ContratistaContrato) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ContratistaContrato[ id=" + id + " ]";
    }
    
}
