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
@Table(name = "contrato_proyecto_proveedor", catalog = "bdsae", schema = "tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContratoProyectoProveedor.findAll", query = "SELECT c FROM ContratoProyectoProveedor c"),
    @NamedQuery(name = "ContratoProyectoProveedor.findById", query = "SELECT c FROM ContratoProyectoProveedor c WHERE c.id = :id"),
    @NamedQuery(name = "ContratoProyectoProveedor.findByPlazo", query = "SELECT c FROM ContratoProyectoProveedor c WHERE c.plazo = :plazo"),
    @NamedQuery(name = "ContratoProyectoProveedor.findByObjeto", query = "SELECT c FROM ContratoProyectoProveedor c WHERE c.objeto = :objeto"),
    @NamedQuery(name = "ContratoProyectoProveedor.findByCodigo", query = "SELECT c FROM ContratoProyectoProveedor c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "ContratoProyectoProveedor.findByNumeroCdp", query = "SELECT c FROM ContratoProyectoProveedor c WHERE c.numeroCdp = :numeroCdp"),
    @NamedQuery(name = "ContratoProyectoProveedor.findByPlazoLiquidacion", query = "SELECT c FROM ContratoProyectoProveedor c WHERE c.plazoLiquidacion = :plazoLiquidacion"),
    @NamedQuery(name = "ContratoProyectoProveedor.findByFechaInicio", query = "SELECT c FROM ContratoProyectoProveedor c WHERE c.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "ContratoProyectoProveedor.findByFechaRegistro", query = "SELECT c FROM ContratoProyectoProveedor c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ContratoProyectoProveedor.findByEstado", query = "SELECT c FROM ContratoProyectoProveedor c WHERE c.estado = :estado"),
    @NamedQuery(name = "ContratoProyectoProveedor.findByIdUsuario", query = "SELECT c FROM ContratoProyectoProveedor c WHERE c.idUsuario = :idUsuario"),
    @NamedQuery(name = "ContratoProyectoProveedor.findByIdLugarFirma", query = "SELECT c FROM ContratoProyectoProveedor c WHERE c.idLugarFirma = :idLugarFirma"),
    @NamedQuery(name = "ContratoProyectoProveedor.findByOtrosi", query = "SELECT c FROM ContratoProyectoProveedor c WHERE c.otrosi = :otrosi"),
    @NamedQuery(name = "ContratoProyectoProveedor.findByIdOrdenCompra", query = "SELECT c FROM ContratoProyectoProveedor c WHERE c.idOrdenCompra = :idOrdenCompra"),
    @NamedQuery(name = "ContratoProyectoProveedor.findByIdRazonSocial", query = "SELECT c FROM ContratoProyectoProveedor c WHERE c.idRazonSocial = :idRazonSocial")})
public class ContratoProyectoProveedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "plazo")
    private Integer plazo;
    @Column(name = "objeto")
    private String objeto;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "numero_cdp")
    private String numeroCdp;
    @Column(name = "plazo_liquidacion")
    private Integer plazoLiquidacion;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_lugar_firma")
    private Integer idLugarFirma;
    @Column(name = "otrosi")
    private Boolean otrosi;
    @Column(name = "id_orden_compra")
    private Integer idOrdenCompra;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @OneToMany(mappedBy = "idContrato")
    private List<EstadoContrato> estadoContratoList;
    @OneToMany(mappedBy = "idContrato")
    private List<ContratistaContrato> contratistaContratoList;
    @OneToMany(mappedBy = "idContrato")
    private List<ActaInicio> actaInicioList;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id")
    @ManyToOne
    private TipoContratoProyecto idTipo;
    @JoinColumn(name = "id_tipo_contratante", referencedColumnName = "id")
    @ManyToOne
    private TipoContratante idTipoContratante;
    @JoinColumn(name = "id_proyecto", referencedColumnName = "id")
    @ManyToOne
    private Proyecto idProyecto;
    @JoinColumn(name = "id_origen_otrosi", referencedColumnName = "id")
    @ManyToOne
    private OrigenOtrosi idOrigenOtrosi;
    @OneToMany(mappedBy = "idContratoOtrosi")
    private List<ContratoProyectoProveedor> contratoProyectoProveedorList;
    @JoinColumn(name = "id_contrato_otrosi", referencedColumnName = "id")
    @ManyToOne
    private ContratoProyectoProveedor idContratoOtrosi;
    @JoinColumn(name = "id_adjunto_legalizacion", referencedColumnName = "id")
    @ManyToOne
    private AdjuntoTecnica idAdjuntoLegalizacion;
    @JoinColumn(name = "id_adjunto", referencedColumnName = "id")
    @ManyToOne
    private AdjuntoTecnica idAdjunto;

    public ContratoProyectoProveedor() {
    }

    public ContratoProyectoProveedor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNumeroCdp() {
        return numeroCdp;
    }

    public void setNumeroCdp(String numeroCdp) {
        this.numeroCdp = numeroCdp;
    }

    public Integer getPlazoLiquidacion() {
        return plazoLiquidacion;
    }

    public void setPlazoLiquidacion(Integer plazoLiquidacion) {
        this.plazoLiquidacion = plazoLiquidacion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdLugarFirma() {
        return idLugarFirma;
    }

    public void setIdLugarFirma(Integer idLugarFirma) {
        this.idLugarFirma = idLugarFirma;
    }

    public Boolean getOtrosi() {
        return otrosi;
    }

    public void setOtrosi(Boolean otrosi) {
        this.otrosi = otrosi;
    }

    public Integer getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(Integer idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    @XmlTransient
    public List<EstadoContrato> getEstadoContratoList() {
        return estadoContratoList;
    }

    public void setEstadoContratoList(List<EstadoContrato> estadoContratoList) {
        this.estadoContratoList = estadoContratoList;
    }

    @XmlTransient
    public List<ContratistaContrato> getContratistaContratoList() {
        return contratistaContratoList;
    }

    public void setContratistaContratoList(List<ContratistaContrato> contratistaContratoList) {
        this.contratistaContratoList = contratistaContratoList;
    }

    @XmlTransient
    public List<ActaInicio> getActaInicioList() {
        return actaInicioList;
    }

    public void setActaInicioList(List<ActaInicio> actaInicioList) {
        this.actaInicioList = actaInicioList;
    }

    public TipoContratoProyecto getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(TipoContratoProyecto idTipo) {
        this.idTipo = idTipo;
    }

    public TipoContratante getIdTipoContratante() {
        return idTipoContratante;
    }

    public void setIdTipoContratante(TipoContratante idTipoContratante) {
        this.idTipoContratante = idTipoContratante;
    }

    public Proyecto getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Proyecto idProyecto) {
        this.idProyecto = idProyecto;
    }

    public OrigenOtrosi getIdOrigenOtrosi() {
        return idOrigenOtrosi;
    }

    public void setIdOrigenOtrosi(OrigenOtrosi idOrigenOtrosi) {
        this.idOrigenOtrosi = idOrigenOtrosi;
    }

    @XmlTransient
    public List<ContratoProyectoProveedor> getContratoProyectoProveedorList() {
        return contratoProyectoProveedorList;
    }

    public void setContratoProyectoProveedorList(List<ContratoProyectoProveedor> contratoProyectoProveedorList) {
        this.contratoProyectoProveedorList = contratoProyectoProveedorList;
    }

    public ContratoProyectoProveedor getIdContratoOtrosi() {
        return idContratoOtrosi;
    }

    public void setIdContratoOtrosi(ContratoProyectoProveedor idContratoOtrosi) {
        this.idContratoOtrosi = idContratoOtrosi;
    }

    public AdjuntoTecnica getIdAdjuntoLegalizacion() {
        return idAdjuntoLegalizacion;
    }

    public void setIdAdjuntoLegalizacion(AdjuntoTecnica idAdjuntoLegalizacion) {
        this.idAdjuntoLegalizacion = idAdjuntoLegalizacion;
    }

    public AdjuntoTecnica getIdAdjunto() {
        return idAdjunto;
    }

    public void setIdAdjunto(AdjuntoTecnica idAdjunto) {
        this.idAdjunto = idAdjunto;
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
        if (!(object instanceof ContratoProyectoProveedor)) {
            return false;
        }
        ContratoProyectoProveedor other = (ContratoProyectoProveedor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ContratoProyectoProveedor[ id=" + id + " ]";
    }
    
}
