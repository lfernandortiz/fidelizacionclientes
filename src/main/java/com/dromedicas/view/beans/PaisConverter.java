package com.dromedicas.view.beans;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.dromedicas.domain.Pais;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.service.PaisService;

@ManagedBean
@RequestScoped
@FacesConverter("paisConverter")
public class PaisConverter implements Converter {
	
	
		@EJB
		private PaisService service;
	 
		@Override
	    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
	        if(value != null && value.trim().length() > 0) {
	            try { 
	            	Pais pais = service.obtenerPaisById(value);            	
	                return pais;
	            } catch(NumberFormatException e) {
	                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid pais."));
	            }
	        }
	        else {
	            return null;
	        }
	    }
	 
		
		
		@Override
	    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
	        if(object != null) {    
	        	
	        	return String.valueOf(  ((Pais) object).getIdpaises()  );
	        }
	        else {
	            return null;
	        }
	    }   
		
	
	

}
