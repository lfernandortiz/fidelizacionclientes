package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ocupacion database table.
 * 
 */
@Entity
@NamedQuery(name="Ocupacion.findAll", query="SELECT o FROM Ocupacion o")
public class Ocupacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idocupacion;

	private String descripcion;

	//bi-directional many-to-one association to Afiliado
	@OneToMany(mappedBy="ocupacionBean", cascade={CascadeType.ALL})
	private List<Afiliado> afiliados;

	public Ocupacion() {
	}

	public int getIdocupacion() {
		return this.idocupacion;
	}

	public void setIdocupacion(int idocupacion) {
		this.idocupacion = idocupacion;
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
		afiliado.setOcupacionBean(this);

		return afiliado;
	}

	public Afiliado removeAfiliado(Afiliado afiliado) {
		getAfiliados().remove(afiliado);
		afiliado.setOcupacionBean(null);

		return afiliado;
	}

}