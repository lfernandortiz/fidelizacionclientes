package com.dromedicas.view.beans;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.dromedicas.service.AfiliadoService;
import com.dromedicas.service.OperacionPuntosService;

@ManagedBean(name="dashboardBean")
@ViewScoped
public class DashboardBean {
	
	@EJB
	private OperacionPuntosService puntosService;
	
	@EJB
	private AfiliadoService afiliadoService;
	
	private String puntosAcumulados;
	private String puntosRedimidos;
	private String puntosAcumuladosTotal;
	private String totalAfiliados;
	private String totalEmailValidado;
	
	public DashboardBean(){
		
	}
	
	@PostConstruct
	public void init(){
		
	}


	public String getPuntosAcumulados() {
		NumberFormat nf = new DecimalFormat("#,###");
		//Desde el EJB de servicio de puntos obtiene el total de puntos 
		//Acumulados que pueden ser redimidos.
		return nf.format(this.puntosService.getTotalPuntosDisponibles());
	}

	public void setPuntosAcumulados(String puntosAcumulados) {
		this.puntosAcumulados = puntosAcumulados;
	}

	public String getPuntosRedimidos() {
		NumberFormat nf = new DecimalFormat("#,###");
		//Desde el EJB de servicio de puntos obtiene el total de puntos 
		//Redimidos
		return nf.format(this.puntosService.getTotalPuntosRedemidos());
	}

	public void setPuntosRedimidos(String puntosRedimidos) {
		this.puntosRedimidos = puntosRedimidos;
	}
	
	public String getPuntosAcumuladosTotal() {
		NumberFormat nf = new DecimalFormat("#,###");
		//Desde el EJB de servicio de puntos obtiene el total de puntos 
		//Redimidos por los afiliados
		return nf.format(this.puntosService.getTotalPuntosAcumulados());
	}

	public void setPuntosAcumuladosTotal(String puntosAcumuladosTotal) {
		this.puntosAcumuladosTotal = puntosAcumuladosTotal;
	}

	public String getTotalAfiliados() {
		NumberFormat nf = new DecimalFormat("#,###");
		//Desde el EJB de servicio de puntos obtiene el total de puntos 
		//Redimidos por los afiliados
		return nf.format(this.afiliadoService.totalAfiliados());
	}

	public void setTotalAfiliados(String totalAfiliados) {
		this.totalAfiliados = totalAfiliados;
	}

	public String getTotalEmailValidado() {
		NumberFormat nf = new DecimalFormat("#,###");
		//Desde el EJB de servicio de puntos obtiene el total de puntos 
		//Redimidos por los afiliados
		return nf.format(this.afiliadoService.totalAfiliadosCorreoValidado());
	}

	public void setTotalEmailValidado(String totalEmailValidado) {
		this.totalEmailValidado = totalEmailValidado;
	}

	
	

}
