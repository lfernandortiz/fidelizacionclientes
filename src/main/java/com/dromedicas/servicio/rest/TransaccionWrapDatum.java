package com.dromedicas.servicio.rest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@XmlRootElement
public class TransaccionWrapDatum implements Serializable {

	@JsonProperty("dni")
	private String dni;
	@JsonProperty("docuid")
	private String docuid;
	@JsonProperty("tipodoc")
	private String tipodoc;
	@JsonProperty("prefijo")
	private String prefijo;
	@JsonProperty("numero")
	private String numero;
	@JsonProperty("fechadoc")
	private String fechadoc;
	@JsonProperty("total")
	private String total;
	
	
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	private final static long serialVersionUID = 7105849166087446898L;

	@JsonProperty("dni")
	public String getDni() {
		return dni;
	}

	@JsonProperty("dni")
	public void setDni(String dni) {
		this.dni = dni;
	}

	@JsonProperty("docuid")
	public String getDocuid() {
		return docuid;
	}

	@JsonProperty("docuid")
	public void setDocuid(String docuid) {
		this.docuid = docuid;
	}

	@JsonProperty("tipodoc")
	public String getTipodoc() {
		return tipodoc;
	}

	@JsonProperty("tipodoc")
	public void setTipodoc(String tipodoc) {
		this.tipodoc = tipodoc;
	}

	@JsonProperty("prefijo")
	public String getPrefijo() {
		return prefijo;
	}

	@JsonProperty("prefijo")
	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}

	@JsonProperty("numero")
	public String getNumero() {
		return numero;
	}

	@JsonProperty("numero")
	public void setNumero(String numero) {
		this.numero = numero;
	}

	@JsonProperty("fechadoc")
	public String getFechadoc() {
		return fechadoc;
	}

	@JsonProperty("fechadoc")
	public void setFechadoc(String fechadoc) {
		this.fechadoc = fechadoc;
	}

	
	@JsonProperty("total")
	public String getTotal() {
		return total;
	}

	@JsonProperty("total")
	public void setTotal(String total) {
		this.total = total;
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