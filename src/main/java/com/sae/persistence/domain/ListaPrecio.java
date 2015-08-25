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
@Table(name = "lista_precio", catalog = "bdsae", schema = "tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListaPrecio.findAll", query = "SELECT l FROM ListaPrecio l"),
    @NamedQuery(name = "ListaPrecio.findById", query = "SELECT l FROM ListaPrecio l WHERE l.id = :id"),
    @NamedQuery(name = "ListaPrecio.findByNombre", query = "SELECT l FROM ListaPrecio l WHERE l.nombre = :nombre"),
    @NamedQuery(name = "ListaPrecio.findByIdInsumo", query = "SELECT l FROM ListaPrecio l WHERE l.idInsumo = :idInsumo"),
    @NamedQuery(name = "ListaPrecio.findByIdUnidad", query = "SELECT l FROM ListaPrecio l WHERE l.idUnidad = :idUnidad"),
    @NamedQuery(name = "ListaPrecio.findByIdMoneda", query = "SELECT l FROM ListaPrecio l WHERE l.idMoneda = :idMoneda"),
    @NamedQuery(name = "ListaPrecio.findByValor", query = "SELECT l FROM ListaPrecio l WHERE l.valor = :valor"),
    @NamedQuery(name = "ListaPrecio.findByEstado", query = "SELECT l FROM ListaPrecio l WHERE l.estado = :estado"),
    @NamedQuery(name = "ListaPrecio.findByFechaRegistro", query = "SELECT l FROM ListaPrecio l WHERE l.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ListaPrecio.findByIdUsuario", query = "SELECT l FROM ListaPrecio l WHERE l.idUsuario = :idUsuario")})
public class ListaPrecio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "id_insumo")
    private Integer idInsumo;
    @Column(name = "id_unidad")
    private Integer idUnidad;
    @Column(name = "id_moneda")
    private Integer idMoneda;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_presupuesto", referencedColumnName = "id")
    @ManyToOne
    private Presupuesto idPresupuesto;

    public ListaPrecio() {
    }

    public ListaPrecio(Integer id) {
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

    public Integer getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(Integer idInsumo) {
        this.idInsumo = idInsumo;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public Integer getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(Integer idMoneda) {
        this.idMoneda = idMoneda;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
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
        if (!(object instanceof ListaPrecio)) {
            return false;
        }
        ListaPrecio other = (ListaPrecio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ListaPrecio[ id=" + id + " ]";
    }
    
}
