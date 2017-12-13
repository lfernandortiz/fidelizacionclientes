package com.dromedicas.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the afiliado database table.
 * 
 */
@ManagedBean(name="afiliado")
@RequestScoped
@Entity
@NamedQuery(name="Afiliado.findAll", query="SELECT a FROM Afiliado a ORDER BY a.momento DESC")
@XmlRootElement
public class Afiliado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idafiliado;

	private String apellidos;

	private String celular;

	private String ciudad;

	private String claveweb;

	private String departamento;

	private String documento;

	private int edad;

	private String email;

	private byte emailvalidado;
	
	private String facebookperfil;
	
	@JsonIgnore
	private String keycode;

	@Temporal(TemporalType.DATE)
	private Date fechanacimiento;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechavalidacionemail;

	private byte hijosentre13y18;

	private byte hijosentre4y12;

	private byte hijosmayores;

	private byte hijosmenoresde4;

	private Integer idvendedor;

	@Temporal(TemporalType.TIMESTAMP)
	private Date momento;

	private String nacionalidad;

	private String nombres;

	private BigInteger puntosavencer;

	private String sexo;

	private String street;

	private String streetdos;

	private String streetdosvalor;

	private String streetvalor;

	private String telefonofijo;

	private int tienehijos;

	private BigInteger totalpuntos;

	private String twitterperfil;

	private String urlimageperfil;

	private String usuarioweb;

	//bi-directional many-to-one association to Estudioafiliado
	@ManyToOne
	@JoinColumn(name="estudios")
	private Estudioafiliado estudioafiliado;

	//bi-directional many-to-one association to Ocupacion
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="ocupacion")
	private Ocupacion ocupacionBean;

	//bi-directional many-to-one association to Sucursal
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="idsucursal")
	@JsonIgnore
	private Sucursal sucursal;

	//bi-directional many-to-one association to Tipodocumento
	@ManyToOne
	@JoinColumn(name="tipodocumento")
	private Tipodocumento tipodocumentoBean;

	//bi-directional many-to-one association to Usuarioweb
	@ManyToOne
	@JoinColumn(name="creador")
	@JsonIgnore
	private Usuarioweb usuariowebBean;

	//bi-directional many-to-one association to Afiliadopatologia
	@OneToMany(mappedBy="afiliado", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Afiliadopatologia> afiliadopatologias;

	//bi-directional many-to-one association to Emailenvio
	@OneToMany(mappedBy="afiliado")
	@JsonIgnore
	private List<Emailenvio> emailenvios;

	//bi-directional many-to-one association to Nucleofamilia
	@OneToMany(mappedBy="afiliado")
	@JsonIgnore
	private List<Nucleofamilia> nucleofamilias;

	
	
	//bi-directional many-to-many association to Afiliado
	@JsonIgnore
		@ManyToMany(cascade={CascadeType.ALL})
		@JoinTable(
			name="afiliadopatologianucleo"
			, joinColumns={
				@JoinColumn(name="idpatologia")
				}
			, inverseJoinColumns={
				@JoinColumn(name="idafiliado")
				}
			)	
	private List<Patologia> patologias;

	//bi-directional many-to-one association to Referido
	@OneToMany(mappedBy="afiliado")
	@JsonIgnore
	private List<Referido> referidos;

	//bi-directional many-to-one association to Smsenvio
	@OneToMany(mappedBy="afiliado")
	@JsonIgnore
	private List<Smsenvio> smsenvios;

	//bi-directional many-to-one association to Transaccion
	@OneToMany(fetch = FetchType.EAGER, mappedBy="afiliado", cascade = CascadeType.ALL)
	@OrderBy("fechatransaccion desc")
	private List<Transaccion> transaccions;

	public Afiliado() {
	}

	public int getIdafiliado() {
		return this.idafiliado;
	}

	public void setIdafiliado(int idafiliado) {
		this.idafiliado = idafiliado;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCiudad() {
		return this.ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getClaveweb() {
		return this.claveweb;
	}

	public void setClaveweb(String claveweb) {
		this.claveweb = claveweb;
	}

	public String getDepartamento() {
		return this.departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getDocumento() {
		return this.documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public int getEdad() {
		return this.edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte getEmailvalidado() {
		return this.emailvalidado;
	}

	public void setEmailvalidado(byte emailvalidado) {
		this.emailvalidado = emailvalidado;
	}

	public String getFacebookperfil() {
		return this.facebookperfil;
	}

	public void setFacebookperfil(String facebookperfil) {
		this.facebookperfil = facebookperfil;
	}

	public Date getFechanacimiento() {
		return this.fechanacimiento;
	}

	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	public Date getFechavalidacionemail() {
		return this.fechavalidacionemail;
	}

	public void setFechavalidacionemail(Date fechavalidacionemail) {
		this.fechavalidacionemail = fechavalidacionemail;
	}

	public byte getHijosentre13y18() {
		return this.hijosentre13y18;
	}

	public void setHijosentre13y18(byte hijosentre13y18) {
		this.hijosentre13y18 = hijosentre13y18;
	}

	public byte getHijosentre4y12() {
		return this.hijosentre4y12;
	}

	public void setHijosentre4y12(byte hijosentre4y12) {
		this.hijosentre4y12 = hijosentre4y12;
	}

	public byte getHijosmayores() {
		return this.hijosmayores;
	}

	public void setHijosmayores(byte hijosmayores) {
		this.hijosmayores = hijosmayores;
	}

	public byte getHijosmenoresde4() {
		return this.hijosmenoresde4;
	}

	public void setHijosmenoresde4(byte hijosmenoresde4) {
		this.hijosmenoresde4 = hijosmenoresde4;
	}

	public Integer getIdvendedor() {
		return this.idvendedor;
	}

	public void setIdvendedor(Integer idvendedor) {
		this.idvendedor = idvendedor;
	}

	public Date getMomento() {
		return this.momento;
	}

	public void setMomento(Date momento) {
		this.momento = momento;
	}

	public String getNacionalidad() {
		return this.nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public BigInteger getPuntosavencer() {
		return this.puntosavencer;
	}

	public void setPuntosavencer(BigInteger puntosavencer) {
		this.puntosavencer = puntosavencer;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetdos() {
		return this.streetdos;
	}

	public void setStreetdos(String streetdos) {
		this.streetdos = streetdos;
	}

	public String getStreetdosvalor() {
		return this.streetdosvalor;
	}

	public void setStreetdosvalor(String streetdosvalor) {
		this.streetdosvalor = streetdosvalor;
	}

	public String getStreetvalor() {
		return this.streetvalor;
	}

	public void setStreetvalor(String streetvalor) {
		this.streetvalor = streetvalor;
	}

	public String getTelefonofijo() {
		return this.telefonofijo;
	}

	public void setTelefonofijo(String telefonofijo) {
		this.telefonofijo = telefonofijo;
	}

	public int getTienehijos() {
		return this.tienehijos;
	}

	public void setTienehijos(int tienehijos) {
		this.tienehijos = tienehijos;
	}

	public BigInteger getTotalpuntos() {
		return this.totalpuntos;
	}

	public void setTotalpuntos(BigInteger totalpuntos) {
		this.totalpuntos = totalpuntos;
	}

	public String getTwitterperfil() {
		return this.twitterperfil;
	}

	public void setTwitterperfil(String twitterperfil) {
		this.twitterperfil = twitterperfil;
	}

	public String getUrlimageperfil() {
		return this.urlimageperfil;
	}

	public void setUrlimageperfil(String urlimageperfil) {
		this.urlimageperfil = urlimageperfil;
	}

	public String getUsuarioweb() {
		return this.usuarioweb;
	}

	public void setUsuarioweb(String usuarioweb) {
		this.usuarioweb = usuarioweb;
	}

	public Estudioafiliado getEstudioafiliado() {
		return this.estudioafiliado;
	}

	public void setEstudioafiliado(Estudioafiliado estudioafiliado) {
		this.estudioafiliado = estudioafiliado;
	}

	public Ocupacion getOcupacionBean() {
		return this.ocupacionBean;
	}

	public void setOcupacionBean(Ocupacion ocupacionBean) {
		this.ocupacionBean = ocupacionBean;
	}

	public Sucursal getSucursal() {
		return this.sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Tipodocumento getTipodocumentoBean() {
		return this.tipodocumentoBean;
	}

	public void setTipodocumentoBean(Tipodocumento tipodocumentoBean) {
		this.tipodocumentoBean = tipodocumentoBean;
	}

	public Usuarioweb getUsuariowebBean() {
		return this.usuariowebBean;
	}

	public void setUsuariowebBean(Usuarioweb usuariowebBean) {
		this.usuariowebBean = usuariowebBean;
	}

	public List<Afiliadopatologia> getAfiliadopatologias() {
		return this.afiliadopatologias;
	}

	public void setAfiliadopatologias(List<Afiliadopatologia> afiliadopatologias) {
		this.afiliadopatologias = afiliadopatologias;
	}

	public Afiliadopatologia addAfiliadopatologia(Afiliadopatologia afiliadopatologia) {
		getAfiliadopatologias().add(afiliadopatologia);
		afiliadopatologia.setAfiliado(this);

		return afiliadopatologia;
	}

	public Afiliadopatologia removeAfiliadopatologia(Afiliadopatologia afiliadopatologia) {
		getAfiliadopatologias().remove(afiliadopatologia);
		afiliadopatologia.setAfiliado(null);

		return afiliadopatologia;
	}

	public List<Emailenvio> getEmailenvios() {
		return this.emailenvios;
	}

	public void setEmailenvios(List<Emailenvio> emailenvios) {
		this.emailenvios = emailenvios;
	}

	public Emailenvio addEmailenvio(Emailenvio emailenvio) {
		getEmailenvios().add(emailenvio);
		emailenvio.setAfiliado(this);

		return emailenvio;
	}

	public Emailenvio removeEmailenvio(Emailenvio emailenvio) {
		getEmailenvios().remove(emailenvio);
		emailenvio.setAfiliado(null);

		return emailenvio;
	}

	public List<Nucleofamilia> getNucleofamilias() {
		return this.nucleofamilias;
	}

	public void setNucleofamilias(List<Nucleofamilia> nucleofamilias) {
		this.nucleofamilias = nucleofamilias;
	}

	public Nucleofamilia addNucleofamilia(Nucleofamilia nucleofamilia) {
		getNucleofamilias().add(nucleofamilia);
		nucleofamilia.setAfiliado(this);

		return nucleofamilia;
	}

	public Nucleofamilia removeNucleofamilia(Nucleofamilia nucleofamilia) {
		getNucleofamilias().remove(nucleofamilia);
		nucleofamilia.setAfiliado(null);

		return nucleofamilia;
	}

	public List<Patologia> getPatologias() {
		return this.patologias;
	}

	public void setPatologias(List<Patologia> patologias) {
		this.patologias = patologias;
	}

	public List<Referido> getReferidos() {
		return this.referidos;
	}

	public void setReferidos(List<Referido> referidos) {
		this.referidos = referidos;
	}

	public Referido addReferido(Referido referido) {
		getReferidos().add(referido);
		referido.setAfiliado(this);

		return referido;
	}

	public Referido removeReferido(Referido referido) {
		getReferidos().remove(referido);
		referido.setAfiliado(null);

		return referido;
	}
	
	public String getKeycode() {
		return this.keycode;
	}

	public void setKeycode(String keycode) {
		this.keycode = keycode;
	}

	public List<Smsenvio> getSmsenvios() {
		return this.smsenvios;
	}

	public void setSmsenvios(List<Smsenvio> smsenvios) {
		this.smsenvios = smsenvios;
	}

	public Smsenvio addSmsenvio(Smsenvio smsenvio) {
		getSmsenvios().add(smsenvio);
		smsenvio.setAfiliado(this);

		return smsenvio;
	}

	public Smsenvio removeSmsenvio(Smsenvio smsenvio) {
		getSmsenvios().remove(smsenvio);
		smsenvio.setAfiliado(null);

		return smsenvio;
	}

	public List<Transaccion> getTransaccions() {
		return this.transaccions;
	}

	public void setTransaccions(List<Transaccion> transaccions) {
		this.transaccions = transaccions;
	}

	public Transaccion addTransaccion(Transaccion transaccion) {
		getTransaccions().add(transaccion);
		transaccion.setAfiliado(this);

		return transaccion;
	}

	public Transaccion removeTransaccion(Transaccion transaccion) {
		getTransaccions().remove(transaccion);
		transaccion.setAfiliado(null);

		return transaccion;
	}

}