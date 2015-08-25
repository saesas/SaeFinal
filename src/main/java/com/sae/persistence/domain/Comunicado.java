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
@Table(name = "comunicado", catalog = "bdsae", schema = "comunicaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comunicado.findAll", query = "SELECT c FROM Comunicado c"),
    @NamedQuery(name = "Comunicado.findById", query = "SELECT c FROM Comunicado c WHERE c.id = :id"),
    @NamedQuery(name = "Comunicado.findByNombre", query = "SELECT c FROM Comunicado c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Comunicado.findByCodigo", query = "SELECT c FROM Comunicado c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "Comunicado.findByIdMedioEnvio", query = "SELECT c FROM Comunicado c WHERE c.idMedioEnvio = :idMedioEnvio"),
    @NamedQuery(name = "Comunicado.findByAsunto", query = "SELECT c FROM Comunicado c WHERE c.asunto = :asunto"),
    @NamedQuery(name = "Comunicado.findByCuerpoMensaje", query = "SELECT c FROM Comunicado c WHERE c.cuerpoMensaje = :cuerpoMensaje"),
    @NamedQuery(name = "Comunicado.findByIdDestinatario", query = "SELECT c FROM Comunicado c WHERE c.idDestinatario = :idDestinatario"),
    @NamedQuery(name = "Comunicado.findByIdRemitente", query = "SELECT c FROM Comunicado c WHERE c.idRemitente = :idRemitente"),
    @NamedQuery(name = "Comunicado.findByIdProyecto", query = "SELECT c FROM Comunicado c WHERE c.idProyecto = :idProyecto"),
    @NamedQuery(name = "Comunicado.findByIdPropuesta", query = "SELECT c FROM Comunicado c WHERE c.idPropuesta = :idPropuesta"),
    @NamedQuery(name = "Comunicado.findByFechaRegistro", query = "SELECT c FROM Comunicado c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Comunicado.findByIdUsuario", query = "SELECT c FROM Comunicado c WHERE c.idUsuario = :idUsuario"),
    @NamedQuery(name = "Comunicado.findByIdRazonSocial", query = "SELECT c FROM Comunicado c WHERE c.idRazonSocial = :idRazonSocial")})
public class Comunicado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "id_medio_envio")
    private Integer idMedioEnvio;
    @Column(name = "asunto")
    private String asunto;
    @Column(name = "cuerpo_mensaje")
    private String cuerpoMensaje;
    @Column(name = "id_destinatario")
    private Integer idDestinatario;
    @Column(name = "id_remitente")
    private Integer idRemitente;
    @Column(name = "id_proyecto")
    private Integer idProyecto;
    @Column(name = "id_propuesta")
    private Integer idPropuesta;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_razon_social")
    private Integer idRazonSocial;
    @JoinColumn(name = "objeto", referencedColumnName = "id")
    @ManyToOne
    private ObjetoComunicado objeto;
    @OneToMany(mappedBy = "idComunicado")
    private List<CopiaComunicado> copiaComunicadoList;

    public Comunicado() {
    }

    public Comunicado(Integer id) {
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getIdMedioEnvio() {
        return idMedioEnvio;
    }

    public void setIdMedioEnvio(Integer idMedioEnvio) {
        this.idMedioEnvio = idMedioEnvio;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getCuerpoMensaje() {
        return cuerpoMensaje;
    }

    public void setCuerpoMensaje(String cuerpoMensaje) {
        this.cuerpoMensaje = cuerpoMensaje;
    }

    public Integer getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(Integer idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public Integer getIdRemitente() {
        return idRemitente;
    }

    public void setIdRemitente(Integer idRemitente) {
        this.idRemitente = idRemitente;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Integer getIdPropuesta() {
        return idPropuesta;
    }

    public void setIdPropuesta(Integer idPropuesta) {
        this.idPropuesta = idPropuesta;
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

    public Integer getIdRazonSocial() {
        return idRazonSocial;
    }

    public void setIdRazonSocial(Integer idRazonSocial) {
        this.idRazonSocial = idRazonSocial;
    }

    public ObjetoComunicado getObjeto() {
        return objeto;
    }

    public void setObjeto(ObjetoComunicado objeto) {
        this.objeto = objeto;
    }

    @XmlTransient
    public List<CopiaComunicado> getCopiaComunicadoList() {
        return copiaComunicadoList;
    }

    public void setCopiaComunicadoList(List<CopiaComunicado> copiaComunicadoList) {
        this.copiaComunicadoList = copiaComunicadoList;
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
        if (!(object instanceof Comunicado)) {
            return false;
        }
        Comunicado other = (Comunicado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.Comunicado[ id=" + id + " ]";
    }
    
}
