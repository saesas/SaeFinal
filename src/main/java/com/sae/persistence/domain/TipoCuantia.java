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
@Table(name = "tipo_cuantia", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCuantia.findAll", query = "SELECT t FROM TipoCuantia t"),
    @NamedQuery(name = "TipoCuantia.findById", query = "SELECT t FROM TipoCuantia t WHERE t.id = :id"),
    @NamedQuery(name = "TipoCuantia.findByNombre", query = "SELECT t FROM TipoCuantia t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoCuantia.findByMayor", query = "SELECT t FROM TipoCuantia t WHERE t.mayor = :mayor"),
    @NamedQuery(name = "TipoCuantia.findByMenor", query = "SELECT t FROM TipoCuantia t WHERE t.menor = :menor"),
    @NamedQuery(name = "TipoCuantia.findByEstado", query = "SELECT t FROM TipoCuantia t WHERE t.estado = :estado"),
    @NamedQuery(name = "TipoCuantia.findByFechaRegistro", query = "SELECT t FROM TipoCuantia t WHERE t.fechaRegistro = :fechaRegistro")})
public class TipoCuantia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "mayor")
    private Double mayor;
    @Column(name = "menor")
    private Double menor;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @JoinColumn(name = "id_procedimiento_juridico", referencedColumnName = "id")
    @ManyToOne
    private Clase idProcedimientoJuridico;

    public TipoCuantia() {
    }

    public TipoCuantia(Integer id) {
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

    public Double getMayor() {
        return mayor;
    }

    public void setMayor(Double mayor) {
        this.mayor = mayor;
    }

    public Double getMenor() {
        return menor;
    }

    public void setMenor(Double menor) {
        this.menor = menor;
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

    public Clase getIdProcedimientoJuridico() {
        return idProcedimientoJuridico;
    }

    public void setIdProcedimientoJuridico(Clase idProcedimientoJuridico) {
        this.idProcedimientoJuridico = idProcedimientoJuridico;
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
        if (!(object instanceof TipoCuantia)) {
            return false;
        }
        TipoCuantia other = (TipoCuantia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.TipoCuantia[ id=" + id + " ]";
    }
    
}
