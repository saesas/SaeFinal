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
@Table(name = "factura_servicio_publico", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturaServicioPublico.findAll", query = "SELECT f FROM FacturaServicioPublico f"),
    @NamedQuery(name = "FacturaServicioPublico.findById", query = "SELECT f FROM FacturaServicioPublico f WHERE f.id = :id"),
    @NamedQuery(name = "FacturaServicioPublico.findByIdProveedor", query = "SELECT f FROM FacturaServicioPublico f WHERE f.idProveedor = :idProveedor"),
    @NamedQuery(name = "FacturaServicioPublico.findByFechaInicioFactura", query = "SELECT f FROM FacturaServicioPublico f WHERE f.fechaInicioFactura = :fechaInicioFactura"),
    @NamedQuery(name = "FacturaServicioPublico.findByFechaFinFactura", query = "SELECT f FROM FacturaServicioPublico f WHERE f.fechaFinFactura = :fechaFinFactura"),
    @NamedQuery(name = "FacturaServicioPublico.findByFechaLimitePago", query = "SELECT f FROM FacturaServicioPublico f WHERE f.fechaLimitePago = :fechaLimitePago"),
    @NamedQuery(name = "FacturaServicioPublico.findByValor", query = "SELECT f FROM FacturaServicioPublico f WHERE f.valor = :valor"),
    @NamedQuery(name = "FacturaServicioPublico.findByIdSucursal", query = "SELECT f FROM FacturaServicioPublico f WHERE f.idSucursal = :idSucursal"),
    @NamedQuery(name = "FacturaServicioPublico.findByCodigo", query = "SELECT f FROM FacturaServicioPublico f WHERE f.codigo = :codigo"),
    @NamedQuery(name = "FacturaServicioPublico.findByCostoLlamadaLocal", query = "SELECT f FROM FacturaServicioPublico f WHERE f.costoLlamadaLocal = :costoLlamadaLocal"),
    @NamedQuery(name = "FacturaServicioPublico.findByCostoInternet", query = "SELECT f FROM FacturaServicioPublico f WHERE f.costoInternet = :costoInternet"),
    @NamedQuery(name = "FacturaServicioPublico.findByCostoLlamadaLargaDistancia", query = "SELECT f FROM FacturaServicioPublico f WHERE f.costoLlamadaLargaDistancia = :costoLlamadaLargaDistancia"),
    @NamedQuery(name = "FacturaServicioPublico.findByCostoLlamadaCelular", query = "SELECT f FROM FacturaServicioPublico f WHERE f.costoLlamadaCelular = :costoLlamadaCelular"),
    @NamedQuery(name = "FacturaServicioPublico.findByConsumoMinutosVoz", query = "SELECT f FROM FacturaServicioPublico f WHERE f.consumoMinutosVoz = :consumoMinutosVoz"),
    @NamedQuery(name = "FacturaServicioPublico.findByNumeroCelular", query = "SELECT f FROM FacturaServicioPublico f WHERE f.numeroCelular = :numeroCelular"),
    @NamedQuery(name = "FacturaServicioPublico.findByFechaRegistro", query = "SELECT f FROM FacturaServicioPublico f WHERE f.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "FacturaServicioPublico.findByIdUsuario", query = "SELECT f FROM FacturaServicioPublico f WHERE f.idUsuario = :idUsuario"),
    @NamedQuery(name = "FacturaServicioPublico.findByIdRazonSocial", query = "SELECT f FROM FacturaServicioPublico f WHERE f.idRazonSocial = :idRazonSocial")})
public class FacturaServicioPublico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_proveedor")
    private Integer idProveedor;
    @Column(name = "fecha_inicio_factura")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioFactura;
    @Column(name = "fecha_fin_factura")
    @Temporal(TemporalType.DATE)
    private Date fechaFinFactura;
    @Column(name = "fecha_limite_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaLimitePago;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "id_sucursal")
    private Integer idSucursal;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "costo_llamada_local")
    private Double costoLlamadaLocal;
    @Column(name = "costo_internet")
    private Double costoInternet;
    @Column(name = "costo_llamada_larga_distancia")
    private Double costoLlamadaLargaDistancia;
    @Column(name = "costo_llamada_celular")
    private Double costoLlamadaCelular;
    @Column(name = "consumo_minutos_voz")
    private Double consumoMinutosVoz;
    @Column(name = "numero_celular")
    private Integer numeroCelular;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @OneToMany(mappedBy = "idFactura")
    private List<ConsumoFacturaServicioPublico> consumoFacturaServicioPublicoList;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id")
    @ManyToOne
    private TipoServicioPublico idTipo;

    public FacturaServicioPublico() {
    }

    public FacturaServicioPublico(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Date getFechaInicioFactura() {
        return fechaInicioFactura;
    }

    public void setFechaInicioFactura(Date fechaInicioFactura) {
        this.fechaInicioFactura = fechaInicioFactura;
    }

    public Date getFechaFinFactura() {
        return fechaFinFactura;
    }

    public void setFechaFinFactura(Date fechaFinFactura) {
        this.fechaFinFactura = fechaFinFactura;
    }

    public Date getFechaLimitePago() {
        return fechaLimitePago;
    }

    public void setFechaLimitePago(Date fechaLimitePago) {
        this.fechaLimitePago = fechaLimitePago;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getCostoLlamadaLocal() {
        return costoLlamadaLocal;
    }

    public void setCostoLlamadaLocal(Double costoLlamadaLocal) {
        this.costoLlamadaLocal = costoLlamadaLocal;
    }

    public Double getCostoInternet() {
        return costoInternet;
    }

    public void setCostoInternet(Double costoInternet) {
        this.costoInternet = costoInternet;
    }

    public Double getCostoLlamadaLargaDistancia() {
        return costoLlamadaLargaDistancia;
    }

    public void setCostoLlamadaLargaDistancia(Double costoLlamadaLargaDistancia) {
        this.costoLlamadaLargaDistancia = costoLlamadaLargaDistancia;
    }

    public Double getCostoLlamadaCelular() {
        return costoLlamadaCelular;
    }

    public void setCostoLlamadaCelular(Double costoLlamadaCelular) {
        this.costoLlamadaCelular = costoLlamadaCelular;
    }

    public Double getConsumoMinutosVoz() {
        return consumoMinutosVoz;
    }

    public void setConsumoMinutosVoz(Double consumoMinutosVoz) {
        this.consumoMinutosVoz = consumoMinutosVoz;
    }

    public Integer getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(Integer numeroCelular) {
        this.numeroCelular = numeroCelular;
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

    @XmlTransient
    public List<ConsumoFacturaServicioPublico> getConsumoFacturaServicioPublicoList() {
        return consumoFacturaServicioPublicoList;
    }

    public void setConsumoFacturaServicioPublicoList(List<ConsumoFacturaServicioPublico> consumoFacturaServicioPublicoList) {
        this.consumoFacturaServicioPublicoList = consumoFacturaServicioPublicoList;
    }

    public TipoServicioPublico getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(TipoServicioPublico idTipo) {
        this.idTipo = idTipo;
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
        if (!(object instanceof FacturaServicioPublico)) {
            return false;
        }
        FacturaServicioPublico other = (FacturaServicioPublico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.FacturaServicioPublico[ id=" + id + " ]";
    }
    
}
