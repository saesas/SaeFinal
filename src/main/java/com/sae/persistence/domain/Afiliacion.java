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
@Table(name = "afiliacion", catalog = "bdsae", schema = "talento_humano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Afiliacion.findAll", query = "SELECT a FROM Afiliacion a"),
    @NamedQuery(name = "Afiliacion.findById", query = "SELECT a FROM Afiliacion a WHERE a.id = :id"),
    @NamedQuery(name = "Afiliacion.findByEstado", query = "SELECT a FROM Afiliacion a WHERE a.estado = :estado"),
    @NamedQuery(name = "Afiliacion.findByIdTercero", query = "SELECT a FROM Afiliacion a WHERE a.idTercero = :idTercero"),
    @NamedQuery(name = "Afiliacion.findByIdEps", query = "SELECT a FROM Afiliacion a WHERE a.idEps = :idEps"),
    @NamedQuery(name = "Afiliacion.findByIdArp", query = "SELECT a FROM Afiliacion a WHERE a.idArp = :idArp"),
    @NamedQuery(name = "Afiliacion.findByIdAfp", query = "SELECT a FROM Afiliacion a WHERE a.idAfp = :idAfp"),
    @NamedQuery(name = "Afiliacion.findByIdCcf", query = "SELECT a FROM Afiliacion a WHERE a.idCcf = :idCcf"),
    @NamedQuery(name = "Afiliacion.findByPensionado", query = "SELECT a FROM Afiliacion a WHERE a.pensionado = :pensionado"),
    @NamedQuery(name = "Afiliacion.findByIdUsuario", query = "SELECT a FROM Afiliacion a WHERE a.idUsuario = :idUsuario"),
    @NamedQuery(name = "Afiliacion.findByFechaRegistro", query = "SELECT a FROM Afiliacion a WHERE a.fechaRegistro = :fechaRegistro")})
public class Afiliacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_tercero")
    private Integer idTercero;
    @Column(name = "id_eps")
    private Integer idEps;
    @Column(name = "id_arp")
    private Integer idArp;
    @Column(name = "id_afp")
    private Integer idAfp;
    @Column(name = "id_ccf")
    private Integer idCcf;
    @Column(name = "pensionado")
    private Boolean pensionado;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "idAfiliacion")
    private List<AdjuntoAfiliacion> adjuntoAfiliacionList;
    @OneToMany(mappedBy = "idAfiliacion")
    private List<ContratoTercero> contratoTerceroList;

    public Afiliacion() {
    }

    public Afiliacion(Integer id) {
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

    public Integer getIdTercero() {
        return idTercero;
    }

    public void setIdTercero(Integer idTercero) {
        this.idTercero = idTercero;
    }

    public Integer getIdEps() {
        return idEps;
    }

    public void setIdEps(Integer idEps) {
        this.idEps = idEps;
    }

    public Integer getIdArp() {
        return idArp;
    }

    public void setIdArp(Integer idArp) {
        this.idArp = idArp;
    }

    public Integer getIdAfp() {
        return idAfp;
    }

    public void setIdAfp(Integer idAfp) {
        this.idAfp = idAfp;
    }

    public Integer getIdCcf() {
        return idCcf;
    }

    public void setIdCcf(Integer idCcf) {
        this.idCcf = idCcf;
    }

    public Boolean getPensionado() {
        return pensionado;
    }

    public void setPensionado(Boolean pensionado) {
        this.pensionado = pensionado;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @XmlTransient
    public List<AdjuntoAfiliacion> getAdjuntoAfiliacionList() {
        return adjuntoAfiliacionList;
    }

    public void setAdjuntoAfiliacionList(List<AdjuntoAfiliacion> adjuntoAfiliacionList) {
        this.adjuntoAfiliacionList = adjuntoAfiliacionList;
    }

    @XmlTransient
    public List<ContratoTercero> getContratoTerceroList() {
        return contratoTerceroList;
    }

    public void setContratoTerceroList(List<ContratoTercero> contratoTerceroList) {
        this.contratoTerceroList = contratoTerceroList;
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
        if (!(object instanceof Afiliacion)) {
            return false;
        }
        Afiliacion other = (Afiliacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Afiliacion[ id=" + id + " ]";
    }
    
}
