package com.dromedicas.view.beans;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dromedicas.domain.Campania;
import com.dromedicas.domain.Paremetroscampania;
import com.dromedicas.domain.Patologia;
import com.dromedicas.domain.Patologiacampania;
import com.dromedicas.domain.PatologiacampaniaPK;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.service.AfiliadoService;
import com.dromedicas.service.CampaniaService;
import com.dromedicas.service.ParametrosCampaniaSevice;
import com.dromedicas.service.PatologiaCampaniaSevice;
import com.dromedicas.service.PatologiaService;
import com.dromedicas.service.SucursalService;
import com.dromedicas.smsservice.SMSService;
import com.dromedicas.util.ExpresionesRegulares;

@ManagedBean(name="smsCampaniaBeanEdit")
@SessionScoped
public class SmsCampaniaBeanEdit implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName="PuntosFPU")
	EntityManager em;
		
	@EJB
	private PatologiaService patologiaSevice;
	
	@EJB
	private AfiliadoService afiliadoService;
	
	@EJB
	private SucursalService sucursalService;
	
	@EJB
	private ExpresionesRegulares regex;
	
	@EJB
	private SMSService smsService;
	
	@EJB
	private CampaniaService campaniaService;
	
	@EJB
	private ParametrosCampaniaSevice parametrosCampService;
	
	@EJB
	private PatologiaCampaniaSevice patologiaCService;
	
	private Campania campaniaSelected;
	private List<Sucursal> sucursalList;
	private List<Object[]> afiliadoList;
	private Sucursal[] selectedSucursal;
	
	private String nombreCampania;
	private String audiencia;
	private String queryStringMain;
	private Date fechaInicio;
	private Date fechaFin;
	private String contenidoSms;
	private String calculoString;
	private String longiMensajeSMS;
	
	private Sucursal sucursalSelected;
	private String sexo;
	private int edadIni = 18;
	private int edadFin = 50;
	private String[] selectedHijos;
	
	private String[] selectedPatologias;
	private List<String> patologiasList;
	
	private String hijosmenoresde4;
	private String hijosentre4y12;
	private String hijosentre13y18;
	private String hijosmayores;
	private Boolean cupoDisponible;
	
	
	
	public SmsCampaniaBeanEdit(){
		
	}
	
	
	@PostConstruct
	public void init(){
		
		System.out.println("@PostConstruct---------");
		
		this.campaniaSelected = new Campania();	
		this.sucursalList = sucursalService.findAllSucursals();		
		//Calendar rightNow = Calendar.getInstance();
		//int hour = rightNow.get(Calendar.HOUR_OF_DAY);
		
		Date dateTemp = this.AddingHHToDate(new Date(), 1);
		this.setFechaInicio( dateTemp );
		System.out.println("FECHA: " + new SimpleDateFormat("dd/MM/yyyy HH").format(this.getFechaInicio()) );	
		
		
		List<Patologia> ptList = this.patologiaSevice.findAllPatologias();		
		this.patologiasList = new ArrayList<String>();		
		for(Patologia e : ptList ){			
			this.patologiasList.add( e.getDrescripcion() );
		}
		
	}	
	
	
	public Campania getCampaniaSelected() {
		return campaniaSelected;
	}

	public void setCampaniaSelected(Campania campaniaSelected) {
		this.campaniaSelected = campaniaSelected;
	}

	public String getNombreCampania() {
		return nombreCampania;
	}

	public void setNombreCampania(String nombreCampania) {
		this.nombreCampania = nombreCampania;
	}
		

	public String getAudiencia() {
		return audiencia;
	}

	public void setAudiencia(String audiencia) {
		this.audiencia = audiencia;
	}

	public String getQueryStringMain() {
		return queryStringMain;
	}

	public void setQueryStringMain(String queryStringMain) {
		this.queryStringMain = queryStringMain;
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

	public String getContenidoSms() {
		return contenidoSms;
	}

	public void setContenidoSms(String contenidoSms) {
		this.contenidoSms = this.regex.removerAcentosNtildesSms( contenidoSms );
	}
	
	
	public Sucursal getSucursalSelected() {
		return sucursalSelected;
	}

	public void setSucursalSelected(Sucursal sucursalSelected) {
		this.sucursalSelected = sucursalSelected;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public int getEdadIni() {
		return edadIni;
	}

	public void setEdadIni(int edadIni) {
		this.edadIni = edadIni;
	}

	public int getEdadFin() {
		return edadFin;
	}

	public void setEdadFin(int edadFin) {
		this.edadFin = edadFin;
	}

	public String[] getSelectedPatologias() {
		return selectedPatologias;
	}

	public void setSelectedPatologias(String[] selectedPatologias) {
		this.selectedPatologias = selectedPatologias;
	}
	
	public List<String> getPatologiasList() {
		return patologiasList;
	}

	public void setPatologiasList(List<String> patologiasList) {
		this.patologiasList = patologiasList;
	}

	public String getHijosmenoresde4() {
		return hijosmenoresde4;
	}

	public void setHijosmenoresde4(String hijosmenoresde4) {
		this.hijosmenoresde4 = hijosmenoresde4;
	}

	public String getHijosentre4y12() {
		return hijosentre4y12;
	}

	public void setHijosentre4y12(String hijosentre4y12) {
		this.hijosentre4y12 = hijosentre4y12;
	}

	public String getHijosentre13y18() {
		return hijosentre13y18;
	}

	public void setHijosentre13y18(String hijosentre13y18) {
		this.hijosentre13y18 = hijosentre13y18;
	}

	public String getHijosmayores() {
		return hijosmayores;
	}

	public void setHijosmayores(String hijosmayores) {
		this.hijosmayores = hijosmayores;
	}

	public List<Sucursal> getSucursalList() {
		return sucursalList;
	}

	public void setSucursalList(List<Sucursal> sucursalList) {
		this.sucursalList = sucursalList;
	}
		
	public String[] getSelectedHijos() {
		return selectedHijos;
	}

	public void setSelectedHijos(String[] selectedHijos) {
		this.selectedHijos = selectedHijos;
	}
	
	public List<Object[]> getAfiliadoList() {
		return afiliadoList;
	}

	public void setAfiliadoList(List<Object[]> afiliadoList) {
		this.afiliadoList = afiliadoList;
	}
	
	public Sucursal[] getSelectedSucursal() {
		return selectedSucursal;
	}

	public void setSelectedSucursal(Sucursal[] selectedSucursal) {
		this.selectedSucursal = selectedSucursal;
	}
	
	public String getCalculoString() {
		return calculoString;
	}

	public void setCalculoString(String calculoString) {
		this.calculoString = calculoString;
	}

	public String getLongiMensajeSMS() {
		return longiMensajeSMS;
	}

	public void setLongiMensajeSMS(String longiMensajeSMS) {
		this.longiMensajeSMS = longiMensajeSMS;
	}
	
	
	public Boolean getCupoDisponible() {
		return cupoDisponible;
	}


	public void setCupoDisponible(Boolean cupoDisponible) {
		this.cupoDisponible = cupoDisponible;
	}


	/**
	 * Llena la coleccion de patologias
	 */
	public void obtenerPatologias(){
		
		
	}
	
	public java.util.Date AddingHHToDate(java.util.Date date, int nombreHeure) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.HOUR_OF_DAY, nombreHeure);
	    return calendar.getTime();
	}	
	
	public void analizaSMS(){
		// La longitud maxima de caracteres a enviar por mensaje SMS es de 160 caracteres
		// segun el proveedor del servicio.
		
		// El metodo longitudMensaje elimina del mensaje las variables "${variable}"
		int longitud =  this.contenidoSms.length() ;
		int restante = 160 - longitud;
		if( longitud > 160){
			this.setLongiMensajeSMS(longitud  + " Mensaje muy extenso");
		}else{
			this.setLongiMensajeSMS( restante  + (restante == 1 ? " Caracter restante" : " Caracteres restantes") );
		}
	}
	
	
	public void calcularCampania(){
		
		System.out.println("Calculando....");
		
		try {			
			System.out.println("Nombre Campana: " + this.campaniaSelected.getNombrecampania().toUpperCase().trim() );
			System.out.println("Sucursales:");			
			for(Sucursal e:  this.getSelectedSucursal()){
				System.out.println("  - " + e.getNombreSucursal());
			}
			System.out.println("Sexo: " + this.getSexo());
			System.out.println("Entre edades: " +  this.edadIni + " y " + this.edadFin);						
			System.out.println("Patologias:" + this.selectedPatologias.length );			
			for(String e:  this.selectedPatologias ){
				System.out.println("  -> " + e);
			}			
			
			System.out.println("Hora de envio:" +new SimpleDateFormat("dd/MM/yyyy HH").format(this.getFechaInicio()) );
			System.out.println("Mensaje: " +  this.getContenidoSms() );
			
			String queryString = this.obtenerConsulta();		
			
			System.out.println("QUERY STRING : ");
			System.out.println(	queryString );
						
			//consulta el saldo con el operador y establece si hay disponibilidad muestra los mensajes
			int cupoMensajes = this.smsService.obtenerMensajesDisponibles();
			
			if( cupoMensajes >= this.getAfiliadoList().size() ){
				this.setCalculoString("<span class=\"calculosms\">Total de Audiencia: " + 
					new DecimalFormat("#,###").format( this.getAfiliadoList().size() ) + " Afiliados. Hay cupo disponibles de mensajes.</span>");
			}else{
				this.setCalculoString("<span class=\"calculosms\">Total de Audiencia: " + new DecimalFormat("#,###").format( this.getAfiliadoList().size()) + 
						" Afiliados. <span class=\"noCupo\"> No Hay cupo disponibles de mensajes. Diponibles " +new DecimalFormat("#,###").format( cupoMensajes )+ " mensajes</span></span>");
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public String crearCampania(){
		//Crea las entidades
		System.out.println("CREANDO LA CAMPANIA.........");
		
		Campania cValida = this.campaniaService.obtenerCampaniaPorFecha(this.fechaInicio);
		
		if( cValida != null ){
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_WARN, "No se puede crear la Campaña", 
							"Ya existe una campaña programada para esa fecha y hora elija una hora diferente!"));			
			return null;
		}
		
		
		//valida que exista un mensaje 
		int tokens = new StringTokenizer(this.getContenidoSms(), " ").countTokens();
		
		if( tokens >= 3){
			this.campaniaSelected.setNombrecampania( this.campaniaSelected.getNombrecampania().toUpperCase().trim()) ;
			this.campaniaSelected.setCriterios(this.obtenerConsulta());
			this.campaniaSelected.setFechainicio(this.fechaInicio);
			this.campaniaSelected.setContenidosms(this.getContenidoSms());
			this.campaniaSelected.setMercadoobjetivo(this.audiencia.toUpperCase().trim());
			this.campaniaSelected.setEstadocampania((byte) 0);
			this.campaniaSelected.setTipocampania("S");
			//persiste la campania
			try {
				Integer id =this.campaniaService.updateCampania(this.campaniaSelected);

				System.out.println("ID DE LA CAMPANA CREADA: " + id );
				//se obtiene el objeto campania persistido
				Campania campaniaPersist =  this.campaniaService.obtenerCampaniaById(id);
				
				System.out.println("ID de la campana pesistida: " +  campaniaPersist.getIdcampania());
				
				//se crea los parametros de la campania
				Paremetroscampania paramC = new Paremetroscampania();
				paramC.setCampania(campaniaPersist);
				paramC.setSexo(this.getSexo());
				paramC.setEdadini(this.getEdadIni());
				paramC.setEdadfin(this.getEdadFin());
				paramC.setFechaenvio(this.getFechaInicio());
				
				this.parametrosCampService.updateParemetroscampania(paramC);			
				
				//-----------------falta establecer Sucursales e Hijos --------------------
				
				
				
				//estableciendo patologias				
				for(String e: this.getSelectedPatologias() ){
					Patologia pTemp = patologiaSevice.obtenerPatologiaPorDescripcion(e);
					//crea la llave primaria de Patologiacampania
					PatologiacampaniaPK pk = new PatologiacampaniaPK();
					pk.setIdcampania(campaniaPersist.getIdcampania());
					pk.setIdpatologia(pTemp.getIdpatologia());
					
					Patologiacampania patC = new Patologiacampania();
					patC.setId(pk);
					patC.setCampania(campaniaPersist);
					patC.setPatologia(pTemp);
					patC.setFecha(fechaInicio);
					
					this.patologiaCService.updatePatologiacampania(patC);
					
				}
				
				FacesContext fContext = FacesContext.getCurrentInstance();
				
				fContext.getExternalContext().getSessionMap().remove("smsCampaniaBeanEdit");
				
				FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Registro Exitoso!", "Campaña creada Exitosamente"));
				FacesContext facesContext = FacesContext.getCurrentInstance();
				@SuppressWarnings("static-access")
				Flash flash = facesContext.getCurrentInstance().getExternalContext().getFlash();
				flash.setKeepMessages(true);
				
				//outcome
				return "smscampanialist?faces-redirect=true";
				
			} catch (Exception e) {
				System.out.println("ALGO PASO EN LA PERSISTENCIA DEL METODO");
				e.printStackTrace();
				
				FacesContext fContext = FacesContext.getCurrentInstance();
				
				fContext.getExternalContext().getSessionMap().remove("smsCampaniaBeanEdit");
				
				fContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Campaña no fue creada!", "Se presento un error en la creacion de la campaña"));
				FacesContext facesContext = FacesContext.getCurrentInstance();
				@SuppressWarnings("static-access")
				Flash flash = facesContext.getCurrentInstance().getExternalContext().getFlash();
				flash.setKeepMessages(true);
				
				return null;
			}
		}else{			
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje Demasiado Corto", "El mensaje es muy corto!"));			
			return null;
		}
	}
	
	
	public String editarCampania(){
		Paremetroscampania param = 
				this.parametrosCampService.obtenerParemetroscampaniaByCampania(this.campaniaSelected);
		this.setSexo(param.getSexo() );
		this.setEdadIni(param.getEdadini());
		this.setEdadFin(param.getEdadfin());
		this.setAudiencia(this.campaniaSelected.getMercadoobjetivo());		
		this.setContenidoSms(this.campaniaSelected.getContenidosms() );
		this.setFechaInicio(this.campaniaSelected.getFechainicio());
		
		List<Patologiacampania> pList = this.campaniaSelected.getPatologiacampanias();
			
		if( this.campaniaSelected.getPatologiacampanias() != null ){
			System.out.println("Entre al if de patologias");			
			this.selectedPatologias = 
					new String[this.campaniaSelected.getPatologiacampanias().size()];
			for( int i = 0; i < pList.size(); i++ ){
				System.out.println("------" + pList.get(i).getPatologia().getDrescripcion() );
				this.selectedPatologias[i] = pList.get(i).getPatologia().getDrescripcion();
			}
		}
				
		return "smscampaniaedit?faces-redirect=true";
	}

	/**
	 * Permite actualizar la campania
	 * @return
	 */
	public String actualizar(){
		
		Campania cValida = this.campaniaService.obtenerCampaniaPorFecha(this.fechaInicio);
		
		if( cValida != null && cValida.getIdcampania() != this.campaniaSelected.getIdcampania() ){
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_WARN, "No se puede crear la Campaña", 
							"Ya existe una campaña programada para esa fecha y hora elija una hora diferente!"));			
			return null;
		}
		
		int tokens = new StringTokenizer(this.getContenidoSms(), " ").countTokens();
		Paremetroscampania param = 
				this.parametrosCampService.obtenerParemetroscampaniaByCampania(this.campaniaSelected);
		
		if( tokens >= 3){
			this.campaniaSelected.setNombrecampania( this.campaniaSelected.getNombrecampania().toUpperCase().trim()) ;
			this.campaniaSelected.setCriterios(this.obtenerConsulta());
			this.campaniaSelected.setFechainicio(this.fechaInicio);
			this.campaniaSelected.setContenidosms(this.getContenidoSms());
			this.campaniaSelected.setMercadoobjetivo(this.audiencia.toUpperCase().trim());
			this.campaniaSelected.setEstadocampania((byte) 0);
			this.campaniaSelected.setTipocampania("S");
			//persiste la campania
			try {
				Integer id =this.campaniaService.updateCampania(this.campaniaSelected);

				
				//se obtiene el objeto campania persistido
				Campania campaniaPersist =  this.campaniaService.obtenerCampaniaById(id);
				
				
				
				//se crea los parametros de la campania
				Paremetroscampania paramC = new Paremetroscampania();
				paramC.setCampania(campaniaPersist);
				paramC.setSexo(this.getSexo());
				paramC.setEdadini(this.getEdadIni());
				paramC.setEdadfin(this.getEdadFin());
				paramC.setFechaenvio(this.getFechaInicio());
				paramC.setIdparemetroscampania(param.getIdparemetroscampania());
				
				this.parametrosCampService.updateParemetroscampania(paramC);			
				
				//-----------------falta establecer Sucursales e Hijos --------------------
				
				//editando las  patologias
					// se eliminan las que estan
				this.patologiaCService.deltePatologiaByIdCampania(campaniaPersist.getIdcampania());
				
				
								
				//se persisten las nuevas
				for(String e: this.getSelectedPatologias() ){
														
					Patologia pTemp = patologiaSevice.obtenerPatologiaPorDescripcion(e);
					//crea la llave primaria de Patologiacampania
					PatologiacampaniaPK pk = new PatologiacampaniaPK();
					pk.setIdcampania(campaniaPersist.getIdcampania());
					pk.setIdpatologia(pTemp.getIdpatologia());
					
					Patologiacampania patC = new Patologiacampania();
					patC.setId(pk);
					patC.setCampania(campaniaPersist);
					patC.setPatologia(pTemp);
					patC.setFecha(fechaInicio);
					
					this.patologiaCService.updatePatologiacampania(patC);
					
				}
				
				FacesContext fContext = FacesContext.getCurrentInstance();
				
				fContext.getExternalContext().getSessionMap().remove("smsCampaniaBeanEdit");
				
				FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Campaña Actulizada!", "Campaña Actualizada Exitosamente"));
				FacesContext facesContext = FacesContext.getCurrentInstance();
				@SuppressWarnings("static-access")
				Flash flash = facesContext.getCurrentInstance().getExternalContext().getFlash();
				flash.setKeepMessages(true);
				
				//outcome
				return "smscampanialist?faces-redirect=true";
				
			} catch (Exception e) {
				System.out.println("ALGO PASO EN LA PERSISTENCIA DEL METODO");
				e.printStackTrace();
				
				FacesContext fContext = FacesContext.getCurrentInstance();				
				fContext.getExternalContext().getSessionMap().remove("smsCampaniaBeanEdit");
				
				fContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Campaña no fue Actualizada!", "Se presento un error en la Actualizacion de la campaña"));
				FacesContext facesContext = FacesContext.getCurrentInstance();
				@SuppressWarnings("static-access")
				Flash flash = facesContext.getCurrentInstance().getExternalContext().getFlash();
				flash.setKeepMessages(true);
				
				return null;
			}
		}else{			
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje Demasiado Corto", "El mensaje es muy corto!"));			
			return null;
		}
		
	}
	
	
	/**
	 * Formula la consulta y establece el List de afiliados de la consulta
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String obtenerConsulta(){
		//formulacion de la consulta
		String queryString = 
				"select a.idafiliado, a.celular from Afiliado a where 1 = 1 ";
		
		if( selectedPatologias.length >= 1 ){
			queryString = "select a.idafiliado, a.celular from Afiliado a inner join a.afiliadopatologias ap where 1 = 1 and ( " ;
			
			String patNucleo = "";
			for( int i = 0 ; i < selectedPatologias.length; i++ ){
				Patologia pTemp = patologiaSevice.obtenerPatologiaPorDescripcion(selectedPatologias[i]);
				
				if( i ==  (selectedPatologias.length-1) ){
					queryString += "ap.patologia.idpatologia = " + pTemp.getIdpatologia() + " )";
					patNucleo += "pn.patologia.idpatologia = " + pTemp.getIdpatologia() + " )";
				}else{
					queryString += "ap.patologia.idpatologia = " + pTemp.getIdpatologia() + " or ";
					patNucleo += "pn.patologia.idpatologia = " + pTemp.getIdpatologia() + " or ";
				}
			}//fin del for
			
			queryString += " or a.idafiliado in (select pn.afiliado.idafiliado from Afiliadopatologianucleo pn where ";
			queryString += patNucleo;		
		}// fin de patologia
		
		if( this.getSelectedSucursal().length >= 1 ){
			queryString += " and ( ";
			for( int i = 0 ; i < getSelectedSucursal().length ; i++){
				if( i ==  (getSelectedSucursal().length-1) ){
					queryString += " a.sucursal.codigointerno = '" + selectedSucursal[i].getCodigointerno() + "' ) ";
				}else{
					queryString += " a.sucursal.codigointerno = '" + selectedSucursal[i].getCodigointerno() + "' or ";
				}
			}
		}// fin sucursal
		
		if( !this.getSexo().equals("t") ){
			queryString += "and a.sexo = '" + this.getSexo() +  "' ";
		}
		
		//si tiene hijos
		if( this.getSelectedHijos().length >= 1 ){
			queryString += "and ( ";
			for(int i = 0; i < getSelectedHijos().length; i++ ){
				
				if( i ==  (getSelectedHijos().length-1) ){
					queryString += " a."+ selectedHijos[i] + " = 1 ) ";
				}else{
					queryString += " a."+ selectedHijos[i] + " = 1 or ";
				}
			}
		}					
		queryString += "and  a.edad between " + this.edadIni + " and " + this.edadFin + " ";	
		queryString += "and length( a.celular ) = 10 and substring(a.celular,1,1) = '3' ";
		queryString += "group by a.idafiliado ";
		
		Query query = em.createQuery(queryString);
			
		try {
			this.afiliadoList = query.getResultList();
		} catch (NoResultException e) {
			System.out.println("No encontrado");			
		}		
		return queryString;		
	}
	
	
	
	/**
	 * Resetea todos los campos del formulario
	 */
	public void resetCampania(){
		this.setCampaniaSelected(new Campania());
		this.setNombreCampania("");
		this.setSelectedSucursal(null);
		this.setSexo("t");
		this.setEdadIni(18);
		this.setEdadFin(50);
		this.setAudiencia("");
		this.setSelectedPatologias(null);
		this.setSelectedHijos(null);
		Date dateTemp = this.AddingHHToDate(new Date(), 1);
		this.setFechaInicio( dateTemp );		
		this.setContenidoSms("");
	}
	
	public String cancelarCampania(){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("smsCampaniaBeanEdit");
		return "smscampanialist?faces-redirect=true";
	}
	
	
		

}
