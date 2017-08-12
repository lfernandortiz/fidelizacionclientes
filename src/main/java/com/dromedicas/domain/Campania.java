package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the campania database table.
 * 
 */
@Entity
@NamedQueries({@NamedQuery(name="Campania.findAll", query="SELECT c FROM Campania c")})
public class Campania implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idcampania;

	private String contenidoemail;

	private String contenidosms;

	private String criterios;

	@Temporal(TemporalType.DATE)
	private Date fechafin;

	@Temporal(TemporalType.DATE)
	private Date fechainicio;

	private String mercadoobjetivo;

	private String nombrecampania;

	private String urlplantillaemail;

	//bi-directional many-to-one association to Emailcampania
	@OneToMany(mappedBy="campania")
	private Set<Emailcampania> emailcampanias;

	//bi-directional many-to-many association to Emailenvio
	@ManyToMany(mappedBy="campanias1")
	private Set<Emailenvio> emailenvios1;

	//bi-directional many-to-many association to Emailenvio
	@ManyToMany(mappedBy="campanias2")
	private Set<Emailenvio> emailenvios2;

	//bi-directional many-to-many association to Emailenvio
	@ManyToMany(mappedBy="campanias3")
	private Set<Emailenvio> emailenvios3;

	//bi-directional many-to-one association to Smscampania
	@OneToMany(mappedBy="campania")
	private Set<Smscampania> smscampanias;

	//bi-directional many-to-many association to Smsenvio
	@ManyToMany(mappedBy="campanias1")
	private Set<Smsenvio> smsenvios1;

	//bi-directional many-to-many association to Smsenvio
	@ManyToMany(mappedBy="campanias2")
	private Set<Smsenvio> smsenvios2;

	//bi-directional many-to-many association to Smsenvio
	@ManyToMany(mappedBy="campanias3")
	private Set<Smsenvio> smsenvios3;

	public Campania() {
	}

	public int getIdcampania() {
		return this.idcampania;
	}

	public void setIdcampania(int idcampania) {
		this.idcampania = idcampania;
	}

	public String getContenidoemail() {
		return this.contenidoemail;
	}

	public void setContenidoemail(String contenidoemail) {
		this.contenidoemail = contenidoemail;
	}

	public String getContenidosms() {
		return this.contenidosms;
	}

	public void setContenidosms(String contenidosms) {
		this.contenidosms = contenidosms;
	}

	public String getCriterios() {
		return this.criterios;
	}

	public void setCriterios(String criterios) {
		this.criterios = criterios;
	}

	public Date getFechafin() {
		return this.fechafin;
	}

	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}

	public Date getFechainicio() {
		return this.fechainicio;
	}

	public void setFechainicio(Date fechainicio) {
		this.fechainicio = fechainicio;
	}

	public String getMercadoobjetivo() {
		return this.mercadoobjetivo;
	}

	public void setMercadoobjetivo(String mercadoobjetivo) {
		this.mercadoobjetivo = mercadoobjetivo;
	}

	public String getNombrecampania() {
		return this.nombrecampania;
	}

	public void setNombrecampania(String nombrecampania) {
		this.nombrecampania = nombrecampania;
	}

	public String getUrlplantillaemail() {
		return this.urlplantillaemail;
	}

	public void setUrlplantillaemail(String urlplantillaemail) {
		this.urlplantillaemail = urlplantillaemail;
	}

	public Set<Emailcampania> getEmailcampanias() {
		return this.emailcampanias;
	}

	public void setEmailcampanias(Set<Emailcampania> emailcampanias) {
		this.emailcampanias = emailcampanias;
	}

	public Emailcampania addEmailcampania(Emailcampania emailcampania) {
		getEmailcampanias().add(emailcampania);
		emailcampania.setCampania(this);

		return emailcampania;
	}

	public Emailcampania removeEmailcampania(Emailcampania emailcampania) {
		getEmailcampanias().remove(emailcampania);
		emailcampania.setCampania(null);

		return emailcampania;
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

	public Set<Emailenvio> getEmailenvios3() {
		return this.emailenvios3;
	}

	public void setEmailenvios3(Set<Emailenvio> emailenvios3) {
		this.emailenvios3 = emailenvios3;
	}

	public Set<Smscampania> getSmscampanias() {
		return this.smscampanias;
	}

	public void setSmscampanias(Set<Smscampania> smscampanias) {
		this.smscampanias = smscampanias;
	}

	public Smscampania addSmscampania(Smscampania smscampania) {
		getSmscampanias().add(smscampania);
		smscampania.setCampania(this);

		return smscampania;
	}

	public Smscampania removeSmscampania(Smscampania smscampania) {
		getSmscampanias().remove(smscampania);
		smscampania.setCampania(null);

		return smscampania;
	}

	public Set<Smsenvio> getSmsenvios1() {
		return this.smsenvios1;
	}

	public void setSmsenvios1(Set<Smsenvio> smsenvios1) {
		this.smsenvios1 = smsenvios1;
	}

	public Set<Smsenvio> getSmsenvios2() {
		return this.smsenvios2;
	}

	public void setSmsenvios2(Set<Smsenvio> smsenvios2) {
		this.smsenvios2 = smsenvios2;
	}

	public Set<Smsenvio> getSmsenvios3() {
		return this.smsenvios3;
	}

	public void setSmsenvios3(Set<Smsenvio> smsenvios3) {
		this.smsenvios3 = smsenvios3;
	}

}