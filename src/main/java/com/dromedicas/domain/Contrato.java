package com.dromedicas.domain;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the contrato database table.
 * 
 */
@ManagedBean(name="contrato")
@SessionScoped
@Entity
@NamedQuery(name="Contrato.findAll", query="SELECT c FROM Contrato c")
public class Contrato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idcontrato;

	private int basegravable;

	private byte envioemail;

	private byte enviosms;

	private int factorpuntos;
	
	private int vrminimoredimir;

	@Temporal(TemporalType.DATE)
	private Date fechafin;

	@Temporal(TemporalType.DATE)
	private Date fechainicio;

	private byte redensionsucursales;

	//bi-directional many-to-one association to Empresa
	@ManyToOne
	@JoinColumn(name="idempresa")
	private Empresa empresa;

	public Contrato() {
	}

	public int getIdcontrato() {
		return this.idcontrato;
	}

	public void setIdcontrato(int idcontrato) {
		this.idcontrato = idcontrato;
	}

	public int getBasegravable() {
		return this.basegravable;
	}

	public void setBasegravable(int basegravable) {
		this.basegravable = basegravable;
	}

	public byte getEnvioemail() {
		return this.envioemail;
	}

	public void setEnvioemail(byte envioemail) {
		this.envioemail = envioemail;
	}

	public byte getEnviosms() {
		return this.enviosms;
	}

	public void setEnviosms(byte enviosms) {
		this.enviosms = enviosms;
	}

	public int getFactorpuntos() {
		return this.factorpuntos;
	}

	public void setFactorpuntos(int factorpuntos) {
		this.factorpuntos = factorpuntos;
	}

	public Date getFechafin() {
		return this.fechafin;
	}

	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}

	public Date getFechainicio() {
		return this.fechainicio;
	}

	public void setFechainicio(Date fechainicio) {
		System.out.println("Fecha Inicio: "  +  fechainicio );
		this.fechainicio = fechainicio;
	}

	public byte getRedensionsucursales() {
		return this.redensionsucursales;
	}

	public void setRedensionsucursales(byte redensionsucursales) {
		this.redensionsucursales = redensionsucursales;
	}

	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public int getVrminimoredimir() {
		return vrminimoredimir;
	}

	public void setVrminimoredimir(int vrminimoredimir) {
		this.vrminimoredimir = vrminimoredimir;
	}
	
	

}