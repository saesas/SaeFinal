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
@Table(name = "orden_compra_insumo", catalog = "bdsae", schema = "compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdenCompraInsumo.findAll", query = "SELECT o FROM OrdenCompraInsumo o"),
    @NamedQuery(name = "OrdenCompraInsumo.findById", query = "SELECT o FROM OrdenCompraInsumo o WHERE o.id = :id"),
    @NamedQuery(name = "OrdenCompraInsumo.findByEstado", query = "SELECT o FROM OrdenCompraInsumo o WHERE o.estado = :estado"),
    @NamedQuery(name = "OrdenCompraInsumo.findByFechaRegistro", query = "SELECT o FROM OrdenCompraInsumo o WHERE o.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "OrdenCompraInsumo.findByIdUsuario", query = "SELECT o FROM OrdenCompraInsumo o WHERE o.idUsuario = :idUsuario")})
public class OrdenCompraInsumo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_proveedor_requerimiento", referencedColumnName = "id")
    @ManyToOne
    private ProveedorRequerimiento idProveedorRequerimiento;
    @JoinColumn(name = "id_orden_compra", referencedColumnName = "id")
    @ManyToOne
    private OrdenCompra idOrdenCompra;
    @OneToMany(mappedBy = "idOrdenInsumo")
    private List<InsumoFacturaCompra> insumoFacturaCompraList;

    public OrdenCompraInsumo() {
    }

    public OrdenCompraInsumo(Integer id) {
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

    public ProveedorRequerimiento getIdProveedorRequerimiento() {
        return idProveedorRequerimiento;
    }

    public void setIdProveedorRequerimiento(ProveedorRequerimiento idProveedorRequerimiento) {
        this.idProveedorRequerimiento = idProveedorRequerimiento;
    }

    public OrdenCompra getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(OrdenCompra idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    @XmlTransient
    public List<InsumoFacturaCompra> getInsumoFacturaCompraList() {
        return insumoFacturaCompraList;
    }

    public void setInsumoFacturaCompraList(List<InsumoFacturaCompra> insumoFacturaCompraList) {
        this.insumoFacturaCompraList = insumoFacturaCompraList;
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
        if (!(object instanceof OrdenCompraInsumo)) {
            return false;
        }
        OrdenCompraInsumo other = (OrdenCompraInsumo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.OrdenCompraInsumo[ id=" + id + " ]";
    }
    
}
