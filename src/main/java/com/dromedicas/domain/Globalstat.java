package com.dromedicas.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the globalstats database table.
 * 
 */
@Entity
@Table(name="globalstats")
@NamedQueries({@NamedQuery(name="Globalstat.findAll", query="SELECT g FROM Globalstat g")})
public class Globalstat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idglobalstats;

	private String ipremota;

	private String sistemaoperativo;

	private String useragent;

	//bi-directional one-to-one association to Emailenvio
	@OneToOne
	@JoinColumn(name="idmail")
	private Emailenvio emailenvio;

	public Globalstat() {
	}

	public int getIdglobalstats() {
		return this.idglobalstats;
	}

	public void setIdglobalstats(int idglobalstats) {
		this.idglobalstats = idglobalstats;
	}

	public String getIpremota() {
		return this.ipremota;
	}

	public void setIpremota(String ipremota) {
		this.ipremota = ipremota;
	}

	public String getSistemaoperativo() {
		return this.sistemaoperativo;
	}

	public void setSistemaoperativo(String sistemaoperativo) {
		this.sistemaoperativo = sistemaoperativo;
	}

	public String getUseragent() {
		return this.useragent;
	}

	public void setUseragent(String useragent) {
		this.useragent = useragent;
	}

	public Emailenvio getEmailenvio() {
		return this.emailenvio;
	}

	public void setEmailenvio(Emailenvio emailenvio) {
		this.emailenvio = emailenvio;
	}

}