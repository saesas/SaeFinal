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
@Table(name = "actividad_economica", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActividadEconomica.findAll", query = "SELECT a FROM ActividadEconomica a"),
    @NamedQuery(name = "ActividadEconomica.findById", query = "SELECT a FROM ActividadEconomica a WHERE a.id = :id"),
    @NamedQuery(name = "ActividadEconomica.findByNombre", query = "SELECT a FROM ActividadEconomica a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "ActividadEconomica.findByCodigo", query = "SELECT a FROM ActividadEconomica a WHERE a.codigo = :codigo"),
    @NamedQuery(name = "ActividadEconomica.findByEstado", query = "SELECT a FROM ActividadEconomica a WHERE a.estado = :estado"),
    @NamedQuery(name = "ActividadEconomica.findByFechaRegistro", query = "SELECT a FROM ActividadEconomica a WHERE a.fechaRegistro = :fechaRegistro")})
public class ActividadEconomica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "idActividadEconomica")
    private List<ActividadConcepto> actividadConceptoList;

    public ActividadEconomica() {
    }

    public ActividadEconomica(Integer id) {
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
    public List<ActividadConcepto> getActividadConceptoList() {
        return actividadConceptoList;
    }

    public void setActividadConceptoList(List<ActividadConcepto> actividadConceptoList) {
        this.actividadConceptoList = actividadConceptoList;
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
        if (!(object instanceof ActividadEconomica)) {
            return false;
        }
        ActividadEconomica other = (ActividadEconomica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ActividadEconomica[ id=" + id + " ]";
    }
    
}
