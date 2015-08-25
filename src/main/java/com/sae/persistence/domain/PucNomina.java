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
@Table(name = "puc_nomina", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PucNomina.findAll", query = "SELECT p FROM PucNomina p"),
    @NamedQuery(name = "PucNomina.findById", query = "SELECT p FROM PucNomina p WHERE p.id = :id"),
    @NamedQuery(name = "PucNomina.findByEstado", query = "SELECT p FROM PucNomina p WHERE p.estado = :estado"),
    @NamedQuery(name = "PucNomina.findByFechaRegistro", query = "SELECT p FROM PucNomina p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "PucNomina.findByIdUsuario", query = "SELECT p FROM PucNomina p WHERE p.idUsuario = :idUsuario"),
    @NamedQuery(name = "PucNomina.findByIdRazonSocial", query = "SELECT p FROM PucNomina p WHERE p.idRazonSocial = :idRazonSocial")})
public class PucNomina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @JoinColumn(name = "id_puc_debito", referencedColumnName = "id")
    @ManyToOne
    private Puc idPucDebito;
    @JoinColumn(name = "id_puc_credito", referencedColumnName = "id")
    @ManyToOne
    private Puc idPucCredito;
    @JoinColumn(name = "id_concepto", referencedColumnName = "id")
    @ManyToOne
    private ConceptoNomina idConcepto;

    public PucNomina() {
    }

    public PucNomina(Integer id) {
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

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    public Puc getIdPucDebito() {
        return idPucDebito;
    }

    public void setIdPucDebito(Puc idPucDebito) {
        this.idPucDebito = idPucDebito;
    }

    public Puc getIdPucCredito() {
        return idPucCredito;
    }

    public void setIdPucCredito(Puc idPucCredito) {
        this.idPucCredito = idPucCredito;
    }

    public ConceptoNomina getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(ConceptoNomina idConcepto) {
        this.idConcepto = idConcepto;
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
        if (!(object instanceof PucNomina)) {
            return false;
        }
        PucNomina other = (PucNomina) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.PucNomina[ id=" + id + " ]";
    }
    
}
