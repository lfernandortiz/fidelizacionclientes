package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the producto database table.
 * 
 */
@Entity
@NamedQueries({@NamedQuery(name="Producto.findAll", query="SELECT p FROM Producto p")})
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idproducto;

	private String codproducto;

	private String descripcion;

	//bi-directional many-to-one association to Detransaccion
	@OneToMany(mappedBy="producto")
	private Set<Detransaccion> detransaccions;

	public Producto() {
	}

	public int getIdproducto() {
		return this.idproducto;
	}

	public void setIdproducto(int idproducto) {
		this.idproducto = idproducto;
	}

	public String getCodproducto() {
		return this.codproducto;
	}

	public void setCodproducto(String codproducto) {
		this.codproducto = codproducto;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<Detransaccion> getDetransaccions() {
		return this.detransaccions;
	}

	public void setDetransaccions(Set<Detransaccion> detransaccions) {
		this.detransaccions = detransaccions;
	}

	public Detransaccion addDetransaccion(Detransaccion detransaccion) {
		getDetransaccions().add(detransaccion);
		detransaccion.setProducto(this);

		return detransaccion;
	}

	public Detransaccion removeDetransaccion(Detransaccion detransaccion) {
		getDetransaccions().remove(detransaccion);
		detransaccion.setProducto(null);

		return detransaccion;
	}

}