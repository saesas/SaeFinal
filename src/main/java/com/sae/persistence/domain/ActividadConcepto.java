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
@Table(name = "actividad_concepto", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActividadConcepto.findAll", query = "SELECT a FROM ActividadConcepto a"),
    @NamedQuery(name = "ActividadConcepto.findById", query = "SELECT a FROM ActividadConcepto a WHERE a.id = :id"),
    @NamedQuery(name = "ActividadConcepto.findByEstado", query = "SELECT a FROM ActividadConcepto a WHERE a.estado = :estado"),
    @NamedQuery(name = "ActividadConcepto.findByFechaRegistro", query = "SELECT a FROM ActividadConcepto a WHERE a.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ActividadConcepto.findByIdUsuario", query = "SELECT a FROM ActividadConcepto a WHERE a.idUsuario = :idUsuario"),
    @NamedQuery(name = "ActividadConcepto.findByIdRazonSocial", query = "SELECT a FROM ActividadConcepto a WHERE a.idRazonSocial = :idRazonSocial")})
public class ActividadConcepto implements Serializable {
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
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @JoinColumn(name = "id_concepto", referencedColumnName = "id")
    @ManyToOne
    private ConceptoRetencion idConcepto;
    @JoinColumn(name = "id_actividad_economica", referencedColumnName = "id")
    @ManyToOne
    private ActividadEconomica idActividadEconomica;

    public ActividadConcepto() {
    }

    public ActividadConcepto(Integer id) {
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

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    public ConceptoRetencion getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(ConceptoRetencion idConcepto) {
        this.idConcepto = idConcepto;
    }

    public ActividadEconomica getIdActividadEconomica() {
        return idActividadEconomica;
    }

    public void setIdActividadEconomica(ActividadEconomica idActividadEconomica) {
        this.idActividadEconomica = idActividadEconomica;
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
        if (!(object instanceof ActividadConcepto)) {
            return false;
        }
        ActividadConcepto other = (ActividadConcepto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ActividadConcepto[ id=" + id + " ]";
    }
    
}
