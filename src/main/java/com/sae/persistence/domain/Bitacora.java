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
@Table(name = "bitacora", catalog = "bdsae", schema = "tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bitacora.findAll", query = "SELECT b FROM Bitacora b"),
    @NamedQuery(name = "Bitacora.findById", query = "SELECT b FROM Bitacora b WHERE b.id = :id"),
    @NamedQuery(name = "Bitacora.findByEstado", query = "SELECT b FROM Bitacora b WHERE b.estado = :estado"),
    @NamedQuery(name = "Bitacora.findByGestionTecnica", query = "SELECT b FROM Bitacora b WHERE b.gestionTecnica = :gestionTecnica"),
    @NamedQuery(name = "Bitacora.findByGestionSiso", query = "SELECT b FROM Bitacora b WHERE b.gestionSiso = :gestionSiso"),
    @NamedQuery(name = "Bitacora.findByGestionAmbiental", query = "SELECT b FROM Bitacora b WHERE b.gestionAmbiental = :gestionAmbiental"),
    @NamedQuery(name = "Bitacora.findByDesicion", query = "SELECT b FROM Bitacora b WHERE b.desicion = :desicion"),
    @NamedQuery(name = "Bitacora.findByFechaRegistro", query = "SELECT b FROM Bitacora b WHERE b.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Bitacora.findByIdUsuario", query = "SELECT b FROM Bitacora b WHERE b.idUsuario = :idUsuario")})
public class Bitacora implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "gestion_tecnica")
    private String gestionTecnica;
    @Column(name = "gestion_siso")
    private String gestionSiso;
    @Column(name = "gestion_ambiental")
    private String gestionAmbiental;
    @Column(name = "desicion")
    private String desicion;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @OneToMany(mappedBy = "idBitacora")
    private List<InsumoGastado> insumoGastadoList;
    @JoinColumn(name = "id_proyecto", referencedColumnName = "id")
    @ManyToOne
    private Proyecto idProyecto;
    @OneToMany(mappedBy = "idBitacora")
    private List<AdjuntoBitacora> adjuntoBitacoraList;

    public Bitacora() {
    }

    public Bitacora(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getGestionTecnica() {
        return gestionTecnica;
    }

    public void setGestionTecnica(String gestionTecnica) {
        this.gestionTecnica = gestionTecnica;
    }

    public String getGestionSiso() {
        return gestionSiso;
    }

    public void setGestionSiso(String gestionSiso) {
        this.gestionSiso = gestionSiso;
    }

    public String getGestionAmbiental() {
        return gestionAmbiental;
    }

    public void setGestionAmbiental(String gestionAmbiental) {
        this.gestionAmbiental = gestionAmbiental;
    }

    public String getDesicion() {
        return desicion;
    }

    public void setDesicion(String desicion) {
        this.desicion = desicion;
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
    public List<InsumoGastado> getInsumoGastadoList() {
        return insumoGastadoList;
    }

    public void setInsumoGastadoList(List<InsumoGastado> insumoGastadoList) {
        this.insumoGastadoList = insumoGastadoList;
    }

    public Proyecto getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Proyecto idProyecto) {
        this.idProyecto = idProyecto;
    }

    @XmlTransient
    public List<AdjuntoBitacora> getAdjuntoBitacoraList() {
        return adjuntoBitacoraList;
    }

    public void setAdjuntoBitacoraList(List<AdjuntoBitacora> adjuntoBitacoraList) {
        this.adjuntoBitacoraList = adjuntoBitacoraList;
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
        if (!(object instanceof Bitacora)) {
            return false;
        }
        Bitacora other = (Bitacora) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Bitacora[ id=" + id + " ]";
    }
    
}
