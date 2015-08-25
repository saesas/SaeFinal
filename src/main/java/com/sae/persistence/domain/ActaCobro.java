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
@Table(name = "acta_cobro", catalog = "bdsae", schema = "tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActaCobro.findAll", query = "SELECT a FROM ActaCobro a"),
    @NamedQuery(name = "ActaCobro.findById", query = "SELECT a FROM ActaCobro a WHERE a.id = :id"),
    @NamedQuery(name = "ActaCobro.findByCodigo", query = "SELECT a FROM ActaCobro a WHERE a.codigo = :codigo"),
    @NamedQuery(name = "ActaCobro.findByEstado", query = "SELECT a FROM ActaCobro a WHERE a.estado = :estado"),
    @NamedQuery(name = "ActaCobro.findByFechaInicio", query = "SELECT a FROM ActaCobro a WHERE a.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "ActaCobro.findByFechaFin", query = "SELECT a FROM ActaCobro a WHERE a.fechaFin = :fechaFin"),
    @NamedQuery(name = "ActaCobro.findByAmortizacionAnticipo", query = "SELECT a FROM ActaCobro a WHERE a.amortizacionAnticipo = :amortizacionAnticipo"),
    @NamedQuery(name = "ActaCobro.findByNumeroActa", query = "SELECT a FROM ActaCobro a WHERE a.numeroActa = :numeroActa"),
    @NamedQuery(name = "ActaCobro.findByFechaRegistro", query = "SELECT a FROM ActaCobro a WHERE a.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ActaCobro.findByIdUsuario", query = "SELECT a FROM ActaCobro a WHERE a.idUsuario = :idUsuario")})
public class ActaCobro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amortizacion_anticipo")
    private Double amortizacionAnticipo;
    @Column(name = "numero_acta")
    private Integer numeroActa;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @OneToMany(mappedBy = "idActaCobro")
    private List<ActaCobroActividad> actaCobroActividadList;
    @JoinColumn(name = "id_presupuesto", referencedColumnName = "id")
    @ManyToOne
    private Presupuesto idPresupuesto;

    public ActaCobro() {
    }

    public ActaCobro(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Double getAmortizacionAnticipo() {
        return amortizacionAnticipo;
    }

    public void setAmortizacionAnticipo(Double amortizacionAnticipo) {
        this.amortizacionAnticipo = amortizacionAnticipo;
    }

    public Integer getNumeroActa() {
        return numeroActa;
    }

    public void setNumeroActa(Integer numeroActa) {
        this.numeroActa = numeroActa;
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
    public List<ActaCobroActividad> getActaCobroActividadList() {
        return actaCobroActividadList;
    }

    public void setActaCobroActividadList(List<ActaCobroActividad> actaCobroActividadList) {
        this.actaCobroActividadList = actaCobroActividadList;
    }

    public Presupuesto getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(Presupuesto idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
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
        if (!(object instanceof ActaCobro)) {
            return false;
        }
        ActaCobro other = (ActaCobro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ActaCobro[ id=" + id + " ]";
    }
    
}
