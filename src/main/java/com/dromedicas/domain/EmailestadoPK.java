package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the emailestado database table.
 * 
 */
@Embeddable
public class EmailestadoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int idemailenvio;

	@Column(insertable=false, updatable=false)
	private int idtipoestadoemail;

	public EmailestadoPK() {
	}
	public int getIdemailenvio() {
		return this.idemailenvio;
	}
	public void setIdemailenvio(int idemailenvio) {
		this.idemailenvio = idemailenvio;
	}
	public int getIdtipoestadoemail() {
		return this.idtipoestadoemail;
	}
	public void setIdtipoestadoemail(int idtipoestadoemail) {
		this.idtipoestadoemail = idtipoestadoemail;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EmailestadoPK)) {
			return false;
		}
		EmailestadoPK castOther = (EmailestadoPK)other;
		return 
			(this.idemailenvio == castOther.idemailenvio)
			&& (this.idtipoestadoemail == castOther.idtipoestadoemail);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idemailenvio;
		hash = hash * prime + this.idtipoestadoemail;
		
		return hash;
	}
}