package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the transaccion database table.
 * 
 */
@Entity
@NamedQuery(name="Transaccion.findAll", query="SELECT t FROM Transaccion t")
public class Transaccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idtransaccion;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechatransaccion;

	private String nrofactura;

	private int puntostransaccion;

	private byte redimidos;

	private int saldo;

	private int valortotaltx;

	@Temporal(TemporalType.DATE)
	private Date vencen;

	//bi-directional many-to-one association to Detransaccion
	@OneToMany(mappedBy="transaccion")
	private Set<Detransaccion> detransaccions;

	//bi-directional many-to-one association to Emailenvio
	@OneToMany(mappedBy="transaccion")
	private Set<Emailenvio> emailenvios;

	//bi-directional many-to-one association to Punto
	@OneToMany(mappedBy="transaccion")
	private Set<Punto> puntos;

	//bi-directional many-to-one association to Afiliado
	@ManyToOne
	@JoinColumn(name="idafiliado")
	private Afiliado afiliado;

	//bi-directional many-to-one association to Sucursal
	@ManyToOne
	@JoinColumn(name="idsucursal")
	private Sucursal sucursal;

	//bi-directional many-to-one association to Tipotransaccion
	@ManyToOne
	@JoinColumn(name="tipotx")
	private Tipotransaccion tipotransaccion;

	public Transaccion() {
	}

	public int getIdtransaccion() {
		return this.idtransaccion;
	}

	public void setIdtransaccion(int idtransaccion) {
		this.idtransaccion = idtransaccion;
	}

	public Date getFechatransaccion() {
		return this.fechatransaccion;
	}

	public void setFechatransaccion(Date fechatransaccion) {
		this.fechatransaccion = fechatransaccion;
	}

	public String getNrofactura() {
		return this.nrofactura;
	}

	public void setNrofactura(String nrofactura) {
		this.nrofactura = nrofactura;
	}

	public int getPuntostransaccion() {
		return this.puntostransaccion;
	}

	public void setPuntostransaccion(int puntostransaccion) {
		this.puntostransaccion = puntostransaccion;
	}

	public byte getRedimidos() {
		return this.redimidos;
	}

	public void setRedimidos(byte redimidos) {
		this.redimidos = redimidos;
	}

	public int getSaldo() {
		return this.saldo;
	}

	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}

	public int getValortotaltx() {
		return this.valortotaltx;
	}

	public void setValortotaltx(int valortotaltx) {
		this.valortotaltx = valortotaltx;
	}

	public Date getVencen() {
		return this.vencen;
	}

	public void setVencen(Date vencen) {
		this.vencen = vencen;
	}

	public Set<Detransaccion> getDetransaccions() {
		return this.detransaccions;
	}

	public void setDetransaccions(Set<Detransaccion> detransaccions) {
		this.detransaccions = detransaccions;
	}

	public Detransaccion addDetransaccion(Detransaccion detransaccion) {
		getDetransaccions().add(detransaccion);
		detransaccion.setTransaccion(this);

		return detransaccion;
	}

	public Detransaccion removeDetransaccion(Detransaccion detransaccion) {
		getDetransaccions().remove(detransaccion);
		detransaccion.setTransaccion(null);

		return detransaccion;
	}

	public Set<Emailenvio> getEmailenvios() {
		return this.emailenvios;
	}

	public void setEmailenvios(Set<Emailenvio> emailenvios) {
		this.emailenvios = emailenvios;
	}

	public Emailenvio addEmailenvio(Emailenvio emailenvio) {
		getEmailenvios().add(emailenvio);
		emailenvio.setTransaccion(this);

		return emailenvio;
	}

	public Emailenvio removeEmailenvio(Emailenvio emailenvio) {
		getEmailenvios().remove(emailenvio);
		emailenvio.setTransaccion(null);

		return emailenvio;
	}

	public Set<Punto> getPuntos() {
		return this.puntos;
	}

	public void setPuntos(Set<Punto> puntos) {
		this.puntos = puntos;
	}

	public Punto addPunto(Punto punto) {
		getPuntos().add(punto);
		punto.setTransaccion(this);

		return punto;
	}

	public Punto removePunto(Punto punto) {
		getPuntos().remove(punto);
		punto.setTransaccion(null);

		return punto;
	}

	public Afiliado getAfiliado() {
		return this.afiliado;
	}

	public void setAfiliado(Afiliado afiliado) {
		this.afiliado = afiliado;
	}

	public Sucursal getSucursal() {
		return this.sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Tipotransaccion getTipotransaccion() {
		return this.tipotransaccion;
	}

	public void setTipotransaccion(Tipotransaccion tipotransaccion) {
		this.tipotransaccion = tipotransaccion;
	}

	@Override
	public String toString() {
		return "Transaccion [idtransaccion=" + idtransaccion + ", fechatransaccion=" + fechatransaccion
				+ ", nrofactura=" + nrofactura + ", puntostransaccion=" + puntostransaccion + ", redimidos=" + redimidos
				+ ", saldo=" + saldo + ", valortotaltx=" + valortotaltx + ", vencen=" + vencen + "]";
	}

	
}