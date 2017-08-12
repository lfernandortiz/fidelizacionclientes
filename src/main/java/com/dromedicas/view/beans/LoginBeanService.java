package com.dromedicas.view.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

@ManagedBean(name = "loginService")
@SessionScoped
public class LoginBeanService {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = -2152389656664659476L;
	private String username;
	private String password;
	private boolean logeado = false;

	public boolean estaLogeado() {
		return logeado;
	}

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void login() {
		
		System.out.println("Usuario: " + this.getUsername());
		System.out.println("Password: " + this.getPassword());
		
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage msg = null;
		if (username != null && username.equals("admin") && password != null && password.equals("apolo11")) {
			logeado = true;
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", username);
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
