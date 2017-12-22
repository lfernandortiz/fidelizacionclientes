package com.dromedicas.view.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.dromedicas.domain.Smsplantilla;
import com.dromedicas.service.SmsPlantillaService;

@ManagedBean(name="plantillaSmsBeanList")
@ViewScoped
public class PlantillaSmsBeanList {
	
	@EJB
	private SmsPlantillaService smsService;
	
	private List<Smsplantilla> smsPlantillaList;
	private Smsplantilla smsPlantillaSelected;


	public PlantillaSmsBeanList(){}
	
	
	@PostConstruct
	public void init(){
		System.out.println("Function PostConstruct SMS LIST");	
		smsPlantillaSelected = new Smsplantilla();
		this.smsPlantillaList = this.smsService.findAllSmsplantillas();
		
	}
	
	public List<Smsplantilla> getSmsPlantillaList() {
		return smsPlantillaList;
	}


	public void setSmsPlantillaList(List<Smsplantilla> smsPlantillaList) {
		this.smsPlantillaList = smsPlantillaList;
	}
	
	
	public Smsplantilla getSmsPlantillaSelected() {
		return smsPlantillaSelected;
	}


	public void setSmsPlantillaSelected(Smsplantilla smsPlantillaSelected) {
		this.smsPlantillaSelected = smsPlantillaSelected;
	}


	/**
	 * Metodos de la vista
	 */
	
	public void buscarPlantillaSMS(){
		
	}
	
	public String crearPlantillaSMS(){
		return "";
	}
	
	public String editarPlantillaSMS(){
		return "";
	}
	
	public void cancelarPlantillaSmsList(){
		
	}

}
