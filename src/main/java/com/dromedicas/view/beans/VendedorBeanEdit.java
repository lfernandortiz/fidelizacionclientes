package com.dromedicas.view.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import com.dromedicas.domain.Empresa;
import com.dromedicas.domain.Vendedor;
import com.dromedicas.service.EmpresaService;
import com.dromedicas.service.VendedorService;

@ManagedBean(name="vendedorBeanEdit")
@SessionScoped
public class VendedorBeanEdit {
	
	@EJB
	private EmpresaService empServicio;
	
	@EJB
	private VendedorService venService;
	
	private Vendedor venSelected;
	private List<Empresa> empresList;
	private boolean activo;
	private boolean existe = false;
	
	public VendedorBeanEdit(){
		
	}
	
	@PostConstruct
	public void init(){
		this.empresList = this.empServicio.buscarEmpresas();
		this.venSelected = new Vendedor();
	}
	
	public Vendedor getVenSelected() {
		return venSelected;
	}

	public void setVenSelected(Vendedor venSelected) {
		this.venSelected = venSelected;
	}

	public List<Empresa> getEmpresList() {
		return empresList;
	}

	public void setEmpresList(List<Empresa> empresList) {
		this.empresList = empresList;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	public boolean isExiste() {
		return existe;
	}

	public void setExiste(boolean existe) {
		this.existe = existe;
	}
	
	
	
	/*
	 * Operaciones  
	 */
	public String editarVendedor(){
		this.activo = (this.venSelected.getActvo() == 1 ? true : false);
		return "vendedoredit";
		
	}
	

	public String crearVendedor(){
		
		System.out.println("Creando vendedor....");
		
		Vendedor documentTemp = this.venService.obtenerVendedorById(this.venSelected);
		
		if(documentTemp == null){
			
			//Establece los valores con la informacion consistente
			this.venSelected.setDocuvendedor(this.venSelected.getDocuvendedor().trim());
			Empresa emp = this.empServicio.obtenerEmpresaById(this.venSelected.getEmpresa());
			this.venSelected.setEmpresa(emp);
			this.venSelected.setNombres(this.venSelected.getNombres().trim().toUpperCase());
			this.venSelected.setApellidos(this.venSelected.getApellidos().trim().toUpperCase());
			
			byte temp = (byte) (this.activo == true ? 1 : 0);
			this.venSelected.setActvo(temp);
			System.out.println("-----" + this.venSelected);
			//se persiste el nuevo objeto 
			this.venService.insertVendedor(this.venSelected);
			
			//se despliegan los mensajes y se redirecciona
			FacesContext.getCurrentInstance().addMessage("globalMessagex", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Registro Exitoso!", "Vendedor " + this.venSelected.getNombres()+ " fue creado correctamente."));
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Flash flash = facesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.setKeepMessages(true);
			this.existe = false;
			return "vendedorlist?faces-redirect=true";
			
		}else{		
			this.existe = true;
			FacesContext.getCurrentInstance().addMessage("crearvendedormessage", 
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Este Numero de Cedula ya esta registrado."));
			
			
			System.out.println("El Documento ya existe: ");
			
			return null;			
		}
		
		
	}
	
	public String actualizarVendedor(){
		//Establece los valores con la informacion consistente
		this.venSelected.setDocuvendedor(this.venSelected.getDocuvendedor().trim());
		Empresa emp = this.empServicio.obtenerEmpresaById(this.venSelected.getEmpresa());
		this.venSelected.setEmpresa(emp);
		this.venSelected.setNombres(this.venSelected.getNombres().trim().toUpperCase());
		this.venSelected.setApellidos(this.venSelected.getApellidos().trim().toUpperCase());
		
		byte temp = (byte) (this.activo == true ? 1 : 0);
		this.venSelected.setActvo(temp);
		System.out.println("-----" + this.venSelected);
		//se persiste el nuevo objeto 
		this.venService.updateVendedor(this.venSelected);
		
		//se despliegan los mensajes y se redirecciona
		FacesContext.getCurrentInstance().addMessage("globalMessagex", new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Actualizacion Exitosa!", "Vendedor " + this.venSelected.getNombres()+ " fue actualizado correctamente."));
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Flash flash = facesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.setKeepMessages(true);
		this.existe = false;
		return "vendedorlist?faces-redirect=true";
	}
	
	public String cancelarEdit(){
		return "vendedorlist?faces-redirect=true";
	}
	

}
