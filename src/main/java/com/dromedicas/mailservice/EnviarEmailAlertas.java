package com.dromedicas.mailservice;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.dromedicas.domain.Afiliado;
import com.dromedicas.domain.BalancePuntos;
import com.dromedicas.domain.Transaccion;
import com.dromedicas.service.OperacionPuntosService;
import com.dromedicas.util.ExpresionesRegulares;
import com.vdurmont.emoji.EmojiParser;

@Remote
@ManagedBean(name="mailAlert")
@SessionScoped
@Stateless
public class EnviarEmailAlertas {
	
	@Context ServletContext context;
	
	@EJB
	private ExpresionesRegulares regex;
	
	@EJB
	private OperacionPuntosService puntosService;
	
	public boolean enviarEmailAlertaVentas(Afiliado afiliado) {
		
		String urlConfirmacion = "http://www.puntosfarmanorte.com.co/seccion/actualizacion.html?id=" + afiliado.getKeycode();
		
		System.out.println("Clase enviar Email Alerta Afilidaod");
		try{
			
			ServletContext servletContext = null;
						
			try {
				servletContext = (ServletContext) FacesContext
				        .getCurrentInstance().getExternalContext().getContext();
			} catch (Exception e) {
				servletContext = context;
			}
			
			File inputHtml = new File(servletContext.getRealPath("emailhtml/registropuntosf.html"));
			// Asginamos el archivo al objeto analizador Document
			Document doc = Jsoup.parse(inputHtml, "UTF-8");
			
			// obtengo los id's del DOM a los que deseo insertar los valores
			// mediante el metodo append() se insertan los valores obtenidos del
			// objeto obtenido como parametro
			Element genero = doc.select("span#genero").first();
			genero.append(afiliado.getSexo().equals("M")? "Sr.": "Sra." );
			
			Element nomAfiliado = doc.select("span#nombreAfiliado").first();
			nomAfiliado.append(afiliado.getNombres() + " " + afiliado.getApellidos());

			// Url de confirmacion de correo para el elemento buton 
			Element btn = doc.select("a#linkconfirm").first();
			btn.attr("href", urlConfirmacion);
			
						
			//Element img = doc.select("img#pixelcontrol").first();
			//img.attr("src", url);
			
			// Propiedades de la conexión
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "deus.wnkserver6.com");
			props.setProperty("mail.smtp.port", "25");// puerto de salida, de
			// entrada 110
			props.setProperty("mail.smtp.user",
								"contacto@puntosfarmanorte.com.co");
			props.setProperty("mail.smtp.auth", "true");
			props.put("mail.transport.protocol.", "smtp");

			// Preparamos la sesion
			Session session = Session.getDefaultInstance(props);
			// Construimos el mensaje

			
			// multiples direcciones
			String[] to = { afiliado.getEmail() };
			
			
			// arreglo con las direcciones de correo
			InternetAddress[] addressTo = new InternetAddress[to.length];
			for (int i = 0; i < addressTo.length; i++) {
				addressTo[i] = new InternetAddress(to[i]);
			}
						
			// se compone el mensaje (Asunto, cuerpo del mensaje y direccion origen)
			final MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress( 
					"contacto@puntosfarmanorte.com.co", "Puntos Farmanorte"));
			message.setRecipients(Message.RecipientType.BCC, addressTo);
			//Emojis :-)			
			String subjectEmojiRaw = ":large_blue_circle: Confirmacion de suscripcion :memo:";
			String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);			
				
			message.setSubject( subjectEmoji , "UTF-8");
			message.setContent(doc.html(), "text/html; charset=utf-8");

			//Envia el correo
			final Transport t = session.getTransport("smtp");			
			//asigno un hilo exclusivo a la conexion y envio del mensaje
			//dado que el proveedor de correo es muy lento para establecer
			//la conexion
			new Thread(new Runnable() {
			    public void run() {
			    	try {
			    		t.connect("contacto@puntosfarmanorte.com.co", "Dromedicas2013.");
						t.sendMessage(message, message.getAllRecipients());
						// Cierre de la conexion
						t.close();
				    
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }	
			}).start();
			
			System.out.println("Conexion cerrada");
			
		}catch(Exception e){
			System.out.println("Falla en el envio del correo:");
			e.printStackTrace();
			return false;
		}
		return true;		
		
	}
	
	
	public boolean emailAcumulacionPuntos(Afiliado afiliado, int ganados, BalancePuntos balance ) {
		//nombrecliente
		//puntostx
		//acumulados
		//redimir
			
		System.out.println("Clase enviar Email Alerta de compra ");
		try{
			ServletContext servletContext = null;
			
			try {
				servletContext = (ServletContext) FacesContext
				        .getCurrentInstance().getExternalContext().getContext();
			} catch (Exception e) {
				servletContext = context;
			}
			
			File inputHtml = new File(servletContext.getRealPath("emailhtml/emailcompra.html"));
			// Asginamos el archivo al objeto analizador Document
			Document doc = Jsoup.parse(inputHtml, "UTF-8");
			
			// obtengo los id's del DOM a los que deseo insertar los valores.
			// Mediante el metodo append() se insertan los valores obtenidos de
			// la consulta
			Element nomAfiliado = doc.select("span#nombrecliente").first();
			nomAfiliado.append(regex.puntoSegundoNombre(afiliado.getNombres()+" "+afiliado.getApellidos()) );
			
			Element puntosTx = doc.select("span#puntostx").first();
			puntosTx.append(Integer.toString(ganados));
			
			Element acumulados = doc.select("span#acumulados").first();
			acumulados.append(Integer.toString(balance.getAcumulados()));
			
			Element redimir = doc.select("span#redimir").first();
			redimir.append(Integer.toString(balance.getDisponiblesaredimir()));
			
						
			//Element img = doc.select("img#pixelcontrol").first();
			//img.attr("src", url);
			
			// Propiedades de la conexión
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "deus.wnkserver6.com");
			props.setProperty("mail.smtp.port", "25");// puerto de salida, de
			// entrada 110
			props.setProperty("mail.smtp.user",
								"contacto@puntosfarmanorte.com.co");
			props.setProperty("mail.smtp.auth", "true");
			props.put("mail.transport.protocol.", "smtp");

			// Preparamos la sesion
			Session session = Session.getDefaultInstance(props);
			// Construimos el mensaje
			
			InternetAddress addressTo = 
					new InternetAddress(afiliado.getEmail());
			
						
			// se compone el mensaje (Asunto, cuerpo del mensaje y direccion origen)
			final MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(
					"contacto@puntosfarmanorte.com.co" , "Puntos Farmanorte"));
			message.setRecipient(Message.RecipientType.TO, addressTo);			
			//Emojis :-)			
			String subjectEmojiRaw = ":pill: Puntos Farmanorte :syringe:";
			String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);
			
			message.setSubject(subjectEmoji  + "| Acumulaste: " + ganados , "UTF-8");
			message.setContent(doc.html(), "text/html; charset=utf-8");

			System.out.println("Enviando Correo....");
			//Envia el correo
			final Transport t = session.getTransport("smtp");
			//asigno un hilo exclusivo a la conexion y envio del mensaje
			//dado que el proveedor de correo es muy lento para establecer
			//la conexion
			new Thread(new Runnable() {
			    public void run() {
			    	try {
			    		t.connect("contacto@puntosfarmanorte.com.co", "Dromedicas2013.");
						t.sendMessage(message, message.getAllRecipients());
						// Cierre de la conexion
						t.close();
				    
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }	
			}).start();
			System.out.println("Conexion cerrada");
			
		}catch(Exception e){
			System.out.println("Falla en el envio del correo:");
			e.printStackTrace();
			return false;
		}
		return true;		
		
	}
		
	
	public boolean emailNotificacionCompra(List<Transaccion> txList ) {
		
		System.out.println("Clase enviar Email Alerta de compra scheduling ");
		try{
			
			//se optiene el contexto de la aplicacion
			//para calificar la ruta de acceso del archivo 
			//template HTML del email
			ServletContext servletContext = null;			
			try {
				servletContext = (ServletContext) FacesContext
				        .getCurrentInstance().getExternalContext().getContext();
			} catch (Exception e) {
				servletContext = context;
			}
			
			// Propiedades de la conexión
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "deus.wnkserver6.com");
			props.setProperty("mail.smtp.port", "25");// puerto de salida, de
			// entrada 110
			props.setProperty("mail.smtp.user", "contacto@puntosfarmanorte.com.co");
			props.setProperty("mail.smtp.auth", "true");
			props.put("mail.transport.protocol.", "smtp");
			
			// Preparamos la sesion
			Session session = Session.getDefaultInstance(props);
			System.out.println("Enviando Correos....");
			//Envia el correo
			final Transport t = session.getTransport("smtp");
			t.connect("contacto@puntosfarmanorte.com.co", "Dromedicas2013.");
			
			for(Transaccion tx : txList){
				Afiliado afiliado = tx.getAfiliado();
				BalancePuntos balance = puntosService.consultaPuntos(afiliado);
				
				File inputHtml = new File(servletContext.getRealPath("emailhtml/emailcompra.html"));
				// Asginamos el archivo al objeto analizador Document
				Document doc = Jsoup.parse(inputHtml, "UTF-8");
				// obtengo los id's del DOM a los que deseo insertar los valores.
				// Mediante el metodo append() se insertan los valores obtenidos de
				// la consulta
				Element nomAfiliado = doc.select("span#nombrecliente").first();
				nomAfiliado.append(regex.puntoSegundoNombre(afiliado.getNombres()+" "+afiliado.getApellidos()) );
				
				//Puntos ganados en la Tx actual
				Element puntosTx = doc.select("span#puntostx").first();
				puntosTx.append(Integer.toString( tx.getPuntostransaccion() ));
				
				//Se obtienen los puntos acumulados desde el balance 
				Element acumulados = doc.select("span#acumulados").first();
				acumulados.append(Integer.toString(balance.getAcumulados()));
				
				//Se obtienen los puntos disponiblea para redimir
				Element redimir = doc.select("span#redimir").first();
				redimir.append(Integer.toString(balance.getDisponiblesaredimir()));
				
				InternetAddress addressTo = 
						new InternetAddress(afiliado.getEmail());	

							
				// se compone el mensaje (Asunto, cuerpo del mensaje y direccion origen)
				final MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(
						"contacto@puntosfarmanorte.com.co" , "Puntos Farmanorte"));
				message.setRecipient(Message.RecipientType.TO, addressTo);			
				//Emojis :-)			
				String subjectEmojiRaw = 
						":pill: Acumulaste: " + tx.getPuntostransaccion() +" Puntos :large_blue_circle:";
				String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);
				
				message.setSubject(subjectEmoji , "UTF-8");
				message.setContent(doc.html(), "text/html; charset=utf-8");

				t.sendMessage(message, message.getAllRecipients());
				// Cierre de la conexion
				
			}
			t.close();
			System.out.println("Conexion cerrada");
			
		}catch(Exception e){
			System.out.println("Falla en el envio del correo:");
			e.printStackTrace();
			return false;
		}
		return true;		
		
	}
	
	
	
public boolean emailNotificacionReferido(final List<String> emailList ) {
		
		System.out.println("Clase enviar Email Alerta referidos ");
		try{
			
			//se optiene el contexto de la aplicacion
			//para calificar la ruta de acceso del archivo 
			//template HTML del email
			ServletContext servletContext = null;			
			try {
				servletContext = (ServletContext) FacesContext
				        .getCurrentInstance().getExternalContext().getContext();
			} catch (Exception e) {
				servletContext = context;
			}
			
			// Propiedades de la conexión
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "deus.wnkserver6.com");
			props.setProperty("mail.smtp.port", "25");// puerto de salida, de
			// entrada 110
			props.setProperty("mail.smtp.user", "contacto@puntosfarmanorte.com.co");
			props.setProperty("mail.smtp.auth", "true");
			props.put("mail.transport.protocol.", "smtp");
			
			// Preparamos la sesion
			final Session session = Session.getDefaultInstance(props);
			System.out.println("Enviando Correos....");
			//Envia el correo
			final ServletContext servletContextFinal =servletContext;
			new Thread(new Runnable() {
			    public void run() {
			    	try {
			    		Transport t = session.getTransport("smtp");
						t.connect("contacto@puntosfarmanorte.com.co", "Dromedicas2013.");
						
						for(String dir : emailList){
							
							File inputHtml = new File(servletContextFinal.getRealPath("emailhtml/emailreferido.html"));
							// Asginamos el archivo al objeto analizador Document
							Document doc = Jsoup.parse(inputHtml, "UTF-8");
							
							InternetAddress addressTo =  new InternetAddress(dir);	
										
							// se compone el mensaje (Asunto, cuerpo del mensaje y direccion origen)
							final MimeMessage message = new MimeMessage(session);
							message.setFrom(new InternetAddress(
									"contacto@puntosfarmanorte.com.co" , "Puntos Farmanorte"));
							message.setRecipient(Message.RecipientType.TO, addressTo);			
							//Emojis :-)			
							String subjectEmojiRaw = 
									":large_blue_circle: Afiliate a Puntos Farmanorte";
							String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);
							
							message.setSubject(subjectEmoji , "UTF-8");
							message.setContent(doc.html(), "text/html; charset=utf-8");

							t.sendMessage(message, message.getAllRecipients());
							
						}
						// Cierre de la conexion
						t.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }	
			}).start();
			
			System.out.println("Conexion cerrada");
			
		}catch(Exception e){
			System.out.println("Falla en el envio del correo de referidos");
			e.printStackTrace();
			return false;
		}
		return true;		
		
	}
	
}
