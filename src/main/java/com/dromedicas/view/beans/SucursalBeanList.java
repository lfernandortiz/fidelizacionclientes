package com.dromedicas.view.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;

import com.dromedicas.domain.Sucursal;
import com.dromedicas.service.SucursalService;

@ManagedBean(name="sucursalbenlist")
@ViewScoped
public class SucursalBeanList implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private SucursalService sucService;
	
	private List<Sucursal> sucursalList;
	private String nombre;
	private String empresa;
	
	/**
	 * Constructor por defecto
	 */
	public SucursalBeanList(){}
	
	@PostConstruct
	public void init(){
		this.sucursalList = sucService.findAllSucursals();		
	}	

	public List<Sucursal> getSucursalList() {
		return sucursalList;
	}

	public void setSucursalList(List<Sucursal> sucursalList) {
		this.sucursalList = sucursalList;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	
	public String crearSucursal(){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("sucursalEdit");
		return "sucursaledit?faces-redirect=true";
	}
	
	public void buscarSucursal(){
		this.sucursalList = sucService.bucarSucursalByFields(this.nombre, this.empresa);
	}
	
	public void cancelarList(){
		
		// Limpiar todos los campos del formulario List
		
		this.setEmpresa("");
		this.setNombre("");

		// Reset DataTable Object (reestablecer la paginacion)
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("formppal:sucursalDetail");
		dataTable.reset();

		// Mandar a Listar nuevamente el List
		this.sucursalList = this.sucService.findAllSucursals();
		
	}
	
	public String editarSucursal(){
		return "sucursaledit";
	}
	

}
