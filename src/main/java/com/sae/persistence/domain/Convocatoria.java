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
@Table(name = "convocatoria", catalog = "bdsae", schema = "licitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Convocatoria.findAll", query = "SELECT c FROM Convocatoria c"),
    @NamedQuery(name = "Convocatoria.findById", query = "SELECT c FROM Convocatoria c WHERE c.id = :id"),
    @NamedQuery(name = "Convocatoria.findByCodigo", query = "SELECT c FROM Convocatoria c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "Convocatoria.findByAsunto", query = "SELECT c FROM Convocatoria c WHERE c.asunto = :asunto"),
    @NamedQuery(name = "Convocatoria.findByDescripcion", query = "SELECT c FROM Convocatoria c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Convocatoria.findByFechaRegistro", query = "SELECT c FROM Convocatoria c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Convocatoria.findByUrl", query = "SELECT c FROM Convocatoria c WHERE c.url = :url"),
    @NamedQuery(name = "Convocatoria.findByPlazo", query = "SELECT c FROM Convocatoria c WHERE c.plazo = :plazo"),
    @NamedQuery(name = "Convocatoria.findByPresupuestoOficial", query = "SELECT c FROM Convocatoria c WHERE c.presupuestoOficial = :presupuestoOficial"),
    @NamedQuery(name = "Convocatoria.findByValorPresentado", query = "SELECT c FROM Convocatoria c WHERE c.valorPresentado = :valorPresentado"),
    @NamedQuery(name = "Convocatoria.findByValorReferencia", query = "SELECT c FROM Convocatoria c WHERE c.valorReferencia = :valorReferencia"),
    @NamedQuery(name = "Convocatoria.findByEstado", query = "SELECT c FROM Convocatoria c WHERE c.estado = :estado"),
    @NamedQuery(name = "Convocatoria.findByIdUsuario", query = "SELECT c FROM Convocatoria c WHERE c.idUsuario = :idUsuario"),
    @NamedQuery(name = "Convocatoria.findByIdLugarEjecucion", query = "SELECT c FROM Convocatoria c WHERE c.idLugarEjecucion = :idLugarEjecucion"),
    @NamedQuery(name = "Convocatoria.findByDireccionEjecucion", query = "SELECT c FROM Convocatoria c WHERE c.direccionEjecucion = :direccionEjecucion"),
    @NamedQuery(name = "Convocatoria.findByIdRazonSocial", query = "SELECT c FROM Convocatoria c WHERE c.idRazonSocial = :idRazonSocial"),
    @NamedQuery(name = "Convocatoria.findByEntidadContratante", query = "SELECT c FROM Convocatoria c WHERE c.entidadContratante = :entidadContratante")})
public class Convocatoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "asunto")
    private String asunto;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "url")
    private String url;
    @Column(name = "plazo")
    private Integer plazo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "presupuesto_oficial")
    private Double presupuestoOficial;
    @Column(name = "valor_presentado")
    private Double valorPresentado;
    @Column(name = "valor_referencia")
    private Double valorReferencia;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_lugar_ejecucion")
    private Integer idLugarEjecucion;
    @Column(name = "direccion_ejecucion")
    private String direccionEjecucion;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @Column(name = "entidad_contratante")
    private Integer entidadContratante;
    @OneToMany(mappedBy = "idConvocatoria")
    private List<MatrizRiesgo> matrizRiesgoList;
    @OneToMany(mappedBy = "idConvocatoria")
    private List<Propuesta> propuestaList;
    @OneToMany(mappedBy = "idConvocatoria")
    private List<SocioConvocatoria> socioConvocatoriaList;
    @OneToMany(mappedBy = "idConvocatoria")
    private List<EquipoRequeridoConvocatoria> equipoRequeridoConvocatoriaList;
    @OneToMany(mappedBy = "idConvocatoria")
    private List<AdjuntoConvocatoria> adjuntoConvocatoriaList;
    @OneToMany(mappedBy = "idConvocatoria")
    private List<EvaluacionConvocatoria> evaluacionConvocatoriaList;
    @OneToMany(mappedBy = "idConvocatoria")
    private List<RupEstadoFinancieroConsolidadoConvocatoria> rupEstadoFinancieroConsolidadoConvocatoriaList;
    @OneToMany(mappedBy = "idConvocatoria")
    private List<EstadoConvocatoria> estadoConvocatoriaList;
    @OneToMany(mappedBy = "idConvocatoria")
    private List<PersonalRequeridoConvocatoria> personalRequeridoConvocatoriaList;
    @JoinColumn(name = "id_tipo_proyecto", referencedColumnName = "id")
    @ManyToOne
    private TipoProyectoConvocatoria idTipoProyecto;
    @JoinColumn(name = "id_tipo_proceso", referencedColumnName = "id")
    @ManyToOne
    private TipoProcesoConvocatoria idTipoProceso;
    @JoinColumn(name = "id_forma_pago", referencedColumnName = "id")
    @ManyToOne
    private FormaPagoConvocatoria idFormaPago;
    @OneToMany(mappedBy = "idConvocatoria")
    private List<AnalisisConvocatoria> analisisConvocatoriaList;

    public Convocatoria() {
    }

    public Convocatoria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }

    public Double getPresupuestoOficial() {
        return presupuestoOficial;
    }

    public void setPresupuestoOficial(Double presupuestoOficial) {
        this.presupuestoOficial = presupuestoOficial;
    }

    public Double getValorPresentado() {
        return valorPresentado;
    }

    public void setValorPresentado(Double valorPresentado) {
        this.valorPresentado = valorPresentado;
    }

    public Double getValorReferencia() {
        return valorReferencia;
    }

    public void setValorReferencia(Double valorReferencia) {
        this.valorReferencia = valorReferencia;
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

    public Integer getIdLugarEjecucion() {
        return idLugarEjecucion;
    }

    public void setIdLugarEjecucion(Integer idLugarEjecucion) {
        this.idLugarEjecucion = idLugarEjecucion;
    }

    public String getDireccionEjecucion() {
        return direccionEjecucion;
    }

    public void setDireccionEjecucion(String direccionEjecucion) {
        this.direccionEjecucion = direccionEjecucion;
    }

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    public Integer getEntidadContratante() {
        return entidadContratante;
    }

    public void setEntidadContratante(Integer entidadContratante) {
        this.entidadContratante = entidadContratante;
    }

    @XmlTransient
    public List<MatrizRiesgo> getMatrizRiesgoList() {
        return matrizRiesgoList;
    }

    public void setMatrizRiesgoList(List<MatrizRiesgo> matrizRiesgoList) {
        this.matrizRiesgoList = matrizRiesgoList;
    }

    @XmlTransient
    public List<Propuesta> getPropuestaList() {
        return propuestaList;
    }

    public void setPropuestaList(List<Propuesta> propuestaList) {
        this.propuestaList = propuestaList;
    }

    @XmlTransient
    public List<SocioConvocatoria> getSocioConvocatoriaList() {
        return socioConvocatoriaList;
    }

    public void setSocioConvocatoriaList(List<SocioConvocatoria> socioConvocatoriaList) {
        this.socioConvocatoriaList = socioConvocatoriaList;
    }

    @XmlTransient
    public List<EquipoRequeridoConvocatoria> getEquipoRequeridoConvocatoriaList() {
        return equipoRequeridoConvocatoriaList;
    }

    public void setEquipoRequeridoConvocatoriaList(List<EquipoRequeridoConvocatoria> equipoRequeridoConvocatoriaList) {
        this.equipoRequeridoConvocatoriaList = equipoRequeridoConvocatoriaList;
    }

    @XmlTransient
    public List<AdjuntoConvocatoria> getAdjuntoConvocatoriaList() {
        return adjuntoConvocatoriaList;
    }

    public void setAdjuntoConvocatoriaList(List<AdjuntoConvocatoria> adjuntoConvocatoriaList) {
        this.adjuntoConvocatoriaList = adjuntoConvocatoriaList;
    }

    @XmlTransient
    public List<EvaluacionConvocatoria> getEvaluacionConvocatoriaList() {
        return evaluacionConvocatoriaList;
    }

    public void setEvaluacionConvocatoriaList(List<EvaluacionConvocatoria> evaluacionConvocatoriaList) {
        this.evaluacionConvocatoriaList = evaluacionConvocatoriaList;
    }

    @XmlTransient
    public List<RupEstadoFinancieroConsolidadoConvocatoria> getRupEstadoFinancieroConsolidadoConvocatoriaList() {
        return rupEstadoFinancieroConsolidadoConvocatoriaList;
    }

    public void setRupEstadoFinancieroConsolidadoConvocatoriaList(List<RupEstadoFinancieroConsolidadoConvocatoria> rupEstadoFinancieroConsolidadoConvocatoriaList) {
        this.rupEstadoFinancieroConsolidadoConvocatoriaList = rupEstadoFinancieroConsolidadoConvocatoriaList;
    }

    @XmlTransient
    public List<EstadoConvocatoria> getEstadoConvocatoriaList() {
        return estadoConvocatoriaList;
    }

    public void setEstadoConvocatoriaList(List<EstadoConvocatoria> estadoConvocatoriaList) {
        this.estadoConvocatoriaList = estadoConvocatoriaList;
    }

    @XmlTransient
    public List<PersonalRequeridoConvocatoria> getPersonalRequeridoConvocatoriaList() {
        return personalRequeridoConvocatoriaList;
    }

    public void setPersonalRequeridoConvocatoriaList(List<PersonalRequeridoConvocatoria> personalRequeridoConvocatoriaList) {
        this.personalRequeridoConvocatoriaList = personalRequeridoConvocatoriaList;
    }

    public TipoProyectoConvocatoria getIdTipoProyecto() {
        return idTipoProyecto;
    }

    public void setIdTipoProyecto(TipoProyectoConvocatoria idTipoProyecto) {
        this.idTipoProyecto = idTipoProyecto;
    }

    public TipoProcesoConvocatoria getIdTipoProceso() {
        return idTipoProceso;
    }

    public void setIdTipoProceso(TipoProcesoConvocatoria idTipoProceso) {
        this.idTipoProceso = idTipoProceso;
    }

    public FormaPagoConvocatoria getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(FormaPagoConvocatoria idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    @XmlTransient
    public List<AnalisisConvocatoria> getAnalisisConvocatoriaList() {
        return analisisConvocatoriaList;
    }

    public void setAnalisisConvocatoriaList(List<AnalisisConvocatoria> analisisConvocatoriaList) {
        this.analisisConvocatoriaList = analisisConvocatoriaList;
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
        if (!(object instanceof Convocatoria)) {
            return false;
        }
        Convocatoria other = (Convocatoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Convocatoria[ id=" + id + " ]";
    }
    
}
