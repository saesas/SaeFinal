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
@Table(name = "consumo_factura_servicio_publico", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConsumoFacturaServicioPublico.findAll", query = "SELECT c FROM ConsumoFacturaServicioPublico c"),
    @NamedQuery(name = "ConsumoFacturaServicioPublico.findById", query = "SELECT c FROM ConsumoFacturaServicioPublico c WHERE c.id = :id"),
    @NamedQuery(name = "ConsumoFacturaServicioPublico.findByValorUnitario", query = "SELECT c FROM ConsumoFacturaServicioPublico c WHERE c.valorUnitario = :valorUnitario"),
    @NamedQuery(name = "ConsumoFacturaServicioPublico.findByEstado", query = "SELECT c FROM ConsumoFacturaServicioPublico c WHERE c.estado = :estado"),
    @NamedQuery(name = "ConsumoFacturaServicioPublico.findByFechaRegistro", query = "SELECT c FROM ConsumoFacturaServicioPublico c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ConsumoFacturaServicioPublico.findByIdUsuario", query = "SELECT c FROM ConsumoFacturaServicioPublico c WHERE c.idUsuario = :idUsuario")})
public class ConsumoFacturaServicioPublico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_unitario")
    private Double valorUnitario;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_tarifa", referencedColumnName = "id")
    @ManyToOne
    private TipoTarifaServicioPublico idTarifa;
    @JoinColumn(name = "id_indicador", referencedColumnName = "id")
    @ManyToOne
    private IndicadorConsumoServicio idIndicador;
    @JoinColumn(name = "id_factura", referencedColumnName = "id")
    @ManyToOne
    private FacturaServicioPublico idFactura;

    public ConsumoFacturaServicioPublico() {
    }

    public ConsumoFacturaServicioPublico(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
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

    public TipoTarifaServicioPublico getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(TipoTarifaServicioPublico idTarifa) {
        this.idTarifa = idTarifa;
    }

    public IndicadorConsumoServicio getIdIndicador() {
        return idIndicador;
    }

    public void setIdIndicador(IndicadorConsumoServicio idIndicador) {
        this.idIndicador = idIndicador;
    }

    public FacturaServicioPublico getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(FacturaServicioPublico idFactura) {
        this.idFactura = idFactura;
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
        if (!(object instanceof ConsumoFacturaServicioPublico)) {
            return false;
        }
        ConsumoFacturaServicioPublico other = (ConsumoFacturaServicioPublico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ConsumoFacturaServicioPublico[ id=" + id + " ]";
    }
    
}
