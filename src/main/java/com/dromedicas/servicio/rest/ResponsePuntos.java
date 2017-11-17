package com.dromedicas.servicio.rest;

import javax.xml.bind.annotation.XmlRootElement;

import com.dromedicas.domain.BalancePuntos;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@XmlRootElement
@JsonPropertyOrder({ "code", "message", "balance"})
public class ResponsePuntos {
	
	private String code;
	
	private String message;
	
	private BalancePuntos balance;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
	
}
