package com.dromedicas.domain;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the campania database table.
 * 
 */
@ManagedBean(name="campania")
@RequestScoped
@Entity
@Table(name="campania")
@NamedQuery(name="Campania.findAll", query="SELECT c FROM Campania c")
public class Campania implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int idcampania;

	@Column(length=300)
	private String contenidoemail;

	@Column(length=150)
	private String contenidosms;

	@Column(length=60)
	private String criterios;

	@Temporal(TemporalType.DATE)
	private Date fechafin;

	@Temporal(TemporalType.DATE)
	private Date fechainicio;

	@Column(length=150)
	private String mercadoobjetivo;

	@Column(nullable=false, length=55)
	private String nombrecampania;

	@Column(length=50)
	private String urlplantillaemail;

	//bi-directional many-to-one association to Emailcampania
	@OneToMany(mappedBy="campania")
	private Set<Emailcampania> emailcampanias;

	//bi-directional many-to-one association to Smscampania
	@OneToMany(mappedBy="campania")
	private Set<Smscampania> smscampanias;

	//bi-directional many-to-one association to Paremetroscampania
	@OneToMany(mappedBy="campania")
	private Set<Paremetroscampania> paremetroscampanias;

	//bi-directional many-to-one association to Patologiacampania
	@OneToMany(mappedBy="campania")
	private Set<Patologiacampania> patologiacampanias;

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

	public Set<Paremetroscampania> getParemetroscampanias() {
		return this.paremetroscampanias;
	}

	public void setParemetroscampanias(Set<Paremetroscampania> paremetroscampanias) {
		this.paremetroscampanias = paremetroscampanias;
	}

	public Paremetroscampania addParemetroscampania(Paremetroscampania paremetroscampania) {
		getParemetroscampanias().add(paremetroscampania);
		paremetroscampania.setCampania(this);

		return paremetroscampania;
	}

	public Paremetroscampania removeParemetroscampania(Paremetroscampania paremetroscampania) {
		getParemetroscampanias().remove(paremetroscampania);
		paremetroscampania.setCampania(null);

		return paremetroscampania;
	}

	public Set<Patologiacampania> getPatologiacampanias() {
		return this.patologiacampanias;
	}

	public void setPatologiacampanias(Set<Patologiacampania> patologiacampanias) {
		this.patologiacampanias = patologiacampanias;
	}

	public Patologiacampania addPatologiacampania(Patologiacampania patologiacampania) {
		getPatologiacampanias().add(patologiacampania);
		patologiacampania.setCampania(this);

		return patologiacampania;
	}

	public Patologiacampania removePatologiacampania(Patologiacampania patologiacampania) {
		getPatologiacampanias().remove(patologiacampania);
		patologiacampania.setCampania(null);

		return patologiacampania;
	}

}