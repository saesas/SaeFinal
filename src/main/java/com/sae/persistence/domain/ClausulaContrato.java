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
@Table(name = "clausula_contrato", catalog = "bdsae", schema = "talento_humano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClausulaContrato.findAll", query = "SELECT c FROM ClausulaContrato c"),
    @NamedQuery(name = "ClausulaContrato.findById", query = "SELECT c FROM ClausulaContrato c WHERE c.id = :id"),
    @NamedQuery(name = "ClausulaContrato.findByEstado", query = "SELECT c FROM ClausulaContrato c WHERE c.estado = :estado"),
    @NamedQuery(name = "ClausulaContrato.findByFechaRegistro", query = "SELECT c FROM ClausulaContrato c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ClausulaContrato.findByIdUsuario", query = "SELECT c FROM ClausulaContrato c WHERE c.idUsuario = :idUsuario")})
public class ClausulaContrato implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_clausula", referencedColumnName = "id")
    @ManyToOne
    private TipoClausulaContrato idClausula;
    @JoinColumn(name = "id_contrato", referencedColumnName = "id")
    @ManyToOne
    private ContratoTercero idContrato;

    public ClausulaContrato() {
    }

    public ClausulaContrato(Integer id) {
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

    public TipoClausulaContrato getIdClausula() {
        return idClausula;
    }

    public void setIdClausula(TipoClausulaContrato idClausula) {
        this.idClausula = idClausula;
    }

    public ContratoTercero getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(ContratoTercero idContrato) {
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
        if (!(object instanceof ClausulaContrato)) {
            return false;
        }
        ClausulaContrato other = (ClausulaContrato) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ClausulaContrato[ id=" + id + " ]";
    }
    
}
