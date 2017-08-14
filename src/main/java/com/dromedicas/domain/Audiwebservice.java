package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;


/**
 * The persistent class for the audiwebservice database table.
 * 
 */
@Entity
@NamedQueries({@NamedQuery(name="Audiwebservice.findAll", query="SELECT a FROM Audiwebservice a")})
@XmlRootElement
public class Audiwebservice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idaudiwebservice;

	private String apikey;

	@Lob
	private String contenido;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecharequest;

	//bi-directional many-to-one association to Eventowebservice
	@ManyToOne
	@JoinColumn(name="idevento")
	private Eventowebservice eventowebservice;

	//bi-directional many-to-one association to Sucursal
	@ManyToOne
	@JoinColumn(name="sucursalid")
	private Sucursal sucursal;

	public Audiwebservice() {
	}

	public int getIdaudiwebservice() {
		return this.idaudiwebservice;
	}

	public void setIdaudiwebservice(int idaudiwebservice) {
		this.idaudiwebservice = idaudiwebservice;
	}

	public String getApikey() {
		return this.apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getContenido() {
		return this.contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Date getFecharequest() {
		return this.fecharequest;
	}

	public void setFecharequest(Date fecharequest) {
		this.fecharequest = fecharequest;
	}

	public Eventowebservice getEventowebservice() {
		return this.eventowebservice;
	}

	public void setEventowebservice(Eventowebservice eventowebservice) {
		this.eventowebservice = eventowebservice;
	}

	public Sucursal getSucursal() {
		return this.sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

}