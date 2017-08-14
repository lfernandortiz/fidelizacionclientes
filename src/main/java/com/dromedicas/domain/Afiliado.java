package com.dromedicas.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the afiliado database table.
 * 
 */
@ManagedBean(name="afiliado")
@RequestScoped
@Entity
@NamedQuery(name="Afiliado.findAll", query="SELECT a FROM Afiliado a")
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

	private String email;

	private byte emailvalidado;

	private String facebookperfil;

	@Temporal(TemporalType.DATE)
	private Date fechanacimiento;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechavalidacionemail;

	private int idvendedor;

	@Temporal(TemporalType.TIMESTAMP)
	private Date momento;

	private String nacionalidad;

	private String nombres;

	private String sexo;

	private String street;

	private String streetdos;

	private String streetdosvalor;

	private String streetvalor;

	private String telefonofijo;

	private String twitterperfil;

	private String urlimageperfil;

	private String usuarioweb;

	//bi-directional many-to-one association to Sucursal
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="idsucursal")
	private Sucursal sucursal;

	//bi-directional many-to-one association to Tipodocumento
	@ManyToOne
	@JoinColumn(name="tipodocumento")
	private Tipodocumento tipodocumentoBean;

	//bi-directional many-to-one association to Usuarioweb
	@ManyToOne
	@JoinColumn(name="creador")
	private Usuarioweb usuariowebBean;

	//bi-directional many-to-one association to Emailenvio
	@OneToMany(mappedBy="afiliado")
	private Set<Emailenvio> emailenvios;

	//bi-directional many-to-one association to Smsenvio
	@OneToMany(mappedBy="afiliado")
	private Set<Smsenvio> smsenvios;

	//bi-directional many-to-one association to Transaccion
	@OneToMany(mappedBy="afiliado")
	private Set<Transaccion> transaccions;

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

	public int getIdvendedor() {
		return this.idvendedor;
	}

	public void setIdvendedor(int idvendedor) {
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
		return usuariowebBean;
	}

	public void setUsuariowebBean(Usuarioweb usuariowebBean) {
		this.usuariowebBean = usuariowebBean;
	}

	public Set<Emailenvio> getEmailenvios() {
		return this.emailenvios;
	}

	public void setEmailenvios(Set<Emailenvio> emailenvios) {
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

	public Set<Smsenvio> getSmsenvios() {
		return this.smsenvios;
	}

	public void setSmsenvios(Set<Smsenvio> smsenvios) {
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

	public Set<Transaccion> getTransaccions() {
		return this.transaccions;
	}

	public void setTransaccions(Set<Transaccion> transaccions) {
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