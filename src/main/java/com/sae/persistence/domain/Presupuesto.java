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
@Table(name = "presupuesto", catalog = "bdsae", schema = "tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Presupuesto.findAll", query = "SELECT p FROM Presupuesto p"),
    @NamedQuery(name = "Presupuesto.findById", query = "SELECT p FROM Presupuesto p WHERE p.id = :id"),
    @NamedQuery(name = "Presupuesto.findByIdPropuesta", query = "SELECT p FROM Presupuesto p WHERE p.idPropuesta = :idPropuesta"),
    @NamedQuery(name = "Presupuesto.findByVersion", query = "SELECT p FROM Presupuesto p WHERE p.version = :version"),
    @NamedQuery(name = "Presupuesto.findByNombre", query = "SELECT p FROM Presupuesto p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Presupuesto.findByTotal", query = "SELECT p FROM Presupuesto p WHERE p.total = :total"),
    @NamedQuery(name = "Presupuesto.findByIdTarifaIva", query = "SELECT p FROM Presupuesto p WHERE p.idTarifaIva = :idTarifaIva"),
    @NamedQuery(name = "Presupuesto.findByTotalAiu", query = "SELECT p FROM Presupuesto p WHERE p.totalAiu = :totalAiu"),
    @NamedQuery(name = "Presupuesto.findByEstado", query = "SELECT p FROM Presupuesto p WHERE p.estado = :estado"),
    @NamedQuery(name = "Presupuesto.findByFechaRegistro", query = "SELECT p FROM Presupuesto p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Presupuesto.findByIdUsuario", query = "SELECT p FROM Presupuesto p WHERE p.idUsuario = :idUsuario"),
    @NamedQuery(name = "Presupuesto.findByIdMoneda", query = "SELECT p FROM Presupuesto p WHERE p.idMoneda = :idMoneda")})
public class Presupuesto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_propuesta")
    private Integer idPropuesta;
    @Column(name = "version")
    private Integer version;
    @Column(name = "nombre")
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private Double total;
    @Column(name = "id_tarifa_iva")
    private Integer idTarifaIva;
    @Column(name = "total_aiu")
    private Double totalAiu;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_moneda")
    private Integer idMoneda;
    @OneToMany(mappedBy = "idPresupuesto")
    private List<ListaPrecio> listaPrecioList;
    @OneToMany(mappedBy = "idPresupuesto")
    private List<AiuPresupuesto> aiuPresupuestoList;
    @OneToMany(mappedBy = "idPresupuesto")
    private List<ActaCobro> actaCobroList;
    @OneToMany(mappedBy = "idPresupuesto")
    private List<AiuEspecificoPresupuesto> aiuEspecificoPresupuestoList;
    @OneToMany(mappedBy = "idPresupuesto")
    private List<ActividadPresupuesto> actividadPresupuestoList;

    public Presupuesto() {
    }

    public Presupuesto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPropuesta() {
        return idPropuesta;
    }

    public void setIdPropuesta(Integer idPropuesta) {
        this.idPropuesta = idPropuesta;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getIdTarifaIva() {
        return idTarifaIva;
    }

    public void setIdTarifaIva(Integer idTarifaIva) {
        this.idTarifaIva = idTarifaIva;
    }

    public Double getTotalAiu() {
        return totalAiu;
    }

    public void setTotalAiu(Double totalAiu) {
        this.totalAiu = totalAiu;
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

    public Integer getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(Integer idMoneda) {
        this.idMoneda = idMoneda;
    }

    @XmlTransient
    public List<ListaPrecio> getListaPrecioList() {
        return listaPrecioList;
    }

    public void setListaPrecioList(List<ListaPrecio> listaPrecioList) {
        this.listaPrecioList = listaPrecioList;
    }

    @XmlTransient
    public List<AiuPresupuesto> getAiuPresupuestoList() {
        return aiuPresupuestoList;
    }

    public void setAiuPresupuestoList(List<AiuPresupuesto> aiuPresupuestoList) {
        this.aiuPresupuestoList = aiuPresupuestoList;
    }

    @XmlTransient
    public List<ActaCobro> getActaCobroList() {
        return actaCobroList;
    }

    public void setActaCobroList(List<ActaCobro> actaCobroList) {
        this.actaCobroList = actaCobroList;
    }

    @XmlTransient
    public List<AiuEspecificoPresupuesto> getAiuEspecificoPresupuestoList() {
        return aiuEspecificoPresupuestoList;
    }

    public void setAiuEspecificoPresupuestoList(List<AiuEspecificoPresupuesto> aiuEspecificoPresupuestoList) {
        this.aiuEspecificoPresupuestoList = aiuEspecificoPresupuestoList;
    }

    @XmlTransient
    public List<ActividadPresupuesto> getActividadPresupuestoList() {
        return actividadPresupuestoList;
    }

    public void setActividadPresupuestoList(List<ActividadPresupuesto> actividadPresupuestoList) {
        this.actividadPresupuestoList = actividadPresupuestoList;
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
        if (!(object instanceof Presupuesto)) {
            return false;
        }
        Presupuesto other = (Presupuesto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Presupuesto[ id=" + id + " ]";
    }
    
}
