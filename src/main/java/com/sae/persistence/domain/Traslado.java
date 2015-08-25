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
@Table(name = "traslado", catalog = "bdsae", schema = "tesoreria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Traslado.findAll", query = "SELECT t FROM Traslado t"),
    @NamedQuery(name = "Traslado.findById", query = "SELECT t FROM Traslado t WHERE t.id = :id"),
    @NamedQuery(name = "Traslado.findByIdProductoOrigen", query = "SELECT t FROM Traslado t WHERE t.idProductoOrigen = :idProductoOrigen"),
    @NamedQuery(name = "Traslado.findByValor", query = "SELECT t FROM Traslado t WHERE t.valor = :valor"),
    @NamedQuery(name = "Traslado.findByDetalle", query = "SELECT t FROM Traslado t WHERE t.detalle = :detalle"),
    @NamedQuery(name = "Traslado.findByIdProductoDestino", query = "SELECT t FROM Traslado t WHERE t.idProductoDestino = :idProductoDestino"),
    @NamedQuery(name = "Traslado.findByIdComprobante", query = "SELECT t FROM Traslado t WHERE t.idComprobante = :idComprobante"),
    @NamedQuery(name = "Traslado.findByIdUsuario", query = "SELECT t FROM Traslado t WHERE t.idUsuario = :idUsuario"),
    @NamedQuery(name = "Traslado.findByFechaRegistro", query = "SELECT t FROM Traslado t WHERE t.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Traslado.findByFechaTraslado", query = "SELECT t FROM Traslado t WHERE t.fechaTraslado = :fechaTraslado"),
    @NamedQuery(name = "Traslado.findByIdRazonSocial", query = "SELECT t FROM Traslado t WHERE t.idRazonSocial = :idRazonSocial")})
public class Traslado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_producto_origen")
    private Integer idProductoOrigen;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "detalle")
    private String detalle;
    @Column(name = "id_producto_destino")
    private Integer idProductoDestino;
    @Column(name = "id_comprobante")
    private Integer idComprobante;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "fecha_traslado")
    @Temporal(TemporalType.DATE)
    private Date fechaTraslado;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id")
    @ManyToOne
    private TipoTraslado idTipo;

    public Traslado() {
    }

    public Traslado(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdProductoOrigen() {
        return idProductoOrigen;
    }

    public void setIdProductoOrigen(Integer idProductoOrigen) {
        this.idProductoOrigen = idProductoOrigen;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Integer getIdProductoDestino() {
        return idProductoDestino;
    }

    public void setIdProductoDestino(Integer idProductoDestino) {
        this.idProductoDestino = idProductoDestino;
    }

    public Integer getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(Integer idComprobante) {
        this.idComprobante = idComprobante;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaTraslado() {
        return fechaTraslado;
    }

    public void setFechaTraslado(Date fechaTraslado) {
        this.fechaTraslado = fechaTraslado;
    }

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    public TipoTraslado getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(TipoTraslado idTipo) {
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
        if (!(object instanceof Traslado)) {
            return false;
        }
        Traslado other = (Traslado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Traslado[ id=" + id + " ]";
    }
    
}
