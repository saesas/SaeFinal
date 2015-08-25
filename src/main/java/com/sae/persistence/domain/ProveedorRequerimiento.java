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
@Table(name = "proveedor_requerimiento", catalog = "bdsae", schema = "compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProveedorRequerimiento.findAll", query = "SELECT p FROM ProveedorRequerimiento p"),
    @NamedQuery(name = "ProveedorRequerimiento.findById", query = "SELECT p FROM ProveedorRequerimiento p WHERE p.id = :id"),
    @NamedQuery(name = "ProveedorRequerimiento.findByEstado", query = "SELECT p FROM ProveedorRequerimiento p WHERE p.estado = :estado"),
    @NamedQuery(name = "ProveedorRequerimiento.findByIdProveedor", query = "SELECT p FROM ProveedorRequerimiento p WHERE p.idProveedor = :idProveedor"),
    @NamedQuery(name = "ProveedorRequerimiento.findByPrecio", query = "SELECT p FROM ProveedorRequerimiento p WHERE p.precio = :precio"),
    @NamedQuery(name = "ProveedorRequerimiento.findBySeleccionado", query = "SELECT p FROM ProveedorRequerimiento p WHERE p.seleccionado = :seleccionado"),
    @NamedQuery(name = "ProveedorRequerimiento.findByFechaRegistro", query = "SELECT p FROM ProveedorRequerimiento p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ProveedorRequerimiento.findByIdUsuario", query = "SELECT p FROM ProveedorRequerimiento p WHERE p.idUsuario = :idUsuario")})
public class ProveedorRequerimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_proveedor")
    private Integer idProveedor;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio")
    private Double precio;
    @Column(name = "seleccionado")
    private Boolean seleccionado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @OneToMany(mappedBy = "idProveedorRequerimiento")
    private List<OrdenCompraInsumo> ordenCompraInsumoList;
    @JoinColumn(name = "id_requerimiento", referencedColumnName = "id")
    @ManyToOne
    private Requerimiento idRequerimiento;
    @JoinColumn(name = "id_insumo_requerimiento", referencedColumnName = "id")
    @ManyToOne
    private RequerimentoInsumo idInsumoRequerimiento;

    public ProveedorRequerimiento() {
    }

    public ProveedorRequerimiento(Integer id) {
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

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Boolean getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(Boolean seleccionado) {
        this.seleccionado = seleccionado;
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
    public List<OrdenCompraInsumo> getOrdenCompraInsumoList() {
        return ordenCompraInsumoList;
    }

    public void setOrdenCompraInsumoList(List<OrdenCompraInsumo> ordenCompraInsumoList) {
        this.ordenCompraInsumoList = ordenCompraInsumoList;
    }

    public Requerimiento getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(Requerimiento idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public RequerimentoInsumo getIdInsumoRequerimiento() {
        return idInsumoRequerimiento;
    }

    public void setIdInsumoRequerimiento(RequerimentoInsumo idInsumoRequerimiento) {
        this.idInsumoRequerimiento = idInsumoRequerimiento;
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
        if (!(object instanceof ProveedorRequerimiento)) {
            return false;
        }
        ProveedorRequerimiento other = (ProveedorRequerimiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ProveedorRequerimiento[ id=" + id + " ]";
    }
    
}
