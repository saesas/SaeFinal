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
@Table(name = "clase", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clase.findAll", query = "SELECT c FROM Clase c"),
    @NamedQuery(name = "Clase.findById", query = "SELECT c FROM Clase c WHERE c.id = :id"),
    @NamedQuery(name = "Clase.findByNombre", query = "SELECT c FROM Clase c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Clase.findByEstado", query = "SELECT c FROM Clase c WHERE c.estado = :estado"),
    @NamedQuery(name = "Clase.findByFechaRegistro", query = "SELECT c FROM Clase c WHERE c.fechaRegistro = :fechaRegistro")})
public class Clase implements Serializable {
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
    @OneToMany(mappedBy = "idInstancia")
    private List<Recurso> recursoList;
    @OneToMany(mappedBy = "idAreaDerecho")
    private List<Tema> temaList;
    @OneToMany(mappedBy = "idInstancia")
    private List<EtapaProceso> etapaProcesoList;
    @OneToMany(mappedBy = "idProcedimientoJuridico")
    private List<ClaseIncidente> claseIncidenteList;
    @OneToMany(mappedBy = "idInstancia")
    private List<SeguimientoProceso> seguimientoProcesoList;
    @OneToMany(mappedBy = "idClaseNormatividad")
    private List<Normatividad> normatividadList;
    @OneToMany(mappedBy = "idAreaDerecho")
    private List<Normatividad> normatividadList1;
    @OneToMany(mappedBy = "idProcedimientoJuridico")
    private List<TipoCuantia> tipoCuantiaList;
    @OneToMany(mappedBy = "idClase")
    private List<Proceso> procesoList;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id")
    @ManyToOne
    private TipoClase idTipo;

    public Clase() {
    }

    public Clase(Integer id) {
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
    public List<Recurso> getRecursoList() {
        return recursoList;
    }

    public void setRecursoList(List<Recurso> recursoList) {
        this.recursoList = recursoList;
    }

    @XmlTransient
    public List<Tema> getTemaList() {
        return temaList;
    }

    public void setTemaList(List<Tema> temaList) {
        this.temaList = temaList;
    }

    @XmlTransient
    public List<EtapaProceso> getEtapaProcesoList() {
        return etapaProcesoList;
    }

    public void setEtapaProcesoList(List<EtapaProceso> etapaProcesoList) {
        this.etapaProcesoList = etapaProcesoList;
    }

    @XmlTransient
    public List<ClaseIncidente> getClaseIncidenteList() {
        return claseIncidenteList;
    }

    public void setClaseIncidenteList(List<ClaseIncidente> claseIncidenteList) {
        this.claseIncidenteList = claseIncidenteList;
    }

    @XmlTransient
    public List<SeguimientoProceso> getSeguimientoProcesoList() {
        return seguimientoProcesoList;
    }

    public void setSeguimientoProcesoList(List<SeguimientoProceso> seguimientoProcesoList) {
        this.seguimientoProcesoList = seguimientoProcesoList;
    }

    @XmlTransient
    public List<Normatividad> getNormatividadList() {
        return normatividadList;
    }

    public void setNormatividadList(List<Normatividad> normatividadList) {
        this.normatividadList = normatividadList;
    }

    @XmlTransient
    public List<Normatividad> getNormatividadList1() {
        return normatividadList1;
    }

    public void setNormatividadList1(List<Normatividad> normatividadList1) {
        this.normatividadList1 = normatividadList1;
    }

    @XmlTransient
    public List<TipoCuantia> getTipoCuantiaList() {
        return tipoCuantiaList;
    }

    public void setTipoCuantiaList(List<TipoCuantia> tipoCuantiaList) {
        this.tipoCuantiaList = tipoCuantiaList;
    }

    @XmlTransient
    public List<Proceso> getProcesoList() {
        return procesoList;
    }

    public void setProcesoList(List<Proceso> procesoList) {
        this.procesoList = procesoList;
    }

    public TipoClase getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(TipoClase idTipo) {
        this.idTipo = idTipo;
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
        if (!(object instanceof Clase)) {
            return false;
        }
        Clase other = (Clase) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Clase[ id=" + id + " ]";
    }
    
}
