package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Date;


/**
 * The persistent class for the emailcampania database table.
 * 
 */
@Entity
@NamedQueries({@NamedQuery(name="Emailcampania.findAll", query="SELECT e FROM Emailcampania e")})
public class Emailcampania implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "idcampania", column = @Column(name = "idcampania", nullable = false)),
			@AttributeOverride(name = "idemail", column = @Column(name = "idemail", nullable = false)) })
	@NotNull
	private EmailcampaniaPK id;

	@Temporal(TemporalType.DATE)
	private Date fechacampania;

	//bi-directional many-to-one association to Campania
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcampania", nullable = false, insertable = false, updatable = false)
	@NotNull
	private Campania campania;

	//bi-directional many-to-one association to Emailenvio
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idemail", nullable = false, insertable = false, updatable = false)
	@NotNull
	private Emailenvio emailenvio;

	public Emailcampania() {
	}

	public EmailcampaniaPK getId() {
		return this.id;
	}

	public void setId(EmailcampaniaPK id) {
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

	public Emailenvio getEmailenvio() {
		return this.emailenvio;
	}

	public void setEmailenvio(Emailenvio emailenvio) {
		this.emailenvio = emailenvio;
	}

}