package com.dromedicas.view.beans;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.dromedicas.domain.Usuarioweb;
import com.dromedicas.service.UsuarioWebService;
import com.dromedicas.util.ExpresionesRegulares;

@ManagedBean(name = "loginService")
@SessionScoped
public class LoginBeanService {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = -2152389656664659476L;
	private String username;
	private String password;
	private boolean logeado = false;
	
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

	@SuppressWarnings("unused")
	public void login() {
		
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage msg = null;				
		//Aca proceso de busqueda de credenciales enla base de datos
		
		Usuarioweb user = this.usuarioService.validarUsuario(this.getUsername(), this.getPassword());
				
		if (user != null) {
			logeado = true;
			this.setNombreUsuario( this.rgx.puntoSegundoNombre( 
										this.rgx.nombrePropio(user.getNombreusuario(), true)) );
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", this.getNombreUsuario());
		} else {
			logeado = false;
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Credenciales no válidas");
		}

		FacesContext.getCurrentInstance().addMessage(null, msg);
		context.addCallbackParam("estaLogeado", logeado);
		if (logeado)
			context.addCallbackParam("view", "dashboard.xhtml");		
	}
	
	
	public String logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.invalidate();
		logeado = false;
		return "login";
	}

}
