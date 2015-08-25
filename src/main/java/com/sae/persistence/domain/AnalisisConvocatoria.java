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
@Table(name = "analisis_convocatoria", catalog = "bdsae", schema = "licitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AnalisisConvocatoria.findAll", query = "SELECT a FROM AnalisisConvocatoria a"),
    @NamedQuery(name = "AnalisisConvocatoria.findById", query = "SELECT a FROM AnalisisConvocatoria a WHERE a.id = :id"),
    @NamedQuery(name = "AnalisisConvocatoria.findByEstado", query = "SELECT a FROM AnalisisConvocatoria a WHERE a.estado = :estado"),
    @NamedQuery(name = "AnalisisConvocatoria.findByExperienciaGeneralProponente", query = "SELECT a FROM AnalisisConvocatoria a WHERE a.experienciaGeneralProponente = :experienciaGeneralProponente"),
    @NamedQuery(name = "AnalisisConvocatoria.findByExperienciaEspecificaProponente", query = "SELECT a FROM AnalisisConvocatoria a WHERE a.experienciaEspecificaProponente = :experienciaEspecificaProponente"),
    @NamedQuery(name = "AnalisisConvocatoria.findByRealizaPropuesta", query = "SELECT a FROM AnalisisConvocatoria a WHERE a.realizaPropuesta = :realizaPropuesta"),
    @NamedQuery(name = "AnalisisConvocatoria.findByFechaRegistro", query = "SELECT a FROM AnalisisConvocatoria a WHERE a.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "AnalisisConvocatoria.findByIdUsuario", query = "SELECT a FROM AnalisisConvocatoria a WHERE a.idUsuario = :idUsuario"),
    @NamedQuery(name = "AnalisisConvocatoria.findByAsuntoExperienciaGeneral", query = "SELECT a FROM AnalisisConvocatoria a WHERE a.asuntoExperienciaGeneral = :asuntoExperienciaGeneral"),
    @NamedQuery(name = "AnalisisConvocatoria.findByAsuntoExperienciaEspecifica", query = "SELECT a FROM AnalisisConvocatoria a WHERE a.asuntoExperienciaEspecifica = :asuntoExperienciaEspecifica"),
    @NamedQuery(name = "AnalisisConvocatoria.findByDecisionPropuesta", query = "SELECT a FROM AnalisisConvocatoria a WHERE a.decisionPropuesta = :decisionPropuesta")})
public class AnalisisConvocatoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "experiencia_general_proponente")
    private String experienciaGeneralProponente;
    @Column(name = "experiencia_especifica_proponente")
    private String experienciaEspecificaProponente;
    @Column(name = "realiza_propuesta")
    private Boolean realizaPropuesta;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "asunto_experiencia_general")
    private String asuntoExperienciaGeneral;
    @Column(name = "asunto_experiencia_especifica")
    private String asuntoExperienciaEspecifica;
    @Column(name = "decision_propuesta")
    private String decisionPropuesta;
    @JoinColumn(name = "id_convocatoria", referencedColumnName = "id")
    @ManyToOne
    private Convocatoria idConvocatoria;

    public AnalisisConvocatoria() {
    }

    public AnalisisConvocatoria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getExperienciaGeneralProponente() {
        return experienciaGeneralProponente;
    }

    public void setExperienciaGeneralProponente(String experienciaGeneralProponente) {
        this.experienciaGeneralProponente = experienciaGeneralProponente;
    }

    public String getExperienciaEspecificaProponente() {
        return experienciaEspecificaProponente;
    }

    public void setExperienciaEspecificaProponente(String experienciaEspecificaProponente) {
        this.experienciaEspecificaProponente = experienciaEspecificaProponente;
    }

    public Boolean getRealizaPropuesta() {
        return realizaPropuesta;
    }

    public void setRealizaPropuesta(Boolean realizaPropuesta) {
        this.realizaPropuesta = realizaPropuesta;
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

    public String getAsuntoExperienciaGeneral() {
        return asuntoExperienciaGeneral;
    }

    public void setAsuntoExperienciaGeneral(String asuntoExperienciaGeneral) {
        this.asuntoExperienciaGeneral = asuntoExperienciaGeneral;
    }

    public String getAsuntoExperienciaEspecifica() {
        return asuntoExperienciaEspecifica;
    }

    public void setAsuntoExperienciaEspecifica(String asuntoExperienciaEspecifica) {
        this.asuntoExperienciaEspecifica = asuntoExperienciaEspecifica;
    }

    public String getDecisionPropuesta() {
        return decisionPropuesta;
    }

    public void setDecisionPropuesta(String decisionPropuesta) {
        this.decisionPropuesta = decisionPropuesta;
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
        if (!(object instanceof AnalisisConvocatoria)) {
            return false;
        }
        AnalisisConvocatoria other = (AnalisisConvocatoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.AnalisisConvocatoria[ id=" + id + " ]";
    }
    
}
