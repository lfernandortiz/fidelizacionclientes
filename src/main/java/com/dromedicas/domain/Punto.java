package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the puntos database table.
 * 
 */
@Entity
@Table(name="puntos")
@NamedQueries({@NamedQuery(name="Punto.findAll", query="SELECT p FROM Punto p")})
public class Punto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idpuntos;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	private int valor;

	//bi-directional many-to-one association to Transaccion
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="idtransaccion")
	private Transaccion transaccion;

	public Punto() {
	}

	public int getIdpuntos() {
		return this.idpuntos;
	}

	public void setIdpuntos(int idpuntos) {
		this.idpuntos = idpuntos;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getValor() {
		return this.valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public Transaccion getTransaccion() {
		return this.transaccion;
	}

	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}

}