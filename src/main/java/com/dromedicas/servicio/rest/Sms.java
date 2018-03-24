package com.dromedicas.servicio.rest;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class Sms {
	
	@JsonProperty("1")
	private com.dromedicas.servicio.rest.SmsOnly smsOnly;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("1")
	public com.dromedicas.servicio.rest.SmsOnly get1() {
	return smsOnly;
	}

	@JsonProperty("1")
	public void set1(com.dromedicas.servicio.rest.SmsOnly _1) {
	this.smsOnly = _1;
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
