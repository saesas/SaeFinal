/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author SAE2
 */
@Entity
@Table(name = "indicador_consumo_servicio", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IndicadorConsumoServicio.findAll", query = "SELECT i FROM IndicadorConsumoServicio i"),
    @NamedQuery(name = "IndicadorConsumoServicio.findById", query = "SELECT i FROM IndicadorConsumoServicio i WHERE i.id = :id"),
    @NamedQuery(name = "IndicadorConsumoServicio.findByNombre", query = "SELECT i FROM IndicadorConsumoServicio i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "IndicadorConsumoServicio.findBySimbolo", query = "SELECT i FROM IndicadorConsumoServicio i WHERE i.simbolo = :simbolo"),
    @NamedQuery(name = "IndicadorConsumoServicio.findByEstado", query = "SELECT i FROM IndicadorConsumoServicio i WHERE i.estado = :estado")})
public class IndicadorConsumoServicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "simbolo")
    private String simbolo;
    @Column(name = "estado")
    private Boolean estado;
    @OneToMany(mappedBy = "idIndicador")
    private List<ConsumoFacturaServicioPublico> consumoFacturaServicioPublicoList;

    public IndicadorConsumoServicio() {
    }

    public IndicadorConsumoServicio(Integer id) {
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

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<ConsumoFacturaServicioPublico> getConsumoFacturaServicioPublicoList() {
        return consumoFacturaServicioPublicoList;
    }

    public void setConsumoFacturaServicioPublicoList(List<ConsumoFacturaServicioPublico> consumoFacturaServicioPublicoList) {
        this.consumoFacturaServicioPublicoList = consumoFacturaServicioPublicoList;
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
        if (!(object instanceof IndicadorConsumoServicio)) {
            return false;
        }
        IndicadorConsumoServicio other = (IndicadorConsumoServicio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.IndicadorConsumoServicio[ id=" + id + " ]";
    }
    
}
