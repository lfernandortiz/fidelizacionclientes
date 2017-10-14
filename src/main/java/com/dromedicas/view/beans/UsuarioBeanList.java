package com.dromedicas.view.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DualListModel;

import com.dromedicas.domain.Rol;
import com.dromedicas.domain.Usuarioweb;
import com.dromedicas.service.RolService;
import com.dromedicas.service.UsuarioWebService;

@ManagedBean(name="usuarioBeanList")
@ViewScoped
public class UsuarioBeanList {
	
	@EJB
	private UsuarioWebService usuarioService;
	
	@EJB
	private RolService rolService;
	
	private String nombreUsuario;
	private List<Usuarioweb> usuarioList;
	
	public UsuarioBeanList(){}
	
	@PostConstruct
	public void init(){		
		
		this.usuarioList = this.usuarioService.findAllUsuariowebs();		
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public List<Usuarioweb> getUsuarioList() {
		return usuarioList;
	}

	public void setUsuarioList(List<Usuarioweb> usuarioList) {
		this.usuarioList = usuarioList;
	}
	
	
	
	
	
	
	/*
	 * Metodos de acciones de la vista List
	 */

	public String crearUsuario(){
		return "usuarioedit?faces-redirect=true";
	}
	
	public void cancelarList(){
		
	}
	
	public void buscarUsuarioList(){
		
	}
	
	public String editarUsuario(){
		return "usuarioedit?faces-redirect=true";
	}
	

}
