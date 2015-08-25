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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "factura_venta", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturaVenta.findAll", query = "SELECT f FROM FacturaVenta f"),
    @NamedQuery(name = "FacturaVenta.findById", query = "SELECT f FROM FacturaVenta f WHERE f.id = :id"),
    @NamedQuery(name = "FacturaVenta.findByIdProyecto", query = "SELECT f FROM FacturaVenta f WHERE f.idProyecto = :idProyecto"),
    @NamedQuery(name = "FacturaVenta.findByIdCliente", query = "SELECT f FROM FacturaVenta f WHERE f.idCliente = :idCliente"),
    @NamedQuery(name = "FacturaVenta.findByFechaElaboracion", query = "SELECT f FROM FacturaVenta f WHERE f.fechaElaboracion = :fechaElaboracion"),
    @NamedQuery(name = "FacturaVenta.findByFechaVencimiento", query = "SELECT f FROM FacturaVenta f WHERE f.fechaVencimiento = :fechaVencimiento"),
    @NamedQuery(name = "FacturaVenta.findByObservacion", query = "SELECT f FROM FacturaVenta f WHERE f.observacion = :observacion"),
    @NamedQuery(name = "FacturaVenta.findByIdPreActa", query = "SELECT f FROM FacturaVenta f WHERE f.idPreActa = :idPreActa"),
    @NamedQuery(name = "FacturaVenta.findByFechaRegistro", query = "SELECT f FROM FacturaVenta f WHERE f.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "FacturaVenta.findByEstado", query = "SELECT f FROM FacturaVenta f WHERE f.estado = :estado"),
    @NamedQuery(name = "FacturaVenta.findByIdUsuario", query = "SELECT f FROM FacturaVenta f WHERE f.idUsuario = :idUsuario"),
    @NamedQuery(name = "FacturaVenta.findByIdRazonSocial", query = "SELECT f FROM FacturaVenta f WHERE f.idRazonSocial = :idRazonSocial")})
public class FacturaVenta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_proyecto")
    private Integer idProyecto;
    @Column(name = "id_cliente")
    private Integer idCliente;
    @Column(name = "fecha_elaboracion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaElaboracion;
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "id_pre_acta")
    private Integer idPreActa;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @OneToMany(mappedBy = "idFacturaVenta")
    private List<FacturaVentaMovimientoContable> facturaVentaMovimientoContableList;
    @OneToMany(mappedBy = "idFactura")
    private List<FacturaVentaRetencion> facturaVentaRetencionList;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id")
    @ManyToOne
    private TipoFacturaVenta idTipo;
    @JoinColumn(name = "id_puc", referencedColumnName = "id")
    @ManyToOne
    private Puc idPuc;

    public FacturaVenta() {
    }

    public FacturaVenta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getIdPreActa() {
        return idPreActa;
    }

    public void setIdPreActa(Integer idPreActa) {
        this.idPreActa = idPreActa;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
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

    @XmlTransient
    public List<FacturaVentaMovimientoContable> getFacturaVentaMovimientoContableList() {
        return facturaVentaMovimientoContableList;
    }

    public void setFacturaVentaMovimientoContableList(List<FacturaVentaMovimientoContable> facturaVentaMovimientoContableList) {
        this.facturaVentaMovimientoContableList = facturaVentaMovimientoContableList;
    }

    @XmlTransient
    public List<FacturaVentaRetencion> getFacturaVentaRetencionList() {
        return facturaVentaRetencionList;
    }

    public void setFacturaVentaRetencionList(List<FacturaVentaRetencion> facturaVentaRetencionList) {
        this.facturaVentaRetencionList = facturaVentaRetencionList;
    }

    public TipoFacturaVenta getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(TipoFacturaVenta idTipo) {
        this.idTipo = idTipo;
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
        if (!(object instanceof FacturaVenta)) {
            return false;
        }
        FacturaVenta other = (FacturaVenta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.FacturaVenta[ id=" + id + " ]";
    }
    
}
