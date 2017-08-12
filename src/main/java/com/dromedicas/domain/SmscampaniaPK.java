package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the smscampania database table.
 * 
 */
@Embeddable
public class SmscampaniaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int idsmsenvio;

	@Column(insertable=false, updatable=false)
	private int idcampania;

	public SmscampaniaPK() {
	}
	public int getIdsmsenvio() {
		return this.idsmsenvio;
	}
	public void setIdsmsenvio(int idsmsenvio) {
		this.idsmsenvio = idsmsenvio;
	}
	public int getIdcampania() {
		return this.idcampania;
	}
	public void setIdcampania(int idcampania) {
		this.idcampania = idcampania;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SmscampaniaPK)) {
			return false;
		}
		SmscampaniaPK castOther = (SmscampaniaPK)other;
		return 
			(this.idsmsenvio == castOther.idsmsenvio)
			&& (this.idcampania == castOther.idcampania);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idsmsenvio;
		hash = hash * prime + this.idcampania;
		
		return hash;
	}
}