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
@Table(name = "periodo_pago", catalog = "bdsae", schema = "talento_humano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PeriodoPago.findAll", query = "SELECT p FROM PeriodoPago p"),
    @NamedQuery(name = "PeriodoPago.findById", query = "SELECT p FROM PeriodoPago p WHERE p.id = :id"),
    @NamedQuery(name = "PeriodoPago.findByNombre", query = "SELECT p FROM PeriodoPago p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "PeriodoPago.findByEstado", query = "SELECT p FROM PeriodoPago p WHERE p.estado = :estado"),
    @NamedQuery(name = "PeriodoPago.findByFechaRegistro", query = "SELECT p FROM PeriodoPago p WHERE p.fechaRegistro = :fechaRegistro")})
public class PeriodoPago implements Serializable {
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
    @OneToMany(mappedBy = "idPeriodo")
    private List<GrupoNominal> grupoNominalList;

    public PeriodoPago() {
    }

    public PeriodoPago(Integer id) {
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
    public List<GrupoNominal> getGrupoNominalList() {
        return grupoNominalList;
    }

    public void setGrupoNominalList(List<GrupoNominal> grupoNominalList) {
        this.grupoNominalList = grupoNominalList;
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
        if (!(object instanceof PeriodoPago)) {
            return false;
        }
        PeriodoPago other = (PeriodoPago) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.PeriodoPago[ id=" + id + " ]";
    }
    
}
