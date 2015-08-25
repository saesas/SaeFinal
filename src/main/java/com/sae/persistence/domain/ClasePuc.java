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
@Table(name = "clase_puc", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClasePuc.findAll", query = "SELECT c FROM ClasePuc c"),
    @NamedQuery(name = "ClasePuc.findById", query = "SELECT c FROM ClasePuc c WHERE c.id = :id"),
    @NamedQuery(name = "ClasePuc.findByIdProyecto", query = "SELECT c FROM ClasePuc c WHERE c.idProyecto = :idProyecto"),
    @NamedQuery(name = "ClasePuc.findByIdEntidadFinanciera", query = "SELECT c FROM ClasePuc c WHERE c.idEntidadFinanciera = :idEntidadFinanciera"),
    @NamedQuery(name = "ClasePuc.findByEstado", query = "SELECT c FROM ClasePuc c WHERE c.estado = :estado"),
    @NamedQuery(name = "ClasePuc.findByFechaRegistro", query = "SELECT c FROM ClasePuc c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ClasePuc.findByIdUsuario", query = "SELECT c FROM ClasePuc c WHERE c.idUsuario = :idUsuario")})
public class ClasePuc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_proyecto")
    private Integer idProyecto;
    @Column(name = "id_entidad_financiera")
    private Integer idEntidadFinanciera;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_clase", referencedColumnName = "id")
    @ManyToOne
    private TipoClasePuc idClase;
    @JoinColumn(name = "id_puc", referencedColumnName = "id")
    @ManyToOne
    private Puc idPuc;

    public ClasePuc() {
    }

    public ClasePuc(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Integer getIdEntidadFinanciera() {
        return idEntidadFinanciera;
    }

    public void setIdEntidadFinanciera(Integer idEntidadFinanciera) {
        this.idEntidadFinanciera = idEntidadFinanciera;
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

    public TipoClasePuc getIdClase() {
        return idClase;
    }

    public void setIdClase(TipoClasePuc idClase) {
        this.idClase = idClase;
    }

    public Puc getIdPuc() {
        return idPuc;
    }

    public void setIdPuc(Puc idPuc) {
        this.idPuc = idPuc;
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
        if (!(object instanceof ClasePuc)) {
            return false;
        }
        ClasePuc other = (ClasePuc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ClasePuc[ id=" + id + " ]";
    }
    
}
