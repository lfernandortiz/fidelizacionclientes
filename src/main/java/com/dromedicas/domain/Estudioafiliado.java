package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the estudioafiliado database table.
 * 
 */
@Entity
@NamedQuery(name="Estudioafiliado.findAll", query="SELECT e FROM Estudioafiliado e")
public class Estudioafiliado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idestudioafiliado;

	private String descripcion;

	//bi-directional many-to-one association to Afiliado
	@OneToMany(mappedBy="estudioafiliado")
	private List<Afiliado> afiliados;

	public Estudioafiliado() {
	}

	public int getIdestudioafiliado() {
		return this.idestudioafiliado;
	}

	public void setIdestudioafiliado(int idestudioafiliado) {
		this.idestudioafiliado = idestudioafiliado;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Afiliado> getAfiliados() {
		return this.afiliados;
	}

	public void setAfiliados(List<Afiliado> afiliados) {
		this.afiliados = afiliados;
	}

	public Afiliado addAfiliado(Afiliado afiliado) {
		getAfiliados().add(afiliado);
		afiliado.setEstudioafiliado(this);

		return afiliado;
	}

	public Afiliado removeAfiliado(Afiliado afiliado) {
		getAfiliados().remove(afiliado);
		afiliado.setEstudioafiliado(null);

		return afiliado;
	}

}