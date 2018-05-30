package com.dromedicas.mailservice;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
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
import com.dromedicas.domain.Campania;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.domain.Transaccion;
import com.dromedicas.service.OperacionPuntosService;
import com.dromedicas.service.RegistroNotificacionesService;
import com.dromedicas.util.ExpresionesRegulares;
import com.sun.mail.imap.protocol.FLAGS;
import com.vdurmont.emoji.EmojiParser;

@Remote
@ManagedBean(name="mailAlert")
@SessionScoped
@Stateless
@TransactionManagement (TransactionManagementType.BEAN) 
public class EnviarEmailAlertas {
	
	@Context ServletContext context;
	
	@EJB
	private ExpresionesRegulares regex;
	
	@EJB
	private OperacionPuntosService puntosService;
	
	@EJB
	private RegistroNotificacionesService registroNotificacion;
	
	public String enviarEmailAlertaVentas(Afiliado afiliado) {

		String urlConfirmacion = "http://www.puntosfarmanorte.com.co/seccion/actualizacion.html?id="
				+ afiliado.getKeycode();

		String contenidoEmail = null;
		
		System.out.println("Clase enviar Email Alerta Afilidaod");
		try {
			ServletContext servletContext = null;

			try {
				servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
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
			genero.append(afiliado.getSexo().equals("M") ? "Sr." : "Sra.");

			Element nomAfiliado = doc.select("span#nombreAfiliado").first();
			nomAfiliado.append(afiliado.getNombres() + " " + afiliado.getApellidos());

			Element anioactual = doc.select("span#anioactual").first();
			anioactual.append(new SimpleDateFormat("yyyy").format(new Date()));

			// Url de confirmacion de correo para el elemento buton
			Element btn = doc.select("a#linkconfirm").first();
			btn.attr("href", urlConfirmacion);

			// Element img = doc.select("img#pixelcontrol").first();
			// img.attr("src", url);

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
			// Construimos el mensaje

			// multiples direcciones
			String[] to = { afiliado.getEmail() };

			// arreglo con las direcciones de correo
			InternetAddress[] addressTo = new InternetAddress[to.length];
			for (int i = 0; i < addressTo.length; i++) {
				addressTo[i] = new InternetAddress(to[i]);
			}

			// se compone el mensaje (Asunto, cuerpo del mensaje y direccion
			// origen)
			final MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("contacto@puntosfarmanorte.com.co", "Puntos Farmanorte"));
			message.setRecipients(Message.RecipientType.TO, addressTo);
			// Emojis :-)
			String subjectEmojiRaw = ":large_blue_circle: Confirmacion de suscripcion :memo:";
			String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);

			message.setSubject(subjectEmoji, "UTF-8");
			message.setContent(doc.html(), "text/html; charset=utf-8");

			message.setFlag(FLAGS.Flag.RECENT, true);

			contenidoEmail = doc.html();
			// Envia el correo
			final Transport t = session.getTransport("smtp");
			// asigno un hilo exclusivo a la conexion y envio del mensaje
			// dado que el proveedor de correo es muy lento para establecer
			// la conexion
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

		} catch (Exception e) {
			System.out.println("Falla en el envio del correo:");
			e.printStackTrace();
			return null;
		}
		return contenidoEmail;
	}
	
	

	public boolean emailAcumulacionPuntos(Afiliado afiliado, int ganados, BalancePuntos balance) {
		
		System.out.println("Clase enviar Email Alerta de compra ");
		try {
			// primero valida que el email registrado no este como rechazado
			if (afiliado.getEmailrechazado() == 0) {

				ServletContext servletContext = null;
				
				String urlConfirmacion = "http://www.puntosfarmanorte.com.co/seccion/actualizacion.html?id="
						+ afiliado.getKeycode();

				try {
					servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
							.getContext();
				} catch (Exception e) {
					servletContext = context;
				}

				File inputHtml = new File(servletContext.getRealPath("emailhtml/emailcompra.html"));
				// Asginamos el archivo al objeto analizador Document
				Document doc = Jsoup.parse(inputHtml, "UTF-8");

				// obtengo los id's del DOM a los que deseo insertar los
				// valores.
				// Mediante el metodo append() se insertan los valores obtenidos
				// de
				// la consulta
				Element nomAfiliado = doc.select("span#nombrecliente").first();
				nomAfiliado.append(regex.puntoSegundoNombre(afiliado.getNombres() + " " + afiliado.getApellidos()));

				Element puntosTx = doc.select("span#puntostx").first();
				puntosTx.append(Integer.toString(ganados));

				Element acumulados = doc.select("span#acumulados").first();
				acumulados.append(Integer.toString(balance.getAcumulados()));

				Element redimir = doc.select("span#redimir").first();
				redimir.append(Integer.toString(balance.getDisponiblesaredimir()));
				
//				Element btn = doc.select("a#unsuscribe").first();
//				btn.attr("href", urlConfirmacion);

				// Element img = doc.select("img#pixelcontrol").first();
				// img.attr("src", url);

				// Propiedades de la conexión
				Properties props = new Properties();
				props.setProperty("mail.smtp.host", "deus.wnkserver6.com");
				props.setProperty("mail.smtp.port", "25");// puerto de salida,
															// de
				// entrada 110
				props.setProperty("mail.smtp.user", "contacto@puntosfarmanorte.com.co");
				props.setProperty("mail.smtp.auth", "true");
				props.put("mail.transport.protocol.", "smtp");

				// Preparamos la sesion
				Session session = Session.getDefaultInstance(props);
				// Construimos el mensaje

				InternetAddress addressTo = new InternetAddress(afiliado.getEmail());

				// se compone el mensaje (Asunto, cuerpo del mensaje y direccion
				// origen)
				final MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("contacto@puntosfarmanorte.com.co", "Puntos Farmanorte"));
				message.setRecipient(Message.RecipientType.TO, addressTo);
				// Emojis :-)
				String subjectEmojiRaw = ":pill: Puntos Farmanorte :syringe:";
				String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);

				message.setSubject(subjectEmoji + "| Acumulaste: " + ganados, "UTF-8");
				message.setContent(doc.html(), "text/html; charset=utf-8");

				System.out.println("Enviando Correo....");
				// Envia el correo
				final Transport t = session.getTransport("smtp");
				// asigno un hilo exclusivo a la conexion y envio del mensaje
				// dado que el proveedor de correo es muy lento para establecer
				// la conexion
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

			} // fin del if de validacion de email rechazado

		} catch (Exception e) {
			System.out.println("Falla en el envio del correo:");
			e.printStackTrace();
			return false;
		}
		return true;

	}

	
	public boolean emailNotificacionCompra(List<Transaccion> txList) {

		System.out.println("Clase enviar Email Alerta de compra scheduling ");
		try {

			// se optiene el contexto de la aplicacion
			// para calificar la ruta de acceso del archivo
			// template HTML del email
			ServletContext servletContext = null;
			try {
				servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
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
			// Envia el correo
			final Transport t = session.getTransport("smtp");
			t.connect("contacto@puntosfarmanorte.com.co", "Dromedicas2013.");

			for (Transaccion tx : txList) {
				Afiliado afiliado = tx.getAfiliado();

				// si el email registrado no esta como rechazado se envia la
				// notificacion
				if (afiliado.getEmailrechazado() == 0) {
					BalancePuntos balance = puntosService.consultaPuntos(afiliado);

					File inputHtml = new File(servletContext.getRealPath("emailhtml/emailcompra.html"));
					// Asginamos el archivo al objeto analizador Document
					Document doc = Jsoup.parse(inputHtml, "UTF-8");
					// obtengo los id's del DOM a los que deseo insertar los valores.
					// Mediante el metodo append() se insertan los valores obtenidos de la consulta
					Element nomAfiliado = doc.select("span#nombrecliente").first();
					nomAfiliado.append(regex.puntoSegundoNombre(afiliado.getNombres() + " " + afiliado.getApellidos()));

					// Puntos ganados en la Tx actual
					Element puntosTx = doc.select("span#puntostx").first();
					puntosTx.append(Integer.toString(tx.getPuntostransaccion()));

					// Se obtienen los puntos acumulados desde el balance
					Element acumulados = doc.select("span#acumulados").first();
					acumulados.append(Integer.toString(balance.getAcumulados()));

					// Se obtienen los puntos disponiblea para redimir
					Element redimir = doc.select("span#redimir").first();
					redimir.append(Integer.toString(balance.getDisponiblesaredimir()));

					InternetAddress addressTo = new InternetAddress(afiliado.getEmail());

					// se compone el mensaje (Asunto, cuerpo del mensaje y
					// direccion origen)
					final MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress("contacto@puntosfarmanorte.com.co", "Puntos Farmanorte"));
					message.setRecipient(Message.RecipientType.TO, addressTo);
					// Emojis :-)
					String subjectEmojiRaw = ":pill: Acumulaste: " + tx.getPuntostransaccion()
							+ " Puntos :large_blue_circle:";
					String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);

					message.setSubject(subjectEmoji, "UTF-8");
					message.setContent(doc.html(), "text/html; charset=utf-8");
					
					this.registroNotificacion.auditarEmailEnviado(afiliado, doc.html(), "Compra o acumulacion de puntos");

					t.sendMessage(message, message.getAllRecipients());
				}
			}
			// Cierre de la conexion
			t.close();
			System.out.println("Conexion cerrada");

		} catch (Exception e) {
			System.out.println("Falla en el envio del correo:");
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public boolean emailNotificacionReferido(final List<String> emailList) {

		System.out.println("Clase enviar Email Alerta referidos ");
		try {

			// se optiene el contexto de la aplicacion
			// para calificar la ruta de acceso del archivo
			// template HTML del email
			ServletContext servletContext = null;
			try {
				servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
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
			// Envia el correo
			final ServletContext servletContextFinal = servletContext;
			new Thread(new Runnable() {
				public void run() {
					try {
						Transport t = session.getTransport("smtp");
						t.connect("contacto@puntosfarmanorte.com.co", "Dromedicas2013.");

						for (String dir : emailList) {

							File inputHtml = new File(servletContextFinal.getRealPath("emailhtml/emailreferido.html"));
							// Asginamos el archivo al objeto analizador
							// Document
							Document doc = Jsoup.parse(inputHtml, "UTF-8");

							Element anioactual = doc.select("span#anioactual").first();
							anioactual.append(new SimpleDateFormat("yyyy").format(new Date()));

							InternetAddress addressTo = new InternetAddress(dir);

							// se compone el mensaje (Asunto, cuerpo del mensaje
							// y direccion origen)
							final MimeMessage message = new MimeMessage(session);
							message.setFrom(
									new InternetAddress("contacto@puntosfarmanorte.com.co", "Puntos Farmanorte"));
							message.setRecipient(Message.RecipientType.TO, addressTo);
							// Emojis :-)
							String subjectEmojiRaw = ":large_blue_circle: Afiliate a Puntos Farmanorte";
							String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);

							message.setSubject(subjectEmoji, "UTF-8");
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

		} catch (Exception e) {
			System.out.println("Falla en el envio del correo de referidos");
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public String emailConfirmacionFinalSuscripcion(Afiliado afiliado) {

		System.out.println("Clase enviar Email Alerta Confirmacion final");
		
		String cmensaje = null;
		try {

			ServletContext servletContext = null;

			try {
				servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			} catch (Exception e) {
				servletContext = context;
			}

			File inputHtml = new File(servletContext.getRealPath("emailhtml/emailDos.html"));
			// Asginamos el archivo al objeto analizador Document
			Document doc = Jsoup.parse(inputHtml, "UTF-8");

			// obtengo los id's del DOM a los que deseo insertar los valores
			// mediante el metodo append() se insertan los valores obtenidos del
			// objeto obtenido como parametro
			Element genero = doc.select("span#genero").first();
			genero.append(afiliado.getSexo().equals("M") ? "o" : "a");

			Element nomAfiliado = doc.select("span#nombreAfiliado").first();
			nomAfiliado.append(afiliado.getNombres() + " " + afiliado.getApellidos());

			Element anioactual = doc.select("span#anioactual").first();
			anioactual.append(new SimpleDateFormat("yyyy").format(new Date()));

			// Element img = doc.select("img#pixelcontrol").first();
			// img.attr("src", url);

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
			// Construimos el mensaje

			// multiples direcciones
			String[] to = { afiliado.getEmail() };

			// arreglo con las direcciones de correo
			InternetAddress[] addressTo = new InternetAddress[to.length];
			for (int i = 0; i < addressTo.length; i++) {
				addressTo[i] = new InternetAddress(to[i]);
			}

			// se compone el mensaje (Asunto, cuerpo del mensaje y direccion
			// origen)
			final MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("contacto@puntosfarmanorte.com.co", "Puntos Farmanorte"));
			message.setRecipients(Message.RecipientType.BCC, addressTo);
			// Emojis :-)
			String subjectEmojiRaw = ":ok_hand: Registro Completo";
			String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);

			message.setSubject(subjectEmoji, "UTF-8");
			message.setContent(doc.html(), "text/html; charset=utf-8");
			
			cmensaje = doc.html();

			// Envia el correo
			final Transport t = session.getTransport("smtp");
			// asigno un hilo exclusivo a la conexion y envio del mensaje
			// dado que el proveedor de correo es muy lento para establecer
			// la conexion
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

		} catch (Exception e) {
			System.out.println("Falla en el envio del correo:");
			e.printStackTrace();
			return null;
		}
		return cmensaje;
	}

	public boolean notificacionRedencion(Sucursal sucursal, String momento, String nrofactura, Integer valortx,
			Afiliado afiliado, int puntosARedimir) {

		System.out.println("Envia email de auditoria ");
		try {

			ServletContext servletContext = null;

			try {
				servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			} catch (Exception e) {
				servletContext = context;
			}

			String mensaje = "Se registro una redencion de puntos. La siguientes es la informacion de la Tx.\n\n "
					+ "Sucursal: " + sucursal.getNombreSucursal() + "\n" + "Fecha: " + momento + "\n" + "Nro Factura: "
					+ nrofactura + "\n" + "Afiliado: " + afiliado.getDocumento() + "\n" + "Valor de la Tx: " + valortx
					+ "\n" + "Puntos a Redimir: " + puntosARedimir + "\n";

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

			// multiples direcciones
			String[] to = { "sistemas2@dromedicas.com.co", 
						    "sistemas@dromedicas.com.co", 
						    "johnduran@dromedicas.com.co",
						    "seguridadfarmanorte@gmail.com", 
						    "archivo@dromedicas.com.co", 
						    "elianaarredondo@dromedicas.com.co" 
						    };

			// arreglo con las direcciones de correo
			InternetAddress[] addressTo = new InternetAddress[to.length];
			for (int i = 0; i < addressTo.length; i++) {
				addressTo[i] = new InternetAddress(to[i]);
			}
			// se compone el mensaje (Asunto, cuerpo del mensaje y direccion
			// origen)
			final MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("contacto@puntosfarmanorte.com.co", "Puntos Farmanorte"));
			message.setRecipients(Message.RecipientType.BCC, addressTo);
			// Emojis :-)
			String subjectEmojiRaw = ":warning: Redencion de puntos sucursal " + sucursal.getNombreSucursal();
			String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);

			message.setSubject(subjectEmoji, "UTF-8");
			message.setContent(mensaje, "text/plain; charset=utf-8");

			// Envia el correo
			final Transport t = session.getTransport("smtp");
			// asigno un hilo exclusivo a la conexion y envio del mensaje
			// dado que el proveedor de correo es muy lento para establecer
			// la conexion
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

		} catch (Exception e) {
			System.out.println("Falla en el envio del correo:");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	

	public String emailRecuparacionClave(Afiliado afiliado) {

		String urlConfirmacion = "http://www.puntosfarmanorte.com.co/seccion/resetpassword.html?id="
				+ afiliado.getKeycode();

		System.out.println("Enviar Email Recuperacion clave");
		
		String cmensaje = null;
		try {
			ServletContext servletContext = null;

			try {
				servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			} catch (Exception e) {
				servletContext = context;
			}

			File inputHtml = new File(servletContext.getRealPath("emailhtml/recuperaclave.html"));
			// Asginamos el archivo al objeto analizador Document
			Document doc = Jsoup.parse(inputHtml, "UTF-8");

			// obtengo los id's del DOM a los que deseo insertar los valores
			// mediante el metodo append() se insertan los valores obtenidos del
			// objeto obtenido como parametro

			Element anioactual = doc.select("span#anioactual").first();
			anioactual.append(new SimpleDateFormat("yyyy").format(new Date()));

			// Url de confirmacion de correo para el elemento buton
			Element btn = doc.select("a#linkrecupera").first();
			btn.attr("href", urlConfirmacion);

			// Element img = doc.select("img#pixelcontrol").first();
			// img.attr("src", url);

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
			// Construimos el mensaje

			// multiples direcciones
			String[] to = { afiliado.getEmail() };

			// arreglo con las direcciones de correo
			InternetAddress[] addressTo = new InternetAddress[to.length];
			for (int i = 0; i < addressTo.length; i++) {
				addressTo[i] = new InternetAddress(to[i]);
			}

			// se compone el mensaje (Asunto, cuerpo del mensaje y direccion
			// origen)
			final MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("contacto@puntosfarmanorte.com.co", "Puntos Farmanorte"));
			message.setRecipients(Message.RecipientType.TO, addressTo);
			// Emojis :-)
			String subjectEmojiRaw = ":key: Recuperacion Contraseña Puntos Farmanorte";
			String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);

			message.setSubject(subjectEmoji, "UTF-8");
			message.setContent(doc.html(), "text/html; charset=utf-8");
			
			cmensaje = doc.html();

			message.setFlag(FLAGS.Flag.RECENT, true);

			// Envia el correo
			final Transport t = session.getTransport("smtp");
			// asigno un hilo exclusivo a la conexion y envio del mensaje
			// dado que el proveedor de correo es muy lento para establecer
			// la conexion
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

		} catch (Exception e) {
			System.out.println("Falla en el envio del correo:");
			e.printStackTrace();
			return null;
		}
		return cmensaje;
	}

	public String emailConfirmacionClave(Afiliado afiliado) {

		System.out.println("Enviar Email confirmacion clave");
		
		String cmensaje = null;
		try {
			ServletContext servletContext = null;

			try {
				servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			} catch (Exception e) {
				servletContext = context;
			}

			File inputHtml = new File(servletContext.getRealPath("emailhtml/emailconfirmaclave.html"));
			// Asginamos el archivo al objeto analizador Document
			Document doc = Jsoup.parse(inputHtml, "UTF-8");

			// obtengo los id's del DOM a los que deseo insertar los valores
			// mediante el metodo append() se insertan los valores obtenidos del
			// objeto obtenido como parametro

			Element anioactual = doc.select("span#anioactual").first();
			anioactual.append(new SimpleDateFormat("yyyy").format(new Date()));

			// Url de confirmacion de correo para el elemento buton
			Element genero = doc.select("span#genero").first();
			genero.append(afiliado.getSexo().equals("M") ? "o" : "a");

			Element nomAfiliado = doc.select("span#nombreAfiliado").first();
			nomAfiliado.append(afiliado.getNombres() + " " + afiliado.getApellidos());

			// Element img = doc.select("img#pixelcontrol").first();
			// img.attr("src", url);

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
			// Construimos el mensaje

			// multiples direcciones
			String[] to = { afiliado.getEmail() };

			// arreglo con las direcciones de correo
			InternetAddress[] addressTo = new InternetAddress[to.length];
			for (int i = 0; i < addressTo.length; i++) {
				addressTo[i] = new InternetAddress(to[i]);
			}

			// se compone el mensaje (Asunto, cuerpo del mensaje y direccion
			// origen)
			final MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("contacto@puntosfarmanorte.com.co", "Puntos Farmanorte"));
			message.setRecipients(Message.RecipientType.TO, addressTo);
			// Emojis :-)
			String subjectEmojiRaw = ":white_check_mark: Contraseña Reestablecida - Puntos Farmanorte";
			String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);

			message.setSubject(subjectEmoji, "UTF-8");
			message.setContent(doc.html(), "text/html; charset=utf-8");
			
			cmensaje = doc.html();

			message.setFlag(FLAGS.Flag.RECENT, true);

			// Envia el correo
			final Transport t = session.getTransport("smtp");
			// asigno un hilo exclusivo a la conexion y envio del mensaje
			// dado que el proveedor de correo es muy lento para establecer
			// la conexion
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

		} catch (Exception e) {
			System.out.println("Falla en el envio del correo:");
			e.printStackTrace();
			return null;
		}
		return cmensaje;
	}


	/**
	 * 
	 * @param afiliado
	 * @return
	 */
	public boolean emailCumpleanosAfiliadoAdmin(List<Afiliado> afiliadoList) {

		System.out.println("Enviar administratrivo de cumpleanos");
		try {
			ServletContext servletContext = null;

			try {
				servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			} catch (Exception e) {
				servletContext = context;
			}

			File inputHtml = new File(servletContext.getRealPath("emailhtml/cumpleafiliado.html"));
			// Asginamos el archivo al objeto analizador Document
			Document doc = Jsoup.parse(inputHtml, "UTF-8");

			// obtengo los id's del DOM a los que deseo insertar los valores
			// mediante el metodo append() se insertan los valores obtenidos del
			// objeto obtenido como parametro

			Element fechaactual = doc.select("span#fechaactual").first();
			fechaactual.append(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

			Element rowProx = doc.select("table#detallec tbody").first();
			// builder que contiene el detalle de la tabla
			StringBuilder detalle = new StringBuilder();

			for (int i = 0; i < afiliadoList.size(); i++) {

				detalle.append(
						"<tr>" +
						"<td style=\"padding: 2px 3px 2px 3px; text-align:center;  border-color:#666666; \">"+
						"<span style=\"font-size: 11px; font-family: Arial,Helvetica,sans-serif; display:block; color:#333333\">"+
						 (i+1) +
						"</span>" +
						"</td>" +
						"<td style=\"padding: 2px 3px 2px 3px; border-color:#666666; \">"+
						"<span style=\"font-size: 11px; font-family: Arial,Helvetica,sans-serif; display:block; color:#333333\">"+
						afiliadoList.get(i).getDocumento() +
						"</span>" +
						"</td>" +
						"<td style=\"padding: 2px 3px 2px 3px; border-color:#666666; \">"+
						"<span style=\"font-size: 11px; font-family: Arial,Helvetica,sans-serif; display:block; color:#333333\">"+
						afiliadoList.get(i).getNombres() +" " +afiliadoList.get(i).getApellidos() +
						"</span>" +
						"</td>" +
						"<td style=\"padding: 2px 3px 2px 3px; border-color:#666666; \">"+
						"<span style=\"font-size: 11px; font-family: Arial,Helvetica,sans-serif; display:block; color:#333333\">"+
						new SimpleDateFormat("dd-MM-yyyy").format(afiliadoList.get(i).getFechanacimiento()) +
						"</span>" +
						"</td>" +
						"<td style=\"padding: 2px 3px 2px 3px;  border-color:#666666; \">"+
						"<span style=\"font-size: 11px; font-family: Arial,Helvetica,sans-serif; display:block; color:#333333\">"+
						"<strong>"+
						afiliadoList.get(i).getEdad() +
						"</strong>"+
						"</span>" +
						"</td>" +
						"<td style=\"padding: 2px 3px 2px 3px; border-color:#666666; \">"+
						"<span style=\"font-size: 11px; font-family: Arial,Helvetica,sans-serif; display:block; color:#333333\">"+
						afiliadoList.get(i).getCelular() +
						"</span>" +
						"</td>" +
						"<td style=\"padding: 2px 3px 2px 3px; text-align:center; border-color:#666666; \">"+
						"<span style=\"font-size: 12px; font-family: Arial,Helvetica,sans-serif; display:block; color:#333333\">"+
						afiliadoList.get(i).getTelefonofijo() +
						"</span>" +
						"</td>" +
						"<td style=\"padding: 2px 3px 2px 3px; border-color:#666666; \">"+
						"<span style=\"font-size: 11px; font-family: Arial,Helvetica,sans-serif; display:block; color:#333333\">"+
						afiliadoList.get(i).getSucursal().getNombreSucursal()+
						"</span>" +
						"</td>" +
						"</tr>");				
			} // fin del ciclo que imprime la tabla

			rowProx.html(detalle.toString());

			Element auditor = doc.select("span#auditor").first();
			auditor.append(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));

			// Element img = doc.select("img#pixelcontrol").first();
			// img.attr("src", url);

			// Propiedades de la conexión
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "deus.wnkserver6.com");
			props.setProperty("mail.smtp.port", "25");// puerto de salida, de
			// entrada 110
			props.setProperty("mail.smtp.user", "noreply@puntosfarmanorte.com.co");
			props.setProperty("mail.smtp.auth", "true");
			props.put("mail.transport.protocol.", "smtp");

			// Preparamos la sesion
			Session session = Session.getDefaultInstance(props);
			// Construimos el mensaje

			// multiples direcciones
			String[] to = { "sistemas2@dromedicas.com.co" };

			// arreglo con las direcciones de correo
			InternetAddress[] addressTo = new InternetAddress[to.length];
			for (int i = 0; i < addressTo.length; i++) {
				addressTo[i] = new InternetAddress(to[i]);
			}

			// se compone el mensaje (Asunto, cuerpo del mensaje y direccion
			// origen)
			final MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("noreply@puntosfarmanorte.com.co", "Puntos Farmanorte"));
			message.setRecipients(Message.RecipientType.TO, addressTo);
			// Emojis :-)
			String subjectEmojiRaw = ":birthday: " + new SimpleDateFormat("dd-MM-yyyy").format(new Date())
					+ " Cumpleaños Afiliados Puntos Farmanorte";
			String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);

			message.setSubject(subjectEmoji, "UTF-8");
			message.setContent(doc.html(), "text/html; charset=utf-8");

			message.setFlag(FLAGS.Flag.RECENT, true);

			// Envia el correo
			final Transport t = session.getTransport("smtp");
			// asigno un hilo exclusivo a la conexion y envio del mensaje
			// dado que el proveedor de correo es muy lento para establecer
			// la conexion
			new Thread(new Runnable() {
				public void run() {
					try {
						t.connect("noreply@puntosfarmanorte.com.co", "Dromedicas2013.");
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

		} catch (Exception e) {
			System.out.println("Falla en el envio del correo:");
			e.printStackTrace();
			return false;
		}
		return true;
	}



	/**
	 * Envia emaila de felicitacion a un List de afiliados
	 * @param emailList
	 * @return
	 */
	public boolean emailAfiliadoCumpleanios(final List<String> emailList) {

		System.out.println("Clase enviar Emial cumpleanos afilaido ");
		try {

			// se optiene el contexto de la aplicacion
			// para calificar la ruta de acceso del archivo
			// template HTML del email
			ServletContext servletContext = null;
			try {
				servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
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
			// Envia el correo
			final ServletContext servletContextFinal = servletContext;
			new Thread(new Runnable() {
				public void run() {
					try {
						Transport t = session.getTransport("smtp");
						t.connect("contacto@puntosfarmanorte.com.co", "Dromedicas2013.");

						for (String dir : emailList) {

							File inputHtml = new File(servletContextFinal.getRealPath("emailhtml/emailreferido.html"));
							// Asginamos el archivo al objeto analizador
							// Document
							Document doc = Jsoup.parse(inputHtml, "UTF-8");

							Element anioactual = doc.select("span#anioactual").first();
							anioactual.append(new SimpleDateFormat("yyyy").format(new Date()));

							InternetAddress addressTo = new InternetAddress(dir);

							// se compone el mensaje (Asunto, cuerpo del mensaje
							// y direccion origen)
							final MimeMessage message = new MimeMessage(session);
							message.setFrom(
									new InternetAddress("contacto@puntosfarmanorte.com.co", "Puntos Farmanorte"));
							message.setRecipient(Message.RecipientType.TO, addressTo);
							// Emojis :-)
							String subjectEmojiRaw = ":large_blue_circle: Afiliate a Puntos Farmanorte";
							String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);

							message.setSubject(subjectEmoji, "UTF-8");
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

		} catch (Exception e) {
			System.out.println("Falla en el envio del correo de referidos");
			e.printStackTrace();
			return false;
		}
		return true;

	}
	
	
	
	public boolean emailErrorCampania(Campania campania, String contenido) {

		System.out.println("Envia email de error campania ");
		try {

			ServletContext servletContext = null;

			try {
				servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			} catch (Exception e) {
				servletContext = context;
			}

			String mensaje = contenido + ".\n\n "
					+ "Nombre Campaña: " + campania.getNombrecampania() + "\n" + "Fecha: " + campania.getFechainicio() + "\n";

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

			// multiples direcciones
			String[] to = { "sistemas2@dromedicas.com.co", "mercadeo@dromedicas.com.co" };

			// arreglo con las direcciones de correo
			InternetAddress[] addressTo = new InternetAddress[to.length];
			for (int i = 0; i < addressTo.length; i++) {
				addressTo[i] = new InternetAddress(to[i]);
			}
			// se compone el mensaje (Asunto, cuerpo del mensaje y direccion
			// origen)
			final MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("contacto@puntosfarmanorte.com.co", "Puntos Farmanorte"));
			message.setRecipients(Message.RecipientType.BCC, addressTo);
			// Emojis :-)
			String subjectEmojiRaw = ":rotating_light: Error en envio de Campaña";
			String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);

			message.setSubject(subjectEmoji, "UTF-8");
			message.setContent(mensaje, "text/plain; charset=utf-8");

			// Envia el correo
			final Transport t = session.getTransport("smtp");
			// asigno un hilo exclusivo a la conexion y envio del mensaje
			// dado que el proveedor de correo es muy lento para establecer
			// la conexion
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

		} catch (Exception e) {
			System.out.println("Falla en el envio del correo:");
			e.printStackTrace();
			return false;
		}
		return true;
	}



	
}
