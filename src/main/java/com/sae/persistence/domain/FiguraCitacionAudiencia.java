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
@Table(name = "figura_citacion_audiencia", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FiguraCitacionAudiencia.findAll", query = "SELECT f FROM FiguraCitacionAudiencia f"),
    @NamedQuery(name = "FiguraCitacionAudiencia.findById", query = "SELECT f FROM FiguraCitacionAudiencia f WHERE f.id = :id"),
    @NamedQuery(name = "FiguraCitacionAudiencia.findByIdTercero", query = "SELECT f FROM FiguraCitacionAudiencia f WHERE f.idTercero = :idTercero"),
    @NamedQuery(name = "FiguraCitacionAudiencia.findByEstado", query = "SELECT f FROM FiguraCitacionAudiencia f WHERE f.estado = :estado"),
    @NamedQuery(name = "FiguraCitacionAudiencia.findByFechaRegistro", query = "SELECT f FROM FiguraCitacionAudiencia f WHERE f.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "FiguraCitacionAudiencia.findByIdUsuario", query = "SELECT f FROM FiguraCitacionAudiencia f WHERE f.idUsuario = :idUsuario")})
public class FiguraCitacionAudiencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_tercero")
    private Integer idTercero;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_figura", referencedColumnName = "id")
    @ManyToOne
    private Figura idFigura;
    @JoinColumn(name = "id_citacion", referencedColumnName = "id")
    @ManyToOne
    private CitacionAudiencia idCitacion;

    public FiguraCitacionAudiencia() {
    }

    public FiguraCitacionAudiencia(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdTercero() {
        return idTercero;
    }

    public void setIdTercero(Integer idTercero) {
        this.idTercero = idTercero;
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

    public Figura getIdFigura() {
        return idFigura;
    }

    public void setIdFigura(Figura idFigura) {
        this.idFigura = idFigura;
    }

    public CitacionAudiencia getIdCitacion() {
        return idCitacion;
    }

    public void setIdCitacion(CitacionAudiencia idCitacion) {
        this.idCitacion = idCitacion;
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
        if (!(object instanceof FiguraCitacionAudiencia)) {
            return false;
        }
        FiguraCitacionAudiencia other = (FiguraCitacionAudiencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.FiguraCitacionAudiencia[ id=" + id + " ]";
    }
    
}
