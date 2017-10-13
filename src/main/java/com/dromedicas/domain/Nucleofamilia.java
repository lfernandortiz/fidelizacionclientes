package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * The persistent class for the nucleofamilia database table.
 * 
 */
@Entity
@NamedQuery(name="Nucleofamilia.findAll", query="SELECT n FROM Nucleofamilia n")
public class Nucleofamilia implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "idafiliado", column = @Column(name = "idafiliado", nullable = false)),
			@AttributeOverride(name = "idtipomiembro", column = @Column(name = "idtipomiembro", nullable = false)) })
	@NotNull
	private NucleofamiliaPK id;

	private int rangofin;

	private int rangoinicio;

	//bi-directional many-to-one association to Afiliado
	@ManyToOne
	@JoinColumn(name="idafiliado", nullable = false, insertable = false, updatable = false)
	private Afiliado afiliado;

	//bi-directional many-to-one association to Tipomiembro
	@ManyToOne
	@JoinColumn(name="idtipomiembro", nullable = false, insertable = false, updatable = false)
	private Tipomiembro tipomiembro;

	public Nucleofamilia() {
	}

	public NucleofamiliaPK getId() {
		return this.id;
	}

	public void setId(NucleofamiliaPK id) {
		this.id = id;
	}

	public int getRangofin() {
		return this.rangofin;
	}

	public void setRangofin(int rangofin) {
		this.rangofin = rangofin;
	}

	public int getRangoinicio() {
		return this.rangoinicio;
	}

	public void setRangoinicio(int rangoinicio) {
		this.rangoinicio = rangoinicio;
	}

	public Afiliado getAfiliado() {
		return this.afiliado;
	}

	public void setAfiliado(Afiliado afiliado) {
		this.afiliado = afiliado;
	}

	public Tipomiembro getTipomiembro() {
		return this.tipomiembro;
	}

	public void setTipomiembro(Tipomiembro tipomiembro) {
		this.tipomiembro = tipomiembro;
	}

}