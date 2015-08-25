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
@Table(name = "informacion_sobre_propuesta", catalog = "bdsae", schema = "licitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InformacionSobrePropuesta.findAll", query = "SELECT i FROM InformacionSobrePropuesta i"),
    @NamedQuery(name = "InformacionSobrePropuesta.findById", query = "SELECT i FROM InformacionSobrePropuesta i WHERE i.id = :id"),
    @NamedQuery(name = "InformacionSobrePropuesta.findByValor", query = "SELECT i FROM InformacionSobrePropuesta i WHERE i.valor = :valor"),
    @NamedQuery(name = "InformacionSobrePropuesta.findByEstado", query = "SELECT i FROM InformacionSobrePropuesta i WHERE i.estado = :estado"),
    @NamedQuery(name = "InformacionSobrePropuesta.findByFechaRegistro", query = "SELECT i FROM InformacionSobrePropuesta i WHERE i.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "InformacionSobrePropuesta.findByIdUsuario", query = "SELECT i FROM InformacionSobrePropuesta i WHERE i.idUsuario = :idUsuario")})
public class InformacionSobrePropuesta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "valor")
    private String valor;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_item", referencedColumnName = "id")
    @ManyToOne
    private SobrePropuesta idItem;
    @JoinColumn(name = "id_propuesta", referencedColumnName = "id")
    @ManyToOne
    private Propuesta idPropuesta;

    public InformacionSobrePropuesta() {
    }

    public InformacionSobrePropuesta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
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

    public SobrePropuesta getIdItem() {
        return idItem;
    }

    public void setIdItem(SobrePropuesta idItem) {
        this.idItem = idItem;
    }

    public Propuesta getIdPropuesta() {
        return idPropuesta;
    }

    public void setIdPropuesta(Propuesta idPropuesta) {
        this.idPropuesta = idPropuesta;
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
        if (!(object instanceof InformacionSobrePropuesta)) {
            return false;
        }
        InformacionSobrePropuesta other = (InformacionSobrePropuesta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.InformacionSobrePropuesta[ id=" + id + " ]";
    }
    
}
