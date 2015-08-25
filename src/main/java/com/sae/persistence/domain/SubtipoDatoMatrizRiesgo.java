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
@Table(name = "subtipo_dato_matriz_riesgo", catalog = "bdsae", schema = "licitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubtipoDatoMatrizRiesgo.findAll", query = "SELECT s FROM SubtipoDatoMatrizRiesgo s"),
    @NamedQuery(name = "SubtipoDatoMatrizRiesgo.findById", query = "SELECT s FROM SubtipoDatoMatrizRiesgo s WHERE s.id = :id"),
    @NamedQuery(name = "SubtipoDatoMatrizRiesgo.findByNombre", query = "SELECT s FROM SubtipoDatoMatrizRiesgo s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "SubtipoDatoMatrizRiesgo.findByEstado", query = "SELECT s FROM SubtipoDatoMatrizRiesgo s WHERE s.estado = :estado"),
    @NamedQuery(name = "SubtipoDatoMatrizRiesgo.findByFechaRegistro", query = "SELECT s FROM SubtipoDatoMatrizRiesgo s WHERE s.fechaRegistro = :fechaRegistro")})
public class SubtipoDatoMatrizRiesgo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id")
    @ManyToOne
    private TipoDatoMatrizRiesgo idTipo;
    @OneToMany(mappedBy = "idTipo")
    private List<RegistroMatrizRiesgo> registroMatrizRiesgoList;
    @OneToMany(mappedBy = "idProbabilidadTratamiento")
    private List<RegistroMatrizRiesgo> registroMatrizRiesgoList1;
    @OneToMany(mappedBy = "idProbabilidad")
    private List<RegistroMatrizRiesgo> registroMatrizRiesgoList2;
    @OneToMany(mappedBy = "idMonitoreo")
    private List<RegistroMatrizRiesgo> registroMatrizRiesgoList3;
    @OneToMany(mappedBy = "idFuente")
    private List<RegistroMatrizRiesgo> registroMatrizRiesgoList4;
    @OneToMany(mappedBy = "idEtapa")
    private List<RegistroMatrizRiesgo> registroMatrizRiesgoList5;
    @OneToMany(mappedBy = "idClase")
    private List<RegistroMatrizRiesgo> registroMatrizRiesgoList6;
    @OneToMany(mappedBy = "idCategoriaTratamiento")
    private List<RegistroMatrizRiesgo> registroMatrizRiesgoList7;
    @OneToMany(mappedBy = "idCategoria")
    private List<RegistroMatrizRiesgo> registroMatrizRiesgoList8;

    public SubtipoDatoMatrizRiesgo() {
    }

    public SubtipoDatoMatrizRiesgo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public TipoDatoMatrizRiesgo getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(TipoDatoMatrizRiesgo idTipo) {
        this.idTipo = idTipo;
    }

    @XmlTransient
    public List<RegistroMatrizRiesgo> getRegistroMatrizRiesgoList() {
        return registroMatrizRiesgoList;
    }

    public void setRegistroMatrizRiesgoList(List<RegistroMatrizRiesgo> registroMatrizRiesgoList) {
        this.registroMatrizRiesgoList = registroMatrizRiesgoList;
    }

    @XmlTransient
    public List<RegistroMatrizRiesgo> getRegistroMatrizRiesgoList1() {
        return registroMatrizRiesgoList1;
    }

    public void setRegistroMatrizRiesgoList1(List<RegistroMatrizRiesgo> registroMatrizRiesgoList1) {
        this.registroMatrizRiesgoList1 = registroMatrizRiesgoList1;
    }

    @XmlTransient
    public List<RegistroMatrizRiesgo> getRegistroMatrizRiesgoList2() {
        return registroMatrizRiesgoList2;
    }

    public void setRegistroMatrizRiesgoList2(List<RegistroMatrizRiesgo> registroMatrizRiesgoList2) {
        this.registroMatrizRiesgoList2 = registroMatrizRiesgoList2;
    }

    @XmlTransient
    public List<RegistroMatrizRiesgo> getRegistroMatrizRiesgoList3() {
        return registroMatrizRiesgoList3;
    }

    public void setRegistroMatrizRiesgoList3(List<RegistroMatrizRiesgo> registroMatrizRiesgoList3) {
        this.registroMatrizRiesgoList3 = registroMatrizRiesgoList3;
    }

    @XmlTransient
    public List<RegistroMatrizRiesgo> getRegistroMatrizRiesgoList4() {
        return registroMatrizRiesgoList4;
    }

    public void setRegistroMatrizRiesgoList4(List<RegistroMatrizRiesgo> registroMatrizRiesgoList4) {
        this.registroMatrizRiesgoList4 = registroMatrizRiesgoList4;
    }

    @XmlTransient
    public List<RegistroMatrizRiesgo> getRegistroMatrizRiesgoList5() {
        return registroMatrizRiesgoList5;
    }

    public void setRegistroMatrizRiesgoList5(List<RegistroMatrizRiesgo> registroMatrizRiesgoList5) {
        this.registroMatrizRiesgoList5 = registroMatrizRiesgoList5;
    }

    @XmlTransient
    public List<RegistroMatrizRiesgo> getRegistroMatrizRiesgoList6() {
        return registroMatrizRiesgoList6;
    }

    public void setRegistroMatrizRiesgoList6(List<RegistroMatrizRiesgo> registroMatrizRiesgoList6) {
        this.registroMatrizRiesgoList6 = registroMatrizRiesgoList6;
    }

    @XmlTransient
    public List<RegistroMatrizRiesgo> getRegistroMatrizRiesgoList7() {
        return registroMatrizRiesgoList7;
    }

    public void setRegistroMatrizRiesgoList7(List<RegistroMatrizRiesgo> registroMatrizRiesgoList7) {
        this.registroMatrizRiesgoList7 = registroMatrizRiesgoList7;
    }

    @XmlTransient
    public List<RegistroMatrizRiesgo> getRegistroMatrizRiesgoList8() {
        return registroMatrizRiesgoList8;
    }

    public void setRegistroMatrizRiesgoList8(List<RegistroMatrizRiesgo> registroMatrizRiesgoList8) {
        this.registroMatrizRiesgoList8 = registroMatrizRiesgoList8;
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
        if (!(object instanceof SubtipoDatoMatrizRiesgo)) {
            return false;
        }
        SubtipoDatoMatrizRiesgo other = (SubtipoDatoMatrizRiesgo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.SubtipoDatoMatrizRiesgo[ id=" + id + " ]";
    }
    
}
