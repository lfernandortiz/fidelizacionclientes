package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Date;


/**
 * The persistent class for the smscampania database table.
 * 
 */
@Entity
@NamedQueries({@NamedQuery(name="Smscampania.findAll", query="SELECT s FROM Smscampania s")})
public class Smscampania implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "idcampania", column = @Column(name = "idcampania", nullable = false)),
			@AttributeOverride(name = "idsmsenvio", column = @Column(name = "idsmsenvio", nullable = false)) })
	@NotNull
	private SmscampaniaPK id;

	@Temporal(TemporalType.DATE)
	private Date fechacampania;

	//bi-directional many-to-one association to Campania
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcampania", nullable = false, insertable = false, updatable = false)
	@NotNull
	private Campania campania;

	//bi-directional many-to-one association to Smsenvio
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idsmsenvio", nullable = false, insertable = false, updatable = false)
	@NotNull
	private Smsenvio smsenvio;

	public Smscampania() {
	}

	public SmscampaniaPK getId() {
		return this.id;
	}

	public void setId(SmscampaniaPK id) {
		this.id = id;
	}

	public Date getFechacampania() {
		return this.fechacampania;
	}

	public void setFechacampania(Date fechacampania) {
		this.fechacampania = fechacampania;
	}

	public Campania getCampania() {
		return this.campania;
	}

	public void setCampania(Campania campania) {
		this.campania = campania;
	}

	public Smsenvio getSmsenvio() {
		return this.smsenvio;
	}

	public void setSmsenvio(Smsenvio smsenvio) {
		this.smsenvio = smsenvio;
	}

}