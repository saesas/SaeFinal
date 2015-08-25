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
@Table(name = "estado_orden_compra", catalog = "bdsae", schema = "compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoOrdenCompra.findAll", query = "SELECT e FROM EstadoOrdenCompra e"),
    @NamedQuery(name = "EstadoOrdenCompra.findById", query = "SELECT e FROM EstadoOrdenCompra e WHERE e.id = :id"),
    @NamedQuery(name = "EstadoOrdenCompra.findByEstado", query = "SELECT e FROM EstadoOrdenCompra e WHERE e.estado = :estado"),
    @NamedQuery(name = "EstadoOrdenCompra.findByDescripcion", query = "SELECT e FROM EstadoOrdenCompra e WHERE e.descripcion = :descripcion"),
    @NamedQuery(name = "EstadoOrdenCompra.findByIdUsuario", query = "SELECT e FROM EstadoOrdenCompra e WHERE e.idUsuario = :idUsuario"),
    @NamedQuery(name = "EstadoOrdenCompra.findByFechaRegistro", query = "SELECT e FROM EstadoOrdenCompra e WHERE e.fechaRegistro = :fechaRegistro")})
public class EstadoOrdenCompra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @JoinColumn(name = "id_estado_orden_compra", referencedColumnName = "id")
    @ManyToOne
    private TipoEstadoOrdenCompra idEstadoOrdenCompra;
    @JoinColumn(name = "id_orden_compra", referencedColumnName = "id")
    @ManyToOne
    private OrdenCompra idOrdenCompra;

    public EstadoOrdenCompra() {
    }

    public EstadoOrdenCompra(Integer id) {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public TipoEstadoOrdenCompra getIdEstadoOrdenCompra() {
        return idEstadoOrdenCompra;
    }

    public void setIdEstadoOrdenCompra(TipoEstadoOrdenCompra idEstadoOrdenCompra) {
        this.idEstadoOrdenCompra = idEstadoOrdenCompra;
    }

    public OrdenCompra getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(OrdenCompra idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
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
        if (!(object instanceof EstadoOrdenCompra)) {
            return false;
        }
        EstadoOrdenCompra other = (EstadoOrdenCompra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.EstadoOrdenCompra[ id=" + id + " ]";
    }
    
}
