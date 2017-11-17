package com.dromedicas.view.beans;

import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import com.dromedicas.domain.Empresa;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.service.EmpresaService;
import com.dromedicas.service.SucursalService;

@ManagedBean(name="sucursalEdit")
@SessionScoped
public class SucursalBeanEdit {
	
	@EJB
	private SucursalService sucServicio;
	
	@EJB
	private EmpresaService empServicio;
	
	private Sucursal sucSelected;
	private List<Empresa> empresList;
	
	public SucursalBeanEdit(){}		
	
	@PostConstruct
	public void init(){
		this.sucSelected = new Sucursal();
		this.empresList = empServicio.buscarEmpresas();
	}

	public Sucursal getSucSelected() {
		return sucSelected;
	}

	public void setSucSelected(Sucursal sucSelected) {
		this.sucSelected = sucSelected;
	}
	
	public List<Empresa> getEmpresList() {
		return empresList;
	}

	public void setEmpresList(List<Empresa> empresList) {
		this.empresList = empresList;
	}
	
	
	/*
	 * Metodos CRUD
	 */
	
	public String crearSucursal(){
		
		try {
			this.sucSelected.setNombreSucursal(this.sucSelected.getNombreSucursal().trim().toUpperCase());
			this.sucSelected.setDireccion(this.sucSelected.getDireccion().trim().toUpperCase());
			this.sucSelected.setEmail(this.sucSelected.getEmail().trim());
			this.sucSelected.setCodigointerno(this.sucSelected.getCodigointerno().trim());
			this.sucSelected.setApikey(this.sucSelected.getApikey().trim());
			
			Empresa emp = this.empServicio.obtenerEmpresaById(this.sucSelected.getEmpresa());
			this.sucSelected.setEmpresa(emp);
			
			this.sucServicio.insertSucursal(this.sucSelected);
			
			
			System.out.println("----> " + this.sucSelected.getEmpresa().getNombreEmpresa());
			
			FacesContext.getCurrentInstance().addMessage("globalMessagex", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Registro Exitoso!", "Sucursal " + this.sucSelected.getNombreSucursal() + " fue creada correctamente."));
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Flash flash = facesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.setKeepMessages(true);
			
			return "sucursallist?faces-redirect=true";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("globalMessagex", new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Error al Registrar Sucursal!", "No se pudo registrar la sucursal " + this.sucSelected.getNombreSucursal() + ""));
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Flash flash = facesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.setKeepMessages(true);
			
			return "null";
		}
	}
	
	public String actualizarSucursal(){
		try {
			this.sucSelected.setNombreSucursal(this.sucSelected.getNombreSucursal().trim().toUpperCase());
			this.sucSelected.setDireccion(this.sucSelected.getDireccion().trim().toUpperCase());
			this.sucSelected.setEmail(this.sucSelected.getEmail().trim());
			this.sucSelected.setCodigointerno(this.sucSelected.getCodigointerno().trim());
			this.sucSelected.setApikey(this.sucSelected.getApikey().trim());
			
			Empresa emp = this.empServicio.obtenerEmpresaById(this.sucSelected.getEmpresa());
			this.sucSelected.setEmpresa(emp);
			
			this.sucServicio.updateSucursal(this.sucSelected);
			
			
			System.out.println("----> " + this.sucSelected.getEmpresa().getNombreEmpresa());
			
			FacesContext.getCurrentInstance().addMessage("globalMessagex", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Actualizacion Exitosa!", "Sucursal " + this.sucSelected.getNombreSucursal() + " fue actualizado correctamente."));
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Flash flash = facesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.setKeepMessages(true);
			
			return "sucursallist?faces-redirect=true";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("globalMessagex", new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Error al Registrar Sucursal!", "No se pudo actualizar la sucursal " + this.sucSelected.getNombreSucursal() + ""));
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Flash flash = facesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.setKeepMessages(true);
			
			return "null";
		}
	}
	
	public String cancelarEdit(){
		return "sucursallist?faces-redirect=true";
	}
	
	public void generarCodigoApi(){
		UUID uniqueKey = UUID.randomUUID();
		this.sucSelected.setApikey(uniqueKey.toString().replace("-", ""));
		System.out.println(uniqueKey);
	}
	
	
	public String eliminar(){
		Sucursal suc = this.sucServicio.obtenerSucursalById(this.sucSelected);
		System.out.println("Sucursal a eliminar: " + suc.getNombreSucursal() );
		
		this.sucServicio.deleteSucursal(suc);
		
		FacesContext.getCurrentInstance().addMessage("globalMessagex", new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Eliminacion Exitosa!", "Sucursal " + this.sucSelected.getNombreSucursal() + " fue Eliminado correctamente."));
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Flash flash = facesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.setKeepMessages(true);
		
		return "sucursallist?faces-redirect=true";
		
	}

}
