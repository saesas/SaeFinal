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
@Table(name = "pago_soi", catalog = "bdsae", schema = "tesoreria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoSoi.findAll", query = "SELECT p FROM PagoSoi p"),
    @NamedQuery(name = "PagoSoi.findById", query = "SELECT p FROM PagoSoi p WHERE p.id = :id"),
    @NamedQuery(name = "PagoSoi.findByNumeroPlanilla", query = "SELECT p FROM PagoSoi p WHERE p.numeroPlanilla = :numeroPlanilla"),
    @NamedQuery(name = "PagoSoi.findByIdMedioPago", query = "SELECT p FROM PagoSoi p WHERE p.idMedioPago = :idMedioPago"),
    @NamedQuery(name = "PagoSoi.findByIdEntidadFinanciera", query = "SELECT p FROM PagoSoi p WHERE p.idEntidadFinanciera = :idEntidadFinanciera"),
    @NamedQuery(name = "PagoSoi.findByFechaPago", query = "SELECT p FROM PagoSoi p WHERE p.fechaPago = :fechaPago"),
    @NamedQuery(name = "PagoSoi.findByIdComprobante", query = "SELECT p FROM PagoSoi p WHERE p.idComprobante = :idComprobante"),
    @NamedQuery(name = "PagoSoi.findByIdNomina", query = "SELECT p FROM PagoSoi p WHERE p.idNomina = :idNomina"),
    @NamedQuery(name = "PagoSoi.findByFechaRegistro", query = "SELECT p FROM PagoSoi p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "PagoSoi.findByIdUsuario", query = "SELECT p FROM PagoSoi p WHERE p.idUsuario = :idUsuario")})
public class PagoSoi implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "numero_planilla")
    private Integer numeroPlanilla;
    @Column(name = "id_medio_pago")
    private Integer idMedioPago;
    @Column(name = "id_entidad_financiera")
    private Integer idEntidadFinanciera;
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @Column(name = "id_comprobante")
    private Integer idComprobante;
    @Column(name = "id_nomina")
    private Integer idNomina;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @OneToMany(mappedBy = "idPagoSoi")
    private List<AportePagoSoi> aportePagoSoiList;

    public PagoSoi() {
    }

    public PagoSoi(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumeroPlanilla() {
        return numeroPlanilla;
    }

    public void setNumeroPlanilla(Integer numeroPlanilla) {
        this.numeroPlanilla = numeroPlanilla;
    }

    public Integer getIdMedioPago() {
        return idMedioPago;
    }

    public void setIdMedioPago(Integer idMedioPago) {
        this.idMedioPago = idMedioPago;
    }

    public Integer getIdEntidadFinanciera() {
        return idEntidadFinanciera;
    }

    public void setIdEntidadFinanciera(Integer idEntidadFinanciera) {
        this.idEntidadFinanciera = idEntidadFinanciera;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Integer getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(Integer idComprobante) {
        this.idComprobante = idComprobante;
    }

    public Integer getIdNomina() {
        return idNomina;
    }

    public void setIdNomina(Integer idNomina) {
        this.idNomina = idNomina;
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

    @XmlTransient
    public List<AportePagoSoi> getAportePagoSoiList() {
        return aportePagoSoiList;
    }

    public void setAportePagoSoiList(List<AportePagoSoi> aportePagoSoiList) {
        this.aportePagoSoiList = aportePagoSoiList;
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
        if (!(object instanceof PagoSoi)) {
            return false;
        }
        PagoSoi other = (PagoSoi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.PagoSoi[ id=" + id + " ]";
    }
    
}
