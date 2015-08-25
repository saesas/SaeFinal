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
@Table(name = "figura_proceso", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FiguraProceso.findAll", query = "SELECT f FROM FiguraProceso f"),
    @NamedQuery(name = "FiguraProceso.findById", query = "SELECT f FROM FiguraProceso f WHERE f.id = :id"),
    @NamedQuery(name = "FiguraProceso.findByIdTercero", query = "SELECT f FROM FiguraProceso f WHERE f.idTercero = :idTercero"),
    @NamedQuery(name = "FiguraProceso.findByTercerParte", query = "SELECT f FROM FiguraProceso f WHERE f.tercerParte = :tercerParte"),
    @NamedQuery(name = "FiguraProceso.findByEstado", query = "SELECT f FROM FiguraProceso f WHERE f.estado = :estado"),
    @NamedQuery(name = "FiguraProceso.findByFechaRegistro", query = "SELECT f FROM FiguraProceso f WHERE f.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "FiguraProceso.findByIdUsuario", query = "SELECT f FROM FiguraProceso f WHERE f.idUsuario = :idUsuario")})
public class FiguraProceso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_tercero")
    private Integer idTercero;
    @Column(name = "tercer_parte")
    private Boolean tercerParte;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_proceso", referencedColumnName = "id")
    @ManyToOne
    private Proceso idProceso;
    @JoinColumn(name = "id_figura", referencedColumnName = "id")
    @ManyToOne
    private Figura idFigura;

    public FiguraProceso() {
    }

    public FiguraProceso(Integer id) {
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

    public Boolean getTercerParte() {
        return tercerParte;
    }

    public void setTercerParte(Boolean tercerParte) {
        this.tercerParte = tercerParte;
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

    public Proceso getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Proceso idProceso) {
        this.idProceso = idProceso;
    }

    public Figura getIdFigura() {
        return idFigura;
    }

    public void setIdFigura(Figura idFigura) {
        this.idFigura = idFigura;
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
        if (!(object instanceof FiguraProceso)) {
            return false;
        }
        FiguraProceso other = (FiguraProceso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.FiguraProceso[ id=" + id + " ]";
    }
    
}
