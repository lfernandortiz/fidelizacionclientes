package com.dromedicas.service;

import java.text.SimpleDateFormat;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import com.dromedicas.domain.Afiliado;

@ManagedBean
@RequestScoped
public class AfiliadoService {
	
	@ManagedProperty("#{afiliado}")
	private Afiliado afiliado;
	
	private Integer r1;
	private Integer r2;

	public AfiliadoService() {
		
	}

	public Afiliado getPersona() {
		return afiliado;
	}

	public void setPersona(Afiliado afiliado) {
		this.afiliado = afiliado;
	}
		
	public Integer getR1() {
		return r1;
	}

	public void setR1(Integer r1) {
		this.r1 = r1;
	}

	public Integer getR2() {
		return r2;
	}

	public void setR2(Integer r2) {
		this.r2 = r2;
	}

	public String cambioPagina(){
		System.out.println("cambiando pagina");		
		return "data";
	}
	
	
	public String colocarValores(){
		
		System.out.println("Manged Bean----------");
		System.out.println("Nombres: " + this.getPersona().getNombres().trim().toUpperCase());
		System.out.println("Apellidos: " +  this.getPersona().getApellidos().trim().toUpperCase());
		System.out.println("Cedula: " + this.getPersona().getDocumento());
		System.out.println("Fecha: " + new SimpleDateFormat("dd/MM/yyy").format(this.getPersona().getFechanacimiento()) );
		
		System.out.println("Email:"+ this.getPersona().getEmail());
		System.out.println("Telefono:"+ this.getPersona().getTelefonofijo());
		System.out.println("Celular:"+ this.getPersona().getCelular());
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FacesContext.getCurrentInstance().addMessage("null", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro Exitoso!", "Afiliado Creado Correctamente."));
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Flash flash = facesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.setKeepMessages(true);
		return "dashboard?faces-redirect=true";	
		
	}

}
