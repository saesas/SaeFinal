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
@Table(name = "grupo_servicios", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoServicios.findAll", query = "SELECT g FROM GrupoServicios g"),
    @NamedQuery(name = "GrupoServicios.findById", query = "SELECT g FROM GrupoServicios g WHERE g.id = :id"),
    @NamedQuery(name = "GrupoServicios.findByNombre", query = "SELECT g FROM GrupoServicios g WHERE g.nombre = :nombre"),
    @NamedQuery(name = "GrupoServicios.findByEstado", query = "SELECT g FROM GrupoServicios g WHERE g.estado = :estado"),
    @NamedQuery(name = "GrupoServicios.findByFechaRegistro", query = "SELECT g FROM GrupoServicios g WHERE g.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "GrupoServicios.findByIdUsuario", query = "SELECT g FROM GrupoServicios g WHERE g.idUsuario = :idUsuario"),
    @NamedQuery(name = "GrupoServicios.findByIdRazonSocial", query = "SELECT g FROM GrupoServicios g WHERE g.idRazonSocial = :idRazonSocial")})
public class GrupoServicios implements Serializable {
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
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @OneToMany(mappedBy = "grupo")
    private List<GrupoServicioConceptoRetencion> grupoServicioConceptoRetencionList;
    @JoinColumn(name = "id_puc_gastos_administrativos", referencedColumnName = "id")
    @ManyToOne
    private Puc idPucGastosAdministrativos;
    @JoinColumn(name = "id_puc_costos_produccion", referencedColumnName = "id")
    @ManyToOne
    private Puc idPucCostosProduccion;
    @JoinColumn(name = "id_concepto_retecree", referencedColumnName = "id")
    @ManyToOne
    private ConceptoRetencion idConceptoRetecree;
    @JoinColumn(name = "id_concepto_retefuente", referencedColumnName = "id")
    @ManyToOne
    private ConceptoRetencion idConceptoRetefuente;
    @OneToMany(mappedBy = "idGrupo")
    private List<GrupoAsociadoServicio> grupoAsociadoServicioList;

    public GrupoServicios() {
    }

    public GrupoServicios(Integer id) {
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

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    @XmlTransient
    public List<GrupoServicioConceptoRetencion> getGrupoServicioConceptoRetencionList() {
        return grupoServicioConceptoRetencionList;
    }

    public void setGrupoServicioConceptoRetencionList(List<GrupoServicioConceptoRetencion> grupoServicioConceptoRetencionList) {
        this.grupoServicioConceptoRetencionList = grupoServicioConceptoRetencionList;
    }

    public Puc getIdPucGastosAdministrativos() {
        return idPucGastosAdministrativos;
    }

    public void setIdPucGastosAdministrativos(Puc idPucGastosAdministrativos) {
        this.idPucGastosAdministrativos = idPucGastosAdministrativos;
    }

    public Puc getIdPucCostosProduccion() {
        return idPucCostosProduccion;
    }

    public void setIdPucCostosProduccion(Puc idPucCostosProduccion) {
        this.idPucCostosProduccion = idPucCostosProduccion;
    }

    public ConceptoRetencion getIdConceptoRetecree() {
        return idConceptoRetecree;
    }

    public void setIdConceptoRetecree(ConceptoRetencion idConceptoRetecree) {
        this.idConceptoRetecree = idConceptoRetecree;
    }

    public ConceptoRetencion getIdConceptoRetefuente() {
        return idConceptoRetefuente;
    }

    public void setIdConceptoRetefuente(ConceptoRetencion idConceptoRetefuente) {
        this.idConceptoRetefuente = idConceptoRetefuente;
    }

    @XmlTransient
    public List<GrupoAsociadoServicio> getGrupoAsociadoServicioList() {
        return grupoAsociadoServicioList;
    }

    public void setGrupoAsociadoServicioList(List<GrupoAsociadoServicio> grupoAsociadoServicioList) {
        this.grupoAsociadoServicioList = grupoAsociadoServicioList;
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
        if (!(object instanceof GrupoServicios)) {
            return false;
        }
        GrupoServicios other = (GrupoServicios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.GrupoServicios[ id=" + id + " ]";
    }
    
}
