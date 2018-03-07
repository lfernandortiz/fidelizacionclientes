package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the patologiacampania database table.
 * 
 */
@Embeddable
public class PatologiacampaniaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false, unique=true, nullable=false)
	private int idcampania;

	@Column(insertable=false, updatable=false, unique=true, nullable=false)
	private int idpatologia;

	public PatologiacampaniaPK() {
	}
	public int getIdcampania() {
		return this.idcampania;
	}
	public void setIdcampania(int idcampania) {
		this.idcampania = idcampania;
	}
	public int getIdpatologia() {
		return this.idpatologia;
	}
	public void setIdpatologia(int idpatologia) {
		this.idpatologia = idpatologia;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PatologiacampaniaPK)) {
			return false;
		}
		PatologiacampaniaPK castOther = (PatologiacampaniaPK)other;
		return 
			(this.idcampania == castOther.idcampania)
			&& (this.idpatologia == castOther.idpatologia);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idcampania;
		hash = hash * prime + this.idpatologia;
		
		return hash;
	}
}