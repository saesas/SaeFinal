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
@Table(name = "actividad_insumo", catalog = "bdsae", schema = "tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActividadInsumo.findAll", query = "SELECT a FROM ActividadInsumo a"),
    @NamedQuery(name = "ActividadInsumo.findById", query = "SELECT a FROM ActividadInsumo a WHERE a.id = :id"),
    @NamedQuery(name = "ActividadInsumo.findByIdInsumo", query = "SELECT a FROM ActividadInsumo a WHERE a.idInsumo = :idInsumo"),
    @NamedQuery(name = "ActividadInsumo.findByCantidad", query = "SELECT a FROM ActividadInsumo a WHERE a.cantidad = :cantidad"),
    @NamedQuery(name = "ActividadInsumo.findByValorTotal", query = "SELECT a FROM ActividadInsumo a WHERE a.valorTotal = :valorTotal"),
    @NamedQuery(name = "ActividadInsumo.findByTiempoRequerido", query = "SELECT a FROM ActividadInsumo a WHERE a.tiempoRequerido = :tiempoRequerido"),
    @NamedQuery(name = "ActividadInsumo.findByIdMoneda", query = "SELECT a FROM ActividadInsumo a WHERE a.idMoneda = :idMoneda"),
    @NamedQuery(name = "ActividadInsumo.findByFechaRegistro", query = "SELECT a FROM ActividadInsumo a WHERE a.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ActividadInsumo.findByIdUsuario", query = "SELECT a FROM ActividadInsumo a WHERE a.idUsuario = :idUsuario")})
public class ActividadInsumo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_insumo")
    private Integer idInsumo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad")
    private Double cantidad;
    @Column(name = "valor_total")
    private Double valorTotal;
    @Column(name = "tiempo_requerido")
    private Double tiempoRequerido;
    @Column(name = "id_moneda")
    private Integer idMoneda;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_actividad_presupuesto", referencedColumnName = "id")
    @ManyToOne
    private ActividadPresupuesto idActividadPresupuesto;

    public ActividadInsumo() {
    }

    public ActividadInsumo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(Integer idInsumo) {
        this.idInsumo = idInsumo;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getTiempoRequerido() {
        return tiempoRequerido;
    }

    public void setTiempoRequerido(Double tiempoRequerido) {
        this.tiempoRequerido = tiempoRequerido;
    }

    public Integer getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(Integer idMoneda) {
        this.idMoneda = idMoneda;
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

    public ActividadPresupuesto getIdActividadPresupuesto() {
        return idActividadPresupuesto;
    }

    public void setIdActividadPresupuesto(ActividadPresupuesto idActividadPresupuesto) {
        this.idActividadPresupuesto = idActividadPresupuesto;
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
        if (!(object instanceof ActividadInsumo)) {
            return false;
        }
        ActividadInsumo other = (ActividadInsumo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ActividadInsumo[ id=" + id + " ]";
    }
    
}
