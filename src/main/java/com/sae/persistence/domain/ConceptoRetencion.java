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
@Table(name = "concepto_retencion", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConceptoRetencion.findAll", query = "SELECT c FROM ConceptoRetencion c"),
    @NamedQuery(name = "ConceptoRetencion.findById", query = "SELECT c FROM ConceptoRetencion c WHERE c.id = :id"),
    @NamedQuery(name = "ConceptoRetencion.findByNombre", query = "SELECT c FROM ConceptoRetencion c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ConceptoRetencion.findByEstado", query = "SELECT c FROM ConceptoRetencion c WHERE c.estado = :estado"),
    @NamedQuery(name = "ConceptoRetencion.findByIdMunicipio", query = "SELECT c FROM ConceptoRetencion c WHERE c.idMunicipio = :idMunicipio"),
    @NamedQuery(name = "ConceptoRetencion.findByIdPais", query = "SELECT c FROM ConceptoRetencion c WHERE c.idPais = :idPais"),
    @NamedQuery(name = "ConceptoRetencion.findByIdDepartamento", query = "SELECT c FROM ConceptoRetencion c WHERE c.idDepartamento = :idDepartamento"),
    @NamedQuery(name = "ConceptoRetencion.findByFechaRegistro", query = "SELECT c FROM ConceptoRetencion c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ConceptoRetencion.findByIdUsuario", query = "SELECT c FROM ConceptoRetencion c WHERE c.idUsuario = :idUsuario")})
public class ConceptoRetencion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_municipio")
    private Integer idMunicipio;
    @Column(name = "id_pais")
    private Integer idPais;
    @Column(name = "id_departamento")
    private Integer idDepartamento;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_tipo_retencion", referencedColumnName = "id")
    @ManyToOne
    private Retencion idTipoRetencion;
    @JoinColumn(name = "id_aplicacion_concepto", referencedColumnName = "id")
    @ManyToOne
    private AplicacionConcepto idAplicacionConcepto;
    @OneToMany(mappedBy = "idConcepto")
    private List<ValorRetencion> valorRetencionList;
    @OneToMany(mappedBy = "idConceptoRetecree")
    private List<GrupoServicios> grupoServiciosList;
    @OneToMany(mappedBy = "idConceptoRetefuente")
    private List<GrupoServicios> grupoServiciosList1;
    @OneToMany(mappedBy = "idConcepto")
    private List<ActividadConcepto> actividadConceptoList;

    public ConceptoRetencion() {
    }

    public ConceptoRetencion(Integer id) {
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

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public Integer getIdPais() {
        return idPais;
    }

    public void setIdPais(Integer idPais) {
        this.idPais = idPais;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
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

    public Retencion getIdTipoRetencion() {
        return idTipoRetencion;
    }

    public void setIdTipoRetencion(Retencion idTipoRetencion) {
        this.idTipoRetencion = idTipoRetencion;
    }

    public AplicacionConcepto getIdAplicacionConcepto() {
        return idAplicacionConcepto;
    }

    public void setIdAplicacionConcepto(AplicacionConcepto idAplicacionConcepto) {
        this.idAplicacionConcepto = idAplicacionConcepto;
    }

    @XmlTransient
    public List<ValorRetencion> getValorRetencionList() {
        return valorRetencionList;
    }

    public void setValorRetencionList(List<ValorRetencion> valorRetencionList) {
        this.valorRetencionList = valorRetencionList;
    }

    @XmlTransient
    public List<GrupoServicios> getGrupoServiciosList() {
        return grupoServiciosList;
    }

    public void setGrupoServiciosList(List<GrupoServicios> grupoServiciosList) {
        this.grupoServiciosList = grupoServiciosList;
    }

    @XmlTransient
    public List<GrupoServicios> getGrupoServiciosList1() {
        return grupoServiciosList1;
    }

    public void setGrupoServiciosList1(List<GrupoServicios> grupoServiciosList1) {
        this.grupoServiciosList1 = grupoServiciosList1;
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
        if (!(object instanceof ConceptoRetencion)) {
            return false;
        }
        ConceptoRetencion other = (ConceptoRetencion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ConceptoRetencion[ id=" + id + " ]";
    }
    
}
