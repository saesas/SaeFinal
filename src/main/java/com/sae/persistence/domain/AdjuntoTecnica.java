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
@Table(name = "adjunto_tecnica", catalog = "bdsae", schema = "tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdjuntoTecnica.findAll", query = "SELECT a FROM AdjuntoTecnica a"),
    @NamedQuery(name = "AdjuntoTecnica.findById", query = "SELECT a FROM AdjuntoTecnica a WHERE a.id = :id"),
    @NamedQuery(name = "AdjuntoTecnica.findByNombre", query = "SELECT a FROM AdjuntoTecnica a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "AdjuntoTecnica.findByEstado", query = "SELECT a FROM AdjuntoTecnica a WHERE a.estado = :estado"),
    @NamedQuery(name = "AdjuntoTecnica.findByIdTipoAdjunto", query = "SELECT a FROM AdjuntoTecnica a WHERE a.idTipoAdjunto = :idTipoAdjunto"),
    @NamedQuery(name = "AdjuntoTecnica.findByFechaRegistro", query = "SELECT a FROM AdjuntoTecnica a WHERE a.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "AdjuntoTecnica.findByIdUsuario", query = "SELECT a FROM AdjuntoTecnica a WHERE a.idUsuario = :idUsuario")})
public class AdjuntoTecnica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_tipo_adjunto")
    private Integer idTipoAdjunto;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @OneToMany(mappedBy = "adjuntoTecnica")
    private List<ActaInicio> actaInicioList;
    @OneToMany(mappedBy = "idAdjuntoLegalizacion")
    private List<ContratoProyectoProveedor> contratoProyectoProveedorList;
    @OneToMany(mappedBy = "idAdjunto")
    private List<ContratoProyectoProveedor> contratoProyectoProveedorList1;

    public AdjuntoTecnica() {
    }

    public AdjuntoTecnica(Integer id) {
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

    public Integer getIdTipoAdjunto() {
        return idTipoAdjunto;
    }

    public void setIdTipoAdjunto(Integer idTipoAdjunto) {
        this.idTipoAdjunto = idTipoAdjunto;
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
    public List<ActaInicio> getActaInicioList() {
        return actaInicioList;
    }

    public void setActaInicioList(List<ActaInicio> actaInicioList) {
        this.actaInicioList = actaInicioList;
    }

    @XmlTransient
    public List<ContratoProyectoProveedor> getContratoProyectoProveedorList() {
        return contratoProyectoProveedorList;
    }

    public void setContratoProyectoProveedorList(List<ContratoProyectoProveedor> contratoProyectoProveedorList) {
        this.contratoProyectoProveedorList = contratoProyectoProveedorList;
    }

    @XmlTransient
    public List<ContratoProyectoProveedor> getContratoProyectoProveedorList1() {
        return contratoProyectoProveedorList1;
    }

    public void setContratoProyectoProveedorList1(List<ContratoProyectoProveedor> contratoProyectoProveedorList1) {
        this.contratoProyectoProveedorList1 = contratoProyectoProveedorList1;
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
        if (!(object instanceof AdjuntoTecnica)) {
            return false;
        }
        AdjuntoTecnica other = (AdjuntoTecnica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.AdjuntoTecnica[ id=" + id + " ]";
    }
    
}
