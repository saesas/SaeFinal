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
@Table(name = "etapa_proceso", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EtapaProceso.findAll", query = "SELECT e FROM EtapaProceso e"),
    @NamedQuery(name = "EtapaProceso.findById", query = "SELECT e FROM EtapaProceso e WHERE e.id = :id"),
    @NamedQuery(name = "EtapaProceso.findByNombre", query = "SELECT e FROM EtapaProceso e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "EtapaProceso.findByEstado", query = "SELECT e FROM EtapaProceso e WHERE e.estado = :estado"),
    @NamedQuery(name = "EtapaProceso.findByFechaRegistro", query = "SELECT e FROM EtapaProceso e WHERE e.fechaRegistro = :fechaRegistro")})
public class EtapaProceso implements Serializable {
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
    @JoinColumn(name = "id_instancia", referencedColumnName = "id")
    @ManyToOne
    private Clase idInstancia;
    @OneToMany(mappedBy = "idEtapa")
    private List<SeguimientoProceso> seguimientoProcesoList;

    public EtapaProceso() {
    }

    public EtapaProceso(Integer id) {
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

    public Clase getIdInstancia() {
        return idInstancia;
    }

    public void setIdInstancia(Clase idInstancia) {
        this.idInstancia = idInstancia;
    }

    @XmlTransient
    public List<SeguimientoProceso> getSeguimientoProcesoList() {
        return seguimientoProcesoList;
    }

    public void setSeguimientoProcesoList(List<SeguimientoProceso> seguimientoProcesoList) {
        this.seguimientoProcesoList = seguimientoProcesoList;
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
        if (!(object instanceof EtapaProceso)) {
            return false;
        }
        EtapaProceso other = (EtapaProceso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.EtapaProceso[ id=" + id + " ]";
    }
    
}
