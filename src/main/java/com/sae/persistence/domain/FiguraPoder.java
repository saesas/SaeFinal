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
@Table(name = "figura_poder", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FiguraPoder.findAll", query = "SELECT f FROM FiguraPoder f"),
    @NamedQuery(name = "FiguraPoder.findById", query = "SELECT f FROM FiguraPoder f WHERE f.id = :id"),
    @NamedQuery(name = "FiguraPoder.findByIdTercero", query = "SELECT f FROM FiguraPoder f WHERE f.idTercero = :idTercero"),
    @NamedQuery(name = "FiguraPoder.findByVenta", query = "SELECT f FROM FiguraPoder f WHERE f.venta = :venta"),
    @NamedQuery(name = "FiguraPoder.findByEstado", query = "SELECT f FROM FiguraPoder f WHERE f.estado = :estado"),
    @NamedQuery(name = "FiguraPoder.findByFechaRegistro", query = "SELECT f FROM FiguraPoder f WHERE f.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "FiguraPoder.findByIdUsuario", query = "SELECT f FROM FiguraPoder f WHERE f.idUsuario = :idUsuario")})
public class FiguraPoder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_tercero")
    private Integer idTercero;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "venta")
    private Double venta;
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
    @OneToMany(mappedBy = "idFiguraPoder")
    private List<ContactoFiguraTercero> contactoFiguraTerceroList;

    public FiguraPoder() {
    }

    public FiguraPoder(Integer id) {
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

    public Double getVenta() {
        return venta;
    }

    public void setVenta(Double venta) {
        this.venta = venta;
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

    @XmlTransient
    public List<ContactoFiguraTercero> getContactoFiguraTerceroList() {
        return contactoFiguraTerceroList;
    }

    public void setContactoFiguraTerceroList(List<ContactoFiguraTercero> contactoFiguraTerceroList) {
        this.contactoFiguraTerceroList = contactoFiguraTerceroList;
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
        if (!(object instanceof FiguraPoder)) {
            return false;
        }
        FiguraPoder other = (FiguraPoder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.FiguraPoder[ id=" + id + " ]";
    }
    
}
