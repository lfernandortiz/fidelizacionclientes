package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the smsplantilla database table.
 * 
 */
@Entity
@NamedQueries({@NamedQuery(name="Smsplantilla.findAll", query="SELECT s FROM Smsplantilla s")})
public class Smsplantilla implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idsmsplantilla;

	private String descripcion;

	private String smscontenido;

	public Smsplantilla() {
	}

	public int getIdsmsplantilla() {
		return this.idsmsplantilla;
	}

	public void setIdsmsplantilla(int idsmsplantilla) {
		this.idsmsplantilla = idsmsplantilla;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getSmscontenido() {
		return this.smscontenido;
	}

	public void setSmscontenido(String smscontenido) {
		this.smscontenido = smscontenido;
	}

}