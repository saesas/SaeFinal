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
@Table(name = "reserva", catalog = "bdsae", schema = "compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reserva.findAll", query = "SELECT r FROM Reserva r"),
    @NamedQuery(name = "Reserva.findById", query = "SELECT r FROM Reserva r WHERE r.id = :id"),
    @NamedQuery(name = "Reserva.findByEstado", query = "SELECT r FROM Reserva r WHERE r.estado = :estado"),
    @NamedQuery(name = "Reserva.findByIdAerolinia", query = "SELECT r FROM Reserva r WHERE r.idAerolinia = :idAerolinia"),
    @NamedQuery(name = "Reserva.findByFechaCompra", query = "SELECT r FROM Reserva r WHERE r.fechaCompra = :fechaCompra"),
    @NamedQuery(name = "Reserva.findByObservacion", query = "SELECT r FROM Reserva r WHERE r.observacion = :observacion"),
    @NamedQuery(name = "Reserva.findByCodigo", query = "SELECT r FROM Reserva r WHERE r.codigo = :codigo"),
    @NamedQuery(name = "Reserva.findByIdMedioPago", query = "SELECT r FROM Reserva r WHERE r.idMedioPago = :idMedioPago"),
    @NamedQuery(name = "Reserva.findByDocumentoSoporte", query = "SELECT r FROM Reserva r WHERE r.documentoSoporte = :documentoSoporte"),
    @NamedQuery(name = "Reserva.findByIdProducto", query = "SELECT r FROM Reserva r WHERE r.idProducto = :idProducto"),
    @NamedQuery(name = "Reserva.findByIdTerceroTarjeta", query = "SELECT r FROM Reserva r WHERE r.idTerceroTarjeta = :idTerceroTarjeta"),
    @NamedQuery(name = "Reserva.findByIdTerceroGastoAdministrativo", query = "SELECT r FROM Reserva r WHERE r.idTerceroGastoAdministrativo = :idTerceroGastoAdministrativo"),
    @NamedQuery(name = "Reserva.findByIdProyectoGastoOperativo", query = "SELECT r FROM Reserva r WHERE r.idProyectoGastoOperativo = :idProyectoGastoOperativo"),
    @NamedQuery(name = "Reserva.findByIdUsuario", query = "SELECT r FROM Reserva r WHERE r.idUsuario = :idUsuario"),
    @NamedQuery(name = "Reserva.findByFechaRegistro", query = "SELECT r FROM Reserva r WHERE r.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Reserva.findByIdRazonSocial", query = "SELECT r FROM Reserva r WHERE r.idRazonSocial = :idRazonSocial")})
public class Reserva implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_aerolinia")
    private Integer idAerolinia;
    @Column(name = "fecha_compra")
    @Temporal(TemporalType.DATE)
    private Date fechaCompra;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "id_medio_pago")
    private Integer idMedioPago;
    @Column(name = "documento_soporte")
    private String documentoSoporte;
    @Column(name = "id_producto")
    private Integer idProducto;
    @Column(name = "id_tercero_tarjeta")
    private Integer idTerceroTarjeta;
    @Column(name = "id_tercero_gasto_administrativo")
    private Integer idTerceroGastoAdministrativo;
    @Column(name = "id_proyecto_gasto_operativo")
    private Integer idProyectoGastoOperativo;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @OneToMany(mappedBy = "idReserva")
    private List<ReservaEmail> reservaEmailList;
    @OneToMany(mappedBy = "idReserva")
    private List<Tiquete> tiqueteList;
    @JoinColumn(name = "id_gasto", referencedColumnName = "id")
    @ManyToOne
    private TipoGasto idGasto;
    @OneToMany(mappedBy = "idReserva")
    private List<ReservaCosto> reservaCostoList;

    public Reserva() {
    }

    public Reserva(Integer id) {
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

    public Integer getIdAerolinia() {
        return idAerolinia;
    }

    public void setIdAerolinia(Integer idAerolinia) {
        this.idAerolinia = idAerolinia;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getIdMedioPago() {
        return idMedioPago;
    }

    public void setIdMedioPago(Integer idMedioPago) {
        this.idMedioPago = idMedioPago;
    }

    public String getDocumentoSoporte() {
        return documentoSoporte;
    }

    public void setDocumentoSoporte(String documentoSoporte) {
        this.documentoSoporte = documentoSoporte;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getIdTerceroTarjeta() {
        return idTerceroTarjeta;
    }

    public void setIdTerceroTarjeta(Integer idTerceroTarjeta) {
        this.idTerceroTarjeta = idTerceroTarjeta;
    }

    public Integer getIdTerceroGastoAdministrativo() {
        return idTerceroGastoAdministrativo;
    }

    public void setIdTerceroGastoAdministrativo(Integer idTerceroGastoAdministrativo) {
        this.idTerceroGastoAdministrativo = idTerceroGastoAdministrativo;
    }

    public Integer getIdProyectoGastoOperativo() {
        return idProyectoGastoOperativo;
    }

    public void setIdProyectoGastoOperativo(Integer idProyectoGastoOperativo) {
        this.idProyectoGastoOperativo = idProyectoGastoOperativo;
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

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    @XmlTransient
    public List<ReservaEmail> getReservaEmailList() {
        return reservaEmailList;
    }

    public void setReservaEmailList(List<ReservaEmail> reservaEmailList) {
        this.reservaEmailList = reservaEmailList;
    }

    @XmlTransient
    public List<Tiquete> getTiqueteList() {
        return tiqueteList;
    }

    public void setTiqueteList(List<Tiquete> tiqueteList) {
        this.tiqueteList = tiqueteList;
    }

    public TipoGasto getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(TipoGasto idGasto) {
        this.idGasto = idGasto;
    }

    @XmlTransient
    public List<ReservaCosto> getReservaCostoList() {
        return reservaCostoList;
    }

    public void setReservaCostoList(List<ReservaCosto> reservaCostoList) {
        this.reservaCostoList = reservaCostoList;
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
        if (!(object instanceof Reserva)) {
            return false;
        }
        Reserva other = (Reserva) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Reserva[ id=" + id + " ]";
    }
    
}
