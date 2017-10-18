package com.dromedicas.view.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.domain.Tipodocumento;
import com.dromedicas.service.AfiliadoService;
import com.dromedicas.service.SucursalService;
import com.dromedicas.service.TipoDocumentoService;

@ManagedBean(name="afiliadoBeanEdit")
@SessionScoped
public class AfiliadoBeanEdit {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private AfiliadoService afiliadoService;
	
	@EJB
	private TipoDocumentoService tipodocService;
	
	@EJB
	private SucursalService sucursalService;
	
	private Afiliado afiliadoSelected;
	private List<Tipodocumento> tipodocList; // list para select one menu tipodocumento
	private List<Sucursal> sucursalList;
	
	public AfiliadoBeanEdit(){
		
	}//constructor por defecto 

	@PostConstruct
	public void init(){
		this.afiliadoSelected = new Afiliado();
		this.tipodocList = tipodocService.findAllTipodocumento();
		this.sucursalList = sucursalService.findAllSucursals();
		
	}
	
	
	public List<Tipodocumento> getTipodocList() {
		return tipodocList;
	}

	public void setTipodocList(List<Tipodocumento> tipodocList) {
		this.tipodocList = tipodocList;
	}

	public Afiliado getAfiliadoSelected() {
		return afiliadoSelected;
	}

	public void setAfiliadoSelected(Afiliado afiliadoSelected) {
		this.afiliadoSelected = afiliadoSelected;
	}

	public List<Sucursal> getSucursalList() {
		return sucursalList;
	}

	public void setSucursalList(List<Sucursal> sucursalList) {
		this.sucursalList = sucursalList;
	}
	
	

}
