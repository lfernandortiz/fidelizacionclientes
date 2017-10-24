package com.dromedicas.view.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.model.DualListModel;

import com.dromedicas.domain.Rol;
import com.dromedicas.domain.Tipousuario;
import com.dromedicas.domain.Usuarioweb;
import com.dromedicas.service.RolService;
import com.dromedicas.service.TipoUsuarioService;
import com.dromedicas.service.UsuarioWebService;

@ManagedBean(name="usuarioBeanEdit")
@SessionScoped
public class UsuarioBeanEdit {

	@EJB
	private RolService rolService;
	
	@EJB
	private UsuarioWebService uwebService;
	
	@EJB
	private TipoUsuarioService tipUService;
	
		
	private Usuarioweb usurioSelected;
	private String pwd2;
	private DualListModel<Rol> roles;
	private Boolean activo;
	
	public UsuarioBeanEdit(){}
	
	@PostConstruct
	public void init(){
		List<Rol> rolSource = new ArrayList<Rol>();
		List<Rol> rolTarget = new ArrayList<Rol>();
		this.usurioSelected =  new Usuarioweb();
		
		rolSource = rolService.findAllRols(); 
		rolTarget = new ArrayList<Rol>();
		roles = new DualListModel<Rol>(rolSource, rolTarget); 
		
	}

	public Usuarioweb getUsurioSelected() {
		return usurioSelected;
	}
 
	public void setUsurioSelected(Usuarioweb usurioSelected) {
		this.usurioSelected = usurioSelected;
	}
	
	public String getPwd2() {
		return pwd2;
	}

	public void setPwd2(String pwd2) {
		this.pwd2 = pwd2;
	}
	
	public DualListModel<Rol> getRoles() {
		return roles;
	}

	public void setRoles(DualListModel<Rol> roles) {
		this.roles = roles;
	}
	
	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	
	
	/*
	 * Metodos de acciones de la vista usuarioEdit
	 * 
	 */	
	
	public String editarUsuario(Usuarioweb instance){
		this.activo = instance.getActivo() == 1 ? true : false;
		return "usuarioedit?faces-redirect=true";
	}

	public String actualizarUsuario(){
		this.usurioSelected.setNombreusuario(this.usurioSelected.getNombreusuario().trim().toUpperCase());
		this.usurioSelected.setUsuario(this.usurioSelected.getUsuario().trim().toUpperCase());
		this.usurioSelected.setClave(this.usurioSelected.getClave());
		byte temp = (byte) (this.activo== true ? 1 : 0);
		this.usurioSelected.setActivo(temp);
		this.usurioSelected.setEmailusuario(this.usurioSelected.getEmailusuario());
		
		Tipousuario tipoUsuario = this.tipUService.obtenerTipousuarioById(1);
		
		this.usurioSelected.setTipousuario(tipoUsuario);
		
		this.uwebService.updateUsuarioweb(this.usurioSelected);
		
		FacesContext.getCurrentInstance().addMessage("globalMessagex", new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Actualizacion Exitosa!", "Usuario actualizado Exitosamente"));
		FacesContext facesContext = FacesContext.getCurrentInstance();
		@SuppressWarnings("static-access")
		Flash flash = facesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.setKeepMessages(true);
		return "usuariolist?faces-redirect=true";
	}
	
	public String crearUsuario(){
		//primero crea el usuario y el tipo de usuario
		this.usurioSelected.setNombreusuario(this.usurioSelected.getNombreusuario().trim().toUpperCase());
		this.usurioSelected.setUsuario(this.usurioSelected.getUsuario().trim().toUpperCase());
		this.usurioSelected.setClave(this.usurioSelected.getClave());
		byte temp = (byte) (this.activo== true ? 1 : 0);
		this.usurioSelected.setActivo(temp);
		this.usurioSelected.setEmailusuario(this.usurioSelected.getEmailusuario());
		
		Tipousuario tipoUsuario = this.tipUService.obtenerTipousuarioById(1);
		
		this.usurioSelected.setTipousuario(tipoUsuario);
		
		this.uwebService.updateUsuarioweb(this.usurioSelected);
		
		FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Registro Exitoso!", "Usuario creado Exitosamente"));
		FacesContext facesContext = FacesContext.getCurrentInstance();
		@SuppressWarnings("static-access")
		Flash flash = facesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.setKeepMessages(true);
		
		return "usuariolist?faces-redirect=true";
	}
	
	public String cancelarEdit(){
		
		return "usuariolist?faces-redirect=true";
	}
	
	public void asignarRoles(){
		
		System.out.println("Asignando los siguientes Roles al usuario..");
		try {
			System.out.println("Tamanio arreglo: " + roles.getTarget().size());
			for(Rol e: roles.getTarget()){
				System.out.println("--->" + e.getNombrerol());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
}
