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
@Table(name = "indicador_convocatoria", catalog = "bdsae", schema = "licitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IndicadorConvocatoria.findAll", query = "SELECT i FROM IndicadorConvocatoria i"),
    @NamedQuery(name = "IndicadorConvocatoria.findById", query = "SELECT i FROM IndicadorConvocatoria i WHERE i.id = :id"),
    @NamedQuery(name = "IndicadorConvocatoria.findByNombre", query = "SELECT i FROM IndicadorConvocatoria i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "IndicadorConvocatoria.findByEstado", query = "SELECT i FROM IndicadorConvocatoria i WHERE i.estado = :estado"),
    @NamedQuery(name = "IndicadorConvocatoria.findByFechaRegistro", query = "SELECT i FROM IndicadorConvocatoria i WHERE i.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "IndicadorConvocatoria.findByIdUsuario", query = "SELECT i FROM IndicadorConvocatoria i WHERE i.idUsuario = :idUsuario")})
public class IndicadorConvocatoria implements Serializable {
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
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @OneToMany(mappedBy = "idIndicador")
    private List<RupEstadoFinancieroConsolidadoConvocatoria> rupEstadoFinancieroConsolidadoConvocatoriaList;
    @OneToMany(mappedBy = "idIndicador")
    private List<RupEstadoFinancieroSocioConvocatoria> rupEstadoFinancieroSocioConvocatoriaList;

    public IndicadorConvocatoria() {
    }

    public IndicadorConvocatoria(Integer id) {
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

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlTransient
    public List<RupEstadoFinancieroConsolidadoConvocatoria> getRupEstadoFinancieroConsolidadoConvocatoriaList() {
        return rupEstadoFinancieroConsolidadoConvocatoriaList;
    }

    public void setRupEstadoFinancieroConsolidadoConvocatoriaList(List<RupEstadoFinancieroConsolidadoConvocatoria> rupEstadoFinancieroConsolidadoConvocatoriaList) {
        this.rupEstadoFinancieroConsolidadoConvocatoriaList = rupEstadoFinancieroConsolidadoConvocatoriaList;
    }

    @XmlTransient
    public List<RupEstadoFinancieroSocioConvocatoria> getRupEstadoFinancieroSocioConvocatoriaList() {
        return rupEstadoFinancieroSocioConvocatoriaList;
    }

    public void setRupEstadoFinancieroSocioConvocatoriaList(List<RupEstadoFinancieroSocioConvocatoria> rupEstadoFinancieroSocioConvocatoriaList) {
        this.rupEstadoFinancieroSocioConvocatoriaList = rupEstadoFinancieroSocioConvocatoriaList;
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
        if (!(object instanceof IndicadorConvocatoria)) {
            return false;
        }
        IndicadorConvocatoria other = (IndicadorConvocatoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.IndicadorConvocatoria[ id=" + id + " ]";
    }
    
}
