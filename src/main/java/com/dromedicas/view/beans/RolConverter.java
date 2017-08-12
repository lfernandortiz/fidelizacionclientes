package com.dromedicas.view.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import com.dromedicas.domain.Rol;
import com.dromedicas.service.RolService;

@ManagedBean
@RequestScoped
@FacesConverter("rolConverter")
public class RolConverter implements Converter{
	
	@EJB
	private RolService service;
 
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		return getObjectFromUIPickListComponent(component,value);
	}
 
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		String string;
		
		if(object == null){
			string="";
		}else{
			try{
				string = String.valueOf( ((Rol) object).getIdrol() );
			}catch(ClassCastException cce){
				throw new ConverterException();
			}
		}
		return string;
	}
 
	@SuppressWarnings("unchecked")
	private Rol getObjectFromUIPickListComponent(UIComponent component, String value) {
		final DualListModel<Rol> dualList;
		try{
			dualList = (DualListModel<Rol>) ((PickList)component).getValue();
			Rol rol = getObjectFromList(dualList.getSource(),Integer.valueOf(value));
			if(rol==null){
				rol = getObjectFromList(dualList.getTarget(),Integer.valueOf(value));
			}			
			return rol;
		}catch(ClassCastException cce){
			throw new ConverterException();
		}catch(NumberFormatException nfe){
			throw new ConverterException();
		}
	}
 
	private Rol getObjectFromList(final List<?> list, final Integer identifier) {
		for(final Object object : list){
			final Rol rol = (Rol) object;
			if(rol.getIdrol() == identifier){
				return rol;
			}
		}
		return null;
	}  

}
