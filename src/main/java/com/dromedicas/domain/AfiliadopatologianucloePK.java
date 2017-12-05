package com.dromedicas.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the afiliadopatologia database table.
 * 
 */
@Embeddable
public class AfiliadopatologianucloePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	@Column(insertable=false, updatable=false)
	private int idafiliado;

	@Column(insertable=false, updatable=false)
	private int idpatologia;

	public AfiliadopatologianucloePK() {
	}
	public int getIdafiliado() {
		return this.idafiliado;
	}
	public void setIdafiliado(int idafiliado) {
		this.idafiliado = idafiliado;
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
		if (!(other instanceof AfiliadopatologianucloePK)) {
			return false;
		}
		AfiliadopatologianucloePK castOther = (AfiliadopatologianucloePK)other;
		return 
			(this.idafiliado == castOther.idafiliado)
			&& (this.idpatologia == castOther.idpatologia);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idafiliado;
		hash = hash * prime + this.idpatologia;
		
		return hash;
	}

}
