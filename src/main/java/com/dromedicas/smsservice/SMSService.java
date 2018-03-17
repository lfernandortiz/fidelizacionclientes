package com.dromedicas.smsservice;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.Smsplantilla;
import com.dromedicas.service.RegistroNotificacionesService;
import com.dromedicas.servicio.rest.RespuestaSMSWrap;
import com.dromedicas.servicio.rest.SaldoHablameWrap;
import com.dromedicas.util.ExpresionesRegulares;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@Stateless
@TransactionManagement (TransactionManagementType.BEAN) 
public class SMSService {
	
	@EJB
	private ExpresionesRegulares regex;
	
	@EJB
	private RegistroNotificacionesService regNotificaciones;
	
	private final String urlServicio = "https://api.hablame.co/sms/envio?";
	private final String cliente = "10010333";
	private final String apiKey = "4z1MlW6lsQHKiJ6x909E7zS8Rp5PRF";
	
	
	public int enviarSMSDirecto(String numero, String mensaje, String referencia ){
		
		int resultado = 0;
		
		try {
			// Thread.sleep(5500);
			
			//Objeto cliente que consume el servicio
			Client client = Client.create();
			String url = urlServicio+"api="+ apiKey+"&cliente="+cliente+"&numero=" +
					numero + "&sms=" +  mensaje +(!referencia.equals("") ? "" : "&referencia=" + referencia);
			
			//System.out.println("URL SMS: " + url);
			
			WebResource webResource = client.resource( url.replace(" ",	"%20") );
			RespuestaSMSWrap response = webResource.accept("application/json").get(RespuestaSMSWrap.class);
			
			resultado = response.getResultado();
			
			if( resultado == 0 ){
				System.out.println("Mensaje enviado exitosamente");
			}else{
				System.out.println(":-( No se pudo enviar el SMS");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	
	public void envioSMSCumpleanos(List<Afiliado> afiliadoList, Smsplantilla plantilla){
		
		String mensaje = "";
		
		for(Afiliado af: afiliadoList){		
			mensaje = plantilla.getSmscontenido();
			
			if( mensaje.contains("${genero}")){
				mensaje = regex.reemplazaMensaje(mensaje, "genero", af.getSexo().equals("M") ? "o"  : "a" );
			}
			
			if( mensaje.contains("${nombrecliente}")){
				mensaje = regex.reemplazaMensaje(mensaje, "nombrecliente", af.getNombres());
			}
						
			int estado = this.enviarSMSDirecto(af.getCelular(), mensaje, "cumpleanios");
			//registro en auditor del envio del sms
			this.regNotificaciones.auditarSMSEnviado(af, mensaje, "Cumpleanos", estado);
			
			// sleep solicitado por el por el proveedor de sms
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// fin del for
	}
	
	
	
	public void envioSMSCampania(Afiliado afiliado, String mensaje){
		
		int estado = this.enviarSMSDirecto(afiliado.getCelular(), mensaje, "masivo");
		
		//registro en auditor del envio del sms
		this.regNotificaciones.auditarSMSEnviado(afiliado, mensaje, "SMS Directo", estado);		
		
	}
	
	
	
	
	/**
	 * Con base en el costo del mensaje SMS del operador 
	 * @return
	 */
	public Integer obtenerMensajesDisponibles(){
		
		Integer mensajesDisponibles = null;
		
		try {
			String url = "https://api.hablame.co/saldo/consulta?api=4z1MlW6lsQHKiJ6x909E7zS8Rp5PRF&cliente=10010333";
			
			//Objeto cliente que consume el servicio
			Client client = Client.create();
			WebResource webResource = client.resource( url.replace(" ",	"%20") );
			SaldoHablameWrap response = webResource.accept("application/json").get(SaldoHablameWrap.class);
			
			Double saldo  = Double.parseDouble( response.getSaldo() );			
			mensajesDisponibles = (saldo.intValue() / 18 );//--> Aca se debe reemplazar por una consulta del costo del sms
			
		} catch (Exception e) {
			System.out.println("ERROR AL OBTENER SALDO DE MENSAJES");
			e.printStackTrace();
		}
		return mensajesDisponibles ;
	}

	
	
	
	
}
