package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


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
	private Set<Afiliadopatologia> afiliadopatologias;

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

	public Set<Afiliadopatologia> getAfiliadopatologias() {
		return this.afiliadopatologias;
	}

	public void setAfiliadopatologias(Set<Afiliadopatologia> afiliadopatologias) {
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

}