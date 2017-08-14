package com.dromedicas.domain;

import java.io.Serializable;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the sucursal database table.
 * 
 */
@ManagedBean(name="#{sucursal}")
@RequestScoped
@Entity
@NamedQuery(name="Sucursal.findAll", query="SELECT s FROM Sucursal s")
@XmlRootElement
public class Sucursal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idsucursal;

	private String apikey;

	private String codigointerno;

	private String direccion;

	private String email;
	
	
	@Column(name="nombre_sucursal")
	private String nombreSucursal;
	
	@JsonIgnore
	//bi-directional many-to-one association to Afiliado
	@OneToMany(mappedBy="sucursal", cascade={CascadeType.ALL})
	private Set<Afiliado> afiliados;

	@JsonIgnore
	//bi-directional many-to-one association to Audiwebservice
	@OneToMany(mappedBy="sucursal")
	private Set<Audiwebservice> audiwebservices;

	@JsonIgnore
	//bi-directional many-to-one association to Empresa
	@ManyToOne
	@JoinColumn(name="idempresa")
	private Empresa empresa;

	@JsonIgnore
	//bi-directional many-to-one association to Transaccion
	@OneToMany(mappedBy="sucursal")
	private Set<Transaccion> transaccions;

	public Sucursal() {
	}

	public int getIdsucursal() {
		return this.idsucursal;
	}

	public void setIdsucursal(int idsucursal) {
		this.idsucursal = idsucursal;
	}

	public String getApikey() {
		return this.apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getCodigointerno() {
		return this.codigointerno;
	}

	public void setCodigointerno(String codigointerno) {
		this.codigointerno = codigointerno;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombreSucursal() {
		return this.nombreSucursal;
	}

	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}

	public Set<Afiliado> getAfiliados() {
		return this.afiliados;
	}

	public void setAfiliados(Set<Afiliado> afiliados) {
		this.afiliados = afiliados;
	}

	public Afiliado addAfiliado(Afiliado afiliado) {
		getAfiliados().add(afiliado);
		afiliado.setSucursal(this);

		return afiliado;
	}

	public Afiliado removeAfiliado(Afiliado afiliado) {
		getAfiliados().remove(afiliado);
		afiliado.setSucursal(null);

		return afiliado;
	}

	public Set<Audiwebservice> getAudiwebservices() {
		return this.audiwebservices;
	}

	public void setAudiwebservices(Set<Audiwebservice> audiwebservices) {
		this.audiwebservices = audiwebservices;
	}

	public Audiwebservice addAudiwebservice(Audiwebservice audiwebservice) {
		getAudiwebservices().add(audiwebservice);
		audiwebservice.setSucursal(this);

		return audiwebservice;
	}

	public Audiwebservice removeAudiwebservice(Audiwebservice audiwebservice) {
		getAudiwebservices().remove(audiwebservice);
		audiwebservice.setSucursal(null);

		return audiwebservice;
	}

	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Set<Transaccion> getTransaccions() {
		return this.transaccions;
	}

	public void setTransaccions(Set<Transaccion> transaccions) {
		this.transaccions = transaccions;
	}

	public Transaccion addTransaccion(Transaccion transaccion) {
		getTransaccions().add(transaccion);
		transaccion.setSucursal(this);

		return transaccion;
	}

	public Transaccion removeTransaccion(Transaccion transaccion) {
		getTransaccions().remove(transaccion);
		transaccion.setSucursal(null);

		return transaccion;
	}

}