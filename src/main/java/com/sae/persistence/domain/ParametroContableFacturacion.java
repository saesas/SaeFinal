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
@Table(name = "parametro_contable_facturacion", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametroContableFacturacion.findAll", query = "SELECT p FROM ParametroContableFacturacion p"),
    @NamedQuery(name = "ParametroContableFacturacion.findById", query = "SELECT p FROM ParametroContableFacturacion p WHERE p.id = :id"),
    @NamedQuery(name = "ParametroContableFacturacion.findByTarifaIva", query = "SELECT p FROM ParametroContableFacturacion p WHERE p.tarifaIva = :tarifaIva"),
    @NamedQuery(name = "ParametroContableFacturacion.findByRetencion", query = "SELECT p FROM ParametroContableFacturacion p WHERE p.retencion = :retencion"),
    @NamedQuery(name = "ParametroContableFacturacion.findByEstado", query = "SELECT p FROM ParametroContableFacturacion p WHERE p.estado = :estado"),
    @NamedQuery(name = "ParametroContableFacturacion.findByFechaRegistro", query = "SELECT p FROM ParametroContableFacturacion p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ParametroContableFacturacion.findByIdUsuario", query = "SELECT p FROM ParametroContableFacturacion p WHERE p.idUsuario = :idUsuario"),
    @NamedQuery(name = "ParametroContableFacturacion.findByIdRazonSocial", query = "SELECT p FROM ParametroContableFacturacion p WHERE p.idRazonSocial = :idRazonSocial")})
public class ParametroContableFacturacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "tarifa_iva")
    private Boolean tarifaIva;
    @Column(name = "retencion")
    private Boolean retencion;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @JoinColumn(name = "id_puc", referencedColumnName = "id")
    @ManyToOne
    private Puc idPuc;
    @OneToMany(mappedBy = "idParametroFacturacion")
    private List<ParametroFacturacionTipoProyecto> parametroFacturacionTipoProyectoList;

    public ParametroContableFacturacion() {
    }

    public ParametroContableFacturacion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getTarifaIva() {
        return tarifaIva;
    }

    public void setTarifaIva(Boolean tarifaIva) {
        this.tarifaIva = tarifaIva;
    }

    public Boolean getRetencion() {
        return retencion;
    }

    public void setRetencion(Boolean retencion) {
        this.retencion = retencion;
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

    @XmlTransient
    public List<ParametroFacturacionTipoProyecto> getParametroFacturacionTipoProyectoList() {
        return parametroFacturacionTipoProyectoList;
    }

    public void setParametroFacturacionTipoProyectoList(List<ParametroFacturacionTipoProyecto> parametroFacturacionTipoProyectoList) {
        this.parametroFacturacionTipoProyectoList = parametroFacturacionTipoProyectoList;
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
        if (!(object instanceof ParametroContableFacturacion)) {
            return false;
        }
        ParametroContableFacturacion other = (ParametroContableFacturacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ParametroContableFacturacion[ id=" + id + " ]";
    }
    
}
