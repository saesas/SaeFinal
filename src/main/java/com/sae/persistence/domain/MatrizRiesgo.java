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
@Table(name = "matriz_riesgo", catalog = "bdsae", schema = "licitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MatrizRiesgo.findAll", query = "SELECT m FROM MatrizRiesgo m"),
    @NamedQuery(name = "MatrizRiesgo.findById", query = "SELECT m FROM MatrizRiesgo m WHERE m.id = :id"),
    @NamedQuery(name = "MatrizRiesgo.findByNombre", query = "SELECT m FROM MatrizRiesgo m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "MatrizRiesgo.findByEstado", query = "SELECT m FROM MatrizRiesgo m WHERE m.estado = :estado"),
    @NamedQuery(name = "MatrizRiesgo.findByFechaRegistro", query = "SELECT m FROM MatrizRiesgo m WHERE m.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "MatrizRiesgo.findByDecisionPropuesta", query = "SELECT m FROM MatrizRiesgo m WHERE m.decisionPropuesta = :decisionPropuesta"),
    @NamedQuery(name = "MatrizRiesgo.findByEstadoMatrizRiesgo", query = "SELECT m FROM MatrizRiesgo m WHERE m.estadoMatrizRiesgo = :estadoMatrizRiesgo")})
public class MatrizRiesgo implements Serializable {
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
    @Column(name = "decision_propuesta")
    private String decisionPropuesta;
    @Column(name = "estado_matriz_riesgo")
    private String estadoMatrizRiesgo;
    @JoinColumn(name = "id_convocatoria", referencedColumnName = "id")
    @ManyToOne
    private Convocatoria idConvocatoria;
    @OneToMany(mappedBy = "idMatriz")
    private List<RegistroMatrizRiesgo> registroMatrizRiesgoList;

    public MatrizRiesgo() {
    }

    public MatrizRiesgo(Integer id) {
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

    public String getDecisionPropuesta() {
        return decisionPropuesta;
    }

    public void setDecisionPropuesta(String decisionPropuesta) {
        this.decisionPropuesta = decisionPropuesta;
    }

    public String getEstadoMatrizRiesgo() {
        return estadoMatrizRiesgo;
    }

    public void setEstadoMatrizRiesgo(String estadoMatrizRiesgo) {
        this.estadoMatrizRiesgo = estadoMatrizRiesgo;
    }

    public Convocatoria getIdConvocatoria() {
        return idConvocatoria;
    }

    public void setIdConvocatoria(Convocatoria idConvocatoria) {
        this.idConvocatoria = idConvocatoria;
    }

    @XmlTransient
    public List<RegistroMatrizRiesgo> getRegistroMatrizRiesgoList() {
        return registroMatrizRiesgoList;
    }

    public void setRegistroMatrizRiesgoList(List<RegistroMatrizRiesgo> registroMatrizRiesgoList) {
        this.registroMatrizRiesgoList = registroMatrizRiesgoList;
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
        if (!(object instanceof MatrizRiesgo)) {
            return false;
        }
        MatrizRiesgo other = (MatrizRiesgo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.MatrizRiesgo[ id=" + id + " ]";
    }
    
}
