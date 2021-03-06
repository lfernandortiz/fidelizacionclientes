package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;


/**
 * The persistent class for the tipodocumento database table.
 * 
 */
@Entity
@NamedQueries({@NamedQuery(name="Tipodocumento.findAll", query="SELECT t FROM Tipodocumento t")})
public class Tipodocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idtipodocumento;

	private String descripcion;

	//bi-directional many-to-one association to Afiliado
	@OneToMany(mappedBy="tipodocumentoBean")
	@JsonIgnore
	private Set<Afiliado> afiliados;

	public Tipodocumento() {
	}

	public int getIdtipodocumento() {
		return this.idtipodocumento;
	}

	public void setIdtipodocumento(int idtipodocumento) {
		this.idtipodocumento = idtipodocumento;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<Afiliado> getAfiliados() {
		return this.afiliados;
	}

	public void setAfiliados(Set<Afiliado> afiliados) {
		this.afiliados = afiliados;
	}

	public Afiliado addAfiliado(Afiliado afiliado) {
		getAfiliados().add(afiliado);
		afiliado.setTipodocumentoBean(this);

		return afiliado;
	}

	public Afiliado removeAfiliado(Afiliado afiliado) {
		getAfiliados().remove(afiliado);
		afiliado.setTipodocumentoBean(null);

		return afiliado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idtipodocumento;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tipodocumento other = (Tipodocumento) obj;
		if (idtipodocumento != other.idtipodocumento)
			return false;
		return true;
	}

	
}