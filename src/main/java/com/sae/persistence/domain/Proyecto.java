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
@Table(name = "proyecto", catalog = "bdsae", schema = "tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p"),
    @NamedQuery(name = "Proyecto.findById", query = "SELECT p FROM Proyecto p WHERE p.id = :id"),
    @NamedQuery(name = "Proyecto.findByNombre", query = "SELECT p FROM Proyecto p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Proyecto.findByEstado", query = "SELECT p FROM Proyecto p WHERE p.estado = :estado"),
    @NamedQuery(name = "Proyecto.findByCodigo", query = "SELECT p FROM Proyecto p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Proyecto.findByIdPropuesta", query = "SELECT p FROM Proyecto p WHERE p.idPropuesta = :idPropuesta"),
    @NamedQuery(name = "Proyecto.findByIdInterventor", query = "SELECT p FROM Proyecto p WHERE p.idInterventor = :idInterventor"),
    @NamedQuery(name = "Proyecto.findByObjeto", query = "SELECT p FROM Proyecto p WHERE p.objeto = :objeto"),
    @NamedQuery(name = "Proyecto.findByIdResponsable", query = "SELECT p FROM Proyecto p WHERE p.idResponsable = :idResponsable"),
    @NamedQuery(name = "Proyecto.findByIdOficina", query = "SELECT p FROM Proyecto p WHERE p.idOficina = :idOficina"),
    @NamedQuery(name = "Proyecto.findByFechaRegistro", query = "SELECT p FROM Proyecto p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Proyecto.findByIdUsuario", query = "SELECT p FROM Proyecto p WHERE p.idUsuario = :idUsuario"),
    @NamedQuery(name = "Proyecto.findByIdRazonSocial", query = "SELECT p FROM Proyecto p WHERE p.idRazonSocial = :idRazonSocial")})
public class Proyecto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "id_propuesta")
    private Integer idPropuesta;
    @Column(name = "id_interventor")
    private Integer idInterventor;
    @Column(name = "objeto")
    private String objeto;
    @Column(name = "id_responsable")
    private Integer idResponsable;
    @Column(name = "id_oficina")
    private Integer idOficina;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @OneToMany(mappedBy = "idProyecto")
    private List<Almacen> almacenList;
    @OneToMany(mappedBy = "idProyecto")
    private List<Bitacora> bitacoraList;
    @OneToMany(mappedBy = "idProyecto")
    private List<EstadoProyecto> estadoProyectoList;
    @JoinColumn(name = "id_alcance", referencedColumnName = "id")
    @ManyToOne
    private AlcanceProyecto idAlcance;
    @OneToMany(mappedBy = "idProyecto")
    private List<ContratoProyectoProveedor> contratoProyectoProveedorList;

    public Proyecto() {
    }

    public Proyecto(Integer id) {
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getIdPropuesta() {
        return idPropuesta;
    }

    public void setIdPropuesta(Integer idPropuesta) {
        this.idPropuesta = idPropuesta;
    }

    public Integer getIdInterventor() {
        return idInterventor;
    }

    public void setIdInterventor(Integer idInterventor) {
        this.idInterventor = idInterventor;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public Integer getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(Integer idResponsable) {
        this.idResponsable = idResponsable;
    }

    public Integer getIdOficina() {
        return idOficina;
    }

    public void setIdOficina(Integer idOficina) {
        this.idOficina = idOficina;
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

    @XmlTransient
    public List<Almacen> getAlmacenList() {
        return almacenList;
    }

    public void setAlmacenList(List<Almacen> almacenList) {
        this.almacenList = almacenList;
    }

    @XmlTransient
    public List<Bitacora> getBitacoraList() {
        return bitacoraList;
    }

    public void setBitacoraList(List<Bitacora> bitacoraList) {
        this.bitacoraList = bitacoraList;
    }

    @XmlTransient
    public List<EstadoProyecto> getEstadoProyectoList() {
        return estadoProyectoList;
    }

    public void setEstadoProyectoList(List<EstadoProyecto> estadoProyectoList) {
        this.estadoProyectoList = estadoProyectoList;
    }

    public AlcanceProyecto getIdAlcance() {
        return idAlcance;
    }

    public void setIdAlcance(AlcanceProyecto idAlcance) {
        this.idAlcance = idAlcance;
    }

    @XmlTransient
    public List<ContratoProyectoProveedor> getContratoProyectoProveedorList() {
        return contratoProyectoProveedorList;
    }

    public void setContratoProyectoProveedorList(List<ContratoProyectoProveedor> contratoProyectoProveedorList) {
        this.contratoProyectoProveedorList = contratoProyectoProveedorList;
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
        if (!(object instanceof Proyecto)) {
            return false;
        }
        Proyecto other = (Proyecto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Proyecto[ id=" + id + " ]";
    }
    
}
