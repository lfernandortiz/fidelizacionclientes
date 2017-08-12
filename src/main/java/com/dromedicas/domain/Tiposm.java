package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the tiposms database table.
 * 
 */
@Entity
@Table(name="tiposms")
@NamedQueries({@NamedQuery(name="Tiposm.findAll", query="SELECT t FROM Tiposm t")})
public class Tiposm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idtiposms;

	private String descripcion;

	//bi-directional many-to-one association to Smsenvio
	@OneToMany(mappedBy="tiposm")
	private Set<Smsenvio> smsenvios;

	public Tiposm() {
	}

	public int getIdtiposms() {
		return this.idtiposms;
	}

	public void setIdtiposms(int idtiposms) {
		this.idtiposms = idtiposms;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<Smsenvio> getSmsenvios() {
		return this.smsenvios;
	}

	public void setSmsenvios(Set<Smsenvio> smsenvios) {
		this.smsenvios = smsenvios;
	}

	public Smsenvio addSmsenvio(Smsenvio smsenvio) {
		getSmsenvios().add(smsenvio);
		smsenvio.setTiposm(this);

		return smsenvio;
	}

	public Smsenvio removeSmsenvio(Smsenvio smsenvio) {
		getSmsenvios().remove(smsenvio);
		smsenvio.setTiposm(null);

		return smsenvio;
	}

}