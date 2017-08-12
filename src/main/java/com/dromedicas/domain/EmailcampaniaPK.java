package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the emailcampania database table.
 * 
 */
@Embeddable
public class EmailcampaniaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int idemail;

	@Column(insertable=false, updatable=false)
	private int idcampania;

	public EmailcampaniaPK() {
	}
	public int getIdemail() {
		return this.idemail;
	}
	public void setIdemail(int idemail) {
		this.idemail = idemail;
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
		if (!(other instanceof EmailcampaniaPK)) {
			return false;
		}
		EmailcampaniaPK castOther = (EmailcampaniaPK)other;
		return 
			(this.idemail == castOther.idemail)
			&& (this.idcampania == castOther.idcampania);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idemail;
		hash = hash * prime + this.idcampania;
		
		return hash;
	}
}