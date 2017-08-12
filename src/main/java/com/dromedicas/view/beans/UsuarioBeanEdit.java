package com.dromedicas.view.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DualListModel;

import com.dromedicas.domain.Rol;
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

	public String actualizarUsuario(){
		return null;
	}
	
	public String crearUsuario(){
		//primero crea el usuario y el tipo de usuario
		
		return null;
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
