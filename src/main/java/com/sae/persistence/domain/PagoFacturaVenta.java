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
@Table(name = "pago_factura_venta", catalog = "bdsae", schema = "tesoreria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoFacturaVenta.findAll", query = "SELECT p FROM PagoFacturaVenta p"),
    @NamedQuery(name = "PagoFacturaVenta.findById", query = "SELECT p FROM PagoFacturaVenta p WHERE p.id = :id"),
    @NamedQuery(name = "PagoFacturaVenta.findByIdFactura", query = "SELECT p FROM PagoFacturaVenta p WHERE p.idFactura = :idFactura"),
    @NamedQuery(name = "PagoFacturaVenta.findByIdMedioPago", query = "SELECT p FROM PagoFacturaVenta p WHERE p.idMedioPago = :idMedioPago"),
    @NamedQuery(name = "PagoFacturaVenta.findByIdPuc", query = "SELECT p FROM PagoFacturaVenta p WHERE p.idPuc = :idPuc"),
    @NamedQuery(name = "PagoFacturaVenta.findByCheque", query = "SELECT p FROM PagoFacturaVenta p WHERE p.cheque = :cheque"),
    @NamedQuery(name = "PagoFacturaVenta.findByFechaPago", query = "SELECT p FROM PagoFacturaVenta p WHERE p.fechaPago = :fechaPago"),
    @NamedQuery(name = "PagoFacturaVenta.findByValor", query = "SELECT p FROM PagoFacturaVenta p WHERE p.valor = :valor"),
    @NamedQuery(name = "PagoFacturaVenta.findByObservacion", query = "SELECT p FROM PagoFacturaVenta p WHERE p.observacion = :observacion"),
    @NamedQuery(name = "PagoFacturaVenta.findByDocumento", query = "SELECT p FROM PagoFacturaVenta p WHERE p.documento = :documento"),
    @NamedQuery(name = "PagoFacturaVenta.findByMotivo", query = "SELECT p FROM PagoFacturaVenta p WHERE p.motivo = :motivo"),
    @NamedQuery(name = "PagoFacturaVenta.findByIdComprobante", query = "SELECT p FROM PagoFacturaVenta p WHERE p.idComprobante = :idComprobante"),
    @NamedQuery(name = "PagoFacturaVenta.findByFechaRegistro", query = "SELECT p FROM PagoFacturaVenta p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "PagoFacturaVenta.findByIdUsuario", query = "SELECT p FROM PagoFacturaVenta p WHERE p.idUsuario = :idUsuario"),
    @NamedQuery(name = "PagoFacturaVenta.findByEstado", query = "SELECT p FROM PagoFacturaVenta p WHERE p.estado = :estado")})
public class PagoFacturaVenta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_factura")
    private Integer idFactura;
    @Column(name = "id_medio_pago")
    private Integer idMedioPago;
    @Column(name = "id_puc")
    private Integer idPuc;
    @Column(name = "cheque")
    private Integer cheque;
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "documento")
    private String documento;
    @Column(name = "motivo")
    private String motivo;
    @Column(name = "id_comprobante")
    private Integer idComprobante;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "estado")
    private Boolean estado;

    public PagoFacturaVenta() {
    }

    public PagoFacturaVenta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public Integer getIdMedioPago() {
        return idMedioPago;
    }

    public void setIdMedioPago(Integer idMedioPago) {
        this.idMedioPago = idMedioPago;
    }

    public Integer getIdPuc() {
        return idPuc;
    }

    public void setIdPuc(Integer idPuc) {
        this.idPuc = idPuc;
    }

    public Integer getCheque() {
        return cheque;
    }

    public void setCheque(Integer cheque) {
        this.cheque = cheque;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Integer getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(Integer idComprobante) {
        this.idComprobante = idComprobante;
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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
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
        if (!(object instanceof PagoFacturaVenta)) {
            return false;
        }
        PagoFacturaVenta other = (PagoFacturaVenta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.PagoFacturaVenta[ id=" + id + " ]";
    }
    
}
