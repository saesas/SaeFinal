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
@Table(name = "requerimento_insumo", catalog = "bdsae", schema = "compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RequerimentoInsumo.findAll", query = "SELECT r FROM RequerimentoInsumo r"),
    @NamedQuery(name = "RequerimentoInsumo.findById", query = "SELECT r FROM RequerimentoInsumo r WHERE r.id = :id"),
    @NamedQuery(name = "RequerimentoInsumo.findByEstado", query = "SELECT r FROM RequerimentoInsumo r WHERE r.estado = :estado"),
    @NamedQuery(name = "RequerimentoInsumo.findByIdInsumo", query = "SELECT r FROM RequerimentoInsumo r WHERE r.idInsumo = :idInsumo"),
    @NamedQuery(name = "RequerimentoInsumo.findByIdActividad", query = "SELECT r FROM RequerimentoInsumo r WHERE r.idActividad = :idActividad"),
    @NamedQuery(name = "RequerimentoInsumo.findByDiferenciaCantidad", query = "SELECT r FROM RequerimentoInsumo r WHERE r.diferenciaCantidad = :diferenciaCantidad"),
    @NamedQuery(name = "RequerimentoInsumo.findByCantidad", query = "SELECT r FROM RequerimentoInsumo r WHERE r.cantidad = :cantidad"),
    @NamedQuery(name = "RequerimentoInsumo.findByIdItemAiu", query = "SELECT r FROM RequerimentoInsumo r WHERE r.idItemAiu = :idItemAiu"),
    @NamedQuery(name = "RequerimentoInsumo.findByIdUnidad", query = "SELECT r FROM RequerimentoInsumo r WHERE r.idUnidad = :idUnidad"),
    @NamedQuery(name = "RequerimentoInsumo.findByFechaRegistro", query = "SELECT r FROM RequerimentoInsumo r WHERE r.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "RequerimentoInsumo.findByIdUsuario", query = "SELECT r FROM RequerimentoInsumo r WHERE r.idUsuario = :idUsuario")})
public class RequerimentoInsumo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_insumo")
    private Integer idInsumo;
    @Column(name = "id_actividad")
    private Integer idActividad;
    @Column(name = "diferencia_cantidad")
    private Integer diferenciaCantidad;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "id_item_aiu")
    private Integer idItemAiu;
    @Column(name = "id_unidad")
    private Integer idUnidad;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @OneToMany(mappedBy = "idInsumoRequerimiento")
    private List<ProveedorRequerimiento> proveedorRequerimientoList;
    @JoinColumn(name = "id_requerimiento", referencedColumnName = "id")
    @ManyToOne
    private Requerimiento idRequerimiento;

    public RequerimentoInsumo() {
    }

    public RequerimentoInsumo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(Integer idInsumo) {
        this.idInsumo = idInsumo;
    }

    public Integer getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Integer idActividad) {
        this.idActividad = idActividad;
    }

    public Integer getDiferenciaCantidad() {
        return diferenciaCantidad;
    }

    public void setDiferenciaCantidad(Integer diferenciaCantidad) {
        this.diferenciaCantidad = diferenciaCantidad;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getIdItemAiu() {
        return idItemAiu;
    }

    public void setIdItemAiu(Integer idItemAiu) {
        this.idItemAiu = idItemAiu;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
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
    public List<ProveedorRequerimiento> getProveedorRequerimientoList() {
        return proveedorRequerimientoList;
    }

    public void setProveedorRequerimientoList(List<ProveedorRequerimiento> proveedorRequerimientoList) {
        this.proveedorRequerimientoList = proveedorRequerimientoList;
    }

    public Requerimiento getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(Requerimiento idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
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
        if (!(object instanceof RequerimentoInsumo)) {
            return false;
        }
        RequerimentoInsumo other = (RequerimentoInsumo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.RequerimentoInsumo[ id=" + id + " ]";
    }
    
}
