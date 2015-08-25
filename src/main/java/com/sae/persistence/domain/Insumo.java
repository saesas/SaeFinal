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
@Table(name = "insumo", catalog = "bdsae", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Insumo.findAll", query = "SELECT i FROM Insumo i"),
    @NamedQuery(name = "Insumo.findById", query = "SELECT i FROM Insumo i WHERE i.id = :id"),
    @NamedQuery(name = "Insumo.findByNombre", query = "SELECT i FROM Insumo i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "Insumo.findByEstado", query = "SELECT i FROM Insumo i WHERE i.estado = :estado"),
    @NamedQuery(name = "Insumo.findByReferencia", query = "SELECT i FROM Insumo i WHERE i.referencia = :referencia"),
    @NamedQuery(name = "Insumo.findByEspecificacion", query = "SELECT i FROM Insumo i WHERE i.especificacion = :especificacion"),
    @NamedQuery(name = "Insumo.findByIdTarifaIva", query = "SELECT i FROM Insumo i WHERE i.idTarifaIva = :idTarifaIva"),
    @NamedQuery(name = "Insumo.findByFechaRegistro", query = "SELECT i FROM Insumo i WHERE i.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Insumo.findByIdUsuario", query = "SELECT i FROM Insumo i WHERE i.idUsuario = :idUsuario")})
public class Insumo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "referencia")
    private String referencia;
    @Column(name = "especificacion")
    private String especificacion;
    @Column(name = "id_tarifa_iva")
    private Integer idTarifaIva;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_tipo_insumo", referencedColumnName = "id")
    @ManyToOne
    private TipoInsumo idTipoInsumo;
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    @ManyToOne
    private CategoriaInsumo idCategoria;

    public Insumo() {
    }

    public Insumo(Integer id) {
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

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getEspecificacion() {
        return especificacion;
    }

    public void setEspecificacion(String especificacion) {
        this.especificacion = especificacion;
    }

    public Integer getIdTarifaIva() {
        return idTarifaIva;
    }

    public void setIdTarifaIva(Integer idTarifaIva) {
        this.idTarifaIva = idTarifaIva;
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

    public TipoInsumo getIdTipoInsumo() {
        return idTipoInsumo;
    }

    public void setIdTipoInsumo(TipoInsumo idTipoInsumo) {
        this.idTipoInsumo = idTipoInsumo;
    }

    public CategoriaInsumo getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(CategoriaInsumo idCategoria) {
        this.idCategoria = idCategoria;
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
        if (!(object instanceof Insumo)) {
            return false;
        }
        Insumo other = (Insumo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Insumo[ id=" + id + " ]";
    }
    
}
