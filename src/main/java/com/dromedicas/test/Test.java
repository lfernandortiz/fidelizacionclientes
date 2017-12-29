package com.dromedicas.test;


import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.dromedicas.mailservice.CustomMimeMessage;
import com.dromedicas.mailservice.JavaMailService;
import com.dromedicas.util.EncodingUtil;

public class Test {

	public static void main(String[] args) {
		try {
			//enviarEmail();
			leerMensajes();		
			
		} catch (Exception e) {
			// TODO: handle exception
		}		
	}
		
	public static void enviarEmail(){
		//Objeto de servicio Email
		JavaMailService  service = new JavaMailService();
		//Clase generica para componeer email
		CustomMimeMessage message;
		String messageId = EncodingUtil.generateUUID();
		
		try {
			//Html personalizado para el mensaje
			File inputHtml = new File(
					"C:/FarmapuntosEmail/basic.html");
			Document doc = Jsoup.parse(inputHtml, "UTF-8");
			
			//Parametros del mensaje
			message = new CustomMimeMessage(service.createUserMailSession(), messageId+"@"+service.getServerSMTPHost() );
			message.setOrigen("pruebassistemas@dromedicas.com.co");
			message.setText(doc.html());
			message.setDestino("sistemas2@dromedicas.com.co");
			message.setSubject("Mensaje Farmapuntos");	
			
			//envio del mensajse		
			System.out.println("Enviando");
			service.sendEmail(message.buildText("HTML"));	
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

	
	public static void leerMensajes(){
		try {
			
			
			
			
			//List<Message> inboxM = service.getNewMessages();//Mensajes recibidos
			
			//ArrayList<Message> archivo = new ArrayList<Message>();//coleccion de mensajes errados
			
			//System.out.println("tamanio:" + inboxM.size());
			
			
			String emailAddres = null;
			String messageId = null;
			
//			System.out.println(service.isFailedMessage(inboxM.get(inboxM.size()-1)));
//			emailAddres = service.getEmailFailed(inboxM.get(inboxM.size()-1));			
//			messageId = service.messageId(inboxM.get(inboxM.size()-1));
//			System.out.println("Email Fallido>>>\t"+emailAddres+" Tocken>>>\t" + messageId);
			
			
//			for(Message m: inboxM ){		
//				//metodo perdicado que determina si el mensaje es fallido
//				if(service.isFailedMessage(m)){		
//					
//					//metodo que devuelve la direccion email
//					emailAddres = service.getEmailFailed(m);
//					//metodo que devuelve el message-id
//					messageId = service.messageId(m);
//					//añado el mensaje a la coleecion de leidos					
//					archivo.add(m);
//					
//					//copio el mensaje a la carperta de leidos					
//					service.copiarMensajes(service.getMailInbox(), service.getAchivedFolder(), 
//							archivo.toArray(new Message[0]) );
//					//borro del inbox el mensaje actual
//					
//					service.deleteMessage(m);
//					System.out.println("Email Fallido>>>\t"+emailAddres+" Tocken>>>\t" + messageId);
//				}
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
