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
@Table(name = "tipo_contrato_proyecto", catalog = "bdsae", schema = "tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoContratoProyecto.findAll", query = "SELECT t FROM TipoContratoProyecto t"),
    @NamedQuery(name = "TipoContratoProyecto.findById", query = "SELECT t FROM TipoContratoProyecto t WHERE t.id = :id"),
    @NamedQuery(name = "TipoContratoProyecto.findByNombre", query = "SELECT t FROM TipoContratoProyecto t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoContratoProyecto.findByEstado", query = "SELECT t FROM TipoContratoProyecto t WHERE t.estado = :estado"),
    @NamedQuery(name = "TipoContratoProyecto.findByFechaRegistro", query = "SELECT t FROM TipoContratoProyecto t WHERE t.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "TipoContratoProyecto.findByIdUsuario", query = "SELECT t FROM TipoContratoProyecto t WHERE t.idUsuario = :idUsuario")})
public class TipoContratoProyecto implements Serializable {
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
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_tipo_contratista", referencedColumnName = "id")
    @ManyToOne
    private TipoContratista idTipoContratista;
    @JoinColumn(name = "id_tipo_contratante", referencedColumnName = "id")
    @ManyToOne
    private TipoContratante idTipoContratante;
    @JoinColumn(name = "id_clase", referencedColumnName = "id")
    @ManyToOne
    private ClaseContratoProyecto idClase;
    @OneToMany(mappedBy = "idTipo")
    private List<ContratoProyectoProveedor> contratoProyectoProveedorList;

    public TipoContratoProyecto() {
    }

    public TipoContratoProyecto(Integer id) {
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

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public TipoContratista getIdTipoContratista() {
        return idTipoContratista;
    }

    public void setIdTipoContratista(TipoContratista idTipoContratista) {
        this.idTipoContratista = idTipoContratista;
    }

    public TipoContratante getIdTipoContratante() {
        return idTipoContratante;
    }

    public void setIdTipoContratante(TipoContratante idTipoContratante) {
        this.idTipoContratante = idTipoContratante;
    }

    public ClaseContratoProyecto getIdClase() {
        return idClase;
    }

    public void setIdClase(ClaseContratoProyecto idClase) {
        this.idClase = idClase;
    }

    @XmlTransient
    public List<ContratoProyectoProveedor> getContratoProyectoProveedorList() {
        return contratoProyectoProveedorList;
    }

    public void setContratoProyectoProveedorList(List<ContratoProyectoProveedor> contratoProyectoProveedorList) {
        this.contratoProyectoProveedorList = contratoProyectoProveedorList;
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
        if (!(object instanceof TipoContratoProyecto)) {
            return false;
        }
        TipoContratoProyecto other = (TipoContratoProyecto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.TipoContratoProyecto[ id=" + id + " ]";
    }
    
}
