package com.dromedicas.view.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.dromedicas.domain.Tipodocumento;
import com.dromedicas.service.TipoDocumentoService;

@ManagedBean
@RequestScoped
@FacesConverter("tipoDocumentoConverter")
public class TipoDocumentoConverter implements Converter {
	
	@EJB
	private TipoDocumentoService service ;
 
	@Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try { 
            	  	
                System.out.println("****------" + service.obtenerTipodocumentoByIdString(value));                
                return service.obtenerTipodocumentoByIdString(value);
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
            return String.valueOf(((Tipodocumento) object).getIdtipodocumento());
        }
        else {
            return null;
        }
    }   
}
