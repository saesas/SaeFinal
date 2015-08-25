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
@Table(name = "experiencia_laboral", catalog = "bdsae", schema = "talento_humano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExperienciaLaboral.findAll", query = "SELECT e FROM ExperienciaLaboral e"),
    @NamedQuery(name = "ExperienciaLaboral.findById", query = "SELECT e FROM ExperienciaLaboral e WHERE e.id = :id"),
    @NamedQuery(name = "ExperienciaLaboral.findByEstado", query = "SELECT e FROM ExperienciaLaboral e WHERE e.estado = :estado"),
    @NamedQuery(name = "ExperienciaLaboral.findByNombreEmpresa", query = "SELECT e FROM ExperienciaLaboral e WHERE e.nombreEmpresa = :nombreEmpresa"),
    @NamedQuery(name = "ExperienciaLaboral.findByTelefono", query = "SELECT e FROM ExperienciaLaboral e WHERE e.telefono = :telefono"),
    @NamedQuery(name = "ExperienciaLaboral.findByEmail", query = "SELECT e FROM ExperienciaLaboral e WHERE e.email = :email"),
    @NamedQuery(name = "ExperienciaLaboral.findByNombreJefeDirecto", query = "SELECT e FROM ExperienciaLaboral e WHERE e.nombreJefeDirecto = :nombreJefeDirecto"),
    @NamedQuery(name = "ExperienciaLaboral.findByCargoJefe", query = "SELECT e FROM ExperienciaLaboral e WHERE e.cargoJefe = :cargoJefe"),
    @NamedQuery(name = "ExperienciaLaboral.findByCargoDesempenado", query = "SELECT e FROM ExperienciaLaboral e WHERE e.cargoDesempenado = :cargoDesempenado"),
    @NamedQuery(name = "ExperienciaLaboral.findByTelefonoJefe", query = "SELECT e FROM ExperienciaLaboral e WHERE e.telefonoJefe = :telefonoJefe"),
    @NamedQuery(name = "ExperienciaLaboral.findByEmailJefe", query = "SELECT e FROM ExperienciaLaboral e WHERE e.emailJefe = :emailJefe"),
    @NamedQuery(name = "ExperienciaLaboral.findByIdUsuario", query = "SELECT e FROM ExperienciaLaboral e WHERE e.idUsuario = :idUsuario"),
    @NamedQuery(name = "ExperienciaLaboral.findByFechaRegistro", query = "SELECT e FROM ExperienciaLaboral e WHERE e.fechaRegistro = :fechaRegistro")})
public class ExperienciaLaboral implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "nombre_empresa")
    private String nombreEmpresa;
    @Column(name = "telefono")
    private Integer telefono;
    @Column(name = "email")
    private String email;
    @Column(name = "nombre_jefe_directo")
    private String nombreJefeDirecto;
    @Column(name = "cargo_jefe")
    private String cargoJefe;
    @Column(name = "cargo_desempenado")
    private String cargoDesempenado;
    @Column(name = "telefono_jefe")
    private Integer telefonoJefe;
    @Column(name = "email_jefe")
    private String emailJefe;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @JoinColumn(name = "id_hoja_vida", referencedColumnName = "id")
    @ManyToOne
    private HojaVida idHojaVida;

    public ExperienciaLaboral() {
    }

    public ExperienciaLaboral(Integer id) {
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

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreJefeDirecto() {
        return nombreJefeDirecto;
    }

    public void setNombreJefeDirecto(String nombreJefeDirecto) {
        this.nombreJefeDirecto = nombreJefeDirecto;
    }

    public String getCargoJefe() {
        return cargoJefe;
    }

    public void setCargoJefe(String cargoJefe) {
        this.cargoJefe = cargoJefe;
    }

    public String getCargoDesempenado() {
        return cargoDesempenado;
    }

    public void setCargoDesempenado(String cargoDesempenado) {
        this.cargoDesempenado = cargoDesempenado;
    }

    public Integer getTelefonoJefe() {
        return telefonoJefe;
    }

    public void setTelefonoJefe(Integer telefonoJefe) {
        this.telefonoJefe = telefonoJefe;
    }

    public String getEmailJefe() {
        return emailJefe;
    }

    public void setEmailJefe(String emailJefe) {
        this.emailJefe = emailJefe;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public HojaVida getIdHojaVida() {
        return idHojaVida;
    }

    public void setIdHojaVida(HojaVida idHojaVida) {
        this.idHojaVida = idHojaVida;
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
        if (!(object instanceof ExperienciaLaboral)) {
            return false;
        }
        ExperienciaLaboral other = (ExperienciaLaboral) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ExperienciaLaboral[ id=" + id + " ]";
    }
    
}
