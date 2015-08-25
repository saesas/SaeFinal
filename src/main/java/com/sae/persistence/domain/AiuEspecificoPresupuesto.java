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
@Table(name = "aiu_especifico_presupuesto", catalog = "bdsae", schema = "tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AiuEspecificoPresupuesto.findAll", query = "SELECT a FROM AiuEspecificoPresupuesto a"),
    @NamedQuery(name = "AiuEspecificoPresupuesto.findById", query = "SELECT a FROM AiuEspecificoPresupuesto a WHERE a.id = :id"),
    @NamedQuery(name = "AiuEspecificoPresupuesto.findByValor", query = "SELECT a FROM AiuEspecificoPresupuesto a WHERE a.valor = :valor"),
    @NamedQuery(name = "AiuEspecificoPresupuesto.findByEstado", query = "SELECT a FROM AiuEspecificoPresupuesto a WHERE a.estado = :estado"),
    @NamedQuery(name = "AiuEspecificoPresupuesto.findByFechaRegistro", query = "SELECT a FROM AiuEspecificoPresupuesto a WHERE a.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "AiuEspecificoPresupuesto.findByIdUsuario", query = "SELECT a FROM AiuEspecificoPresupuesto a WHERE a.idUsuario = :idUsuario")})
public class AiuEspecificoPresupuesto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_presupuesto", referencedColumnName = "id")
    @ManyToOne
    private Presupuesto idPresupuesto;
    @JoinColumn(name = "id_item_aiu", referencedColumnName = "id")
    @ManyToOne
    private ItemAiu idItemAiu;

    public AiuEspecificoPresupuesto() {
    }

    public AiuEspecificoPresupuesto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
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

    public Presupuesto getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(Presupuesto idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    public ItemAiu getIdItemAiu() {
        return idItemAiu;
    }

    public void setIdItemAiu(ItemAiu idItemAiu) {
        this.idItemAiu = idItemAiu;
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
        if (!(object instanceof AiuEspecificoPresupuesto)) {
            return false;
        }
        AiuEspecificoPresupuesto other = (AiuEspecificoPresupuesto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.AiuEspecificoPresupuesto[ id=" + id + " ]";
    }
    
}
