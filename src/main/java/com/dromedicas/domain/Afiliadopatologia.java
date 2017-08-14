package com.dromedicas.domain;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.AfiliadopatologiaPK;


/**
 * The persistent class for the afiliadopatologia database table.
 *  
 */
@Entity
@NamedQuery(name="Afiliadopatologia.findAll", query="SELECT a FROM Afiliadopatologia a")
public class Afiliadopatologia implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AfiliadopatologiaPK id;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	//bi-directional many-to-one association to Patologia
	@ManyToOne
	@JoinColumn(name="idpatologia")
	private Patologia patologia;

	//bi-directional many-to-one association to Afiliado
	@ManyToOne
	@JoinColumn(name="idafiliado")
	private Afiliado afiliado;

	public Afiliadopatologia() {
	}

	public AfiliadopatologiaPK getId() {
		return this.id;
	}

	public void setId(AfiliadopatologiaPK id) {
		this.id = id;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Patologia getPatologia() {
		return this.patologia;
	}

	public void setPatologia(Patologia patologia) {
		this.patologia = patologia;
	}

	public Afiliado getAfiliado() {
		return this.afiliado;
	}

	public void setAfiliado(Afiliado afiliado) {
		this.afiliado = afiliado;
	}

}