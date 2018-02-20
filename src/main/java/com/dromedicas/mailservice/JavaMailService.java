package com.dromedicas.mailservice;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderNotFoundException;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.search.FlagTerm;

import com.dromedicas.domain.Parametrosemail;
import com.dromedicas.service.ParametrosEmialService;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;



@Stateless
public class JavaMailService {
	
	@EJB
	private ParametrosEmialService emailParameters; 
	
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
    
   
    private String tipoEmail;
    public  String username;
    public  String password;
    private String serverSMTPHost;
    private String serverProtocol;
    private String port;
    private String autentica;
    private String archivedFolderName;
	private Session session;
	private Folder mailInbox;
	private Folder achivedFolder;
	private Properties config;
	private String defaultName;
	public static final Pattern CERTIFICATE_SUBJECT_PATTERN = Pattern.compile("(Subject: Certificate #)\\w{8}", Pattern.DOTALL);  
	private static final String MIME_TYPE_MULTIPART = "multipart/*";  
	private static final String MIME_TYPE_RFC_822 = "message/rfc822"; 
	private static final String MIME_TYPE_TEXTPLAIN = "text/plain"; 
	private static final String MIME_TYPE_MULTIPAR_REPORT = "multipart/REPORT"; 

	    
	public JavaMailService() {			
	}
	
	/**
	 * Establece los valores de conexion para la cuenta de correo especificada
	 */
	public void setConnectionValues(String tipoEmail){
		System.out.println("Leyendo propiedades");
		
		this.setTipoEmail(tipoEmail);
		
		Parametrosemail param = emailParameters.obtenerParametrosemailPorFinalidad(this.getTipoEmail());		
		
		username = param.getUser();
		password = param.getPassword();
		serverSMTPHost = param.getSmtpHost();
		port = param.getPort();
		serverProtocol = param.getProtocol();
		autentica = param.getSmtpAuth();		
		archivedFolderName = param.getArchived();
		defaultName = "LEIDOS";		
		
	}
	
	public Properties defaultMailConfig() {
		Parametrosemail param = emailParameters.obtenerParametrosemailPorFinalidad(this.getTipoEmail());		
        Properties defaultMailConfig = new Properties();       
        defaultMailConfig.setProperty(MAIL_SMTP_HOST, param.getSmtpHost());        
        defaultMailConfig.setProperty(MAIL_SMTP_PORT, param.getPort());
        defaultMailConfig.setProperty(MAIL_USERNAME, param.getUser());
        defaultMailConfig.setProperty(MAIL_SMTP_AUTH, param.getSmtpAuth());
        defaultMailConfig.setProperty(MAIL_STORE_IMAP, param.getStoreProtocol());
        defaultMailConfig.setProperty(MAIL_TRANSPORT_PROTOCOL, param.getProtocol());
        
        return defaultMailConfig;
    }
	
	
	public Session createUserMailSession() {
		Session session = Session.getInstance(this.defaultMailConfig(), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		return session;

	}
		
	/**
	 * Envia un mensaje de correo a un destinatario
	 * @param message
	 * @throws Exception
	 */
	public void sendEmail(Message message) throws Exception {
		System.out.println(null == session);
		if (null == session) {
			session = createUserMailSession();
		}
		Transport.send(message);
	}
	
	
	Store store = null;

	private Store connectToMailServer() throws MessagingException {
		if (null == session) {
			session = Session.getDefaultInstance(defaultMailConfig());
		}
		if (null == store || !store.isConnected()) {
			store = session.getStore();
			// store.connect(serverHost, port, username, password);
			store.connect(this.serverSMTPHost, this.username, this.password);

		}
		return store;
	}
	
	/**
	 * 
	 * @throws MessagingException
	 */
	private void openInboxFolder() throws MessagingException {
		final Store store = connectToMailServer();
		final String inboxNameVariant1 = "INBOX";
		final String inboxNameVariant2 = "INBOX".toLowerCase();
		final String inboxNameVariant3 = "INBOX".toUpperCase();
		Folder folder;
		if (null != (folder = store.getFolder(inboxNameVariant1))
				|| null != (folder = store.getFolder(inboxNameVariant2))
				|| null != (folder = store.getFolder(inboxNameVariant3))) {
			mailInbox = folder;
			mailInbox.open(Folder.READ_WRITE);
		}
	}
	
	public Folder obtenerFolderAcrhivados(){
		Folder folder = null;
		try {
			final Store store = connectToMailServer();
			folder  =  store.getFolder(archivedFolderName);
			
		} catch (Exception e) {
			System.out.println("ERROR AL OBTENER LA CARPETAD DE ARCHIVADOS");
			
			e.printStackTrace();
			
			return null;
			
		}		
		return folder;
	}
	
	/**
	 * 
	 * @throws MessagingException
	 */
	private void openArchivedFolder() throws MessagingException {
		final Store store = connectToMailServer();
		final String archivedFolderName = this.archivedFolderName == null ? defaultName : this.archivedFolderName;
		achivedFolder = store.getFolder("INBOX."+ archivedFolderName );
		achivedFolder.open (Folder.READ_WRITE);
	}
	
	
	public void deleteMessage(Message message) throws MessagingException{
		
		message.setFlag(Flags.Flag.DELETED,true);		
	}
	
	
	public void closeFolderInbox(){
		try {
			this.mailInbox.close(true);
		} catch (MessagingException e) {
			System.out.println("ERROR al cerrar Inbox");
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Obtiene todos los mensajes no leidos del INBOX
	 * @return
	 * @throws MessagingException
	 */
	public List<Message> getNewMessages() throws MessagingException {
		if (null == mailInbox || !mailInbox.isOpen()) {
			openInboxFolder();
		}
		if (null == achivedFolder || !achivedFolder.isOpen()) {
			try {
				openArchivedFolder();
			} catch (FolderNotFoundException e) {
				crearFolder(connectToMailServer(), archivedFolderName );
			}			
		}
		List<Message> result = Lists.newArrayList();
//		Oresult = Arrays.asList(mailInbox.getMessages()) ;
		final Message[] unreadMessages = mailInbox.search(new FlagTerm(
				new Flags(Flags.Flag.SEEN), false));
		if (null != unreadMessages && unreadMessages.length > 0) {
			result = ImmutableList.copyOf(unreadMessages);			
		}
		return result;
	}
	
	public String findCertId(Part p) throws MessagingException, IOException {
		return p instanceof Message ? findMimeMessageCertId(p)
				: findNonMimeMessageCertId(p);
	}
	
	
	public String readNewEmail(Part p) throws MessagingException, IOException{
		return p instanceof Message ? getEmailFailed((Message) p)
				: findNonMimeMessageCertId(p);
	}
	
	
	public boolean isFailedMessage(Message mensaje) throws MessagingException{
		boolean b = isFailed(mensaje.getAllHeaders());
		return b;		
	}
	
	/**
	 * Determina si un correo es fallido en su entrega por medio de su cabecera
	 * @param e <code>Enumeration</code> Cabecero del mensaje
	 * @return
	 */
	public boolean isFailed(Enumeration e) {
		  boolean isFailed = false;
		  while (e.hasMoreElements()) {				
				Header h = (Header) e.nextElement();	
				if (h.getName().equals("X-Failed-Recipients"))
					isFailed = true;
			}
		  return isFailed;
	}
	
	/**
	 * Devuelve el destinatario de correo fallido.
	 * Recibe como parametro un objeto <code>Enumeration</code> que
	 * contiene todo el cabecero del mensaje.
	 * @param e <code>Enumeration</code> Cabecero del mensaje
	 * @return <code>String</code> Direccion fallida
	 */
	private String getRecipientsTo(Enumeration e){
		String address = null;		
		  while (e.hasMoreElements()) {
				Header h = (Header) e.nextElement();
				//this is a constant into all head messages
				//System.out.println(">-----" + h.getName() +": "+h.getValue() );
				if (h.getName().equals("X-Failed-Recipients")  ){					
					address = h.getValue();	
					
				}	
			}
		return address;
	}
	
	/**
	 * Lee los mensajes que sean fallidos en la entrega
	 * @param mensaje
	 * @return
	 */
	public String getEmailFailed(Message mensaje){
		String addres = null;
		try {				
			if (mensaje.isMimeType(MIME_TYPE_TEXTPLAIN)) {
				addres = this.getRecipientsTo(mensaje.getAllHeaders());
			} else if (mensaje.isMimeType(MIME_TYPE_MULTIPART)) {
				addres = this.getRecipientsTo(mensaje.getAllHeaders());
			} else if (mensaje.isMimeType(MIME_TYPE_RFC_822)) {
				addres = this.getRecipientsTo(mensaje.getAllHeaders());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return addres;
	}
	
	
	/**
	 * Retorna el id del mansaje fallido.
	 * @param mensaje
	 * @return
	 */
	public String messageId(Message mensaje){
		String tocken = null;
		try {				
			if (mensaje.isMimeType(MIME_TYPE_TEXTPLAIN)) {
				tocken = this.messageIDFailedTextPlain(mensaje);
			} else if (mensaje.isMimeType(MIME_TYPE_MULTIPART)) {
				tocken = this.messageIDFailedMultipar(mensaje);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tocken;
	}
	
	
	/**
	 * Metodo auxiliar del metodo <code>messageId</code> para obtener
	 * el id en un mensaje de tipo Multipart.
	 * @param mensaje
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 */
	public String messageIDFailedMultipar(Message mensaje) throws IOException, MessagingException{
		String messageId = null;
		Multipart mp = (Multipart) mensaje.getContent();	            	
		for (int i = 0; i < mp.getCount(); i++) {
			Part p = mp.getBodyPart(i);
			if (p.isMimeType(MIME_TYPE_RFC_822)) {
				Part p2 = (Part) p.getContent();
				Enumeration head = p2.getAllHeaders();
				while (head.hasMoreElements()) {
					Object object = (Object) head.nextElement();
					Header h = (Header) object;
					if (h.getName().equals("Message-ID")) {
						messageId = h.getValue();
					}
				}
			}
    	}
		return messageId;
	}
	
	/**
	 * Metodo auxiliar del metodo <code>messageId</code> para obtener
	 * el id en un mensaje de tipo Text Plain.
	 * @param mensaje
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 */
	public String messageIDFailedTextPlain(Message mensaje) throws IOException, MessagingException{
		String tocken = null;
		String contenido = (String) mensaje.getContent();
		String[] lines = contenido.split(System.getProperty("line.separator"));
		for (String l : lines) {
			if (l.contains("Message-ID:")) {
				tocken = l.substring(12, l.length());
			}
		}
		return tocken;
	}
	
	
	/**
	 * Copia la coleccion de mensajes dados a la carptea especificada dentros
	 * de los parametros
	 * @param carpetaOrigen
	 * @param carpetaDestino
	 * @param mensajes
	 * @throws MessagingException
	 */
	public void copiarMensajes(Folder carpetaOrigen, Folder carpetaDestino,
			Message mensajes[]) throws MessagingException {
		
		if(carpetaDestino == null){
			crearFolder(connectToMailServer(), archivedFolderName );
		}
		carpetaOrigen.copyMessages(mensajes, carpetaDestino);
	}
	
	/**
	 * Crea una nueva carpeta en el directorio raiz del buzon.
	 * Esta carpeta puede alojar otras carpetas
	 * @param store
	 * @param nombreCarpeta
	 * @return
	 * @throws MessagingException
	 */
	public boolean crearFolder( Store store, String nombreCarpeta) throws MessagingException{
		// se obtiene la carpeta raiz del buzon		 
		Folder defaultFolder = store.getDefaultFolder();   
	    return createFolder(defaultFolder, nombreCarpeta);   		
	}
	
	
	/**
	 * Metodo predicado auxiliar para el metodo crearFolder.
	 * @param padre
	 * @param nuevaCarpeta
	 * @return
	 */
	private boolean createFolder(Folder padre, String nuevaCarpeta)   
	{   
	    boolean isCreated = true;   
	    try {   
	    	Folder newFolder = 
	    			padre.getFolder("INBOX." + nuevaCarpeta.trim().toUpperCase());
	        isCreated = newFolder.create(Folder.HOLDS_MESSAGES);
	    } catch (Exception e) {   
	        System.out.println("Error creating folder: " + e.getMessage());   
	        e.printStackTrace();   
	        isCreated = false;   
	    }   
	    return isCreated;   
	}
	
	private String findMimeMessageCertId(Part p) throws MessagingException {
		Message m = (Message) p;
		String subject = m.getSubject();
		String subjectCertificate = extractCertidFromEmailSubject(subject);
		System.out.println("subjectCertificate:" + subjectCertificate);
		if(subjectCertificate == null)
			return subject;
		else
			return subjectCertificate;			
	}
	
	
	private static String extractCertidFromEmailSubject(String subject) {
		String certid = null;
		if (!Strings.isNullOrEmpty(subject)) {
			Matcher certidMatcher = CERTIFICATE_SUBJECT_PATTERN
					.matcher(subject);
			if (certidMatcher.find()) {
				certid = certidMatcher.group(1).trim().toUpperCase();
			}
		}
		return certid;
	}
	
	
	private String findNonMimeMessageCertId(Part p) throws MessagingException,
			IOException {
		String certid = null;
		if (p.isMimeType(MIME_TYPE_MULTIPART)) {
			Multipart mp = (Multipart) p.getContent();
			int count = mp.getCount();
			for (int i = 0; i < count; i++) {
				return findCertId(mp.getBodyPart(i));
			}
		} else if (p.isMimeType(MIME_TYPE_RFC_822)) {
			certid = findCertId((Part) p.getContent());
		}
		return certid;
	}		
		
	public Folder getMailInbox() {
		return mailInbox;
	}

	public void setMailInbox(Folder mailInbox) {
		this.mailInbox = mailInbox;
	}

	public Folder getAchivedFolder() {
		return achivedFolder;
	}

	public void setAchivedFolder(Folder achivedFolder) {
		this.achivedFolder = achivedFolder;
	}
	
	
	
	
	public String getTipoEmail() {
		return tipoEmail;
	}

	public void setTipoEmail(String tipoEmail) {
		this.tipoEmail = tipoEmail;
	}

	/**
	 * @return the serverSMTPHost
	 */
	public String getServerSMTPHost() {
		return serverSMTPHost;
	}

	// Metodo para imprimir las propiedades del archvio de configuracion
	public void listarPropiedades(Properties tabla) {
		Set<Object> claves = tabla.keySet(); // obtiene los nombres de las
												// propiedades
		// imprime los pares nombre/valor
		for (Object clave : claves) {
			System.out.printf("%s\t%s\n", clave,
					tabla.getProperty((String) clave));
		} // fin de for

		System.out.println();
	} // fin del método listarPropiedades

	
}//FIN de la clase

