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
@Table(name = "grupo_nominal", catalog = "bdsae", schema = "talento_humano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoNominal.findAll", query = "SELECT g FROM GrupoNominal g"),
    @NamedQuery(name = "GrupoNominal.findById", query = "SELECT g FROM GrupoNominal g WHERE g.id = :id"),
    @NamedQuery(name = "GrupoNominal.findByEstado", query = "SELECT g FROM GrupoNominal g WHERE g.estado = :estado"),
    @NamedQuery(name = "GrupoNominal.findByCodigo", query = "SELECT g FROM GrupoNominal g WHERE g.codigo = :codigo"),
    @NamedQuery(name = "GrupoNominal.findByIdProyecto", query = "SELECT g FROM GrupoNominal g WHERE g.idProyecto = :idProyecto"),
    @NamedQuery(name = "GrupoNominal.findByFechaRegistro", query = "SELECT g FROM GrupoNominal g WHERE g.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "GrupoNominal.findByIdUsuario", query = "SELECT g FROM GrupoNominal g WHERE g.idUsuario = :idUsuario")})
public class GrupoNominal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "id_proyecto")
    private Integer idProyecto;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @OneToMany(mappedBy = "idGrupoNominal")
    private List<ContratoTercero> contratoTerceroList;
    @OneToMany(mappedBy = "idGrupoNominal")
    private List<Nomina> nominaList;
    @JoinColumn(name = "id_periodo", referencedColumnName = "id")
    @ManyToOne
    private PeriodoPago idPeriodo;

    public GrupoNominal() {
    }

    public GrupoNominal(Integer id) {
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
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
    public List<ContratoTercero> getContratoTerceroList() {
        return contratoTerceroList;
    }

    public void setContratoTerceroList(List<ContratoTercero> contratoTerceroList) {
        this.contratoTerceroList = contratoTerceroList;
    }

    @XmlTransient
    public List<Nomina> getNominaList() {
        return nominaList;
    }

    public void setNominaList(List<Nomina> nominaList) {
        this.nominaList = nominaList;
    }

    public PeriodoPago getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(PeriodoPago idPeriodo) {
        this.idPeriodo = idPeriodo;
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
        if (!(object instanceof GrupoNominal)) {
            return false;
        }
        GrupoNominal other = (GrupoNominal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.GrupoNominal[ id=" + id + " ]";
    }
    
}
