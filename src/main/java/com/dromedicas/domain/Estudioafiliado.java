package com.dromedicas.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the estudioafiliado database table.
 * 
 */
@Entity
@NamedQuery(name="Estudioafiliado.findAll", query="SELECT e FROM Estudioafiliado e")
@XmlRootElement
public class Estudioafiliado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idestudioafiliado;

	private String descripcion;

	//bi-directional many-to-one association to Afiliado
	@OneToMany(mappedBy="estudioafiliado")
	@JsonIgnore
	private List<Afiliado> afiliados;

	public Estudioafiliado() {
	}

	public int getIdestudioafiliado() {
		return this.idestudioafiliado;
	}

	public void setIdestudioafiliado(int idestudioafiliado) {
		this.idestudioafiliado = idestudioafiliado;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Afiliado> getAfiliados() {
		return this.afiliados;
	}

	public void setAfiliados(List<Afiliado> afiliados) {
		this.afiliados = afiliados;
	}

	public Afiliado addAfiliado(Afiliado afiliado) {
		getAfiliados().add(afiliado);
		afiliado.setEstudioafiliado(this);

		return afiliado;
	}

	public Afiliado removeAfiliado(Afiliado afiliado) {
		getAfiliados().remove(afiliado);
		afiliado.setEstudioafiliado(null);

		return afiliado;
	}

}