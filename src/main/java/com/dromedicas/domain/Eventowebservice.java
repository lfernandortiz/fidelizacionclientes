package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the eventowebservice database table.
 * 
 */
@Entity
@NamedQueries({@NamedQuery(name="Eventowebservice.findAll", query="SELECT e FROM Eventowebservice e")})
public class Eventowebservice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int ideventowebservice;

	private String descripcion;

	private int prioridad;

	//bi-directional many-to-one association to Audiwebservice
	@OneToMany(mappedBy="eventowebservice")
	private Set<Audiwebservice> audiwebservices;

	public Eventowebservice() {
	}

	public int getIdeventowebservice() {
		return this.ideventowebservice;
	}

	public void setIdeventowebservice(int ideventowebservice) {
		this.ideventowebservice = ideventowebservice;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getPrioridad() {
		return this.prioridad;
	}

	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}

	public Set<Audiwebservice> getAudiwebservices() {
		return this.audiwebservices;
	}

	public void setAudiwebservices(Set<Audiwebservice> audiwebservices) {
		this.audiwebservices = audiwebservices;
	}

	public Audiwebservice addAudiwebservice(Audiwebservice audiwebservice) {
		getAudiwebservices().add(audiwebservice);
		audiwebservice.setEventowebservice(this);

		return audiwebservice;
	}

	public Audiwebservice removeAudiwebservice(Audiwebservice audiwebservice) {
		getAudiwebservices().remove(audiwebservice);
		audiwebservice.setEventowebservice(null);

		return audiwebservice;
	}

}