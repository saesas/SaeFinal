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
@Table(name = "retencion_empleado", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RetencionEmpleado.findAll", query = "SELECT r FROM RetencionEmpleado r"),
    @NamedQuery(name = "RetencionEmpleado.findById", query = "SELECT r FROM RetencionEmpleado r WHERE r.id = :id"),
    @NamedQuery(name = "RetencionEmpleado.findByValorDesde", query = "SELECT r FROM RetencionEmpleado r WHERE r.valorDesde = :valorDesde"),
    @NamedQuery(name = "RetencionEmpleado.findByValorHasta", query = "SELECT r FROM RetencionEmpleado r WHERE r.valorHasta = :valorHasta"),
    @NamedQuery(name = "RetencionEmpleado.findByPorcentajeAplicacion", query = "SELECT r FROM RetencionEmpleado r WHERE r.porcentajeAplicacion = :porcentajeAplicacion"),
    @NamedQuery(name = "RetencionEmpleado.findByUltimaUvt", query = "SELECT r FROM RetencionEmpleado r WHERE r.ultimaUvt = :ultimaUvt"),
    @NamedQuery(name = "RetencionEmpleado.findByUvtMarginal", query = "SELECT r FROM RetencionEmpleado r WHERE r.uvtMarginal = :uvtMarginal"),
    @NamedQuery(name = "RetencionEmpleado.findByEstado", query = "SELECT r FROM RetencionEmpleado r WHERE r.estado = :estado"),
    @NamedQuery(name = "RetencionEmpleado.findByFechaRegistro", query = "SELECT r FROM RetencionEmpleado r WHERE r.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "RetencionEmpleado.findByIdUsuario", query = "SELECT r FROM RetencionEmpleado r WHERE r.idUsuario = :idUsuario")})
public class RetencionEmpleado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_desde")
    private Double valorDesde;
    @Column(name = "valor_hasta")
    private Double valorHasta;
    @Column(name = "porcentaje_aplicacion")
    private Double porcentajeAplicacion;
    @Column(name = "ultima_uvt")
    private Double ultimaUvt;
    @Column(name = "uvt_marginal")
    private Double uvtMarginal;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_valor_tributario", referencedColumnName = "id")
    @ManyToOne
    private ValorTributario idValorTributario;
    @JoinColumn(name = "id_puc", referencedColumnName = "id")
    @ManyToOne
    private Puc idPuc;

    public RetencionEmpleado() {
    }

    public RetencionEmpleado(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValorDesde() {
        return valorDesde;
    }

    public void setValorDesde(Double valorDesde) {
        this.valorDesde = valorDesde;
    }

    public Double getValorHasta() {
        return valorHasta;
    }

    public void setValorHasta(Double valorHasta) {
        this.valorHasta = valorHasta;
    }

    public Double getPorcentajeAplicacion() {
        return porcentajeAplicacion;
    }

    public void setPorcentajeAplicacion(Double porcentajeAplicacion) {
        this.porcentajeAplicacion = porcentajeAplicacion;
    }

    public Double getUltimaUvt() {
        return ultimaUvt;
    }

    public void setUltimaUvt(Double ultimaUvt) {
        this.ultimaUvt = ultimaUvt;
    }

    public Double getUvtMarginal() {
        return uvtMarginal;
    }

    public void setUvtMarginal(Double uvtMarginal) {
        this.uvtMarginal = uvtMarginal;
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

    public ValorTributario getIdValorTributario() {
        return idValorTributario;
    }

    public void setIdValorTributario(ValorTributario idValorTributario) {
        this.idValorTributario = idValorTributario;
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
        if (!(object instanceof RetencionEmpleado)) {
            return false;
        }
        RetencionEmpleado other = (RetencionEmpleado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.RetencionEmpleado[ id=" + id + " ]";
    }
    
}
