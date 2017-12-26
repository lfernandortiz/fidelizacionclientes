package com.dromedicas.domain;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the smsplantilla database table.
 * 
 */
@ManagedBean(name="smsPlantilla")
@ViewScoped 
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