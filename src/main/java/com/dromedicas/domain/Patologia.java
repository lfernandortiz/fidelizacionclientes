package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the patologia database table.
 * 
 */
@Entity
@NamedQuery(name="Patologia.findAll", query="SELECT p FROM Patologia p")
public class Patologia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idpatologia;

	private String drescripcion;

	//bi-directional many-to-one association to Afiliadopatologia
	@OneToMany(mappedBy="patologia")
	private List<Afiliadopatologia> afiliadopatologias;

	//bi-directional many-to-many association to Afiliado
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(
		name="afiliadopatologianucleo"
		, joinColumns={
			@JoinColumn(name="idpatologia")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idafiliado")
			}
		)
	private List<Afiliado> afiliados;

	public Patologia() {
	}

	public int getIdpatologia() {
		return this.idpatologia;
	}

	public void setIdpatologia(int idpatologia) {
		this.idpatologia = idpatologia;
	}

	public String getDrescripcion() {
		return this.drescripcion;
	}

	public void setDrescripcion(String drescripcion) {
		this.drescripcion = drescripcion;
	}

	public List<Afiliadopatologia> getAfiliadopatologias() {
		return this.afiliadopatologias;
	}

	public void setAfiliadopatologias(List<Afiliadopatologia> afiliadopatologias) {
		this.afiliadopatologias = afiliadopatologias;
	}

	public Afiliadopatologia addAfiliadopatologia(Afiliadopatologia afiliadopatologia) {
		getAfiliadopatologias().add(afiliadopatologia);
		afiliadopatologia.setPatologia(this);

		return afiliadopatologia;
	}

	public Afiliadopatologia removeAfiliadopatologia(Afiliadopatologia afiliadopatologia) {
		getAfiliadopatologias().remove(afiliadopatologia);
		afiliadopatologia.setPatologia(null);

		return afiliadopatologia;
	}

	public List<Afiliado> getAfiliados() {
		return this.afiliados;
	}

	public void setAfiliados(List<Afiliado> afiliados) {
		this.afiliados = afiliados;
	}

}