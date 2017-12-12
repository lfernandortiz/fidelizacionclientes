package com.dromedicas.servicio.rest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "code", "data" })
public class TransaccionWrap implements Serializable {

	@JsonProperty("code")
	private String code;
	@JsonProperty("data")
	private List<TransaccionWrapDatum> data = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	private final static long serialVersionUID = -3679474316427482204L;

	@JsonProperty("code")
	public String getCode() {
		return code;
	}

	@JsonProperty("code")
	public void setCode(String code) {
		this.code = code;
	}

	@JsonProperty("data")
	public List<TransaccionWrapDatum> getData() {
		return data;
	}

	@JsonProperty("data")
	public void setData(List<TransaccionWrapDatum> data) {
		this.data = data;
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