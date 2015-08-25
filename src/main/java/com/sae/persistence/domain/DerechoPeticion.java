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
@Table(name = "derecho_peticion", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DerechoPeticion.findAll", query = "SELECT d FROM DerechoPeticion d"),
    @NamedQuery(name = "DerechoPeticion.findById", query = "SELECT d FROM DerechoPeticion d WHERE d.id = :id"),
    @NamedQuery(name = "DerechoPeticion.findByTerminoRespuesta", query = "SELECT d FROM DerechoPeticion d WHERE d.terminoRespuesta = :terminoRespuesta"),
    @NamedQuery(name = "DerechoPeticion.findByFechaPresentacion", query = "SELECT d FROM DerechoPeticion d WHERE d.fechaPresentacion = :fechaPresentacion"),
    @NamedQuery(name = "DerechoPeticion.findByAdjunto", query = "SELECT d FROM DerechoPeticion d WHERE d.adjunto = :adjunto"),
    @NamedQuery(name = "DerechoPeticion.findByEstado", query = "SELECT d FROM DerechoPeticion d WHERE d.estado = :estado"),
    @NamedQuery(name = "DerechoPeticion.findByFechaRegistro", query = "SELECT d FROM DerechoPeticion d WHERE d.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "DerechoPeticion.findByIdUsuario", query = "SELECT d FROM DerechoPeticion d WHERE d.idUsuario = :idUsuario"),
    @NamedQuery(name = "DerechoPeticion.findByAprobado", query = "SELECT d FROM DerechoPeticion d WHERE d.aprobado = :aprobado"),
    @NamedQuery(name = "DerechoPeticion.findByDescripcionAprobacion", query = "SELECT d FROM DerechoPeticion d WHERE d.descripcionAprobacion = :descripcionAprobacion"),
    @NamedQuery(name = "DerechoPeticion.findByIdRazonSocial", query = "SELECT d FROM DerechoPeticion d WHERE d.idRazonSocial = :idRazonSocial")})
public class DerechoPeticion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "termino_respuesta")
    private Integer terminoRespuesta;
    @Column(name = "fecha_presentacion")
    @Temporal(TemporalType.DATE)
    private Date fechaPresentacion;
    @Column(name = "adjunto")
    private String adjunto;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "aprobado")
    private Boolean aprobado;
    @Column(name = "descripcion_aprobacion")
    private String descripcionAprobacion;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @OneToMany(mappedBy = "idDerecho")
    private List<ContenidoDerechoPeticion> contenidoDerechoPeticionList;
    @JoinColumn(name = "id_origen", referencedColumnName = "id")
    @ManyToOne
    private OrigenDerechoPeticion idOrigen;
    @JoinColumn(name = "id_motivo", referencedColumnName = "id")
    @ManyToOne
    private MotivoDerechoPeticion idMotivo;
    @OneToMany(mappedBy = "idDerechoPeticion")
    private List<FiguraDerechoPeticion> figuraDerechoPeticionList;

    public DerechoPeticion() {
    }

    public DerechoPeticion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTerminoRespuesta() {
        return terminoRespuesta;
    }

    public void setTerminoRespuesta(Integer terminoRespuesta) {
        this.terminoRespuesta = terminoRespuesta;
    }

    public Date getFechaPresentacion() {
        return fechaPresentacion;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }

    public String getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(String adjunto) {
        this.adjunto = adjunto;
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

    public Boolean getAprobado() {
        return aprobado;
    }

    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }

    public String getDescripcionAprobacion() {
        return descripcionAprobacion;
    }

    public void setDescripcionAprobacion(String descripcionAprobacion) {
        this.descripcionAprobacion = descripcionAprobacion;
    }

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    @XmlTransient
    public List<ContenidoDerechoPeticion> getContenidoDerechoPeticionList() {
        return contenidoDerechoPeticionList;
    }

    public void setContenidoDerechoPeticionList(List<ContenidoDerechoPeticion> contenidoDerechoPeticionList) {
        this.contenidoDerechoPeticionList = contenidoDerechoPeticionList;
    }

    public OrigenDerechoPeticion getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(OrigenDerechoPeticion idOrigen) {
        this.idOrigen = idOrigen;
    }

    public MotivoDerechoPeticion getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(MotivoDerechoPeticion idMotivo) {
        this.idMotivo = idMotivo;
    }

    @XmlTransient
    public List<FiguraDerechoPeticion> getFiguraDerechoPeticionList() {
        return figuraDerechoPeticionList;
    }

    public void setFiguraDerechoPeticionList(List<FiguraDerechoPeticion> figuraDerechoPeticionList) {
        this.figuraDerechoPeticionList = figuraDerechoPeticionList;
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
        if (!(object instanceof DerechoPeticion)) {
            return false;
        }
        DerechoPeticion other = (DerechoPeticion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.DerechoPeticion[ id=" + id + " ]";
    }
    
}
