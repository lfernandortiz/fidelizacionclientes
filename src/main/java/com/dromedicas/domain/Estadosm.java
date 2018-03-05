package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the estadosms database table.
 * 
 */
@Entity
@Table(name="estadosms")
@NamedQueries({@NamedQuery(name="Estadosm.findAll", query="SELECT e FROM Estadosm e")})
public class Estadosm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idestadosms;

	private String descripcion;
	

	public Estadosm() {
	}

	public int getIdestadosms() {
		return this.idestadosms;
	}

	public void setIdestadosms(int idestadosms) {
		this.idestadosms = idestadosms;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	

}