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
	private int disponiblesaredimir;
	private int acumuladosnodisponibles;
	
	
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


	public int getAcumuladosnodisponibles() {
		return acumuladosnodisponibles;
	}


	public void setAcumuladosnodisponibles(int acumuladosnodisponibles) {
		this.acumuladosnodisponibles = acumuladosnodisponibles;
	}
	
	
	

}
