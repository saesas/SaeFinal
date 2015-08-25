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
@Table(name = "valor_pagar_nomina", catalog = "bdsae", schema = "talento_humano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValorPagarNomina.findAll", query = "SELECT v FROM ValorPagarNomina v"),
    @NamedQuery(name = "ValorPagarNomina.findById", query = "SELECT v FROM ValorPagarNomina v WHERE v.id = :id"),
    @NamedQuery(name = "ValorPagarNomina.findByDiaDiurno", query = "SELECT v FROM ValorPagarNomina v WHERE v.diaDiurno = :diaDiurno"),
    @NamedQuery(name = "ValorPagarNomina.findByDiaNocturno", query = "SELECT v FROM ValorPagarNomina v WHERE v.diaNocturno = :diaNocturno"),
    @NamedQuery(name = "ValorPagarNomina.findByDiaDfDiurno", query = "SELECT v FROM ValorPagarNomina v WHERE v.diaDfDiurno = :diaDfDiurno"),
    @NamedQuery(name = "ValorPagarNomina.findByDiaDfNocturno", query = "SELECT v FROM ValorPagarNomina v WHERE v.diaDfNocturno = :diaDfNocturno"),
    @NamedQuery(name = "ValorPagarNomina.findByBonificacionNoPrestacional", query = "SELECT v FROM ValorPagarNomina v WHERE v.bonificacionNoPrestacional = :bonificacionNoPrestacional"),
    @NamedQuery(name = "ValorPagarNomina.findByViatico", query = "SELECT v FROM ValorPagarNomina v WHERE v.viatico = :viatico"),
    @NamedQuery(name = "ValorPagarNomina.findByAuxilioTransporte", query = "SELECT v FROM ValorPagarNomina v WHERE v.auxilioTransporte = :auxilioTransporte"),
    @NamedQuery(name = "ValorPagarNomina.findByFondoEmpleados", query = "SELECT v FROM ValorPagarNomina v WHERE v.fondoEmpleados = :fondoEmpleados"),
    @NamedQuery(name = "ValorPagarNomina.findByPrestamo", query = "SELECT v FROM ValorPagarNomina v WHERE v.prestamo = :prestamo"),
    @NamedQuery(name = "ValorPagarNomina.findBySalarioBasico", query = "SELECT v FROM ValorPagarNomina v WHERE v.salarioBasico = :salarioBasico"),
    @NamedQuery(name = "ValorPagarNomina.findByBonificacion", query = "SELECT v FROM ValorPagarNomina v WHERE v.bonificacion = :bonificacion"),
    @NamedQuery(name = "ValorPagarNomina.findBySalarioTotal", query = "SELECT v FROM ValorPagarNomina v WHERE v.salarioTotal = :salarioTotal"),
    @NamedQuery(name = "ValorPagarNomina.findByEps", query = "SELECT v FROM ValorPagarNomina v WHERE v.eps = :eps"),
    @NamedQuery(name = "ValorPagarNomina.findByAfp", query = "SELECT v FROM ValorPagarNomina v WHERE v.afp = :afp"),
    @NamedQuery(name = "ValorPagarNomina.findByArp", query = "SELECT v FROM ValorPagarNomina v WHERE v.arp = :arp"),
    @NamedQuery(name = "ValorPagarNomina.findByCcf", query = "SELECT v FROM ValorPagarNomina v WHERE v.ccf = :ccf"),
    @NamedQuery(name = "ValorPagarNomina.findByDiasAsistencia", query = "SELECT v FROM ValorPagarNomina v WHERE v.diasAsistencia = :diasAsistencia"),
    @NamedQuery(name = "ValorPagarNomina.findByIdUsuario", query = "SELECT v FROM ValorPagarNomina v WHERE v.idUsuario = :idUsuario"),
    @NamedQuery(name = "ValorPagarNomina.findByFechaRegistro", query = "SELECT v FROM ValorPagarNomina v WHERE v.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "ValorPagarNomina.findByCesantias", query = "SELECT v FROM ValorPagarNomina v WHERE v.cesantias = :cesantias"),
    @NamedQuery(name = "ValorPagarNomina.findByInteresesCesantias", query = "SELECT v FROM ValorPagarNomina v WHERE v.interesesCesantias = :interesesCesantias"),
    @NamedQuery(name = "ValorPagarNomina.findByPrima", query = "SELECT v FROM ValorPagarNomina v WHERE v.prima = :prima"),
    @NamedQuery(name = "ValorPagarNomina.findByVacaciones", query = "SELECT v FROM ValorPagarNomina v WHERE v.vacaciones = :vacaciones")})
public class ValorPagarNomina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dia_diurno")
    private Double diaDiurno;
    @Column(name = "dia_nocturno")
    private Double diaNocturno;
    @Column(name = "dia_df_diurno")
    private Double diaDfDiurno;
    @Column(name = "dia_df_nocturno")
    private Double diaDfNocturno;
    @Column(name = "bonificacion_no_prestacional")
    private Double bonificacionNoPrestacional;
    @Column(name = "viatico")
    private Double viatico;
    @Column(name = "auxilio_transporte")
    private Double auxilioTransporte;
    @Column(name = "fondo_empleados")
    private Double fondoEmpleados;
    @Column(name = "prestamo")
    private Double prestamo;
    @Column(name = "salario_basico")
    private Double salarioBasico;
    @Column(name = "bonificacion")
    private Double bonificacion;
    @Column(name = "salario_total")
    private Double salarioTotal;
    @Column(name = "eps")
    private Double eps;
    @Column(name = "afp")
    private Double afp;
    @Column(name = "arp")
    private Double arp;
    @Column(name = "ccf")
    private Double ccf;
    @Column(name = "dias_asistencia")
    private Integer diasAsistencia;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "cesantias")
    private Double cesantias;
    @Column(name = "intereses_cesantias")
    private Double interesesCesantias;
    @Column(name = "prima")
    private Double prima;
    @Column(name = "vacaciones")
    private Double vacaciones;
    @JoinColumn(name = "id_nomina", referencedColumnName = "id")
    @ManyToOne
    private Nomina idNomina;
    @JoinColumn(name = "id_contrato", referencedColumnName = "id")
    @ManyToOne
    private ContratoTercero idContrato;

    public ValorPagarNomina() {
    }

    public ValorPagarNomina(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getDiaDiurno() {
        return diaDiurno;
    }

    public void setDiaDiurno(Double diaDiurno) {
        this.diaDiurno = diaDiurno;
    }

    public Double getDiaNocturno() {
        return diaNocturno;
    }

    public void setDiaNocturno(Double diaNocturno) {
        this.diaNocturno = diaNocturno;
    }

    public Double getDiaDfDiurno() {
        return diaDfDiurno;
    }

    public void setDiaDfDiurno(Double diaDfDiurno) {
        this.diaDfDiurno = diaDfDiurno;
    }

    public Double getDiaDfNocturno() {
        return diaDfNocturno;
    }

    public void setDiaDfNocturno(Double diaDfNocturno) {
        this.diaDfNocturno = diaDfNocturno;
    }

    public Double getBonificacionNoPrestacional() {
        return bonificacionNoPrestacional;
    }

    public void setBonificacionNoPrestacional(Double bonificacionNoPrestacional) {
        this.bonificacionNoPrestacional = bonificacionNoPrestacional;
    }

    public Double getViatico() {
        return viatico;
    }

    public void setViatico(Double viatico) {
        this.viatico = viatico;
    }

    public Double getAuxilioTransporte() {
        return auxilioTransporte;
    }

    public void setAuxilioTransporte(Double auxilioTransporte) {
        this.auxilioTransporte = auxilioTransporte;
    }

    public Double getFondoEmpleados() {
        return fondoEmpleados;
    }

    public void setFondoEmpleados(Double fondoEmpleados) {
        this.fondoEmpleados = fondoEmpleados;
    }

    public Double getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Double prestamo) {
        this.prestamo = prestamo;
    }

    public Double getSalarioBasico() {
        return salarioBasico;
    }

    public void setSalarioBasico(Double salarioBasico) {
        this.salarioBasico = salarioBasico;
    }

    public Double getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(Double bonificacion) {
        this.bonificacion = bonificacion;
    }

    public Double getSalarioTotal() {
        return salarioTotal;
    }

    public void setSalarioTotal(Double salarioTotal) {
        this.salarioTotal = salarioTotal;
    }

    public Double getEps() {
        return eps;
    }

    public void setEps(Double eps) {
        this.eps = eps;
    }

    public Double getAfp() {
        return afp;
    }

    public void setAfp(Double afp) {
        this.afp = afp;
    }

    public Double getArp() {
        return arp;
    }

    public void setArp(Double arp) {
        this.arp = arp;
    }

    public Double getCcf() {
        return ccf;
    }

    public void setCcf(Double ccf) {
        this.ccf = ccf;
    }

    public Integer getDiasAsistencia() {
        return diasAsistencia;
    }

    public void setDiasAsistencia(Integer diasAsistencia) {
        this.diasAsistencia = diasAsistencia;
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

    public Double getCesantias() {
        return cesantias;
    }

    public void setCesantias(Double cesantias) {
        this.cesantias = cesantias;
    }

    public Double getInteresesCesantias() {
        return interesesCesantias;
    }

    public void setInteresesCesantias(Double interesesCesantias) {
        this.interesesCesantias = interesesCesantias;
    }

    public Double getPrima() {
        return prima;
    }

    public void setPrima(Double prima) {
        this.prima = prima;
    }

    public Double getVacaciones() {
        return vacaciones;
    }

    public void setVacaciones(Double vacaciones) {
        this.vacaciones = vacaciones;
    }

    public Nomina getIdNomina() {
        return idNomina;
    }

    public void setIdNomina(Nomina idNomina) {
        this.idNomina = idNomina;
    }

    public ContratoTercero getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(ContratoTercero idContrato) {
        this.idContrato = idContrato;
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
        if (!(object instanceof ValorPagarNomina)) {
            return false;
        }
        ValorPagarNomina other = (ValorPagarNomina) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sae.persistence.domain.ValorPagarNomina[ id=" + id + " ]";
    }
    
}
