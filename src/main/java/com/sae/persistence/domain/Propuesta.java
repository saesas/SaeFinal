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
@Table(name = "propuesta", catalog = "bdsae", schema = "licitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Propuesta.findAll", query = "SELECT p FROM Propuesta p"),
    @NamedQuery(name = "Propuesta.findById", query = "SELECT p FROM Propuesta p WHERE p.id = :id"),
    @NamedQuery(name = "Propuesta.findByEstado", query = "SELECT p FROM Propuesta p WHERE p.estado = :estado"),
    @NamedQuery(name = "Propuesta.findByIdentificacionSobre", query = "SELECT p FROM Propuesta p WHERE p.identificacionSobre = :identificacionSobre"),
    @NamedQuery(name = "Propuesta.findByAsuntoIdentificacionSobre", query = "SELECT p FROM Propuesta p WHERE p.asuntoIdentificacionSobre = :asuntoIdentificacionSobre"),
    @NamedQuery(name = "Propuesta.findByAsuntoCorrespondencia", query = "SELECT p FROM Propuesta p WHERE p.asuntoCorrespondencia = :asuntoCorrespondencia"),
    @NamedQuery(name = "Propuesta.findByCorrespondencia", query = "SELECT p FROM Propuesta p WHERE p.correspondencia = :correspondencia"),
    @NamedQuery(name = "Propuesta.findByFechaRegistro", query = "SELECT p FROM Propuesta p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Propuesta.findByIdUsuario", query = "SELECT p FROM Propuesta p WHERE p.idUsuario = :idUsuario")})
public class Propuesta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "identificacion_sobre")
    private String identificacionSobre;
    @Column(name = "asunto_identificacion_sobre")
    private String asuntoIdentificacionSobre;
    @Column(name = "asunto_correspondencia")
    private String asuntoCorrespondencia;
    @Column(name = "correspondencia")
    private String correspondencia;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_figura", referencedColumnName = "id")
    @ManyToOne
    private FiguraPropuesta idFigura;
    @JoinColumn(name = "id_convocatoria", referencedColumnName = "id")
    @ManyToOne
    private Convocatoria idConvocatoria;
    @OneToMany(mappedBy = "idPropuesta")
    private List<AdjuntoPropuesta> adjuntoPropuestaList;
    @OneToMany(mappedBy = "idPropuesta")
    private List<InformacionSobrePropuesta> informacionSobrePropuestaList;

    public Propuesta() {
    }

    public Propuesta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getIdentificacionSobre() {
        return identificacionSobre;
    }

    public void setIdentificacionSobre(String identificacionSobre) {
        this.identificacionSobre = identificacionSobre;
    }

    public String getAsuntoIdentificacionSobre() {
        return asuntoIdentificacionSobre;
    }

    public void setAsuntoIdentificacionSobre(String asuntoIdentificacionSobre) {
        this.asuntoIdentificacionSobre = asuntoIdentificacionSobre;
    }

    public String getAsuntoCorrespondencia() {
        return asuntoCorrespondencia;
    }

    public void setAsuntoCorrespondencia(String asuntoCorrespondencia) {
        this.asuntoCorrespondencia = asuntoCorrespondencia;
    }

    public String getCorrespondencia() {
        return correspondencia;
    }

    public void setCorrespondencia(String correspondencia) {
        this.correspondencia = correspondencia;
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

    public FiguraPropuesta getIdFigura() {
        return idFigura;
    }

    public void setIdFigura(FiguraPropuesta idFigura) {
        this.idFigura = idFigura;
    }

    public Convocatoria getIdConvocatoria() {
        return idConvocatoria;
    }

    public void setIdConvocatoria(Convocatoria idConvocatoria) {
        this.idConvocatoria = idConvocatoria;
    }

    @XmlTransient
    public List<AdjuntoPropuesta> getAdjuntoPropuestaList() {
        return adjuntoPropuestaList;
    }

    public void setAdjuntoPropuestaList(List<AdjuntoPropuesta> adjuntoPropuestaList) {
        this.adjuntoPropuestaList = adjuntoPropuestaList;
    }

    @XmlTransient
    public List<InformacionSobrePropuesta> getInformacionSobrePropuestaList() {
        return informacionSobrePropuestaList;
    }

    public void setInformacionSobrePropuestaList(List<InformacionSobrePropuesta> informacionSobrePropuestaList) {
        this.informacionSobrePropuestaList = informacionSobrePropuestaList;
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
        if (!(object instanceof Propuesta)) {
            return false;
        }
        Propuesta other = (Propuesta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Propuesta[ id=" + id + " ]";
    }
    
}
