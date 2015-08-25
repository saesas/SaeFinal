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
@Table(name = "comprobante", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comprobante.findAll", query = "SELECT c FROM Comprobante c"),
    @NamedQuery(name = "Comprobante.findByCodigo", query = "SELECT c FROM Comprobante c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "Comprobante.findByConcepto", query = "SELECT c FROM Comprobante c WHERE c.concepto = :concepto"),
    @NamedQuery(name = "Comprobante.findByFechaGeneracion", query = "SELECT c FROM Comprobante c WHERE c.fechaGeneracion = :fechaGeneracion"),
    @NamedQuery(name = "Comprobante.findByFechaRegistro", query = "SELECT c FROM Comprobante c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Comprobante.findByImpreso", query = "SELECT c FROM Comprobante c WHERE c.impreso = :impreso"),
    @NamedQuery(name = "Comprobante.findByVisualizado", query = "SELECT c FROM Comprobante c WHERE c.visualizado = :visualizado"),
    @NamedQuery(name = "Comprobante.findByDescargado", query = "SELECT c FROM Comprobante c WHERE c.descargado = :descargado"),
    @NamedQuery(name = "Comprobante.findByIdUsuario", query = "SELECT c FROM Comprobante c WHERE c.idUsuario = :idUsuario"),
    @NamedQuery(name = "Comprobante.findById", query = "SELECT c FROM Comprobante c WHERE c.id = :id")})
public class Comprobante implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "concepto")
    private String concepto;
    @Column(name = "fecha_generacion")
    @Temporal(TemporalType.DATE)
    private Date fechaGeneracion;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "impreso")
    private Boolean impreso;
    @Column(name = "visualizado")
    private Boolean visualizado;
    @Column(name = "descargado")
    private Boolean descargado;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id")
    @ManyToOne
    private TipoComprobante idTipo;
    @JoinColumn(name = "id_proceso", referencedColumnName = "id")
    @ManyToOne
    private ProcesoComprobante idProceso;
    @OneToMany(mappedBy = "idComprobante")
    private List<Reintegro> reintegroList;
    @OneToMany(mappedBy = "idComprobante")
    private List<ComprobanteCierreDetalle> comprobanteCierreDetalleList;
    @OneToMany(mappedBy = "idComprobante")
    private List<MovimientoPuc> movimientoPucList;

    public Comprobante() {
    }

    public Comprobante(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Boolean getImpreso() {
        return impreso;
    }

    public void setImpreso(Boolean impreso) {
        this.impreso = impreso;
    }

    public Boolean getVisualizado() {
        return visualizado;
    }

    public void setVisualizado(Boolean visualizado) {
        this.visualizado = visualizado;
    }

    public Boolean getDescargado() {
        return descargado;
    }

    public void setDescargado(Boolean descargado) {
        this.descargado = descargado;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoComprobante getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(TipoComprobante idTipo) {
        this.idTipo = idTipo;
    }

    public ProcesoComprobante getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(ProcesoComprobante idProceso) {
        this.idProceso = idProceso;
    }

    @XmlTransient
    public List<Reintegro> getReintegroList() {
        return reintegroList;
    }

    public void setReintegroList(List<Reintegro> reintegroList) {
        this.reintegroList = reintegroList;
    }

    @XmlTransient
    public List<ComprobanteCierreDetalle> getComprobanteCierreDetalleList() {
        return comprobanteCierreDetalleList;
    }

    public void setComprobanteCierreDetalleList(List<ComprobanteCierreDetalle> comprobanteCierreDetalleList) {
        this.comprobanteCierreDetalleList = comprobanteCierreDetalleList;
    }

    @XmlTransient
    public List<MovimientoPuc> getMovimientoPucList() {
        return movimientoPucList;
    }

    public void setMovimientoPucList(List<MovimientoPuc> movimientoPucList) {
        this.movimientoPucList = movimientoPucList;
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
        if (!(object instanceof Comprobante)) {
            return false;
        }
        Comprobante other = (Comprobante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Comprobante[ id=" + id + " ]";
    }
    
}
