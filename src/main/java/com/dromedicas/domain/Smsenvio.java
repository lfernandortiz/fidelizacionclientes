package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the smsenvio database table.
 * 
 */
@Entity
@NamedQueries({@NamedQuery(name="Smsenvio.findAll", query="SELECT s FROM Smsenvio s")})
public class Smsenvio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idsmsenvio;
	
	@Column(length=20)
	private String celular;
	

	@Column(length=180)
	private String mensaje;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaenvio;

	//bi-directional many-to-one association to Smscampania
	@OneToMany(mappedBy="smsenvio")
	private Set<Smscampania> smscampanias;

	//bi-directional many-to-one association to Afiliado
	@ManyToOne
	@JoinColumn(name="afiliadoid")
	private Afiliado afiliado;

	//bi-directional many-to-many association to Campania
	@ManyToMany
	@JoinTable(
		name="smscampania"
		, joinColumns={
			@JoinColumn(name="idsmsenvio")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idcampania")
			}
		)
	private Set<Campania> campanias1;

	//bi-directional many-to-many association to Campania
	@ManyToMany
	@JoinTable(
		name="smscampania"
		, joinColumns={
			@JoinColumn(name="idsmsenvio")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idcampania")
			}
		)
	private Set<Campania> campanias2;

	//bi-directional many-to-many association to Campania
	@ManyToMany
	@JoinTable(
		name="smscampania"
		, joinColumns={
			@JoinColumn(name="idsmsenvio")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idcampania")
			}
		)
	private Set<Campania> campanias3;
	

	//bi-directional many-to-one association to Tiposm
	@ManyToOne
	@JoinColumn(name="tiposms")
	private Tiposm tiposm;

	public Smsenvio() {
	}

	public int getIdsmsenvio() {
		return this.idsmsenvio;
	}

	public void setIdsmsenvio(int idsmsenvio) {
		this.idsmsenvio = idsmsenvio;
	}

	public Date getFechaenvio() {
		return this.fechaenvio;
	}

	public void setFechaenvio(Date fechaenvio) {
		this.fechaenvio = fechaenvio;
	}

	public Set<Smscampania> getSmscampanias() {
		return this.smscampanias;
	}

	public void setSmscampanias(Set<Smscampania> smscampanias) {
		this.smscampanias = smscampanias;
	}

	public Smscampania addSmscampania(Smscampania smscampania) {
		getSmscampanias().add(smscampania);
		smscampania.setSmsenvio(this);

		return smscampania;
	}

	public Smscampania removeSmscampania(Smscampania smscampania) {
		getSmscampanias().remove(smscampania);
		smscampania.setSmsenvio(null);

		return smscampania;
	}

	public Afiliado getAfiliado() {
		return this.afiliado;
	}

	public void setAfiliado(Afiliado afiliado) {
		this.afiliado = afiliado;
	}

	public Set<Campania> getCampanias1() {
		return this.campanias1;
	}

	public void setCampanias1(Set<Campania> campanias1) {
		this.campanias1 = campanias1;
	}

	public Set<Campania> getCampanias2() {
		return this.campanias2;
	}

	public void setCampanias2(Set<Campania> campanias2) {
		this.campanias2 = campanias2;
	}

	public Set<Campania> getCampanias3() {
		return this.campanias3;
	}

	public void setCampanias3(Set<Campania> campanias3) {
		this.campanias3 = campanias3;
	}

	public Tiposm getTiposm() {
		return this.tiposm;
	}

	public void setTiposm(Tiposm tiposm) {
		this.tiposm = tiposm;
	}
		
	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getMensaje() {
		return this.mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}