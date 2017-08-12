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

import com.dromedicas.domain.Empresa;
import com.dromedicas.service.EmpresaService;


@ManagedBean
@RequestScoped
@FacesConverter("sucursalConverter")
public class SucursalConverter implements Converter {
	
	@EJB
	private EmpresaService service;
 
	@Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try { 
            	Empresa emp = service.obtenerEmpresaById(value);
            	
                return emp;
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        else {
            return null;
        }
    }
 
	
	
	@Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {    
        	return String.valueOf( ((Empresa) object).getIdempresa()    );
        }
        else {
            return null;
        }
    }   
}