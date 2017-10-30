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
import com.dromedicas.domain.Referido;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.domain.Tipodocumento;
import com.dromedicas.domain.Tipotransaccion;
import com.dromedicas.domain.Transaccion;
import com.dromedicas.mailservice.EnviarEmailAlertas;
import com.dromedicas.service.AfiliadoService;
import com.dromedicas.service.OperacionPuntosService;
import com.dromedicas.service.ReferidoService;
import com.dromedicas.service.SucursalService;
import com.dromedicas.service.TipoDocumentoService;
import com.dromedicas.service.TipoTransaccionService;
import com.dromedicas.service.TransaccionService;
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
	
	@EJB
	private TipoTransaccionService tipoTxService;
	
	@EJB
	private TransaccionService txService;
	
	@EJB
	private ReferidoService referidoService;
	
	@EJB
	private OperacionPuntosService calculoService;
	
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
	//variable de control para validacion de entrada de correo
	private boolean emailValid = true; 
	//varialbe para gui par
	private boolean emailValidated;
	
	public AfiliadoBeanEdit(){
		
	}//constructor por defecto 

	@PostConstruct
	public void init(){
		this.afiliadoSelected = new Afiliado();
		this.tipodocList = tipodocService.findAllTipodocumento();
		this.sucursalList = sucursalService.findAllSucursals();	
		this.afiliadoSelected.setCiudad("CUCUTA");
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
		
	public boolean isEmailValidated() {
		return emailValidated;
	}

	public void setEmailValidated(boolean emailValidated) {
		this.emailValidated = emailValidated;
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
		
		//1 Establece los valores
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
		
		if(this.getDireccionCompleta()!= null && !this.getDireccionCompleta().equals("")){
			this.afiliadoSelected.setStreet(
					regex.removerAcentosNtildes(this.getDireccionCompleta()).replaceAll("#", "NO. "));
			this.afiliadoSelected.setStreetdos(regex.removerAcentosNtildes(
									this.afiliadoSelected.getStreetdos().trim().toUpperCase()));
		}
				
		this.afiliadoSelected.setCiudad(this.afiliadoSelected.getCiudad());
			//aca validar Email
		if( !this.afiliadoSelected.getEmail().equals("") ){
			if(regex.validateEmail(this.afiliadoSelected.getEmail())){
				this.afiliadoSelected.setEmail(this.afiliadoSelected.getEmail().toLowerCase());
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
		
		//2 Obtiene el usuario que registra..........Faltaaaaa		
		this.afiliadoSelected.setUsuariowebBean( this.loginBean.getUser() );
		
		//3 Persiste el nuevo afiliado
		afiliadoService.updateAfiliado(this.afiliadoSelected);
		
		//4 Acumula los 100 puntos inciales del afiliado 
		
		Afiliado afTemp = this.afiliadoService.obtenerAfiliadoByDocumento(this.afiliadoSelected.getDocumento());
		
		int id = 4;
		Tipotransaccion tipoTx = tipoTxService.obtenerTipoTransaccioById(id);
		Transaccion tx = new Transaccion(); 
		tx.setAfiliado(afTemp);
		tx.setSucursal(this.afiliadoSelected.getSucursal());
		tx.setFechatransaccion(new Date());
		tx.setNrofactura("REGINI");
		tx.setValortotaltx(0);
		tx.setVencen(this.calculoService.addDays(new Date(), 365));
		tx.setTipotransaccion(tipoTx);
		tx.setPuntostransaccion(100);
			//graba los puntos iniciales
		txService.updateTransaccion(tx);
		
			//Se busca si el nuevo afiliado es un referido
		if (this.afiliadoSelected.getEmail() != null && !this.afiliadoSelected.getEmail().equals("")) {
			Referido ref = this.referidoService.obtenerReferidoPorEmail(this.afiliadoSelected.getEmail());
			
			// si el nuevo es un referido graba 100 puntos al afiliado que lo
			// refirio
			
			if (ref != null) {
				Afiliado afiReferente = ref.getAfiliado();

				int idTipo = 5;
				Tipotransaccion tipoTxRef = tipoTxService.obtenerTipoTransaccioById(idTipo);
				Transaccion txRef = new Transaccion();
				txRef.setAfiliado(afiReferente);
				txRef.setSucursal(this.afiliadoSelected.getSucursal());
				txRef.setFechatransaccion(new Date());
				txRef.setNrofactura("REGREF");
				txRef.setValortotaltx(0);
				txRef.setVencen( this.calculoService.addDays(new Date(), 365));
				txRef.setTipotransaccion(tipoTxRef);
				txRef.setPuntostransaccion(100);
				// graba los puntos iniciales
				txService.updateTransaccion(txRef);
			}
		} // end if validacion afiliado		
				
		//5 Envia correo de notificacion de afiliacion
		boolean enviado = false;
		if(this.afiliadoSelected.getEmail() != null && !this.afiliadoSelected.getEmail().equals("")){
			enviado = mailAlert.enviarEmailAlertaVentas(this.afiliadoSelected);
		}
		
		
		if(enviado){
		//6 Registro del Email para tracking	
		}
		
		//7 Despliega Callout sucess				
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
	
	public String editarAfiliado(){
		this.emailValidated = this.afiliadoSelected.getEmailvalidado() == 1 ? true : false;
		this.emailValid = true;
		return "afiliadoedit?faces-redirect=true";
	}
	
	public String actualizar(){
		System.out.println("Nombre: " + this.afiliadoSelected.getNombres().trim().toUpperCase());
		System.out.println("Apellidos: " + this.afiliadoSelected.getApellidos().trim().toUpperCase());
		this.afiliadoSelected.setNombres(
				regex.removerAcentosNtildes(this.afiliadoSelected.getNombres().trim().toUpperCase()));
		this.afiliadoSelected.setApellidos(
				regex.removerAcentosNtildes(this.afiliadoSelected.getApellidos().trim().toUpperCase()));
		this.afiliadoSelected.setTipodocumentoBean(this.afiliadoSelected.getTipodocumentoBean());
		this.afiliadoSelected.setDocumento(this.afiliadoSelected.getDocumento());
		this.afiliadoSelected.setSexo(this.afiliadoSelected.getSexo());
		this.afiliadoSelected.setFechanacimiento(this.afiliadoSelected.getFechanacimiento());
		
		this.afiliadoSelected.setStreet(regex.removerAcentosNtildes(this.afiliadoSelected.getStreet()).replaceAll("#", "NO. "));
		this.afiliadoSelected.setStreetdos(regex.removerAcentosNtildes(
								this.afiliadoSelected.getStreetdos().trim().toUpperCase()));
		this.afiliadoSelected.setCiudad(this.afiliadoSelected.getCiudad());
		if( !this.afiliadoSelected.getEmail().equals("") ){
			if(regex.validateEmail(this.afiliadoSelected.getEmail())){
				this.afiliadoSelected.setEmail(this.afiliadoSelected.getEmail().toLowerCase());
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
		if(regex.getAge(this.afiliadoSelected.getFechanacimiento()) < 18 ){
			FacesContext.getCurrentInstance().addMessage("fechanacid", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "MENOR DE EDAD!"));			
			return null;
		}
		this.afiliadoSelected.setEdad(regex.getAge(this.afiliadoSelected.getFechanacimiento()));
		//this.afiliadoSelected.setMomento(new Date());		
		Sucursal sucursal  = 
				this.sucursalService.obtenerSucursalById(this.afiliadoSelected.getSucursal());
		this.afiliadoSelected.setSucursal(sucursal);
		
		//Obtiene el usuario que registra..........Faltaaaaa		
		this.afiliadoSelected.setUsuariowebBean( this.loginBean.getUser() );
		
		//Persiste el nuevo afiliado
		afiliadoService.updateAfiliado(this.afiliadoSelected);
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("afiliadoBeanEdit");
		
		FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Registro Actualizado!", "Afilado Actualizado Exitosamente"));
		FacesContext facesContext = FacesContext.getCurrentInstance();
		@SuppressWarnings("static-access")
		Flash flash = facesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.setKeepMessages(true);
		
		return "afiliadolist?faces-redirect=true";
	}
	
	public void reenvioEmailAfiliacion(){
		
		
		boolean enviado = mailAlert.enviarEmailAlertaVentas(this.afiliadoSelected);
		
		if(enviado){
			FacesContext.getCurrentInstance().addMessage("messages", 
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Envio Exitoso!", "Un nuevo email de confirmacion "
							+ "de suscripcion a Puntos Farmanorte fue enviando."));
			byte valid = 0 ;
			this.afiliadoSelected.setEmailvalidado(valid);
			this.afiliadoService.updateAfiliado(afiliadoSelected);
		}else{
			FacesContext.getCurrentInstance().addMessage("messages", 
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No fue posible el envio de confirmacion"));
		}
		
	}
	
	public void limpiarForm(){
		this.afiliadoSelected = null;
		this.setStreet1("AVENIDA");
		this.setStreet1Valor("");
		this.setStreet2("No.");
		this.setStreet2Valor("");
		this.setDireccionCompleta("");
		this.afiliadoSelected = new Afiliado();
		this.afiliadoSelected.setCiudad("CUCUTA");
		this.tipodocList = tipodocService.findAllTipodocumento();
		this.sucursalList = sucursalService.findAllSucursals();			
	}
	
	
	public String cancelarAfiliado(){
		return "afiliadolist?faces-redirect=true";
	}
	
	public String volverListAfiliado(){
		return "afiliadolist?faces-redirect=true";
	}
		
		
	
}
