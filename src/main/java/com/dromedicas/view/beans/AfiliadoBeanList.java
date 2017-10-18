package com.dromedicas.view.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.service.AfiliadoService;

@ManagedBean(name="afiliadoBeanList")
@ViewScoped
public class AfiliadoBeanList {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private AfiliadoService afiliadoService; 
	
	private List<Afiliado> afiliadoList;
	private String valorABuscar; // permite buscar por cedula, nombre, apellido, email.
	
	public AfiliadoBeanList(){}// constructor por defecto
	
	@PostConstruct
	public void init(){
		System.out.println("Function PostConstruct");
		this.afiliadoList = afiliadoService.findAllAfiliados();		
	}

	public List<Afiliado> getAfiliadoList() {
		return afiliadoList;
	}

	public void setAfiliadoList(List<Afiliado> afiliadoList) {
		this.afiliadoList = afiliadoList;
	}

	public String getValorABuscar() {
		return valorABuscar;
	}

	public void setValorABuscar(String valorABuscar) {
		this.valorABuscar = valorABuscar;
	}
	
	//metodos para botones del list
	
	public void cancelarList() {
		//Reset campos formulario de busqueda
		this.setValorABuscar("");

		// Reset DataTable Object (reestablecer la paginacion)
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("formppal:sucursalDetail");
		dataTable.reset();
		
		//Consulta nuevamente el List
		this.afiliadoList = this.afiliadoService.findAllAfiliados();

	}
	
	public String crearAfiliado(){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("afiliadoBeanEdit");
		return "afiliadocrear?faces-redirect=true";	
	}
	
	public void buscarAfiliado(){
		this.afiliadoList = afiliadoService.bucarAfiliadoByFields(valorABuscar);
	}
	
	public String editarAfiliado(){
		return "afiliadoedit?faces-redirect=true";
	}
	
	
}
