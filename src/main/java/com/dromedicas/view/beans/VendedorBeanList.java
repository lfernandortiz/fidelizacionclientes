package com.dromedicas.view.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;

import com.dromedicas.domain.Vendedor;
import com.dromedicas.service.VendedorService;

@ManagedBean (name="vendedorBeanList")
@ViewScoped
public class VendedorBeanList {
	
	@EJB
	private VendedorService vService;
	
	private String nombreVendedor;
	private String documento;
	private List<Vendedor> vendedorList;
	
	public VendedorBeanList(){}
	
	@PostConstruct
	public void init(){	
		this.vendedorList = this.vService.findAllVendedors();
	}

	public String getNombreVendedor() {
		return nombreVendedor;
	}

	public void setNombreVendedor(String nombreVendedor) {
		this.nombreVendedor = nombreVendedor;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	public List<Vendedor> getVendedorList() {
		return vendedorList;
	}

	public void setVendedorList(List<Vendedor> vendedorList) {
		this.vendedorList = vendedorList;
	}
	
	/*
	 * Acciones del List
	 */		

	public void buscarVendedor(){
		this.vendedorList = 
				this.vService.buscarVendedorPorCampo(this.nombreVendedor, this.documento);
	}
	
	public void cancelarList() {
		// Limpiar todos los campos del formulario List
		this.setNombreVendedor("");
		this.setDocumento("");		

		// Reset DataTable Object (reestablecer la paginacion)
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("formppal:vendedorDetail");
		dataTable.reset();

		// Mandar a Listar nuevamente el List
		this.vendedorList = this.vService.findAllVendedors();
	}
	
	public String crearVendedor(){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("vendedorBeanEdit");
		return "vendedoredit?faces-redirect=true";
	}
	
	
	public String editarVendedor(){
		return "vendedoredit?faces-redirect=true";
	}
	
}
