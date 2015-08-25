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
@Table(name = "parametro_contable_proyecto", catalog = "bdsae", schema = "contabilidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametroContableProyecto.findAll", query = "SELECT p FROM ParametroContableProyecto p"),
    @NamedQuery(name = "ParametroContableProyecto.findById", query = "SELECT p FROM ParametroContableProyecto p WHERE p.id = :id"),
    @NamedQuery(name = "ParametroContableProyecto.findByFic", query = "SELECT p FROM ParametroContableProyecto p WHERE p.fic = :fic"),
    @NamedQuery(name = "ParametroContableProyecto.findByReteiva", query = "SELECT p FROM ParametroContableProyecto p WHERE p.reteiva = :reteiva"),
    @NamedQuery(name = "ParametroContableProyecto.findByEstado", query = "SELECT p FROM ParametroContableProyecto p WHERE p.estado = :estado"),
    @NamedQuery(name = "ParametroContableProyecto.findByIdRazonSocial", query = "SELECT p FROM ParametroContableProyecto p WHERE p.idRazonSocial = :idRazonSocial"),
    @NamedQuery(name = "ParametroContableProyecto.findByFechaRegistro", query = "SELECT p FROM ParametroContableProyecto p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ParametroContableProyecto.findByIdUsuario", query = "SELECT p FROM ParametroContableProyecto p WHERE p.idUsuario = :idUsuario")})
public class ParametroContableProyecto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fic")
    private Boolean fic;
    @Column(name = "reteiva")
    private Boolean reteiva;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @OneToMany(mappedBy = "idParametro")
    private List<ParametroContableTipoProyecto> parametroContableTipoProyectoList;
    @JoinColumn(name = "id_causacion_iva", referencedColumnName = "id")
    @ManyToOne
    private TipoIva idCausacionIva;
    @JoinColumn(name = "id_nivel_puc", referencedColumnName = "id")
    @ManyToOne
    private NivelPuc idNivelPuc;

    public ParametroContableProyecto() {
    }

    public ParametroContableProyecto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getFic() {
        return fic;
    }

    public void setFic(Boolean fic) {
        this.fic = fic;
    }

    public Boolean getReteiva() {
        return reteiva;
    }

    public void setReteiva(Boolean reteiva) {
        this.reteiva = reteiva;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
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
    public List<ParametroContableTipoProyecto> getParametroContableTipoProyectoList() {
        return parametroContableTipoProyectoList;
    }

    public void setParametroContableTipoProyectoList(List<ParametroContableTipoProyecto> parametroContableTipoProyectoList) {
        this.parametroContableTipoProyectoList = parametroContableTipoProyectoList;
    }

    public TipoIva getIdCausacionIva() {
        return idCausacionIva;
    }

    public void setIdCausacionIva(TipoIva idCausacionIva) {
        this.idCausacionIva = idCausacionIva;
    }

    public NivelPuc getIdNivelPuc() {
        return idNivelPuc;
    }

    public void setIdNivelPuc(NivelPuc idNivelPuc) {
        this.idNivelPuc = idNivelPuc;
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
        if (!(object instanceof ParametroContableProyecto)) {
            return false;
        }
        ParametroContableProyecto other = (ParametroContableProyecto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ParametroContableProyecto[ id=" + id + " ]";
    }
    
}
