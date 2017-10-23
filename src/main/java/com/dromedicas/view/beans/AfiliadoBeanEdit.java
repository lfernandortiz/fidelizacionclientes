package com.dromedicas.view.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.domain.Tipodocumento;
import com.dromedicas.mailservice.EnviarEmailAlertas;
import com.dromedicas.service.AfiliadoService;
import com.dromedicas.service.SucursalService;
import com.dromedicas.service.TipoDocumentoService;
import com.dromedicas.service.UsuarioWebService;
import com.dromedicas.util.ExpresionesRegulares;

@ManagedBean(name="afiliadoBeanEdit")
@SessionScoped
public class AfiliadoBeanEdit implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private AfiliadoService afiliadoService;
	
	@EJB
	private TipoDocumentoService tipodocService;
	
	@EJB
	private SucursalService sucursalService;
	
	@EJB
	private UsuarioWebService usuarioService;
	
	@EJB
	private ExpresionesRegulares regex;
	
	@EJB
	private EnviarEmailAlertas mailAlert;
	
	@ManagedProperty(value = "#{loginService}")
	private LoginBeanService loginBean;
		
	private Afiliado afiliadoSelected;
	private List<Tipodocumento> tipodocList; // list para select one menu tipodocumento
	private List<Sucursal> sucursalList;
	
	private String street1 = "AVENIDA";
	private String street1Valor = "";
	private String street2 = "No.";
	private String street2Valor = "";
	private String direccionCompleta;
	private boolean emailValid = true;
	
	public AfiliadoBeanEdit(){
		
	}//constructor por defecto 

	@PostConstruct
	public void init(){
		this.afiliadoSelected = new Afiliado();
		this.tipodocList = tipodocService.findAllTipodocumento();
		this.sucursalList = sucursalService.findAllSucursals();	
		
	}
		
	public LoginBeanService getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBeanService loginBean) {
		this.loginBean = loginBean;
	}

	public List<Tipodocumento> getTipodocList() {
		return tipodocList;
	}

	public void setTipodocList(List<Tipodocumento> tipodocList) {
		this.tipodocList = tipodocList;
	}

	public Afiliado getAfiliadoSelected() {
		return afiliadoSelected;
	}

	public void setAfiliadoSelected(Afiliado afiliadoSelected) {
		this.afiliadoSelected = afiliadoSelected;
	}

	public List<Sucursal> getSucursalList() {
		return sucursalList;
	}

	public void setSucursalList(List<Sucursal> sucursalList) {
		this.sucursalList = sucursalList;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet1Valor() {
		return street1Valor;
	}

	public void setStreet1Valor(String street1Valor) {
		this.street1Valor = street1Valor;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getStreet2Valor() {
		return street2Valor;
	}

	public void setStreet2Valor(String street2Valor) {
		this.street2Valor = street2Valor;
	}
	
	public String getDireccionCompleta() {
		return direccionCompleta;
	}

	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}
	
	public boolean isEmailValid() {
		return emailValid;
	}

	public void setEmailValid(boolean emailValid) {
		this.emailValid = emailValid;
	}
	
	
	//metodos de control de la interfaz
	
	public void concatenarDireccion(){
		StringBuilder str = new StringBuilder();
		str.append(this.getStreet1().toUpperCase()).append(" ")
					.append(this.getStreet1Valor().trim().toUpperCase()).append(" ")
					.append(this.getStreet2().toUpperCase()).append(" ")
					.append(this.getStreet2Valor().trim().toUpperCase());
		this.setDireccionCompleta(str.toString());
		System.out.println(this.direccionCompleta);
	}
	
	public String crearAfiliado(){
		
		//Establece los valores
		this.afiliadoSelected.setNombres(
					regex.removerAcentosNtildes(this.afiliadoSelected.getNombres().trim().toUpperCase()));
		this.afiliadoSelected.setApellidos(
					regex.removerAcentosNtildes(this.afiliadoSelected.getApellidos().trim().toUpperCase()));
		this.afiliadoSelected.setTipodocumentoBean(this.afiliadoSelected.getTipodocumentoBean());
			//**Valida si ya esta registrada la cedula
		
		Afiliado afTempo = afiliadoService.obtenerAfiliadoByDocumento(this.afiliadoSelected.getDocumento());
		
		if(afTempo != null ){
			FacesContext.getCurrentInstance().addMessage("cedulaid", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Numero de Documento Ya Registrado!"));			
			System.out.println("Cedula ya registrada: " );	
			return null;
		}
		
		this.afiliadoSelected.setDocumento(this.afiliadoSelected.getDocumento());
		this.afiliadoSelected.setSexo(this.afiliadoSelected.getSexo());
		this.afiliadoSelected.setFechanacimiento(this.afiliadoSelected.getFechanacimiento());
		
		this.afiliadoSelected.setStreet(
				regex.removerAcentosNtildes(this.getDireccionCompleta()).replaceAll("#", "NO."));
		this.afiliadoSelected.setStreetdos(regex.removerAcentosNtildes(
								this.afiliadoSelected.getStreetdos().trim().toUpperCase()));
		this.afiliadoSelected.setCiudad(this.afiliadoSelected.getCiudad());
			//aca validar Email
		if( !this.afiliadoSelected.getEmail().equals("") ){
			if(regex.validateEmail(this.afiliadoSelected.getEmail())){
				this.afiliadoSelected.setEmail(this.afiliadoSelected.getEmail());
				this.setEmailValid(true);
			}else{
				FacesContext.getCurrentInstance().addMessage("emailid", 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								"Error", "Direccion de Email no valida"));
				this.setEmailValid(false);
				System.out.println("Correo no valido: " );
				return null;
			}
		}else{
			this.afiliadoSelected.setEmail("");
		}
		this.afiliadoSelected.setCelular(this.afiliadoSelected.getCelular().trim());
		this.afiliadoSelected.setTelefonofijo(this.afiliadoSelected.getTelefonofijo().trim());
		this.afiliadoSelected.setDepartamento("");	
		this.afiliadoSelected.setNacionalidad("COL");
			//**calcular la edad	
		System.out.println("-----validando edad");
		if(regex.getAge(this.afiliadoSelected.getFechanacimiento()) < 18 ){
			FacesContext.getCurrentInstance().addMessage("fechanacid", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "MENOR DE EDAD!"));			
			return null;
		}
		this.afiliadoSelected.setEdad(regex.getAge(this.afiliadoSelected.getFechanacimiento()));
		this.afiliadoSelected.setMomento(new Date());		
		Sucursal sucursal  = 
				this.sucursalService.obtenerSucursalById(this.afiliadoSelected.getSucursal());
		this.afiliadoSelected.setSucursal(sucursal);
		
		//Obtiene el usuario que registra..........Faltaaaaa		
		this.afiliadoSelected.setUsuariowebBean( this.loginBean.getUser() );
		
		//Persiste el nuevo afiliado
		afiliadoService.updateAfiliado(this.afiliadoSelected);
		
		//Envia correo de notificacion de afiliacion
		boolean enviado = mailAlert.enviarEmailAlertaVentas(this.afiliadoSelected);
		
		if(enviado){
			
		}
		
		
		//Despliega Callout sucess				
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("afiliadoBeanEdit");
		
		FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Registro Exitoso!", "Cliente afiliado Exitosamente"));
		FacesContext facesContext = FacesContext.getCurrentInstance();
		@SuppressWarnings("static-access")
		Flash flash = facesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.setKeepMessages(true);
		
		return "afiliadocrear?faces-redirect=true";
	}
	
	public void validarCedula(){
		Afiliado afTemp = afiliadoService.obtenerAfiliadoByDocumento(this.afiliadoSelected.getDocumento());
		if(afTemp != null ){
			FacesContext.getCurrentInstance().addMessage("cedulaid", 
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Numero de DOCUMENTO YA REGISTRADA!"));			
			System.out.println("Cedula ya registrada: " );			
		}
	}
	
	public void validarEdad(){
		if(regex.getAge(this.afiliadoSelected.getFechanacimiento()) < 18 ){
			FacesContext.getCurrentInstance().addMessage("fechanacid", 
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Menor de Edad!"));			
			System.out.println("Menor de edad: " );		
		}		
	}
	
	
	public String cancelarAfiliado(){
		return "afiliadolist?faces-redirect=true";
	}
	
	public String volverListAfiliado(){
		return "afiliadolist?faces-redirect=true";
	}
		
		
	
}
