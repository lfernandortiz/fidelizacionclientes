package com.dromedicas.view.beans;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

import com.dromedicas.domain.Smsplantilla;
import com.dromedicas.service.SmsPlantillaService;

@ManagedBean(name="plantillaSmsBeanList")
@ViewScoped
public class PlantillaSmsBeanList {
	
	@EJB
	private SmsPlantillaService smsService;
	
	private List<Smsplantilla> smsPlantillaList;
	private Smsplantilla smsPlantillaSelected;
	private String criterioBusqueda;
	private String longiMensajeSMS;
	private String mensajeTemp;


	public PlantillaSmsBeanList(){}
	
	
	@PostConstruct
	public void init(){
		System.out.println("Function PostConstruct SMS LIST");	
		smsPlantillaSelected = new Smsplantilla();
		this.smsPlantillaList = this.smsService.findAllSmsplantillas();
		
	}
	
	public List<Smsplantilla> getSmsPlantillaList() {
		return smsPlantillaList;
	}


	public void setSmsPlantillaList(List<Smsplantilla> smsPlantillaList) {
		this.smsPlantillaList = smsPlantillaList;
	}
	
	
	public Smsplantilla getSmsPlantillaSelected() {
		return smsPlantillaSelected;
	}


	public void setSmsPlantillaSelected(Smsplantilla smsPlantillaSelected) {
		this.smsPlantillaSelected = smsPlantillaSelected;
	}
	
	public String getCriterioBusqueda() {
		return criterioBusqueda;
	}


	public void setCriterioBusqueda(String criterioBusqueda) {
		this.criterioBusqueda = criterioBusqueda;
	}


	public String getLongiMensajeSMS() {
		return longiMensajeSMS;
	}


	public void setLongiMensajeSMS(String longiMensajeSMS) {
		this.longiMensajeSMS = longiMensajeSMS;
	}

	public String getMensajeTemp() {
		return mensajeTemp;
	}


	public void setMensajeTemp(String mensajeTemp) {
		this.mensajeTemp = mensajeTemp;
	}


	/**
	 * Metodos de la vista
	 */
	
	/*
	 * Metodos del List
	 */
	public void buscarPlantillaSMS(){
		this.smsPlantillaList = this.smsService.bucarSMSByFields(this.criterioBusqueda);
	}
	
	public void crearPlantillaSMSList(){
		this.smsPlantillaSelected.setDescripcion("");
		this.smsPlantillaSelected.setSmscontenido("");
	}
	
	public void crearPlantillaSMS(){
		
		try {
			
			System.out.println("Descripcion: " + this.smsPlantillaSelected.getDescripcion());
			System.out.println("Contenido: " + this.getMensajeTemp());
			
			
			this.smsPlantillaSelected.setDescripcion(this.smsPlantillaSelected.getDescripcion());
			this.smsPlantillaSelected.setSmscontenido(this.smsPlantillaSelected.getSmscontenido());
			
			//this.smsService.updateSmsplantilla(smsPlantillaSelected);
			
			
			//cierra el cuado de dialogo
			RequestContext.getCurrentInstance().execute("PF('smsDialogCrear').hide();");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("globalMessagex", new FacesMessage(FacesMessage.SEVERITY_FATAL,
					"Registro NO Creado! ", " Plantilla SMS NO fue creada."));
		}
		
	}
	
	public void editarPlantillaSMS(){
		System.out.println("Contenido SMS: " + this.smsPlantillaSelected.getSmscontenido());
	}
	
	public void cancelarPlantillaSmsList() {
		// Reset campos formulario de busqueda
		this.setCriterioBusqueda("");

		// Reset DataTable Object (reestablecer la paginacion)
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("formppal:smsDetail");
		dataTable.reset();

		// Consulta nuevamente el List
		this.smsPlantillaList = this.smsService.findAllSmsplantillas();

	}
	
	/*
	 * Metodos del Edit
	 */
	
	public void creaPlantillaSMS(){				
		//RequestContext context = RequestContext.getCurrentInstance();
		//context.execute("PF('myDialogVar').show();");
		//RequestContext.execute("dialogWidgetVar.hide()");
	}
	
	public void cancelarCrearSMS(){
		this.smsPlantillaSelected.setDescripcion("");
		this.smsPlantillaSelected.setSmscontenido("");
		this.setLongiMensajeSMS("");
	}
	
	
	
	public void analizaSMS(){
		// La longitud maxima de caracteres a enviar por mensaje SMS es de 160 caracteres
		// segun el proveedor del servicio.
		
		// El metodo longitudMensaje elimina del mensaje las variables "${variable}"
		int longitud =  this.longitudMensaje( this.smsPlantillaSelected.getSmscontenido()) ;
		int restante = 160 - longitud;
		
		if( longitud > 160){
			this.setLongiMensajeSMS(longitud  + " Mensaje muy extenso");
		}else{
			this.setLongiMensajeSMS( restante  + (restante == 1 ? " Caracter restante" : " Caracteres restantes") );
		}
	}
	
	
	
	/**
	 * Reemplaza las variables "${[a-z] [0-9]}" por espacio vacio 
	 * para dar la longitud del mensaje sn las variables
	 * @param mensaje
	 * @return
	 */
	private int longitudMensaje(String mensaje){
		Pattern p = Pattern.compile("(\\{.*?\\})");
		 Matcher m = p.matcher(mensaje);		 
		 StringBuffer sb = new StringBuffer();
		 while (m.find()) {
		     m.appendReplacement(sb, "");
		 }
		 m.appendTail(sb);
		 return sb.toString().length();
	}
	
	
	
	

}
