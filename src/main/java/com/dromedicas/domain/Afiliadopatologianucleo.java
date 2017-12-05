package com.dromedicas.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the afiliadopatologia database table.
 * 
 */
@Entity
@NamedQuery(name = "Afiliadopatologianucleo.findAll", query = "SELECT a FROM Afiliadopatologianucleo a")
@XmlRootElement
public class Afiliadopatologianucleo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "idpatologia", column = @Column(name = "idpatologia", nullable = false)),
			@AttributeOverride(name = "idafiliado", column = @Column(name = "idafiliado", nullable = false)) })
	@NotNull
	private AfiliadopatologianucloePK id;

	// bi-directional many-to-one association to Patologia
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idpatologia", nullable = false, insertable = false, updatable = false)
	@NotNull
	@JsonIgnore
	private Patologia patologia;

	// bi-directional many-to-one association to Afiliado
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idafiliado", nullable = false, insertable = false, updatable = false)
	@NotNull
	@JsonIgnore
	private Afiliado afiliado;
	
	
	public Afiliadopatologianucleo() {
	}
	
	public AfiliadopatologianucloePK getId() {
		return this.id;
	}

	public void setId(AfiliadopatologianucloePK id) {
		this.id = id;
	}

	public Patologia getPatologia() {
		return this.patologia;
	}

	public void setPatologia(Patologia patologia) {
		this.patologia = patologia;
	}

	public Afiliado getAfiliado() {
		return this.afiliado;
	}

	public void setAfiliado(Afiliado afiliado) {
		this.afiliado = afiliado;
	}

}
