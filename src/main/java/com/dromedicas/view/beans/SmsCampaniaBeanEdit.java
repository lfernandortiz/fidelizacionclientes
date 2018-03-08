package com.dromedicas.view.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Campania;
import com.dromedicas.domain.Patologia;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.service.PatologiaService;
import com.dromedicas.service.SucursalService;

@ManagedBean(name="smsCampaniaBeanEdit")
@SessionScoped
public class SmsCampaniaBeanEdit implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private SucursalService sucursalService;
	
	@EJB
	private PatologiaService patologiaSevice;
	
	private Campania campaniaSelected;
	private List<Sucursal> sucursalList;
	private List<Afiliado> afiliadoList;
	private Sucursal[] selectedSucursal;
	
	private String nombreCampania;
	private String audiencia;
	private String queryStringMain;
	private Date fechaInicio;
	private Date fechaFin;
	private String contenidoSms;
	private String calculoString;
	private String longiMensajeSMS;
	
	private Sucursal sucursalSelected;
	private String sexo;
	private int edadIni = 18;
	private int edadFin = 50;
	private String[] selectedHijos;
	
	private String[] selectedPatologias;
	private List<String> patologiasList;
	
	private String[] selectedCities2;
    private List<String> cities;

	private String hijosmenoresde4;
	private String hijosentre4y12;
	private String hijosentre13y18;
	private String hijosmayores;
	
	
	
	public SmsCampaniaBeanEdit(){
		
	}
	
	
	@PostConstruct
	public void init(){
		
		this.campaniaSelected = new Campania();	
		this.sucursalList = sucursalService.findAllSucursals();		
		//Calendar rightNow = Calendar.getInstance();
		//int hour = rightNow.get(Calendar.HOUR_OF_DAY);
		
		Date dateTemp = this.AddingHHToDate(new Date(), 1);
		this.setFechaInicio( dateTemp );
		System.out.println("FECHA: " + new SimpleDateFormat("dd/MM/yyyy HH").format(this.getFechaInicio()) );	
		
		
		List<Patologia> ptList = this.patologiaSevice.findAllPatologias();		
		this.cities = new ArrayList<String>();		
		for(Patologia e : ptList ){			
			this.cities.add( e.getDrescripcion() );
		}
		
		
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
	
	public List<String> getPatologiasList() {
		return patologiasList;
	}

	public void setPatologiasList(List<String> patologiasList) {
		this.patologiasList = patologiasList;
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
		
	public String[] getSelectedHijos() {
		return selectedHijos;
	}

	public void setSelectedHijos(String[] selectedHijos) {
		this.selectedHijos = selectedHijos;
	}
	
	public List<Afiliado> getAfiliadoList() {
		return afiliadoList;
	}

	public void setAfiliadoList(List<Afiliado> afiliadoList) {
		this.afiliadoList = afiliadoList;
	}
	
	public Sucursal[] getSelectedSucursal() {
		return selectedSucursal;
	}

	public void setSelectedSucursal(Sucursal[] selectedSucursal) {
		this.selectedSucursal = selectedSucursal;
	}
	
	public String getCalculoString() {
		return calculoString;
	}

	public void setCalculoString(String calculoString) {
		this.calculoString = calculoString;
	}

	public String getLongiMensajeSMS() {
		return longiMensajeSMS;
	}

	public void setLongiMensajeSMS(String longiMensajeSMS) {
		this.longiMensajeSMS = longiMensajeSMS;
	}
	
	public String[] getSelectedCities2() {
		return selectedCities2;
	}


	public void setSelectedCities2(String[] selectedCities2) {
		this.selectedCities2 = selectedCities2;
	}


	public List<String> getCities() {
		return cities;
	}


	public void setCities(List<String> cities) {
		this.cities = cities;
	}


	/**
	 * Llena la coleccion de patologias
	 */
	public void obtenerPatologias(){
		
		
	}
	
	public java.util.Date AddingHHToDate(java.util.Date date, int nombreHeure) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.HOUR_OF_DAY, nombreHeure);
	    return calendar.getTime();
	}	
	
	public void analizaSMS(){
		// La longitud maxima de caracteres a enviar por mensaje SMS es de 160 caracteres
		// segun el proveedor del servicio.
		
		// El metodo longitudMensaje elimina del mensaje las variables "${variable}"
		int longitud =  this.contenidoSms.length() ;
		int restante = 160 - longitud;
		if( longitud > 160){
			this.setLongiMensajeSMS(longitud  + " Mensaje muy extenso");
		}else{
			this.setLongiMensajeSMS( restante  + (restante == 1 ? " Caracter restante" : " Caracteres restantes") );
		}
	}
	
	
	public void calcularCampania(){
		System.out.println("Calculando....");
		
		try {			
			System.out.println("Nombre Campana: " + this.campaniaSelected.getNombrecampania().toUpperCase().trim() );
			System.out.println("Sucursales:");			
			for(Sucursal e:  this.getSelectedSucursal()){
				System.out.println("  - " + e.getNombreSucursal());
			}
			System.out.println("Sexo: " + this.getSexo());
			System.out.println("Entre edades: " +  this.edadIni + " y " + this.edadFin);
			
			
			
			System.out.println("Patologias:" + this.selectedCities2.length );
			
			for(String e:  this.selectedCities2 ){
				System.out.println("  -> " + e);
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}



	
	
	

}
