package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the rol database table.
 * 
 */
@Entity
@NamedQueries({@NamedQuery(name="Rol.findAll", query="SELECT r FROM Rol r")})
public class Rol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idrol;

	private String nombrerol;

	//bi-directional many-to-one association to Permisorol
	@OneToMany(mappedBy="rol")
	private Set<Permisorol> permisorols;

	//bi-directional many-to-many association to Usuarioweb
	@ManyToMany(mappedBy="rols1")
	private Set<Usuarioweb> usuariowebs1;

	//bi-directional many-to-many association to Usuarioweb
	@ManyToMany(mappedBy="rols2")
	private Set<Usuarioweb> usuariowebs2;

	public Rol() {
	}

	public int getIdrol() {
		return this.idrol;
	}

	public void setIdrol(int idrol) {
		this.idrol = idrol;
	}

	public String getNombrerol() {
		return this.nombrerol;
	}

	public void setNombrerol(String nombrerol) {
		this.nombrerol = nombrerol;
	}

	public Set<Permisorol> getPermisorols() {
		return this.permisorols;
	}

	public void setPermisorols(Set<Permisorol> permisorols) {
		this.permisorols = permisorols;
	}

	public Permisorol addPermisorol(Permisorol permisorol) {
		getPermisorols().add(permisorol);
		permisorol.setRol(this);

		return permisorol;
	}

	public Permisorol removePermisorol(Permisorol permisorol) {
		getPermisorols().remove(permisorol);
		permisorol.setRol(null);

		return permisorol;
	}

	public Set<Usuarioweb> getUsuariowebs1() {
		return this.usuariowebs1;
	}

	public void setUsuariowebs1(Set<Usuarioweb> usuariowebs1) {
		this.usuariowebs1 = usuariowebs1;
	}

	public Set<Usuarioweb> getUsuariowebs2() {
		return this.usuariowebs2;
	}

	public void setUsuariowebs2(Set<Usuarioweb> usuariowebs2) {
		this.usuariowebs2 = usuariowebs2;
	}

}