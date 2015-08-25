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
@Table(name = "adjunto_bitacora", catalog = "bdsae", schema = "tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdjuntoBitacora.findAll", query = "SELECT a FROM AdjuntoBitacora a"),
    @NamedQuery(name = "AdjuntoBitacora.findById", query = "SELECT a FROM AdjuntoBitacora a WHERE a.id = :id"),
    @NamedQuery(name = "AdjuntoBitacora.findByEstado", query = "SELECT a FROM AdjuntoBitacora a WHERE a.estado = :estado"),
    @NamedQuery(name = "AdjuntoBitacora.findByIdTipoAdjunto", query = "SELECT a FROM AdjuntoBitacora a WHERE a.idTipoAdjunto = :idTipoAdjunto"),
    @NamedQuery(name = "AdjuntoBitacora.findByFechaRegistro", query = "SELECT a FROM AdjuntoBitacora a WHERE a.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "AdjuntoBitacora.findByIdUsuario", query = "SELECT a FROM AdjuntoBitacora a WHERE a.idUsuario = :idUsuario")})
public class AdjuntoBitacora implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_tipo_adjunto")
    private Integer idTipoAdjunto;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_bitacora", referencedColumnName = "id")
    @ManyToOne
    private Bitacora idBitacora;

    public AdjuntoBitacora() {
    }

    public AdjuntoBitacora(Integer id) {
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

    public Integer getIdTipoAdjunto() {
        return idTipoAdjunto;
    }

    public void setIdTipoAdjunto(Integer idTipoAdjunto) {
        this.idTipoAdjunto = idTipoAdjunto;
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

    public Bitacora getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Bitacora idBitacora) {
        this.idBitacora = idBitacora;
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
        if (!(object instanceof AdjuntoBitacora)) {
            return false;
        }
        AdjuntoBitacora other = (AdjuntoBitacora) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.AdjuntoBitacora[ id=" + id + " ]";
    }
    
}
