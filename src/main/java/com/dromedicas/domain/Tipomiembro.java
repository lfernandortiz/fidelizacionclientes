package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipomiembro database table.
 * 
 */
@Entity
@NamedQuery(name="Tipomiembro.findAll", query="SELECT t FROM Tipomiembro t")
public class Tipomiembro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idtipomiembro;

	private String descripcion;

	//bi-directional many-to-one association to Nucleofamilia
	@OneToMany(mappedBy="tipomiembro")
	private List<Nucleofamilia> nucleofamilias;

	public Tipomiembro() {
	}

	public int getIdtipomiembro() {
		return this.idtipomiembro;
	}

	public void setIdtipomiembro(int idtipomiembro) {
		this.idtipomiembro = idtipomiembro;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Nucleofamilia> getNucleofamilias() {
		return this.nucleofamilias;
	}

	public void setNucleofamilias(List<Nucleofamilia> nucleofamilias) {
		this.nucleofamilias = nucleofamilias;
	}

	public Nucleofamilia addNucleofamilia(Nucleofamilia nucleofamilia) {
		getNucleofamilias().add(nucleofamilia);
		nucleofamilia.setTipomiembro(this);

		return nucleofamilia;
	}

	public Nucleofamilia removeNucleofamilia(Nucleofamilia nucleofamilia) {
		getNucleofamilias().remove(nucleofamilia);
		nucleofamilia.setTipomiembro(null);

		return nucleofamilia;
	}

}