package com.dromedicas.view.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.dromedicas.domain.Afiliado;

@ManagedBean(name="afiliadoEdit")
@SessionScoped
public class AfiliadoBeanEdit {
	
	
	private Afiliado afiliadoSelected;
	
	public AfiliadoBeanEdit(){}//constructor por defecto 

	public Afiliado getAfiliadoSelected() {
		return afiliadoSelected;
	}

	public void setAfiliadoSelected(Afiliado afiliadoSelected) {
		this.afiliadoSelected = afiliadoSelected;
	}

}
