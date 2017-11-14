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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codephone == null) ? 0 : codephone.hashCode());
		result = prime * result + ((fileflag == null) ? 0 : fileflag.hashCode());
		result = prime * result + idpaises;
		result = prime * result + ((iso2 == null) ? 0 : iso2.hashCode());
		result = prime * result + ((iso3 == null) ? 0 : iso3.hashCode());
		result = prime * result + ((nombees == null) ? 0 : nombees.hashCode());
		result = prime * result + ((nombreen == null) ? 0 : nombreen.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pais other = (Pais) obj;
		if (codephone == null) {
			if (other.codephone != null)
				return false;
		} else if (!codephone.equals(other.codephone))
			return false;
		if (fileflag == null) {
			if (other.fileflag != null)
				return false;
		} else if (!fileflag.equals(other.fileflag))
			return false;
		if (idpaises != other.idpaises)
			return false;
		if (iso2 == null) {
			if (other.iso2 != null)
				return false;
		} else if (!iso2.equals(other.iso2))
			return false;
		if (iso3 == null) {
			if (other.iso3 != null)
				return false;
		} else if (!iso3.equals(other.iso3))
			return false;
		if (nombees == null) {
			if (other.nombees != null)
				return false;
		} else if (!nombees.equals(other.nombees))
			return false;
		if (nombreen == null) {
			if (other.nombreen != null)
				return false;
		} else if (!nombreen.equals(other.nombreen))
			return false;
		return true;
	}
	
	

}