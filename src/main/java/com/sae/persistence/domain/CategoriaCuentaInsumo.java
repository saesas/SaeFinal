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
@Table(name = "categoria_cuenta_insumo", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoriaCuentaInsumo.findAll", query = "SELECT c FROM CategoriaCuentaInsumo c"),
    @NamedQuery(name = "CategoriaCuentaInsumo.findById", query = "SELECT c FROM CategoriaCuentaInsumo c WHERE c.id = :id"),
    @NamedQuery(name = "CategoriaCuentaInsumo.findByIdCategoria", query = "SELECT c FROM CategoriaCuentaInsumo c WHERE c.idCategoria = :idCategoria"),
    @NamedQuery(name = "CategoriaCuentaInsumo.findByEstado", query = "SELECT c FROM CategoriaCuentaInsumo c WHERE c.estado = :estado"),
    @NamedQuery(name = "CategoriaCuentaInsumo.findByIdUsuario", query = "SELECT c FROM CategoriaCuentaInsumo c WHERE c.idUsuario = :idUsuario"),
    @NamedQuery(name = "CategoriaCuentaInsumo.findByFechaRegistro", query = "SELECT c FROM CategoriaCuentaInsumo c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "CategoriaCuentaInsumo.findByIdRazonSocial", query = "SELECT c FROM CategoriaCuentaInsumo c WHERE c.idRazonSocial = :idRazonSocial")})
public class CategoriaCuentaInsumo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_categoria")
    private Integer idCategoria;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @JoinColumn(name = "id_puc", referencedColumnName = "id")
    @ManyToOne
    private Puc idPuc;

    public CategoriaCuentaInsumo() {
    }

    public CategoriaCuentaInsumo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
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

    public Puc getIdPuc() {
        return idPuc;
    }

    public void setIdPuc(Puc idPuc) {
        this.idPuc = idPuc;
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
        if (!(object instanceof CategoriaCuentaInsumo)) {
            return false;
        }
        CategoriaCuentaInsumo other = (CategoriaCuentaInsumo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.CategoriaCuentaInsumo[ id=" + id + " ]";
    }
    
}
