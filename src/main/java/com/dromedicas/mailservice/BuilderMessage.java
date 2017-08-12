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

public class BuilderMessage {

	private MimeMessage message;  
	  
    private String subject;  
    private String text;  
    private FileDataSource attachment;  
    private String from;  
    private String multipleRecipients[]; 
    private String recipient; 
    
    private static final String MESSAGE_TEXT_CONTENT_PARAMS = "text/plain; charset=UTF-8";
    private static final String MESSAGE_HTML_CONTENT_PARAMS = "text/html; charset=utf-8";
    
    public BuilderMessage(Session session){
    	this.setMessage(new MimeMessage(session));
    }
    
	public MimeMessage buildText(String type) throws MessagingException {  
		System.out.println(new InternetAddress(this.getFrom()).getAddress());
        message.setFrom(new InternetAddress(this.getFrom()));  
        message.setRecipients(Message.RecipientType.TO,  
                InternetAddress.parse(this.getRecipient()));  
        String subject = this.subject.replace('\n', ' ');  
        message.setSubject(subject);     
        message.setHeader("X-Failed-Recipients", "<123@tockenfinal>");
        message.setContent(createBodyPart(type));  
        return message;  
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

	public Message getMessage() {
		return message;
	}

	public void setMessage(MimeMessage message) {
		this.message = message;
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

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String[] getMultipleRecipients() {
		return multipleRecipients;
	}

	public void setMultipleRecipients(String[] multipleRecipients) {
		this.multipleRecipients = multipleRecipients;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
    
}
