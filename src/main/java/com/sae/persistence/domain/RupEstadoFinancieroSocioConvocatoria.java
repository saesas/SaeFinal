/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SAE2
 */
@Entity
@Table(name = "rup_estado_financiero_socio_convocatoria", catalog = "bdsae", schema = "licitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RupEstadoFinancieroSocioConvocatoria.findAll", query = "SELECT r FROM RupEstadoFinancieroSocioConvocatoria r"),
    @NamedQuery(name = "RupEstadoFinancieroSocioConvocatoria.findById", query = "SELECT r FROM RupEstadoFinancieroSocioConvocatoria r WHERE r.id = :id"),
    @NamedQuery(name = "RupEstadoFinancieroSocioConvocatoria.findByValorRup", query = "SELECT r FROM RupEstadoFinancieroSocioConvocatoria r WHERE r.valorRup = :valorRup"),
    @NamedQuery(name = "RupEstadoFinancieroSocioConvocatoria.findByEstado", query = "SELECT r FROM RupEstadoFinancieroSocioConvocatoria r WHERE r.estado = :estado"),
    @NamedQuery(name = "RupEstadoFinancieroSocioConvocatoria.findByValorEstadoFinanciero", query = "SELECT r FROM RupEstadoFinancieroSocioConvocatoria r WHERE r.valorEstadoFinanciero = :valorEstadoFinanciero"),
    @NamedQuery(name = "RupEstadoFinancieroSocioConvocatoria.findByFechaRegistro", query = "SELECT r FROM RupEstadoFinancieroSocioConvocatoria r WHERE r.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "RupEstadoFinancieroSocioConvocatoria.findByIdUsuario", query = "SELECT r FROM RupEstadoFinancieroSocioConvocatoria r WHERE r.idUsuario = :idUsuario")})
public class RupEstadoFinancieroSocioConvocatoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "valor_rup")
    private String valorRup;
    @Column(name = "estado")
    private Boolean estado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_estado_financiero")
    private Double valorEstadoFinanciero;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_socio_convocatoria", referencedColumnName = "id")
    @ManyToOne
    private SocioConvocatoria idSocioConvocatoria;
    @JoinColumn(name = "id_indicador", referencedColumnName = "id")
    @ManyToOne
    private IndicadorConvocatoria idIndicador;

    public RupEstadoFinancieroSocioConvocatoria() {
    }

    public RupEstadoFinancieroSocioConvocatoria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValorRup() {
        return valorRup;
    }

    public void setValorRup(String valorRup) {
        this.valorRup = valorRup;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Double getValorEstadoFinanciero() {
        return valorEstadoFinanciero;
    }

    public void setValorEstadoFinanciero(Double valorEstadoFinanciero) {
        this.valorEstadoFinanciero = valorEstadoFinanciero;
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

    public SocioConvocatoria getIdSocioConvocatoria() {
        return idSocioConvocatoria;
    }

    public void setIdSocioConvocatoria(SocioConvocatoria idSocioConvocatoria) {
        this.idSocioConvocatoria = idSocioConvocatoria;
    }

    public IndicadorConvocatoria getIdIndicador() {
        return idIndicador;
    }

    public void setIdIndicador(IndicadorConvocatoria idIndicador) {
        this.idIndicador = idIndicador;
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
        if (!(object instanceof RupEstadoFinancieroSocioConvocatoria)) {
            return false;
        }
        RupEstadoFinancieroSocioConvocatoria other = (RupEstadoFinancieroSocioConvocatoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.RupEstadoFinancieroSocioConvocatoria[ id=" + id + " ]";
    }
    
}
