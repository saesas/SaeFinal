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
@Table(name = "cuenta_bancaria_tercero", catalog = "bdsae", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuentaBancariaTercero.findAll", query = "SELECT c FROM CuentaBancariaTercero c"),
    @NamedQuery(name = "CuentaBancariaTercero.findById", query = "SELECT c FROM CuentaBancariaTercero c WHERE c.id = :id"),
    @NamedQuery(name = "CuentaBancariaTercero.findByNumeroCuenta", query = "SELECT c FROM CuentaBancariaTercero c WHERE c.numeroCuenta = :numeroCuenta"),
    @NamedQuery(name = "CuentaBancariaTercero.findByEstado", query = "SELECT c FROM CuentaBancariaTercero c WHERE c.estado = :estado"),
    @NamedQuery(name = "CuentaBancariaTercero.findByFechaRegistro", query = "SELECT c FROM CuentaBancariaTercero c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "CuentaBancariaTercero.findByIdUsuario", query = "SELECT c FROM CuentaBancariaTercero c WHERE c.idUsuario = :idUsuario")})
public class CuentaBancariaTercero implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "numero_cuenta")
    private Integer numeroCuenta;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_tipo_cuenta", referencedColumnName = "id")
    @ManyToOne
    private TipoCuentaBancaria idTipoCuenta;
    @JoinColumn(name = "id_tercero", referencedColumnName = "id")
    @ManyToOne
    private Tercero idTercero;
    @JoinColumn(name = "id_nombre_banco", referencedColumnName = "id")
    @ManyToOne
    private Tercero idNombreBanco;

    public CuentaBancariaTercero() {
    }

    public CuentaBancariaTercero(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(Integer numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
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

    public TipoCuentaBancaria getIdTipoCuenta() {
        return idTipoCuenta;
    }

    public void setIdTipoCuenta(TipoCuentaBancaria idTipoCuenta) {
        this.idTipoCuenta = idTipoCuenta;
    }

    public Tercero getIdTercero() {
        return idTercero;
    }

    public void setIdTercero(Tercero idTercero) {
        this.idTercero = idTercero;
    }

    public Tercero getIdNombreBanco() {
        return idNombreBanco;
    }

    public void setIdNombreBanco(Tercero idNombreBanco) {
        this.idNombreBanco = idNombreBanco;
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
        if (!(object instanceof CuentaBancariaTercero)) {
            return false;
        }
        CuentaBancariaTercero other = (CuentaBancariaTercero) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.CuentaBancariaTercero[ id=" + id + " ]";
    }
    
}
