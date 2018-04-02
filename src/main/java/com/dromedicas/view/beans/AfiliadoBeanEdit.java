package com.dromedicas.view.beans;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.BalancePuntos;
import com.dromedicas.domain.Emailenvio;
import com.dromedicas.domain.Pais;
import com.dromedicas.domain.Smsenvio;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.domain.Ticketredencion;
import com.dromedicas.domain.Tipodocumento;
import com.dromedicas.domain.Transaccion;
import com.dromedicas.domain.Vendedor;
import com.dromedicas.mailservice.EnviarEmailAlertas;
import com.dromedicas.reportes.Reporteador;
import com.dromedicas.service.AfiliadoService;
import com.dromedicas.service.OperacionPuntosService;
import com.dromedicas.service.PaisService;
import com.dromedicas.service.ReferidoService;
import com.dromedicas.service.RegistroNotificacionesService;
import com.dromedicas.service.SucursalService;
import com.dromedicas.service.TicketredencionService;
import com.dromedicas.service.TipoDocumentoService;
import com.dromedicas.service.TipoTransaccionService;
import com.dromedicas.service.TransaccionService;
import com.dromedicas.service.UsuarioWebService;
import com.dromedicas.service.VendedorService;
import com.dromedicas.smsservice.SMSService;
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
	
	@EJB
	private PaisService paisService;
	
	@EJB
	private OperacionPuntosService puntosService;
	
	@EJB
	private TicketredencionService ticketService;
	
	@EJB
	private Reporteador report;
	
	@EJB
	private VendedorService vendedorService;
	
	@EJB
	private RegistroNotificacionesService registroNotificacion;
	
	@EJB
	private SMSService smsService;
	
	@EJB
	private RegistroNotificacionesService regNotificaciones;
	
	@ManagedProperty(value = "#{loginService}")
	private LoginBeanService loginBean;
		
	private Afiliado afiliadoSelected;
	private List<Tipodocumento> tipodocList; // list para select one menu tipodocumento
	private List<Sucursal> sucursalList;
	private List<Pais> paisList;
	private List<Emailenvio> emailEnvioList;
	private List<Transaccion> transaccionList;
	private Emailenvio emailEnvioSelected;
	private String mensajeSms;
	private String longiMensajeSMS;
	private Smsenvio smsEnvioSelected;
	
	private String street1 = "AVENIDA";
	private String street1Valor = "";
	private String street2 = "No.";
	private String street2Valor = "";
	private String direccionCompleta;
	private Pais nacionalidad;
	//variable de control para validacion de entrada de correo
	private boolean emailValid = true; 
	//varialbe para gui par
	private boolean emailValidated;
	private boolean emailRechazado;
	
	private BalancePuntos balancePuntos;
	
	//upload ticket redenciones
	private String nroFacturaTemp;
	private UploadedFile fileUp;
	private Transaccion txTemp;
	private StreamedContent fileDow;
	
	//download ticket
	private StreamedContent ticketDown;
	
	
	/**
	 * Constructor por defecto
	 */
	public AfiliadoBeanEdit(){
		
		
	}//constructor por defecto 

	@PostConstruct
	public void init(){
		
		this.afiliadoSelected = new Afiliado();	
		this.nacionalidad = new Pais();
		this.tipodocList = tipodocService.findAllTipodocumento();
		this.sucursalList = sucursalService.findAllSucursals();
		this.paisList = paisService.findAllPais();
		
		this.afiliadoSelected.setCiudad("CUCUTA");		
		this.nacionalidad = paisService.obtenerPaisPorNombre("Colombia");
		
		System.out.println("Pais: " + this.getNacionalidad().getNombees());
	}
			
	public StreamedContent getFileDow() {
		return fileDow;
	}

	public void setFileDow(StreamedContent fileDow) {
		this.fileDow = fileDow;
	}

	public Transaccion getTxTemp() {
		return txTemp;
	}

	public void setTxTemp(Transaccion txTemp) {
		this.txTemp = txTemp;
	}

	public UploadedFile getFileUp() {
		return fileUp;
	}

	public void setFileUp(UploadedFile fileUp) {
		this.fileUp = fileUp;
	}
	
	public String getNroFacturaTemp() {
		return nroFacturaTemp;
	}

	public void setNroFacturaTemp(String nroFacturaTemp) {
		this.nroFacturaTemp = nroFacturaTemp;
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
		
	public boolean isEmailRechazado() {
		return emailRechazado;
	}

	public void setEmailRechazado(boolean emailRechazado) {
		this.emailRechazado = emailRechazado;
	}

	public List<Pais> getPaisList() {
		return paisList;
	}

	public void setPaisList(List<Pais> paisList) {
		this.paisList = paisList;
	}	

	public Pais getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(Pais nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	
	public BalancePuntos getBalancePuntos() {
		return balancePuntos;
	}

	public void setBalancePuntos(BalancePuntos balancePuntos) {
		this.balancePuntos = balancePuntos;
	}
	
	
	public List<Emailenvio> getEmailEnvioList() {
		return emailEnvioList;
	}

	public void setEmailEnvioList(List<Emailenvio> emailEnvioList) {
		this.emailEnvioList = emailEnvioList;
	}	

	public Emailenvio getEmailEnvioSelected() {
		return emailEnvioSelected;
	}

	public void setEmailEnvioSelected(Emailenvio emailEnvioSelected) {
		this.emailEnvioSelected = emailEnvioSelected;
	}

	public String getMensajeSms() {
		return mensajeSms;
	}

	public void setMensajeSms(String mensajeSms) {
		this.mensajeSms = mensajeSms;
	}
	

	public String getLongiMensajeSMS() {
		return longiMensajeSMS;
	}

	public void setLongiMensajeSMS(String longiMensajeSMS) {
		this.longiMensajeSMS = longiMensajeSMS;
	}
		
	public Smsenvio getSmsEnvioSelected() {
		return smsEnvioSelected;
	}

	public void setSmsEnvioSelected(Smsenvio smsEnvioSelected) {
		this.smsEnvioSelected = smsEnvioSelected;
	}
	
	public List<Transaccion> getTransaccionList() {
		return transaccionList;
	}

	public void setTransaccionList(List<Transaccion> transaccionList) {
		this.transaccionList = transaccionList;
	}

	public void analizaSMS(){
		// La longitud maxima de caracteres a enviar por mensaje SMS es de 160 caracteres
		// segun el proveedor del servicio.
		
		// El metodo longitudMensaje elimina del mensaje las variables "${variable}"
		int longitud =  this.mensajeSms.length() ;
		int restante = 160 - longitud;
		if( longitud > 160){
			this.setLongiMensajeSMS(longitud  + " Mensaje muy extenso");
		}else{
			this.setLongiMensajeSMS( restante  + (restante == 1 ? " Caracter restante" : " Caracteres restantes") );
		}
	}
	
	public void cancelarCrearSMS(){
		this.setMensajeSms("");
		this.setLongiMensajeSMS("");
	}
	
	public void enviarSMSDirecto(){
		
		//valida que el afilado tenga un celular valido 
		
		if( this.afiliadoSelected.getCelular().length() == 10 && 
				this.afiliadoSelected.getCelular().substring(0,1).equals("3") && 
					this.getMensajeSms().length() > 10 ){
			
			int estado = this.smsService.enviarSMSDirecto(this.afiliadoSelected.getCelular(),
						this.getMensajeSms(), "SMS Directo");
			
			System.out.println("CODIGO DE RESPUESTA: " + estado);
			
			if(estado == 0 ){
				//cierra el cuado de dialogo
				RequestContext.getCurrentInstance().execute("PF('smsdirectoafil').hide();");
				
				//Mensaje de confirmacion en el list
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"SMS enviado con exito!", "SMS enviado satisfactoriamente al Nro. " 
								+ this.getAfiliadoSelected().getCelular()));
				//registr auditoria del mensaje
				this.regNotificaciones.auditarSMSEnviado(this.afiliadoSelected, this.getMensajeSms(), "SMS Directo", estado);
				
				this.cancelarCrearSMS();
			}else{
				//cierra el cuado de dialogo
				RequestContext.getCurrentInstance().execute("PF('smsdirectoafil').hide();");
				
				//Mensaje de confirmacion en el list
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"SMS no enviado!", "El mensaje no fue enviado, hay un error en el numero celular."));
				
				this.cancelarCrearSMS();
			}
		}//fin else validacion nro
		
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
		
		System.out.println("NACIONALIDAD:  " + this.getNacionalidad().getNombees()); 
		
		
		this.afiliadoSelected.setNacionalidad(this.getNacionalidad().getNombees());
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
		//this.afiliadoSelected.setNacionalidad("COL");
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
		
		UUID uniqueKey = UUID.randomUUID(); // codigo usado para validaciones web
		this.afiliadoSelected.setKeycode(uniqueKey.toString().replace("-", ""));
		
		//2 Obtiene el usuario que registra..........Faltaaaaa		
		this.afiliadoSelected.setUsuariowebBean( this.loginBean.getUser() );
		
		//3 Persiste el nuevo afiliado
		afiliadoService.crearAfiliado(this.afiliadoSelected);
		
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
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Menor de Edad!"));			
			System.out.println("Menor de edad: " );		
		}		
	}
	
	public String editarAfiliado(){
		this.emailValidated = this.afiliadoSelected.getEmailvalidado() == 1 ? true : false;
		this.emailRechazado = this.afiliadoSelected.getEmailrechazado() == 1 ? true : false;
		//variable de control para validacion de correo se usa para aplicar el css en la vista
		this.emailValid = true;		
		this.nacionalidad = this.paisService.obtenerPaisPorNombre(this.afiliadoSelected.getNacionalidad());	
		
		//Carga de informacion del Balance de Puntos
		System.out.println("Afiliado Seleccionado: " + afiliadoSelected.getDocumento());		
		this.balancePuntos = this.puntosService.consultaPuntos(afiliadoSelected);
		
		this.transaccionList = this.afiliadoService.obtenerTodasTransacciones(afiliadoSelected);
		
		System.out.println("*-*-*--*-*--*-*-*--*Tamanio de las Txs: " + this.transaccionList.size());
		return "afiliadoedit?faces-redirect=true";
	}
	
	
	public String editarAfiliadoDocumento(String documento){
		//Establece el objeto seleccionado
//		Object el[]= (Object [])  event.getObject();
//		String documento = (String) el[0];
		
		System.out.println("Documento recibido " + documento);
		Afiliado af = this.afiliadoService.obtenerAfiliadoByDocumento(documento);
		
		System.out.println("AFILIADO SELECCTIONADO: " + af.getDocumento());
		this.setAfiliadoSelected(af);
		
		this.emailValidated = this.afiliadoSelected.getEmailvalidado() == 1 ? true : false;
		this.emailRechazado = this.afiliadoSelected.getEmailrechazado() == 1 ? true : false;
		//variable de control para validacion de correo se usa para aplicar el css en la vista
		this.emailValid = true;		
		this.nacionalidad = this.paisService.obtenerPaisPorNombre(this.afiliadoSelected.getNacionalidad());	
		
		//Carga de informacion del Balance de Puntos
		System.out.println("Afiliado Seleccionado: " + afiliadoSelected.getDocumento());		
		this.balancePuntos = this.puntosService.consultaPuntos(afiliadoSelected);
		
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
		
		//valida si la cedula que se esta ingresando no este registrada en otro afiliado
		Afiliado afTempo = afiliadoService.obtenerAfiliadoByDocumento(this.afiliadoSelected.getDocumento());

 		if(afTempo != null && afTempo.getIdafiliado() != this.afiliadoSelected.getIdafiliado() ){		
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Numero de Documento Ya Registrado a otro afiliado!"));			
			System.out.println("Cedula ya registrada: " );	
			return null;
		}	
		
		this.afiliadoSelected.setDocumento(this.afiliadoSelected.getDocumento());
		this.afiliadoSelected.setSexo(this.afiliadoSelected.getSexo());
		this.afiliadoSelected.setFechanacimiento(this.afiliadoSelected.getFechanacimiento());
		this.afiliadoSelected.setNacionalidad(this.nacionalidad.getNombees());
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
		
		//Actualiza afiliado
		afiliadoService.actualizarAfiliado(this.afiliadoSelected);
		
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
		
		String enviado = mailAlert.enviarEmailAlertaVentas(this.afiliadoSelected);
		
		if(enviado != null){
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Envio Exitoso!", "Un nuevo email de confirmacion "
							+ "de suscripcion a Puntos Farmanorte fue enviando."));
			byte valid = 0 ;
			this.afiliadoSelected.setEmailvalidado(valid);
			this.afiliadoService.updateAfiliado(afiliadoSelected);
			this.registroNotificacion.auditarEmailEnviado(afiliadoSelected, enviado, "Bienvenida al programa");
		}else{
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No fue posible el envio de confirmacion"));
		}
		
	}
	
	public String usuarioRegistro(){
		String registro = "";
		
		System.out.println("CODVENDE " + this.afiliadoSelected.getCodvende() );
		if( this.afiliadoSelected.getCodvende() != null &&  !this.afiliadoSelected.getCodvende().equals("")){			
			Vendedor v = 
					this.vendedorService.obtenerVendedorPorCodigo( this.afiliadoSelected.getCodvende());
			
			registro = v.getNombres() + " " + v.getApellidos();
		}else{
			registro = this.afiliadoSelected.getUsuariowebBean().getNombreusuario();
		}
		
		return registro;
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
		//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("afiliadoBeanEdit");
		return "afiliadolist?faces-redirect=true";
	}
	
	public String volverListAfiliado(){
		return "afiliadolist?faces-redirect=true";
	}
		
	
	/**
	 * INFORMACION DEL BALANCE DE PUNTOS
	 */
	
	public String nombrePropioAfiliado(String nombre){
		return this.regex.nombrePropio(nombre, true);
	}
	
	
	/**
	 * Metodo para la carga y descarga de archivos ticket
	 * */
	public void uploadFile(FileUploadEvent event){
		
		try {
			
			this.fileUp = event.getFile();
			
			//valida que solo se permita carga de ticket de redension y que existan los archivos
			if( this.fileUp != null && this.getTxTemp() != null && this.getTxTemp().getTipotransaccion().getIdtipotransaccion() == 2){
				//valida si la Tx ya tiene una imagen de ticket asignada
				Ticketredencion tck = 
						this.ticketService.obtenerTicketredencionByNroFactura(this.txTemp.getNrofactura());
				
				if( tck != null ){//actualiza la imagen de la Tx actual
					
					tck.setTransaccion(this.getTxTemp());
					
					tck.setImgticket( IOUtils.toByteArray( this.fileUp.getInputstream() ));
					
					//con el objeto servicio de ticket persisto la actualizacion del ticket
					this.ticketService.updateTicketredencion(tck);
					
					//despliego los mensajes, cierro el cuadro de dialogo y actualizdo el datatable de Tx's
					FacesContext context = FacesContext.getCurrentInstance();	         
			        context.addMessage(":formticketid:messagesupload", new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizacion Exitosa!",
			        		"Ticket Actualizado Exitosamente a la Transaccion de Redencion"));
			        
			        this.cancelarUpload();
			        
			        //actualiza el datatable de tx's
			        this.afiliadoSelected = afiliadoService.obtenerAfiliadoById(this.afiliadoSelected);		
			        RequestContext.getCurrentInstance().update("balancedatail");
					
				}else{// Carga una nueva imagen de ticket
					
					//primero establezco el objeto a persistir junto con sus miembros
					tck = new Ticketredencion();
					
					tck.setTransaccion(this.getTxTemp());
					
					tck.setImgticket( IOUtils.toByteArray( this.fileUp.getInputstream() ));
					
					//con el objeto servicio de ticket persisto el nuevo objeto 
					this.ticketService.updateTicketredencion(tck);
					
					//despliego los mensajes, cierro el cuadro de dialogo y actualizdo el datatable de Tx's
					FacesContext context = FacesContext.getCurrentInstance();	         
			        context.addMessage(":formticketid:messagesupload", new FacesMessage(FacesMessage.SEVERITY_INFO, "Carga Exitosa!",
			        		"Ticket Asociao Exitosamente a la Transaccion de Redencion"));
			        
			        this.cancelarUpload();
			        
			        //actualiza el datatable de tx's
			        this.afiliadoSelected = afiliadoService.obtenerAfiliadoById(this.afiliadoSelected);		
			        RequestContext.getCurrentInstance().update("balancedatail");	
				}//end else (tck != null)		        
			}else{
				FacesContext context = FacesContext.getCurrentInstance();	         
		        context.addMessage(":formticketid:messagesupload", new FacesMessage(FacesMessage.SEVERITY_WARN, "Falta Informacion",
		        		"Informacion invalida para cargar el ticket."));
			}
	        
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();	         
	        context.addMessage(":formticketid:messagesupload", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Archivo MUY Grande",
	        		"El tamano maximo del archivo es de 1MB."));
			e.printStackTrace();
		}
	}
	
	
	public StreamedContent getTicketDown() {
		
		Ticketredencion tkTemp = this.ticketService.obtenerTicketredencionByNroFactura(this.txTemp.getNrofactura());

		if (tkTemp != null) {
			byte[] image = null;
			image = tkTemp.getImgticket();

			DefaultStreamedContent ds = new DefaultStreamedContent(new ByteArrayInputStream(image), "image/jpg",
					"redencion" + this.txTemp.getNrofactura()+".jpg");
			
			this.resetFilesField();
			
			return ds;
		} else {
			FacesContext context = FacesContext.getCurrentInstance();	         
	        context.addMessage("messagesupload", new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", 
	        		"No hay archivos para descargar") );
	        this.resetFilesField();
	        
	        return null;
		}
	}
	

	public void setTicketDown(StreamedContent ticketDown) {
		this.ticketDown = ticketDown;
	}

	
	/**
	 * Retorna la imagen del ticket digitalizado
	 * @return
	 */
	public StreamedContent getImage(){
		//obtengo el Ticket
		FacesContext context = FacesContext.getCurrentInstance();
		String factura = context.getExternalContext().getRequestParameterMap().get("nrofactura");
		
		Ticketredencion tkTemp = this.ticketService.obtenerTicketredencionByNroFactura(factura);
		if( tkTemp != null ){
			byte[] image = null;
			
			image = tkTemp.getImgticket();
			
			DefaultStreamedContent ds = new DefaultStreamedContent(new ByteArrayInputStream(image),
					"image/jpg");
			return ds;
		}else{
			return new DefaultStreamedContent();
		}
	}
	
	
	public void buscarFacturaRedencion(){
		//Obtengo el objeto factura al que le vamos relacionar el 
		//archivo de redenion
		try {
			this.txTemp = 
					txService.obtenerRedencionPorFacturaYAfiliado(this.getNroFacturaTemp(), this.afiliadoSelected);
			
			if( txTemp != null ){
				FacesContext context = FacesContext.getCurrentInstance();	         
		        context.addMessage("messagesupload", new FacesMessage(FacesMessage.SEVERITY_INFO, "Redencion si Existe!",
		        		"Factura de Redencion encontrada"));
			}else{
				FacesContext context = FacesContext.getCurrentInstance();	         
		        context.addMessage("messagesupload", new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", 
		        		"Factura de Redencion no hallada") );
			}
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();	         
	        context.addMessage("messagesupload", new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", 
	        		"Criterios de busqueda no validos") );
		}
	}
	
	
	public void cancelarUpload(){
		this.resetFilesField();
		//cierra el cuado de dialogo
		RequestContext.getCurrentInstance().execute("PF('ticketDialogCrear').hide();");
	}
	
	
	private void resetFilesField(){
		this.setTxTemp(null);
		this.setNroFacturaTemp("");		
		this.setFileUp(null);
	}
	
	public boolean ticketAsociado(Transaccion tx){
		return this.ticketService.obtenerTicketredencionByNroFactura(tx.getNrofactura())!= null? true : false;
	}
	
	public void exportarExcel(String documento){
		
		report.generarReporteExcelElipsis("balancepuntos", documento);		
	}
	
	
	
	
}
