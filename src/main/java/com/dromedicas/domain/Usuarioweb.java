package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the usuarioweb database table.
 * 
 */
@Entity
@NamedQuery(name="Usuarioweb.findAll", query="SELECT u FROM Usuarioweb u")
public class Usuarioweb implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idusuarioweb;

	private byte activo;

	private String clave;

	private String emailusuario;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechacreado;

	private String nombreusuario;

	@Temporal(TemporalType.TIMESTAMP)
	private Date ultacceso;

	@Temporal(TemporalType.TIMESTAMP)
	private Date ultcamioclave;

	private String usuario;
	
	// bi-directional many-to-many association to Rol
	@ManyToMany
	@JoinTable(name = "permisorol", joinColumns = { @JoinColumn(name = "idusuario") }, inverseJoinColumns = {
			@JoinColumn(name = "idrol") })
	private Set<Rol> rols1;

	// bi-directional many-to-many association to Rol
	@ManyToMany
	@JoinTable(name = "permisorol", joinColumns = { @JoinColumn(name = "idusuario") }, inverseJoinColumns = {
			@JoinColumn(name = "idrol") })
	private Set<Rol> rols2;	
	
	// bi-directional many-to-many association to Perfil
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "usuarioperfil", joinColumns = { @JoinColumn(name = "idusuario") }, inverseJoinColumns = {
			@JoinColumn(name = "idperfil") })
	private Set<Perfil> perfils1;

	// bi-directional many-to-many association to Perfil
	@ManyToMany
	@JoinTable(name = "usuarioperfil", joinColumns = { @JoinColumn(name = "idusuario") }, inverseJoinColumns = {
			@JoinColumn(name = "idperfil") })
	private Set<Perfil> perfils2;
	

	//bi-directional many-to-one association to Afiliado
	@OneToMany(mappedBy="usuariowebBean")
	private Set<Afiliado> afiliados;

	//bi-directional many-to-one association to Permisorol
	@OneToMany(mappedBy="usuarioweb")
	private Set<Permisorol> permisorols;

	//bi-directional many-to-one association to Usuarioperfil
	@OneToMany(mappedBy="usuarioweb")
	private Set<Usuarioperfil> usuarioperfils;

	//bi-directional many-to-one association to Tipousuario
	@ManyToOne
	@JoinColumn(name="tipousuarioid")
	private Tipousuario tipousuario;

	//bi-directional many-to-one association to Usuarioweb
	@ManyToOne
	@JoinColumn(name="creado")
	private Usuarioweb usuarioweb;

	//bi-directional many-to-one association to Usuarioweb
	@OneToMany(mappedBy="usuarioweb")
	private Set<Usuarioweb> usuariowebs;

	public Usuarioweb() {
	}

	public int getIdusuarioweb() {
		return this.idusuarioweb;
	}

	public void setIdusuarioweb(int idusuarioweb) {
		this.idusuarioweb = idusuarioweb;
	}

	public byte getActivo() {
		return this.activo;
	}

	public void setActivo(byte activo) {
		this.activo = activo;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getEmailusuario() {
		return this.emailusuario;
	}

	public void setEmailusuario(String emailusuario) {
		this.emailusuario = emailusuario;
	}

	public Date getFechacreado() {
		return this.fechacreado;
	}

	public void setFechacreado(Date fechacreado) {
		this.fechacreado = fechacreado;
	}

	public String getNombreusuario() {
		return this.nombreusuario;
	}

	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}

	public Date getUltacceso() {
		return this.ultacceso;
	}

	public void setUltacceso(Date ultacceso) {
		this.ultacceso = ultacceso;
	}

	public Date getUltcamioclave() {
		return this.ultcamioclave;
	}

	public void setUltcamioclave(Date ultcamioclave) {
		this.ultcamioclave = ultcamioclave;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Set<Afiliado> getAfiliados() {
		return this.afiliados;
	}

	public void setAfiliados(Set<Afiliado> afiliados) {
		this.afiliados = afiliados;
	}

	public Afiliado addAfiliado(Afiliado afiliado) {
		getAfiliados().add(afiliado);
		afiliado.setUsuariowebBean(this);

		return afiliado;
	}

	public Afiliado removeAfiliado(Afiliado afiliado) {
		getAfiliados().remove(afiliado);
		afiliado.setUsuariowebBean(null);

		return afiliado;
	}

	public Set<Permisorol> getPermisorols() {
		return this.permisorols;
	}

	public void setPermisorols(Set<Permisorol> permisorols) {
		this.permisorols = permisorols;
	}

	public Permisorol addPermisorol(Permisorol permisorol) {
		getPermisorols().add(permisorol);
		permisorol.setUsuarioweb(this);

		return permisorol;
	}

	public Permisorol removePermisorol(Permisorol permisorol) {
		getPermisorols().remove(permisorol);
		permisorol.setUsuarioweb(null);

		return permisorol;
	}

	public Set<Usuarioperfil> getUsuarioperfils() {
		return this.usuarioperfils;
	}

	public void setUsuarioperfils(Set<Usuarioperfil> usuarioperfils) {
		this.usuarioperfils = usuarioperfils;
	}

	public Usuarioperfil addUsuarioperfil(Usuarioperfil usuarioperfil) {
		getUsuarioperfils().add(usuarioperfil);
		usuarioperfil.setUsuarioweb(this);

		return usuarioperfil;
	}

	public Usuarioperfil removeUsuarioperfil(Usuarioperfil usuarioperfil) {
		getUsuarioperfils().remove(usuarioperfil);
		usuarioperfil.setUsuarioweb(null);

		return usuarioperfil;
	}

	public Tipousuario getTipousuario() {
		return this.tipousuario;
	}

	public void setTipousuario(Tipousuario tipousuario) {
		this.tipousuario = tipousuario;
	}

	public Usuarioweb getUsuarioweb() {
		return this.usuarioweb;
	}

	public void setUsuarioweb(Usuarioweb usuarioweb) {
		this.usuarioweb = usuarioweb;
	}

	public Set<Usuarioweb> getUsuariowebs() {
		return this.usuariowebs;
	}

	public void setUsuariowebs(Set<Usuarioweb> usuariowebs) {
		this.usuariowebs = usuariowebs;
	}

	public Usuarioweb addUsuarioweb(Usuarioweb usuarioweb) {
		getUsuariowebs().add(usuarioweb);
		usuarioweb.setUsuarioweb(this);

		return usuarioweb;
	}

	public Usuarioweb removeUsuarioweb(Usuarioweb usuarioweb) {
		getUsuariowebs().remove(usuarioweb);
		usuarioweb.setUsuarioweb(null);

		return usuarioweb;
	}

	public Set<Rol> getRols2() {
		return rols2;
	}

	public void setRols2(Set<Rol> rols2) {
		this.rols2 = rols2;
	}

	public Set<Rol> getRols1() {
		return rols1;
	}

	public void setRols1(Set<Rol> rols1) {
		this.rols1 = rols1;
	}

	public Set<Perfil> getPerfils1() {
		return perfils1;
	}

	public void setPerfils1(Set<Perfil> perfils1) {
		this.perfils1 = perfils1;
	}

	public Set<Perfil> getPerfils2() {
		return perfils2;
	}

	public void setPerfils2(Set<Perfil> perfils2) {
		this.perfils2 = perfils2;
	}
	
	

}