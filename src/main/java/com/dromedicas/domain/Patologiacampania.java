package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;


/**
 * The persistent class for the patologiacampania database table.
 * 
 */
@Entity
@Table(name="patologiacampania")
@NamedQuery(name="Patologiacampania.findAll", query="SELECT p FROM Patologiacampania p")
public class Patologiacampania implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "idcampania", column = @Column(name = "idcampania", nullable = false)),
			@AttributeOverride(name = "idpatologia", column = @Column(name = "idpatologia", nullable = false)) })
	@NotNull
	private PatologiacampaniaPK id;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	
	// bi-directional many-to-one association to Afiliado
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="idcampania", nullable=false, insertable=false, updatable=false)
	@NotNull
	@JsonIgnore
	private Campania campania;
	
	// bi-directional many-to-one association to Afiliado
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="idpatologia", nullable=false, insertable=false, updatable=false)
	@NotNull
	@JsonIgnore
	private Patologia patologia;

	public Patologiacampania() {
	}

	public PatologiacampaniaPK getId() {
		return this.id;
	}

	public void setId(PatologiacampaniaPK id) {
		this.id = id;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Campania getCampania() {
		return this.campania;
	}

	public void setCampania(Campania campania) {
		this.campania = campania;
	}

	public Patologia getPatologia() {
		return this.patologia;
	}

	public void setPatologia(Patologia patologia) {
		this.patologia = patologia;
	}

}