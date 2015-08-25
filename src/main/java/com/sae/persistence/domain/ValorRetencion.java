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
@Table(name = "valor_retencion", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValorRetencion.findAll", query = "SELECT v FROM ValorRetencion v"),
    @NamedQuery(name = "ValorRetencion.findById", query = "SELECT v FROM ValorRetencion v WHERE v.id = :id"),
    @NamedQuery(name = "ValorRetencion.findByBase", query = "SELECT v FROM ValorRetencion v WHERE v.base = :base"),
    @NamedQuery(name = "ValorRetencion.findByPorcentaje", query = "SELECT v FROM ValorRetencion v WHERE v.porcentaje = :porcentaje"),
    @NamedQuery(name = "ValorRetencion.findByFechaInicio", query = "SELECT v FROM ValorRetencion v WHERE v.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "ValorRetencion.findByFechaFin", query = "SELECT v FROM ValorRetencion v WHERE v.fechaFin = :fechaFin"),
    @NamedQuery(name = "ValorRetencion.findByEstado", query = "SELECT v FROM ValorRetencion v WHERE v.estado = :estado"),
    @NamedQuery(name = "ValorRetencion.findByPorcentajeNodeclarante", query = "SELECT v FROM ValorRetencion v WHERE v.porcentajeNodeclarante = :porcentajeNodeclarante"),
    @NamedQuery(name = "ValorRetencion.findByFechaRegistro", query = "SELECT v FROM ValorRetencion v WHERE v.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ValorRetencion.findByIdUsuario", query = "SELECT v FROM ValorRetencion v WHERE v.idUsuario = :idUsuario"),
    @NamedQuery(name = "ValorRetencion.findByIdRazonSocial", query = "SELECT v FROM ValorRetencion v WHERE v.idRazonSocial = :idRazonSocial")})
public class ValorRetencion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "base")
    private Double base;
    @Column(name = "porcentaje")
    private Double porcentaje;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "porcentaje_nodeclarante")
    private Double porcentajeNodeclarante;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @OneToMany(mappedBy = "idValorRetencion")
    private List<CuentaRetencion> cuentaRetencionList;
    @JoinColumn(name = "id_regimen_nodeclarante", referencedColumnName = "id")
    @ManyToOne
    private Regimen idRegimenNodeclarante;
    @JoinColumn(name = "id_concepto", referencedColumnName = "id")
    @ManyToOne
    private ConceptoRetencion idConcepto;

    public ValorRetencion() {
    }

    public ValorRetencion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getBase() {
        return base;
    }

    public void setBase(Double base) {
        this.base = base;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Double getPorcentajeNodeclarante() {
        return porcentajeNodeclarante;
    }

    public void setPorcentajeNodeclarante(Double porcentajeNodeclarante) {
        this.porcentajeNodeclarante = porcentajeNodeclarante;
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

    @XmlTransient
    public List<CuentaRetencion> getCuentaRetencionList() {
        return cuentaRetencionList;
    }

    public void setCuentaRetencionList(List<CuentaRetencion> cuentaRetencionList) {
        this.cuentaRetencionList = cuentaRetencionList;
    }

    public Regimen getIdRegimenNodeclarante() {
        return idRegimenNodeclarante;
    }

    public void setIdRegimenNodeclarante(Regimen idRegimenNodeclarante) {
        this.idRegimenNodeclarante = idRegimenNodeclarante;
    }

    public ConceptoRetencion getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(ConceptoRetencion idConcepto) {
        this.idConcepto = idConcepto;
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
        if (!(object instanceof ValorRetencion)) {
            return false;
        }
        ValorRetencion other = (ValorRetencion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ValorRetencion[ id=" + id + " ]";
    }
    
}
