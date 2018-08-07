package com.dromedicas.mailservice;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

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
import com.dromedicas.domain.Parametrosemail;
import com.dromedicas.domain.Sucursal;
import com.dromedicas.domain.Transaccion;
import com.dromedicas.service.OperacionPuntosService;
import com.dromedicas.service.ParametrosEmialService;
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
	
	@EJB
	private ParametrosEmialService emailParameterService;
	
	
	private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String MAIL_SMTP_HOST = "mail.smtp.host"; 
    private static final String MAIL_SMTP_PORT = "mail.smtp.port";
    private static final String MAIL_SMTP_SSL_SOCKETFACTORY = "mail.smtp.ssl.socketFactory";
    private static final String MAIL_SERVER_PROTOCOL = "mail.server.protocol";
    private static final String MAIL_SERVER_PORT = "mail.port";
    private static final String MAIL_USERNAME = "mail.smtp.user";
    private static final String MAIL_PASSWORD = "mail.smtp.password";
    private static final String MAIL_SERVER_HOST = "mail.server.host";
    private static final String MAIL_STORE_IMAP = "mail.store.protocol";
    private static final String MAIL_SMTP_STATRTTLS = "mail.smtp.starttls.enable";
    private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    private static final String ARCHIVED_FOLDER= "archived";
    
    private Parametrosemail param;
    private Properties defaultMailConfig;
    
    private String userSession;
    private String passwordSession;
    private String emailSession;
    
    /**
     * Establece las propiedades de conexion y envio del 
     * email a usar, desde la base de datos.
     */
    private void setProperties(){
    	//instancia  el objeto properties
    	 this.defaultMailConfig = new Properties(); 
    	 
    	 //obtiene los parametros de la cuenta de correo de la base de datos
    	 //estos parametros se configuran en el modulo de empresa
    	 this.param = 
 				this.emailParameterService.obtenerParametrosemailPorFinalidad(Integer.toString(1));
    	 
    	 //establece los valores del objeto properties usando las constantes declaradas
    	 //como llaves
    	 defaultMailConfig.setProperty(MAIL_SMTP_HOST, param.getSmtpHost());        
         defaultMailConfig.setProperty(MAIL_SMTP_PORT, param.getPort());
         defaultMailConfig.setProperty(MAIL_USERNAME, param.getSmtpUser());
         defaultMailConfig.setProperty(MAIL_SMTP_AUTH, param.getSmtpAuth());
         defaultMailConfig.put(MAIL_TRANSPORT_PROTOCOL, param.getTransportprotocol());
         
			Set<Object> claves = defaultMailConfig.keySet(); 
			// imprime los pares nombre/valor
			for (Object clave : claves) {
				System.out.printf("%s\t%s\n", clave, defaultMailConfig.getProperty((String) clave));
			} // fin de for
			System.out.println();
		
         
         //se establecen variables de instancia para inicio de session
			
			System.out.println("Usuario: "+  param.getSmtpUser() + " Password: " + param.getSmtpPassword());
         this.setUserSession(param.getSmtpUser());
         this.setPasswordSession(param.getSmtpPassword());
        
    }        
	
	public Properties getDefaultMailConfig() {
		return defaultMailConfig;
	}

	public void setDefaultMailConfig(Properties defaultMailConfig) {
		this.defaultMailConfig = defaultMailConfig;
	}

	public String getUserSession() {
		return userSession;
	}

	public void setUserSession(String userSession) {
		this.userSession = userSession;
	}

	public String getPasswordSession() {
		return passwordSession;
	}

	public void setPasswordSession(String passwordSession) {
		this.passwordSession = passwordSession;
	}
	
	public String getEmailSession() {
		return emailSession;
	}

	public void setEmailSession(String emailSession) {
		this.emailSession = emailSession;
	}

	public String enviarEmailAlertaVentas(Afiliado afiliado) {
		
//		String urlConfirmacion = "http://www.puntosfarmanorte.com.co/seccion/actualizacion.html?id="
//		String urlConfirmacion = "http://www.puntosfarmanorte.com.co/index.html?id="			
		String urlConfirmacion = "http://www.puntosfarmanorte.com.co/index.html?id="
				+ afiliado.getKeycode();

		String contenidoEmail = null;
		
		System.out.println("Clase enviar Email Alerta Afiliado");
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

			//Establece las propiedades de conexion
			this.setProperties();
			
			// Preparamos la sesion
			Session session = Session.getDefaultInstance( this.defaultMailConfig );
			
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
			message.setFrom(new InternetAddress(this.getUserSession(), "Puntos Farmanorte"));
			message.setRecipients(Message.RecipientType.TO, addressTo);
			// Emojis :-)
			String subjectEmojiRaw = ":large_blue_circle: Confirmacion de suscripcion :memo:";
			String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);

			message.setSubject(subjectEmoji, "UTF-8");
			message.setContent(doc.html(), "text/html; charset=utf-8");

			message.setFlag(FLAGS.Flag.RECENT, true);

			contenidoEmail = doc.html();
			// Envia el correo | constantes usadas en el subproceso
			final Transport t = session.getTransport("smtp");
			final String email = this.getUserSession();
			final String passw = this.getPasswordSession();
			
			// asigno un hilo exclusivo a la conexion y envio del mensaje
			// dado que el proveedor de correo es muy lento para establecer
			// la conexion
			new Thread(new Runnable() {
				public void run() {
					try {
						t.connect(email, passw);
						t.sendMessage(message, message.getAllRecipients());
						// Cierre de la conexion
						t.close();
						System.out.println("Conexion cerrada");

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();			

		} catch (Exception e) {
			System.out.println("Falla en el envio del correo:");
			e.printStackTrace();
			return null;
		}
		
		return contenidoEmail;
	}
	
	
	/**
	 * Envia email de confirmacion de correo, cuando se actualizan los datos
	 * del afiliado.
	 * @param afiliado
	 * @return
	 */
	public String validarCorreoActualizacion(Afiliado afiliado) {

//		String urlConfirmacion = "http://www.puntosfarmanorte.com.co/index.html?param=true&id="
		String urlConfirmacion = "http://www.puntosfarmanorte.com.co/index.html?param=true&id="
				+ afiliado.getKeycode();

		String contenidoEmail = null;
		
		System.out.println("Clase enviar Email Alerta Afiliado");
		try {
			ServletContext servletContext = null;

			try {
				servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			} catch (Exception e) {
				servletContext = context;
			}

			File inputHtml = new File(servletContext.getRealPath("emailhtml/validaemailafil.html"));
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
			props.setProperty("mail.smtp.host", "smtpout.secureserver.net");
			props.setProperty("mail.smtp.port", "80");// puerto de salida,entrada 110
			props.setProperty("mail.smtp.user", "contacto@farmanorte.com.co");
			props.setProperty("mail.smtp.auth", "true");
			props.put("mail.transport.protocol.", "smtp");

			//establece los valores de la propiedades
			this.setProperties();
		
			// Preparamos la sesion
			Session session = Session.getDefaultInstance( this.defaultMailConfig );
						
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
			message.setFrom(new InternetAddress(this.getUserSession(), "Puntos Farmanorte"));
			message.setRecipients(Message.RecipientType.TO, addressTo);
			// Emojis :-)
			String subjectEmojiRaw = ":large_blue_circle: Puntos Farmanorte | Validacion de correo ";
			String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);

			message.setSubject(subjectEmoji, "UTF-8");
			message.setContent(doc.html(), "text/html; charset=utf-8");

			message.setFlag(FLAGS.Flag.RECENT, true);

			contenidoEmail = doc.html();
			// Envia el correo
			final Transport t = session.getTransport("smtp");
			final String email = this.getUserSession();
			final String passw = this.getPasswordSession();
			// asigno un hilo exclusivo a la conexion y envio del mensaje
			// dado que el proveedor de correo es muy lento para establecer
			// la conexion
			new Thread(new Runnable() {
				public void run() {
					try {
						t.connect(email, passw);
						t.sendMessage(message, message.getAllRecipients());
						// Cierre de la conexion
						t.close();
						System.out.println("Conexion cerrada");

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();			

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
				props.setProperty("mail.smtp.host", "smtpout.secureserver.net");
				props.setProperty("mail.smtp.port", "80");// puerto de salida,entrada 110
				props.setProperty("mail.smtp.user", "contacto@farmanorte.com.co");
				props.setProperty("mail.smtp.auth", "true");
				props.put("mail.transport.protocol.", "smtp");

				//establece los valores de la propiedades
				this.setProperties();
				
				// Preparamos la sesion
				Session session = Session.getDefaultInstance( this.defaultMailConfig );
								
				// Construimos el mensaje

				InternetAddress addressTo = new InternetAddress(afiliado.getEmail());

				// se compone el mensaje (Asunto, cuerpo del mensaje y direccion
				// origen)
				final MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(this.getUserSession(), "Puntos Farmanorte"));
				message.setRecipient(Message.RecipientType.TO, addressTo);
				// Emojis :-)
				String subjectEmojiRaw = ":pill: Puntos Farmanorte :syringe:";
				String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);

				message.setSubject(subjectEmoji + "| Acumulaste: " + ganados, "UTF-8");
				message.setContent(doc.html(), "text/html; charset=utf-8");

				System.out.println("Enviando Correo....");
				// Envia el correo
				final Transport t = session.getTransport("smtp");
				final String email = this.getUserSession();
				final String passw = this.getPasswordSession();
				// asigno un hilo exclusivo a la conexion y envio del mensaje
				// dado que el proveedor de correo es muy lento para establecer
				// la conexion
				new Thread(new Runnable() {
					public void run() {
						try {
							t.connect(email, passw);
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
			props.setProperty("mail.smtp.host", "smtpout.secureserver.net");
			props.setProperty("mail.smtp.port", "80");// puerto de salida,entrada 110
			props.setProperty("mail.smtp.user", "contacto@farmanorte.com.co");
			props.setProperty("mail.smtp.auth", "true");
			props.put("mail.transport.protocol.", "smtp");

			//establece los valores de la propiedades
			this.setProperties();
			
			// Preparamos la sesion
			Session session = Session.getDefaultInstance( this.defaultMailConfig );
			System.out.println("Enviando Correos....");
			
			// Envia el correo
			final Transport t = session.getTransport("smtp");
			final String email = this.getUserSession();
			final String passw = this.getPasswordSession();
			t.connect(email, passw);

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
					message.setFrom(new InternetAddress(email, "Puntos Farmanorte"));
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
			props.setProperty("mail.smtp.host", "smtpout.secureserver.net");
			props.setProperty("mail.smtp.port", "80");// puerto de salida,entrada 110
			props.setProperty("mail.smtp.user", "contacto@farmanorte.com.co");
			props.setProperty("mail.smtp.auth", "true");
			props.put("mail.transport.protocol.", "smtp");

			
			//establece los valores de la propiedades
			this.setProperties();
			
			// Preparamos la sesion
			final Session session = Session.getDefaultInstance(this.defaultMailConfig);
			final String email = this.getUserSession();
			final String passw = this.getPasswordSession();
			System.out.println("Enviando Correos....");
			// Envia el correo
			final ServletContext servletContextFinal = servletContext;
			new Thread(new Runnable() {
				public void run() {
					try {
						Transport t = session.getTransport("smtp");
						t.connect(email, passw);

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
							message.setFrom(new InternetAddress(email, "Puntos Farmanorte"));
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
			props.setProperty("mail.smtp.host", "smtpout.secureserver.net");
			props.setProperty("mail.smtp.port", "80");// puerto de salida,entrada 110
			props.setProperty("mail.smtp.user", "contacto@farmanorte.com.co");
			props.setProperty("mail.smtp.auth", "true");
			props.put("mail.transport.protocol.", "smtp");

			//Establece las propiedades de conexion
			this.setProperties();
			
			// Preparamos la sesion
			Session session = Session.getDefaultInstance( this.defaultMailConfig );			
			
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
			message.setFrom(new InternetAddress(this.getUserSession(), "Puntos Farmanorte"));
			message.setRecipients(Message.RecipientType.BCC, addressTo);
			// Emojis :-)
			String subjectEmojiRaw = ":ok_hand: Registro Completo";
			String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);

			message.setSubject(subjectEmoji, "UTF-8");
			message.setContent(doc.html(), "text/html; charset=utf-8");
			
			cmensaje = doc.html();

			// Envia el correo
			final Transport t = session.getTransport("smtp");
			final String email = this.getUserSession();
			final String passw = this.getPasswordSession();
			// asigno un hilo exclusivo a la conexion y envio del mensaje
			// dado que el proveedor de correo es muy lento para establecer
			// la conexion
			new Thread(new Runnable() {
				public void run() {
					try {
						t.connect(email, passw);
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

	
	
	public String emailActualizacionDatos(Afiliado afiliado) {

		System.out.println("Clase enviar Email Alerta ACTUALIZACION DATOS final");
		
		String cmensaje = null;
		try {

			ServletContext servletContext = null;

			try {
				servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			} catch (Exception e) {
				servletContext = context;
			}

			File inputHtml = new File(servletContext.getRealPath("emailhtml/actualizacionconfirm.html"));
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
			props.setProperty("mail.smtp.host", "smtpout.secureserver.net");
			props.setProperty("mail.smtp.port", "80");// puerto de salida,entrada 110
			props.setProperty("mail.smtp.user", "contacto@farmanorte.com.co");
			props.setProperty("mail.smtp.auth", "true");
			props.put("mail.transport.protocol.", "smtp");

			//Establece las propiedades de conexion
			this.setProperties();
			
			// Preparamos la sesion
			Session session = Session.getDefaultInstance( this.defaultMailConfig );
			
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
			message.setFrom(new InternetAddress(this.getUserSession(), "Puntos Farmanorte"));
			message.setRecipients(Message.RecipientType.BCC, addressTo);
			// Emojis :-)
			String subjectEmojiRaw = ":ok_hand: Actualizacion datos completa";
			String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);

			message.setSubject(subjectEmoji, "UTF-8");
			message.setContent(doc.html(), "text/html; charset=utf-8");
			
			cmensaje = doc.html();

			// Envia el correo
			final Transport t = session.getTransport("smtp");
			final String email = this.getUserSession();
			final String passw = this.getPasswordSession();// asigno un hilo exclusivo a la conexion y envio del mensaje
			// dado que el proveedor de correo es muy lento para establecer
			// la conexion
			new Thread(new Runnable() {
				public void run() {
					try {
						t.connect(email, passw);
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
			props.setProperty("mail.smtp.host", "smtpout.secureserver.net");
			props.setProperty("mail.smtp.port", "80");// puerto de salida,entrada 110
			props.setProperty("mail.smtp.user", "contacto@farmanorte.com.co");
			props.setProperty("mail.smtp.auth", "true");
			props.put("mail.transport.protocol.", "smtp");

			///Establece las propiedades de conexion
			this.setProperties();
			
			// Preparamos la sesion
			Session session = Session.getDefaultInstance( this.defaultMailConfig );

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
			message.setFrom(new InternetAddress(this.getUserSession(), "Puntos Farmanorte"));
			message.setRecipients(Message.RecipientType.BCC, addressTo);
			// Emojis :-)
			String subjectEmojiRaw = ":warning: Redencion de puntos sucursal " + sucursal.getNombreSucursal();
			String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);

			message.setSubject(subjectEmoji, "UTF-8");
			message.setContent(mensaje, "text/plain; charset=utf-8");

			// Envia el correo
			final Transport t = session.getTransport("smtp");
			final String email = this.getUserSession();
			final String passw = this.getPasswordSession();
			// asigno un hilo exclusivo a la conexion y envio del mensaje
			// dado que el proveedor de correo es muy lento para establecer
			// la conexion
			new Thread(new Runnable() {
				public void run() {
					try {
						t.connect(email, passw);
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

		//String urlConfirmacion = "http://www.puntosfarmanorte.com.co/seccion/resetpassword.html?id="
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
			props.setProperty("mail.smtp.host", "smtpout.secureserver.net");
			props.setProperty("mail.smtp.port", "80");// puerto de salida,entrada 110
			props.setProperty("mail.smtp.user", "contacto@farmanorte.com.co");
			props.setProperty("mail.smtp.auth", "true");
			props.put("mail.transport.protocol.", "smtp");

			//Establece las propiedades de conexion
			this.setProperties();
			
			// Preparamos la sesion
			Session session = Session.getDefaultInstance( this.defaultMailConfig );
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
			message.setFrom(new InternetAddress(this.getUserSession(), "Puntos Farmanorte"));
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
			final String email = this.getUserSession();
			final String passw = this.getPasswordSession();
			// asigno un hilo exclusivo a la conexion y envio del mensaje
			// dado que el proveedor de correo es muy lento para establecer
			// la conexion
			new Thread(new Runnable() {
				public void run() {
					try {
						t.connect(email, passw);
						t.sendMessage(message, message.getAllRecipients());
						// Cierre de la conexion
						t.close();
						System.out.println("Conexion cerrada");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();			

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
			props.setProperty("mail.smtp.host", "smtpout.secureserver.net");
			props.setProperty("mail.smtp.port", "80");// puerto de salida,entrada 110
			props.setProperty("mail.smtp.user", "contacto@farmanorte.com.co");
			props.setProperty("mail.smtp.auth", "true");
			props.put("mail.transport.protocol.", "smtp");

			//Establece las propiedades de conexion
			this.setProperties();
			
			// Preparamos la sesion
			Session session = Session.getDefaultInstance( this.defaultMailConfig );
			
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
			message.setFrom(new InternetAddress(this.getUserSession(), "Puntos Farmanorte"));
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
			final String email = this.getUserSession();
			final String passw = this.getPasswordSession();
			// asigno un hilo exclusivo a la conexion y envio del mensaje
			// dado que el proveedor de correo es muy lento para establecer
			// la conexion
			new Thread(new Runnable() {
				public void run() {
					try {
						t.connect(email, passw);
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
			props.setProperty("mail.smtp.host", "smtpout.secureserver.net");
			props.setProperty("mail.smtp.port", "80");// puerto de salida,entrada 110
			props.setProperty("mail.smtp.user", "contacto@farmanorte.com.co");
			props.setProperty("mail.smtp.auth", "true");
			props.put("mail.transport.protocol.", "smtp");

			//Establece las propiedades de conexion
			this.setProperties();
			
			// Preparamos la sesion
			Session session = Session.getDefaultInstance( this.defaultMailConfig );
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
			message.setFrom(new InternetAddress(this.getUserSession(), "Puntos Farmanorte"));
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
			final String email = this.getUserSession();
			final String passw = this.getPasswordSession();
			// asigno un hilo exclusivo a la conexion y envio del mensaje
			// dado que el proveedor de correo es muy lento para establecer
			// la conexion
			new Thread(new Runnable() {
				public void run() {
					try {
						t.connect(email, passw);
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
			props.setProperty("mail.smtp.host", "smtpout.secureserver.net");
			props.setProperty("mail.smtp.port", "80");// puerto de salida,entrada 110
			props.setProperty("mail.smtp.user", "contacto@farmanorte.com.co");
			props.setProperty("mail.smtp.auth", "true");
			props.put("mail.transport.protocol.", "smtp");

			//Establece las propiedades de conexion
			this.setProperties();
			
			// Preparamos la sesion
			Session session = Session.getDefaultInstance( this.defaultMailConfig );

			// multiples direcciones
			String[] to = { "sistemas2@dromedicas.com.co", "elianaarredondo@dromedicas.com.co" };

			// arreglo con las direcciones de correo
			InternetAddress[] addressTo = new InternetAddress[to.length];
			for (int i = 0; i < addressTo.length; i++) {
				addressTo[i] = new InternetAddress(to[i]);
			}
			// se compone el mensaje (Asunto, cuerpo del mensaje y direccion
			// origen)
			final MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.getUserSession(), "Puntos Farmanorte"));
			message.setRecipients(Message.RecipientType.BCC, addressTo);
			// Emojis :-)
			String subjectEmojiRaw = ":rotating_light: Error en envio de Campaña";
			String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);

			message.setSubject(subjectEmoji, "UTF-8");
			message.setContent(mensaje, "text/plain; charset=utf-8");

			// Envia el correo
			final Transport t = session.getTransport("smtp");
			final String email = this.getUserSession();
			final String passw = this.getPasswordSession();
			// asigno un hilo exclusivo a la conexion y envio del mensaje
			// dado que el proveedor de correo es muy lento para establecer
			// la conexion
			new Thread(new Runnable() {
				public void run() {
					try {
						t.connect(email, passw);
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


	
	public String enviarEmailPrueba(String dirEmail) throws Exception{
		
		System.out.println("Email Test Configuracion.. ");
		
		String enviado = "Exitoso";
		
		try {
			String mensaje = 
					"Mensaje de prueba configuracion de correo Puntos Farmanorte.";	
			
			//Establece las propiedades de conexion
			this.setProperties();
			
			// Preparamos la sesion
			Session session = Session.getDefaultInstance( this.defaultMailConfig );

			// se compone el mensaje (Asunto, cuerpo del mensaje y direccion
			// origen)
			final MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.getUserSession(), "Puntos Farmanorte"));
			message.setRecipient(Message.RecipientType.BCC, new InternetAddress(dirEmail));
			// Emojis :-)
			String subjectEmojiRaw = ":white_check_mark: Correo de Prueba";
			String subjectEmoji = EmojiParser.parseToUnicode(subjectEmojiRaw);

			message.setSubject(subjectEmoji, "UTF-8");
			message.setContent(mensaje, "text/plain; charset=utf-8");

			// Envia el correo
			final Transport t = session.getTransport("smtp");
			final String email = this.getUserSession();
			final String passw = this.getPasswordSession();
			// asigno un hilo exclusivo a la conexion y envio del mensaje
			// dado que el proveedor de correo es muy lento para establecer
			// la conexion
			t.connect(email, passw);
			t.sendMessage(message, message.getAllRecipients());
			System.out.println("Conexion cerrada");

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception( e.getMessage() );
		}
		return enviado;
	}
	
	
	
	
	
	
	
}
