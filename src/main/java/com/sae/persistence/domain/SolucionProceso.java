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
@Table(name = "solucion_proceso", catalog = "bdsae", schema = "juridico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SolucionProceso.findAll", query = "SELECT s FROM SolucionProceso s"),
    @NamedQuery(name = "SolucionProceso.findById", query = "SELECT s FROM SolucionProceso s WHERE s.id = :id"),
    @NamedQuery(name = "SolucionProceso.findByObjeto", query = "SELECT s FROM SolucionProceso s WHERE s.objeto = :objeto"),
    @NamedQuery(name = "SolucionProceso.findByResultado", query = "SELECT s FROM SolucionProceso s WHERE s.resultado = :resultado"),
    @NamedQuery(name = "SolucionProceso.findByFechaSolucion", query = "SELECT s FROM SolucionProceso s WHERE s.fechaSolucion = :fechaSolucion"),
    @NamedQuery(name = "SolucionProceso.findByFechaRegistro", query = "SELECT s FROM SolucionProceso s WHERE s.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "SolucionProceso.findByIdUsuario", query = "SELECT s FROM SolucionProceso s WHERE s.idUsuario = :idUsuario")})
public class SolucionProceso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "objeto")
    private String objeto;
    @Column(name = "resultado")
    private String resultado;
    @Column(name = "fecha_solucion")
    @Temporal(TemporalType.DATE)
    private Date fechaSolucion;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_mecanismo", referencedColumnName = "id")
    @ManyToOne
    private TipoMecanismoSolucion idMecanismo;
    @JoinColumn(name = "id_seguimiento", referencedColumnName = "id")
    @ManyToOne
    private SeguimientoProceso idSeguimiento;
    @OneToMany(mappedBy = "idSolucion")
    private List<FiguraSolucion> figuraSolucionList;

    public SolucionProceso() {
    }

    public SolucionProceso(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Date getFechaSolucion() {
        return fechaSolucion;
    }

    public void setFechaSolucion(Date fechaSolucion) {
        this.fechaSolucion = fechaSolucion;
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

    public TipoMecanismoSolucion getIdMecanismo() {
        return idMecanismo;
    }

    public void setIdMecanismo(TipoMecanismoSolucion idMecanismo) {
        this.idMecanismo = idMecanismo;
    }

    public SeguimientoProceso getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(SeguimientoProceso idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    @XmlTransient
    public List<FiguraSolucion> getFiguraSolucionList() {
        return figuraSolucionList;
    }

    public void setFiguraSolucionList(List<FiguraSolucion> figuraSolucionList) {
        this.figuraSolucionList = figuraSolucionList;
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
        if (!(object instanceof SolucionProceso)) {
            return false;
        }
        SolucionProceso other = (SolucionProceso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.SolucionProceso[ id=" + id + " ]";
    }
    
}
