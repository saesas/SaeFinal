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
@Table(name = "credito", catalog = "bdsae", schema = "tesoreria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Credito.findAll", query = "SELECT c FROM Credito c"),
    @NamedQuery(name = "Credito.findById", query = "SELECT c FROM Credito c WHERE c.id = :id"),
    @NamedQuery(name = "Credito.findByIdEntidadFinanciera", query = "SELECT c FROM Credito c WHERE c.idEntidadFinanciera = :idEntidadFinanciera"),
    @NamedQuery(name = "Credito.findByNumero", query = "SELECT c FROM Credito c WHERE c.numero = :numero"),
    @NamedQuery(name = "Credito.findByValorPrestamo", query = "SELECT c FROM Credito c WHERE c.valorPrestamo = :valorPrestamo"),
    @NamedQuery(name = "Credito.findByFechaDesembolso", query = "SELECT c FROM Credito c WHERE c.fechaDesembolso = :fechaDesembolso"),
    @NamedQuery(name = "Credito.findByValorCuota", query = "SELECT c FROM Credito c WHERE c.valorCuota = :valorCuota"),
    @NamedQuery(name = "Credito.findByValorComision", query = "SELECT c FROM Credito c WHERE c.valorComision = :valorComision"),
    @NamedQuery(name = "Credito.findByPlazo", query = "SELECT c FROM Credito c WHERE c.plazo = :plazo"),
    @NamedQuery(name = "Credito.findByTasaInteres", query = "SELECT c FROM Credito c WHERE c.tasaInteres = :tasaInteres"),
    @NamedQuery(name = "Credito.findByIdCuentaDesembolso", query = "SELECT c FROM Credito c WHERE c.idCuentaDesembolso = :idCuentaDesembolso"),
    @NamedQuery(name = "Credito.findByIdUsuario", query = "SELECT c FROM Credito c WHERE c.idUsuario = :idUsuario"),
    @NamedQuery(name = "Credito.findByFechaRegistro", query = "SELECT c FROM Credito c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Credito.findByIdRazonSocial", query = "SELECT c FROM Credito c WHERE c.idRazonSocial = :idRazonSocial")})
public class Credito implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_entidad_financiera")
    private Integer idEntidadFinanciera;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "numero")
    private Double numero;
    @Column(name = "valor_prestamo")
    private Double valorPrestamo;
    @Column(name = "fecha_desembolso")
    @Temporal(TemporalType.DATE)
    private Date fechaDesembolso;
    @Column(name = "valor_cuota")
    private Double valorCuota;
    @Column(name = "valor_comision")
    private Double valorComision;
    @Column(name = "plazo")
    private Integer plazo;
    @Column(name = "tasa_interes")
    private Double tasaInteres;
    @Column(name = "id_cuenta_desembolso")
    private Integer idCuentaDesembolso;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id")
    @ManyToOne
    private TipoCredito idTipo;
    @OneToMany(mappedBy = "idCredito")
    private List<PagoCredito> pagoCreditoList;

    public Credito() {
    }

    public Credito(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdEntidadFinanciera() {
        return idEntidadFinanciera;
    }

    public void setIdEntidadFinanciera(Integer idEntidadFinanciera) {
        this.idEntidadFinanciera = idEntidadFinanciera;
    }

    public Double getNumero() {
        return numero;
    }

    public void setNumero(Double numero) {
        this.numero = numero;
    }

    public Double getValorPrestamo() {
        return valorPrestamo;
    }

    public void setValorPrestamo(Double valorPrestamo) {
        this.valorPrestamo = valorPrestamo;
    }

    public Date getFechaDesembolso() {
        return fechaDesembolso;
    }

    public void setFechaDesembolso(Date fechaDesembolso) {
        this.fechaDesembolso = fechaDesembolso;
    }

    public Double getValorCuota() {
        return valorCuota;
    }

    public void setValorCuota(Double valorCuota) {
        this.valorCuota = valorCuota;
    }

    public Double getValorComision() {
        return valorComision;
    }

    public void setValorComision(Double valorComision) {
        this.valorComision = valorComision;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }

    public Double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(Double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public Integer getIdCuentaDesembolso() {
        return idCuentaDesembolso;
    }

    public void setIdCuentaDesembolso(Integer idCuentaDesembolso) {
        this.idCuentaDesembolso = idCuentaDesembolso;
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

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    public TipoCredito getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(TipoCredito idTipo) {
        this.idTipo = idTipo;
    }

    @XmlTransient
    public List<PagoCredito> getPagoCreditoList() {
        return pagoCreditoList;
    }

    public void setPagoCreditoList(List<PagoCredito> pagoCreditoList) {
        this.pagoCreditoList = pagoCreditoList;
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
        if (!(object instanceof Credito)) {
            return false;
        }
        Credito other = (Credito) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Credito[ id=" + id + " ]";
    }
    
}
