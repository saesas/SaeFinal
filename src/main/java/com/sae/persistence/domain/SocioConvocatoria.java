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
@Table(name = "socio_convocatoria", catalog = "bdsae", schema = "licitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SocioConvocatoria.findAll", query = "SELECT s FROM SocioConvocatoria s"),
    @NamedQuery(name = "SocioConvocatoria.findById", query = "SELECT s FROM SocioConvocatoria s WHERE s.id = :id"),
    @NamedQuery(name = "SocioConvocatoria.findByIdTercero", query = "SELECT s FROM SocioConvocatoria s WHERE s.idTercero = :idTercero"),
    @NamedQuery(name = "SocioConvocatoria.findByIdRazonSocial", query = "SELECT s FROM SocioConvocatoria s WHERE s.idRazonSocial = :idRazonSocial"),
    @NamedQuery(name = "SocioConvocatoria.findByPorcentajeParticipacion", query = "SELECT s FROM SocioConvocatoria s WHERE s.porcentajeParticipacion = :porcentajeParticipacion"),
    @NamedQuery(name = "SocioConvocatoria.findByEstado", query = "SELECT s FROM SocioConvocatoria s WHERE s.estado = :estado"),
    @NamedQuery(name = "SocioConvocatoria.findByFechaRegistro", query = "SELECT s FROM SocioConvocatoria s WHERE s.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "SocioConvocatoria.findByIdUsuario", query = "SELECT s FROM SocioConvocatoria s WHERE s.idUsuario = :idUsuario")})
public class SocioConvocatoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_tercero")
    private Integer idTercero;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "porcentaje_participacion")
    private Double porcentajeParticipacion;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_convocatoria", referencedColumnName = "id")
    @ManyToOne
    private Convocatoria idConvocatoria;
    @OneToMany(mappedBy = "idSocioConvocatoria")
    private List<RupEstadoFinancieroSocioConvocatoria> rupEstadoFinancieroSocioConvocatoriaList;

    public SocioConvocatoria() {
    }

    public SocioConvocatoria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdTercero() {
        return idTercero;
    }

    public void setIdTercero(Integer idTercero) {
        this.idTercero = idTercero;
    }

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    public Double getPorcentajeParticipacion() {
        return porcentajeParticipacion;
    }

    public void setPorcentajeParticipacion(Double porcentajeParticipacion) {
        this.porcentajeParticipacion = porcentajeParticipacion;
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

    public Convocatoria getIdConvocatoria() {
        return idConvocatoria;
    }

    public void setIdConvocatoria(Convocatoria idConvocatoria) {
        this.idConvocatoria = idConvocatoria;
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
        if (!(object instanceof SocioConvocatoria)) {
            return false;
        }
        SocioConvocatoria other = (SocioConvocatoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.SocioConvocatoria[ id=" + id + " ]";
    }
    
}
