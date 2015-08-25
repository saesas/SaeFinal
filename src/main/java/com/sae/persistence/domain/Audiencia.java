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
@Table(name = "audiencia", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Audiencia.findAll", query = "SELECT a FROM Audiencia a"),
    @NamedQuery(name = "Audiencia.findById", query = "SELECT a FROM Audiencia a WHERE a.id = :id"),
    @NamedQuery(name = "Audiencia.findByFecha", query = "SELECT a FROM Audiencia a WHERE a.fecha = :fecha"),
    @NamedQuery(name = "Audiencia.findByEstado", query = "SELECT a FROM Audiencia a WHERE a.estado = :estado"),
    @NamedQuery(name = "Audiencia.findByFechaRegistro", query = "SELECT a FROM Audiencia a WHERE a.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Audiencia.findByIdUsuario", query = "SELECT a FROM Audiencia a WHERE a.idUsuario = :idUsuario"),
    @NamedQuery(name = "Audiencia.findByIdRazonSocial", query = "SELECT a FROM Audiencia a WHERE a.idRazonSocial = :idRazonSocial")})
public class Audiencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @JoinColumn(name = "id_citacion", referencedColumnName = "id")
    @ManyToOne
    private CitacionAudiencia idCitacion;
    @OneToMany(mappedBy = "idAudiencia")
    private List<EstadoAudiencia> estadoAudienciaList;

    public Audiencia() {
    }

    public Audiencia(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    public CitacionAudiencia getIdCitacion() {
        return idCitacion;
    }

    public void setIdCitacion(CitacionAudiencia idCitacion) {
        this.idCitacion = idCitacion;
    }

    @XmlTransient
    public List<EstadoAudiencia> getEstadoAudienciaList() {
        return estadoAudienciaList;
    }

    public void setEstadoAudienciaList(List<EstadoAudiencia> estadoAudienciaList) {
        this.estadoAudienciaList = estadoAudienciaList;
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
        if (!(object instanceof Audiencia)) {
            return false;
        }
        Audiencia other = (Audiencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Audiencia[ id=" + id + " ]";
    }
    
}
