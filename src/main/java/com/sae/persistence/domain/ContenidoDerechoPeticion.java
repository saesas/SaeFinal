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
@Table(name = "contenido_derecho_peticion", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContenidoDerechoPeticion.findAll", query = "SELECT c FROM ContenidoDerechoPeticion c"),
    @NamedQuery(name = "ContenidoDerechoPeticion.findById", query = "SELECT c FROM ContenidoDerechoPeticion c WHERE c.id = :id"),
    @NamedQuery(name = "ContenidoDerechoPeticion.findByAsunto", query = "SELECT c FROM ContenidoDerechoPeticion c WHERE c.asunto = :asunto"),
    @NamedQuery(name = "ContenidoDerechoPeticion.findByTitulo", query = "SELECT c FROM ContenidoDerechoPeticion c WHERE c.titulo = :titulo"),
    @NamedQuery(name = "ContenidoDerechoPeticion.findByContenido", query = "SELECT c FROM ContenidoDerechoPeticion c WHERE c.contenido = :contenido"),
    @NamedQuery(name = "ContenidoDerechoPeticion.findByEstado", query = "SELECT c FROM ContenidoDerechoPeticion c WHERE c.estado = :estado"),
    @NamedQuery(name = "ContenidoDerechoPeticion.findByFechaRegistro", query = "SELECT c FROM ContenidoDerechoPeticion c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ContenidoDerechoPeticion.findByIdUsuario", query = "SELECT c FROM ContenidoDerechoPeticion c WHERE c.idUsuario = :idUsuario")})
public class ContenidoDerechoPeticion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "asunto")
    private String asunto;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "contenido")
    private String contenido;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_tipo_contenido", referencedColumnName = "id")
    @ManyToOne
    private TipoContenidoDerechoPeticion idTipoContenido;
    @JoinColumn(name = "id_derecho", referencedColumnName = "id")
    @ManyToOne
    private DerechoPeticion idDerecho;

    public ContenidoDerechoPeticion() {
    }

    public ContenidoDerechoPeticion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
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

    public TipoContenidoDerechoPeticion getIdTipoContenido() {
        return idTipoContenido;
    }

    public void setIdTipoContenido(TipoContenidoDerechoPeticion idTipoContenido) {
        this.idTipoContenido = idTipoContenido;
    }

    public DerechoPeticion getIdDerecho() {
        return idDerecho;
    }

    public void setIdDerecho(DerechoPeticion idDerecho) {
        this.idDerecho = idDerecho;
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
        if (!(object instanceof ContenidoDerechoPeticion)) {
            return false;
        }
        ContenidoDerechoPeticion other = (ContenidoDerechoPeticion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ContenidoDerechoPeticion[ id=" + id + " ]";
    }
    
}
