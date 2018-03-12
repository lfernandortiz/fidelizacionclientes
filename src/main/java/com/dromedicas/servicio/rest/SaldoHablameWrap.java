
package com.dromedicas.servicio.rest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class SaldoHablameWrap implements Serializable {

	@JsonProperty("resultado")
	private Integer resultado;
	@JsonProperty("resultado_t")
	private String resultadoT;
	@JsonProperty("cliente")
	private String cliente;
	@JsonProperty("saldo")
	private String saldo;
	@JsonProperty("credito")
	private String credito;
	@JsonProperty("bloqueo")
	private String bloqueo;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("resultado")
	public Integer getResultado() {
		return resultado;
	}

	@JsonProperty("resultado")
	public void setResultado(Integer resultado) {
		this.resultado = resultado;
	}

	@JsonProperty("resultado_t")
	public String getResultadoT() {
		return resultadoT;
	}

	@JsonProperty("resultado_t")
	public void setResultadoT(String resultadoT) {
		this.resultadoT = resultadoT;
	}

	@JsonProperty("cliente")
	public String getCliente() {
		return cliente;
	}

	@JsonProperty("cliente")
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	@JsonProperty("saldo")
	public String getSaldo() {
		return saldo;
	}

	@JsonProperty("saldo")
	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	@JsonProperty("credito")
	public String getCredito() {
		return credito;
	}

	@JsonProperty("credito")
	public void setCredito(String credito) {
		this.credito = credito;
	}

	@JsonProperty("bloqueo")
	public String getBloqueo() {
		return bloqueo;
	}

	@JsonProperty("bloqueo")
	public void setBloqueo(String bloqueo) {
		this.bloqueo = bloqueo;
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