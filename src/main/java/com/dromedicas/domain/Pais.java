package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the paises database table.
 * 
 */
@Entity
@Table(name="paises")
@NamedQuery(name="Pais.findAll", query="SELECT p FROM Pais p")
public class Pais implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idpaises;

	private String codephone;

	private String fileflag;

	private String iso2;

	private String iso3;

	private String nombees;

	private String nombreen;

	public Pais() {
	}

	public int getIdpaises() {
		return this.idpaises;
	}

	public void setIdpaises(int idpaises) {
		this.idpaises = idpaises;
	}

	public String getCodephone() {
		return this.codephone;
	}

	public void setCodephone(String codephone) {
		this.codephone = codephone;
	}

	public String getFileflag() {
		return this.fileflag;
	}

	public void setFileflag(String fileflag) {
		this.fileflag = fileflag;
	}

	public String getIso2() {
		return this.iso2;
	}

	public void setIso2(String iso2) {
		this.iso2 = iso2;
	}

	public String getIso3() {
		return this.iso3;
	}

	public void setIso3(String iso3) {
		this.iso3 = iso3;
	}

	public String getNombees() {
		return this.nombees;
	}

	public void setNombees(String nombees) {
		this.nombees = nombees;
	}

	public String getNombreen() {
		return this.nombreen;
	}

	public void setNombreen(String nombreen) {
		this.nombreen = nombreen;
	}

}