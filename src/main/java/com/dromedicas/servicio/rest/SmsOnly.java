package com.dromedicas.servicio.rest;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class SmsOnly {
	@JsonProperty("id")
	private String id;
	@JsonProperty("numero")
	private String numero;
	@JsonProperty("sms")
	private String sms;
	@JsonProperty("fecha_envio")
	private String fechaEnvio;
	@JsonProperty("ind_area_nom")
	private String indAreaNom;
	@JsonProperty("precio_sms")
	private String precioSms;
	@JsonProperty("resultado_t")
	private String resultadoT;
	@JsonProperty("resultado")
	private String resultado;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("id")
	public String getId() {
	return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
	this.id = id;
	}

	@JsonProperty("numero")
	public String getNumero() {
	return numero;
	}

	@JsonProperty("numero")
	public void setNumero(String numero) {
	this.numero = numero;
	}

	@JsonProperty("sms")
	public String getSms() {
	return sms;
	}

	@JsonProperty("sms")
	public void setSms(String sms) {
	this.sms = sms;
	}

	@JsonProperty("fecha_envio")
	public String getFechaEnvio() {
	return fechaEnvio;
	}

	@JsonProperty("fecha_envio")
	public void setFechaEnvio(String fechaEnvio) {
	this.fechaEnvio = fechaEnvio;
	}

	@JsonProperty("ind_area_nom")
	public String getIndAreaNom() {
	return indAreaNom;
	}

	@JsonProperty("ind_area_nom")
	public void setIndAreaNom(String indAreaNom) {
	this.indAreaNom = indAreaNom;
	}

	@JsonProperty("precio_sms")
	public String getPrecioSms() {
	return precioSms;
	}

	@JsonProperty("precio_sms")
	public void setPrecioSms(String precioSms) {
	this.precioSms = precioSms;
	}

	@JsonProperty("resultado_t")
	public String getResultadoT() {
	return resultadoT;
	}

	@JsonProperty("resultado_t")
	public void setResultadoT(String resultadoT) {
	this.resultadoT = resultadoT;
	}

	@JsonProperty("resultado")
	public String getResultado() {
	return resultado;
	}

	@JsonProperty("resultado")
	public void setResultado(String resultado) {
	this.resultado = resultado;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}


}
