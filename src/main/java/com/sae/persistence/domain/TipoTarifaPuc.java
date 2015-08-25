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
@Table(name = "tipo_tarifa_puc", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoTarifaPuc.findAll", query = "SELECT t FROM TipoTarifaPuc t"),
    @NamedQuery(name = "TipoTarifaPuc.findById", query = "SELECT t FROM TipoTarifaPuc t WHERE t.id = :id"),
    @NamedQuery(name = "TipoTarifaPuc.findByEstado", query = "SELECT t FROM TipoTarifaPuc t WHERE t.estado = :estado"),
    @NamedQuery(name = "TipoTarifaPuc.findByFechaInicio", query = "SELECT t FROM TipoTarifaPuc t WHERE t.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "TipoTarifaPuc.findByFechaFin", query = "SELECT t FROM TipoTarifaPuc t WHERE t.fechaFin = :fechaFin"),
    @NamedQuery(name = "TipoTarifaPuc.findByFechaRegistro", query = "SELECT t FROM TipoTarifaPuc t WHERE t.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "TipoTarifaPuc.findByIdUsuario", query = "SELECT t FROM TipoTarifaPuc t WHERE t.idUsuario = :idUsuario")})
public class TipoTarifaPuc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_tipo_iva", referencedColumnName = "id")
    @ManyToOne
    private TipoIva idTipoIva;
    @JoinColumn(name = "id_tarifa_iva", referencedColumnName = "id")
    @ManyToOne
    private TarifaIva idTarifaIva;
    @JoinColumn(name = "id_puc", referencedColumnName = "id")
    @ManyToOne
    private Puc idPuc;
    @OneToMany(mappedBy = "idTipoTarifaIva")
    private List<RegimenTipoTarifaIva> regimenTipoTarifaIvaList;

    public TipoTarifaPuc() {
    }

    public TipoTarifaPuc(Integer id) {
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
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

    public TipoIva getIdTipoIva() {
        return idTipoIva;
    }

    public void setIdTipoIva(TipoIva idTipoIva) {
        this.idTipoIva = idTipoIva;
    }

    public TarifaIva getIdTarifaIva() {
        return idTarifaIva;
    }

    public void setIdTarifaIva(TarifaIva idTarifaIva) {
        this.idTarifaIva = idTarifaIva;
    }

    public Puc getIdPuc() {
        return idPuc;
    }

    public void setIdPuc(Puc idPuc) {
        this.idPuc = idPuc;
    }

    @XmlTransient
    public List<RegimenTipoTarifaIva> getRegimenTipoTarifaIvaList() {
        return regimenTipoTarifaIvaList;
    }

    public void setRegimenTipoTarifaIvaList(List<RegimenTipoTarifaIva> regimenTipoTarifaIvaList) {
        this.regimenTipoTarifaIvaList = regimenTipoTarifaIvaList;
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
        if (!(object instanceof TipoTarifaPuc)) {
            return false;
        }
        TipoTarifaPuc other = (TipoTarifaPuc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.TipoTarifaPuc[ id=" + id + " ]";
    }
    
}
