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
@Table(name = "seguimiento_normatividad", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SeguimientoNormatividad.findAll", query = "SELECT s FROM SeguimientoNormatividad s"),
    @NamedQuery(name = "SeguimientoNormatividad.findById", query = "SELECT s FROM SeguimientoNormatividad s WHERE s.id = :id"),
    @NamedQuery(name = "SeguimientoNormatividad.findByFechaRevision", query = "SELECT s FROM SeguimientoNormatividad s WHERE s.fechaRevision = :fechaRevision"),
    @NamedQuery(name = "SeguimientoNormatividad.findByMedioConsulta", query = "SELECT s FROM SeguimientoNormatividad s WHERE s.medioConsulta = :medioConsulta"),
    @NamedQuery(name = "SeguimientoNormatividad.findByDescripcion", query = "SELECT s FROM SeguimientoNormatividad s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "SeguimientoNormatividad.findByDiarioOficial", query = "SELECT s FROM SeguimientoNormatividad s WHERE s.diarioOficial = :diarioOficial"),
    @NamedQuery(name = "SeguimientoNormatividad.findByUrl", query = "SELECT s FROM SeguimientoNormatividad s WHERE s.url = :url"),
    @NamedQuery(name = "SeguimientoNormatividad.findByCambio", query = "SELECT s FROM SeguimientoNormatividad s WHERE s.cambio = :cambio"),
    @NamedQuery(name = "SeguimientoNormatividad.findByFechaCambio", query = "SELECT s FROM SeguimientoNormatividad s WHERE s.fechaCambio = :fechaCambio"),
    @NamedQuery(name = "SeguimientoNormatividad.findByDescripcionAnalisis", query = "SELECT s FROM SeguimientoNormatividad s WHERE s.descripcionAnalisis = :descripcionAnalisis"),
    @NamedQuery(name = "SeguimientoNormatividad.findByEstado", query = "SELECT s FROM SeguimientoNormatividad s WHERE s.estado = :estado"),
    @NamedQuery(name = "SeguimientoNormatividad.findByFechaRegistro", query = "SELECT s FROM SeguimientoNormatividad s WHERE s.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "SeguimientoNormatividad.findByIdUsuario", query = "SELECT s FROM SeguimientoNormatividad s WHERE s.idUsuario = :idUsuario")})
public class SeguimientoNormatividad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha_revision")
    @Temporal(TemporalType.DATE)
    private Date fechaRevision;
    @Column(name = "medio_consulta")
    private String medioConsulta;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "diario_oficial")
    private String diarioOficial;
    @Column(name = "url")
    private String url;
    @Column(name = "cambio")
    private Boolean cambio;
    @Column(name = "fecha_cambio")
    @Temporal(TemporalType.DATE)
    private Date fechaCambio;
    @Column(name = "descripcion_analisis")
    private String descripcionAnalisis;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @OneToMany(mappedBy = "idSeguimiento")
    private List<AreaAfectadaNormatividad> areaAfectadaNormatividadList;
    @JoinColumn(name = "id_estado", referencedColumnName = "id")
    @ManyToOne
    private TipoEstadoNormatividad idEstado;
    @JoinColumn(name = "id_tipo_analisis", referencedColumnName = "id")
    @ManyToOne
    private TipoAnalisisNorma idTipoAnalisis;
    @JoinColumn(name = "id_normatividad", referencedColumnName = "id")
    @ManyToOne
    private Normatividad idNormatividad;

    public SeguimientoNormatividad() {
    }

    public SeguimientoNormatividad(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(Date fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public String getMedioConsulta() {
        return medioConsulta;
    }

    public void setMedioConsulta(String medioConsulta) {
        this.medioConsulta = medioConsulta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDiarioOficial() {
        return diarioOficial;
    }

    public void setDiarioOficial(String diarioOficial) {
        this.diarioOficial = diarioOficial;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getCambio() {
        return cambio;
    }

    public void setCambio(Boolean cambio) {
        this.cambio = cambio;
    }

    public Date getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(Date fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public String getDescripcionAnalisis() {
        return descripcionAnalisis;
    }

    public void setDescripcionAnalisis(String descripcionAnalisis) {
        this.descripcionAnalisis = descripcionAnalisis;
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
    public List<AreaAfectadaNormatividad> getAreaAfectadaNormatividadList() {
        return areaAfectadaNormatividadList;
    }

    public void setAreaAfectadaNormatividadList(List<AreaAfectadaNormatividad> areaAfectadaNormatividadList) {
        this.areaAfectadaNormatividadList = areaAfectadaNormatividadList;
    }

    public TipoEstadoNormatividad getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(TipoEstadoNormatividad idEstado) {
        this.idEstado = idEstado;
    }

    public TipoAnalisisNorma getIdTipoAnalisis() {
        return idTipoAnalisis;
    }

    public void setIdTipoAnalisis(TipoAnalisisNorma idTipoAnalisis) {
        this.idTipoAnalisis = idTipoAnalisis;
    }

    public Normatividad getIdNormatividad() {
        return idNormatividad;
    }

    public void setIdNormatividad(Normatividad idNormatividad) {
        this.idNormatividad = idNormatividad;
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
        if (!(object instanceof SeguimientoNormatividad)) {
            return false;
        }
        SeguimientoNormatividad other = (SeguimientoNormatividad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.SeguimientoNormatividad[ id=" + id + " ]";
    }
    
}
