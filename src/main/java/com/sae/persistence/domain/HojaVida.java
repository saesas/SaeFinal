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
@Table(name = "hoja_vida", catalog = "bdsae", schema = "talento_humano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HojaVida.findAll", query = "SELECT h FROM HojaVida h"),
    @NamedQuery(name = "HojaVida.findById", query = "SELECT h FROM HojaVida h WHERE h.id = :id"),
    @NamedQuery(name = "HojaVida.findByIdTercero", query = "SELECT h FROM HojaVida h WHERE h.idTercero = :idTercero"),
    @NamedQuery(name = "HojaVida.findByAnosExperiencia", query = "SELECT h FROM HojaVida h WHERE h.anosExperiencia = :anosExperiencia"),
    @NamedQuery(name = "HojaVida.findByIdProfesion", query = "SELECT h FROM HojaVida h WHERE h.idProfesion = :idProfesion"),
    @NamedQuery(name = "HojaVida.findByIdCargoAspira", query = "SELECT h FROM HojaVida h WHERE h.idCargoAspira = :idCargoAspira"),
    @NamedQuery(name = "HojaVida.findByNumeroLibreta", query = "SELECT h FROM HojaVida h WHERE h.numeroLibreta = :numeroLibreta"),
    @NamedQuery(name = "HojaVida.findByIdClaseLibretaMilitar", query = "SELECT h FROM HojaVida h WHERE h.idClaseLibretaMilitar = :idClaseLibretaMilitar"),
    @NamedQuery(name = "HojaVida.findByNumeroDistrito", query = "SELECT h FROM HojaVida h WHERE h.numeroDistrito = :numeroDistrito"),
    @NamedQuery(name = "HojaVida.findByServiciosPublicos", query = "SELECT h FROM HojaVida h WHERE h.serviciosPublicos = :serviciosPublicos"),
    @NamedQuery(name = "HojaVida.findByNombreContacto", query = "SELECT h FROM HojaVida h WHERE h.nombreContacto = :nombreContacto"),
    @NamedQuery(name = "HojaVida.findByTelefonoContacto", query = "SELECT h FROM HojaVida h WHERE h.telefonoContacto = :telefonoContacto"),
    @NamedQuery(name = "HojaVida.findByDireccionContacto", query = "SELECT h FROM HojaVida h WHERE h.direccionContacto = :direccionContacto"),
    @NamedQuery(name = "HojaVida.findByMunicipioContacto", query = "SELECT h FROM HojaVida h WHERE h.municipioContacto = :municipioContacto"),
    @NamedQuery(name = "HojaVida.findByTallaPantalon", query = "SELECT h FROM HojaVida h WHERE h.tallaPantalon = :tallaPantalon"),
    @NamedQuery(name = "HojaVida.findByTallaCamisa", query = "SELECT h FROM HojaVida h WHERE h.tallaCamisa = :tallaCamisa"),
    @NamedQuery(name = "HojaVida.findByTallaCalzado", query = "SELECT h FROM HojaVida h WHERE h.tallaCalzado = :tallaCalzado"),
    @NamedQuery(name = "HojaVida.findByAdjuntoFoto", query = "SELECT h FROM HojaVida h WHERE h.adjuntoFoto = :adjuntoFoto"),
    @NamedQuery(name = "HojaVida.findByAdjuntoCedula", query = "SELECT h FROM HojaVida h WHERE h.adjuntoCedula = :adjuntoCedula"),
    @NamedQuery(name = "HojaVida.findByFechaRegistro", query = "SELECT h FROM HojaVida h WHERE h.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "HojaVida.findByIdUsuario", query = "SELECT h FROM HojaVida h WHERE h.idUsuario = :idUsuario")})
public class HojaVida implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_tercero")
    private Integer idTercero;
    @Column(name = "anos_experiencia")
    private Integer anosExperiencia;
    @Column(name = "id_profesion")
    private Integer idProfesion;
    @Column(name = "id_cargo_aspira")
    private Integer idCargoAspira;
    @Column(name = "numero_libreta")
    private Integer numeroLibreta;
    @Column(name = "id_clase_libreta_militar")
    private Integer idClaseLibretaMilitar;
    @Column(name = "numero_distrito")
    private Integer numeroDistrito;
    @Column(name = "servicios_publicos")
    private Boolean serviciosPublicos;
    @Column(name = "nombre_contacto")
    private String nombreContacto;
    @Column(name = "telefono_contacto")
    private Integer telefonoContacto;
    @Column(name = "direccion_contacto")
    private String direccionContacto;
    @Column(name = "municipio_contacto")
    private Integer municipioContacto;
    @Column(name = "talla_pantalon")
    private String tallaPantalon;
    @Column(name = "talla_camisa")
    private String tallaCamisa;
    @Column(name = "talla_calzado")
    private String tallaCalzado;
    @Column(name = "adjunto_foto")
    private String adjuntoFoto;
    @Column(name = "adjunto_cedula")
    private String adjuntoCedula;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @OneToMany(mappedBy = "idHojaVida")
    private List<ExperienciaLaboral> experienciaLaboralList;
    @JoinColumn(name = "id_tipo_propiedad_habita", referencedColumnName = "id")
    @ManyToOne
    private TipoPropiedad idTipoPropiedadHabita;
    @JoinColumn(name = "id_parentesco_contacto", referencedColumnName = "id")
    @ManyToOne
    private Parentesco idParentescoContacto;
    @JoinColumn(name = "id_nivel_educacion", referencedColumnName = "id")
    @ManyToOne
    private NivelEducacion idNivelEducacion;
    @OneToMany(mappedBy = "idHojaVida")
    private List<ReferenciaPersonal> referenciaPersonalList;

    public HojaVida() {
    }

    public HojaVida(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdTercero() {
        return idTercero;
    }

    public void setIdTercero(Integer idTercero) {
        this.idTercero = idTercero;
    }

    public Integer getAnosExperiencia() {
        return anosExperiencia;
    }

    public void setAnosExperiencia(Integer anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }

    public Integer getIdProfesion() {
        return idProfesion;
    }

    public void setIdProfesion(Integer idProfesion) {
        this.idProfesion = idProfesion;
    }

    public Integer getIdCargoAspira() {
        return idCargoAspira;
    }

    public void setIdCargoAspira(Integer idCargoAspira) {
        this.idCargoAspira = idCargoAspira;
    }

    public Integer getNumeroLibreta() {
        return numeroLibreta;
    }

    public void setNumeroLibreta(Integer numeroLibreta) {
        this.numeroLibreta = numeroLibreta;
    }

    public Integer getIdClaseLibretaMilitar() {
        return idClaseLibretaMilitar;
    }

    public void setIdClaseLibretaMilitar(Integer idClaseLibretaMilitar) {
        this.idClaseLibretaMilitar = idClaseLibretaMilitar;
    }

    public Integer getNumeroDistrito() {
        return numeroDistrito;
    }

    public void setNumeroDistrito(Integer numeroDistrito) {
        this.numeroDistrito = numeroDistrito;
    }

    public Boolean getServiciosPublicos() {
        return serviciosPublicos;
    }

    public void setServiciosPublicos(Boolean serviciosPublicos) {
        this.serviciosPublicos = serviciosPublicos;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public Integer getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(Integer telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getDireccionContacto() {
        return direccionContacto;
    }

    public void setDireccionContacto(String direccionContacto) {
        this.direccionContacto = direccionContacto;
    }

    public Integer getMunicipioContacto() {
        return municipioContacto;
    }

    public void setMunicipioContacto(Integer municipioContacto) {
        this.municipioContacto = municipioContacto;
    }

    public String getTallaPantalon() {
        return tallaPantalon;
    }

    public void setTallaPantalon(String tallaPantalon) {
        this.tallaPantalon = tallaPantalon;
    }

    public String getTallaCamisa() {
        return tallaCamisa;
    }

    public void setTallaCamisa(String tallaCamisa) {
        this.tallaCamisa = tallaCamisa;
    }

    public String getTallaCalzado() {
        return tallaCalzado;
    }

    public void setTallaCalzado(String tallaCalzado) {
        this.tallaCalzado = tallaCalzado;
    }

    public String getAdjuntoFoto() {
        return adjuntoFoto;
    }

    public void setAdjuntoFoto(String adjuntoFoto) {
        this.adjuntoFoto = adjuntoFoto;
    }

    public String getAdjuntoCedula() {
        return adjuntoCedula;
    }

    public void setAdjuntoCedula(String adjuntoCedula) {
        this.adjuntoCedula = adjuntoCedula;
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
    public List<ExperienciaLaboral> getExperienciaLaboralList() {
        return experienciaLaboralList;
    }

    public void setExperienciaLaboralList(List<ExperienciaLaboral> experienciaLaboralList) {
        this.experienciaLaboralList = experienciaLaboralList;
    }

    public TipoPropiedad getIdTipoPropiedadHabita() {
        return idTipoPropiedadHabita;
    }

    public void setIdTipoPropiedadHabita(TipoPropiedad idTipoPropiedadHabita) {
        this.idTipoPropiedadHabita = idTipoPropiedadHabita;
    }

    public Parentesco getIdParentescoContacto() {
        return idParentescoContacto;
    }

    public void setIdParentescoContacto(Parentesco idParentescoContacto) {
        this.idParentescoContacto = idParentescoContacto;
    }

    public NivelEducacion getIdNivelEducacion() {
        return idNivelEducacion;
    }

    public void setIdNivelEducacion(NivelEducacion idNivelEducacion) {
        this.idNivelEducacion = idNivelEducacion;
    }

    @XmlTransient
    public List<ReferenciaPersonal> getReferenciaPersonalList() {
        return referenciaPersonalList;
    }

    public void setReferenciaPersonalList(List<ReferenciaPersonal> referenciaPersonalList) {
        this.referenciaPersonalList = referenciaPersonalList;
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
        if (!(object instanceof HojaVida)) {
            return false;
        }
        HojaVida other = (HojaVida) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.HojaVida[ id=" + id + " ]";
    }
    
}
