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
@Table(name = "adjunto_factura", catalog = "bdsae", schema = "compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdjuntoFactura.findAll", query = "SELECT a FROM AdjuntoFactura a"),
    @NamedQuery(name = "AdjuntoFactura.findById", query = "SELECT a FROM AdjuntoFactura a WHERE a.id = :id"),
    @NamedQuery(name = "AdjuntoFactura.findByNombre", query = "SELECT a FROM AdjuntoFactura a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "AdjuntoFactura.findByEstado", query = "SELECT a FROM AdjuntoFactura a WHERE a.estado = :estado"),
    @NamedQuery(name = "AdjuntoFactura.findByIdTipoAdjunto", query = "SELECT a FROM AdjuntoFactura a WHERE a.idTipoAdjunto = :idTipoAdjunto"),
    @NamedQuery(name = "AdjuntoFactura.findByFechaRegistro", query = "SELECT a FROM AdjuntoFactura a WHERE a.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "AdjuntoFactura.findByIdUsuario", query = "SELECT a FROM AdjuntoFactura a WHERE a.idUsuario = :idUsuario")})
public class AdjuntoFactura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_tipo_adjunto")
    private Integer idTipoAdjunto;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_factura", referencedColumnName = "id")
    @ManyToOne
    private FacturaCompra idFactura;

    public AdjuntoFactura() {
    }

    public AdjuntoFactura(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getIdTipoAdjunto() {
        return idTipoAdjunto;
    }

    public void setIdTipoAdjunto(Integer idTipoAdjunto) {
        this.idTipoAdjunto = idTipoAdjunto;
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

    public FacturaCompra getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(FacturaCompra idFactura) {
        this.idFactura = idFactura;
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
        if (!(object instanceof AdjuntoFactura)) {
            return false;
        }
        AdjuntoFactura other = (AdjuntoFactura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.AdjuntoFactura[ id=" + id + " ]";
    }
    
}
