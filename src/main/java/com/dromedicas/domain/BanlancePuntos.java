package com.dromedicas.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BanlancePuntos implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int acumulados;
	private int redimidos;
	private int vencidos;
	private int avencer;
	private String fechavencimiento;
	private int disponibles;
	
	
	public BanlancePuntos (){}


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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 
		
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


	public int getDisponibles() {
		return disponibles;
	}


	public void setDisponibles(int disponibles) {
		this.disponibles = disponibles;
	}
	
	

}
