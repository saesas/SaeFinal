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
@Table(name = "contrato_tercero", catalog = "bdsae", schema = "talento_humano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContratoTercero.findAll", query = "SELECT c FROM ContratoTercero c"),
    @NamedQuery(name = "ContratoTercero.findById", query = "SELECT c FROM ContratoTercero c WHERE c.id = :id"),
    @NamedQuery(name = "ContratoTercero.findByEstado", query = "SELECT c FROM ContratoTercero c WHERE c.estado = :estado"),
    @NamedQuery(name = "ContratoTercero.findByFechaInicio", query = "SELECT c FROM ContratoTercero c WHERE c.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "ContratoTercero.findByFechaFin", query = "SELECT c FROM ContratoTercero c WHERE c.fechaFin = :fechaFin"),
    @NamedQuery(name = "ContratoTercero.findByIdSucursalTrabajo", query = "SELECT c FROM ContratoTercero c WHERE c.idSucursalTrabajo = :idSucursalTrabajo"),
    @NamedQuery(name = "ContratoTercero.findBySalario", query = "SELECT c FROM ContratoTercero c WHERE c.salario = :salario"),
    @NamedQuery(name = "ContratoTercero.findByBonificacion", query = "SELECT c FROM ContratoTercero c WHERE c.bonificacion = :bonificacion"),
    @NamedQuery(name = "ContratoTercero.findByBonificacionNoPrestacional", query = "SELECT c FROM ContratoTercero c WHERE c.bonificacionNoPrestacional = :bonificacionNoPrestacional"),
    @NamedQuery(name = "ContratoTercero.findByIdCargo", query = "SELECT c FROM ContratoTercero c WHERE c.idCargo = :idCargo"),
    @NamedQuery(name = "ContratoTercero.findByAsuntoObservacion", query = "SELECT c FROM ContratoTercero c WHERE c.asuntoObservacion = :asuntoObservacion"),
    @NamedQuery(name = "ContratoTercero.findByObservacion", query = "SELECT c FROM ContratoTercero c WHERE c.observacion = :observacion"),
    @NamedQuery(name = "ContratoTercero.findByDocumentoSoporte", query = "SELECT c FROM ContratoTercero c WHERE c.documentoSoporte = :documentoSoporte"),
    @NamedQuery(name = "ContratoTercero.findByFechaRegistro", query = "SELECT c FROM ContratoTercero c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ContratoTercero.findByIdUsuario", query = "SELECT c FROM ContratoTercero c WHERE c.idUsuario = :idUsuario"),
    @NamedQuery(name = "ContratoTercero.findByFechaTerminacion", query = "SELECT c FROM ContratoTercero c WHERE c.fechaTerminacion = :fechaTerminacion"),
    @NamedQuery(name = "ContratoTercero.findByFinSeguridadSocial", query = "SELECT c FROM ContratoTercero c WHERE c.finSeguridadSocial = :finSeguridadSocial"),
    @NamedQuery(name = "ContratoTercero.findByObservacionRetiro", query = "SELECT c FROM ContratoTercero c WHERE c.observacionRetiro = :observacionRetiro"),
    @NamedQuery(name = "ContratoTercero.findByFechaRegistroTerminacion", query = "SELECT c FROM ContratoTercero c WHERE c.fechaRegistroTerminacion = :fechaRegistroTerminacion"),
    @NamedQuery(name = "ContratoTercero.findByIdUsuarioTerminacion", query = "SELECT c FROM ContratoTercero c WHERE c.idUsuarioTerminacion = :idUsuarioTerminacion"),
    @NamedQuery(name = "ContratoTercero.findByIdTercero", query = "SELECT c FROM ContratoTercero c WHERE c.idTercero = :idTercero"),
    @NamedQuery(name = "ContratoTercero.findByOtrosi", query = "SELECT c FROM ContratoTercero c WHERE c.otrosi = :otrosi"),
    @NamedQuery(name = "ContratoTercero.findByIdRazonSocial", query = "SELECT c FROM ContratoTercero c WHERE c.idRazonSocial = :idRazonSocial")})
public class ContratoTercero implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "id_sucursal_trabajo")
    private Integer idSucursalTrabajo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "salario")
    private Double salario;
    @Column(name = "bonificacion")
    private Double bonificacion;
    @Column(name = "bonificacion_no_prestacional")
    private Double bonificacionNoPrestacional;
    @Column(name = "id_cargo")
    private Integer idCargo;
    @Column(name = "asunto_observacion")
    private String asuntoObservacion;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "documento_soporte")
    private String documentoSoporte;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "fecha_terminacion")
    @Temporal(TemporalType.DATE)
    private Date fechaTerminacion;
    @Column(name = "fin_seguridad_social")
    private Boolean finSeguridadSocial;
    @Column(name = "observacion_retiro")
    private String observacionRetiro;
    @Column(name = "fecha_registro_terminacion")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistroTerminacion;
    @Column(name = "id_usuario_terminacion")
    private Integer idUsuarioTerminacion;
    @Column(name = "id_tercero")
    private Integer idTercero;
    @Column(name = "otrosi")
    private Boolean otrosi;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @OneToMany(mappedBy = "idContrato")
    private List<InasistenciaContrato> inasistenciaContratoList;
    @OneToMany(mappedBy = "idContrato")
    private List<ValorPagarNomina> valorPagarNominaList;
    @JoinColumn(name = "id_tipo_contrato", referencedColumnName = "id")
    @ManyToOne
    private TipoContrato idTipoContrato;
    @JoinColumn(name = "id_nivel_contrato", referencedColumnName = "id")
    @ManyToOne
    private NivelContrato idNivelContrato;
    @JoinColumn(name = "id_motivo_terminacion", referencedColumnName = "id")
    @ManyToOne
    private MotivoTerminacion idMotivoTerminacion;
    @JoinColumn(name = "id_jornada_laboral", referencedColumnName = "id")
    @ManyToOne
    private JornadaLaboral idJornadaLaboral;
    @JoinColumn(name = "id_grupo_nominal", referencedColumnName = "id")
    @ManyToOne
    private GrupoNominal idGrupoNominal;
    @OneToMany(mappedBy = "idContratoOtrosi")
    private List<ContratoTercero> contratoTerceroList;
    @JoinColumn(name = "id_contrato_otrosi", referencedColumnName = "id")
    @ManyToOne
    private ContratoTercero idContratoOtrosi;
    @JoinColumn(name = "id_afiliacion", referencedColumnName = "id")
    @ManyToOne
    private Afiliacion idAfiliacion;
    @OneToMany(mappedBy = "idContrato")
    private List<ClausulaContrato> clausulaContratoList;
    @OneToMany(mappedBy = "idContrato")
    private List<ContratoFuncion> contratoFuncionList;

    public ContratoTercero() {
    }

    public ContratoTercero(Integer id) {
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getIdSucursalTrabajo() {
        return idSucursalTrabajo;
    }

    public void setIdSucursalTrabajo(Integer idSucursalTrabajo) {
        this.idSucursalTrabajo = idSucursalTrabajo;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Double getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(Double bonificacion) {
        this.bonificacion = bonificacion;
    }

    public Double getBonificacionNoPrestacional() {
        return bonificacionNoPrestacional;
    }

    public void setBonificacionNoPrestacional(Double bonificacionNoPrestacional) {
        this.bonificacionNoPrestacional = bonificacionNoPrestacional;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getAsuntoObservacion() {
        return asuntoObservacion;
    }

    public void setAsuntoObservacion(String asuntoObservacion) {
        this.asuntoObservacion = asuntoObservacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getDocumentoSoporte() {
        return documentoSoporte;
    }

    public void setDocumentoSoporte(String documentoSoporte) {
        this.documentoSoporte = documentoSoporte;
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

    public Date getFechaTerminacion() {
        return fechaTerminacion;
    }

    public void setFechaTerminacion(Date fechaTerminacion) {
        this.fechaTerminacion = fechaTerminacion;
    }

    public Boolean getFinSeguridadSocial() {
        return finSeguridadSocial;
    }

    public void setFinSeguridadSocial(Boolean finSeguridadSocial) {
        this.finSeguridadSocial = finSeguridadSocial;
    }

    public String getObservacionRetiro() {
        return observacionRetiro;
    }

    public void setObservacionRetiro(String observacionRetiro) {
        this.observacionRetiro = observacionRetiro;
    }

    public Date getFechaRegistroTerminacion() {
        return fechaRegistroTerminacion;
    }

    public void setFechaRegistroTerminacion(Date fechaRegistroTerminacion) {
        this.fechaRegistroTerminacion = fechaRegistroTerminacion;
    }

    public Integer getIdUsuarioTerminacion() {
        return idUsuarioTerminacion;
    }

    public void setIdUsuarioTerminacion(Integer idUsuarioTerminacion) {
        this.idUsuarioTerminacion = idUsuarioTerminacion;
    }

    public Integer getIdTercero() {
        return idTercero;
    }

    public void setIdTercero(Integer idTercero) {
        this.idTercero = idTercero;
    }

    public Boolean getOtrosi() {
        return otrosi;
    }

    public void setOtrosi(Boolean otrosi) {
        this.otrosi = otrosi;
    }

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    @XmlTransient
    public List<InasistenciaContrato> getInasistenciaContratoList() {
        return inasistenciaContratoList;
    }

    public void setInasistenciaContratoList(List<InasistenciaContrato> inasistenciaContratoList) {
        this.inasistenciaContratoList = inasistenciaContratoList;
    }

    @XmlTransient
    public List<ValorPagarNomina> getValorPagarNominaList() {
        return valorPagarNominaList;
    }

    public void setValorPagarNominaList(List<ValorPagarNomina> valorPagarNominaList) {
        this.valorPagarNominaList = valorPagarNominaList;
    }

    public TipoContrato getIdTipoContrato() {
        return idTipoContrato;
    }

    public void setIdTipoContrato(TipoContrato idTipoContrato) {
        this.idTipoContrato = idTipoContrato;
    }

    public NivelContrato getIdNivelContrato() {
        return idNivelContrato;
    }

    public void setIdNivelContrato(NivelContrato idNivelContrato) {
        this.idNivelContrato = idNivelContrato;
    }

    public MotivoTerminacion getIdMotivoTerminacion() {
        return idMotivoTerminacion;
    }

    public void setIdMotivoTerminacion(MotivoTerminacion idMotivoTerminacion) {
        this.idMotivoTerminacion = idMotivoTerminacion;
    }

    public JornadaLaboral getIdJornadaLaboral() {
        return idJornadaLaboral;
    }

    public void setIdJornadaLaboral(JornadaLaboral idJornadaLaboral) {
        this.idJornadaLaboral = idJornadaLaboral;
    }

    public GrupoNominal getIdGrupoNominal() {
        return idGrupoNominal;
    }

    public void setIdGrupoNominal(GrupoNominal idGrupoNominal) {
        this.idGrupoNominal = idGrupoNominal;
    }

    @XmlTransient
    public List<ContratoTercero> getContratoTerceroList() {
        return contratoTerceroList;
    }

    public void setContratoTerceroList(List<ContratoTercero> contratoTerceroList) {
        this.contratoTerceroList = contratoTerceroList;
    }

    public ContratoTercero getIdContratoOtrosi() {
        return idContratoOtrosi;
    }

    public void setIdContratoOtrosi(ContratoTercero idContratoOtrosi) {
        this.idContratoOtrosi = idContratoOtrosi;
    }

    public Afiliacion getIdAfiliacion() {
        return idAfiliacion;
    }

    public void setIdAfiliacion(Afiliacion idAfiliacion) {
        this.idAfiliacion = idAfiliacion;
    }

    @XmlTransient
    public List<ClausulaContrato> getClausulaContratoList() {
        return clausulaContratoList;
    }

    public void setClausulaContratoList(List<ClausulaContrato> clausulaContratoList) {
        this.clausulaContratoList = clausulaContratoList;
    }

    @XmlTransient
    public List<ContratoFuncion> getContratoFuncionList() {
        return contratoFuncionList;
    }

    public void setContratoFuncionList(List<ContratoFuncion> contratoFuncionList) {
        this.contratoFuncionList = contratoFuncionList;
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
        if (!(object instanceof ContratoTercero)) {
            return false;
        }
        ContratoTercero other = (ContratoTercero) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ContratoTercero[ id=" + id + " ]";
    }
    
}
