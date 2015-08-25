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
@Table(name = "registro_matriz_riesgo", catalog = "bdsae", schema = "licitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegistroMatrizRiesgo.findAll", query = "SELECT r FROM RegistroMatrizRiesgo r"),
    @NamedQuery(name = "RegistroMatrizRiesgo.findById", query = "SELECT r FROM RegistroMatrizRiesgo r WHERE r.id = :id"),
    @NamedQuery(name = "RegistroMatrizRiesgo.findByDescripcion", query = "SELECT r FROM RegistroMatrizRiesgo r WHERE r.descripcion = :descripcion"),
    @NamedQuery(name = "RegistroMatrizRiesgo.findByConsecuencia", query = "SELECT r FROM RegistroMatrizRiesgo r WHERE r.consecuencia = :consecuencia"),
    @NamedQuery(name = "RegistroMatrizRiesgo.findByImpacto", query = "SELECT r FROM RegistroMatrizRiesgo r WHERE r.impacto = :impacto"),
    @NamedQuery(name = "RegistroMatrizRiesgo.findByValoracionRiesgo", query = "SELECT r FROM RegistroMatrizRiesgo r WHERE r.valoracionRiesgo = :valoracionRiesgo"),
    @NamedQuery(name = "RegistroMatrizRiesgo.findByAsignado", query = "SELECT r FROM RegistroMatrizRiesgo r WHERE r.asignado = :asignado"),
    @NamedQuery(name = "RegistroMatrizRiesgo.findByTratamiento", query = "SELECT r FROM RegistroMatrizRiesgo r WHERE r.tratamiento = :tratamiento"),
    @NamedQuery(name = "RegistroMatrizRiesgo.findByValoracionRiesgoTratamiento", query = "SELECT r FROM RegistroMatrizRiesgo r WHERE r.valoracionRiesgoTratamiento = :valoracionRiesgoTratamiento"),
    @NamedQuery(name = "RegistroMatrizRiesgo.findByImpactoTratamiento", query = "SELECT r FROM RegistroMatrizRiesgo r WHERE r.impactoTratamiento = :impactoTratamiento"),
    @NamedQuery(name = "RegistroMatrizRiesgo.findByAfectaContrato", query = "SELECT r FROM RegistroMatrizRiesgo r WHERE r.afectaContrato = :afectaContrato"),
    @NamedQuery(name = "RegistroMatrizRiesgo.findByResponsableTratamiento", query = "SELECT r FROM RegistroMatrizRiesgo r WHERE r.responsableTratamiento = :responsableTratamiento"),
    @NamedQuery(name = "RegistroMatrizRiesgo.findByFechaInicioTratamiento", query = "SELECT r FROM RegistroMatrizRiesgo r WHERE r.fechaInicioTratamiento = :fechaInicioTratamiento"),
    @NamedQuery(name = "RegistroMatrizRiesgo.findByFechaCompletoTratamiento", query = "SELECT r FROM RegistroMatrizRiesgo r WHERE r.fechaCompletoTratamiento = :fechaCompletoTratamiento"),
    @NamedQuery(name = "RegistroMatrizRiesgo.findByEstado", query = "SELECT r FROM RegistroMatrizRiesgo r WHERE r.estado = :estado"),
    @NamedQuery(name = "RegistroMatrizRiesgo.findByFechaRegistro", query = "SELECT r FROM RegistroMatrizRiesgo r WHERE r.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "RegistroMatrizRiesgo.findByIdUsuario", query = "SELECT r FROM RegistroMatrizRiesgo r WHERE r.idUsuario = :idUsuario")})
public class RegistroMatrizRiesgo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "consecuencia")
    private String consecuencia;
    @Column(name = "impacto")
    private Integer impacto;
    @Column(name = "valoracion_riesgo")
    private Integer valoracionRiesgo;
    @Column(name = "asignado")
    private String asignado;
    @Column(name = "tratamiento")
    private String tratamiento;
    @Column(name = "valoracion_riesgo_tratamiento")
    private Integer valoracionRiesgoTratamiento;
    @Column(name = "impacto_tratamiento")
    private Integer impactoTratamiento;
    @Column(name = "afecta_contrato")
    private Boolean afectaContrato;
    @Column(name = "responsable_tratamiento")
    private String responsableTratamiento;
    @Column(name = "fecha_inicio_tratamiento")
    private String fechaInicioTratamiento;
    @Column(name = "fecha_completo_tratamiento")
    private String fechaCompletoTratamiento;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id")
    @ManyToOne
    private SubtipoDatoMatrizRiesgo idTipo;
    @JoinColumn(name = "id_probabilidad_tratamiento", referencedColumnName = "id")
    @ManyToOne
    private SubtipoDatoMatrizRiesgo idProbabilidadTratamiento;
    @JoinColumn(name = "id_probabilidad", referencedColumnName = "id")
    @ManyToOne
    private SubtipoDatoMatrizRiesgo idProbabilidad;
    @JoinColumn(name = "id_monitoreo", referencedColumnName = "id")
    @ManyToOne
    private SubtipoDatoMatrizRiesgo idMonitoreo;
    @JoinColumn(name = "id_fuente", referencedColumnName = "id")
    @ManyToOne
    private SubtipoDatoMatrizRiesgo idFuente;
    @JoinColumn(name = "id_etapa", referencedColumnName = "id")
    @ManyToOne
    private SubtipoDatoMatrizRiesgo idEtapa;
    @JoinColumn(name = "id_clase", referencedColumnName = "id")
    @ManyToOne
    private SubtipoDatoMatrizRiesgo idClase;
    @JoinColumn(name = "id_categoria_tratamiento", referencedColumnName = "id")
    @ManyToOne
    private SubtipoDatoMatrizRiesgo idCategoriaTratamiento;
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    @ManyToOne
    private SubtipoDatoMatrizRiesgo idCategoria;
    @JoinColumn(name = "id_matriz", referencedColumnName = "id")
    @ManyToOne
    private MatrizRiesgo idMatriz;

    public RegistroMatrizRiesgo() {
    }

    public RegistroMatrizRiesgo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getConsecuencia() {
        return consecuencia;
    }

    public void setConsecuencia(String consecuencia) {
        this.consecuencia = consecuencia;
    }

    public Integer getImpacto() {
        return impacto;
    }

    public void setImpacto(Integer impacto) {
        this.impacto = impacto;
    }

    public Integer getValoracionRiesgo() {
        return valoracionRiesgo;
    }

    public void setValoracionRiesgo(Integer valoracionRiesgo) {
        this.valoracionRiesgo = valoracionRiesgo;
    }

    public String getAsignado() {
        return asignado;
    }

    public void setAsignado(String asignado) {
        this.asignado = asignado;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public Integer getValoracionRiesgoTratamiento() {
        return valoracionRiesgoTratamiento;
    }

    public void setValoracionRiesgoTratamiento(Integer valoracionRiesgoTratamiento) {
        this.valoracionRiesgoTratamiento = valoracionRiesgoTratamiento;
    }

    public Integer getImpactoTratamiento() {
        return impactoTratamiento;
    }

    public void setImpactoTratamiento(Integer impactoTratamiento) {
        this.impactoTratamiento = impactoTratamiento;
    }

    public Boolean getAfectaContrato() {
        return afectaContrato;
    }

    public void setAfectaContrato(Boolean afectaContrato) {
        this.afectaContrato = afectaContrato;
    }

    public String getResponsableTratamiento() {
        return responsableTratamiento;
    }

    public void setResponsableTratamiento(String responsableTratamiento) {
        this.responsableTratamiento = responsableTratamiento;
    }

    public String getFechaInicioTratamiento() {
        return fechaInicioTratamiento;
    }

    public void setFechaInicioTratamiento(String fechaInicioTratamiento) {
        this.fechaInicioTratamiento = fechaInicioTratamiento;
    }

    public String getFechaCompletoTratamiento() {
        return fechaCompletoTratamiento;
    }

    public void setFechaCompletoTratamiento(String fechaCompletoTratamiento) {
        this.fechaCompletoTratamiento = fechaCompletoTratamiento;
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

    public SubtipoDatoMatrizRiesgo getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(SubtipoDatoMatrizRiesgo idTipo) {
        this.idTipo = idTipo;
    }

    public SubtipoDatoMatrizRiesgo getIdProbabilidadTratamiento() {
        return idProbabilidadTratamiento;
    }

    public void setIdProbabilidadTratamiento(SubtipoDatoMatrizRiesgo idProbabilidadTratamiento) {
        this.idProbabilidadTratamiento = idProbabilidadTratamiento;
    }

    public SubtipoDatoMatrizRiesgo getIdProbabilidad() {
        return idProbabilidad;
    }

    public void setIdProbabilidad(SubtipoDatoMatrizRiesgo idProbabilidad) {
        this.idProbabilidad = idProbabilidad;
    }

    public SubtipoDatoMatrizRiesgo getIdMonitoreo() {
        return idMonitoreo;
    }

    public void setIdMonitoreo(SubtipoDatoMatrizRiesgo idMonitoreo) {
        this.idMonitoreo = idMonitoreo;
    }

    public SubtipoDatoMatrizRiesgo getIdFuente() {
        return idFuente;
    }

    public void setIdFuente(SubtipoDatoMatrizRiesgo idFuente) {
        this.idFuente = idFuente;
    }

    public SubtipoDatoMatrizRiesgo getIdEtapa() {
        return idEtapa;
    }

    public void setIdEtapa(SubtipoDatoMatrizRiesgo idEtapa) {
        this.idEtapa = idEtapa;
    }

    public SubtipoDatoMatrizRiesgo getIdClase() {
        return idClase;
    }

    public void setIdClase(SubtipoDatoMatrizRiesgo idClase) {
        this.idClase = idClase;
    }

    public SubtipoDatoMatrizRiesgo getIdCategoriaTratamiento() {
        return idCategoriaTratamiento;
    }

    public void setIdCategoriaTratamiento(SubtipoDatoMatrizRiesgo idCategoriaTratamiento) {
        this.idCategoriaTratamiento = idCategoriaTratamiento;
    }

    public SubtipoDatoMatrizRiesgo getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(SubtipoDatoMatrizRiesgo idCategoria) {
        this.idCategoria = idCategoria;
    }

    public MatrizRiesgo getIdMatriz() {
        return idMatriz;
    }

    public void setIdMatriz(MatrizRiesgo idMatriz) {
        this.idMatriz = idMatriz;
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
        if (!(object instanceof RegistroMatrizRiesgo)) {
            return false;
        }
        RegistroMatrizRiesgo other = (RegistroMatrizRiesgo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.RegistroMatrizRiesgo[ id=" + id + " ]";
    }
    
}
