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
@Table(name = "requerimiento", catalog = "bdsae", schema = "compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Requerimiento.findAll", query = "SELECT r FROM Requerimiento r"),
    @NamedQuery(name = "Requerimiento.findById", query = "SELECT r FROM Requerimiento r WHERE r.id = :id"),
    @NamedQuery(name = "Requerimiento.findByEstado", query = "SELECT r FROM Requerimiento r WHERE r.estado = :estado"),
    @NamedQuery(name = "Requerimiento.findByIdPresupuesto", query = "SELECT r FROM Requerimiento r WHERE r.idPresupuesto = :idPresupuesto"),
    @NamedQuery(name = "Requerimiento.findByCodigo", query = "SELECT r FROM Requerimiento r WHERE r.codigo = :codigo"),
    @NamedQuery(name = "Requerimiento.findByFechaProyectadaEntrega", query = "SELECT r FROM Requerimiento r WHERE r.fechaProyectadaEntrega = :fechaProyectadaEntrega"),
    @NamedQuery(name = "Requerimiento.findByIdUsuario", query = "SELECT r FROM Requerimiento r WHERE r.idUsuario = :idUsuario"),
    @NamedQuery(name = "Requerimiento.findByFechaRegistro", query = "SELECT r FROM Requerimiento r WHERE r.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Requerimiento.findByIdRazonSocial", query = "SELECT r FROM Requerimiento r WHERE r.idRazonSocial = :idRazonSocial")})
public class Requerimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_presupuesto")
    private Integer idPresupuesto;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "fecha_proyectada_entrega")
    @Temporal(TemporalType.DATE)
    private Date fechaProyectadaEntrega;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id")
    @ManyToOne
    private TipoRequerimiento idTipo;
    @JoinColumn(name = "id_alcance", referencedColumnName = "id")
    @ManyToOne
    private TipoAlcanceRequerimiento idAlcance;
    @OneToMany(mappedBy = "idRequerimiento")
    private List<ProveedorRequerimiento> proveedorRequerimientoList;
    @OneToMany(mappedBy = "idRequerimiento")
    private List<RequerimentoInsumo> requerimentoInsumoList;

    public Requerimiento() {
    }

    public Requerimiento(Integer id) {
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

    public Integer getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(Integer idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFechaProyectadaEntrega() {
        return fechaProyectadaEntrega;
    }

    public void setFechaProyectadaEntrega(Date fechaProyectadaEntrega) {
        this.fechaProyectadaEntrega = fechaProyectadaEntrega;
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

    public TipoRequerimiento getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(TipoRequerimiento idTipo) {
        this.idTipo = idTipo;
    }

    public TipoAlcanceRequerimiento getIdAlcance() {
        return idAlcance;
    }

    public void setIdAlcance(TipoAlcanceRequerimiento idAlcance) {
        this.idAlcance = idAlcance;
    }

    @XmlTransient
    public List<ProveedorRequerimiento> getProveedorRequerimientoList() {
        return proveedorRequerimientoList;
    }

    public void setProveedorRequerimientoList(List<ProveedorRequerimiento> proveedorRequerimientoList) {
        this.proveedorRequerimientoList = proveedorRequerimientoList;
    }

    @XmlTransient
    public List<RequerimentoInsumo> getRequerimentoInsumoList() {
        return requerimentoInsumoList;
    }

    public void setRequerimentoInsumoList(List<RequerimentoInsumo> requerimentoInsumoList) {
        this.requerimentoInsumoList = requerimentoInsumoList;
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
        if (!(object instanceof Requerimiento)) {
            return false;
        }
        Requerimiento other = (Requerimiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Requerimiento[ id=" + id + " ]";
    }
    
}
