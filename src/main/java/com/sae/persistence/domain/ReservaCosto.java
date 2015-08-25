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
@Table(name = "reserva_costo", catalog = "bdsae", schema = "compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReservaCosto.findAll", query = "SELECT r FROM ReservaCosto r"),
    @NamedQuery(name = "ReservaCosto.findById", query = "SELECT r FROM ReservaCosto r WHERE r.id = :id"),
    @NamedQuery(name = "ReservaCosto.findByEstado", query = "SELECT r FROM ReservaCosto r WHERE r.estado = :estado"),
    @NamedQuery(name = "ReservaCosto.findByIdServicio", query = "SELECT r FROM ReservaCosto r WHERE r.idServicio = :idServicio"),
    @NamedQuery(name = "ReservaCosto.findByValor", query = "SELECT r FROM ReservaCosto r WHERE r.valor = :valor"),
    @NamedQuery(name = "ReservaCosto.findByFechaRegistro", query = "SELECT r FROM ReservaCosto r WHERE r.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ReservaCosto.findByIdUsuario", query = "SELECT r FROM ReservaCosto r WHERE r.idUsuario = :idUsuario")})
public class ReservaCosto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_servicio")
    private Integer idServicio;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_reserva", referencedColumnName = "id")
    @ManyToOne
    private Reserva idReserva;

    public ReservaCosto() {
    }

    public ReservaCosto(Integer id) {
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

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
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

    public Reserva getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Reserva idReserva) {
        this.idReserva = idReserva;
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
        if (!(object instanceof ReservaCosto)) {
            return false;
        }
        ReservaCosto other = (ReservaCosto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ReservaCosto[ id=" + id + " ]";
    }
    
}
