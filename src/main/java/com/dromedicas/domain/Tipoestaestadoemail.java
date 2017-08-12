package com.dromedicas.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


/**
 * The persistent class for the tipoestaestadoemail database table.
 * 
 */
@Entity
@NamedQueries({@NamedQuery(name="Tipoestaestadoemail.findAll", query="SELECT t FROM Tipoestaestadoemail t")})
public class Tipoestaestadoemail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idtipoestaestadoemail;

	private String codigostandarid;

	private String descripcion;

	//bi-directional many-to-many association to Emailenvio
	@ManyToMany(mappedBy="tipoestaestadoemails1")
	private Set<Emailenvio> emailenvios1;

	//bi-directional many-to-many association to Emailenvio
	@ManyToMany(mappedBy="tipoestaestadoemails2")
	private Set<Emailenvio> emailenvios2;

	//bi-directional many-to-one association to Emailestado
	@OneToMany(mappedBy="tipoestaestadoemail")
	private Set<Emailestado> emailestados;

	public Tipoestaestadoemail() {
	}

	public int getIdtipoestaestadoemail() {
		return this.idtipoestaestadoemail;
	}

	public void setIdtipoestaestadoemail(int idtipoestaestadoemail) {
		this.idtipoestaestadoemail = idtipoestaestadoemail;
	}

	public String getCodigostandarid() {
		return this.codigostandarid;
	}

	public void setCodigostandarid(String codigostandarid) {
		this.codigostandarid = codigostandarid;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<Emailenvio> getEmailenvios1() {
		return this.emailenvios1;
	}

	public void setEmailenvios1(Set<Emailenvio> emailenvios1) {
		this.emailenvios1 = emailenvios1;
	}

	public Set<Emailenvio> getEmailenvios2() {
		return this.emailenvios2;
	}

	public void setEmailenvios2(Set<Emailenvio> emailenvios2) {
		this.emailenvios2 = emailenvios2;
	}

	public Set<Emailestado> getEmailestados() {
		return this.emailestados;
	}

	public void setEmailestados(Set<Emailestado> emailestados) {
		this.emailestados = emailestados;
	}

	public Emailestado addEmailestado(Emailestado emailestado) {
		getEmailestados().add(emailestado);
		emailestado.setTipoestaestadoemail(this);

		return emailestado;
	}

	public Emailestado removeEmailestado(Emailestado emailestado) {
		getEmailestados().remove(emailestado);
		emailestado.setTipoestaestadoemail(null);

		return emailestado;
	}

}