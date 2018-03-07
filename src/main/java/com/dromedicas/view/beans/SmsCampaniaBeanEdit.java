package com.dromedicas.view.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.dromedicas.domain.Campania;
import com.dromedicas.domain.Patologia;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.service.PatologiaService;
import com.dromedicas.service.SucursalService;

@ManagedBean(name="smsCampaniaBeanEdit")
@SessionScoped
public class SmsCampaniaBeanEdit {
	
	@EJB
	private SucursalService sucursalService;
	
	@EJB
	private PatologiaService patologiaSevice;
		
	
	private Campania campaniaSelected;
	private List<Sucursal> sucursalList;
	
	private String nombreCampania;
	private String audiencia;
	private String queryStringMain;
	private Date fechaInicio;
	private Date fechaFin;
	private String contenidoSms;
	
	private Sucursal sucursalSelected;
	private String sexo;
	private int edadIni = 18;
	private int edadFin = 50;
	private String[] selectedPatologias;
	private List<String> patologias;	

	private String hijosmenoresde4;
	private String hijosentre4y12;
	private String hijosentre13y18;
	private String hijosmayores;
	
	
	public SmsCampaniaBeanEdit(){
		
	}
	
	@PostConstruct
	public void init(){
		
		this.sucursalList = sucursalService.findAllSucursals();
		this.obtenerPatologias();
		
	}
	
	public Campania getCampaniaSelected() {
		return campaniaSelected;
	}

	public void setCampaniaSelected(Campania campaniaSelected) {
		this.campaniaSelected = campaniaSelected;
	}

	public String getNombreCampania() {
		return nombreCampania;
	}

	public void setNombreCampania(String nombreCampania) {
		this.nombreCampania = nombreCampania;
	}
		

	public String getAudiencia() {
		return audiencia;
	}

	public void setAudiencia(String audiencia) {
		this.audiencia = audiencia;
	}

	public String getQueryStringMain() {
		return queryStringMain;
	}

	public void setQueryStringMain(String queryStringMain) {
		this.queryStringMain = queryStringMain;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getContenidoSms() {
		return contenidoSms;
	}

	public void setContenidoSms(String contenidoSms) {
		this.contenidoSms = contenidoSms;
	}
	
	
	public Sucursal getSucursalSelected() {
		return sucursalSelected;
	}

	public void setSucursalSelected(Sucursal sucursalSelected) {
		this.sucursalSelected = sucursalSelected;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public int getEdadIni() {
		return edadIni;
	}

	public void setEdadIni(int edadIni) {
		this.edadIni = edadIni;
	}

	public int getEdadFin() {
		return edadFin;
	}

	public void setEdadFin(int edadFin) {
		this.edadFin = edadFin;
	}

	public String[] getSelectedPatologias() {
		return selectedPatologias;
	}

	public void setSelectedPatologias(String[] selectedPatologias) {
		this.selectedPatologias = selectedPatologias;
	}

	public List<String> getPatologias() {
		return patologias;
	}

	public void setPatologias(List<String> patologias) {
		this.patologias = patologias;
	}

	public String getHijosmenoresde4() {
		return hijosmenoresde4;
	}

	public void setHijosmenoresde4(String hijosmenoresde4) {
		this.hijosmenoresde4 = hijosmenoresde4;
	}

	public String getHijosentre4y12() {
		return hijosentre4y12;
	}

	public void setHijosentre4y12(String hijosentre4y12) {
		this.hijosentre4y12 = hijosentre4y12;
	}

	public String getHijosentre13y18() {
		return hijosentre13y18;
	}

	public void setHijosentre13y18(String hijosentre13y18) {
		this.hijosentre13y18 = hijosentre13y18;
	}

	public String getHijosmayores() {
		return hijosmayores;
	}

	public void setHijosmayores(String hijosmayores) {
		this.hijosmayores = hijosmayores;
	}

	public List<Sucursal> getSucursalList() {
		return sucursalList;
	}

	public void setSucursalList(List<Sucursal> sucursalList) {
		this.sucursalList = sucursalList;
	}
	
	
	/**
	 * Llena la coleccion de patologias
	 */
	public void obtenerPatologias(){
		
		List<Patologia> patologiaList = this.patologiaSevice.findAllPatologias();
		
		this.patologias = new ArrayList<String>();
		
		for(Patologia e : patologiaList ){
			
			System.out.println("Patologia: " +  e.getDrescripcion() );
			
			this.patologias.add( e.getDrescripcion() );
		}
	}
	
	
	

}
