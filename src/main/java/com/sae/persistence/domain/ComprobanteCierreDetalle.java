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
@Table(name = "comprobante_cierre_detalle", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComprobanteCierreDetalle.findAll", query = "SELECT c FROM ComprobanteCierreDetalle c"),
    @NamedQuery(name = "ComprobanteCierreDetalle.findById", query = "SELECT c FROM ComprobanteCierreDetalle c WHERE c.id = :id"),
    @NamedQuery(name = "ComprobanteCierreDetalle.findByIdTercero", query = "SELECT c FROM ComprobanteCierreDetalle c WHERE c.idTercero = :idTercero"),
    @NamedQuery(name = "ComprobanteCierreDetalle.findByEstado", query = "SELECT c FROM ComprobanteCierreDetalle c WHERE c.estado = :estado"),
    @NamedQuery(name = "ComprobanteCierreDetalle.findByFechaRegistro", query = "SELECT c FROM ComprobanteCierreDetalle c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ComprobanteCierreDetalle.findByIdUsuario", query = "SELECT c FROM ComprobanteCierreDetalle c WHERE c.idUsuario = :idUsuario")})
public class ComprobanteCierreDetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_tercero")
    private Integer idTercero;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_cuenta_hasta", referencedColumnName = "id")
    @ManyToOne
    private Puc idCuentaHasta;
    @JoinColumn(name = "id_cuenta_destino", referencedColumnName = "id")
    @ManyToOne
    private Puc idCuentaDestino;
    @JoinColumn(name = "id_cuenta_desde", referencedColumnName = "id")
    @ManyToOne
    private Puc idCuentaDesde;
    @JoinColumn(name = "id_comprobante", referencedColumnName = "id")
    @ManyToOne
    private Comprobante idComprobante;

    public ComprobanteCierreDetalle() {
    }

    public ComprobanteCierreDetalle(Integer id) {
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

    public Puc getIdCuentaHasta() {
        return idCuentaHasta;
    }

    public void setIdCuentaHasta(Puc idCuentaHasta) {
        this.idCuentaHasta = idCuentaHasta;
    }

    public Puc getIdCuentaDestino() {
        return idCuentaDestino;
    }

    public void setIdCuentaDestino(Puc idCuentaDestino) {
        this.idCuentaDestino = idCuentaDestino;
    }

    public Puc getIdCuentaDesde() {
        return idCuentaDesde;
    }

    public void setIdCuentaDesde(Puc idCuentaDesde) {
        this.idCuentaDesde = idCuentaDesde;
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
        if (!(object instanceof ComprobanteCierreDetalle)) {
            return false;
        }
        ComprobanteCierreDetalle other = (ComprobanteCierreDetalle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ComprobanteCierreDetalle[ id=" + id + " ]";
    }
    
}
