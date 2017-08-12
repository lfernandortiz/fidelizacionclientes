package com.dromedicas.view.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.apache.log4j.Logger;

import com.dromedicas.domain.Contrato;
import com.dromedicas.domain.Empresa;
import com.dromedicas.service.EmpresaService;
import com.dromedicas.util.ExpresionesRegulares;

@ManagedBean(name="empresaBeanList")
@ViewScoped
public class EmpresaBeanList  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Empresa> empresaList;
	private Empresa selectedEmpresa = new Empresa();
	//campos para empresa
	private String nit;
	private String nombreEmpresa;
	private String direccion;
	private String telefono;
	private String emailNotificaciones;
	private String email;
	//campos para contrato
	private Date fechaInicio;
	private Date fechaFin;
	private int baseGravable;
	private int factorPuntos;
	private boolean envioSms;
	private boolean envioEmail;
	private boolean redensionSuc;
	
	private int idContratoSel;
	private int idEmpresaSel;
	
	
	Logger log = Logger.getLogger(EmpresaBeanList.class);
		
	@EJB
	private EmpresaService empEjb;	

	
	public EmpresaBeanList() {
	}
	

	@PostConstruct
	public void init() {
		System.out.println("Funcion PostConstruct");
		System.out.println("Datos id Empresesa Seleccionada: " + this.selectedEmpresa.getIdempresa());
		this.empresaList = empEjb.buscarEmpresas();		
	}
	

	public List<Empresa> getEmpresaList() {
		return empresaList;
	}
	

	public void setEmpresaList(List<Empresa> empresaList) {
		this.empresaList = empresaList;
	}	
	
	public Empresa getSelectedEmpresa() {
		return selectedEmpresa;
	}
	

	public void setSelectedEmpresa(Empresa selectedEmpresa) {
		this.selectedEmpresa = selectedEmpresa;
	}

	public String getNit() {
		return nit;
	}


	public void setNit(String nit) {
		this.nit = nit.trim();
	}


	public String getNombreEmpresa() {
		return nombreEmpresa;
	}


	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa.trim().toUpperCase();
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion.trim().toUpperCase();
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono.trim();
	}


	public String getEmailNotificaciones() {
		return emailNotificaciones;
	}


	public void setEmailNotificaciones(String emailNotificaciones) {
		this.emailNotificaciones = emailNotificaciones;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public Date getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public Date getFechaFin() {
		return fechaFin;
	}


	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}


	public int getBaseGravable() {
		return baseGravable;
	}


	public void setBaseGravable(int baseGravable) {
		this.baseGravable = baseGravable;
	}


	public int getFactorPuntos() {
		return factorPuntos;
	}


	public void setFactorPuntos(int factorPuntos) {
		this.factorPuntos = factorPuntos;
	}


	public boolean isEnvioSms() {
		return envioSms;
	}


	public void setEnvioSms(boolean envioSms) {
		this.envioSms = envioSms;
	}


	public boolean isEnvioEmail() {
		return envioEmail;
	}


	public void setEnvioEmail(boolean envioEmail) {
		this.envioEmail = envioEmail;
	}


	public boolean isRedensionSuc() {
		return redensionSuc;
	}


	public void setRedensionSuc(boolean redensionSuc) {
		this.redensionSuc = redensionSuc;
	}

	
	public int getIdContratoSel() {
		return idContratoSel;
	}


	public void setIdContratoSel(int idContratoSel) {
		this.idContratoSel = idContratoSel;
	}


	public int getIdEmpresaSel() {
		return idEmpresaSel;
	}


	public void setIdEmpresaSel(int idEmpresaSel) {
		this.idEmpresaSel = idEmpresaSel;
	}
		
	public String crear(){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("empresaBeanEdit");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("contrato");
		return "empresaedit?faces-redirect=true";
	}
	
	public void cancelarList(){
	
		this.setNit("");
		this.setNombreEmpresa("");
		this.setDireccion("");
		this.setTelefono("");
		this.setEmail("");
		this.setEmailNotificaciones("");		
		this.empresaList = empEjb.buscarEmpresas();		
	}
	
	
	public void buscarEmpresa(){
		this.empresaList = 
				this.empEjb.bucarEmpresaByFields(nit, nombreEmpresa, direccion, telefono);
	}
	

}
