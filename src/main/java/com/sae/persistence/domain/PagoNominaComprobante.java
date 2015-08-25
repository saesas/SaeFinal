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
@Table(name = "pago_nomina_comprobante", catalog = "bdsae", schema = "tesoreria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoNominaComprobante.findAll", query = "SELECT p FROM PagoNominaComprobante p"),
    @NamedQuery(name = "PagoNominaComprobante.findByIdComprobante", query = "SELECT p FROM PagoNominaComprobante p WHERE p.idComprobante = :idComprobante"),
    @NamedQuery(name = "PagoNominaComprobante.findByEstado", query = "SELECT p FROM PagoNominaComprobante p WHERE p.estado = :estado"),
    @NamedQuery(name = "PagoNominaComprobante.findByFechaRegistro", query = "SELECT p FROM PagoNominaComprobante p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "PagoNominaComprobante.findByIdUsuario", query = "SELECT p FROM PagoNominaComprobante p WHERE p.idUsuario = :idUsuario"),
    @NamedQuery(name = "PagoNominaComprobante.findById", query = "SELECT p FROM PagoNominaComprobante p WHERE p.id = :id")})
public class PagoNominaComprobante implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "id_comprobante")
    private Integer idComprobante;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_pago_nomina", referencedColumnName = "id")
    @ManyToOne
    private PagoNomina idPagoNomina;

    public PagoNominaComprobante() {
    }

    public PagoNominaComprobante(Integer id) {
        this.id = id;
    }

    public Integer getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(Integer idComprobante) {
        this.idComprobante = idComprobante;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PagoNomina getIdPagoNomina() {
        return idPagoNomina;
    }

    public void setIdPagoNomina(PagoNomina idPagoNomina) {
        this.idPagoNomina = idPagoNomina;
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
        if (!(object instanceof PagoNominaComprobante)) {
            return false;
        }
        PagoNominaComprobante other = (PagoNominaComprobante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.PagoNominaComprobante[ id=" + id + " ]";
    }
    
}
