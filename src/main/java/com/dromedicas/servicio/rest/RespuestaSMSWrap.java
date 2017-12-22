package com.dromedicas.servicio.rest;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class RespuestaSMSWrap {

	@JsonProperty("cliente")
	private String cliente;
	@JsonProperty("lote_id")
	private String loteId;
	@JsonProperty("fecha_recepcion")
	private String fechaRecepcion;
	@JsonProperty("resultado")
	private Integer resultado;
	@JsonProperty("resultado_t")
	private Object resultadoT;
	@JsonProperty("sms_procesados")
	private Integer smsProcesados;
	@JsonProperty("referecia")
	private Object referecia;
	@JsonProperty("ip")
	private String ip;
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	private final static long serialVersionUID = -1561778896447044379L;

	@JsonProperty("cliente")
	public String getCliente() {
	return cliente;
	}

	@JsonProperty("cliente")
	public void setCliente(String cliente) {
	this.cliente = cliente;
	}

	@JsonProperty("lote_id")
	public String getLoteId() {
	return loteId;
	}

	@JsonProperty("lote_id")
	public void setLoteId(String loteId) {
	this.loteId = loteId;
	}

	@JsonProperty("fecha_recepcion")
	public String getFechaRecepcion() {
	return fechaRecepcion;
	}

	@JsonProperty("fecha_recepcion")
	public void setFechaRecepcion(String fechaRecepcion) {
	this.fechaRecepcion = fechaRecepcion;
	}

	@JsonProperty("resultado")
	public Integer getResultado() {
	return resultado;
	}

	@JsonProperty("resultado")
	public void setResultado(Integer resultado) {
	this.resultado = resultado;
	}

	@JsonProperty("resultado_t")
	public Object getResultadoT() {
	return resultadoT;
	}

	@JsonProperty("resultado_t")
	public void setResultadoT(Object resultadoT) {
	this.resultadoT = resultadoT;
	}

	@JsonProperty("sms_procesados")
	public Integer getSmsProcesados() {
	return smsProcesados;
	}

	@JsonProperty("sms_procesados")
	public void setSmsProcesados(Integer smsProcesados) {
	this.smsProcesados = smsProcesados;
	}

	@JsonProperty("referecia")
	public Object getReferecia() {
	return referecia;
	}

	@JsonProperty("referecia")
	public void setReferecia(Object referecia) {
	this.referecia = referecia;
	}

	@JsonProperty("ip")
	public String getIp() {
	return ip;
	}

	@JsonProperty("ip")
	public void setIp(String ip) {
	this.ip = ip;
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