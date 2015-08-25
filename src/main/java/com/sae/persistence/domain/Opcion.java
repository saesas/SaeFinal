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
@Table(name = "opcion", catalog = "bdsae", schema = "autenticacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Opcion.findAll", query = "SELECT o FROM Opcion o"),
    @NamedQuery(name = "Opcion.findById", query = "SELECT o FROM Opcion o WHERE o.id = :id"),
    @NamedQuery(name = "Opcion.findByNombre", query = "SELECT o FROM Opcion o WHERE o.nombre = :nombre"),
    @NamedQuery(name = "Opcion.findByEstado", query = "SELECT o FROM Opcion o WHERE o.estado = :estado"),
    @NamedQuery(name = "Opcion.findByFechaRegistro", query = "SELECT o FROM Opcion o WHERE o.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Opcion.findByUrl", query = "SELECT o FROM Opcion o WHERE o.url = :url")})
public class Opcion implements Serializable {
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
    @Column(name = "url")
    private String url;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @ManyToOne
    private Usuario idUsuario;
    @OneToMany(mappedBy = "idOpcion")
    private List<Opcion> opcionList;
    @JoinColumn(name = "id_opcion", referencedColumnName = "id")
    @ManyToOne
    private Opcion idOpcion;
    @OneToMany(mappedBy = "idOpcion")
    private List<OpcionPerfilVisibilidad> opcionPerfilVisibilidadList;

    public Opcion() {
    }

    public Opcion(Integer id) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlTransient
    public List<Opcion> getOpcionList() {
        return opcionList;
    }

    public void setOpcionList(List<Opcion> opcionList) {
        this.opcionList = opcionList;
    }

    public Opcion getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(Opcion idOpcion) {
        this.idOpcion = idOpcion;
    }

    @XmlTransient
    public List<OpcionPerfilVisibilidad> getOpcionPerfilVisibilidadList() {
        return opcionPerfilVisibilidadList;
    }

    public void setOpcionPerfilVisibilidadList(List<OpcionPerfilVisibilidad> opcionPerfilVisibilidadList) {
        this.opcionPerfilVisibilidadList = opcionPerfilVisibilidadList;
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
        if (!(object instanceof Opcion)) {
            return false;
        }
        Opcion other = (Opcion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Opcion[ id=" + id + " ]";
    }
    
}
