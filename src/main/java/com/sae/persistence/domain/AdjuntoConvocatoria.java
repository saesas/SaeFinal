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
@Table(name = "adjunto_convocatoria", catalog = "bdsae", schema = "licitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdjuntoConvocatoria.findAll", query = "SELECT a FROM AdjuntoConvocatoria a"),
    @NamedQuery(name = "AdjuntoConvocatoria.findById", query = "SELECT a FROM AdjuntoConvocatoria a WHERE a.id = :id"),
    @NamedQuery(name = "AdjuntoConvocatoria.findByIdTipo", query = "SELECT a FROM AdjuntoConvocatoria a WHERE a.idTipo = :idTipo"),
    @NamedQuery(name = "AdjuntoConvocatoria.findByEstado", query = "SELECT a FROM AdjuntoConvocatoria a WHERE a.estado = :estado"),
    @NamedQuery(name = "AdjuntoConvocatoria.findByFechaRegistro", query = "SELECT a FROM AdjuntoConvocatoria a WHERE a.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "AdjuntoConvocatoria.findByIdUsuario", query = "SELECT a FROM AdjuntoConvocatoria a WHERE a.idUsuario = :idUsuario"),
    @NamedQuery(name = "AdjuntoConvocatoria.findByObservacion", query = "SELECT a FROM AdjuntoConvocatoria a WHERE a.observacion = :observacion"),
    @NamedQuery(name = "AdjuntoConvocatoria.findByNombreAdjunto", query = "SELECT a FROM AdjuntoConvocatoria a WHERE a.nombreAdjunto = :nombreAdjunto")})
public class AdjuntoConvocatoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_tipo")
    private Integer idTipo;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "nombre_adjunto")
    private String nombreAdjunto;
    @JoinColumn(name = "id_convocatoria", referencedColumnName = "id")
    @ManyToOne
    private Convocatoria idConvocatoria;
    @OneToMany(mappedBy = "idAdjuntoItem")
    private List<EvaluacionConvocatoria> evaluacionConvocatoriaList;

    public AdjuntoConvocatoria() {
    }

    public AdjuntoConvocatoria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getNombreAdjunto() {
        return nombreAdjunto;
    }

    public void setNombreAdjunto(String nombreAdjunto) {
        this.nombreAdjunto = nombreAdjunto;
    }

    public Convocatoria getIdConvocatoria() {
        return idConvocatoria;
    }

    public void setIdConvocatoria(Convocatoria idConvocatoria) {
        this.idConvocatoria = idConvocatoria;
    }

    @XmlTransient
    public List<EvaluacionConvocatoria> getEvaluacionConvocatoriaList() {
        return evaluacionConvocatoriaList;
    }

    public void setEvaluacionConvocatoriaList(List<EvaluacionConvocatoria> evaluacionConvocatoriaList) {
        this.evaluacionConvocatoriaList = evaluacionConvocatoriaList;
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
        if (!(object instanceof AdjuntoConvocatoria)) {
            return false;
        }
        AdjuntoConvocatoria other = (AdjuntoConvocatoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.AdjuntoConvocatoria[ id=" + id + " ]";
    }
    
}
