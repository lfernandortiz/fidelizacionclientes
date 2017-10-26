package com.dromedicas.mailservice;

import java.io.File;
import java.util.Properties;

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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.dromedicas.domain.Afiliado;


@ManagedBean(name="mailAlert")
@SessionScoped
@Stateless
public class EnviarEmailAlertas {
	
	public boolean enviarEmailAlertaVentas(Afiliado afiliado) {
		
		String urlConfirmacion = "http://www.puntosfarmanorte.com.co/seccion/actualizacion.html?documento=" + afiliado.getDocumento();
		
		System.out.println("Clase enviar Email Alerta Afilidaod");
		try{
			
			ServletContext servletContext = (ServletContext) FacesContext
			        .getCurrentInstance().getExternalContext().getContext();
			
			File inputHtml = new File(servletContext.getRealPath("emailhtml/registropuntosf.html"));
			// Asginamos el archivo al objeto analizador Document
			Document doc = Jsoup.parse(inputHtml, "UTF-8");
			
			// obtengo los id's del DOM a los que deseo insertar los valores
			// mediante el metodo append() se insertan los valores obtenidos de
			// la consulta
			Element nomAfiliado = doc.select("span#nombreAfiliado").first();
			nomAfiliado.append(afiliado.getNombres()+" "+afiliado.getApellidos());

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
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(
					"contacto@puntosfarmanorte.com.co"));
			message.setRecipients(Message.RecipientType.BCC, addressTo);
			message.setSubject("Puntos Farmanorte | Confirmacion de suscripcion");
			message.setContent(doc.html(), "text/html; charset=utf-8");

			//Envia el correo
			Transport t = session.getTransport("smtp");
			t.connect("contacto@puntosfarmanorte.com.co", "Dromedicas2013.");
			t.sendMessage(message, message.getAllRecipients());
			
			// Cierre de la conexion
			t.close();
			System.out.println("Conexion cerrada");
			
		}catch(Exception e){
			System.out.println("Falla en el envio del correo:");
			e.printStackTrace();
			return false;
		}
		return true;		
		
	}

}
