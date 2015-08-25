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
@Table(name = "evaluacion_convocatoria", catalog = "bdsae", schema = "licitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EvaluacionConvocatoria.findAll", query = "SELECT e FROM EvaluacionConvocatoria e"),
    @NamedQuery(name = "EvaluacionConvocatoria.findById", query = "SELECT e FROM EvaluacionConvocatoria e WHERE e.id = :id"),
    @NamedQuery(name = "EvaluacionConvocatoria.findByEstado", query = "SELECT e FROM EvaluacionConvocatoria e WHERE e.estado = :estado"),
    @NamedQuery(name = "EvaluacionConvocatoria.findByValor", query = "SELECT e FROM EvaluacionConvocatoria e WHERE e.valor = :valor"),
    @NamedQuery(name = "EvaluacionConvocatoria.findByFechaRegistro", query = "SELECT e FROM EvaluacionConvocatoria e WHERE e.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "EvaluacionConvocatoria.findByIdUsuario", query = "SELECT e FROM EvaluacionConvocatoria e WHERE e.idUsuario = :idUsuario")})
public class EvaluacionConvocatoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "valor")
    private String valor;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @JoinColumn(name = "id_item", referencedColumnName = "id")
    @ManyToOne
    private ItemEvaluacionConvocatoria idItem;
    @JoinColumn(name = "id_convocatoria", referencedColumnName = "id")
    @ManyToOne
    private Convocatoria idConvocatoria;
    @JoinColumn(name = "id_adjunto_item", referencedColumnName = "id")
    @ManyToOne
    private AdjuntoConvocatoria idAdjuntoItem;

    public EvaluacionConvocatoria() {
    }

    public EvaluacionConvocatoria(Integer id) {
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
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

    public ItemEvaluacionConvocatoria getIdItem() {
        return idItem;
    }

    public void setIdItem(ItemEvaluacionConvocatoria idItem) {
        this.idItem = idItem;
    }

    public Convocatoria getIdConvocatoria() {
        return idConvocatoria;
    }

    public void setIdConvocatoria(Convocatoria idConvocatoria) {
        this.idConvocatoria = idConvocatoria;
    }

    public AdjuntoConvocatoria getIdAdjuntoItem() {
        return idAdjuntoItem;
    }

    public void setIdAdjuntoItem(AdjuntoConvocatoria idAdjuntoItem) {
        this.idAdjuntoItem = idAdjuntoItem;
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
        if (!(object instanceof EvaluacionConvocatoria)) {
            return false;
        }
        EvaluacionConvocatoria other = (EvaluacionConvocatoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.EvaluacionConvocatoria[ id=" + id + " ]";
    }
    
}
