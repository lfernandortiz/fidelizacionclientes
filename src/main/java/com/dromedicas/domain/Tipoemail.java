package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the tipoemail database table.
 * 
 */
@Entity
@NamedQueries({@NamedQuery(name="Tipoemail.findAll", query="SELECT t FROM Tipoemail t")})
public class Tipoemail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idtipoemail;

	private String descripcion;

	//bi-directional many-to-one association to Emailenvio
	@OneToMany(mappedBy="tipoemailBean")
	private Set<Emailenvio> emailenvios;

	public Tipoemail() {
	}

	public int getIdtipoemail() {
		return this.idtipoemail;
	}

	public void setIdtipoemail(int idtipoemail) {
		this.idtipoemail = idtipoemail;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<Emailenvio> getEmailenvios() {
		return this.emailenvios;
	}

	public void setEmailenvios(Set<Emailenvio> emailenvios) {
		this.emailenvios = emailenvios;
	}

	public Emailenvio addEmailenvio(Emailenvio emailenvio) {
		getEmailenvios().add(emailenvio);
		emailenvio.setTipoemailBean(this);

		return emailenvio;
	}

	public Emailenvio removeEmailenvio(Emailenvio emailenvio) {
		getEmailenvios().remove(emailenvio);
		emailenvio.setTipoemailBean(null);

		return emailenvio;
	}

}