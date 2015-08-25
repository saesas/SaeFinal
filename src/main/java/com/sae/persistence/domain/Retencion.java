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
@Table(name = "retencion", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Retencion.findAll", query = "SELECT r FROM Retencion r"),
    @NamedQuery(name = "Retencion.findById", query = "SELECT r FROM Retencion r WHERE r.id = :id"),
    @NamedQuery(name = "Retencion.findByNombre", query = "SELECT r FROM Retencion r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "Retencion.findByEstado", query = "SELECT r FROM Retencion r WHERE r.estado = :estado"),
    @NamedQuery(name = "Retencion.findByFechaRegistro", query = "SELECT r FROM Retencion r WHERE r.fechaRegistro = :fechaRegistro")})
public class Retencion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "idTipoRetencion")
    private List<ConceptoRetencion> conceptoRetencionList;
    @OneToMany(mappedBy = "idRetencion")
    private List<FacturaVentaRetencion> facturaVentaRetencionList;
    @OneToMany(mappedBy = "idRetencion")
    private List<AplicacionTributaria> aplicacionTributariaList;

    public Retencion() {
    }

    public Retencion(Integer id) {
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

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @XmlTransient
    public List<ConceptoRetencion> getConceptoRetencionList() {
        return conceptoRetencionList;
    }

    public void setConceptoRetencionList(List<ConceptoRetencion> conceptoRetencionList) {
        this.conceptoRetencionList = conceptoRetencionList;
    }

    @XmlTransient
    public List<FacturaVentaRetencion> getFacturaVentaRetencionList() {
        return facturaVentaRetencionList;
    }

    public void setFacturaVentaRetencionList(List<FacturaVentaRetencion> facturaVentaRetencionList) {
        this.facturaVentaRetencionList = facturaVentaRetencionList;
    }

    @XmlTransient
    public List<AplicacionTributaria> getAplicacionTributariaList() {
        return aplicacionTributariaList;
    }

    public void setAplicacionTributariaList(List<AplicacionTributaria> aplicacionTributariaList) {
        this.aplicacionTributariaList = aplicacionTributariaList;
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
        if (!(object instanceof Retencion)) {
            return false;
        }
        Retencion other = (Retencion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Retencion[ id=" + id + " ]";
    }
    
}
