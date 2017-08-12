package com.dromedicas.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;


/**
 * The persistent class for the emailestado database table.
 * 
 */
@Entity
@NamedQueries({@NamedQuery(name="Emailestado.findAll", query="SELECT e FROM Emailestado e")})
public class Emailestado implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "idemailenvio", column = @Column(name = "idemailenvio", nullable = false)),
			@AttributeOverride(name = "idtipoestadoemail", column = @Column(name = "idtipoestadoemail", nullable = false)) })
	@NotNull
	private EmailestadoPK id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaestado;

	//bi-directional many-to-one association to Emailenvio
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idemailenvio", nullable = false, insertable = false, updatable = false)
	@NotNull
	private Emailenvio emailenvio;

	//bi-directional many-to-one association to Tipoestaestadoemail
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idtipoestadoemail", nullable = false, insertable = false, updatable = false)
	@NotNull
	private Tipoestaestadoemail tipoestaestadoemail;

	public Emailestado() {
	}

	public EmailestadoPK getId() {
		return this.id;
	}

	public void setId(EmailestadoPK id) {
		this.id = id;
	}

	public Date getFechaestado() {
		return this.fechaestado;
	}

	public void setFechaestado(Date fechaestado) {
		this.fechaestado = fechaestado;
	}

	public Emailenvio getEmailenvio() {
		return this.emailenvio;
	}

	public void setEmailenvio(Emailenvio emailenvio) {
		this.emailenvio = emailenvio;
	}

	public Tipoestaestadoemail getTipoestaestadoemail() {
		return this.tipoestaestadoemail;
	}

	public void setTipoestaestadoemail(Tipoestaestadoemail tipoestaestadoemail) {
		this.tipoestaestadoemail = tipoestaestadoemail;
	}

}