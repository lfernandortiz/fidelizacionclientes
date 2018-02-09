package com.dromedicas.view.beans;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.dromedicas.domain.Empresa;
import com.dromedicas.domain.Usuarioweb;
import com.dromedicas.service.UsuarioWebService;
import com.dromedicas.util.ExpresionesRegulares;

@ManagedBean(name = "loginService")
@SessionScoped
public class LoginBeanService implements Serializable {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = -2152389656664659476L;
	private String username;
	private String password;
	private boolean logeado = false;
	private Usuarioweb user;	
	
	private String NombreUsuario;
	
	//Falta carga de roles
	
	@EJB
	private UsuarioWebService usuarioService;
	
	@EJB
	private ExpresionesRegulares rgx;
	

	public boolean estaLogeado() {
		return logeado;
	}

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username.toUpperCase();
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password.toUpperCase();
	}
	
	public String getNombreUsuario() {
		return NombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		NombreUsuario = nombreUsuario;
	}
	
	public Usuarioweb getUser() {
		return user;
	}

	public void setUser(Usuarioweb user) {
		this.user = user;
	}

	@SuppressWarnings("unused")
	public void login() {
		
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage msg = null;				
		
		//Valida que el usuario exista obteniendo su instancia
		this.user = this.usuarioService.validarUsuario(this.getUsername(), this.getPassword());
				
		if (this.user != null) {
			logeado = true;
			this.setNombreUsuario( this.rgx.puntoSegundoNombre( 
										this.rgx.nombrePropio(user.getNombreusuario(), true)) );
			this.user.setUltacceso(new Date());
			//registra el auditor para el usuario
			this.usuarioService.updateUsuarioweb(user);
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", this.getNombreUsuario());
		} else {
			logeado = false;
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Credenciales no válidas");
		}

		FacesContext.getCurrentInstance().addMessage(null, msg);
		context.addCallbackParam("estaLogeado", logeado);
		if (logeado)
			context.addCallbackParam("view", "afiliadolist.xhtml");		
	}
	
	
	public String logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.invalidate();
		logeado = false;
		return "login";
	}

}
