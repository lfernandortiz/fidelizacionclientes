package com.dromedicas.servicio.rest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "dni", "docuid", "tipodoc", "prefijo", "numero", "periodo", "fechadoc", "fecvence", "fecasentado",
		"fecanulado", "formapago", "nrodocref", "importado", "terid1", "terid2", "terid3", "codigo1", "codigo2",
		"codigo3", "observacion", "observacion2", "retefuente", "descuentogen", "base", "iva", "total", "impreso",
		"totalitems", "sucursalid", "creador", "momento" })
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
	@JsonProperty("periodo")
	private String periodo;
	@JsonProperty("fechadoc")
	private String fechadoc;
	@JsonProperty("fecvence")
	private String fecvence;
	@JsonProperty("fecasentado")
	private String fecasentado;
	@JsonProperty("fecanulado")
	private String fecanulado;
	@JsonProperty("formapago")
	private String formapago;
	@JsonProperty("nrodocref")
	private String nrodocref;
	@JsonProperty("importado")
	private String importado;
	@JsonProperty("terid1")
	private String terid1;
	@JsonProperty("terid2")
	private String terid2;
	@JsonProperty("terid3")
	private String terid3;
	@JsonProperty("codigo1")
	private String codigo1;
	@JsonProperty("codigo2")
	private String codigo2;
	@JsonProperty("codigo3")
	private String codigo3;
	@JsonProperty("observacion")
	private String observacion;
	@JsonProperty("observacion2")
	private Object observacion2;
	@JsonProperty("retefuente")
	private String retefuente;
	@JsonProperty("descuentogen")
	private String descuentogen;
	@JsonProperty("base")
	private String base;
	@JsonProperty("iva")
	private String iva;
	@JsonProperty("total")
	private String total;
	@JsonProperty("impreso")
	private String impreso;
	@JsonProperty("totalitems")
	private String totalitems;
	@JsonProperty("sucursalid")
	private String sucursalid;
	@JsonProperty("creador")
	private String creador;
	@JsonProperty("momento")
	private String momento;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	private final static long serialVersionUID = 6954442956966922802L;

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

	@JsonProperty("periodo")
	public String getPeriodo() {
		return periodo;
	}

	@JsonProperty("periodo")
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	@JsonProperty("fechadoc")
	public String getFechadoc() {
		return fechadoc;
	}

	@JsonProperty("fechadoc")
	public void setFechadoc(String fechadoc) {
		this.fechadoc = fechadoc;
	}

	@JsonProperty("fecvence")
	public String getFecvence() {
		return fecvence;
	}

	@JsonProperty("fecvence")
	public void setFecvence(String fecvence) {
		this.fecvence = fecvence;
	}

	@JsonProperty("fecasentado")
	public String getFecasentado() {
		return fecasentado;
	}

	@JsonProperty("fecasentado")
	public void setFecasentado(String fecasentado) {
		this.fecasentado = fecasentado;
	}

	@JsonProperty("fecanulado")
	public String getFecanulado() {
		return fecanulado;
	}

	@JsonProperty("fecanulado")
	public void setFecanulado(String fecanulado) {
		this.fecanulado = fecanulado;
	}

	@JsonProperty("formapago")
	public String getFormapago() {
		return formapago;
	}

	@JsonProperty("formapago")
	public void setFormapago(String formapago) {
		this.formapago = formapago;
	}

	@JsonProperty("nrodocref")
	public String getNrodocref() {
		return nrodocref;
	}

	@JsonProperty("nrodocref")
	public void setNrodocref(String nrodocref) {
		this.nrodocref = nrodocref;
	}

	@JsonProperty("importado")
	public String getImportado() {
		return importado;
	}

	@JsonProperty("importado")
	public void setImportado(String importado) {
		this.importado = importado;
	}

	@JsonProperty("terid1")
	public String getTerid1() {
		return terid1;
	}

	@JsonProperty("terid1")
	public void setTerid1(String terid1) {
		this.terid1 = terid1;
	}

	@JsonProperty("terid2")
	public String getTerid2() {
		return terid2;
	}

	@JsonProperty("terid2")
	public void setTerid2(String terid2) {
		this.terid2 = terid2;
	}

	@JsonProperty("terid3")
	public String getTerid3() {
		return terid3;
	}

	@JsonProperty("terid3")
	public void setTerid3(String terid3) {
		this.terid3 = terid3;
	}

	@JsonProperty("codigo1")
	public String getCodigo1() {
		return codigo1;
	}

	@JsonProperty("codigo1")
	public void setCodigo1(String codigo1) {
		this.codigo1 = codigo1;
	}

	@JsonProperty("codigo2")
	public String getCodigo2() {
		return codigo2;
	}

	@JsonProperty("codigo2")
	public void setCodigo2(String codigo2) {
		this.codigo2 = codigo2;
	}

	@JsonProperty("codigo3")
	public String getCodigo3() {
		return codigo3;
	}

	@JsonProperty("codigo3")
	public void setCodigo3(String codigo3) {
		this.codigo3 = codigo3;
	}

	@JsonProperty("observacion")
	public String getObservacion() {
		return observacion;
	}

	@JsonProperty("observacion")
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	@JsonProperty("observacion2")
	public Object getObservacion2() {
		return observacion2;
	}

	@JsonProperty("observacion2")
	public void setObservacion2(Object observacion2) {
		this.observacion2 = observacion2;
	}

	@JsonProperty("retefuente")
	public String getRetefuente() {
		return retefuente;
	}

	@JsonProperty("retefuente")
	public void setRetefuente(String retefuente) {
		this.retefuente = retefuente;
	}

	@JsonProperty("descuentogen")
	public String getDescuentogen() {
		return descuentogen;
	}

	@JsonProperty("descuentogen")
	public void setDescuentogen(String descuentogen) {
		this.descuentogen = descuentogen;
	}

	@JsonProperty("base")
	public String getBase() {
		return base;
	}

	@JsonProperty("base")
	public void setBase(String base) {
		this.base = base;
	}

	@JsonProperty("iva")
	public String getIva() {
		return iva;
	}

	@JsonProperty("iva")
	public void setIva(String iva) {
		this.iva = iva;
	}

	@JsonProperty("total")
	public String getTotal() {
		return total;
	}

	@JsonProperty("total")
	public void setTotal(String total) {
		this.total = total;
	}

	@JsonProperty("impreso")
	public String getImpreso() {
		return impreso;
	}

	@JsonProperty("impreso")
	public void setImpreso(String impreso) {
		this.impreso = impreso;
	}

	@JsonProperty("totalitems")
	public String getTotalitems() {
		return totalitems;
	}

	@JsonProperty("totalitems")
	public void setTotalitems(String totalitems) {
		this.totalitems = totalitems;
	}

	@JsonProperty("sucursalid")
	public String getSucursalid() {
		return sucursalid;
	}

	@JsonProperty("sucursalid")
	public void setSucursalid(String sucursalid) {
		this.sucursalid = sucursalid;
	}

	@JsonProperty("creador")
	public String getCreador() {
		return creador;
	}

	@JsonProperty("creador")
	public void setCreador(String creador) {
		this.creador = creador;
	}

	@JsonProperty("momento")
	public String getMomento() {
		return momento;
	}

	@JsonProperty("momento")
	public void setMomento(String momento) {
		this.momento = momento;
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