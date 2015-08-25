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
@Table(name = "vuelo", catalog = "bdsae", schema = "compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vuelo.findAll", query = "SELECT v FROM Vuelo v"),
    @NamedQuery(name = "Vuelo.findById", query = "SELECT v FROM Vuelo v WHERE v.id = :id"),
    @NamedQuery(name = "Vuelo.findByEstado", query = "SELECT v FROM Vuelo v WHERE v.estado = :estado"),
    @NamedQuery(name = "Vuelo.findByIdCiudadOrigen", query = "SELECT v FROM Vuelo v WHERE v.idCiudadOrigen = :idCiudadOrigen"),
    @NamedQuery(name = "Vuelo.findByIdCiudadDestino", query = "SELECT v FROM Vuelo v WHERE v.idCiudadDestino = :idCiudadDestino"),
    @NamedQuery(name = "Vuelo.findByFechaHoraSalida", query = "SELECT v FROM Vuelo v WHERE v.fechaHoraSalida = :fechaHoraSalida"),
    @NamedQuery(name = "Vuelo.findByNumeroVuelo", query = "SELECT v FROM Vuelo v WHERE v.numeroVuelo = :numeroVuelo"),
    @NamedQuery(name = "Vuelo.findByFechaRegistro", query = "SELECT v FROM Vuelo v WHERE v.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Vuelo.findByIdUsuario", query = "SELECT v FROM Vuelo v WHERE v.idUsuario = :idUsuario")})
public class Vuelo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_ciudad_origen")
    private Integer idCiudadOrigen;
    @Column(name = "id_ciudad_destino")
    private Integer idCiudadDestino;
    @Column(name = "fecha_hora_salida")
    @Temporal(TemporalType.DATE)
    private Date fechaHoraSalida;
    @Column(name = "numero_vuelo")
    private String numeroVuelo;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_tiquete", referencedColumnName = "id")
    @ManyToOne
    private Tiquete idTiquete;

    public Vuelo() {
    }

    public Vuelo(Integer id) {
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

    public Integer getIdCiudadOrigen() {
        return idCiudadOrigen;
    }

    public void setIdCiudadOrigen(Integer idCiudadOrigen) {
        this.idCiudadOrigen = idCiudadOrigen;
    }

    public Integer getIdCiudadDestino() {
        return idCiudadDestino;
    }

    public void setIdCiudadDestino(Integer idCiudadDestino) {
        this.idCiudadDestino = idCiudadDestino;
    }

    public Date getFechaHoraSalida() {
        return fechaHoraSalida;
    }

    public void setFechaHoraSalida(Date fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
    }

    public String getNumeroVuelo() {
        return numeroVuelo;
    }

    public void setNumeroVuelo(String numeroVuelo) {
        this.numeroVuelo = numeroVuelo;
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

    public Tiquete getIdTiquete() {
        return idTiquete;
    }

    public void setIdTiquete(Tiquete idTiquete) {
        this.idTiquete = idTiquete;
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
        if (!(object instanceof Vuelo)) {
            return false;
        }
        Vuelo other = (Vuelo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Vuelo[ id=" + id + " ]";
    }
    
}
