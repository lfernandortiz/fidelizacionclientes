package com.dromedicas.servicio.rest;

import javax.xml.bind.annotation.XmlRootElement;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.BalancePuntos;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@XmlRootElement
@JsonPropertyOrder({ "code", "status", "message", "balance", "urlFotoAfiliado", "afiliado"})
public class ResponsePuntos {
	
	private int code;
	
	private String status;
	
	private String message;
	
	private BalancePuntos balance;
	
	private String urlFotoAfiliado;
	
	private Afiliado afiliado;	

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	

	public BalancePuntos getBalance() {
		return balance;
	}

	public void setBalance(BalancePuntos balance) {
		this.balance = balance;
	}
	
	public String getUrlFotoAfiliado() {
		return urlFotoAfiliado;
	}

	public void setUrlFotoAfiliado(String urlFotoAfiliado) {
		this.urlFotoAfiliado = urlFotoAfiliado;
	}

	public Afiliado getAfiliado() {
		return afiliado;
	}

	public void setAfiliado(Afiliado afiliado) {
		this.afiliado = afiliado;
	}
	
}
