package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the perfil database table.
 * 
 */
@Entity
@NamedQuery(name="Perfil.findAll", query="SELECT p FROM Perfil p")
public class Perfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idperfil;

	private String css;

	private String descripcion;

	//bi-directional many-to-one association to Usuarioperfil
	@OneToMany(mappedBy="perfil")
	private Set<Usuarioperfil> usuarioperfils;
	
	// bi-directional many-to-many association to Usuarioweb
	@ManyToMany(mappedBy = "perfils1")
	private Set<Usuarioweb> usuariowebs1;

	// bi-directional many-to-many association to Usuarioweb
	@ManyToMany(mappedBy = "perfils2", cascade = { CascadeType.ALL })
	private Set<Usuarioweb> usuariowebs2;

	public Perfil() {
	}

	public int getIdperfil() {
		return this.idperfil;
	}

	public void setIdperfil(int idperfil) {
		this.idperfil = idperfil;
	}

	public String getCss() {
		return this.css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<Usuarioperfil> getUsuarioperfils() {
		return this.usuarioperfils;
	}

	public void setUsuarioperfils(Set<Usuarioperfil> usuarioperfils) {
		this.usuarioperfils = usuarioperfils;
	}

	public Usuarioperfil addUsuarioperfil(Usuarioperfil usuarioperfil) {
		getUsuarioperfils().add(usuarioperfil);
		usuarioperfil.setPerfil(this);

		return usuarioperfil;
	}

	public Usuarioperfil removeUsuarioperfil(Usuarioperfil usuarioperfil) {
		getUsuarioperfils().remove(usuarioperfil);
		usuarioperfil.setPerfil(null);

		return usuarioperfil;
	}

	public Set<Usuarioweb> getUsuariowebs1() {
		return usuariowebs1;
	}

	public void setUsuariowebs1(Set<Usuarioweb> usuariowebs1) {
		this.usuariowebs1 = usuariowebs1;
	}

	public Set<Usuarioweb> getUsuariowebs2() {
		return usuariowebs2;
	}

	public void setUsuariowebs2(Set<Usuarioweb> usuariowebs2) {
		this.usuariowebs2 = usuariowebs2;
	}
	
	

}