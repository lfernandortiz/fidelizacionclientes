package com.dromedicas.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import com.sun.istack.internal.NotNull;


/**
 * The persistent class for the permisorol database table.
 * 
 */
@Entity
@NamedQuery(name="Permisorol.findAll", query="SELECT p FROM Permisorol p")
public class Permisorol implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "idrol", column = @Column(name = "idrol", nullable = false)),
			@AttributeOverride(name = "idusuario", column = @Column(name = "idusuario", nullable = false)) })
	@NotNull
	private PermisorolPK id;

	private byte agregar;

	private byte eliminar;

	private byte especial1;

	private byte especial2;

	private byte insertar;

	private byte seleccionar;

	//bi-directional many-to-one association to Rol
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idrol", nullable = false, insertable = false, updatable = false)
	@NotNull
	private Rol rol;

	//bi-directional many-to-one association to Usuarioweb
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idusuario", nullable = false, insertable = false, updatable = false)
	@NotNull
	private Usuarioweb usuarioweb;

	public Permisorol() {
	}

	public PermisorolPK getId() {
		return this.id;
	}

	public void setId(PermisorolPK id) {
		this.id = id;
	}

	public byte getAgregar() {
		return this.agregar;
	}

	public void setAgregar(byte agregar) {
		this.agregar = agregar;
	}

	public byte getEliminar() {
		return this.eliminar;
	}

	public void setEliminar(byte eliminar) {
		this.eliminar = eliminar;
	}

	public byte getEspecial1() {
		return this.especial1;
	}

	public void setEspecial1(byte especial1) {
		this.especial1 = especial1;
	}

	public byte getEspecial2() {
		return this.especial2;
	}

	public void setEspecial2(byte especial2) {
		this.especial2 = especial2;
	}

	public byte getInsertar() {
		return this.insertar;
	}

	public void setInsertar(byte insertar) {
		this.insertar = insertar;
	}

	public byte getSeleccionar() {
		return this.seleccionar;
	}

	public void setSeleccionar(byte seleccionar) {
		this.seleccionar = seleccionar;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Usuarioweb getUsuarioweb() {
		return this.usuarioweb;
	}

	public void setUsuarioweb(Usuarioweb usuarioweb) {
		this.usuarioweb = usuarioweb;
	}

}