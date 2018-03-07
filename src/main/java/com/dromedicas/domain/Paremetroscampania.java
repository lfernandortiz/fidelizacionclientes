package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the paremetroscampania database table.
 * 
 */
@Entity
@Table(name="paremetroscampania")
@NamedQuery(name="Paremetroscampania.findAll", query="SELECT p FROM Paremetroscampania p")
public class Paremetroscampania implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int idparemetroscampania;

	private int edadfin;

	private int edadini;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaenvio;

	private byte hijosentre13y18;

	private byte hijosentre4y12;

	private byte hijosmayores;

	private byte hijosmenoresde4;

	private int idsucursal;

	@Column(length=5)
	private String sexo;

	//bi-directional many-to-one association to Campania
	@ManyToOne
	@JoinColumn(name="idcampania")
	private Campania campania;

	public Paremetroscampania() {
	}

	public int getIdparemetroscampania() {
		return this.idparemetroscampania;
	}

	public void setIdparemetroscampania(int idparemetroscampania) {
		this.idparemetroscampania = idparemetroscampania;
	}

	public int getEdadfin() {
		return this.edadfin;
	}

	public void setEdadfin(int edadfin) {
		this.edadfin = edadfin;
	}

	public int getEdadini() {
		return this.edadini;
	}

	public void setEdadini(int edadini) {
		this.edadini = edadini;
	}

	public Date getFechaenvio() {
		return this.fechaenvio;
	}

	public void setFechaenvio(Date fechaenvio) {
		this.fechaenvio = fechaenvio;
	}

	public byte getHijosentre13y18() {
		return this.hijosentre13y18;
	}

	public void setHijosentre13y18(byte hijosentre13y18) {
		this.hijosentre13y18 = hijosentre13y18;
	}

	public byte getHijosentre4y12() {
		return this.hijosentre4y12;
	}

	public void setHijosentre4y12(byte hijosentre4y12) {
		this.hijosentre4y12 = hijosentre4y12;
	}

	public byte getHijosmayores() {
		return this.hijosmayores;
	}

	public void setHijosmayores(byte hijosmayores) {
		this.hijosmayores = hijosmayores;
	}

	public byte getHijosmenoresde4() {
		return this.hijosmenoresde4;
	}

	public void setHijosmenoresde4(byte hijosmenoresde4) {
		this.hijosmenoresde4 = hijosmenoresde4;
	}

	public int getIdsucursal() {
		return this.idsucursal;
	}

	public void setIdsucursal(int idsucursal) {
		this.idsucursal = idsucursal;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Campania getCampania() {
		return this.campania;
	}

	public void setCampania(Campania campania) {
		this.campania = campania;
	}

}