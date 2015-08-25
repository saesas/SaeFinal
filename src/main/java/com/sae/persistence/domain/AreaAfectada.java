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
@Table(name = "area_afectada", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AreaAfectada.findAll", query = "SELECT a FROM AreaAfectada a"),
    @NamedQuery(name = "AreaAfectada.findById", query = "SELECT a FROM AreaAfectada a WHERE a.id = :id"),
    @NamedQuery(name = "AreaAfectada.findByNombre", query = "SELECT a FROM AreaAfectada a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "AreaAfectada.findByEstado", query = "SELECT a FROM AreaAfectada a WHERE a.estado = :estado"),
    @NamedQuery(name = "AreaAfectada.findByFechaRegistro", query = "SELECT a FROM AreaAfectada a WHERE a.fechaRegistro = :fechaRegistro")})
public class AreaAfectada implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "idArea")
    private List<AreaAfectadaNormatividad> areaAfectadaNormatividadList;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id")
    @ManyToOne
    private TipoAreaAfectadaNorma idTipo;

    public AreaAfectada() {
    }

    public AreaAfectada(Integer id) {
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

    @XmlTransient
    public List<AreaAfectadaNormatividad> getAreaAfectadaNormatividadList() {
        return areaAfectadaNormatividadList;
    }

    public void setAreaAfectadaNormatividadList(List<AreaAfectadaNormatividad> areaAfectadaNormatividadList) {
        this.areaAfectadaNormatividadList = areaAfectadaNormatividadList;
    }

    public TipoAreaAfectadaNorma getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(TipoAreaAfectadaNorma idTipo) {
        this.idTipo = idTipo;
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
        if (!(object instanceof AreaAfectada)) {
            return false;
        }
        AreaAfectada other = (AreaAfectada) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.AreaAfectada[ id=" + id + " ]";
    }
    
}
