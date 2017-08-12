package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the tipousuario database table.
 * 
 */
@Entity
@NamedQuery(name="Tipousuario.findAll", query="SELECT t FROM Tipousuario t")
public class Tipousuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idtipousuario;

	private String descripcion;

	//bi-directional many-to-one association to Usuarioweb
	@OneToMany(mappedBy="tipousuario")
	private Set<Usuarioweb> usuariowebs;

	public Tipousuario() {
	}

	public int getIdtipousuario() {
		return this.idtipousuario;
	}

	public void setIdtipousuario(int idtipousuario) {
		this.idtipousuario = idtipousuario;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<Usuarioweb> getUsuariowebs() {
		return this.usuariowebs;
	}

	public void setUsuariowebs(Set<Usuarioweb> usuariowebs) {
		this.usuariowebs = usuariowebs;
	}

	public Usuarioweb addUsuarioweb(Usuarioweb usuarioweb) {
		getUsuariowebs().add(usuarioweb);
		usuarioweb.setTipousuario(this);

		return usuarioweb;
	}

	public Usuarioweb removeUsuarioweb(Usuarioweb usuarioweb) {
		getUsuariowebs().remove(usuarioweb);
		usuarioweb.setTipousuario(null);

		return usuarioweb;
	}

}