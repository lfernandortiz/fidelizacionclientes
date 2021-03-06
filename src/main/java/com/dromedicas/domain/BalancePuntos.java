package com.dromedicas.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@XmlRootElement
@JsonPropertyOrder({ "ganadostxactual", "acumulados","redimidos", "vencidos", "avencer","fechavencimiento", "disponiblesaredimir", "totalpuntosactual"})
public class BalancePuntos implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int acumulados;
	private int redimidos;
	private int vencidos;
	private int avencer;
	private String fechavencimiento;
	private int disponiblesaredimir;
	private int totalpuntosactual;
	private int ganadostxactual;

	
	
	public BalancePuntos (){}


	public int getAcumulados() {
		return acumulados;
	}


	public void setAcumulados(int acumulados) {
		this.acumulados = acumulados;
	}


	public int getRedimidos() {
		return redimidos;
	}


	public void setRedimidos(int redimidos) {
		this.redimidos = redimidos;
	}


	public int getVencidos() {
		return vencidos;
	}


	public void setVencidos(int vencidos) {		
		this.vencidos = vencidos;
	}


	public int getAvencer() {
		return avencer;
	}


	public void setAvencer(int avencer) {
		this.avencer = avencer;
	}


	public String getFechavencimiento() {
		return fechavencimiento;
	}


	public void setFechavencimiento(String fechavencimiento) {
		this.fechavencimiento = fechavencimiento;
	}


	public int getDisponiblesaredimir() {
		return disponiblesaredimir;
	}


	public void setDisponiblesaredimir(int disponiblesaredimir) {
		this.disponiblesaredimir = disponiblesaredimir;
	}


	
	public int getTotalpuntosactual() {
		return totalpuntosactual;
	}


	public void setTotalpuntosactual(int totalpuntosactual) {
		this.totalpuntosactual = totalpuntosactual;
	}


	public int getGanadostxactual() {
		return ganadostxactual;
	}


	public void setGanadostxactual(int ganadostxactual) {
		this.ganadostxactual = ganadostxactual;
	}
	
	

}
