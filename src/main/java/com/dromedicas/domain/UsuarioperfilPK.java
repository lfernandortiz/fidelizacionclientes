package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the usuarioperfil database table.
 * 
 */
@Embeddable
public class UsuarioperfilPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int idusuario;

	@Column(insertable=false, updatable=false)
	private int idperfil;

	public UsuarioperfilPK() {
	}
	public int getIdusuario() {
		return this.idusuario;
	}
	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}
	public int getIdperfil() {
		return this.idperfil;
	}
	public void setIdperfil(int idperfil) {
		this.idperfil = idperfil;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UsuarioperfilPK)) {
			return false;
		}
		UsuarioperfilPK castOther = (UsuarioperfilPK)other;
		return 
			(this.idusuario == castOther.idusuario)
			&& (this.idperfil == castOther.idperfil);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idusuario;
		hash = hash * prime + this.idperfil;
		
		return hash;
	}
}