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
@Table(name = "valor_tributario", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValorTributario.findAll", query = "SELECT v FROM ValorTributario v"),
    @NamedQuery(name = "ValorTributario.findById", query = "SELECT v FROM ValorTributario v WHERE v.id = :id"),
    @NamedQuery(name = "ValorTributario.findByFechaInicio", query = "SELECT v FROM ValorTributario v WHERE v.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "ValorTributario.findByFechaFin", query = "SELECT v FROM ValorTributario v WHERE v.fechaFin = :fechaFin"),
    @NamedQuery(name = "ValorTributario.findByValorUnitario", query = "SELECT v FROM ValorTributario v WHERE v.valorUnitario = :valorUnitario"),
    @NamedQuery(name = "ValorTributario.findByBase", query = "SELECT v FROM ValorTributario v WHERE v.base = :base"),
    @NamedQuery(name = "ValorTributario.findByEstado", query = "SELECT v FROM ValorTributario v WHERE v.estado = :estado"),
    @NamedQuery(name = "ValorTributario.findByFechaRegistro", query = "SELECT v FROM ValorTributario v WHERE v.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ValorTributario.findByIdUsuario", query = "SELECT v FROM ValorTributario v WHERE v.idUsuario = :idUsuario")})
public class ValorTributario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_unitario")
    private Double valorUnitario;
    @Column(name = "base")
    private Double base;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @OneToMany(mappedBy = "idValorTributario")
    private List<RetencionEmpleado> retencionEmpleadoList;

    public ValorTributario() {
    }

    public ValorTributario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Double getBase() {
        return base;
    }

    public void setBase(Double base) {
        this.base = base;
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

    @XmlTransient
    public List<RetencionEmpleado> getRetencionEmpleadoList() {
        return retencionEmpleadoList;
    }

    public void setRetencionEmpleadoList(List<RetencionEmpleado> retencionEmpleadoList) {
        this.retencionEmpleadoList = retencionEmpleadoList;
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
        if (!(object instanceof ValorTributario)) {
            return false;
        }
        ValorTributario other = (ValorTributario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ValorTributario[ id=" + id + " ]";
    }
    
}
