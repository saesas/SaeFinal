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
@Table(name = "motivo_inasistencia", catalog = "bdsae", schema = "talento_humano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MotivoInasistencia.findAll", query = "SELECT m FROM MotivoInasistencia m"),
    @NamedQuery(name = "MotivoInasistencia.findById", query = "SELECT m FROM MotivoInasistencia m WHERE m.id = :id"),
    @NamedQuery(name = "MotivoInasistencia.findByNombre", query = "SELECT m FROM MotivoInasistencia m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "MotivoInasistencia.findByAbreviatura", query = "SELECT m FROM MotivoInasistencia m WHERE m.abreviatura = :abreviatura"),
    @NamedQuery(name = "MotivoInasistencia.findByEstado", query = "SELECT m FROM MotivoInasistencia m WHERE m.estado = :estado"),
    @NamedQuery(name = "MotivoInasistencia.findByFechaRegistro", query = "SELECT m FROM MotivoInasistencia m WHERE m.fechaRegistro = :fechaRegistro")})
public class MotivoInasistencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "abreviatura")
    private String abreviatura;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "idMotivoInasistencia")
    private List<InasistenciaContrato> inasistenciaContratoList;

    public MotivoInasistencia() {
    }

    public MotivoInasistencia(Integer id) {
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

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
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

    @XmlTransient
    public List<InasistenciaContrato> getInasistenciaContratoList() {
        return inasistenciaContratoList;
    }

    public void setInasistenciaContratoList(List<InasistenciaContrato> inasistenciaContratoList) {
        this.inasistenciaContratoList = inasistenciaContratoList;
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
        if (!(object instanceof MotivoInasistencia)) {
            return false;
        }
        MotivoInasistencia other = (MotivoInasistencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.MotivoInasistencia[ id=" + id + " ]";
    }
    
}
