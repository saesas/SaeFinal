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
@Table(name = "actividad_presupuesto", catalog = "bdsae", schema = "tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActividadPresupuesto.findAll", query = "SELECT a FROM ActividadPresupuesto a"),
    @NamedQuery(name = "ActividadPresupuesto.findById", query = "SELECT a FROM ActividadPresupuesto a WHERE a.id = :id"),
    @NamedQuery(name = "ActividadPresupuesto.findByOrden", query = "SELECT a FROM ActividadPresupuesto a WHERE a.orden = :orden"),
    @NamedQuery(name = "ActividadPresupuesto.findByItem", query = "SELECT a FROM ActividadPresupuesto a WHERE a.item = :item"),
    @NamedQuery(name = "ActividadPresupuesto.findByIdUnidad", query = "SELECT a FROM ActividadPresupuesto a WHERE a.idUnidad = :idUnidad"),
    @NamedQuery(name = "ActividadPresupuesto.findBySubtotal", query = "SELECT a FROM ActividadPresupuesto a WHERE a.subtotal = :subtotal"),
    @NamedQuery(name = "ActividadPresupuesto.findByFechaRegistro", query = "SELECT a FROM ActividadPresupuesto a WHERE a.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ActividadPresupuesto.findByIdUsuario", query = "SELECT a FROM ActividadPresupuesto a WHERE a.idUsuario = :idUsuario"),
    @NamedQuery(name = "ActividadPresupuesto.findByIdMoneda", query = "SELECT a FROM ActividadPresupuesto a WHERE a.idMoneda = :idMoneda")})
public class ActividadPresupuesto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "orden")
    private String orden;
    @Column(name = "item")
    private String item;
    @Column(name = "id_unidad")
    private Integer idUnidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "subtotal")
    private Double subtotal;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_moneda")
    private Integer idMoneda;
    @JoinColumn(name = "id_presupuesto", referencedColumnName = "id")
    @ManyToOne
    private Presupuesto idPresupuesto;
    @JoinColumn(name = "id_actividad", referencedColumnName = "id")
    @ManyToOne
    private Actividad idActividad;
    @OneToMany(mappedBy = "idActividadPresupuesto")
    private List<ActividadInsumo> actividadInsumoList;

    public ActividadPresupuesto() {
    }

    public ActividadPresupuesto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
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

    public Integer getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(Integer idMoneda) {
        this.idMoneda = idMoneda;
    }

    public Presupuesto getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(Presupuesto idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    public Actividad getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Actividad idActividad) {
        this.idActividad = idActividad;
    }

    @XmlTransient
    public List<ActividadInsumo> getActividadInsumoList() {
        return actividadInsumoList;
    }

    public void setActividadInsumoList(List<ActividadInsumo> actividadInsumoList) {
        this.actividadInsumoList = actividadInsumoList;
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
        if (!(object instanceof ActividadPresupuesto)) {
            return false;
        }
        ActividadPresupuesto other = (ActividadPresupuesto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ActividadPresupuesto[ id=" + id + " ]";
    }
    
}
