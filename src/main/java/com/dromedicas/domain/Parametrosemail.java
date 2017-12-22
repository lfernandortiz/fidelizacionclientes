package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the parametrosemail database table.
 * 
 */
@Entity
@NamedQuery(name="Parametrosemail.findAll", query="SELECT p FROM Parametrosemail p")
public class Parametrosemail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String user;

	private String archived;

	private String host;

	private String password;

	private String port;

	private String protocol;

	@Column(name="smtp_auth")
	private String smtpAuth;

	@Column(name="smtp_host")
	private String smtpHost;

	@Column(name="smtp_password")
	private String smtpPassword;

	@Column(name="smtp_port")
	private String smtpPort;

	private String smtp_socketFactory_class;

	private String smtp_socketFactory_port;

	@Column(name="smtp_starttls_enable")
	private String smtpStarttlsEnable;

	@Column(name="smtp_user")
	private String smtpUser;

	@Column(name="store_protocol")
	private String storeProtocol;

	public Parametrosemail() {
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getArchived() {
		return this.archived;
	}

	public void setArchived(String archived) {
		this.archived = archived;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPort() {
		return this.port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getProtocol() {
		return this.protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getSmtpAuth() {
		return this.smtpAuth;
	}

	public void setSmtpAuth(String smtpAuth) {
		this.smtpAuth = smtpAuth;
	}

	public String getSmtpHost() {
		return this.smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public String getSmtpPassword() {
		return this.smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public String getSmtpPort() {
		return this.smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtp_socketFactory_class() {
		return this.smtp_socketFactory_class;
	}

	public void setSmtp_socketFactory_class(String smtp_socketFactory_class) {
		this.smtp_socketFactory_class = smtp_socketFactory_class;
	}

	public String getSmtp_socketFactory_port() {
		return this.smtp_socketFactory_port;
	}

	public void setSmtp_socketFactory_port(String smtp_socketFactory_port) {
		this.smtp_socketFactory_port = smtp_socketFactory_port;
	}

	public String getSmtpStarttlsEnable() {
		return this.smtpStarttlsEnable;
	}

	public void setSmtpStarttlsEnable(String smtpStarttlsEnable) {
		this.smtpStarttlsEnable = smtpStarttlsEnable;
	}

	public String getSmtpUser() {
		return this.smtpUser;
	}

	public void setSmtpUser(String smtpUser) {
		this.smtpUser = smtpUser;
	}

	public String getStoreProtocol() {
		return this.storeProtocol;
	}

	public void setStoreProtocol(String storeProtocol) {
		this.storeProtocol = storeProtocol;
	}

}