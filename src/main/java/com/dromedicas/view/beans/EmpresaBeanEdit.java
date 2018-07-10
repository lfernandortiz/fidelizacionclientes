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
import org.primefaces.context.RequestContext;

import com.dromedicas.domain.Contrato;
import com.dromedicas.domain.Empresa;
import com.dromedicas.domain.Parametrosemail;
import com.dromedicas.mailservice.EnviarEmailAlertas;
import com.dromedicas.service.EmpresaService;
import com.dromedicas.service.ParametrosEmialService;
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
	
	@EJB
	private ParametrosEmialService emailParameterService; 
	
	@EJB
	private ExpresionesRegulares ex;
	
	@EJB
	private EnviarEmailAlertas emailService;
	
	Logger log = Logger.getLogger(EmpresaBeanList.class);
	
	private Empresa selectedEmpresa;
	private Parametrosemail paramEmailSelected;
	private boolean envioSms;
	private boolean envioEmail;
	private boolean redensionSuc;
	private int idContratoSel;
	private int idEmpresaSel;
	private String emailTest;
	
	
	public EmpresaBeanEdit(){}
	
	@PostConstruct
	public void init(){	
		this.selectedEmpresa = new Empresa();
		this.paramEmailSelected = new Parametrosemail();
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
	
	public Parametrosemail getParamEmailSelected() {
		return paramEmailSelected;
	}

	public void setParamEmailSelected(Parametrosemail paramEmailSelected) {
		this.paramEmailSelected = paramEmailSelected;
	}
	
	public String getEmailTest() {
		return emailTest;
	}

	public void setEmailTest(String emailTest) {
		this.emailTest = emailTest;
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

		//carga los datos de configuracion de email
		this.paramEmailSelected = 
				this.emailParameterService.obtenerParametrosemailPorFinalidad(Integer.toString(selectedEmpresa.getIdempresa()));
		
		
		this.setIdEmpresaSel(this.selectedEmpresa.getIdempresa());
		this.setIdContratoSel(this.contrato.getIdcontrato());		
		
		return "empresaedit?faces-redirect=true";
	}
	
	
	public String actualizarEmpresa(){
		System.out.println("Actualizando empresa.....");
		//Actualiza la informacion general de la empresa
		this.selectedEmpresa.setIdempresa(this.selectedEmpresa.getIdempresa());
		System.out.println("Nit----:" + this.selectedEmpresa.getNit());
		this.selectedEmpresa.setNit(this.selectedEmpresa.getNit());
		this.selectedEmpresa.setNombreEmpresa(this.selectedEmpresa.getNombreEmpresa().trim().toUpperCase());
		this.selectedEmpresa.setDireccion(this.selectedEmpresa.getDireccion().trim().toUpperCase());
		this.selectedEmpresa.setTelefono(this.selectedEmpresa.getTelefono().trim());
		this.selectedEmpresa.setEmailNotificaciones(this.selectedEmpresa.getEmailNotificaciones().trim());
		this.selectedEmpresa.setCodigoprom(this.selectedEmpresa.getCodigoprom().trim());
		
		
		//Actualiza la informacion de la dinamica de puntos 
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
			
		
		//Actualiza la informacion de configuracion de la cuenta 
		//de email central de puntos
		this.paramEmailSelected.setSmtpUser(this.paramEmailSelected.getSmtpUser().trim());
		this.paramEmailSelected.setSmtpPassword(this.paramEmailSelected.getSmtpPassword().trim());
		this.paramEmailSelected.setSmtpHost(this.paramEmailSelected.getSmtpHost().trim());
		this.paramEmailSelected.setSmtpPort(this.paramEmailSelected.getSmtpPort().trim());
		this.paramEmailSelected.setSmtpAuth(this.paramEmailSelected.getSmtpAuth().trim());
		
		String prt = this.paramEmailSelected.getTransportprotocol();
		System.out.println("Protocolo de transporte: " +  prt);
		this.paramEmailSelected.setTransportprotocol(prt);
		
				
		//Actualiza la informacion de configuracion del proveedor de sms
		
		this.empEjb.updateEmpresa(this.selectedEmpresa);
		this.contrato.setEmpresa(this.selectedEmpresa);
		this.empEjb.updateContrato(this.contrato);
		this.emailParameterService.updateParametrosemail(this.paramEmailSelected);
				
		FacesContext.getCurrentInstance().addMessage("globalMessagex", new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Actualizacion Exitosa!", "Empresa " + this.selectedEmpresa.getNombreEmpresa() + " fue actualizada correctamente."));
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Flash flash = facesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.setKeepMessages(true);
		
		//return "null";
		return "empresaedit?faces-redirect=true";		
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
	
	
	public void enviarEmailTest(){
		System.out.println("Direccion de email: " + this.emailTest);
		
		boolean valido = ex.validateEmail( this.emailTest );
		System.out.println("Email valido: " + valido);
		
		if( valido ){
			try {
				emailService.enviarEmailPrueba(this.emailTest);
				//cierra el cuado de dialogo de envio de Email de prueba
				RequestContext.getCurrentInstance().execute("PF('testEmail').hide();");
				cancelarTest();
			} catch (Exception e) {
				// TODO: handle exception
			}			
			
		}else{
			
			//cierra el cuado de dialogo de envio de Email de prueba
			RequestContext.getCurrentInstance().execute("PF('testEmail').hide();");
			cancelarTest();
			
			//RequestContext.getCurrentInstance().execute("PF('globalMessagex').hide();");
			FacesContext.getCurrentInstance().addMessage("globalMessagex", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Direccion de Email no valida", "La direccion ingresada no es valida"));
		}
	}
	
	
	public void cancelarTest(){
		this.emailTest = "";
	}
	
	
	public String cancelar(){
		System.out.println("Cancelando formulario");
		
		return "empresalist?faces-redirect=true";
	}


}
