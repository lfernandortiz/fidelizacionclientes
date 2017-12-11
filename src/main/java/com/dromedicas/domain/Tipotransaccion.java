package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;


/**
 * The persistent class for the tipotransaccion database table.
 * 
 */
@Entity
@NamedQueries({@NamedQuery(name="Tipotransaccion.findAll", query="SELECT t FROM Tipotransaccion t")})
public class Tipotransaccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore
	private int idtipotransaccion;

	private String descripcion;

	//bi-directional many-to-one association to Transaccion
	@OneToMany(mappedBy="tipotransaccion")
	@JsonIgnore
	private Set<Transaccion> transaccions;

	public Tipotransaccion() {
	}

	public int getIdtipotransaccion() {
		return this.idtipotransaccion;
	}

	public void setIdtipotransaccion(int idtipotransaccion) {
		this.idtipotransaccion = idtipotransaccion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<Transaccion> getTransaccions() {
		return this.transaccions;
	}

	public void setTransaccions(Set<Transaccion> transaccions) {
		this.transaccions = transaccions;
	}

	public Transaccion addTransaccion(Transaccion transaccion) {
		getTransaccions().add(transaccion);
		transaccion.setTipotransaccion(this);

		return transaccion;
	}

	public Transaccion removeTransaccion(Transaccion transaccion) {
		getTransaccions().remove(transaccion);
		transaccion.setTipotransaccion(null);

		return transaccion;
	}

}