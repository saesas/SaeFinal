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
@Table(name = "cuenta_retencion", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuentaRetencion.findAll", query = "SELECT c FROM CuentaRetencion c"),
    @NamedQuery(name = "CuentaRetencion.findById", query = "SELECT c FROM CuentaRetencion c WHERE c.id = :id"),
    @NamedQuery(name = "CuentaRetencion.findByEstado", query = "SELECT c FROM CuentaRetencion c WHERE c.estado = :estado"),
    @NamedQuery(name = "CuentaRetencion.findByFechaRegistro", query = "SELECT c FROM CuentaRetencion c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "CuentaRetencion.findByIdUsuario", query = "SELECT c FROM CuentaRetencion c WHERE c.idUsuario = :idUsuario"),
    @NamedQuery(name = "CuentaRetencion.findByIdRazonSocial", query = "SELECT c FROM CuentaRetencion c WHERE c.idRazonSocial = :idRazonSocial")})
public class CuentaRetencion implements Serializable {
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
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @JoinColumn(name = "id_valor_retencion", referencedColumnName = "id")
    @ManyToOne
    private ValorRetencion idValorRetencion;
    @JoinColumn(name = "id_puc", referencedColumnName = "id")
    @ManyToOne
    private Puc idPuc;

    public CuentaRetencion() {
    }

    public CuentaRetencion(Integer id) {
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

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    public ValorRetencion getIdValorRetencion() {
        return idValorRetencion;
    }

    public void setIdValorRetencion(ValorRetencion idValorRetencion) {
        this.idValorRetencion = idValorRetencion;
    }

    public Puc getIdPuc() {
        return idPuc;
    }

    public void setIdPuc(Puc idPuc) {
        this.idPuc = idPuc;
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
        if (!(object instanceof CuentaRetencion)) {
            return false;
        }
        CuentaRetencion other = (CuentaRetencion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.CuentaRetencion[ id=" + id + " ]";
    }
    
}
