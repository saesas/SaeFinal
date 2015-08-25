/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author SAE2
 */
@Entity
@Table(name = "pago_nomina", catalog = "bdsae", schema = "tesoreria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoNomina.findAll", query = "SELECT p FROM PagoNomina p"),
    @NamedQuery(name = "PagoNomina.findByIdValorPagar", query = "SELECT p FROM PagoNomina p WHERE p.idValorPagar = :idValorPagar"),
    @NamedQuery(name = "PagoNomina.findBySalario", query = "SELECT p FROM PagoNomina p WHERE p.salario = :salario"),
    @NamedQuery(name = "PagoNomina.findByBonificacionPrestacional", query = "SELECT p FROM PagoNomina p WHERE p.bonificacionPrestacional = :bonificacionPrestacional"),
    @NamedQuery(name = "PagoNomina.findByPagoAdicional", query = "SELECT p FROM PagoNomina p WHERE p.pagoAdicional = :pagoAdicional"),
    @NamedQuery(name = "PagoNomina.findByLiquidacion", query = "SELECT p FROM PagoNomina p WHERE p.liquidacion = :liquidacion"),
    @NamedQuery(name = "PagoNomina.findByDescuento", query = "SELECT p FROM PagoNomina p WHERE p.descuento = :descuento"),
    @NamedQuery(name = "PagoNomina.findByTotal", query = "SELECT p FROM PagoNomina p WHERE p.total = :total"),
    @NamedQuery(name = "PagoNomina.findByFechaRegistro", query = "SELECT p FROM PagoNomina p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "PagoNomina.findByIdUsuario", query = "SELECT p FROM PagoNomina p WHERE p.idUsuario = :idUsuario"),
    @NamedQuery(name = "PagoNomina.findByIdMedioPago", query = "SELECT p FROM PagoNomina p WHERE p.idMedioPago = :idMedioPago"),
    @NamedQuery(name = "PagoNomina.findById", query = "SELECT p FROM PagoNomina p WHERE p.id = :id")})
public class PagoNomina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "id_valor_pagar")
    private Integer idValorPagar;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "salario")
    private Double salario;
    @Column(name = "bonificacion_prestacional")
    private Double bonificacionPrestacional;
    @Column(name = "pago_adicional")
    private Double pagoAdicional;
    @Column(name = "liquidacion")
    private Double liquidacion;
    @Column(name = "descuento")
    private Double descuento;
    @Column(name = "total")
    private Double total;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_medio_pago")
    private Integer idMedioPago;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "idPagoNomina")
    private List<PagoNominaComprobante> pagoNominaComprobanteList;

    public PagoNomina() {
    }

    public PagoNomina(Integer id) {
        this.id = id;
    }

    public Integer getIdValorPagar() {
        return idValorPagar;
    }

    public void setIdValorPagar(Integer idValorPagar) {
        this.idValorPagar = idValorPagar;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Double getBonificacionPrestacional() {
        return bonificacionPrestacional;
    }

    public void setBonificacionPrestacional(Double bonificacionPrestacional) {
        this.bonificacionPrestacional = bonificacionPrestacional;
    }

    public Double getPagoAdicional() {
        return pagoAdicional;
    }

    public void setPagoAdicional(Double pagoAdicional) {
        this.pagoAdicional = pagoAdicional;
    }

    public Double getLiquidacion() {
        return liquidacion;
    }

    public void setLiquidacion(Double liquidacion) {
        this.liquidacion = liquidacion;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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

    public Integer getIdMedioPago() {
        return idMedioPago;
    }

    public void setIdMedioPago(Integer idMedioPago) {
        this.idMedioPago = idMedioPago;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public List<PagoNominaComprobante> getPagoNominaComprobanteList() {
        return pagoNominaComprobanteList;
    }

    public void setPagoNominaComprobanteList(List<PagoNominaComprobante> pagoNominaComprobanteList) {
        this.pagoNominaComprobanteList = pagoNominaComprobanteList;
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
        if (!(object instanceof PagoNomina)) {
            return false;
        }
        PagoNomina other = (PagoNomina) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.PagoNomina[ id=" + id + " ]";
    }
    
}
