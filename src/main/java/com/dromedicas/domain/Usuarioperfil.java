package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * The persistent class for the usuarioperfil database table.
 * 
 */
@Entity
@NamedQuery(name="Usuarioperfil.findAll", query="SELECT u FROM Usuarioperfil u")
public class Usuarioperfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "idperfil", column = @Column(name = "idperfil", nullable = false)),
			@AttributeOverride(name = "idusuario", column = @Column(name = "idusuario", nullable = false)) })
	@NotNull
	private UsuarioperfilPK id;

	private String descripcion;

	//bi-directional many-to-one association to Perfil
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idperfil", nullable = false, insertable = false, updatable = false)
	@NotNull
	private Perfil perfil;

	//bi-directional many-to-one association to Usuarioweb
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idusuario", nullable = false, insertable = false, updatable = false)
	@NotNull
	private Usuarioweb usuarioweb;

	public Usuarioperfil() {
	}

	public UsuarioperfilPK getId() {
		return this.id;
	}

	public void setId(UsuarioperfilPK id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Usuarioweb getUsuarioweb() {
		return this.usuarioweb;
	}

	public void setUsuarioweb(Usuarioweb usuarioweb) {
		this.usuarioweb = usuarioweb;
	}

}