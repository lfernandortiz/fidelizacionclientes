package com.dromedicas.domain;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
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
	
	private String criterios;

	@Temporal(TemporalType.DATE)
	private Date fechafin;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechainicio;

	@Column(length=150)
	private String mercadoobjetivo;

	@Column(nullable=false, length=55)
	private String nombrecampania;

	@Column(length=50)
	private String urlplantillaemail;
	
	private byte estadocampania;
	
	private String tipocampania;

	// bi-directional many-to-one association to Emailcampania
	@OneToMany(mappedBy = "campania")
	private List<Emailcampania> emailcampanias;

	// bi-directional many-to-one association to Paremetroscampania
	@OneToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE},mappedBy = "campania")
	private List<Paremetroscampania> paremetroscampanias;

	// bi-directional many-to-one association to Patologiacampania
	@OneToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="campania", fetch = FetchType.EAGER)
	private List<Patologiacampania> patologiacampanias;

	// bi-directional many-to-one association to Smscampania
	@OneToMany( mappedBy = "campania")
	private List<Smscampania> smscampanias;

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

	public byte getEstadocampania() {
		return estadocampania;
	}

	public void setEstadocampania(byte estadocampania) {
		this.estadocampania = estadocampania;
	}

	public String getTipocampania() {
		return tipocampania;
	}

	public void setTipocampania(String tipocampania) {
		this.tipocampania = tipocampania;
	}

	public List<Emailcampania> getEmailcampanias() {
		return emailcampanias;
	}

	public void setEmailcampanias(List<Emailcampania> emailcampanias) {
		this.emailcampanias = emailcampanias;
	}

	public List<Paremetroscampania> getParemetroscampanias() {
		return paremetroscampanias;
	}

	public void setParemetroscampanias(List<Paremetroscampania> paremetroscampanias) {
		this.paremetroscampanias = paremetroscampanias;
	}

	public List<Patologiacampania> getPatologiacampanias() {
		return patologiacampanias;
	}

	public void setPatologiacampanias(List<Patologiacampania> patologiacampanias) {
		this.patologiacampanias = patologiacampanias;
	}

	public List<Smscampania> getSmscampanias() {
		return smscampanias;
	}

	public void setSmscampanias(List<Smscampania> smscampanias) {
		this.smscampanias = smscampanias;
	}
	
	
	

}