package com.dromedicas.smsservice;

import javax.ejb.Stateless;

import com.dromedicas.servicio.rest.RespuestaSMSWrap;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@Stateless
public class SMSService {
	
	private final String urlServicio = "https://api.hablame.co/sms/envio?";
	private final String cliente = "10010333";
	private final String apiKey = "4z1MlW6lsQHKiJ6x909E7zS8Rp5PRF";
	
	
	public String enviarSMSDirecto(String numero, String mensaje, String referencia ){
		
		String respuesta = "";
		
		try {
			// Thread.sleep(5500);
			
			//Objeto cliente que consume el servicio
			Client client = Client.create();
			String url = urlServicio+"api="+ apiKey+"&cliente="+cliente+"&numero=" +
					numero + "&sms=" +  mensaje +(!referencia.equals("") ? "" : "&referencia=" + referencia);
			System.out.println("URL SMS: " + url);
			
			WebResource webResource = client.resource( url.replace(" ",	"%20") );
			RespuestaSMSWrap response = webResource.accept("application/json").get(RespuestaSMSWrap.class);
			
			int resultado = response.getResultado();
			
			if( resultado == 0 ){
				System.out.println("Mensaje enviado exitosamente");
			}else{
				System.out.println(":-( No se pudo enviar el SMS");
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return respuesta;
	}

}
