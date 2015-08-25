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
@Table(name = "aporte_pago_soi", catalog = "bdsae", schema = "tesoreria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AportePagoSoi.findAll", query = "SELECT a FROM AportePagoSoi a"),
    @NamedQuery(name = "AportePagoSoi.findById", query = "SELECT a FROM AportePagoSoi a WHERE a.id = :id"),
    @NamedQuery(name = "AportePagoSoi.findByValorAporte", query = "SELECT a FROM AportePagoSoi a WHERE a.valorAporte = :valorAporte"),
    @NamedQuery(name = "AportePagoSoi.findByIntereses", query = "SELECT a FROM AportePagoSoi a WHERE a.intereses = :intereses"),
    @NamedQuery(name = "AportePagoSoi.findByIncapacidad", query = "SELECT a FROM AportePagoSoi a WHERE a.incapacidad = :incapacidad"),
    @NamedQuery(name = "AportePagoSoi.findByFechaRegistro", query = "SELECT a FROM AportePagoSoi a WHERE a.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "AportePagoSoi.findByIdUsuario", query = "SELECT a FROM AportePagoSoi a WHERE a.idUsuario = :idUsuario"),
    @NamedQuery(name = "AportePagoSoi.findByIdEntidad", query = "SELECT a FROM AportePagoSoi a WHERE a.idEntidad = :idEntidad")})
public class AportePagoSoi implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_aporte")
    private Double valorAporte;
    @Column(name = "intereses")
    private Double intereses;
    @Column(name = "incapacidad")
    private Double incapacidad;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_entidad")
    private Integer idEntidad;
    @JoinColumn(name = "id_pago_soi", referencedColumnName = "id")
    @ManyToOne
    private PagoSoi idPagoSoi;

    public AportePagoSoi() {
    }

    public AportePagoSoi(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValorAporte() {
        return valorAporte;
    }

    public void setValorAporte(Double valorAporte) {
        this.valorAporte = valorAporte;
    }

    public Double getIntereses() {
        return intereses;
    }

    public void setIntereses(Double intereses) {
        this.intereses = intereses;
    }

    public Double getIncapacidad() {
        return incapacidad;
    }

    public void setIncapacidad(Double incapacidad) {
        this.incapacidad = incapacidad;
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

    public Integer getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Integer idEntidad) {
        this.idEntidad = idEntidad;
    }

    public PagoSoi getIdPagoSoi() {
        return idPagoSoi;
    }

    public void setIdPagoSoi(PagoSoi idPagoSoi) {
        this.idPagoSoi = idPagoSoi;
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
        if (!(object instanceof AportePagoSoi)) {
            return false;
        }
        AportePagoSoi other = (AportePagoSoi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.AportePagoSoi[ id=" + id + " ]";
    }
    
}
