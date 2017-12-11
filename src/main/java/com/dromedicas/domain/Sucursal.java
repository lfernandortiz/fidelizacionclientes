package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.Set;


/**
 * The persistent class for the sucursal database table.
 * 
 */
@Entity
@NamedQuery(name="Sucursal.findAll", query="SELECT s FROM Sucursal s")
public class Sucursal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idsucursal;

	private String apikey;

	private String codigointerno;

	private String direccion;

	private String email;

	private String es24horas;

	private Time horacierrees;

	private Time horacierregen;

	private Time horaperturaes;

	private Time horaperturagen;

	@Column(name="nombre_sucursal")
	private String nombreSucursal;

	private String rutaweb;

	//bi-directional many-to-one association to Afiliado
	@OneToMany(mappedBy="sucursal", cascade={CascadeType.ALL})
	private Set<Afiliado> afiliados;

	//bi-directional many-to-one association to Audiwebservice
	@OneToMany(mappedBy="sucursal")
	private Set<Audiwebservice> audiwebservices;

	//bi-directional many-to-one association to Empresa
	@ManyToOne
	@JoinColumn(name="idempresa")
	private Empresa empresa;

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

	public String getEs24horas() {
		return this.es24horas;
	}

	public void setEs24horas(String es24horas) {
		this.es24horas = es24horas;
	}

	public Time getHoracierrees() {
		return this.horacierrees;
	}

	public void setHoracierrees(Time horacierrees) {
		this.horacierrees = horacierrees;
	}

	public Time getHoracierregen() {
		return this.horacierregen;
	}

	public void setHoracierregen(Time horacierregen) {
		this.horacierregen = horacierregen;
	}

	public Time getHoraperturaes() {
		return this.horaperturaes;
	}

	public void setHoraperturaes(Time horaperturaes) {
		this.horaperturaes = horaperturaes;
	}

	public Time getHoraperturagen() {
		return this.horaperturagen;
	}

	public void setHoraperturagen(Time horaperturagen) {
		this.horaperturagen = horaperturagen;
	}

	public String getNombreSucursal() {
		return this.nombreSucursal;
	}

	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}

	public String getRutaweb() {
		return this.rutaweb;
	}

	public void setRutaweb(String rutaweb) {
		this.rutaweb = rutaweb;
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