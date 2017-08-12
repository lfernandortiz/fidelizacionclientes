package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the permisorol database table.
 * 
 */
@Embeddable
public class PermisorolPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int idrol;

	@Column(insertable=false, updatable=false)
	private int idusuario;

	public PermisorolPK() {
	}
	public int getIdrol() {
		return this.idrol;
	}
	public void setIdrol(int idrol) {
		this.idrol = idrol;
	}
	public int getIdusuario() {
		return this.idusuario;
	}
	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PermisorolPK)) {
			return false;
		}
		PermisorolPK castOther = (PermisorolPK)other;
		return 
			(this.idrol == castOther.idrol)
			&& (this.idusuario == castOther.idusuario);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idrol;
		hash = hash * prime + this.idusuario;
		
		return hash;
	}
}