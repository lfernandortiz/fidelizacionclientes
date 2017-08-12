package com.dromedicas.mailservice;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class CustomMimeMessage extends MimeMessage {
	
	private String destino;
	private String origen;
	private String subject;  
    private String text;  
    private FileDataSource attachment;  
    private String multipleRecipients[]; 
    private String uniqueMessageId;

    
    private static final String MESSAGE_TEXT_CONTENT_PARAMS = "text/plain; charset=UTF-8";
    private static final String MESSAGE_HTML_CONTENT_PARAMS = "text/html; charset=utf-8";
    
	public CustomMimeMessage(Session session , String uniqueMessageId) {
		super(session);
		this.uniqueMessageId = uniqueMessageId;
		// TODO Auto-generated constructor stub
	}
	
	public MimeMessage buildText(String type) throws MessagingException {  
		
        super.setFrom(new InternetAddress(this.getOrigen()));  
        super.setRecipients(Message.RecipientType.TO,  
                InternetAddress.parse(this.getDestino()));  
        String subject = this.subject.replace('\n', ' ');  
        super.setSubject(subject);
        this.updateHeaders();
        super.setContent(createBodyPart(type));  
        return this;  
    }  

	
	protected void updateMessageID() throws MessagingException {
        setHeader("Message-ID", "<" + uniqueMessageId + ">");
    }
	
	
    private Multipart createBodyPart(String type) throws MessagingException {  
        Multipart multipart = new MimeMultipart();  
        if("HTML".equals(type)){        	
        	multipart.addBodyPart(createHTMLPart());
        }else{
        	multipart.addBodyPart(createTextPart()); 
        }
         
        if (null != attachment) {  
            multipart.addBodyPart(createAttacmentPart());  
        }  

        return multipart;  

    }  

    private BodyPart createTextPart() throws MessagingException {  
        BodyPart messageBody = new MimeBodyPart();  
        messageBody.setContent(text, MESSAGE_TEXT_CONTENT_PARAMS);  

        return messageBody;  
    }  
    
    private BodyPart createHTMLPart() throws MessagingException {  
        BodyPart messageBody = new MimeBodyPart();          
        messageBody.setContent(this.getText(), MESSAGE_HTML_CONTENT_PARAMS);  
        return messageBody;  
    }  
    
    private BodyPart createAttacmentPart() throws MessagingException {  
        BodyPart attachmentPart = new MimeBodyPart();  
        attachmentPart.setDataHandler(new DataHandler(attachment));  
        attachmentPart.setFileName(attachment.getName());  

        return attachmentPart;  

    }

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public FileDataSource getAttachment() {
		return attachment;
	}

	public void setAttachment(FileDataSource attachment) {
		this.attachment = attachment;
	}

	public String[] getMultipleRecipients() {
		return multipleRecipients;
	}

	public void setMultipleRecipients(String[] multipleRecipients) {
		this.multipleRecipients = multipleRecipients;
	}

    
    
    

}
