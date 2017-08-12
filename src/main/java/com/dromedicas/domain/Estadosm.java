package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the estadosms database table.
 * 
 */
@Entity
@Table(name="estadosms")
@NamedQueries({@NamedQuery(name="Estadosm.findAll", query="SELECT e FROM Estadosm e")})
public class Estadosm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idestadosms;

	private String descripcion;

	//bi-directional many-to-one association to Smsenvio
	@OneToMany(mappedBy="estadosm")
	private Set<Smsenvio> smsenvios;

	public Estadosm() {
	}

	public int getIdestadosms() {
		return this.idestadosms;
	}

	public void setIdestadosms(int idestadosms) {
		this.idestadosms = idestadosms;
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
		smsenvio.setEstadosm(this);

		return smsenvio;
	}

	public Smsenvio removeSmsenvio(Smsenvio smsenvio) {
		getSmsenvios().remove(smsenvio);
		smsenvio.setEstadosm(null);

		return smsenvio;
	}

}