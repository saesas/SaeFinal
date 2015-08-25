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
@Table(name = "figura", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Figura.findAll", query = "SELECT f FROM Figura f"),
    @NamedQuery(name = "Figura.findById", query = "SELECT f FROM Figura f WHERE f.id = :id"),
    @NamedQuery(name = "Figura.findByNombre", query = "SELECT f FROM Figura f WHERE f.nombre = :nombre"),
    @NamedQuery(name = "Figura.findByEstado", query = "SELECT f FROM Figura f WHERE f.estado = :estado"),
    @NamedQuery(name = "Figura.findByFechaRegistro", query = "SELECT f FROM Figura f WHERE f.fechaRegistro = :fechaRegistro")})
public class Figura implements Serializable {
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
    @OneToMany(mappedBy = "idFigura")
    private List<FiguraPoder> figuraPoderList;
    @OneToMany(mappedBy = "idFigura")
    private List<FiguraCitacionAudiencia> figuraCitacionAudienciaList;
    @OneToMany(mappedBy = "idFigura")
    private List<FiguraProceso> figuraProcesoList;
    @OneToMany(mappedBy = "idFigura")
    private List<FiguraDerechoPeticion> figuraDerechoPeticionList;
    @OneToMany(mappedBy = "idFigura")
    private List<FiguraSolucion> figuraSolucionList;

    public Figura() {
    }

    public Figura(Integer id) {
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

    @XmlTransient
    public List<FiguraPoder> getFiguraPoderList() {
        return figuraPoderList;
    }

    public void setFiguraPoderList(List<FiguraPoder> figuraPoderList) {
        this.figuraPoderList = figuraPoderList;
    }

    @XmlTransient
    public List<FiguraCitacionAudiencia> getFiguraCitacionAudienciaList() {
        return figuraCitacionAudienciaList;
    }

    public void setFiguraCitacionAudienciaList(List<FiguraCitacionAudiencia> figuraCitacionAudienciaList) {
        this.figuraCitacionAudienciaList = figuraCitacionAudienciaList;
    }

    @XmlTransient
    public List<FiguraProceso> getFiguraProcesoList() {
        return figuraProcesoList;
    }

    public void setFiguraProcesoList(List<FiguraProceso> figuraProcesoList) {
        this.figuraProcesoList = figuraProcesoList;
    }

    @XmlTransient
    public List<FiguraDerechoPeticion> getFiguraDerechoPeticionList() {
        return figuraDerechoPeticionList;
    }

    public void setFiguraDerechoPeticionList(List<FiguraDerechoPeticion> figuraDerechoPeticionList) {
        this.figuraDerechoPeticionList = figuraDerechoPeticionList;
    }

    @XmlTransient
    public List<FiguraSolucion> getFiguraSolucionList() {
        return figuraSolucionList;
    }

    public void setFiguraSolucionList(List<FiguraSolucion> figuraSolucionList) {
        this.figuraSolucionList = figuraSolucionList;
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
        if (!(object instanceof Figura)) {
            return false;
        }
        Figura other = (Figura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Figura[ id=" + id + " ]";
    }
    
}
