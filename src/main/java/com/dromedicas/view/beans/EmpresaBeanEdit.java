package com.dromedicas.view.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.apache.log4j.Logger;

import com.dromedicas.domain.Contrato;
import com.dromedicas.domain.Empresa;
import com.dromedicas.service.EmpresaService;
import com.dromedicas.util.ExpresionesRegulares;

@ManagedBean(name="empresaBeanEdit")
@SessionScoped
public class EmpresaBeanEdit implements Serializable {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	EmpresaService empEjb;

	@ManagedProperty(value = "#{empresa}")
	private Empresa empresa;
	
	@ManagedProperty(value ="#{contrato}")
	private Contrato contrato;
	
	@ManagedProperty(value ="#{regex}")
	private ExpresionesRegulares ex;
	
	Logger log = Logger.getLogger(EmpresaBeanList.class);
	
	private Empresa selectedEmpresa;
	private boolean envioSms;
	private boolean envioEmail;
	private boolean redensionSuc;
	private int idContratoSel;
	private int idEmpresaSel;
	
	
	public EmpresaBeanEdit(){}
	
	@PostConstruct
	public void init(){	
		this.selectedEmpresa = new Empresa();
		
	}

	public Empresa getSelectedEmpresa() {
		return selectedEmpresa;
	}

	public void setSelectedEmpresa(Empresa selectedEmpresa) {
		this.selectedEmpresa = selectedEmpresa;
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
	
	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public ExpresionesRegulares getEx() {
		return ex;
	}

	public void setEx(ExpresionesRegulares ex) {
		this.ex = ex;
	}
	
	public void limpiarEdit(){
		this.selectedEmpresa.setNit("");
		this.selectedEmpresa.setNombreEmpresa("");
		this.selectedEmpresa.setDireccion("");
		this.selectedEmpresa.setTelefono("");
		this.selectedEmpresa.setEmailNotificaciones("");
		this.selectedEmpresa.setEmail("");
		this.contrato.setFechainicio(null);
		this.contrato.setFechafin(null);
		this.contrato.setBasegravable(0);
		this.contrato.setFactorpuntos(0);
		this.contrato.setEnviosms((byte) 0);
		this.contrato.setEnvioemail((byte) 0);
		this.contrato.setRedensionsucursales((byte) 0);
	}

	public String editarRegistro(Empresa instance){
		
		this.selectedEmpresa = instance;
		System.out.println("Empresa Recibida: " + this.selectedEmpresa.getNombreEmpresa());
		System.out.println("Empresa ID: " + this.selectedEmpresa.getIdempresa());
		this.contrato = empEjb.obtenerUltimoContrato(this.selectedEmpresa);
		this.envioSms =	this.contrato.getEnviosms() == 1 ? true : false;
		this.envioEmail = this.contrato.getEnvioemail() == 1 ? true : false;
		this.redensionSuc = this.contrato.getRedensionsucursales() == 1 ? true : false;
		
		
		this.setIdEmpresaSel(this.selectedEmpresa.getIdempresa());
		this.setIdContratoSel(this.contrato.getIdcontrato());		
		
		return "empresaedit?faces-redirect=true";
	}
	
	
	public String actualizarEmpresa(){
		System.out.println("Actualizando empresa.....");
		this.selectedEmpresa.setIdempresa(this.selectedEmpresa.getIdempresa());
		System.out.println("Nit----:" + this.selectedEmpresa.getNit());
		this.selectedEmpresa.setNit(this.selectedEmpresa.getNit());
		this.selectedEmpresa.setNombreEmpresa(this.selectedEmpresa.getNombreEmpresa().trim().toUpperCase());
		this.selectedEmpresa.setDireccion(this.selectedEmpresa.getDireccion().trim().toUpperCase());
		this.selectedEmpresa.setTelefono(this.selectedEmpresa.getTelefono().trim());
		this.selectedEmpresa.setEmailNotificaciones(this.selectedEmpresa.getEmailNotificaciones().trim());
		
		
		System.out.println("ID contrato: "+ this.contrato.getIdcontrato());
		this.contrato.setIdcontrato(this.contrato.getIdcontrato());
		this.contrato.setFechainicio(this.contrato.getFechainicio());
		this.contrato.setFechafin(this.contrato.getFechafin());
		this.contrato.setBasegravable(this.contrato.getBasegravable());
		this.contrato.setFactorpuntos(this.contrato.getFactorpuntos());
		this.contrato.setVrminimoredimir(this.contrato.getVrminimoredimir());
		byte temp = (byte) (this.envioSms == true ? 1 : 0);
		this.contrato.setEnviosms( temp	);
		temp = (byte) (this.envioEmail == true ? 1 : 0);
		this.contrato.setEnvioemail(temp);
		temp = (byte) (this.redensionSuc == true ? 1 : 0);
		this.contrato.setRedensionsucursales(temp);
			
		
		this.empEjb.updateEmpresa(this.selectedEmpresa);
		this.contrato.setEmpresa(this.selectedEmpresa);
		this.empEjb.updateContrato(this.contrato);
				
		FacesContext.getCurrentInstance().addMessage("globalMessagex", new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Actualizacion Exitosa!", "Empresa " + this.selectedEmpresa.getNombreEmpresa() + " fue actualizada correctamente."));
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Flash flash = facesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.setKeepMessages(true);
		
		return "empresalist?faces-redirect=true";		
	}
	
	
	
	public String crearEmpresa() {
		//Validaciones de los campos
		log.info("crear empresa");
		this.selectedEmpresa.setNit(this.selectedEmpresa.getNit().trim());
		this.selectedEmpresa.setNombreEmpresa(this.selectedEmpresa.getNombreEmpresa().trim().toUpperCase());
		this.selectedEmpresa.setDireccion(this.selectedEmpresa.getDireccion().trim().toUpperCase());
		this.selectedEmpresa.setTelefono(this.selectedEmpresa.getTelefono().trim());
		this.selectedEmpresa.setEmailNotificaciones(this.selectedEmpresa.getEmailNotificaciones().trim());
		this.selectedEmpresa.setEmail(this.selectedEmpresa.getEmail().trim());
		
		System.out.println("Fecha Inicio: "  +  this.contrato.getFechainicio() );

		//realiza persistencia con el objeto EntityManager
		
		empEjb.insertEmpresa(this.selectedEmpresa);
		Empresa empTemp = empEjb.obtenerEmpresaPorNombre(this.selectedEmpresa);
		
		byte temp = (byte) (this.isEnvioSms() ? 1 : 0);
		this.contrato.setEnviosms(temp);
		temp = (byte) (this.isEnvioEmail()? 1 : 0);
		this.contrato.setEnvioemail(temp);
		temp = (byte) (this.isRedensionSuc()? 1 : 0);
		this.contrato.setRedensionsucursales(temp);
		this.contrato.setEmpresa(empTemp);		
		this.contrato.setBasegravable(this.contrato.getBasegravable());
		this.contrato.setVrminimoredimir(this.contrato.getVrminimoredimir());
		this.contrato.setFactorpuntos(this.contrato.getFactorpuntos());
				
		empEjb.insertarContrato(this.contrato);
		//Prepara mensaje a mostrar
		FacesContext.getCurrentInstance().addMessage("globalMessagex", new FacesMessage(
							FacesMessage.SEVERITY_INFO, "Registro Exitoso!", "Empresa "
							+ this.selectedEmpresa.getNombreEmpresa() 
							+" fue creada correctamente."));
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Flash flash = facesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.setKeepMessages(true);
		//Outcome para navegacion
		return "empresalist?faces-redirect=true";		
	}
	
	
	public String cancelar(){
		System.out.println("Cancelando formulario");
		
		return "empresalist?faces-redirect=true";
	}


}
