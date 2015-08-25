/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.domain;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "estado_financiero_tercero", catalog = "bdsae", schema = "licitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoFinancieroTercero.findAll", query = "SELECT e FROM EstadoFinancieroTercero e"),
    @NamedQuery(name = "EstadoFinancieroTercero.findById", query = "SELECT e FROM EstadoFinancieroTercero e WHERE e.id = :id"),
    @NamedQuery(name = "EstadoFinancieroTercero.findByIdTercero", query = "SELECT e FROM EstadoFinancieroTercero e WHERE e.idTercero = :idTercero"),
    @NamedQuery(name = "EstadoFinancieroTercero.findByIdRazonSocial", query = "SELECT e FROM EstadoFinancieroTercero e WHERE e.idRazonSocial = :idRazonSocial"),
    @NamedQuery(name = "EstadoFinancieroTercero.findByValor", query = "SELECT e FROM EstadoFinancieroTercero e WHERE e.valor = :valor"),
    @NamedQuery(name = "EstadoFinancieroTercero.findByEstado", query = "SELECT e FROM EstadoFinancieroTercero e WHERE e.estado = :estado"),
    @NamedQuery(name = "EstadoFinancieroTercero.findByFechaRegistro", query = "SELECT e FROM EstadoFinancieroTercero e WHERE e.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "EstadoFinancieroTercero.findByIdUsuario", query = "SELECT e FROM EstadoFinancieroTercero e WHERE e.idUsuario = :idUsuario")})
public class EstadoFinancieroTercero implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_tercero")
    private Integer idTercero;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @Column(name = "valor")
    private BigInteger valor;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id")
    @ManyToOne
    private EstadoFinanciero idTipo;

    public EstadoFinancieroTercero() {
    }

    public EstadoFinancieroTercero(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdTercero() {
        return idTercero;
    }

    public void setIdTercero(Integer idTercero) {
        this.idTercero = idTercero;
    }

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    public BigInteger getValor() {
        return valor;
    }

    public void setValor(BigInteger valor) {
        this.valor = valor;
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

    public EstadoFinanciero getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(EstadoFinanciero idTipo) {
        this.idTipo = idTipo;
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
        if (!(object instanceof EstadoFinancieroTercero)) {
            return false;
        }
        EstadoFinancieroTercero other = (EstadoFinancieroTercero) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.EstadoFinancieroTercero[ id=" + id + " ]";
    }
    
}
