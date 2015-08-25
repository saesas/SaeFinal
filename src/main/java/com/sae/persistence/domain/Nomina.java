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
@Table(name = "nomina", catalog = "bdsae", schema = "talento_humano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nomina.findAll", query = "SELECT n FROM Nomina n"),
    @NamedQuery(name = "Nomina.findById", query = "SELECT n FROM Nomina n WHERE n.id = :id"),
    @NamedQuery(name = "Nomina.findByCodigo", query = "SELECT n FROM Nomina n WHERE n.codigo = :codigo"),
    @NamedQuery(name = "Nomina.findByEstado", query = "SELECT n FROM Nomina n WHERE n.estado = :estado"),
    @NamedQuery(name = "Nomina.findByFechaInicio", query = "SELECT n FROM Nomina n WHERE n.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Nomina.findByFechaFin", query = "SELECT n FROM Nomina n WHERE n.fechaFin = :fechaFin"),
    @NamedQuery(name = "Nomina.findByTotalSoi", query = "SELECT n FROM Nomina n WHERE n.totalSoi = :totalSoi"),
    @NamedQuery(name = "Nomina.findByTotalFic", query = "SELECT n FROM Nomina n WHERE n.totalFic = :totalFic"),
    @NamedQuery(name = "Nomina.findByFechaRegistro", query = "SELECT n FROM Nomina n WHERE n.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Nomina.findByIdUsuario", query = "SELECT n FROM Nomina n WHERE n.idUsuario = :idUsuario")})
public class Nomina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_soi")
    private Double totalSoi;
    @Column(name = "total_fic")
    private Double totalFic;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @OneToMany(mappedBy = "idNomina")
    private List<ValorPagarNomina> valorPagarNominaList;
    @OneToMany(mappedBy = "idNomina")
    private List<EstadoNomina> estadoNominaList;
    @JoinColumn(name = "id_grupo_nominal", referencedColumnName = "id")
    @ManyToOne
    private GrupoNominal idGrupoNominal;

    public Nomina() {
    }

    public Nomina(Integer id) {
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

    public Double getTotalSoi() {
        return totalSoi;
    }

    public void setTotalSoi(Double totalSoi) {
        this.totalSoi = totalSoi;
    }

    public Double getTotalFic() {
        return totalFic;
    }

    public void setTotalFic(Double totalFic) {
        this.totalFic = totalFic;
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

    @XmlTransient
    public List<ValorPagarNomina> getValorPagarNominaList() {
        return valorPagarNominaList;
    }

    public void setValorPagarNominaList(List<ValorPagarNomina> valorPagarNominaList) {
        this.valorPagarNominaList = valorPagarNominaList;
    }

    @XmlTransient
    public List<EstadoNomina> getEstadoNominaList() {
        return estadoNominaList;
    }

    public void setEstadoNominaList(List<EstadoNomina> estadoNominaList) {
        this.estadoNominaList = estadoNominaList;
    }

    public GrupoNominal getIdGrupoNominal() {
        return idGrupoNominal;
    }

    public void setIdGrupoNominal(GrupoNominal idGrupoNominal) {
        this.idGrupoNominal = idGrupoNominal;
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
        if (!(object instanceof Nomina)) {
            return false;
        }
        Nomina other = (Nomina) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Nomina[ id=" + id + " ]";
    }
    
}
