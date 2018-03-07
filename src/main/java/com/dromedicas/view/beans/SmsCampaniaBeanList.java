package com.dromedicas.view.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.dromedicas.domain.Campania;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.service.CampaniaService;
import com.dromedicas.service.SmsCampaniaService;

@ManagedBean(name="smsCampaniaBeanList")
@ViewScoped
public class SmsCampaniaBeanList implements Serializable {	
	
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@EJB
	private SmsCampaniaService smsCampaniaService;
	
	@EJB
	private CampaniaService campaniaService;
	
	private List<Campania> campaniaSMSList;
	private String nombreCampania;
		
			
	@PostConstruct
	public void init(){
				
	}

	public List<Campania> getCampaniaSMSList() {
		return campaniaSMSList;
	}

	public void setCampaniaSMSList(List<Campania> campaniaSMSList) {
		this.campaniaSMSList = campaniaSMSList;
	}

	public String getNombreCampania() {
		return nombreCampania;
	}

	public void setNombreCampania(String nombreCampania) {
		this.nombreCampania = nombreCampania;
	}

	// ======================================
    // =       Metodos de la vista          =
    // ======================================
	public void cancelarList(){
		
	}
	
	
	public String crearSMSCampania(){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("smsCampaniaBeanEdit");
		return "smscampaniaedit?faces-redirect=true";	
	}
	
	
	
	
	public void buscarCampania(){
		
	}
	

}
