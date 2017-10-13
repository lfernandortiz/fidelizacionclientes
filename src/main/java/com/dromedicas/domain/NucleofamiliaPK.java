package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the nucleofamilia database table.
 * 
 */
@Embeddable
public class NucleofamiliaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int idafiliado;

	@Column(insertable=false, updatable=false)
	private int idtipomiembro;

	public NucleofamiliaPK() {
	}
	public int getIdafiliado() {
		return this.idafiliado;
	}
	public void setIdafiliado(int idafiliado) {
		this.idafiliado = idafiliado;
	}
	public int getIdtipomiembro() {
		return this.idtipomiembro;
	}
	public void setIdtipomiembro(int idtipomiembro) {
		this.idtipomiembro = idtipomiembro;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof NucleofamiliaPK)) {
			return false;
		}
		NucleofamiliaPK castOther = (NucleofamiliaPK)other;
		return 
			(this.idafiliado == castOther.idafiliado)
			&& (this.idtipomiembro == castOther.idtipomiembro);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idafiliado;
		hash = hash * prime + this.idtipomiembro;
		
		return hash;
	}
}