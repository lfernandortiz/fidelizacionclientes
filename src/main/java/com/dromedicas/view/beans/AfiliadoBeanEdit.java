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
	
	private String street1 = "AVENIDA";
	private String street1Valor = "";
	private String street2 = "No.";
	private String street2Valor = "";
	private String direccionCompleta;
	
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

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet1Valor() {
		return street1Valor;
	}

	public void setStreet1Valor(String street1Valor) {
		this.street1Valor = street1Valor;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getStreet2Valor() {
		return street2Valor;
	}

	public void setStreet2Valor(String street2Valor) {
		this.street2Valor = street2Valor;
	}
	
	public String getDireccionCompleta() {
		return direccionCompleta;
	}

	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}
	
	//metodos de control de la interfaz
	
	
	public void concatenarDireccion(){
		StringBuilder str = new StringBuilder();
		str.append(this.getStreet1().toUpperCase()).append(" ").append(this.getStreet1Valor().trim().toUpperCase()).append(" ")
					.append(this.getStreet2().toUpperCase()).append(" ").append(this.getStreet2Valor().trim().toUpperCase());
		this.setDireccionCompleta(str.toString());
		System.out.println(this.direccionCompleta);
	}
	
	public String crearAfiliado(){
		return null;
	}
	
	public String cancelarAfiliado(){
		return null;
	}
	
	public String volverListAfiliado(){
		return "afiliadolist?faces-redirect=true";
	}
	

}
