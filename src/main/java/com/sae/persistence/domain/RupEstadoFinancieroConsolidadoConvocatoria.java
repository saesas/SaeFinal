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
@Table(name = "rup_estado_financiero_consolidado_convocatoria", catalog = "bdsae", schema = "licitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RupEstadoFinancieroConsolidadoConvocatoria.findAll", query = "SELECT r FROM RupEstadoFinancieroConsolidadoConvocatoria r"),
    @NamedQuery(name = "RupEstadoFinancieroConsolidadoConvocatoria.findById", query = "SELECT r FROM RupEstadoFinancieroConsolidadoConvocatoria r WHERE r.id = :id"),
    @NamedQuery(name = "RupEstadoFinancieroConsolidadoConvocatoria.findByFormula", query = "SELECT r FROM RupEstadoFinancieroConsolidadoConvocatoria r WHERE r.formula = :formula"),
    @NamedQuery(name = "RupEstadoFinancieroConsolidadoConvocatoria.findByTotal", query = "SELECT r FROM RupEstadoFinancieroConsolidadoConvocatoria r WHERE r.total = :total"),
    @NamedQuery(name = "RupEstadoFinancieroConsolidadoConvocatoria.findByObservacion", query = "SELECT r FROM RupEstadoFinancieroConsolidadoConvocatoria r WHERE r.observacion = :observacion"),
    @NamedQuery(name = "RupEstadoFinancieroConsolidadoConvocatoria.findByEstado", query = "SELECT r FROM RupEstadoFinancieroConsolidadoConvocatoria r WHERE r.estado = :estado"),
    @NamedQuery(name = "RupEstadoFinancieroConsolidadoConvocatoria.findByFechaRegistro", query = "SELECT r FROM RupEstadoFinancieroConsolidadoConvocatoria r WHERE r.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "RupEstadoFinancieroConsolidadoConvocatoria.findByIdUsuario", query = "SELECT r FROM RupEstadoFinancieroConsolidadoConvocatoria r WHERE r.idUsuario = :idUsuario"),
    @NamedQuery(name = "RupEstadoFinancieroConsolidadoConvocatoria.findByAsunto", query = "SELECT r FROM RupEstadoFinancieroConsolidadoConvocatoria r WHERE r.asunto = :asunto"),
    @NamedQuery(name = "RupEstadoFinancieroConsolidadoConvocatoria.findByRequerido", query = "SELECT r FROM RupEstadoFinancieroConsolidadoConvocatoria r WHERE r.requerido = :requerido")})
public class RupEstadoFinancieroConsolidadoConvocatoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "formula")
    private String formula;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private Double total;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "asunto")
    private String asunto;
    @Column(name = "requerido")
    private String requerido;
    @JoinColumn(name = "id_indicador", referencedColumnName = "id")
    @ManyToOne
    private IndicadorConvocatoria idIndicador;
    @JoinColumn(name = "id_convocatoria", referencedColumnName = "id")
    @ManyToOne
    private Convocatoria idConvocatoria;

    public RupEstadoFinancieroConsolidadoConvocatoria() {
    }

    public RupEstadoFinancieroConsolidadoConvocatoria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getRequerido() {
        return requerido;
    }

    public void setRequerido(String requerido) {
        this.requerido = requerido;
    }

    public IndicadorConvocatoria getIdIndicador() {
        return idIndicador;
    }

    public void setIdIndicador(IndicadorConvocatoria idIndicador) {
        this.idIndicador = idIndicador;
    }

    public Convocatoria getIdConvocatoria() {
        return idConvocatoria;
    }

    public void setIdConvocatoria(Convocatoria idConvocatoria) {
        this.idConvocatoria = idConvocatoria;
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
        if (!(object instanceof RupEstadoFinancieroConsolidadoConvocatoria)) {
            return false;
        }
        RupEstadoFinancieroConsolidadoConvocatoria other = (RupEstadoFinancieroConsolidadoConvocatoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.RupEstadoFinancieroConsolidadoConvocatoria[ id=" + id + " ]";
    }
    
}
