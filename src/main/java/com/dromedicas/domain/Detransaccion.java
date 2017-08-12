package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the detransaccion database table.
 * 
 */
@Entity
@NamedQueries({@NamedQuery(name="Detransaccion.findAll", query="SELECT d FROM Detransaccion d")})
public class Detransaccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int iddetransaccion;

	private int cantidad;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="idproducto")
	private Producto producto;

	//bi-directional many-to-one association to Transaccion
	@ManyToOne
	@JoinColumn(name="idtransaccion")
	private Transaccion transaccion;

	public Detransaccion() {
	}

	public int getIddetransaccion() {
		return this.iddetransaccion;
	}

	public void setIddetransaccion(int iddetransaccion) {
		this.iddetransaccion = iddetransaccion;
	}

	public int getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Transaccion getTransaccion() {
		return this.transaccion;
	}

	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}

}