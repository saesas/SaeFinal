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
@Table(name = "historial_saldo_tercero", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistorialSaldoTercero.findAll", query = "SELECT h FROM HistorialSaldoTercero h"),
    @NamedQuery(name = "HistorialSaldoTercero.findById", query = "SELECT h FROM HistorialSaldoTercero h WHERE h.id = :id"),
    @NamedQuery(name = "HistorialSaldoTercero.findByIdTercero", query = "SELECT h FROM HistorialSaldoTercero h WHERE h.idTercero = :idTercero"),
    @NamedQuery(name = "HistorialSaldoTercero.findBySaldoFinal", query = "SELECT h FROM HistorialSaldoTercero h WHERE h.saldoFinal = :saldoFinal"),
    @NamedQuery(name = "HistorialSaldoTercero.findByAno", query = "SELECT h FROM HistorialSaldoTercero h WHERE h.ano = :ano"),
    @NamedQuery(name = "HistorialSaldoTercero.findByFechaRegistro", query = "SELECT h FROM HistorialSaldoTercero h WHERE h.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "HistorialSaldoTercero.findByIdUsuario", query = "SELECT h FROM HistorialSaldoTercero h WHERE h.idUsuario = :idUsuario"),
    @NamedQuery(name = "HistorialSaldoTercero.findByIdRazonSocial", query = "SELECT h FROM HistorialSaldoTercero h WHERE h.idRazonSocial = :idRazonSocial")})
public class HistorialSaldoTercero implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_tercero")
    private Integer idTercero;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "saldo_final")
    private Double saldoFinal;
    @Column(name = "ano")
    private Integer ano;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;

    public HistorialSaldoTercero() {
    }

    public HistorialSaldoTercero(Integer id) {
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

    public Double getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(Double saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
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
        if (!(object instanceof HistorialSaldoTercero)) {
            return false;
        }
        HistorialSaldoTercero other = (HistorialSaldoTercero) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.HistorialSaldoTercero[ id=" + id + " ]";
    }
    
}
