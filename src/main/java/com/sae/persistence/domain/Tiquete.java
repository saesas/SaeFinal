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
@Table(name = "tiquete", catalog = "bdsae", schema = "compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tiquete.findAll", query = "SELECT t FROM Tiquete t"),
    @NamedQuery(name = "Tiquete.findById", query = "SELECT t FROM Tiquete t WHERE t.id = :id"),
    @NamedQuery(name = "Tiquete.findByEstado", query = "SELECT t FROM Tiquete t WHERE t.estado = :estado"),
    @NamedQuery(name = "Tiquete.findByCodigo", query = "SELECT t FROM Tiquete t WHERE t.codigo = :codigo"),
    @NamedQuery(name = "Tiquete.findByIdPasajero", query = "SELECT t FROM Tiquete t WHERE t.idPasajero = :idPasajero"),
    @NamedQuery(name = "Tiquete.findByFechaRegistro", query = "SELECT t FROM Tiquete t WHERE t.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Tiquete.findByIdUsuario", query = "SELECT t FROM Tiquete t WHERE t.idUsuario = :idUsuario")})
public class Tiquete implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "id_pasajero")
    private Integer idPasajero;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_reserva", referencedColumnName = "id")
    @ManyToOne
    private Reserva idReserva;
    @OneToMany(mappedBy = "idTiquete")
    private List<Vuelo> vueloList;

    public Tiquete() {
    }

    public Tiquete(Integer id) {
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getIdPasajero() {
        return idPasajero;
    }

    public void setIdPasajero(Integer idPasajero) {
        this.idPasajero = idPasajero;
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

    @XmlTransient
    public List<Vuelo> getVueloList() {
        return vueloList;
    }

    public void setVueloList(List<Vuelo> vueloList) {
        this.vueloList = vueloList;
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
        if (!(object instanceof Tiquete)) {
            return false;
        }
        Tiquete other = (Tiquete) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Tiquete[ id=" + id + " ]";
    }
    
}
