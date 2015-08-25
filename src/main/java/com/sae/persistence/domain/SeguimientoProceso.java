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
@Table(name = "seguimiento_proceso", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SeguimientoProceso.findAll", query = "SELECT s FROM SeguimientoProceso s"),
    @NamedQuery(name = "SeguimientoProceso.findById", query = "SELECT s FROM SeguimientoProceso s WHERE s.id = :id"),
    @NamedQuery(name = "SeguimientoProceso.findByFechaSentencia", query = "SELECT s FROM SeguimientoProceso s WHERE s.fechaSentencia = :fechaSentencia"),
    @NamedQuery(name = "SeguimientoProceso.findByTermino", query = "SELECT s FROM SeguimientoProceso s WHERE s.termino = :termino"),
    @NamedQuery(name = "SeguimientoProceso.findByDescripcion", query = "SELECT s FROM SeguimientoProceso s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "SeguimientoProceso.findByTerminoCumplimiento", query = "SELECT s FROM SeguimientoProceso s WHERE s.terminoCumplimiento = :terminoCumplimiento"),
    @NamedQuery(name = "SeguimientoProceso.findByValor", query = "SELECT s FROM SeguimientoProceso s WHERE s.valor = :valor"),
    @NamedQuery(name = "SeguimientoProceso.findByIdEntidadBancaria", query = "SELECT s FROM SeguimientoProceso s WHERE s.idEntidadBancaria = :idEntidadBancaria"),
    @NamedQuery(name = "SeguimientoProceso.findByIdNumeroCuenta", query = "SELECT s FROM SeguimientoProceso s WHERE s.idNumeroCuenta = :idNumeroCuenta"),
    @NamedQuery(name = "SeguimientoProceso.findByFechaRegistro", query = "SELECT s FROM SeguimientoProceso s WHERE s.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "SeguimientoProceso.findByIdUsuario", query = "SELECT s FROM SeguimientoProceso s WHERE s.idUsuario = :idUsuario"),
    @NamedQuery(name = "SeguimientoProceso.findByEstado", query = "SELECT s FROM SeguimientoProceso s WHERE s.estado = :estado")})
public class SeguimientoProceso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha_sentencia")
    @Temporal(TemporalType.DATE)
    private Date fechaSentencia;
    @Column(name = "termino")
    private String termino;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "termino_cumplimiento")
    private String terminoCumplimiento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "id_entidad_bancaria")
    private Integer idEntidadBancaria;
    @Column(name = "id_numero_cuenta")
    private Integer idNumeroCuenta;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "estado")
    private Boolean estado;
    @OneToMany(mappedBy = "idSeguimiento")
    private List<SolucionProceso> solucionProcesoList;
    @JoinColumn(name = "id_solucion", referencedColumnName = "id")
    @ManyToOne
    private TipoSolucionProceso idSolucion;
    @JoinColumn(name = "id_proceso", referencedColumnName = "id")
    @ManyToOne
    private Proceso idProceso;
    @JoinColumn(name = "id_forma", referencedColumnName = "id")
    @ManyToOne
    private FormaProceso idForma;
    @JoinColumn(name = "id_etapa", referencedColumnName = "id")
    @ManyToOne
    private EtapaProceso idEtapa;
    @JoinColumn(name = "id_instancia", referencedColumnName = "id")
    @ManyToOne
    private Clase idInstancia;

    public SeguimientoProceso() {
    }

    public SeguimientoProceso(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaSentencia() {
        return fechaSentencia;
    }

    public void setFechaSentencia(Date fechaSentencia) {
        this.fechaSentencia = fechaSentencia;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTerminoCumplimiento() {
        return terminoCumplimiento;
    }

    public void setTerminoCumplimiento(String terminoCumplimiento) {
        this.terminoCumplimiento = terminoCumplimiento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getIdEntidadBancaria() {
        return idEntidadBancaria;
    }

    public void setIdEntidadBancaria(Integer idEntidadBancaria) {
        this.idEntidadBancaria = idEntidadBancaria;
    }

    public Integer getIdNumeroCuenta() {
        return idNumeroCuenta;
    }

    public void setIdNumeroCuenta(Integer idNumeroCuenta) {
        this.idNumeroCuenta = idNumeroCuenta;
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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<SolucionProceso> getSolucionProcesoList() {
        return solucionProcesoList;
    }

    public void setSolucionProcesoList(List<SolucionProceso> solucionProcesoList) {
        this.solucionProcesoList = solucionProcesoList;
    }

    public TipoSolucionProceso getIdSolucion() {
        return idSolucion;
    }

    public void setIdSolucion(TipoSolucionProceso idSolucion) {
        this.idSolucion = idSolucion;
    }

    public Proceso getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Proceso idProceso) {
        this.idProceso = idProceso;
    }

    public FormaProceso getIdForma() {
        return idForma;
    }

    public void setIdForma(FormaProceso idForma) {
        this.idForma = idForma;
    }

    public EtapaProceso getIdEtapa() {
        return idEtapa;
    }

    public void setIdEtapa(EtapaProceso idEtapa) {
        this.idEtapa = idEtapa;
    }

    public Clase getIdInstancia() {
        return idInstancia;
    }

    public void setIdInstancia(Clase idInstancia) {
        this.idInstancia = idInstancia;
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
        if (!(object instanceof SeguimientoProceso)) {
            return false;
        }
        SeguimientoProceso other = (SeguimientoProceso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.SeguimientoProceso[ id=" + id + " ]";
    }
    
}
